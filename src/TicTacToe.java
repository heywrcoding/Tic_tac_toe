import Model.*;
import View.TicTacToeConsoleView;

public class TicTacToe {
    public static void main(String[] args) {
        CheckBoard board = new CheckBoard(TicTacToeConsoleView.askGameMode());
        int[] position;

        TicTacToeConsoleView view = new TicTacToeConsoleView(board);
        while (board.getWinner() == null) {
            position = board.mark();
            view.markCell(position[0], position[1], board.getCurrentOppo());  // currentTurn player has change in the invocation of board.mark()
        }

        System.out.println("Winner is: " + board.getWinner());

    }
}
