package com.maizhiyu.yzt.aci;

import com.maizhiyu.yzt.aro.HsUserRO;
import com.maizhiyu.yzt.avo.HsUserVO;
import com.maizhiyu.yzt.entity.HsUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface HsUserCI {

    HsUserCI INSTANCE = Mappers.getMapper(HsUserCI.class);

    HsUser convert(HsUserRO.AddUserRO ro);

    HsUserVO.AddUserVO invertAddUserVO(HsUser user);

}
