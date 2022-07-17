package com.gilang.network.example.init;

import com.gilang.common.context.BeanFactoryContext;
import com.gilang.network.context.BeanLoader;
import com.gilang.network.context.ServerContext;
import com.gilang.network.example.action.CallBackAction;
import com.gilang.network.example.action.JoinAction;
import com.gilang.network.example.action.SendMessageAction;
import com.gilang.network.example.session.ChatRoomSession;

/**
 * @author gylang
 * data 2022/7/10
 */
public class ActionBeanLoader implements BeanLoader {

    @Override
    public void scan(ServerContext serverContext) {
        BeanFactoryContext beanFactoryContext = serverContext.getBeanFactoryContext();

        beanFactoryContext.register(ChatRoomSession.class.getName(), new ChatRoomSession());


        beanFactoryContext.register(CallBackAction.class.getName(), new CallBackAction());
        beanFactoryContext.register(JoinAction.class.getName(), new JoinAction());
        beanFactoryContext.register(SendMessageAction.class.getName(), new SendMessageAction());
    }
}
