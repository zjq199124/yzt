package com.maizhiyu.yzt.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.PsFmaily;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.entity.RelUser;
import com.maizhiyu.yzt.mapper.RelUserMapper;
import com.maizhiyu.yzt.result.Result;
import com.maizhiyu.yzt.service.IRelUserService;
import com.maizhiyu.yzt.utils.IdcardToAge;
import com.maizhiyu.yzt.utils.RegexUtils;
import com.maizhiyu.yzt.utils.Sex;
import com.maizhiyu.yzt.vo.FamilyVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelUserService extends ServiceImpl<RelUserMapper, RelUser> implements IRelUserService {

    @Resource
    private PsUserService psUserService;

    @Resource
    private RelUserService relUserService;

    @Resource
    private PsFamilyService psFamilyService;

    @Override
    public List<Map<String, Object>> getFamily(Long userId) {
        Map map = new HashMap<>();
        //根据id查找相关的家庭成员的关联对象
        List<RelUser> relUsers = relUserService.query().eq("user_id", userId).list();
        //获取关联对象的家庭成员ids
        List<Long> userIds = relUsers.stream().map(RelUser::getAnotherUserId).collect(Collectors.toList());
        //根据ids批量查询到家庭成员的信息
        List<PsFmaily> psUsers = psFamilyService.listByIds(userIds);
        //家庭成员信息进行拼接
        List<Map<String, Object>> collect = relUsers.stream().map(e -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("userId",e.getUserId());
            map1.put("familyId",e.getAnotherUserId());
            map1.put("relationUserId",e.getId());
            map1.put("relId",e.getRelId());
            return map1;
        }).collect(Collectors.toList());
        List<Map<String, Object>> list = collect.stream().map(m -> {
            psUsers.stream().filter(m2 -> Objects.equals(m.get("userId"), m2.getId()))
                    .forEach(m2 -> {
                        m.put("nickname", m2.getNickname());
                        m.put("phone", m2.getPhone());
                        m.put("idCard", m2.getIdCard());
                        m.put("sex", m2.getSex());
                        m.put("age", m2.getAge());
                    });
            return m;
        }).collect(Collectors.toList());
        //循环给具体信息
        for (Map<String, Object> eve : list) {
            if (Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("zsex", "女");
            } else {
                eve.put("zset", "男");
            }
            if (Integer.parseInt(String.valueOf(eve.get("relId"))) == 1 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("tag", "母亲");
            } else if (Integer.parseInt(String.valueOf(eve.get("relId"))) == 1 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 1) {
                eve.put("tag", "父亲");
            } else if (Integer.parseInt(String.valueOf(eve.get("relId"))) == 2 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("tag", "妻子");
            } else if (Integer.parseInt(String.valueOf(eve.get("relId"))) == 2 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 1) {
                eve.put("tag", "丈夫");
            } else if (Integer.parseInt(String.valueOf(eve.get("relId"))) == 3 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 0) {
                eve.put("tag", "女儿");
            } else if (Integer.parseInt(String.valueOf(eve.get("relId"))) == 3 && Integer.parseInt(String.valueOf(eve.get("sex"))) == 1) {
                eve.put("tag", "儿子");
            } else {
                eve.put("tag", "其他");
            }
        }
        //根据家庭成员id的获取家庭成员信息
        PsUser user = psUserService.getUser(userId);
        map.put("id", user.getId());
        map.put("nickname", user.getNickname());
        map.put("age", user.getAge());
        map.put("zsex", Sex.SexToName(user.getSex()));
        map.put("tag", "本人");
        list.add(map);
        Collections.swap(list, list.size() - 1, 0);
        return list;
    }

    @Override
    public Boolean addFamily(FamilyVo familyVo) {
        Assert.isTrue(!RegexUtils.isPhoneInvalid(familyVo.getPhone()), "手机格式不合法");
        Assert.isTrue(IdcardToAge.isValid(familyVo.getIdCard()), "身份证号码不合法");
        PsFmaily psFmaily = new PsFmaily();
        psFmaily.setNickname(familyVo.getNickname())
                .setPhone(familyVo.getPhone())
                .setIdCard(familyVo.getIdCard())
                .setAge(familyVo.getAge())
                        .setSex(familyVo.getSex());
        psFamilyService.save(psFmaily);
        RelUser relUser = new RelUser();
        relUser.setUserId(familyVo.getUserId())
                .setAnotherUserId(psFmaily.getId())
                .setRelId(familyVo.getRelId());
        relUserService.save(relUser);
        return true;
    }

    @Override
    public Boolean updateFamily(FamilyVo familyVo) {
        PsFmaily usr = psFamilyService.getById(familyVo.getFamilyId());
        usr.setNickname(familyVo.getNickname())
                .setPhone(familyVo.getPhone())
                .setIdCard(familyVo.getIdCard())
                .setSex(familyVo.getSex())
                .setAge(familyVo.getAge());
        psFamilyService.updateById(usr);
        RelUser relUser = relUserService.getById(familyVo.getRelationUserId());
        relUser.setRelId(familyVo.getRelId());
        relUserService.updateById(relUser);
        return true;
    }
}
