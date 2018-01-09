package com.dafy.base.gateway.api.util;

import com.dafy.base.gateway.common.domain.constants.errorCode.SignErrorCode;
import com.dafy.base.gateway.common.exception.DafyBaseException;
import lombok.Builder;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/8/22
 */
@Builder
public class SignHelper {

    private String secretKey;

    private String appKey;

    private String url;

    private String version;

    private String sign;

    private String serverSign;

    private long timestamp;


    public void checkSign() throws DafyBaseException {
        if (!StringUtils.equals(serverSign, sign)) {
            throw new DafyBaseException(SignErrorCode.SIGN_ERROR, "方法签名错误");
        }
    }

    public SignHelper doSign() {
        String originStr;

        if (StringUtils.isBlank(version)) {
            originStr = String.format("appKey=%s&timestamp=%d&url=%s", appKey, timestamp, url);
        } else {
            originStr = String.format("appKey=%s&timestamp=%d&url=%s&version=%s", appKey, timestamp, url, version);
        }
        serverSign = Md5Crypt.md5Crypt(originStr.getBytes(), secretKey);

        return this;
    }

}
