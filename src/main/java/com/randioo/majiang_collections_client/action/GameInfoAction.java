package com.randioo.majiang_collections_client.action;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.template.IActionSupport;

@Component
public class GameInfoAction implements IActionSupport {
    private JTextField role1Text;
    private JTextField role2Text;
    private JTextField role3Text;
    private JTextField role4Text;
    private JTextArea remainCardsText;

    @Autowired
    private ClientJFrame clientJFrame;

    @Override
    public void execute(Object data, IoSession session) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        List<Integer> list4 = new ArrayList<>();
        List<Integer> remainCards = new ArrayList<>();
        role1Text = UIUtils.get(clientJFrame, "role1Text");
        role2Text = UIUtils.get(clientJFrame, "role2Text");
        role3Text = UIUtils.get(clientJFrame, "role3Text");
        role4Text = UIUtils.get(clientJFrame, "role4Text");
        remainCardsText = UIUtils.get(clientJFrame, "remainCardsText");

        role1Text.setText(array(list1));
        role2Text.setText(array(list2));
        role3Text.setText(array(list3));
        role4Text.setText(array(list4));
        remainCardsText.setText(array(remainCards));
    }

    private String array(List<Integer> list) {
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
