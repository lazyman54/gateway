package com.dafy.base.gateway.common.util;

/**
 * 此类用于集中存储redis key，防止redis key散步在代码的各个位置
 * 存放系统中所有的redisKey，请根据业务类型通过: - .等来命名，建议名字不要太长（浪费空间影响性能），也不要太短（可读性太差）
 * <p>不同数据结构key的命名规则规则:</p>
 * <li>字符串无需加前缀</li>
 * <li>set加前缀<code>s:</code></li>
 * <li>zset 加前缀<code>z:</code></li>
 * <li>list（queue）加前缀<code>l:</code></li>
 * <li>hash加前缀<code>h:</code></li>
 *
 * @author lazyman
 * @version v1.0
 * @date 2017/7/6
 */
public class RedisKey {


    /**
     * 根据url找到url对应map的映射，因为暂时redis没有切片，所以不考虑分key
     *
     * @param appKey url,格式为：base.gateway.login，即把url里的/转换成. {@link RedisKeyUtil#formatFromOriginUrl}
     */
    public static String getRpcInfoKey(String appKey) {
        return String.format("h:url_rpc:%s", appKey);
    }

    /**
     * 获得服务地址与appKey的映射关系
     */
    public static String getServerMapAppKey() {
        return "h:server_app";
    }

    /**
     * 获得服务开关的key
     *
     * @param appKey appKey
     */
    public static String getServerSwitchKey(String appKey) {
        return String.format("h:server_switch:%s", appKey);
    }
}
