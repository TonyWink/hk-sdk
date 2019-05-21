package com.wink.sdk.hk.test;

import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    private ArrayList<String> ans = new ArrayList<String>();
    
    //拆分字符串，并返回所有可能的子串
    public static ArrayList<String> Permutation(String str) {
    	ArrayList<String> rs= new ArrayList<String>();
        //递归结束
        if(str.length()==1){
           rs.add(str);
           return rs;
        }
        String tailStr = str.charAt(str.length()-1)+"";
        String subStr = str.substring(0,str.length()-1);
        ArrayList<String> arrStr=Permutation(subStr);
        //可以插入到子串的任意位置
        for(String s : arrStr){
            if(!rs.contains(s+tailStr)){
                rs.add(s+tailStr);
            }
            if(!rs.contains(tailStr+s)){
                rs.add(tailStr+s);
            }
        }
        
        String headStr = str.charAt(0)+"";
        subStr = str.substring(1,str.length());
        arrStr=Permutation(subStr);
        for(String s : arrStr){
            if(!rs.contains(s+headStr)){
                rs.add(s+headStr);
            }
            if(!rs.contains(headStr+s)){
                rs.add(headStr+s);
            }
        }
        Collections.sort(rs);
        return rs;
    }
    
    public static void main(String[] args) {
    	System.out.println(Permutation("aab"));
	}
}
