import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginForm extends JFrame {
    private JPanel mainPanel;
    private JButton enterButton;
    private JButton cancelButton;
    private JTextField usernametextField;
    private JPasswordField passwordField;
    private JCheckBox viewPasswordCheckBox;
    private char echoChar;
    private ArrayList <Player> players;
    private Player player;
    private static final String WORDFILE = "resources/players.txt";

    public LoginForm(){
        super("Login Form");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        echoChar = passwordField.getEchoChar();
        this.pack();
        this.players = new ArrayList<>();
        this.readPlayerFile();

        /* if the player doesn't enter a username, enter button will be disabled. When the player enters their
        username and password, we will check their username and password. If the player entered the details
        correctly, we can display the GameForm.
         */
        enterButton.addActionListener(e -> {
            if (usernametextField.getText().isEmpty()) {
                enterButton.setEnabled(false);
            }else {
                enterButton.setEnabled(true);
                player = checkUserData();
                if (player != null){
                    GameForm.displayForm(player);
                    dispose();
                }
            }
        });

        //Cancel button will take the player to MainMenuForm class.
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm.displayForm();
                dispose();
            }
        });

        // Whenever the checkbox for password is selected, it will display the password.
        viewPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(viewPasswordCheckBox.isSelected()){
                    passwordField.setEchoChar((char)0);
//                    JOptionPane.showMessageDialog(null, "Selected", "Checkbox", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    passwordField.setEchoChar(echoChar);
//                    JOptionPane.showMessageDialog(null, " Not Selected", "Checkbox", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void showError(JFrame frame, String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void readPlayerFile(){
        ArrayList<String> allPlayers = FileIO.readDataFromFile(WORDFILE);
        for (String str: allPlayers) {
            String[] strs = str.split(",");
            players.add(new Player(strs[0], strs[1], Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
        }
    }

    public Player findPlayer (String name){
        for (Player p : players){
            if(p.getUsername().equals(name)){
                return p;
            }
        }
        return null;
    }

    public void writePlayerFile() {
        try {
            FileWriter myWriter = new FileWriter(WORDFILE);
            for (Player p : players) {
                //System.out.println(p.toString());
                myWriter.write(p.toString());
            }
            myWriter.close();
            System.out.println("Successfully wrote the file ");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
    /* We get the username and password from the player and we need to check if they match our requirements.
    We check for the length of the username that player enters first. If the length is not lower than 2, we print
    an error message saying the length must be at least 2 char long.
     */
    public Player checkUserData(){
        String username = usernametextField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if(username.length() < 2) {
            JOptionPane.showMessageDialog(this, "Username must be at least 2 char long",
                    "Username Error", JOptionPane.ERROR_MESSAGE);
        }
        /* We couldn't find the player's username in the list so we will print a message asking user if they
        want to create a new user. If they say yes, we will add and write the player to our list by their username,
        password and we will set the gamesPlayed and highScore to 0 since they haven't played any games and they
        don't have a score yet.
        */
        else if (this.findPlayer(username) == null) {
            // Our LogIn class didn't find out a user having this username, so there are two options:
            //1. Register a new user
            int confirmDialogValue = JOptionPane.showConfirmDialog(this,
                    "Do you want to create new user?", "Please confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirmDialogValue == JOptionPane.YES_NO_OPTION) {
                //it confirmed to create a new user
                if(Password.checkPassword(this, password)){
                    Player newPlayer = new Player(username, password,0, 0);
                    players.add(newPlayer);
                    this.writePlayerFile();
                    return newPlayer;
                }
            }
            //2. Cancel and go back to the Main menu
            else {
                MainMenuForm.displayForm();
                dispose();
            }
        //if the username and password matches, we can display their information from the other games in the GameForm class.
        }else if(this.findPlayer(username) != null && Objects.equals(this.findPlayer(username).getPassword(), password)){
            //Successfully login
            //GameForm newGame = new GameForm(this.findPlayer(username));
            return this.findPlayer(username);
        }
        return null;

    }
    public static void displayForm() {
        JFrame frame = new LoginForm();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        displayForm();
    }
}
