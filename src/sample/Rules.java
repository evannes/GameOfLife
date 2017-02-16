package sample;

/**
 * Created by miinael on 15.02.2017.
 */
public class Rules {
    private boolean[][] board;

    //Sette brettet Rules skal jobbe med
    public void setBoard(boolean[][] board) { this.board = board; }

    //Gi ut oppdatert brett
    public boolean[][] getBoard() {
        return board;
    }

    public void nextGeneration(){
        boolean[][] newBoard = new boolean[board.length][board[0].length];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){

                int neighbors = countNeighbor(i, j, board);
                //System.out.print(neighbors);
                if(board[i][j] == false) {
                    newBoard[i][j] = shouldSpawnActiveCell(neighbors) ? true : false;
                } else {
                    newBoard[i][j] = shouldStayAlive(neighbors) ? true : false;
                }
            }
        }
        board = newBoard;
    }

    private static int countNeighbor(int i, int j, boolean[][] board){
        int count = 0;

        //sjekke topp
        if (isActiveCell(i, j-1, board))
            count++;

        //sjekke topp-venstre
        if (isActiveCell(i-1, j-1, board))
            count++;

        //sjekke topp-høyre
        if (isActiveCell(i+1, j-1, board))
            count++;

        //sjekke venstre
        if (isActiveCell(i-1, j, board))
            count++;

        //sjekke høyre
        if (isActiveCell(i+1, j, board))
            count++;

        //sjekke under
        if (isActiveCell(i, j+1, board))
            count++;

        //sjekke under-høyre
        if (isActiveCell(i+1, j+1, board))
            count++;

        //sjekke under-venstre
        if (isActiveCell(i-1, j+1, board))
            count++;

        return count;
    }

    //hvis plassen er død skal den sjekke om den skal bli født
    private static boolean shouldSpawnActiveCell(int counter) {
        return counter == 3;
    }

    //hvis plassen har en levende celle skal den sjekke om den skal leve videre eller dø
    private static boolean shouldStayAlive(int counter) {
        return counter == 2 || counter == 3;
    }

    //finne ut om cellen er levende OG om den er innafor brettet
    private static boolean isActiveCell(int i, int j, boolean[][] board) {
        return inBounds(i, j, board) && board[i][j] == true;
    }

    //sjekke om man er utenfor brettet
    private static boolean inBounds(int i, int j, boolean[][] board){
        if(i == -1 || j == -1){
            return false;
        }

        if(i >= board.length || j >= board[0].length){
            return false;
        }

        return true;
    }
}


