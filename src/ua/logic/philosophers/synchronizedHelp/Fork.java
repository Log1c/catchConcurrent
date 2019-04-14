package ua.logic.philosophers.synchronizedHelp;

public class Fork {
    private Philosopher owner;

    public Philosopher getOwner() {
        return owner;
    }

    public void setOwner(Philosopher owner) {
        this.owner = owner;
    }
}
