package com.maizhiyu.yzt.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.ro.VisitRO;
import com.maizhiyu.yzt.service.BuVisitService;
import com.maizhiyu.yzt.vo.VisitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 患者随访表(BuVisit)表服务控制层
 *
 * @author liuyzh
 * @since 2022-12-19 18:56:17
 */
@Api(tags = "患者随访表(BuVisit)")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("buVisit")
public class BuVisitController {

    @Resource
    private final BuVisitService buVisitService;

    @ApiOperation(value = "查询随访列表", notes = "随访-列表")
    @GetMapping("/getPage")
    public Result getPage(Page page,VisitRO visitRO) {
        IPage<VisitVO> result = buVisitService.getPage(page, visitRO);
        return Result.success(result);
    }

}
