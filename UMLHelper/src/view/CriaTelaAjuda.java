/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
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
    
    public JFrame getTelaAcessivel(String texto){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for(char c: texto.toCharArray()){
            JLabel label = new JLabel();
            ImageIcon imageIcon = new ImageIcon(CriaTelaAjuda.class.getResource("/libras_images/letra_"+c+".png"));
            label.setIcon(imageIcon);
            panel.add(label);
        }
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        frame.add(panel);
        frame.setResizable(false);
        frame.setTitle(texto);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        return frame;
    }
    
    public JDialog getDialog(String texto){
        JDialog dialog = new JDialog();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for(char c: texto.toCharArray()){
            JLabel label = new JLabel();
            ImageIcon imageIcon = new ImageIcon(CriaTelaAjuda.class.getResource("/libras_images/letra_"+c+".png"));
            label.setIcon(imageIcon);
            panel.add(label);
        }
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        dialog.add(panel);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        return dialog;
    }
    
}
