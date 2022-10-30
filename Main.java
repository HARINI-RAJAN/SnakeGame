package snakeGame;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    JFrame frame;
    Main()
    {
        frame=new JFrame("SNAKE GAME");
        frame.setBounds(10,10,905,660);
        //to have a constant sized game screen
        frame.setResizable(false);
        GamePanel panel=new GamePanel();
        panel.setBackground(Color.DARK_GRAY);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {

        Main main=new Main();
    }
}
