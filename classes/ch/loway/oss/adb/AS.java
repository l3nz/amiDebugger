/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.loway.oss.adb;

import akka.actor.ActorSystem;
import ch.loway.oss.adb.actors.ACT;

/**
 *
 * @author lenz
 */
public class AS {

    public ActorSystem system = null;


    public final synchronized ActorSystem restartAS() {
        if ( system != null) {

            system.log().error("Shutting down AS");

            system.shutdown();
            system.awaitTermination();
        
            system.log().error("Starting AS");
        }
        
        system = ActorSystem.create( ACT.ActorSystem );
        return system;
    }


    private AS() {
        restartAS();
    }

    public static AS getInstance() {
        return ASHolder.INSTANCE;
    }

    private static class ASHolder {
        private static final AS INSTANCE = new AS();
    }
 }
