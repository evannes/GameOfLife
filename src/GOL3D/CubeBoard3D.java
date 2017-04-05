package GOL3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import sample.BoardManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruker on 28.03.2017.
 */
public class CubeBoard3D {

    private Group group;
    private List<List<Boolean>> board1;
    private List<List<Boolean>> board2;
    private List<List<Boolean>> board3;
    private List<List<Boolean>> board4;
    private List<List<Boolean>> board5;
    private List<List<Boolean>> board6;
    private List<List<Box>> boxBoard1;
    private List<List<Box>> boxBoard2;
    private List<List<Box>> boxBoard3;
    private List<List<Box>> boxBoard4;
    private List<List<Box>> boxBoard5;
    private List<List<Box>> boxBoard6;
    private int boardsize = 30;
    private int cellsize = 50;
    private int boxX = 0;
    private int boxY = 0;
    private int boxZ = 0;
    private AnimationTimer animationTimer;
    private long time = System.nanoTime();
    private BoardManager3D boardManager3D;

    public CubeBoard3D(Group group){
        this.group = group;

        board1 = initBoards(board1);
        board2 = initBoards(board2);
        board3 = initBoards(board3);
        board4 = initBoards(board4);
        board5 = initBoards(board5);
        board6 = initBoards(board6);

        boxBoard1 = createBoxes(boxBoard1,0,-53,0,53,0,53,true,false);
        boxBoard2 = createBoxes(boxBoard2,0,0,-53,53,53,0,false,true);
        boxBoard3 = createBoxes(boxBoard3,0,1590,0,53,0,53,true,false);
        boxBoard4 = createBoxes(boxBoard4,0,0,1590,53,53,0,false,true);
        boxBoard5 = createBoxes(boxBoard5,1590,0,0,0,53,53,false,false);
        boxBoard6 = createBoxes(boxBoard6,-53,0,0,0,53,53,false,false);
    }

    public void animateBoard(){
        defaultStartBoard();
        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                if ((now - time) > 250000000) {
                    System.out.println("animating board");
                    nextCubeGeneration();
                    time = System.nanoTime();
                }
            }
        };
        animationTimer.start();
    }

    public void removeBoxes(){

        group.getChildren().clear();

    }

    public List<List<Boolean>> initBoards(List<List<Boolean>> board){
        board = new ArrayList<List<Boolean>>(boardsize);

        for(int i = 0; i < boardsize; i++) {
            board.add(i, new ArrayList<Boolean>(boardsize));
        }

        for(int i = 0; i < boardsize; i++){
            for(int j = 0; j < boardsize; j++){
                board.get(i).add(j,false);
            }
        }
        return board;
    }

    public void defaultStartBoard(){
        board2.get(0).set(2,true);
        board2.get(1).set(2,true);
        board2.get(2).set(2,true);
        board2.get(2).set(1,true);
        board2.get(1).set(0,true);
    }
    Box currentBox;

    public void nextCubeGeneration(){
        for(int i = 0; i < board2.size(); i++){
            for(int j = 0; j < board2.size(); j++){
                currentBox = boxBoard2.get(i).get(j);
                if(board2.get(i).get(j)){
                    boardManager3D.setPurpleMaterial(currentBox);
                } else {
                    boardManager3D.setBlueMaterial(currentBox);
                }
            }
        }
    }

    // teste regler for board3
    public void checkRules(){

    }

    public List<List<Box>> createBoxes(List<List<Box>> boardBoxes,int boxX,int boxY,int boxZ,
                            int incrementX,int decrementY,int incrementZ,
                            boolean resetX,boolean resetY){

        boardBoxes = new ArrayList<List<Box>>();

        for(int i = 0; i < boardsize; i++) {
            boardBoxes.add(i, new ArrayList<Box>(boardsize));
        }

        for(int i = 0; i < boardsize; i++){
            for(int j = 0; j < boardsize; j++){
                Box myBox = new Box(cellsize,cellsize,cellsize);
                boardBoxes.get(i).add(j,myBox);

                if(j%2==0){
                    //boardManager3D.setBlueMaterial(myBox);
                } else {
                    //boardManager3D.setPurpleMaterial(myBox);
                }

                myBox.setTranslateX(boxX);
                myBox.setTranslateY(boxY);
                myBox.setTranslateZ(boxZ);
                group.getChildren().add(myBox);

                if(resetX) {
                    boxX += incrementX;//53;
                } else if(resetY) {
                    boxY += decrementY;
                } else {
                    boxZ += incrementZ;
                }
            }

            boxX += incrementX;
            boxY += decrementY;
            boxZ += incrementZ;//53;

            if(resetX) {
                boxX = 0;
            } else if(resetY) {
                boxY = 0;
            } else {
                boxZ = 0;
            }

        }
        return boardBoxes;
        //initStartBoard();
    }
}
