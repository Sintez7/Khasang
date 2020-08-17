package app.model;

public class Field {

    private static final int DEFAULT_SIZE = 10;

    private Cell[][] field;
    private State state;

    public Field() {
        this(DEFAULT_SIZE);
    }

    public Field(int size) {
        state = State.READY;
        initField(size);
        state = State.PLAYABLE;
    }

    private void initField(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    public enum State {
        READY,
        PLAYABLE,
        OK,
        WAITING,
        LOSE
    }
}
