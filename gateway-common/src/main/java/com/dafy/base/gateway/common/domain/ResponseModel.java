package com.dafy.base.gateway.common.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/7/3
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> {

    public final static ResponseModel<Object> DEFAULT_RESPONSE = new ResponseModel<Object>();

    private String code;
    private String message = "";
    private String redirect;
    private T data;

    public ResponseModel() {
        this("0", null);
    }

    public ResponseModel(String code, T data) {
        this(code, null, null, data);
    }

    public ResponseModel(String code, String message, String redirect, T data) {
        this.code = code;
        this.message = message;
        this.redirect = redirect;
        this.data = data;
    }

    private ResponseModel(Builder<T> builder) {
        setCode(builder.code);
        setMessage(builder.message);
        setRedirect(builder.redirect);
        setData(builder.data);
    }

    public static <T> Builder<T> newBuilder() {
        return new Builder<T>();
    }


    public static final class Builder<T> {
        private String code = "0";
        private String message;
        private String redirect;
        private T data;

        private Builder() {
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder redirect(String redirect) {
            this.redirect = redirect;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public ResponseModel<T> build() {
            return new ResponseModel<T>(this);
        }
    }


}
