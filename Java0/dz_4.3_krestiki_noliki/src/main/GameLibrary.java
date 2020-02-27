package main;

import java.util.ArrayList;

public class GameLibrary{

    private ArrayList<Game> gameLibrary;        // Собсно, объект содержащий ссылки на все игры

    GameLibrary () {
        gameLibrary = new ArrayList<>();        // Инициализируем коллекцию пустым листом, заполнением занимается метод register()
    }

    public int register(Game game) {
        return processRegistration(game);       // Здесь должна быть логика проверки игры на валидность
    }

    /*
     * Полученный экземпляр main.Game добавляется в коллекцию gameLibrary,
     * позиция в листе возвращается как gameID,
     * по которому мы будем вытаскивать игру из библиотеки
     */
    private int processRegistration (Game game) { //throws GameRegistrationException
        gameLibrary.add(game);
        return gameLibrary.size() - 1;
    }

    GameStrategy pickGame() {
        for (Game g : gameLibrary) {        // Отображаем весь список игр для пользователя
            System.out.println(g.getName());
        }
        return gameLibrary.get(askUserToPickGame());     // Делигируем контроль модулю ответственному за ввод пользователя
    }

    /*
     * Здесь должен быть модуль который будет принимать ввод пользователя
     * На текущий момент - болванка для теста
     */
    private int askUserToPickGame() {
        return 0;
    }
}
