package View;
import Model.Cell;
import Model.CheckBoard;
import Model.Marks;
import Model.Player;
import java.util.*;

public class TicTacToeConsoleView {

    private Cell[][] board = new Cell[3][3];

    public TicTacToeConsoleView(CheckBoard board) {
        restart(board);
    }

    private void restart(CheckBoard board) {
        clear();
        System.out.println("Welcome to TIC TAC TOE.");
        System.out.println("You are " + board.getCurrentTurn().getPlayerMark() + ", and your opponent is " + opponentMark(board.getCurrentTurn()) + ".");
        drawBoard();
    }

    private void drawBoard() {
        System.out.println(" -----------");
        System.out.print("|");
        for (int i = 0; i < 3; i++) {
            drawCell(0, i);
        }
        System.out.println("\n -----------");
        System.out.print("|");
        for (int i = 0; i < 3; i++) {
            drawCell(1, i);
        }
        System.out.println("\n -----------");
        System.out.print("|");
        for (int i = 0; i < 3; i++) {
            drawCell(2, i);
        }
        System.out.println("\n -----------");

    }

    private void drawCell(int row, int col) {
        if (board[row][col].getCheckMark() == null)
            System.out.print("   |");
        else
            System.out.print(" " +board[row][col].getCheckMark() + " |");
    }

    public void markCell(int row, int col, Player player) {
        board[row][col].setCheckMark(player.getPlayerMark());
        drawBoard();
    }

    private void clear() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                board[i][j] = new Cell();
        }
    }

    private static void clearConsole() {
        // clear console output
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (Exception e){
            System.out.println("Console clear error!");
        }
    }

    private Marks opponentMark(Player player) {
        if (player.getPlayerMark().equals(Marks.X))
            return Marks.O;
        else
            return Marks.X;
    }

    public static int askGameMode() {
        System.out.println("Please choose game mode: (Enter 1 for Player Against Computer, enter 0 for Player Against Player) ");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("[01]")) {
            System.out.println("Error input! Please input again: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

}
