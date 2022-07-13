package com.gilang.network.converter;

import com.gilang.common.domian.SocketDataPackage;
import com.gilang.network.util.MessageUtil;

/**
 * @author gylang
 * data 2022/7/9
 */
public class PackageConverter {


    public static <T> SocketDataPackage<T> copyBaseNew(SocketDataPackage<T> source) {
        SocketDataPackage<T> dataPackage = new SocketDataPackage<>();
        dataPackage.setQos(source.getQos());
        dataPackage.setTranslatorType(source.getTranslatorType());
        dataPackage.setInLabel(source.getInLabel());
        dataPackage.setMsgId(MessageUtil.nextMsgId());
        return dataPackage;
    }
}
