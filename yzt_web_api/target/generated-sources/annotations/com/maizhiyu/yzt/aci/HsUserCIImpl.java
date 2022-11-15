package com.maizhiyu.yzt.aci;

import com.maizhiyu.yzt.aro.HsUserRO.AddUserRO;
import com.maizhiyu.yzt.avo.HsUserVO.AddUserVO;
import com.maizhiyu.yzt.entity.HsUser;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:58:12+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class HsUserCIImpl implements HsUserCI {

    @Override
    public HsUser convert(AddUserRO ro) {
        if ( ro == null ) {
            return null;
        }

        HsUser hsUser = new HsUser();

        hsUser.setUsername( ro.getUsername() );
        hsUser.setPassword( ro.getPassword() );
        hsUser.setRealname( ro.getRealname() );
        hsUser.setPhone( ro.getPhone() );
        hsUser.setSex( ro.getSex() );
        hsUser.setHisId( ro.getHisId() );

        return hsUser;
    }

    @Override
    public AddUserVO invertAddUserVO(HsUser user) {
        if ( user == null ) {
            return null;
        }

        AddUserVO addUserVO = new AddUserVO();

        addUserVO.setId( user.getId() );
        addUserVO.setUsername( user.getUsername() );
        addUserVO.setPassword( user.getPassword() );
        addUserVO.setRealname( user.getRealname() );
        addUserVO.setPhone( user.getPhone() );
        addUserVO.setSex( user.getSex() );
        addUserVO.setHisId( user.getHisId() );

        return addUserVO;
    }
}
