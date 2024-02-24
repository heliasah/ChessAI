import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite, "bishop");
        id = Chessman.BISHOP;
    }

    @Override
    public ArrayList<Position> validMoves(Piece[][] board, Position position) {
        int[][] dir = new int[][]{{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        return this.dirMoves(dir, board, position);
    }
}
