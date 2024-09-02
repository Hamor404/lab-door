package com.villege.common.validator;

import com.villege.common.exception.WebException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author 赖国荣 31343080@qq.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new WebException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new WebException(message);
        }
    }
}
