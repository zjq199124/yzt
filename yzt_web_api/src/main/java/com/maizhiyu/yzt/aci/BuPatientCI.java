package com.maizhiyu.yzt.aci;

import com.maizhiyu.yzt.aro.BuPatientRO;
import com.maizhiyu.yzt.avo.BuPatientVO;
import com.maizhiyu.yzt.entity.BuPatient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BuPatientCI {

    BuPatientCI INSTANCE = Mappers.getMapper(BuPatientCI.class);

    BuPatient convert(BuPatientRO.AddPatientRO ro);

    BuPatientVO.AddPatientVO invertAddPatientVO(BuPatient patient);

}
