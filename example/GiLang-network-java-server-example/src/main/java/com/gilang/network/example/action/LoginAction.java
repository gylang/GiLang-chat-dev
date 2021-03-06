package com.gilang.network.example.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.context.ServerContext;
import com.gilang.network.context.SessionContext;
import com.gilang.network.converter.PackageConverter;
import com.gilang.network.example.constant.CodeConst;
import com.gilang.network.example.domain.db.User;
import com.gilang.network.example.domain.payload.req.LoginReq;
import com.gilang.network.example.domain.payload.res.CodeRes;
import com.gilang.network.hook.AfterNetWorkContextInitialized;
import com.gilang.network.layer.app.socket.ActionType;
import com.gilang.network.layer.app.socket.MessageAction;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author gylang
 * data 2022/7/17
 */
@ActionType(2)
public class LoginAction implements MessageAction<LoginReq>, AfterNetWorkContextInitialized {


    @Override
    public void doAction(SocketDataPackage<LoginReq> dataPackage, SessionContext sessionContext) {

        LoginReq payload = dataPackage.getPayload();
        CompletableFuture.runAsync(() -> {
            try {
                List<Entity> userList = Db.use().query("select from gn_user where username = ?", payload.getUsername());
                SocketDataPackage<CodeRes> callBack = PackageConverter.copyBase(dataPackage);
                if (CollUtil.isNotEmpty(userList)) {
                    Entity entity = userList.get(0);
                    if (entity.getStr("password").equals(DigestUtil.md5Hex(payload.getPassword() + entity.getStr("salt")))) {
                        // 登录成功
                        User user = new User();
                        user.setId(entity.getLong("id"));
                        user.setNickname(entity.getStr("username"));
                        user.setNickname(entity.getStr("nickname"));
                        user.setNickname(entity.getStr("nickname"));
                        sessionContext.setAttr(User.class.getName(), user);
                        callBack.setPayload(new CodeRes(CodeConst.OK, "登录成功"));
                        sessionContext.write(callBack);
                        return;
                    }
                }
                callBack.setPayload(new CodeRes(CodeConst.FAIL, "登录失败,账号密码错误"));
                sessionContext.write(callBack);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    @Override
    public void post(ServerContext serverContext) {
    }
}
