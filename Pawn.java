import javax.swing.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, "pawn");
        id = Chessman.PAWN;
    }

    @Override
    public ArrayList<Position> validMoves(Piece[][] board, Position position) {
        ArrayList<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();
        if (this.isWhite) {
            if (Board.isValidPos(r - 1, c) && board[r - 1][c] == null) {
                moves.add(new Position(r - 1, c));
                if (r == 6 && board[r - 2][c] == null)
                    moves.add(new Position(r - 2, c));
            }
            if (Board.isValidPos(r - 1, c + 1) && board[r - 1][c + 1] != null && !board[r - 1][c + 1].isWhite) {
                moves.add(new Position(r - 1, c + 1));
            }
            if (Board.isValidPos(r - 1, c - 1) && board[r - 1][c - 1] != null && !board[r - 1][c - 1].isWhite) {
                moves.add(new Position(r - 1, c - 1));
            }
        } else {
            if (board[r + 1][c] == null) {
                moves.add(new Position(r + 1, c));
                if (r == 1 && board[r + 2][c] == null)
                    moves.add(new Position(r + 2, c));
            }
            if (Board.isValidPos(r + 1, c + 1) && board[r + 1][c + 1] != null && board[r + 1][c + 1].isWhite) {
                moves.add(new Position(r + 1, c + 1));
            }
            if (Board.isValidPos(r + 1, c - 1) && board[r + 1][c - 1] != null && board[r + 1][c - 1].isWhite) {
                moves.add(new Position(r + 1, c - 1));
            }
        }
        return moves;
    }
}
