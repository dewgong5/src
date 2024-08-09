package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultWordsTest {
    private DifficultWords dw1;
    private DifficultWords dw2;
    private DifficultWords dw3;
    private DifficultWords dw4;
    private DifficultWords dw5;
    private DifficultWords dw6;


    @BeforeEach
    public void setUp() {
        dw1 = new DifficultWords("abc");
        dw2 = new DifficultWords("HelloWorld");
        dw4 = new DifficultWords("GettingIntoComputerScience");
        dw5 = new DifficultWords("HelloWorldd");
        dw3 = new DifficultWords("abab");
        dw6 = new DifficultWords("z");


    }

    @Test
    public void testConstructor() {
        assertEquals("abab", dw3.getSpelling());
        assertEquals(1, dw3.getLevel());
        assertFalse(dw3.getStatus());
        assertEquals("abab", dw3.getLeftToType());
        assertEquals("", dw3.getSpellingRandomized());

    }

    @Test
    public void checkDifficultyVeryLong() {
        dw4.checkDifficulty();
        assertEquals(2, dw4.getLevel());
    }

    @Test
    public void checkDifficultyLong() {
        dw2.checkDifficulty();
        assertEquals(1, dw2.getLevel());
    }

    @Test
    public void checkDifficultyBarelyLong() {
        dw5.checkDifficulty();
        assertEquals(2, dw5.getLevel());
    }

    @Test
    public void checkDifficultyShort() {
        dw1.checkDifficulty();
        assertEquals(1, dw1.getLevel());

    }

    @Test
    public void getNextCharShort() {
        assertEquals("a", dw1.getNextChar());

    }

    @Test
    public void typeCharCorrectSmall() {
        dw1.typeChar("a");
        assertEquals("bc", dw1.getLeftToType());
    }

    @Test
    public void typeCharCorrectCapital() {
        dw2.typeChar("H");
        assertEquals("elloWorld", dw2.getLeftToType());
    }

    @Test
    public void typeCharWrong() {
        dw1.typeChar("z");
        assertEquals("abc", dw1.getLeftToType());
    }

    @Test
    public void typeCharWrongCapital() {
        dw2.typeChar("h");
        assertEquals("HelloWorld", dw2.getLeftToType());
    }

    @Test
    public void typeCharMultiple() {
        dw2.typeChar("k");
        assertEquals("HelloWorld", dw2.getLeftToType());
        assertEquals(10, dw2.getLeftToType().length());
        dw2.typeChar("H");
        dw2.typeChar("e");
        dw2.typeChar("l");
        assertEquals("loWorld", dw2.getLeftToType());
        dw2.typeChar("l");
        dw2.typeChar("o");
        dw2.typeChar("W");
        assertEquals("orld", dw2.getLeftToType());
        assertEquals(4, dw2.getLeftToType().length());
        dw2.typeChar("o");
        dw2.typeChar("r");
        dw2.typeChar("l");
        dw2.typeChar("d");
        assertEquals("", dw2.getLeftToType());
        assertEquals(0, dw2.getLeftToType().length());

    }

    @Test
    public void typeCharEnding() {
        dw1.typeChar("a");
        dw1.typeChar("b");
        assertEquals("c", dw1.getLeftToType());
        assertEquals(1, dw1.getLeftToType().length());
        dw1.typeChar("c");
        assertEquals("", dw1.getLeftToType());
        assertEquals(0, dw1.getLeftToType().length());
    }

    @Test
    public void setSpellingOfAWord() {
        dw1.setSpelling("doggy");
        assertEquals("doggy", dw1.getSpelling());
    }

    @Test
    public void randomizeWordLong() {
        dw4.randomizeWord();
        assertEquals(dw4.getSpelling().length(), dw4.getSpellingRandomized().length());

    }

    @Test
    public void randomizeWordSingle() {
        dw6.randomizeWord();
        assertTrue(dw6.getSpelling().equals(dw6.getSpellingRandomized()));
        assertEquals(dw6.getSpelling().length(), dw6.getSpellingRandomized().length());

    }

    @Test
    public void randomizeWordMultiple() {
        assertEquals("", dw4.getSpellingRandomized());
        dw4.randomizeWord();
        assertEquals(dw4.getSpelling().length(), dw4.getSpellingRandomized().length());
        assertFalse(dw4.getSpellingRandomized().equals(dw4.getSpelling()));
        dw4.randomizeWord();
        assertEquals(dw4.getSpelling().length(), dw4.getSpellingRandomized().length());
        assertFalse(dw4.getSpellingRandomized().equals(dw4.getSpelling()));
        dw5.randomizeWord();
        assertEquals(dw5.getSpelling().length(), dw5.getSpellingRandomized().length());
        assertFalse(dw5.getSpellingRandomized().equals(dw5.getSpelling()));

    }


    @Test
    public void setAlreadyTypedOfAWord() {
        assertFalse(dw1.getStatus());
        dw1.setAlreadyTyped();
        assertTrue(dw1.getStatus());
    }
}









