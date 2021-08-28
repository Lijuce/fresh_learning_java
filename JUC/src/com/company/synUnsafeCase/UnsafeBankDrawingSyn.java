package com.company.synUnsafeCase;

/**
 * 不安全案例之银行取钱
 */
public class UnsafeBankDrawingSyn {
    public static void main(String[] args) {
        Account account = new Account("生活费", 100);

        DrawingMoney xiaoming = new DrawingMoney(account, 50, "小明");
        DrawingMoney xiaohong = new DrawingMoney(account, 51, "小红");
        xiaoming.start();
        xiaohong.start();
    }
}

/**
 * 账户类
 */
class Account{
    String Name;
    int Balance;

    public Account(String name, int balance) {
        Name = name;
        Balance = balance;
    }
}

/**
 * 模拟银行取钱操作
 */
class DrawingMoney extends Thread{
    Account account;
    int drawingMoney;  // 取钱额度
    int nowMoney;  // 手里现金

    public DrawingMoney(Account account, int drawingMoney, String name){
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    /**
     * 取钱操作
     */
    @Override
    public void run(){
        // 代码块形式解决并发问题
        synchronized (account){
            if (account.Balance - drawingMoney < 0){
                System.out.println(Thread.currentThread().getName() + "余额不足，无法取钱");
                return;
            }

            // 模拟取钱延时
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 取钱成功
            account.Balance = account.Balance - drawingMoney;  // 扣除银行卡金额
            nowMoney = nowMoney + drawingMoney;

            System.out.println(this.getName() + "手里现金：" + nowMoney);
        }

    }
}


