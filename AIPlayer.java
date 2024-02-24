import java.io.Serializable;

public class AIPlayer implements Player, Serializable {

    @Override
    public int[][] nextMove(int[][] chess, boolean white) {
        MiniMax.MiniMaxState miniMaxState = MiniMax.miniMax(chess, white, 4);
        return miniMaxState.getChess();
    }

    @Override
    public void move(Board board, boolean isWhite) {
        int[][] arr = nextMove(board.toArr(), isWhite);
        Position from = null;
        Position to = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (arr[i][j] != board.toArr()[i][j]) {
                    if (arr[i][j] == 0)
                        from = new Position(i, j);
                    else
                        to = new Position(i, j);
                }
            }
        }
        board.move(from, to);
    }

}
