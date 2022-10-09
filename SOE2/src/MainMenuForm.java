import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuForm extends JFrame {
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton guestButton;
    private JButton quitButton;

    public MainMenuForm(){
        super("Main Form");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //LogIn button will take the player to LoginForm class.
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm.displayForm();
                dispose();
            }
        });

        //Quit button will exit the game by using System.exit() method.
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /* Guest button will go directly to the GameForm class but since the player doesn't need to have a username
        or password, their scores will start from 0.
         */
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameForm.displayForm(new Player("Guest", "", 0, 0));
                dispose();
            }
        });
    }
    public static void displayForm() {
        JFrame frame = new MainMenuForm();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        displayForm();
    }

}

