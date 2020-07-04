package app.model.menuOptions;

import app.model.MenuOption;

public class WithdrawMoney implements MenuOption {

    private final Type type = Type.WITHDRAW;
    private final String description = "Withdraw money";

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
