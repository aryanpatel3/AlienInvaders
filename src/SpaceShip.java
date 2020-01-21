
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * Spaceship
 */

/**
 *
 * @author Aryan Patel
 */
public class SpaceShip extends Sprite{
    
    private int dx, dy;
    private Image image;
    private ArrayList<Missile> missiles;
    
    public SpaceShip(int x, int y){
        super(x, y);
        initSpaceShip();
        missiles = new ArrayList<>();
    }//end of constructor
    
    public void initSpaceShip(){
        loadImage("src/spaceship.png");
    }
    
    public void move(){
        x += dx;
        y += dy;
        
        //spaceship stops at left side of form
        if (x < 1){
            x = 1;
        }
        //spaceship stops at right side of form
        if (x > 800 - this.getW()){
            x = 800 - this.getW();
        }
        //spaceship stops at bottom of form
        if (y > 600 - 80){
            y = 600 - 80;
        }
        //spaceship stops at top of form
        if (y < 1){
            y = 1;
        }
    }//end of move method
    
    //plays a sound when each missile is fired
    public void fire(){
        
        missiles.add(new Missile(x + w, y + h/2));
        playMissileSound(); 
        
    }
   public ArrayList<Missile> getMissiles(){
        return missiles;
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT){ dx = -2;}
        if (key == KeyEvent.VK_RIGHT){ dx = 2;}
        if (key == KeyEvent.VK_UP){ dy = -2;}
        if (key == KeyEvent.VK_DOWN){ dy = 2;}
        if (key == KeyEvent.VK_SPACE) {fire();}
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT){ dx = 0;}
        if (key == KeyEvent.VK_RIGHT){ dx = 0;}
        if (key == KeyEvent.VK_UP){ dy = 0;}
        if (key == KeyEvent.VK_DOWN){ dy = 0;}
    }
    private void playMissileSound () 
    {
        try {
         // Open an audio input stream.           
          File soundFile = new File("src/missileShot.wav"); //you could also get the sound file with an URL
          AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
}
}//end of class
