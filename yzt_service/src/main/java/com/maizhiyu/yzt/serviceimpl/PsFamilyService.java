package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsFamily;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.mapper.PsFamilyMapper;
import com.maizhiyu.yzt.service.IPsFamilyService;
import com.maizhiyu.yzt.utils.IdcardToAge;
import com.maizhiyu.yzt.utils.Sex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PsFamilyService extends ServiceImpl<PsFamilyMapper, PsFamily> implements IPsFamilyService {

    @Resource
    private PsFamilyService psFamilyService;

    @Resource
    private PsUserService psUserService;

    @Override
    public List<Map<String, Object>> getFamily(Long userId) {
        List<PsFamily> family = psFamilyService.query().eq("ps_user_id", userId).list();
        List<Map<String, Object>> familylist = new ArrayList<>();
        for (PsFamily familys:family){
            Map<String, Object> eve = BeanUtil.beanToMap(familys);
            eve.put("zsex",IdcardToAge.getAge(familys.getIdCard()));
            if (Integer.parseInt(String.valueOf(eve.get("relType"))) == 1 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("tag", "母亲");
            } else if (Integer.parseInt(String.valueOf(eve.get("relType"))) == 1 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 1) {
                eve.put("tag", "父亲");
            } else if (Integer.parseInt(String.valueOf(eve.get("relType"))) == 2 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("tag", "妻子");
            } else if (Integer.parseInt(String.valueOf(eve.get("relType"))) == 2 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 1) {
                eve.put("tag", "丈夫");
            } else if (Integer.parseInt(String.valueOf(eve.get("relType"))) == 3 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("tag", "女儿");
            } else if (Integer.parseInt(String.valueOf(eve.get("relType"))) == 3 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 1) {
                eve.put("tag", "儿子");
            } else {
                eve.put("tag", "其他");
            }
            familylist.add(eve);
        }
        Map map = new HashMap<>();
        PsUser user = psUserService.getById(userId);
        map.put("id", user.getId());
        map.put("nickname", user.getNickname());
        map.put("age", IdcardToAge.getAge(user.getIdCard()));
        map.put("sex",user.getSex());
        map.put("zsex", Sex.SexToName(user.getSex()));
        map.put("tag", "本人");
        familylist.add(map);
        return familylist;
    }

    @Override
    public Boolean addFamily(PsFamily psFamily) {
        boolean b = psFamilyService.saveOrUpdate(psFamily);
        return null;
    }
}
