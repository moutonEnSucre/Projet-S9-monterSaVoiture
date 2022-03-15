package com.sma.taquinsma;

import com.sma.taquinsma.model.sma.agent.AgentManager;
import com.sma.taquinsma.model.sma.world.World;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {
    int rowNum = 10;
    int colNum = 10;

    final float sceneWidth = 600;
    final float sceneHeight = 600;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Création du monde
        World world = new World(5);

        //Création du container gérant les agents
        AgentManager agentManager = new AgentManager(13, world);
        agentManager.update();

        GridPane grid = new GridPane();
        Random rand = new Random();
        Color[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.RED};
        int index = 0;
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                int n = rand.nextInt(4);
                index++;
                Text text = new Text(Integer.toString(index));
                text.setFill(Color.WHITE);

                Rectangle agent = new Rectangle();
                agent.setWidth(sceneWidth / colNum);
                agent.setHeight(sceneHeight / rowNum);
                agent.setFill(colors[n]);

                StackPane stack = new StackPane();
                stack.getChildren().addAll(agent, text);

                GridPane.setRowIndex(stack, row);
                GridPane.setColumnIndex(stack, col);

                grid.getChildren().addAll(stack);
            }
        }

        Scene scene = new Scene(grid, sceneWidth, sceneHeight);

        primaryStage.setTitle("Jeu du taquin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}