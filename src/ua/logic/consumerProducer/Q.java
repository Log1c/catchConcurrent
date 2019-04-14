package ua.logic.consumerProducer;

public class Q {
    int n;
    boolean valueSet = false;

    synchronized int get() {
        while (!valueSet)
        try {
            wait();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException intercept");
        }

        System.out.println("recd " + n);
        valueSet = false;
        notify();
        return n;
    }

    synchronized void put(int n) {
        while (valueSet)
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException intercept");
            }

            this.n = n;
            valueSet = true;
            System.out.println("send: " + n);
            notify();
    }
}
