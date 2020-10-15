package app;

import app.shared.GamePackage;

public class GameStateMachine {

    private State state;

    public GameStateMachine() {
        state = new Starting();
    }

    public static abstract class State {
        abstract State execute();
        abstract void receivePlayerInput();

        public GamePackage currentGameData() {
            return collectGameData();
        }

        private GamePackage collectGameData() {
            return null;
        }

    }

    public static class Starting extends State {

        @Override
        State execute() {
            return new Ready();
        }

        @Override
        void receivePlayerInput() {
            System.err.println("illegal state - input ignored");
        }
    }

    public static class Initialize extends State {

        @Override
        State execute() {
            return null;
        }

        @Override
        void receivePlayerInput() {

        }
    }

    public static class Ready extends State {

        @Override
        State execute() {
            return new Playing();
        }

        @Override
        void receivePlayerInput() {
            System.err.println("input ignored");
        }
    }

    public static class Playing extends State {

        @Override
        State execute() {
            return null;
        }

        @Override
        void receivePlayerInput() {

        }
    }

    public static class Ended extends State {

    }

    public static class Terminate extends State {

    }
}
