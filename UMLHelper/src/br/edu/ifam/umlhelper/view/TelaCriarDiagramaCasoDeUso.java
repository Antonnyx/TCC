/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.umlhelper.view;

import br.edu.ifam.umlhelper.model.Editor;
import br.edu.ifam.umlhelper.model.Editor.CustomGraph;
import br.edu.ifam.umlhelper.model.EditorToolBar;
import br.edu.ifam.umlhelper.model.Paletas;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.CriarDiagramaTela;
import view.CriarTelaDiagramaCasoDeUso;

/**
 *
 * @author Antonio
 */
public class TelaCriarDiagramaCasoDeUso extends javax.swing.JFrame {

    /**
     * Creates new form TelaCriarDiagramaCasoDeUso
     */
    private Editor editor;
    private String tituloTela = "Criar Diagrama de Caso de Uso";
    private MouseListener jMenuMouseListener;
    private MouseListener jMenuItemMouseListener;
    private MouseListener jLabelMouseListener;
    
    
    
    public TelaCriarDiagramaCasoDeUso() {
        initComponents();
        setUI();
        initGUI();
        inicializarPaletaUML();
        inserirBarraFerramentas();
        initListenersDeAcessibilidade();
        setTitle(tituloTela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initGUI(){
        editor = new Editor();
        painelUML.add(editor);
        painelUML.setTitleAt(0, "Novo");
        this.setExtendedState(JFrame.MAXIMIZED_HORIZ);
        
    }
    
    //Inicia paleta com elementos para o Diagrama de Caso de Uso
    //Ator, Caso de Uso, Agregação
    private void inicializarPaletaUML(){
        Paletas paletaUML = inserirPaleta("UML");
        paletaUML.addListener(mxEvent.SELECT, new mxEventSource.mxIEventListener()
	{
            public void invoke(Object sender, mxEventObject evt)
            {
                    Object tmp = evt.getProperty("transferable");

                    if (tmp instanceof mxGraphTransferable)
                    {
                            mxGraphTransferable t = (mxGraphTransferable) tmp;
                            Object cell = t.getCells()[0];

                            if (editor.getGraph().getModel().isEdge(cell))
                            {
                                    ((CustomGraph) editor.getGraph()).setEdgeTemplate(cell);
                            }
                    }
            }

	});
        paletaUML.addTemplate("Ator",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/actor.png")),
                "shape=actor", 120, 160, "");
        paletaUML.addEdgeTemplate("Agregação",new ImageIcon(CriarDiagramaTela.class.getResource("/br/edu/ifam/umlhelper/images/straight.png")),
                "straight", 120, 120, "");  
        paletaUML.addTemplate("Caso de Uso",new ImageIcon(CriarDiagramaTela.class.getResource("/br/edu/ifam/umlhelper/images/ellipse.png")),"ellipse", 160, 100, "");
        painelPaletas.add(paletaUML);
        painelPaletas.setTitleAt(0, "UML");
        
    }
    
    
    private void initListenersDeAcessibilidade(){
        jMenuMouseListener = new MouseListener() {
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
                JMenu jMenu = (JMenu) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jMenu.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
            }
        };
        
        jMenuItemMouseListener = new MouseListener() {
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
                JMenuItem jMenuItem = (JMenuItem) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jMenuItem.getText().toLowerCase()));
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
                JLabel jLabel = (JLabel) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jLabel.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
            }
        };         
        
        //Listeners de acessibilidade para os JMENU
        jMenuArquivo.addMouseListener(jMenuMouseListener);
        jMenuEditar.addMouseListener(jMenuMouseListener);
        jMenuOpcoes.addMouseListener(jMenuMouseListener);
        
        //Listeners de acessibilidade para os JMENUITEM
        jMenuArquivoNovo.addMouseListener(jMenuItemMouseListener);
        jMenuArquivoAbrir.addMouseListener(jMenuItemMouseListener);
        jMenuArquivoSalvar.addMouseListener(jMenuItemMouseListener);
        jMenuArquivoSalvarComo.addMouseListener(jMenuItemMouseListener);
        jMenuArquivoSair.addMouseListener(jMenuItemMouseListener);
        
        jMenuEditarDesfazer.addMouseListener(jMenuItemMouseListener);
        jMenuEditarEditar.addMouseListener(jMenuItemMouseListener);
        jMenuEditarApagar.addMouseListener(jMenuItemMouseListener);
        
        jMenuOpcoesDatilologiaOn.addMouseListener(jMenuItemMouseListener);
        jMenuOpcoesDatilologiaOff.addMouseListener(jMenuItemMouseListener);
        
    }
    
    public void atualizarPainelAcessivel(ArrayList<JLabel> listaLabels){
        painelAcessivel.setLayout(new FlowLayout());        
        for(JLabel lbl: listaLabels){
            painelAcessivel.add(lbl);
        }
        painelAcessivel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        painelAcessivel.validate();
    }
    
    public void ligarAcessibilidade(){
        painelAcessivel.setVisible(true);
    }
    
    public void desligarAcessibilidade(){
        painelAcessivel.setVisible(false);
    }
    
    public void limparPainelAcessivel(){
        for(Component c: painelAcessivel.getComponents()){
            painelAcessivel.remove(c);
        }
        painelAcessivel.repaint();
    }
    
    private void inserirBarraFerramentas(){
        EditorToolBar ferramentas = new EditorToolBar(editor, JToolBar.HORIZONTAL);
        painelFerramentasToolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        for(Component c: ferramentas.getComponents()){
            painelFerramentasToolBar.add(c);
        }

    }
    
    
    
    //Método que devolve a estrutura de uma Paleta
    public Paletas inserirPaleta(String title)
    {
            Paletas paleta = new Paletas();
            JTabbedPane painelUML = new JTabbedPane();
            final JScrollPane scrollPane = new JScrollPane(paleta);
            scrollPane
                            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane
                            .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            painelUML.add(title, scrollPane);

            // Updates the widths of the palettes if the container size changes
            painelUML.addComponentListener(new ComponentAdapter()
            {
                    /**
                     * 
                     */
                    public void componentResized(ComponentEvent e)
                    {
                            int w = scrollPane.getWidth()
                                            - scrollPane.getVerticalScrollBar().getWidth();
                            paleta.setPreferredWidth(w);
                    }

            });

            return paleta;
    }
    
    private void setUI(){
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelAcessivel = new javax.swing.JPanel();
        painelFerramentas = new javax.swing.JPanel();
        painelFerramentasToolBar = new javax.swing.JToolBar();
        painelPaletas = new javax.swing.JTabbedPane();
        painelUML = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuArquivoNovo = new javax.swing.JMenuItem();
        jMenuArquivoAbrir = new javax.swing.JMenuItem();
        jMenuArquivoSalvar = new javax.swing.JMenuItem();
        jMenuArquivoSalvarComo = new javax.swing.JMenuItem();
        jMenuArquivoSair = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenu();
        jMenuEditarDesfazer = new javax.swing.JMenuItem();
        jMenuEditarEditar = new javax.swing.JMenuItem();
        jMenuEditarApagar = new javax.swing.JMenuItem();
        jMenuOpcoes = new javax.swing.JMenu();
        jMenuOpcoesDatilologiaOn = new javax.swing.JMenuItem();
        jMenuOpcoesDatilologiaOff = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1920, 1000));

        painelAcessivel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout painelAcessivelLayout = new javax.swing.GroupLayout(painelAcessivel);
        painelAcessivel.setLayout(painelAcessivelLayout);
        painelAcessivelLayout.setHorizontalGroup(
            painelAcessivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painelAcessivelLayout.setVerticalGroup(
            painelAcessivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );

        painelFerramentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelFerramentasToolBar.setRollover(true);

        javax.swing.GroupLayout painelFerramentasLayout = new javax.swing.GroupLayout(painelFerramentas);
        painelFerramentas.setLayout(painelFerramentasLayout);
        painelFerramentasLayout.setHorizontalGroup(
            painelFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFerramentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelFerramentasToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addGap(1188, 1188, 1188))
        );
        painelFerramentasLayout.setVerticalGroup(
            painelFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFerramentasLayout.createSequentialGroup()
                .addComponent(painelFerramentasToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        painelPaletas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        painelUML.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        jMenuArquivo.setText("Arquivo");

        jMenuArquivoNovo.setText("Novo");
        jMenuArquivo.add(jMenuArquivoNovo);

        jMenuArquivoAbrir.setText("Abrir");
        jMenuArquivo.add(jMenuArquivoAbrir);

        jMenuArquivoSalvar.setText("Salvar");
        jMenuArquivo.add(jMenuArquivoSalvar);

        jMenuArquivoSalvarComo.setText("Salvar Como");
        jMenuArquivo.add(jMenuArquivoSalvarComo);

        jMenuArquivoSair.setText("Sair");
        jMenuArquivo.add(jMenuArquivoSair);

        jMenuBar1.add(jMenuArquivo);

        jMenuEditar.setText("Editar");

        jMenuEditarDesfazer.setText("Desfazer");
        jMenuEditar.add(jMenuEditarDesfazer);

        jMenuEditarEditar.setText("Editar");
        jMenuEditar.add(jMenuEditarEditar);

        jMenuEditarApagar.setText("Apagar");
        jMenuEditar.add(jMenuEditarApagar);

        jMenuBar1.add(jMenuEditar);

        jMenuOpcoes.setText("Opções");

        jMenuOpcoesDatilologiaOn.setText("Datilologia ON");
        jMenuOpcoesDatilologiaOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpcoesDatilologiaOnActionPerformed(evt);
            }
        });
        jMenuOpcoes.add(jMenuOpcoesDatilologiaOn);

        jMenuOpcoesDatilologiaOff.setText("Datilologia OFF");
        jMenuOpcoesDatilologiaOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpcoesDatilologiaOffActionPerformed(evt);
            }
        });
        jMenuOpcoes.add(jMenuOpcoesDatilologiaOff);

        jMenuBar1.add(jMenuOpcoes);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(painelPaletas, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addComponent(painelUML))
                    .addComponent(painelFerramentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelAcessivel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelAcessivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelFerramentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelPaletas, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelUML))
                .addGap(733, 733, 733))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuOpcoesDatilologiaOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpcoesDatilologiaOnActionPerformed
        ligarAcessibilidade();
    }//GEN-LAST:event_jMenuOpcoesDatilologiaOnActionPerformed

    private void jMenuOpcoesDatilologiaOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpcoesDatilologiaOffActionPerformed
        desligarAcessibilidade();
    }//GEN-LAST:event_jMenuOpcoesDatilologiaOffActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCriarDiagramaCasoDeUso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuItem jMenuArquivoAbrir;
    private javax.swing.JMenuItem jMenuArquivoNovo;
    private javax.swing.JMenuItem jMenuArquivoSair;
    private javax.swing.JMenuItem jMenuArquivoSalvar;
    private javax.swing.JMenuItem jMenuArquivoSalvarComo;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEditar;
    private javax.swing.JMenuItem jMenuEditarApagar;
    private javax.swing.JMenuItem jMenuEditarDesfazer;
    private javax.swing.JMenuItem jMenuEditarEditar;
    private javax.swing.JMenu jMenuOpcoes;
    private javax.swing.JMenuItem jMenuOpcoesDatilologiaOff;
    private javax.swing.JMenuItem jMenuOpcoesDatilologiaOn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel painelAcessivel;
    private javax.swing.JPanel painelFerramentas;
    private javax.swing.JToolBar painelFerramentasToolBar;
    private javax.swing.JTabbedPane painelPaletas;
    private javax.swing.JTabbedPane painelUML;
    // End of variables declaration//GEN-END:variables
}
