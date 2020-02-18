package com.activiti.utils;

import java.util.Date;

public class test {
	public static void main(String[] args) {
        boolean flag = true;
        int index = 0;
        Long timeoutTime = 50L;  //超时时间50秒
        Date IstTime = null;
        while (flag) {
            try {
            	Date currentDate = new Date();
            	if(index==0){
            		IstTime = currentDate;
            	}
            	if(DatetimeUtil.calLastedTime(IstTime)>=timeoutTime){
            		flag = false;
            		System.out.println("超时了");
            		break;
            	}
            	System.out.println(DatetimeUtil.getDate(currentDate, DatetimeUtil.DEFAULT_FORMAT) + "--循环执行第" + (++index) + "次");
                Thread.sleep(5 * 1000); //设置暂停的时间 5 秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static int calLastedTime(Date startDate) {
		long a = new Date().getTime();
		long b = startDate.getTime();
		int c = (int)((a - b) / 1000);
		return c;
	}
}
