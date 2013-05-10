package ch.loway.oss.adb.actors;


import akka.actor.ActorRef;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Duration;
import ch.loway.oss.adb.containers.AmiBlock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * This actor opens a connection to an AMI instance and dies when it goes down.
 *
 * $Id$
 * @author lenz
 */
public class AmiTracker extends UntypedActor {

    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

    Socket sock = null;
    InputStream in   = null;
    OutputStream out = null;
    InputStreamReader isr = null;
    BufferedReader bri = null;

    boolean isConnected = false;
    public static int MAX_CONNECT_TIMEOUT = 2000;


    StartMsg currentServer = null;

    public AmiTracker() {
        //getContext().setReceiveTimeout(Duration.create(100, TimeUnit.MILLISECONDS));
        getContext().setReceiveTimeout( Duration.create(200, java.util.concurrent.TimeUnit.MILLISECONDS));
    }



    public void onReceive(Object message) throws Exception {
        if (message instanceof StartMsg) {

            currentServer = (StartMsg) message;
            //disconnectFromAsterisk();
            connect();
            
        } else
        if ( message instanceof SendCmd) {
            AmiBlock blkIn = ((SendCmd) message).cmd;
            sendSocketMessage(blkIn);
            addBlockToListHandler( blkIn );

        } else
        if (message instanceof ReceiveTimeout) {
                logger.info("On timeout");
        } else {
            //logger.error("Unknown message " + message.toString() + " - " + message.getClass().getCanonicalName() );
        }

        AmiBlock blk = readCompleteBlock();
        addBlockToListHandler(blk);

    }

    private void addBlockToListHandler( AmiBlock blk ) {
        if ( !blk.isEmpty() ) {
            ActorRef history = getContext().actorFor( ACT.getPath(HistoryHandler.class));

            if ( history != null ) {
                history.tell( HistoryHandler.AddBlock.build(blk) );
            }

            logger.info( blk.toString() );
        }
    }


    private boolean connect() throws IOException {

        String stServer = currentServer.address;
        int    inPort   = currentServer.port;
        String stUser  = currentServer.login;
        String stPass   = currentServer.password;

        isConnected = false;

        openSocket(stServer, inPort);

        

        sendSocketOutput(AmiBlock.Login(stUser, stPass, true));

        AmiBlock resp = readCompleteBlock();

        String response = resp.getResponse();
        if (response.equalsIgnoreCase("success")) {
            logger.info("Logged on OK");
            isConnected = true;
            return true;
        } else {
            logger.error("Error logging on - '" + response + "'");
            addBlockToListHandler( AmiBlock.AmiMessage("Error logging on to " + stServer));
            return false;
        }
    }

    private void disconnectFromAsterisk() throws IOException {
        sendSocketOutput(AmiBlock.LogOff());
        closeSocket();
        logger.info("Disconnected  from Asterisk");
        
    }


    private void openSocket( String server, int port ) throws IOException {
        try {
            sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress( InetAddress.getByName(server), port );
            sock.connect( sockaddr, MAX_CONNECT_TIMEOUT );

            in  = sock.getInputStream();
            out = sock.getOutputStream();

            isr = new InputStreamReader(in, "US-ASCII");
            bri = new BufferedReader( isr );

            // carica che ci sia Asteirk Call Manager....
            String stWelcome = readSocketResponse_blocking();
            if ( stWelcome.startsWith( "Asterisk Call Manager/" ) ) {
                // we have an AMI
            } else {
                throw new IOException( "Unknown ACM version: '" + stWelcome + "'" );
            }


        } catch ( Exception e ) {
            throw new IOException(
                "Error TCP connecting to: " + server + ":" + port + " timeout: " + MAX_CONNECT_TIMEOUT + "ms\n" + e.toString()
            );
        }
    }

    public void closeSocket() {
        try {

            if ( bri  != null ) {
                bri.close();
            }

            if ( isr != null ) {
                isr.close();
            }

            if ( in != null ) {
                in.close();
            }

            if ( out != null ) {
                out.close();
            }

            if ( sock != null ) {
                if ( sock.isConnected() ) {
                    sock.close();
                }
            }
        } catch ( Throwable t ) {
            // ullalla ullalla ullalla
            // questo e' il valzer del moscerino
            logger.error("Exception trying to close resources", t);
        }
    }

    private String readSocketResponse() throws IOException {
        if ( bri.ready() ) {
            return bri.readLine();
        } else {
            return null;
        }
    }

    private String readSocketResponse_blocking() throws IOException {
        String res = null;
        int maxWaitLoops = 50; // 500 * 10 ms = 5 secondi

        while ( maxWaitLoops > 0 ) {
            res = readSocketResponse();
            if ( res != null ) {
                //logger( "[BLK]" + res );
                return res;
            }
            sleep(10);
            maxWaitLoops = maxWaitLoops - 1;
        }

        return "";
    }


    private AmiBlock  readCompleteBlock() throws IOException {
        AmiBlock blk = new AmiBlock();
        String s = null;
        long beforeReading = System.currentTimeMillis();

        do {
            s = readSocketResponse_blocking();
            blk.addEntry(s);
        } while ( s.length() > 0);

        //logIO( "BlockRead", beforeReading, blk );

        return blk;
    }


    public boolean sendSocketMessage( AmiBlock e ) {
        try {
            sendSocketOutput(e);
            return true;
        } catch ( IOException ex ) {
            isConnected = false;
            return false;
        }
    }


    public void sendSocketOutput( AmiBlock e ) throws IOException {

        long beforeWriting = System.currentTimeMillis();
        
        sendSocketOutput( e.toString());

        //logIO( "BlockWrite", beforeWriting, e );
    }


    public void sendSocketOutput( String s ) throws IOException {

        //logger( "[OUT]" + s);

        byte[] b = s.getBytes( "US-ASCII" );

        if ( out != null ) {
            out.write(b);
            out.flush();
        } else {
            logger.error("Cannot write on closed socket  - " + s);
            throw new IOException( "Out socket not initialized");
        }
    }

    private void sleep( int inMillis ) {
        try {
            Thread.sleep( inMillis ) ;
        } catch ( Exception e ) {
            // fun coool
        }
    }





    /**
     * Tells this actor to start connecting to an Asterisk instance.
     */

    public static class StartMsg {
        String address = "";
        int port = 5038;
        String login = "";
        String password = "";

        public static StartMsg build( String address, int port, String login, String password ) {
            StartMsg m = new StartMsg();
            m.address = address;
            m.port    = port;
            m.login   = login;
            m.password= password;
            return m;
        }
    }

    public static class SendCmd {
        AmiBlock cmd = null;
        public static SendCmd build( AmiBlock cmd ) {
            SendCmd c = new SendCmd();
            c.cmd = cmd;
            return c;
        }
    }

}
