package com.liangzhicheng.config.redis;

import com.liangzhicheng.common.redis.properties.SingleProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Configuration
public class SingleConfig {

    @Resource
    private SingleProperties singleProperties;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(
                        String.format(
                                "redis://%s:%s", singleProperties.getHost(), singleProperties.getPort()
                        )
                );
        if(StringUtils.hasText(singleProperties.getPassword())){
            singleServerConfig.setPassword(singleProperties.getPassword());
        }else{
            singleServerConfig.setPassword(null);
        }
        return Redisson.create(config);
    }

}
