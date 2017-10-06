package com.randioo.majiang_collections_client.action;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.Entity.ClientCard;
import com.randioo.mahjong_public_server.protocol.Entity.EnvVarsData;
import com.randioo.mahjong_public_server.protocol.Error.ErrorCode;
import com.randioo.mahjong_public_server.protocol.Gm.GmGameInfoResponse;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.utils.UIAction;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.EntityRunnable;

@PTAnnotation(GmGameInfoResponse.class)
@Controller
public class GameInfoAction extends UIAction {
    private JTextArea roleTextArea;
    private JTextArea remainCardsText;
    private TableModel tableModel;
    private JLabel promptLabel;

    private JButton cardLockButton;

    private JButton configLockButton;

    private Color cardLockColor;
    private Color configLockColor;
    @Autowired
    private ClientJFrame clientJFrame;

    @Override
    public void init() {
        roleTextArea = UIUtils.get(clientJFrame, "roleTextArea");
        remainCardsText = UIUtils.get(clientJFrame, "remainCardsText");
        JTable table = UIUtils.get(clientJFrame, "jTable1");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
        tableModel = table.getModel();

        cardLockButton = UIUtils.get(clientJFrame, "cardLockButton");
        configLockButton = UIUtils.get(clientJFrame, "configLockButton");
        Color color1 = cardLockButton.getBackground();
        cardLockColor = new Color(color1.getRed(), color1.getGreen(), color1.getBlue());

        Color color2 = configLockButton.getBackground();
        configLockColor = new Color(color2.getRed(), color2.getGreen(), color2.getBlue());
    }

    @Override
    public void execute(Object data, IoSession session) {
        GmGameInfoResponse response = (GmGameInfoResponse) data;

        EventQueue.invokeLater(new EntityRunnable<GmGameInfoResponse>(response) {

            @Override
            public void run(GmGameInfoResponse response) {
                if (response.getErrorCode() == ErrorCode.GAME_NOT_EXIST.getNumber()) {
                    promptLabel.setText("游戏不存在");
                    return;
                }
                List<ClientCard> list = response.getClientCardsList();

                if (cardLockButton.getBackground().equals(cardLockColor)) {
                    String clientCards = explain(list);
                    roleTextArea.setText(clientCards);

                    List<Integer> remainCards = response.getRemainCardsList();
                    remainCardsText.setText(array1(remainCards));
                }

                if (configLockButton.getBackground().equals(configLockColor)) {
                    List<EnvVarsData> list1 = response.getEnvVarsDataList();
                    int rowCount = tableModel.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        String key = "";
                        String value = "";
                        String type = "";
                        if (list1.size() > i) {
                            EnvVarsData envVarsData = list1.get(i);
                            key = envVarsData.getKey();
                            value = envVarsData.getValue();
                            type = envVarsData.getType();
                        }

                        tableModel.setValueAt(key, i, 0);
                        tableModel.setValueAt(value, i, 1);
                        tableModel.setValueAt(type, i, 2);
                    }
                }
            }

        });
    }

    public String explain(List<ClientCard> list) {
        System.out.println("GameInfoAction.explain()");
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ClientCard clientCard = list.get(i);
            List<Integer> cards = clientCard.getCardsList();
            int cardSize = cards.size();
            for (int j = 0; j < cardSize; j++) {
                sb.append(cards.get(j));
                if (j < cardSize - 1) {
                    sb.append(",");
                }
            }

            if (i < size - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private String array1(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
