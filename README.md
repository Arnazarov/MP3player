# MP3player
Simple JavaFX application that plays music.

MP3player will get music files and plays them using JavaFX Media and MediaPlayer.

The app written in MVC architecture and has following classes, files and methods: 
  - Main.java (Main class to run the program)
      - main() method launches the app
      - start() method starts the app (includes anonymous inner class)
  
  - Controller.java (Data & Application logic)
      - initialize() method initializes the View (includes anonymous inner class and method reference)
      - playMusic() method plays media
      - pauseMusic() method pauses media
      - resetMusic() method resets media
      - perviousMusic() method changes to previous song
      - nextMusic() method changes to the next song
      - modifyPlaybackSpeed() method changes the playback speed
      - muteMusic() method mutes media
      - startTimer() and endTimer() methods changes progress bar (includes lambda expression) 
 
 - View.fxml (User-interface)
 
 Screenshot:
 ![Image of example screenshot] (https://github.com/Arnazarov/RSSNewsReader/blob/master/Screenshot.JPG)
