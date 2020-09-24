package app;

public class TestServer1StateMachine {

    private State currentState = new FirstState();
    private State prevState = null;

    public void execute() {
        prevState = currentState;
        currentState = currentState.execute();

    }

    private class State {
        protected State execute() {
            System.err.println("default execute method, return null");
            return null;
        }
    }

    private class FirstState extends State {
        @Override
        protected State execute() {
            System.err.println("FirstState work");
            return new SecondState();
        }
    }

    private class SecondState extends State {
        @Override
        protected State execute() {
            System.err.println("SecondState work");
            return new ThirdState();
        }
    }

    private class ThirdState extends State {
        @Override
        protected State execute() {
            System.err.println("ThirdState work");
            return new FourthState();
        }
    }

    private class FourthState extends State {
        @Override
        protected State execute() {
            System.err.println("FourthState work");
            return new FirstState();
        }
    }
}
