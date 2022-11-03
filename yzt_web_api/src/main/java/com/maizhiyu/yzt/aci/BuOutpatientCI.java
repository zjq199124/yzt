package com.maizhiyu.yzt.aci;

import com.maizhiyu.yzt.aro.BuOutpatientRO;
import com.maizhiyu.yzt.avo.BuOutpatientVO;
import com.maizhiyu.yzt.entity.BuOutpatient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BuOutpatientCI {

    BuOutpatientCI INSTANCE = Mappers.getMapper(BuOutpatientCI.class);

    BuOutpatientVO.AddOutpatientVO invertAddOutpatientVO(BuOutpatient outpatient);

}
