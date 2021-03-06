package com.aiolos.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Aiolos
 * @date 2019-01-31 13:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitResponse {

    private List<Long> ids;
}
