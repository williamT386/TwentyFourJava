//DONE
/**
 *William Tang
 *Start.java
 *5/20
 *
 *This class is called as the first part of the CardLayout. It has multiple buttons to choose which card the user wants to go to, which are "Level Select",
 *"Timed Trials", "Endless", and "Instructions".
 **/

//import Color, Dimension and Font for drawing
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import the FlowLayout for use as the LayoutManager
import java.awt.FlowLayout;

//import the JPanel and JLabel to display. also imports JButton, ActionListener, ActionEvent in order to display and react to the button being clicked
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Start extends JPanel implements ActionListener
{
    private Cards card;
    private final int buttonWidth = 290;
    private final int buttonHeight = 60;

    private TimedTrials timedTrialsInstance;

    public Start(Cards cardIn)
    {
        card = cardIn;

        //sets the background to a light, close to white color. sets the layoutManager to nullLayout. creates and stores a font called myFont
        setBackground(new Color(232, 241, 242));
        setLayout(null);
        Font myFont = new Font("Arial", Font.PLAIN, 45);

        //creates the JPanel called StartPanelTop for the JLabel on the top of the JFrame. sets the color of the JPanel to a light purple and sets the
        //boundaries. The layout is set to a FlowLayout in the center. This is added
        JPanel startPanelTop = new JPanel();
        startPanelTop.setBackground(new Color(207, 186, 209));
        startPanelTop.setBounds(290,70,315,60);
        startPanelTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(startPanelTop);

        //creates the JLable called label24 inside startPanelTop. sets the color of the JPanel to a dark blue and sets the font. This is added to the
        //startPanelTop.
        JLabel label24 = new JLabel("24");
        label24.setFont(myFont);
        label24.setForeground(new Color(1, 22, 30));
        startPanelTop.add(label24);

        //creates the JPanel called StartPanelBottom for the JLabel on the bottom of the JFrame. sets the color of the JPanel to an indigo color and sets
        //the boundaries. The layout is set to a FlowLayout in the center. This is added
        JPanel startPanelBottom = new JPanel();
        startPanelBottom.setBackground(new Color(74, 78, 105));
        startPanelBottom.setBounds(290,200,315,315);
        startPanelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        add(startPanelBottom);

        //creates the JButton called practice for the JLabel inside startPanelBottom. sets the color of the JPanel to a dark blue and sets the font. The
        //ActionListener is added. This is added to the startPanelBottom.
        JButton levelSelect = new JButton("Level Select");
        levelSelect.setFont(myFont);
        levelSelect.addActionListener(this);
        levelSelect.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startPanelBottom.add(levelSelect);

        //creates the JButton called timedTrials for the JLabel inside startPanelbottom. sets the color of the JPanel to a dark blue and sets the font. The
        //ActionListener is added. This is added to the startPanelBottom.
        JButton timedTrials = new JButton("Timed Trials");
        timedTrials.setFont(myFont);
        timedTrials.addActionListener(this);
        timedTrials.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startPanelBottom.add(timedTrials);

        //creates the JButton called endless for the JLabel inside startPanelbottom. sets the color of the JPanel to a dark blue and sets the font. The
        //ActionListener is added. This is added to the startPanelBottom.
        JButton endless = new JButton("Endless");
        endless.setFont(myFont);
        endless.addActionListener(this);
        endless.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startPanelBottom.add(endless);

        //creates the JButton called instructions for the JLabel inside startPanelbottom. sets the color of the JPanel to a dark blue and sets the font. The
        //ActionListener is added. This is added to the startPanelBottom.
        JButton instructions = new JButton("Instructions");
        instructions.setFont(myFont);
        instructions.addActionListener(this);
        instructions.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        startPanelBottom.add(instructions);
    }

    //changes the status to match the button that the user clicked on. Depending on the button, the corresponding card is displayed. That instance then has
    //its focus requested so that the keyboard works.
    public void actionPerformed(ActionEvent evt)
    {
        String command = evt.getActionCommand();

        if(command.equals("Level Select"))
        {
            card.getLayoutOfCards().show(card, "Level Select");
            card.getLevelSelectInstance().requestTheFocus();
        }
        else if(command.equals("Timed Trials"))
        {
            //timedTrialsInstance.startTheTimer();
            card.getLayoutOfCards().show(card, "Timed Trials");
            card.getTimedTrialsInstance().requestTheFocus();
            card.getTimedTrialsInstance().getDrawGame().startTimer();
        }
        else if(command.equals("Endless"))
        {
            card.getLayoutOfCards().show(card, "Difficulty Select");
            card.getDifficultySelectInstance().requestTheFocus();
        }
        else if(command.equals("Instructions"))
        {
            card.getLayoutOfCards().show(card, "Instructions");
            card.getInstructionsInstance().requestTheFocus();
        }

    }

    //this is a setter for the TimedTrialsInstance in this class
    public void setTimedTrialsInstance(TimedTrials timedTrialsInstanceIn)
    {
        timedTrialsInstance = timedTrialsInstanceIn;
    }
}
