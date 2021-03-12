package ua.logic;

public class VolatileCatch {
    public static void main(String[] args) {
        new VolatileCatch().start();
    }

    volatile private boolean finish = false; //try to erase volatile

    public void start() {
        new Thread(flagChangerFlag).start();
        System.out.println("flagChangerFlag started");
        new Thread(eternalThread).start();
        System.out.println("eternalThread started");
    }

    Runnable flagChangerFlag = () -> {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        finish = true;
        System.out.println("flagChangerFlag finished");
    };

    Runnable eternalThread = () -> {
        //noinspection StatementWithEmptyBody
        while (!finish) {
            // eternal without volatile
        }
        System.out.println("eternalThread finished");
    };
}
