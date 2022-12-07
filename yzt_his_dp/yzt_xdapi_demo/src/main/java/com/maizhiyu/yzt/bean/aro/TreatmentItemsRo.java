package com.maizhiyu.yzt.bean.aro;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@ApiModel
@Validated
public class TreatmentItemsRo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer treatmentItemsId;

    private Integer treatmentId;

    private Integer fmedicalItemsId;

    private Integer quantity;

    private Integer actualQuantity;

    private Integer expenseItemsId;

    private String validStatus;
}
