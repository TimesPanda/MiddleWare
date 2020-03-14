package com.times.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 二倍均值法：实现红包生成工具类
 */
public class RedPacketUtil {
    /**
     * 发红包算法，金额参数以分为单位
     * @param totalAmount    总金额
     * @param totalPeopleNum 总人数
     * @return
     */
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum){
        List<Integer> amountList = new ArrayList<Integer>();
        if(totalAmount > 0 && totalPeopleNum > 0){
            //记录剩余的总金额：初始化时金额即为红包的总金额
            Integer restAmount = totalAmount;
            //记录剩余的总人数：初始化时即为指定的总人数
            Integer restPeopleNum = totalPeopleNum;
            //定义产生随机数的对象
            Random random = new Random();
            //循环遍历、迭代更新的产生随机金额，直到N-1>0
            for(int i=0;i<totalPeopleNum-1;i++){
                //随机范围：[1,剩余人均金额的两倍，左闭右开amount产生的随机金额R]
                int amount = random.nextInt(restAmount/restPeopleNum*2 - 1) +1;
                //更新剩余的总金额M=M-R
                restAmount -= amount;
                //更新剩余的总人数N=N-1
                restPeopleNum--;
                //将产生的随机金额添加进列表List中
                amountList.add(amount);
            }
            //将最后一个金额也加入列表之中
            amountList.add(restAmount);
        }
        //返回最终产生的随机金额列表
        return amountList;
    }
}
