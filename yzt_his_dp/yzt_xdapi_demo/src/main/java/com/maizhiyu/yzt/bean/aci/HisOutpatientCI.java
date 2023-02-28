package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.BuOutpatientXO;
import com.maizhiyu.yzt.entity.HisOutpatient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HisOutpatientCI {

    HisOutpatientCI INSTANCE = Mappers.getMapper(HisOutpatientCI.class);

    @Mapping(source = "id", target = "hisId")
    BuOutpatientXO.AddOutpatientXO toAddOutpatientXO(HisOutpatient outpatient);

}
