/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Antonio
 */
public class CriaTelaAjuda {
    
    //Método que recebe uma String(nome do JMENU que irá essar essa método dentro de um mouseListener)
    //Método cria um JFRAME para mostrar o texto em String em forma de Imagens em Libras(Datilologia)
    public JFrame getTelaAcessivel(String texto){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for(char c: texto.toCharArray()){
            if(!Character.toString(c).equals(" ")){
                JLabel label = new JLabel();
                ImageIcon imageIcon = new ImageIcon(CriaTelaAjuda.class.getResource("/libras_images/letra_"+c+".png"));
                label.setIcon(imageIcon);
                panel.add(label);                
            }

        }
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        frame.add(panel);
        frame.setResizable(false);
        frame.setTitle(texto);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        return frame;
    }
    
    public JPanel getPainelAcessivel(String texto){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for(char c: texto.toCharArray()){
            if(!Character.toString(c).equals(" ") && !Character.toString(c).equals(":")){
                JLabel label = new JLabel();
                ImageIcon imageIcon = new ImageIcon(CriaTelaAjuda.class.getResource("/libras_images/letra_"+c+".png"));
                label.setIcon(imageIcon);
                panel.add(label);                
            }

        }
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return panel;
    }
    
    public ArrayList<JLabel> getLabelLibras(String texto){
        ArrayList<JLabel> labels = new ArrayList<JLabel>();
        for(char c: texto.toCharArray()){
            if(!Character.toString(c).equals(" ") && !Character.toString(c).equals(":")){
                JLabel label = new JLabel();
                ImageIcon imageIcon = new ImageIcon(CriaTelaAjuda.class.getResource("/libras_images/letra_"+c+".png"));
                label.setIcon(imageIcon);
                labels.add(label);
            }

        }
        return labels;
    }
    
}
