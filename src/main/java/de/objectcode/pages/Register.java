package de.objectcode.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.objectcode.data.dao.interfaces.UserDao;
import de.objectcode.data.dataobjects.User;

public class Register extends WebPage
{
	

	private User user;
	
	WebPage errorPage;
	private TextField username;
	
	@SpringBean
	private UserDao userDao;
	
    FeedbackPanel messages;
	
	public Register(final PageParameters parameters)
    {	
		user= new User();
		errorPage=this;
		Form registerForm = new RegisterForm("registration");  
		add(registerForm);
		registerForm.add(new FeedbackPanel("messages"));

    }

	public class RegisterForm extends Form
	{

	   private String verify;
	   
	   public String getVerify()
      {
         return verify;
      }
	   
	   public void setVerify(String verify)
      {
         this.verify = verify;
      }
	   
      public RegisterForm(String id)
      {
         super(id);
         add(new PageLink("cancel", Home.class));
         username = new TextField("username");
         username.setRequired(true);
         add(new FormInputBorder("usernameDecorate", "Username", username, new PropertyModel(user,"username")));
         add(new FormInputBorder("nameDecorate", "Real Name", new TextField("name").setRequired(true), new PropertyModel(user,"name")));
         FormComponent password = new PasswordTextField("password").setRequired(true);
         FormComponent verify = new PasswordTextField("verify").setRequired(true);
         add(new FormInputBorder("passwordDecorate", "Password", password , new PropertyModel(user,"password")));
         add(new FormInputBorder("verifyDecorate", "Verify Password", verify, new PropertyModel(this, "verify")));
         add(new EqualPasswordInputValidator(password, verify));         
      }
      
      @Override
		protected void onSubmit() {
			if (userDao.userAlreadyExist(user.getName())) {
				error("Username " +user.getName()+ " already exists");
				user.setPassword("");
				verify = "";
				setResponsePage(errorPage);
			} else {
				userDao.createUser(user);
				setResponsePage(Home.class);
//				try {
//					AuthenticatedWebSession.get().signIn(user.getUsername(),user.getPassword());
//					if (AuthenticatedWebSession.get().isSignedIn()) {
//						setResponsePage(Main.class);						
//					} else {
//						error("Login failed");
//						setResponsePage(Home.class);
//					}
//				} catch (Exception e) {
//					error("Login failed");
//					e.printStackTrace();
//				}
			}


		}
      
	}
	
}
