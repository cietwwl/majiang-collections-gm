package com.randioo.majiang_collections_client.action;

import javax.swing.JLabel;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.Gm.GmRoundResponse;
import com.randioo.majiang_collections_client.ClientJFrame;
import com.randioo.majiang_collections_client.utils.UIAction;
import com.randioo.majiang_collections_client.utils.UIUtils;
import com.randioo.randioo_server_base.annotation.PTAnnotation;

@PTAnnotation(GmRoundResponse.class)
@Controller
public class RoundAction extends UIAction {

    @Autowired
    private ClientJFrame clientJFrame;

    private JLabel promptLabel;

    @Override
    public void init() {
        promptLabel = UIUtils.get(clientJFrame, "promptLabel");
    }

    @Override
    public void execute(Object data, IoSession session) {

    }

}
