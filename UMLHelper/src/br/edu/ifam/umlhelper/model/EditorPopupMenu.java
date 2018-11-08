package br.edu.ifam.umlhelper.model;


import br.edu.ifam.umlhelper.model.EditorActions.HistoryAction;
import br.edu.ifam.umlhelper.util.Player;
import br.edu.ifam.umlhelper.util.Player2;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import java.awt.event.ActionEvent;
import javafx.application.Application;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;
        

	public EditorPopupMenu(Editor editor)
	{
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
                                JOptionPane.showMessageDialog(null,"Elemento não possui video");
                            }
                            
                        }
                      
                    }
                };
                
                add(
				editor.bind("Add Video", mxGraphActions
						.getAddVideoAction(),null))
				.setEnabled(selected);
                
                
		addSeparator();
		add(
				editor.bind("Del Video", mxGraphActions
						.getRemoveVideoAction(),
						null))
				.setEnabled(selected);
                

		addSeparator();
                add(editor.bind("Tocar Video", tocarVideoAction ,null)).setEnabled(selected);
                

		addSeparator();
                /*
                JMenuItem tocarVideoItem = new JMenuItem("Tocar Video");
                tocarVideoItem.addActionListener(tocarVideoAction);
                add(tocarVideoItem).setEnabled(true);
                */
		add(editor.bind(mxResources.get("undo"), new HistoryAction(true),"/br/edu/ifam/umlhelper/images/undo.gif"));
                add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(),"/br/edu/ifam/umlhelper/images/delete.gif"))
                        .setEnabled(selected);
                
                
               
	}

}
