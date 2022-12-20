package com.maizhiyu.yzt.controller;

import com.maizhiyu.yzt.service.BuCureService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 治疗表(BuCure)表服务控制层
 *
 * @author liuyzh
 * @since 2022-12-19 18:34:06
 */
@Api(tags = "治疗表(BuCure)")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("buCure")
public class BuCureController {

    @Resource
    private final BuCureService buCureService;

}
