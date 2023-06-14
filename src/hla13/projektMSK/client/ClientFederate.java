package hla13.projektMSK.client;

import hla.rti.*;
import hla.rti.jlc.EncodingHelpers;
import hla.rti.jlc.RtiFactoryFactory;
import hla.rti1516.ParameterHandle;
import hla.rti1516.ParameterHandleValueMap;
import hla.rti1516.jlc.EncoderFactory;
import org.portico.impl.hla13.types.DoubleTime;
import org.portico.impl.hla13.types.DoubleTimeInterval;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Random;

/**
 * Created by Michal on 2016-04-27.
 */
public class ClientFederate {

    public static final String READY_TO_RUN = "ReadyToRun";
    //----------------------------------------------------------
    //                   INSTANCE VARIABLES
    //----------------------------------------------------------
    private RTIambassador rtiamb;
    private ClientAmbassador fedamb;
    private final double timeStep           = 10.0;
    //to nwm czy ma zostać
    protected EncoderFactory encoderFactory;

    //klienci w kolejce krotkiej
    protected int numberOfClientsInFastQ = 0;
    protected int numberOfClientsInSlowQ = 0;
    protected int numberOfProductsForClientF = 0;
    protected int numberOfProductsForClientS = 0;
    //roboczo
    protected int maxNumberOfClients = 20;

    protected int maxNumberOfClientsInSlowQ = 5;
    protected int maxNumberOfClientsInFastQ = 5;

