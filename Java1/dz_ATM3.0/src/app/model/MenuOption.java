package app.model;

public interface MenuOption {
    String getDescription();
    Type getType();
    MenuOption getOption();

    enum Type {
        WITHDRAW,
        BALANCE
    }
}
