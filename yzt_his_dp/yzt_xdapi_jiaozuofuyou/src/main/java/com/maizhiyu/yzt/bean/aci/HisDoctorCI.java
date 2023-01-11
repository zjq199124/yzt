package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.HisDoctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HisDoctorCI {

    HisDoctorCI INSTANCE = Mappers.getMapper(HisDoctorCI.class);

    //@Mapping(source = "username", target = "username")
    @Mapping(source = "username", target = "hisId")
    HsUserXO.AddUserXO toAddUserXO(HisDoctor doctor);

}
