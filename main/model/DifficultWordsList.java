package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//CITATION: Json Methods inspired by CPSC 210's JsonSerializationDemo
//Represents a list of DifficultWords which the user can view, add a word, and remove a word
//This list would also get be the one used in main game to test the user on the spelling accuracy of these words
public class DifficultWordsList implements Writable {
    private final ArrayList<DifficultWords> listOfDifficultWords;

    //EFFECTS: creates a new list of Difficult Words
    public DifficultWordsList() {
        listOfDifficultWords = new ArrayList<>();
    }

    //REQUIRES: difficultWord.length() != 0 && difficultWord cannot contain whitespace, numbers, capslock
    //MODIFIES: this
    //EFFECTS: adds the given word into listOfDifficultWords
    public void addWord(DifficultWords difficultWord) {
        EventLog.getInstance().logEvent(new Event("added " + difficultWord.getSpelling() + " to list"));
        listOfDifficultWords.add(difficultWord);
    }

    //REQUIRES: listOfDifficultWords.contains(difficultWord)
    //MODIFIES: this
    //EFFECTS: removes the given word into listOfDifficultWords
    public void removeWord(DifficultWords difficultWord) {
        EventLog.getInstance().logEvent(new Event("removed " + difficultWord.getSpelling() + " from list"));
        listOfDifficultWords.remove(difficultWord);
    }

    public ArrayList<DifficultWords> getListOfDifficultWords() {
        return listOfDifficultWords;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfDifficultWords", listOfDifficultWordsToJson());
        return json;
    }

    // EFFECTS: returns DifficultWords in this DifficultWordsList as a JSON array
    private JSONArray listOfDifficultWordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DifficultWords dw : listOfDifficultWords) {
            jsonArray.put(dw.toJson());
        }

        return jsonArray;
    }

}
