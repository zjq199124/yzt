package com.maizhiyu.yzt.serviceimpl;

import com.maizhiyu.yzt.entity.SchZhongyao;
import com.maizhiyu.yzt.mapper.MsZhongyaoHerbsMapper;
import com.maizhiyu.yzt.mapper.SchZhongyaoMapper;
import com.maizhiyu.yzt.service.ISchZhongyaoService;
import com.maizhiyu.yzt.vo.SchZhongyaoHerbsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor=Exception.class)
public class SchZhongyaoService implements ISchZhongyaoService {

    @Autowired
    private SchZhongyaoMapper mapper;

    @Autowired
    private MsZhongyaoHerbsMapper msZhongyaoHerbsMapper;

    @Override
    public Integer addZhongyao(SchZhongyao chengyao) {
        return mapper.insert(chengyao);
    }

    @Override
    public Integer delZhongyao(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public Integer setZhongyao(SchZhongyao chengyao) {
        return mapper.updateById(chengyao);
    }

    @Override
    public SchZhongyao getZhongyao(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getZhongyaoList(Long diseaseId, Integer status, String term) {
        List<Map<String, Object>> list = mapper.selectZhongyaoList(status, diseaseId, term);

        if(list != null && list.size() > 0) {
            List<Long> ids = list.stream().map(item -> (Long)item.get("id")).collect(Collectors.toList());
            List<SchZhongyaoHerbsVO> list1 = msZhongyaoHerbsMapper.getMsZhongyaoHerbsListBySchZhongyaoIds(ids);
            if(list1 != null && list1.size() > 0) {
                Map<Long, List<SchZhongyaoHerbsVO>> collect = list1.stream().collect(Collectors.groupingBy(SchZhongyaoHerbsVO::getZyId));
                list.forEach(item -> {
                    List<SchZhongyaoHerbsVO> list2 = collect.get((Long) item.get("id"));
                    if(list2 != null) {
                        StringBuffer stringBuffer = new StringBuffer("");
                        for (int i = 0; i < list2.size(); i++) {
                            SchZhongyaoHerbsVO schZhongyaoHerbsVOS = list2.get(i);
                            stringBuffer.append(schZhongyaoHerbsVOS.getHerbsName());
                            stringBuffer.append(":");
                            stringBuffer.append(schZhongyaoHerbsVOS.getNum());
                            stringBuffer.append(schZhongyaoHerbsVOS.getUnit());
                            if(i < list2.size()-1) {
                                stringBuffer.append("，");
                            }
                        }
                        item.put("herbss",stringBuffer.toString());

                    }
                });
            }

        }

        return list;
    }
}
