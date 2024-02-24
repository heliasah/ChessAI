import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Piece implements GUI, Serializable {
    protected Icon iconB;
    protected Icon iconW;
    protected int row;
    protected int col;
    protected int id;
    protected boolean isWhite;


    public Piece(boolean isWhite, String icon) {
        this.isWhite = isWhite;
        this.iconB = new ImageIcon(Piece.class.getClassLoader().getResource(icon + "B.png"));
        this.iconW = new ImageIcon(Piece.class.getClassLoader().getResource(icon + "W.png"));
    }

    public abstract ArrayList<Position> validMoves(Piece[][] board, Position piecePosition);

    protected ArrayList<Position> dirMoves(int[][] dir, Piece[][] board, Position position) {
        ArrayList<Position> moves = new ArrayList<>();
        int r = position.getRow();
        int c = position.getCol();
        for (int[] ints : dir) {
            int di = ints[0];
            int dj = ints[1];
            int i = r + di;
            int j = c + dj;
            while (Board.isValidPos(i, j)) {
                if (board[i][j] != null) {
                    if (board[i][j].isWhite != this.isWhite)
                        moves.add(new Position(i, j));
                    break;
                }
                moves.add(new Position(i, j));
                i += di;
                j += dj;
            }
        }
        return moves;
    }

    @Override
    public JComponent render() {
        JLabel label;
        if (isWhite)
            label = new JLabel(this.iconW);
        else
            label = new JLabel(this.iconB);

        return label;
    }

    public int toInt() {
        if (isWhite)
            return id;
        return -id;
    }
}
