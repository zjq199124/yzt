package com.maizhiyu.yzt.bean.aci;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    IPage<YptMedicantVO.GetMedicantListVO> invertGetMedicantListVO(IPage<YptMedicant> pageInfo);

}
