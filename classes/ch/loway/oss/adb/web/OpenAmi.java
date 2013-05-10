
package ch.loway.oss.adb.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import ch.loway.oss.adb.AS;
import ch.loway.oss.adb.actors.ACT;
import ch.loway.oss.adb.actors.AmiTracker;
import ch.loway.oss.adb.actors.HistoryHandler;
import ch.loway.oss.adb.containers.AmiBlock;

/**
 *
 *
 * $Id$
 * @author lenz
 */
public class OpenAmi {

    public void open( String srv, int port, String login, String pass ) {

        ActorSystem system = AS.getInstance().restartAS();
        system.log().warning("Before actor created");

        ActorRef trackerActor = system.actorOf(new Props(AmiTracker.class), AmiTracker.class.getSimpleName() );
        ActorRef listActor = system.actorOf(new Props(HistoryHandler.class), HistoryHandler.class.getSimpleName() );

        system.log().warning("Before msg sent");
        trackerActor.tell( AmiTracker.StartMsg.build(srv, port, login, pass));
        
    }

    public void sendCmd( String cmd ) {
        String[] lines = cmd.split("[\\r\\n]");
        AmiBlock blk = new AmiBlock();
        for ( String line: lines ) {
            blk.addEntry(line);
        }
        blk.setUserCommand();

        ActorSystem system = AS.getInstance().system;
        ActorRef trackerActor = system.actorFor( ACT.getPath(AmiTracker.class));
        trackerActor.tell( AmiTracker.SendCmd.build(blk));

    }



}

// $Log$
//
