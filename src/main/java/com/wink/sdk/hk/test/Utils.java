package com.wink.sdk.hk.test;

public class Utils {
	
	private static Object obj = new Object();
	
    //静态同步方法
    public void makeCall() {
    	synchronized(obj){
	        for (int i = 0; i < 5; i++) {
	            try {
	                System.out.println("makeCall");
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
    	}
    }
    //静态同步方法
    public  void sendMail() {
    	synchronized(obj){
	        for (int i = 0; i < 5; i++) {
	            try {
	                System.out.println("sendMail");
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
    	}
    }
 public static void main(String[] agrs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	Utils u = new Utils();
                u.makeCall();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
            	Utils u = new Utils();
                u.sendMail();
            }
        }).start();
    }
 }

