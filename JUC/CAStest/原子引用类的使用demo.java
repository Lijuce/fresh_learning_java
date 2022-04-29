public class Main {
    static int num = 0;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Start...");
        DecimalAccountCas decimalAccountCas = new DecimalAccountCas("2500");
        DecimalAccount.demo(decimalAccountCas);
    }
}

class DecimalAccountCas implements DecimalAccount {
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(String amount) {
        this.balance = new AtomicReference<>(new BigDecimal(amount));
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}
interface DecimalAccount {
    BigDecimal getBalance();

    void withdraw(BigDecimal amount);

    static void demo(DecimalAccount account) {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new Thread(() -> {
                account.withdraw(new BigDecimal("2.5"));
//                account.withdraw(BigDecimal.TEN);
            }));
        }
        list.forEach(Thread::start);
        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }
}