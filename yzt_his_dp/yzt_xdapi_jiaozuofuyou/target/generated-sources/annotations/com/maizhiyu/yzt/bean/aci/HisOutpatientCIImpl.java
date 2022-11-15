package com.maizhiyu.yzt.bean.aci;

import com.maizhiyu.yzt.bean.axo.BuOutpatientXO.AddOutpatientXO;
import com.maizhiyu.yzt.entity.HisOutpatient;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T09:43:04+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class HisOutpatientCIImpl implements HisOutpatientCI {

    @Override
    public AddOutpatientXO toAddOutpatientXO(HisOutpatient outpatient) {
        if ( outpatient == null ) {
            return null;
        }

        AddOutpatientXO addOutpatientXO = new AddOutpatientXO();

        addOutpatientXO.setHisId( outpatient.getCode() );
        addOutpatientXO.setTime( outpatient.getTime() );
        addOutpatientXO.setDoctorId( outpatient.getDoctorId() );
        addOutpatientXO.setPatientId( outpatient.getPatientId() );

        return addOutpatientXO;
    }
}
