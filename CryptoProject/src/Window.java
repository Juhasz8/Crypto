import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Window extends JComponent{


    public JPanel mainPanel;
    private JButton button1;
    private JButton Translate;

    private ArrayList<Position> savedLinePositions = new ArrayList<>();
    private int MAX_LINES = 50; //one less is actually visible

    private int strokeSize = 3;

    private  int widthScale = 15;

    public Window()
    {
        Random rand = new Random();

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Graphics2D g2D = (Graphics2D) mainPanel.getGraphics();
                g2D.setStroke(new BasicStroke(strokeSize));

                //at this point this stores the starting coordinates of the first line
                Position lastPos = new Position(25, 350);
                savedLinePositions.add(new Position(lastPos));

                for (int i=0; i<500; i++)
                {
                    Position travelPos = new Position(widthScale, rand.nextInt(100) -50);
                    g2D.setColor(GetColor(travelPos.y));

                    Position from = savedLinePositions.get(savedLinePositions.size()-1);
                    Position to = new Position(savedLinePositions.get(savedLinePositions.size()-1)).Add(travelPos);
                    g2D.drawLine(from.x, from.y, to.x, to.y);

                    g2D.setColor(new Color(50, 50, 50));
                    g2D.fillOval(from.x-3, from.y-3, 6, 6);

                    savedLinePositions.add(to);

                    if(savedLinePositions.size()-1 == MAX_LINES)
                    {
                        ReDrawCurrentLines(i-MAX_LINES);
                    }

                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(100);
                        //Thread.sleep(1000);
                    }
                    catch (Exception exception)
                    {
                        System.out.println("TimeUnit Exception occurred!");
                    }
                }

            }
        });
    }

    public void ReDrawCurrentLines(int translateIndex)
    {
        Graphics2D g2D = (Graphics2D) mainPanel.getGraphics();
        g2D.setStroke(new BasicStroke(strokeSize));
        g2D.setColor(new Color(50, 50, 50));

        for (int i=0; i<savedLinePositions.size()-1; i++)
        {
            Position from = savedLinePositions.get(i);
            Position to = savedLinePositions.get(i+1);
            g2D.drawLine(from.x, from.y, to.x, to.y);
            g2D.fillOval(from.x-3, from.y-3, 6, 6);
            //System.out.println("deleted line drawn from: " + from.x + " " + from.y + " to: " + to.x +" "+ to.y);
        }

        savedLinePositions.remove(0);

        for (int i=0; i<savedLinePositions.size()-1; i++)
        {
            Position from = new Position(savedLinePositions.get(i));

            if(i==0)
                from.Add(new Position(-widthScale, 0));

            Position to = new Position(savedLinePositions.get(i+1).Add(new Position(-widthScale, 0)));

            g2D.setColor(GetColor(to.y - from.y));
            g2D.drawLine(from.x, from.y, to.x, to.y);

            g2D.setColor(new Color(50, 50, 50));
            g2D.fillOval(from.x-3, from.y-3, 6, 6);
            //System.out.println("new Line drawn from: " + from.x+ " " + from.y + " to "+to.x+ " " +to.y);

            savedLinePositions.set(i, new Position(from));
            savedLinePositions.set(i+1, new Position(to));
        }
    }


    public Color GetColor(int travelPosY)
    {
        Color returnColor = travelPosY < 0 ? new Color(0, 200, 0) : new Color(200, 0, 0);
        return returnColor;
    }

}
