package hk.ust.comp3021.gui.component.board;

import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import hk.ust.comp3021.entities.Player;
import hk.ust.comp3021.entities.Wall;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.gui.utils.Message;
import hk.ust.comp3021.gui.utils.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static hk.ust.comp3021.utils.StringResources.UNDO_QUOTA_TEMPLATE;
import static hk.ust.comp3021.utils.StringResources.UNDO_QUOTA_UNLIMITED;

/**
 * Control logic for a {@link GameBoard}.
 * <p>
 * GameBoardController serves the {@link RenderingEngine} which draws the current game map.
 */
public class GameBoardController implements RenderingEngine, Initializable {
    @FXML
    private GridPane map;

    @FXML
    private Label undoQuota;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Draw the game map in the {@link #map} GridPane.
     *
     * @param state The current game state.
     */
    @Override
    public void render(@NotNull GameState state) {

        // Platform.runLater(() -> map.getChildren().clear());
        if (map.getChildren().isEmpty()) {
            // TODO
            for (int x = 0; x < state.getMapMaxWidth(); x++) {
                for (int y = 0; y < state.getMapMaxHeight(); y++) {
                    int finalX = x;
                    int finalY = y;
                    Platform.runLater(() -> {
                        Cell cell = null;
                        try {
                            cell = new Cell();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        cell.getController().setImage(returnImageURL(state, Position.of(finalX, finalY)));
                        Cell finalCell = cell;
                        map.add(finalCell, finalX, finalY);
                    });
                }
            }
        } else {
            for (Node node : map.getChildren()) {
                if (node instanceof Cell cell) {

                    var x = GridPane.getColumnIndex(node);
                    var y = GridPane.getRowIndex(node);
                    Platform.runLater(() -> {
                        cell.getController().setImage(returnImageURL(state, Position.of(x, y)));
                        if (state.getDestinations().contains(Position.of(x, y)) && state.getEntity(Position.of(x, y)) instanceof Box) {
                            Platform.runLater(() -> cell.getController().markAtDestination());
                        }
                    });

                }
            }
        }
        Platform.runLater(() -> {
            if (state.getUndoQuota().isPresent()) {
                undoQuota.setText(String.format(UNDO_QUOTA_TEMPLATE, state.getUndoQuota().get()));
            } else {
                undoQuota.setText(String.format(UNDO_QUOTA_TEMPLATE, UNDO_QUOTA_UNLIMITED));
            }
        });


    }

    /**
     * Display a message via a dialog.
     *
     * @param content The message
     */
    @Override
    public void message(@NotNull String content) {
        Platform.runLater(() -> Message.info("Sokoban", content));
    }

    private URL returnImageURL(GameState state, Position position) {
        var entity = state.getEntity(position);
        switch (entity) {
            case Wall ignored1:
                return Resource.getWallImageURL();
            case Box b:
                return Resource.getBoxImageURL(b.getPlayerId());
            case Player p:
                return Resource.getPlayerImageURL(p.getId());
            case Empty ignored:
                if (state.getDestinations().contains(position)) {
                    return Resource.getDestinationImageURL();
                } else {
                    return Resource.getEmptyImageURL();
                }
            case null:
                return Resource.getEmptyImageURL();
            default:
                return Resource.getEmptyImageURL();
        }
    }
}
