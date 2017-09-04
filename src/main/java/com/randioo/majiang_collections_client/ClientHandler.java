package com.randioo.majiang_collections_client;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.handler.GameServerHandlerAdapter;

@Component
public class ClientHandler extends GameServerHandlerAdapter {
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        actionDispatcher((SC) message, session);
    }
    
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println(message);
    }

}
