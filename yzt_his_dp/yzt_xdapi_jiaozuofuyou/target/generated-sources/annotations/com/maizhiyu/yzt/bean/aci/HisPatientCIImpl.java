package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.BuPatientXO.AddPatientXO;
import com.maizhiyu.yzt.entity.HisPatient;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:43:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class HisPatientCIImpl implements HisPatientCI {

    @Override
    public AddPatientXO toAddPatientXO(HisPatient patient) {
        if ( patient == null ) {
            return null;
        }

        AddPatientXO addPatientXO = new AddPatientXO();

        addPatientXO.setHisId( patient.getCode() );
        addPatientXO.setName( patient.getName() );
        addPatientXO.setSex( patient.getSex() );
        addPatientXO.setPhone( patient.getPhone() );
        addPatientXO.setIdcard( patient.getIdcard() );

        return addPatientXO;
    }
}
