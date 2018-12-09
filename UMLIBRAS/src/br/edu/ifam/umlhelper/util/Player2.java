/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.umlhelper.util;

import java.awt.Toolkit;
import java.io.File;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javax.swing.JFrame;
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
  public static void setInternalVideoPath(String videoPath){
      String path = new File(".").getAbsolutePath();
      path = path.replaceAll(".", "");
      path = path + videoPath;
      System.out.println("Path: "  + path);
      Player2.videoPath = path;
      
  }  
  
  private static void initAndShowGUI(String path) {
    // This method is invoked on Swing thread
    
    File f = new File(path);
    
    frame = new JFrame(f.getName());
    final JFXPanel fxPanel = new JFXPanel();
    frame.add(fxPanel);
    
    
    
    frame.setBounds(0, 0, (int)(1920 * 0.9),(int) (1080 * 0.9));
    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Player2.class.getResource("/br/edu/ifam/umlhelper/images/icon_libras.png")));
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
      
    
    StackPane layout = new StackPane();

    // determine the source directory for the playlist
     File dir = new File(this.videoPath);
    

   final Media m = new Media(dir.toURI().toString());
   
   final MediaPlayer mp = new MediaPlayer(m);
   final MediaView mv = new MediaView(mp);
   
   DoubleProperty width = mv.fitWidthProperty();
   DoubleProperty height = mv.fitHeightProperty();
   //width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
   //height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
   
    //PLAY IMAGE CREATION
    Image playI=new Image(getClass().getResourceAsStream("/br/edu/ifam/umlhelper/images/play_icon.png"));
    final ImageView play1=new ImageView(playI);
    play1.setFitHeight(50);
    play1.setFitWidth(69);
    //STOP IMAGE CREATION
    Image stopI =new Image(getClass().getResourceAsStream("/br/edu/ifam/umlhelper/images/stop_icon.png"));
    final ImageView stop1=new ImageView(stopI);
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
   Button rewind = new Button("", rewind1);
   Button forward = new Button("", forward1);
   
   progress.setPrefWidth(1000);
   
   //mv.setPreserveRatio(true);
        
        //VBox pBar = VBoxBuilder.create().spacing(10).alignment(Pos).children(progress).build();
        HBox controls = HBoxBuilder.create().spacing(10).alignment(Pos.BOTTOM_CENTER).children(rewind, play, forward).build();
        VBox video = VBoxBuilder.create().spacing(10).alignment(Pos.TOP_CENTER).children(mv, progress, controls).build();
        //VBox controls = VBoxBuilder.create().spacing(10).alignment(Pos.BOTTOM_CENTER).children(play).build();
        
   layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
   
   layout.getChildren().addAll(video);
      
     
   
   //mp.setOnReady(() -> layout.getScene().getRoot().sizeToScene());
   mp.setOnError(new Runnable() {
        @Override
        public void run() {
            System.out.println("MediaPlayer Erro:");
            String message = mp.errorProperty().get().getMessage();
            System.out.println(message);

        }
    });
   
   m.setOnError(new Runnable() {
        @Override
        public void run() {
            System.out.println("Media Erro:");
            String message = m.errorProperty().get().getMessage();
            System.out.println(message);
        }
    });
   
   mv.setOnError(new EventHandler<MediaErrorEvent>() {
        @Override
        public void handle(MediaErrorEvent event) {
            System.out.println("Media View Erro:");
            event.getMediaError().toString();
        }
    });
   
   
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
             if(mv == null) {
                 System.out.println("MediaView NULL");
            }
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
 private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
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
