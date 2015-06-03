package xeadModeler;

/*
 * Copyright (c) 2014 WATANABE kozo <qyf05466@nifty.com>,
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
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.apache.poi.xssf.usermodel.*;
import org.w3c.dom.*;

public class DialogMatrixList extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private String fileName_;
	private String xlsFileName;
	private JPanel jPanelMain = new JPanel();
	private JPanel jPanelSouth = new JPanel();
	private JPanel jPanelCenter = new JPanel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private GridLayout gridLayout1 = new GridLayout();
	private JCheckBox jCheckBoxSubjectAreaAndTask = new JCheckBox();
	private JCheckBox jCheckBoxSubjectAreaAndFunction = new JCheckBox();
	private JCheckBox jCheckBoxTaskAndFunction = new JCheckBox();
	private JCheckBox jCheckBoxTaskAndTable = new JCheckBox();
	private JCheckBox jCheckBoxTableAndFunction = new JCheckBox();
	private JButton jButtonStart = new JButton();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonCloseDialog = new JButton();
	private NodeList subjectAreaList = null;
	private NodeList taskList = null;
	private NodeList taskTypeList = null;
	private NodeList roleList = null;
	private NodeList subsystemList = null;
	private NodeList tableList = null;
	private NodeList tableTypeList = null;
	private NodeList functionList = null;
	private NodeList functionTypeList = null;
	private int currentRowNumber = 0;
	private int countOfErrors = 0;
	private xeadModeler.Modeler.SortableDomElementListModel sortableDomElementListModel0 = null;
	private xeadModeler.Modeler.SortableDomElementListModel sortableDomElementListModel1 = null;
	private xeadModeler.Modeler.SortableDomElementListModel sortableDomElementListModel2 = null;
	private xeadModeler.Modeler.SortableDomElementListModel sortableDomElementListModel3 = null;
	private ArrayList<String> keyList = new ArrayList<String>();
	private HashMap<String, String> hashKeyList = new HashMap<String, String>();

	private XSSFFont fontTitle = null;
	private XSSFFont fontHeader1 = null;
	private XSSFFont fontHeader2 = null;
	private XSSFFont fontValue = null;
	private XSSFWorkbook workBook = null;
	private XSSFCellStyle styleTitle = null;
	private XSSFCellStyle styleHeaderRotated = null;
	private XSSFCellStyle styleHeaderNormal = null;
	private XSSFCellStyle styleHeaderNumber = null;
	private XSSFCellStyle styleValue = null;
	private XSSFCellStyle styleValueNumber = null;
	private XSSFCellStyle styleCheck = null;

	public DialogMatrixList(Modeler frame, String title, boolean modal) {
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

	public DialogMatrixList(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		jPanelMain.setLayout(borderLayout1);
		jPanelMain.setPreferredSize(new Dimension(300, 180));
		jPanelMain.setBorder(null);
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 45));
		jPanelCenter.setLayout(gridLayout1);
		gridLayout1.setColumns(1);
		gridLayout1.setRows(5);
		jPanelCenter.setLayout(gridLayout1);
		jCheckBoxSubjectAreaAndTask.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubjectAreaAndTask.setText(res.getString("S75"));
		jCheckBoxSubjectAreaAndTask.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxSubjectAreaAndFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubjectAreaAndFunction.setText(res.getString("S76"));
		jCheckBoxSubjectAreaAndFunction.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxTaskAndFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTaskAndFunction.setText(res.getString("S77"));
		jCheckBoxTaskAndFunction.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxTaskAndTable.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTaskAndTable.setText(res.getString("S78"));
		jCheckBoxTaskAndTable.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxTableAndFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTableAndFunction.setText(res.getString("S79"));
		jCheckBoxTableAndFunction.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));

		getContentPane().add(jPanelMain);
		jPanelMain.add(jPanelSouth, BorderLayout.SOUTH);
		jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
		jPanelCenter.add(jCheckBoxSubjectAreaAndTask, null);
		jPanelCenter.add(jCheckBoxSubjectAreaAndFunction, null);
		jPanelCenter.add(jCheckBoxTaskAndFunction, null);
		jPanelCenter.add(jCheckBoxTaskAndTable, null);
		jPanelCenter.add(jCheckBoxTableAndFunction, null);

		jButtonCloseDialog.setBounds(new Rectangle(13, 8, 110, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogDocuments08"));
		jButtonCloseDialog.addActionListener(new DialogMatrixList_jButtonCloseDialog_actionAdapter(this));
		jButtonStart.setBounds(new Rectangle(170, 8, 120, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogDocuments07"));
		jButtonStart.addActionListener(new DialogMatrixList_jButtonStart_actionAdapter(this));
		jProgressBar.setBounds(new Rectangle(170, 8, 120, 27));
		jProgressBar.setVisible(false);
		jProgressBar.setStringPainted(true);
		jPanelSouth.add(jButtonCloseDialog, null);
		jPanelSouth.add(jButtonStart, null);
		jPanelSouth.add(jProgressBar, null);
		sortableDomElementListModel0 = frame_.new SortableDomElementListModel();
		sortableDomElementListModel1 = frame_.new SortableDomElementListModel();
		sortableDomElementListModel2 = frame_.new SortableDomElementListModel();
		sortableDomElementListModel3 = frame_.new SortableDomElementListModel();

		this.setResizable(false);
		this.setTitle(res.getString("S74"));
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String fileName) {
		fileName_ = fileName;
		xlsFileName = "";

		subsystemList = frame_.domDocument.getElementsByTagName("Subsystem");
		subjectAreaList = frame_.domDocument.getElementsByTagName("SubjectArea");
		taskTypeList = frame_.domDocument.getElementsByTagName("TaskType");
		taskList = frame_.domDocument.getElementsByTagName("Task");
		roleList = frame_.domDocument.getElementsByTagName("Role");
		tableList = frame_.domDocument.getElementsByTagName("Table");
		tableTypeList = frame_.domDocument.getElementsByTagName("TableType");
		functionList = frame_.domDocument.getElementsByTagName("Function");
		functionTypeList = frame_.domDocument.getElementsByTagName("FunctionType");

		jCheckBoxSubjectAreaAndTask.setSelected(false);
		jCheckBoxSubjectAreaAndFunction.setSelected(false);
		jCheckBoxTaskAndFunction.setSelected(false);
		jCheckBoxTaskAndTable.setSelected(false);
		jCheckBoxTableAndFunction.setSelected(false);
		jButtonStart.setEnabled(false);
		super.setVisible(true);

		return xlsFileName;
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		int countOfDefinitions = 0;

		try{
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);
			countOfErrors = 0;

			File file = new File(fileName_);
			xlsFileName = file.getParent() + File.separator + "MatrixList" + getStringValueOfDateAndTime() + ".xlsx";

			if (jCheckBoxSubjectAreaAndTask.isSelected()) {
				countOfDefinitions = countOfDefinitions + taskList.getLength();
			}
			if (jCheckBoxSubjectAreaAndFunction.isSelected()) {
				countOfDefinitions = countOfDefinitions + functionList.getLength() + subjectAreaList.getLength();
			}
			if (jCheckBoxTaskAndFunction.isSelected()) {
				countOfDefinitions = countOfDefinitions + functionList.getLength() + taskList.getLength();
			}
			if (jCheckBoxTaskAndTable.isSelected()) {
				countOfDefinitions = countOfDefinitions + tableList.getLength() + taskList.getLength();
			}
			if (jCheckBoxTableAndFunction.isSelected()) {
				countOfDefinitions = countOfDefinitions + tableList.getLength() + functionList.getLength();
			}

			jProgressBar.setValue(0);
			jProgressBar.setMaximum(countOfDefinitions);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
			FileOutputStream fileOut = new FileOutputStream(xlsFileName);
			createWorkBookAndStyles();

			if (jCheckBoxSubjectAreaAndTask.isSelected()) {
				generateSheetForSubjectAreaAndTask();
			}
			if (jCheckBoxSubjectAreaAndFunction.isSelected()) {
				generateSheetForSubjectAreaAndFunction();
			}
			if (jCheckBoxTaskAndFunction.isSelected()) {
				generateSheetForTaskAndFunction();
			}
			if (jCheckBoxTaskAndTable.isSelected()) {
				generateSheetForTaskAndTable();
			}
			if (jCheckBoxTableAndFunction.isSelected()) {
				generateSheetForTableAndFunction();
			}

			workBook.write(fileOut);
			fileOut.close();

		} catch(Exception ex1) {
			ex1.printStackTrace();
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			if (countOfErrors == 0) {
				//JOptionPane.showMessageDialog(this.getContentPane(), res.getString("S81") + "\n" + xlsFileName);
				File workXlsFile = new File(xlsFileName);
				try {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
					frame_.desktop.open(workXlsFile);
				} catch (Exception ex) {
				} finally {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			} else {
				countOfDefinitions = countOfDefinitions - countOfErrors;
				JOptionPane.showMessageDialog(this.getContentPane(), "Generating Matrix List has failed possibly because that too many columns for a Excel sheet has been demanded.");
			}
			super.setVisible(false);
		}
	}
	
	void generateSheetForSubjectAreaAndTask() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S75"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Task Type //
			sheet.setColumnWidth(2, 6000); // Role //
			sheet.setColumnWidth(3, 8000); // Task //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S362")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S348")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S360")));

			setupSubjectAreaColumnsForTask(sheet, topRow);
			setupTaskRowsForSubjectAreas(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForSubjectAreaAndFunction() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S76"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 12000); // Function //
			sheet.setColumnWidth(3, 6000); // Function Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S415")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S417")));

			setupSubjectAreaColumnsForFunction(sheet, topRow);
			setupFunctionRowsForSubjectAreas(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForTaskAndFunction() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S77"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 12000); // Function //
			sheet.setColumnWidth(3, 6000); // Function Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S415")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S417")));

			setupTaskColumnsForFunction(sheet, topRow);
			setupFunctionRowsForTasks(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForTaskAndTable() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S78"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 8000); // Table //
			sheet.setColumnWidth(3, 4500); // Table Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S471")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S477")));

			setupTaskColumnsForTable(sheet, topRow);
			setupTableRowsForTask(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForTableAndFunction() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S79"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 12000); // Function //
			sheet.setColumnWidth(3, 6000); // Function Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S415")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S417")));

			setupTableColumnsForFunction(sheet, topRow);
			setupFunctionRowsForTable(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}

	void setupSubjectAreaColumnsForTask(XSSFSheet sheet, XSSFRow topRow) {
		org.w3c.dom.Element workElement1 = null;
		String workString = "";

		sortableDomElementListModel0.removeAllElements();
		for (int i = 0; i < subjectAreaList.getLength(); i++) {
			sortableDomElementListModel0.addElement((Object)subjectAreaList.item(i));
		}
		sortableDomElementListModel0.sortElements();
		for (int i = 0; i < sortableDomElementListModel0.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1000);
		}
		if (sortableDomElementListModel0.getSize() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupSubjectAreaColumnsForFunction(XSSFSheet sheet, XSSFRow topRow) {
		org.w3c.dom.Element workElement1, workElement2, workElement3, workElement4;
		String workString = "";
		NodeList nodeList1, nodeList2;

		keyList.clear();
		sortableDomElementListModel0.removeAllElements();
		for (int i = 0; i < subjectAreaList.getLength(); i++) {
			sortableDomElementListModel0.addElement((Object)subjectAreaList.item(i));
		}
		sortableDomElementListModel0.sortElements();
		for (int i = 0; i < sortableDomElementListModel0.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1000);
			
			nodeList1 = workElement1.getElementsByTagName("DataflowNode");
			for (int j = 0; j < nodeList1.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)nodeList1.item(j);
				if (workElement2.getAttribute("Type").equals("Process")) {
					for (int k = 0; k < taskList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)taskList.item(k);
						if (workElement3.getAttribute("ID").equals(workElement2.getAttribute("TaskID"))) {
							nodeList2 = workElement3.getElementsByTagName("TaskFunctionIO");
							for (int l = 0; l < nodeList2.getLength(); l++) {
								workElement4 = (org.w3c.dom.Element)nodeList2.item(l);
								workString = workElement1.getAttribute("ID") + "," + workElement4.getAttribute("FunctionID"); 
								if (!keyList.contains(workString)) {
									keyList.add(workString);
								}
							}
							break;
						}
					}
				}
			}

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortableDomElementListModel0.getSize() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTaskColumnsForFunction(XSSFSheet sheet, XSSFRow topRow) {
		org.w3c.dom.Element workElement1, workElement2;
		String workString = "";
		NodeList nodeList1;

		keyList.clear();
		sortableDomElementListModel0.removeAllElements();
		for (int i = 0; i < taskList.getLength(); i++) {
			sortableDomElementListModel0.addElement((Object)taskList.item(i));
		}
		sortableDomElementListModel0.sortElements();
		for (int i = 0; i < sortableDomElementListModel0.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1000);
			
			nodeList1 = workElement1.getElementsByTagName("TaskFunctionIO");
			for (int j = 0; j < nodeList1.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)nodeList1.item(j);
				workString = workElement1.getAttribute("ID") + "," + workElement2.getAttribute("FunctionID"); 
				if (!keyList.contains(workString)) {
					keyList.add(workString);
				}
			}

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortableDomElementListModel0.getSize() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTaskColumnsForTable(XSSFSheet sheet, XSSFRow topRow) {
		org.w3c.dom.Element workElement1, workElement2, workElement3, workElement4;
		String workString = "";
		String crudMark = "";
		NodeList nodeList1, nodeList2;

		hashKeyList.clear();
		sortableDomElementListModel0.removeAllElements();
		for (int i = 0; i < taskList.getLength(); i++) {
			sortableDomElementListModel0.addElement((Object)taskList.item(i));
		}
		sortableDomElementListModel0.sortElements();
		for (int i = 0; i < sortableDomElementListModel0.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1400);
			
			nodeList1 = workElement1.getElementsByTagName("TaskFunctionIO");
			for (int j = 0; j < nodeList1.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)nodeList1.item(j);
					for (int k = 0; k < functionList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)functionList.item(k);
						if (workElement3.getAttribute("ID").equals(workElement2.getAttribute("FunctionID"))) {
							nodeList2 = workElement3.getElementsByTagName("IOTable");
							for (int l = 0; l < nodeList2.getLength(); l++) {
								workElement4 = (org.w3c.dom.Element)nodeList2.item(l);
								crudMark = "";
								workString = workElement1.getAttribute("ID") + "," + workElement4.getAttribute("TableID"); 
								if (hashKeyList.containsKey(workString)) {
									crudMark = hashKeyList.get(workString);
								}
								if (workElement4.getAttribute("OpC").equals("+") && !crudMark.contains("C")) {
									crudMark = crudMark + "C";
								}
								if (workElement4.getAttribute("OpR").equals("+") && !crudMark.contains("R")) {
									crudMark = crudMark + "R";
								}
								if (workElement4.getAttribute("OpU").equals("+") && !crudMark.contains("U")) {
									crudMark = crudMark + "U";
								}
								if (workElement4.getAttribute("OpD").equals("+") && !crudMark.contains("D")) {
									crudMark = crudMark + "D";
								}
								hashKeyList.put(workString, crudMark);
							}
							break;
						}
					}
			}

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortableDomElementListModel0.getSize() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTableColumnsForFunction(XSSFSheet sheet, XSSFRow topRow) {
		org.w3c.dom.Element workElement1;
		String workString = "";

		hashKeyList.clear();
		sortableDomElementListModel0.removeAllElements();
		for (int i = 0; i < tableList.getLength(); i++) {
			sortableDomElementListModel0.addElement((Object)tableList.item(i));
		}
		sortableDomElementListModel0.sortElements();
		
		for (int i = 0; i < sortableDomElementListModel0.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1400);

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortableDomElementListModel0.getSize() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTaskRowsForSubjectAreas(XSSFSheet sheet) {
		org.w3c.dom.Element workElement0, workElement1, workElement2, workElement3, workElement4;
		String workString = "";
		NodeList nodeList;
		boolean hasThisTask;

		sortableDomElementListModel1.removeAllElements();
		sortableDomElementListModel2.removeAllElements();
		sortableDomElementListModel3.removeAllElements();
		for (int i = 0; i < taskTypeList.getLength(); i++) {
			sortableDomElementListModel1.addElement((Object)taskTypeList.item(i));
		}
		for (int i = 0; i < roleList.getLength(); i++) {
			sortableDomElementListModel2.addElement((Object)roleList.item(i));
		}
		for (int i = 0; i < taskList.getLength(); i++) {
			sortableDomElementListModel3.addElement((Object)taskList.item(i));
		}
		sortableDomElementListModel1.sortElements();
		sortableDomElementListModel2.sortElements();
		sortableDomElementListModel3.sortElements();
		
		if (sortableDomElementListModel1.getSize() > 1) {
			for (int i = 0; i < sortableDomElementListModel1.getSize(); i++) {
				workElement1 = (org.w3c.dom.Element)sortableDomElementListModel1.getElementAt(i);
				for (int j = 0; j < sortableDomElementListModel2.getSize(); j++) {
					workElement2 = (org.w3c.dom.Element)sortableDomElementListModel2.getElementAt(j);
					for (int k = 0; k < sortableDomElementListModel3.getSize(); k++) {
						workElement3 = (org.w3c.dom.Element)sortableDomElementListModel3.getElementAt(k);
						if (workElement3.getAttribute("RoleID").equals(workElement2.getAttribute("ID"))
								&& workElement3.getAttribute("TaskTypeID").equals(workElement1.getAttribute("ID"))) {

							currentRowNumber++;
							XSSFRow row = sheet.createRow(currentRowNumber);
							XSSFCell cellSequence = row.createCell(0);
							cellSequence.setCellStyle(styleValueNumber);
							cellSequence.setCellValue(currentRowNumber);

							XSSFCell cellTaskType = row.createCell(1);
							cellTaskType.setCellStyle(styleValue);
							workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
							cellTaskType.setCellValue(new XSSFRichTextString(workString));

							XSSFCell cellRole = row.createCell(2);
							cellRole.setCellStyle(styleValue);
							workString = workElement2.getAttribute("SortKey") + " " + workElement2.getAttribute("Name");
							cellRole.setCellValue(new XSSFRichTextString(workString));

							XSSFCell cellTask = row.createCell(3);
							cellTask.setCellStyle(styleValue);
							workString = workElement3.getAttribute("SortKey") + " " + workElement3.getAttribute("Name");
							cellTask.setCellValue(new XSSFRichTextString(workString));

							for (int m = 0; m < sortableDomElementListModel0.getSize(); m++) {
								workElement0 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(m);
								hasThisTask = false;
								nodeList = workElement0.getElementsByTagName("DataflowNode");
								for (int n = 0; n < nodeList.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)nodeList.item(n);
									if (workElement4.getAttribute("TaskID").equals(workElement3.getAttribute("ID"))) {
										hasThisTask = true;
										break;
									}
								}
								XSSFCell cellMatrix = row.createCell(m+4);
								cellMatrix.setCellStyle(styleCheck);
								if (hasThisTask) {
									cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
								}
							}
							jProgressBar.setValue(jProgressBar.getValue() + 1);
							jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
						}
					}
				}
			}
		} else {
			for (int j = 0; j < sortableDomElementListModel2.getSize(); j++) {
				workElement2 = (org.w3c.dom.Element)sortableDomElementListModel2.getElementAt(j);
				for (int k = 0; k < sortableDomElementListModel3.getSize(); k++) {
					workElement3 = (org.w3c.dom.Element)sortableDomElementListModel3.getElementAt(k);
					if (workElement3.getAttribute("RoleID").equals(workElement2.getAttribute("ID"))) {
						currentRowNumber++;
						XSSFRow row = sheet.createRow(currentRowNumber);
						XSSFCell cellSequence = row.createCell(0);
						cellSequence.setCellStyle(styleValueNumber);
						cellSequence.setCellValue(currentRowNumber);

						XSSFCell cellTaskType = row.createCell(1);
						cellTaskType.setCellStyle(styleValue);
						cellTaskType.setCellValue(new XSSFRichTextString("N/A"));

						XSSFCell cellRole = row.createCell(2);
						cellRole.setCellStyle(styleValue);
						workString = workElement2.getAttribute("SortKey") + " / " + workElement2.getAttribute("Name");
						cellRole.setCellValue(new XSSFRichTextString(workString));

						XSSFCell cellTask = row.createCell(3);
						cellTask.setCellStyle(styleValue);
						workString = workElement3.getAttribute("SortKey") + " / " + workElement3.getAttribute("Name");
						cellTask.setCellValue(new XSSFRichTextString(workString));

						for (int m = 0; m < sortableDomElementListModel0.getSize(); m++) {
							workElement0 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(m);
							hasThisTask = false;
							nodeList = workElement0.getElementsByTagName("DataflowNode");
							for (int n = 0; n < nodeList.getLength(); n++) {
								workElement4 = (org.w3c.dom.Element)nodeList.item(n);
								if (workElement4.getAttribute("TaskID").equals(workElement3.getAttribute("ID"))) {
									hasThisTask = true;
								}
							}
							XSSFCell cellMatrix = row.createCell(m+4);
							cellMatrix.setCellStyle(styleCheck);
							if (hasThisTask) {
								cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
							}
						}
						jProgressBar.setValue(jProgressBar.getValue() + 1);
						jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					}
				}
			}
		}
	}

	void setupFunctionRowsForSubjectAreas(XSSFSheet sheet) {
		org.w3c.dom.Element workElement0, workElement1, workElement2, workElement3;
		String workString = "";

		sortableDomElementListModel1.removeAllElements();
		sortableDomElementListModel2.removeAllElements();
		for (int i = 0; i < subsystemList.getLength(); i++) {
			sortableDomElementListModel1.addElement((Object)subsystemList.item(i));
		}
		for (int i = 0; i < functionList.getLength(); i++) {
			sortableDomElementListModel2.addElement((Object)functionList.item(i));
		}
		sortableDomElementListModel1.sortElements();
		sortableDomElementListModel2.sortElements();
		
		for (int i = 0; i < sortableDomElementListModel1.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel1.getElementAt(i);
			for (int j = 0; j < sortableDomElementListModel2.getSize(); j++) {
				workElement2 = (org.w3c.dom.Element)sortableDomElementListModel2.getElementAt(j);
				if (workElement2.getAttribute("SubsystemID").equals(workElement1.getAttribute("ID"))) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = workElement2.getAttribute("SortKey") + " " + workElement2.getAttribute("Name");
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					for (int k = 0; k < functionTypeList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)functionTypeList.item(k);
						if (workElement2.getAttribute("FunctionTypeID").equals(workElement3.getAttribute("ID"))) {
							workString = workElement3.getAttribute("SortKey") + " " + workElement3.getAttribute("Name");
							break;
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					for (int m = 0; m < sortableDomElementListModel0.getSize(); m++) {
						workElement0 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(m);
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						workString = workElement0.getAttribute("ID") + "," + workElement2.getAttribute("ID");
						if (keyList.contains(workString)) {
							cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
						}
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void setupFunctionRowsForTasks(XSSFSheet sheet) {
		org.w3c.dom.Element workElement0, workElement1, workElement2, workElement3;
		String workString = "";

		sortableDomElementListModel1.removeAllElements();
		sortableDomElementListModel2.removeAllElements();
		for (int i = 0; i < subsystemList.getLength(); i++) {
			sortableDomElementListModel1.addElement((Object)subsystemList.item(i));
		}
		for (int i = 0; i < functionList.getLength(); i++) {
			sortableDomElementListModel2.addElement((Object)functionList.item(i));
		}
		sortableDomElementListModel1.sortElements();
		sortableDomElementListModel2.sortElements();

		for (int i = 0; i < sortableDomElementListModel1.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel1.getElementAt(i);
			for (int j = 0; j < sortableDomElementListModel2.getSize(); j++) {
				workElement2 = (org.w3c.dom.Element)sortableDomElementListModel2.getElementAt(j);
				if (workElement2.getAttribute("SubsystemID").equals(workElement1.getAttribute("ID"))) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = workElement2.getAttribute("SortKey") + " " + workElement2.getAttribute("Name");
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					for (int k = 0; k < functionTypeList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)functionTypeList.item(k);
						if (workElement2.getAttribute("FunctionTypeID").equals(workElement3.getAttribute("ID"))) {
							workString = workElement3.getAttribute("SortKey") + " " + workElement3.getAttribute("Name");
							break;
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					for (int m = 0; m < sortableDomElementListModel0.getSize(); m++) {
						workElement0 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(m);
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						workString = workElement0.getAttribute("ID") + "," + workElement2.getAttribute("ID");
						if (keyList.contains(workString)) {
							cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
						}
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void setupTableRowsForTask(XSSFSheet sheet) {
		org.w3c.dom.Element workElement0, workElement1, workElement2, workElement3;
		String workString = "";
		String crudMark = "";
		String crudCheck = "";

		sortableDomElementListModel1.removeAllElements();
		sortableDomElementListModel2.removeAllElements();
		for (int i = 0; i < subsystemList.getLength(); i++) {
			sortableDomElementListModel1.addElement((Object)subsystemList.item(i));
		}
		for (int i = 0; i < tableList.getLength(); i++) {
			sortableDomElementListModel2.addElement((Object)tableList.item(i));
		}
		sortableDomElementListModel1.sortElements();
		sortableDomElementListModel2.sortElements();

		for (int i = 0; i < sortableDomElementListModel1.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel1.getElementAt(i);
			for (int j = 0; j < sortableDomElementListModel2.getSize(); j++) {
				workElement2 = (org.w3c.dom.Element)sortableDomElementListModel2.getElementAt(j);
				if (workElement2.getAttribute("SubsystemID").equals(workElement1.getAttribute("ID"))) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = workElement2.getAttribute("SortKey") + " " + workElement2.getAttribute("Name");
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					for (int k = 0; k < tableTypeList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)tableTypeList.item(k);
						if (workElement2.getAttribute("TableTypeID").equals(workElement3.getAttribute("ID"))) {
							workString = workElement3.getAttribute("SortKey") + " " + workElement3.getAttribute("Name");
							break;
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					for (int m = 0; m < sortableDomElementListModel0.getSize(); m++) {
						workElement0 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(m);
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						workString = workElement0.getAttribute("ID") + "," + workElement2.getAttribute("ID");
						if (hashKeyList.containsKey(workString)) {
							crudMark = hashKeyList.get(workString);
							crudCheck = "";
							if (crudMark.contains("C")) {
								crudCheck = crudCheck + "C";
							}
							if (crudMark.contains("R")) {
								crudCheck = crudCheck + "R";
							}
							if (crudMark.contains("U")) {
								crudCheck = crudCheck + "U";
							}
							if (crudMark.contains("D")) {
								crudCheck = crudCheck + "D";
							}
							cellMatrix.setCellValue(new XSSFRichTextString(crudCheck));
						}
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void setupFunctionRowsForTable(XSSFSheet sheet) {
		org.w3c.dom.Element workElement0, workElement1, workElement2, workElement3;
		String workString = "";
		String crudMark = "";
		String crudCheck = "";
		NodeList nodeList1;

		sortableDomElementListModel1.removeAllElements();
		sortableDomElementListModel2.removeAllElements();
		for (int i = 0; i < subsystemList.getLength(); i++) {
			sortableDomElementListModel1.addElement((Object)subsystemList.item(i));
		}
		for (int i = 0; i < functionList.getLength(); i++) {
			sortableDomElementListModel2.addElement((Object)functionList.item(i));
		}
		sortableDomElementListModel1.sortElements();
		sortableDomElementListModel2.sortElements();
		
		for (int i = 0; i < sortableDomElementListModel1.getSize(); i++) {
			workElement1 = (org.w3c.dom.Element)sortableDomElementListModel1.getElementAt(i);
			for (int j = 0; j < sortableDomElementListModel2.getSize(); j++) {
				workElement2 = (org.w3c.dom.Element)sortableDomElementListModel2.getElementAt(j);
				if (workElement2.getAttribute("SubsystemID").equals(workElement1.getAttribute("ID"))) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = workElement1.getAttribute("SortKey") + " " + workElement1.getAttribute("Name");
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = workElement2.getAttribute("SortKey") + " " + workElement2.getAttribute("Name");
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					for (int k = 0; k < functionTypeList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)functionTypeList.item(k);
						if (workElement2.getAttribute("FunctionTypeID").equals(workElement3.getAttribute("ID"))) {
							workString = workElement3.getAttribute("SortKey") + " " + workElement3.getAttribute("Name");
							break;
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					nodeList1 = workElement2.getElementsByTagName("IOTable");
					for (int m = 0; m < sortableDomElementListModel0.getSize(); m++) {
						workElement0 = (org.w3c.dom.Element)sortableDomElementListModel0.getElementAt(m);
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						crudMark = "";
						for (int p = 0; p < nodeList1.getLength(); p++) {
							workElement3 = (org.w3c.dom.Element)nodeList1.item(p);
							if (workElement3.getAttribute("TableID").equals(workElement0.getAttribute("ID"))) {
								if (workElement3.getAttribute("OpC").equals("+") && !crudMark.contains("C")) {
									crudMark = crudMark + "C";
								}
								if (workElement3.getAttribute("OpR").equals("+") && !crudMark.contains("R")) {
									crudMark = crudMark + "R";
								}
								if (workElement3.getAttribute("OpU").equals("+") && !crudMark.contains("U")) {
									crudMark = crudMark + "U";
								}
								if (workElement3.getAttribute("OpD").equals("+") && !crudMark.contains("D")) {
									crudMark = crudMark + "D";
								}
							}
						}
						crudCheck = "";
						if (crudMark.contains("C")) {
							crudCheck = crudCheck + "C";
						}
						if (crudMark.contains("R")) {
							crudCheck = crudCheck + "R";
						}
						if (crudMark.contains("U")) {
							crudCheck = crudCheck + "U";
						}
						if (crudMark.contains("D")) {
							crudCheck = crudCheck + "D";
						}
						cellMatrix.setCellValue(new XSSFRichTextString(crudCheck));
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void createWorkBookAndStyles() {
		workBook = new XSSFWorkbook();
		fontTitle = workBook.createFont();
		fontTitle.setFontName(res.getString("DialogDocuments13"));
		fontTitle.setFontHeightInPoints((short)14);
		fontTitle.setColor(new XSSFColor(Color.WHITE));
		fontTitle.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		fontHeader1 = workBook.createFont();
		fontHeader1.setFontName(res.getString("DialogDocuments13"));
		fontHeader1.setFontHeightInPoints((short)10);
		fontHeader1.setItalic(true);
		fontHeader1.setColor(new XSSFColor(Color.WHITE));
		fontHeader2 = workBook.createFont();
		fontHeader2.setFontName(res.getString("DialogDocuments13"));
		fontHeader2.setFontHeightInPoints((short)10);
		fontValue = workBook.createFont();
		fontValue.setFontName(res.getString("DialogDocuments14"));
		fontValue.setFontHeightInPoints((short)10);
		styleTitle = workBook.createCellStyle();
		styleTitle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleTitle.setFillForegroundColor(new XSSFColor(Color.DARK_GRAY));
		styleTitle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleTitle.setFont(fontTitle);
		styleHeaderRotated = workBook.createCellStyle();
		styleHeaderRotated.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHeaderRotated.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHeaderRotated.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleHeaderRotated.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHeaderRotated.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleHeaderRotated.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleHeaderRotated.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleHeaderRotated.setRotation((short)180);
		styleHeaderRotated.setFont(fontValue);
		styleHeaderNormal = workBook.createCellStyle();
		styleHeaderNormal.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHeaderNormal.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHeaderNormal.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleHeaderNormal.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHeaderNormal.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleHeaderNormal.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHeaderNormal.setFont(fontValue);
		styleHeaderNumber = workBook.createCellStyle();
		styleHeaderNumber.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHeaderNumber.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHeaderNumber.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleHeaderNumber.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHeaderNumber.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		styleHeaderNumber.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHeaderNumber.setFont(fontValue);
		styleValue = workBook.createCellStyle();
		styleValue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleValue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleValue.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleValue.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleValue.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleValue.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleValue.setFont(fontValue);
		styleValue.setWrapText(true);
		styleCheck = workBook.createCellStyle();
		styleCheck.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleCheck.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleCheck.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleCheck.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleCheck.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleCheck.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleCheck.setFont(fontValue);
		styleValueNumber = workBook.createCellStyle();
		styleValueNumber.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		styleValueNumber.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleValueNumber.setFont(fontValue);
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void jCheckBox_stateChanged(ChangeEvent e) {
		jButtonStart.setEnabled(false);
		if (jCheckBoxSubjectAreaAndTask.isSelected()
				|| jCheckBoxSubjectAreaAndFunction.isSelected()
				|| jCheckBoxTaskAndFunction.isSelected()
				|| jCheckBoxTaskAndTable.isSelected()
				|| jCheckBoxTableAndFunction.isSelected()) {
			jButtonStart.setEnabled(true);
			this.getRootPane().setDefaultButton(jButtonStart);
		}
	}

	String getStringValueOfDateAndTime() {
		String returnValue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		//
		int year = calendar.get(Calendar.YEAR);
		//
		int month = calendar.get(Calendar.MONTH) + 1;
		String monthStr = "";
		if (month < 10) {
			monthStr = "0" + Integer.toString(month);
		} else {
			monthStr = Integer.toString(month);
		}
		//
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = "";
		if (day < 10) {
			dayStr = "0" + Integer.toString(day);
		} else {
			dayStr = Integer.toString(day);
		}
		//
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String hourStr = "";
		if (hour < 10) {
			hourStr = "0" + Integer.toString(hour);
		} else {
			hourStr = Integer.toString(hour);
		}
		//
		int minute = calendar.get(Calendar.MINUTE);
		String minStr = "";
		if (minute < 10) {
			minStr = "0" + Integer.toString(minute);
		} else {
			minStr = Integer.toString(minute);
		}
		//
		int second = calendar.get(Calendar.SECOND);
		String secStr = "";
		if (second < 10) {
			secStr = "0" + Integer.toString(second);
		} else {
			secStr = Integer.toString(second);
		}
		//
		returnValue = Integer.toString(year) + monthStr + dayStr + hourStr + minStr + secStr;
		return returnValue;
	}

}

class DialogMatrixList_jCheckBox_changeAdapter  implements ChangeListener {
	DialogMatrixList adaptee;
	DialogMatrixList_jCheckBox_changeAdapter(DialogMatrixList adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBox_stateChanged(e);
	}
}

class DialogMatrixList_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogMatrixList adaptee;

	DialogMatrixList_jButtonStart_actionAdapter(DialogMatrixList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogMatrixList_jButtonCloseDialog_actionAdapter implements java.awt.event.ActionListener {
	DialogMatrixList adaptee;

	DialogMatrixList_jButtonCloseDialog_actionAdapter(DialogMatrixList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}