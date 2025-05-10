import javax.swing.*;

public class Frame extends JFrame {

    Frame() {
        this.add(new Panel());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

}
