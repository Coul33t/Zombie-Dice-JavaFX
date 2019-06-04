package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ZDice;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import model.Dice;

import tools.Rect;

public class GameController {

    private ZDice zdice_game;
    private GraphicsContext gc;
    private HashMap<String, Image> resources;

    @FXML
    private Text current_player_info;
    @FXML
    private Canvas canvas_game;

    public void initialize() {
        gc = canvas_game.getGraphicsContext2D();
        resources = new HashMap<String, Image>();
        resources.put("dice_green", new Image("file:resources/dice_green.png"));
        resources.put("dice_yellow", new Image("file:resources/dice_yellow.png"));
        resources.put("dice_red", new Image("file:resources/dice_red.png"));
        resources.put("shotgun", new Image("file:resources/shotgun.png"));
        resources.put("brain", new Image("file:resources/brain.png"));
    }

    public void setParameters(String diff, ObservableList<String> players) {
        zdice_game = new ZDice(diff);
        zdice_game.addPlayers((List<String>) players);
        setPlayerText();
        updateCanvasDrawings();
    }

    private void setPlayerText() {
        String status = "Current player: " + zdice_game.getPlayers_list().get(zdice_game.getCurrent_player_turn()).getName();
        status += " Number of points: " + zdice_game.getPlayers_list().get(zdice_game.getCurrent_player_turn()).getTotalPoints();
        current_player_info.setText(status);
    }

    private void updateCanvasDrawings() {
        gc.clearRect(0,0, canvas_game.getWidth(), canvas_game.getHeight());

        double offset_x = this.resources.get("dice_green").getWidth() + 10;

        int i = 0;
        for (Dice dice: zdice_game.getDice_to_throw()) {
            gc.drawImage(this.resources.get("dice_" + dice.getColor()), i*offset_x, 0);
            ++i;
        }

        Rect brain_rect = new Rect(this.resources.get("brain"),
                (int)(canvas_game.getWidth() - this.resources.get(
                "brain").getWidth() - 10),
                10);

        Rect shotgun_rect = new Rect(this.resources.get("shotgun"),
                canvas_game.getWidth() - this.resources.get(
                        "shotgun").getWidth() - 10,
                10 + this.resources.get("brain").getHeight() + 10);

        gc.drawImage(this.resources.get("brain"), brain_rect.getX(), brain_rect.getY());
        gc.drawImage(this.resources.get("shotgun"), shotgun_rect.getX(), shotgun_rect.getY());

        gc.strokeText(Integer.toString(zdice_game.getPlayers_list().get(zdice_game.getCurrent_player_turn()).getScore_tmp()),
                brain_rect.getX() - 20,
                brain_rect.getTop() + (brain_rect.getTop() + brain_rect.getBottom()) / 2);
        gc.strokeText(Integer.toString(zdice_game.getPlayers_list().get(zdice_game.getCurrent_player_turn()).getShotgun()),
                shotgun_rect.getX() - 20,
                shotgun_rect.getTop() + (brain_rect.getBottom() - brain_rect.getTop()) / 2);
    }

    public void takeDices() {
        if (!zdice_game.takeDices()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "You can't take any dice at the moment " +
                            "(either you didn't throw your current dices " +
                            "or you had 3 steps)",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }


        updateCanvasDrawings();
    }

    public void throwDices() throws IOException {
        if (zdice_game.getDice_to_throw().size() != 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "You can't roll the dices if you don't take " +
                            "them before, stoopid",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean stop = zdice_game.throwingAndProcess();

        updateCanvasDrawings();

        if (stop) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "3 shotguns! BOOM, next player.",
                    ButtonType.OK);
            alert.showAndWait();
            if(!zdice_game.nextTurn()) {
                goToScore();
            }
        }



        setPlayerText();
    }

    public void stopTurn() throws IOException {
        gc.clearRect(0,0, canvas_game.getWidth(), canvas_game.getHeight());
        if(!zdice_game.nextTurn()) {
            goToScore();
        }
        setPlayerText();
    }

    public void goToScore() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/score.fxml"));
        Parent root = loader.load();

        ScoreController sController = loader.getController();
        sController.displayScores(zdice_game);

        Stage primaryStage = (Stage) canvas_game.getScene().getWindow();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
