/*
 * 文件名：Test.java
 * 版权：Copyright 2017 QuarkFinance IT
 * 描述：<描述>
 * 创建人：YanCui
 * 创建时间：2017年6月27日
 * 备注: 
 */
package com.cc.common;

import java.lang.reflect.Field;

import org.junit.Test;

import sun.misc.Unsafe;


/**
 * 名称: UnSafeTest.java<br>
 * 描述:Unsafe类是一个受保护的类，是不能直接在程序中使用的。直接的使用会抛出SecurityException异常<br>
 * import sun.misc.Unsafe 需要手工添加，Eclispe或者其他IDE并不会直接提示<br>
 * 
 * 为什么要用unsafe直接操作内存，因为快：
 * 每次set值的时候，JVM内部依旧会每次去找这个对象属性的内存偏移量。
 * 现在我直接将偏移量拿出来了，不用每次找偏移量了，速度加快那是必然滴，当然被修改的对象肯定是一个对象。
 * 
 * 类型: JAVA<br>
 * 最近修改时间:2017年6月27日 下午6:59:33<br>
 * @version [版本号, V1.0]
 * @since 2017年6月27日 下午6:59:33
 * @author YanCui
 */
public class UnSafeTest {
	public static void main(String[] args) {
		isTrust();
	}
	
	public void useUnSafeNo() {
        try {
        	UnSafeTest UnSafeTest = new UnSafeTest();
            User user = UnSafeTest.new User();
            Unsafe unsafe = Unsafe.getUnsafe();//这行会直接报错SecurityException
            Field field = user.getClass().getDeclaredField("age");
            long ageOffset = unsafe.objectFieldOffset(field);
            unsafe.putInt(user, ageOffset, 10);
            System.out.println(user.getName());
            System.out.println(unsafe);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	/**
	 * 方法描述 通过反射打破unsafe直接使用限制
	 * @变更记录 2017年8月3日 下午5:55:28  YanCui 
	 */
	@Test
	public void useUnsafeYes() {
		try {                
			//方法一 反射theUnsafe变量
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            
            System.out.println(unsafe);
            
            //方法二 反射构造函数
//            Constructor<Unsafe> con = Unsafe.class.getDeclaredConstructor();
//            // 用该私有构造方法创建对象
//            // IllegalAccessException:非法的访问异常。
//            // 暴力访问
//            con.setAccessible(true);// 值为true则指示反射的对象在使用时应该取消Java语言访问检查。
//            Unsafe UNSAFE1 = con.newInstance(null);
//            System.out.println(UNSAFE1);

            
            //操作对象
            User user = new User();
            Field filed = user.getClass().getDeclaredField("age");
            long ageOffset = unsafe.objectFieldOffset(filed);
            unsafe.putInt(user, ageOffset, 10);
            System.out.println(user.getAge());
            Field name = user.getClass().getDeclaredField("name");
            long nameOffset = unsafe.objectFieldOffset(name);
            unsafe.putChar(user, nameOffset, '2');
            System.out.println(user.getName());

            //操作byte数组
//            byte[] data = new byte[10];
//            System.out.println(Arrays.toString(data));
//            long  byteArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
//            System.out.println(byteArrayBaseOffset);
//            unsafe.putByte(data, byteArrayBaseOffset, (byte) 1);
//            unsafe.putByte(data, byteArrayBaseOffset + 5, (byte) 5);
//            System.out.println(Arrays.toString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void isTrust() {
		//在Java中，如果一个对象的classLoader等于null，这就说明这个对象的类加载器是boostrap classloader，
		//那么如果类是由bootstrap classloader加载的话，那么它就是受信任的代码。
		//可以直接打印String的ClassLoader来检测一下结论是否正确：
		System.out.println(String.class.getClassLoader());		
	}
	
	class User{
		private  int age;
		private  char  name;
		
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public char getName() {
			return name;
		}
		public void setName(char name) {
			this.name = name;
		}
	}
}
