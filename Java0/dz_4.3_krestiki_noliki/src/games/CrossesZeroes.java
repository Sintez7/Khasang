package games;//package games;
//import GameLibrary;

public class CrossesZeroes extends Game {

    private int gameID;

    public CrossesZeroes(GameLibrary gameLibrary) {
        super("Crosses-Zeroes");
        gameID = gameLibrary.register(this);    // Получаем ID для этой игры и сохраняем его для дальнейшей работы
    }

    @Override
    public void runGame() {
        System.out.println(getName() + " is running!");
    }

    public int getGameID() {
        return gameID;
    }
}
