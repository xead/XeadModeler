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

import org.apache.xerces.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import xeadModeler.Modeler.XeadTreeNode;

public class DialogToListChangesOfFiles extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JScrollPane jScrollPane = new JScrollPane();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonStart = new JButton();
	private JCheckBox jCheckBoxListDiffDDL = new JCheckBox();
	private JButton jButtonCancel = new JButton();
	private JTextArea jTextArea = new JTextArea();
	private JLabel jLabel1 = new JLabel();
	private JTextField jTextFieldImportFileName = new JTextField();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextFieldImportSystemName = new JTextField();
	private Modeler frame_;
	private org.w3c.dom.Document domDocumentOld;
	private org.w3c.dom.Element systemElementOld, systemElementNew;
	private int countOfChanges = 0;
	private boolean containsValueToBeCorrected = false;
	private StringBuffer buffer;
	private HashMap<String, org.w3c.dom.Element> newDataTypeElementMap = new HashMap<String, org.w3c.dom.Element>();
	private HashMap<String, org.w3c.dom.Element> oldDataTypeElementMap = new HashMap<String, org.w3c.dom.Element>();
	
	public DialogToListChangesOfFiles(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DialogToListChangesOfFiles(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogToListChangesOfFiles1"));

		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogToListChangesOfFiles3"));
		jLabel1.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 650, 25));
		jTextFieldImportFileName.setEditable(false);

		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogToListChangesOfFiles4"));
		jLabel2.setBounds(new Rectangle(5, 43, 170, 20));
		jTextFieldImportSystemName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportSystemName.setBounds(new Rectangle(180, 40, 650, 25));
		jTextFieldImportSystemName.setEditable(false);

		jScrollPane.setBounds(new Rectangle(9, 73, 822, 458));
		jScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		jScrollPane.getViewport().add(jTextArea);
		jTextArea.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setBackground(SystemColor.control);
		jTextArea.setBorder(null);

		jProgressBar.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jProgressBar.setBounds(new Rectangle(30, 540, 420, 30));
		jProgressBar.setStringPainted(true);
		jProgressBar.setVisible(false);

		jButtonStart.setBounds(new Rectangle(30, 540, 420, 30));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogToListChangesOfFiles5"));
		jButtonStart.addActionListener(new DialogToListChangesOfFiles_jButtonStart_actionAdapter(this));

		jCheckBoxListDiffDDL.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxListDiffDDL.setText(res.getString("DialogToListChangesOfFiles36"));
		jCheckBoxListDiffDDL.setBounds(new Rectangle(460, 545, 235, 20));

		jButtonCancel.setBounds(new Rectangle(700, 540, 110, 30));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogToListChangesOfFiles6"));
		jButtonCancel.addActionListener(new DialogToListChangesOfFiles_jButtonCancel_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(840, 583));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jTextFieldImportFileName, null);
		panelMain.add(jTextFieldImportSystemName, null);
		panelMain.add(jScrollPane, null);
		panelMain.add(jProgressBar, null);
		panelMain.add(jButtonStart, null);
		panelMain.add(jCheckBoxListDiffDDL, null);
		panelMain.add(jButtonCancel, null);

		this.getContentPane().add(panelMain, BorderLayout.SOUTH);
		this.getRootPane().setDefaultButton(jButtonStart);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public void request(String fileName) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new FileInputStream(fileName)));
			domDocumentOld = parser.getDocument();

			NodeList elementList = domDocumentOld.getElementsByTagName("System");
			systemElementOld = (org.w3c.dom.Element)elementList.item(0);
			elementList = frame_.domDocument.getElementsByTagName("System");
			systemElementNew = (org.w3c.dom.Element)elementList.item(0);

			float importFileFormat = Float.parseFloat(systemElementOld.getAttribute("FormatVersion"));
			float appliFormat = Float.parseFloat(DialogAbout.FORMAT_VERSION);
			if (importFileFormat > appliFormat) {
				JOptionPane.showMessageDialog(this, res.getString("S1") + systemElementOld.getAttribute("FormatVersion") + res.getString("S2") + DialogAbout.FORMAT_VERSION + res.getString("S3"));
			} else {
				jTextFieldImportFileName.setText(fileName);
				jTextFieldImportSystemName.setText(systemElementOld.getAttribute("Name") + " V" + systemElementOld.getAttribute("Version"));
				jTextArea.setText(res.getString("DialogToListChangesOfFiles2"));
				jProgressBar.setValue(0);
				jProgressBar.setString(res.getString("DialogToListChangesOfFiles37"));
				jButtonStart.setEnabled(true);
				jCheckBoxListDiffDDL.setEnabled(true);
				this.getRootPane().setDefaultButton(jButtonStart);
				super.setVisible(true);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "XML format of the file is invalid. Process is canceled.");
		}
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));

			jButtonStart.setVisible(false);
			jProgressBar.setVisible(true);
			setupProgressMaxValue();
			buffer = new StringBuffer();
			countOfChanges = 0;

			listChangesOfBasicInfo();
			listChangesOfDataflow();
			listChangesOfRoleAndTask();
			listChangesOfSubsystem();
			listChangesOfTable();
			listChangesOfFunction();

			////////////////////////////////////
			// Generate text file of Diff-DDL //
			////////////////////////////////////
			if (jCheckBoxListDiffDDL.isSelected()) {
				FileWriter fileWriter = null;
				NodeList oldTableElementList = systemElementOld.getElementsByTagName("Table");
				NodeList newTableElementList = systemElementNew.getElementsByTagName("Table");
				BufferedWriter bufferedWriter = null;
				try {
					String diffDDL = getDiffDDL(newTableElementList, oldTableElementList);
					if (!diffDDL.equals("")) {
						File textFile = File.createTempFile("AlterTables" + frame_.getStringValueOfDateTime("withTime"), ".txt");
						String textFileName = textFile.getPath();
						fileWriter = new FileWriter(textFileName);
						bufferedWriter = new BufferedWriter(fileWriter);
						if (containsValueToBeCorrected) {
							diffDDL = frame_.ddlCommentMark + "Warning: It contains constraint id(s) indicated as '???' that should be checked and corrected before executing.\n\n" + diffDDL;
						}
						bufferedWriter.write(diffDDL);
						bufferedWriter.flush();
						frame_.desktop.open(textFile);
					}
				} catch (Exception ex1) {
					ex1.printStackTrace();
				} finally {
					try {
						bufferedWriter.close();
					} catch (Exception ex2) {}
				}
			}

		} finally {
			jTextArea.setText(countOfChanges + res.getString("DialogToListChangesOfFiles35") + buffer.toString());
			jTextArea.setCaretPosition(0);

			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			jButtonStart.setEnabled(false);
			jCheckBoxListDiffDDL.setEnabled(false);
			this.getRootPane().setDefaultButton(jButtonCancel);

			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	void setupProgressMaxValue() {
		NodeList workElementList;
		int countOfElementsProcessed = 0;

		workElementList = systemElementOld.getElementsByTagName("Department");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Department");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("TaskType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("TaskType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("TableType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("TableType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("DataType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("DataType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("FunctionType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("FunctionType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("Terms");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Terms");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("MaintenanceLog");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("MaintenanceLog");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();

		workElementList = systemElementOld.getElementsByTagName("SubjectArea");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("SubjectArea");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();

		workElementList = systemElementOld.getElementsByTagName("Role");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Role");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("Task");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Task");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();

		workElementList = systemElementOld.getElementsByTagName("Subsystem");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Subsystem");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("Table");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Table");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementOld.getElementsByTagName("Function");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		workElementList = systemElementNew.getElementsByTagName("Function");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();

		jProgressBar.setMaximum(countOfElementsProcessed + 1); // System Info counts 1//
	}

	void listChangesOfBasicInfo() {
		NodeList newElementList, oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("DialogToListChangesOfFiles8") + ">");

		/////////////////////////
		// System Descriptions //
		/////////////////////////
		compareNewAndOldElements(systemElementNew, systemElementOld, "Name", res.getString("DialogToListChangesOfFiles9"));
		compareNewAndOldElements(systemElementNew, systemElementOld, "Version", res.getString("DialogToListChangesOfFiles9"));
		compareNewAndOldElements(systemElementNew, systemElementOld, "RefFiles", res.getString("DialogToListChangesOfFiles9"));
		compareNewAndOldElements(systemElementNew, systemElementOld, "Descriptions", res.getString("DialogToListChangesOfFiles9"));

		/////////////////
		// Departments //
		/////////////////
		tagName = "Department";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////
		// Task Types //
		////////////////
		tagName = "TaskType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		/////////////////
		// Table Types //
		/////////////////
		tagName = "TableType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName("TableType");
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////
		// Data Types //
		////////////////
		tagName = "DataType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("BasicType");
		attrList.add("Length");
		attrList.add("Decimal");
		attrList.add("SQLExpression");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////////
		// Function Types //
		////////////////////
		tagName = "FunctionType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		///////////
		// Terms //
		///////////
		tagName = "Terms";
		attrList.clear();
		attrList.add("Header");
		attrList.add("HtmlFileName");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Header");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Header");

		/////////////////////
		// Maintenance Log //
		/////////////////////
		tagName = "MaintenanceLog";
		attrList.clear();
		attrList.add("Headder");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Header");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Header");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfDataflow() {
		NodeList newElementList, oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("S3385") + ">");

		///////////////////
		// Subject Areas //
		///////////////////
		tagName = "SubjectArea";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfRoleAndTask() {
		NodeList newElementList, oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("S3391") + ">");

		///////////
		// Roles //
		///////////
		tagName = "Role";
		attrList.clear();
		attrList.add("Name");
		attrList.add("DepartmentID");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		//////////
		// Task //
		//////////
		tagName = "Task";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("Event");
		attrList.add("TaskTypeID");
		attrList.add("RoleID");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfSubsystem() {
		NodeList newElementList, oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("S413") + ">");

		////////////////
		// Subsystems //
		////////////////
		tagName = "Subsystem";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("DatamodelDescriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfTable() {
		NodeList newElementList, oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		containsValueToBeCorrected = false;
		buffer.append("\n\n<" + res.getString("DialogToListChangesOfFiles10") + ">");

		////////////
		// Tables //
		////////////
		tagName = "Table";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("Alias");
		attrList.add("SynchFile");
		attrList.add("TableTypeID");
		attrList.add("SubsystemID");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	String getDiffDDL(NodeList newTableElementList, NodeList oldTableElementList) {
		StringBuffer bf = new StringBuffer();
		String wrkStr, tableID = "";
		XeadTreeNode subsystemNode, tableNode;
		org.w3c.dom.Element newTableElement, workElement, oldTableElement;
		NodeList dataTypeList;
		NodeList relationshipList = frame_.getDomDocument().getElementsByTagName("Relationship");
		NodeList oldSubsystemList = systemElementOld.getElementsByTagName("Subsystem");

		jProgressBar.setValue(0);
		jProgressBar.setString(res.getString("DialogToListChangesOfFiles38"));
		jProgressBar.setMaximum(newTableElementList.getLength() + oldTableElementList.getLength());

		////////////////////////
		// Setup DataType-Map //
		////////////////////////
		newDataTypeElementMap.clear();
		dataTypeList = systemElementNew.getElementsByTagName("DataType");
		for (int i = 0; i < dataTypeList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)dataTypeList.item(i);
			newDataTypeElementMap.put(workElement.getAttribute("ID"), workElement);
		}
		oldDataTypeElementMap.clear();
		dataTypeList = systemElementOld.getElementsByTagName("DataType");
		for (int i = 0; i < dataTypeList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)dataTypeList.item(i);
			oldDataTypeElementMap.put(workElement.getAttribute("ID"), workElement);
		}

		///////////////////////////////////
		// Setup Create/Alter statements //
		///////////////////////////////////
		for (int i = 0; i < frame_.getSubsystemListNode().getChildCount(); i++) {
			subsystemNode = (XeadTreeNode)frame_.getSubsystemListNode().getChildAt(i);
			for (int j = 0; j < subsystemNode.getChildAt(0).getChildCount(); j++) {
				tableNode = (XeadTreeNode)subsystemNode.getChildAt(0).getChildAt(j);
				newTableElement = (org.w3c.dom.Element)tableNode.getElement();

				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				oldTableElement = null;
				for (int k = 0; k < oldTableElementList.getLength(); k++) {
					workElement = (org.w3c.dom.Element)oldTableElementList.item(k);
					if (frame_.useTableNameAsTableNameInCreateTableStatement) {
						if (workElement.getAttribute("Name").equals(newTableElement.getAttribute("Name"))) {
							oldTableElement = workElement;
							break;
						}
					} else {
						if (workElement.getAttribute("Alias").equals("")) {
							if (newTableElement.getAttribute("Alias").equals("")) {
								if (workElement.getAttribute("SortKey").equals(newTableElement.getAttribute("SortKey"))) {
									oldTableElement = workElement;
									break;
								}
							} else {
								if (workElement.getAttribute("SortKey").equals(newTableElement.getAttribute("Alias"))) {
									oldTableElement = workElement;
									break;
								}
							}
						} else {
							if (newTableElement.getAttribute("Alias").equals("")) {
								if (workElement.getAttribute("Alias").equals(newTableElement.getAttribute("SortKey"))) {
									oldTableElement = workElement;
									break;
								}
							} else {
								if (workElement.getAttribute("Alias").equals(newTableElement.getAttribute("Alias"))) {
									oldTableElement = workElement;
									break;
								}
							}
						}
					}
				}
				if (oldTableElement == null) {
					bf.append(frame_.getCreateStatementByTable(subsystemNode, tableNode, relationshipList));
				} else {
					wrkStr = getAlterStatementsByTable(subsystemNode, tableNode, oldTableElement);
					if (!wrkStr.equals("")) {
						bf.append(wrkStr + "\n");
					}
				}
			}
		}

		///////////////////////////
		// Setup Drop statements //
		///////////////////////////
		for (int i = 0; i < oldTableElementList.getLength(); i++) {

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			oldTableElement = (org.w3c.dom.Element)oldTableElementList.item(i);
			newTableElement = null;
			for (int j = 0; j < newTableElementList.getLength(); j++) {
				workElement = (org.w3c.dom.Element)newTableElementList.item(j);
				if (frame_.useTableNameAsTableNameInCreateTableStatement) {
					tableID = oldTableElement.getAttribute("Name");
					if (workElement.getAttribute("Name").equals(tableID)) {
						newTableElement = workElement;
						break;
					}
				} else {
					if (workElement.getAttribute("Alias").equals("")) {
						if (oldTableElement.getAttribute("Alias").equals("")) {
							tableID = oldTableElement.getAttribute("SortKey");
							if (workElement.getAttribute("SortKey").equals(tableID)) {
								newTableElement = workElement;
								break;
							}
						} else {
							tableID = oldTableElement.getAttribute("Alias");
							if (workElement.getAttribute("SortKey").equals(tableID)) {
								newTableElement = workElement;
								break;
							}
						}
					} else {
						if (oldTableElement.getAttribute("Alias").equals("")) {
							tableID = oldTableElement.getAttribute("SortKey");
							if (workElement.getAttribute("Alias").equals(tableID)) {
								newTableElement = workElement;
								break;
							}
						} else {
							tableID = oldTableElement.getAttribute("Alias");
							if (workElement.getAttribute("Alias").equals(tableID)) {
								newTableElement = workElement;
								break;
							}
						}
					}
				}
			}
			if (newTableElement == null) {
				for (int j = 0; j < oldSubsystemList.getLength(); j++) {
					workElement = (org.w3c.dom.Element)oldSubsystemList.item(j);
					if (oldTableElement.getAttribute("SubsystemID").equals(workElement.getAttribute("ID"))) {
						if (oldTableElement.getAttribute("Alias").equals("")) {
							if (oldTableElement.getAttribute("SortKey").equals("")) {
								bf.append(frame_.ddlCommentMark + workElement.getAttribute("Name")
										+ "(" + workElement.getAttribute("SortKey") + ")"
										+ " - " + oldTableElement.getAttribute("Name")
										+ "\nDROP TABLE " + tableID + frame_.ddlSectionMark + "\n\n");
							} else {
								bf.append(frame_.ddlCommentMark + workElement.getAttribute("Name")
										+ "(" + workElement.getAttribute("SortKey") + ")"
										+ " - " + oldTableElement.getAttribute("Name")
										+ "(" + oldTableElement.getAttribute("SortKey") + ")"
										+ "\nDROP TABLE " + tableID + frame_.ddlSectionMark + "\n\n");
							}
						} else {
							bf.append(frame_.ddlCommentMark + workElement.getAttribute("Name")
									+ "(" + workElement.getAttribute("SortKey") + ")"
									+ " - " + oldTableElement.getAttribute("Name")
									+ "(" + oldTableElement.getAttribute("Alias") + ")"
									+ "\nDROP TABLE " + tableID + frame_.ddlSectionMark + "\n\n");
						}
						break;
					}
				}
			}
		}
		return bf.toString();
	}

	String getAlterStatementsByTable(XeadTreeNode subsystemNode, XeadTreeNode tableNode, org.w3c.dom.Element oldTableElement) {
		org.w3c.dom.Element keyFieldElement, dataTypeElement, fieldElement, keyElement, workElement, correspondingFieldElement;
		XeadTreeNode fieldNode, keyNode, workFieldNode;
		NodeList keyFieldList;
		boolean firstFieldStatement = true;
		int indexOfSK = 0; int indexOfFK = 0;
		String statement = "";
		String tableName, fieldName, fieldComment, dataType, dataTypeOld, defaultValue, defaultValueOld, keyFields, keyFieldsOld, wrkStr;
		boolean isNotNull, isNotNullOld, isNotFound;

		//List of old elements//
		NodeList oldFieldList = oldTableElement.getElementsByTagName("TableField");
		NodeList oldKeyList = oldTableElement.getElementsByTagName("TableKey");
		NodeList newRelationshipList = systemElementNew.getElementsByTagName("Relationship");
		NodeList oldRelationshipList = systemElementOld.getElementsByTagName("Relationship");

		//List of fields to be added//
		ArrayList<String> addFieldIDList = new ArrayList<String>();
		ArrayList<String> addFieldTypeList = new ArrayList<String>();
		ArrayList<String> addFieldDefaultList = new ArrayList<String>();
		ArrayList<Boolean> addFieldNotNullList = new ArrayList<Boolean>();
		ArrayList<String> addFieldCommentList = new ArrayList<String>();
		//List of fields to be modified//
		ArrayList<String> modifyFieldIDList = new ArrayList<String>();
		ArrayList<String> modifyFieldTypeList = new ArrayList<String>();
		ArrayList<String> modifyFieldDefaultList = new ArrayList<String>();
		ArrayList<Boolean> modifyFieldNotNullList = new ArrayList<Boolean>();
		//List of fields to be dropped//
		ArrayList<String> dropFieldIDList = new ArrayList<String>();
		//Variants to operate keys//
		boolean isToDropOldPrimaryKey = false;
		String addPrimaryKeyFieldIDs = "";
		ArrayList<String> dropUniqueKeyList = new ArrayList<String>();
		ArrayList<String> addUniqueKeyList = new ArrayList<String>();
		ArrayList<String> addUniqueKeyConstraintIDList = new ArrayList<String>();
		ArrayList<String> dropIndexKeyList = new ArrayList<String>();
		ArrayList<String> addIndexKeyList = new ArrayList<String>();
		//Variants to operate foreign keys//
		ArrayList<String> addForeignKeyConstraintIDList = new ArrayList<String>();
		ArrayList<String> addForeignKeyFieldIDsList = new ArrayList<String>();
		ArrayList<String> addForeignKeyRefferencesList = new ArrayList<String>();
		ArrayList<String> dropForeignKeyFieldIDsList = new ArrayList<String>();
		ArrayList<String> dropForeignKeyRefferencesList = new ArrayList<String>();

		//////////////////////
		// Setup Table Name //
		//////////////////////
		if (frame_.useTableNameAsTableNameInCreateTableStatement) {
			tableName = tableNode.getElement().getAttribute("Name");
		} else {
			if (tableNode.getElement().getAttribute("Alias").equals("")) {
				if (tableNode.getElement().getAttribute("SortKey").equals("")) {
					tableName = tableNode.getElement().getAttribute("Name");
				} else {
					tableName = tableNode.getElement().getAttribute("SortKey");
				}
			} else {
				tableName = tableNode.getElement().getAttribute("Alias");
			}
		}

		/////////////////////////////////////////
		// Scan fields to be added or modified //
		/////////////////////////////////////////
		for (int i = 0; i < tableNode.getChildAt(0).getChildCount(); i++) {
			fieldNode = (XeadTreeNode)tableNode.getChildAt(0).getChildAt(i);

			if (fieldNode.getElement().getAttribute("Alias").equals("")) {
				fieldName = fieldNode.getElement().getAttribute("Name");
				fieldComment = "";
			} else {
				fieldName = fieldNode.getElement().getAttribute("Alias");
				fieldComment = fieldNode.getElement().getAttribute("Name");
			}
			dataTypeElement = newDataTypeElementMap.get(fieldNode.getElement().getAttribute("DataTypeID"));
			if (dataTypeElement == null) {
				dataType = "Invalid-data-type";
			} else {
				if (dataTypeElement.getAttribute("SQLExpression").equals("")) {
					dataType = "Invalid-data-type";
				} else {
					dataType = dataTypeElement.getAttribute("SQLExpression");
				}
			}
			if (fieldNode.getElement().getAttribute("Default").equals("")) {
				defaultValue = "";
			} else {
				defaultValue = fieldNode.getElement().getAttribute("Default");
			}
			if (fieldNode.getElement().getAttribute("NotNull").equals("true")) {
				isNotNull = true;
			} else {
				isNotNull = false;
			}

			correspondingFieldElement = null;
			for (int j = 0; j < oldFieldList.getLength(); j++) {
				workElement = (org.w3c.dom.Element)oldFieldList.item(j);
				if (workElement.getAttribute("Alias").equals("")) {
					if (workElement.getAttribute("Name").equals(fieldName)) {
						correspondingFieldElement = workElement;
						break;
					}
				} else {
					if (workElement.getAttribute("Alias").equals(fieldName)) {
						correspondingFieldElement = workElement;
						break;
					}
				}
			}

			if (correspondingFieldElement == null) {
				addFieldIDList.add(fieldName);
				addFieldTypeList.add(dataType);
				addFieldDefaultList.add(defaultValue);
				addFieldNotNullList.add(isNotNull);
				addFieldCommentList.add(fieldComment);
			} else {
				workElement = oldDataTypeElementMap.get(correspondingFieldElement.getAttribute("DataTypeID"));
				if (workElement == null) {
					dataTypeOld = "Invalid-data-type";
				} else {
					if (workElement.getAttribute("SQLExpression").equals("")) {
						dataTypeOld = "Invalid-data-type";
					} else {
						dataTypeOld = workElement.getAttribute("SQLExpression");
					}
				}
				if (correspondingFieldElement.getAttribute("Default").equals("")) {
					defaultValueOld = "";
				} else {
					defaultValueOld = correspondingFieldElement.getAttribute("Default");
				}
				if (correspondingFieldElement.getAttribute("NotNull").equals("true")) {
					isNotNullOld = true;
				} else {
					isNotNullOld = false;
				}
				if (!dataType.equals(dataTypeOld) || !defaultValue.equals(defaultValueOld) || isNotNull != isNotNullOld) {
					modifyFieldIDList.add(fieldName);
					 if (dataType.equals(dataTypeOld)) {
						 modifyFieldTypeList.add(null);
					 } else {
						 modifyFieldTypeList.add(dataType);
					 }
					 if (defaultValue.equals(defaultValueOld)) {
						 modifyFieldDefaultList.add(null);
					 } else {
						 modifyFieldDefaultList.add(defaultValue);
					 }
					 if (isNotNull == isNotNullOld) {
						 modifyFieldNotNullList.add(null);
					 } else {
						 modifyFieldNotNullList.add(isNotNull);
					 }
				}
			}
		}

		///////////////////////////////
		// Scan fields to be dropped //
		///////////////////////////////
		for (int i = 0; i < oldFieldList.getLength(); i++) {
			fieldElement = (org.w3c.dom.Element)oldFieldList.item(i);
			if (fieldElement.getAttribute("Alias").equals("")) {
				fieldName = fieldElement.getAttribute("Name");
			} else {
				fieldName = fieldElement.getAttribute("Alias");
			}
			correspondingFieldElement = null;
			for (int j = 0; j < tableNode.getChildAt(0).getChildCount(); j++) {
				workElement = ((XeadTreeNode)tableNode.getChildAt(0).getChildAt(j)).getElement();
				if (workElement.getAttribute("Alias").equals("")) {
					if (workElement.getAttribute("Name").equals(fieldName)) {
						correspondingFieldElement = workElement;
						break;
					}
				} else {
					if (workElement.getAttribute("Alias").equals(fieldName)) {
						correspondingFieldElement = workElement;
						break;
					}
				}
			}
			if (correspondingFieldElement == null) {
				dropFieldIDList.add(fieldName);
			}
		}
		
		///////////////////////////
		// Scan keys to be added //
		///////////////////////////
		for (int i = 0; i < tableNode.getChildAt(1).getChildCount(); i++) {
			keyNode = (XeadTreeNode)tableNode.getChildAt(1).getChildAt(i);
			if (keyNode.getElement().getAttribute("Type").equals("SK")) {
				indexOfSK++;
			}
			if (keyNode.getElement().getAttribute("Type").equals("PK")
					|| keyNode.getElement().getAttribute("Type").equals("SK")
					|| keyNode.getElement().getAttribute("Type").equals("XK")) {
				keyFields = getKeyFieldIDs(tableNode.getElement(), keyNode.getElement(), systemElementNew);
				isNotFound = true;
				for (int j = 0; j < oldKeyList.getLength(); j++) {
					workElement = (org.w3c.dom.Element)oldKeyList.item(j);
					if (workElement.getAttribute("Type").equals(keyNode.getElement().getAttribute("Type"))) {
						keyFieldsOld = getKeyFieldIDs(oldTableElement, workElement, systemElementOld);
						if (keyFieldsOld.equals(keyFields)) {
							isNotFound = false;
							break;
						}
					}
				}
				if (isNotFound) {
					isToDropOldPrimaryKey = false;
					if (keyNode.getElement().getAttribute("Type").equals("PK")) {
						for (int j = 0; j < oldKeyList.getLength(); j++) {
							workElement = (org.w3c.dom.Element)oldKeyList.item(j);
							if (workElement.equals("PK")) {
								isToDropOldPrimaryKey = true;
								break;
							}
						}
					}
					frame_.sortableDomElementListModel.removeAllElements();
					keyFieldList = keyNode.getElement().getElementsByTagName("TableKeyField");
					for (int m = 0; m < keyFieldList.getLength(); m++) {
						frame_.sortableDomElementListModel.addElement((Object)keyFieldList.item(m));
					}
					if (frame_.sortableDomElementListModel.getSize() > 0) {
						frame_.sortableDomElementListModel.sortElements();
						wrkStr = "";
						for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
							keyFieldElement = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(m);
							workFieldNode = frame_.getSpecificXeadTreeNode("TableField", tableNode.getElement().getAttribute("ID"), keyFieldElement.getAttribute("FieldID"));
							if (keyNode.getElement().getAttribute("Type").equals("PK")) {
								if (m > 0) {
									addPrimaryKeyFieldIDs = addPrimaryKeyFieldIDs + ", ";
								}
								if (workFieldNode.getElement().getAttribute("Alias").equals("")) {
									addPrimaryKeyFieldIDs = addPrimaryKeyFieldIDs + workFieldNode.getElement().getAttribute("Name");
								} else {
									addPrimaryKeyFieldIDs = addPrimaryKeyFieldIDs + workFieldNode.getElement().getAttribute("Alias");
								}
							} else {
								if (m > 0) {
									wrkStr = wrkStr + ", ";
								}
								if (workFieldNode.getElement().getAttribute("Alias").equals("")) {
									wrkStr = wrkStr + workFieldNode.getElement().getAttribute("Name");
								} else {
									wrkStr = wrkStr + workFieldNode.getElement().getAttribute("Alias");
								}
							}
						}
						if (keyNode.getElement().getAttribute("Type").equals("SK")) {
							addUniqueKeyList.add(wrkStr);
							addUniqueKeyConstraintIDList.add(Integer.toString(indexOfSK));
						}
						if (keyNode.getElement().getAttribute("Type").equals("XK")) {
							addIndexKeyList.add(wrkStr);
						}
					}
				}
			}
		}

		/////////////////////////////
		// Scan keys to be dropped //
		/////////////////////////////
		for (int i = 0; i < oldKeyList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)oldKeyList.item(i);
			if (workElement.getAttribute("Type").equals("PK")
					|| workElement.getAttribute("Type").equals("SK")
					|| workElement.getAttribute("Type").equals("XK")) {
				keyFieldsOld = getKeyFieldIDs(oldTableElement, workElement, systemElementOld);
				isNotFound = true;
				for (int j = 0; j < tableNode.getChildAt(1).getChildCount(); j++) {
					keyNode = (XeadTreeNode)tableNode.getChildAt(1).getChildAt(j);
					if (workElement.getAttribute("Type").equals(keyNode.getElement().getAttribute("Type"))) {
						keyFields = getKeyFieldIDs(tableNode.getElement(), keyNode.getElement(), systemElementNew);
						if (keyFields.equals(keyFieldsOld)) {
							isNotFound = false;
							break;
						}
					}
				}
				if (isNotFound) {
					wrkStr = getKeyFieldIDs(oldTableElement, workElement, systemElementOld);
					if (!wrkStr.equals("")) {
						if (workElement.getAttribute("Type").equals("PK") || workElement.getAttribute("Type").equals("SK")) {
							dropUniqueKeyList.add(wrkStr);
							containsValueToBeCorrected = true;
						}
						if (workElement.getAttribute("Type").equals("XK")) {
							dropIndexKeyList.add(wrkStr);
						}
					}
				}
			}
		}

		//////////////////////////////////////////////
		// Scan foreign-key constraints to be added //
		//////////////////////////////////////////////
		if (!frame_.ignoreForeignKeyConstraints) {
			for (int i = 0; i < tableNode.getChildAt(1).getChildCount(); i++) {
				keyNode = (XeadTreeNode)tableNode.getChildAt(1).getChildAt(i);
				indexOfFK++;
				if (keyNode.getElement().getAttribute("Type").equals("PK")
						|| keyNode.getElement().getAttribute("Type").equals("SK")
						|| keyNode.getElement().getAttribute("Type").equals("FK")) {
					for (int j = 0; j < newRelationshipList.getLength(); j++) {
						workElement = (org.w3c.dom.Element)newRelationshipList.item(j);
						if (workElement.getAttribute("Table2ID").equals(tableNode.getElement().getAttribute("ID"))
								&& workElement.getAttribute("TableKey2ID").equals(keyNode.getElement().getAttribute("ID"))
								&& workElement.getAttribute("ExistWhen1").equals("")) {
							if (isLackingForeignKeyConstraint(workElement, tableNode.getElement(), systemElementNew, oldTableElement, systemElementOld)) {
								addForeignKeyConstraintIDList.add(Integer.toString(indexOfFK));
								addForeignKeyFieldIDsList.add(getKeyFieldIDs(tableNode.getElement(), keyNode.getElement(), systemElementNew));
								addForeignKeyRefferencesList.add(getRefferences(workElement, systemElementNew));
							}
							break;
						}
					}
				}
			}
		}

		////////////////////////////////////////////////
		// Scan foreign-key constraints to be dropped //
		////////////////////////////////////////////////
		if (!frame_.ignoreForeignKeyConstraints) {
			for (int i = 0; i < oldKeyList.getLength(); i++) {
				keyElement = (org.w3c.dom.Element)oldKeyList.item(i);
				if (keyElement.getAttribute("Type").equals("PK")
						|| keyElement.getAttribute("Type").equals("SK")
						|| keyElement.getAttribute("Type").equals("FK")) {
					for (int j = 0; j < oldRelationshipList.getLength(); j++) {
						workElement = (org.w3c.dom.Element)oldRelationshipList.item(j);
						if (workElement.getAttribute("Table2ID").equals(oldTableElement.getAttribute("ID"))
								&& workElement.getAttribute("TableKey2ID").equals(keyElement.getAttribute("ID"))
								&& workElement.getAttribute("ExistWhen1").equals("")) {
							if (isLackingForeignKeyConstraint(workElement, oldTableElement, systemElementOld, tableNode.getElement(), systemElementNew)) {
								dropForeignKeyFieldIDsList.add(getKeyFieldIDs(oldTableElement, keyElement, systemElementOld));
								dropForeignKeyRefferencesList.add(getRefferences(workElement, systemElementOld));
								containsValueToBeCorrected = true;
							}
							break;
						}
					}
				}
			}
		}

		////////////////////////////////////////
		// Generate ALTER-DROP-KEY statements //
		////////////////////////////////////////
		for (int i = 0; i < dropUniqueKeyList.size(); i++) {
			statement = statement + frame_.ddlCommentMark + "Following is the statement to drop unique-constraint with keys(" + dropUniqueKeyList.get(i) + "). Check value of constraint id and correct '???' before executing.\n";
			statement = statement + "ALTER TABLE " + tableName + " DROP CONSTRAINT ???" + frame_.ddlSectionMark + "\n";
		}
		for (int i = 0; i < dropIndexKeyList.size(); i++) {
			statement = statement + "ALTER TABLE " + tableName + " DROP INDEX (" + dropIndexKeyList.get(i) + ")" + frame_.ddlSectionMark + "\n";
		}

		////////////////////////////////////////////////
		// Generate ALTER-DROP-FOREIGN-KEY statements //
		////////////////////////////////////////////////
		for (int i = 0; i < dropForeignKeyFieldIDsList.size(); i++) {
			statement = statement + frame_.ddlCommentMark + "Following is the statement to drop refference-constraint with keys(" + dropForeignKeyFieldIDsList.get(i) + ") to " + dropForeignKeyRefferencesList.get(i) + ". Check value of constraint id and correct '???' before executing.\n";
			statement = statement + "ALTER TABLE " + tableName + " DROP CONSTRAINT ???" + frame_.ddlSectionMark + "\n";
		}

		////////////////////////////////////
		// Generate DROP-COLUMN statement //
		////////////////////////////////////
		firstFieldStatement = true;
		for (int i = 0; i < dropFieldIDList.size(); i++) {
			if (firstFieldStatement) {
				firstFieldStatement = false;
				statement = statement + "ALTER TABLE " + tableName + " DROP (" + "\n";
			} else {
				statement = statement + "," + "\n";
			}
			statement = statement + "\t" + dropFieldIDList.get(i);
		}
		if (dropFieldIDList.size() > 0) {
			statement = statement + ")" + frame_.ddlSectionMark + "\n";
		}

		///////////////////////////////////
		// Generate ADD-COLUMN statement //
		///////////////////////////////////
		firstFieldStatement = true;
		for (int i = 0; i < addFieldIDList.size(); i++) {
			if (firstFieldStatement) {
				firstFieldStatement = false;
				statement = statement + "ALTER TABLE " + tableName + " ADD (" + "\n";
			} else {
				statement = statement + "," + "\n";
			}
			statement = statement + "\t" + addFieldIDList.get(i);
			statement = statement + " " + addFieldTypeList.get(i);
			if (addFieldNotNullList.get(i)) {
				statement = statement + " NOT NULL";
			}
			statement = statement + " DEFAULT " + addFieldDefaultList.get(i);
			if (!addFieldCommentList.get(i).equals("") && frame_.setCommentToFieldsWithAlias) {
				statement = statement + " COMMENT '" + addFieldCommentList.get(i) + "'";
			}
		}
		if (addFieldIDList.size() > 0) {
			statement = statement + ")" + frame_.ddlSectionMark + "\n";
		}

		//////////////////////////////////////
		// Generate MODIFY-COLUMN statement //
		//////////////////////////////////////
		firstFieldStatement = true;
		for (int i = 0; i < modifyFieldIDList.size(); i++) {
			if (firstFieldStatement) {
				firstFieldStatement = false;
				statement = statement + "ALTER TABLE " + tableName + " MODIFY (" + "\n";
			} else {
				statement = statement + "," + "\n";
			}
			statement = statement + "\t" + modifyFieldIDList.get(i);
			if (modifyFieldTypeList.get(i) != null) {
				statement = statement + " " + modifyFieldTypeList.get(i);
			}
			if (modifyFieldNotNullList.get(i) != null) {
				if (modifyFieldNotNullList.get(i)) {
					statement = statement + " NOT NULL";
				} else {
					statement = statement + " NULL";
				}
			}
			if (modifyFieldDefaultList.get(i) != null) {
				statement = statement + " DEFAULT " + modifyFieldDefaultList.get(i);
			}
		}
		if (modifyFieldIDList.size() > 0) {
			statement = statement + ")" + frame_.ddlSectionMark + "\n";
		}

		///////////////////////////////////////
		// Generate ALTER-ADD-KEY statements //
		///////////////////////////////////////
		if (!addPrimaryKeyFieldIDs.equals("")) {
			if (isToDropOldPrimaryKey) {
				statement = statement + "ALTER TABLE " + tableName + " DROP CONSTRAINT " + tableName + "_PK PRIMARY KEY" + frame_.ddlSectionMark + "\n";
			}
			statement = statement + "ALTER TABLE " + tableName + " ADD CONSTRAINT " + tableName + "_PK PRIMARY KEY (" + addPrimaryKeyFieldIDs + ")" + frame_.ddlSectionMark + "\n";
		}
		for (int i = 0; i < addUniqueKeyList.size(); i++) {
			statement = statement + "ALTER TABLE " + tableName + " ADD CONSTRAINT " + tableName + "_SK" + addUniqueKeyConstraintIDList.get(i) + " UNIQUE (" + addUniqueKeyList.get(i) + ")" + frame_.ddlSectionMark + "\n";
		}
		for (int i = 0; i < addIndexKeyList.size(); i++) {
			statement = statement + "ALTER TABLE " + tableName + " ADD INDEX (" + addIndexKeyList.get(i) + ")" + frame_.ddlSectionMark + "\n";
		}

		///////////////////////////////////////////////
		// Generate ALTER-ADD-FOREIGN-KEY statements //
		///////////////////////////////////////////////
		for (int i = 0; i < addForeignKeyConstraintIDList.size(); i++) {
			statement = statement + "ALTER TABLE " + tableName + " ADD CONSTRAINT " + tableName + "_FK" + addForeignKeyConstraintIDList.get(i) + " FOREIGN KEY (" + addForeignKeyFieldIDsList.get(i) + ") REFFERENCES " + addForeignKeyRefferencesList.get(i) + frame_.ddlSectionMark + "\n";
		}

		/////////////////////
		// Header Comments //
		/////////////////////
		if (!statement.equals("")) {
			if (tableNode.getElement().getAttribute("Alias").equals("")) {
				if (tableNode.getElement().getAttribute("SortKey").equals("")) {
					statement = frame_.ddlCommentMark + subsystemNode.getElement().getAttribute("Name")
							+ "(" + subsystemNode.getElement().getAttribute("SortKey") + ")"
							+ " - " + tableNode.getElement().getAttribute("Name")
							+ "\n" + statement;
				} else {
					statement = frame_.ddlCommentMark + subsystemNode.getElement().getAttribute("Name")
							+ "(" + subsystemNode.getElement().getAttribute("SortKey") + ")"
							+ " - " + tableNode.getElement().getAttribute("Name")
							+ "(" + tableNode.getElement().getAttribute("SortKey") + ")"
							+ "\n" + statement;
				}
			} else {
				statement = frame_.ddlCommentMark + subsystemNode.getElement().getAttribute("Name")
						+ "(" + subsystemNode.getElement().getAttribute("SortKey") + ")"
						+ " - " + tableNode.getElement().getAttribute("Name")
						+ "(" + tableNode.getElement().getAttribute("Alias") + ")"
						+ "\n" + statement;
			}
		}

		return statement;
	}

	String getKeyFieldIDs(org.w3c.dom.Element tableElement, org.w3c.dom.Element keyElement, org.w3c.dom.Element systemElement) {
		String keyFieldIDs = "";
		org.w3c.dom.Element keyFieldElement, fieldElement;
		NodeList keyFieldList = keyElement.getElementsByTagName("TableKeyField");
		frame_.sortableDomElementListModel.removeAllElements();
		for (int m = 0; m < keyFieldList.getLength(); m++) {
			frame_.sortableDomElementListModel.addElement((Object)keyFieldList.item(m));
		}
		if (frame_.sortableDomElementListModel.getSize() > 0) {
			frame_.sortableDomElementListModel.sortElements();
			for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
				keyFieldElement = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(m);
				fieldElement = getFieldElementAccordingToID(tableElement.getAttribute("ID"), keyFieldElement.getAttribute("FieldID"), systemElement);
				if (m > 0) {
					keyFieldIDs = keyFieldIDs + ", ";
				}
				if (fieldElement.getAttribute("Alias").equals("")) {
					keyFieldIDs = keyFieldIDs + fieldElement.getAttribute("Name");
				} else {
					keyFieldIDs = keyFieldIDs + fieldElement.getAttribute("Alias");
				}
				if (keyElement.getAttribute("Type").equals("XK") && keyFieldElement.getAttribute("AscDesc").equals("D")) {
					keyFieldIDs = keyFieldIDs + "(D)";
				}
			}
		}
		return keyFieldIDs;
	}

	String getRefferences(org.w3c.dom.Element relationshipElement, org.w3c.dom.Element systemElement) {
		String value = "";
		String tableName = "";
		String fieldNames = "";
		org.w3c.dom.Element tableElement = null;
		org.w3c.dom.Element tableKeyElement = null;
		org.w3c.dom.Element workElement;

		String tableID = relationshipElement.getAttribute("Table1ID");
		String tableKeyID = relationshipElement.getAttribute("TableKey1ID");

		NodeList tableElementList = systemElement.getElementsByTagName("Table");
		for (int i = 0; i < tableElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)tableElementList.item(i);
			if (workElement.getAttribute("ID").equals(tableID)) {
				tableElement = workElement;
				break;
			}
		}
		if (tableElement != null) {
			if (frame_.useTableNameAsTableNameInCreateTableStatement) {
				tableName = tableElement.getAttribute("Name");
			} else {
				if (tableElement.getAttribute("Alias").equals("")) {
					if (tableElement.getAttribute("SortKey").equals("")) {
						tableName = tableElement.getAttribute("Name");
					} else {
						tableName = tableElement.getAttribute("SortKey");
					}
				} else {
					tableName = tableElement.getAttribute("Alias");
				}
			}
			NodeList tableKeyElementList = tableElement.getElementsByTagName("TableKey");
			for (int i = 0; i < tableKeyElementList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)tableKeyElementList.item(i);
				if (workElement.getAttribute("ID").equals(tableKeyID)) {
					tableKeyElement = workElement;
					break;
				}
			}
			if (tableKeyElement != null) {
				fieldNames = getKeyFieldIDs(tableElement, tableKeyElement, systemElement);
			}
			value = tableName + "(" + fieldNames + ")";
		}

		return value;
	}

	boolean isLackingForeignKeyConstraint(org.w3c.dom.Element relationshipElement, org.w3c.dom.Element tableElement, org.w3c.dom.Element systemElement, org.w3c.dom.Element targetTableElement, org.w3c.dom.Element targetSystemElement) {
		boolean reply = true;
		String wrkStr;
		org.w3c.dom.Element workElement, keyFieldElement, fieldElement;

		String tableIDFrom = relationshipElement.getAttribute("Table2ID");
		String tableKeyIDFrom = relationshipElement.getAttribute("TableKey2ID");
		int countOfTableKeyFieldsFrom = 0;
		String tableIDThru = relationshipElement.getAttribute("Table1ID");
		String tableKeyIDThru = relationshipElement.getAttribute("TableKey1ID");
		int countOfTableKeyFieldsThru = 0;

		String tableKeyTypeFrom = "";
		String tableKeyFieldsFrom = "";
		String tableNameThru = "";
		String tableKeyTypeThru = "";
		String tableKeyFieldsThru = "";

		String tableIDFromTarget = targetTableElement.getAttribute("ID");
		String tableKeyIDFromTarget = "";
		String tableIDThruTarget = "";
		String tableKeyIDThruTarget = "";

		/////////////////////////////////////////////////
		// Get tableKeyTypeFrom and tableKeyFieldsFrom //
		/////////////////////////////////////////////////
		NodeList keyElementList = tableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < keyElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)keyElementList.item(i);
			if (workElement.getAttribute("ID").equals(tableKeyIDFrom)) {
				tableKeyTypeFrom = workElement.getAttribute("Type");
				NodeList keyFieldList = workElement.getElementsByTagName("TableKeyField");
				frame_.sortableDomElementListModel.removeAllElements();
				for (int m = 0; m < keyFieldList.getLength(); m++) {
					frame_.sortableDomElementListModel.addElement((Object)keyFieldList.item(m));
				}
				if (frame_.sortableDomElementListModel.getSize() > 0) {
					frame_.sortableDomElementListModel.sortElements();
					for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
						countOfTableKeyFieldsFrom++;
						keyFieldElement = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(m);
						fieldElement = getFieldElementAccordingToID(tableIDFrom, keyFieldElement.getAttribute("FieldID"), systemElement);
						if (fieldElement == null) {
							tableKeyFieldsFrom = "";
							break;
						} else {
							if (m > 0) {
								tableKeyFieldsFrom = tableKeyFieldsFrom + ", ";
							}
							if (fieldElement.getAttribute("Alias").equals("")) {
								tableKeyFieldsFrom = tableKeyFieldsFrom + fieldElement.getAttribute("Name");
							} else {
								tableKeyFieldsFrom = tableKeyFieldsFrom + fieldElement.getAttribute("Alias");
							}
						}
					}
				}
				break;
			}
		}
		if (tableKeyFieldsFrom.equals("")) {
			return false;
		}

		////////////////////////////////////////////////////////////////
		// Get tableNameThru, tableKeyTypeThru and tableKeyFieldsThru //
		////////////////////////////////////////////////////////////////
		NodeList tableElementList = systemElement.getElementsByTagName("Table");
		for (int i = 0; i < tableElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)tableElementList.item(i);
			if (workElement.getAttribute("ID").equals(tableIDThru)) {
				if (frame_.useTableNameAsTableNameInCreateTableStatement) {
					tableNameThru = workElement.getAttribute("Name");
				} else {
					if (workElement.getAttribute("Alias").equals("")) {
						if (workElement.getAttribute("SortKey").equals("")) {
							tableNameThru = workElement.getAttribute("Name");
						} else {
							tableNameThru = workElement.getAttribute("SortKey");
						}
					} else {
						tableNameThru = workElement.getAttribute("Alias");
					}
				}
				keyElementList = workElement.getElementsByTagName("TableKey");
				for (int j = 0; j < keyElementList.getLength(); j++) {
					workElement = (org.w3c.dom.Element)keyElementList.item(j);
					if (workElement.getAttribute("ID").equals(tableKeyIDThru)) {
						tableKeyTypeThru = workElement.getAttribute("Type");
						NodeList keyFieldList = workElement.getElementsByTagName("TableKeyField");
						frame_.sortableDomElementListModel.removeAllElements();
						for (int m = 0; m < keyFieldList.getLength(); m++) {
							frame_.sortableDomElementListModel.addElement((Object)keyFieldList.item(m));
						}
						if (frame_.sortableDomElementListModel.getSize() > 0) {
							frame_.sortableDomElementListModel.sortElements();
							for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
								countOfTableKeyFieldsThru++;
								keyFieldElement = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(m);
								fieldElement = getFieldElementAccordingToID(tableIDThru, keyFieldElement.getAttribute("FieldID"), systemElement);
								if (fieldElement == null) {
									tableKeyFieldsThru = "";
									break;
								} else {
									if (m > 0) {
										tableKeyFieldsThru = tableKeyFieldsThru + ", ";
									}
									if (fieldElement.getAttribute("Alias").equals("")) {
										tableKeyFieldsThru = tableKeyFieldsThru + fieldElement.getAttribute("Name");
									} else {
										tableKeyFieldsThru = tableKeyFieldsThru + fieldElement.getAttribute("Alias");
									}
								}
							}
						}
						break;
					}
				}
				break;
			}
		}
		if (tableNameThru.equals("") || tableKeyFieldsThru.equals("")) {
			return false;
		}
		if (countOfTableKeyFieldsFrom > countOfTableKeyFieldsThru) {
			return false;
		}

		//////////////////////////////
		// Get tableKeyIDFromTarget //
		//////////////////////////////
		keyElementList = targetTableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < keyElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)keyElementList.item(i);
			if (workElement.getAttribute("Type").equals(tableKeyTypeFrom)) {
				NodeList keyFieldList = workElement.getElementsByTagName("TableKeyField");
				frame_.sortableDomElementListModel.removeAllElements();
				for (int m = 0; m < keyFieldList.getLength(); m++) {
					frame_.sortableDomElementListModel.addElement((Object)keyFieldList.item(m));
				}
				if (frame_.sortableDomElementListModel.getSize() > 0) {
					frame_.sortableDomElementListModel.sortElements();
					wrkStr = "";
					for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
						keyFieldElement = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(m);
						fieldElement = getFieldElementAccordingToID(targetTableElement.getAttribute("ID"), keyFieldElement.getAttribute("FieldID"), targetSystemElement);
						if (fieldElement == null) {
							wrkStr = "";
							break;
						} else {
							if (m > 0) {
								wrkStr = wrkStr + ", ";
							}
							if (fieldElement.getAttribute("Alias").equals("")) {
								wrkStr = wrkStr + fieldElement.getAttribute("Name");
							} else {
								wrkStr = wrkStr + fieldElement.getAttribute("Alias");
							}
						}
					}
					if (wrkStr.equals(tableKeyFieldsFrom)) {
						tableKeyIDFromTarget = workElement.getAttribute("ID");
						break;
					}
				}
			}
		}

		////////////////////////////////////////////////////
		// Get tableIDThruTarget and tableKeyIDThruTarget //
		////////////////////////////////////////////////////
		tableElementList = targetSystemElement.getElementsByTagName("Table");
		for (int i = 0; i < tableElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)tableElementList.item(i);
			if (frame_.useTableNameAsTableNameInCreateTableStatement) {
				wrkStr = workElement.getAttribute("Name");
			} else {
				if (workElement.getAttribute("Alias").equals("")) {
					if (workElement.getAttribute("SortKey").equals("")) {
						wrkStr = workElement.getAttribute("Name");
					} else {
						wrkStr = workElement.getAttribute("SortKey");
					}
				} else {
					wrkStr = workElement.getAttribute("Alias");
				}
			}
			if (wrkStr.equals(tableNameThru)) {
				tableIDThruTarget = workElement.getAttribute("ID");
				keyElementList = workElement.getElementsByTagName("TableKey");
				for (int j = 0; j < keyElementList.getLength(); j++) {
					workElement = (org.w3c.dom.Element)keyElementList.item(j);
					if (workElement.getAttribute("Type").equals(tableKeyTypeThru)) {
						NodeList keyFieldList = workElement.getElementsByTagName("TableKeyField");
						frame_.sortableDomElementListModel.removeAllElements();
						for (int m = 0; m < keyFieldList.getLength(); m++) {
							frame_.sortableDomElementListModel.addElement((Object)keyFieldList.item(m));
						}
						if (frame_.sortableDomElementListModel.getSize() > 0) {
							frame_.sortableDomElementListModel.sortElements();
							wrkStr = "";
							for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
								keyFieldElement = (org.w3c.dom.Element)frame_.sortableDomElementListModel.getElementAt(m);
								fieldElement = getFieldElementAccordingToID(tableIDThruTarget, keyFieldElement.getAttribute("FieldID"), targetSystemElement);
								if (fieldElement == null) {
									wrkStr = "";
									break;
								} else {
									if (m > 0) {
										wrkStr = wrkStr + ", ";
									}
									if (fieldElement.getAttribute("Alias").equals("")) {
										wrkStr = wrkStr + fieldElement.getAttribute("Name");
									} else {
										wrkStr = wrkStr + fieldElement.getAttribute("Alias");
									}
								}
							}
							if (wrkStr.equals(tableKeyFieldsThru)) {
								tableKeyIDThruTarget = workElement.getAttribute("ID");
								break;
							}
						}
					}
				}
				break;
			}
		}

		NodeList relationshipList = targetSystemElement.getElementsByTagName("Relationship");
		for (int i = 0; i < relationshipList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)relationshipList.item(i);
			if (workElement.getAttribute("Table2ID").equals(tableIDFromTarget)
					&& workElement.getAttribute("TableKey2ID").equals(tableKeyIDFromTarget)
					&& workElement.getAttribute("Table1ID").equals(tableIDThruTarget)
					&& workElement.getAttribute("TableKey1ID").equals(tableKeyIDThruTarget)
					&& workElement.getAttribute("ExistWhen1").equals("")) {
				reply = false;
				break;
			}
		}

		return reply;
	}

	void listChangesOfFunction() {
		NodeList newElementList, oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("DialogToListChangesOfFiles14") + ">");

		///////////////
		// Functions //
		///////////////
		tagName = "Function";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("Summary");
		attrList.add("Parameters");
		attrList.add("Return");
		attrList.add("FunctionTypeID");
		attrList.add("SubsystemID");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(newElementList, oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(newElementList, oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void compareNewAndOldElements(org.w3c.dom.Element elementNew, org.w3c.dom.Element elementOld, String attribute, String elementLabel) {
		String sortKeyOld, sortKeyNew, notNullOld, notNullNew;
		if (attribute.endsWith("ID")) {
			sortKeyOld = getSortKeyAccordingToID(attribute, elementOld.getAttribute(attribute), systemElementOld);
			sortKeyNew = getSortKeyAccordingToID(attribute, elementNew.getAttribute(attribute), systemElementNew);
			if (!sortKeyNew.equals(sortKeyOld)) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
							+ elementLabel + res.getString("DialogToListChangesOfFiles20")
							+ attribute.replace("ID", "") + res.getString("DialogToListChangesOfFiles21")
							+ sortKeyOld + res.getString("DialogToListChangesOfFiles22")
							+ sortKeyNew + res.getString("DialogToListChangesOfFiles23"));
			}
		} else {
			if (attribute.equals("NoUpdate")) {
				notNullNew = elementNew.getAttribute(attribute);
				notNullOld = elementOld.getAttribute(attribute);
				if ((notNullNew.equals("") && notNullOld.equals("true"))
					|| (notNullNew.equals("false") && notNullOld.equals("true"))
					|| (notNullNew.equals("true") && notNullOld.equals(""))
					|| (notNullNew.equals("true") && notNullOld.equals("false"))) {
					buffer.append("\n" + countOfChanges + "."
							+ elementLabel + res.getString("DialogToListChangesOfFiles20")
							+ attribute + res.getString("DialogToListChangesOfFiles21")
							+ elementOld.getAttribute(attribute) + res.getString("DialogToListChangesOfFiles22")
							+ elementNew.getAttribute(attribute) + res.getString("DialogToListChangesOfFiles23"));
				}
			} else {
				if (!elementNew.getAttribute(attribute).equals(elementOld.getAttribute(attribute))) {
					countOfChanges++;
					if (attribute.equals("ImageText")) {
						buffer.append("\n" + countOfChanges + "."
								+ elementLabel + res.getString("DialogToListChangesOfFiles24"));
					} else {
						if (attribute.equals("Descriptions")) {
							buffer.append("\n" + countOfChanges + "."
									+ elementLabel + res.getString("DialogToListChangesOfFiles25"));
						} else {
							if (attribute.equals("DatamodelDescriptions")) {
								buffer.append("\n" + countOfChanges + "."
										+ elementLabel + res.getString("DialogToListChangesOfFiles26"));
							} else {
								if (attribute.equals("Name")) {
									buffer.append("\n" + countOfChanges + "."
											+ elementLabel + res.getString("DialogToListChangesOfFiles27")
											+ elementOld.getAttribute(attribute) + res.getString("DialogToListChangesOfFiles22")
											+ elementNew.getAttribute(attribute) + res.getString("DialogToListChangesOfFiles23"));
								} else {
									buffer.append("\n" + countOfChanges + "."
											+ elementLabel + res.getString("DialogToListChangesOfFiles20")
											+ attribute + res.getString("DialogToListChangesOfFiles21")
											+ elementOld.getAttribute(attribute) + res.getString("DialogToListChangesOfFiles22")
											+ elementNew.getAttribute(attribute) + res.getString("DialogToListChangesOfFiles23"));
								}
							}
						}
					}
				}
			}
		}
	}

	private String getSortKeyAccordingToID(String attribute, String id, org.w3c.dom.Element systemElement) {
		String value = "(N/A)";
		String tagName = "";
		org.w3c.dom.Element element;

		if (attribute.equals("DepartmentID")) {
			tagName = "Department";
		}
		if (attribute.equals("TaskTypeID")) {
			tagName = "TaskType";
		}
		if (attribute.equals("RoleID")) {
			tagName = "Role";
		}
		if (attribute.equals("SubsystemID")) {
			tagName = "Subsystem";
		}
		if (attribute.equals("TableTypeID")) {
			tagName = "TableType";
		}
		if (attribute.equals("DataTypeID")) {
			tagName = "DataType";
		}
		if (attribute.equals("FunctionTypeID")) {
			tagName = "FunctionType";
		}
		if (attribute.equals("TableID")) {
			tagName = "Table";
		}
		if (attribute.equals("FunctionID")) {
			tagName = "Function";
		}

		NodeList elementList = systemElement.getElementsByTagName(tagName);
		for (int i = 0; i < elementList.getLength(); i++) {
			element = (org.w3c.dom.Element)elementList.item(i);
			if (element.getAttribute("ID").equals(id)) {
				value = element.getAttribute("SortKey");
				break;
			}
		}
		return value;
	}

	void checkIfNewElementChangedOrAdded(NodeList newElementList, NodeList oldElementList, ArrayList<String> attrList, String tagName, String nameAttr) {
		org.w3c.dom.Element newElement, oldElement;
		boolean isNotFound;
		String elementLabel;
		String elementName = getElementNameAccordingToTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
			newElement = (org.w3c.dom.Element)newElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElement = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElement.getAttribute("SortKey").equals(newElement.getAttribute("SortKey"))) {
					for (int k = 0; k < attrList.size(); k++) {
						elementLabel = elementName + res.getString("DialogToListChangesOfFiles28")
										+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute(nameAttr)
										+ res.getString("DialogToListChangesOfFiles29");
						compareNewAndOldElements(newElement, oldElement, attrList.get(k), elementLabel);
					}
					isNotFound = false;
					if (tagName.equals("SubjectArea")) {
						checkSubjectAreaDetails(oldElement, newElement, elementName);
					}
					if (tagName.equals("Task")) {
						checkTaskDetails(oldElement, newElement, elementName);
					}
					if (tagName.equals("Subsystem")) {
						checkSubsystemDetails(oldElement, newElement, elementName);
					}
					if (tagName.equals("Table")) {
						checkTableDetails(oldElement, newElement);
					}
					if (tagName.equals("Function")) {
						checkFunctionDetails(oldElement, newElement);
					}
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute(nameAttr)
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles30"));
			}
		}
	}

	void checkIfNewElementDeleted(NodeList newElementList, NodeList oldElementList, String tagName, String nameAttr) {
		org.w3c.dom.Element newElement, oldElement;
		boolean isNotFound;
		String elementName = getElementNameAccordingToTagName(tagName);
		for (int i = 0; i < oldElementList.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
			oldElement = (org.w3c.dom.Element)oldElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElement = (org.w3c.dom.Element)newElementList.item(j);
				if (oldElement.getAttribute("SortKey").equals(newElement.getAttribute("SortKey"))) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ oldElement.getAttribute("SortKey") + " " + oldElement.getAttribute(nameAttr)
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}
	}

	void checkSubjectAreaDetails(org.w3c.dom.Element oldElement, org.w3c.dom.Element newElement, String elementName) {
		if (!oldElement.getAttribute("BoundaryPosition").equals(newElement.getAttribute("BoundaryPosition"))
				|| !oldElement.getAttribute("BoundarySize").equals(newElement.getAttribute("BoundarySize"))
				|| !oldElement.getAttribute("BoundarySetting").equals(newElement.getAttribute("BoundarySetting"))) {
			countOfChanges++;
			buffer.append("\n" + countOfChanges + "."
					+ elementName + res.getString("DialogToListChangesOfFiles28")
					+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
					+ res.getString("DialogToListChangesOfFiles29")
					+ res.getString("DialogToListChangesOfFiles24"));
		} else {
			NodeList oldNodeList = oldElement.getElementsByTagName("DataflowNode");
			NodeList newNodeList = newElement.getElementsByTagName("DataflowNode");
			NodeList oldLineList = oldElement.getElementsByTagName("DataflowLine");
			NodeList newLineList = newElement.getElementsByTagName("DataflowLine");
			if (oldNodeList.getLength() != newNodeList.getLength()
					|| oldLineList.getLength() != newLineList.getLength()) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles24"));
			} else {
				if (!oldElement.isEqualNode(newElement)) {
					countOfChanges++;
					buffer.append("\n" + countOfChanges + "."
							+ elementName + res.getString("DialogToListChangesOfFiles28")
							+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
							+ res.getString("DialogToListChangesOfFiles29")
							+ res.getString("DialogToListChangesOfFiles24"));
				}
			}
		}
	}

	void checkTaskDetails(org.w3c.dom.Element oldElement, org.w3c.dom.Element newElement, String elementName) {
		NodeList oldActionList = oldElement.getElementsByTagName("TaskAction");
		NodeList newActionList = newElement.getElementsByTagName("TaskAction");
		if (oldActionList.getLength() != newActionList.getLength()) {
			countOfChanges++;
			buffer.append("\n" + countOfChanges + "."
					+ elementName + res.getString("DialogToListChangesOfFiles28")
					+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
					+ res.getString("DialogToListChangesOfFiles29")
					+ res.getString("DialogToListChangesOfFiles32"));
		} else {
			if (!oldElement.isEqualNode(newElement)) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles32"));
			}
		}
	}

	void checkSubsystemDetails(org.w3c.dom.Element oldElement, org.w3c.dom.Element newElement, String elementName) {
		NodeList oldTableBoxList = oldElement.getElementsByTagName("SubsystemTable");
		NodeList newTableBoxList = newElement.getElementsByTagName("SubsystemTable");
		NodeList oldTableRelList = oldElement.getElementsByTagName("SubsystemRelationship");
		NodeList newTableRelList = newElement.getElementsByTagName("SubsystemRelationship");
		if (oldTableBoxList.getLength() != newTableBoxList.getLength()
				|| oldTableRelList.getLength() != newTableRelList.getLength()) {
			countOfChanges++;
			buffer.append("\n" + countOfChanges + "."
					+ elementName + res.getString("DialogToListChangesOfFiles28")
					+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
					+ res.getString("DialogToListChangesOfFiles29")
					+ res.getString("DialogToListChangesOfFiles33"));
		} else {
			if (!oldElement.isEqualNode(newElement)) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles33"));
			}
		}
	}

	void checkTableDetails(org.w3c.dom.Element oldElement, org.w3c.dom.Element newElement) {
		NodeList oldElementList, newElementList;
		ArrayList<org.w3c.dom.Element> newRelList = new ArrayList<org.w3c.dom.Element>();
		ArrayList<org.w3c.dom.Element> oldRelList = new ArrayList<org.w3c.dom.Element>();
		ArrayList<String> attrList = new ArrayList<String>();
		org.w3c.dom.Element newElementWork, oldElementWork;
		boolean isNotFound;
		String tagName, elementName, elementLabel, fieldID, fieldIDOld;
		String tableInternalID = newElement.getAttribute("ID");
		String tableLabel = res.getString("DialogToListChangesOfFiles10")
					+ res.getString("DialogToListChangesOfFiles28")
					+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
					+ res.getString("DialogToListChangesOfFiles29");

		////////////
		// Fields //
		////////////
		tagName = "TableField";
		elementName = getElementNameAccordingToTagName(tagName);
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("AttributeType");
		attrList.add("DataTypeID");
		attrList.add("ShowOnModel");
		attrList.add("NotNull");
		attrList.add("NoUpdate");
		attrList.add("Default");
		attrList.add("SortKey");
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			if (newElementWork.getAttribute("Alias").equals("")) {
				fieldID = newElementWork.getAttribute("Name");
			} else {
				fieldID = newElementWork.getAttribute("Alias");
			}
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElementWork.getAttribute("Alias").equals("")) {
					fieldIDOld = oldElementWork.getAttribute("Name");
				} else {
					fieldIDOld = oldElementWork.getAttribute("Alias");
				}
				if (fieldIDOld.equals(fieldID)) {
					elementLabel = tableLabel+ res.getString("DialogToListChangesOfFiles20")
							+ elementName + res.getString("DialogToListChangesOfFiles28")
							+ newElementWork.getAttribute("Alias")
							+ " " + newElementWork.getAttribute("Name")
							+ res.getString("DialogToListChangesOfFiles29");
					for (int k = 0; k < attrList.size(); k++) {
						compareNewAndOldElements(newElementWork, oldElementWork, attrList.get(k), elementLabel);
					}
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ tableLabel+ res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElementWork.getAttribute("Alias") + " " + newElementWork.getAttribute("Name")
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles30"));
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			if (oldElementWork.getAttribute("Alias").equals("")) {
				fieldIDOld = oldElementWork.getAttribute("Name");
			} else {
				fieldIDOld = oldElementWork.getAttribute("Alias");
			}
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				if (newElementWork.getAttribute("Alias").equals("")) {
					fieldID = newElementWork.getAttribute("Name");
				} else {
					fieldID = newElementWork.getAttribute("Alias");
				}
				if (fieldID.equals(fieldIDOld)) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ tableLabel+ res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ oldElementWork.getAttribute("Alias") + " " + oldElementWork.getAttribute("Name")
						+ res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}

		//////////
		// Keys //
		//////////
		tagName = "TableKey";
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElementWork.getAttribute("Type").equals(newElementWork.getAttribute("Type"))) {
					if (oldElementWork.isEqualNode(newElementWork)) {
						isNotFound = false;
						break;
					}
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ tableLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + "(" + newElementWork.getAttribute("Type") + ")"
						+ res.getString("DialogToListChangesOfFiles34"));
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				if (oldElementWork.getAttribute("Type").equals(newElementWork.getAttribute("Type"))) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ tableLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + "(" + oldElementWork.getAttribute("Type") + ")"
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}

		///////////////////
		// Relationships //
		///////////////////
		tagName = "Relationship";
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		newElementList = systemElementNew.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			if (newElementWork.getAttribute("Table1ID").equals(tableInternalID)
					|| newElementWork.getAttribute("Table2ID").equals(tableInternalID)) {
				newRelList.add(newElementWork);
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			if (oldElementWork.getAttribute("Table1ID").equals(tableInternalID)
					|| oldElementWork.getAttribute("Table2ID").equals(tableInternalID)) {
				oldRelList.add(oldElementWork);
			}
		}
		if (newRelList.size() != oldRelList.size()) {
			countOfChanges++;
			buffer.append("\n" + countOfChanges + "."
					+ tableLabel + res.getString("DialogToListChangesOfFiles20")
					+ elementName + res.getString("DialogToListChangesOfFiles34"));
		} else {
			isNotFound = true;
			for (int i = 0; i < newRelList.size(); i++) {
				newElementWork = newRelList.get(i);
				for (int j = 0; j < oldRelList.size(); j++) {
					oldElementWork = oldRelList.get(i);
					if (newElementWork.isEqualNode(oldElementWork)) {
						isNotFound = false;
						break;
					}
				}
				if (isNotFound) {
					countOfChanges++;
					buffer.append("\n" + countOfChanges + "."
							+ tableLabel + res.getString("DialogToListChangesOfFiles20")
							+ elementName + res.getString("DialogToListChangesOfFiles34"));
					break;
				}
			}
		}
	}

	void checkFunctionDetails(org.w3c.dom.Element oldElement, org.w3c.dom.Element newElement) {
		NodeList oldElementList, newElementList, oldElementList2, newElementList2;
		String tagName, elementName, wrkStr1, wrkStr2, tableID1, tableID2, fieldName1, fieldName2, tableCRUD1, tableCRUD2, elementLabel;
		org.w3c.dom.Element newElementWork, oldElementWork, newElementWork2, oldElementWork2, workElement;
		ArrayList<String> attrList = new ArrayList<String>();
		String functionLabel = res.getString("DialogToListChangesOfFiles14")
				+ res.getString("DialogToListChangesOfFiles28")
				+ newElement.getAttribute("SortKey") + " " + newElement.getAttribute("Name")
				+ res.getString("DialogToListChangesOfFiles29");
		boolean isNotFound, isNotFound2, isAnyChanged;

		////////////////////////////
		// Functions Used By This //
		////////////////////////////
		tagName = "FunctionUsedByThis";
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElementWork.getAttribute("LaunchEvent").equals(newElementWork.getAttribute("LaunchEvent"))
						&& oldElementWork.getAttribute("SortKey").equals(newElementWork.getAttribute("SortKey"))) {
					wrkStr1 = getSortKeyAccordingToID("FunctionID", oldElementWork.getAttribute("FunctionID"), systemElementOld);
					wrkStr2 = getSortKeyAccordingToID("FunctionID", newElementWork.getAttribute("FunctionID"), systemElementNew);
					if (wrkStr1.equals(wrkStr2)) {
						isNotFound = false;
						break;
					}
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles34"));
				break;
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				if (oldElementWork.getAttribute("LaunchEvent").equals(newElementWork.getAttribute("LaunchEvent"))
						&& oldElementWork.getAttribute("SortKey").equals(newElementWork.getAttribute("SortKey"))) {
					wrkStr1 = getSortKeyAccordingToID("FunctionID", oldElementWork.getAttribute("FunctionID"), systemElementOld);
					wrkStr2 = getSortKeyAccordingToID("FunctionID", newElementWork.getAttribute("FunctionID"), systemElementNew);
					if (wrkStr1.equals(wrkStr2)) {
						isNotFound = false;
						break;
					}
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles34"));
			}
		}

		///////////////
		// IO Panels //
		///////////////
		tagName = "IOPanel";
		attrList.clear();
		attrList.add("Descriptions");
		attrList.add("ImageText");
		attrList.add("Background");
		attrList.add("Size");
		attrList.add("FontSize");
		attrList.add("SortKey");
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElementWork.getAttribute("Name").equals(newElementWork.getAttribute("Name"))) {

					elementLabel = functionLabel + res.getString("DialogToListChangesOfFiles20")
							+ elementName + res.getString("DialogToListChangesOfFiles28")
							+ newElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29");
					for (int k = 0; k < attrList.size(); k++) {
						compareNewAndOldElements(newElementWork, oldElementWork, attrList.get(k), elementLabel);
					}
	
					/////////////////////
					// IO Panel Styles //
					/////////////////////
					isAnyChanged = false;
					oldElementList2 = oldElementWork.getElementsByTagName("IOPanelStyle");
					newElementList2 = newElementWork.getElementsByTagName("IOPanelStyle");
					for (int m = 0; m < newElementList2.getLength(); m++) {
						newElementWork2 = (org.w3c.dom.Element)newElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < oldElementList2.getLength(); p++) {
							oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(p);
							if (oldElementWork2.getAttribute("Value").equals(newElementWork2.getAttribute("Value"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					for (int m = 0; m < oldElementList2.getLength(); m++) {
						oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < newElementList2.getLength(); p++) {
							newElementWork2 = (org.w3c.dom.Element)newElementList2.item(p);
							if (oldElementWork2.getAttribute("Value").equals(newElementWork2.getAttribute("Value"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					if (isAnyChanged) {
						countOfChanges++;
						buffer.append("\n" + countOfChanges + "."
								+ elementLabel + res.getString("DialogToListChangesOfFiles20")
								+ "Style" + res.getString("DialogToListChangesOfFiles34"));
					}

					/////////////////////
					// IO Panel Fields //
					/////////////////////
					isAnyChanged = false;
					oldElementList2 = oldElementWork.getElementsByTagName("IOPanelField");
					newElementList2 = newElementWork.getElementsByTagName("IOPanelField");
					for (int m = 0; m < newElementList2.getLength(); m++) {
						newElementWork2 = (org.w3c.dom.Element)newElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < oldElementList2.getLength(); p++) {
							oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(p);
							if (oldElementWork2.getAttribute("Name").equals(newElementWork2.getAttribute("Name"))
									&& oldElementWork2.getAttribute("Label").equals(newElementWork2.getAttribute("Label"))
									&& oldElementWork2.getAttribute("IOType").equals(newElementWork2.getAttribute("IOType"))
									&& oldElementWork2.getAttribute("Descriptions").equals(newElementWork2.getAttribute("Descriptions"))
									&& oldElementWork2.getAttribute("SortKey").equals(newElementWork2.getAttribute("SortKey"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					for (int m = 0; m < oldElementList2.getLength(); m++) {
						oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < newElementList2.getLength(); p++) {
							newElementWork2 = (org.w3c.dom.Element)newElementList2.item(p);
							if (oldElementWork2.getAttribute("Name").equals(newElementWork2.getAttribute("Name"))
									&& oldElementWork2.getAttribute("Label").equals(newElementWork2.getAttribute("Label"))
									&& oldElementWork2.getAttribute("IOType").equals(newElementWork2.getAttribute("IOType"))
									&& oldElementWork2.getAttribute("Descriptions").equals(newElementWork2.getAttribute("Descriptions"))
									&& oldElementWork2.getAttribute("SortKey").equals(newElementWork2.getAttribute("SortKey"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					if (isAnyChanged) {
						countOfChanges++;
						buffer.append("\n" + countOfChanges + "."
								+ elementLabel + res.getString("DialogToListChangesOfFiles20")
								+ "Field" + res.getString("DialogToListChangesOfFiles34"));
					}

					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles30"));
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				if (oldElementWork.getAttribute("Name").equals(newElementWork.getAttribute("Name"))) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ oldElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}

		///////////////
		// IO Spools //
		///////////////
		tagName = "IOSpool";
		attrList.clear();
		attrList.add("Descriptions");
		attrList.add("ImageText");
		attrList.add("Background");
		attrList.add("Size");
		attrList.add("FontSize");
		attrList.add("SortKey");
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElementWork.getAttribute("Name").equals(newElementWork.getAttribute("Name"))) {

					elementLabel = functionLabel + res.getString("DialogToListChangesOfFiles20")
							+ elementName + res.getString("DialogToListChangesOfFiles28")
							+ newElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29");
					for (int k = 0; k < attrList.size(); k++) {
						compareNewAndOldElements(newElementWork, oldElementWork, attrList.get(k), elementLabel);
					}
					
					/////////////////////
					// IO Spool Styles //
					/////////////////////
					isAnyChanged = false;
					oldElementList2 = oldElementWork.getElementsByTagName("IOSpoolStyle");
					newElementList2 = newElementWork.getElementsByTagName("IOSpoolStyle");
					for (int m = 0; m < newElementList2.getLength(); m++) {
						newElementWork2 = (org.w3c.dom.Element)newElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < oldElementList2.getLength(); p++) {
							oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(p);
							if (oldElementWork2.getAttribute("Value").equals(newElementWork2.getAttribute("Value"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					for (int m = 0; m < oldElementList2.getLength(); m++) {
						oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < newElementList2.getLength(); p++) {
							newElementWork2 = (org.w3c.dom.Element)newElementList2.item(p);
							if (oldElementWork2.getAttribute("Value").equals(newElementWork2.getAttribute("Value"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					if (isAnyChanged) {
						countOfChanges++;
						buffer.append("\n" + countOfChanges + "."
								+ elementLabel + res.getString("DialogToListChangesOfFiles20")
								+ "Style" + res.getString("DialogToListChangesOfFiles34"));
					}
					
					/////////////////////
					// IO Spool Fields //
					/////////////////////
					isAnyChanged = false;
					oldElementList2 = oldElementWork.getElementsByTagName("IOSpoolField");
					newElementList2 = newElementWork.getElementsByTagName("IOSpoolField");
					for (int m = 0; m < newElementList2.getLength(); m++) {
						newElementWork2 = (org.w3c.dom.Element)newElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < oldElementList2.getLength(); p++) {
							oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(p);
							if (oldElementWork2.getAttribute("Name").equals(newElementWork2.getAttribute("Name"))
									&& oldElementWork2.getAttribute("Label").equals(newElementWork2.getAttribute("Label"))
									&& oldElementWork2.getAttribute("Descriptions").equals(newElementWork2.getAttribute("Descriptions"))
									&& oldElementWork2.getAttribute("SortKey").equals(newElementWork2.getAttribute("SortKey"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					for (int m = 0; m < oldElementList2.getLength(); m++) {
						oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < newElementList2.getLength(); p++) {
							newElementWork2 = (org.w3c.dom.Element)newElementList2.item(p);
							if (oldElementWork2.getAttribute("Name").equals(newElementWork2.getAttribute("Name"))
									&& oldElementWork2.getAttribute("Label").equals(newElementWork2.getAttribute("Label"))
									&& oldElementWork2.getAttribute("Descriptions").equals(newElementWork2.getAttribute("Descriptions"))
									&& oldElementWork2.getAttribute("SortKey").equals(newElementWork2.getAttribute("SortKey"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					if (isAnyChanged) {
						countOfChanges++;
						buffer.append("\n" + countOfChanges + "."
								+ elementLabel + res.getString("DialogToListChangesOfFiles20")
								+ "Field" + res.getString("DialogToListChangesOfFiles34"));
					}

					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles30"));
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				if (oldElementWork.getAttribute("Name").equals(newElementWork.getAttribute("Name"))) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ oldElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}

		//////////////////
		// IO Web Pages //
		//////////////////
		tagName = "IOWebPage";
		attrList.clear();
		attrList.add("Descriptions");
		attrList.add("FileName");
		attrList.add("SortKey");
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElementWork.getAttribute("Name").equals(newElementWork.getAttribute("Name"))) {

					elementLabel = functionLabel + res.getString("DialogToListChangesOfFiles20")
							+ elementName + res.getString("DialogToListChangesOfFiles28")
							+ newElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29");
					for (int k = 0; k < attrList.size(); k++) {
						compareNewAndOldElements(newElementWork, oldElementWork, attrList.get(k), elementLabel);
					}
					
					////////////////////////
					// IO Web Page Fields //
					////////////////////////
					isAnyChanged = false;
					oldElementList2 = oldElementWork.getElementsByTagName("IOWebPageField");
					newElementList2 = newElementWork.getElementsByTagName("IOWebPageField");
					for (int m = 0; m < newElementList2.getLength(); m++) {
						newElementWork2 = (org.w3c.dom.Element)newElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < oldElementList2.getLength(); p++) {
							oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(p);
							if (oldElementWork2.getAttribute("Name").equals(newElementWork2.getAttribute("Name"))
									&& oldElementWork2.getAttribute("Label").equals(newElementWork2.getAttribute("Label"))
									&& oldElementWork2.getAttribute("IOType").equals(newElementWork2.getAttribute("IOType"))
									&& oldElementWork2.getAttribute("Descriptions").equals(newElementWork2.getAttribute("Descriptions"))
									&& oldElementWork2.getAttribute("SortKey").equals(newElementWork2.getAttribute("SortKey"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					for (int m = 0; m < oldElementList2.getLength(); m++) {
						oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(m);
						isNotFound2 = true;
						for (int p = 0; p < newElementList2.getLength(); p++) {
							newElementWork2 = (org.w3c.dom.Element)newElementList2.item(p);
							if (oldElementWork2.getAttribute("Name").equals(newElementWork2.getAttribute("Name"))
									&& oldElementWork2.getAttribute("Label").equals(newElementWork2.getAttribute("Label"))
									&& oldElementWork2.getAttribute("IOType").equals(newElementWork2.getAttribute("IOType"))
									&& oldElementWork2.getAttribute("Descriptions").equals(newElementWork2.getAttribute("Descriptions"))
									&& oldElementWork2.getAttribute("SortKey").equals(newElementWork2.getAttribute("SortKey"))) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							isAnyChanged = true;
							break;
						}
					}
					if (isAnyChanged) {
						countOfChanges++;
						buffer.append("\n" + countOfChanges + "."
								+ elementLabel + res.getString("DialogToListChangesOfFiles20")
								+ "Field" + res.getString("DialogToListChangesOfFiles34"));
					}

					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ newElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles30"));
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				if (oldElementWork.getAttribute("Name").equals(newElementWork.getAttribute("Name"))) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ oldElementWork.getAttribute("Name") + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}

		///////////////
		// IO Tables //
		///////////////
		tagName = "IOTable";
		attrList.clear();
		attrList.add("Descriptions");
		attrList.add("NameExtension");
		attrList.add("SortKey");
		attrList.add("Position");
		elementName = getElementNameAccordingToTagName(tagName);
		oldElementList = oldElement.getElementsByTagName(tagName);
		newElementList = newElement.getElementsByTagName(tagName);
		for (int i = 0; i < newElementList.getLength(); i++) {
			newElementWork = (org.w3c.dom.Element)newElementList.item(i);
			tableID1 = getSortKeyAccordingToID("TableID", newElementWork.getAttribute("TableID"), systemElementNew);
			tableCRUD1 = getTableCRUD(newElementWork);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				oldElementWork = (org.w3c.dom.Element)oldElementList.item(j);
				tableID2 = getSortKeyAccordingToID("TableID", oldElementWork.getAttribute("TableID"), systemElementOld);
				tableCRUD2 = getTableCRUD(oldElementWork);
				if (tableID1.equals(tableID2) && tableCRUD1.equals(tableCRUD2)) {

					elementLabel = functionLabel + res.getString("DialogToListChangesOfFiles20")
							+ elementName + " " + tableID1 + "(" + tableCRUD1 + ")";
					for (int k = 0; k < attrList.size(); k++) {
						compareNewAndOldElements(newElementWork, oldElementWork, attrList.get(k), elementLabel);
					}

					/////////////////////
					// IO Table Fields //
					/////////////////////
					oldElementList2 = oldElementWork.getElementsByTagName("IOTableField");
					newElementList2 = newElementWork.getElementsByTagName("IOTableField");
					for (int m = 0; m < newElementList2.getLength(); m++) {
						newElementWork2 = (org.w3c.dom.Element)newElementList2.item(m);
						fieldName1 = getSortKeyAccordingToID("FieldID", newElementWork2.getAttribute("FieldID"), systemElementNew);
						isNotFound2 = true;
						for (int p = 0; p < oldElementList2.getLength(); p++) {
							oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(p);
							fieldName2 = getSortKeyAccordingToID("FieldID", oldElementWork2.getAttribute("FieldID"), systemElementOld);
							if (fieldName1.equals(fieldName2)) {
								if (newElementWork2.getAttribute("Descriptions").equals(oldElementWork2.getAttribute("Descriptions"))) {
									buffer.append("\n" + countOfChanges + "."
											+ elementLabel + res.getString("DialogToListChangesOfFiles20")
											+ res.getString("DialogToListChangesOfFiles28")
											+ fieldName1 + res.getString("DialogToListChangesOfFiles29")
											+ res.getString("DialogToListChangesOfFiles34"));
								}
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							countOfChanges++;
							buffer.append("\n" + countOfChanges + "."
									+ elementLabel + res.getString("DialogToListChangesOfFiles20")
									+ res.getString("DialogToListChangesOfFiles28")
									+ fieldName1 + res.getString("DialogToListChangesOfFiles29")
									+ res.getString("DialogToListChangesOfFiles34"));
							break;
						}
					}
					for (int m = 0; m < oldElementList2.getLength(); m++) {
						oldElementWork2 = (org.w3c.dom.Element)oldElementList2.item(m);
						workElement = getFieldElementAccordingToID(oldElementWork.getAttribute("TableID"), oldElementWork2.getAttribute("FieldID"), systemElementOld);
						fieldName2 = workElement.getAttribute("Name");
						isNotFound2 = true;
						for (int p = 0; p < newElementList2.getLength(); p++) {
							newElementWork2 = (org.w3c.dom.Element)newElementList2.item(p);
							workElement = getFieldElementAccordingToID(newElementWork.getAttribute("TableID"), newElementWork2.getAttribute("FieldID"), systemElementNew);
							fieldName1 = workElement.getAttribute("Name");
							if (fieldName1.equals(fieldName2)) {
								isNotFound2 = false;
								break;
							}
						}
						if (isNotFound2) {
							countOfChanges++;
							buffer.append("\n" + countOfChanges + "."
									+ elementLabel + res.getString("DialogToListChangesOfFiles20")
									+ res.getString("DialogToListChangesOfFiles28")
									+ fieldName2 + res.getString("DialogToListChangesOfFiles29")
									+ res.getString("DialogToListChangesOfFiles31"));
							break;
						}
					}
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ tableID1 + "(" + tableCRUD1 + ")" + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles30"));
			}
		}
		for (int i = 0; i < oldElementList.getLength(); i++) {
			oldElementWork = (org.w3c.dom.Element)oldElementList.item(i);
			tableID2 = getSortKeyAccordingToID("TableID", oldElementWork.getAttribute("TableID"), systemElementOld);
			tableCRUD2 = getTableCRUD(oldElementWork);
			isNotFound = true;
			for (int j = 0; j < newElementList.getLength(); j++) {
				newElementWork = (org.w3c.dom.Element)newElementList.item(j);
				tableID1 = getSortKeyAccordingToID("TableID", newElementWork.getAttribute("TableID"), systemElementNew);
				tableCRUD1 = getTableCRUD(newElementWork);
				if (tableID1.equals(tableID2) && tableCRUD1.equals(tableCRUD2)) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + countOfChanges + "."
						+ functionLabel + res.getString("DialogToListChangesOfFiles20")
						+ elementName + res.getString("DialogToListChangesOfFiles28")
						+ tableID2 + "(" + tableCRUD2 + ")" + res.getString("DialogToListChangesOfFiles29")
						+ res.getString("DialogToListChangesOfFiles31"));
			}
		}
	}

	private String getTableCRUD(org.w3c.dom.Element tableIOElement) {
		String crud = "";
		if (tableIOElement.getAttribute("OpC").equals("+")) {
			crud = "C";
		}
		if (tableIOElement.getAttribute("OpR").equals("+")) {
			crud = crud + "R";
		}
		if (tableIOElement.getAttribute("OpU").equals("+")) {
			crud = crud + "U";
		}
		if (tableIOElement.getAttribute("OpD").equals("+")) {
			crud = crud + "D";
		}
		return crud;
	}

//	private String getFieldNameAccordingToID(String tableID, String fieldID, org.w3c.dom.Element systemElement) {
//		String value = "(N/A)";
//		org.w3c.dom.Element tableElement, fieldElement;
//		NodeList tableList = systemElement.getElementsByTagName("Table");
//		for (int i = 0; i < tableList.getLength(); i++) {
//			tableElement = (org.w3c.dom.Element)tableList.item(i);
//			if (tableElement.getAttribute("ID").equals(tableID)) {
//				NodeList fieldList = tableElement.getElementsByTagName("TableField");
//				for (int j = 0; j < fieldList.getLength(); j++) {
//					fieldElement = (org.w3c.dom.Element)fieldList.item(j);
//					if (fieldElement.getAttribute("ID").equals(fieldID)) {
//						value = fieldElement.getAttribute("Name");
//						break;
//					}
//				}
//				break;
//			}
//		}
//		return value;
//	}

	private org.w3c.dom.Element getFieldElementAccordingToID(String tableID, String fieldID, org.w3c.dom.Element systemElement) {
		org.w3c.dom.Element fieldElement = null;
		org.w3c.dom.Element tableElement, workElement;
		NodeList tableList = systemElement.getElementsByTagName("Table");
		for (int i = 0; i < tableList.getLength(); i++) {
			tableElement = (org.w3c.dom.Element)tableList.item(i);
			if (tableElement.getAttribute("ID").equals(tableID)) {
				NodeList fieldList = tableElement.getElementsByTagName("TableField");
				for (int j = 0; j < fieldList.getLength(); j++) {
					workElement = (org.w3c.dom.Element)fieldList.item(j);
					if (workElement.getAttribute("ID").equals(fieldID)) {
						fieldElement = workElement;
						break;
					}
				}
				break;
			}
		}
		return fieldElement;
	}

	private String getElementNameAccordingToTagName(String tagName) {
		String elementName = "???";

		if (tagName.equals("Department")) {
			elementName = res.getString("S341");
		}
		if (tagName.equals("TaskType")) {
			elementName = res.getString("S362");
		}
		if (tagName.equals("TableType")) {
			elementName = res.getString("S477");
		}
		if (tagName.equals("DataType")) {
			elementName = res.getString("S544");
		}
		if (tagName.equals("FunctionType")) {
			elementName = res.getString("S417");
		}
		if (tagName.equals("Terms")) {
			elementName = res.getString("S245");
		}
		if (tagName.equals("MaintenanceLog")) {
			elementName = res.getString("S252");
		}

		if (tagName.equals("SubjectArea")) {
			elementName = res.getString("S3385");
		}
		if (tagName.equals("Role")) {
			elementName = res.getString("S339");
		}
		if (tagName.equals("Task")) {
			elementName = res.getString("S359");
		}

		if (tagName.equals("Subsystem")) {
			elementName = res.getString("S413");
		}

		if (tagName.equals("Table")) {
			elementName = res.getString("DialogToListChangesOfFiles10");
		}
		if (tagName.equals("TableField")) {
			elementName = res.getString("DialogToListChangesOfFiles11");
		}
		if (tagName.equals("TableKey")) {
			elementName = res.getString("DialogToListChangesOfFiles12");
		}
		if (tagName.equals("Relationship")) {
			elementName = res.getString("DialogToListChangesOfFiles13");
		}

		if (tagName.equals("Function")) {
			elementName = res.getString("DialogToListChangesOfFiles14");
		}
		if (tagName.equals("FunctionUsedByThis")) {
			elementName = res.getString("DialogToListChangesOfFiles15");
		}
		if (tagName.equals("IOPanel")) {
			elementName = res.getString("DialogToListChangesOfFiles16");
		}
		if (tagName.equals("IOWebPage")) {
			elementName = res.getString("DialogToListChangesOfFiles17");
		}
		if (tagName.equals("IOSpool")) {
			elementName = res.getString("DialogToListChangesOfFiles18");
		}
		if (tagName.equals("IOTable")) {
			elementName = res.getString("DialogToListChangesOfFiles19");
		}

		return elementName;
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

class DialogToListChangesOfFiles_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogToListChangesOfFiles adaptee;

	DialogToListChangesOfFiles_jButtonStart_actionAdapter(DialogToListChangesOfFiles adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogToListChangesOfFiles_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogToListChangesOfFiles adaptee;

	DialogToListChangesOfFiles_jButtonCancel_actionAdapter(DialogToListChangesOfFiles adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}