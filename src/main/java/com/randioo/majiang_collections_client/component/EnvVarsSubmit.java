package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;
import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Entity.EnvVarsData;
import com.randioo.mahjong_public_server.protocol.Gm.GmEnvVarsRequest;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.WanClient;
import com.randioo.majiang_collections_client.utils.UIUtils;

@Component
public class EnvVarsSubmit implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    private JTable jTable;

    private JLabel promptLabel;

    private JTextField gameLockText;

    @Autowired
    private WanClient wanClient;

    @Override
    public void init() {
        JButton jButton = UIUtils.get(clientJFrame, "submitGameEnvVarsButton");
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
        gameLockText = UIUtils.get(clientJFrame, "gameLockText");
        jTable = UIUtils.get(clientJFrame, "jTable1");
        jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        TableModel tableModel = jTable.getModel();
                        int rowCount = tableModel.getRowCount();
                        List<EnvVarsData> list = new ArrayList<>();
                        for (int i = 0; i < rowCount; i++) {
                            String value1 = (String) tableModel.getValueAt(i, 0);
                            String value2 = (String) tableModel.getValueAt(i, 1);
                            String value3 = (String) tableModel.getValueAt(i, 2);

                            if (StringUtils.isNullOrEmpty(value1) || StringUtils.isNullOrEmpty(value2)
                                    || StringUtils.isNullOrEmpty(value3)) {
                                continue;
                            }
                            EnvVarsData data = EnvVarsData.newBuilder().setKey(value1).setValue(value2).setType(value3)
                                    .build();
                            list.add(data);
                        }

                        String roomId = gameLockText.getText();
                        wanClient.send(CS
                                .newBuilder()
                                .setGmEnvVarsRequest(
                                        GmEnvVarsRequest.newBuilder().setRoomId(roomId).addAllEnvVarsData(list))
                                .build());
                        promptLabel.setText("正在提交配置表");
                    }
                });

            }
        });
    }

}
