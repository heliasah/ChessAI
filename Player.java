import java.util.List;

public interface Player {

    int[][] nextMove(int[][] positions, boolean white);
    void move(Board board, boolean isWhite);
}
