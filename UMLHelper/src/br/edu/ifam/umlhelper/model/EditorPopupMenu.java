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
import javafx.application.Application;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;
        private ImageIcon naoTemIcon = new ImageIcon(EditorPopupMenu.class.getResource("/br/edu/ifam/umlhelper/images/nao_ter.png"));
        private MouseListener jMenuItemMouseListener;
        private Editor editor;

	public EditorPopupMenu(Editor editor)
	{
            
                this.editor = editor;
                initListenersAcessibilidade();
                
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();
                Action tocarVideoAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        Object source = e.getSource();
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            if(!cell.getVideoPath().equals("")){
                         
                                try{
                                    String pathName = cell.getVideoPath();
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
                        
                        Object source = e.getSource();
                        
                        if (source instanceof mxGraphComponent)
                        {
                            
                            mxGraph graph = ((mxGraphComponent) source).getGraph();
                            mxCell cell = (mxCell) graph.getSelectionCell();
                            if(!cell.getVideoPathLibras().equals("")){
                         
                                try{
                                    String pathName = cell.getVideoPathLibras();
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
                
                add(
				editor.bind("Add Video", mxGraphActions
						.getAddVideoAction(),null)).addMouseListener(jMenuItemMouseListener);
				;
                
                
		
		add(
				editor.bind("Del Video", mxGraphActions
						.getRemoveVideoAction(),
						null))
				.addMouseListener(jMenuItemMouseListener);;
                add(editor.bind("Exibir Video", tocarVideoAction ,null)).addMouseListener(jMenuItemMouseListener);
                
                addSeparator();
                add(
				editor.bind("Add Video Libras", mxGraphActions
						.getAddVideoLibrasAction(),null))
				.addMouseListener(jMenuItemMouseListener);
                
                
		add(
				editor.bind("Del Video Libras", mxGraphActions
						.getRemoveVideoLibrasAction(),
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
                JMenuItem jMenuItem = (JMenuItem) e.getComponent();
                editor.getTela().atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(jMenuItem.getText().toLowerCase()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editor.getTela().limparPainelAcessivel();
            }
        }; 
            
            
        }


}
