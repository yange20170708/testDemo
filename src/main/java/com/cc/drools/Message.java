/*
 * 文件名：Message.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年4月6日
 * 备注: 
 */
package com.cc.drools;

/**
 * 名称: Message.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年4月6日 下午2:55:09<br>
 * @version [版本号, V1.0]
 * @since 2017年4月6日 下午2:55:09
 * @author YanCui
 */
public class Message {  
	  
	  
    //常量 HELLO 值0  
    public static final int HELLO = 0;  
    //常量 GOODBYE 值0  
    public static final int GOODBYE = 1;  
  
  
    //消息  
    private String message;  
    //状态  
    private int status;  
  
  
    public String getMessage() {  
        return this.message;  
    }  
  
  
    public void setMessage(String message) {  
        this.message = message;  
    }  
  
  
    public int getStatus() {  
        return this.status;  
    }  
  
  
    public void setStatus(int status) {  
        this.status = status;  
    }  
      
  
  
}  