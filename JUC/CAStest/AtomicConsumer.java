package CAStest;

public class AtomicConsumer extends Thread{
    private AtomicCounter atomicCounter;

    public AtomicConsumer(AtomicCounter atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < AtomicTest.LOOP; i++) {
            System.out.println("consumer: " + atomicCounter.getInteger());
            atomicCounter.decrement();
        }
    }
}
