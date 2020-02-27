public class Loader {
    private static GameLibrary gameLibrary;  // Создаём объект GameLibrary, который будет содержать в себе игры

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
     * Т.к. ссылка на созданый объект помещается в коллекцию в GameLibrary,
     * сборщик мусора не уничтожит созданную игру, но в Loader'е не будет лишней копии,
     * ведь по окончанию работы метода локальная переменная будет стёрта
     * По-хорошему, здесь можно использовать паттерн "Подписчик",
     * чтобы автоматически инициализировать игры без необходимости добавления кода в этот класс.
     */
    private static void createGames() {
        games.CrossesZeroes cz = new games.CrossesZeroes(gameLibrary);
    }
}
