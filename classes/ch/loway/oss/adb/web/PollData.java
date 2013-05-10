
package ch.loway.oss.adb.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;
import ch.loway.oss.adb.AS;
import ch.loway.oss.adb.actors.ACT;
import ch.loway.oss.adb.actors.HistoryHandler;
import ch.loway.oss.adb.containers.AmiBlock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * $Id$
 * @author lenz
 */
public class PollData {

    public String poll(final int from, final String filter ) throws Exception {

        ActorSystem system = AS.getInstance().system;
        ActorRef listActor = system.actorFor( ACT.getPath(HistoryHandler.class));

        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(listActor, HistoryHandler.FetchBlocks.buildQuery(from), timeout);
        HistoryHandler.FetchBlocks result = (HistoryHandler.FetchBlocks) Await.result(future, timeout.duration());

        StringBuilder sb = new StringBuilder();

        List<AmiBlock> lData = new ArrayList<AmiBlock>( result.list );
        Collections.reverse(lData);

        for ( AmiBlock b: lData) {
            if ( b.containsString(filter) ) {
                sb.append( b.toHtml() ).append("\n");
            }
        }

        return sb.toString();

    }

}

// $Log$
//
