package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Login.LoginGetRoleDataRequest;
import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.protocol.protobuf.ProtoCodecFactory;
import com.randioo.randioo_server_base.utils.SpringContext;

@Component
public class GetGameInfoComponent implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    @Autowired
    private WanClient wanClient;

    @Override
    public void init() {

        JButton gameInfo = UIUtils.get(clientJFrame, "getGameInfoButton");

        gameInfo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        JTextField host = UIUtils.get(clientJFrame, "hostText");
                        JTextField port = UIUtils.get(clientJFrame, "portText");
                        String hostText = host.getText();
                        int portInt = Integer.parseInt(port.getText());

                        ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(new ProtoCodecFactory(SC
                                .getDefaultInstance(), null));
                        InetSocketAddress address = new InetSocketAddress(hostText, portInt);
//                        wanClient.startClient(protocolCodecFilter, handler, inetSocketAddress, type);

                    }
                });

            }

        });

    }
}
