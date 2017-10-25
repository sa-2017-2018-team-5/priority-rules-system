package fr.polytech.al.five.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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
            this.channel.exchangeDeclare(EXCHANGE, "fanout");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void publish(String message) throws IOException {
        channel.basicPublish(EXCHANGE, "", null, message.getBytes("UTF-8"));
    }


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
