package ui;

import model.DifficultWords;
import model.DifficultWordsList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


//CITATION: methods for saving Json files inspired by CPSC 210
public class AutocorerctApp {
    private static final String JSON_STORE = "./data/difficultwordslist.json";
    private Scanner input;
    private DifficultWordsList list;
    private boolean firstCustomization;
    private DifficultWordsList gameOrder;
    private int attempts;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: creates an AutocorectApp class and runs the app
    // instantiates several variables like list, firstCustomization, input, gameOrder, attempts
    public AutocorerctApp() throws FileNotFoundException {
        list = new DifficultWordsList();
        firstCustomization = true;
        input = new Scanner(System.in);
        gameOrder = new DifficultWordsList();
        attempts = 0;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        startApp();

    }

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: starts the app (including frame) and displays the Home section and takes user inputs,
    // user has to type 1,2, or 3
    protected void startApp() {

        displayHome();
        String userType = input.next();
        int response = Integer.parseInt(userType);
        produceResponse(response);

    }

    //MODIFIES: this
    //CITATION: displayHome method is inspired displayMenu method from UBC CPSC 210's TellerApp
    //EFFECTS: displays the home screen of the game, where the user can view their options,
    // checks whether this is the first customization or not (incase the user loaded data)
    private void displayHome() {
        firstCustomization = list.getListOfDifficultWords().size() == 0;

        System.out.println("=======================================================");
        System.out.println("      Welcome to Autocorect (Beta Version 1.0)");
        System.out.println("=======================================================");
        System.out.println("\n Let's see whether you can do without autocorrect :)");
        System.out.println("\t [1] Play");
        System.out.println("\t [2] Customization");
        System.out.println("\t [3] Save Current Level");
        System.out.println("\t [4] Load Past Level");
        System.out.println("\t [5] Exit");
        System.out.print("\n Please type a number to continue: ");


    }

