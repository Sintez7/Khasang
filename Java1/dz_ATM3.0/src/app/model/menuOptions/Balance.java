package app.model.menuOptions;

import app.model.MenuOption;

public class Balance implements MenuOption {

    private final String description = "Ask for current balance";
    private final Type type = Type.BALANCE;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public MenuOption getOption() {
        return this;
    }
}
