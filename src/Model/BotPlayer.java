package Model;
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

        if (board.getMoveCounter() != 9){
            ArrayList<Position> availablePositions = new ArrayList<>();
            CheckBoard mockBoard = copyCheckBoard(board, this);
            mainBot = 1;
            evaluate(mockBoard, this, availablePositions);

            Collections.sort(availablePositions, (Position p1, Position p2) -> p2.evaluation - p1.evaluation);

            return new int[] {availablePositions.get(0).row, availablePositions.get(0).col};

        }
        else
            return new int[] {-1, -1};
    }

    private int[] senteFirstStep(CheckBoard board) {

        int row = (int) (Math.random() * 2);
        int col = (int) (Math.random() * 2);
        return new int[] {row, col};
    }

    private void getAvailablePositions(CheckBoard board, ArrayList<Position> availablePositions) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getCellMark(row, col) == null) {
                    availablePositions.add(new Position(row, col));
                }
            }
        }
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

    private Position evaluate(CheckBoard board, BotPlayer player, ArrayList<Position> availablePositions) {
//        if (this.mainBot == 0)
        getAvailablePositions(board, availablePositions);
        Collections.shuffle(availablePositions);
        for (Position p: availablePositions) {
            CheckBoard mockBoard = copyCheckBoard(board, player);
            mockBoard.mark(p.row, p.col, player.getPlayerMark());
            if (mockBoard.isWinning(p.row, p.col, player)) {
                p.evaluation = 1;  // positive evaluation
                return p;
            }
            else if (mockBoard.isTieAfterThisMark()){
                p.evaluation = 0;
            }
            else {
                mainBot = 0;
                Position nextMove = evaluate(mockBoard, player.getOppositePlayer(), new ArrayList<Position>());
//                if (nextMoveEvaluation == -1)
//                    p.evaluation = -nextMove.evaluation;
//                else
//                    p.evaluation = nextMove.evaluation;
                p.evaluation = -nextMove.evaluation;
            }
        }
        Collections.sort(availablePositions, (Position p1, Position p2) -> p2.evaluation - p1.evaluation);
        return availablePositions.get(0);
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
