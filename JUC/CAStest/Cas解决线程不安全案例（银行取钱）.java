public class Main {
    static int num = 0;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Start...");
        Account accountUnsafe = new AccountCas(10000);
        Account.demo(accountUnsafe);
    }
}

class AccountCas implements Account {
    private AtomicInteger balance;

    public AccountCas(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return this.balance.get();
    }

    /**
     * 关键代码
     * @param withdrawAmount
     */
    @Override
    public void withdraw(Integer withdrawAmount) {
        while (true) {
            // 余额最新值
            Integer prev = getBalance();
            // 修改金额
            Integer after = prev - withdrawAmount;
            boolean flag = this.balance.compareAndSet(prev, after);
            if (flag) {
                break;
            }
            System.out.println("多次尝试...");
        }
    }
}

class AccountUnsafe implements Account {
    private int balance;

    public AccountUnsafe(int balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public void withdraw(Integer withdrawAmount) {
        this.balance -= withdrawAmount;
    }
}

interface Account {
    Integer getBalance();

    void withdraw(Integer withdrawAmount);

    static void demo(Account account) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            threadList.add(new Thread(() -> {
                account.withdraw(2);
            }));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(
                t -> {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        System.out.println("取款后余额：" + account.getBalance());
    }
}