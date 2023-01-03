package com.maizhiyu.yzt.controller;


import com.maizhiyu.yzt.aci.BuOutpatientCI;
import com.maizhiyu.yzt.aro.BuOutpatientRO;
import com.maizhiyu.yzt.avo.BuOutpatientVO;
import com.maizhiyu.yzt.entity.BuOutpatient;
import com.maizhiyu.yzt.entity.BuPatient;
import com.maizhiyu.yzt.entity.HsDepartment;
import com.maizhiyu.yzt.entity.HsUser;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IBuOutpatientService;
import com.maizhiyu.yzt.service.IBuPatientService;
import com.maizhiyu.yzt.service.IHsDepartmentService;
import com.maizhiyu.yzt.service.IHsUserService;
import com.maizhiyu.yzt.utils.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;


@Api(tags = "科室接口")
@RestController
@RequestMapping("/department")
public class BuDepartmentController {

    @Resource
    IHsDepartmentService iHsDepartmentService;

    @ApiOperation(value = "新增科室His", notes = "新增科室His")
    @PostMapping("/addDepartmentHis")
    public Result<Long> addDepartment(HsDepartment department) {
        Boolean save = iHsDepartmentService.save(department);
        // 返回结果
        return Result.success(save);
    }


}
