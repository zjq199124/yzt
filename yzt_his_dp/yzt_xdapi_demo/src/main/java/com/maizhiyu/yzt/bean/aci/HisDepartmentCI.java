package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.HsDepartmentXO;
import com.maizhiyu.yzt.bean.axo.HsUserXO;
import com.maizhiyu.yzt.entity.HisDepartment;
import com.maizhiyu.yzt.entity.HisDoctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HisDepartmentCI {

    HisDepartmentCI INSTANCE = Mappers.getMapper(HisDepartmentCI.class);

    @Mapping(source = "id", target = "hisId")
    @Mapping(source = "name", target = "dname")
    HsDepartmentXO.AddDepartmentXO toAddDepartmentXO(HisDepartment department);

}
