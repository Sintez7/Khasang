package main;
/*
 * Абстрактный класс, который должны наследовать все игры для корректной работы
 * Не имеет конструктора по-умолчанию, чтобы исключить возможность создания игры без имени
 * Имплеминтирует интерфейс main.GameStrategy для обязательного наличия у конкретной игры метода runGame()
 */

public abstract class Game implements GameStrategy {
    private String gameName;

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public String getName() {
        return gameName;
    }

    abstract public int getGameID();
}
