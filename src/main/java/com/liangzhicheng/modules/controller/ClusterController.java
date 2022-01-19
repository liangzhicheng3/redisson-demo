package com.liangzhicheng.modules.controller;

import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/cluster")
public class ClusterController {

    private static String TEST_NUM = "test_num";
    private static String TEST_KEY = "test_key";

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    @PostMapping(value = "/save")
    public String save(){
        stringRedisTemplate.opsForValue().set(TEST_NUM, "100");
        return "success";
    }

    @PostMapping(value = "/seckill")
    public String seckill(){
        String flag = "success";
        RLock lock = redissonClient.getLock(TEST_KEY);
        try{
            RFuture<Boolean> future = lock.tryLockAsync(100, 5, TimeUnit.SECONDS);
            Boolean result = future.get();
            System.out.println("[分布式锁] result：" + result);
            if(result){
                int num = Integer.parseInt(stringRedisTemplate.opsForValue().get(TEST_NUM));
                if(num > 0){
                    stringRedisTemplate.opsForValue().set(TEST_NUM, (num - 1) + "");
                }else{
                    flag = "fail";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return flag;
    }

}
