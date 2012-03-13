package de.objectcode.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import de.objectcode.data.dataobjects.User;

public class Home extends WebPage {

	private User user;

	private static final long serialVersionUID = 1L;

	public Home(final PageParameters parameters) {
		this.user = new User();
		add(new LoginForm("login"));
	}

	public class LoginForm extends Form {
		public LoginForm(String id) {
			super(id);
			add(new TextField("username", new PropertyModel(user, "username")));
			add(new PasswordTextField("password", new PropertyModel(user,"password")));
	        add(new BookmarkablePageLink("register", Register.class));
	        add(new FeedbackPanel("messages"));

		}

		@Override
		protected void onSubmit() {
			try {
				 
				AuthenticatedWebSession.get().signIn(user.getUsername(), user.getPassword());
				if (AuthenticatedWebSession.get().isSignedIn()){
					setResponsePage(Main.class);					
				} else {
					setResponsePage(Home.class);					
				}
			} catch (Exception e) {
				error("Login failed");
				e.printStackTrace();
			}
		}

	}
}
