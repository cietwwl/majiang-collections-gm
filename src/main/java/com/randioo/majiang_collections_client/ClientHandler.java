package com.randioo.majiang_collections_client;

import org.apache.mina.core.session.IoSession;

import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.handler.GameServerHandlerAdapter;
import com.randioo.randioo_server_base.net.IoHandlerAdapter;

public class ClientHandler extends GameServerHandlerAdapter {
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        actionDispatcher((SC) message, session);
    }

}
