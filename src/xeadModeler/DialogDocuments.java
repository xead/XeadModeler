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
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.w3c.dom.*;

public class DialogDocuments extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private String fileName_;
	private String xlsFileName;
	private JPanel jPanelMain = new JPanel();
	private JPanel jPanelCenter = new JPanel();
	private JPanel jPanelSouth = new JPanel();
	private JLabel jLabelDocumentType = new JLabel();
	private JLabel jLabelSubsystem = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private GridLayout gridLayout1 = new GridLayout();
	private JRadioButton jRadioButtonTable = new JRadioButton();
	private JRadioButton jRadioButtonFunction = new JRadioButton();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private SortableXeadNodeComboBoxModel comboBoxModelSubsystems = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxSubsystems = new JComboBox(comboBoxModelSubsystems);
	private SortableXeadNodeComboBoxModel comboBoxModelFunctions = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxFunctions = new JComboBox(comboBoxModelFunctions);
	private SortableXeadNodeComboBoxModel comboBoxModelTables = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxTables = new JComboBox(comboBoxModelTables);
	private JTextArea jTextArea1 = new JTextArea();
	private JButton jButtonStart = new JButton();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonCloseDialog = new JButton();
	private NodeList taskList = null;
	private NodeList roleList = null;
	private NodeList subsystemList = null;
	private NodeList tableList = null;
	private NodeList ioTableList = null;
	private NodeList relationshipList = null;
	private NodeList tableTypeList = null;
	private NodeList dataTypeList = null;
	private NodeList functionList = null;
	private NodeList functionTypeList = null;
	private org.w3c.dom.Element subsystemElement = null;
	private String subsystemID = "";
	private String tableID = "";
	private String functionID = "";
	private int currentRowNumber = 0;
	private int countOfErrors = 0;
	private XeadNode[] definitionArray = new XeadNode[200];
	private XeadNode[] elementArray1 = new XeadNode[1000];
	private int countOfElementArray1 = -1;
	private XeadNode[] elementArray2 = new XeadNode[1000];
	private int countOfElementArray2 = -1;

	private XSSFFont fontTitle = null;
	private XSSFFont fontHeader1 = null;
	private XSSFFont fontHeader2 = null;
	private XSSFFont fontValue = null;
	private XSSFFont fontValueStrong = null;
	private XSSFFont fontImage = null;
	private XSSFFont fontImageUL = null;
	private XSSFWorkbook workBook = null;
	private XSSFCellStyle styleTitle = null;
	private XSSFCellStyle styleHeader1 = null;
	private XSSFCellStyle styleHeader2 = null;
	private XSSFCellStyle styleHeader2Number = null;
	private XSSFCellStyle styleValue = null;
	private XSSFCellStyle styleValueStrong = null;
	private XSSFCellStyle styleValueNumber = null;
	private XSSFCellStyle styleImage = null;
	protected XSSFPicture picture = null;

	public DialogDocuments(Modeler frame, String title, boolean modal) {
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

	public DialogDocuments(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		jPanelMain.setLayout(borderLayout1);
		jPanelMain.setPreferredSize(new Dimension(600, 321));
		jPanelMain.setBorder(BorderFactory.createEtchedBorder());
		jPanelCenter.setBorder(BorderFactory.createEtchedBorder());
		jPanelCenter.setLayout(null);
		jPanelCenter.setPreferredSize(new Dimension(800, 134));
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 43));
		jPanel1.setLayout(gridLayout1);
		jLabelSubsystem.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelSubsystem.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelSubsystem.setText(res.getString("DialogDocuments01"));
		jLabelSubsystem.setBounds(new Rectangle(5, 12, 130, 20));
		jComboBoxSubsystems.setBounds(new Rectangle(140, 9, 250, 25));
		jComboBoxSubsystems.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxSubsystems.addActionListener(new DialogDocuments_jComboBoxSubsystems_actionAdapter(this));
		jLabelDocumentType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelDocumentType.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelDocumentType.setText(res.getString("DialogDocuments02"));
		jLabelDocumentType.setBounds(new Rectangle(5, 43, 130, 20));
		gridLayout1.setColumns(1);
		gridLayout1.setRows(2);
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setBounds(new Rectangle(140, 40, 180, 60));
		jPanel1.setLayout(gridLayout1);
		jRadioButtonTable.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTable.setText(res.getString("DialogDocuments03"));
		jRadioButtonTable.setSelected(true);
		jRadioButtonTable.addChangeListener(new DialogDocuments_jRadioButtonTable_changeAdapter(this));
		jRadioButtonFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonFunction.setText(res.getString("DialogDocuments04"));
		jRadioButtonFunction.addChangeListener(new DialogDocuments_jRadioButtonFunction_changeAdapter(this));
		buttonGroup.add(jRadioButtonTable);
		buttonGroup.add(jRadioButtonFunction);
		jComboBoxTables.setBounds(new Rectangle(325, 40, 262, 25));
		jComboBoxTables.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxTables.setEnabled(false);
		jComboBoxFunctions.setBounds(new Rectangle(325, 74, 262, 25));
		jComboBoxFunctions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxFunctions.setEnabled(false);
		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(5, 106, 584, 160));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea1.setText(res.getString("DialogDocuments05") + "\n" + "\n" + res.getString("DialogDocuments06"));

		getContentPane().add(jPanelMain);
		jPanelMain.add(jPanelSouth, BorderLayout.SOUTH);
		jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
		jPanel1.add(jRadioButtonTable, null);
		jPanel1.add(jRadioButtonFunction, null);
		jPanelCenter.add(jLabelSubsystem, null);
		jPanelCenter.add(jComboBoxSubsystems, null);
		jPanelCenter.add(jLabelDocumentType, null);
		jPanelCenter.add(jPanel1, null);
		jPanelCenter.add(jComboBoxTables, null);
		jPanelCenter.add(jComboBoxFunctions, null);
		jPanelCenter.add(jTextArea1, null);

		jButtonStart.setBounds(new Rectangle(30, 7, 200, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogDocuments07"));
		jButtonStart.addActionListener(new DialogDocuments_jButtonStart_actionAdapter(this));
		jProgressBar.setBounds(new Rectangle(30, 7, 200, 27));
		jProgressBar.setVisible(false);
		jProgressBar.setStringPainted(true);
		jButtonCloseDialog.setBounds(new Rectangle(440, 7, 110, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogDocuments08"));
		jButtonCloseDialog.addActionListener(new DialogDocuments_jButtonCloseDialog_actionAdapter(this));
		jPanelSouth.add(jButtonCloseDialog, null);
		jPanelSouth.add(jButtonStart, null);
		jPanelSouth.add(jProgressBar, null);

		this.setResizable(false);
		this.setTitle(res.getString("DialogDocuments09"));
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String fileName) {
		XeadNode node;
		fileName_ = fileName;
		xlsFileName = "";

		comboBoxModelSubsystems.removeAllElements();
		subsystemList = frame_.domDocument.getElementsByTagName("Subsystem");
		for (int i = 0; i < subsystemList.getLength(); i++) {
			node = new XeadNode("Subsystem",(org.w3c.dom.Element)subsystemList.item(i));
			comboBoxModelSubsystems.addElement((Object)node);
		}
		comboBoxModelSubsystems.sortElements();
		comboBoxModelSubsystems.insertElementAt(res.getString("DialogDocuments10"), 0);
		comboBoxModelSubsystems.setSelectedItem(comboBoxModelSubsystems.getElementAt(0));

		taskList = frame_.domDocument.getElementsByTagName("Task");
		roleList = frame_.domDocument.getElementsByTagName("Role");
		tableList = frame_.domDocument.getElementsByTagName("Table");
		ioTableList = frame_.domDocument.getElementsByTagName("IOTable");
		relationshipList = frame_.domDocument.getElementsByTagName("Relationship");
		dataTypeList = frame_.domDocument.getElementsByTagName("DataType");
		tableTypeList = frame_.domDocument.getElementsByTagName("TableType");
		functionList = frame_.domDocument.getElementsByTagName("Function");
		functionTypeList = frame_.domDocument.getElementsByTagName("FunctionType");

		super.setVisible(true);

		return xlsFileName;
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		org.w3c.dom.Element workElement = null;
		XeadNode workNode = null;
		int countOfDefinitions = 0;

		try{
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);
			subsystemID = "";
			countOfErrors = 0;

			if (jComboBoxSubsystems.getSelectedIndex() > 0) {
				workNode = (XeadNode)comboBoxModelSubsystems.getSelectedItem();
				subsystemElement = workNode.getElement();
				subsystemID = subsystemElement.getAttribute("ID");

				File file = new File(fileName_);

				if (jRadioButtonTable.isSelected()) {
					if (jComboBoxTables.getSelectedIndex() > 0) {
						workNode = (XeadNode)comboBoxModelTables.getSelectedItem();
						workElement = workNode.getElement();
						tableID = workElement.getAttribute("ID");
					}

					countOfDefinitions = 0;
					for (int m = 0; m < tableList.getLength(); m++) {
						workElement = (org.w3c.dom.Element)tableList.item(m);
						if (workElement.getAttribute("SubsystemID").equals(subsystemID)) {
							if (workElement.getAttribute("ID").equals(tableID) || tableID.equals("")) {
								workNode = new XeadNode("Table", workElement);
								definitionArray[countOfDefinitions] = workNode;
								countOfDefinitions++;
							}
						}
					}
					if (countOfDefinitions > 1) {
						Arrays.sort(definitionArray, 0, countOfDefinitions);
					}
					xlsFileName = file.getParent() + File.separator + "TableDoc" + getStringValueOfDateAndTime() + ".xlsx";
				}

				if (jRadioButtonFunction.isSelected()) {
					if (jComboBoxFunctions.getSelectedIndex() > 0) {
						workNode = (XeadNode)comboBoxModelFunctions.getSelectedItem();
						workElement = workNode.getElement();
						functionID = workElement.getAttribute("ID");
					}

					countOfDefinitions = 0;
					for (int m = 0; m < functionList.getLength(); m++) {
						workElement = (org.w3c.dom.Element)functionList.item(m);
						if (workElement.getAttribute("SubsystemID").equals(subsystemID)) {
							if (workElement.getAttribute("ID").equals(functionID) || functionID.equals("")) {
								workNode = new XeadNode("Table", workElement);
								definitionArray[countOfDefinitions] = workNode;
								countOfDefinitions++;
							}
						}
					}
					if (countOfDefinitions > 1) {
						Arrays.sort(definitionArray, 0, countOfDefinitions);
					}
					xlsFileName = file.getParent() + File.separator + "FunctionDoc" + getStringValueOfDateAndTime() + ".xlsx";
				}

				jProgressBar.setValue(0);
				jProgressBar.setMaximum(countOfDefinitions);

				FileOutputStream fileOut = new FileOutputStream(xlsFileName);
				createWorkBookAndStyles();

				if (jRadioButtonTable.isSelected()) {
					for (int m = 0; m < countOfDefinitions; m++) {
						workElement = (org.w3c.dom.Element)definitionArray[m].getElement();
						if (workElement.getAttribute("SubsystemID").equals(subsystemID)) {
							if (workElement.getAttribute("ID").equals(tableID) || tableID.equals("")) {
								jProgressBar.setValue(jProgressBar.getValue() + 1);
								jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
								//
								generateTableDocument(workElement);
							}
						}
					}
				}

				if (jRadioButtonFunction.isSelected()) {
					for (int m = 0; m < countOfDefinitions; m++) {
						workElement = (org.w3c.dom.Element)definitionArray[m].getElement();
						if (workElement.getAttribute("SubsystemID").equals(subsystemID)) {
							if (workElement.getAttribute("ID").equals(functionID) || functionID.equals("")) {
								jProgressBar.setValue(jProgressBar.getValue() + 1);
								jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
								//
								generateFunctionDocument(workElement);
							}
						}
					}
				}

				workBook.write(fileOut);
				fileOut.close();
			}
		} catch(Exception ex1) {
			ex1.printStackTrace();
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			if (countOfErrors == 0) {
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
				JOptionPane.showMessageDialog(this.getContentPane(), countOfDefinitions + res.getString("DialogDocuments11") + "\n" + xlsFileName + "\n" + countOfErrors + res.getString("DialogDocuments12"));
			}
		}
	}

	void createWorkBookAndStyles() {
		workBook = new XSSFWorkbook();

		fontTitle = workBook.createFont();
		fontTitle.setFontName(res.getString("DialogDocuments13"));
		fontTitle.setFontHeightInPoints((short)14);
		fontTitle.setColor(new XSSFColor(Color.black)); //this turns to white :XSSFColor bug! 
		fontTitle.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

		fontHeader1 = workBook.createFont();
		fontHeader1.setFontName(res.getString("DialogDocuments13"));
		fontHeader1.setFontHeightInPoints((short)10);
		fontHeader1.setItalic(true);
		fontHeader1.setColor(new XSSFColor(Color.black)); //this turns to white :XSSFColor bug!

		fontHeader2 = workBook.createFont();
		fontHeader2.setFontName(res.getString("DialogDocuments13"));
		fontHeader2.setFontHeightInPoints((short)10);

		fontValue = workBook.createFont();
		fontValue.setFontName(res.getString("DialogDocuments14"));
		fontValue.setFontHeightInPoints((short)10);

		fontValueStrong = workBook.createFont();
		fontValueStrong.setFontName(res.getString("DialogDocuments14"));
		fontValueStrong.setFontHeightInPoints((short)10);
		fontValueStrong.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

		fontImage = workBook.createFont();
		fontImage.setFontName(res.getString("DialogDocuments15"));
		fontImage.setFontHeightInPoints((short)6);

		fontImageUL = workBook.createFont();
		fontImageUL.setFontName(res.getString("DialogDocuments15"));
		fontImageUL.setFontHeightInPoints((short)6);
		fontImageUL.setUnderline(XSSFFont.U_SINGLE);

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

		styleHeader1 = workBook.createCellStyle();
		styleHeader1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHeader1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHeader1.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleHeader1.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHeader1.setFillForegroundColor(new XSSFColor(Color.DARK_GRAY));
		styleHeader1.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleHeader1.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleHeader1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHeader1.setFont(fontHeader1);

		styleHeader2 = workBook.createCellStyle();
		styleHeader2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHeader2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHeader2.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleHeader2.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHeader2.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY));
		styleHeader2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleHeader2.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleHeader2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHeader2.setFont(fontHeader2);

		styleHeader2Number = workBook.createCellStyle();
		styleHeader2Number.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setFillForegroundColor(new XSSFColor(Color.LIGHT_GRAY));
		styleHeader2Number.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		styleHeader2Number.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		styleHeader2Number.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleHeader2Number.setFont(fontHeader2);

		styleValue = workBook.createCellStyle();
		styleValue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleValue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleValue.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleValue.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleValue.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleValue.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleValue.setFont(fontValue);
		styleValue.setWrapText(true);

		styleValueStrong = workBook.createCellStyle();
		styleValueStrong.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleValueStrong.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleValueStrong.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleValueStrong.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleValueStrong.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleValueStrong.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleValueStrong.setFont(fontValueStrong);
		styleValueStrong.setWrapText(true);

		styleValueNumber = workBook.createCellStyle();
		styleValueNumber.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleValueNumber.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
		styleValueNumber.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleValueNumber.setFont(fontValue);

		styleImage = workBook.createCellStyle();
		styleImage.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleImage.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleImage.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleImage.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleImage.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleImage.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
		styleImage.setFont(fontImage);
		styleImage.setWrapText(true);
	}

	void generateTableDocument(org.w3c.dom.Element element) {
		try {
			XSSFSheet sheet = workBook.createSheet("(" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name"));
			sheet.setDefaultRowHeight( (short) 300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 4100);
			sheet.setColumnWidth(3, 4100);
			Footer footer = sheet.getFooter();
			footer.setRight("(" + subsystemElement.getAttribute("SortKey") + ")" + subsystemElement.getAttribute("Name") + " - (" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name") + "  Page &P / &N");

			setupTableSummary(sheet, element);
			setupTableFieldList(sheet, element);
			setupTableKeyList(sheet, element);
			setupTableUsageList(sheet, element);
			setupTableKeyDetails(sheet, element);
		}
		catch (Exception ex) {
			countOfErrors++;
		}
	}

	void generateFunctionDocument(org.w3c.dom.Element element) {
		try {
			XSSFSheet sheet = workBook.createSheet("(" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name"));
			sheet.setDefaultRowHeight( (short) 300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 4100);
			Footer footer = sheet.getFooter();
			footer.setRight("(" + subsystemElement.getAttribute("SortKey") + ")" + subsystemElement.getAttribute("Name") + " - (" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name") + "  Page &P / &N");

			setupFunctionSummary(sheet, element);
			setupFunctionIOList(sheet, element);
			setupFunctionsCalledByThisFunction(sheet, element);
			setupFunctionsCallingThis(sheet, element);
			setupTasksUsingThisFunction(sheet, element);
			setupFunctionIODetails(sheet, element);
		}
		catch (Exception ex) {
			countOfErrors++;
		}
	}

	void setupFunctionSummary(XSSFSheet sheet, org.w3c.dom.Element element) {
		org.w3c.dom.Element workElement1 = null;
		String workString = "";

		XSSFRow row0 = sheet.createRow(0);
		XSSFRow row1 = sheet.createRow(1);
		XSSFRow row2 = sheet.createRow(2);
		XSSFRow row3 = sheet.createRow(3);
		XSSFRow row4 = sheet.createRow(4);
		XSSFRow row5 = sheet.createRow(5);
		XSSFRow row6 = sheet.createRow(6);
		XSSFRow row7 = sheet.createRow(7);
		XSSFRow row8 = sheet.createRow(8);
		XSSFRow row9 = sheet.createRow(9);

		//Title
		XSSFRichTextString title = new XSSFRichTextString(res.getString("DialogDocuments16"));
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleTitle);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleTitle);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleTitle);
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleTitle);
		cellA0.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

		//Label Subsystem
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader2);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader2);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader2);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader2);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader2);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader2);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader2);
		cellC0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));

		//Value Subsystem
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleValue);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleValue);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleValue);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleValue);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleValue);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleValue);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleValue);
		cellC1.setCellValue(new XSSFRichTextString(subsystemElement.getAttribute("SortKey") + " / " + subsystemElement.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));

		//Label Function
		XSSFCell cellA2 = row2.createCell(0);
		cellA2.setCellStyle(styleHeader2);
		XSSFCell cellB2 = row2.createCell(1);
		cellB2.setCellStyle(styleHeader2);
		XSSFCell cellC2 = row2.createCell(2);
		cellC2.setCellStyle(styleHeader2);
		XSSFCell cellD2 = row2.createCell(3);
		cellD2.setCellStyle(styleHeader2);
		XSSFCell cellE2 = row2.createCell(4);
		cellE2.setCellStyle(styleHeader2);
		cellA2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));

		//Value Function
		XSSFCell cellA3 = row3.createCell(0);
		cellA3.setCellStyle(styleValue);
		XSSFCell cellB3 = row3.createCell(1);
		cellB3.setCellStyle(styleValue);
		XSSFCell cellC3 = row3.createCell(2);
		cellC3.setCellStyle(styleValue);
		XSSFCell cellD3 = row3.createCell(3);
		cellD3.setCellStyle(styleValue);
		XSSFCell cellE3 = row3.createCell(4);
		cellE3.setCellStyle(styleValue);
		cellA3.setCellValue(new XSSFRichTextString(element.getAttribute("SortKey") + " / " + element.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));

		//Label FunctionType
		XSSFCell cellF2 = row2.createCell(5);
		cellF2.setCellStyle(styleHeader2);
		XSSFCell cellG2 = row2.createCell(6);
		cellG2.setCellStyle(styleHeader2);
		XSSFCell cellH2 = row2.createCell(7);
		cellH2.setCellStyle(styleHeader2);
		XSSFCell cellI2 = row2.createCell(8);
		cellI2.setCellStyle(styleHeader2);
		cellF2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments19")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));

		//Value FunctionType
		XSSFCell cellF3 = row3.createCell(5);
		cellF3.setCellStyle(styleValue);
		XSSFCell cellG3 = row3.createCell(6);
		cellG3.setCellStyle(styleValue);
		XSSFCell cellH3 = row3.createCell(7);
		cellH3.setCellStyle(styleValue);
		XSSFCell cellI3 = row3.createCell(8);
		cellI3.setCellStyle(styleValue);
		for (int i = 0; i < functionTypeList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)functionTypeList.item(i);
			if (workElement1.getAttribute("ID").equals(element.getAttribute("FunctionTypeID"))) {
				workString = workElement1.getAttribute("SortKey") + " / " + workElement1.getAttribute("Name");
				break;
			}
		}
		cellF3.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));

		//Label Summary
		XSSFCell cellA4 = row4.createCell(0);
		cellA4.setCellStyle(styleHeader2);
		XSSFCell cellB4 = row4.createCell(1);
		cellB4.setCellStyle(styleHeader2);
		XSSFCell cellC4 = row4.createCell(2);
		cellC4.setCellStyle(styleHeader2);
		XSSFCell cellD4 = row4.createCell(3);
		cellD4.setCellStyle(styleHeader2);
		XSSFCell cellE4 = row4.createCell(4);
		cellE4.setCellStyle(styleHeader2);
		XSSFCell cellF4 = row4.createCell(5);
		cellF4.setCellStyle(styleHeader2);
		XSSFCell cellG4 = row4.createCell(6);
		cellG4.setCellStyle(styleHeader2);
		XSSFCell cellH4 = row4.createCell(7);
		cellH4.setCellStyle(styleHeader2);
		XSSFCell cellI4 = row4.createCell(8);
		cellI4.setCellStyle(styleHeader2);
		cellA4.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments20")));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

		//Value Summary
		XSSFCell cellA5 = row5.createCell(0);
		cellA5.setCellStyle(styleValue);
		XSSFCell cellB5 = row5.createCell(1);
		cellB5.setCellStyle(styleValue);
		XSSFCell cellC5 = row5.createCell(2);
		cellC5.setCellStyle(styleValue);
		XSSFCell cellD5 = row5.createCell(3);
		cellD5.setCellStyle(styleValue);
		XSSFCell cellE5 = row5.createCell(4);
		cellE5.setCellStyle(styleValue);
		XSSFCell cellF5 = row5.createCell(5);
		cellF5.setCellStyle(styleValue);
		XSSFCell cellG5 = row5.createCell(6);
		cellG5.setCellStyle(styleValue);
		XSSFCell cellH5 = row5.createCell(7);
		cellH5.setCellStyle(styleValue);
		XSSFCell cellI5 = row5.createCell(8);
		cellI5.setCellStyle(styleValue);
		cellA5.setCellValue(new XSSFRichTextString(element.getAttribute("Summary")));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));

		//Label Parameters
		XSSFCell cellA6 = row6.createCell(0);
		cellA6.setCellStyle(styleHeader2);
		XSSFCell cellB6 = row6.createCell(1);
		cellB6.setCellStyle(styleHeader2);
		XSSFCell cellC6 = row6.createCell(2);
		cellC6.setCellStyle(styleHeader2);
		XSSFCell cellD6 = row6.createCell(3);
		cellD6.setCellStyle(styleHeader2);
		XSSFCell cellE6 = row6.createCell(4);
		cellE6.setCellStyle(styleHeader2);
		XSSFCell cellF6 = row6.createCell(5);
		cellF6.setCellStyle(styleHeader2);
		cellA6.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments21")));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));

		//Value Parameters
		XSSFCell cellA7 = row7.createCell(0);
		cellA7.setCellStyle(styleValue);
		XSSFCell cellB7 = row7.createCell(1);
		cellB7.setCellStyle(styleValue);
		XSSFCell cellC7 = row7.createCell(2);
		cellC7.setCellStyle(styleValue);
		XSSFCell cellD7 = row7.createCell(3);
		cellD7.setCellStyle(styleValue);
		XSSFCell cellE7 = row7.createCell(4);
		cellE7.setCellStyle(styleValue);
		XSSFCell cellF7 = row7.createCell(5);
		cellF7.setCellStyle(styleValue);
		cellA7.setCellValue(new XSSFRichTextString(element.getAttribute("Parameters")));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 5));

		//Label Return
		XSSFCell cellG6 = row6.createCell(6);
		cellG6.setCellStyle(styleHeader2);
		XSSFCell cellH6 = row6.createCell(7);
		cellH6.setCellStyle(styleHeader2);
		XSSFCell cellI6 = row6.createCell(8);
		cellI6.setCellStyle(styleHeader2);
		cellG6.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments22")));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 8));

		//Value Return
		XSSFCell cellG7 = row7.createCell(6);
		cellG7.setCellStyle(styleValue);
		XSSFCell cellH7 = row7.createCell(7);
		cellH7.setCellStyle(styleValue);
		XSSFCell cellI7 = row7.createCell(8);
		cellI7.setCellStyle(styleValue);
		cellG7.setCellValue(new XSSFRichTextString(element.getAttribute("Return")));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 8));

		//Label Descriptions
		XSSFCell cellA8 = row8.createCell(0);
		cellA8.setCellStyle(styleHeader2);
		XSSFCell cellB8 = row8.createCell(1);
		cellB8.setCellStyle(styleHeader2);
		XSSFCell cellC8 = row8.createCell(2);
		cellC8.setCellStyle(styleHeader2);
		XSSFCell cellD8 = row8.createCell(3);
		cellD8.setCellStyle(styleHeader2);
		XSSFCell cellE8 = row8.createCell(4);
		cellE8.setCellStyle(styleHeader2);
		XSSFCell cellF8 = row8.createCell(5);
		cellF8.setCellStyle(styleHeader2);
		XSSFCell cellG8 = row8.createCell(6);
		cellG8.setCellStyle(styleHeader2);
		XSSFCell cellH8 = row8.createCell(7);
		cellH8.setCellStyle(styleHeader2);
		XSSFCell cellI8 = row8.createCell(8);
		cellI8.setCellStyle(styleHeader2);
		cellA8.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 8));

		//Value Descriptions
		XSSFCell cellA9 = row9.createCell(0);
		cellA9.setCellStyle(styleValue);
		XSSFCell cellB9 = row9.createCell(1);
		cellB9.setCellStyle(styleValue);
		XSSFCell cellC9 = row9.createCell(2);
		cellC9.setCellStyle(styleValue);
		XSSFCell cellD9 = row9.createCell(3);
		cellD9.setCellStyle(styleValue);
		XSSFCell cellE9 = row9.createCell(4);
		cellE9.setCellStyle(styleValue);
		XSSFCell cellF9 = row9.createCell(5);
		cellF9.setCellStyle(styleValue);
		XSSFCell cellG9 = row9.createCell(6);
		cellG9.setCellStyle(styleValue);
		XSSFCell cellH9 = row9.createCell(7);
		cellH9.setCellStyle(styleValue);
		XSSFCell cellI9 = row9.createCell(8);
		cellI9.setCellStyle(styleValue);
		workString = substringLinesWithTokenOfEOL(element.getAttribute("Descriptions"), "\n");
		cellA9.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 8));
		row9.setHeight((short)1000);

		currentRowNumber = 9;
	}

	void setupFunctionIOList(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		String workString = "";
		int rowSequence = 0;
		XeadNode node = null;

		//Label IOList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments24")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header IOList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments25")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments26")));
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

		countOfElementArray1 = -1;
		workList = element.getElementsByTagName("IOPanel");
		for (int i = 0; i < workList.getLength(); i++) {
			countOfElementArray1++;
			node = new XeadNode("IOPanel",(org.w3c.dom.Element)workList.item(i));
			elementArray1[countOfElementArray1] = node;
		}
		workList = element.getElementsByTagName("IOSpool");
		for (int i = 0; i < workList.getLength(); i++) {
			countOfElementArray1++;
			node = new XeadNode("IOSpool",(org.w3c.dom.Element)workList.item(i));
			elementArray1[countOfElementArray1] = node;
		}
		workList = element.getElementsByTagName("IOWebPage");
		for (int i = 0; i < workList.getLength(); i++) {
			countOfElementArray1++;
			node = new XeadNode("IOWebPage",(org.w3c.dom.Element)workList.item(i));
			elementArray1[countOfElementArray1] = node;
		}
		workList = element.getElementsByTagName("IOTable");
		for (int i = 0; i < workList.getLength(); i++) {
			countOfElementArray1++;
			node = new XeadNode("IOTable",(org.w3c.dom.Element)workList.item(i));
			elementArray1[countOfElementArray1] = node;
		}
		if (countOfElementArray1 > 0) {
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
		}

		rowSequence = 0;
		for (int i = 0; i <= countOfElementArray1; i++) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			rowSequence++;
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			cellA.setCellValue(rowSequence);
			XSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			workString = "";
			if (elementArray1[i].getType().equals("IOTable")) {
				for (int j = 0; j < tableList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableList.item(j);
					if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TableID"))) {
						workString = workElement2.getAttribute("SortKey") + workElement1.getAttribute("NameExtension") + " / " + workElement2.getAttribute("Name");
						break;
					}
				}
			} else {
				workString = elementArray1[i].getName();
			}
			cellB.setCellValue(new XSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			workString = "";
			if (elementArray1[i].getType().equals("IOPanel")) {
				workString = res.getString("DialogDocuments27");
			}
			if (elementArray1[i].getType().equals("IOSpool")) {
				workString = res.getString("DialogDocuments28");
			}
			if (elementArray1[i].getType().equals("IOWebPage")) {
				workString = res.getString("DialogDocuments29");
			}
			if (elementArray1[i].getType().equals("IOTable")) {
				workString = "[";
				if (workElement1.getAttribute("OpC").equals("+")) {
					workString = workString + "C";
				}
				if (workElement1.getAttribute("OpR").equals("+")) {
					workString = workString + "R";
				}
				if (workElement1.getAttribute("OpU").equals("+")) {
					workString = workString + "U";
				}
				if (workElement1.getAttribute("OpD").equals("+")) {
					workString = workString + "D";
				}
				workString = workString + "]";
			}
			cellE.setCellValue(new XSSFRichTextString(workString));
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			workString = substringLinesWithTokenOfEOL(workElement1.getAttribute("Descriptions"), "\n");
			cellF.setCellValue(new XSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			XSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			cellB.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		}
	}

	void setupFunctionsCalledByThisFunction(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		String workString1 = "";
		String workString2 = "";
		int rowSequence = 0;

		//Label FunctionsCalledByThis
		currentRowNumber++;
		XSSFRow rowLabel = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments30")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FunctionsCalledByThis
		currentRowNumber++;
		XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		XSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments21")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
		XSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellG1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments31")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

		//Rows of FunctionsCalledByThis
		workList = element.getElementsByTagName("FunctionUsedByThis");
		for (int i = 0; i < workList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList.item(i);
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			XSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			workString1 = "";
			workString2 = "";
			for (int j = 0; j < functionList.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)functionList.item(j);
				if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("FunctionID"))) {
					workString1 = workElement2.getAttribute("SortKey") + " / " + workElement2.getAttribute("Name");
					workString2 = workElement2.getAttribute("Parameters");
					break;
				}
			}
			cellB.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			cellE.setCellValue(new XSSFRichTextString(workString2));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			cellG.setCellValue(new XSSFRichTextString(workElement1.getAttribute("LaunchEvent")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupFunctionsCallingThis(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		int rowSequence = 0;

		//Label FunctionsCallingThis
		currentRowNumber++;
		XSSFRow rowLabel = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments32")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FunctionsCallingThis
		currentRowNumber++;
		XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		XSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments31")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));

		for (int i = 0; i < functionList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)functionList.item(i);
			workList = workElement1.getElementsByTagName("FunctionUsedByThis");
			for (int j = 0; j < workList.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)workList.item(j);
				if (workElement2.getAttribute("FunctionID").equals(element.getAttribute("ID"))) {
					currentRowNumber++;
					XSSFRow nextRow = sheet.createRow(currentRowNumber);
					XSSFCell cellA = nextRow.createCell(0);
					cellA.setCellStyle(styleValueNumber);
					rowSequence++;
					cellA.setCellValue(rowSequence);
					XSSFCell cellB = nextRow.createCell(1);
					cellB.setCellStyle(styleValue);
					XSSFCell cellC = nextRow.createCell(2);
					cellC.setCellStyle(styleValue);
					XSSFCell cellD = nextRow.createCell(3);
					cellD.setCellStyle(styleValue);
					cellB.setCellValue(new XSSFRichTextString(workElement1.getAttribute("SortKey") + " / " + workElement1.getAttribute("Name")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
					XSSFCell cellE = nextRow.createCell(4);
					cellE.setCellStyle(styleValue);
					XSSFCell cellF = nextRow.createCell(5);
					cellF.setCellStyle(styleValue);
					XSSFCell cellG = nextRow.createCell(6);
					cellG.setCellStyle(styleValue);
					XSSFCell cellH = nextRow.createCell(7);
					cellH.setCellStyle(styleValue);
					XSSFCell cellI = nextRow.createCell(8);
					cellI.setCellStyle(styleValue);
					cellE.setCellValue(new XSSFRichTextString(workElement2.getAttribute("LaunchEvent")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
				}
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
		}
	}

	void setupTasksUsingThisFunction(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		NodeList workList2 = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		org.w3c.dom.Element workElement4 = null;
		String workString1 = "";
		int rowSequence = 0;

		//Label TasksUsingThisFunction
		currentRowNumber++;
		XSSFRow rowLabel = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments33")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header TasksUsingThisFunction
		currentRowNumber++;
		XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments34")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		XSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		XSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments35")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
		XSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments36")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

		for (int i = 0; i < taskList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)taskList.item(i);
			workList1 = workElement1.getElementsByTagName("TaskAction");
			for (int j = 0; j < workList1.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)workList1.item(j);
				workList2 = workElement2.getElementsByTagName("TaskFunctionIO");
				for (int k = 0; k < workList2.getLength(); k++) {
					workElement3 = (org.w3c.dom.Element)workList2.item(k);
					if (workElement3.getAttribute("FunctionID").equals(element.getAttribute("ID"))) {
						currentRowNumber++;
						XSSFRow nextRow = sheet.createRow(currentRowNumber);
						XSSFCell cellA = nextRow.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = nextRow.createCell(1);
						cellB.setCellStyle(styleValue);
						XSSFCell cellC = nextRow.createCell(2);
						cellC.setCellStyle(styleValue);
						workString1 = "";
						for (int m = 0; m < roleList.getLength(); m++) {
							workElement4 = (org.w3c.dom.Element)roleList.item(m);
							if (workElement4.getAttribute("ID").equals(workElement1.getAttribute("RoleID"))) {
								workString1 = workElement4.getAttribute("Name");
								break;
							}
						}
						cellB.setCellValue(new XSSFRichTextString(workString1));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
						XSSFCell cellD = nextRow.createCell(3);
						cellD.setCellStyle(styleValue);
						XSSFCell cellE = nextRow.createCell(4);
						cellE.setCellStyle(styleValue);
						cellD.setCellValue(new XSSFRichTextString(workElement1.getAttribute("Name")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
						XSSFCell cellF = nextRow.createCell(5);
						cellF.setCellStyle(styleValue);
						XSSFCell cellG = nextRow.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = nextRow.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = nextRow.createCell(8);
						cellI.setCellStyle(styleValue);
						cellF.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Label")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
					}
				}
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 5));
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupFunctionIODetails(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		NodeList workList2 = null;
		NodeList workList3 = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		org.w3c.dom.Element workElement4 = null;
		String workString1 = "";
		String workString2 = "";
		String workString3 = "";
		int rowSequence = 0;
		int sectionNumber = 0;
		int totalSectionNumber = countOfElementArray1 + 1;
		XeadNode node = null;
		int start = 0;
		int end = 0;

		for (int i = 0; i <= countOfElementArray1; i++) {
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			sectionNumber++;

			currentRowNumber++;
			XSSFRow rowLabel = sheet.createRow(currentRowNumber);
			XSSFCell cellA0 = rowLabel.createCell(0);
			cellA0.setCellStyle(styleHeader1);
			XSSFCell cellB0 = rowLabel.createCell(1);
			cellB0.setCellStyle(styleHeader1);
			XSSFCell cellC0 = rowLabel.createCell(2);
			cellC0.setCellStyle(styleHeader1);
			XSSFCell cellD0 = rowLabel.createCell(3);
			cellD0.setCellStyle(styleHeader1);
			XSSFCell cellE0 = rowLabel.createCell(4);
			cellE0.setCellStyle(styleHeader1);
			XSSFCell cellF0 = rowLabel.createCell(5);
			cellF0.setCellStyle(styleHeader1);
			XSSFCell cellG0 = rowLabel.createCell(6);
			cellG0.setCellStyle(styleHeader1);
			XSSFCell cellH0 = rowLabel.createCell(7);
			cellH0.setCellStyle(styleHeader1);
			XSSFCell cellI0 = rowLabel.createCell(8);
			cellI0.setCellStyle(styleHeader1);
			cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments37") + "(" + sectionNumber + "/" + totalSectionNumber + ")"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

			currentRowNumber++;
			XSSFRow rowHeading1 = sheet.createRow(currentRowNumber);
			XSSFCell cellA1 = rowHeading1.createCell(0);
			cellA1.setCellStyle(styleHeader2);
			XSSFCell cellB1 = rowHeading1.createCell(1);
			cellB1.setCellStyle(styleHeader2);
			XSSFCell cellC1 = rowHeading1.createCell(2);
			cellC1.setCellStyle(styleHeader2);
			XSSFCell cellD1 = rowHeading1.createCell(3);
			cellD1.setCellStyle(styleHeader2);
			XSSFCell cellE1 = rowHeading1.createCell(4);
			cellE1.setCellStyle(styleHeader2);
			XSSFCell cellF1 = rowHeading1.createCell(5);
			cellF1.setCellStyle(styleHeader2);
			XSSFCell cellG1 = rowHeading1.createCell(6);
			cellG1.setCellStyle(styleHeader2);
			cellA1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments25")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 6));
			XSSFCell cellH1 = rowHeading1.createCell(7);
			cellH1.setCellStyle(styleHeader2);
			XSSFCell cellI1 = rowHeading1.createCell(8);
			cellI1.setCellStyle(styleHeader2);
			cellH1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments38")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 7, 8));

			currentRowNumber++;
			XSSFRow rowValue1 = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = rowValue1.createCell(0);
			cellA2.setCellStyle(styleValue);
			XSSFCell cellB2 = rowValue1.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = rowValue1.createCell(2);
			cellC2.setCellStyle(styleValue);
			XSSFCell cellD2 = rowValue1.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = rowValue1.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = rowValue1.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = rowValue1.createCell(6);
			cellG2.setCellStyle(styleValue);
			workString1 = "";
			if (elementArray1[i].getType().equals("IOTable")) {
				for (int j = 0; j < tableList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableList.item(j);
					if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TableID"))) {
						workString1 = workElement2.getAttribute("SortKey") + workElement1.getAttribute("NameExtension") + " / " + workElement2.getAttribute("Name");
						break;
					}
				}
			} else {
				workString1 = elementArray1[i].getName();
			}
			cellA2.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 6));
			XSSFCell cellH2 = rowValue1.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = rowValue1.createCell(8);
			cellI2.setCellStyle(styleValue);
			workString1 = "";
			if (elementArray1[i].getType().equals("IOPanel")) {
				workString1 = res.getString("DialogDocuments27");
			}
			if (elementArray1[i].getType().equals("IOSpool")) {
				workString1 = res.getString("DialogDocuments28");
			}
			if (elementArray1[i].getType().equals("IOWebPage")) {
				workString1 = res.getString("DialogDocuments29");
			}
			if (elementArray1[i].getType().equals("IOTable")) {
				workString1 = "Table[";
				if (workElement1.getAttribute("OpC").equals("+")) {
					workString1 = workString1 + "C";
				}
				if (workElement1.getAttribute("OpR").equals("+")) {
					workString1 = workString1 + "R";
				}
				if (workElement1.getAttribute("OpU").equals("+")) {
					workString1 = workString1 + "U";
				}
				if (workElement1.getAttribute("OpD").equals("+")) {
					workString1 = workString1 + "D";
				}
				workString1 = workString1 + "]";
			}
			cellH2.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 7, 8));

			currentRowNumber++;
			XSSFRow rowLabelHeading2 = sheet.createRow(currentRowNumber);
			XSSFCell cellA3 = rowLabelHeading2.createCell(0);
			cellA3.setCellStyle(styleHeader2);
			XSSFCell cellB3 = rowLabelHeading2.createCell(1);
			cellB3.setCellStyle(styleHeader2);
			XSSFCell cellC3 = rowLabelHeading2.createCell(2);
			cellC3.setCellStyle(styleHeader2);
			XSSFCell cellD3 = rowLabelHeading2.createCell(3);
			cellD3.setCellStyle(styleHeader2);
			XSSFCell cellE3 = rowLabelHeading2.createCell(4);
			cellE3.setCellStyle(styleHeader2);
			XSSFCell cellF3 = rowLabelHeading2.createCell(5);
			cellF3.setCellStyle(styleHeader2);
			XSSFCell cellG3 = rowLabelHeading2.createCell(6);
			cellG3.setCellStyle(styleHeader2);
			XSSFCell cellH3 = rowLabelHeading2.createCell(7);
			cellH3.setCellStyle(styleHeader2);
			XSSFCell cellI3 = rowLabelHeading2.createCell(8);
			cellI3.setCellStyle(styleHeader2);
			cellA3.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

			currentRowNumber++;
			XSSFRow rowValueDescriptions = sheet.createRow(currentRowNumber);
			XSSFCell cellA4 = rowValueDescriptions.createCell(0);
			cellA4.setCellStyle(styleValue);
			XSSFCell cellB4 = rowValueDescriptions.createCell(1);
			cellB4.setCellStyle(styleValue);
			XSSFCell cellC4 = rowValueDescriptions.createCell(2);
			cellC4.setCellStyle(styleValue);
			XSSFCell cellD4 = rowValueDescriptions.createCell(3);
			cellD4.setCellStyle(styleValue);
			XSSFCell cellE4 = rowValueDescriptions.createCell(4);
			cellE4.setCellStyle(styleValue);
			XSSFCell cellF4 = rowValueDescriptions.createCell(5);
			cellF4.setCellStyle(styleValue);
			XSSFCell cellG4 = rowValueDescriptions.createCell(6);
			cellG4.setCellStyle(styleValue);
			XSSFCell cellH4 = rowValueDescriptions.createCell(7);
			cellH4.setCellStyle(styleValue);
			XSSFCell cellI4 = rowValueDescriptions.createCell(8);
			cellI4.setCellStyle(styleValue);
			workString1 = substringLinesWithTokenOfEOL(workElement1.getAttribute("Descriptions"), "\n");
			cellA4.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
			rowValueDescriptions.setHeight((short)1000);

			if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOSpool") || elementArray1[i].getType().equals("IOWebPage")) {
				currentRowNumber++;
				XSSFRow rowLabelImage = sheet.createRow(currentRowNumber);
				XSSFCell cellA5 = rowLabelImage.createCell(0);
				cellA5.setCellStyle(styleHeader2);
				XSSFCell cellB5 = rowLabelImage.createCell(1);
				cellB5.setCellStyle(styleHeader2);
				XSSFCell cellC5 = rowLabelImage.createCell(2);
				cellC5.setCellStyle(styleHeader2);
				XSSFCell cellD5 = rowLabelImage.createCell(3);
				cellD5.setCellStyle(styleHeader2);
				XSSFCell cellE5 = rowLabelImage.createCell(4);
				cellE5.setCellStyle(styleHeader2);
				XSSFCell cellF5 = rowLabelImage.createCell(5);
				cellF5.setCellStyle(styleHeader2);
				XSSFCell cellG5 = rowLabelImage.createCell(6);
				cellG5.setCellStyle(styleHeader2);
				XSSFCell cellH5 = rowLabelImage.createCell(7);
				cellH5.setCellStyle(styleHeader2);
				XSSFCell cellI5 = rowLabelImage.createCell(8);
				cellI5.setCellStyle(styleHeader2);
				cellA5.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments39")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

				currentRowNumber++;
				XSSFRow rowValueImage = sheet.createRow(currentRowNumber);
				XSSFCell cellA6 = rowValueImage.createCell(0);
				cellA6.setCellStyle(styleImage);
				XSSFCell cellB6 = rowValueImage.createCell(1);
				cellB6.setCellStyle(styleImage);
				XSSFCell cellC6 = rowValueImage.createCell(2);
				cellC6.setCellStyle(styleImage);
				XSSFCell cellD6 = rowValueImage.createCell(3);
				cellD6.setCellStyle(styleImage);
				XSSFCell cellE6 = rowValueImage.createCell(4);
				cellE6.setCellStyle(styleImage);
				XSSFCell cellF6 = rowValueImage.createCell(5);
				cellF6.setCellStyle(styleImage);
				XSSFCell cellG6 = rowValueImage.createCell(6);
				cellG6.setCellStyle(styleImage);
				XSSFCell cellH6 = rowValueImage.createCell(7);
				cellH6.setCellStyle(styleImage);
				XSSFCell cellI6 = rowValueImage.createCell(8);
				cellI6.setCellStyle(styleImage);
				XSSFRichTextString valueImage = null;
				if (elementArray1[i].getType().equals("IOWebPage")) {
					workString1 = res.getString("DialogDocuments40a") + workElement1.getAttribute("FileName") + res.getString("DialogDocuments40b");
					valueImage = new XSSFRichTextString(workString1);
					cellA6.setCellValue(valueImage);
				}
				if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOSpool")) {
					int imageType = -1;
					File file = new File(fileName_);
					String imageFileName = "";
					if (frame_.ioImageFolder.equals("") || frame_.ioImageFolder.equals("<CURRENT>")) {
						imageFileName = file.getParent() + File.separator + element.getAttribute("SortKey") + "_" + workElement1.getAttribute("SortKey");
					} else {
						if (frame_.ioImageFolder.contains("<CURRENT>")) {
							imageFileName = frame_.ioImageFolder.replace("<CURRENT>", file.getParent());
							imageFileName = imageFileName + File.separator + element.getAttribute("SortKey") + "_" + workElement1.getAttribute("SortKey");
						} else {
							imageFileName = frame_.ioImageFolder + File.separator + element.getAttribute("SortKey") + "_" + workElement1.getAttribute("SortKey");
						}
					}
					File imageFile = new File(imageFileName + ".png");
					if (imageFile.exists()) {
						imageType = XSSFWorkbook.PICTURE_TYPE_PNG;
					} else {
						imageFile = new File(imageFileName + ".PNG");
						if (imageFile.exists()) {
							imageType = XSSFWorkbook.PICTURE_TYPE_PNG;
						} else {
							imageFile = new File(imageFileName + ".jpg");
							if (imageFile.exists()) {
								imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
							} else {
								imageFile = new File(imageFileName + ".JPG");
								if (imageFile.exists()) {
									imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
								} else {
									imageFile = new File(imageFileName + ".jpeg");
									if (imageFile.exists()) {
										imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
									} else {
										imageFile = new File(imageFileName + ".JPEG");
										if (imageFile.exists()) {
											imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
										}
									}
								}
							}
						}
					}

					if (imageFile.exists()) {
						FileInputStream fis = null;
						try {
							fis = new FileInputStream(imageFile);
							byte[] bytes = IOUtils.toByteArray(fis);
							int pictureIndex = workBook.addPicture(bytes, imageType);

							CreationHelper helper = workBook.getCreationHelper();
							Drawing drawing = sheet.createDrawingPatriarch();
							ClientAnchor anchor = helper.createClientAnchor();
							anchor.setCol1((short)0);
							anchor.setRow1(cellA6.getRowIndex());
							anchor.setCol2((short)9);
							anchor.setRow2(cellA6.getRowIndex()+1);
							anchor.setDx1(0);
							anchor.setDy1(0);
							anchor.setDx2(0);
							anchor.setDy2(0);
							drawing.createPicture(anchor, pictureIndex);
						} catch(Exception ex) {
							ex.printStackTrace();
						} finally {
							try {
								if (fis != null) {
									fis.close();
								}
							} catch (IOException ex1) {
							}
						}
					} else {
						workString1 = substringLinesWithTokenOfEOL(workElement1.
								getAttribute("ImageText"), "\n");
						if (elementArray1[i].getType().equals("IOPanel")) {
							workList1 = workElement1.getElementsByTagName("IOPanelStyle");
						}
						if (elementArray1[i].getType().equals("IOSpool")) {
							workList1 = workElement1.getElementsByTagName("IOSpoolStyle");
						}
						for (int j = 0; j < workList1.getLength(); j++) {
							org.w3c.dom.Element elementTextStyle = (org.w3c.dom.Element)
							workList1.item(j);
							StringTokenizer workTokenizer = new StringTokenizer(
									elementTextStyle.getAttribute("Value"), ",");
							start = Integer.parseInt(workTokenizer.nextToken());
							end = Integer.parseInt(workTokenizer.nextToken());
							if (workTokenizer.nextToken().equals("UL")) {
								for (int k = start; k <= end; k++) {
									if (workString1.substring(k, k + 1).equals(" ")) {
										workString2 = workString1.substring(0, k);
										workString3 = workString1.substring(k + 1,
												workString1.length());
										workString1 = workString2 + "." + workString3;
									}
								}
							}
						}
						valueImage = new XSSFRichTextString(workString1);
						valueImage.applyFont(0, workString1.length(), fontImage);
						for (int j = 0; j < workList1.getLength(); j++) {
							org.w3c.dom.Element elementTextStyle = (org.w3c.dom.Element)
							workList1.item(j);
							StringTokenizer workTokenizer = new StringTokenizer(
									elementTextStyle.getAttribute("Value"), ",");
							start = Integer.parseInt(workTokenizer.nextToken());
							end = Integer.parseInt(workTokenizer.nextToken());
							if (workTokenizer.nextToken().equals("UL")) {
								valueImage.applyFont(start, end, fontImageUL);
							}
						}
						cellA6.setCellValue(valueImage);
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
				rowValueImage.setHeight((short)3500);

				if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOWebPage")) {
					currentRowNumber++;
					XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
					XSSFCell cellA7 = rowColumnHeading.createCell(0);
					cellA7.setCellStyle(styleHeader2Number);
					cellA7.setCellValue(new XSSFRichTextString("No."));
					XSSFCell cellB7 = rowColumnHeading.createCell(1);
					cellB7.setCellStyle(styleHeader2);
					cellB7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments41")));
					XSSFCell cellC7 = rowColumnHeading.createCell(2);
					cellC7.setCellStyle(styleHeader2);
					XSSFCell cellD7 = rowColumnHeading.createCell(3);
					cellD7.setCellStyle(styleHeader2);
					cellC7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments42")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
					XSSFCell cellE7 = rowColumnHeading.createCell(4);
					cellE7.setCellStyle(styleHeader2);
					XSSFCell cellF7 = rowColumnHeading.createCell(5);
					cellF7.setCellStyle(styleHeader2);
					cellE7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments43")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
					XSSFCell cellG7 = rowColumnHeading.createCell(6);
					cellG7.setCellStyle(styleHeader2);
					XSSFCell cellH7 = rowColumnHeading.createCell(7);
					cellH7.setCellStyle(styleHeader2);
					XSSFCell cellI7 = rowColumnHeading.createCell(8);
					cellI7.setCellStyle(styleHeader2);
					cellG7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

					//Rows of Field
					rowSequence = 0;
					countOfElementArray2 = -1;
					if (elementArray1[i].getType().equals("IOPanel")) {
						workList1 = workElement1.getElementsByTagName("IOPanelField");
						for (int j = 0; j < workList1.getLength(); j++) {
							countOfElementArray2++;
							node = new XeadNode("IOPanelField",(org.w3c.dom.Element)workList1.item(j));
							elementArray2[countOfElementArray2] = node;
						}
					}
					if (elementArray1[i].getType().equals("IOWebPage")) {
						workList1 = workElement1.getElementsByTagName("IOWebPageField");
						for (int j = 0; j < workList1.getLength(); j++) {
							countOfElementArray2++;
							node = new XeadNode("IOWebPageField",(org.w3c.dom.Element)workList1.item(j));
							elementArray2[countOfElementArray2] = node;
						}
					}
					if (countOfElementArray2 > 0) {
						Arrays.sort(elementArray2, 0, countOfElementArray2 + 1);
					}
					for (int j = 0; j <= countOfElementArray2; j++) {
						workElement2 = (org.w3c.dom.Element)elementArray2[j].getElement();
						currentRowNumber++;
						XSSFRow rowValue2 = sheet.createRow(currentRowNumber);
						XSSFCell cellA = rowValue2.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = rowValue2.createCell(1);
						cellB.setCellStyle(styleValue);
						cellB.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Label")));
						XSSFCell cellC = rowValue2.createCell(2);
						cellC.setCellStyle(styleValue);
						XSSFCell cellD = rowValue2.createCell(3);
						cellD.setCellStyle(styleValue);
						cellC.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Name")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellE = rowValue2.createCell(4);
						cellE.setCellStyle(styleValue);
						XSSFCell cellF = rowValue2.createCell(5);
						cellF.setCellStyle(styleValue);
						workString1 = "";
						if (workElement2.getAttribute("IOType").equals("INPUT")) {
							workString1 = res.getString("DialogDocuments44");
						}
						if (workElement2.getAttribute("IOType").equals("REQUIRED")) {
							workString1 = res.getString("DialogDocuments45");
						}
						if (workElement2.getAttribute("IOType").equals("OUTPUT")) {
							workString1 = res.getString("DialogDocuments46");
						}
						if (workElement2.getAttribute("IOType").equals("CONTROL")) {
							workString1 = res.getString("DialogDocuments47");
						}
						cellE.setCellValue(new XSSFRichTextString(workString1));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
						XSSFCell cellG = rowValue2.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = rowValue2.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = rowValue2.createCell(8);
						cellI.setCellStyle(styleValue);
						cellG.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Descriptions")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					}

					if (rowSequence == 0) {
						currentRowNumber++;
						XSSFRow rowBlank = sheet.createRow(currentRowNumber);
						XSSFCell cellA8 = rowBlank.createCell(0);
						cellA8.setCellStyle(styleValueNumber);
						XSSFCell cellB8 = rowBlank.createCell(1);
						cellB8.setCellStyle(styleValue);
						cellB8.setCellValue(new XSSFRichTextString("(None)"));
						XSSFCell cellC8 = rowBlank.createCell(2);
						cellC8.setCellStyle(styleValue);
						XSSFCell cellD8 = rowBlank.createCell(3);
						cellD8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellE8 = rowBlank.createCell(4);
						cellE8.setCellStyle(styleValue);
						XSSFCell cellF8 = rowBlank.createCell(5);
						cellF8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
						XSSFCell cellG8 = rowBlank.createCell(6);
						cellG8.setCellStyle(styleValue);
						XSSFCell cellH8 = rowBlank.createCell(7);
						cellH8.setCellStyle(styleValue);
						XSSFCell cellI8 = rowBlank.createCell(8);
						cellI8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					}
				}
				if (elementArray1[i].getType().equals("IOSpool")) {
					currentRowNumber++;
					XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
					XSSFCell cellA7 = rowColumnHeading.createCell(0);
					cellA7.setCellStyle(styleHeader2Number);
					cellA7.setCellValue(new XSSFRichTextString("No."));
					XSSFCell cellB7 = rowColumnHeading.createCell(1);
					cellB7.setCellStyle(styleHeader2);
					cellB7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments41")));
					XSSFCell cellC7 = rowColumnHeading.createCell(2);
					cellC7.setCellStyle(styleHeader2);
					XSSFCell cellD7 = rowColumnHeading.createCell(3);
					cellD7.setCellStyle(styleHeader2);
					cellC7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments42")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
					XSSFCell cellE7 = rowColumnHeading.createCell(4);
					cellE7.setCellStyle(styleHeader2);
					XSSFCell cellF7 = rowColumnHeading.createCell(5);
					cellF7.setCellStyle(styleHeader2);
					XSSFCell cellG7 = rowColumnHeading.createCell(6);
					cellG7.setCellStyle(styleHeader2);
					XSSFCell cellH7 = rowColumnHeading.createCell(7);
					cellH7.setCellStyle(styleHeader2);
					XSSFCell cellI7 = rowColumnHeading.createCell(8);
					cellI7.setCellStyle(styleHeader2);
					cellE7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));

					//Rows of Field
					rowSequence = 0;
					countOfElementArray2 = -1;
					workList1 = workElement1.getElementsByTagName("IOSpoolField");
					for (int j = 0; j < workList1.getLength(); j++) {
						countOfElementArray2++;
						node = new XeadNode("IOSpoolField",(org.w3c.dom.Element)workList1.item(j));
						elementArray2[countOfElementArray2] = node;
					}
					if (countOfElementArray2 > 0) {
						Arrays.sort(elementArray2, 0, countOfElementArray2 + 1);
					}
					for (int j = 0; j <= countOfElementArray2; j++) {
						workElement2 = (org.w3c.dom.Element)elementArray2[j].getElement();
						currentRowNumber++;
						XSSFRow rowValue2 = sheet.createRow(currentRowNumber);
						XSSFCell cellA = rowValue2.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = rowValue2.createCell(1);
						cellB.setCellStyle(styleValue);
						cellB.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Label")));
						XSSFCell cellC = rowValue2.createCell(2);
						cellC.setCellStyle(styleValue);
						XSSFCell cellD = rowValue2.createCell(3);
						cellD.setCellStyle(styleValue);
						cellC.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Name")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellE = rowValue2.createCell(4);
						cellE.setCellStyle(styleValue);
						XSSFCell cellF = rowValue2.createCell(5);
						cellF.setCellStyle(styleValue);
						XSSFCell cellG = rowValue2.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = rowValue2.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = rowValue2.createCell(8);
						cellI.setCellStyle(styleValue);
						cellE.setCellValue(new XSSFRichTextString(workElement2.getAttribute("Descriptions")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					}

					if (rowSequence == 0) {
						currentRowNumber++;
						XSSFRow rowBlank = sheet.createRow(currentRowNumber);
						XSSFCell cellA8 = rowBlank.createCell(0);
						cellA8.setCellStyle(styleValueNumber);
						XSSFCell cellB8 = rowBlank.createCell(1);
						cellB8.setCellStyle(styleValue);
						cellB8.setCellValue(new XSSFRichTextString("(None)"));
						XSSFCell cellC8 = rowBlank.createCell(2);
						cellC8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellD8 = rowBlank.createCell(3);
						cellD8.setCellStyle(styleValue);
						XSSFCell cellE8 = rowBlank.createCell(4);
						cellE8.setCellStyle(styleValue);
						XSSFCell cellF8 = rowBlank.createCell(5);
						cellF8.setCellStyle(styleValue);
						XSSFCell cellG8 = rowBlank.createCell(6);
						cellG8.setCellStyle(styleValue);
						XSSFCell cellH8 = rowBlank.createCell(7);
						cellH8.setCellStyle(styleValue);
						XSSFCell cellI8 = rowBlank.createCell(8);
						cellI8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					}
				}
			}
			if (elementArray1[i].getType().equals("IOTable")) {
				for (int j = 0; j < tableList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableList.item(j);
					if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TableID"))) {
						NodeList ioFieldList = workElement1.getElementsByTagName("IOTableField");

						currentRowNumber++;
						XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
						XSSFCell cellA7 = rowColumnHeading.createCell(0);
						cellA7.setCellStyle(styleHeader2Number);
						cellA7.setCellValue(new XSSFRichTextString("No."));
						XSSFCell cellB7 = rowColumnHeading.createCell(1);
						cellB7.setCellStyle(styleHeader2);
						XSSFCell cellC7 = rowColumnHeading.createCell(2);
						cellC7.setCellStyle(styleHeader2);
						cellB7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments48")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
						XSSFCell cellD7 = rowColumnHeading.createCell(3);
						cellD7.setCellStyle(styleHeader2);
						XSSFCell cellE7 = rowColumnHeading.createCell(4);
						cellE7.setCellStyle(styleHeader2);
						cellD7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments49")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
						XSSFCell cellF7 = rowColumnHeading.createCell(5);
						cellF7.setCellStyle(styleHeader2);
						XSSFCell cellG7 = rowColumnHeading.createCell(6);
						cellG7.setCellStyle(styleHeader2);
						XSSFCell cellH7 = rowColumnHeading.createCell(7);
						cellH7.setCellStyle(styleHeader2);
						XSSFCell cellI7 = rowColumnHeading.createCell(8);
						cellI7.setCellStyle(styleHeader2);
						cellF7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments50")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

						NodeList primaryKeyFieldList = null;
						workList3 = workElement2.getElementsByTagName("TableKey");
						for (int m = 0; m < workList3.getLength(); m++) {
							workElement3 = (org.w3c.dom.Element)workList3.item(m);
							if (workElement3.getAttribute("Type").equals("PK")) {
								primaryKeyFieldList = workElement3.getElementsByTagName("TableKeyField");
								break;
							}
						}

						rowSequence = 0;
						countOfElementArray2 = -1;
						workList2 = workElement2.getElementsByTagName("TableField");
						for (int k = 0; k < workList2.getLength(); k++) {
							countOfElementArray2++;
							node = new XeadNode("TableField",(org.w3c.dom.Element)workList2.item(k));
							elementArray2[countOfElementArray2] = node;
						}
						if (countOfElementArray2 > 0) {
							Arrays.sort(elementArray2, 0, countOfElementArray2 + 1);
						}
						for (int k = 0; k <= countOfElementArray2; k++) {
							workElement3 = (org.w3c.dom.Element)elementArray2[k].getElement();
							currentRowNumber++;
							XSSFRow rowValue2 = sheet.createRow(currentRowNumber);
							XSSFCell cellA = rowValue2.createCell(0);
							cellA.setCellStyle(styleValueNumber);
							rowSequence++;
							cellA.setCellValue(rowSequence);
							XSSFCell cellB = rowValue2.createCell(1);
							cellB.setCellStyle(styleValue);
							XSSFCell cellC = rowValue2.createCell(2);
							cellC.setCellStyle(styleValue);
							workString1 = "";
							boolean primaryKeyField = false;
							for (int m = 0; m < primaryKeyFieldList.getLength(); m++) {
								workElement4 = (org.w3c.dom.Element)primaryKeyFieldList.item(m);
								if (workElement4.getAttribute("FieldID").equals(workElement3.getAttribute("ID"))) {
									primaryKeyField = true;
									break;
								}
							}
							if (workElement3.getAttribute("Alias").equals("")) {
								if (primaryKeyField) {
									workString1 = "*" + workElement3.getAttribute("Name");
								} else {
									workString1 = workElement3.getAttribute("Name");
								}
							} else {
								if (primaryKeyField) {
									workString1 = "*" + workElement3.getAttribute("Alias") + " / " + workElement3.getAttribute("Name");
								} else {
									workString1 = workElement3.getAttribute("Alias") + " / " + workElement3.getAttribute("Name");
								}
							}
							cellB.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
							XSSFCell cellD = rowValue2.createCell(3);
							cellD.setCellStyle(styleValue);
							XSSFCell cellE = rowValue2.createCell(4);
							cellE.setCellStyle(styleValue);
							workString1 = "";
							for (int m = 0; m < dataTypeList.getLength(); m++) {
								workElement4 = (org.w3c.dom.Element)dataTypeList.item(m);
								if (workElement4.getAttribute("ID").equals(workElement3.getAttribute("DataTypeID"))) {
									if (workElement4.getAttribute("Decimal").equals("") || workElement4.getAttribute("Decimal").equals("0")) {
										workString1 = workElement4.getAttribute("Name") + "("  + workElement4.getAttribute("Length") + ")";
									} else {
										workString1 = workElement4.getAttribute("Name") + "(" + workElement4.getAttribute("Length") + "."
										+ workElement4.getAttribute("Decimal") + ")";
									}
									break;
								}
							}
							cellD.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
							XSSFCell cellF = rowValue2.createCell(5);
							cellF.setCellStyle(styleValue);
							XSSFCell cellG = rowValue2.createCell(6);
							cellG.setCellStyle(styleValue);
							XSSFCell cellH = rowValue2.createCell(7);
							cellH.setCellStyle(styleValue);
							XSSFCell cellI = rowValue2.createCell(8);
							cellI.setCellStyle(styleValue);
							workString1 = "";
							for (int m = 0; m < ioFieldList.getLength(); m++) {
								workElement4 = (org.w3c.dom.Element)ioFieldList.item(m);
								if (workElement4.getAttribute("FieldID").equals(workElement3.getAttribute("ID"))) {
									workString1 = workElement4.getAttribute("Descriptions");
									break;
								}
							}
							cellF.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
						}
						break;
					}
				}
			}
		}
	}

	void setupTableSummary(XSSFSheet sheet, org.w3c.dom.Element element) {
		org.w3c.dom.Element workElement1 = null;
		String workString = "";

		XSSFRow row0 = sheet.createRow(0);
		XSSFRow row1 = sheet.createRow(1);
		XSSFRow row2 = sheet.createRow(2);
		XSSFRow row3 = sheet.createRow(3);
		XSSFRow row4 = sheet.createRow(4);
		XSSFRow row5 = sheet.createRow(5);

		//Title
		XSSFRichTextString title = new XSSFRichTextString(res.getString("DialogDocuments51"));
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleTitle);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleTitle);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleTitle);
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleTitle);
		cellA0.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

		//Label Subsystem
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader2);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader2);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader2);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader2);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader2);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader2);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader2);
		cellC0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));

		//Value Subsystem
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleValue);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleValue);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleValue);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleValue);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleValue);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleValue);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleValue);
		cellC1.setCellValue(new XSSFRichTextString(subsystemElement.getAttribute("SortKey") + " / " + subsystemElement.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));

		//Label Table Name
		XSSFCell cellA2 = row2.createCell(0);
		cellA2.setCellStyle(styleHeader2);
		XSSFCell cellB2 = row2.createCell(1);
		cellB2.setCellStyle(styleHeader2);
		XSSFCell cellC2 = row2.createCell(2);
		cellC2.setCellStyle(styleHeader2);
		XSSFCell cellD2 = row2.createCell(3);
		cellD2.setCellStyle(styleHeader2);
		XSSFCell cellE2 = row2.createCell(4);
		cellE2.setCellStyle(styleHeader2);
		cellA2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments52")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));

		//Value Table Name
		XSSFCell cellA3 = row3.createCell(0);
		cellA3.setCellStyle(styleValue);
		XSSFCell cellB3 = row3.createCell(1);
		cellB3.setCellStyle(styleValue);
		XSSFCell cellC3 = row3.createCell(2);
		cellC3.setCellStyle(styleValue);
		XSSFCell cellD3 = row3.createCell(3);
		cellD3.setCellStyle(styleValue);
		XSSFCell cellE3 = row3.createCell(4);
		cellE3.setCellStyle(styleValue);
		if (element.getAttribute("SortKey").equals("")) {
			cellA3.setCellValue(new XSSFRichTextString(element.getAttribute("Name")));
		} else {
			if (element.getAttribute("Alias").equals("")) {
				cellA3.setCellValue(new XSSFRichTextString(element.getAttribute("SortKey") + " / " + element.getAttribute("Name")));
			} else {
				cellA3.setCellValue(new XSSFRichTextString(element.getAttribute("SortKey") + " / " + element.getAttribute("Name") + "(" + element.getAttribute("Alias") + ")"));
			}
		}
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));

		//Label TableType
		XSSFCell cellF2 = row2.createCell(5);
		cellF2.setCellStyle(styleHeader2);
		XSSFCell cellG2 = row2.createCell(6);
		cellG2.setCellStyle(styleHeader2);
		XSSFCell cellH2 = row2.createCell(7);
		cellH2.setCellStyle(styleHeader2);
		XSSFCell cellI2 = row2.createCell(8);
		cellI2.setCellStyle(styleHeader2);
		cellF2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments53")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));

		//Value TableType
		XSSFCell cellF3 = row3.createCell(5);
		cellF3.setCellStyle(styleValue);
		XSSFCell cellG3 = row3.createCell(6);
		cellG3.setCellStyle(styleValue);
		XSSFCell cellH3 = row3.createCell(7);
		cellH3.setCellStyle(styleValue);
		XSSFCell cellI3 = row3.createCell(8);
		cellI3.setCellStyle(styleValue);
		for (int i = 0; i < tableTypeList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)tableTypeList.item(i);
			if (workElement1.getAttribute("ID").equals(element.getAttribute("TableTypeID"))) {
				workString = workElement1.getAttribute("Name");
				break;
			}
		}
		cellF3.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));

		//Label Descriptions
		XSSFCell cellA4 = row4.createCell(0);
		cellA4.setCellStyle(styleHeader2);
		XSSFCell cellB4 = row4.createCell(1);
		cellB4.setCellStyle(styleHeader2);
		XSSFCell cellC4 = row4.createCell(2);
		cellC4.setCellStyle(styleHeader2);
		XSSFCell cellD4 = row4.createCell(3);
		cellD4.setCellStyle(styleHeader2);
		XSSFCell cellE4 = row4.createCell(4);
		cellE4.setCellStyle(styleHeader2);
		XSSFCell cellF4 = row4.createCell(5);
		cellF4.setCellStyle(styleHeader2);
		XSSFCell cellG4 = row4.createCell(6);
		cellG4.setCellStyle(styleHeader2);
		XSSFCell cellH4 = row4.createCell(7);
		cellH4.setCellStyle(styleHeader2);
		XSSFCell cellI4 = row4.createCell(8);
		cellI4.setCellStyle(styleHeader2);
		cellA4.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

		//Value Descriptions
		XSSFCell cellA5 = row5.createCell(0);
		cellA5.setCellStyle(styleValue);
		XSSFCell cellB5 = row5.createCell(1);
		cellB5.setCellStyle(styleValue);
		XSSFCell cellC5 = row5.createCell(2);
		cellC5.setCellStyle(styleValue);
		XSSFCell cellD5 = row5.createCell(3);
		cellD5.setCellStyle(styleValue);
		XSSFCell cellE5 = row5.createCell(4);
		cellE5.setCellStyle(styleValue);
		XSSFCell cellF5 = row5.createCell(5);
		cellF5.setCellStyle(styleValue);
		XSSFCell cellG5 = row5.createCell(6);
		cellG5.setCellStyle(styleValue);
		XSSFCell cellH5 = row5.createCell(7);
		cellH5.setCellStyle(styleValue);
		XSSFCell cellI5 = row5.createCell(8);
		cellI5.setCellStyle(styleValue);
		workString = substringLinesWithTokenOfEOL(element.getAttribute("Descriptions"), "\n");
		cellA5.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));
		row5.setHeight((short)1000);

		currentRowNumber = 5;
	}

	void setupTableFieldList(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		String workString1 = "";
		int rowSequence = 0;
		XeadNode node = null;

		//Label FieldList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments54")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FieldList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments55")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments56")));
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString("Null"));
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments57")));
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellG1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

		//Rows of TableField
		countOfElementArray1 = -1;
		workList = element.getElementsByTagName("TableField");
		for (int i = 0; i < workList.getLength(); i++) {
			countOfElementArray1++;
			node = new XeadNode("TableField",(org.w3c.dom.Element)workList.item(i));
			elementArray1[countOfElementArray1] = node;
		}
		if (countOfElementArray1 > 0) {
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
		}
		for (int i = 0; i <= countOfElementArray1; i++) {
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			XSSFCell cellB = nextRow.createCell(1);
			if (isPrimaryKeyField(element, workElement1)) {
				cellB.setCellStyle(styleValueStrong);
			} else {
				cellB.setCellStyle(styleValue);
			}
			if (workElement1.getAttribute("Alias").equals("")) {
				if (workElement1.getAttribute("AttributeType").equals("NATIVE")) {
					workString1 = workElement1.getAttribute("Name");
				} else {
					workString1 = "(" + workElement1.getAttribute("Name") + ")";
				}
			} else {
				if (workElement1.getAttribute("AttributeType").equals("NATIVE")) {
					workString1 = workElement1.getAttribute("Alias") + " / " + workElement1.getAttribute("Name");
				} else {
					workString1 = "(" + workElement1.getAttribute("Alias") + " / " + workElement1.getAttribute("Name") + ")";
				}
			}
			cellB.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			workString1 = "";
			for (int m = 0; m < dataTypeList.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)dataTypeList.item(m);
				if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("DataTypeID"))) {
//					if (workElement2.getAttribute("Decimal").equals("") || workElement2.getAttribute("Decimal").equals("0")) {
//						workString1 = workElement2.getAttribute("Name") + "("  + workElement2.getAttribute("Length") + ")";
//					} else {
//						workString1 = workElement2.getAttribute("Name") + "(" + workElement2.getAttribute("Length") + "."
//						+ workElement2.getAttribute("Decimal") + ")";
//					}
					workString1 = workElement2.getAttribute("Name") + " "  + workElement2.getAttribute("SQLExpression");
					break;
				}
			}
			cellD.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			if (workElement1.getAttribute("NotNull").equals("true")) {
				workString1 = res.getString("DialogDocuments58");
			} else {
				workString1 = res.getString("DialogDocuments59");
			}
			cellE.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			cellF.setCellValue(new XSSFRichTextString(workElement1.getAttribute("Default")));
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			workString1 = substringLinesWithTokenOfEOL(workElement1.getAttribute("Descriptions"), "\n");
			cellG.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	boolean isPrimaryKeyField(org.w3c.dom.Element tableElement, org.w3c.dom.Element fieldElement) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList workList1, workList2;
		boolean isKey = false;

		workList1 = tableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < workList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList1.item(i);
			if (workElement1.getAttribute("Type").equals("PK")) {
				workList2 = workElement1.getElementsByTagName("TableKeyField");
				for (int j = 0; j < workList2.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)workList2.item(j);
					if (workElement2.getAttribute("FieldID").equals(fieldElement.getAttribute("ID"))) {
						isKey = true;
						break;
					}
				}
				break;
			}
		}

		return isKey;
	}
	void setupTableKeyList(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		NodeList workList2 = null;
		NodeList fieldList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		String workString1 = "";
		int rowSequence = 0;
		XeadNode node = null;

		//Label KeyList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments60")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header KeyList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments61")));
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellC1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments62")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));

		//Rows of Key
		fieldList = element.getElementsByTagName("TableField");
		workList1 = element.getElementsByTagName("TableKey");
		for (int i = 0; i < workList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList1.item(i);
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			XSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			if (workElement1.getAttribute("Type").equals("PK")) {
				workString1 = res.getString("DialogDocuments63");
			}
			if (workElement1.getAttribute("Type").equals("FK")) {
				workString1 = res.getString("DialogDocuments64");
			}
			if (workElement1.getAttribute("Type").equals("SK")) {
				workString1 = res.getString("DialogDocuments65");
			}
			if (workElement1.getAttribute("Type").equals("XK")) {
				workString1 = res.getString("DialogDocuments74");
			}
			cellB.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			workString1 = "";
			countOfElementArray1 = -1;
			workList2 = workElement1.getElementsByTagName("TableKeyField");
			for (int j = 0; j < workList2.getLength(); j++) {
				countOfElementArray1++;
				node = new XeadNode("TableKeyField",(org.w3c.dom.Element)workList2.item(j));
				elementArray1[countOfElementArray1] = node;
			}
			if (countOfElementArray1 > 0) {
				Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
			}
			for (int j = 0; j <= countOfElementArray1; j++) {
				workElement2 = (org.w3c.dom.Element)elementArray1[j].getElement();
				for (int k = 0; k < fieldList.getLength(); k++) {
					workElement3 = (org.w3c.dom.Element)fieldList.item(k);
					if (workElement2.getAttribute("FieldID").equals(workElement3.getAttribute("ID"))) {
						if (workString1.equals("")) {
							workString1 = workElement3.getAttribute("Name");
						} else {
							if (workElement1.getAttribute("Type").equals("XK")) {
								workString1 = workString1 + " > " + workElement3.getAttribute("Name");
								if (workElement2.getAttribute("AscDesc").equals("D")) {
									workString1 = workString1 + "(D)";
								}
							} else {
								workString1 = workString1 + " + " + workElement3.getAttribute("Name");
							}
						}
						break;
					}
				}
			}
			if (workString1.equals("")) {
				workString1 = "*None";
			}
			cellC.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));
		}
	}

	void setupTableUsageList(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		String workString1 = "";
		int rowSequence = 0;
		XeadNode node = null;

		//Label FieldList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments66")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FunctionList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellI1.setCellValue(new XSSFRichTextString("CRUD"));

		//Rows of Function
		countOfElementArray1 = -1;
		for (int i = 0; i < ioTableList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)ioTableList.item(i);
			if (workElement1.getAttribute("TableID").equals(element.getAttribute("ID"))) {
				countOfElementArray1++;
				node = new XeadNode("Function",(org.w3c.dom.Element)workElement1.getParentNode());
				elementArray1[countOfElementArray1] = node;
			}
		}
		if (countOfElementArray1 > 0) {
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
		}
		for (int i = 0; i <= countOfElementArray1; i++) {
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			workList1 = workElement1.getElementsByTagName("IOTable");
			for (int j = 0; j < workList1.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)workList1.item(j);
				if (workElement2.getAttribute("TableID").equals(element.getAttribute("ID"))) {
					currentRowNumber++;
					XSSFRow nextRow = sheet.createRow(currentRowNumber);
					XSSFCell cellA = nextRow.createCell(0);
					cellA.setCellStyle(styleValueNumber);
					rowSequence++;
					cellA.setCellValue(rowSequence);
					XSSFCell cellB = nextRow.createCell(1);
					cellB.setCellStyle(styleValue);
					XSSFCell cellC = nextRow.createCell(2);
					cellC.setCellStyle(styleValue);
					workString1 = "";
					for (int k = 0; k < subsystemList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)subsystemList.item(k);
						if (workElement1.getAttribute("SubsystemID").equals(workElement3.getAttribute("ID"))) {
							workString1 = workElement3.getAttribute("Name");
							break;
						}
					}
					cellB.setCellValue(new XSSFRichTextString(workString1));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
					XSSFCell cellD = nextRow.createCell(3);
					cellD.setCellStyle(styleValue);
					XSSFCell cellE = nextRow.createCell(4);
					cellE.setCellStyle(styleValue);
					XSSFCell cellF = nextRow.createCell(5);
					cellF.setCellStyle(styleValue);
					XSSFCell cellG = nextRow.createCell(6);
					cellG.setCellStyle(styleValue);
					XSSFCell cellH = nextRow.createCell(7);
					cellH.setCellStyle(styleValue);
					cellD.setCellValue(new XSSFRichTextString(workElement1.getAttribute("SortKey") + " / " + workElement1.getAttribute("Name")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
					XSSFCell cellI = nextRow.createCell(8);
					cellI.setCellStyle(styleValue);
					workString1 = "";
					if (workElement2.getAttribute("OpC").equals("+")) {
						workString1 = "C";
					}
					if (workElement2.getAttribute("OpR").equals("+")) {
						workString1 = workString1 + "R";
					}
					if (workElement2.getAttribute("OpU").equals("+")) {
						workString1 = workString1 + "U";
					}
					if (workElement2.getAttribute("OpD").equals("+")) {
						workString1 = workString1 + "D";
					}
					cellI.setCellValue(new XSSFRichTextString(workString1));
				}
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
		}
	}

	void setupTableKeyDetails(XSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		NodeList workList2 = null;
		NodeList workList3 = null;
		NodeList fieldList1 = null;
		NodeList fieldList2 = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		org.w3c.dom.Element workElement4 = null;
		org.w3c.dom.Element workElement5 = null;
		org.w3c.dom.Element relationshipTableElement = null;
		String workString = "";
		String relationshipKeyID = "";
		int rowSequence = 0;
		int sectionNumber = 0;
		XeadNode node = null;
		//
		fieldList1 = element.getElementsByTagName("TableField");
		workList1 = element.getElementsByTagName("TableKey");
		int countOfKeys = 0;
		for (int i = 0; i < workList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList1.item(i);
			if (!workElement1.getAttribute("Type").equals("XK")) {
				countOfKeys++;
			}
		}
		for (int i = 0; i < workList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList1.item(i);
			if (!workElement1.getAttribute("Type").equals("XK")) {
				rowSequence = 0;

				//Label Key Definition
				currentRowNumber++;
				XSSFRow row0 = sheet.createRow(currentRowNumber);
				XSSFCell cellA0 = row0.createCell(0);
				cellA0.setCellStyle(styleHeader1);
				XSSFCell cellB0 = row0.createCell(1);
				cellB0.setCellStyle(styleHeader1);
				XSSFCell cellC0 = row0.createCell(2);
				cellC0.setCellStyle(styleHeader1);
				XSSFCell cellD0 = row0.createCell(3);
				cellD0.setCellStyle(styleHeader1);
				XSSFCell cellE0 = row0.createCell(4);
				cellE0.setCellStyle(styleHeader1);
				XSSFCell cellF0 = row0.createCell(5);
				cellF0.setCellStyle(styleHeader1);
				XSSFCell cellG0 = row0.createCell(6);
				cellG0.setCellStyle(styleHeader1);
				XSSFCell cellH0 = row0.createCell(7);
				cellH0.setCellStyle(styleHeader1);
				XSSFCell cellI0 = row0.createCell(8);
				cellI0.setCellStyle(styleHeader1);
				sectionNumber++;
				cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments67") + "(" + sectionNumber + "/" + countOfKeys + ")"));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

				//Header Key Definition
				currentRowNumber++;
				XSSFRow row1 = sheet.createRow(currentRowNumber);
				XSSFCell cellA1 = row1.createCell(0);
				cellA1.setCellStyle(styleHeader2);
				XSSFCell cellB1 = row1.createCell(1);
				cellB1.setCellStyle(styleHeader2);
				cellA1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments61")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 1));
				XSSFCell cellC1 = row1.createCell(2);
				cellC1.setCellStyle(styleHeader2);
				XSSFCell cellD1 = row1.createCell(3);
				cellD1.setCellStyle(styleHeader2);
				XSSFCell cellE1 = row1.createCell(4);
				cellE1.setCellStyle(styleHeader2);
				XSSFCell cellF1 = row1.createCell(5);
				cellF1.setCellStyle(styleHeader2);
				XSSFCell cellG1 = row1.createCell(6);
				cellG1.setCellStyle(styleHeader2);
				XSSFCell cellH1 = row1.createCell(7);
				cellH1.setCellStyle(styleHeader2);
				XSSFCell cellI1 = row1.createCell(8);
				cellI1.setCellStyle(styleHeader2);
				cellC1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments62")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));

				currentRowNumber++;
				XSSFRow next2 = sheet.createRow(currentRowNumber);
				XSSFCell cellA2 = next2.createCell(0);
				cellA2.setCellStyle(styleValue);
				XSSFCell cellB2 = next2.createCell(1);
				cellB2.setCellStyle(styleValue);
				if (workElement1.getAttribute("Type").equals("PK")) {
					workString = res.getString("DialogDocuments63");
				}
				if (workElement1.getAttribute("Type").equals("FK")) {
					workString = res.getString("DialogDocuments64");
				}
				if (workElement1.getAttribute("Type").equals("SK")) {
					workString = res.getString("DialogDocuments65");
				}
				cellA2.setCellValue(new XSSFRichTextString(workString));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 1));
				XSSFCell cellC2 = next2.createCell(2);
				cellC2.setCellStyle(styleValue);
				XSSFCell cellD2 = next2.createCell(3);
				cellD2.setCellStyle(styleValue);
				XSSFCell cellE2 = next2.createCell(4);
				cellE2.setCellStyle(styleValue);
				XSSFCell cellF2 = next2.createCell(5);
				cellF2.setCellStyle(styleValue);
				XSSFCell cellG2 = next2.createCell(6);
				cellG2.setCellStyle(styleValue);
				XSSFCell cellH2 = next2.createCell(7);
				cellH2.setCellStyle(styleValue);
				XSSFCell cellI2 = next2.createCell(8);
				cellI2.setCellStyle(styleValue);
				workString = "";
				countOfElementArray1 = -1;
				workList2 = workElement1.getElementsByTagName("TableKeyField");
				for (int j = 0; j < workList2.getLength(); j++) {
					countOfElementArray1++;
					node = new XeadNode("TableKeyField",(org.w3c.dom.Element)workList2.item(j));
					elementArray1[countOfElementArray1] = node;
				}
				if (countOfElementArray1 > 0) {
					Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
				}
				for (int j = 0; j <= countOfElementArray1; j++) {
					workElement2 = (org.w3c.dom.Element)elementArray1[j].getElement();
					for (int k = 0; k < fieldList1.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)fieldList1.item(k);
						if (workElement2.getAttribute("FieldID").equals(workElement3.getAttribute("ID"))) {
							if (workString.equals("")) {
								workString = workElement3.getAttribute("Name");
							} else {
								workString = workString + " + " + workElement3.getAttribute("Name");
							}
							break;
						}
					}
				}
				if (workString.equals("")) {
					workString = "*None";
				}
				cellC2.setCellValue(new XSSFRichTextString(workString));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));

				//Header RelationshipList
				currentRowNumber++;
				XSSFRow row3 = sheet.createRow(currentRowNumber);
				XSSFCell cellA3 = row3.createCell(0);
				cellA3.setCellStyle(styleHeader2Number);
				cellA3.setCellValue(new XSSFRichTextString("No."));
				XSSFCell cellB3 = row3.createCell(1);
				cellB3.setCellStyle(styleHeader2);
				cellB3.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments68")));
				XSSFCell cellC3 = row3.createCell(2);
				cellC3.setCellStyle(styleHeader2);
				XSSFCell cellD3 = row3.createCell(3);
				cellD3.setCellStyle(styleHeader2);
				XSSFCell cellE3 = row3.createCell(4);
				cellE3.setCellStyle(styleHeader2);
				cellC3.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments69")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 4));
				XSSFCell cellF3 = row3.createCell(5);
				cellF3.setCellStyle(styleHeader2);
				XSSFCell cellG3 = row3.createCell(6);
				cellG3.setCellStyle(styleHeader2);
				XSSFCell cellH3 = row3.createCell(7);
				cellH3.setCellStyle(styleHeader2);
				XSSFCell cellI3 = row3.createCell(8);
				cellI3.setCellStyle(styleHeader2);
				cellF3.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments70")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

				for (int j = 0; j < relationshipList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)relationshipList.item(j);
					if ((workElement2.getAttribute("Table1ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey1ID").equals(workElement1.getAttribute("ID"))))
							|| (workElement2.getAttribute("Table2ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey2ID").equals(workElement1.getAttribute("ID")))) ) {
						currentRowNumber++;
						XSSFRow nextRow = sheet.createRow(currentRowNumber);
						XSSFCell cellA = nextRow.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = nextRow.createCell(1);
						cellB.setCellStyle(styleValue);
						workString = "";
						if (workElement2.getAttribute("Type").equals("FAMILY")) {
							workString = res.getString("DialogDocuments71");
						}
						if (workElement2.getAttribute("Type").equals("REFFER")) {
							workString = res.getString("DialogDocuments72");
						}
						if (workElement2.getAttribute("Type").equals("SUBTYPE")) {
							workString = res.getString("DialogDocuments73");
						}
						cellB.setCellValue(new XSSFRichTextString(workString));
						XSSFCell cellC = nextRow.createCell(2);
						cellC.setCellStyle(styleValue);
						XSSFCell cellD = nextRow.createCell(3);
						cellD.setCellStyle(styleValue);
						XSSFCell cellE = nextRow.createCell(4);
						cellE.setCellStyle(styleValue);
						workString = "";
						for (int m = 0; m < tableList.getLength(); m++) {
							workElement3 = (org.w3c.dom.Element)tableList.item(m);
							if (workElement2.getAttribute("Table1ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey1ID").equals(workElement1.getAttribute("ID")))) {
								if (workElement3.getAttribute("ID").equals(workElement2.getAttribute("Table2ID"))) {
									workString = workElement3.getAttribute("SortKey") + " / " + workElement3.getAttribute("Name");
									relationshipTableElement = workElement3;
									break;
								}
							}
							if (workElement2.getAttribute("Table2ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey2ID").equals(workElement1.getAttribute("ID")))) {
								if (workElement3.getAttribute("ID").equals(workElement2.getAttribute("Table1ID"))) {
									workString = workElement3.getAttribute("SortKey") + " / " + workElement3.getAttribute("Name");
									relationshipTableElement = workElement3;
									break;
								}
							}
						}
						cellC.setCellValue(new XSSFRichTextString(workString));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 4));
						XSSFCell cellF = nextRow.createCell(5);
						cellF.setCellStyle(styleValue);
						XSSFCell cellG = nextRow.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = nextRow.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = nextRow.createCell(8);
						cellI.setCellStyle(styleValue);
						workString = "";
						if (workElement2.getAttribute("Table1ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey1ID").equals(workElement1.getAttribute("ID")))) {
							relationshipKeyID = workElement2.getAttribute("TableKey2ID");
						}
						if (workElement2.getAttribute("Table2ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey2ID").equals(workElement1.getAttribute("ID")))) {
							relationshipKeyID = workElement2.getAttribute("TableKey1ID");
						}
						countOfElementArray1 = -1;
						fieldList2 = relationshipTableElement.getElementsByTagName("TableField");
						workList2 = relationshipTableElement.getElementsByTagName("TableKey");
						for (int m = 0; m < workList2.getLength(); m++) {
							workElement3 = (org.w3c.dom.Element)workList2.item(m);
							if (workElement3.getAttribute("ID").equals(relationshipKeyID)) {
								workList3 = workElement3.getElementsByTagName("TableKeyField");
								countOfElementArray1 = -1;
								for (int n = 0; n < workList3.getLength(); n++) {
									countOfElementArray1++;
									node = new XeadNode("TableKeyField",(org.w3c.dom.Element)workList3.item(n));
									elementArray1[countOfElementArray1] = node;
								}
								if (countOfElementArray1 > 0) {
									Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
								}
								for (int n = 0; n <= countOfElementArray1; n++) {
									workElement4 = (org.w3c.dom.Element)elementArray1[n].getElement();
									for (int p = 0; p < fieldList2.getLength(); p++) {
										workElement5 = (org.w3c.dom.Element)fieldList2.item(p);
										if (workElement4.getAttribute("FieldID").equals(workElement5.getAttribute("ID"))) {
											if (workString.equals("")) {
												workString = "[" + workElement3.getAttribute("Type") + "] " + workElement5.getAttribute("Name");
											} else {
												workString = workString + " + " + workElement5.getAttribute("Name");
											}
											break;
										}
									}
								}
								break;
							}
						}
						cellF.setCellValue(new XSSFRichTextString(workString));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
					}
				}
			}

			if (rowSequence == 0) {
				currentRowNumber++;
				XSSFRow row4 = sheet.createRow(currentRowNumber);
				XSSFCell cellA4 = row4.createCell(0);
				cellA4.setCellStyle(styleValueNumber);
				XSSFCell cellB4 = row4.createCell(1);
				cellB4.setCellStyle(styleValue);
				XSSFCell cellC4 = row4.createCell(2);
				cellC4.setCellStyle(styleValue);
				cellC4.setCellValue(new XSSFRichTextString("(None)"));
				XSSFCell cellD4 = row4.createCell(3);
				cellD4.setCellStyle(styleValue);
				XSSFCell cellE4 = row4.createCell(4);
				cellE4.setCellStyle(styleValue);
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 4));
				XSSFCell cellF4 = row4.createCell(5);
				cellF4.setCellStyle(styleValue);
				XSSFCell cellG4 = row4.createCell(6);
				cellG4.setCellStyle(styleValue);
				XSSFCell cellH4 = row4.createCell(7);
				cellH4.setCellStyle(styleValue);
				XSSFCell cellI4 = row4.createCell(8);
				cellI4.setCellStyle(styleValue);
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
			}
		}
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
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

	String getStringValueOfDateAndTime() {
		String returnValue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String monthStr = "";
		if (month < 10) {
			monthStr = "0" + Integer.toString(month);
		} else {
			monthStr = Integer.toString(month);
		}
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = "";
		if (day < 10) {
			dayStr = "0" + Integer.toString(day);
		} else {
			dayStr = Integer.toString(day);
		}
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String hourStr = "";
		if (hour < 10) {
			hourStr = "0" + Integer.toString(hour);
		} else {
			hourStr = Integer.toString(hour);
		}
		int minute = calendar.get(Calendar.MINUTE);
		String minStr = "";
		if (minute < 10) {
			minStr = "0" + Integer.toString(minute);
		} else {
			minStr = Integer.toString(minute);
		}
		int second = calendar.get(Calendar.SECOND);
		String secStr = "";
		if (second < 10) {
			secStr = "0" + Integer.toString(second);
		} else {
			secStr = Integer.toString(second);
		}
		returnValue = Integer.toString(year) + monthStr + dayStr + hourStr + minStr + secStr;
		return returnValue;
	}

	void jComboBoxSubsystems_actionPerformed(ActionEvent e) {
		org.w3c.dom.Element workElement = null;
		XeadNode workNode = null;
		String subsystemID = "";

		comboBoxModelTables.removeAllElements();
		comboBoxModelFunctions.removeAllElements();

		if (jComboBoxSubsystems.getSelectedIndex() > 0) {
			workNode = (XeadNode)comboBoxModelSubsystems.getSelectedItem();
			workElement = workNode.getElement();
			subsystemID = workElement.getAttribute("ID");

			for (int m = 0; m < tableList.getLength(); m++) {
				workElement = (org.w3c.dom.Element)tableList.item(m);
				if (workElement.getAttribute("SubsystemID").equals(subsystemID)) {
					workNode = new XeadNode("Table", (org.w3c.dom.Element)tableList.item(m));
					comboBoxModelTables.addElement((Object)workNode);
				}
			}
			comboBoxModelTables.sortElements();
			if (jRadioButtonTable.isSelected()) {
				jComboBoxTables.setEnabled(true);
				jComboBoxFunctions.setEnabled(false);
			}

			for (int m = 0; m < functionList.getLength(); m++) {
				workElement = (org.w3c.dom.Element)functionList.item(m);
				if (workElement.getAttribute("SubsystemID").equals(subsystemID)) {
					workNode = new XeadNode("Function",(org.w3c.dom.Element)functionList.item(m));
					comboBoxModelFunctions.addElement((Object)workNode);
				}
			}
			comboBoxModelFunctions.sortElements();
			if (jRadioButtonFunction.isSelected()) {
				jComboBoxTables.setEnabled(false);
				jComboBoxFunctions.setEnabled(true);
			}

			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}

		comboBoxModelTables.insertElementAt("(All Tables)", 0);
		comboBoxModelTables.setSelectedItem(comboBoxModelTables.getElementAt(0));
		comboBoxModelFunctions.insertElementAt("(All Functions)", 0);
		comboBoxModelFunctions.setSelectedItem(comboBoxModelFunctions.getElementAt(0));
	}

	void jRadioButtonTableFunction_stateChanged(ChangeEvent e) {
		if (jRadioButtonTable.isSelected()) {
			if (jComboBoxSubsystems.getSelectedIndex() > 0) {
				jComboBoxTables.setEnabled(true);
			}
			jComboBoxFunctions.setEnabled(false);
		} else {
			jComboBoxTables.setEnabled(false);
			if (jComboBoxSubsystems.getSelectedIndex() > 0) {
				jComboBoxFunctions.setEnabled(true);
			}
		}
	}

	class SortableXeadNodeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;
		public void sortElements() {
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
			String str = domNode_.getAttribute("SortKey") + " / " + domNode_.getAttribute("Name");
			return str;
		}
		public org.w3c.dom.Element getElement() {
			return domNode_;
		}
		public String getType() {
			return nodeType_;
		}
        public int compareTo(Object other) {
            XeadNode otherNode = (XeadNode)other;
            return domNode_.getAttribute("SortKey").compareTo(otherNode.getElement().getAttribute("SortKey"));
        }
	}
}

class DialogDocuments_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogDocuments adaptee;

	DialogDocuments_jButtonStart_actionAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogDocuments_jButtonCloseDialog_actionAdapter implements java.awt.event.ActionListener {
	DialogDocuments adaptee;

	DialogDocuments_jButtonCloseDialog_actionAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}

class DialogDocuments_jComboBoxSubsystems_actionAdapter implements java.awt.event.ActionListener {
	DialogDocuments adaptee;

	DialogDocuments_jComboBoxSubsystems_actionAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxSubsystems_actionPerformed(e);
	}
}

class DialogDocuments_jRadioButtonTable_changeAdapter  implements ChangeListener {
	DialogDocuments adaptee;
	DialogDocuments_jRadioButtonTable_changeAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jRadioButtonTableFunction_stateChanged(e);
	}
}

class DialogDocuments_jRadioButtonFunction_changeAdapter  implements ChangeListener {
	DialogDocuments adaptee;
	DialogDocuments_jRadioButtonFunction_changeAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jRadioButtonTableFunction_stateChanged(e);
	}
}
