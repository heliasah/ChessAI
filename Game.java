import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Game implements GUI, Subscriber, Serializable {
    private Board board;
    private Player white;
    private Player black;
    private boolean isWhiteTurn;
    private JPanel panel;
    private JPanel whitePanel;
    private JPanel blackPanel;
    private ArrayList<Piece> whiteDeleted;
    private ArrayList<Piece> blackDeleted;

    public Game(Player white, Player black) {
        this.board = new Board();
        this.white = white;
        this.black = black;
        isWhiteTurn = true;
        this.whiteDeleted = new ArrayList<>();
        this.blackDeleted = new ArrayList<>();

    }


    @Override
    public JComponent render() {
        panel = new JPanel();
        deletedPanels();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(whitePanel);
        panel.add(board.render());
        panel.add(blackPanel);
        panel.setSize(panel.getPreferredSize());
        return panel;
    }

    private void deletedPanels() {
        whitePanel = new JPanel();
        whitePanel.setLayout(new GridLayout(8, 2));
        blackPanel = new JPanel();
        blackPanel.setLayout(new GridLayout(8, 2));
        for (Piece piece : whiteDeleted) {
            whitePanel.add(piece.render());
        }
        for (Piece piece : blackDeleted) {
            blackPanel.add(piece.render());
        }
        whitePanel.setPreferredSize(new Dimension(2 * 60, 8 * 60));
        whitePanel.setMaximumSize(new Dimension(2 * 60, 8 * 60));
        blackPanel.setPreferredSize(new Dimension(2 * 60, 8 * 60));
        blackPanel.setMaximumSize(new Dimension(2 * 60, 8 * 60));


    }

    public void start() {
        board.addSubscriber(this);
        if (isWhiteTurn)
            white.move(board, true);
        else
            black.move(board, false);
    }

    @Override
    public void onEvent(Object obj) {
        if (obj instanceof King)
            return;
        if (obj != null) {
            Piece piece = (Piece) obj;
            if (piece.isWhite) {
                whitePanel.add(piece.render());
                whiteDeleted.add(piece);
            } else {
                blackPanel.add(piece.render());
                blackDeleted.add(piece);
            }
//            panel.setSize(panel.getPreferredSize());
        }
        isWhiteTurn = !isWhiteTurn;
        if (isWhiteTurn)
            white.move(board, true);
        else
            black.move(board, false);
    }

}
