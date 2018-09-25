/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Container;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Antonio
 */
public class Elemento extends JLabel{
    
    public Elemento(){
    }
    
    public Elemento(String texto, BufferedImage imagem){
        this.texto = texto;
        this.imagem = imagem;             
    }
    
    private String texto;
    private BufferedImage imagem;

    @Override
    public Icon getIcon() {
        return super.getIcon(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getText() {
        return super.getText(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setText(String text) {
        super.setText(text); //To change body of generated methods, choose Tools | Templates.
    }


    public void setImagem(BufferedImage imagem) {
        this.setIcon(new ImageIcon(imagem));
    }
    
    public BufferedImage getImagem(){
        return imagem;
    }
    

    
    
    
}
