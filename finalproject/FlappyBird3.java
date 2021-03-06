/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class FlappyBird3 implements ActionListener, KeyListener {

    public FlappyBird3() {
    }
    
    
    
    public static final int FPS = 60, WIDTH = 640, HEIGHT = 480;
    
    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer t;
    
    private boolean paused;

  
    
    public void go() {
        frame = new JFrame("LEVEL-4");
        bird = new Bird();
        rects = new ArrayList<Rectangle>();
        panel = new GamePanel3(this, bird, rects);
        frame.add(panel);
        
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
        paused = true;
        
        t = new Timer(300/FPS, this);
        t.start();
    }
//    public static void main(String[] args) {
//        new FlappyBird().go();
//    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            bird.physics();
            if(scroll % 90 == 0) {
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT));
                int h2 = (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT);
                Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            boolean game = true;
            for(Rectangle r : rects) {
                r.x-=3;
                if(r.x + r.width <= 0) {
                    toRemove.add(r);
                }
                if(r.contains(bird.x, bird.y)) {
                    JOptionPane.showMessageDialog(frame, "You lose!\n"+"Your score was: "+time+".");
                    game = false;
                }
            }
            rects.removeAll(toRemove);
            time++;
            scroll++;
             if(scroll==2000){
                JOptionPane.showMessageDialog(null, "You Successfully Completed");
                paused=true;
                new MainFrame().setVisible(true);
            }
            
            
            if(bird.y > HEIGHT || bird.y+bird.RAD < 0) {
                game = false;
            }

            if(!game) {
                rects.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
        else {
            
        }
    }
    
    public int getScore() {
        return time;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) {
            bird.jump();
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            if(paused==true){
                paused = false;
            }
            else{
                paused=true;
            }
            
        }
        else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            paused=true;
            frame.setVisible(false);
            new MainFrame().setVisible(true);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    public boolean paused() {
        return paused;
    }
}
