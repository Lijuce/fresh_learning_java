package com.company;
// 2021年4月12日
// 静态代理模式
// 真实对象和代理对象都要实现同一接口
// 好处：有助于真实对象专注于自己应该完成的任务
public class StaticProxy {
    public static void main(String[] args) {
        You you = new You();

        Restaurant res = new Restaurant(you);
        res.Eat();
    }
}

// 上级接口
interface Food{
    void Eat();
}

class You implements Food{
    @Override
    public void Eat(){
        System.out.println("吃海鲜咯...");
    }
}

// 代理角色
class Restaurant implements Food{
    private final Food food;

    public Restaurant(Food food){
        this.food = food;
    }

    @Override
    public void Eat(){
        before();
        this.food.Eat();
        after();
    }

    public void before(){
        System.out.println("准备开吃啦...");
    }
    public void after(){
        System.out.println("我吃饱啦!!");
    }

}