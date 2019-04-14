package ua.logic.philosophers.synchronizedHelp;

public class Philosopher extends Thread {
    private static final Object mutex = new Object();

    private Waiter waiter;
    private Fork leftFork;
    private Fork rightFork;
    private Fork mustLeftFork;
    private Fork mustRightFork;

    public void think() {
        synchronized (mutex) {
            try {
                System.out.println(getName() + " think");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Fork getRightFork() {
        return rightFork;
    }

    public Fork getLeftFork() {
        return leftFork;
    }

    public void setRightFork(Fork fork) {
        if ((fork == null) && (rightFork != null)) {
            waiter.putFork(rightFork);
        }

        rightFork = fork;
    }

    public void setLeftFork(Fork fork) {
        synchronized (mutex) {
            if ((fork == null) && (leftFork != null)) {
                waiter.putFork(leftFork);
                System.out.println("wake up");
                mutex.notifyAll();
            }

            leftFork = fork;
        }
    }

    public Philosopher(String name, Waiter waiter, Fork mustLeftFork, Fork mustRightFork) {
        super(name);
        this.waiter = waiter;
        this.mustLeftFork = mustLeftFork;
        this.mustRightFork = mustRightFork;
    }

    @Override
    public void run() {
        //waiter.sit(this);
        for(int i = 0; i<5; i++) {

            takeLeftFork();
            while (leftFork == null) {
                synchronized (mutex) {
                    System.out.println(getName() + " no left fork");
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    takeLeftFork();
                }
            }

            takeRightFork();
            while (rightFork == null) {
                synchronized (mutex) {
                    System.out.println(getName() + " no right fork");
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    takeRightFork();
                }
            }
            eat();
        }
    }

    public void takeLeftFork() {
        if (getLeftFork() == null) {
            setLeftFork(waiter.getFork(this, mustLeftFork));
        }
    }

    public void takeRightFork() {
        if (leftFork == null) {
            return;
        }

        if (getRightFork() == null) {
            setRightFork(waiter.getFork(this, mustRightFork));
        }
    }

    public synchronized void eat() {
        if ((leftFork == null) || (rightFork == null)) {
            System.out.println("no fork");
            return;
        }

        System.out.println(getName() + " eat");

        setLeftFork(null);
        setRightFork(null);
        think();
    }

    public static void main(String[] args) {
        Waiter waiter = new Waiter();
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        Philosopher A = new Philosopher("A", waiter, fork1, fork5);
        Philosopher B = new Philosopher("B", waiter, fork2, fork1);
        fork1.setOwner(B);

        Philosopher C = new Philosopher("C", waiter, fork3, fork2);
        Philosopher D = new Philosopher("D", waiter, fork4, fork3);
        Philosopher E = new Philosopher("E", waiter, fork5, fork4);

        A.start();
        B.start();
        //C.start();
        //D.start();
        //E.start();
    }
}
