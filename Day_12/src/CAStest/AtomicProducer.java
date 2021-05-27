package CAStest;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicProducer extends Thread{
    private AtomicCounter atomicCounter;

    public AtomicProducer(AtomicCounter atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < AtomicTest.LOOP; i++) {
            System.out.println("producer: " + atomicCounter.getInteger());
            atomicCounter.increment();
        }
    }
}
