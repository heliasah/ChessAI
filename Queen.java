import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite, "queen");
        id = Chessman.QUEEN;
    }

    @Override
    public ArrayList<Position> validMoves(Piece[][] board, Position position) {
        int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        return this.dirMoves(dir, board, position);
    }
}
