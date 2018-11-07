/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.umlhelper.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author Antonio
 */
public class Player2 {
  private static String videoPath;
  
  public static void setVideoPath(String videoPath){
      Player2.videoPath = videoPath;
  }
  
  private static void initAndShowGUI() {
    // This method is invoked on Swing thread
    JFrame frame = new JFrame("FX");
    final JFXPanel fxPanel = new JFXPanel();
    frame.add(fxPanel);
    
    
  
    frame.setBounds(200, 100, 900, 600);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setVisible(true);
    
    
    
    Platform.runLater(new Runnable() {
      @Override public void run() {
        initFX(fxPanel);        
      }
    });
  }

  private static void initFX(JFXPanel fxPanel) {
    // This method is invoked on JavaFX thread
    Scene scene = new SceneGenerator(videoPath).createScene();
    fxPanel.setScene(scene);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run() {
        initAndShowGUI();
      }
    });
  }
}

class SceneGenerator {    
  final Label currentlyPlaying = new Label();
  final ProgressBar progress = new ProgressBar();
  private ChangeListener<Duration> progressChangeListener;
  private String videoPath;
  public SceneGenerator(String videoPath){
      this.videoPath = videoPath;
  }
  
  public Scene createScene() {
      
    
    final StackPane layout = new StackPane();

    // determine the source directory for the playlist
    final File dir = new File(this.videoPath);


   final Media m = new Media(dir.toURI().toString());
   final MediaPlayer mp = new MediaPlayer(m);
   final MediaView mv = new MediaView(mp);
   final DoubleProperty width = mv.fitWidthProperty();
   final DoubleProperty height = mv.fitHeightProperty();
   width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
   height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
   final Button play = new Button("Tocar");
   //mv.setPreserveRatio(true);
   layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
   layout.getChildren().addAll(
      
      VBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(
        
        mv,
        HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(play).build()
      ).build()
   );
   
   mp.setOnEndOfMedia(new Runnable() {
        @Override
        public void run() {
            play.setText("Repetir");
        }
    });
   
   play.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if ("Pause".equals(play.getText())) {
                mv.getMediaPlayer().pause();
                play.setText("Tocar");
            } else if ("Repetir".equals(play.getText())){
               mp.seek(mp.getStartTime());
               play.setText("Pause");
            }
            else{
                mv.getMediaPlayer().play();
                play.setText("Pause");   
                    
              }
        }
    });
   layout.getChildren().add(1, play);
   return new Scene(layout, m.getWidth(), m.getHeight());

  }  
}
