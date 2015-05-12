package xeadModeler;

/*
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 *
 * This file is part of XEAD Modeler.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the XEAD Project nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;
import org.w3c.dom.*;
import xeadModeler.Modeler.XeadTreeNode;

public class DialogReorganizeDataTypes extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private BorderLayout borderLayoutMain = new BorderLayout();
	private JPanel panelMain = new JPanel();
	private JSplitPane jSplitPane = new JSplitPane();
	private JPanel jPanelSouth = new JPanel();
	private JPanel jPanelButtons = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JTextField jTextFieldDataTypeFrom = new JTextField();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextFieldDataTypeInto = new JTextField();
	private String targetDataTypeID = "";
	private TableModelReadOnlyList tableModelDataTypeList = new TableModelReadOnlyList();
	private JTable jTableDataTypeList = new JTable(tableModelDataTypeList);
	private JScrollPane jScrollPaneDataTypeList = new JScrollPane();
	private TableModelFieldList tableModelFieldList = new TableModelFieldList();
	private JTable jTableFieldList = new JTable(tableModelFieldList);
	private JScrollPane jScrollPaneFieldList = new JScrollPane();
	private TableColumn column0, column1, column2, column3, column4, column5, column6;
	private DefaultTableCellRenderer rendererTableHeader = null;
	private CheckBoxHeaderRenderer checkBoxHeaderRenderer = null;
	private DefaultTableCellRenderer rendererAlignmentCenter = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rendererAlignmentRight = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rendererAlignmentLeft = new DefaultTableCellRenderer();
	private JButton jButtonListDataTypes = new JButton();
	private JButton jButtonTransfer = new JButton();
	private JButton jButtonCloseDialog = new JButton();
	private JButton jButtonDeleteDataType = new JButton();
	private SortableDomElementFieldListModel sortableDomElementFieldListModel = new SortableDomElementFieldListModel();
	private DataTypePopupMenu dataTypePopupMenu = new DataTypePopupMenu();
	private JMenuItem jMenuItemAddDataType = new JMenuItem();
	private boolean updated = false;

	public DialogReorganizeDataTypes(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogReorganizeDataTypes(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {

		//panelMain//
		panelMain.setLayout(borderLayoutMain);
		panelMain.setPreferredSize(new Dimension(950, 700));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jSplitPane, BorderLayout.CENTER);
		panelMain.add(jPanelSouth, BorderLayout.SOUTH);

		//jPanelNorth and objects on it//
		jTableDataTypeList.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTableDataTypeList.setBackground(SystemColor.control);
		jTableDataTypeList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableDataTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableDataTypeList.getSelectionModel().addListSelectionListener(new DialogReorganizeDataTypes_jTableDataTypeList_listSelectionAdapter(this));
		jTableDataTypeList.addFocusListener(new Modeler_FocusListener());
		jTableDataTypeList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
		tableModelDataTypeList.addColumn("NO.");
		tableModelDataTypeList.addColumn(res.getString("S201"));
		tableModelDataTypeList.addColumn(res.getString("S289"));
		tableModelDataTypeList.addColumn(res.getString("S290"));
		tableModelDataTypeList.addColumn(res.getString("S291"));
		tableModelDataTypeList.addColumn(res.getString("S292"));
		tableModelDataTypeList.addColumn(res.getString("S293"));
		column0 = jTableDataTypeList.getColumnModel().getColumn(0);
		column1 = jTableDataTypeList.getColumnModel().getColumn(1);
		column2 = jTableDataTypeList.getColumnModel().getColumn(2);
		column3 = jTableDataTypeList.getColumnModel().getColumn(3);
		column4 = jTableDataTypeList.getColumnModel().getColumn(4);
		column5 = jTableDataTypeList.getColumnModel().getColumn(5);
		column6 = jTableDataTypeList.getColumnModel().getColumn(6);
		column0.setPreferredWidth(40);
		column1.setPreferredWidth(160);
		column2.setPreferredWidth(230);
		column3.setPreferredWidth(150);
		column4.setPreferredWidth(65);
		column5.setPreferredWidth(75);
		column6.setPreferredWidth(150);
		column0.setCellRenderer(rendererAlignmentCenter);
		column1.setCellRenderer(rendererAlignmentLeft);
		column2.setCellRenderer(rendererAlignmentLeft);
		column3.setCellRenderer(rendererAlignmentLeft);
		column4.setCellRenderer(rendererAlignmentRight);
		column5.setCellRenderer(rendererAlignmentRight);
		column6.setCellRenderer(rendererAlignmentLeft);
		jTableDataTypeList.getTableHeader().setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		rendererTableHeader = (DefaultTableCellRenderer)jTableDataTypeList.getTableHeader().getDefaultRenderer();
		rendererTableHeader.setHorizontalAlignment(2); //LEFT//
		jScrollPaneDataTypeList.getViewport().add(jTableDataTypeList, null);

		//jTableFieldList//
		jTableFieldList.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTableFieldList.setBackground(SystemColor.control);
		jTableFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableFieldList.setRowSelectionAllowed(true);
		jTableFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
		jTableFieldList.addMouseListener(new DialogReorganizeDataTypes_jTableFieldList_mouseAdapter(this));
		jTableFieldList.getTableHeader().setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		tableModelFieldList.addColumn("NO.");
		tableModelFieldList.addColumn("");
		tableModelFieldList.addColumn(res.getString("S297"));
		tableModelFieldList.addColumn(res.getString("S298"));
		tableModelFieldList.addColumn(res.getString("S299"));
		tableModelFieldList.addColumn(res.getString("S300"));
		column0 = jTableFieldList.getColumnModel().getColumn(0);
		column1 = jTableFieldList.getColumnModel().getColumn(1);
		column2 = jTableFieldList.getColumnModel().getColumn(2);
		column3 = jTableFieldList.getColumnModel().getColumn(3);
		column4 = jTableFieldList.getColumnModel().getColumn(4);
		column5 = jTableFieldList.getColumnModel().getColumn(5);
		column0.setPreferredWidth(40);
		column1.setPreferredWidth(30);
		column2.setPreferredWidth(130);
		column3.setPreferredWidth(150);
		column4.setPreferredWidth(300);
		column5.setPreferredWidth(250);
		rendererAlignmentCenter.setHorizontalAlignment(0); //CENTER//
		rendererAlignmentRight.setHorizontalAlignment(4); //RIGHT//
		rendererAlignmentLeft.setHorizontalAlignment(2); //LEFT//
		column0.setCellRenderer(rendererAlignmentCenter);
		CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
		column1.setCellRenderer(checkBoxRenderer);
		DefaultCellEditor editorWithCheckBox = new DefaultCellEditor(new JCheckBox());
		column1.setCellEditor(editorWithCheckBox);
		checkBoxHeaderRenderer = new CheckBoxHeaderRenderer(new CheckBoxHeaderListener());
		column1.setHeaderRenderer(checkBoxHeaderRenderer);
		column2.setCellRenderer(rendererAlignmentLeft);
		column3.setCellRenderer(rendererAlignmentLeft);
		column4.setCellRenderer(rendererAlignmentLeft);
		column5.setCellRenderer(rendererAlignmentLeft);
		rendererTableHeader = (DefaultTableCellRenderer)jTableFieldList.getTableHeader().getDefaultRenderer();
		rendererTableHeader.setHorizontalAlignment(2); //LEFT//
		jScrollPaneFieldList.getViewport().add(jTableFieldList, null);

		jSplitPane.setBorder(null);
		jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane.setDividerLocation(300);
		jSplitPane.add(jScrollPaneDataTypeList, JSplitPane.TOP);
		jSplitPane.add(jScrollPaneFieldList, JSplitPane.BOTTOM);

		//jPanelSouth and objects on it//
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setBorder(null);
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 90));
		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogReorganizeDataTypes02"));
		jLabel1.setBounds(new Rectangle(5, 13, 150, 20));
		jTextFieldDataTypeFrom.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldDataTypeFrom.setText("");
		jTextFieldDataTypeFrom.setBounds(new Rectangle(160, 10, 240, 25));
		jTextFieldDataTypeFrom.setEditable(false);
		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogReorganizeDataTypes03"));
		jLabel2.setBounds(new Rectangle(450, 13, 150, 20));
		jTextFieldDataTypeInto.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldDataTypeInto.setText("");
		jTextFieldDataTypeInto.setBounds(new Rectangle(605, 10, 240, 25));
		jTextFieldDataTypeInto.setEditable(false);
		jButtonListDataTypes.setBounds(new Rectangle(845, 9, 70, 27));
		jButtonListDataTypes.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonListDataTypes.setText("List");
		jButtonListDataTypes.addActionListener(new DialogReorganizeDataTypes_jButtonListDataTypes_actionAdapter(this));
		jButtonCloseDialog.setBounds(new Rectangle(30, 10, 120, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogReorganizeDataTypes04"));
		jButtonCloseDialog.addActionListener(new DialogReorganizeDataTypes_jButtonCloseDialog_actionAdapter(this));
		jButtonTransfer.setBounds(new Rectangle(250, 10, 350, 27));
		jButtonTransfer.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonTransfer.setText(res.getString("DialogReorganizeDataTypes05"));
		jButtonTransfer.addActionListener(new DialogReorganizeDataTypes_jButtonTransfer_actionAdapter(this));
		jButtonDeleteDataType.setBounds(new Rectangle(695, 10, 220, 27));
		jButtonDeleteDataType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonDeleteDataType.setText(res.getString("DialogReorganizeDataTypes06"));
		jButtonDeleteDataType.setEnabled(false);
		jButtonDeleteDataType.addActionListener(new DialogReorganizeDataTypes_jButtonDeleteDataType_actionAdapter(this));
		jPanelButtons.setBounds(new Rectangle(0, 45, 950, 47));
		jPanelButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelButtons.setLayout(null);
		jPanelButtons.add(jButtonCloseDialog, null);
		jPanelButtons.add(jButtonTransfer, null);
		jPanelButtons.add(jButtonDeleteDataType, null);
		jPanelSouth.add(jLabel1, null);
		jPanelSouth.add(jTextFieldDataTypeFrom, null);
		jPanelSouth.add(jLabel2, null);
		jPanelSouth.add(jTextFieldDataTypeInto, null);
		jPanelSouth.add(jPanelButtons, null);
		jPanelSouth.add(jButtonListDataTypes, null);

		//DialogReorganizeDataTypes//
		this.setTitle(res.getString("DialogReorganizeDataTypes01"));
		this.getContentPane().add(panelMain);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public boolean request() {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			updated = false;
			jButtonTransfer.setEnabled(false);
			jButtonDeleteDataType.setEnabled(false);
			jTextFieldDataTypeInto.setText("");
			targetDataTypeID = "";

			NodeList fieldList = frame_.domDocument.getElementsByTagName("TableField");
			sortableDomElementFieldListModel.removeAllElements();
			for (int j = 0; j < fieldList.getLength(); j++) {
				sortableDomElementFieldListModel.addElement((Object)fieldList.item(j));
			}
			sortableDomElementFieldListModel.sortElements();

			//Clear list rows//
			if (tableModelDataTypeList.getRowCount() > 0) {
				int rowCount = tableModelDataTypeList.getRowCount();
				for (int i = 0; i < rowCount; i++) {tableModelDataTypeList.removeRow(0);}
			}

			//Add rows to list//
			NodeList dataTypeList = frame_.domDocument.getElementsByTagName("DataType");
			dataTypePopupMenu.initializeMenuItem(dataTypeList);
			frame_.sortableDomElementListModel.removeAllElements();
			for (int i = 0; i < dataTypeList.getLength(); i++) {
				frame_.sortableDomElementListModel.addElement((Object)dataTypeList.item(i));
			}
			frame_.sortableDomElementListModel.sortElements();
			for (int i = 0; i < frame_.sortableDomElementListModel.getSize(); i++) {
				org.w3c.dom.Element element = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(i);
				Object[] Cell = new Object[7];
				Cell[0] =  new TableRowNumber(i+1, element);
				Cell[1] = element.getAttribute("SortKey");
				Cell[2] = element.getAttribute("Name");
				Cell[3] = element.getAttribute("BasicType");
				Integer length = new Integer(element.getAttribute("Length"));
				Cell[4] = length;
				Integer decimal = new Integer(element.getAttribute("Decimal"));
				Cell[5] = decimal;
				Cell[6] = element.getAttribute("SQLExpression");
				tableModelDataTypeList.addRow(Cell);
			}
			if (jTableDataTypeList.getRowCount() > 0) {
				jTableDataTypeList.setRowSelectionInterval(0, 0);
			}
		} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		super.setVisible(true);
		return updated;
	}

	static String substringLinesWithTokenOfEOL(String originalString, String stringToBeInserted) {
		StringBuffer processedString = new StringBuffer();
		int lastEnd = 0;
		for (int i = 0; i <= originalString.length(); i++) {
			if (i+5 <= originalString.length()) {
				if (originalString.substring(i,i+5).equals("#EOL#")) {
					processedString.append(originalString.substring(lastEnd, i));
					processedString.append(stringToBeInserted);
					lastEnd = i+5;
				}
			} else {
				if (i == originalString.length()) {
					processedString.append(originalString.substring(lastEnd, i));
				}
			}
		}
		return processedString.toString();
	}

	static String concatLinesWithTokenOfEOL(String originalString) {
		StringBuffer processedString = new StringBuffer();
		int lastEnd = 0;
		for (int i = 0; i <= originalString.length(); i++) {
			if (i < originalString.length()) {
				if (originalString.substring(i,i+1).equals("\n")) {
					processedString.append(originalString.substring(lastEnd, i));
					processedString.append("#EOL#");
					lastEnd = i+1;
				}
			} else {
				processedString.append(originalString.substring(lastEnd, i));
			}
		}
		return processedString.toString();
	}

	void jTableFieldList_mouseClicked(MouseEvent e) {
		jButtonTransfer.setEnabled(false);
		if (!targetDataTypeID.equals("")) {
			for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
				if (((Boolean)tableModelFieldList.getValueAt(i, 1)).booleanValue()) {
					jButtonTransfer.setEnabled(true);
					break;
				}
			}
		}
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	void jTableDataTypeList_valueChanged(ListSelectionEvent e) {
		String wrkStr1, wrkStr2 = "";

		jButtonTransfer.setEnabled(false);
		jButtonDeleteDataType.setEnabled(false);

		if (jTableDataTypeList.getSelectedRow() >= 0) {
			TableRowNumber tableRowNumber = (TableRowNumber)tableModelDataTypeList.getValueAt(jTableDataTypeList.getSelectedRow(), 0);
			org.w3c.dom.Element dataTypeElement = tableRowNumber.getElement();
			if (dataTypeElement.getAttribute("Decimal").equals("") || dataTypeElement.getAttribute("Decimal").equals("0")) {
				wrkStr1 = dataTypeElement.getAttribute("Length");
			} else {
				wrkStr1 = dataTypeElement.getAttribute("Length") + "." + dataTypeElement.getAttribute("Decimal");
			}
			String zenkaku = Modeler.getZenkakuOfHankaku(wrkStr1);
			if (dataTypeElement.getAttribute("Name").contains(wrkStr1)
					|| dataTypeElement.getAttribute("SortKey").contains(wrkStr1)
					|| dataTypeElement.getAttribute("Name").contains(zenkaku)) {
				wrkStr2 = dataTypeElement.getAttribute("Name");
			} else {
				wrkStr2 = dataTypeElement.getAttribute("Name") + "(" + wrkStr1 + ")";
			}
			if (wrkStr2.startsWith(dataTypeElement.getAttribute("SortKey"))) {
				jTextFieldDataTypeFrom.setText(wrkStr2);
			} else {
				jTextFieldDataTypeFrom.setText(dataTypeElement.getAttribute("SortKey") + " " + wrkStr2);
			}

			if (tableModelFieldList.getRowCount() > 0) {
				int rowCount = tableModelFieldList.getRowCount();
				for (int j = 0; j < rowCount; j++) {tableModelFieldList.removeRow(0);}
			}

			XeadTreeNode subsystemNode;
			org.w3c.dom.Element tableElement, fieldElement;
			int m = 0;
			for (int j = 0; j < sortableDomElementFieldListModel.getSize(); j++) {
				fieldElement = (org.w3c.dom.Element)sortableDomElementFieldListModel.getElementAt(j);
				if (fieldElement.getAttribute("DataTypeID").equals(tableRowNumber.getElement().getAttribute("ID"))) {
					Object[] Cell = new Object[6];
					m = m + 1;
					Cell[0] =  new TableRowNumber(m, fieldElement);
					Cell[1] = Boolean.FALSE;
					Cell[2] = fieldElement.getAttribute("Alias");
					Cell[3] = fieldElement.getAttribute("Name");
					tableElement = (org.w3c.dom.Element)fieldElement.getParentNode();
					Cell[4] = tableElement.getAttribute("SortKey") + " " + tableElement.getAttribute("Name");
					subsystemNode = frame_.getSpecificXeadTreeNode("Subsystem", tableElement.getAttribute("SubsystemID"), null);
					Cell[5] = subsystemNode.getElement().getAttribute("Name");
					tableModelFieldList.addRow(Cell);
				}
			}
			if (tableModelFieldList.getRowCount() == 0) {
				jButtonDeleteDataType.setEnabled(true);
			}
		}
	}

	void jButtonTransfer_actionPerformed(ActionEvent e) {
		if (!targetDataTypeID.equals("")) {
			for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
				if (((Boolean)tableModelFieldList.getValueAt(i, 1)).booleanValue()) {
					TableRowNumber tableRowNumber = (TableRowNumber)tableModelFieldList.getValueAt(i, 0);
					org.w3c.dom.Element element = tableRowNumber.getElement();
					element.setAttribute("DataTypeID", targetDataTypeID);
					updated = true;
				}
			}
			jTableDataTypeList_valueChanged(null);
		}
	}

	void jButtonListDataTypes_actionPerformed(ActionEvent e) {
		Component com = (Component)e.getSource();
		dataTypePopupMenu.show(com, 0, 0);
	}

	void jButtonDeleteDataType_actionPerformed(ActionEvent e) {
		int workInt = jTableDataTypeList.getSelectedRow();

		TableRowNumber tableRowNumber = (TableRowNumber)tableModelDataTypeList.getValueAt(workInt, 0);
		NodeList nodeList = frame_.domDocument.getElementsByTagName("System");
		nodeList.item(0).removeChild(tableRowNumber.getElement());

		tableModelDataTypeList.removeRow(jTableDataTypeList.getSelectedRow());
		if (tableModelDataTypeList.getRowCount() > 0) {
			if (workInt > (tableModelDataTypeList.getRowCount()-1)) {
				jTableDataTypeList.setRowSelectionInterval(tableModelDataTypeList.getRowCount()-1, tableModelDataTypeList.getRowCount()-1);
			} else {
				jTableDataTypeList.setRowSelectionInterval(workInt, workInt);
			}
		} else {
			jTextFieldDataTypeFrom.setText("");
			jButtonTransfer.setEnabled(false);
			jButtonDeleteDataType.setEnabled(false);
		}
	}

	class CheckBoxHeaderListener implements ItemListener {
		private CheckBoxHeaderRenderer rendererComponent_ = null;   
		public void setRenderer(CheckBoxHeaderRenderer rendererComponent) {
			rendererComponent_ = rendererComponent;
		}
		public void itemStateChanged(ItemEvent e) {   
			if (rendererComponent_ != null) {
				if (rendererComponent_.isSelected()) {
					for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
						tableModelFieldList.setValueAt(Boolean.TRUE, i, 1);
					}
					jButtonTransfer.setEnabled(true);
				} else {
					for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
						tableModelFieldList.setValueAt(Boolean.FALSE, i, 1);
					}
					jButtonTransfer.setEnabled(false);
				}
			}
		}   
	}   

	class CheckBoxHeaderRenderer extends JCheckBox implements TableCellRenderer, MouseListener {   
		private static final long serialVersionUID = 1L;
		protected CheckBoxHeaderRenderer rendererComponent;   
		protected int column;   
		protected boolean mousePressed = false;   
		public CheckBoxHeaderRenderer(CheckBoxHeaderListener itemListener) {   
			rendererComponent = this;   
			rendererComponent.addItemListener(itemListener);  
			itemListener.setRenderer(this);
		}   
		public Component getTableCellRendererComponent(	JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {  
			if (table != null) {   
				JTableHeader header = table.getTableHeader();   
				if (header != null) {   
					header.addMouseListener(rendererComponent);   
				}   
			}   
			setColumn(column);   
			rendererComponent.setText("");   
			rendererComponent.setBackground(new Color(219,219,219));   
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));   
			return rendererComponent;   
		}   
		protected void setColumn(int column) {   
			this.column = column;   
		}   
		public int getColumn() {   
			return column;   
		}   
		protected void handleClickEvent(MouseEvent e) {   
			if (mousePressed) {   
				mousePressed=false;   
				JTableHeader header = (JTableHeader)(e.getSource());   
				JTable tableView = header.getTable();   
				TableColumnModel columnModel = tableView.getColumnModel();   
				int viewColumn = columnModel.getColumnIndexAtX(e.getX());   
				int column = tableView.convertColumnIndexToModel(viewColumn);   
				if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {   
					doClick();   
				}   
			}   
		}   
		public void mouseClicked(MouseEvent e) {   
			handleClickEvent(e);   
			((JTableHeader)e.getSource()).repaint();   
		}   
		public void mousePressed(MouseEvent e) {   
			mousePressed = true;   
		}   
		public void mouseReleased(MouseEvent e) {   
		}   
		public void mouseEntered(MouseEvent e) {   
		}   
		public void mouseExited(MouseEvent e) {   
		}   
	}  

	class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		CheckBoxRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			setSelected((value != null && ((Boolean)value).booleanValue()));
			return this;
		}
	}

	class TableModelReadOnlyList extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int col) {return false;}
	}

	class TableModelFieldList extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable( int row, int col) {
			if (col != 1) {return false;} else {return true;}
		}
	}

	class TableRowNumber extends Object {
		private org.w3c.dom.Element element_;
		private int number_;
		public TableRowNumber(int num, org.w3c.dom.Element elm) {
			number_ = num;
			element_ = elm;
		}
		public String toString() {
			return Integer.toString(number_);
		}
		public org.w3c.dom.Element getElement() {
			return element_;
		}
	}

	class SortableXeadNodeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;
		public void sortElements() {
//			TreeSet<XeadNode> treeSet = new TreeSet<XeadNode>(new NodeComparator());
//			int elementCount = this.getSize();
//			XeadNode node;
//			for (int i = 0; i < elementCount; i++) {
//				node = (XeadNode)this.getElementAt(i);
//				treeSet.add(node);
//			}
//			this.removeAllElements();
//			Iterator<XeadNode> it = treeSet.iterator();
//			while( it.hasNext() ){
//				node = (XeadNode)it.next();
//				this.addElement(node);
//			}
			ArrayList<XeadNode> list = new ArrayList<XeadNode>();
			for (int i = 0; i < this.getSize(); i++) {
				list.add((XeadNode)this.getElementAt(i));
			}
			this.removeAllElements();
			Collections.sort(list);
			Iterator<XeadNode> it = list.iterator();
			while(it.hasNext()){
				this.addElement(it.next());
			}
		}
	}

//	class NodeComparator implements java.util.Comparator<XeadNode> {
//		public int compare(XeadNode node1, XeadNode node2) {
//			String value1, value2;
//			value1 = node1.getElement().getAttribute("SortKey");
//			value2 = node2.getElement().getAttribute("SortKey");
//			int compareResult = value1.compareTo(value2);
//			if (compareResult == 0) {
//				value1 = node1.getElement().getAttribute("ID");
//				value2 = node2.getElement().getAttribute("ID");
//				compareResult = value1.compareTo(value2);
//				if (compareResult == 0) {
//					compareResult = 1;
//				}
//			}
//			return(compareResult);
//		}
//	}

	class XeadNode implements Comparable {
		private String nodeType_;
		private org.w3c.dom.Element domNode_;
		public XeadNode(String type, org.w3c.dom.Element node) {
			super();
			nodeType_ = type;
			domNode_ = node;
		}
		public String toString() {
			String str = "";
			str = this.getName();
			return str;
		}
		public String getName() {
			String str = "";
			if (nodeType_.equals("Subsystem")) {
				str = domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
			return str;
		}
		public org.w3c.dom.Element getElement() {
			return domNode_;
		}
        public int compareTo(Object other) {
            XeadNode otherNode = (XeadNode)other;
            return domNode_.getAttribute("SortKey").compareTo(otherNode.getElement().getAttribute("SortKey"));
        }
	}

	class SortableDomElementFieldListModel extends DefaultListModel {
		private static final long serialVersionUID = 1L;
		public void addElement(Object object) {
			XeadFieldElement element = new XeadFieldElement((org.w3c.dom.Element)object);
			super.addElement(element);
		}
		public void sortElements() {
//			TreeSet<org.w3c.dom.Element> treeSet = new TreeSet<org.w3c.dom.Element>(new ElementFieldComparator());
//			int elementCount = this.getSize();
//			org.w3c.dom.Element domElement;
//			for (int i = 0; i < elementCount; i++) {
//				domElement = (org.w3c.dom.Element)this.getElementAt(i);
//				treeSet.add(domElement);
//			}
//			this.removeAllElements();
//			Iterator<org.w3c.dom.Element> it = treeSet.iterator();
//			while( it.hasNext() ){
//				domElement = (org.w3c.dom.Element)it.next();
//				this.addElement(domElement);
//			}
			ArrayList<XeadFieldElement> list = new ArrayList<XeadFieldElement>();
			for (int i = 0; i < this.getSize(); i++) {
				list.add((XeadFieldElement)super.getElementAt(i));
			}
			this.removeAllElements();
			Collections.sort(list);
			Iterator<XeadFieldElement> it = list.iterator();
			while(it.hasNext()){
				super.addElement((XeadFieldElement)it.next());
			}
		}
		public Object getElementAt(int index) {
			XeadFieldElement element = (XeadFieldElement)super.getElementAt(index);
			return element.getElement();
		}
	}

//	class ElementFieldComparator implements java.util.Comparator<org.w3c.dom.Element> {
//		public int compare(org.w3c.dom.Element element1, org.w3c.dom.Element element2) {
//			String value1, value2;
//			value1 = element1.getAttribute("Alias");
//			value2 = element2.getAttribute("Alias");
//			int compareResult = value1.compareTo(value2);
//			if (compareResult == 0) {
//				value1 = element1.getAttribute("ID");
//				value2 = element2.getAttribute("ID");
//				compareResult = value1.compareTo(value2);
//				if (compareResult == 0) {
//					compareResult = 1;
//				}
//			}
//			return(compareResult);
//		}
//	}

	class XeadFieldElement implements Comparable {
		private org.w3c.dom.Element domNode_;
		public XeadFieldElement(org.w3c.dom.Element node) {
			super();
			domNode_ = node;
		}
		public org.w3c.dom.Element getElement() {
			return domNode_;
		}
        public int compareTo(Object other) {
            XeadFieldElement otherNode = (XeadFieldElement)other;
            return domNode_.getAttribute("Alias").compareTo(otherNode.getElement().getAttribute("Alias"));
        }
	}

	class DataTypePopupMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;
		public DataTypePopupMenu(){
			super();
		}
		public void initializeMenuItem(NodeList nodeList){
			org.w3c.dom.Element element;
			String sortKeyOfDataType = "";
			String first2BytesOfsortKey = "";
			JMenu currentMenu = null;
			this.removeAll();
			frame_.sortableDomElementListModel.removeAllElements();
			for (int i = 0; i < nodeList.getLength(); i++) {
				frame_.sortableDomElementListModel.addElement((Object)nodeList.item(i));
			}
			frame_.sortableDomElementListModel.sortElements();
			for (int i = 0; i < frame_.sortableDomElementListModel.getSize(); i++) {
				element = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(i);
				sortKeyOfDataType = element.getAttribute("SortKey") + "  ";
				if (!first2BytesOfsortKey.equals(sortKeyOfDataType.substring(0,2)) || i==0) {
					first2BytesOfsortKey = sortKeyOfDataType.substring(0,2);
					currentMenu = new JMenu(first2BytesOfsortKey);
					this.add(currentMenu);
				}
				currentMenu.add(new DataTypeMenuItem(element));
			}
			this.add(jMenuItemAddDataType);
		}
	}

	class DataTypeMenuItem extends JMenuItem {
		private static final long serialVersionUID = 1L;
		String dataTypeID;
		org.w3c.dom.Element dataTypeElement;
		public DataTypeMenuItem(org.w3c.dom.Element element){
			super();
			String wrkStr1 = "";
			String wrkStr2 = "";
			String zenkaku = "";
			dataTypeElement = element;
			if (dataTypeElement.getAttribute("Decimal").equals("") || dataTypeElement.getAttribute("Decimal").equals("0")) {
				wrkStr1 = dataTypeElement.getAttribute("Length");
			} else {
				wrkStr1 = dataTypeElement.getAttribute("Length") + "." + dataTypeElement.getAttribute("Decimal");
			}
			zenkaku = Modeler.getZenkakuOfHankaku(wrkStr1);
			if (dataTypeElement.getAttribute("Name").contains(wrkStr1)
					|| dataTypeElement.getAttribute("SortKey").contains(wrkStr1)
					|| dataTypeElement.getAttribute("Name").contains(zenkaku)) {
				wrkStr2 = dataTypeElement.getAttribute("Name");
			} else {
				wrkStr2 = dataTypeElement.getAttribute("Name") + "(" + wrkStr1 + ")";
			}
			if (wrkStr2.startsWith(dataTypeElement.getAttribute("SortKey"))) {
				setText(wrkStr2);
			} else {
				setText(dataTypeElement.getAttribute("SortKey") + " " + wrkStr2);
			}
			dataTypeID = dataTypeElement.getAttribute("ID");
			addActionListener(new dataTypeMenuItemActionListener());
		}

		class dataTypeMenuItemActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				jTextFieldDataTypeInto.setText(getText());
				targetDataTypeID = dataTypeID;
				jTableFieldList_mouseClicked(null);
			}
		}
	}
}

class DialogReorganizeDataTypes_jTableDataTypeList_listSelectionAdapter  implements ListSelectionListener {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jTableDataTypeList_listSelectionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void valueChanged(ListSelectionEvent e) {
		adaptee.jTableDataTypeList_valueChanged(e);
	}
}

class DialogReorganizeDataTypes_jButtonTransfer_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jButtonTransfer_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonTransfer_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jButtonCloseDialog_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;

	DialogReorganizeDataTypes_jButtonCloseDialog_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jButtonDeleteDataType_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;

	DialogReorganizeDataTypes_jButtonDeleteDataType_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonDeleteDataType_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jButtonListDataTypes_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;

	DialogReorganizeDataTypes_jButtonListDataTypes_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonListDataTypes_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jTableFieldList_mouseAdapter extends java.awt.event.MouseAdapter {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jTableFieldList_mouseAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void mouseClicked(MouseEvent e) {
		adaptee.jTableFieldList_mouseClicked(e);
	}
}

