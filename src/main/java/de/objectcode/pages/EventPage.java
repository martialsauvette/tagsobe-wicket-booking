package de.objectcode.pages;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.objectcode.data.dao.interfaces.EventDao;
import de.objectcode.data.dataobjects.Event;
/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public class EventPage extends WebPage {
	
	@SpringBean
	private EventDao eventDao;
	
	public EventPage(final PageParameters pp)
	{
		Form<Event> eventForm = new Form<Event>("eventForm", new CompoundPropertyModel<Event>(new Event()));
		eventForm.add(new TextField<String>("title").setRequired(true));
		eventForm.add(new TextField<String>("location").setRequired(true));
		
		final WebMarkupContainer wmc = new WebMarkupContainer("listContainer");
		
		wmc.add(new ListView<Event>("list", new PropertyModel<List<Event>>(this, "eventDao.findAll")){

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Event> item) {
				Event event = item.getModelObject();
				item.add(new Label("eventName", event.getTitle()));
				item.add(new Label("eventLocation", event.getLocation()));
			}
			
		});
		wmc.setOutputMarkupId(true);
		add(wmc);
		
		eventForm.add(new AjaxSubmitLink("submit"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Event event = (Event) form.getModelObject();
				Event newEvent = new Event();
				newEvent.setLocation(event.getLocation());
				newEvent.setTitle(event.getTitle());
				eventDao.save(newEvent);
				target.addComponent(wmc);
			}
		});
		
		
		add(eventForm);
		
	}
}
