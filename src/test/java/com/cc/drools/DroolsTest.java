/*
 * 文件名：DroolsTest.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年4月6日
 * 备注: 
 */
package com.cc.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 名称: DroolsTest.java<br>
 * 描述:<br>
 * 类型: JAVA<br>
 * 最近修改时间:2017年4月6日 下午2:56:22<br>
 * @version [版本号, V1.0]
 * @since 2017年4月6日 下午2:56:22
 * @author YanCui
 */
public class DroolsTest {
	public static final void main(String[] args) {  
        try {  
            // load up the knowledge base  
            //得到一个KieService    用于访问KIE的API
            KieServices ks = KieServices.Factory.get();  
            //加载默认路径下的kmodule.xml 得到kContainer
            KieContainer kContainer = ks.getKieClasspathContainer();  
            //kContainer得到一个会话  
            KieSession kSession = kContainer.newKieSession("ksession-rules");  
  
            // go !  
            Message message = new Message();
            message.setMessage("Hello World");  
            message.setStatus(Message.HELLO);  
              
            //将事实数据传入kSession中  
            kSession.insert(message);  
            //执行所有的规则  
            kSession.fireAllRules();  
        } catch (Throwable t) {  
            t.printStackTrace();  
        }  
    }  
}
