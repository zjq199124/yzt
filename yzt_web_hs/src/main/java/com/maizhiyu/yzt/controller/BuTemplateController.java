package com.maizhiyu.yzt.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.BuTemplate;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Api(tags = "模板接口")
@RestController
@RequestMapping("/template")
public class BuTemplateController {

    @Autowired
    private IBuTemplateService service;


    @ApiOperation(value = "新增模板", notes = "新增模板")
    @PostMapping("/addTemplate")
    public Result addTemplate(@RequestBody BuTemplate template) {
        template.setStatus(1);
        template.setContent(JSON.toJSONString(template.getContents()));
        template.setCreateTime(new Date());
        template.setUpdateTime(template.getCreateTime());
        Integer res = service.addTemplate(template);
        return Result.success(template);
    }


    @ApiOperation(value = "删除模板", notes = "删除模板")
    @GetMapping("/delTemplate")
    public Result delTemplate(Long id) {
        Integer res = service.delTemplate(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改模板信息", notes = "修改模板信息")
    @PostMapping("/setTemplate")
    public Result setTemplate(@RequestBody BuTemplate template) {
        template.setUpdateTime(new Date());
        template.setContent(JSON.toJSONString(template.getContents()));
        Integer res = service.setTemplate(template);
        return Result.success(template);
    }


    @ApiOperation(value = "修改模板状态", notes = "修改模板状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setTemplateStatus")
    public Result setTemplateStatus(Long id, Integer status) {
        BuTemplate template = new BuTemplate();
        template.setId(id);
        template.setStatus(status);
        template.setUpdateTime(new Date());
        service.setTemplate(template);
        return Result.success(template);
    }


    @ApiOperation(value = "获取模板信息", notes = "获取模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板ID", required = true),
    })
    @GetMapping("/getTemplate")
    public Result getTemplate(Long id) {
        BuTemplate template = service.getTemplate(id);
        if (template != null) {
            try {
                template.setContents(JSON.parseArray(template.getContent()));
            } catch (Exception e) {
                template.setContents(null);
            }
        }
        return Result.success(template);
    }


    @ApiOperation(value = "获取模板单列表", notes = "获取模板单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "doctorId", value = "医生ID", required = false),
    })
    @GetMapping("/getTemplateList")
    public Result getTemplateList(Long doctorId,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        // 获取模板列表
        IPage<BuTemplate> pages = service.getTemplateList(new Page(pageNum, pageSize), doctorId);
        // 处理模板字段
        for (BuTemplate template : pages.getRecords()) {
            try {
                template.setContents(JSON.parseArray(template.getContent()));
            } catch (Exception e) {
                template.setContents(null);
            }
        }
        return Result.success(pages);
    }

}
