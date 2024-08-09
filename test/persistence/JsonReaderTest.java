package persistence;

import model.DifficultWords;
import model.DifficultWordsList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderDoesNotExistFile() {
        JsonReader reader = new JsonReader("./data/doesNotExist.json");
        try {
            DifficultWordsList dwlist = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDifficultWordsList() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyDifficultWordsList.json");
        try {
            DifficultWordsList dwlist = reader.read();
            assertEquals(0, dwlist.getListOfDifficultWords().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNormalCase() {
        JsonReader reader = new JsonReader("./data/testWriterNormalCase.json");
        try {
            DifficultWordsList dwlist = reader.read();
            List<DifficultWords> list = dwlist.getListOfDifficultWords();
            assertEquals(2, list.size());
            checkWord("watch", list.get(0));
            checkWord("baller", list.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }




}
