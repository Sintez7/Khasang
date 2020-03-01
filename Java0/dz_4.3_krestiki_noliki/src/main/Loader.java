package main;

public class Loader {
    private static GameLibrary gameLibrary;  // Создаём объект main.GameLibrary, который будет содержать в себе игры

    public static void main(String[] args) {
        startupInitialize();        // Блок для предварительной инициализации необходимых модулей
        GameStrategy strategy = gameLibrary.pickGame();     // делегируем выбор игры библиотеке
        strategy.runGame();         //  запускаем выбранную игроком игру
    }

    private static void startupInitialize() {
        gameLibrary = new GameLibrary();
        createGames();
    }

    /*
     * На текущий момент игры попадают в библиотеку после создания объекта игры.
     * Т.к. ссылка на созданый объект помещается в коллекцию в main.GameLibrary,
     * сборщик мусора не уничтожит созданную игру, но в Loader'е не будет лишней копии,
     * ведь по окончанию работы метода локальная переменная будет стёрта
     * Для автоматического добавления игр, надо использовать рефлексию,
     * но пока этого я делать не буду, пусть пока будет так
     */
    private static void createGames() {
        games.CrossesZeroes cz = new games.CrossesZeroes(gameLibrary);
    }
}
