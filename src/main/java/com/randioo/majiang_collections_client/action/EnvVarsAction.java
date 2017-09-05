package com.randioo.majiang_collections_client.action;

import java.awt.EventQueue;

import javax.swing.JLabel;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.Error.ErrorCode;
import com.randioo.mahjong_public_server.protocol.Gm.GmEnvVarsResponse;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.utils.UIAction;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.EntityRunnable;

@PTAnnotation(GmEnvVarsResponse.class)
@Controller
public class EnvVarsAction extends UIAction {

    @Autowired
    private ClientJFrame clientJFrame;

    private JLabel promptLabel;

    @Override
    public void init() {
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
    }

    @Override
    public void execute(Object data, IoSession session) {
        GmEnvVarsResponse response = (GmEnvVarsResponse) data;
        EventQueue.invokeLater(new EntityRunnable<GmEnvVarsResponse>(response) {

            @Override
            public void run(GmEnvVarsResponse entity) {
                if (entity.getErrorCode() == ErrorCode.GAME_NOT_EXIST.getNumber()) {
                    promptLabel.setText("游戏不存在");
                } else {
                    promptLabel.setText("游戏配置表更新完毕");

                }
            }
        });

    }

}
