import javax.swing.*;

public class LeaderboardForm extends JFrame {
    private JPanel mainPanel;

    public LeaderboardForm(){
        super("Leaderboard Form");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }
    public static void displayForm() {
        JFrame frame = new LeaderboardForm();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        displayForm();
    }

}
