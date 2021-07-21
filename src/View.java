import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.swing.JEditorPane;

public class View extends JFrame  {



    JEditorPaneEx jEditorPaneEx = new JEditorPaneEx();


    JFrame frame=new JFrame("rabbitmq");//creating instance of JFrame
    JPanel panel = new JPanel();
    JButton button=new JButton("click");//creating instance of JButton
    EmitLog emitLog = new EmitLog();
    ReceiveLogs receiveLogs1=new ReceiveLogs();
    ReceiveLogs receiveLogs2=new ReceiveLogs();
    JTextField jTextField = new JTextField(16);
    JTextField receiveLogsText1 = new JTextField(30);
    JTextField receiveLogsText2 = new JTextField(30);
    View() throws IOException, TimeoutException {

        frame.add(panel);

        jTextField.setText("hello rabbitmq");


        frame.setSize(300, 300);

        frame.show();
        panel.setBounds(0,0,500,500);
        panel.add(jTextField);
        panel.add(receiveLogsText1);
        panel.add(receiveLogsText2);
        button.setBounds(130,100,100, 40);
        panel.add(button);

        /*
                 show website in frame
         */
        //jEditorPaneEx.test();





        frame.setSize(800,800);//400 width and 500 height
        frame.setLayout(null);//using no layout managers
        frame.setVisible(true);//making the frame visible

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    receiveLogs1.run();
                    receiveLogsText1.setText(receiveLogs1.getMessageText());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                try {

                    receiveLogs2.run();
                    receiveLogsText2.setText(receiveLogs2.getMessageText());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                try {
                    String str = jTextField.getText();
                    emitLog.changeMessage(str);
                    emitLog.run();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });




    }


}
