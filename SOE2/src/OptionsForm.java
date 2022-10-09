import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsForm extends JFrame{
    private JSpinner numOfGuessSpinner;
    private JSpinner wordLengthSpinner;
    private JPanel mainPanel;
    private JButton OKButton;

    public OptionsForm(Player player){
        super("Options Form");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        //We set the spinners' maximum and minimum values, their starting values and the step size
        numOfGuessSpinner.setModel(new SpinnerNumberModel(1, 1, 15, 1));
        wordLengthSpinner.setModel(new SpinnerNumberModel(4, 4, 14, 1));
        this.pack();

        /* When we click on the OK button, we want to go back to the GameFrom class and the game will start based on
        the values of player's word length choice and estimated turn choice. We get the value from numOfGuessSpinner
        and set it to EstimatedTurns and we get the other value from wordLengthSpinner and set it to GuessWordLength
        which we will pass to GameFrom class.
        */
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setEstimatedTurns((Integer)numOfGuessSpinner.getValue());
                player.setGuessWordLength((Integer)wordLengthSpinner.getValue());
                GameForm.displayForm(player);
                dispose();
            }
        });
    }
    public static void displayForm(Player player) {
        JFrame frame = new OptionsForm(player);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        displayForm(new Player("Tekin", "123456**", 0, 0));
    }
}
