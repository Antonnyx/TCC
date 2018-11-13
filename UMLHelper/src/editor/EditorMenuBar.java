package editor;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import com.mxgraph.analysis.StructuralException;
import com.mxgraph.analysis.mxGraphProperties.GraphType;
import com.mxgraph.analysis.mxAnalysisGraph;
import com.mxgraph.analysis.mxGraphProperties;
import com.mxgraph.analysis.mxGraphStructure;
import com.mxgraph.analysis.mxTraversal;
import com.mxgraph.costfunction.mxCostFunction;
import editor.EditorActions.AlignCellsAction;
import editor.EditorActions.AutosizeAction;
import editor.EditorActions.BackgroundAction;
import editor.EditorActions.BackgroundImageAction;
import editor.EditorActions.ColorAction;
import editor.EditorActions.ExitAction;
import editor.EditorActions.GridColorAction;
import editor.EditorActions.GridStyleAction;
import editor.EditorActions.HistoryAction;
import editor.EditorActions.ImportAction;
import editor.EditorActions.KeyValueAction;
import editor.EditorActions.NewAction;
import editor.EditorActions.OpenAction;
import editor.EditorActions.PageBackgroundAction;
import editor.EditorActions.PageSetupAction;
import editor.EditorActions.PrintAction;
import editor.EditorActions.PromptPropertyAction;
import editor.EditorActions.PromptValueAction;
import editor.EditorActions.SaveAction;
import editor.EditorActions.ScaleAction;
import editor.EditorActions.SelectShortestPathAction;
import editor.EditorActions.SelectSpanningTreeAction;
import editor.EditorActions.SetLabelPositionAction;
import editor.EditorActions.SetStyleAction;
import editor.EditorActions.StyleAction;
import editor.EditorActions.StylesheetAction;
import editor.EditorActions.ToggleAction;
import editor.EditorActions.ToggleConnectModeAction;
import editor.EditorActions.ToggleCreateTargetItem;
import editor.EditorActions.ToggleDirtyAction;
import editor.EditorActions.ToggleGridItem;
import editor.EditorActions.ToggleOutlineItem;
import editor.EditorActions.TogglePropertyItem;
import editor.EditorActions.ToggleRulersItem;
import editor.EditorActions.WarningAction;
import editor.EditorActions.ZoomPolicyAction;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import br.edu.ifam.umlhelper.view.CriaTelaAjuda;
import view.CriarTelaDiagramaCasoDeUso;

public class EditorMenuBar extends JMenuBar
{

	/**
	 * 
	 */
        private MouseListener jMenuMouseListener;
        private MouseListener jMenuItemMouseListener, jMenuItemDatilologiaOn, jMenuItemDatilologiaOff;
        public CriarTelaDiagramaCasoDeUso telaUML;
        
	private static final long serialVersionUID = 4060203894740766714L;
        
	public enum AnalyzeType
	{
		IS_CONNECTED, IS_SIMPLE, IS_CYCLIC_DIRECTED, IS_CYCLIC_UNDIRECTED, COMPLEMENTARY, REGULARITY, COMPONENTS, MAKE_CONNECTED, MAKE_SIMPLE, IS_TREE, ONE_SPANNING_TREE, IS_DIRECTED, GET_CUT_VERTEXES, GET_CUT_EDGES, GET_SOURCES, GET_SINKS, PLANARITY, IS_BICONNECTED, GET_BICONNECTED, SPANNING_TREE, FLOYD_ROY_WARSHALL
	}
        private void sleep(int qtd){
            try {
                Thread.sleep(qtd);
            } catch (InterruptedException ex) {
                Logger.getLogger(EditorMenuBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Método para inicializar os mouseListeners para mostra a Datilologia em Libras dos itens da tela
        private void initMouseListeners(){
            
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
                    JMenu j = (JMenu)e.getComponent();
                    telaUML.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(j.getText().toLowerCase()));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    telaUML.limparPainelAcessivel();
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
                    JMenuItem j = (JMenuItem)e.getComponent();
                    telaUML.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(j.getText().toLowerCase()));    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                     telaUML.limparPainelAcessivel();           
                }
            };
            
