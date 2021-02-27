package day002teammembers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day002TeamMembers {

    static HashMap<String, ArrayList<String>> playersByTeams = new HashMap<>();
    static HashMap<String, ArrayList<String>> teamsByPlayers = new HashMap<>();

    //readDataFromFile
    static void readDataFromFile() {
        //read the file teams.txt, and put value into hashmap palyersByTeams by line.
        try (Scanner scan = new Scanner(new File("teams.txt"))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String newTeam = line.split(":")[0];
                //the splitted value can NOT put into an ArrayList directly, so put them into an array, then from the array to the arrayList
                String[] newPlayerArray = line.split(":")[1].split(",");
                ArrayList<String> newPlayerArrayList = new ArrayList<>();
                for (String player : newPlayerArray) {
                    newPlayerArrayList.add(player);
                }
                playersByTeams.put(newTeam, newPlayerArrayList);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static void writeDataToFile() {
        try (PrintWriter pw = new PrintWriter(new File("player.txt"))) {;
            teamsByPlayers.forEach((k, v) -> {
                String player = k;
                // join all teams to one String for the same player, seperate by a comma ","
                String teams = String.join(", ", v);
                // Join player and teams, seperate by a semicolon ":"
                String line = String.join(": ", player, teams);
                pw.println(line);
                System.out.println("Following data has been written to the file ---- " + line);
            });
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        readDataFromFile();

        //loop thorugh the first HashMap playersByTeams, and put the player in value arraylist as the key of the second HashMap teamsByPlayers
        playersByTeams.forEach((k, v) -> {
            for (int i = 0; i < v.size(); i++) {
                String player = v.get(i);
                //If current player(teamsByPlayers.v.get(i)) already exists as a key in the teamsByPlayers, add the team name(teamsByPlayers.k) to the value ArrayList (teamsByPlayers.get().v)
                if (teamsByPlayers.containsKey(player)) {
                    ArrayList<String> teams = teamsByPlayers.get(player);
                    teams.add(k);
                    teamsByPlayers.replace(player, teams);
                } else { // if the player does NOT exist as a key in the teamsByPlayers, add it as a new key, and put the team name as the first value in the value ArrayList
                    ArrayList<String> teams = new ArrayList<String>();
                    teams.add(k);
                    teamsByPlayers.put(player, teams);
                }
            }
        });//the end of playersByTeams.forEach((k, v)
              
        //write the teamsByPlayers to the file player.txt
        writeDataToFile();
         
    }

}
