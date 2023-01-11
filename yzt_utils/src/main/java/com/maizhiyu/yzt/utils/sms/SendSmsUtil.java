package com.maizhiyu.yzt.utils.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Component
public class SendSmsUtil {

    private static String accessKeyId = "LTAI5tMFpwTnAJLCBNgxjAQ7";
    private static String accessKeySecret = "EZ0gRdDiP6QkvfGAfuJfxK57PEBr34";
    private static String endPoint = "dysmsapi.aliyuncs.com";

    @Resource
    private SmsConfig smsConfig;


    public static void main(String[] args) throws Exception {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                .build());

        AsyncClient client = AsyncClient.builder()
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(endPoint)
                )
                .build();

        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("杭州一真医疗器械")
                .templateCode("SMS_266960575")
                .phoneNumbers("17770696308")
                .templateParam("{\"code\":\"1234\"}")
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = response.get();
        System.out.println(new Gson().toJson(resp));
        client.close();
    }


    public String sendSms(String signName, String templateCode, String phone, Map<String,String> requestMap) throws Exception {
        AsyncClient client = smsConfig.createClient();
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName(signName)
                .templateCode(templateCode)
                .phoneNumbers(phone)
                .templateParam(new Gson().toJson(requestMap))
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = response.get();
        JSONObject jsonObject = JSONObject.parseObject(new Gson().toJson(resp));
        client.close();
        return jsonObject.get("body").toString();
    }
}
