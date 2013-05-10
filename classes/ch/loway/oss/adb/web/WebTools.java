
package ch.loway.oss.adb.web;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * $Id$
 * @author lenz
 */
public class WebTools {

    public static String getS( HttpServletRequest req, String label ) {

        String s = req.getParameter(label);

        if ( s == null ) {
            return "";
        } else {
            return s.trim();
        }
    }


    public static int getI( HttpServletRequest req, String label ) {

        return cint( getS( req, label) );

    }


    public static int cint( String s ) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e ) {
            return 0;
        }
    }

}

// $Log$
//
