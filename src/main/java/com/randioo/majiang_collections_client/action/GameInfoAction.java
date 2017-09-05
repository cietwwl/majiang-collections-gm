package com.randioo.majiang_collections_client.action;

import java.awt.EventQueue;
import java.util.List;

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
    private JTextField role1Text;
    private JTextField role2Text;
    private JTextField role3Text;
    private JTextField role4Text;
    private JTextArea remainCardsText;
    private TableModel tableModel;
    private JLabel promptLabel;

    @Autowired
    private ClientJFrame clientJFrame;

    @Override
    public void init() {
        role1Text = UIUtils.get(clientJFrame, "role1Text");
        role2Text = UIUtils.get(clientJFrame, "role2Text");
        role3Text = UIUtils.get(clientJFrame, "role3Text");
        role4Text = UIUtils.get(clientJFrame, "role4Text");
        remainCardsText = UIUtils.get(clientJFrame, "remainCardsText");
        JTable table = UIUtils.get(clientJFrame, "jTable1");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
        tableModel = table.getModel();
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
                List<Integer> remainCards = response.getRemainCardsList();
                role1Text.setText(array(list, 0));
                role2Text.setText(array(list, 1));
                role3Text.setText(array(list, 2));
                role4Text.setText(array(list, 3));
                remainCardsText.setText(array1(remainCards));

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
        });
    }

    private String array(List<ClientCard> cards, int index) {

        if (cards.size() - 1 < index) {
            return "";
        }

        List<Integer> list = cards.get(index).getCardsList();
        list.get(index);
        return array1(list);
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
