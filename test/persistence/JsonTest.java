package persistence;

import model.DifficultWords;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWord(String word, DifficultWords dw) {
        assertEquals(word, dw.getSpelling());
    }

}

