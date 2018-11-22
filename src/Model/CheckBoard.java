package Model;

public class CheckBoard {
    private int botFlag = 0;  // 1 for starting bot
    private Cell[][] board = new Cell[3][3];
    private int moveCounter = 0;
    private Player[] players;
    private Player winner = null;
    private Player currentTurn;
    private boolean isClear = true;

    public CheckBoard(int gameMode) {
        setBotFlag(gameMode);
        restart();
    }

    CheckBoard(){
        clear();
    }

    private void restart() {
        clear();
        winner = null;
        players = new Player[2];

        players[0] = new Player();
        if (botFlag == 0)
            players[1] = new Player();
        else
            players[1] = new BotPlayer(this);

        if (players[0].getPlayerMark() == Marks.X)  // X goes first
            currentTurn = players[0];
        else
            currentTurn = players[1];

    }

    private void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                board[i][j] = new Cell();
        }
    }

    public int[] mark() {
        int[] position = currentTurn.mark(this);
        int row = position[0];
        int col = position[1];
        if (row == -1 && col == -1){
            return position;
        }
        while (!isValid(row, col)) {
            position = currentTurn.mark(this);
            row = position[0];
            col = position[1];
        }

        mark(row,col,currentTurn.getPlayerMark());

        if (isWinning(row, col, currentTurn)) {
            winner = currentTurn;
            flipCurrentPlayer(currentTurn);
        }
        else {
            // change current player
            flipCurrentPlayer(currentTurn);
        }

        return position;
    }

    void mark(int row, int col, Marks marks) {
        board[row][col].setCheckMark(marks);
        moveCounter++;
        isClear = false;
    }

    private boolean isValid(int row, int col) {
        if (isOutOfBound(row) || isOutOfBound(col))
            return false;
        else
            return board[row][col].getCheckMark() == null;
    }

    private boolean isOutOfBound(int x) {
        return x < 0 || x > 2;
    }

    boolean isWinning(int thisRow, int thisCol, Player thisPlayer) {
        if (moveCounter >= 5)
            return ((board[0][thisCol].getCheckMark() == thisPlayer.getPlayerMark()     //3 checks in one col
                    && board[1][thisCol].getCheckMark() == thisPlayer.getPlayerMark()
                    && board[2][thisCol].getCheckMark() == thisPlayer.getPlayerMark())
                    || (board[thisRow][0].getCheckMark() == thisPlayer.getPlayerMark()  //3 checks in one row
                    && board[thisRow][1].getCheckMark() == thisPlayer.getPlayerMark()
                    && board[thisRow][2].getCheckMark() == thisPlayer.getPlayerMark())
                    || ( thisRow == thisCol
                    && board[0][0].getCheckMark() == thisPlayer.getPlayerMark()  //3 checks in one diagonal
                    && board[1][1].getCheckMark() == thisPlayer.getPlayerMark()
                    && board[2][2].getCheckMark() == thisPlayer.getPlayerMark())
                    || (thisRow == thisCol
                    && board[2][0].getCheckMark() == thisPlayer.getPlayerMark()  //3 checks in another diagonal
                    && board[1][1].getCheckMark() == thisPlayer.getPlayerMark()
                    && board[0][2].getCheckMark() == thisPlayer.getPlayerMark()));
        else
            return false;
    }

    boolean isTieAfterThisMark() {
        return moveCounter == 8;
    }
    private void flipCurrentPlayer(Player player) {
        if (players[0].equals(currentTurn))
            currentTurn = players[1];
        else
            currentTurn = players[0];
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public Player getCurrentOppo() {
        if (currentTurn.equals(players[0]))
            return players[1];
        else
            return players[0];
    }

    private void setBotFlag(int botFlag) {
        this.botFlag = botFlag;
    }

    boolean isClear() {
        return isClear;
    }

    int getMoveCounter() {
        return moveCounter;
    }

    Marks getCellMark(int row, int col) {
        return board[row][col].getCheckMark();
    }

    Cell[][] getBoard() {
        return board;
    }

    void setCurrentTurn(Player currentTurn) {
        this.currentTurn = currentTurn;
    }
}
