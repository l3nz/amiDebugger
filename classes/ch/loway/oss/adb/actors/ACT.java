
package ch.loway.oss.adb.actors;

/**
 *
 *
 * $Id$
 * @author lenz
 */
public class ACT {

    public static final String ActorSystem = "adb";

    public static String getPath( Class c ) {
        return "akka://" + ActorSystem + "/user/" + c.getSimpleName();
    }

}

// $Log$
//
