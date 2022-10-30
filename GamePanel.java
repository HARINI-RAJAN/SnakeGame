package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private int[] snakexlength = new int[750];
    private int[] snakeylength = new int[750];
    private int[] xpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,800};
    private int[] ypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525};
    private Random random=new Random();
    private int foodx,foody;
    private int lengthOfSnake = 3;
    //At start snake game starts with the snake moving right
    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean gameOver=false;
    int moves = 0;
    int score=0;


    GamePanel() {
        addKeyListener(this);
          setFocusable(true);
          setFocusTraversalKeysEnabled(true);
        timer=new Timer(delay,this);
        timer.start();
        newFood();
    }

    private void newFood() {
        foodx=xpos[random.nextInt(30)];
        foody=ypos[random.nextInt(19)];
        //to avoid the food from appearing on the snake
        for(int i=lengthOfSnake-1;i>=0;i--)
        {
            if(snakexlength[i]==foodx && snakeylength[i]==foody)
            {
                newFood();
            }
        }
    }

    private ImageIcon snakeTitle = new ImageIcon(getClass().getResource("pictures/gameTitle.jpeg"));
    private ImageIcon leftMouth = new ImageIcon(getClass().getResource("pictures/leftMouth1.png"));
    private ImageIcon rightMouth = new ImageIcon(getClass().getResource("pictures/rightMouth1.png"));
    private ImageIcon upMouth = new ImageIcon(getClass().getResource("pictures/upMouth1.png"));
    private ImageIcon downMouth = new ImageIcon(getClass().getResource("pictures/downMouth1.png"));
    private ImageIcon snakeImage=new ImageIcon(getClass().getResource("pictures/snakeBody3.png"));
    private ImageIcon food = new ImageIcon(getClass().getResource("pictures/food.jpeg"));
    private Timer timer;
    private int delay=100;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //to draw rectangles with white border
        g.setColor(Color.white);
        //for the heading Area
        g.drawRect(24, 10, 851, 57);
        //for the gaming area
        g.drawRect(24, 74, 851, 520);
        //adding the gameTitle image
        snakeTitle.paintIcon(this, g, 25, 11);
        //setting the gamingArea to black
        //changing the graphic color and then using it to fill rectangle
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 519);
        if (moves == 0) {
            snakexlength[0] = 100;
            snakexlength[1] = 75;
            snakexlength[2] = 50;

            snakeylength[0] = 100;
            snakeylength[1] = 100;
            snakeylength[2] = 100;
            moves++;
        }
        if (left) {
            leftMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (right) {
            rightMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (up) {
            upMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        if (down) {
            downMouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
        }
        for(int i=1;i<lengthOfSnake;i++)
        {
            snakeImage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
        }
        food.paintIcon(this,g,foodx,foody);
        if(gameOver)
        {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("GAME OVER",300,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
            g.drawString("Press Space to RESTART",330,360);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString("Score : "+ score,750,30);
        g.drawString("Length : "+lengthOfSnake,750,50);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];
        }
        if(left)
        {
            snakexlength[0]=snakexlength[0]-25;
        }
        if(right)
        {
            snakexlength[0]=snakexlength[0]+25;
        }
        if(up)
        {
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down)
        {
            snakeylength[0]=snakeylength[0]+25;
        }
        if (snakexlength[0]>850) snakexlength[0]=25;
        if(snakexlength[0]<25) snakexlength[0]=850;
        if (snakeylength[0]>555) snakeylength[0]=75;
        if(snakeylength[0]<75) snakeylength[0]=555;
        collidesWithFood();
        collideWithBody();
        repaint();
    }

    private void collideWithBody() {
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0])
            {
                timer.stop();
                gameOver=true;
            }
        }

    }

    private void collidesWithFood() {
        if(snakexlength[0]==foodx && snakeylength[0]==foody)
        {
            newFood();
            lengthOfSnake++;
            snakexlength[lengthOfSnake-1]=snakexlength[lengthOfSnake-2];
            snakeylength[lengthOfSnake-1]=snakeylength[lengthOfSnake-2];
            score++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_SPACE && gameOver==true)
      {
          restart();
      }
      if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right))
      {
            left=true;
            right=false;
            up=false;
            down=false;
            moves++;
      }
      if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left))
       {
            left=false;
            right=true;
            up=false;
            down=false;
           moves++;
       }
       if(e.getKeyCode()==KeyEvent.VK_UP && (!down))
        {
            left=false;
            right=false;
            up=true;
            down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN &&(!up))
        {
            left=false;
            right=false;
            up=false;
            down=true;
            moves++;
        }
    }

    private void restart() {
        gameOver=false;
        moves=0;
        score=0;
        lengthOfSnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

