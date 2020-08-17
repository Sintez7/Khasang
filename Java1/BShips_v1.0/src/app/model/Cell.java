package app.model;

public class Cell {

    private Status status;
    private Ship occupier;

    public Cell() {
        this(Status.FREE);
    }

    public Cell (Status status) {
        this.status = status;
    }

    public void occupy(Ship ship) {
        if (status == Status.FREE) {
            occupier = ship;
            status = Status.OCCUPIED;
        }
    }

    public void hit() {
        status = Status.HIT;
    }

    public enum Status {
        FREE,
        OCCUPIED,
        HIT
    }
}
