package ex1;

import java.io.Serializable;

public class CustomMessageObj implements Serializable {

    int[] m = new int[4];

    public CustomMessageObj() {
        for(int i = 0; i < m.length; i++) {
            m[i] = (int) (Math.random() * 10);
        }
    }

    public int[] getM() {
        return m;
    }
}
