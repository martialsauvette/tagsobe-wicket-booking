/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.objectcode.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import de.objectcode.data.dataobjects.User;

public class Home extends WebPage {

	// @In
	// private Identity identity;

	// @Logger
	// private Log log;
	private User user;
	// private TextField username;
	// private PasswordTextField password;
	private static final long serialVersionUID = 1L;

	// public Home(final PageParameters parameters)
	// {
	// Template body = new Template("body");
	// add(body);
	//
	// // body.add(new SignInPanel("signInPanel"));
	// }

	public Home(final PageParameters parameters) {
		this.user = new User();
		add(new LoginForm("login"));
	}

	public class LoginForm extends Form {
		public LoginForm(String id) {
			super(id);
			add(new TextField("username", new PropertyModel(user, "username")));
			add(new PasswordTextField("password", new PropertyModel(user,
					"password")));
			// add(new BookmarkablePageLink("register", Register.class));
			add(new FeedbackPanel("messages"));
		}

		@Override
		protected void onSubmit() {
			try {
				 
				AuthenticatedWebSession.get().signIn(user.getUsername(), user.getPassword());
				if (AuthenticatedWebSession.get().isSignedIn()){
					setResponsePage(Main.class);
					System.out.println("########## logged in ##############");
				} else {
					setResponsePage(Home.class);
					System.out.println("########## Password falsch ##############");
				}
			} catch (Exception e) {
				error("Login failed");
				e.printStackTrace();
			}
		}

	}
}
