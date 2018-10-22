/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import editor.EditorMenuBar;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import view.CriarDiagramaTela.CustomGraph;
import view.CriarDiagramaTela.CustomGraphComponent;



/**
 *
 * @author Antonio
 */
public class CriarTelaDiagramaCasoDeUso extends javax.swing.JFrame {

    /**
     * Creates new form CriarTelaDiagramaCasoDeUso
     */
    private CriaTelaAjuda telaAjuda = new CriaTelaAjuda();
    
    private Dimension screenSize;
    private JPanel painelAcessivel;
    private JButton botaoAdicionarVideo;
    private MouseListener jButtonMouseListener;
    private String pathToVideoFile;
    private JLabel videoSelecionado;
    private MouseListener jLabelMouseListener;
    
    public CriarTelaDiagramaCasoDeUso() {
        initComponents();
        initListeners();
        setTamanhoTela();
        setLayout(null);
        
        painelAcessivel = new JPanel();
        
        getContentPane().add(painelAcessivel);
        Double acessivelWidth = screenSize.getWidth() * 0.8;
        painelAcessivel.setSize(new Dimension(acessivelWidth.intValue(), 100));
        painelAcessivel.setLocation(100, 0);
        //painelAcessivel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK));
        JPanel p = criarAreaUML();
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        
        getContentPane().add(separator);
        
        getContentPane().add(p);
        Double doubleW = screenSize.getWidth();
        Double heightW = screenSize.getHeight()*0.8;
        p.setSize(new Dimension(doubleW.intValue(), heightW.intValue()));
        p.setLocation(0, 110);
        separator.setSize(doubleW.intValue(), 1);
        separator.setLocation(0, 101);
        
        botaoAdicionarVideo = new JButton("Adicionar Video");
        getContentPane().add(botaoAdicionarVideo);
        botaoAdicionarVideo.setSize(140, 30);
        botaoAdicionarVideo.setLocation(1650, 0);
        
        botaoAdicionarVideo.addMouseListener(jButtonMouseListener);
        
        videoSelecionado = new JLabel("Video Selecionado: Não");
 
        getContentPane().add(videoSelecionado);
        videoSelecionado.setSize(140, 30);
        videoSelecionado.setLocation(1650, 30);
        videoSelecionado.addMouseListener(jLabelMouseListener);
        
        

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(this);
        setTitle("Novo Diagrama de Caso de Uso");
        
        
    }
    
    private void initListeners(){
        jButtonMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser carregarArquivo  = new JFileChooser();
                String nomeArquivo = "";
                carregarArquivo.setApproveButtonText("Escolher arquivo");
                switch (carregarArquivo.showOpenDialog(CriarTelaDiagramaCasoDeUso.this)){
                    case JFileChooser.APPROVE_OPTION:
                        System.out.println(carregarArquivo.getSelectedFile().toPath());
                        //arquivo = carregarArquivo.getSelectedFile();
                        nomeArquivo = carregarArquivo.getSelectedFile().getName();
                        System.out.println("Nome arquivo: " + nomeArquivo);
                        videoSelecionado.setText("Video Selecionado: Sim");
                        break;
                
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton button = (JButton) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(button.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
            }
        };
        
        jLabelMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(label.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
            }
        };
       
    }
    
    
    
    public JFrame onMyFrame(){
        return this;
    }
    
    
    public void atualizarPainelAcessivel(ArrayList<JLabel> listaLabels){
        painelAcessivel.setLayout(new FlowLayout());        
        for(JLabel lbl: listaLabels){
            painelAcessivel.add(lbl);
        }
        painelAcessivel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        painelAcessivel.validate();
    }
    
    public void limparPainelAcessivel(){
        for(Component c: painelAcessivel.getComponents()){
            painelAcessivel.remove(c);
        }
        painelAcessivel.repaint();
    }
           
    
    private void setTamanhoTela(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double doubleW = screenSize.getWidth();
        Double heightW = screenSize.getHeight();
        this.setSize(doubleW.intValue(), heightW.intValue());
    }

    private JPanel criarAreaUML(){
        CriarDiagramaTela editor = new CriarDiagramaTela("Novo Diagrama de Caso de Uso", new CustomGraphComponent(new CustomGraph()), this);
        setJMenuBar(new EditorMenuBar(editor, this));
        return editor;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 912, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 509, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CriarTelaDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CriarTelaDiagramaCasoDeUso().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
