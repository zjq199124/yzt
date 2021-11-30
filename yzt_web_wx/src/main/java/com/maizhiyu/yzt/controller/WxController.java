package com.maizhiyu.yzt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.feign.FeignLocationClient;
import com.maizhiyu.yzt.feign.FeignWxClient;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.serviceimpl.PsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Api(tags = "微信接口")
@Configuration
@RestController
@RequestMapping("/wx")
public class WxController {

    private static Logger logger = LoggerFactory.getLogger(WxController.class);

    private String accessToken;

    @Value("${weixin.enable}")
    private Boolean ENABLE;

    @Value("${weixin.token}")
    private String TOKEN;

    @Value("${weixin.appid}")
    private String APPID;

    @Value("${weixin.appsecret}")
    private String APPSECRET;

    @Value("${weixin.auth_scope}")
    private String AUTHORIZE_SCOPE;

    @Value(("${weixin.authorize_url}"))
    private String AUTHORIZE_URL;

    @Value(("${weixin.redirect_url}"))
    private String REDIRECT_URL;

    @Value(("${weixin.business_url}"))
    private String BUSINESS_URL;

    @Autowired
    private FeignWxClient wxClient;

    @Autowired
    private FeignLocationClient locationClient;

    @Autowired
    private PsUserService userService;


