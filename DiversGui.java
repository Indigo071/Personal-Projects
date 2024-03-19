import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Main class for the GUI
 */
public class DiversGui extends JPanel {

    /**
     * Name of the competition
     */
    private String competitionName;
    /**
     * An array for storing judge objects
     */
    private ArrayList<Judge> judges = new ArrayList<>();
    /**
     * A text field for entering the name of the competition
     */
    private JTextField compTextField = new JTextField(20);
    /**
     * the frame for the gui
     */
    private JFrame frame;
    /**
     * A text field for entering the number of divers competing
     */
    private JTextField numDiversTextField;
    /**
     * Button for generating the appropiate amount of entry fields
     */
    private JButton generateButton;
    /**
     * Button that saves the data entered in the entry fields
     */
    private JButton saveButton;
    /**
     * The panel representing the entry fields panel
     */
    private JPanel entryFieldsPanel;
    /**
     * Used to make the panel scrollable in the event a large number of fields are generated
     */
    private JScrollPane scrollPane;
    /**
     * A list for storing the divers entered in the competiton
     */
    private ArrayList<Diver> divers;
    private HashMap<String,Float> ddTable = new HashMap<>();
    /**
     * Button that calculates the meet results upon its conclusion
     */
    private JButton calculateResultsButton;
    /**
     * Text field for entering the names of the judges
     */
    private JTextField judgeNameField = new JTextField(12);
    /**
     * used for adding options into the JComboBox
     */
    private String[] boardHeights = {"1m","3m"};
    /**
     * Generates a txt file containing the meet results
     */
    private JButton generateFileButton = new JButton("Generate File");
    /**
     * Number of divers competing on one meter
     */
    private int oneMeterDivers;
    /**
     * Used for the results to rank one meter divers
     */
    private int oneMeterPlace = 1;
    /**
     * Number of divers competing on one meter
     */
    private int threeMeterDivers;
    /**
     *  Used for the results to rank three meter divers
     */
    private int threeMeterPlace = 1;
    

    /**
     * Exectues GUI and initializes gui
     * @param args default parameter for main
     */
    public static void main(String[] args) {
        //DiversGui d = new DiversGui();
        DiversGui d1 = new DiversGui();
    }

    /**
     * Sets up the GUI by calling the initialize method
     */
    public DiversGui() {
        initialize();
    }

