import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    final int BUTTON_SIZE = 50;
    final int WIDTH = 900;
    final int HEIGHT = 750;
    final int MINES_NUMBER = HEIGHT / BUTTON_SIZE + WIDTH / BUTTON_SIZE;
    int userFlagNumber = 0;

    ImageIcon mineImage;
    ImageIcon flag;
    ImageIcon bomb;

    ArrayList<Point> mine;
    JButton[][] buttons;
    int[][] numbers;

    boolean running = true;

    Panel() {
        this.setPreferredSize(new Dimension(900, 750));
        this.setFocusable(true);
        this.setLayout(new GridLayout(HEIGHT / BUTTON_SIZE, WIDTH / BUTTON_SIZE));

        Image img = new ImageIcon(Objects.requireNonNull(getClass().getResource("mines.jpg"))).getImage();
        Image newImage = img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH);
        mineImage = new ImageIcon(newImage);

        Image flagimg = new ImageIcon(Objects.requireNonNull(getClass().getResource("./flag.jpg"))).getImage();
        Image newflagImage = flagimg.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH);
        flag = new ImageIcon(newflagImage);

        Image bombimg = new ImageIcon(Objects.requireNonNull(getClass().getResource("./mine.jpeg"))).getImage();
        Image newbombImage = bombimg.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH);
        bomb = new ImageIcon(newbombImage);

        numbers = new int[(HEIGHT / BUTTON_SIZE)][(WIDTH / BUTTON_SIZE)];
        mine = new ArrayList<>();
        buttons = new JButton[(HEIGHT / BUTTON_SIZE)][(WIDTH / BUTTON_SIZE)];

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton(String.valueOf(i));
                buttons[i][j].setIcon(mineImage);
                buttons[i][j].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3 && buttons[finalI][finalJ].isEnabled()) {
                            if (buttons[finalI][finalJ].getIcon() == mineImage) {
                                buttons[finalI][finalJ].setIcon(flag);
                                userFlagNumber++;
                                CheckGame();
                            } else {
                                buttons[finalI][finalJ].setIcon(mineImage);
                                userFlagNumber--;
                            }
                        }
                    }
                });
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this);

                this.add(buttons[i][j]);
            }
        }
        putMines();
        numbers();
    }

    private void putMines() {
        Random random = new Random();
        for (int i = 0; i < MINES_NUMBER; i++) {
            int mineInX = random.nextInt(HEIGHT / BUTTON_SIZE);
            int mineInY = random.nextInt(WIDTH / BUTTON_SIZE);
            if (!mine.contains(new Point(mineInX, mineInY))) {
                mine.add(new Point(mineInX, mineInY));
            } else {
                i--;
            }
        }
    }

    private void numbers() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (!mine.contains(new Point(i, j))) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int newI = i + dx;
                            int newJ = j + dy;
                            if (newI >= 0 && newI < buttons.length && newJ >= 0 && newJ < buttons[i].length) {
                                if (mine.contains(new Point(newI, newJ))) {
                                    numbers[i][j] += 1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void clickedZero() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (!buttons[i][j].isEnabled() && numbers[i][j] == 0) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int newI = i + dx;
                            int newJ = j + dy;
                            if (newI >= 0 && newI < buttons.length && newJ >= 0 && newJ < buttons[i].length && buttons[newI][newJ].getIcon() != flag) {
                                buttons[newI][newJ].setIcon(null);
                                buttons[newI][newJ].setFont(new Font("Apple", Font.BOLD, 30));
                                buttons[newI][newJ].setForeground(Color.BLACK);
                                buttons[newI][newJ].setOpaque(true);
                                buttons[newI][newJ].setText(String.valueOf(numbers[newI][newJ]));
                                buttons[newI][newJ].setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void GameOver() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (mine.contains(new Point(i, j))) {
                    buttons[i][j].setIcon(bomb);
                }
                if (buttons[i][j].getIcon() == flag && !mine.contains(new Point(i, j))) {
                    buttons[i][j].setIcon(null);
                    buttons[i][j].setFont(new Font("Apple", Font.BOLD, 30));
                    buttons[i][j].setForeground(Color.BLACK);
                    buttons[i][j].setOpaque(true);
                    buttons[i][j].setText("X");
                }
                buttons[i][j].setEnabled(false);
            }
        }
        running = false;
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.PLAIN_MESSAGE);

    }

    private void CheckGame() {
        if (userFlagNumber == MINES_NUMBER) {
            int enabledButton = 0;
            for (JButton[] button : buttons) {
                for (JButton Button : button) {
                    if (Button.isEnabled()) {
                        enabledButton++;
                        if (enabledButton == userFlagNumber && enabledButton == MINES_NUMBER) {
                            JOptionPane.showMessageDialog(this, "Winner", "There are a Winner", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                clickedZero();
                if (e.getSource() == buttons[i][j]) {
                    if (mine.contains(new Point(i, j))) {
                        GameOver();
                    } else if (buttons[i][j].getIcon() == flag) {
                    } else {
                        buttons[i][j].setFont(new Font("Apple", Font.BOLD, 30));
                        buttons[i][j].setIcon(null);
                        buttons[i][j].setForeground(Color.BLACK);
                        buttons[i][j].setText(String.valueOf(numbers[i][j]));
                        buttons[i][j].setOpaque(true);
                        buttons[i][j].setEnabled(false);
                    }
                }
            }
        }
    }
}
