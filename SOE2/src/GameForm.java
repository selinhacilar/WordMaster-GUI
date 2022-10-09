import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameForm extends JFrame {
    private JPanel mainPanel;
    private JTextField wordTextField;
    private JButton enterButton;
    private JButton newGameButton;
    private JButton mainMenuButton;
    private JButton optionsButton;
    private JCheckBox cheatModeCheckBox;
    private JButton leaderboardButton;
    private JLabel secretWord;
    private JLabel cheatMode;
    private JLabel estimatedTurns;
    private JLabel playerDetails;


    private WordList wordList;
    private Player player;
    private boolean guessed;

    public GameForm(Player player){
        super("Game Form");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.player = player;
        /* We need to set the TurnsTaken in the beginning of every game and we need to increase it by 1 as the
        player takes their turn
        */
        this.player.setTurnsTaken(0);
        this.wordList = new WordList();
        this.wordList.setSecretWord(this.player.getGuessWordLength());
        /* We need to put the secret word into the cheatMode label and not make it visible when the player clicks
        on the cheatMode checkbox.
        */
        cheatMode.setText(this.wordList.getSecretWord());
        cheatMode.setVisible(false);
        this.guessed = false;
        this.pack();
        this.playerChoice();

        //Options button will take the player to OptionsForm class.
        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OptionsForm.displayForm(player);
                dispose();
            }
        });

        //MainMenu button will take the player to MainMenuForm class.
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm.displayForm();
                dispose();
            }
        });

        //NewGame button will start a new game in the GameForm class.
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameForm.displayForm(player);
                dispose();
            }
        });

        /* We create a boolean guessed and assign it to false. We need to first display the player's choices and
        increase the TurnsTaken as the player plays the game. If the player still has turns to play, meaning
        estimatedTurns is greater than turnsTaken, the game will continue. We create a String called guessWord
        which will be the player's guess for the secret word. If the lengths of both of the guessWord and secret
        word match, we will compare the words using compareWords() method. Else, we will display an error message
        saying the word length should be the length of the secret word. If the player guesses the word right, we use
        the playerWon() method, display the winning message and we can then end the game.
         */
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setTurnsTaken(player.getTurnsTaken() + 1);
                playerChoice();
                if(!guessed && player.getTurnsTaken() < player.getEstimatedTurns()){
                    String guessWord = wordTextField.getText();
                    if (guessWord.length() == player.getGuessWordLength()){
                        if (wordList.compareWords(guessWord)) {
                            guessed = true;
                            secretWord.setText(wordList.getDisplayWordArray());
                            enterButton.setEnabled(false);
                            playerWon();
                            playerChoice();
                            return;
                        } else {
                            secretWord.setText(wordList.getDisplayWordArray());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "the length of the word should be" +
                                player.getGuessWordLength(), "error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                /* if the player is out of turns, we will disable the Enter button to let the player know that
                the game ended and we will use the playerLost() method and display the message.
                 */
                if(player.getTurnsTaken() >= player.getEstimatedTurns()){
                    enterButton.setEnabled(false);
                    playerLost();
                }
            }

        });

        //Leaderboard button will take the player to LeaderboardForm class.
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeaderboardForm.displayForm();
                dispose();
            }
        });

        //Whenever the cheatMode checkbox is selected, the secret word will be visible to the player
        cheatModeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cheatMode.setVisible(cheatModeCheckBox.isSelected());
            }
        });
    }
    /* We create a playerChoice() method in which we will put the player's information and their choices in the
    OptionsForm class and choose and display the array for the secret word based on the player's decision for the
    word's length.
    */
    public void playerChoice() {
        secretWord.setText(wordList.getDisplayWordArray());
        estimatedTurns.setText("Estimated turns " + player.getEstimatedTurns() + ". Turns taken " + player.getTurnsTaken());
        playerDetails.setText("Player name: " +player.getUsername() + ". High score " + player.getHighScore());
    }

    /* We create a playerWon() method which will display a winning message containing their username, score and
    highscore when player wins the game. Then we need to set the score to highscore and increase the games they
    played. If the player's name is Guest, we don't need to show their highscore.
    */
    public void playerWon (){
        int score = player.getScore(player.getEstimatedTurns(), player.getTurnsTaken(), player.getGuessWordLength());
        String message = "Congratulations " + player.getUsername() + ", you guessed the word. Your score is = " + score;

        if(!player.getUsername().equals("Guest")){
            player.setHighScore(score);
            player.incGamesPlayed();
            message +=  ".Your highscore is " + player.getHighScore();
        }
        JOptionPane.showMessageDialog(this,message,"You won ",JOptionPane.INFORMATION_MESSAGE);
    }

    /* We create a playerLost() method which will display a losing message saying the player lost the game.
    If the player's name is Guest, we don't need to show their highscore.
     */
    public void playerLost(){
        int score = player.getScore(player.getEstimatedTurns(), player.getTurnsTaken(), player.getGuessWordLength());
        String message = "Oww! " + player.getUsername() + ", you lost. Your score is = " + score;

        if(!player.getUsername().equals("Guest")){
            player.setHighScore(score);
            player.incGamesPlayed();
            message +=  ".Your highscore is " + player.getHighScore();
        }
        JOptionPane.showMessageDialog(this,message,"You LOST ",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void displayForm(Player player) {
        JFrame frame = new GameForm(player);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        displayForm(new Player("Tekin", "123456**", 0, 0));
    }

}

