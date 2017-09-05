package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Entity.ClientCard;
import com.randioo.mahjong_public_server.protocol.Gm.GmDispatchCardRequest;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.utils.StringUtils;

@Component
public class CardDispatcher implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    private JLabel promptLabel;

    private JTextField gameLockText;
    private JTextField role1Text;
    private JTextField role2Text;
    private JTextField role3Text;
    private JTextField role4Text;
    private JTextArea remainCardsText;

    @Autowired
    private WanClient wanClient;

    @Override
    public void init() {
        JButton jButton = UIUtils.get(clientJFrame, "submitGameCardsButton");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
        gameLockText = UIUtils.get(clientJFrame, "gameLockText");
        role1Text = UIUtils.get(clientJFrame, "role1Text");
        role2Text = UIUtils.get(clientJFrame, "role2Text");
        role3Text = UIUtils.get(clientJFrame, "role3Text");
        role4Text = UIUtils.get(clientJFrame, "role4Text");
        remainCardsText = UIUtils.get(clientJFrame, "remainCardsText");

        jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        promptLabel.setText("正在提交卡牌");
                    }
                });

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String roomId = gameLockText.getText();

                        List<Integer> list1 = getCards(role1Text);
                        List<Integer> list2 = getCards(role2Text);
                        List<Integer> list3 = getCards(role3Text);
                        List<Integer> list4 = getCards(role4Text);
                        List<Integer> remainCards = getCards(remainCardsText);
                        GmDispatchCardRequest.Builder request = GmDispatchCardRequest.newBuilder().setRoomId(roomId);
                        request.addClientCards(ClientCard.newBuilder().addAllCards(list1));
                        request.addClientCards(ClientCard.newBuilder().addAllCards(list2));
                        request.addClientCards(ClientCard.newBuilder().addAllCards(list3));
                        request.addClientCards(ClientCard.newBuilder().addAllCards(list4));
                        request.addAllRemainCards(remainCards);
                        CS cs = CS.newBuilder().setGmDispatchCardRequest(request).build();

                        wanClient.send(cs);

                    }
                });

            }
        });
    }

    private List<Integer> getCards(JTextComponent text) {
        String value = text.getText();
        List<Integer> list = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(value)) {
            return list;
        }
        String[] arr = value.split(",");
        for (String a : arr) {
            int card = Integer.parseInt(a);
            list.add(card);
        }
        return list;
    }

}
