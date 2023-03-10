package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.enums.FamilyTypeEnum;
import com.maizhiyu.yzt.entity.PsFamily;
import com.maizhiyu.yzt.entity.PsUser;
import com.maizhiyu.yzt.mapper.PsFamilyMapper;
import com.maizhiyu.yzt.mapper.PsUserMapper;
import com.maizhiyu.yzt.service.IPsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


@Service
@Transactional(rollbackFor=Exception.class)
public class PsUserService extends ServiceImpl<PsUserMapper,PsUser> implements IPsUserService {

    @Autowired
    private PsUserMapper mapper;

    @Resource
    private PsFamilyMapper psFamilyMapper;

    @Override
    public Integer addUser(PsUser user) {
        Integer res;
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", user.getOpenid());
        Long count = mapper.selectCount(wrapper);
        if (count > 0) {
            res = mapper.updateById(user);
        } else {
            res = mapper.insert(user);
        }
        return res;
    }

    @Override
    public Boolean setUser(PsUser user) {
        user.setStatus(1);
        user.setIsCompleteDetail(1);
        boolean res = this.saveOrUpdate(user);
        //设置完成之后主用户的信息（本人自己），将这份信息添加到家人表中去
        if (res) {
            LambdaQueryWrapper<PsFamily> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PsFamily::getPsUserId, user.getId())
                    .eq(PsFamily::getRelType, FamilyTypeEnum.SELF.code())
                    .eq(PsFamily::getIsDel, 0)
                    .orderByDesc(PsFamily::getUpdateTime)
                    .last("limit 1");
            PsFamily select = psFamilyMapper.selectOne(queryWrapper);
            PsFamily psFamily = new PsFamily();
            psFamily.setBirthday(user.getBirthday())
                    .setPhone(user.getPhone())
                    .setPsUserId(user.getId())
                    .setNickname(user.getNickname())
                    .setSex(user.getSex())
                    .setIdCard(user.getIdCard())
                    .setRelType(FamilyTypeEnum.SELF.code());

            if (Objects.isNull(select)) {
                int insert = psFamilyMapper.insert(psFamily);
                return insert > 0;
            } else {
                psFamily.setId(select.getId());
                int update = psFamilyMapper.updateById(psFamily);
                return update > 0;
            }
        }
        return false;
    }

    @Override
    public Integer setUserByOpenid(PsUser user) {
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", user.getOpenid());
        return mapper.update(user, wrapper);
    }

    @Override
    public PsUser getUser(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public PsUser getUserByOpenid(String openid) {
        QueryWrapper<PsUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid)
                .eq("is_del", 0)
                .orderByDesc("id")
                .last("limit 1");

        return mapper.selectOne(wrapper);
    }

    @Override
    public PsUser getUserByPhone(String phone) {
        LambdaQueryWrapper<PsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PsUser::getPhone, phone)
                .eq(PsUser::getIsDel, 0)
                .orderByDesc(PsUser::getCreateTime)
                .last("limit 1");

        return mapper.selectOne(wrapper);
    }
}
