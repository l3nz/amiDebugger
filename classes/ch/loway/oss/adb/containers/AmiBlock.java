
package ch.loway.oss.adb.containers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * $Id$
 * @author lenz
 */
public class AmiBlock {

    List<String> lData = new ArrayList<String>();

    public void addEntry( String row ) {
        lData.add(row);
    }

    public String getToken( String token ) {
        String tokenPlus = token + ": ";

        for ( String s: lData ) {
            if ( s.startsWith( tokenPlus )) {
                return s.substring( tokenPlus.length() );
            }
        }

        return "";
    }


    public String getResponse() {
        return getToken("Response");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for ( String s: lData ) {
            sb.append(s).append( "\n" );
        }
        sb.append( "\n" );
        return sb.toString();
    }

    /**
     * Prepares a login commnad.
     * 
     * @param user
     * @param pass
     * @param events
     * @return
     */

    public static AmiBlock Login( String user, String pass, boolean events ) {
        AmiBlock a = new AmiBlock();
        a.addEntry("Action: login");
        a.addEntry("Username: " + user);
        a.addEntry("Secret: " + pass);
        a.addEntry("Events: " + (events ? "on" : "off"));
        return a;
    }


    public static AmiBlock LogOff() {
        AmiBlock a = new AmiBlock();
        a.addEntry("Action: logoff");
        return a;
    }



}

// $Log$
//
