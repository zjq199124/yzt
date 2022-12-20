package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.service.BuSignatureService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 治疗签到表(BuSignature)表服务控制层
 *
 * @author liuyzh
 * @since 2022-12-19 19:03:08
 */
@Api(tags = "治疗签到表(BuSignature)")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("buSignature")
public class BuSignatureController {
    @Resource
    private final BuSignatureService buSignatureService;

}
