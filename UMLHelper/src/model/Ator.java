/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyEditorManager;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 *
 * @author Antonio
 */
public class Ator extends JPanel {

    private Point initiPos = new Point(0, 0);
    int xPressed = 0;
    int yPressed = 0;
    private JLabel label;
    private JTextField text;
    //private Ator ator;
    private Component dragged;

    public Point getInitiPos() {
        return initiPos;
    }

    public void setInitiPos(Point initiPos) {
        this.initiPos = initiPos;
    }
    
    
    
    public Ator(){        
        super();
        try {
            this.setLayout(new BorderLayout());
            this.label = new JLabel();
            this.text = new JTextField("Ator");
            this.label.setLabelFor(this.text);
            this.text.setEditable(false);
            //this.text.setEnabled(false);
            this.text.setBorder(null);
            this.text.setBackground(Color.white);
            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setSize(80, 150);
            this.setMaximumSize(new Dimension(80, 150));
            this.setImagem(ImageIO.read(Ator.class.getResourceAsStream("/res/ator.png")));       
            this.setBackground(Color.white);
            this.add(this.label, BorderLayout.NORTH);
            this.add(this.text, BorderLayout.SOUTH);
            initListeners();
            //this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
        } catch (IOException ex) {
            Logger.getLogger(Ator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setImagem(BufferedImage imagem) {
        this.label.setIcon(new ImageIcon(imagem));
    }
    

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80,150); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void removerElemento(){
        Container cont = this.getParent();
        cont.remove(this);
        cont.repaint();
    }

    @Override
    public boolean equals(Object obj) {
        Ator ator = (Ator)obj;
        return this.text.equals(ator.text);
    }
    
    
    
    public void initListeners(){
        this.text.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 ){
                    text.setFocusable(true);
                    text.setEditable(true);
                    text.requestFocus();
                }
            }
            
        });
        
        this.text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ( e.getKeyCode() == KeyEvent.VK_ENTER ){
                    text.setEditable(false);
                    text.setFocusable(false);
                }
            }
            
        });
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 ){
                    text.setFocusable(true);
                    text.setEditable(true);
                    text.requestFocus();
                }
            }
            
        });
            
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3){
                    JPopupMenu jPop = new JPopupMenu();
                    JMenuItem jMenu = new JMenuItem("Deletar");
                    jMenu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("You're asking to remove me");
                            removerElemento();                         
                        }
                    });
                    JMenuItem jMenu2 = new JMenuItem("Mostra Pos");
                    jMenu2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("You're asking to remove me");
                            System.out.println("POS X: " + getX());                      
                            System.out.println("POS Y: " + getY());                      
                        }
                    });
                    jPop.add(jMenu);
                    jPop.add(jMenu2);
                    jPop.show(getParent(), getX()+75, getY());
                }
            }          
        });
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xPressed = e.getX();
                yPressed = e.getY();
                //initiPos = dragged.getParent().getMousePosition();              
            }
            
        });
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getParent().getMousePosition(true).x - xPressed, getParent().getMousePosition(true).y - yPressed);               

            }
            
        });
        
        
    }   
}
