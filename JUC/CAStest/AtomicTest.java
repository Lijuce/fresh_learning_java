package CAStest;

public class AtomicTest {
    final static int LOOP = 10000;

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        AtomicProducer atomicProducer = new AtomicProducer(counter);
        AtomicConsumer atomicConsumer = new AtomicConsumer(counter);

        atomicProducer.start();
        atomicConsumer.start();

        atomicProducer.join();
        atomicConsumer.join();

        System.out.println(counter.getInteger());
    }
}
