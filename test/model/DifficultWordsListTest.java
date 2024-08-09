package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DifficultWordsListTest {
    private DifficultWordsList dwlist;
    private DifficultWords dw1;
    private DifficultWords dw2;
    private DifficultWords dw3;
    private DifficultWords dw4;

    @BeforeEach
    public void setUp() {
        dwlist = new DifficultWordsList();
        dw1 = new DifficultWords("abc");
        dw2 = new DifficultWords("HelloWorld!!!");
        dw3 = new DifficultWords("mAthemaTics");
        dw4 = new DifficultWords("californiaSushiRoll");

    }

    @Test
    public void testConstructor() {
        assertEquals(0, dwlist.getListOfDifficultWords().size());
    }

    @Test
    public void addWordSingle() {
        dwlist.addWord(dw1);
        assertEquals(dw1, dwlist.getListOfDifficultWords().get(0));
        assertEquals(1, dwlist.getListOfDifficultWords().size());

    }

    @Test
    public void addWordMultiple() {
        dwlist.addWord(dw1);
        assertEquals(dw1, dwlist.getListOfDifficultWords().get(0));
        assertEquals(1, dwlist.getListOfDifficultWords().size());
        dwlist.addWord(dw2);
        dwlist.addWord(dw3);
        assertEquals(dw2, dwlist.getListOfDifficultWords().get(1));
        assertEquals(dw3, dwlist.getListOfDifficultWords().get(2));
        assertEquals(3, dwlist.getListOfDifficultWords().size());
    }

    @Test
    public void removeWordSingle() {
        dwlist.addWord(dw1);
        assertEquals(dw1, dwlist.getListOfDifficultWords().get(0));
        assertEquals(1, dwlist.getListOfDifficultWords().size());
        dwlist.removeWord(dw1);
        assertFalse(dwlist.getListOfDifficultWords().contains(dw1));
        assertEquals(0, dwlist.getListOfDifficultWords().size());
    }

    @Test
    public void removeWordMultiple() {
        assertEquals(0, dwlist.getListOfDifficultWords().size());
        dwlist.addWord(dw2);
        assertEquals(1, dwlist.getListOfDifficultWords().size());
        assertTrue(dwlist.getListOfDifficultWords().contains(dw2));
        dwlist.addWord(dw1);
        dwlist.removeWord(dw2);
        assertEquals(1, dwlist.getListOfDifficultWords().size());
        assertFalse(dwlist.getListOfDifficultWords().contains(dw2));
        dwlist.addWord(dw3);
        assertTrue(dwlist.getListOfDifficultWords().contains(dw3));
        dwlist.addWord(dw2);
        assertTrue(dwlist.getListOfDifficultWords().contains(dw2));
        dwlist.addWord(dw4);
        assertTrue(dwlist.getListOfDifficultWords().contains(dw4));
        assertEquals(4, dwlist.getListOfDifficultWords().size());
        dwlist.removeWord(dw3);
        assertFalse(dwlist.getListOfDifficultWords().contains(dw3));
        dwlist.removeWord(dw4);
        assertFalse(dwlist.getListOfDifficultWords().contains(dw4));
        assertEquals(2, dwlist.getListOfDifficultWords().size());

    }


}
