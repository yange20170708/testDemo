/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;


/**
 * 名称: Test.java<br>
 * 描述:看spring jms的代码时，发现SmartInitializingSingleton 这个接口也比较有意思。
		就是当所有的singleton的bean都初始化完了之后才会回调这个接口。不过要注意是 4.1 之后才出现的接口
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public interface SmartInitializingSingletonTest {

    /**
     * Invoked right at the end of the singleton pre-instantiation phase,
     * with a guarantee that all regular singleton beans have been created
     * already. {@link ListableBeanFactory#getBeansOfType} calls within
     * this method won't trigger accidental side effects during bootstrap.
     * <p><b>NOTE:</b> This callback won't be triggered for singleton beans
     * lazily initialized on demand after {@link BeanFactory} bootstrap,
     * and not for any other bean scope either. Carefully use it for beans
     * with the intended bootstrap semantics only.
     */
    void afterSingletonsInstantiated();

}
