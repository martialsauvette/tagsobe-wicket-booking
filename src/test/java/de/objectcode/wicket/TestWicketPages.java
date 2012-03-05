package de.objectcode.wicket;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.objectcode.WicketApplication;
import de.objectcode.data.dao.interfaces.EventDao;
import de.objectcode.pages.EventPage;
import de.objectcode.pages.Home;
import de.objectcode.pages.Main;

/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public class TestWicketPages {
	
	protected WicketTester tester;
	
	@Before
	public void setup()
	{
		final ApplicationContextMock acm = new ApplicationContextMock();
		
		EventDao eventDao = Mockito.mock(EventDao.class);
		
		acm.putBean("eventDao", eventDao);
		
		tester = new WicketTester(new WicketApplication(){
			/* (non-Javadoc)
			 * @see de.objectcode.WicketApplication#getGuiceInjector()
			 */
			@Override
			protected SpringComponentInjector getSpringInjector() {
				return  new SpringComponentInjector(this, acm, true);
			}
		});
	}
	
	@Test
	public void testStartPage()
	{
		tester.startPage(Home.class);
	}
	
	@Test
	public void testHomePage()
	{
		Home home = (Home)tester.startPage(Home.class);
		FormTester form = tester.newFormTester("login");
        form.setValue("username", "keith");
        form.setValue("password", "melbourne");        
        form.submit();
        tester.getLastRenderedPage();
        tester.assertRenderedPage(Main.class);
	}

	
	@Test
	public void testEventPage()
	{
		tester.startPage(EventPage.class);
	}

}
