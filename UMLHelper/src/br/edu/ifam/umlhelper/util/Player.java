/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.umlhelper.util;


import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Antonio
 */
public class Player extends Application {
    private Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Application.Parameters params = getParameters();
        String caminhoArquivo = params.getRaw().get(0);
        String workingDir = System.getProperty("user.dir");
        
        Stage s = new Stage();
        final File f = new File(caminhoArquivo);
        final Media m = new Media(f.toURI().toString());
        final MediaPlayer mp = new MediaPlayer(m);
        final MediaView mv = new MediaView(mp);
        final DoubleProperty width = mv.fitWidthProperty();
        final DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        mv.setPreserveRatio(true);
        StackPane root = new StackPane();
        root.getChildren().add(mv);
        final Scene scene = new Scene(root, 960, 540);
        scene.setFill(Color.BLACK);
        s.setScene(scene);
        s.setTitle("Video Player");
        s.show();
        mp.play();
        
    }
    
    public void playVideo(String filePath){
        String caminhoArquivo = filePath;
        String workingDir = System.getProperty("user.dir");
        
        Stage s = new Stage();
        final File f = new File(caminhoArquivo);
        final Media m = new Media(f.toURI().toString());
        final MediaPlayer mp = new MediaPlayer(m);
        final MediaView mv = new MediaView(mp);
        final DoubleProperty width = mv.fitWidthProperty();
        final DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
        mv.setPreserveRatio(true);
        StackPane root = new StackPane();
        root.getChildren().add(mv);
        final Scene scene = new Scene(root, 960, 540);
        scene.setFill(Color.BLACK);
        s.setScene(scene);
        s.setTitle("Video Player");
        s.show();
        mp.play();
    }

    @Override
    public void stop() throws Exception {
        Platform.exit();
    }
    
    public static void main(String[] args) {
        launch(args);
    }


    
}