/* 
 * 
 * Copyright (c) 2001-2014, JGraph Ltd
 * All rights reserved.
 */


package br.edu.ifam.umlhelper.model;


import br.edu.ifam.umlhelper.model.EditorActions.HistoryAction;
import br.edu.ifam.umlhelper.util.Player2;
import br.edu.ifam.umlhelper.view.CriaTelaAjuda;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javafx.application.Application;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;
        private ImageIcon naoTemIcon = new ImageIcon(EditorPopupMenu.class.getResource("/br/edu/ifam/umlhelper/images/nao_ter.png"));
        private MouseListener jMenuItemMouseListener;
        private Editor editor;
        private ArrayList<JLabel> lblPadrao = CriaTelaAjuda.getLabelPadrao();
	public EditorPopupMenu(final Editor editor)
	{
            
                this.editor = editor;
                initListenersAcessibilidade();
                
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();
                Action tocarVideoAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String diretorio = "";
                        Object source = e.getSource();
                        if(editor.getCurrentFile() != null){
                            diretorio = "C:\\" + FilenameUtils.getPath(editor.getCurrentFile().getAbsolutePath());
                        }
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            if(!cell.getVideoPath().equals("")){
                         
                                try{
                                    
                                    String pathName = "";
                                    if(editor.getCurrentFile() == null){
                                        pathName = cell.getVideoPath();
                                    }
                                    else{
                                        pathName = diretorio + cell.getVideoPath();
                                    }
                                    Player2.setVideoPath(pathName);
                                    Player2.main(null);
                                    
                                    //p.playVideo(cell.getVideoPath());
                                }catch(Exception ex){
                                    //p.playVideo(cell.getVideoPath());
                                    ex.printStackTrace();
                                           
                                }
                                
                            }else{
                                
                                JOptionPane.showMessageDialog(null,"Elemento não possui video", "Não possui video", JOptionPane.INFORMATION_MESSAGE, naoTemIcon);
                                
                            }
                            
                        }
                      
                    }
                };
  

                Action tocarVideoLibrasAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String diretorio = "";
                        Object source = e.getSource();
                        if(editor.getCurrentFile() != null){
                            diretorio = "C:\\" + FilenameUtils.getPath(editor.getCurrentFile().getAbsolutePath());
                        }
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            if(!cell.getVideoPathLibras().equals("")){
                         
                                try{
                                    String pathName = "";
                                    if(editor.getCurrentFile() == null){
                                        pathName = cell.getVideoPathLibras();
                                    }
                                    else{
                                        pathName = diretorio + cell.getVideoPathLibras();
                                    }
                                           
                                    Player2.setVideoPath(pathName);
                                    Player2.main(null);
                                    
                                    //p.playVideo(cell.getVideoPath());
                                }catch(Exception ex){
                                    //p.playVideo(cell.getVideoPath());
                                    ex.printStackTrace();
                                           
                                }
                                
                            }else{
                                JOptionPane.showMessageDialog(null,"Elemento não possui video em Libras", "Não possui video em Libras", JOptionPane.INFORMATION_MESSAGE, naoTemIcon);
                            }
                            
                        }
                      
                    }
                };

                Action addVideoAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Object source = e.getSource();
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            JFileChooser carregarArquivo  = new JFileChooser();
                            String nomeArquivo = "";
                            carregarArquivo.setApproveButtonText("Escolher arquivo");
                            switch (carregarArquivo.showOpenDialog(null))
                            {
                                case JFileChooser.APPROVE_OPTION:
                                    System.out.println(carregarArquivo.getSelectedFile().toPath());
                                    //arquivo = carregarArquivo.getSelectedFile();
                                    nomeArquivo = carregarArquivo.getSelectedFile().getName();
                                    //System.out.println("Nome arquivo: " + nomeArquivo);
                                    cell.setVideoPath(carregarArquivo.getSelectedFile().getAbsolutePath());
                                    break;
                                default:                       
                                    break;

                            }
                            
                        }
                      
                    }
                }; 
  
                Action addVideoLibrasAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Object source = e.getSource();
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            JFileChooser carregarArquivo  = new JFileChooser();
                            String nomeArquivo = "";
                            carregarArquivo.setApproveButtonText("Escolher arquivo");
                            switch (carregarArquivo.showOpenDialog(null))
                            {
                                case JFileChooser.APPROVE_OPTION:
                                    System.out.println(carregarArquivo.getSelectedFile().toPath());
                                    //arquivo = carregarArquivo.getSelectedFile();
                                    nomeArquivo = carregarArquivo.getSelectedFile().getName();
                                    //System.out.println("Nome arquivo: " + nomeArquivo);
                                    cell.setVideoPathLibras(carregarArquivo.getSelectedFile().getAbsolutePath());
                                    break;
                                default:                       
                                    break;

                            }
                            
                        }
                      
                    }
                }; 
                
                 Action removeVideoAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Object source = e.getSource();
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            if(cell.getVideoPath().equals("")){
                                JOptionPane.showMessageDialog(null,"Elemento não possui video", "Não possui video", JOptionPane.INFORMATION_MESSAGE, naoTemIcon);
                                
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Removendo video : "+cell.getVideoPath());
                                cell.setVideoPath("");
                            }
                            
                            
                        }
                      
                    }
                };
                 
                  Action removeVideoLibrasAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Object source = e.getSource();
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            if(cell.getVideoPathLibras().equals("")){
                                JOptionPane.showMessageDialog(null,"Elemento não possui video em Libras", "Não possui video em Libras", JOptionPane.INFORMATION_MESSAGE, naoTemIcon);
                                
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Removendo video : "+cell.getVideoPathLibras());
                                cell.setVideoPathLibras("");
                            }
                            
                            
                        }
                      
                    }
                };             
                
                
                

                
                add(
				editor.bind("Add Video", addVideoAction,null)).addMouseListener(jMenuItemMouseListener);
				;
                
                
		
		add(
				editor.bind("Del Video", removeVideoAction,
						null))
				.addMouseListener(jMenuItemMouseListener);;
                add(editor.bind("Exibir Video", tocarVideoAction ,null)).addMouseListener(jMenuItemMouseListener);
                
                addSeparator();
                add(
				editor.bind("Add Video Libras", addVideoLibrasAction,null))
				.addMouseListener(jMenuItemMouseListener);
                
                
		add(
				editor.bind("Del Video Libras", removeVideoLibrasAction,
						null))
				.addMouseListener(jMenuItemMouseListener);
                
                add(editor.bind("Exibir Video Libras", tocarVideoLibrasAction ,null)).addMouseListener(jMenuItemMouseListener);                

		addSeparator();
                /*
                JMenuItem tocarVideoItem = new JMenuItem("Tocar Video");
                tocarVideoItem.addActionListener(tocarVideoAction);
                add(tocarVideoItem).setEnabled(true);
                */
		add(editor.bind(mxResources.get("undo"), new HistoryAction(true),"/br/edu/ifam/umlhelper/images/undo.gif")).addMouseListener(jMenuItemMouseListener);
                add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(),"/br/edu/ifam/umlhelper/images/delete.gif"))
                        .addMouseListener(jMenuItemMouseListener);
                
                
               
	}
        private void initListenersAcessibilidade(){
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
                editor.getTela().limparPainelAcessivel();
                JMenuItem jMenuItem = (JMenuItem) e.getComponent();
                editor.getTela().atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jMenuItem.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editor.getTela().limparPainelAcessivel();
                editor.getTela().atualizarPainelAcessivel(lblPadrao);
            }
        }; 
            
            
        }


}
