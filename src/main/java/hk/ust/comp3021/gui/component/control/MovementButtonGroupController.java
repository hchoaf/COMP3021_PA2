package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for {@link MovementButtonGroup}.
 */
public class MovementButtonGroupController implements Initializable {
    @FXML
    private GridPane playerControl;

    @FXML
    private ImageView playerImage;

    private Player player = null;

    /**
     * Observable List of actions.
     */
    public ObservableList<Action> actionObservableList = FXCollections.observableArrayList();


    /**
     * Sets the player controller by the button group.
     *
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The URL to the profile image of the player.
     *
     * @param url The URL.
     */
    public void setPlayerImage(URL url) {

        this.playerImage.setImage(new Image(url.toExternalForm()));
        // TODO
    }

    @FXML
    private void moveUp() {
        // TODO
        actionObservableList.add(new Move.Up(player.getId()));
        // ControlPanelController.actionQueue.add(new Move.Up(player.getId()));
    }

    @FXML
    private void moveDown() {
        // TODO
        actionObservableList.add(new Move.Down(player.getId()));
    }

    @FXML
    private void moveLeft() {
        // TODO
        actionObservableList.add(new Move.Left(player.getId()));
    }

    @FXML
    private void moveRight() {
        // TODO
        actionObservableList.add(new Move.Right(player.getId()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
}
