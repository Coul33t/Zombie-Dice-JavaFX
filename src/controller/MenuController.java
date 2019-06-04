package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private VBox background_pane;

    @FXML
    private ListView<String> difficulty;

    @FXML
    private TextField new_player_name;

    @FXML
    private ListView player_list;

    public void initialize() {
        ObservableList<String> lst = FXCollections.observableArrayList();
        lst.addAll("Easy", "Medium", "Hard");
        difficulty.setItems(lst);
        difficulty.getSelectionModel().selectFirst();
        BackgroundImage back_img = new BackgroundImage(new Image("file:ressources/zombie_hand.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        background_pane.setBackground(new Background(back_img));
    }

    @FXML
    public void addPlayer() {
        if (!new_player_name.getText().equals("")) {
            player_list.getItems().add(new_player_name.getText());
            new_player_name.setText("");
        }
    }

    @FXML
    public void startGame() throws IOException {
        if (player_list.getItems().size() > 1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/game.fxml"));
            Parent root = loader.load();

            GameController gController = loader.getController();
            gController.setParameters(difficulty.getSelectionModel().getSelectedItem(),
                                      player_list.getItems());

            Stage primaryStage = (Stage) player_list.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }
}
