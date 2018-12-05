/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.umlhelper.view;

import br.edu.ifam.umlhelper.model.Editor;
import br.edu.ifam.umlhelper.model.Editor.CustomGraph;
import br.edu.ifam.umlhelper.model.EditorActions;
import br.edu.ifam.umlhelper.model.EditorActions.ExitAction;
import br.edu.ifam.umlhelper.model.EditorActions.HistoryAction;
import br.edu.ifam.umlhelper.model.EditorActions.NewAction;
import br.edu.ifam.umlhelper.model.EditorActions.OpenAction;
import br.edu.ifam.umlhelper.model.EditorActions.SaveAction;
import br.edu.ifam.umlhelper.model.EditorSobreFrame;
import br.edu.ifam.umlhelper.model.EditorToolBar;
import br.edu.ifam.umlhelper.model.Paletas;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.swing.util.mxGraphTransferable;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Antonio
 */
public class TelaCriarDiagramaCasoDeUso extends javax.swing.JFrame {

    /**
     * Creates new form TelaCriarDiagramaCasoDeUso
     */
    private static Editor editor;
    private String tituloTela = "UML Libras";
    private MouseListener jMenuMouseListener;
    private MouseListener jMenuItemMouseListener;
    private MouseListener jLabelMouseListener;
    private MouseListener cellListener;
    private MouseAdapter closeTabbedPaneListener;
    private boolean painelAcessivelDisponivel = true;
    private Image frameIcon;
    private ArrayList<JLabel> lblPadrao = CriaTelaAjuda.getLabelPadrao();
    
    
    
