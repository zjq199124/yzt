package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.BuPatientXO;
import com.maizhiyu.yzt.entity.HisPatient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HisPatientCI {

    HisPatientCI INSTANCE = Mappers.getMapper(HisPatientCI.class);

    @Mapping(source = "code", target = "hisId")
    BuPatientXO.AddPatientXO toAddPatientXO(HisPatient patient);

}
