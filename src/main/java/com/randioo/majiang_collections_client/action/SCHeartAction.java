package com.randioo.majiang_collections_client.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Controller;

import com.randioo.mahjong_public_server.protocol.ClientMessage.CS;
import com.randioo.mahjong_public_server.protocol.Heart.HeartRequest;
import com.randioo.mahjong_public_server.protocol.Heart.SCHeart;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.template.IActionSupport;

@PTAnnotation(SCHeart.class)
@Controller
public class SCHeartAction implements IActionSupport {

    @Override
    public void execute(Object data, IoSession session) {
        HeartRequest request = HeartRequest.newBuilder().build();
        CS cs = CS.newBuilder().setHeartRequest(request).build();
        session.write(cs);
    }

}
