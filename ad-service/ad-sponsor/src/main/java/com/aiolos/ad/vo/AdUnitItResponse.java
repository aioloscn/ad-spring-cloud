package com.aiolos.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-01-24 13:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItResponse {

    private List<Long> ids;
}
