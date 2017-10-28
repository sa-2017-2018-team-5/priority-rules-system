package fr.polytech.al.five.consumers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import fr.polytech.al.five.util.EventEmitter;
import org.apache.log4j.Logger;

import java.io.IOException;

public class IncomingCarConsumer extends DefaultConsumer{

    final static Logger logger = Logger.getLogger(IncomingCarConsumer.class);

    private EventEmitter emitter;

    public IncomingCarConsumer(Channel channel, String exchange) {
        super(channel);
        this.emitter = new EventEmitter(exchange);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {

    }

}
