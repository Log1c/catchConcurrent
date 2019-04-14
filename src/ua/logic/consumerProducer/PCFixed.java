package ua.logic.consumerProducer;

//Schildt book
public class PCFixed {
    public static void main(String[] args) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);

        System.out.println("Ctrl + c to break");
    }
}
