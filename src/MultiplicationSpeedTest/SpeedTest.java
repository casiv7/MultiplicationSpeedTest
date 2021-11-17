package MultiplicationSpeedTest;

import java.util.ArrayList;
import java.util.Random;

public class SpeedTest {
    private final boolean random;
    private final MultiplicationTable table;
    private int score;
    private ArrayList<Pair> picked;
    private ArrayList<Pair> incorrect;
    private int finalTime;
    private boolean finished;

    public SpeedTest(int multiplier, boolean isRandom){
        random = isRandom;
        table = new MultiplicationTable(multiplier);
        score = 0;
        picked = new ArrayList<>();
        incorrect = new ArrayList<>();
        finalTime = 0;
        finished = false;
    }

    void checkResponse(int answer){
        if (answer == picked.get(picked.size()-1).getSecond()){
            score++;
        }
        else {
            incorrect.add(new Pair(picked.get(picked.size()-1).getFirst(), answer));
        }
        finished = picked.size() == table.getTable().size();
    }

    String getQuestion(){
        if (random){
            Random r = new Random();
            Pair question = table.getTable().get(r.nextInt(12));
            while (picked.contains(question)){
                question = table.getTable().get(r.nextInt(12));
            }
            picked.add(question);
            return question.getFirst();
        }
        Pair question = table.getTable().get(picked.size());
        picked.add(question);
        return question.getFirst();
    }

    int getScore(){
        return score;
    }

    ArrayList<Pair> getIncorrect(){
        return incorrect;
    }

    boolean isFinished(){
        return finished;
    }

    int getFinalTime(){
        return finalTime;
    }

}
