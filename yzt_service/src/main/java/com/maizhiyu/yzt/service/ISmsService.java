package com.maizhiyu.yzt.service;

import java.util.Map;

public interface ISmsService {

    /**
     *
     * @param templateCode
     * @param phone
     * @param map
     * @return
     */
    public String sendSms(String templateCode, String phone, Map<String,String> map);

}
