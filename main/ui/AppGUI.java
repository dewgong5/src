package ui;

import model.DifficultWords;
import model.DifficultWordsList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//represents the AppGUI with certain methods that enables GUI
public class AppGUI extends JFrame {
    private static final String JSON_STORE = "./data/difficultwordslist.json";
    private JFrame frame;
    private JFrame imageFrame;
    private JPanel textFieldPanel;
    private JLabel titlabel;
    private JPanel titlePanel;
    private JButton playButton;
    private JButton cusButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton loadButton;
    private JButton imgButton;
    private DifficultWordsList list;
    private Color customColor;
    private JLabel label;
    private JTextField textField;
    private JTextField remove;
    private JFrame removeF;
    private JButton removeButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //MODIFIES: this
    //EFFECTS: the constructor, instantiates several important fields and call methods to enable button clicking,
    // make sure the frame is visible
    public AppGUI() {
        initial();
        customColor = new Color(234, 226, 183);
        label = new JLabel("No words have been added!");
        textField = new JTextField(10);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        remove = new JTextField(10);
        imageFrame = new JFrame();
        functionUtil();
        imageFunc();
        startFrame();
        frame.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: instantiates a few key fields
    public void initial() {
        list = new DifficultWordsList();
        frame = new JFrame();
        removeF = new JFrame();
        playButton = new JButton("Play");
        playButton.setBackground(Color.white);
        cusButton = new JButton("Customize");
        cusButton.setBackground(Color.white);
        saveButton = new JButton("Save");
        saveButton.setBackground(Color.white);
        loadButton = new JButton("Load");
        loadButton.setBackground(Color.white);
        exitButton = new JButton("Exit");
        exitButton.setBackground(Color.white);
        imgButton = new JButton("Image");
        imgButton.setBackground(Color.white);
        removeButton = new JButton("Back");
        removeButton.setBackground(Color.white);
        cusGUI();


    }

    //MODIFY: this
    //EFFECTS: creates a frame with a certain size and title, creates title.
    public void startFrame() {
        frame.setSize(400, 600);
        removeF.setSize(400, 200);
        imageFrame.setSize(400, 600);

        frame.setTitle("Autocorect");
        frame.setResizable(false);
        removeF.setTitle("Autocorect");
        removeF.setResizable(false);

        titlabel = new JLabel();
        titlabel.setText("Welcome to Autocorect!");
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(customColor);
        titlePanel.add(titlabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                switchToHome();
            }
        });
        setFocus();
        startButton();
    }

    //MODIFIES: this
    //EFFECTS: sets the focus paint for buttons
    public void setFocus() {
        playButton.setFocusPainted(false);
        cusButton.setFocusPainted(false);
        saveButton.setFocusPainted(false);
        loadButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);
        imgButton.setFocusPainted(false);
        removeButton.setFocusPainted(false);
    }

    //MODIFY: this
    //EFFECTS: intialize buttons with the right ratios and centered layout, calls the customize GUI
    public void startButton() {
        JPanel playPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.Y_AXIS));
        playPanel.setBackground(customColor);

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playPanel.add(playButton);
        playPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playPanel.add(cusButton);
        playPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playPanel.add(saveButton);
        playPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playPanel.add(loadButton);
        playPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playPanel.add(exitButton);
        playPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playPanel.add(imgButton);
        frame.add(playPanel, BorderLayout.CENTER);

    }

    //MODIFY: this
    //EFFECTS: overwrites actionPerformed for save, load, and exit
    public void functionUtil() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDifficultWordsList();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDifficultWordsList();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        });

    }

    //EFFECTS: prints all the events from the event log
    public void printLog(EventLog el) {
        for (Event e: el) {
            System.out.println(e);
        }
    }


    //MODIFY: this
    //EFFECTS: enables image to be displayed upon click and closed upon another click
    public void imageFunc() {
        imgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imagePath = "src/main/image/snorlax.jpg";
                ImageIcon icon = new ImageIcon(imagePath);
                JLabel label = new JLabel(icon);
                imageFrame.setSize(400, 600);
                JButton closeButton = new JButton("Close");
                closeButton.setBackground(Color.white);
                closeButton.setFocusPainted(false);
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imageFrame.dispose();
                    }
                });

                JPanel panel = new JPanel(new BorderLayout());
                panel.add(label, BorderLayout.CENTER);
                panel.add(closeButton, BorderLayout.SOUTH);
                imageFrame.getContentPane().add(panel);
                imageFrame.setVisible(true);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: switches to the screen when cusButton is pressed
    public void switchToCusScreen() {
        frame.getContentPane().removeAll();
        titlabel.setText("Here is a list of already added words!");
        frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
        removeWordGUI();
        addWordGUI();
        extensionCus();
        frame.revalidate();
        frame.repaint();
    }

    //MODIFIES: this
    //EFFECTS: switches to the home screen when back is pressed
    public void switchToHome() {
        frame.getContentPane().removeAll();
        removeF.setVisible(false);
        startFrame();
        frame.revalidate();
        frame.repaint();
    }

    //MODIFIES: this
    //EFFECTS: adds certain components for customize screen
    public void cusGUI() {

        cusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToCusScreen();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: adds a few effects to the panel
    public void panelAdd(JPanel t) {
        t.setBackground(customColor);
        t.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
        t.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    //MODIFIES: this
    //EFFECTS: adds a bunch of panels for the Customization screen and calls customization GUIs
    public void extensionCus() {
        textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAdd(textFieldPanel);
        textField.setMaximumSize(new Dimension(100, 20));
        remove.setMaximumSize(new Dimension(100, 20));
        JLabel ad = new JLabel("Add your words below!");
        ad.setAlignmentX(Component.CENTER_ALIGNMENT);
        textFieldPanel.add(ad);
        textFieldPanel.add(textField);
        JLabel rem = new JLabel("Remove your words below!");
        rem.setAlignmentX(Component.CENTER_ALIGNMENT);
        textFieldPanel.add(rem);
        textFieldPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textFieldPanel.add(remove);
        textFieldPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel label2 = new JLabel("Type yours with no space, numbers, or capitals!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        textFieldPanel.add(removeButton);
        textFieldPanel.add(label2);
        textFieldPanel.add(label);
        frame.add(textFieldPanel, BorderLayout.CENTER);

    }

    //MODIFIES: this
    //EFFECTS: enables button to add words and display the live changes
    public void addWordGUI() {
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String var = textField.getText();
                StringBuilder completeList = new StringBuilder();
                if (!var.equals("")) {
                    list.addWord(new DifficultWords(var));
                    for (DifficultWords w : list.getListOfDifficultWords()) {
                        completeList.append(list.getListOfDifficultWords().indexOf(w) + 1).append(". ");
                        completeList.append(w.getSpelling()).append("<br>");
                    }
                    label.setText("<html>" + completeList + "</html>");
                    textField.setText("");
                }
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: checks whether the list has any content and respond accordingly
    public void checkFirst() {
        if (list.getListOfDifficultWords().size() != 0) {
            StringBuilder completeList = new StringBuilder();
            for (DifficultWords w : list.getListOfDifficultWords()) {
                completeList.append(list.getListOfDifficultWords().indexOf(w) + 1).append(". ");
                completeList.append(w.getSpelling()).append("<br>");
            }
            label.setText("<html>" + completeList + "</html>");
        }
    }


    //MODIFIES:this
    //EFFECTS: creates textfield that allows user to delete added words
    public void removeWordGUI() {
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String var = remove.getText();
                if (!var.equals("")) {
                    for (DifficultWords w : list.getListOfDifficultWords()) {
                        if (w.getSpelling().equals(var)) {
                            list.removeWord(w);
                            StringBuilder completeList = new StringBuilder();

                            for (DifficultWords wo : list.getListOfDifficultWords()) {
                                completeList.append(list.getListOfDifficultWords().indexOf(wo) + 1).append(". ");
                                completeList.append(wo.getSpelling()).append("<br>");
                            }
                            label.setText("<html>" + completeList + "</html>");

                            break;
                        }
                    }
                }
            }
        });







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


}


