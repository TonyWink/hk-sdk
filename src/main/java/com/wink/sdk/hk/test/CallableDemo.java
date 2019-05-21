package com.wink.sdk.hk.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池
       // ExecutorService executorService = Executors.newFixedThreadPool(2);
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("Thread_current="+Thread.currentThread());
                return "Hello world";
            }
        };
        System.out.println("start");
        // 执行任务并获取Future对象  
        FutureTask<String> future = new FutureTask<String>(callable);
        new Thread(future).start();
        try{
            //future.get()线程结果，会阻塞当前线程直到线程结束
            System.out.println("future.get()="+future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("end");

        // 关闭线程池  
        //executorService.shutdown();
    }
}
