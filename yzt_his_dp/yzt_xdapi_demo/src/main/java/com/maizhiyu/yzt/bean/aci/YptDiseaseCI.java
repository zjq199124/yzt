package com.maizhiyu.yzt.bean.aci;

import com.github.pagehelper.PageInfo;
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

    PageInfo<YptDiseaseVO.GetDiseaseListVO> invertGetDiseaseListVO(PageInfo<YptDisease> pageInfo);

}
