import javax.swing.*;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, "rook");
        id = Chessman.ROOK;
    }

    @Override
    public ArrayList<Position> validMoves(Piece[][] board, Position position) {
        int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        return this.dirMoves(dir, board, position);
    }
}
