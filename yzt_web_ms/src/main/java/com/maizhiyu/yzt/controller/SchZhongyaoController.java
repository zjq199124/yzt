package com.maizhiyu.yzt.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.entity.MsHerbs;
import com.maizhiyu.yzt.entity.MsZhongyaoHerbs;
import com.maizhiyu.yzt.entity.SchZhongyao;
import com.maizhiyu.yzt.request.SchZhongyaoHerbsRequest;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsHerbsService;
import com.maizhiyu.yzt.service.IMsZhongyaoHerbsService;
import com.maizhiyu.yzt.service.ISchZhongyaoService;
import com.maizhiyu.yzt.service.ISynSyndromeService;
import com.maizhiyu.yzt.vo.SchZhongyaoHerbsVO;
import com.maizhiyu.yzt.vo.SchZhongyaoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Api(tags = "中药方案接口")
@RestController
@RequestMapping("/zhongyao")
public class SchZhongyaoController {

    @Autowired
    private ISchZhongyaoService service;

    @Autowired
    private ISynSyndromeService syndromeService;

    @Autowired
    private IMsHerbsService msHerbsService;

    @Autowired
    private IMsZhongyaoHerbsService msZhongyaoHerbsService;

    @ApiOperation(value = "增加中药方案", notes = "增加中药方案")
    @PostMapping("/addZhongyao")
    public Result addZhongyao(@RequestBody SchZhongyao zhongyao) {
        // 如果有辨证ID则只增加一调记录
        if (zhongyao.getSyndromeId() != null) {
            zhongyao.setStatus(1);
            Integer res = service.addZhongyao(zhongyao);
            return Result.success(zhongyao);
        }
        // 如果无辨证ID则查询疾病对应的所有辨证ID，每个辨证增加一条记录
        else {
            IPage<Map<String, Object>> pages = syndromeService.getSyndromeList(null, zhongyao.getDiseaseId(), null, null);
            for (Map<String, Object> map : pages.getRecords()) {
                zhongyao.setStatus(1);
                zhongyao.setSyndromeId((Long) map.get("id"));
                Integer res = service.addZhongyao(zhongyao);
            }
            return Result.success();
        }
    }


    @ApiOperation(value = "删除中药方案", notes = "删除中药方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药方案id", required = true)
    })
    @GetMapping("/delZhongyao")
    public Result delZhongyao(Long id) {
        Integer res = service.delZhongyao(id);
        return Result.success(res);
    }


    @ApiOperation(value = "修改中药方案信息", notes = "修改中药方案信息")
    @PostMapping("/setZhongyao")
    public Result setZhongyao(@RequestBody SchZhongyao zhongyao) {
        Integer res = service.setZhongyao(zhongyao);
        return Result.success(zhongyao);
    }

    @ApiOperation(value = "移除单个中药关联", notes = "移除单个中药关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药关联id", required = true)
    })
    @PostMapping("/removeSchZhongyaoHerbs")
    public Result removeSchZhongyaoHerbs(@RequestParam Long id) {
        Integer res = msZhongyaoHerbsService.removeSchZhongyaoHerbs(id);
        return res > 0 ? Result.success() : Result.failure();
    }

    @ApiOperation(value = "绑定多个中药", notes = "绑定多个中药")
    @PostMapping("/addsSchZhongyaoHerbs")
    public Result addsSchZhongyaoHerbs(@RequestBody List<SchZhongyaoHerbsRequest> list) {
        List<MsZhongyaoHerbs> list1 = new ArrayList<>();
        list.stream().forEach(item -> {
            MsZhongyaoHerbs msZhongyaoHerbs = new MsZhongyaoHerbs();
            BeanUtils.copyProperties(item, msZhongyaoHerbs);
            list1.add(msZhongyaoHerbs);
        });
        msZhongyaoHerbsService.adds(list1);
        return Result.success();
    }


    @ApiOperation(value = "修改中药方案状态", notes = "修改中药方案状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药方案id", required = true),
            @ApiImplicitParam(name = "status", value = "状态", required = true)
    })
    @GetMapping("/setZhongyaoStatus")
    public Result setZhongyaoStatus(Long id, Integer status) {
        SchZhongyao zhongyao = new SchZhongyao();
        zhongyao.setId(id);
        zhongyao.setStatus(status);
        Integer res = service.setZhongyao(zhongyao);
        return Result.success();
    }


    @ApiOperation(value = "获取中药方案信息", notes = "获取中药方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "中药方案id", required = true)
    })
    @GetMapping("/getZhongyao")
    public Result getZhongyao(Long id) {
        SchZhongyao zhongyao = service.getZhongyao(id);
        SchZhongyaoVO schZhongyaoVO = new SchZhongyaoVO();
        BeanUtils.copyProperties(zhongyao, schZhongyaoVO);
        List<SchZhongyaoHerbsVO> list = msZhongyaoHerbsService.getMsZhongyaoHerbsListBySchZhongyaoId(id);
        schZhongyaoVO.setList(list);
        return Result.success(schZhongyaoVO);
    }


    @ApiOperation(value = "获取中药方案列表", notes = "获取中药方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", value = "疾病ID", required = false),
            @ApiImplicitParam(name = "status", value = "状态", required = false),
            @ApiImplicitParam(name = "term", value = "搜索词", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false),
    })
    @GetMapping("/getZhongyaoList")
    public Result getZhongyaoList(Long diseaseId, Integer status, String term,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<Map<String, Object>> list = service.getZhongyaoList(new Page(pageNum, pageSize), diseaseId, status, term);
        return Result.success(list);
    }


    @ApiOperation(value = "获取药材列表", notes = "获取药材列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zyId", value = "中药方案id", required = true),
            @ApiImplicitParam(name = "herbsName", value = "药材名称", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false)
    })
    @GetMapping("/getMsHerbsList")
    public Result getMsHerbsList(@RequestParam Long zyId, String herbsName,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<MsHerbs> paperList = msHerbsService.getMsHerbsList(herbsName, pageNum, pageSize, zyId);
        return Result.success(paperList);
    }


}
