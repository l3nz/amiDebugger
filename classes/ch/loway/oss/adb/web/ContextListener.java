
package ch.loway.oss.adb.web;

import akka.actor.ActorSystem;
import ch.loway.oss.adb.AS;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * See http://docs.oracle.com/javaee/5/tutorial/doc/bnafi.html
 *
 * $Id$
 * @author lenz
 */
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
    //    context = event.getServletContext();

        ActorSystem system = AS.getInstance().system;
        system.log().error("Actor system up");
    }

    public void contextDestroyed(ServletContextEvent event) {
    //    context = event.getServletContext();

        

        ActorSystem system = AS.getInstance().system;
        system.log().error("Actor system going down");
        
        system.shutdown();
        system.awaitTermination();


    }
}

