package br.edu.ifam.umlhelper.model;


import br.edu.ifam.umlhelper.model.EditorActions.HistoryAction;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

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
		add(editor.bind(mxResources.get("undo"), new HistoryAction(true),"/br/edu/ifam/umlhelper/images/undo.gif"));
                add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(),"/br/edu/ifam/umlhelper/images/delete.gif"))
                        .setEnabled(selected);
                JMenuItem addVideoMenuItem = new JMenuItem("Add Video");
		add(addVideoMenuItem);
	}

}
