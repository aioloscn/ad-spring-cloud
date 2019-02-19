package com.aiolos.ad.search;

import com.aiolos.ad.search.vo.SearchRequest;
import com.aiolos.ad.search.vo.SearchResponse;

/**
 * @author Aiolos
 * @date 2019-02-19 21:56
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
