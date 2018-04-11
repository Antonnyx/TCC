/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;

/**
 *
 * @author Antonio
 */
public class Ator extends Elemento {

    private Point initiPos = new Point(0, 0);
    
    
    
    public Ator(){        
        super();
        
        try {
            this.setImagem(ImageIO.read(Ator.class.getResourceAsStream("/res/ator.png")));
            this.setText("Ator");          
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            initListeners();
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        } catch (IOException ex) {
            Logger.getLogger(Ator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    /*
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawString(this.getText(), 100, 100);
        g.drawImage(this.getImagem(), 0, 0, this);
    }*/

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10,10); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void initListeners(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initiPos = e.getPoint();
                repaint();
            }
            
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - initiPos.x;
                int dy = e.getY() - initiPos.y;
                setLocation(getX() + dx, getY() + dy);
                initiPos = e.getPoint();
                repaint();
            }
            
        });
    }   
}
