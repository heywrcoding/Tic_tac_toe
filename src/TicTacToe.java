import Model.*;
import View.TicTacToeConsoleView;

public class TicTacToe {
    public static void main(String[] args) {
        CheckBoard board = new CheckBoard(TicTacToeConsoleView.askGameMode());
        int[] position;

        TicTacToeConsoleView view = new TicTacToeConsoleView(board);
        while (board.getWinner() == null) {
            position = board.mark();
            if (position[0] != -1 && position[1] != -1)
                view.markCell(position[0], position[1], board.getCurrentOppo());  // currentTurn player has change in the invocation of board.mark()
            else
                break;
        }

        if (board.getWinner() != null)
            System.out.println("Winner is: " + board.getWinner());
        else
            System.out.println("No winner! It is a tie.");

    }
}
