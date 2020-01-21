
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;



/**
 *
 * @author Aryan Patel
 */
public class Board extends JPanel implements ActionListener{
    private SpaceShip spaceShip;
    private Timer timer;
    private Timer timerWait;
    private final int DELAY = 10; 
    private ArrayList<Alien>aliens;
    private ArrayList<SuperAlien>superAliens;
    private boolean inGame;
    private final int NUM_ALIENS = 10;
    private final int NUM_SUPER_ALIENS = 3;
    private int alienPos[][];
    private int superAlienPos[][];
    private String backgroundSound;
    private InputStream in;
    private AudioStream audioStream;
    private int hits;
    private Label lblPause;
    private JButton btnPause;
    private int lives;
    
    public  Board() throws IOException {
      initBoard();
      playSound();
    }
    
     private void initBoard(){
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);
      
        btnPause = new JButton("Pause");
        btnPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPauseActionPerformed(evt);
            }
        });
        
        add(btnPause);
        
        initSuperAliens();
        initAliens();
        
        lives = 3;
        
        spaceShip = new SpaceShip(40, 60);
        
        timer = new Timer(DELAY,this);
        timer.start();
        
        timerWait = new Timer(1000, this);
        
        timerWait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timerWaitActionPerformed(evt);
            }
        });
        
        inGame = true;
    }
     
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        if(inGame == true){
            doDrawing(g);
        }else{
            drawGameOver(g);
        }
        
    }
    
    public void doDrawing(Graphics g) {
        
        Graphics2D g2D= (Graphics2D) g;
        
        if(spaceShip.isVisible()){
            g2D.drawImage(spaceShip.getImage(),spaceShip.getX(),spaceShip.getY(), this);
        }

        if(spaceShip.getMissiles() != null){
        ArrayList<Missile>missiles= spaceShip.getMissiles();
        for(Missile m: missiles){
            if(m.visible){
            g2D.drawImage(m.getImage(),m.getX(),m.getY(),this);
            }
        } 
        }
        
        for(Alien a: aliens){
            if(a.isVisible()){
                g2D.drawImage(a.getImage(),a.getX(),a.getY(), this);
            } 
        }
        
        for(SuperAlien sa: superAliens){
            if (sa.isVisible()){
                g2D.drawImage(sa.getImage(), sa.getX(), sa.getY(), this);
            }
        }
        
        g.setColor(Color.WHITE);
        g.drawString("Aliens left: " + aliens.size(), 5, 15);
        g.drawString("Super Aliens left: " + superAliens.size(), 5, 30);
        g.drawString("Lives left: " + lives, 730, 15);
    }
    private void initSuperAliens(){
        
        superAlienPos = new int[NUM_SUPER_ALIENS][2];
        
        superAliens = new ArrayList<>();
        
        for(int i=0; i < NUM_SUPER_ALIENS; i++){
        superAlienPos[i][0] =(int)((Math.random() * 3001) + 1000 ); 
        superAlienPos[i][1] =(int)((Math.random() * (601 - 80)) );
        superAliens.add(new SuperAlien(superAlienPos[i][0], superAlienPos[i][1]));
        }
      
    }
    private void updateSuperAliens(){
         if(superAliens.isEmpty() && aliens.isEmpty()){
            inGame = false;
            return;
        }
         for(int i= 0 ;i < superAliens.size(); i++){
             SuperAlien sa = superAliens.get(i);
             
             if(sa.isVisible()){
                 sa.move();
             }else{
                superAliens.remove(i);
           }
         }          
     }
     private void initAliens() {
         
        alienPos = new int[NUM_ALIENS][2];
        
        aliens = new ArrayList<>();
        
        for(int i=0; i < NUM_ALIENS; i++){
        alienPos[i][0] =(int)((Math.random() * 3000) + 850); 
        alienPos[i][1] =(int)((Math.random() * (601 - 40)));
        aliens.add(new Alien(alienPos[i][0], alienPos[i][1]));
        }
     }
     
     private void updateAliens(){
         if(aliens.isEmpty()){
            inGame = false;
            return;
        }
         for(int i= 0 ;i < aliens.size(); i++){
             Alien a = aliens.get(i);
             
             if(a.isVisible()){
                 a.move();
             }else{
                 aliens.remove(i);
           }
         }          
     }
    
    private void updateShip(){
        spaceShip.move();
        repaint(spaceShip.getX()-1,spaceShip.getY()-1,spaceShip.getW()+2,spaceShip.getH()+2);
    }
    
    private void updateMissiles() {
       ArrayList<Missile>missiles = spaceShip.getMissiles();
       
       for(int i = 0;i< missiles.size();i++){
           Missile missile = missiles.get(i);
           if(missile.isVisible()){
               missile.move();
           }else{
               missiles.remove(i);
           }
       }
    }
    
    public void checkCollisions(){
         Rectangle shipBounds = spaceShip.getBounds();
          
         for(Alien a: aliens){
             Rectangle alienBounds = a.getBounds();
             
             if(shipBounds.intersects(alienBounds)){
                timer.stop();
                lives -= 1;
                AudioPlayer.player.stop(audioStream);
                for(int i=0; i < aliens.size(); i++){
                    aliens.remove(i);
                    alienPos[i][0] =(int)((Math.random() * 3000) + 850); 
                    alienPos[i][1] =(int)((Math.random() * (601 - 40)));
                    aliens.add(new Alien(alienPos[i][0], alienPos[i][1]));
        }
        
                timerWait.start();
                if(lives == 0 ){
                    spaceShip.setVisible(false);
                    a.setVisible(false);
                    inGame = false; 
                }
             }
         }
         
         for(SuperAlien sa: superAliens){
             Rectangle superAlienBounds = sa.getBounds();
             
             if(shipBounds.intersects(superAlienBounds)){
                timer.stop();
                lives -= 1;
                AudioPlayer.player.stop(audioStream);
                for(int i=0; i < superAliens.size(); i++){
                    superAliens.remove(i);
                    superAlienPos[i][0] =(int)((Math.random() * 3000) + 850); 
                    superAlienPos[i][1] =(int)((Math.random() * (601 - 40)));
                    superAliens.add(new SuperAlien(superAlienPos[i][0], superAlienPos[i][1]));
        }
                timerWait.start();
                if(lives == 0 ){
                    spaceShip.setVisible(false);
                    sa.setVisible(false);
                    inGame = false; 
                }
             }
         }
         
          ArrayList<Missile> missiles = spaceShip.getMissiles();
             for(Missile m: missiles){
                Rectangle missileBounds = m.getBounds();
                
                for(Alien a: aliens){
                    Rectangle alienBounds = a.getBounds();
                
                    if(missileBounds.intersects(alienBounds)){
                        a.setVisible(false);
                    }
                }
                for(SuperAlien sa: superAliens){
                    Rectangle superAlienBounds = sa.getBounds();
                    
                    //if the super alien is hit 3 times, it dies
                    if(missileBounds.intersects(superAlienBounds)){
                        hits += 1;
                        if(hits == 6){
                            hits =0;
                           sa.setVisible(false);
                        }
                    }
                }
            }
    }
    
    private void playSound() throws FileNotFoundException, IOException{
        backgroundSound = "src/moveItOut.wav";
        
        //open the sound file as a jave input stream
        in = new FileInputStream(backgroundSound);
        
        //create an audiostream from the inputstream
        audioStream = new AudioStream(in);
        
        //play the audio clip with the audio player 
        AudioPlayer.player.start(audioStream);
    }
    
     private void drawGameOver(Graphics g){
        String msg = "Game over";
        Font small = new Font("sjhdjk", Font.BOLD,14);
        FontMetrics fm = getFontMetrics(small); 
        
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(800 - fm.stringWidth(msg))/2,600/2);
    }
    
    public void inGame(){
      if(inGame == false){
          timer.stop();
          
          btnPause.setVisible(false);
          AudioPlayer.player.stop(audioStream);
      }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        inGame();
        checkCollisions();
        updateShip();
        updateAliens();
        updateSuperAliens();
        updateMissiles();
        repaint();
       
    }
    public int getLives(){
        return this.lives;
    }
     private void timerWaitActionPerformed(java.awt.event.ActionEvent evt){
         
        timerWait.stop();
         
        
        JOptionPane.showMessageDialog(null, "You have " + getLives() + " lives left");
        AudioPlayer.player.start(audioStream);
        
        timer.start();
        
    }
    private void btnPauseActionPerformed(java.awt.event.ActionEvent evt){
        if (btnPause.getText().equals("Pause")){
            btnPause.setText("Resume");
            AudioPlayer.player.stop(audioStream);
            timer.stop();
        } else if (btnPause.getText().equals("Resume")){
            
            btnPause.setText("Pause");
            AudioPlayer.player.start(audioStream);
            timer.start();
        }
        
    }
    private class TAdapter extends KeyAdapter{
        
        @Override
        public void keyReleased(KeyEvent e){
            spaceShip.keyReleased(e);
        }
        
        @Override
        public void keyPressed(KeyEvent e){
            spaceShip.keyPressed(e);
        }
    }
}//end board
