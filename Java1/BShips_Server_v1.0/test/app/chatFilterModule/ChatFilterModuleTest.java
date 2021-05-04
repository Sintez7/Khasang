package app.chatFilterModule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatFilterModuleTest {

    @Test
    void startupTest() {
        ChatFilterModule cfm = new ChatFilterModule();
    }

    @Test
    void clearMessage() {
        ChatFilterModule cfm = new ChatFilterModule();
        System.err.println(cfm.clearMessage("123456"));
        System.err.println(cfm.clearMessage("12"));
        System.err.println(cfm.clearMessage("4321"));
        System.err.println(cfm.clearMessage("asdqwert"));
        System.err.println(cfm.clearMessage("rinbos"));
        System.err.println(cfm.clearMessage("123 123123456ewqqwe"));
    }
}