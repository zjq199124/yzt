package com.maizhiyu.yzt.bean.aci;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maizhiyu.yzt.bean.aro.YptDiseaseRO;
import com.maizhiyu.yzt.bean.avo.YptDiseaseVO;
import com.maizhiyu.yzt.entity.YptDisease;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface YptDiseaseCI {

    YptDiseaseCI INSTANCE = Mappers.getMapper(YptDiseaseCI.class);

    YptDisease convert(YptDiseaseRO.AddDiseaseRO ro);

    YptDisease convert(YptDiseaseRO.SetDiseaseRO ro);

    YptDiseaseVO.GetDiseaseVO invertGetDiseaseVO(YptDisease disease);

    IPage<YptDiseaseVO.GetDiseaseListVO> invertGetDiseaseListVO(IPage<YptDisease> pageInfo);

}