    public void runFederate() throws RTIexception {
        /////////////////////////////////////////////////
        // 1 . create the RTIambassador //
        /////////////////////////////////////////////////

        rtiamb = RtiFactoryFactory.getRtiFactory().createRtiAmbassador();

        //////////////////////////////
        // 2. create the federation //
        //////////////////////////////
        // create
        // NOTE: some other federate may have already created the federation,
        //       in that case, we'll just try and join it


        try
        {
            File fom = new File( "producer-consumer.fed" );
            rtiamb.createFederationExecution( "ExampleFederation",
                    fom.toURI().toURL() );
            log( "Created Federation" );
        }
        catch( FederationExecutionAlreadyExists exists )
        {
            log( "Didn't create federation, it already existed" );
        }
        catch( MalformedURLException urle )
        {
            log( "Exception processing fom: " + urle.getMessage() );
            urle.printStackTrace();
            return;
        }

        ////////////////////////////
        // 3. join the federation //
        ////////////////////////////
        // create the federate ambassador and join the federation


        fedamb = new ClientAmbassador();
        rtiamb.joinFederationExecution( "ClientFederate", "ExampleFederation", fedamb );
        log( "Joined Federation as ClientFederate");

        ////////////////////////////////
        // 4. announce the sync point //
        ////////////////////////////////
        // announce a sync point to get everyone on the same page. if the point
        // has already been registered, we'll get a callback saying it failed,
        // but we don't care about that, as long as someone registered it


        rtiamb.registerFederationSynchronizationPoint( READY_TO_RUN, null );
        //czekanie na ogloszenie punktu synchronizacji
        while( fedamb.isAnnounced == false )
        {
            rtiamb.tick();
        }
        //czekanie na innych wedaratow
        waitForUser();

        ///////////////////////////////////////////////////////
        // 5. achieve the point and wait for synchronization //
        ///////////////////////////////////////////////////////
        // tell the RTI we are ready to move past the sync point and then wait
        // until the federation has synchronized on

        rtiamb.synchronizationPointAchieved( READY_TO_RUN );
        log( "Achieved sync point: " +READY_TO_RUN+ ", waiting for federation..." );
        while( fedamb.isReadyToRun == false )
        {
            rtiamb.tick();
        }

        /////////////////////////////
        // 6. enable time policies //
        /////////////////////////////
        // in this section we enable/disable all time policies
        // note that this step is optional!



        enableTimePolicy();


        //////////////////////////////
        // 7. publish and subscribe //
        //////////////////////////////
        // in this section we tell the RTI of all the data we are going to
        // produce, and all the data we want to know about


        publishAndSubscribe();

        ////////////////////////////////////
        // 9. do the main simulation loop //
        ////////////////////////////////////
        // here is where we do the meat of our work. in each iteration, we will
        // update the attribute values of the object we registered, and will
        // send an interaction.

        Client client = new Client();

        while (fedamb.running) {
            int dodaniKlienci = client.produce();
            if(numberOfClientsInFastQ + dodaniKlienci <= maxNumberOfClients && numberOfClientsInSlowQ + dodaniKlienci <= maxNumberOfClientsInSlowQ ){
                log("test");
            }else{
                log("Jest juz za duzo klientów w jednej z kolejek");
            }


            advanceTime(randomTime());
            sendInteraction(fedamb.federateTime + fedamb.federateLookahead);
            rtiamb.tick();
        }

    }
    //zostaje tak jak jest
    private void waitForUser()
    {
        log( " >>>>>>>>>> Press Enter to Continue <<<<<<<<<<" );
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in) );
        try
        {
            reader.readLine();
        }
        catch( Exception e )
        {
            log( "Error while waiting for user input: " + e.getMessage() );
            e.printStackTrace();
        }
    }

    private void enableTimePolicy() throws RTIexception
    {
        LogicalTime currentTime = convertTime( fedamb.federateTime );
        LogicalTimeInterval lookahead = convertInterval( fedamb.federateLookahead );

        this.rtiamb.enableTimeRegulation( currentTime, lookahead );

        while( fedamb.isRegulating == false )
        {
            rtiamb.tick();
        }

        this.rtiamb.enableTimeConstrained();

        while( fedamb.isConstrained == false )
        {
            rtiamb.tick();
        }
    }

    private void sendInteraction(double timeStep) throws RTIexception {
        SuppliedParameters parameters =
                RtiFactoryFactory.getRtiFactory().createSuppliedParameters();
        Random random = new Random();
        int quantityInt = random.nextInt(10) + 1;
        byte[] quantity = EncodingHelpers.encodeInt(quantityInt);

        int interactionHandle = rtiamb.getInteractionClassHandle("InteractionRoot.GetProduct");
        int quantityHandle = rtiamb.getParameterHandle( "quantity", interactionHandle );

        parameters.add(quantityHandle, quantity);

        LogicalTime time = convertTime( timeStep );
        log("Sending GetProduct: " + quantityInt);
        // TSO
        rtiamb.sendInteraction( interactionHandle, parameters, "tag".getBytes(), time );
//        // RO
//        rtiamb.sendInteraction( interactionHandle, parameters, "tag".getBytes() );
    }

    private void publishAndSubscribe() throws RTIexception {
        int addProductHandle = rtiamb.getInteractionClassHandle( "InteractionRoot.GetProduct" );
        rtiamb.publishInteractionClass(addProductHandle);
    }

    //to tez zostaje tak jak jest
    private void advanceTime( double timestep ) throws RTIexception
    {
        log("requesting time advance for: " + timestep);
        // request the advance
        fedamb.isAdvancing = true;
        LogicalTime newTime = convertTime( fedamb.federateTime + timestep );
        rtiamb.timeAdvanceRequest( newTime );
        while( fedamb.isAdvancing )
        {
            rtiamb.tick();
        }
    }

    private double randomTime() {
        Random r = new Random();
        return 1 +(4 * r.nextDouble());
    }

    private LogicalTime convertTime( double time )
    {
        // PORTICO SPECIFIC!!
        return new DoubleTime( time );
    }

    /**
     * Same as for {@link #convertTime(double)}
     */
    private LogicalTimeInterval convertInterval( double time )
    {
        // PORTICO SPECIFIC!!
        return new DoubleTimeInterval( time );
    }

    private void log( String message )
    {
        System.out.println( "StorageFederate   : " + message );
    }

    public static void main(String[] args) {
        try {
            new ClientFederate().runFederate();
        } catch (RTIexception rtIexception) {
            rtIexception.printStackTrace();
        }
    }


}
