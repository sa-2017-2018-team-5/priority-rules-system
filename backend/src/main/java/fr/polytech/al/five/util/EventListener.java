package fr.polytech.al.five.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
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
            this.channel.exchangeDeclare(EXCHANGE, "fanout");
            this.queueName = channel.queueDeclare().getQueue();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException
     */
    public void bind() throws IOException {
        channel.queueBind(queueName, EXCHANGE, "");

//        channel.basicConsume(queueName, true, consumer);
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
