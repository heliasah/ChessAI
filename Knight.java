import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite, "knight");
        id = Chessman.KNIGHT;
    }

    @Override
    public ArrayList<Position> validMoves(Piece[][] board, Position position) {
        ArrayList<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();
        int[][] lmoves = new int[][]{{r - 1, c - 2}, {r - 1, c + 2}, {r + 1, c - 2}, {r + 1, c + 2}, {r - 2, c - 1}, {r + 2, c - 1}, {r + 2, c + 1}, {r - 2, c + 1}};
        for (int i = 0; i < lmoves.length; i++) {
            if (Board.isValidPos(lmoves[i][0], lmoves[i][1]) && (board[lmoves[i][0]][lmoves[i][1]] == null || board[lmoves[i][0]][lmoves[i][1]].isWhite != this.isWhite))
                moves.add(new Position(lmoves[i][0], lmoves[i][1]));
        }
        return moves;
    }
}
