import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";
    private String messageText = "";
    private String queueName ;
    private Channel channel;
    public ReceiveLogs() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        /*
         * queuename queueDeclare ile oluşturulyor.
         * burada spesific bi isim yok . java queue boş olduğunda bunu rabbitmq dan siliyor
         * emitlog da publish yaparken exchange name e göre yapıyor
         * bu nedenle spesific bir queue ismine gerek yok
         * burada önemli olan exchange name
         * publish te exchange name i aynı olan tüm queue lara aynı data gönderilmiş oluyor
         */
        this.queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

    }
    public void run() throws Exception {

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(queueName + " [x] Received '" + message + "'");
            this.messageText=message;
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    public String getMessageText() {
        String name = this.queueName.substring(this.queueName.length()-6);
        return "queue name : " + name + " -> " + this.messageText;
    }
}