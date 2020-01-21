
import java.io.IOException;
import javax.swing.JFrame;

/*
 */

/**
 *
 * @author 307698
 */
public class MovingSprite extends JFrame{
    
    public static void main(String[] args) throws IOException {
        MovingSprite m = new MovingSprite();
        m.setVisible(true);
    }
    
    public MovingSprite() throws IOException{
        initUI();
    }
    
    private void initUI() throws IOException{
        add(new Board()); //this is how you add items to a JFRame
        
        setTitle("Moving sprite");
        setSize(800, 600);
        
        //starts form in the center of monitor
        setLocationRelativeTo(null); 
        setResizable(false);
        
        //stop execution when closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
}
