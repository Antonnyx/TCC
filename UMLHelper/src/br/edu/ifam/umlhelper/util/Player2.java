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
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
  
  static JFrame frame;
  public static void setVideoPath(String videoPath){
      Player2.videoPath = videoPath;
  }
  
  private static void initAndShowGUI(String path) {
    // This method is invoked on Swing thread
    
    File f = new File(path);
    
    frame = new JFrame(f.getName());
    final JFXPanel fxPanel = new JFXPanel();
    frame.add(fxPanel);
    
    
    
    frame.setBounds(0, 0, (int)(1920 * 0.9),(int) (1080 * 0.9));
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
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
        initAndShowGUI(videoPath);
      }
    });
  }
}

class SceneGenerator {    
    
   
    
    private ChangeListener<Duration> progressChangeListener;
    private String videoPath;
    protected static String videoStatus = "";
    final ProgressBar progress = new ProgressBar();
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
   //width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
   //height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
   
    //PLAY IMAGE CREATION
    Image playI=new Image(getClass().getResourceAsStream("/br/edu/ifam/umlhelper/images/play_icon.png"));
    ImageView play1=new ImageView(playI);
    play1.setFitHeight(50);
    play1.setFitWidth(69);
    //STOP IMAGE CREATION
    Image stopI =new Image(getClass().getResourceAsStream("/br/edu/ifam/umlhelper/images/stop_icon.png"));
    ImageView stop1=new ImageView(stopI);
    stop1.setFitHeight(50);
    stop1.setFitWidth(69);
    //REWIND IMAGE CREATION
    Image rewindI =new Image(getClass().getResourceAsStream("/br/edu/ifam/umlhelper/images/back_icon.png"));
    ImageView rewind1=new ImageView(rewindI);
    rewind1.setFitHeight(50);
    rewind1.setFitWidth(69);
    //FORWARD IMAGE CREATION
    Image forwardI =new Image(getClass().getResourceAsStream("/br/edu/ifam/umlhelper/images/forward_icon.png"));
    ImageView forward1=new ImageView(forwardI);
    forward1.setFitHeight(50);
    forward1.setFitWidth(69);    
    
            
   final Button play = new Button("", play1);
   final Button rewind = new Button("", rewind1);
   final Button forward = new Button("", forward1);
   
   progress.setPrefWidth(1000);
   
   //mv.setPreserveRatio(true);
        
        //VBox pBar = VBoxBuilder.create().spacing(10).alignment(Pos).children(progress).build();
        HBox controls = HBoxBuilder.create().spacing(10).alignment(Pos.BOTTOM_CENTER).children(rewind, play, forward).build();
        VBox video = VBoxBuilder.create().spacing(10).alignment(Pos.TOP_CENTER).children(mv, progress, controls).build();
        //VBox controls = VBoxBuilder.create().spacing(10).alignment(Pos.BOTTOM_CENTER).children(play).build();
        
   layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
   
   layout.getChildren().addAll(video);
      
     
   
   //mp.setOnReady(() -> layout.getScene().getRoot().sizeToScene());
 
   
   
   rewind.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            mp.seek(mp.getStartTime());
            play.setGraphic(stop1);
            videoStatus = "Pausar";
        }
    });
   
   forward.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            mp.seek(mp.getTotalDuration());
            play.setGraphic(play1);
            videoStatus = "Repetir";
        }
    });
   
   
   
   mp.setOnEndOfMedia(new Runnable() {
        @Override
        public void run() {
            play.setGraphic(play1);
            videoStatus = "Repetir";
            progress.setProgress(100);
        }
    });
   
   mp.setOnReady(new Runnable() {
        @Override
        public void run() {
            mp.setAutoPlay(true);
            videoStatus = "Pausar";
            play.setGraphic(stop1);
            if(Player2.frame.getWidth() != m.getWidth()+20 && Player2.frame.getHeight()!= (m.getHeight()+170)){
                Player2.frame.setBounds(0, 0, m.getWidth()+20, m.getHeight()+170);
                Player2.frame.setLocationRelativeTo(null);
                progress.setPrefWidth(Player2.frame.getWidth());
            }
            
            setCurrentlyPlaying(mv.getMediaPlayer());
        }
    });
   
   
   
   play.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
           
            
            if(Player2.frame.getWidth() != m.getWidth()+20 && Player2.frame.getHeight()!= (m.getHeight()+170)){
                
                Player2.frame.setBounds(0, 0, m.getWidth()+20, m.getHeight()+170);
                Player2.frame.setLocationRelativeTo(null);
                progress.setPrefWidth(Player2.frame.getWidth());
                
            }
            
            if ("Pausar".equals(videoStatus)) {
                mv.getMediaPlayer().pause();
                play.setGraphic(play1);
                videoStatus = "Tocar";
            } else if ("Repetir".equals(videoStatus)){
               mp.seek(mp.getStartTime());
               play.setGraphic(stop1);
               videoStatus = "Pausar";
               
            }
            else{
                mv.getMediaPlayer().play();
                play.setGraphic(stop1);
                videoStatus = "Pausar"; 
                              
              }
        }
    });
   
    mv.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
      @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
        setCurrentlyPlaying(newPlayer);
      }

   
    });
   //layout.getChildren().add(1, play);
   
   
   return new Scene(layout, (int)(1920*0.8),(int) (1080*0.8));

  }
 private void setCurrentlyPlaying(MediaPlayer newPlayer) {
        progress.setProgress(0);
        progressChangeListener = new ChangeListener<Duration>() {
        @Override 
        public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
            progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
        }
        };
        newPlayer.currentTimeProperty().addListener(progressChangeListener);
    }  
}
