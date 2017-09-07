package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
    private JTextArea roleTextArea;
    private JTextArea remainCardsText;

    @Autowired
    private WanClient wanClient;

    @Override
    public void init() {
        JButton jButton = UIUtils.get(clientJFrame, "submitGameCardsButton");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
        gameLockText = UIUtils.get(clientJFrame, "gameLockText");
        roleTextArea = UIUtils.get(clientJFrame, "roleTextArea");
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

                        String roleCards = roleTextArea.getText();
                        try {
                            List<List<Integer>> lists = explainString2Cards(roleCards);
                            System.out.println(lists);
                            List<Integer> remainCards = getCards(remainCardsText.getText());

                            GmDispatchCardRequest.Builder request = GmDispatchCardRequest.newBuilder()
                                    .setRoomId(roomId);
                            for (List<Integer> list : lists) {
                                request.addClientCards(ClientCard.newBuilder().addAllCards(list));
                            }
                            request.addAllRemainCards(remainCards);
                            CS cs = CS.newBuilder().setGmDispatchCardRequest(request).build();
                            try{
                                wanClient.send(cs);                                
                            }catch(Exception e){
                                promptLabel.setText("服务器断开");
                            }
                        } catch (Exception e) {
                            promptLabel.setText("玩家出牌格式化失败");
                        }

                    }
                });

            }
        });
    }

    private List<Integer> getCards(String value) {
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

    public List<List<Integer>> explainString2Cards(String value) {
        List<List<Integer>> arrays = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            arrays.add(new ArrayList<Integer>());
        }
        if (StringUtils.isNullOrEmpty(value)) {
            return arrays;
        }
        int startIndex = 0;
        for (int i = 0; i < 4; i++) {
            int endIndex = value.indexOf("\n", startIndex);
            if (endIndex == -1) {
                String part = value.substring(startIndex, value.length()).trim();
                List<Integer> list = getCards(part);
                List<Integer> array = arrays.get(i);
                array.clear();
                array.addAll(list);
                return arrays;
            }

            String numsStr = value.substring(startIndex, endIndex).trim();
            List<Integer> list = getCards(numsStr);
            List<Integer> array = arrays.get(i);
            array.clear();
            array.addAll(list);
            startIndex = endIndex + 1;
        }

        System.out.println(arrays);
        return arrays;
    }

}
