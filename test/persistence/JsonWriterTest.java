package persistence;

import model.DifficultWords;
import model.DifficultWordsList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterIsInvalid() {
        try {
            DifficultWordsList dwlist = new DifficultWordsList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDifficultWordsList() {
        try {
            DifficultWordsList dwlist = new DifficultWordsList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDifficultWordsList.json");
            writer.open();
            writer.write(dwlist);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDifficultWordsList.json");
            dwlist = reader.read();
            assertEquals(0, dwlist.getListOfDifficultWords().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalCase() {
        try {
            DifficultWordsList dwlist = new DifficultWordsList();
            dwlist.addWord(new DifficultWords("watch"));
            dwlist.addWord(new DifficultWords("baller"));

            JsonWriter writer = new JsonWriter("./data/testWriterNormalCase.json");
            writer.open();
            writer.write(dwlist);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalCase.json");
            dwlist = reader.read();
            List<DifficultWords> list  = dwlist.getListOfDifficultWords();
            assertEquals(2, list.size());
            checkWord("watch", list.get(0));
            checkWord("baller", list.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
