package hk.ust.comp3021.gui;

import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The JavaFX application that launches the game.
 */
public class App extends Application {
    private Stage primaryStage;
    private Scene startScene;

    /**
     * Set up the primary stage and show the {@link StartScene}.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        this.startScene = new StartScene();
        this.startScene.addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, e -> {
            try {
                onOpenMap(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        this.primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
        this.primaryStage.setScene(this.startScene);

        this.primaryStage.show();
        // TODO

    }


    /**
     * Event handler for opening a map.
     * Swith to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     */
    public void onOpenMap(MapEvent event) throws Exception {
        // TODO
        Scene gameScene = new GameScene(new GameState(event.getModel().gameMap()));
        gameScene.addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame);
        primaryStage.setScene(gameScene);

    }

    /**
     * Event handler for exiting the game.
     * Switch to the {@link StartScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to exiting the game.
     */
    public void onExitGame(ExitEvent event) {
        // TODO
        primaryStage.setScene(this.startScene);
    }
}
