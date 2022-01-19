//package com.liangzhicheng.common.redis.properties;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Data
//@ConfigurationProperties(prefix = "spring.redis")
//@Component
//public class ClusterProperties {
//
//    /**
//     * redis客户端密码
//     */
//    private String password;
//
//    /**
//     * 集群
//     */
//    private cluster cluster;
//
//    @Data
//    public static class cluster{
//
//        /**
//         * 节点
//         */
//        private List<String> nodes;
//
//    }
//
//}
