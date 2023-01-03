package com.maizhiyu.yzt.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizhiyu.yzt.entity.TsAss;
import com.maizhiyu.yzt.entity.UserAss;
import com.maizhiyu.yzt.mapper.TsAssMapper;
import com.maizhiyu.yzt.mapper.UserAssMapper;
import com.maizhiyu.yzt.service.IUserAssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAssService extends ServiceImpl<UserAssMapper, UserAss> implements IUserAssService {

    @Resource
    private UserAssMapper userAssMapper;

    @Resource
    private TsAssMapper tsAssMapper;

    @Override
    public IPage<UserAss> getAssGrade(Page page, UserAss userAss) {
        QueryWrapper<UserAss> wrapper=new QueryWrapper<UserAss>(userAss);
        wrapper.lambda().orderByAsc(UserAss::getCreateTime);
        return userAssMapper.selectPage(page,wrapper);
    }

    @Override
    public List<Map<String, Object>> getUserGrade(Long assId) {
        List<Map<String,Object>> list = userAssMapper.selectUserGrade(assId);
        int totalscore = list.stream().mapToInt(e -> Integer.parseInt(e.get("score").toString())).sum();
        Map m1 =new HashMap();
        m1.put("总分",totalscore);
        list.add(m1);
        TsAss tsAss = tsAssMapper.selectById(assId);
        tsAss.setStatus(2).setTotalScore(totalscore);
        Integer res = tsAssMapper.updateById(tsAss);
        return list;
    }
}
