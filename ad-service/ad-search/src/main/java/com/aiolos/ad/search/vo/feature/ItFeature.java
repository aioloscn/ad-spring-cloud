package com.aiolos.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 兴趣特征
 * @author Aiolos
 * @date 2019-02-19 22:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItFeature {

    private List<String> its;
}
