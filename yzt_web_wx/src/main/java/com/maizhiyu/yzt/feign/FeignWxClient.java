package com.maizhiyu.yzt.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "wx", url = "${weixin.api_domain}")
public interface FeignWxClient {

    // 这个是不是有用呢？？？？？

    /**
     * 网页授权token获取
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    @GetMapping(value = "/sns/oauth2/access_token?grant_type=authorization_code")
    String getAccessToken(
            @RequestParam("appid") String appId,
            @RequestParam("secret") String appSecret,
            @RequestParam("code") String code);

    /**
     * 基础token获取
     *
     * @param appId
     * @param appSecret
     * @return
     */
    @GetMapping(value = "/cgi-bin/token?grant_type=client_credential")
    String getToken(
            @RequestParam("appid") String appId,
            @RequestParam("secret") String appSecret);

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openid
     * @return
     */
    @GetMapping(value = "/cgi-bin/user/info?lang=zh_CN")
    String getUserInfo(
            @RequestParam("access_token") String accessToken,
            @RequestParam("openid") String openid);

    /**
     * 创建菜单
     *
     * @param accessToken
     * @param body
     * @return
     */
    @PostMapping(value = "/cgi-bin/menu/create")
    String createMenu(
            @RequestParam("access_token") String accessToken,
            @RequestBody String body);

    /**
     * @param accessToken
     * @return
     */
    @GetMapping(value = "/cgi-bin/get_current_selfmenu_info")
    String getMenuList(
            @RequestParam("access_token") String accessToken);
}
