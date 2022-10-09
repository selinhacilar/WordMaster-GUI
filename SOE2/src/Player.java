import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private String username;
    private String password;
    private int gamesPlayed;
    private int highScore;
    private int estimatedTurns;
    private int turnsTaken;
    private int guessWordLength;
    public static final String GUEST = "Guest";
    private static final String WORDFILE = "resources/players.txt";


    public int getEstimatedTurns() {
        return estimatedTurns;
    }

    public void setEstimatedTurns(int estimatedTurns) {
        this.estimatedTurns = estimatedTurns;
    }

    public int getTurnsTaken() {
        return this.turnsTaken;
    }

    public void setTurnsTaken(int turnsTaken) {
        this.turnsTaken = turnsTaken;
    }

    public int getGuessWordLength() {
        return guessWordLength;
    }

    public void setGuessWordLength(int guessWordLength) {
        this.guessWordLength = guessWordLength;
    }


    public Player(String username, String password, int gamesPlayed, int highScore) {
        this.username = username;
        this.password = password;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        //Don't check this new username with existing ones because new username maybe used anyone else registered into sustem
        this.update();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.update();
    }

    public void incGamesPlayed(){
        gamesPlayed++;
        this.update();
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score){
        if (score > highScore){
            highScore = score;
            this.update();
        }
    }
    public int getScore(int estimatedTurns, int actualTurns, int wordLength){
        return 100 / estimatedTurns * (estimatedTurns - actualTurns + 1) * wordLength;
    }

    /* We need to update the data of the player when they play another game, or when they get a higher score. We
     create a boolean nameFound and assign it to false. We then traverse the ArrayList players, if the username we
     get from a player is the same as the one in the list, we update their data and nameFound becomes true. If we
     can't find the username in the list, that means it is a new player and we add their data to players.txt file
     by using for each method and going through the players list again and write their data by using .write() method.
     In case an error occurs, we do try catch which will print an error message without crashing the program and if
     we write player's data we can print a message saying the data is written successfully.
     */
    public void update() {
        ArrayList<Player> players = this.readPlayerFile();
        boolean nameFound = false;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getUsername().equals(this.getUsername())) {
                System.out.println(player + " updated in players");
                players.set(i, this);
                nameFound = true;
                break;
            }
        }
        if (!nameFound) {
            System.out.println(this + " not found in players");

        }else{
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
    }

    public ArrayList<Player> readPlayerFile(){
        ArrayList<String> allPlayers = FileIO.readDataFromFile(WORDFILE);
        ArrayList<Player> players = new ArrayList<>();
        for (String str: allPlayers) {
            String[] strs = str.split(",");
            players.add(new Player(strs[0], strs[1], Integer.parseInt(strs[2]), Integer.parseInt(strs[3])));
        }
        return players;
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d\n",username,password,gamesPlayed,highScore);
    }
}
