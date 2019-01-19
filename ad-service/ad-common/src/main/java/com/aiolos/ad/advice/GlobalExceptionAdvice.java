package com.aiolos.ad.advice;

import com.aiolos.ad.exception.AdException;
import com.aiolos.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aiolos
 * @date 2019-01-19 16:38
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 捕获AdException进行异常处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req, AdException e) {

        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(e.getMessage());
        return response;
    }
}
