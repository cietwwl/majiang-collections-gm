package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.utils.UIUtils;

@Component
public class Linker implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    @Autowired
    private WanClient wanClient;

    @Override
    public void init() {
        JButton linkButton = UIUtils.get(clientJFrame, "linkButton");

        linkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        JTextField host = UIUtils.get(clientJFrame, "hostText");
                        JTextField port = UIUtils.get(clientJFrame, "portText");
                        String hostText = host.getText();
                        int portInt = Integer.parseInt(port.getText());

                        InetSocketAddress address = new InetSocketAddress(hostText, portInt);

                    }
                });

            }

        });
    }

}
