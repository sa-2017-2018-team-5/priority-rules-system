package emitter;

import com.rabbitmq.client.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EventEmitterTest {

    private final static String HOST = "localhost";
    private final static String EXCHANGE = "trafficExchange";

    private EventEmitter emitter;

    @Before
    public void setUp(){
        //this.emitter = new EventEmitter();
    }

    @After
    public void cleanUp(){
//        this.emitter.close();
    }

    @Test
    public void exchangeTest() throws IOException, TimeoutException {


    }
}
