package com.randioo.majiang_collections_client.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.utils.UIUtils;

@Component
public class EnvVarsSubmit implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    private JTable jTable;

    @Override
    public void init() {
        JButton jButton = UIUtils.get(clientJFrame, "submitGameEnvVarsButton");
        jTable = UIUtils.get(clientJFrame, "jTable1");
        jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        TableModel tableModel = jTable.getModel();
                        int rowCount = tableModel.getRowCount();
                        for (int i = 0; i < rowCount; i++) {
                            String value1 = (String) tableModel.getValueAt(i, 0);
                            String value2 = (String) tableModel.getValueAt(i, 1);
                            String value3 = (String) tableModel.getValueAt(i, 2);

                            if (StringUtils.isNullOrEmpty(value1) || StringUtils.isNullOrEmpty(value2)
                                    || StringUtils.isNullOrEmpty(value3)) {
                                continue;
                            }
                            System.out.println(value1 + " " + value2 + " " + value3);
                        }
                    }
                });

            }
        });
    }

}
