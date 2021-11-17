package MultiplicationSpeedTest;

import javax.swing.*;
import java.awt.*;

public class SpeedTestGUI{
    private final int SETTINGS_WINDOW_HEIGHT = 250;
    private final int SETTINGS_WINDOW_WIDTH = 250;
    private final int MAIN_WINDOW_HEIGHT = 420;
    private final int MAIN_WINDOW_WIDTH = 420;

    private SpeedTest test;

    public SpeedTestGUI(){
        createSettingsWindow();
    }

    private void createSettingsWindow(){
        //Create Frame and Panel
        JFrame settingsFrame = new JFrame("Multiplication Speed Test");
        settingsFrame.setSize(SETTINGS_WINDOW_WIDTH, SETTINGS_WINDOW_HEIGHT);
        settingsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel settingsPanel = new JPanel(new GridLayout(4, 1));

        //Create Settings Label
        JLabel settingsLabel = new JLabel("Select Settings", SwingConstants.CENTER);
        settingsPanel.add(settingsLabel);

        //Create Multiplier Dropdown Panel
        JPanel multiplierPanel = new JPanel();
        JLabel multiplierLabel = new JLabel("Multiplier: ");
        JComboBox<Integer> multiplierBox = new JComboBox<>();
        for (int i=1; i < 13; i++){
            multiplierBox.addItem(i);
        }
        multiplierPanel.add(multiplierLabel);
        multiplierPanel.add(multiplierBox);
        settingsPanel.add(multiplierPanel);

        //Create the Random and Sequential Radiobuttons
        JPanel radioButtonPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        JRadioButton isRandom = new JRadioButton("Random");
        JRadioButton isSequential = new JRadioButton("Sequential");
        isRandom.setSelected(true);
        group.add(isRandom);
        group.add(isSequential);
        radioButtonPanel.add(isRandom);
        radioButtonPanel.add(isSequential);
        settingsPanel.add(radioButtonPanel);

        //Create Start Button
        JButton start = new JButton("START");
        start.addActionListener(e -> {
            test = new SpeedTest((int)multiplierBox.getSelectedItem(), isRandom.isSelected());
            settingsFrame.dispose();
            createMainWindow();
        });
        settingsPanel.add(start);

        //Display Frame
        settingsFrame.add(settingsPanel);
        settingsFrame.setVisible(true);
    }

    private void createMainWindow(){
        JFrame mainFrame = new JFrame("Multiplication Speed Test");
        mainFrame.setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        JLabel timer = new JLabel("Time", SwingConstants.CENTER);

        JLabel displayQuestion = new JLabel(test.getQuestion(), SwingConstants.CENTER);

        JTextField answer = new JTextField(15);
        answer.addActionListener(e -> {
            if (!answer.getText().matches("[0-9]+")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid whole number");
            } else {
                test.checkResponse(Integer.parseInt(answer.getText()));
                if (!test.isFinished()) {
                    answer.setText("");
                    displayQuestion.setText(test.getQuestion());
                } else {
                    mainFrame.dispose();
                    createResultsWindow();
                }
            }
        });

        mainPanel.add(timer);
        mainPanel.add(displayQuestion);
        mainPanel.add(answer);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void createResultsWindow(){
        //Create Results Frame and Panel
        JFrame resultsFrame = new JFrame("Results");
        resultsFrame.setSize(350, 350);
        resultsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel resultsPanel = new JPanel(new GridLayout(3, 1));

        //Create Raw Score and Percentage Label
        JLabel rawScore = new JLabel("Raw Score: "+ test.getScore(), SwingConstants.CENTER);
        resultsPanel.add(rawScore);

        JLabel percentage = new JLabel("Final Percentage: " + (test.getScore() * 100) / 12 + "%", SwingConstants.CENTER);
        resultsPanel.add(percentage);

        //Create Incorrect Answers Label
        JTextArea incorrectTextArea = new JTextArea();
        incorrectTextArea.setEditable(false);
        String n = "";
        for (Pair p : test.getIncorrect()){
            incorrectTextArea.append(n + "Your answer to " + p.getFirst() + " was " + p.getSecond() + ", which is incorrect");
            n = "\n";
        }
        JScrollPane incorrectScrollPane = new JScrollPane(incorrectTextArea);
        resultsPanel.add(incorrectScrollPane);

        resultsFrame.add(resultsPanel);
        resultsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new SpeedTestGUI();
    }
}