package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.PsOpinion;
import java.util.List;

public interface IPsOpinionService extends IService<PsOpinion> {

    Integer addOpinion(PsOpinion opinion);

    PsOpinion getOpinion(Long id);

    List<PsOpinion> getOpinionList(Long uid);
}
