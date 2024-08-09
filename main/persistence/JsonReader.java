package persistence;

import model.DifficultWords;
import model.DifficultWordsList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//CITATION: methods are largely inspired by methods in JsonReader's class from CPSC 210's JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: creates an instance of JsonReader that reads a file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a DifficultWordsList from file and returns it;
    // throws IOException if an error when reading the data from file
    public DifficultWordsList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDWList(jsonObject);
    }

    // EFFECTS: reads the saved file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses DifficultWordsList from JSON object and returns it
    private DifficultWordsList parseDWList(JSONObject jsonObject) {
        DifficultWordsList dwlist = new DifficultWordsList();
        addDifficultWords(dwlist, jsonObject);
        return dwlist;
    }

    // MODIFIES: dwlist
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addDifficultWords(DifficultWordsList dwlist, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfDifficultWords");
        for (Object json : jsonArray) {
            JSONObject nextWord = (JSONObject) json;
            addDifficultWord(dwlist, nextWord);
        }
    }

    // MODIFIES: dwlist
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addDifficultWord(DifficultWordsList dwlist, JSONObject jsonObject) {
        String spelling = jsonObject.getString("word");
        DifficultWords word = new DifficultWords(spelling);
        dwlist.addWord(word);
    }

}
