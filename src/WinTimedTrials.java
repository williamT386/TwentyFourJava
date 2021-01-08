//DONE
/**
 * William Tang
 * WinTimedTrails.java
 * 5/21/2020
 * This program displays after the user wins the TimedTrials and the name is gained. This file takes in the name, minutes, and seconds of the user from the
 * GetName instance and sends it to the WriteWinnersIntoFile instance. This class also displays the leaderboard and the user's placing. The user can exit
 * out of this class by clicking the Exit button or pressing 'e' or 'E'.
 */

//imports Color and Font for drawing
import java.awt.Color;
import java.awt.Font;

//imports JButton, JLabel, JPanel, JScrollPane, and JTextArea for drawing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//imports FlowLayout and GridLayout as layout managers
import java.awt.FlowLayout;
import java.awt.GridLayout;

//imports ActionEvent and ActionListener to check if a button was clicked
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imports FocusEvent, FocusListener, KeyEvent, and KeyListener to check if a key was typed
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WinTimedTrials extends JPanel implements ActionListener, KeyListener, FocusListener
{
    private Cards card;
    private String name;
    private int minutes, seconds, place;
    private JTextArea leaderboardTextArea;
    private JLabel placeLabel;

    //constructor
    public WinTimedTrials(Cards cardIn)
    {
        //sets card to cardIn
        card = cardIn;
        //sets the background
        setBackground(new Color(232, 241, 242));
        //sets the layout
        setLayout(null);

        //creates JPanel for the leaderboard. This uses FlowLayout
        JPanel leaderboardTitle = new JPanel();
        leaderboardTitle.setBackground(new Color(207, 186, 209));
        leaderboardTitle.setBounds(240,40,390,55);
        leaderboardTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(leaderboardTitle);

        //creates a JLabel that says "Leaderboard"
        JLabel leaderboardLabel = new JLabel("Leaderboard");
        leaderboardLabel.setFont(new Font("Arial", Font.PLAIN, 45));
        leaderboardLabel.setForeground(new Color(1, 22, 30));
        leaderboardTitle.add(leaderboardLabel);

        //creates a JPanel on the left side
        JPanel left = new JPanel();
        left.setBounds(130, 150, 160, 60);
        left.setBackground(new Color(232, 241, 242));
        left.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(left);

        //this JLabel shows what place the user got
        placeLabel = new JLabel("");
        placeLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        placeLabel.setForeground(new Color(1, 22, 30));
        left.add(placeLabel);

        //creates a JPanel on the right side
        JPanel right = new JPanel();
        right.setBounds(340, 150, 390, 300);
        right.setBackground(new Color(207, 186, 209));
        right.setLayout(new GridLayout(1,2));
        add(right);

        //this is a JTextArea for the leaderboard
        leaderboardTextArea = new JTextArea("");
        leaderboardTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        leaderboardTextArea.setLineWrap(false);
        leaderboardTextArea.setWrapStyleWord(true);
        leaderboardTextArea.setEditable(false);
        right.add(leaderboardTextArea);
        //this is a JScrollPane for the leaderboardTextArea because it will be long, so scrolling is necessary
        JScrollPane instructionsScroller = new JScrollPane(leaderboardTextArea);
        right.add(instructionsScroller);

        //creates an Exit button with ActionListener
        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setBounds(730, 485, 110, 60);
        exit.addActionListener(this);
        add(exit);

        //adds the FocusListener and KeyListener
        this.addFocusListener(this);
        this.addKeyListener(this);
    }

    //setter for name
    public void setName(String nameIn)
    {
        name = nameIn;
    }

    //setter for minutes
    public void setMinutes(int minutesIn)
    {
        minutes = minutesIn;
    }

    //setter for seconds. after the seconds is set, the leaderboard is generated. the program then determiens what grammatical ending to put at the end of the
    //place that the user got before setting the place as well.
    public void setSeconds(int secondsIn)
    {
        //generates the leaderboard and sets the text in leaderboardTextArea to the leaderboard
        seconds = secondsIn;
        WriteWinnersIntoFile instance = new WriteWinnersIntoFile(name, minutes, seconds);
        String[] linesToAdd = instance.getLeaderboard();
        String stringToAdd = "";
        for(int i = 0; i < linesToAdd.length; i++)
        {
            stringToAdd += linesToAdd[i];
            if(i < linesToAdd.length - 1)
            {
                stringToAdd += "\n";
            }
        }
        place = instance.getPlace();
        leaderboardTextArea.setText(stringToAdd);

        //determines the grammatical ending for the place and sets placeLabel with that ending and other text, including the place
        String endOfPlaceString;
        int tempPlace = place;
        while(tempPlace > 10)
        {
            tempPlace -= 10;
        }

        if(tempPlace == 1)
        {
            endOfPlaceString = "st";
        }
        else if(tempPlace == 2)
        {
            endOfPlaceString = "nd";
        }
        else if(tempPlace == 3)
        {
            endOfPlaceString = "rd";
        }
        else
        {
            endOfPlaceString = "th";
        }

        placeLabel.setText("You got " + place + endOfPlaceString + "!");
    }

    //checks if a button was pressed
    public void actionPerformed(ActionEvent e)
    {
        //stores the ActionCommand of e into command
        String command = e.getActionCommand();
        //if the user clicked the Exit button, the "Start" card is displayed
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
        }
    }

    public void keyTyped(KeyEvent e)
    {
        //stores the Keychar of e into command
        char command = e.getKeyChar();
        //if the user pressed the 'e' or 'E' button, the same occurs as if the user pressed the Exit button
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
        }
    }

    //these 4 methods exist to make sure that the KeyListener and FocusListener work
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) { }

    //this requests the focus
    public void requestTheFocus()
    {
        this.requestFocus();
    }
}
