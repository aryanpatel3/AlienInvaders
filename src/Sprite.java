
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/*
 * 
 */

/**
 *
 * @author Aryan Patel
 */
public class Sprite {
    
    protected int x, y, w, h;
    protected boolean visible;
    protected Image image;
    
    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
    
    public Image getImage(){
        return image;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
        
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x, y, w, h);
    }
    
    protected void loadImage(String imageName){
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        
        w = image.getWidth(null);
        h = image.getHeight(null);
    }//end of loadImage method
    
}//end of sprite class
