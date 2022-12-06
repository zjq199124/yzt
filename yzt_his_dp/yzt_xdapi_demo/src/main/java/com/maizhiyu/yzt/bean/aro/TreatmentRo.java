package com.maizhiyu.yzt.bean.aro;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
@Validated
public class TreatmentRo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer treatmentId;

    private Integer medicalRecordId;

    private Integer doctorId;

    private Date submitTime;

    private List<TreatmentItemsRo> treatmentItemsList;

}
