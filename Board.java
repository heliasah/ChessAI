import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Board extends Publisher implements GUI, MouseListener, Serializable {
    private Piece[][] board;
    private JComponent selected;
    private JComponent[][] boardUI;
    private JPanel panel;
    private int turn;

    public Board() {
        createBoard();
        selected = null;
        panel = null;
        turn = 0;
    }

    private void createBoard() {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(true);
            board[1][i] = new Pawn(false);
        }
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);
        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);
        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);
        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);
        board[0][3] = new Queen(false);
        board[7][3] = new Queen(true);
        board[0][4] = new King(false);
        board[7][4] = new King(true);
    }

    private void addPiece(Piece piece, Position pos) {
        JComponent component;
        int i = pos.getRow();
        int j = pos.getCol();
        if (piece == null) {
            component = new JLabel();
            component.setMinimumSize(new Dimension(60, 60));
            component.setPreferredSize(new Dimension(60, 60));
            component.putClientProperty("type", "empty");
        } else {
            component = piece.render();
            component.putClientProperty("type", "piece");
        }
        component.setOpaque(true);
        if ((i + j) % 2 == 0)
            component.setBackground(cellColor1);
        else
            component.setBackground(cellColor2);
        component.addMouseListener(this);
        component.putClientProperty("pos", new Position(i, j));
        component.putClientProperty("valid", false);
        boardUI[i][j] = component;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = j;
        gbc.gridy = i;
        panel.add(component, gbc);
    }

    @Override
    public JComponent render() {
        boardUI = new JComponent[8][8];
        panel = new JPanel();

        this.panel.setLayout(new GridBagLayout());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                addPiece(board[i][j], new Position(i, j));
            }
        }
        this.panel.setSize(this.panel.getPreferredSize());
        return this.panel;
    }

    public int[][] toArr() {
        int[][] arr = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0;
                if (board[i][j] != null)
                    arr[i][j] = board[i][j].toInt();
            }
        }
        return arr;
    }

    private void unselect() {
        Position pos = (Position) selected.getClientProperty("pos");
        setBackground(selected, cellColor1, cellColor2);
        Piece piece = board[pos.getRow()][pos.getCol()];
        if ((turn == 2 && piece.isWhite) || (turn == 1 && !piece.isWhite))
            return;
        ArrayList<Position> moves = piece.validMoves(board, pos);
        for (Position move : moves) {
            setBackground(boardUI[move.getRow()][move.getCol()], cellColor1, cellColor2);
            boardUI[move.getRow()][move.getCol()].putClientProperty("valid", false);
        }
        selected = null;
    }

    private void setBackground(JComponent component, Color color1, Color color2) {
        Position pos = (Position) component.getClientProperty("pos");
        if ((pos.getCol() + pos.getRow()) % 2 == 0)
            component.setBackground(color1);
        else
            component.setBackground(color2);
    }


    private void hitPiece(JComponent component) {
        turn = 0;
        Position piecePos = (Position) selected.getClientProperty("pos");
        Position enemyPos = (Position) component.getClientProperty("pos");
        Piece piece = board[piecePos.getRow()][piecePos.getCol()];
        Piece enemy = board[enemyPos.getRow()][enemyPos.getCol()];
        ArrayList<Position> moves = piece.validMoves(board, piecePos);
        for (Position move : moves) {
            setBackground(boardUI[move.getRow()][move.getCol()], cellColor1, cellColor2);
            boardUI[move.getRow()][move.getCol()].putClientProperty("valid", false);
        }
        board[piecePos.getRow()][piecePos.getCol()] = null;
        board[enemyPos.getRow()][enemyPos.getCol()] = piece;
        panel.remove(component);
        panel.remove(boardUI[piecePos.getRow()][piecePos.getCol()]);
        addPiece(piece, enemyPos);
        addPiece(null, piecePos);
        panel.revalidate();
        panel.repaint();
        selected = null;
        this.publish(enemy);
    }

    public void move(Position fromPos, Position toPos) {
        if (fromPos == null || toPos == null)
            return;
        turn = 0;
        Piece piece = board[fromPos.getRow()][fromPos.getCol()];
        Piece enemy = board[toPos.getRow()][toPos.getCol()];
        board[fromPos.getRow()][fromPos.getCol()] = null;
        board[toPos.getRow()][toPos.getCol()] = piece;
        panel.remove(boardUI[toPos.getRow()][toPos.getCol()]);
        panel.remove(boardUI[fromPos.getRow()][fromPos.getCol()]);
        addPiece(piece, toPos);
        addPiece(null, fromPos);
        panel.revalidate();
        panel.repaint();
        this.publish(enemy);
    }

    private void selectPiece(JComponent component) {
        if (selected != null)
            if ((boolean) component.getClientProperty("valid")) {
                hitPiece(component);
                return;
            } else
                unselect();

        selected = component;
        Position pos = (Position) component.getClientProperty("pos");
        setBackground(component, selectedColor1, selectedColor2);
        Piece piece = board[pos.getRow()][pos.getCol()];
        if ((turn == 2 && piece.isWhite) || (turn == 1 && !piece.isWhite))
            return;
        ArrayList<Position> moves = piece.validMoves(board, pos);
        for (Position move : moves) {
            setBackground(boardUI[move.getRow()][move.getCol()], selectedColor1, selectedColor2);
            boardUI[move.getRow()][move.getCol()].putClientProperty("valid", true);
        }

    }

    private void select(JComponent component) {
        if (selected == null)
            return;
        if (!(boolean) component.getClientProperty("valid")) {
            unselect();
            return;
        }
        hitPiece(component);

    }

    public static boolean isValidPos(int r, int c) {
        if (r < 0 || r >= 8)
            return false;
        return c >= 0 && c < 8;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (turn == 0)
            return;
        JComponent component = (JComponent) mouseEvent.getComponent();
        if (component.getClientProperty("type").equals("piece"))
            selectPiece(component);
        else
            select(component);

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
