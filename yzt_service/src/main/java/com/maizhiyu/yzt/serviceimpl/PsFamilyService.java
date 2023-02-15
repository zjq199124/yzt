package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.enums.FamilyTypeEnum;
import com.maizhiyu.yzt.entity.PsFamily;
import com.maizhiyu.yzt.mapper.PsFamilyMapper;
import com.maizhiyu.yzt.service.IPsFamilyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PsFamilyService extends ServiceImpl<PsFamilyMapper, PsFamily> implements IPsFamilyService {

    @Resource
    private PsUserService psUserService;

    @Override
    public List<PsFamily> getFamily(Long userId) {
        List<PsFamily> familyList = this.query().eq("ps_user_id", userId).list();
        familyList.forEach(item -> {
            FamilyTypeEnum familyTypeEnum = FamilyTypeEnum.getByCode(item.getRelType());
            item.setRelDescription(familyTypeEnum.msg());
        });
        return familyList;
    }

    @Override
    public Boolean addFamily(PsFamily psFamily) {
        if (psFamily.getRelType().equals(FamilyTypeEnum.PARENT.code())) {
            if (psFamily.getSex() == 1) {
                psFamily.setRelType(FamilyTypeEnum.FATHER.code());
            } else {
                psFamily.setRelType(FamilyTypeEnum.MOTHER.code());
            }
        }
        if (psFamily.getRelType().equals(FamilyTypeEnum.CHILD.code())) {
            if (psFamily.getSex() == 1) {
                psFamily.setRelType(FamilyTypeEnum.SON.code());
            } else {
                psFamily.setRelType(FamilyTypeEnum.DAUGHTER.code());
            }
        }
        if (psFamily.getRelType().equals(FamilyTypeEnum.SPOUSE.code())) {
            if (psFamily.getSex() == 1) {
                psFamily.setRelType(FamilyTypeEnum.HUSBAND.code());
            } else {
                psFamily.setRelType(FamilyTypeEnum.WIFE.code());
            }
        }
        boolean res = this.saveOrUpdate(psFamily);
        return res;
    }
}
