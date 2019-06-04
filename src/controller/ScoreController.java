package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;
import model.ZDice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ScoreController {

    @FXML
    private TextField scoreboard;
    @FXML
    private Button replay;

    public void displayScores(ZDice zdice_game) {
        ArrayList<Player> players = new ArrayList<Player>();

        players.addAll(zdice_game.getPlayers_list());

        Collections.sort(players, Comparator.comparing(Player::getScore));
        Collections.reverse(players);

        for (Player pl: players) {
            scoreboard.appendText(pl.getName() + ": " + pl.getScore() + "\n");
        }
    }

    public void newGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/menu.fxml"));
        Stage primaryStage = (Stage) scoreboard.getScene().getWindow();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
