package com.randioo.majiang_collections_client;

import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.stereotype.Component;

import com.randioo.randioo_server_base.net.IoHandlerAdapter;

@Component
public class WanClient {
    public enum WanClientType {
        TCP;
    }

    private IoSession session = null;
    private IoConnector connector = null;
    private InetSocketAddress inetSocketAddress = null;

    public void startClient(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress,
            WanClientType type) {
        switch (type) {
        case TCP:
            tcp(handler, inetSocketAddress);
            break;
        }

    }

    public void init(IoFilter ioFilter) {
        connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", ioFilter);
    }

    public void tcp(IoHandlerAdapter handler, InetSocketAddress inetSocketAddress) {
        
        // connector.getFilterChain().addLast("threadpool", new
        // ExecutorFilter(Executors.newCachedThreadPool()));
        connector.setHandler(handler);
        this.inetSocketAddress = inetSocketAddress;
    }

    public void disconnect() {
        if (session != null) {
            session.close(true);
            session = null;
        }
    }

    private IoSession getSession() {
        if (session == null || !session.isConnected()) {
            try {
                ConnectFuture future = connector.connect(inetSocketAddress);
                future.awaitUninterruptibly();
                session = future.getSession();
            } catch (RuntimeIoException e) {
                e.printStackTrace();
            }
        }
        return session;
    }

    public void send(Object message) {
        getSession().write(message);
    }

}
