package ua.logic.philosophers.synchronizedHelp;

public class Waiter {
//    private static Philosopher philosopher1;
//    private static Philosopher philosopher2;
//    private static Philosopher philosopher3;
//    private static Philosopher philosopher4;
//    private static Philosopher philosopher5;
//
//    private static Fork fork1;
//    private static Fork fork2;
//    private static Fork fork3;
//    private static Fork fork4;
//    private static Fork fork5;

    public synchronized Fork getFork(Philosopher philosopher, Fork fork) {
        if ((fork.getOwner() != null) && (fork.getOwner() != philosopher)) {
            System.out.println("fork busy, owner " + fork.getOwner());

            return null;
        }

        fork.setOwner(philosopher);
        return fork;
    }

    public synchronized void putFork(Fork fork) {
        fork.setOwner(null);

        //notifyAll();
        System.out.println("wake up");
    }

//    public void sit(Philosopher philosopher) {
//        if (philosopher1 == null) {
//            philosopher1 = philosopher;
//        } else if (philosopher2 == null) {
//            philosopher2 = philosopher;
//        } else if (philosopher3 == null) {
//            philosopher3 = philosopher;
//        } else if (philosopher4 == null) {
//            philosopher4 = philosopher;
//        } else if (philosopher5 == null) {
//            philosopher5 = philosopher;
//        } else {
//            System.out.println("table is full");
//        }
//    }
}
