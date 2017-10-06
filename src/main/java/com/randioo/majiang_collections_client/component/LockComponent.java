package com.randioo.majiang_collections_client.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.UIComponent;
import com.randioo.majiang_collections_client.utils.UIUtils;

@Component
public class LockComponent implements UIComponent {

    @Autowired
    private ClientJFrame clientJFrame;

    private JButton cardLockButton;

    private JButton configLockButton;

    private Color cardLockColor;
    private Color configLockColor;

    @Override
    public void init() {
        cardLockButton = UIUtils.get(clientJFrame, "cardLockButton");
        configLockButton = UIUtils.get(clientJFrame, "configLockButton");

        Color color1 = cardLockButton.getBackground();
        cardLockColor = new Color(color1.getRed(), color1.getGreen(), color1.getBlue());

        Color color2 = configLockButton.getBackground();
        configLockColor = new Color(color2.getRed(), color2.getGreen(), color2.getBlue());

        cardLockButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        cardLockButton
                                .setBackground(cardLockButton.getBackground().equals(cardLockColor) ? Color.RED : cardLockColor);
                    }
                });

            }
        });

        configLockButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        configLockButton
                                .setBackground(configLockButton.getBackground().equals(configLockColor) ? Color.RED : configLockColor);
                    }
                });

            }
        });

    }

    @Override
    public void onEnter() {
        cardLockButton.setBackground(Color.RED);
        configLockButton.setBackground(Color.RED);
    }

}
