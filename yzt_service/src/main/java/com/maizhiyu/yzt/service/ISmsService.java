package com.maizhiyu.yzt.service;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Map;

public interface ISmsService {

    /**
     *
     * @param templateCode
     * @param phone
     * @param map
     * @return
     */
    public Boolean sendSms(String signName,String templateCode, String phone, Map<String,String> map);

}
