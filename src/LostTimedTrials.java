/**
 * William Tang
 * LostTimedTrails.java
 * 5/21/2020
 *
 * This class works when the user loses the TimedTrials gamemode. The user is informed that they lost and they are told that they might be luckier next time.
 * The user can exit this by clicking the Exit button or typing 'e' or 'E'.
 */

//imports Color and Font for drawing
import java.awt.Color;
import java.awt.Font;

//imports JButton, JLabel, JPanel, and JTextArea for drawing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//imports FlowLayout as layout manager
import java.awt.FlowLayout;

//imports ActionEvent and ActionListener to check if a button is pressed
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imports FocusEvent, FocusListener, KeyEvent, and KeyListener to check if a key is typed
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LostTimedTrials extends JPanel implements ActionListener, KeyListener, FocusListener
{
    private Cards card;

    //constructor
    public LostTimedTrials(Cards cardIn)
    {
        //card is set to cardIn
        card = cardIn;
        //the background is drawn
        setBackground(new Color(232, 241, 242));
        //the layout is set to null
        setLayout(null);

        //a panel is made for informing the user that they lost
        JPanel loseTitle = new JPanel();
        loseTitle.setBackground(new Color(207, 186, 209));
        loseTitle.setBounds(240,40,390,55);
        loseTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(loseTitle);

        //a JLabel that says the user is lost is added to loseTitle
        JLabel loseLabel = new JLabel("You lose...");
        loseLabel.setFont(new Font("Arial", Font.PLAIN, 45));
        loseLabel.setForeground(new Color(1, 22, 30));
        loseTitle.add(loseLabel);

        //an Exit JButton is added with the ActionListener
        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setBounds(730, 485, 110, 60);
        exit.addActionListener(this);
        add(exit);

        //a JPanel is created in the center
        JPanel center = new JPanel();
        center.setBounds(240, 340, 440, 55);
        center.setBackground(new Color(232, 241, 242));
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(center);

        //a JLabel is placed in the center to tell the user good luck next time
        JLabel placeLabel = new JLabel("Better Luck Next Time!");
        placeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        placeLabel.setForeground(new Color(1, 22, 30));
        center.add(placeLabel);

        //adds the KeyListener and FocusListener
        addKeyListener(this);
        addFocusListener(this);
    }

    //check if a button is pressed
    public void actionPerformed(ActionEvent e)
    {
        //gets the ActionCommand and stores it into command
        String command = e.getActionCommand();
        //if the user clicked exit, the "Start" card is displayed
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
        }
    }

    //checks if a key is pressed
    public void keyTyped(KeyEvent e)
    {
        //the KeyChar of e is stored into command
        char command = e.getKeyChar();
        //if the user typed 'e' or 'E', the same effect occurs as if the user pressed the Exit button
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
        }
    }

    //these 4 methods exist to make sure that KeyListener and FocusListener work
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}

    //this requests the focus
    public void requestTheFocus()
    {
        this.requestFocus();
    }
}
