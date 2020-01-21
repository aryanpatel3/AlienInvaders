/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aryan
 */
public class SuperAlien extends Sprite{
    
    private final int INITIAL_X = 800;
    private final int ALIEN_SPEED = 4;
    
    public SuperAlien(int x, int y){
        super(x, y);
        initSuperAlien();
    }
    
    public void initSuperAlien(){
        loadImage("src/superAlien.png");
    }
    
    public void move(){
        
        //once the super aliens go off the screen, when they come back
        //their y position isn't the same so the player 
        //won't know where it's going to come from
        if (this.x < 0 - this.getW()){
            this.x = INITIAL_X;
            this.y = (int)((Math.random() * (601- 80)) );
        }
        
        this.x -= ALIEN_SPEED;
        
    }
}
