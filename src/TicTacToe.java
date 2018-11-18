import Model.*;
import View.TicTacToeConsoleView;

public class TicTacToe {
    public static void main(String[] args) {
        CheckBoard board = new CheckBoard();
        int[] position = new int[2];

        TicTacToeConsoleView view = new TicTacToeConsoleView(board.getCurrentTurn());
        while (board.getWinner() == null) {
            position =board.getCurrentTurn().mark();
            board.mark(position);
            view.markCell(position[0], position[1], board.getCurrentOppo());  // currentTurn player has change in the invocation of board.mark()
        }

        System.out.println("Winner is: " + board.getWinner());

    }
}