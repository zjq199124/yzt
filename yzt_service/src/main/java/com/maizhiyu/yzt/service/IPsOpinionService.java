package com.maizhiyu.yzt.service;

import com.maizhiyu.yzt.entity.PsOpinion;
import java.util.List;

public interface IPsOpinionService {

    Integer addOpinion(PsOpinion opinion);

    PsOpinion getOpinion(Long id);

    List<PsOpinion> getOpinionList(Long uid);
}
