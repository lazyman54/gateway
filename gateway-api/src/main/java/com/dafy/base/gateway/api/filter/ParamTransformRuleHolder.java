package com.dafy.base.gateway.api.filter;

import com.google.common.collect.ImmutableSet;

import javax.servlet.ServletRequest;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/19
 */
class ParamTransformRuleHolder {

    private static final ParamTransformRuleHolder _holder = new ParamTransformRuleHolder();

    private final ImmutableSet<String> contentTypes;

    private ParamTransformRuleHolder() {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        contentTypes = builder.add("multipart/form-data").add("application/x-www-form-urlencoded").build();
    }

    static ParamTransformRuleHolder getInstance() {
        return _holder;
    }

    boolean isContentTypeNeedSpecialHandle(String contentType) {
        return !contentTypes.contains(contentType);
    }

    String getContentType(ServletRequest request) {
        String contentType = request.getContentType();
        if (contentType == null) {
            return "";
        }
        int semicolon = contentType.indexOf(';');
        if (semicolon >= 0) {
            return contentType.substring(0, semicolon).trim();
        } else {
            return contentType.trim();
        }
    }


}
