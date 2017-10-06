package com.randioo.majiang_collections_client;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.randioo.mahjong_public_server.protocol.ServerMessage.SC;
import com.randioo.majiang_collections_client.WanClient.WanClientType;
import com.randioo.randioo_server_base.net.IoHandlerAdapter;
import com.randioo.randioo_server_base.protocol.protobuf.ProtoCodecFactory;
import com.randioo.randioo_server_base.utils.PackageUtil;
import com.randioo.randioo_server_base.utils.SpringContext;
import com.randioo.randioo_server_base.utils.StringUtils;

public class majiang_collections_clientApp {

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }

        SpringContext.initSpringCtx("ApplicationContext.xml");
        List<Class<?>> clazzes = PackageUtil.getClasses("com.randioo.majiang_collections_client.component");
        List<UIComponent> comps = new ArrayList<>();
        for (Class<?> clazz : clazzes) {
            if (StringUtils.isNullOrEmpty(clazz.getSimpleName())) {
                continue;
            }
            String value = clazz.getSimpleName();
            String beanName = StringUtils.firstStrToLowerCase(value);
            UIComponent uiComponent = SpringContext.getBean(beanName);
            comps.add(uiComponent);
            uiComponent.init();
        }

        for (UIComponent comp : comps) {
            comp.onEnter();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientJFrame frame = SpringContext.getBean(ClientJFrame.class);
                frame.setVisible(true);
            }
        });

    }
}
