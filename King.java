import java.util.ArrayList;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite, "king");
        id = Chessman.KING;
    }

    @Override
    public ArrayList<Position> validMoves(Piece[][] board, Position position) {
        ArrayList<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();
        for (int di = -1; di < 2; di++) {
            for (int dj = -1; dj < 2; dj++) {
                if (di == 0 && dj == 0)
                    continue;
                int i = r + di;
                int j = c + dj;
                if (Board.isValidPos(i, j) && (board[i][j] == null || board[i][j].isWhite != this.isWhite))
                    moves.add(new Position(i, j));
            }
        }
        return moves;
    }
}
