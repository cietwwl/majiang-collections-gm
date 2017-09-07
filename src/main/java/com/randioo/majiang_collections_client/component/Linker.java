package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;
import com.randioo.majiang_collections_client.ClientHandler;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.WanClient.WanClientType;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.protocol.protobuf.ProtoCodecFactory;
import com.randioo.randioo_server_base.utils.StringUtils;

@Component
public class Linker implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    @Autowired
    private WanClient wanClient;

    @Autowired
    private ClientHandler clientHandler;

    private JTextField host;
    private JTextField port;
    private JLabel promptLabel;

    @Override
    public void init() {
        JButton linkButton = UIUtils.get(clientJFrame, "linkButton");
        host = UIUtils.get(clientJFrame, "hostText");
        port = UIUtils.get(clientJFrame, "portText");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");

        linkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {

                        if (StringUtils.isNullOrEmpty(host.getText())) {
                            host.setText("127.0.0.1");
                        }

                        if (StringUtils.isNullOrEmpty(port.getText())) {
                            port.setText("10006");
                        }
                        String hostText = host.getText();
                        int portInt = Integer.parseInt(port.getText());

                        promptLabel.setText("开始连接服务器");

                        InetSocketAddress address = new InetSocketAddress(hostText, portInt);
                        try {
                            wanClient.startClient(new ProtocolCodecFilter(new ProtoCodecFactory(
                                    SC.getDefaultInstance(), null)), clientHandler, address, WanClientType.TCP);

                            promptLabel.setText("开始连接服务器");

                        } catch (Exception e) {
                            promptLabel.setText("连接服务器失败");
                        }
                        promptLabel.setText("连接服务器成功");

                        wanClient.send(CS.newBuilder().build());
                    }
                });

            }

        });
    }

}
