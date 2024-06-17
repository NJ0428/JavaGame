package AwtSwing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;


public class PongGameSingle {
   static class MyFrame extends JFrame{
      static class MyPanel extends JPanel{
         public MyPanel() {
            this.setSize(400,500);
            this.setBackground(Color.BLACK);
         }
         public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d=(Graphics2D)g;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(barLeftPos.x, barLeftPos.y, 20, BarHeight);
            g2d.fillRect(barRightPos.x, barRightPos.y, 20, BarHeight);
            g2d.setFont(new Font("D2coding",Font.BOLD,50));
            g2d.drawString(scoreLeft+" vs "+scoreRight, 400-50, 50);
            g2d.fillOval(ballPos.x,ballPos.y, 20, 20);
         }
      }
      static Timer timer = null;
      static int dir = 0;//0=>up1,2,3;
      static Point ballPos = new Point(400-10,200-10);
      static final int BALL_SPEED=2;
      static int BallSpeedX =BALL_SPEED;
      static int BallSpeedY =BALL_SPEED;
      static int BallWidth =20;
      static int BallHeight =20;
      static int BarHeight =80;
      static Point barLeftPos = new Point(50, 200-40);
      static Point barRightPos = new Point(800-50-20, 200-40);
      static int barLeftYTarget = barLeftPos.y;
      static int barRightYTarget = barRightPos.y;
      static MyPanel myPanel= null;
      static int scoreLeft=0;
      static int scoreRight=0;
      
      public MyFrame(String title) {
         super(title);
         this.setVisible(true);
         this.setSize(800,400);
         this.setLayout(new BorderLayout());
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         myPanel = new MyPanel();
         this.add("Center", myPanel);
         
         setKeyListener();
         startTimer();
      
      }

      private void setKeyListener() {
         this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               if(e.getKeyCode()==KeyEvent.VK_UP) {
                  System.out.println("Pressed Up key.");
                  barLeftYTarget -= 10;
                  if(barLeftPos.y < barLeftYTarget) {
                     barLeftYTarget = barLeftPos.y - 10;
                  }
                  barRightYTarget -= 10;
                  if(barRightPos.y < barRightYTarget) {
                     barRightYTarget = barRightPos.y - 10;
                  }
               }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
                  System.out.println("Pressed Dowr key.");
                  barLeftYTarget += 10;
                  if(barLeftPos.y > barLeftYTarget) {
                     barLeftYTarget = barLeftPos.y + 10;
                  }
                  barRightYTarget += 10;
                  if(barRightPos.y > barRightYTarget) {
                     barRightYTarget = barRightPos.y + 10;
                  }
               }
            }
         });
         
      }
      private void startTimer() {
         timer = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               if(barLeftPos.y > barLeftYTarget) {
                  barLeftPos.y-=5;
               }else if(barLeftPos.y < barLeftYTarget){
                  barLeftPos.y+=5;
               }
               if(barRightPos.y > barRightYTarget) {
                  barRightPos.y-=5;
                }else if(barRightPos.y < barRightYTarget){
                   barRightPos.y+=5;
                }
               if(dir==0) {
                  ballPos.x +=BallSpeedX;
                  ballPos.y -=BallSpeedY;
               }else if(dir==1) {
                  ballPos.x +=BallSpeedX;
                  ballPos.y +=BallSpeedY;
               }else if(dir==2) {
                  ballPos.x -=BallSpeedX;
                  ballPos.y -=BallSpeedY;
               }else if(dir==3) {
                  ballPos.x -=BallSpeedX;
                  ballPos.y +=BallSpeedY;
               }
               checkColl();
               myPanel.repaint();
            }
            
         });
         timer.start();//
         
      }
      public void checkColl() {
         if(dir==0) {
            if(ballPos.y <0) {
               dir =1;
            }
            if(ballPos.x>800-BallWidth) {
               dir=2;
               scoreLeft++;
            }
            if(ballPos.x > barRightPos.x-BallWidth &&
                  (ballPos.y>=barRightPos.y&&ballPos.y<=barRightPos.y+BarHeight)) {
               dir=2;
               
            }
         }else if (dir==1){
            if(ballPos.y > 400-BallHeight-20) {
               dir =0;
            }
            if(ballPos.x>800-BallWidth) {
               dir=3;
               scoreLeft++;
            }
            if(ballPos.x > barRightPos.x-BallWidth &&
                  (ballPos.y>=barRightPos.y&&ballPos.y<=barRightPos.y+BarHeight)) {
               dir=3;
               
            }
         }else if (dir==2){
            if(ballPos.y <0) {
               dir = 3;
            }
            if(ballPos.x<0) {
               dir=0;
               scoreRight++;
            }
            if(ballPos.x < barLeftPos.x+BallWidth &&
                  (ballPos.y>=barLeftPos.y&&ballPos.y<=barLeftPos.y+BallHeight+BarHeight)) {
               dir=0;
               
            }
         }else if (dir==3){
            if(ballPos.y>400-BallHeight-20) {
               dir =2;
            }
            if(ballPos.x<0) {
               dir=1;
               scoreRight++;
            }
            if(ballPos.x < barLeftPos.x+BallWidth &&
                  (ballPos.y>=barLeftPos.y&&ballPos.y<=barLeftPos.y+BallHeight+BarHeight)) {
               dir=1;
               
            }
         }
      }
   }
   public static void main(String[] args) {
      new MyFrame("Pong Game Single");
   }

}