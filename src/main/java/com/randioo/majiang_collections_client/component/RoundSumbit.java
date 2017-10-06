package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Gm.GmRoundRequest;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.utils.StringUtils;

@Component
public class RoundSumbit implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    private JTextField remainRoundCountText;

    private JLabel promptLabel;

    private JTextField gameLockText;

    @Autowired
    private WanClient wanClient;

    @Override
    public void init() {
        JButton jButton = UIUtils.get(clientJFrame, "submitRoundButton");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
        gameLockText = UIUtils.get(clientJFrame, "gameLockText");

        jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        promptLabel.setText("正在提交剩余局数");
                    }
                });

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {

                        String roundCountStr = remainRoundCountText.getText();
                        try {
                            int roundCount = Integer.parseInt(roundCountStr);
                            String roomId = gameLockText.getText();
                            GmRoundRequest request = GmRoundRequest.newBuilder().setRoomId(roomId)
                                    .setRemainRound(roundCount).build();
                            CS cs = CS.newBuilder().setGmRoundRequest(request).build();
                            try {
                                wanClient.send(cs);
                            } catch (Exception e) {
                                promptLabel.setText("服务器断开");
                            }

                        } catch (Exception e) {
                            promptLabel.setText("局数格式错误");
                        }

                    }
                });

            }
        });
    }

    @Override
    public void onEnter() {
        // TODO Auto-generated method stub

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
