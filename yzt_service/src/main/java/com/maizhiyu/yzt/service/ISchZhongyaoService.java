package com.maizhiyu.yzt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizhiyu.yzt.entity.SchZhongyao;
import java.util.List;
import java.util.Map;

public interface ISchZhongyaoService extends IService<SchZhongyao> {

    public Integer addZhongyao(SchZhongyao agency);

    public Integer delZhongyao(Long id);

    public Integer setZhongyao(SchZhongyao agency);

    public SchZhongyao getZhongyao(Long id);

    public List<Map<String,Object>> getZhongyaoList(Long diseaseId, Integer status, String term);

    public List<Map<String,Object>> getZhongyaoList2(Long diseaseId, Integer status, String term,Long customerId);
}
