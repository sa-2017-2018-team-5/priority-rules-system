package listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import engine.MessageProcessImpl;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarArrivalConsumer extends DefaultConsumer{

    private final static String ID = "Group-40096";

    private MessageProcessImpl process  = new MessageProcessImpl();
    private List<JSONObject> mytraffic;

    public CarArrivalConsumer(Channel channel, String exchange) {
        super(channel);
        this.mytraffic = new ArrayList<>();
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
        JSONObject jsonObject = new JSONObject(new String(body, "UTF-8"));


        if (process.isCorrectID(ID,jsonObject)){
            System.out.println("receive : " + jsonObject.toString());

            mytraffic.add(process.getCar(jsonObject));
            System.out.println("Waiting for these vehicles to pass : " + mytraffic.toString());
        }
    }

    public List<JSONObject> getMytraffic() {
        return mytraffic;
    }

    public void setMytraffic(List<JSONObject> mytraffic) {
        this.mytraffic = mytraffic;
    }
}
