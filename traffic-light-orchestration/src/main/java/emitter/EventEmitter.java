package emitter;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class EventEmitter {

    private final static String HOST = "localhost";
    private final static String EXCHANGE = "trafficExchange";


    private Connection connection;
    private Channel channel;

    public EventEmitter(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.exchangeDeclare(EXCHANGE, "direct");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    /**
     * Publish a message to a destination (Key)
     * @param key the recipient id
     * @param message the message
     * @throws IOException
     */
    public void publish(String key, String message) throws IOException {
        channel.basicPublish(EXCHANGE, key, null, message.getBytes("UTF-8"));
    }

    /**
     * Close the channel and connection
     */
    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
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



}
