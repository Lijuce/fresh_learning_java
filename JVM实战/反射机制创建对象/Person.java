package com;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName Person
 * @Description 证明类仅加载一次，并演示通过反射方式创建对象
 * @Author Lijuce_K
 * @Date 2022/3/2 20:14
 * @Version 1.0
 **/
public class Person {
    String name;
    int age;
    public static int staticVariable = 1;

    static {
        staticVariable = 2;
    }

    {
        age = 18;
    }

    public Person() {
        age = 20;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        // Class.forName 方式获取对象
        Class cls1 = Class.forName("com.Person");
        System.out.println(cls1.getClasses());

        // 通过类名属性 class 获取
        Class cls2 = Person.class;
        System.out.println(cls2.getClasses());

        // getClass方法获取
        Person person = new Person();
        Class<? extends Person> cls3 = person.getClass();
        System.out.println(cls3.getClasses());

        // 判断三个类是否相同(最终结果证明类仅加载一次)
        System.out.println(cls1 == cls2);
        System.out.println(cls1 == cls3);

        // 反射方式实例化对象
        Object cls = cls1.newInstance();
        Method getAge = cls.getClass().getMethod("getAge");
        System.out.println(getAge.invoke(cls));
    }
}
