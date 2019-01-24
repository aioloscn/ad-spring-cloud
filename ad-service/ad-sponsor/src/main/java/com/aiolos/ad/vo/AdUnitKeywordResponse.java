package com.aiolos.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-01-24 13:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordResponse {

    private List<Long> ids;
}
