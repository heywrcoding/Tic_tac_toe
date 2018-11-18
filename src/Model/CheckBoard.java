package Model;

public class CheckBoard {
    private Cell[][] board = new Cell[3][3];
    private Player[] players;
    private Player winner = null;
    private Player currentTurn;
    private Player currentOppo;

    public CheckBoard() {
        restart();
    }

    private void restart() {
        clear();
        winner = null;
        players = new Player[2];

        players[0] = new Player();
        players[1] = new Player();
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

    public void mark(int[] position) {
        int row = position[0];
        int col = position[1];
        if (isValid(row, col)) {
            board[row][col].setCheckMark(currentTurn.getPlayerMark());

            if (isWinning(row, col, currentTurn)) {
                winner = currentTurn;
                flipCurrentPlayer(currentTurn);
            }
            else {
                // change current player
                flipCurrentPlayer(currentTurn);
            }
        }
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

    private boolean isWinning(int thisRow, int thisCol, Player thisPlayer) {
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
}
