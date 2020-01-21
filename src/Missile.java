/*
 * 
 */

/**
 *
 * @author Aryan Patel
 */
public class Missile extends Sprite{
    
    private final int BOARD_WIDTH = 800;
    private final int MISSILE_SPEED = 50;
    
    public Missile(int x, int y){
        super(x, y);
        initMissile();
    }
    
    public void initMissile(){
        
        loadImage("src/missile.png");
        
    }
    
    public void move(){
        
        this.x += MISSILE_SPEED;
        if (this.x > BOARD_WIDTH) {
            this.visible= false;
        
    }
    }
}
