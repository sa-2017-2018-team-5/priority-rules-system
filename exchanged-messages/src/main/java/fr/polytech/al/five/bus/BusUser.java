package fr.polytech.al.five.bus;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public abstract class BusUser {

    private final ConnectionFactory connectionFactory;

    public BusUser(BusInformation busInformation) {
        this.connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(busInformation.getHostname());
    }

    protected ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}
