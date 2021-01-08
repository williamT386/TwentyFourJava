//3536 lines as of 5/21/2020

//DONE
/**
 * William Tang
 * Game.java
 * 5/21/2020
 *
 * This class creates the JFrame and another instance of Cards. This class also sets the JFrame to the correct size, location, visibility, and default close
 * operation. This method also adds the JPanel to the JFrame.
 */

//imports the JFrame and JPanel
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel
{
    //main method. does what is mentioned above
    public static void main(String[] args)
    {
        //makes the JFrame and JPanel
        JFrame bigFrame = new JFrame("24");
        Cards myPanel = new Cards();

        //attaches the JPanel from Cards to the JFrame and sets the JFrame to the correct size, location, visibility, and default close operation
        bigFrame.add(myPanel);
        bigFrame.setSize(900, 600);
        bigFrame.setLocation(100, 100);
        bigFrame.setVisible(true);
        bigFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
