import java.io.Serializable;

public class HumanPlayer implements Player , Serializable {
    private boolean isWhite;

    public HumanPlayer(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public int[][] nextMove(int[][] positions, boolean white) {
        return new int[0][];
    }

    @Override
    public void move(Board board, boolean isWhite) {
        if (isWhite) board.setTurn(1);
        else board.setTurn(2);
    }

}
