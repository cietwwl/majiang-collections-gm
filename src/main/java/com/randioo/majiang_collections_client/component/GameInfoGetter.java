package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Gm.GmGameInfoRequest;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.utils.UIUtils;

@Component
public class GameInfoGetter implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    @Autowired
    private WanClient wanClient;

    private JTextField gameLockText;

    @Override
    public void init() {

        gameLockText = UIUtils.get(clientJFrame, "gameLockText");

        JButton gameInfo = UIUtils.get(clientJFrame, "getGameInfoButton");
        gameInfo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String roomId = gameLockText.getText();
                        CS cs = CS.newBuilder().setGmGameInfoRequest(GmGameInfoRequest.newBuilder().setRoomId(roomId))
                                .build();
                        wanClient.send(cs);
                    }
                });

            }

        });

    }

}
