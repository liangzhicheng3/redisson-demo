package com.liangzhicheng.modules.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/single")
public class SingleController {

    /**
     * 锁数量
     */
    private Integer lockNum = 10;

    /**
     * 无锁数量
     */
    private Integer notLockNum = 10;

    /**
     * 线程数
     */
    private static Integer threadNum = 10;

    @Resource
    private RedissonClient redissonClient;

    @GetMapping(value = "/test")
    public void test(){
        //计数器，指定1数量，每次试行后countDownLatch就会减1，直到减到0，此时就会执行countDownLatch.await()往下代码
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i = 0; i < threadNum; i++){
            TestRunnable testRunnable = new TestRunnable(countDownLatch);
            new Thread(testRunnable).start();
        }
        //释放所有线程
        countDownLatch.countDown();
    }

    private void testLockNum(){
        String testKey = "test_key";
        RLock lock = redissonClient.getLock(testKey);
        try{
            //加锁并设置超时时间2s
            lock.tryLock(2, TimeUnit.SECONDS);
            lockNum--;
            System.out.println("lockNum的值：" + lockNum);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    private void testNotLockNum(){
        notLockNum--;
        System.out.println("notLockNum的值：" + notLockNum);
    }

    public class TestRunnable implements Runnable{

        final CountDownLatch countDownLatch;

        public TestRunnable(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try{
                countDownLatch.await();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            testNotLockNum();
            testLockNum();
        }

    }

}
