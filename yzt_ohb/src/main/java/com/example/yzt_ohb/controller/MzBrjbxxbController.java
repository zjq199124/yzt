package com.example.yzt_ohb.controller;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.example.yzt_ohb.model.MzBrjbxxbDO;
import com.example.yzt_ohb.service.MzBrjbxxbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * ??????? 前端控制器
 * </p>
 *
 * @author zyx
 * @since 2021-12-23
 */
@RestController
@RequestMapping("/mzBrjbxxbDO")
public class MzBrjbxxbController {
    @Autowired
    private MzBrjbxxbService mzBrjbxxbService;
    @RequestMapping(value = "queryMzBrjbxxbDO")
    public MzBrjbxxbDO test(@RequestParam String token) {
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, "ohbtestohbtest11".getBytes());
        //解密为字符串
        String decryptStr = aes.decryptStr(token, CharsetUtil.CHARSET_UTF_8);
            MzBrjbxxbDO mzBrjbxxbDO = mzBrjbxxbService.getById(Long.parseLong(decryptStr));
            return mzBrjbxxbDO;
    }
}

