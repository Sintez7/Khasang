package app;

// Это пин код.
// Пока это 4 символа.

public class PinCode {
    private final int PIN_LENGTH = 4;
    private final String VALID_SYM = "0123456789";
    private final char[] pinCode = new char[PIN_LENGTH];

    public PinCode(char[] newPinCode) {
        for (int index = 0; index < PIN_LENGTH; index++)
            this.pinCode[index] = (index < newPinCode.length) ? newPinCode[index] : '0';
    }

    public PinCode(String newPinCode) {
        this(newPinCode.toCharArray());
    }

    public boolean isValid() {
        int validSym = 0;
        for (int index = 0; index < PIN_LENGTH; index++)
            if (VALID_SYM.contains(Character.toString(pinCode[index]))) {
                validSym++;
            }
        return validSym == PIN_LENGTH;
    }

    public boolean isMatch(PinCode somePinCode) {
        int valid = 0;
        for (int index = 0; index < PIN_LENGTH; index++)
            if (pinCode[index] == somePinCode.pinCode[index]) {
                valid++;
            }
        return valid == PIN_LENGTH;
    }

}
