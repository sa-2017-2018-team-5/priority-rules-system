package listener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class EventListener {

    private final static String HOST = "localhost";
    private final static String EXCHANGE = "trafficExchange";

    private Connection connection;
    private Channel channel;
    private String queueName;

    public EventListener(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);

        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.exchangeDeclare(EXCHANGE, "direct");
            this.queueName = channel.queueDeclare().getQueue();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bind the listener with a key(ID) to an exchange(Router) to retrieve message
     * @param key The listeners ID
     * @throws IOException
     */
    public void bind(String key) throws IOException {
        channel.queueBind(queueName, EXCHANGE, key);
        System.out.println("Bind to channel " + EXCHANGE + ". Ready to receive message");

        EventConsumer consumer = new EventConsumer(channel);

        channel.basicConsume(queueName, true, consumer);
    }


    /*
            Getter and Setter
     */
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }


}
