package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label musicLabel;
    @FXML
    private ProgressBar musicBar;
    @FXML
    private ComboBox<String> playbackSpeedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ImageView volumeIcon;
    @FXML
    private Button playBtn, pauseBtn, resetBtn, prevBtn, nextBtn;

    private File[] files;
    private List<File> songs;

    private Media media;
    private MediaPlayer mediaPlayer;

    private Image image;

    private Timer timer;
    private TimerTask timerTask;

    private boolean playing;
    private int songNo;
    private int[] playbackSpeeds = {50,100,150,200};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        files = new File("music").listFiles();
        songs = new ArrayList<>();

        if (files != null) {
            for(File file : files) {
                songs.add(file);
                System.out.println(file);
            }
        }

        media = new Media(songs.get(songNo).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        musicLabel.setText(songs.get(songNo).getName());

        for (int i = 0; i < playbackSpeeds.length; i++) {
            playbackSpeedBox.getItems().add(Integer.toString(playbackSpeeds[i]) + "%");
        }

        playbackSpeedBox.setOnAction(this::modifyPlaybackSpeed);

        image = new Image(getClass().getResourceAsStream("Volume_Icon.png"));
        volumeIcon.setImage(image);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

        mediaPlayer.setVolume(0.5);
        musicBar.setStyle("-fx-accent: Orange");
    }

    public void playMusic() {
        startTimer();
        modifyPlaybackSpeed(null);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
        /*playBtn.setText("Pause");
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            playBtn.setText("Play");
        }*/
    }

    public void pauseMusic() {
        cancelTimer();
        mediaPlayer.pause();
    }

    public void resetMusic() {
        musicBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0.0));
    }

    public void previousMusic() {
        if (songNo > 0)
            songNo--;
        else
            songNo = songs.size() - 1;

        if (playing)
            cancelTimer();

        mediaPlayer.stop();
        media = new Media(songs.get(songNo).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        musicLabel.setText(songs.get(songNo).getName());
        playMusic();
    }

    public void nextMusic() {

        if (songNo < songs.size() - 1)
            songNo++;
        else
            songNo = 0;

        if (playing)
            cancelTimer();

        mediaPlayer.stop();
        media = new Media(songs.get(songNo).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        musicLabel.setText(songs.get(songNo).getName());
        playMusic();
    }

    public void modifyPlaybackSpeed(ActionEvent e) {
        if (playbackSpeedBox.getValue() == null)
            mediaPlayer.setRate(1);
        else
            mediaPlayer.setRate(Integer.parseInt(playbackSpeedBox.getValue().replace("%","")) * 0.01);
    }

    public void muteMusic() {
        volumeSlider.setValue(0);
        mediaPlayer.setVolume(0);

    }

    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                playing = true;
                double timeNow = mediaPlayer.getCurrentTime().toSeconds();
                double timeEnd = media.getDuration().toSeconds();
                musicBar.setProgress(timeNow/timeEnd);

                if (timeNow/timeEnd == 1)
                    cancelTimer();
            }
        };

        timer.schedule(timerTask, 0,1000);
    }

    public void cancelTimer() {
        playing = false;
        timer.cancel();
    }
}