    //REQUIRES: inputted value to be either 1,2, or 3
    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: moves the app into the appropriate section from either Play, Customization, or Exit
    private void produceResponse(int i) {
        if (i == 1) {
            System.out.println();
            playApp();
        } else if (i == 2) {
            System.out.println();
            displayCustomization();
        } else if (i == 3) {
            System.out.println();
            saveDifficultWordsList();
            startApp();
        } else if (i == 4) {
            System.out.println();
            loadDifficultWordsList();
            startApp();
        } else if (i == 5) {
            System.out.println();
            System.out.println("Quitting....");
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    // EFFECTS: saves the difficult words to file
    private void saveDifficultWordsList() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            System.out.println("Saved a list of " + list.getListOfDifficultWords().size() + " words to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads DifficultWordsList from file
    private void loadDifficultWordsList() {
        try {
            list = jsonReader.read();
            System.out.println("Load a list of " + list.getListOfDifficultWords().size() + " words from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: shows the customization screen consisting of a list of words added to the game
    // user is able to add more or remove certain words.
    private void displayCustomization() {
        if (firstCustomization) {
            firstCustomization();
        }

        System.out.println("Here is a list of already added words:");
        if (list.getListOfDifficultWords().size() == 0) {
            System.out.println("\n");
        }
        for (int i = 0; i < list.getListOfDifficultWords().size(); i++) {
            String printNum = Integer.toString(i + 1);
            System.out.println(printNum + ". " + list.getListOfDifficultWords().get(i).getSpelling());
        }

        displayCustomizationChoices();

    }

    //MODIFIES: this
    //EFFECTS: when the list of difficult words is blank, adds an initial first word
    // and word cannot contain space, numbers, or capital letters
    private void firstCustomization() {
        System.out.println("Spaces, numbers, and capslock are not allowed!");
        System.out.print("Please add your first word: ");
        String userType = input.next();
        list.addWord(new DifficultWords(userType));
        firstCustomization = false;

    }

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: shows the choices in customization and respond accordingly, user must click either 1,2,3
    private void displayCustomizationChoices() {
        System.out.println("Please choose your action:");
        System.out.println("\t [1] Add a word");
        System.out.println("\t [2] Remove a word");
        System.out.println("\t [3] Return to Home");
        System.out.print("Choose a number: ");
        String userType = input.next();
        int response = Integer.parseInt(userType);

        if (response == 1) {
            addNewWord();
        } else if (response == 2) {
            removeWord();
        } else {
            startApp();
        }

    }

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: adds a word into a list of difficult words,
    // word cannot contain space, numbers, or capital letters
    private void addNewWord() {
        System.out.println("Spaces, numbers, and capslock are not allowed!");
        System.out.print("Please add your word: ");
        String userType = input.next();
        list.addWord(new DifficultWords(userType));
        displayCustomization();
    }

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: removes a word into a list of difficult words,
    // word cannot contain space, numbers, or capital letters
    // tells the user when it detects invalid input
    private void removeWord() {
        DifficultWords wordRemoved = new DifficultWords(" ");

        System.out.println("With no typos, please type the word you'd like to delete");
        System.out.print("Please type your word: ");
        String userType = input.next();

        for (int i = 0; i < list.getListOfDifficultWords().size(); i++) {
            if (list.getListOfDifficultWords().get(i).getSpelling().equals(userType)) {
                wordRemoved = list.getListOfDifficultWords().get(i);
            }
        }

        if (!wordRemoved.getSpelling().equals(" ")) {
            list.removeWord(wordRemoved);
            System.out.println("Successfully deleted " + wordRemoved.getSpelling());
        } else {
            System.out.println("You made a mistake, make sure the word exist and no typos");
        }
        displayCustomization();
    }

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: shows the game level using the words in the customization
    private void playApp() {
        isItFirstCustomization();
        System.out.println("Ready or not, here it begins!");
        attempts = 0;
        playTheGame();
        startApp();

    }

    //CITATIONS: inspired by runTeller method in CPSC 210's TellerApp
    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: allows the user to play the game  level, the user can see the random words
    //, type their guesses and get feedback (right or wrong), it ends the round once the score is 10
    private void playTheGame() {
        boolean playGame = true;
        int score = 0;
        while (playGame) {
            createRandomOrder();
            String correctAnswer = pickRandomWord();
            System.out.print("Type your answer: ");
            String userType = input.next();
            if (userType.equals(correctAnswer)) {
                System.out.println("Correct! +1 score");
                score++;
            } else {
                System.out.println("Wrong! -1 score");
                if (score > 0) {
                    score--;
                }
            }
            attempts++;
            System.out.println("Your score is: " + score);
            if (score == 10) {
                printCongrats(attempts);
                playGame = false;
            }
        }
    }

    //EFFECTS: print the congratulations and how many attempts used
    private void printCongrats(int attempts) {
        System.out.println("Congratulations, you've finished one round!");
        System.out.println("You did it in " + attempts + " attempts!");
    }

    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: creates a random word order to be used in the game, runs randomization on each element
    private void createRandomOrder() {
        for (int i = 0; i < list.getListOfDifficultWords().size(); i++) {
            gameOrder.addWord(list.getListOfDifficultWords().get(i));
        }

        Collections.shuffle(gameOrder.getListOfDifficultWords());

        for (int i = 0; i < gameOrder.getListOfDifficultWords().size(); i++) {
            boolean isDuplicate = true;
            DifficultWords impactedWord = gameOrder.getListOfDifficultWords().get(i);
            if (impactedWord.getSpelling().length() == 1) {
                impactedWord.randomizeWord();
            }
            while (isDuplicate && impactedWord.getSpelling().length() > 1) {
                impactedWord.randomizeWord();
                if (!impactedWord.getSpellingRandomized().equals(impactedWord.getSpelling())) {
                    isDuplicate = false;
                }
            }

        }
    }

    //EFFECTS: picks a random DifficultWords element from the gameOrder and prints it
    private String pickRandomWord() {
        Random random = new Random();
        int randomInt = random.nextInt(gameOrder.getListOfDifficultWords().size());
        DifficultWords randomWord = gameOrder.getListOfDifficultWords().get(randomInt);
        System.out.println("Your word is: " + randomWord.getSpellingRandomized());
        return randomWord.getSpelling();
    }


    //MODIFIES: this, ListOfDifficultWords, DifficultWords
    //EFFECTS: checks whether the user has added any words to customization,
    // if not prompts the user to add a word
    private void isItFirstCustomization() {
        if (firstCustomization) {
            System.out.println("You can't play yet, go add a word first");
            displayCustomization();
        }
    }


}
