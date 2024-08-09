package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

//CITATION: JSON methods inspired by CPSC 210's JsonSerializationDemo
//Represents an individual difficult word to be used in the game
// Each have a correct spelling of a word, the level of difficulty, and typing progress
public class DifficultWords implements Writable {
    private String spelling;
    private int difficultyLevel;
    private boolean isTyped;
    private String leftToType;
    private String spellingRandomized;

    //MODIFIES: this
    //EFFECTS: creates a new difficult word instance with a spelling, level of difficulty, typing progress
    // and randomized version of the spelling (still empty as no randomization has occurred)
    public DifficultWords(String word) {
        this.spelling = word;
        checkDifficulty();
        isTyped = false;
        leftToType = spelling;
        spellingRandomized = "";


    }

    //MODIFIES: this
    //EFFECTS: checks the difficulty of a word, sets the difficultyLevel 2 if length of string is bigger than 10
    // and 1 otherwise
    public void checkDifficulty() {
        if (spelling.length() > 10) {
            this.difficultyLevel = 2;
        } else {
            this.difficultyLevel = 1;
        }
    }

    //REQUIRES: leftToType.length() > 0
    //MODIFIES: this
    //EFFECTS: returns the next char of spelling to be typed
    public String getNextChar() {
        return leftToType.substring(0,1);

    }

    //REQUIRES: input not be an empty string and input.length() = 1
    //MODIFIES: this
    //EFFECTS: types a single character
    // if true, updates the remaining characters to type
    // if false, don't update the remaining characters
    public void typeChar(String input) {
        if (input.equals(getNextChar())) {
            leftToType = leftToType.substring(1);
        }

    }

    public String getSpelling() {
        return spelling;
    }

    public int getLevel() {
        return difficultyLevel;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public boolean getStatus() {
        return isTyped;
    }

    public String getLeftToType() {
        return leftToType;
    }

    //MODIFIES: this
    //EFFECTS; sets the progress of typing to be true
    public void setAlreadyTyped() {
        isTyped = true;
    }

    //CITATIONS:
    // - https://www.geeksforgeeks.org/collections-shuffle-method-in-java-with-examples/
    //MODIFIES: this
    //EFFECTS: randomizes the spelling of a word
    public void randomizeWord() {
        spellingRandomized = "";
        ArrayList<String> arrayOneString = new ArrayList<>();
        for (int i = 0;i != spelling.length();i++) {
            String c = spelling.substring(i, i + 1);
            arrayOneString.add(c);
        }

        Collections.shuffle(arrayOneString);

        for (String s: arrayOneString) {
            spellingRandomized = spellingRandomized + s;
        }
    }

    public String getSpellingRandomized() {
        return spellingRandomized;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("word", spelling);
        return json;
    }


}
