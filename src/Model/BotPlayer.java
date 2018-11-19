package Model;

import java.util.*;

public class BotPlayer extends Player {

    class Position {
        private int row;
        private int col;
        int evaluation = -1;
        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

    private static int mainBot = 0;
    private ArrayList<Position> availablePositions = new ArrayList<>();
    private CheckBoard boardCopy = new CheckBoard();
//    private Cell[][] boardCopy = new Cell[3][3];

    BotPlayer(CheckBoard board) {
        super();
        botStrategy(board);
    }
    private BotPlayer(Marks m) {
        playerMark = m;
    }

    private int[] botStrategy(CheckBoard board) {
        int[] position;

        if (board.isClear()) {
            return senteFirstStep(board);
        }

        getAvailablePositions(board);
        mainBot = 1;
        evaluate(this.boardCopy, this);

        Collections.sort(availablePositions, (Position p1, Position p2) -> p1.evaluation - p2.evaluation);

        return new int[] {availablePositions.get(0).row, availablePositions.get(0).col};
    }

    private int[] senteFirstStep(CheckBoard board) {

        int row = (int) (Math.random() * 2);
        int col = (int) (Math.random() * 2);
        return new int[] {row, col};
    }

    private void getAvailablePositions(CheckBoard board) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getCellMark(row, col) == null) {
                    if (mainBot == 1)
                        availablePositions.add(new Position(row, col));
                    boardCopy.getBoard()[row][col].setCheckMark(null);
                }
                else {
                    boardCopy.getBoard()[row][col].setCheckMark(board.getCellMark(row, col));
                }
            }
        }

    }

    private int evaluate(CheckBoard board, BotPlayer player) {
        getAvailablePositions(board);
        Collections.shuffle(availablePositions);
        for (Position p: availablePositions) {
            if (board.isWinning(p.row, p.col, player)) {
                p.evaluation = 1;  // positive evaluation
            }
            else if (board.isTieAfterThisMark()){
                p.evaluation = 0;
            }
            else {
                int nextMove = evaluate(board, player.getOppositePlayer());
                p.evaluation = -nextMove;
            }
            if (p.evaluation == 1)
                return p.evaluation;
        }
        return availablePositions.get(0).evaluation;
    }

    @Override
    public int[] mark(CheckBoard board) {
        return botStrategy(board);
    }

    private BotPlayer getOppositePlayer() {
        if (this.playerMark.equals(Marks.X))
            return new BotPlayer(Marks.O);
        else
            return new BotPlayer(Marks.X);
    }
}