            jMenuItemDatilologiaOn = new MouseListener() {
                
                
                @Override
                public void mouseClicked(MouseEvent e) {
                   
                }

                @Override
                public void mousePressed(MouseEvent e) {
                   JMenuItem j = (JMenuItem)e.getComponent();
                   j.setSelected(true);
                   telaUML.ligarAcessibilidade();
                   this.mouseExited(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JMenuItem j = (JMenuItem)e.getComponent();
                    telaUML.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(j.getText().toLowerCase()));    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                     telaUML.limparPainelAcessivel();           
                }
            };
         
            jMenuItemDatilologiaOff = new MouseListener() {
                
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    telaUML.desligarAcessibilidade();
                    this.mouseExited(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JMenuItem j = (JMenuItem)e.getComponent();
                    telaUML.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(j.getText().toLowerCase()));    
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                     telaUML.limparPainelAcessivel();           
                }
            };
        
        }
        

	public EditorMenuBar(final BasicGraphEditor editor, JFrame telaUML)
	{
                initMouseListeners();
                this.telaUML = (CriarTelaDiagramaCasoDeUso) telaUML;
		final mxGraphComponent graphComponent = editor.getGraphComponent();
		final mxGraph graph = graphComponent.getGraph();
		mxAnalysisGraph aGraph = new mxAnalysisGraph();
		JMenu menu = null;
		JMenu submenu = null;
                initMouseListeners();
		// Creates the file menu
		menu = add(new JMenu(mxResources.get("file")));
                menu.addMouseListener(jMenuMouseListener);
                
                Action action = editor.bind(mxResources.get("new"), new NewAction(), "/images/new.gif");
                
		menu.add(action).addMouseListener(jMenuItemMouseListener);
                action.setEnabled(true);
		menu.add(editor.bind(mxResources.get("openFile"), new OpenAction(), "/images/open.gif")).addMouseListener(jMenuItemMouseListener);

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("save"), new SaveAction(false), "/images/save.gif")).addMouseListener(jMenuItemMouseListener);
		menu.add(editor.bind(mxResources.get("saveAs"), new SaveAction(true), "/images/saveas.gif")).addMouseListener(jMenuItemMouseListener);
                

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("exit"), new ExitAction())).addMouseListener(jMenuItemMouseListener);
                
                menu.setEnabled(true);
		// Creates the edit menu
		menu = add(new JMenu(mxResources.get("edit")));
                menu.addMouseListener(jMenuMouseListener);
		menu.add(editor.bind(mxResources.get("undo"), new HistoryAction(true), "/images/undo.gif")).addMouseListener(jMenuItemMouseListener);
		menu.add(editor.bind(mxResources.get("redo"), new HistoryAction(false), "/images/redo.gif")).addMouseListener(jMenuItemMouseListener);
                
                menu.add(editor.bind(mxResources.get("edit"), mxGraphActions.getEditAction())).addMouseListener(jMenuItemMouseListener);
		menu.addSeparator();
 
		menu.add(editor.bind(mxResources.get("delete"), mxGraphActions.getDeleteAction(), "/images/delete.gif")).addMouseListener(jMenuItemMouseListener);
                
		// Creates the window menu
		menu = add(new JMenu(mxResources.get("window")));
                menu.addMouseListener(jMenuMouseListener);
		UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < lafs.length; i++)
		{
			final String clazz = lafs[i].getClassName();
			
			menu.add(new AbstractAction(lafs[i].getName())
			{
				
				private static final long serialVersionUID = 7588919504149148501L;

				public void actionPerformed(ActionEvent e)
				{
					editor.setLookAndFeel(clazz);
				}
			});
		}
                //Menu para habilitar/desabilitar libras
                menu = add(new JMenu("Opções"));
                menu.addMouseListener(jMenuMouseListener);
                menu.add("Datilologia ON").addMouseListener(jMenuItemDatilologiaOn);
                menu.add("Datilologia OFF").addMouseListener(jMenuItemDatilologiaOff);
                    
                

	}

	/**
	 * Adds menu items to the given shape menu. This is factored out because
	 * the shape menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateShapeMenu(JMenu menu, BasicGraphEditor editor)
	{
		menu.add(editor.bind(mxResources.get("home"), mxGraphActions.getHomeAction(), "/images/house.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("exitGroup"), mxGraphActions.getExitGroupAction(), "/images/up.gif"));
		menu.add(editor.bind(mxResources.get("enterGroup"), mxGraphActions.getEnterGroupAction(),
				"/images/down.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("group"), mxGraphActions.getGroupAction(), "/images/group.gif"));
		menu.add(editor.bind(mxResources.get("ungroup"), mxGraphActions.getUngroupAction(),
				"/images/ungroup.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("removeFromGroup"), mxGraphActions.getRemoveFromParentAction()));

		menu.add(editor.bind(mxResources.get("updateGroupBounds"), mxGraphActions.getUpdateGroupBoundsAction()));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("collapse"), mxGraphActions.getCollapseAction(),
				"/images/collapse.gif"));
		menu.add(editor.bind(mxResources.get("expand"), mxGraphActions.getExpandAction(), "/images/expand.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("toBack"), mxGraphActions.getToBackAction(), "/images/toback.gif"));
		menu.add(editor.bind(mxResources.get("toFront"), mxGraphActions.getToFrontAction(),
				"/images/tofront.gif"));

		menu.addSeparator();

		JMenu submenu = (JMenu) menu.add(new JMenu(mxResources.get("align")));

		submenu.add(editor.bind(mxResources.get("left"), new AlignCellsAction(mxConstants.ALIGN_LEFT),
				"/images/alignleft.gif"));
		submenu.add(editor.bind(mxResources.get("center"), new AlignCellsAction(mxConstants.ALIGN_CENTER),
				"/images/aligncenter.gif"));
		submenu.add(editor.bind(mxResources.get("right"), new AlignCellsAction(mxConstants.ALIGN_RIGHT),
				"/images/alignright.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("top"), new AlignCellsAction(mxConstants.ALIGN_TOP),
				"/images/aligntop.gif"));
		submenu.add(editor.bind(mxResources.get("middle"), new AlignCellsAction(mxConstants.ALIGN_MIDDLE),
				"/images/alignmiddle.gif"));
		submenu.add(editor.bind(mxResources.get("bottom"), new AlignCellsAction(mxConstants.ALIGN_BOTTOM),
				"/images/alignbottom.gif"));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("autosize"), new AutosizeAction()));

	}

	/**
	 * Adds menu items to the given format menu. This is factored out because
	 * the format menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateFormatMenu(JMenu menu, BasicGraphEditor editor)
	{
		JMenu submenu = (JMenu) menu.add(new JMenu(mxResources.get("background")));

		submenu.add(editor.bind(mxResources.get("fillcolor"), new ColorAction("Fillcolor", mxConstants.STYLE_FILLCOLOR),
				"/images/fillcolor.gif"));
                /*
		submenu.add(editor.bind(mxResources.get("gradient"), new ColorAction("Gradient", mxConstants.STYLE_GRADIENTCOLOR)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("image"), new PromptValueAction(mxConstants.STYLE_IMAGE, "Image")));
		submenu.add(editor.bind(mxResources.get("shadow"), new ToggleAction(mxConstants.STYLE_SHADOW)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("opacity"), new PromptValueAction(mxConstants.STYLE_OPACITY, "Opacity (0-100)")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("label")));

		submenu.add(editor.bind(mxResources.get("fontcolor"), new ColorAction("Fontcolor", mxConstants.STYLE_FONTCOLOR),
				"/images/fontcolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("labelFill"), new ColorAction("Label Fill", mxConstants.STYLE_LABEL_BACKGROUNDCOLOR)));
		submenu.add(editor.bind(mxResources.get("labelBorder"), new ColorAction("Label Border", mxConstants.STYLE_LABEL_BORDERCOLOR)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("rotateLabel"), new ToggleAction(mxConstants.STYLE_HORIZONTAL, true)));

		submenu.add(editor.bind(mxResources.get("textOpacity"), new PromptValueAction(mxConstants.STYLE_TEXT_OPACITY, "Opacity (0-100)")));

		submenu.addSeparator();

		JMenu subsubmenu = (JMenu) submenu.add(new JMenu(mxResources.get("position")));

		subsubmenu.add(editor.bind(mxResources.get("top"), new SetLabelPositionAction(mxConstants.ALIGN_TOP, mxConstants.ALIGN_BOTTOM)));
		subsubmenu.add(editor.bind(mxResources.get("middle"),
				new SetLabelPositionAction(mxConstants.ALIGN_MIDDLE, mxConstants.ALIGN_MIDDLE)));
		subsubmenu.add(editor.bind(mxResources.get("bottom"), new SetLabelPositionAction(mxConstants.ALIGN_BOTTOM, mxConstants.ALIGN_TOP)));

		subsubmenu.addSeparator();

		subsubmenu.add(editor.bind(mxResources.get("left"), new SetLabelPositionAction(mxConstants.ALIGN_LEFT, mxConstants.ALIGN_RIGHT)));
		subsubmenu.add(editor.bind(mxResources.get("center"),
				new SetLabelPositionAction(mxConstants.ALIGN_CENTER, mxConstants.ALIGN_CENTER)));
		subsubmenu.add(editor.bind(mxResources.get("right"), new SetLabelPositionAction(mxConstants.ALIGN_RIGHT, mxConstants.ALIGN_LEFT)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("wordWrap"), new KeyValueAction(mxConstants.STYLE_WHITE_SPACE, "wrap")));
		submenu.add(editor.bind(mxResources.get("noWordWrap"), new KeyValueAction(mxConstants.STYLE_WHITE_SPACE, null)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("hide"), new ToggleAction(mxConstants.STYLE_NOLABEL)));
*/
		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("line")));

		submenu.add(editor.bind(mxResources.get("linecolor"), new ColorAction("Linecolor", mxConstants.STYLE_STROKECOLOR),
				"/images/linecolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("orthogonal"), new ToggleAction(mxConstants.STYLE_ORTHOGONAL)));
		submenu.add(editor.bind(mxResources.get("dashed"), new ToggleAction(mxConstants.STYLE_DASHED)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("linewidth"), new PromptValueAction(mxConstants.STYLE_STROKEWIDTH, "Linewidth")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("connector")));

		submenu.add(editor.bind(mxResources.get("straight"), new SetStyleAction("straight"),
				"/images/straight.gif"));

		submenu.add(editor.bind(mxResources.get("horizontal"), new SetStyleAction(""), "/images/connect.gif"));
		submenu.add(editor.bind(mxResources.get("vertical"), new SetStyleAction("vertical"),
				"/images/vertical.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("entityRelation"), new SetStyleAction("edgeStyle=mxEdgeStyle.EntityRelation"),
				"/images/entity.gif"));
		//submenu.add(editor.bind(mxResources.get("arrow"), new SetStyleAction("arrow"), "/images/arrow.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("plain"), new ToggleAction(mxConstants.STYLE_NOEDGESTYLE)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("linestart")));

		submenu.add(editor.bind(mxResources.get("open"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OPEN),
				"/images/open_start.gif"));
		submenu.add(editor.bind(mxResources.get("classic"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC),
				"/images/classic_start.gif"));
		submenu.add(editor.bind(mxResources.get("block"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_BLOCK),
				"/images/block_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("diamond"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND),
				"/images/diamond_start.gif"));
		submenu.add(editor.bind(mxResources.get("oval"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OVAL),
				"/images/oval_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("none"), new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.NONE)));
		submenu.add(editor.bind(mxResources.get("size"), new PromptValueAction(mxConstants.STYLE_STARTSIZE, "Linestart Size")));

		submenu = (JMenu) menu.add(new JMenu(mxResources.get("lineend")));

		submenu.add(editor.bind(mxResources.get("open"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OPEN),
				"/images/open_end.gif"));
		submenu.add(editor.bind(mxResources.get("classic"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC),
				"/images/classic_end.gif"));
		submenu.add(editor.bind(mxResources.get("block"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK),
				"/images/block_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("diamond"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_DIAMOND),
				"/images/diamond_end.gif"));
		submenu.add(editor.bind(mxResources.get("oval"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OVAL),
				"/images/oval_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("none"), new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.NONE)));
		submenu.add(editor.bind(mxResources.get("size"), new PromptValueAction(mxConstants.STYLE_ENDSIZE, "Lineend Size")));

		

                /*
		submenu = (JMenu) menu.add(new JMenu(mxResources.get("alignment")));

		submenu.add(editor.bind(mxResources.get("left"), new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT),
				"/images/left.gif"));
		submenu.add(editor.bind(mxResources.get("center"), new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER),
				"/images/center.gif"));
		submenu.add(editor.bind(mxResources.get("right"), new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT),
				"/images/right.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("top"), new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP),
				"/images/top.gif"));
		submenu.add(editor.bind(mxResources.get("middle"), new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE),
				"/images/middle.gif"));
		submenu.add(editor.bind(mxResources.get("bottom"), new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM),
				"/images/bottom.gif"));
                */
                /*
		submenu = (JMenu) menu.add(new JMenu(mxResources.get("spacing")));

		submenu.add(editor.bind(mxResources.get("top"), new PromptValueAction(mxConstants.STYLE_SPACING_TOP, "Top Spacing")));
		submenu.add(editor.bind(mxResources.get("right"), new PromptValueAction(mxConstants.STYLE_SPACING_RIGHT, "Right Spacing")));
		submenu.add(editor.bind(mxResources.get("bottom"), new PromptValueAction(mxConstants.STYLE_SPACING_BOTTOM, "Bottom Spacing")));
		submenu.add(editor.bind(mxResources.get("left"), new PromptValueAction(mxConstants.STYLE_SPACING_LEFT, "Left Spacing")));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("global"), new PromptValueAction(mxConstants.STYLE_SPACING, "Spacing")));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("sourceSpacing"), new PromptValueAction(mxConstants.STYLE_SOURCE_PERIMETER_SPACING,
				mxResources.get("sourceSpacing"))));
		submenu.add(editor.bind(mxResources.get("targetSpacing"), new PromptValueAction(mxConstants.STYLE_TARGET_PERIMETER_SPACING,
				mxResources.get("targetSpacing"))));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("perimeter"), new PromptValueAction(mxConstants.STYLE_PERIMETER_SPACING,
				"Perimeter Spacing")));
                */
                /*
		submenu = (JMenu) menu.add(new JMenu(mxResources.get("direction")));

		submenu.add(editor.bind(mxResources.get("north"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_NORTH)));
		submenu.add(editor.bind(mxResources.get("east"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_EAST)));
		submenu.add(editor.bind(mxResources.get("south"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_SOUTH)));
		submenu.add(editor.bind(mxResources.get("west"), new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_WEST)));

		submenu.addSeparator();

		submenu.add(editor.bind(mxResources.get("rotation"), new PromptValueAction(mxConstants.STYLE_ROTATION, "Rotation (0-360)")));

		menu.addSeparator();

		menu.add(editor.bind(mxResources.get("rounded"), new ToggleAction(mxConstants.STYLE_ROUNDED)));

		menu.add(editor.bind(mxResources.get("style"), new StyleAction()));
                */
	}

	/**
	 *
	 */
	public static class InsertGraph extends AbstractAction
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4010463992665008365L;

		/**
		 * 
		 */
		protected GraphType graphType;

		protected mxAnalysisGraph aGraph;

		/**
		 * @param aGraph 
		 * 
		 */
		public InsertGraph(GraphType tree, mxAnalysisGraph aGraph)
		{
			this.graphType = tree;
			this.aGraph = aGraph;
		}

		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();

				// dialog = new FactoryConfigDialog();
				String dialogText = "";
				if (graphType == GraphType.NULL)
					dialogText = "Configure null graph";
				else if (graphType == GraphType.COMPLETE)
					dialogText = "Configure complete graph";
				else if (graphType == GraphType.NREGULAR)
					dialogText = "Configure n-regular graph";
				else if (graphType == GraphType.GRID)
					dialogText = "Configure grid graph";
				else if (graphType == GraphType.BIPARTITE)
					dialogText = "Configure bipartite graph";
				else if (graphType == GraphType.COMPLETE_BIPARTITE)
					dialogText = "Configure complete bipartite graph";
				else if (graphType == GraphType.BFS_DIR)
					dialogText = "Configure BFS algorithm";
				else if (graphType == GraphType.BFS_UNDIR)
					dialogText = "Configure BFS algorithm";
				else if (graphType == GraphType.DFS_DIR)
					dialogText = "Configure DFS algorithm";
				else if (graphType == GraphType.DFS_UNDIR)
					dialogText = "Configure DFS algorithm";
				else if (graphType == GraphType.DIJKSTRA)
					dialogText = "Configure Dijkstra's algorithm";
				else if (graphType == GraphType.BELLMAN_FORD)
					dialogText = "Configure Bellman-Ford algorithm";
				else if (graphType == GraphType.MAKE_TREE_DIRECTED)
					dialogText = "Configure make tree directed algorithm";
				else if (graphType == GraphType.KNIGHT_TOUR)
					dialogText = "Configure knight's tour";
				else if (graphType == GraphType.GET_ADJ_MATRIX)
					dialogText = "Configure adjacency matrix";
				else if (graphType == GraphType.FROM_ADJ_MATRIX)
					dialogText = "Input adjacency matrix";
				else if (graphType == GraphType.PETERSEN)
					dialogText = "Configure Petersen graph";
				else if (graphType == GraphType.WHEEL)
					dialogText = "Configure Wheel graph";
				else if (graphType == GraphType.STAR)
					dialogText = "Configure Star graph";
				else if (graphType == GraphType.PATH)
					dialogText = "Configure Path graph";
				else if (graphType == GraphType.FRIENDSHIP_WINDMILL)
					dialogText = "Configure Friendship Windmill graph";
				else if (graphType == GraphType.INDEGREE)
					dialogText = "Configure indegree analysis";
				else if (graphType == GraphType.OUTDEGREE)
					dialogText = "Configure outdegree analysis";
				GraphConfigDialog dialog = new GraphConfigDialog(graphType, dialogText);
				dialog.configureLayout(graph, graphType, aGraph);
				dialog.setModal(true);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = dialog.getSize();
				dialog.setLocation(screenSize.width / 2 - (frameSize.width / 2), screenSize.height / 2 - (frameSize.height / 2));
				dialog.setVisible(true);
			}
		}
	}

	/**
	 *
	 */
	public static class AnalyzeGraph extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6926170745240507985L;

		mxAnalysisGraph aGraph;

		/**
		 * 
		 */
		protected AnalyzeType analyzeType;

		/**
		 * Examples for calling analysis methods from mxGraphStructure 
		 */
		public AnalyzeGraph(AnalyzeType analyzeType, mxAnalysisGraph aGraph)
		{
			this.analyzeType = analyzeType;
			this.aGraph = aGraph;
		}

		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();
				aGraph.setGraph(graph);

				if (analyzeType == AnalyzeType.IS_CONNECTED)
				{
					boolean isConnected = mxGraphStructure.isConnected(aGraph);

					if (isConnected)
					{
						System.out.println("The graph is connected");
					}
					else
					{
						System.out.println("The graph is not connected");
					}
				}
				else if (analyzeType == AnalyzeType.IS_SIMPLE)
				{
					boolean isSimple = mxGraphStructure.isSimple(aGraph);

					if (isSimple)
					{
						System.out.println("The graph is simple");
					}
					else
					{
						System.out.println("The graph is not simple");
					}
				}
				else if (analyzeType == AnalyzeType.IS_CYCLIC_DIRECTED)
				{
					boolean isCyclicDirected = mxGraphStructure.isCyclicDirected(aGraph);

					if (isCyclicDirected)
					{
						System.out.println("The graph is cyclic directed");
					}
					else
					{
						System.out.println("The graph is acyclic directed");
					}
				}
				else if (analyzeType == AnalyzeType.IS_CYCLIC_UNDIRECTED)
				{
					boolean isCyclicUndirected = mxGraphStructure.isCyclicUndirected(aGraph);

					if (isCyclicUndirected)
					{
						System.out.println("The graph is cyclic undirected");
					}
					else
					{
						System.out.println("The graph is acyclic undirected");
					}
				}
				else if (analyzeType == AnalyzeType.COMPLEMENTARY)
				{
					graph.getModel().beginUpdate();

					mxGraphStructure.complementaryGraph(aGraph);

					mxGraphStructure.setDefaultGraphStyle(aGraph, true);
					graph.getModel().endUpdate();
				}
				else if (analyzeType == AnalyzeType.REGULARITY)
				{
					try
					{
						int regularity = mxGraphStructure.regularity(aGraph);
						System.out.println("Graph regularity is: " + regularity);
					}
					catch (StructuralException e1)
					{
						System.out.println("The graph is irregular");
					}
				}
				else if (analyzeType == AnalyzeType.COMPONENTS)
				{
					Object[][] components = mxGraphStructure.getGraphComponents(aGraph);
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < components.length; i++)
					{
						System.out.print("Component " + i + " :");

						for (int j = 0; j < components[i].length; j++)
						{
							System.out.print(" " + model.getValue(components[i][j]));
						}

						System.out.println(".");
					}

					System.out.println("Number of components: " + components.length);

				}
				else if (analyzeType == AnalyzeType.MAKE_CONNECTED)
				{
					graph.getModel().beginUpdate();

					if (!mxGraphStructure.isConnected(aGraph))
					{
						mxGraphStructure.makeConnected(aGraph);
						mxGraphStructure.setDefaultGraphStyle(aGraph, false);
					}

					graph.getModel().endUpdate();
				}
				else if (analyzeType == AnalyzeType.MAKE_SIMPLE)
				{
					mxGraphStructure.makeSimple(aGraph);
				}
				else if (analyzeType == AnalyzeType.IS_TREE)
				{
					boolean isTree = mxGraphStructure.isTree(aGraph);

					if (isTree)
					{
						System.out.println("The graph is a tree");
					}
					else
					{
						System.out.println("The graph is not a tree");
					}
				}
				else if (analyzeType == AnalyzeType.ONE_SPANNING_TREE)
				{
					try
					{
						graph.getModel().beginUpdate();
						aGraph.getGenerator().oneSpanningTree(aGraph, true, true);
						mxGraphStructure.setDefaultGraphStyle(aGraph, false);
						graph.getModel().endUpdate();
					}
					catch (StructuralException e1)
					{
						System.out.println("The graph must be simple and connected");
					}
				}
				else if (analyzeType == AnalyzeType.IS_DIRECTED)
				{
					boolean isDirected = mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED);

					if (isDirected)
					{
						System.out.println("The graph is directed.");
					}
					else
					{
						System.out.println("The graph is undirected.");
					}
				}
				else if (analyzeType == AnalyzeType.GET_CUT_VERTEXES)
				{
					Object[] cutVertices = mxGraphStructure.getCutVertices(aGraph);

					System.out.print("Cut vertices of the graph are: [");
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < cutVertices.length; i++)
					{
						System.out.print(" " + model.getValue(cutVertices[i]));
					}

					System.out.println(" ]");
				}
				else if (analyzeType == AnalyzeType.GET_CUT_EDGES)
				{
					Object[] cutEdges = mxGraphStructure.getCutEdges(aGraph);

					System.out.print("Cut edges of the graph are: [");
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < cutEdges.length; i++)
					{
						System.out.print(" " + Integer.parseInt((String) model.getValue(aGraph.getTerminal(cutEdges[i], true))) + "-"
								+ Integer.parseInt((String) model.getValue(aGraph.getTerminal(cutEdges[i], false))));
					}

					System.out.println(" ]");
				}
				else if (analyzeType == AnalyzeType.GET_SOURCES)
				{
					try
					{
						Object[] sourceVertices = mxGraphStructure.getSourceVertices(aGraph);
						System.out.print("Source vertices of the graph are: [");
						mxIGraphModel model = aGraph.getGraph().getModel();

						for (int i = 0; i < sourceVertices.length; i++)
						{
							System.out.print(" " + model.getValue(sourceVertices[i]));
						}

						System.out.println(" ]");
					}
					catch (StructuralException e1)
					{
						System.out.println(e1);
					}
				}
				else if (analyzeType == AnalyzeType.GET_SINKS)
				{
					try
					{
						Object[] sinkVertices = mxGraphStructure.getSinkVertices(aGraph);
						System.out.print("Sink vertices of the graph are: [");
						mxIGraphModel model = aGraph.getGraph().getModel();

						for (int i = 0; i < sinkVertices.length; i++)
						{
							System.out.print(" " + model.getValue(sinkVertices[i]));
						}

						System.out.println(" ]");
					}
					catch (StructuralException e1)
					{
						System.out.println(e1);
					}
				}
				else if (analyzeType == AnalyzeType.PLANARITY)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.IS_BICONNECTED)
				{
					boolean isBiconnected = mxGraphStructure.isBiconnected(aGraph);

					if (isBiconnected)
					{
						System.out.println("The graph is biconnected.");
					}
					else
					{
						System.out.println("The graph is not biconnected.");
					}
				}
				else if (analyzeType == AnalyzeType.GET_BICONNECTED)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.SPANNING_TREE)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.FLOYD_ROY_WARSHALL)
				{
					
					ArrayList<Object[][]> FWIresult = new ArrayList<Object[][]>();
					try
					{
						//only this line is needed to get the result from Floyd-Roy-Warshall, the rest is code for displaying the result
						FWIresult = mxTraversal.floydRoyWarshall(aGraph);

						Object[][] dist = FWIresult.get(0);
						Object[][] paths = FWIresult.get(1);
						Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());
						int vertexNum = vertices.length;
						System.out.println("Distances are:");

						for (int i = 0; i < vertexNum; i++)
						{
							System.out.print("[");

							for (int j = 0; j < vertexNum; j++)
							{
								System.out.print(" " + Math.round((Double) dist[i][j] * 100.0) / 100.0);
							}

							System.out.println("] ");
						}

						System.out.println("Path info:");

						mxCostFunction costFunction = aGraph.getGenerator().getCostFunction();
						mxGraphView view = aGraph.getGraph().getView();

						for (int i = 0; i < vertexNum; i++)
						{
							System.out.print("[");

							for (int j = 0; j < vertexNum; j++)
							{
								if (paths[i][j] != null)
								{
									System.out.print(" " + costFunction.getCost(view.getState(paths[i][j])));
								}
								else
								{
									System.out.print(" -");
								}
							}

							System.out.println(" ]");
						}

						try
						{
							Object[] path = mxTraversal.getWFIPath(aGraph, FWIresult, vertices[0], vertices[vertexNum - 1]);
							System.out.print("The path from " + costFunction.getCost(view.getState(vertices[0])) + " to "
									+ costFunction.getCost((view.getState(vertices[vertexNum - 1]))) + " is:");

							for (int i = 0; i < path.length; i++)
							{
								System.out.print(" " + costFunction.getCost(view.getState(path[i])));
							}

							System.out.println();
						}
						catch (StructuralException e1)
						{
							System.out.println(e1);
						}
					}
					catch (StructuralException e2)
					{
						System.out.println(e2);
					}
				}
			}
		}
	};
};