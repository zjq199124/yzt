package com.maizhiyu.yzt.aci;

import com.maizhiyu.yzt.avo.BuOutpatientVO.AddOutpatientVO;
import com.maizhiyu.yzt.entity.BuOutpatient;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-15T17:10:27+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_341 (Oracle Corporation)"
)
public class BuOutpatientCIImpl implements BuOutpatientCI {

    @Override
    public AddOutpatientVO invertAddOutpatientVO(BuOutpatient outpatient) {
        if ( outpatient == null ) {
            return null;
        }

        AddOutpatientVO addOutpatientVO = new AddOutpatientVO();

        addOutpatientVO.setId( outpatient.getId() );
        addOutpatientVO.setTime( outpatient.getTime() );
        addOutpatientVO.setHisId( outpatient.getHisId() );
        addOutpatientVO.setExtra( outpatient.getExtra() );

        return addOutpatientVO;
    }
}
