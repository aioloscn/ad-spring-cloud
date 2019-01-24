package com.aiolos.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Aiolos
 * @date 2019-01-24 13:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate() {

        return planId != null && !StringUtils.isEmpty(unitName) && positionType != null && budget != null;
    }
}
