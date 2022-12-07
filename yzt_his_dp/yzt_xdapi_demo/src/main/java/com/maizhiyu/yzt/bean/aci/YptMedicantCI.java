package com.maizhiyu.yzt.bean.aci;

import com.github.pagehelper.PageInfo;
import com.maizhiyu.yzt.bean.aro.YptMedicantRO;
import com.maizhiyu.yzt.bean.avo.YptMedicantVO;
import com.maizhiyu.yzt.entity.YptMedicant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface YptMedicantCI {

    YptMedicantCI INSTANCE = Mappers.getMapper(YptMedicantCI.class);

    YptMedicant convert(YptMedicantRO.AddMedicantRO ro);

    YptMedicant convert(YptMedicantRO.SetMedicantRO ro);

    YptMedicantVO.GetMedicantVO invertGetMedicantVO(YptMedicant medicant);

    PageInfo<YptMedicantVO.GetMedicantListVO> invertGetMedicantListVO(PageInfo<YptMedicant> pageInfo);

}
