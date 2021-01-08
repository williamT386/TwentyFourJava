//DONE
/**
 * William Tang
 * GetName.java
 * 5/21/2020
 *
 * If the user won the TimedTrials instance, this program gets their first name. After the user clicked the Enter button for their first name, the "Win Timed
 * Trials" card is displayed. The user cannot exit out of this card. The only way to switch out of this card is to type their first name.
 */

//imports the Color and Font for drawing
import java.awt.Color;
import java.awt.Font;

//imports the JButton, JLabel, JPanel, and JTextField for drawing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//imports FlowLayout as a layout manager
import java.awt.FlowLayout;

//imports ActionEvent and ActionListener to check if a button is pressed
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetName extends JPanel implements ActionListener
{
    private Cards card;

    private JTextField nameTextField;

    private int minutes, seconds;

    //constructor
    public GetName(Cards cardIn)
    {
        //adds cardIn to card
        card = cardIn;

        //sets the background color
        setBackground(new Color(232, 241, 242));
        //sets the layout to null
        setLayout(null);

        //creates a JPanel on top with FlowLayout
        JPanel top = new JPanel();
        top.setBackground(new Color(207, 186, 209));
        top.setBounds(230,40,430,55);
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(top);

        //creats a JLabel that tells the user that they won
        JLabel labelWin = new JLabel("Congrats! You Win!");
        labelWin.setFont(new Font("Arial", Font.PLAIN, 45));
        labelWin.setForeground(new Color(1, 22, 30));
        top.add(labelWin);

        //creates a JPanel in the center
        JPanel center = new JPanel();
        center.setBounds(240, 150, 390, 300);
        center.setBackground(new Color(207, 186, 209));
        center.setLayout(null);
        add(center);

        //creates a JLabel to ask the user for their first name
        JLabel nameLabel = new JLabel("Enter your first name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        nameLabel.setForeground(new Color(1, 22, 30));
        nameLabel.setBounds(20,30,370,40);
        center.add(nameLabel);

        //creates a JTextField for the user to type their first name
        nameTextField = new JTextField(10);
        nameTextField.setFont(new Font("Arial", Font.BOLD, 20));
        nameTextField.setEditable(true);
        nameTextField.setBounds(25,150,180,40);
        center.add(nameTextField);

        //creates an enter button to click after the user typed their first name in the JTextField
        JButton enter = new JButton("Enter");
        enter.setFont(new Font("Arial", Font.PLAIN, 20));
        enter.setBounds(220,150,140,40);
        enter.addActionListener(this);
        center.add(enter);

    }

    //checks if a button was clicked
    public void actionPerformed(ActionEvent e)
    {
        //adds the ActionCommand of e into command
        String command = e.getActionCommand();

        //occurs if the user clicked the Enter button and the JTextField has some String inside
        if(command.equals("Enter"))
        {
            if(!nameTextField.getText().equals(""))
            {
                //sets the name by taking the String in the JTextField. the minutes and seconds are then set. The "Win Timed Trials" card is then displayed
                //and the WinTimedTrialsInstance has the focus requested.
                card.getWinTimedTrialsInstance().setName(nameTextField.getText());
                card.getWinTimedTrialsInstance().setMinutes(minutes);
                card.getWinTimedTrialsInstance().setSeconds(seconds);
                card.getLayoutOfCards().show(card, "Win Timed Trials");
                card.getWinTimedTrialsInstance().requestTheFocus();
            }
        }
    }

    //this is a setter for minutes
    public void setMinutes(int minutesIn)
    {
        minutes = minutesIn;
    }

    //this is a setter for seconds
    public void setSeconds(int secondsIn)
    {
        seconds = secondsIn;
        getTimeUsedNotLeft();

    }

    //after all the minutes and seconds data are inputted, this method calculates the amount of time that was used to win, not the amount of time that is
    //remaining. This is essential for the leaderboard in a separate class to check who was the fastest, not who has the most time left
    public void getTimeUsedNotLeft()
    {
        if(seconds != 0)
        {
            seconds = 60 - seconds;
            minutes = 9 - minutes;
        }
        else
        {
            minutes = 10 - minutes;
        }
    }
}