    /**
     * Initialzes the gui
     */
    private void initialize() {

        frame = new JFrame("Dive Meet Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1006, 800);
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("Competition Name:"));
        frame.add(compTextField,BorderLayout.NORTH);
        frame.add(new JLabel("Judges:"));
        frame.add(judgeNameField);
    
        frame.add(new JLabel("Number of divers:"));
        numDiversTextField = new JTextField(5);
        frame.add(numDiversTextField);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(new GenerateButtonActionListener());
        frame.add(generateButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonActionListener());
        frame.add(saveButton);

        entryFieldsPanel = new JPanel();
        entryFieldsPanel.setLayout(new BoxLayout(entryFieldsPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(entryFieldsPanel);
        scrollPane.setPreferredSize(new Dimension(1040, 700));
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);


        divers = new ArrayList<>();
        frame.setVisible(true);
        Diver.initializeDiveList();
    
    }

    /**
     * Class that creates a new ActionListener type for generating entry fields
     */
    private class GenerateButtonActionListener implements ActionListener {
        /**
         * Generates fields upon button press
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            entryFieldsPanel.removeAll();
            competitionName = compTextField.getText();
            String[] string = judgeNameField.getText().split(" ");
            for(int i = 0; i < string.length; i++){
                Judge judge = new Judge(string[i]);
                judges.add(judge);
            }


            int numDivers = Integer.parseInt(numDiversTextField.getText());
            for (int i = 0; i < numDivers; i++) {
                JPanel diverPanel = new JPanel();
                diverPanel.add(new JLabel("Name:"));
                diverPanel.add(new JTextField(10));
                diverPanel.add(new JLabel("Board Height:"));
                diverPanel.add(new JComboBox<String>(boardHeights));

                for (int j = 1; j <= 6; j++) {
                    diverPanel.add(new JLabel("Dive " + j + ":"));
                    diverPanel.add(new JTextField(4));
                }

                entryFieldsPanel.add(diverPanel);
            }

            entryFieldsPanel.revalidate();
            entryFieldsPanel.repaint();
        }
    }

    /**
     * Creates a new ActionListener type for creating a new Button that saves the entered information
     */
    private class SaveButtonActionListener implements ActionListener {
        /**
         * Saves entry field information
         * @param Button click
         */
        public void actionPerformed(ActionEvent e) {
            boolean flag = true;

            divers.clear();
            for(Component c : entryFieldsPanel.getComponents()){
                if(c instanceof JPanel){
                    JPanel diverPanel = (JPanel) c;

                    //Performs input validation for entry fields
                    for(int i = 5; i < 17; i+=2){
                        JTextField textField = (JTextField) diverPanel.getComponent(i);


                        if(Diver.diveMap(1).containsKey(textField.getText()) == false){
                            diverPanel.remove(i);
                            JTextField j = new JTextField("Invalid");
                            j.setForeground(new Color(255,0,0));
                            diverPanel.add(j,i);
                            flag = false;
                        }
                        else if(Diver.diveMap(3).containsKey(textField.getText()) == false){
                            diverPanel.remove(i);
                            JTextField j = new JTextField("Invalid");
                            j.setForeground(new Color(255,0,0));
                            diverPanel.add(j,i);
                            flag = false;
                        }
                    }
                    diverPanel.repaint();
                    diverPanel.revalidate();
                }

            }

            if(flag){
                for (Component component : entryFieldsPanel.getComponents()) {
                    if (component instanceof JPanel) {
                        JPanel diverPanel = (JPanel) component;
                        JTextField nameField = (JTextField) diverPanel.getComponent(1);

                        ArrayList<String> dives = new ArrayList<>();
        
                        for (int i = 5; i < 17; i+= 2) {
                            JTextField diveField = (JTextField) diverPanel.getComponent(i);
                            dives.add(diveField.getText());
                        }
                        JComboBox comboBox = (JComboBox) diverPanel.getComponent(3);
                        String name = nameField.getText();
                        int boardHeight = Integer.parseInt(comboBox.getSelectedItem().toString().substring(0, 1));

                        Diver diver = new Diver(name, boardHeight, dives);
                        divers.add(diver);
                    }
                }
                startMeet();
            }
        }

        /**
         * Begins the dive meet
         */
        public void startMeet(){
            int round = 1;

            entryFieldsPanel.removeAll();
            entryFieldsPanel.repaint();
            entryFieldsPanel.revalidate();
            for(int i = 0; i<6;i++){
                entryFieldsPanel.add(new JLabel("Round:"+ round));
                for(int j = 0; j< divers.size(); j++){
                    entryFieldsPanel.add(new JLabel("Enter judge score for diver "+divers.get(j).getName()+" for dive: "+divers.get(j).getDives().get(i)));
                    JTextField textField = new JTextField(2);
                    textField.setAlignmentX(0);
                    entryFieldsPanel.add(textField);
                    entryFieldsPanel.repaint();
                    entryFieldsPanel.revalidate();
                }
                round++;
                entryFieldsPanel.repaint();
                entryFieldsPanel.revalidate();
            }
            calculateResultsButton = new JButton("Calculate Results");
            calculateResultsButton.addActionListener(new CalculateResultsActionListener());
            frame.add(calculateResultsButton);
            frame.repaint();
            frame.revalidate();
        }

        /**
         * Creates a new ActionListener type for calculating results
         */
        private class CalculateResultsActionListener implements ActionListener{
            /**
             * Upon the Calculate button being pressed the divers scores are totaled and output to the screen
             * @param e Button click
             */
            public void actionPerformed(ActionEvent e){
                Component[] meetField = entryFieldsPanel.getComponents();
                int count = 0;
                for(Component c: meetField){
                    if(c instanceof JTextField){
                        JTextField textField = (JTextField) c;
                        Float judgeScore = Float.parseFloat(textField.getText());
                        divers.get(count).getJudgeScores().add(judgeScore);
                    }
                    count++;
                    if(count == Integer.parseInt(numDiversTextField.getText())){
                        count = 0;
                    }
                }
                for(int i = 0; i < divers.size(); i++){
                    divers.get(i).calculateScores();
                }
                results();
            }
        }

        /**
         * Displays the results on the panel ordering the divers in descending order
         */
        public void results(){
            Collections.sort(divers);
            entryFieldsPanel.removeAll();
            frame.remove(calculateResultsButton);
            generateFileButton.addActionListener(new GenerateFileButton());
            frame.add(generateFileButton,BorderLayout.SOUTH);
            entryFieldsPanel.repaint();
            entryFieldsPanel.revalidate();
            entryFieldsPanel.add(new JLabel("Results for "+compTextField.getText()));
            entryFieldsPanel.add(new JLabel(" "));
            StringBuilder sb = new StringBuilder();
            if(judges.size()>1) {
                for(int i = 0; i < judges.size()-1; i++){
                    sb.append(judges.get(i).getName()+", ");
                }
                sb.append(judges.get(judges.size()-1).getName());
                entryFieldsPanel.add(new JLabel("Judges: "+sb.toString()));
                entryFieldsPanel.add(new JLabel(" "));
                entryFieldsPanel.add(new JLabel(" "));
            }
            else{
                entryFieldsPanel.add(new JLabel("Judge: "+judges.get(0).getName()));
                entryFieldsPanel.add(new JLabel(" "));
                entryFieldsPanel.add(new JLabel(" "));
            }
            oneMeterDivers = 0;
            threeMeterDivers = 0;
            for(int i = 0; i < divers.size(); i++){
                if(divers.get(i).getBoardHeight() == 1) oneMeterDivers+=1;
                else if(divers.get(i).getBoardHeight() == 3) threeMeterDivers+=1;
            }
            entryFieldsPanel.revalidate();
            entryFieldsPanel.repaint();
            frame.repaint();
            frame.revalidate();
            oneMeterPlace = 1;
            threeMeterPlace = 1;
            

            if(oneMeterDivers>0 && threeMeterDivers>0){//replace
                oneAndThreeResults();
            }
            else if(oneMeterDivers!= 0){
                oneMeterResults();
            }
            else{
                threeMeterResults();
            }
            entryFieldsPanel.repaint();
            entryFieldsPanel.revalidate();

            System.out.println("Divers data saved:");
            for (Diver diver : divers) {
                System.out.println("Name: " + diver.getName());
                System.out.println("Board Height: " + diver.getBoardHeight());
                System.out.println("Dives:");
                System.out.println(diver.getDivesWithScore());
                for (int i = 0; i < diver.getDives().size(); i++) {
                    System.out.println("Dive " + (i + 1) + ": " + diver.getDives().get(i));
                }
                System.out.println();
                
            }
        }

        /**
         * Formats results for when there are divers competing on both one and three meter
         */
        public void oneAndThreeResults(){
            entryFieldsPanel.add(new JLabel("1 Meter Results"));
            entryFieldsPanel.add(new JLabel("------------"));
            for(int i = 0; i <oneMeterDivers; i++){
                entryFieldsPanel.add(new JLabel((oneMeterPlace+" "+divers.get(i).getName())));
                entryFieldsPanel.add(new JLabel("  "+divers.get(i).toString()));
                entryFieldsPanel.add(new JLabel(" "));
                oneMeterPlace++;
            }
            for(int i = oneMeterDivers; i < divers.size(); i++){
                if(i==oneMeterDivers) {
                    entryFieldsPanel.add(new JLabel(" "));
                    entryFieldsPanel.add(new JLabel(" "));
                    entryFieldsPanel.add(new JLabel("3 Meter Results"));
                    entryFieldsPanel.add(new JLabel("------------"));
                }
                entryFieldsPanel.add(new JLabel(threeMeterPlace+" "+divers.get(i).getName()));
                entryFieldsPanel.add(new JLabel("  "+divers.get(i).toString()));
                entryFieldsPanel.add(new JLabel(" "));
                threeMeterPlace++;
            }

        }

        /**
         * Formats results for when there are only divers competing on one meter
         */
        public void oneMeterResults(){
            entryFieldsPanel.add(new JLabel("1 Meter Results"));
            entryFieldsPanel.add(new JLabel("------------"));
            for(int i = 0; i <divers.size(); i++){
                entryFieldsPanel.add(new JLabel(oneMeterPlace+" "+divers.get(i).getName()));
                entryFieldsPanel.add(new JLabel("  "+divers.get(i).toString()));
                entryFieldsPanel.add(new JLabel(" "));
                oneMeterPlace++;
            }
        }

        /**
         * Formats results for when there are only divers competing on three meter
         */
        public void threeMeterResults(){
            entryFieldsPanel.add(new JLabel("3 Meter Results"));
            entryFieldsPanel.add(new JLabel("------------"));
            for(int i = 0; i <divers.size(); i++) {
                entryFieldsPanel.add(new JLabel(threeMeterPlace+" "+divers.get(i).getName()));
                entryFieldsPanel.add(new JLabel("  " + divers.get(i).toString()));
                entryFieldsPanel.add(new JLabel(" "));
                threeMeterPlace++;
            }

        }

        /**
         * New ActionListener for writing to file
         */
        private class GenerateFileButton implements  ActionListener{

            @Override
            /**
             * Calls the writeToFile() method upon clicking the Generate File Button
             * @param e Button click
             */
            public void actionPerformed(ActionEvent e) {
                writeToFile();
            }
        }

        /**
         * Outputs the results displayed on the results screen to a txt file name MeetResults.txt
         */
        public void writeToFile(){
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File("MeetResults.txt")));
                for (Component c : entryFieldsPanel.getComponents()) {
                    String text = "";
                    if (c instanceof JLabel) {
                        text = ((JLabel) c).getText()+'\n';

                    }
                    bw.write(text);
                }
                if(bw!=null)bw.close();
            }
            catch(FileNotFoundException e){
                System.err.print(e.getMessage());
            }
            catch(IOException e){
                System.err.print(e.getMessage());
            }

        }
    }
}