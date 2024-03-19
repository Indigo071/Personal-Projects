import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for the Divers Competing
 */
public class Diver implements Comparable<Diver>{
    /**
     * Name for diver
     */
    private String name;
    /**
     * Height of the board diver is competing on
     */
    private int boardHeight;
    /**
     * Total score for the diver
     */
    private float totalScore;
    /**
     * The dives the diver is competing
     */
    private ArrayList<String> dives;

    /**
     * The judge scores the diver recieves
     */
    private ArrayList<Float> judgeScores = new ArrayList<>();
    /**
     * The dive paired with its score
     */
    private HashMap<String,Float> diveWithScore = new HashMap<>();
    /**
     * Degree of Difficulty table for one meter dives
     */
    private static HashMap<String, Float> oneMeter = new HashMap<>();
    /**
     * Degree of Difficulty table for three meter dives
     */
    private static HashMap<String, Float> threeMeter = new HashMap<>();

    /**
     * Constructor for a diver
     * @param name name of the diver
     * @param boardHeight competing board height
     * @param dives the dives being competed
     */
    public Diver(String name, int boardHeight, ArrayList<String> dives) {
        this.name = name;
        this.boardHeight = boardHeight;
        this.dives = dives;
    }

    /**
     * Getter for the name
     * @return the name field
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for board height
     * @return board height field
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Getter for the dive list
     * @return the dives field
     */
    public ArrayList<String> getDives() {
        return dives;
    }

    /**
     * Score calculation method that sets the total score field
     */
    public void calculateScores(){
        DecimalFormat df = new DecimalFormat("#.000");
        for(int i = 0; i< 6; i++){
            float score = Float.valueOf(df.format((judgeScores.get(i)*3)*getDD(dives.get(i),boardHeight)));
            //diveWithScore.put(dives.get(i), (judgeScores.get(i)*3)*getDD(dives.get(i),boardHeight));
            diveWithScore.put(dives.get(i),score);
        }
        float totalScore = 0.0f;
        for(int i = 0; i < 6; i++){
            totalScore+= diveWithScore.get(dives.get(i));
        }
        setTotalScore(totalScore);
    }

    /**
     * Implementation of the compare to method to sort divers in descending order by total score and the board they are competing on
     * @param diver the object to be compared.
     * @return integer representative of the placement of objects relative to others
     */
    public int compareTo(Diver diver){
        if(diver.boardHeight > this.boardHeight)
            return -1;   
        if(diver.boardHeight < this.boardHeight)
            return 1;
        if(diver.totalScore > this.totalScore)
            return 1;
        if(diver.totalScore <  this.totalScore)
            return -1;
        return 0;
    }

    /**
     * String represntation of diver attributes
     * @return a string with the dives with individual scores and the total score
     */
    public String toString(){
        return " Dives:"+diveWithScore.toString() +" "+ "Total Score: "+totalScore;
    }

    /**
     * setter for total score field
     * @param score sets the total score field
     */
    public void setTotalScore(float score){
        this.totalScore = score;
    }

    /**
     * getter for the judge scores
     * @return an array list containing the judge scores earned
     */
    public ArrayList<Float> getJudgeScores(){
        return judgeScores;
    }

    /**
     * Getter for the dives associated with the scores stored in a HashMap
     * @return a HashMap containing the dives and scores for the diver
     */
    public HashMap<String, Float> getDivesWithScore(){
        return diveWithScore;
    }

    /**
     *
     * @param dive the dive to be searched
     * @param boardHeight the board height the dive is being competed on
     * @return the degree of difficulty for the specified dive
     */
    public static float getDD(String dive, int boardHeight){
        dive.toLowerCase();
        if(boardHeight == 1) return oneMeter.get(dive);
        else return threeMeter.get(dive);
    }

    /**
     * used to get the degree of difficulty table
     * @param boardHeight board height corresponding to the degree of difficulty tablke
     * @return the specified degree of difficulty table for the appropiate height
     */
    public static HashMap<String, Float> diveMap(int boardHeight){
        if(boardHeight == 1) return oneMeter;
        else return threeMeter;
        
    }

