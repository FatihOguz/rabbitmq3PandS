import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";
    private String message ;
    public void run() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");


            channel.basicPublish(EXCHANGE_NAME, "", null, this.message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + this.message + "'");


        }
    }

    public void changeMessage(String str){
        this.message = str;
    }

}
