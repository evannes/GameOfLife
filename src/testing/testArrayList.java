package testing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 22.03.2017.
 */
public class testArrayList {

    private final static int SIZE = 5;
    private List<List<Boolean>> dynamicBoard = new ArrayList<List<Boolean>>(SIZE);

    public void initStartBoard() {
        for (int i = 0; i < SIZE; i++) {
            dynamicBoard.add(i, new ArrayList<Boolean>(SIZE));
        }

        for (int i = 0; i < dynamicBoard.size(); i++) {
            for (int j = 0; j < dynamicBoard.size(); j++) {
                dynamicBoard.get(i).add(j, false);
            }
        }
    }

    public void forLoop() {
        int teller = 0;
        for (int i = 0; i < this.dynamicBoard.size(); i++) {
            teller ++;
            System.out.print("Teller er nå: " + teller + ", i er: ");
            System.out.println(this.dynamicBoard.get(i));
        }
    }

    public static void main(String[] args) {
        testArrayList test = new testArrayList();
        test.initStartBoard();

        System.out.println("Elise sin kode:");

        test.forLoop();

        test.dynamicBoard.add(new ArrayList<Boolean>(SIZE));

        for (int j = 0; j < SIZE; j ++) {
            test.dynamicBoard.get(SIZE).add(j, false);
        }

        System.out.println("");
        System.out.println("Lagt til en ny liste på slutten:");

        test.forLoop();

        System.out.println("");
        System.out.println("Legger til ett ekstra element på slutten av hver liste:");

        for (int i = 0; i < test.dynamicBoard.size(); i++) {
            test.dynamicBoard.get(i).add(5, true);
        }

        test.forLoop();
    }
}
