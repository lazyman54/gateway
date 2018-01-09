package com.dafy.base.gateway.api.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * @author maojinlin
 */
public class GatewayRpcParam implements Serializable {
    private static final long serialVersionUID = -3488516829871701406L;
    private String url;
    private String appKey;
    private String version;

    private Map<String, Object> data;


    public GatewayRpcParam() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GatewayRpcParam{" +
                "url='" + url + '\'' +
                ", appKey='" + appKey + '\'' +
                ", version='" + version + '\'' +
                ", data=" + data +
                '}';
    }

    private GatewayRpcParam(Builder builder) {
        setUrl(builder.url);
        setAppKey(builder.appKey);
        setVersion(builder.version);
        setData(builder.data);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private String appKey;
        private String version;
        private Map<String, Object> data;

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder appKey(String appKey) {
            this.appKey = appKey;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public GatewayRpcParam build() {
            return new GatewayRpcParam(this);
        }
    }
}