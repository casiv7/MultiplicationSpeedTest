package MultiplicationSpeedTest;

import java.util.ArrayList;

public class MultiplicationTable {
    private final ArrayList<Pair> table;

    public MultiplicationTable(int n){
        table = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            table.add(new Pair(String.format("%s * %d", n, i), n * i));
        }
    }

    public ArrayList<Pair> getTable() {
        return table;
    }

    public void printTable(){
        for (Pair p : table){
            System.out.println(p.printPair());
        }
    }
}
