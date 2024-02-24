import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame implements ActionListener {
    private Game game;
    private CardLayout layout;
    private JButton btn1, btn2, btn3, btn4, btn5, btn6;

    public MainFrame() {
        this.setTitle("Chess");
        this.setSize(800, 800);
        this.setLayout(null);

        Container cpane = getContentPane();
        layout = new CardLayout();
        cpane.setLayout(layout);
        cpane.add("main", menu());
        cpane.add("player", newGame());
        cpane.add("color", chooseColor());
        layout.show(cpane, "main");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (game == null)
                return;
            FileOutputStream fos;
            try {
                fos = new FileOutputStream("data");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(game);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private JPanel menu() {
        JPanel panel = new JPanel();
        btn1 = new JButton("New Game");
        btn2 = new JButton("Continue");
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        panel.add(btn1);
        panel.add(btn2);
        return panel;

    }

    private JPanel newGame() {
        JPanel panel = new JPanel();
        btn3 = new JButton("One Player");
        btn4 = new JButton("Two Player");
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        panel.add(btn3);
        panel.add(btn4);
        return panel;
    }

    private JPanel chooseColor() {
        JPanel panel = new JPanel();
        btn5 = new JButton("White");
        btn6 = new JButton("Black");
        btn5.addActionListener(this);
        btn6.addActionListener(this);
        panel.add(btn5);
        panel.add(btn6);
        return panel;
    }


    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btn1) {
            layout.show(getContentPane(), "player");
        }
        if (actionEvent.getSource() == btn2) {
            readData();
            if (game == null)
                return;
            getContentPane().add("game", game.render());
            layout.show(getContentPane(), "game");
            game.start();
        }
        if (actionEvent.getSource() == btn3) {
            layout.show(getContentPane(), "color");
        }
        if (actionEvent.getSource() == btn4) {
            game = new Game(new HumanPlayer(true), new HumanPlayer(false));
            getContentPane().add("game", game.render());
            layout.show(getContentPane(), "game");
            game.start();
        }
        if (actionEvent.getSource() == btn5) {
            game = new Game(new HumanPlayer(true), new AIPlayer());
            getContentPane().add("game", game.render());
            layout.show(getContentPane(), "game");
            game.start();
        }
        if (actionEvent.getSource() == btn6) {
            game = new Game(new AIPlayer(), new HumanPlayer(false));
            getContentPane().add("game", game.render());
            layout.show(getContentPane(), "game");
            game.start();
        }
    }

    public void readData() {
        ObjectInputStream ois;
        try {
            FileInputStream fin = new FileInputStream("data");
            ois = new ObjectInputStream(fin);
            game = (Game) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException ignored) {

        }

    }

}
