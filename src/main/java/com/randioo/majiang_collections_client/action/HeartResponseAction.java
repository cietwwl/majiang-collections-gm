package com.randioo.majiang_collections_client.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Heart.CSHeart;
import com.randioo.mahjong_public_server.protocol.Heart.HeartResponse;
import com.randioo.majiang_collections_client.utils.UIAction;
import com.randioo.randioo_server_base.annotation.PTAnnotation;

@PTAnnotation(HeartResponse.class)
@Controller
public class HeartResponseAction extends UIAction {

    @Override
    public void execute(Object data, IoSession session) {
        CSHeart csHeart = CSHeart.newBuilder().build();
        CS cs = CS.newBuilder().setCSHeart(csHeart).build();
        session.write(cs);
    }

}