    /**
     * Sets up the degree of diffculty table for one and three meter dives
     */
    public static void initializeDiveList(){
        
        oneMeter.put("101c",1.2f);
        oneMeter.put("101b",1.3f);
        oneMeter.put("101a",1.4f);
        oneMeter.put("102a",1.6f);
        oneMeter.put("102b",1.5f);
        oneMeter.put("102c",1.4f);
	    oneMeter.put("103c", 1.6f);
	    oneMeter.put("103b",1.7f);
        oneMeter.put("104c",2.2f);
        oneMeter.put("104b",2.3f);
        oneMeter.put("105c",2.4f);
        oneMeter.put("105b",2.6f);
        oneMeter.put("106c",2.9f);
        oneMeter.put("107c",3.0f);

        threeMeter.put("103c",1.5f);
        threeMeter.put("103b",1.6f);
        threeMeter.put("105c",2.2f);
        threeMeter.put("105b",2.4f);
        threeMeter.put("107c",2.8f);
        threeMeter.put("107b",3.1f);

        oneMeter.put("201a",1.7f);
        oneMeter.put("201c",1.5f);
        oneMeter.put("201b",1.6f);
        oneMeter.put("203c",2.0f);
        oneMeter.put("203b",2.3f);
        oneMeter.put("204c",2.2f);
        oneMeter.put("204b",2.5f);
        oneMeter.put("205c",3.0f);
		
        threeMeter.put("201a",1.9f);
        threeMeter.put("201c",1.7f);
        threeMeter.put("201b", 1.8f);
        threeMeter.put("203a",2.4f);
        threeMeter.put("203c", 1.9f);
        threeMeter.put("203b", 2.2f);
        threeMeter.put("205c", 2.8f);
        threeMeter.put("205b",3.0f);

        oneMeter.put("301a",1.8f);
        oneMeter.put("301c", 1.6f);
        oneMeter.put("301b", 1.7f);
        oneMeter.put("302c", 1.6f);
        oneMeter.put("302b", 1.7f);
        oneMeter.put("303c", 2.1f);
        oneMeter.put("303b", 2.4f);
        oneMeter.put("304c", 2.3f);
        oneMeter.put("304b",2.6f);
        oneMeter.put("305c", 3.0f);

        threeMeter.put("301a",2.0f);
        threeMeter.put("301c", 1.8f);
        threeMeter.put("301b", 1.9f);
        threeMeter.put("303c", 2.0f);
        threeMeter.put("303b", 2.3f);
        threeMeter.put("305c", 2.8f);
        threeMeter.put("305b",3.0f);

        oneMeter.put("401a",1.8f);
        oneMeter.put("401c", 1.4f);
        oneMeter.put("401b", 1.5f);
        oneMeter.put("403c", 2.2f);
        oneMeter.put("403b", 2.4f);
        oneMeter.put("404c", 2.8f);
        oneMeter.put("405c", 3.1f);

        threeMeter.put("401c", 1.3f);
        threeMeter.put("401b", 1.4f);
        threeMeter.put("403c", 1.9f);
        threeMeter.put("403b", 2.1f);
        threeMeter.put("404c", 2.4f);
        threeMeter.put("405c", 2.7f);
        threeMeter.put("405b",3.0f);

        oneMeter.put("5132d", 2.2f);
        oneMeter.put("5134d", 2.6f);
        oneMeter.put("5136d",3.0f);
        oneMeter.put("5152b",3.2f);
        oneMeter.put("5221d", 1.7f);
        oneMeter.put("5231d", 2.1f);
        oneMeter.put("5233d", 2.5f);
        oneMeter.put("5331d",2.2f);
        oneMeter.put("5333d",2.5f);

        threeMeter.put("5132d", 2.1f);
        threeMeter.put("5134d", 2.5f);
        threeMeter.put("5152b", 3.0f);
        threeMeter.put("5231d", 2.0f);
        threeMeter.put("5233d", 2.4f);
        threeMeter.put("5235d",2.8f);
        threeMeter.put("5331d", 2.1f);
        threeMeter.put("5333d", 2.5f);
        threeMeter.put("5335d",2.9f);
    }
}