    // 定时更新微信access_token
    @PostConstruct
    @Scheduled(cron = "${weixin.crontab}")
    protected void updateToken() {
        if (ENABLE) {
            try {
                // 获取token
                logger.info("### Update Token ###");
                String string = wxClient.getToken(APPID, APPSECRET);
                logger.info(string);
                // 解析token
                JSONObject jobj = JSON.parseObject(string);
                accessToken = jobj.getString("access_token");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }


    @ApiOperation(value = "响应微信验证", notes = "响应微信验证token信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signature", value = "微信加密签名", required = true),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true),
            @ApiImplicitParam(name = "nonce", value = "随机数", required = true),
            @ApiImplicitParam(name = "echostr", value = "回传字符串", required = true),
    })
    @GetMapping(value = "")
    public String processSignature(String body, String signature, String timestamp, String nonce, String echostr) {
        // 判断签名合法性
        boolean res = doCheckSignature(signature, timestamp, nonce);
        // 返回echostr
        if (res) {
            return echostr;
        } else {
            logger.warn("签名验证失败！");
            return "";
        }
    }


    /**
     * 接收消息的接口，消息类型是xml
     * 通过解析xml，判断MsgType和Event字段，判断消息类型
     */
    @ApiOperation(value = "处理用户消息", notes = "处理用户消息，body是xml格式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signature", value = "微信加密签名", required = true),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true),
            @ApiImplicitParam(name = "nonce", value = "随机数", required = true),
            @ApiImplicitParam(name = "openid", value = "openid", required = false),
    })
    @PostMapping(value = "")
    public String processMessage(@RequestBody String body, String signature, String timestamp, String nonce, String openid) {
        logger.debug("### body: " + body);
        // 判断签名合法性
        boolean valid = doCheckSignature(signature, timestamp, nonce);
        if (!valid) {
            logger.warn("签名验证失败！");
            return "签名验证失败！";
        }
        // 业务逻辑处理
        try {
            // 解析xml数据
            Document document = DocumentHelper.parseText(body);
            Element root = document.getRootElement();
            // 获取消息类型
            String msgType = root.elementText("MsgType");
            // 业务逻辑处理
            switch (msgType) {
                case "event":
                    String event = root.elementText("Event");
                    switch (event) {
                        case "subscribe": {
                            addUser(openid);
                            break;
                        }
                        case "LOCATION": {
                            setLocation(openid, root);
                            break;
                        }
                    }
                    break;
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 点击菜单的时候，进入到此页面
     * 此页面没有太多业务逻辑，只需要进行页面重定向：
     * 1、重定向到微信的授权页面（这个页面是微信的页面）
     * 2、同时带上授权后重新重定向的地址（这个页面是我们的页面）
     * **/
    @ApiOperation(value = "菜单点击回调", notes = "菜单点击回调，引导用户到授权页面")
    @ApiImplicitParams({})
    @GetMapping("/onClick")
    public void onClick(HttpServletResponse response) throws IOException {
        String authorizeUrl = AUTHORIZE_URL;
        String redirectUri = REDIRECT_URL;
        String responseType = "code";
        String scope = AUTHORIZE_SCOPE;
        String state = "STATE";
        String url = authorizeUrl +
                "?appid=" + APPID +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8") +
                "&response_type=" + responseType +
                "&scope=" + scope +
                "&state=" + state +
                "#wechat_redirect";
        logger.info("redirect: " + url);
        response.sendRedirect(url);
    }


    /**
     * 授权完成后回调此接口，此时可以获取用户的信息
     * **/
    @ApiOperation(value = "菜单授权回调", notes = "菜单授权回调，引导用户到系统页面")
    @ApiImplicitParams({})
    @GetMapping("/onCallback")
    public void onCallback(String code, String state, HttpServletResponse response) throws IOException {
        // 获取openid
        String jstr = wxClient.getAccessToken(APPID, APPSECRET, code);
        JSONObject jobj = JSON.parseObject(jstr);
        String openid = jobj.getString("openid");

        // 添加或更新用户
        PsUser user = addUser(openid);

        // 引导用户到业务系统页面
        Long uid = user.getId();
        String url = BUSINESS_URL + "?openid=" + openid + "&uid=" + uid;
        logger.info("redirect: " + url);

        // 页面重定向
        response.sendRedirect(url);
    }


    @ApiOperation(value = "创建菜单", notes = "创建菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "菜单信息", required = true),
    })
    @PostMapping("/createMenu")
    public Result createMenu(@RequestBody String body) {
        logger.info("### accessToken : " + accessToken);
        String result = wxClient.createMenu(accessToken, body);
        return Result.success(result);
    }


    @ApiOperation(value = "获取菜单列表", notes = "获取菜单列表")
    @ApiImplicitParams({})
    @GetMapping("/getMenuList")
    public Result getMenuList() {
        String result = wxClient.getMenuList(accessToken);
        return Result.success(result);
    }


    private boolean doCheckSignature(String signature, String timestamp, String nonce) {
        boolean valid = false;
        if (signature != null && timestamp != null && nonce != null) {
            // 字典序排序
            List<String> list = Arrays.asList(new String[]{TOKEN, timestamp, nonce});
            list.sort((String x, String y) -> { return x.compareTo(y); });
            // 字符串拼接
            String str = String.join("", list);
            // SHA1加密
            String mysig = DigestUtils.sha1Hex(str);
            // 判断合法性
            valid = mysig.equals(signature);
        }
        return valid;
    }


    private PsUser addUser(String openid) {
        // 获取用户微信信息
        String jsonstr = wxClient.getUserInfo(accessToken, openid);
        JSONObject jsonobj = JSON.parseObject(jsonstr);
        logger.info("### user: " + jsonstr);
        // 获取用户的基本信息(本系统内的信息)
        PsUser user = userService.getUserByOpenid(openid);
        // 用户不存在则添加用户
        if (user == null) {
            user = doAddUser(jsonobj);
        }
        // 用户已存在则更新用户
        else {
            user = doSetUser(user, jsonobj);
        }
        // 返回结果
        return user;
    }


    private void setLocation(String openid, Element element) {
        // 解析位置数据
        String latitude = element.elementText("Latitude");
        String longitude = element.elementText("Longitude");
        String precision = element.elementText("Precision");
        // 获取省份城市
        String location = latitude + "," + longitude;
        String locainfo = locationClient.getLocation(location);
        // 解析位置数据
        JSONObject jsonObject = JSON.parseObject(locainfo);
        // 更新用户位置
        doSetUserLocation(openid, latitude, longitude, precision, jsonObject);
    }


    private PsUser doAddUser(JSONObject jobj) {
        // 解析用户数据
        String openid = jobj.getString("openid");
        String unionid = jobj.getString("unionid");
        String nickname = jobj.getString("nickname");
        Integer sex = jobj.getInteger("sex");
        String country = jobj.getString("country");
        String province = jobj.getString("province");
        String city = jobj.getString("city");
        String avatar = jobj.getString("headimgurl");
        Date date = new Date();
        // 创建用户对象
        PsUser user = new PsUser();
        user.setStatus(1);
        user.setOpenid(openid);
        user.setUnionid(unionid);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setCountry(country);
        user.setProvince(province);
        user.setCity(city);
        user.setAvatar(avatar);
        user.setCreateTime(date);
        user.setUpdateTime(date);
        // 添加用户信息
        userService.addUser(user);
        // 返回结果
        return user;
    }


    private PsUser doSetUser(PsUser user, JSONObject jobj) {
        // 解析用户数据
        String unionid = jobj.getString("unionid");
        String nickname = jobj.getString("nickname");
        Integer sex = jobj.getInteger("sex");
        String country = jobj.getString("country");
        String province = jobj.getString("province");
        String city = jobj.getString("city");
        String avatar = jobj.getString("headimgurl");
        Date date = new Date();
        // 创建用户对象
        user.setUnionid(unionid);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setCountry(country);
        user.setProvince(province);
        user.setCity(city);
        user.setAvatar(avatar);
        user.setCreateTime(date);
        user.setUpdateTime(date);
        // 添加用户信息
        userService.setUser(user);
        // 返回用户对象
        return user;
    }


    private PsUser doSetUserLocation(String openid, String latitude, String longitude, String precision, JSONObject jsonObject) {
        // 解析位置数据
        JSONObject resultObject = jsonObject.getJSONObject("result");
        JSONObject addressObject = resultObject.getJSONObject("address_component");
        String realtimeCountry = addressObject.getString("nation");
        String realtimeProvince = addressObject.getString("province");
        String realtimeCity = addressObject.getString("city");
        Date date = new Date();
        // 创建用户对象
        PsUser user = new PsUser();
        user.setOpenid(openid);
        user.setLatitude(latitude);
        user.setLongitude(longitude);
        user.setPrecision(precision);
        user.setRealtimeCountry(realtimeCountry);
        user.setRealtimeProvince(realtimeProvince);
        user.setRealtimeCity(realtimeCity);
        user.setUpdateTime(date);
        // 更新用户信息
        userService.setUserByOpenid(user);
        // 返回用户对象
        return user;
    }

}
