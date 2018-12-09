/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.umlhelper.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 *
 * @author Antonio
 */
public class SplashEsperar {
    private final int largura = 400;
    private final int altura = 200;
    private final int tempo = 5000; 
    private final String caminhoGif = "/libras_images/libras_esperar.png";
    
    public SplashEsperar(){
        JWindow janelaSplash = new JWindow();
        janelaSplash.getContentPane().add(new JLabel("", new ImageIcon(getClass().getResource(caminhoGif)), SwingConstants.CENTER));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        
        janelaSplash.setBounds((dimension.width - largura)/2,(dimension.height - altura)/2 , largura, altura);
        janelaSplash.setVisible(true);
        try{
            Thread.sleep(tempo);
        }catch(InterruptedException ex){
            
        }finally{
            janelaSplash.dispose();
        }
        new InitScreen().setVisible(true);
    }
    public static void main(String[] args) {
        new SplashEsperar();
    }
    
}
