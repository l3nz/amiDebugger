
package ch.loway.oss.adb.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
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
public class HistoryHandler extends UntypedActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    List<AmiBlock> lBlocks = new ArrayList<AmiBlock>();


    @Override
    public void onReceive(Object o) throws Exception {
        if ( o instanceof AddBlock ) {
            AddBlock ab = (AddBlock) o;
            lBlocks.add( ab.blk );

            logger.info( "Total blocks: " + lBlocks.size() );


        } else
        if ( o instanceof FetchBlocks ) {

            logger.info ( "Got request - have " + lBlocks.size() + " blocks");

            FetchBlocks b = (FetchBlocks) o;
            List<AmiBlock> out = new ArrayList<AmiBlock>();

            for ( int i = b.minBlock; i < lBlocks.size(); i++ ) {
                out.add( lBlocks.get(i) );
            }

            getSender().tell( FetchBlocks.buildAnswer(b.minBlock, out), getSelf());

        } else {
            unhandled(o);
        }
    }



    public static final class AddBlock {
        AmiBlock blk = null;

        public static AddBlock build( AmiBlock blk ) {
            AddBlock ab = new AddBlock();
            ab.blk = blk.dup();
            return ab;
        }
    }

    public static final class FetchBlocks {
        public List<AmiBlock> list = null;
        int minBlock = 0;

        public static FetchBlocks buildQuery( int minBlock ) {
            FetchBlocks b = new FetchBlocks();
            b.minBlock = minBlock;
            return b;
        }

        public static FetchBlocks buildAnswer( int minBlock, List<AmiBlock> results ) {
            FetchBlocks b = new FetchBlocks();
            b.minBlock = minBlock;
            b.list = Collections.unmodifiableList(results);
            return b;
        }

    }


}

// $Log$
//