    public TelaCriarDiagramaCasoDeUso() {
        initComponents();
        setUI();
        initGUI();
        inicializarPaletaUML();
        inserirBarraFerramentas();
        initListenersDeAcessibilidade();
        initActionListeners();
        setTitle(tituloTela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/edu/ifam/umlhelper/images/icon_uml.png")));
        
    }
    
    class MyCloseActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            JTabbedPane tabPane = (JTabbedPane)evt.getSource();
            Component selected = tabPane.getSelectedComponent();
            if (selected != null) {

                tabPane.remove(selected);
                // It would probably be worthwhile getting the source
                // casting it back to a JButton and removing
                // the action handler reference ;)

            }

        }

    }
    
      
    class MyCloseButton extends JButton{

        public MyCloseButton() {
            setName("x");
            setBorder(BorderFactory.createEmptyBorder());
            addActionListener(new MyCloseActionHandler());
        }
        
    
    }
    
    
    
    private void initGUI(){
        editor = new Editor(this);
        painelUML.addTab("Novo", editor);
        //painelUML.setTitleAt(0, "Novo");
        this.setExtendedState(JFrame.MAXIMIZED_HORIZ);
        
    }
    
    public void updateTabTitle(String fileName){
        painelUML.setTitleAt(painelUML.getSelectedIndex(), fileName);
    }
    
    public void addNewGraph(String title){
        Editor ed = new Editor(this);
        painelUML.addTab(title,ed);
        
        int pos = painelUML.getComponentCount() - 1;
        painelUML.setSelectedIndex(pos);
        ed.getGraphComponent().zoomAndCenter();
        
    }
    
    public static Editor getEditor(){
        return editor;
    }
    
    private void showCloseTabbedPanePopup(MouseEvent e)
    {
        final MouseEvent fecharEvent = e;
        JPopupMenu closePopup = new JPopupMenu();
        JMenuItem jMenuFechar = new JMenuItem("Fechar");
        jMenuFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTabbedPane tabPane = (JTabbedPane)fecharEvent.getSource();
                Component selected = tabPane.getSelectedComponent();
                if (selected != null ) {
                    if(tabPane.getComponentCount() > 1){
                        tabPane.remove(selected);
                    }
                    
                    
                // It would probably be worthwhile getting the source
                // casting it back to a JButton and removing
                // the action handler reference ;)

                }
            }
            });
        closePopup.add(jMenuFechar);
        closePopup.show(painelUML, fecharEvent.getX(), fecharEvent.getY());
        closePopup.setVisible(true);
        
    
    }
    
    
    private void initActionListeners(){
        painelUML.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                editor = (Editor) painelUML.getComponent(painelUML.getSelectedIndex());
                
            }
        });
        
        
        
        closeTabbedPaneListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    showCloseTabbedPanePopup(e);
                }
            }
            
        };
        
        painelUML.addMouseListener(closeTabbedPaneListener);
        
        jMenuArquivoNovo.addActionListener(editor.bind("Novo", new NewAction(), null));
        jMenuArquivoNovo.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/new.gif")));
        
        jMenuArquivoAbrir.addActionListener(editor.bind("Abrir", new OpenAction(), null));
        jMenuArquivoAbrir.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/open.gif")));
        
        jMenuArquivoSalvar.addActionListener(editor.bind("Salvar", new SaveAction(false), null));
        jMenuArquivoSalvar.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/save.gif")));
        
        jMenuArquivoSalvarComo.addActionListener(editor.bind("Salvar", new SaveAction(true), null));
        jMenuArquivoSalvarComo.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/saveas.gif")));
        
        jMenuArquivoSair.addActionListener(editor.bind("Salvar", new ExitAction()));
        jMenuArquivoImprimir.addActionListener(editor.bind("Imprimir", new EditorActions.PrintAction()));  
        jMenuArquivoImprimir.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/print.gif")));
        
        jMenuEditarApagar.addActionListener(editor.bind("Apagar", mxGraphActions.getDeleteAction()));
        jMenuEditarApagar.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/delete.gif")));
        
        jMenuEditarEditar.addActionListener(editor.bind("Editar", mxGraphActions.getEditAction()));
        
        jMenuEditarDesfazer.addActionListener(editor.bind("Desfazer", new HistoryAction(true)));
        jMenuEditarDesfazer.setIcon(new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/undo.gif")));
        
        jMenuEditarDesfazer.addActionListener(editor.bind("Editar", mxGraphActions.getEditAction()));
        
        
       
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
                "shape=actor;fillColor=#FFFF99;gradientColor=#FFFF99;fontSize=20", 120, 160, "");
        paletaUML.addEdgeTemplate("Agregação",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/associacao.png")),
                "straight;strokeWidth=4;endArrow=none", 120, 120, "");  
        paletaUML.addTemplate("Caso de Uso",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/ellipse.png")),"ellipse;fillColor=#FFFF99;gradientColor=#FFFF99;fontSize=20", 160, 100, "");
        paletaUML.addEdgeTemplate("Incluir",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/include.png")),
                "straight;dashed=1;strokeWidth=4;fontSize=20", 120, 120, "<<include>>");
        paletaUML.addEdgeTemplate("Estender",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/extend.png")),
                "straight;dashed=1;strokeWidth=4;fontSize=20", 120, 120, "<<extend>>");
        paletaUML.addEdgeTemplate("Generalizar",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/straight.png")),
                "straight;strokeWidth=4", 120, 120, "");
        paletaUML.addTemplate("Subsistema",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/rectangle.png")),
                "fillColor=#FFFFFF;gradientColor=#FFFFFF;verticalAlign=top;align=left;fontSize=20", 120, 120, "<<Subsistema>>");
        paletaUML.addTemplate("Video",new ImageIcon(TelaCriarDiagramaCasoDeUso.class.getResource("/br/edu/ifam/umlhelper/images/video.png")),
						"label;image=/br/edu/ifam/umlhelper/images/video.png;fontSize=25",
						130, 50, "Video");
        painelPaletas.add(paletaUML);
        painelPaletas.setTitleAt(0, "UML");
        atualizarPainelAcessivel(lblPadrao);
        
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
                limparPainelAcessivel();
                JMenu jMenu = (JMenu) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jMenu.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
                atualizarPainelAcessivel(lblPadrao);
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
                limparPainelAcessivel();
                JMenuItem jMenuItem = (JMenuItem) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jMenuItem.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
                atualizarPainelAcessivel(lblPadrao);
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
                limparPainelAcessivel();
                JLabel jLabel = (JLabel) e.getComponent();
                atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jLabel.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                limparPainelAcessivel();
                atualizarPainelAcessivel(lblPadrao);
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
        jMenuArquivoImprimir.addMouseListener(jMenuItemMouseListener);
        
        jMenuEditarDesfazer.addMouseListener(jMenuItemMouseListener);
        jMenuEditarEditar.addMouseListener(jMenuItemMouseListener);
        jMenuEditarApagar.addMouseListener(jMenuItemMouseListener);
        
        jMenuOpcoesDatilologiaOn.addMouseListener(jMenuItemMouseListener);
        jMenuOpcoesDatilologiaOff.addMouseListener(jMenuItemMouseListener);
        
        jMenuAjuda.addMouseListener(jMenuMouseListener);
        jMenuSobre.addMouseListener(jMenuItemMouseListener);
        
    }
    
    public void atualizarPainelAcessivel(ArrayList<JLabel> listaLabels){
        
        painelAcessivel.setLayout(new FlowLayout());        
        painelAcessivel.setBackground(Color.WHITE);
        for(JLabel lbl: listaLabels){
            painelAcessivel.add(lbl);
        }
        painelAcessivel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        painelAcessivel.validate();
    }
    
    public void ligarAcessibilidade(){
        painelAcessivel.setVisible(true);
        painelAcessivelDisponivel = true;
        
    }
    
    public void desligarAcessibilidade(){
        painelAcessivel.setVisible(false);
        painelAcessivelDisponivel = false;        
        
        
    }
    
    public void limparPainelAcessivel(){
        for(Component c: painelAcessivel.getComponents()){
            painelAcessivel.remove(c);
        }
        painelAcessivel.repaint();
    }
    
    private void inserirBarraFerramentas(){
        EditorToolBar ferramentas = new EditorToolBar(editor, JToolBar.HORIZONTAL, this);
        painelFerramentasToolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        for(Component c: ferramentas.getComponents()){
            painelFerramentasToolBar.add(c);
        }
        painelFerramentas.setEnabled(false);
        painelFerramentasToolBar.setEnabled(false);
        

    }
    
    
    
    //Método que devolve a estrutura de uma Paleta
    public Paletas inserirPaleta(String title)
    {
            final Paletas paleta = new Paletas(this);
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
            Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TelaCriarDiagramaCasoDeUso.class.getName()).log(Level.SEVERE, null, ex);
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
        labelIcon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuArquivoNovo = new javax.swing.JMenuItem();
        jMenuArquivoAbrir = new javax.swing.JMenuItem();
        jMenuArquivoSalvar = new javax.swing.JMenuItem();
        jMenuArquivoSalvarComo = new javax.swing.JMenuItem();
        jMenuArquivoImprimir = new javax.swing.JMenuItem();
        jMenuArquivoSair = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenu();
        jMenuEditarDesfazer = new javax.swing.JMenuItem();
        jMenuEditarEditar = new javax.swing.JMenuItem();
        jMenuEditarApagar = new javax.swing.JMenuItem();
        jMenuOpcoes = new javax.swing.JMenu();
        jMenuOpcoesDatilologiaOn = new javax.swing.JMenuItem();
        jMenuOpcoesDatilologiaOff = new javax.swing.JMenuItem();
        jMenuAjuda = new javax.swing.JMenu();
        jMenuSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1920, 1000));

        painelAcessivel.setPreferredSize(new java.awt.Dimension(1902, 104));

        javax.swing.GroupLayout painelAcessivelLayout = new javax.swing.GroupLayout(painelAcessivel);
        painelAcessivel.setLayout(painelAcessivelLayout);
        painelAcessivelLayout.setHorizontalGroup(
            painelAcessivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painelAcessivelLayout.setVerticalGroup(
            painelAcessivelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 104, Short.MAX_VALUE)
        );

        painelFerramentasToolBar.setRollover(true);

        javax.swing.GroupLayout painelFerramentasLayout = new javax.swing.GroupLayout(painelFerramentas);
        painelFerramentas.setLayout(painelFerramentasLayout);
        painelFerramentasLayout.setHorizontalGroup(
            painelFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFerramentasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelFerramentasToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                .addGap(1188, 1188, 1188))
        );
        painelFerramentasLayout.setVerticalGroup(
            painelFerramentasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFerramentasLayout.createSequentialGroup()
                .addComponent(painelFerramentasToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        labelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/ifam/umlhelper/images/icone_png_uml.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setText("LIBRAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(labelIcon)
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(101, 101, 101))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(labelIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(168, Short.MAX_VALUE))
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

        jMenuArquivoImprimir.setText("Imprimir");
        jMenuArquivo.add(jMenuArquivoImprimir);

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

        jMenuAjuda.setText("Ajuda");

        jMenuSobre.setText("Sobre UMLHelper");
        jMenuSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSobreActionPerformed(evt);
            }
        });
        jMenuAjuda.add(jMenuSobre);

        jMenuBar1.add(jMenuAjuda);

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
                            .addComponent(painelPaletas)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
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

    private void jMenuSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSobreActionPerformed
        EditorSobreFrame sobre = new EditorSobreFrame(this);
        sobre.setModal(true);
        int x = this.getX() + (this.getWidth() - sobre.getWidth()) / 2;
        int y = this.getY() + (this.getHeight() - sobre.getHeight()) / 2;
        sobre.setLocation(x, y);
        sobre.setVisible(true);
        
    }//GEN-LAST:event_jMenuSobreActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuAjuda;
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuItem jMenuArquivoAbrir;
    private javax.swing.JMenuItem jMenuArquivoImprimir;
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
    private javax.swing.JMenuItem jMenuSobre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelIcon;
    private javax.swing.JPanel painelAcessivel;
    private javax.swing.JPanel painelFerramentas;
    private javax.swing.JToolBar painelFerramentasToolBar;
    private javax.swing.JTabbedPane painelPaletas;
    private javax.swing.JTabbedPane painelUML;
    // End of variables declaration//GEN-END:variables
}

