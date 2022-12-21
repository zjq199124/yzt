package com.maizhiyu.yzt.bean.aci;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maizhiyu.yzt.bean.aro.YptTreatmentRO;
import com.maizhiyu.yzt.bean.avo.YptTreatmentVO;
import com.maizhiyu.yzt.entity.YptTreatment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface YptTreatmentCI {

    YptTreatmentCI INSTANCE = Mappers.getMapper(YptTreatmentCI.class);

    YptTreatment convert(YptTreatmentRO.AddTreatmentRO ro);

    YptTreatment convert(YptTreatmentRO.SetTreatmentRO ro);

    YptTreatmentVO.GetTreatmentVO invertGetTreatmentVO(YptTreatment treatment);

    Page<YptTreatmentVO.GetTreatmentListVO> invertGetTreatmentListVO(IPage<YptTreatment> pageInfo);

}
