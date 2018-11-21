package Model;

import javax.swing.text.Position;
import java.util.*;

public class BotPlayer extends Player {

    class Position {
        private int row;
        private int col;
        int evaluation = 0;
        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

    private int mainBot;
//    private ArrayList<Position> availablePositions = new ArrayList<>();
//    private Cell[][] boardCopy = new Cell[3][3];

    BotPlayer(CheckBoard board) {
        super();
        botStrategy(board);
    }
    private BotPlayer(Marks m) {
        playerMark = m;
        if (playerMark == Marks.O)
            oppositeMark = Marks.X;
        else
            oppositeMark = Marks.O;
    }

    private int[] botStrategy(CheckBoard board) {
        int[] position;

        if (board.isClear()) {
            return senteFirstStep(board);
        }

        ArrayList<Position> availablePositions = new ArrayList<>();

//        getAvailablePositions(board, availablePositions);
        CheckBoard mockBoard = copyCheckBoard(board, this);
        mainBot = 1;
        evaluate(mockBoard, this, availablePositions);

        Collections.sort(availablePositions, (Position p1, Position p2) -> p2.evaluation - p1.evaluation);

        return new int[] {availablePositions.get(0).row, availablePositions.get(0).col};
    }

    private int[] senteFirstStep(CheckBoard board) {

        int row = (int) (Math.random() * 2);
        int col = (int) (Math.random() * 2);
        return new int[] {row, col};
    }

    private ArrayList<Position> getAvailablePositions(CheckBoard board, ArrayList<Position> availablePositions) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getCellMark(row, col) == null) {
//                    if (mainBot == 1)
                    availablePositions.add(new Position(row, col));
//                    boardCopy.getBoard()[row][col].setCheckMark(null);
                }
                else {
//                    boardCopy.mark(row, col);
                }
            }
        }
        return availablePositions;
    }

    private CheckBoard copyCheckBoard(CheckBoard checkBoard, Player player) {
        CheckBoard newCopy = new CheckBoard();
        newCopy.setCurrentTurn(this);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (checkBoard.getCellMark(row, col) == null) {
                    newCopy.getBoard()[row][col].setCheckMark(null);
                }
                else if (checkBoard.getCellMark(row, col) == player.playerMark) {
                    newCopy.mark(row, col, player.getPlayerMark());
                }
                else {
                    newCopy.mark(row, col, player.getOppositeMark());
                }

            }
        }
        return newCopy;
    }

    private int evaluate(CheckBoard board, BotPlayer player, ArrayList<Position> availablePositions) {
//        if (this.mainBot == 0)
        getAvailablePositions(board, availablePositions);
        Collections.shuffle(availablePositions);
        for (Position p: availablePositions) {
            if (board.isWinning(p.row, p.col, player)) {
                p.evaluation = 1;  // positive evaluation
                return p.evaluation;
            }
            else if (board.isTieAfterThisMark()){
                p.evaluation = 0;
            }
            else {
                CheckBoard mockBoard = copyCheckBoard(board, player);
                mockBoard.mark(p.row, p.col, player.getPlayerMark());
                mainBot = 0;
                int nextMoveEvaluation = evaluate(mockBoard, player.getOppositePlayer(), new ArrayList<Position>());
//                if (nextMoveEvaluation == -1)
//                    p.evaluation = -nextMoveEvaluation;
//                else
//                    p.evaluation = nextMoveEvaluation;
                p.evaluation = -nextMoveEvaluation;
            }
//            if (p.evaluation == 1)
//                return p.evaluation;
        }
        Collections.sort(availablePositions, (Position p1, Position p2) -> p2.evaluation - p1.evaluation);
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
