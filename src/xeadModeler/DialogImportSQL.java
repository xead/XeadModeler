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

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import org.w3c.dom.*;

public class DialogImportSQL extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonStart = new JButton();
	private JButton jButtonClose = new JButton();
	private SortableXeadNodeComboBoxModel comboBoxModelSubsystem = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxSubsystem = new JComboBox(comboBoxModelSubsystem);
	private JTextArea jTextArea1 = new JTextArea();
	private JTextField jTextFieldImportFileName = new JTextField();
	private FontMetrics metrics = null;
	private JCheckBox jCheckBoxDataTypeControl = new JCheckBox();
	private JCheckBox jCheckBoxShowControl = new JCheckBox();
	private JCheckBox jCheckBoxTableNameControl = new JCheckBox();
	private JCheckBox jCheckBoxFieldNameControl = new JCheckBox();
	private JCheckBox jCheckBoxCommentControl = new JCheckBox();

	private int tableIDCounter = 0;
	private String textFileName = "";
	private String importResult = "";
	private Modeler frame_;
	private org.w3c.dom.Element systemElement;
	private org.w3c.dom.Element subsystemElement;
	private String subsystemID;
	private int numberOfTablesCreated = 0;
	private int numberOfTableRelationshipsCreated = 0;
	private String defaultDataTypeID = "";
	private String defaultTableTypeID = "";
	private String primaryKeyFieldID = "";

	private String[] sqlDataTypeArray = new String[84];
	private String[] sqlDataTypeArray2 = new String[40];
	private String[] nameArray = new String[1000];
	private String[] attrArray = new String[1000];
	private org.w3c.dom.Element[] tableElementArray = new org.w3c.dom.Element[1000];
	private int itemsOfArray = -1;
	private String[] hideFieldArray = new String[50];
	private int itemsOfHideFieldArray = -1;

	public DialogImportSQL(Modeler frame, String title, boolean modal) {
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

	public DialogImportSQL(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogImportSQL01"));

		sqlDataTypeArray[0] = " INTEGER ";
		sqlDataTypeArray[1] = " INT ";
		sqlDataTypeArray[2] = " TINYINT ";
		sqlDataTypeArray[3] = " SMALLINT ";
		sqlDataTypeArray[4] = " MEDIUMINT ";
		sqlDataTypeArray[5] = " BIGINT ";
		sqlDataTypeArray[6] = " REAL ";
		sqlDataTypeArray[7] = " DOUBLE ";
		sqlDataTypeArray[8] = " FLOAT ";
		sqlDataTypeArray[9] = " FLOAT(";
		sqlDataTypeArray[10] = " DECIMAL ";
		sqlDataTypeArray[11] = " DECIMAL(";
		sqlDataTypeArray[12] = " NUMERIC ";
		sqlDataTypeArray[13] = " NUMERIC(";
		sqlDataTypeArray[14] = " NUMBER ";
		sqlDataTypeArray[15] = " NUMBER(";
		sqlDataTypeArray[16] = " ENUM ";
		sqlDataTypeArray[17] = " ENUM(";
		sqlDataTypeArray[18] = " CHAR ";
		sqlDataTypeArray[19] = " CHAR(";
		sqlDataTypeArray[20] = " NCHAR ";
		sqlDataTypeArray[21] = " NCHAR(";
		sqlDataTypeArray[22] = " DATE ";
		sqlDataTypeArray[23] = " TIME ";
		sqlDataTypeArray[24] = " DATETIME ";
		sqlDataTypeArray[25] = " TIMESTAMP ";
		sqlDataTypeArray[26] = " BLOB ";
		sqlDataTypeArray[27] = " TEXT ";
		sqlDataTypeArray[28] = " TEXT(";
		sqlDataTypeArray[29] = " SET ";
		sqlDataTypeArray[30] = " BOOLEAN ";
		sqlDataTypeArray[31] = " BIT ";
		sqlDataTypeArray[32] = " integer ";
		sqlDataTypeArray[33] = " int ";
		sqlDataTypeArray[34] = " tinyint ";
		sqlDataTypeArray[35] = " smallint ";
		sqlDataTypeArray[36] = " mediumint ";
		sqlDataTypeArray[37] = " bigint ";
		sqlDataTypeArray[38] = " real ";
		sqlDataTypeArray[39] = " double ";
		sqlDataTypeArray[40] = " float ";
		sqlDataTypeArray[41] = " float(";
		sqlDataTypeArray[42] = " decimal ";
		sqlDataTypeArray[43] = " decimal(";
		sqlDataTypeArray[44] = " numeric ";
		sqlDataTypeArray[45] = " numeric(";
		sqlDataTypeArray[46] = " number ";
		sqlDataTypeArray[47] = " number(";
		sqlDataTypeArray[48] = " enum ";
		sqlDataTypeArray[49] = " enum(";
		sqlDataTypeArray[50] = " char ";
		sqlDataTypeArray[51] = " char(";
		sqlDataTypeArray[52] = " nchar ";
		sqlDataTypeArray[53] = " nchar(";
		sqlDataTypeArray[54] = " date ";
		sqlDataTypeArray[55] = " time ";
		sqlDataTypeArray[56] = " datetime ";
		sqlDataTypeArray[57] = " timestamp ";
		sqlDataTypeArray[58] = " blob ";
		sqlDataTypeArray[59] = " text ";
		sqlDataTypeArray[60] = " text(";
		sqlDataTypeArray[61] = " set ";
		sqlDataTypeArray[62] = " boolean ";
		sqlDataTypeArray[63] = " bit ";
		sqlDataTypeArray[64] = " VARCHAR ";
		sqlDataTypeArray[65] = " VARCHAR(";
		sqlDataTypeArray[66] = " varchar ";
		sqlDataTypeArray[67] = " varchar(";
		sqlDataTypeArray[68] = " LONGBLOB ";
		sqlDataTypeArray[69] = " longblob ";
		sqlDataTypeArray[70] = " INT(";
		sqlDataTypeArray[71] = " int(";
		sqlDataTypeArray[72] = " TINYINT(";
		sqlDataTypeArray[73] = " tinyint(";
		sqlDataTypeArray[74] = " LONGTEXT ";
		sqlDataTypeArray[75] = " longtext ";
		sqlDataTypeArray[76] = " TIMESTAMP(";
		sqlDataTypeArray[77] = " timestamp(";
		sqlDataTypeArray[78] = " VARCHAR2 ";
		sqlDataTypeArray[79] = " VARCHAR2(";
		sqlDataTypeArray[80] = " varchar2 ";
		sqlDataTypeArray[81] = " varchar2(";
		sqlDataTypeArray[82] = " RAW(";
		sqlDataTypeArray[83] = " raw(";

		sqlDataTypeArray2[0] = "INT";
		sqlDataTypeArray2[1] = "REAL";
		sqlDataTypeArray2[2] = "DOUBLE";
		sqlDataTypeArray2[3] = "FLOAT";
		sqlDataTypeArray2[4] = "DECIMAL";
		sqlDataTypeArray2[5] = "NUMERIC";
		sqlDataTypeArray2[6] = "NUMBER";
		sqlDataTypeArray2[7] = "ENUM";
		sqlDataTypeArray2[8] = "int";
		sqlDataTypeArray2[9] = "real";
		sqlDataTypeArray2[10] = "double";
		sqlDataTypeArray2[11] = "float";
		sqlDataTypeArray2[12] = "decimal";
		sqlDataTypeArray2[13] = "numeric";
		sqlDataTypeArray2[14] = "number";
		sqlDataTypeArray2[15] = "enum";
		sqlDataTypeArray2[16] = "CHAR";
		sqlDataTypeArray2[17] = "NCHAR";
		sqlDataTypeArray2[18] = "char";
		sqlDataTypeArray2[19] = "nchar";
		sqlDataTypeArray2[20] = "DATE";
		sqlDataTypeArray2[21] = "TIME";
		sqlDataTypeArray2[22] = "date";
		sqlDataTypeArray2[23] = "time";
		sqlDataTypeArray2[24] = "BLOB";
		sqlDataTypeArray2[25] = "blob";
		sqlDataTypeArray2[26] = "TEXT";
		sqlDataTypeArray2[27] = "text";
		sqlDataTypeArray2[28] = "SET";
		sqlDataTypeArray2[29] = "set";
		sqlDataTypeArray2[30] = "BOOLEAN";
		sqlDataTypeArray2[31] = "BIT";
		sqlDataTypeArray2[32] = "boolean";
		sqlDataTypeArray2[33] = "bit";
		sqlDataTypeArray2[34] = "VARCHAR";
		sqlDataTypeArray2[35] = "varchar";
		sqlDataTypeArray2[36] = "LONGBLOB";
		sqlDataTypeArray2[37] = "longblob";
		sqlDataTypeArray2[38] = "LONGTEXT";
		sqlDataTypeArray2[39] = "longtext";

		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogImportSQL02"));
		jLabel1.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 280, 25));
		jTextFieldImportFileName.setEditable(false);
		metrics = jTextFieldImportFileName.getFontMetrics(jTextFieldImportFileName.getFont());

		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogImportSQL03"));
		jLabel2.setBounds(new Rectangle(5, 43, 170, 20));
		jComboBoxSubsystem.setBounds(new Rectangle(180, 40, 280, 25));
		jComboBoxSubsystem.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxSubsystem.addActionListener(new DialogImportSQL_jComboBoxSubsystem_actionAdapter(this));

		jCheckBoxTableNameControl.setBounds(new Rectangle(9, 71, 460, 25));
		jCheckBoxTableNameControl.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTableNameControl.setText(res.getString("DialogImportSQL12"));
		jCheckBoxTableNameControl.setSelected(true);

		jCheckBoxCommentControl.setBounds(new Rectangle(9, 102, 460, 25));
		jCheckBoxCommentControl.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxCommentControl.setText(res.getString("DialogImportSQL14"));
		jCheckBoxCommentControl.setSelected(true);

		jCheckBoxFieldNameControl.setBounds(new Rectangle(9, 133, 460, 25));
		jCheckBoxFieldNameControl.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxFieldNameControl.setText(res.getString("DialogImportSQL13"));
		jCheckBoxFieldNameControl.setSelected(true);

		jCheckBoxDataTypeControl.setBounds(new Rectangle(9, 164, 460, 25));
		jCheckBoxDataTypeControl.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxDataTypeControl.setText(res.getString("DialogImportSQL10"));
		jCheckBoxDataTypeControl.setSelected(true);

		jCheckBoxShowControl.setBounds(new Rectangle(9, 195, 460, 25));
		jCheckBoxShowControl.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxShowControl.setText(res.getString("DialogImportSQL11"));
		jCheckBoxShowControl.setSelected(true);
		jCheckBoxShowControl.addChangeListener(new DialogImportSQL_jCheckBoxShowControl_changeAdapter(this));

		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setEditable(true);
		jTextArea1.setBounds(new Rectangle(9, 226, 455, 100));
		jTextArea1.setText(res.getString("DialogImportSQL04"));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());

		jProgressBar.setBounds(new Rectangle(20, 335, 200, 27));
		jButtonStart.setBounds(new Rectangle(20, 335, 200, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogImportSQL05"));
		jButtonStart.addActionListener(new DialogImportSQL_jButtonStart_actionAdapter(this));
		jButtonStart.setEnabled(false);
		jButtonClose.setBounds(new Rectangle(330, 335, 110, 27));
		jButtonClose.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonClose.setText(res.getString("DialogImportSQL06"));
		jButtonClose.addActionListener(new DialogImportSQL_jButtonClose_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(473, 373));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jTextFieldImportFileName, null);
		panelMain.add(jComboBoxSubsystem, null);
		panelMain.add(jCheckBoxTableNameControl, null);
		panelMain.add(jCheckBoxCommentControl, null);
		panelMain.add(jCheckBoxFieldNameControl, null);
		panelMain.add(jCheckBoxDataTypeControl, null);
		panelMain.add(jCheckBoxShowControl, null);
		panelMain.add(jTextArea1, null);
		panelMain.add(jProgressBar, null);
		panelMain.add(jButtonStart, null);
		panelMain.add(jButtonClose, null);

		this.getContentPane().add(panelMain, BorderLayout.SOUTH);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String name) {
		XeadNode node;
		NodeList elementList;
		importResult = "";

		textFileName = name;
		jTextFieldImportFileName.setText(textFileName);
		if (metrics.stringWidth(jTextFieldImportFileName.getText()) > jTextFieldImportFileName.getBounds().width) {
			jTextFieldImportFileName.setHorizontalAlignment(SwingConstants.RIGHT);
		} else {
			jTextFieldImportFileName.setHorizontalAlignment(SwingConstants.LEFT);
		}

		comboBoxModelSubsystem.removeAllElements();
		elementList = frame_.domDocument.getElementsByTagName("Subsystem");
		for (int i = 0; i < elementList.getLength(); i++) {
			node = new XeadNode("Subsystem",(org.w3c.dom.Element)elementList.item(i));
			comboBoxModelSubsystem.addElement((Object)node);
		}
		comboBoxModelSubsystem.sortElements();
		comboBoxModelSubsystem.insertElementAt(res.getString("DialogImportSQL07"), 0);
		comboBoxModelSubsystem.setSelectedItem(comboBoxModelSubsystem.getElementAt(0));

		jProgressBar.setValue(0);
		jProgressBar.setVisible(false);
		super.setVisible(true);

		return importResult;
	}

	void jComboBoxSubsystem_actionPerformed(ActionEvent e) {
		if (jComboBoxSubsystem.getSelectedIndex() != 0) {
			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		XeadNode workNode;
		org.w3c.dom.Element workElement;
		NodeList elementList;
		StringBuffer buffer = new StringBuffer();
		String wrkStr = "";

		numberOfTablesCreated = 0;
		numberOfTableRelationshipsCreated = 0;
		itemsOfArray = -1;
		tableIDCounter = 0;
		itemsOfHideFieldArray = -1;

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jButtonStart.setVisible(false);
			jProgressBar.setVisible(true);

			elementList = frame_.domDocument.getElementsByTagName("System");
			systemElement = (org.w3c.dom.Element)elementList.item(0);

			///////////////////////////////////////////
			// get ID of the subsystem imported into //
			///////////////////////////////////////////
			workNode = (XeadNode)comboBoxModelSubsystem.getSelectedItem();
			subsystemElement = workNode.getElement();
			subsystemID = subsystemElement.getAttribute("ID");

			////////////////////////////////////////////////////////////////
			// get ID of the TableType with the smallest value of SortKey //
			////////////////////////////////////////////////////////////////
			wrkStr = "";
			elementList = frame_.domDocument.getElementsByTagName("TableType");
			for (int i = 0; i < elementList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)elementList.item(i);
				if (wrkStr.equals("")) {
					wrkStr = workElement.getAttribute("SortKey");
					defaultTableTypeID = workElement.getAttribute("ID");
				} else {
					if (workElement.getAttribute("SortKey").compareTo(wrkStr) < 0) {
						wrkStr = workElement.getAttribute("SortKey");
						defaultTableTypeID = workElement.getAttribute("ID");
					}
				}
			}

			///////////////////////////////////////////////////////////////
			// get ID of the DataType with the smallest value of SortKey //
			///////////////////////////////////////////////////////////////
			wrkStr = "";
			elementList = frame_.domDocument.getElementsByTagName("DataType");
			for (int i = 0; i < elementList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)elementList.item(i);
				if (wrkStr.equals("")) {
					wrkStr = workElement.getAttribute("SortKey");
					defaultDataTypeID = workElement.getAttribute("ID");
				} else {
					if (workElement.getAttribute("SortKey").compareTo(wrkStr) < 0) {
						wrkStr = workElement.getAttribute("SortKey");
						defaultDataTypeID = workElement.getAttribute("ID");
					}
				}
			}

			try {
				BufferedReader br = new BufferedReader(new FileReader(textFileName));
				String line;
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				br.close();
			} catch (IOException ex) {}
			String statements = buffer.toString().toUpperCase();

			if (jCheckBoxShowControl.isSelected()) {
				setupHideFieldNameArray();
			}

			//////////////////////////////////////////////////////
			//Substring table name and its attributes into array//
			//////////////////////////////////////////////////////
			int bracketOpen = 0;
			int bracketClose = 0;
			int scanStartFrom = 0;
			int posOfCREATE_TABLE = 0;
			int posOfFirstBracket = 0;
			int posWork = 0;
			while (scanStartFrom < statements.length()) {
				posOfCREATE_TABLE = statements.indexOf("CREATE TABLE ", scanStartFrom);
				if (posOfCREATE_TABLE >= 0) {
					posOfFirstBracket = statements.indexOf("(", posOfCREATE_TABLE);
					if (posOfFirstBracket > 0) {
						posWork = posOfFirstBracket;
						bracketOpen = 0;
						bracketClose = 0;
						while (posWork < statements.length()) {
							wrkStr = statements.substring(posWork,posWork+1);
							if (wrkStr.equals("(")) {
								bracketOpen++;
							}
							if (wrkStr.equals(")")) {
								bracketClose++;
							}
							if (bracketOpen == bracketClose && bracketOpen > 0) {
								itemsOfArray++;
								nameArray[itemsOfArray] = trimBlankFromValue(statements.substring(posOfCREATE_TABLE+13, posOfFirstBracket));
								attrArray[itemsOfArray] = statements.substring(posOfFirstBracket+1, posWork);
								posOfCREATE_TABLE = posWork;
								break;
							}
							posWork++;
						}
					}
					scanStartFrom = posOfCREATE_TABLE+1;
				} else {
					scanStartFrom = statements.length();
				}
			}

			jProgressBar.setValue(0);
			jProgressBar.setMaximum(itemsOfArray + itemsOfArray);

			for (int p = 0; p <= itemsOfArray; p++) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				tableElementArray[p] = createTableDefinition(nameArray[p], attrArray[p]);
			}

			for (int p = 0; p <= itemsOfArray; p++) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				createTableRelationship(tableElementArray[p], attrArray[p]);
			}

			importResult = numberOfTablesCreated + res.getString("DialogImportSQL08") +  numberOfTableRelationshipsCreated + res.getString("DialogImportSQL09") +"\n" + "\n";

		} finally {
			jProgressBar.setValue(0);
			jButtonStart.setVisible(true);
			jProgressBar.setVisible(false);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.setVisible(false);
		}
	}

	void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void jCheckBoxShowControl_stateChanged() {
		if (jCheckBoxShowControl.isSelected()) {
			jTextArea1.setForeground(Color.BLACK);
			jTextArea1.setEditable(true);
			jTextArea1.setBackground(SystemColor.window);
		} else {
			jTextArea1.setForeground(Color.GRAY);
			jTextArea1.setEditable(false);
			jTextArea1.setBackground(SystemColor.control);
		}
	}

	void setupHideFieldNameArray() {
		String wrkStr = jTextArea1.getText().replaceAll("\n", "");
		StringTokenizer workTokenizer = new StringTokenizer(wrkStr, "," );
		int numberOfItem = workTokenizer.countTokens();
		for (int j = 0; j < numberOfItem; j++) {
			if (j < 50) {
				hideFieldArray[j] = trimBlankFromValue(workTokenizer.nextToken());
				itemsOfHideFieldArray = j+1;
			}
		}
	}

	org.w3c.dom.Element createTableDefinition(String tableName, String tableAttributes) {
		org.w3c.dom.Element lastElement;
		int lastID = 0;
		String[] fieldArray = new String[1000];

		///////////////////////////
		//Create Table Definition//
		///////////////////////////
		org.w3c.dom.Element tableElement = frame_.domDocument.createElement("Table");
		lastElement = getLastDomElementOfTheType("Table");
		if (lastElement == null) {
			lastID = 0;
		} else {
			lastID = Integer.parseInt(lastElement.getAttribute("ID"));
		}
		tableElement.setAttribute("ID", Integer.toString(lastID + 1));
		tableElement.setAttribute("Name", tableName);

		if (jCheckBoxTableNameControl.isSelected()) {
			tableIDCounter++;
			tableElement.setAttribute("SortKey", "T" + getFormatted3ByteString(tableIDCounter) + "0");
		} else {
			tableElement.setAttribute("SortKey", tableName);
		}
		tableElement.setAttribute("SubsystemID", subsystemID);
		tableElement.setAttribute("Descriptions", "");
		tableElement.setAttribute("TableTypeID", defaultTableTypeID);
		systemElement.appendChild(tableElement);

		//////////////////////
		//Create Primary Key//
		//////////////////////
		org.w3c.dom.Element primaryKeyElement = frame_.domDocument.createElement("TableKey");
		primaryKeyElement.setAttribute("ID", "1");
		primaryKeyElement.setAttribute("SortKey", "000");
		primaryKeyElement.setAttribute("Type", "PK");
		tableElement.appendChild(primaryKeyElement);
		primaryKeyFieldID = "";

		/////////////////////////////////////
		//Create Subsystem Table attributes//
		/////////////////////////////////////
		org.w3c.dom.Element subsystemTableElement = frame_.domDocument.createElement("SubsystemTable");
		subsystemTableElement.setAttribute("TableID", tableElement.getAttribute("ID"));
		int work = lastID % 10;
		work = 50 + work * 10;
		subsystemTableElement.setAttribute("BoxPosition", work + "," + work);
		subsystemTableElement.setAttribute("ExtDivLoc", "600");
		subsystemTableElement.setAttribute("IntDivLoc", "300");
		subsystemTableElement.setAttribute("ShowOnModel", "true");
		subsystemTableElement.setAttribute("ShowInstance", "false");
		subsystemTableElement.setAttribute("Instance", "");
		subsystemElement.appendChild(subsystemTableElement);

		///////////////////////////////////
		//Substring attributes into array//
		///////////////////////////////////
		int itemsOfArray = -1;
		int i = 0;
		int j = 0;
		int bracketOpen = 0;
		int bracketClose = 0;
		tableAttributes = tableAttributes.replaceAll("\n", "");
		tableAttributes = tableAttributes.replaceAll("\t", " ");
		tableAttributes = tableAttributes.replaceAll(",", " ,");
		int posStartFrom = 0;
		for (int pos = 0; pos < tableAttributes.length(); pos++) {
			if (tableAttributes.substring(pos, pos+1).equals(",") || pos+1 == tableAttributes.length()) {
				if (bracketOpen == bracketClose || pos+1 == tableAttributes.length()) {
					itemsOfArray++;
					fieldArray[itemsOfArray] = tableAttributes.substring(posStartFrom, pos);
					posStartFrom = pos + 1;
					bracketOpen = 0;
					bracketClose = 0;
				}
			}
			if (tableAttributes.substring(pos, pos+1).equals("(")) {
				bracketOpen++;
			}
			if (tableAttributes.substring(pos, pos+1).equals(")")) {
				bracketClose++;
			}
		}

		/////////////////////
		//Create TableField//
		/////////////////////
		int sortKey = 0;
		for (i = 0; i <= itemsOfArray; i++) {
			for (j = 0; j < sqlDataTypeArray.length; j++) {
				if (fieldArray[i].indexOf(sqlDataTypeArray[j], 0) > 0) {
					sortKey++;
					createFieldDefinition(tableElement, sortKey, fieldArray[i]);
					break;
				}
			}
		}

		////////////////////////////
		//Set Fields of PrimaryKey//
		////////////////////////////
		if (primaryKeyFieldID.equals("")) {
			for (i = 0; i <= itemsOfArray; i++) {
				if (fieldArray[i].indexOf("PRIMARY KEY", 0) >= 0) {
					setKeyFieldOfTheKey(primaryKeyElement, tableElement, fieldArray[i]);
					break;
				}
			}
		} else {
			org.w3c.dom.Element keyFieldElement = frame_.domDocument.createElement("TableKeyField");
			keyFieldElement.setAttribute("FieldID", primaryKeyFieldID);
			keyFieldElement.setAttribute("SortKey", "0010");
			primaryKeyElement.appendChild(keyFieldElement);
		}

		/////////////////////
		//Create SK and FK //
		/////////////////////
		for (i = 0; i <= itemsOfArray; i++) {
			if (fieldArray[i].indexOf("UNIQUE", 0) >= 0 && fieldArray[i].indexOf("PRIMARY KEY", 0) == -1) {
				createSecondaryKeyDefinition(tableElement, fieldArray[i]);
			}
		}

		numberOfTablesCreated++;

		return tableElement;
	}

	void createFieldDefinition(org.w3c.dom.Element tableElement, int sortKey, String fieldAttrString) {
		String fieldNameValue = "";
		String notNullValue = "false";
		String defaultValue = "";
		String commentValue = "";
		org.w3c.dom.Element lastElement;
		int lastID = 0;

		fieldNameValue = trimBlankFromValue(fieldAttrString);
		if (fieldNameValue.equals("")) {
			return;
		}

		////////////////
		//Get NOT NULL//
		////////////////
		if (fieldAttrString.indexOf("NOT NULL", 0) > 0) {
			notNullValue = "true";
		}

		////////////////
		//Get Default //
		////////////////
		defaultValue = "";
		int posStart = 0;
		int posEnd = 0;
		int i = fieldAttrString.indexOf(" DEFAULT ", 0);
		if (i > 0) {
			for (int j=i+8; j < fieldAttrString.length(); j++) {
				if (posStart == 0 && !fieldAttrString.substring(j,j+1).equals(" ")) {
					posStart = j;
				}
				posEnd = j;
				if (posStart > 0 && (fieldAttrString.substring(j,j+1).equals(" ") || fieldAttrString.substring(j,j+1).equals(","))) {
					break;
				}
			}
			if (posEnd > posStart) {
				defaultValue = fieldAttrString.substring(posStart, posEnd).replaceAll("'", "");
			}
		}

		////////////////
		//Get COMMENT //
		////////////////
		commentValue = "";
		posStart = 0;
		posEnd = 0;
		i = fieldAttrString.indexOf(" COMMENT ", 0);
		if (i > 0) {
			for (int j=i+9; j < fieldAttrString.length(); j++) {
				if (posStart == 0 && !fieldAttrString.substring(j,j+1).equals("'")) {
					posStart = j;
				}
				posEnd = j;
				if (posStart > 0 && fieldAttrString.substring(j,j+1).equals("'")) {
					break;
				}
			}
			if (posEnd > posStart) {
				commentValue = fieldAttrString.substring(posStart, posEnd);
			}
		}

		////////////////////////
		//Set field attributes//
		////////////////////////
		org.w3c.dom.Element fieldElement = frame_.domDocument.createElement("TableField");
		lastElement = getLastDomElementOfTheType("TableField");
		if (lastElement == null) {
			lastID = 0;
		} else {
			lastID = Integer.parseInt(lastElement.getAttribute("ID"));
		}
		fieldElement.setAttribute("ID", Integer.toString(lastID + 1));
		if (jCheckBoxCommentControl.isSelected()) {
			fieldElement.setAttribute("Name", commentValue);
			fieldElement.setAttribute("Descriptions", "");
		} else {
			fieldElement.setAttribute("Name", fieldNameValue);
			fieldElement.setAttribute("Descriptions", commentValue);
		}
		fieldElement.setAttribute("SortKey", getFormatted3ByteString(sortKey) + "0");
		if (jCheckBoxFieldNameControl.isSelected()) {
			fieldElement.setAttribute("Alias", fieldNameValue);
		} else {
			fieldElement.setAttribute("Alias", "");
		}
		fieldElement.setAttribute("AttributeType", "NATIVE");
		fieldElement.setAttribute("DataTypeID", getDataTypeID(fieldAttrString));
		fieldElement.setAttribute("ShowOnModel", "true");
		if (jCheckBoxShowControl.isSelected()) {
			for (i = 0; i < itemsOfHideFieldArray; i++) {
				if (hideFieldArray[i].equals(fieldNameValue)) {
					fieldElement.setAttribute("ShowOnModel", "false");
					break;
				}
			}
		}
		fieldElement.setAttribute("NotNull", notNullValue);
		fieldElement.setAttribute("Default", defaultValue);
		tableElement.appendChild(fieldElement);

		if (fieldAttrString.indexOf("PRIMARY KEY", 0) > 0) {
			primaryKeyFieldID = fieldElement.getAttribute("ID");
		}
	}

	String getDataTypeID(String fieldAttrString) {
		org.w3c.dom.Element workElement, lastElement;
		org.w3c.dom.Node nextSiblingNode;
		NodeList elementList;
		int lastID = 0;
		String wrkStr;
		String sqlExpression = "";
		String typeName = "";
		String lengthAndDecimal = "";
		String length = "";
		String decimal = "";
		int validPosFrom = 0;
		int validPosThru = 0;
		int j =0;
		int m =0;
		int n =0;
		int checkCounter = 0;
		String dataTypeID = defaultDataTypeID;

		if (jCheckBoxDataTypeControl.isSelected()) {
			for (int i = 0; i < sqlDataTypeArray.length; i++) {
				j = fieldAttrString.indexOf(sqlDataTypeArray[i], 0);
				if (j >= 0) {
					//Get start position of value//
					validPosFrom = j+1;
					//Get end position of value//
					validPosThru = j+1;
					for (int k = j+1; k < fieldAttrString.length(); k++) {
						wrkStr = fieldAttrString.substring(k, k+1);
						if (wrkStr.equals(" ")) {
							wrkStr = fieldAttrString.substring(k-1, k);
							if (!wrkStr.equals(",")) {
								validPosThru = k;
								break;
							}
						}
						if (wrkStr.equals(")")) {
							validPosThru = k+1;
							break;
						}
						validPosThru = k;
					}
					//
					if (validPosFrom < validPosThru) {
						sqlExpression = fieldAttrString.substring(validPosFrom, validPosThru).replaceAll(" ", "");
						elementList = frame_.domDocument.getElementsByTagName("DataType");
						checkCounter = 0;
						for (int k = 0; k < elementList.getLength(); k++) {
							workElement = (org.w3c.dom.Element)elementList.item(k);
							if (workElement.getAttribute("SortKey").equals(sqlExpression)) {
								dataTypeID = workElement.getAttribute("ID");
								checkCounter++;
								break;
							}
						}
						if (checkCounter == 0) {
							//Setup length and decimal//
							length = "5";
							decimal = "0";
							typeName = sqlExpression;
							m = sqlExpression.indexOf("(", 0);
							if (m >= 0) {
								n = sqlExpression.indexOf(")", 0);
								if (n >= 0) {
									typeName = sqlExpression.substring(0, m);
									lengthAndDecimal = sqlExpression.substring(m+1, n);
									StringTokenizer workTokenizer = new StringTokenizer(lengthAndDecimal, "," );
									length = workTokenizer.nextToken();
									if (workTokenizer.countTokens() > 0) {
										decimal = workTokenizer.nextToken();
									}
								}
							}

							org.w3c.dom.Element newElement = frame_.domDocument.createElement("DataType");
							lastElement = getLastDomElementOfTheType("DataType");
							if (lastElement == null) {
								lastID = 0;
							} else {
								lastID = Integer.parseInt(lastElement.getAttribute("ID"));
							}
							newElement.setAttribute("ID", Integer.toString(lastID + 1));
							newElement.setAttribute("Name", typeName);
							newElement.setAttribute("BasicType", getBasicTypeFromTypeName(typeName));
							newElement.setAttribute("SortKey", sqlExpression);
							newElement.setAttribute("Length", length);
							newElement.setAttribute("Decimal", decimal);
							if (newElement.getAttribute("BasicType").equals("Date")) {
								newElement.setAttribute("Length", "8");
								if (newElement.getAttribute("Name").equals("TIME") || newElement.getAttribute("Name").equals("time")) {
									newElement.setAttribute("Length", "6");
								}
								if (newElement.getAttribute("Name").equals("DATETIME") || newElement.getAttribute("Name").equals("datetime")) {
									newElement.setAttribute("Length", "12");
								}
								if (newElement.getAttribute("Name").equals("TIMESTAMP") || newElement.getAttribute("Name").equals("timestamp")) {
									newElement.setAttribute("Length", "14");
								}
							}
							if (newElement.getAttribute("BasicType").equals("Boolean")) {
								newElement.setAttribute("Length", "1");
							}
							if (newElement.getAttribute("BasicType").equals("Object")) {
								newElement.setAttribute("Length", "256");
							}
							newElement.setAttribute("SQLExpression", sqlExpression);
							nextSiblingNode = lastElement.getNextSibling();
							systemElement.insertBefore(newElement, nextSiblingNode);
							dataTypeID = newElement.getAttribute("ID");
						}
					}

					break;
				}
			}
		}

		return dataTypeID;
	}

	String getBasicTypeFromTypeName(String typeName) {
		String basicType = "String";
		for (int j = 0; j < sqlDataTypeArray2.length; j++) {
			if (typeName.indexOf(sqlDataTypeArray2[j], 0) >= 0) {
				if (j >= 0 &&  j <= 15) {basicType = "SignedNumber";}
				if (j >= 20 &&  j <= 23) {basicType = "Date";}
				if (j >= 24 &&  j <= 25) {basicType = "Object";}
				if (j >= 30 &&  j <= 33) {basicType = "Boolean";}
				break;
			}
		}
		return basicType;
	}

	void createSecondaryKeyDefinition(org.w3c.dom.Element tableElement, String fieldAttrString) {
		org.w3c.dom.Element secondaryKeyElement = frame_.domDocument.createElement("TableKey");
		secondaryKeyElement.setAttribute("ID", getNextKeyIDOfTheTable(tableElement));
		secondaryKeyElement.setAttribute("Type", "SK");
		secondaryKeyElement.setAttribute("SortKey", "010");
		setKeyFieldOfTheKey(secondaryKeyElement, tableElement, fieldAttrString);
		tableElement.appendChild(secondaryKeyElement);
	}

	void setKeyFieldOfTheKey(org.w3c.dom.Element keyElement, org.w3c.dom.Element tableElement, String fieldNames) {
		org.w3c.dom.Element element;

		int i = fieldNames.indexOf("(", 0);
		if (i >= 0) {
			int j = fieldNames.indexOf(")", i);
			if (j < 0) {
				fieldNames = fieldNames.substring(i+1, fieldNames.length());
			} else {
				fieldNames = fieldNames.substring(i+1, j);
			}
		}

		NodeList fieldList = tableElement.getElementsByTagName("TableField");
		StringTokenizer workTokenizer = new StringTokenizer(fieldNames, "," );
		String fieldName = "";
		int sortKey = 0;
		int countOfField = workTokenizer.countTokens();
		for (int k = 1; k <= countOfField; k++) {
			fieldName = trimBlankFromValue(workTokenizer.nextToken());
			for (int j = 0; j < fieldList.getLength(); j++) {
				element = (org.w3c.dom.Element)fieldList.item(j);
				if (element.getAttribute("Alias").equals(fieldName)) {
					org.w3c.dom.Element keyFieldElement = frame_.domDocument.createElement("TableKeyField");
					keyFieldElement.setAttribute("FieldID", element.getAttribute("ID"));
					sortKey++;
					keyFieldElement.setAttribute("SortKey", getFormatted3ByteString(sortKey) + "0");
					keyElement.appendChild(keyFieldElement);
					break;
				}
			}
		}
	}

	void createTableRelationship(org.w3c.dom.Element tableElement, String tableAttributes) {
		org.w3c.dom.Element workElement;
		String[] fieldArray = new String[1000];
		int lastID = 0;
		String wrkStr1 = "";
		String wrkStr2 = "";

		///////////////////////////////////
		//Substring attributes into array//
		///////////////////////////////////
		int itemsOfArray = -1;
		int i = 0;
		int j = 0;
		int bracketOpen = 0;
		int bracketClose = 0;
		while (j >= 0) {
			j = tableAttributes.indexOf(",", i);
			if (j >= 0) {
				wrkStr2 = wrkStr2 + tableAttributes.substring(i, j+1);
				//
				bracketOpen = 0;
				bracketClose = 0;
				for (int p = 0; p < wrkStr2.length(); p++) {
					wrkStr1 = wrkStr2.substring(p,p+1);
					if (wrkStr1.equals("(")) {
						bracketOpen++;
					}
					if (wrkStr1.equals(")")) {
						bracketClose++;
					}
				}
				if (bracketOpen == bracketClose) {
					itemsOfArray++;
					fieldArray[itemsOfArray] = wrkStr2;
					wrkStr2 = "";
				}
				i = j+1;
			} else {
				itemsOfArray++;
				fieldArray[itemsOfArray] = wrkStr2 + tableAttributes.substring(i, tableAttributes.length());
			}
		}

		//////////////////////
		//Create Foreign Key//
		//////////////////////
		int pos1 = 0;
		int pos2 = 0;
		int pos3 = 0;
		int pos4 = 0;
		int pos5 = 0;
		int pos6 = 0;
		String table1ID = "";
		String tableKey1ID = "";
		String tableKey1Type = "";
		int numberOfFieldsOfKey1 = 0;
		String table2ID = "";
		String tableKey2ID = "";
		String tableKey2Type = "";
		int numberOfFieldsOfKey2 = 0;
		String relationshipType = "";

		for (i = 0; i <= itemsOfArray; i++) {
			try {
				pos1 = fieldArray[i].indexOf("FOREIGN KEY", 0);
				pos2 = fieldArray[i].indexOf("(", pos1 + 10);
				pos3 = fieldArray[i].indexOf(")", pos2 + 1);
				pos4 = fieldArray[i].indexOf("REFERENCES", pos3 + 1);
				pos5 = fieldArray[i].indexOf("(", pos4 + 10);
				pos6 = fieldArray[i].indexOf(")", pos5 + 1);

				if (pos1 >= 0 && pos2 >= 0 && pos3 >= 0 && pos4 >= 0 && pos5 >= 0 && pos6 >= 0) {

					table2ID = tableElement.getAttribute("ID");

					workElement = createForeignKeyDefinition(tableElement, fieldArray[i].substring(pos2+1, pos3));
					if (workElement != null) {
						tableKey2ID = workElement.getAttribute("ID");
						tableKey2Type = workElement.getAttribute("Type");
						numberOfFieldsOfKey2 = workElement.getElementsByTagName("TableKeyField").getLength();

						workElement = getTableElementWithTableName(fieldArray[i].substring(pos4+10, pos5));
						if (workElement != null) {
							table1ID = workElement.getAttribute("ID");

							workElement = getUniqueKeyElementWithFieldNames(workElement, fieldArray[i].substring(pos5+1, pos6));
							if (workElement != null) {
								tableKey1ID = workElement.getAttribute("ID");
								tableKey1Type = workElement.getAttribute("Type");
								numberOfFieldsOfKey1 = workElement.getElementsByTagName("TableKeyField").getLength();

								if (tableKey1Type.equals("PK") || tableKey1Type.equals("SK")) {

									///////////////////////////////
									//Create Relationship Element//
									///////////////////////////////
									relationshipType = "";
									if (tableKey2Type.equals("PK") || tableKey2Type.equals("SK")) {
										if (numberOfFieldsOfKey1 == numberOfFieldsOfKey2) {
											relationshipType = "SUBTYPE";
										}
										if (numberOfFieldsOfKey1 < numberOfFieldsOfKey2) {
											relationshipType = "FAMILY";
										}
									}
									if (tableKey2Type.equals("FK")) {
										if (numberOfFieldsOfKey1 == numberOfFieldsOfKey2) {
											relationshipType = "REFFER";
										}
									}

									if (!relationshipType.equals("")) {
										org.w3c.dom.Element newElement1 = frame_.domDocument.createElement("Relationship");
										org.w3c.dom.Element lastElement = getLastDomElementOfTheType("Relationship");
										if (lastElement == null) {
											lastID = 0;
										} else {
											lastID = Integer.parseInt(lastElement.getAttribute("ID"));
										}
										newElement1.setAttribute("ID", Integer.toString(lastID + 1));
										newElement1.setAttribute("Table1ID", table1ID);
										newElement1.setAttribute("Table2ID", table2ID);
										newElement1.setAttribute("TableKey1ID", tableKey1ID);
										newElement1.setAttribute("TableKey2ID", tableKey2ID);
										newElement1.setAttribute("Type", relationshipType);
										newElement1.setAttribute("ExistWhen1", "");
										newElement1.setAttribute("ExistWhen2", "");
										systemElement.appendChild(newElement1);

										////////////////////////////////////////
										//Create SubsystemRelationship Element//
										////////////////////////////////////////
										org.w3c.dom.Element newElement2 = frame_.domDocument.createElement("SubsystemRelationship");
										newElement2.setAttribute("RelationshipID", newElement1.getAttribute("ID"));
										newElement2.setAttribute("TerminalIndex1", "17");
										newElement2.setAttribute("TerminalIndex2", "20");
										newElement2.setAttribute("ShowOnModel", "true");
										subsystemElement.appendChild(newElement2);

										numberOfTableRelationshipsCreated++;
									}
								}
							}
						}
					}
				}
			}
			catch (Exception ex) {
			}
		}
	}

	org.w3c.dom.Element createForeignKeyDefinition(org.w3c.dom.Element tableElement, String fieldNames) {
		org.w3c.dom.Element foreignKeyElement = null;
		org.w3c.dom.Element element1, element2;
		int index = 0;
		String[] fieldIDArray = new String[50];
		String wrkStr;

		//////////////////////////
		//trim brackets of string//
		//////////////////////////
		int i = fieldNames.indexOf("(", 0);
		if (i >= 0) {
			int j = fieldNames.indexOf(")", i);
			fieldNames = fieldNames.substring(i+1, j);
		}

		///////////////////////////////////////////
		//Setup fieldID array of this foreign key//
		///////////////////////////////////////////
		NodeList fieldList = tableElement.getElementsByTagName("TableField");
		StringTokenizer workTokenizer = new StringTokenizer(fieldNames, "," );
		String fieldName = "";
		int countOfField = workTokenizer.countTokens();
		for (int k = 1; k <= countOfField; k++) {
			fieldName = trimBlankFromValue(workTokenizer.nextToken());
			for (int j = 0; j < fieldList.getLength(); j++) {
				element1 = (org.w3c.dom.Element)fieldList.item(j);
				if (element1.getAttribute("Name").equals(fieldName)) {
					fieldIDArray[index] = element1.getAttribute("ID");
					index++;
					break;
				}
			}
		}

		////////////////////////////////////////////////
		//search corresponding PK(or SK) in this table//
		////////////////////////////////////////////////
		int countWork = 0;
		NodeList keyFieldList = null;
		NodeList keyList = tableElement.getElementsByTagName("TableKey");
		for (int k = 0; k < keyList.getLength(); k++) {
			element1 = (org.w3c.dom.Element)keyList.item(k);
			if (element1.getAttribute("Type").equals("PK") || element1.getAttribute("Type").equals("SK")) {
				countWork = 0;
				keyFieldList = element1.getElementsByTagName("TableKeyField");
				for (int j = 0; j < keyFieldList.getLength(); j++) {
					element2 = (org.w3c.dom.Element)keyFieldList.item(j);
					wrkStr = element2.getAttribute("FieldID");
					for (int m = 0; m < index; m++) {
						if (fieldIDArray[m].equals(wrkStr)) {
							countWork++;
						}
					}
				}
				if (keyFieldList.getLength() >= countWork && countWork > 0) {
					foreignKeyElement = element1;
					break;
				}
			}
		}

		if (foreignKeyElement == null) {
			foreignKeyElement = frame_.domDocument.createElement("TableKey");
			foreignKeyElement.setAttribute("ID", getNextKeyIDOfTheTable(tableElement));
			foreignKeyElement.setAttribute("Type", "FK");
			foreignKeyElement.setAttribute("SortKey", "010");
			setKeyFieldOfTheKey(foreignKeyElement, tableElement, fieldNames);
			tableElement.appendChild(foreignKeyElement);
		}

		return foreignKeyElement;
	}

	org.w3c.dom.Element getUniqueKeyElementWithFieldNames(org.w3c.dom.Element tableElement, String fieldNames) {
		org.w3c.dom.Element keyElement = null;
		org.w3c.dom.Element element1, element2;
		int index = 0;
		String[] fieldIDArray = new String[50];
		String wrkStr;

		///////////////////////////////////
		//trim brackets of fieldAttrString//
		///////////////////////////////////
		int i = fieldNames.indexOf("(", 0);
		if (i >= 0) {
			int j = fieldNames.indexOf(")", i);
			fieldNames = fieldNames.substring(i+1, j);
		}

		///////////////////////////////////
		//Setup fieldID array of this key//
		///////////////////////////////////
		NodeList fieldList = tableElement.getElementsByTagName("TableField");
		StringTokenizer workTokenizer = new StringTokenizer(fieldNames, "," );
		String fieldName = "";
		int countOfField = workTokenizer.countTokens();
		for (int k = 1; k <= countOfField; k++) {
			fieldName = trimBlankFromValue(workTokenizer.nextToken());
			for (int j = 0; j < fieldList.getLength(); j++) {
				element1 = (org.w3c.dom.Element)fieldList.item(j);
				if (element1.getAttribute("Name").equals(fieldName)) {
					fieldIDArray[index] = element1.getAttribute("ID");
					index++;
					break;
				}
			}
		}

		////////////////////////////////////////////////
		//search corresponding PK(or SK) in this table//
		////////////////////////////////////////////////
		int countWork = 0;
		NodeList keyFieldList = null;
		NodeList keyList = tableElement.getElementsByTagName("TableKey");
		for (int k = 0; k < keyList.getLength(); k++) {
			element1 = (org.w3c.dom.Element)keyList.item(k);
			if (element1.getAttribute("Type").equals("PK") || element1.getAttribute("Type").equals("SK")) {
				countWork = 0;
				keyFieldList = element1.getElementsByTagName("TableKeyField");
				for (int j = 0; j < keyFieldList.getLength(); j++) {
					element2 = (org.w3c.dom.Element)keyFieldList.item(j);
					wrkStr = element2.getAttribute("FieldID");
					for (int m = 0; m < index; m++) {
						if (fieldIDArray[m].equals(wrkStr)) {
							countWork++;
						}
					}
				}
				if (keyFieldList.getLength() == countWork) {
					keyElement = element1;
					break;
				}
			}
		}

		return keyElement;
	}

	org.w3c.dom.Element getTableElementWithTableName(String name) {
		org.w3c.dom.Element keyElement = null;
		String trimmedName = trimBlankFromValue(name);
		for (int i = 0; i <= itemsOfArray; i++) {
			if (nameArray[i].equals(trimmedName)) {
				keyElement = tableElementArray[i];
			}
		}
		return keyElement;
	}

	String trimBlankFromValue(String value) {
		int namePosFrom = -1;
		int namePosThru = -1;
		String wrkStr;
		String trimmedValue = "";

		for (int i = 0; i < value.length(); i++) {
			wrkStr = value.substring(i, i+1);
			if (!wrkStr.equals(" ") && namePosFrom == -1) {
				namePosFrom = i;
			}
			if (wrkStr.equals(" ") && namePosFrom != -1 && namePosThru == -1) {
				namePosThru = i;
				break;
			}
		}
		if (namePosThru == -1) {
			namePosThru = value.length();
		}

		if (namePosFrom < namePosThru) {
			trimmedValue = value.substring(namePosFrom, namePosThru);
		}

		return trimmedValue;
	}

	String getFormatted3ByteString(int number) {
		String formattedNumberString = "999";
		String numberString = Integer.toString(number);
		if (0 <= number && number < 10) {formattedNumberString = "00" + numberString;}
		if (10 <= number && number < 100) {formattedNumberString = "0" + numberString;}
		if (100 <= number && number < 1000) {formattedNumberString = numberString;}
		return formattedNumberString;
	}

	org.w3c.dom.Element getLastDomElementOfTheType(String tagName) {
		org.w3c.dom.Element element = null;
		org.w3c.dom.Element lastDomElement = null;
		int lastDomElementID = 0;
		int elementID = 0;
		NodeList nodeList = frame_.domDocument.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (lastDomElement == null) {
				lastDomElement = element;
			} else {
				elementID = Integer.parseInt(element.getAttribute("ID"));
				lastDomElementID = Integer.parseInt(lastDomElement.getAttribute("ID"));
				if (elementID > lastDomElementID) {
					lastDomElement = element;
				}
			}
		}
		return lastDomElement;
	}

	String getNextKeyIDOfTheTable(org.w3c.dom.Element tableElement) {
		org.w3c.dom.Element element = null;
		int workElementID = 0;
		int elementID = 0;
		NodeList nodeList = tableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (elementID == 0) {
				elementID = Integer.parseInt(element.getAttribute("ID"));
			} else {
				workElementID = Integer.parseInt(element.getAttribute("ID"));
				if (workElementID > elementID) {
					elementID = workElementID;
				}
			}
		}
		return Integer.toString(elementID + 1);
	}

	/**
	 * Class of Xead Tree Node
	 */
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

	/**
	 * Class of ComboBox-model to sort elements by its "SortKey"
	 */
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

//	/**
//	 * Class of Node Comparator
//	 */
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
}

class DialogImportSQL_jCheckBoxShowControl_changeAdapter implements ChangeListener {
	DialogImportSQL adaptee;

	DialogImportSQL_jCheckBoxShowControl_changeAdapter(DialogImportSQL adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBoxShowControl_stateChanged();
	}
}

class DialogImportSQL_jComboBoxSubsystem_actionAdapter implements java.awt.event.ActionListener {
	DialogImportSQL adaptee;

	DialogImportSQL_jComboBoxSubsystem_actionAdapter(DialogImportSQL adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxSubsystem_actionPerformed(e);
	}
}
class DialogImportSQL_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogImportSQL adaptee;

	DialogImportSQL_jButtonStart_actionAdapter(DialogImportSQL adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogImportSQL_jButtonClose_actionAdapter implements java.awt.event.ActionListener {
	DialogImportSQL adaptee;

	DialogImportSQL_jButtonClose_actionAdapter(DialogImportSQL adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonClose_actionPerformed(e);
	}
}
