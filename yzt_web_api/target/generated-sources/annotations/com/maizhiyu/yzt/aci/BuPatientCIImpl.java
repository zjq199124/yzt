package com.maizhiyu.yzt.aci;

import com.maizhiyu.yzt.aro.BuPatientRO.AddPatientRO;
import com.maizhiyu.yzt.avo.BuPatientVO.AddPatientVO;
import com.maizhiyu.yzt.entity.BuPatient;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T17:10:27+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class BuPatientCIImpl implements BuPatientCI {

    @Override
    public BuPatient convert(AddPatientRO ro) {
        if ( ro == null ) {
            return null;
        }

        BuPatient buPatient = new BuPatient();

        buPatient.setName( ro.getName() );
        buPatient.setSex( ro.getSex() );
        buPatient.setBirthday( ro.getBirthday() );
        buPatient.setPhone( ro.getPhone() );
        buPatient.setIdcard( ro.getIdcard() );
        buPatient.setHisId( ro.getHisId() );
        buPatient.setExtra( ro.getExtra() );

        return buPatient;
    }

    @Override
    public AddPatientVO invertAddPatientVO(BuPatient patient) {
        if ( patient == null ) {
            return null;
        }

        AddPatientVO addPatientVO = new AddPatientVO();

        addPatientVO.setId( patient.getId() );
        addPatientVO.setName( patient.getName() );
        addPatientVO.setSex( patient.getSex() );
        addPatientVO.setBirthday( patient.getBirthday() );
        addPatientVO.setPhone( patient.getPhone() );
        addPatientVO.setIdcard( patient.getIdcard() );
        addPatientVO.setHisId( patient.getHisId() );
        addPatientVO.setExtra( patient.getExtra() );

        return addPatientVO;
    }
}
