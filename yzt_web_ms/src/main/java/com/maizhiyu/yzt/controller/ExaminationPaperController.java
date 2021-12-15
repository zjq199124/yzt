package com.maizhiyu.yzt.controller;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.entity.MsExaminationPaper;
import com.maizhiyu.yzt.entity.TsSytechItem;
import com.maizhiyu.yzt.exception.BusinessException;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IMsExaminationPaperService;
import com.maizhiyu.yzt.service.ITsSytechItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * className:ExaminationPaperController
 * Package:com.maizhiyu.yzt.controller
 * Description:
 *
 * @DATE:2021/12/10 4:01 下午
 * @Author:2101825180@qq.com
 */

@Api(tags = "适宜技术考核接口")
@RestController
@RequestMapping("/examinationPaper")
public class ExaminationPaperController {

    @Autowired
    private IMsExaminationPaperService msExaminationPaperService;

    @Autowired
    private ITsSytechItemService iTsSytechItemService;

    @ApiOperation(value = "增加适宜技术考核试卷", notes = "增加适宜技术考核试卷")
    @PostMapping("/addExaminationPaper")
    public Result addExaminationPaper(@RequestBody MsExaminationPaper item) {
        item.setCreateTime(new Date());
        Integer res = msExaminationPaperService.addMsExaminationPaper(item);
        return Result.success();
    }
//
//
    @ApiOperation(value = "删除适宜技术考核试卷", notes = "删除适宜技术考核试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜技术考核试卷id", required = true)
    })
    @GetMapping("/delExaminationPaper")
    public Result delExaminationPaper(@RequestParam Long id) {
        Integer res = msExaminationPaperService.delMsExaminationPaper(id);
        return Result.success();
    }
//
//
    @ApiOperation(value = "修改适宜技术考核试卷", notes = "修改适宜技术考核试卷")
    @PostMapping("/setExaminationPaper")
    public Result setExaminationPaper(@RequestBody MsExaminationPaper item) {
        Integer res = msExaminationPaperService.setMsExaminationPaper(item);
        return res > 0 ? Result.success(item) : Result.failure();
    }
//
//
    @ApiOperation(value = "获取适宜技术考核试卷", notes = "获取适宜技术考核试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "适宜技术考核试卷id", required = true)
    })
    @GetMapping("/getExaminationPaper")
    public Result getExaminationPaper(@RequestParam Long id) {
        MsExaminationPaper item = msExaminationPaperService.getMsExaminationPaper(id);
        return item != null ? Result.success(item) : Result.failure();
    }
//
//
    @ApiOperation(value = "获取适宜技术考核试卷列表", notes = "获取适宜技术考核试卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sytechId", value = "适宜技术ID", required = false),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false)
    })
    @GetMapping("/getExaminationPaperList")
    public Result getExaminationPaperList(Long sytechId,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<MsExaminationPaper> paperList = msExaminationPaperService.getMsExaminationPaperList(sytechId,pageNum,pageSize);
        return Result.success(paperList);
    }
//
//
    @ApiOperation(value = "增加考核题目", notes = "增加考核题目")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "title", value = "题目", required = true),
//            @ApiImplicitParam(name = "examinationPaperId", value = "试卷id", required = true)
//    })
    @PostMapping("/addItem")
    public Result addItem(@RequestBody TsSytechItem item) {


        MsExaminationPaper msExaminationPaper = msExaminationPaperService.getMsExaminationPaper(item.getExaminationPaperId());
        item.setCreateTime(new Date());
        if(msExaminationPaper == null) {
            throw new BusinessException("当前试卷不存在");
        }
        item.setSytechId(msExaminationPaper.getSytechId());
        Integer res = iTsSytechItemService.addSytechItem(item);
        return Result.success();
    }
//
//
    @ApiOperation(value = "删除考核题目", notes = "删除考核题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核项目id", required = true)
    })
    @GetMapping("/delItem")
    public Result delItem(@RequestParam Long id) {
        Integer res = iTsSytechItemService.delSytechItem(id);
        return Result.success();
    }
//
//
    @ApiOperation(value = "修改考核题目信息", notes = "修改考核题目信息")
    @PostMapping("/setItem")
    public Result setItem(@RequestBody TsSytechItem item) {
        Integer res = iTsSytechItemService.setSytechItem(item);
        return res > 0 ? Result.success(item) : Result.failure();
    }
//
//
    @ApiOperation(value = "获取考核题目信息", notes = "获取考核题目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考核项目id", required = true)
    })
    @GetMapping("/getItem")
    public Result getItem(Long id) {
        TsSytechItem item = iTsSytechItemService.getSytechItem(id);
        return Result.success(item);
    }

    @ApiOperation(value = "获取考核题目列表", notes = "获取考核题目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examinationPaperId", value = "试卷ID", required = true),
            @ApiImplicitParam(name = "pageNum", value = "开始页数", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false)
    })
    @GetMapping("/getItemList")
    public Result getItemList(@RequestParam Long examinationPaperId,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<TsSytechItem> paperList = iTsSytechItemService.getSytechItemList(examinationPaperId,pageNum,pageSize);
        return Result.success(paperList);
    }

}
