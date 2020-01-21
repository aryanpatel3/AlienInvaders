/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aryan Patel
 */
public class Alien extends Sprite{
    
    private final int INITIAL_X = 800;
    private final int ALIEN_SPEED = 2;
    
    public Alien(int x, int y) {
        super(x, y);
        initAlien();
    }
    
    public void initAlien(){
        loadImage("src/alienSpaceship.png");
    }
    
    public void move(){
        
         //once the super aliens go off the screen, when they come back
        //their y position isn't the same so the player 
        //won't know where it's going to come from
        if (this.x < 0 - this.getW()){
            this.x = INITIAL_X;
            this.y = (int)((Math.random() * (601 - 40)));
        }
        
        this.x -= ALIEN_SPEED;
        
    }
    
}
