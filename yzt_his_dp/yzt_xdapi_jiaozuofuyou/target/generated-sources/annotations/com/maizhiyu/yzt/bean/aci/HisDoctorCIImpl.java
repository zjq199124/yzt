package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.HsUserXO.AddUserXO;
import com.maizhiyu.yzt.entity.HisDoctor;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:43:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class HisDoctorCIImpl implements HisDoctorCI {

    @Override
    public AddUserXO toAddUserXO(HisDoctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        AddUserXO addUserXO = new AddUserXO();

        addUserXO.setUsername( doctor.getUsername() );
        addUserXO.setHisId( doctor.getUsername() );
        addUserXO.setRealname( doctor.getRealname() );

        return addUserXO;
    }
}
