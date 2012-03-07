package de.objectcode;

import org.apache.wicket.Page;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.objectcode.pages.EventPage;
import de.objectcode.pages.Home;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see wicket.myproject.Start#main(String[])
 * 
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public class WicketApplication extends AuthenticatedWebApplication
 {    
	/**
	 * Constructor
	 */
//	private ApplicationContext ctx;
//
//	public void setApplicationContext(
//			ApplicationContext applicationContext) throws BeansException {
//			this.ctx = applicationContext;
//			}
//
//	public ApplicationContext getSpringContext(){
//		return this.ctx;
//	}
//	
//	public WicketApplication()
//	{
//	}
	
	protected SpringComponentInjector getSpringInjector()
	{
		return new SpringComponentInjector(this);
	}
	
	public ApplicationContext getApplicationContext()
	{
	return WebApplicationContextUtils
	.getRequiredWebApplicationContext(getServletContext());
	}

	@Override
	protected void init() {
		super.init();

		mountBookmarkablePage("event", EventPage.class);
//		this.ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		addComponentInstantiationListener(getSpringInjector());
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return Home.class;
	}
	
	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return HotelBookingWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Home.class;
	}




}
