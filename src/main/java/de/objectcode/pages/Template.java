package de.objectcode.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.Link;

import de.objectcode.HotelBookingWebSession;
import de.objectcode.data.dataobjects.User;


public class Template extends Border
{

  //@In 
   private User user;
   
  // @In
  // private Identity identity;
   
   public Template(String id)
   {
      super(id);
      this.user = ((HotelBookingWebSession)this.getSession()).getUser() ;
//      add(new Link("search")
//      {
//         @Override
//         public void onClick()
//         {
//            //Manager.instance().leaveConversation();
//            setResponsePage(Main.class);
//         }
//      });
//      add(new Link("settings")
//      {
//         @Override
//         public void onClick()
//         {
//            //Manager.instance().leaveConversation();
//            //setResponsePage(Password.class);
//         }
//      });
      add(new Link("logout")
      {
         @Override
         public void onClick()
         {
        	 ((HotelBookingWebSession)this.getSession()).signOut();
            setResponsePage(Home.class);
         }
      });

      add(new Label("userName", user.getName()));
//      add(new Label("userName", "HOURA!!!"));
   }

}
