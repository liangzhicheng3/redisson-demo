//package com.liangzhicheng.config.redis;
//
//import com.liangzhicheng.common.redis.properties.ClusterProperties;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.ClusterServersConfig;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class ClusterConfig {
//
//    @Resource
//    private ClusterProperties clusterProperties;
//
//    @Bean
//    public RedissonClient redisson(){
//        int nodes = clusterProperties.getCluster().getNodes().size();
//        List<String> clusterNodes = new ArrayList<>(nodes);
//        for(int i = 0; i < nodes; i++){
//            clusterNodes.add(String.format("redis://%s", clusterProperties.getCluster().getNodes().get(i)));
//        }
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
//        if(StringUtils.hasText(clusterProperties.getPassword())){
//            clusterServersConfig.setPassword(clusterProperties.getPassword());
//        }else{
//            clusterServersConfig.setPassword(null);
//        }
//        return Redisson.create(config);
//    }
//
//}
