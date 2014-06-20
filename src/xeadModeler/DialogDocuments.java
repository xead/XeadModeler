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
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.CellRangeAddress;

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
	//private File xlsFile = null;
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
	//
	private HSSFFont fontTitle = null;
	private HSSFFont fontHeader1 = null;
	private HSSFFont fontHeader2 = null;
	private HSSFFont fontValue = null;
	private HSSFFont fontImage = null;
	private HSSFFont fontImageUL = null;
	private HSSFWorkbook workBook = null;
	private HSSFCellStyle styleTitle = null;
	private HSSFCellStyle styleHeader1 = null;
	private HSSFCellStyle styleHeader2 = null;
	private HSSFCellStyle styleHeader2Number = null;
	private HSSFCellStyle styleValue = null;
	private HSSFCellStyle styleValueNumber = null;
	private HSSFCellStyle styleImage = null;
	private HSSFPatriarch patriarch = null;
	private HSSFClientAnchor anchor = null;
	protected HSSFPicture picture = null;

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
		//
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
		//
		jButtonStart.setBounds(new Rectangle(30, 7, 200, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogDocuments07"));
		jButtonStart.addActionListener(new DialogDocuments_jButtonStart_actionAdapter(this));
		jProgressBar.setBounds(new Rectangle(30, 7, 200, 27));
		jProgressBar.setVisible(false);
		jButtonCloseDialog.setBounds(new Rectangle(440, 7, 110, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogDocuments08"));
		jButtonCloseDialog.addActionListener(new DialogDocuments_jButtonCloseDialog_actionAdapter(this));
		jPanelSouth.add(jButtonCloseDialog, null);
		jPanelSouth.add(jButtonStart, null);
		jPanelSouth.add(jProgressBar, null);
		//
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
		//
		comboBoxModelSubsystems.removeAllElements();
		subsystemList = frame_.domDocument.getElementsByTagName("Subsystem");
		for (int i = 0; i < subsystemList.getLength(); i++) {
			node = new XeadNode("Subsystem",(org.w3c.dom.Element)subsystemList.item(i));
			comboBoxModelSubsystems.addElement((Object)node);
		}
		comboBoxModelSubsystems.sortElements();
		comboBoxModelSubsystems.insertElementAt(res.getString("DialogDocuments10"), 0);
		comboBoxModelSubsystems.setSelectedItem(comboBoxModelSubsystems.getElementAt(0));
		//
		taskList = frame_.domDocument.getElementsByTagName("Task");
		roleList = frame_.domDocument.getElementsByTagName("Role");
		tableList = frame_.domDocument.getElementsByTagName("Table");
		ioTableList = frame_.domDocument.getElementsByTagName("IOTable");
		relationshipList = frame_.domDocument.getElementsByTagName("Relationship");
		dataTypeList = frame_.domDocument.getElementsByTagName("DataType");
		tableTypeList = frame_.domDocument.getElementsByTagName("TableType");
		functionList = frame_.domDocument.getElementsByTagName("Function");
		functionTypeList = frame_.domDocument.getElementsByTagName("FunctionType");
		//
		super.setVisible(true);
		//
		return xlsFileName;
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		org.w3c.dom.Element workElement = null;
		XeadNode workNode = null;
		int countOfDefinitions = 0;
		//
		try{
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);
			subsystemID = "";
			countOfErrors = 0;
			//
			if (jComboBoxSubsystems.getSelectedIndex() > 0) {
				workNode = (XeadNode)comboBoxModelSubsystems.getSelectedItem();
				subsystemElement = workNode.getElement();
				subsystemID = subsystemElement.getAttribute("ID");
				//
				File file = new File(fileName_);
				//
				if (jRadioButtonTable.isSelected()) {
					if (jComboBoxTables.getSelectedIndex() > 0) {
						workNode = (XeadNode)comboBoxModelTables.getSelectedItem();
						workElement = workNode.getElement();
						tableID = workElement.getAttribute("ID");
					}
					//
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
						Arrays.sort(definitionArray, 0, countOfDefinitions, new NodeComparator());
					}
					xlsFileName = file.getParent() + File.separator + "TableDoc" + getStringValueOfDateAndTime() + ".xls";
					//xlsFileName = "C:" + file.separator + "Documents and Settings" + file.separator + "Administrator" + file.separator + "My Documents" + file.separator + "TableDoc" + getStringValueOfDateAndTime() + ".xls";
				}
				//
				if (jRadioButtonFunction.isSelected()) {
					if (jComboBoxFunctions.getSelectedIndex() > 0) {
						workNode = (XeadNode)comboBoxModelFunctions.getSelectedItem();
						workElement = workNode.getElement();
						functionID = workElement.getAttribute("ID");
					}
					//
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
						Arrays.sort(definitionArray, 0, countOfDefinitions, new NodeComparator());
					}
					xlsFileName = file.getParent() + File.separator + "FunctionDoc" + getStringValueOfDateAndTime() + ".xls";
					//xlsFileName = "C:" + file.separator + "Documents and Settings" + file.separator + "Administrator" + file.separator + "My Documents" + file.separator + "FunctionDoc" + getStringValueOfDateAndTime() + ".xls";
				}
				//
				jProgressBar.setValue(0);
				jProgressBar.setMaximum(countOfDefinitions);
				//
				FileOutputStream fileOut = new FileOutputStream(xlsFileName);
				createWorkBookAndStyles();
				//
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
				//
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
				//
				workBook.write(fileOut);
				fileOut.close();
			}
		} catch(Exception ex1) {
			ex1.printStackTrace();
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			//
			if (countOfErrors == 0) {
				JOptionPane.showMessageDialog(this.getContentPane(), countOfDefinitions + res.getString("DialogDocuments11") + "\n" + xlsFileName);
			} else {
				countOfDefinitions = countOfDefinitions - countOfErrors;
				JOptionPane.showMessageDialog(this.getContentPane(), countOfDefinitions + res.getString("DialogDocuments11") + "\n" + xlsFileName + "\n" + countOfErrors + res.getString("DialogDocuments12"));
			}
		}
	}

	void createWorkBookAndStyles() {
		workBook = new HSSFWorkbook();
		//
		fontTitle = workBook.createFont();
		fontTitle.setFontName(res.getString("DialogDocuments13"));
		fontTitle.setFontHeightInPoints((short)14);
		fontTitle.setColor(HSSFColor.WHITE.index);
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//
		fontHeader1 = workBook.createFont();
		fontHeader1.setFontName(res.getString("DialogDocuments13"));
		fontHeader1.setFontHeightInPoints((short)10);
		fontHeader1.setItalic(true);
		fontHeader1.setColor(HSSFColor.WHITE.index);
		//
		fontHeader2 = workBook.createFont();
		fontHeader2.setFontName(res.getString("DialogDocuments13"));
		fontHeader2.setFontHeightInPoints((short)10);
		//
		fontValue = workBook.createFont();
		fontValue.setFontName(res.getString("DialogDocuments14"));
		fontValue.setFontHeightInPoints((short)10);
		//
		fontImage = workBook.createFont();
		fontImage.setFontName(res.getString("DialogDocuments15"));
		fontImage.setFontHeightInPoints((short)6);
		//
		fontImageUL = workBook.createFont();
		fontImageUL.setFontName(res.getString("DialogDocuments15"));
		fontImageUL.setFontHeightInPoints((short)6);
		fontImageUL.setUnderline(HSSFFont.U_SINGLE);
		//
		styleTitle = workBook.createCellStyle();
		styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleTitle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleTitle.setFont(fontTitle);
		//
		styleHeader1 = workBook.createCellStyle();
		styleHeader1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeader1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader1.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
		styleHeader1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeader1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleHeader1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHeader1.setFont(fontHeader1);
		//
		styleHeader2 = workBook.createCellStyle();
		styleHeader2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeader2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		styleHeader2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeader2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleHeader2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHeader2.setFont(fontHeader2);
		//
		styleHeader2Number = workBook.createCellStyle();
		styleHeader2Number.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader2Number.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		styleHeader2Number.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeader2Number.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		styleHeader2Number.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleHeader2Number.setFont(fontHeader2);
		//
		styleValue = workBook.createCellStyle();
		styleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleValue.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleValue.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		styleValue.setFont(fontValue);
		styleValue.setWrapText(true);
		//
		styleValueNumber = workBook.createCellStyle();
		styleValueNumber.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleValueNumber.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleValueNumber.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		styleValueNumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		styleValueNumber.setFont(fontValue);
		//
		styleImage = workBook.createCellStyle();
		styleImage.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleImage.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleImage.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleImage.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleImage.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		styleImage.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		styleImage.setFont(fontImage);
		styleImage.setWrapText(true);
	}

	void generateTableDocument(org.w3c.dom.Element element) {
		//
		try {
			HSSFSheet sheet = workBook.createSheet("(" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name"));
			sheet.setDefaultRowHeight( (short) 300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 4100);
			HSSFFooter footer = sheet.getFooter();
			footer.setRight("(" + subsystemElement.getAttribute("SortKey") + ")" + subsystemElement.getAttribute("Name") + " - (" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name") + "  Page " + HSSFFooter.page() + " / " + HSSFFooter.numPages() );
			//
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
		//
		try {
			HSSFSheet sheet = workBook.createSheet("(" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name"));
			sheet.setDefaultRowHeight( (short) 300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 4100);
			HSSFFooter footer = sheet.getFooter();
			footer.setRight("(" + subsystemElement.getAttribute("SortKey") + ")" + subsystemElement.getAttribute("Name") + " - (" + element.getAttribute("SortKey") + ")" + element.getAttribute("Name") + "  Page " + HSSFFooter.page() + " / " + HSSFFooter.numPages() );
			patriarch = sheet.createDrawingPatriarch();
			//
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

	void setupFunctionSummary(HSSFSheet sheet, org.w3c.dom.Element element) {
		//NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		//org.w3c.dom.Element workElement2 = null;
		String workString = "";
		//
		HSSFRow row0 = sheet.createRow(0);
		HSSFRow row1 = sheet.createRow(1);
		HSSFRow row2 = sheet.createRow(2);
		HSSFRow row3 = sheet.createRow(3);
		HSSFRow row4 = sheet.createRow(4);
		HSSFRow row5 = sheet.createRow(5);
		HSSFRow row6 = sheet.createRow(6);
		HSSFRow row7 = sheet.createRow(7);
		HSSFRow row8 = sheet.createRow(8);
		HSSFRow row9 = sheet.createRow(9);
		//
		//Title
		HSSFRichTextString title = new HSSFRichTextString(res.getString("DialogDocuments16"));
		HSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleTitle);
		HSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleTitle);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleTitle);
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleTitle);
		cellA0.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
		//
		//Label Subsystem
		HSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader2);
		HSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader2);
		HSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader2);
		HSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader2);
		HSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader2);
		HSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader2);
		HSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader2);
		cellC0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));
		//
		//Value Subsystem
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleValue);
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleValue);
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleValue);
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleValue);
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleValue);
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleValue);
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleValue);
		cellC1.setCellValue(new HSSFRichTextString(subsystemElement.getAttribute("SortKey") + " / " + subsystemElement.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));
		//
		//Label Function
		HSSFCell cellA2 = row2.createCell(0);
		cellA2.setCellStyle(styleHeader2);
		HSSFCell cellB2 = row2.createCell(1);
		cellB2.setCellStyle(styleHeader2);
		HSSFCell cellC2 = row2.createCell(2);
		cellC2.setCellStyle(styleHeader2);
		HSSFCell cellD2 = row2.createCell(3);
		cellD2.setCellStyle(styleHeader2);
		HSSFCell cellE2 = row2.createCell(4);
		cellE2.setCellStyle(styleHeader2);
		cellA2.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
		//
		//Value Function
		HSSFCell cellA3 = row3.createCell(0);
		cellA3.setCellStyle(styleValue);
		HSSFCell cellB3 = row3.createCell(1);
		cellB3.setCellStyle(styleValue);
		HSSFCell cellC3 = row3.createCell(2);
		cellC3.setCellStyle(styleValue);
		HSSFCell cellD3 = row3.createCell(3);
		cellD3.setCellStyle(styleValue);
		HSSFCell cellE3 = row3.createCell(4);
		cellE3.setCellStyle(styleValue);
		cellA3.setCellValue(new HSSFRichTextString(element.getAttribute("SortKey") + " / " + element.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
		//
		//Label FunctionType
		HSSFCell cellF2 = row2.createCell(5);
		cellF2.setCellStyle(styleHeader2);
		HSSFCell cellG2 = row2.createCell(6);
		cellG2.setCellStyle(styleHeader2);
		HSSFCell cellH2 = row2.createCell(7);
		cellH2.setCellStyle(styleHeader2);
		HSSFCell cellI2 = row2.createCell(8);
		cellI2.setCellStyle(styleHeader2);
		cellF2.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments19")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));
		//
		//Value FunctionType
		HSSFCell cellF3 = row3.createCell(5);
		cellF3.setCellStyle(styleValue);
		HSSFCell cellG3 = row3.createCell(6);
		cellG3.setCellStyle(styleValue);
		HSSFCell cellH3 = row3.createCell(7);
		cellH3.setCellStyle(styleValue);
		HSSFCell cellI3 = row3.createCell(8);
		cellI3.setCellStyle(styleValue);
		for (int i = 0; i < functionTypeList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)functionTypeList.item(i);
			if (workElement1.getAttribute("ID").equals(element.getAttribute("FunctionTypeID"))) {
				workString = workElement1.getAttribute("SortKey") + " / " + workElement1.getAttribute("Name");
				break;
			}
		}
		cellF3.setCellValue(new HSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));
		//
		//Label Summary
		HSSFCell cellA4 = row4.createCell(0);
		cellA4.setCellStyle(styleHeader2);
		HSSFCell cellB4 = row4.createCell(1);
		cellB4.setCellStyle(styleHeader2);
		HSSFCell cellC4 = row4.createCell(2);
		cellC4.setCellStyle(styleHeader2);
		HSSFCell cellD4 = row4.createCell(3);
		cellD4.setCellStyle(styleHeader2);
		HSSFCell cellE4 = row4.createCell(4);
		cellE4.setCellStyle(styleHeader2);
		HSSFCell cellF4 = row4.createCell(5);
		cellF4.setCellStyle(styleHeader2);
		HSSFCell cellG4 = row4.createCell(6);
		cellG4.setCellStyle(styleHeader2);
		HSSFCell cellH4 = row4.createCell(7);
		cellH4.setCellStyle(styleHeader2);
		HSSFCell cellI4 = row4.createCell(8);
		cellI4.setCellStyle(styleHeader2);
		cellA4.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments20")));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));
		//
		//Value Summary
		HSSFCell cellA5 = row5.createCell(0);
		cellA5.setCellStyle(styleValue);
		HSSFCell cellB5 = row5.createCell(1);
		cellB5.setCellStyle(styleValue);
		HSSFCell cellC5 = row5.createCell(2);
		cellC5.setCellStyle(styleValue);
		HSSFCell cellD5 = row5.createCell(3);
		cellD5.setCellStyle(styleValue);
		HSSFCell cellE5 = row5.createCell(4);
		cellE5.setCellStyle(styleValue);
		HSSFCell cellF5 = row5.createCell(5);
		cellF5.setCellStyle(styleValue);
		HSSFCell cellG5 = row5.createCell(6);
		cellG5.setCellStyle(styleValue);
		HSSFCell cellH5 = row5.createCell(7);
		cellH5.setCellStyle(styleValue);
		HSSFCell cellI5 = row5.createCell(8);
		cellI5.setCellStyle(styleValue);
		cellA5.setCellValue(new HSSFRichTextString(element.getAttribute("Summary")));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));
		//
		//Label Parameters
		HSSFCell cellA6 = row6.createCell(0);
		cellA6.setCellStyle(styleHeader2);
		HSSFCell cellB6 = row6.createCell(1);
		cellB6.setCellStyle(styleHeader2);
		HSSFCell cellC6 = row6.createCell(2);
		cellC6.setCellStyle(styleHeader2);
		HSSFCell cellD6 = row6.createCell(3);
		cellD6.setCellStyle(styleHeader2);
		HSSFCell cellE6 = row6.createCell(4);
		cellE6.setCellStyle(styleHeader2);
		HSSFCell cellF6 = row6.createCell(5);
		cellF6.setCellStyle(styleHeader2);
		cellA6.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments21")));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
		//
		//Value Parameters
		HSSFCell cellA7 = row7.createCell(0);
		cellA7.setCellStyle(styleValue);
		HSSFCell cellB7 = row7.createCell(1);
		cellB7.setCellStyle(styleValue);
		HSSFCell cellC7 = row7.createCell(2);
		cellC7.setCellStyle(styleValue);
		HSSFCell cellD7 = row7.createCell(3);
		cellD7.setCellStyle(styleValue);
		HSSFCell cellE7 = row7.createCell(4);
		cellE7.setCellStyle(styleValue);
		HSSFCell cellF7 = row7.createCell(5);
		cellF7.setCellStyle(styleValue);
		cellA7.setCellValue(new HSSFRichTextString(element.getAttribute("Parameters")));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 5));
		//
		//Label Return
		HSSFCell cellG6 = row6.createCell(6);
		cellG6.setCellStyle(styleHeader2);
		HSSFCell cellH6 = row6.createCell(7);
		cellH6.setCellStyle(styleHeader2);
		HSSFCell cellI6 = row6.createCell(8);
		cellI6.setCellStyle(styleHeader2);
		cellG6.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments22")));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 8));
		//
		//Value Return
		HSSFCell cellG7 = row7.createCell(6);
		cellG7.setCellStyle(styleValue);
		HSSFCell cellH7 = row7.createCell(7);
		cellH7.setCellStyle(styleValue);
		HSSFCell cellI7 = row7.createCell(8);
		cellI7.setCellStyle(styleValue);
		cellG7.setCellValue(new HSSFRichTextString(element.getAttribute("Return")));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 8));
		//
		//Label Descriptions
		HSSFCell cellA8 = row8.createCell(0);
		cellA8.setCellStyle(styleHeader2);
		HSSFCell cellB8 = row8.createCell(1);
		cellB8.setCellStyle(styleHeader2);
		HSSFCell cellC8 = row8.createCell(2);
		cellC8.setCellStyle(styleHeader2);
		HSSFCell cellD8 = row8.createCell(3);
		cellD8.setCellStyle(styleHeader2);
		HSSFCell cellE8 = row8.createCell(4);
		cellE8.setCellStyle(styleHeader2);
		HSSFCell cellF8 = row8.createCell(5);
		cellF8.setCellStyle(styleHeader2);
		HSSFCell cellG8 = row8.createCell(6);
		cellG8.setCellStyle(styleHeader2);
		HSSFCell cellH8 = row8.createCell(7);
		cellH8.setCellStyle(styleHeader2);
		HSSFCell cellI8 = row8.createCell(8);
		cellI8.setCellStyle(styleHeader2);
		cellA8.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 8));
		//
		//Value Descriptions
		HSSFCell cellA9 = row9.createCell(0);
		cellA9.setCellStyle(styleValue);
		HSSFCell cellB9 = row9.createCell(1);
		cellB9.setCellStyle(styleValue);
		HSSFCell cellC9 = row9.createCell(2);
		cellC9.setCellStyle(styleValue);
		HSSFCell cellD9 = row9.createCell(3);
		cellD9.setCellStyle(styleValue);
		HSSFCell cellE9 = row9.createCell(4);
		cellE9.setCellStyle(styleValue);
		HSSFCell cellF9 = row9.createCell(5);
		cellF9.setCellStyle(styleValue);
		HSSFCell cellG9 = row9.createCell(6);
		cellG9.setCellStyle(styleValue);
		HSSFCell cellH9 = row9.createCell(7);
		cellH9.setCellStyle(styleValue);
		HSSFCell cellI9 = row9.createCell(8);
		cellI9.setCellStyle(styleValue);
		workString = substringLinesWithTokenOfEOL(element.getAttribute("Descriptions"), "\n");
		cellA9.setCellValue(new HSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 8));
		row9.setHeight((short)1000);
		//
		currentRowNumber = 9;
	}

	void setupFunctionIOList(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		String workString = "";
		int rowSequence = 0;
		XeadNode node = null;
		//
		//Label IOList
		currentRowNumber++;
		HSSFRow row0 = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments24")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header IOList
		currentRowNumber++;
		HSSFRow row1 = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments25")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments26")));
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		//
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
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1, new NodeComparator());
		}
		//
		rowSequence = 0;
		for (int i = 0; i <= countOfElementArray1; i++) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			rowSequence++;
			HSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			cellA.setCellValue(rowSequence);
			HSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			HSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			HSSFCell cellD = nextRow.createCell(3);
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
			cellB.setCellValue(new HSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			HSSFCell cellE = nextRow.createCell(4);
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
			cellE.setCellValue(new HSSFRichTextString(workString));
			HSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			HSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			HSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			HSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			workString = substringLinesWithTokenOfEOL(workElement1.getAttribute("Descriptions"), "\n");
			cellF.setCellValue(new HSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		}
		//
		if (rowSequence == 0) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			HSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			HSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			HSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			cellB.setCellValue(new HSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			HSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			HSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			HSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			HSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			HSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		}
	}

	void setupFunctionsCalledByThisFunction(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		String workString1 = "";
		String workString2 = "";
		int rowSequence = 0;
		//
		//Label FunctionsCalledByThis
		currentRowNumber++;
		HSSFRow rowLabel = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments30")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header FunctionsCalledByThis
		currentRowNumber++;
		HSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		HSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		HSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		HSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		HSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments21")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
		HSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		HSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellG1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments31")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		//
		//Rows of FunctionsCalledByThis
		workList = element.getElementsByTagName("FunctionUsedByThis");
		for (int i = 0; i < workList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList.item(i);
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			HSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			HSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			HSSFCell cellD = nextRow.createCell(3);
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
			cellB.setCellValue(new HSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			HSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			HSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			cellE.setCellValue(new HSSFRichTextString(workString2));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
			HSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			HSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			HSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			cellG.setCellValue(new HSSFRichTextString(workElement1.getAttribute("LaunchEvent")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
		//
		if (rowSequence == 0) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			HSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			HSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			HSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			cellB2.setCellValue(new HSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			HSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
			HSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			HSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			HSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupFunctionsCallingThis(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		int rowSequence = 0;
		//
		//Label FunctionsCallingThis
		currentRowNumber++;
		HSSFRow rowLabel = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments32")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header FunctionsCallingThis
		currentRowNumber++;
		HSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		HSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		HSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		HSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		HSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		HSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		HSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments31")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
		//
		for (int i = 0; i < functionList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)functionList.item(i);
			workList = workElement1.getElementsByTagName("FunctionUsedByThis");
			for (int j = 0; j < workList.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)workList.item(j);
				if (workElement2.getAttribute("FunctionID").equals(element.getAttribute("ID"))) {
					currentRowNumber++;
					HSSFRow nextRow = sheet.createRow(currentRowNumber);
					HSSFCell cellA = nextRow.createCell(0);
					cellA.setCellStyle(styleValueNumber);
					rowSequence++;
					cellA.setCellValue(rowSequence);
					HSSFCell cellB = nextRow.createCell(1);
					cellB.setCellStyle(styleValue);
					HSSFCell cellC = nextRow.createCell(2);
					cellC.setCellStyle(styleValue);
					HSSFCell cellD = nextRow.createCell(3);
					cellD.setCellStyle(styleValue);
					cellB.setCellValue(new HSSFRichTextString(workElement1.getAttribute("SortKey") + " / " + workElement1.getAttribute("Name")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
					HSSFCell cellE = nextRow.createCell(4);
					cellE.setCellStyle(styleValue);
					HSSFCell cellF = nextRow.createCell(5);
					cellF.setCellStyle(styleValue);
					HSSFCell cellG = nextRow.createCell(6);
					cellG.setCellStyle(styleValue);
					HSSFCell cellH = nextRow.createCell(7);
					cellH.setCellStyle(styleValue);
					HSSFCell cellI = nextRow.createCell(8);
					cellI.setCellStyle(styleValue);
					cellE.setCellValue(new HSSFRichTextString(workElement2.getAttribute("LaunchEvent")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
				}
			}
		}
		//
		if (rowSequence == 0) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			HSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			HSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			HSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			cellB2.setCellValue(new HSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			HSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			HSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			HSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			HSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
		}
	}

	void setupTasksUsingThisFunction(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		NodeList workList2 = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		org.w3c.dom.Element workElement4 = null;
		String workString1 = "";
		//String workString2 = "";
		int rowSequence = 0;
		//XeadNode node = null;
		//
		//Label TasksUsingThisFunction
		currentRowNumber++;
		HSSFRow rowLabel = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments33")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header TasksUsingThisFunction
		currentRowNumber++;
		HSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		HSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments34")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		HSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		HSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments35")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
		HSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		HSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		HSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments36")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		//
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
						HSSFRow nextRow = sheet.createRow(currentRowNumber);
						HSSFCell cellA = nextRow.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						HSSFCell cellB = nextRow.createCell(1);
						cellB.setCellStyle(styleValue);
						HSSFCell cellC = nextRow.createCell(2);
						cellC.setCellStyle(styleValue);
						workString1 = "";
						for (int m = 0; m < roleList.getLength(); m++) {
							workElement4 = (org.w3c.dom.Element)roleList.item(m);
							if (workElement4.getAttribute("ID").equals(workElement1.getAttribute("RoleID"))) {
								workString1 = workElement4.getAttribute("Name");
								break;
							}
						}
						cellB.setCellValue(new HSSFRichTextString(workString1));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
						HSSFCell cellD = nextRow.createCell(3);
						cellD.setCellStyle(styleValue);
						HSSFCell cellE = nextRow.createCell(4);
						cellE.setCellStyle(styleValue);
						cellD.setCellValue(new HSSFRichTextString(workElement1.getAttribute("Name")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
						HSSFCell cellF = nextRow.createCell(5);
						cellF.setCellStyle(styleValue);
						HSSFCell cellG = nextRow.createCell(6);
						cellG.setCellStyle(styleValue);
						HSSFCell cellH = nextRow.createCell(7);
						cellH.setCellStyle(styleValue);
						HSSFCell cellI = nextRow.createCell(8);
						cellI.setCellStyle(styleValue);
						cellF.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Label")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
					}
				}
			}
		}
		//
		if (rowSequence == 0) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			HSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			HSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new HSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			HSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			HSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 5));
			HSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			HSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			HSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupFunctionIODetails(HSSFSheet sheet, org.w3c.dom.Element element) {
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
		//
		for (int i = 0; i <= countOfElementArray1; i++) {
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			//
			sectionNumber++;
			//
			currentRowNumber++;
			HSSFRow rowLabel = sheet.createRow(currentRowNumber);
			HSSFCell cellA0 = rowLabel.createCell(0);
			cellA0.setCellStyle(styleHeader1);
			HSSFCell cellB0 = rowLabel.createCell(1);
			cellB0.setCellStyle(styleHeader1);
			HSSFCell cellC0 = rowLabel.createCell(2);
			cellC0.setCellStyle(styleHeader1);
			HSSFCell cellD0 = rowLabel.createCell(3);
			cellD0.setCellStyle(styleHeader1);
			HSSFCell cellE0 = rowLabel.createCell(4);
			cellE0.setCellStyle(styleHeader1);
			HSSFCell cellF0 = rowLabel.createCell(5);
			cellF0.setCellStyle(styleHeader1);
			HSSFCell cellG0 = rowLabel.createCell(6);
			cellG0.setCellStyle(styleHeader1);
			HSSFCell cellH0 = rowLabel.createCell(7);
			cellH0.setCellStyle(styleHeader1);
			HSSFCell cellI0 = rowLabel.createCell(8);
			cellI0.setCellStyle(styleHeader1);
			cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments37") + "(" + sectionNumber + "/" + totalSectionNumber + ")"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
			//
			currentRowNumber++;
			HSSFRow rowHeading1 = sheet.createRow(currentRowNumber);
			HSSFCell cellA1 = rowHeading1.createCell(0);
			cellA1.setCellStyle(styleHeader2);
			HSSFCell cellB1 = rowHeading1.createCell(1);
			cellB1.setCellStyle(styleHeader2);
			HSSFCell cellC1 = rowHeading1.createCell(2);
			cellC1.setCellStyle(styleHeader2);
			HSSFCell cellD1 = rowHeading1.createCell(3);
			cellD1.setCellStyle(styleHeader2);
			HSSFCell cellE1 = rowHeading1.createCell(4);
			cellE1.setCellStyle(styleHeader2);
			HSSFCell cellF1 = rowHeading1.createCell(5);
			cellF1.setCellStyle(styleHeader2);
			HSSFCell cellG1 = rowHeading1.createCell(6);
			cellG1.setCellStyle(styleHeader2);
			cellA1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments25")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 6));
			HSSFCell cellH1 = rowHeading1.createCell(7);
			cellH1.setCellStyle(styleHeader2);
			HSSFCell cellI1 = rowHeading1.createCell(8);
			cellI1.setCellStyle(styleHeader2);
			cellH1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments38")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 7, 8));
			//
			currentRowNumber++;
			HSSFRow rowValue1 = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = rowValue1.createCell(0);
			cellA2.setCellStyle(styleValue);
			HSSFCell cellB2 = rowValue1.createCell(1);
			cellB2.setCellStyle(styleValue);
			HSSFCell cellC2 = rowValue1.createCell(2);
			cellC2.setCellStyle(styleValue);
			HSSFCell cellD2 = rowValue1.createCell(3);
			cellD2.setCellStyle(styleValue);
			HSSFCell cellE2 = rowValue1.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = rowValue1.createCell(5);
			cellF2.setCellStyle(styleValue);
			HSSFCell cellG2 = rowValue1.createCell(6);
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
			cellA2.setCellValue(new HSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 6));
			HSSFCell cellH2 = rowValue1.createCell(7);
			cellH2.setCellStyle(styleValue);
			HSSFCell cellI2 = rowValue1.createCell(8);
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
			cellH2.setCellValue(new HSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 7, 8));
			//
			currentRowNumber++;
			HSSFRow rowLabelHeading2 = sheet.createRow(currentRowNumber);
			HSSFCell cellA3 = rowLabelHeading2.createCell(0);
			cellA3.setCellStyle(styleHeader2);
			HSSFCell cellB3 = rowLabelHeading2.createCell(1);
			cellB3.setCellStyle(styleHeader2);
			HSSFCell cellC3 = rowLabelHeading2.createCell(2);
			cellC3.setCellStyle(styleHeader2);
			HSSFCell cellD3 = rowLabelHeading2.createCell(3);
			cellD3.setCellStyle(styleHeader2);
			HSSFCell cellE3 = rowLabelHeading2.createCell(4);
			cellE3.setCellStyle(styleHeader2);
			HSSFCell cellF3 = rowLabelHeading2.createCell(5);
			cellF3.setCellStyle(styleHeader2);
			HSSFCell cellG3 = rowLabelHeading2.createCell(6);
			cellG3.setCellStyle(styleHeader2);
			HSSFCell cellH3 = rowLabelHeading2.createCell(7);
			cellH3.setCellStyle(styleHeader2);
			HSSFCell cellI3 = rowLabelHeading2.createCell(8);
			cellI3.setCellStyle(styleHeader2);
			cellA3.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
			//
			currentRowNumber++;
			HSSFRow rowValueDescriptions = sheet.createRow(currentRowNumber);
			HSSFCell cellA4 = rowValueDescriptions.createCell(0);
			cellA4.setCellStyle(styleValue);
			HSSFCell cellB4 = rowValueDescriptions.createCell(1);
			cellB4.setCellStyle(styleValue);
			HSSFCell cellC4 = rowValueDescriptions.createCell(2);
			cellC4.setCellStyle(styleValue);
			HSSFCell cellD4 = rowValueDescriptions.createCell(3);
			cellD4.setCellStyle(styleValue);
			HSSFCell cellE4 = rowValueDescriptions.createCell(4);
			cellE4.setCellStyle(styleValue);
			HSSFCell cellF4 = rowValueDescriptions.createCell(5);
			cellF4.setCellStyle(styleValue);
			HSSFCell cellG4 = rowValueDescriptions.createCell(6);
			cellG4.setCellStyle(styleValue);
			HSSFCell cellH4 = rowValueDescriptions.createCell(7);
			cellH4.setCellStyle(styleValue);
			HSSFCell cellI4 = rowValueDescriptions.createCell(8);
			cellI4.setCellStyle(styleValue);
			workString1 = substringLinesWithTokenOfEOL(workElement1.getAttribute("Descriptions"), "\n");
			cellA4.setCellValue(new HSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
			rowValueDescriptions.setHeight((short)1000);
			//
			if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOSpool") || elementArray1[i].getType().equals("IOWebPage")) {
				//
				currentRowNumber++;
				HSSFRow rowLabelImage = sheet.createRow(currentRowNumber);
				HSSFCell cellA5 = rowLabelImage.createCell(0);
				cellA5.setCellStyle(styleHeader2);
				HSSFCell cellB5 = rowLabelImage.createCell(1);
				cellB5.setCellStyle(styleHeader2);
				HSSFCell cellC5 = rowLabelImage.createCell(2);
				cellC5.setCellStyle(styleHeader2);
				HSSFCell cellD5 = rowLabelImage.createCell(3);
				cellD5.setCellStyle(styleHeader2);
				HSSFCell cellE5 = rowLabelImage.createCell(4);
				cellE5.setCellStyle(styleHeader2);
				HSSFCell cellF5 = rowLabelImage.createCell(5);
				cellF5.setCellStyle(styleHeader2);
				HSSFCell cellG5 = rowLabelImage.createCell(6);
				cellG5.setCellStyle(styleHeader2);
				HSSFCell cellH5 = rowLabelImage.createCell(7);
				cellH5.setCellStyle(styleHeader2);
				HSSFCell cellI5 = rowLabelImage.createCell(8);
				cellI5.setCellStyle(styleHeader2);
				cellA5.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments39")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
				//
				currentRowNumber++;
				HSSFRow rowValueImage = sheet.createRow(currentRowNumber);
				HSSFCell cellA6 = rowValueImage.createCell(0);
				cellA6.setCellStyle(styleImage);
				HSSFCell cellB6 = rowValueImage.createCell(1);
				cellB6.setCellStyle(styleImage);
				HSSFCell cellC6 = rowValueImage.createCell(2);
				cellC6.setCellStyle(styleImage);
				HSSFCell cellD6 = rowValueImage.createCell(3);
				cellD6.setCellStyle(styleImage);
				HSSFCell cellE6 = rowValueImage.createCell(4);
				cellE6.setCellStyle(styleImage);
				HSSFCell cellF6 = rowValueImage.createCell(5);
				cellF6.setCellStyle(styleImage);
				HSSFCell cellG6 = rowValueImage.createCell(6);
				cellG6.setCellStyle(styleImage);
				HSSFCell cellH6 = rowValueImage.createCell(7);
				cellH6.setCellStyle(styleImage);
				HSSFCell cellI6 = rowValueImage.createCell(8);
				cellI6.setCellStyle(styleImage);
				HSSFRichTextString valueImage = null;
				if (elementArray1[i].getType().equals("IOWebPage")) {
					workString1 = res.getString("DialogDocuments40a") + workElement1.getAttribute("FileName") + res.getString("DialogDocuments40b");
					valueImage = new HSSFRichTextString(workString1);
					cellA6.setCellValue(valueImage);
				}
				if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOSpool")) {
					int imageType = -1;
					File file = new File(fileName_);
					String imageFileName = file.getParent() + File.separator + element.getAttribute("SortKey") + "_" + workElement1.getAttribute("SortKey");
					File imageFile = new File(imageFileName + ".png");
					if (imageFile.exists()) {
						imageType = HSSFWorkbook.PICTURE_TYPE_PNG;
					} else {
						imageFile = new File(imageFileName + ".jpg");
						if (imageFile.exists()) {
							imageType = HSSFWorkbook.PICTURE_TYPE_JPEG;
						} else {
							imageFile = new File(imageFileName + ".jpeg");
							if (imageFile.exists()) {
								imageType = HSSFWorkbook.PICTURE_TYPE_JPEG;
							}
						}
					}
					//
					if (imageFile.exists()) {
						int pictureIndex;
						FileInputStream fis = null;
						ByteArrayOutputStream bos = null;
						try {
							// read in the image file
							fis = new FileInputStream(imageFile);
							bos = new ByteArrayOutputStream( );
							int c;
							// copy the image bytes into the ByteArrayOutputStream
							while ((c = fis.read()) != -1) {
								bos.write(c);
							}
							// add the image bytes to the workbook
							pictureIndex = workBook.addPicture( bos.toByteArray(), imageType);
							anchor = new HSSFClientAnchor(0,0,0,0,(short)0,cellA6.getRowIndex(),(short)9,cellA6.getRowIndex()+1);
							anchor.setAnchorType(0);
							// 0 = Move and size with Cells.
							// 2 = Move but don't size with cells.
							// 3 = Don't move or size with cells.
							picture = patriarch.createPicture(anchor, pictureIndex);
							//
						} catch(Exception ex) {
							ex.printStackTrace();
						} finally {
							try {
								if (fis != null) {
									fis.close();
								}
								if (bos != null) {
									bos.close();
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
						valueImage = new HSSFRichTextString(workString1);
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
				//
				if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOWebPage")) {
					currentRowNumber++;
					HSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
					HSSFCell cellA7 = rowColumnHeading.createCell(0);
					cellA7.setCellStyle(styleHeader2Number);
					cellA7.setCellValue(new HSSFRichTextString("No."));
					HSSFCell cellB7 = rowColumnHeading.createCell(1);
					cellB7.setCellStyle(styleHeader2);
					cellB7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments41")));
					HSSFCell cellC7 = rowColumnHeading.createCell(2);
					cellC7.setCellStyle(styleHeader2);
					HSSFCell cellD7 = rowColumnHeading.createCell(3);
					cellD7.setCellStyle(styleHeader2);
					cellC7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments42")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
					HSSFCell cellE7 = rowColumnHeading.createCell(4);
					cellE7.setCellStyle(styleHeader2);
					HSSFCell cellF7 = rowColumnHeading.createCell(5);
					cellF7.setCellStyle(styleHeader2);
					cellE7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments43")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
					HSSFCell cellG7 = rowColumnHeading.createCell(6);
					cellG7.setCellStyle(styleHeader2);
					HSSFCell cellH7 = rowColumnHeading.createCell(7);
					cellH7.setCellStyle(styleHeader2);
					HSSFCell cellI7 = rowColumnHeading.createCell(8);
					cellI7.setCellStyle(styleHeader2);
					cellG7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					//
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
						Arrays.sort(elementArray2, 0, countOfElementArray2 + 1, new NodeComparator());
					}
					for (int j = 0; j <= countOfElementArray2; j++) {
						workElement2 = (org.w3c.dom.Element)elementArray2[j].getElement();
						currentRowNumber++;
						HSSFRow rowValue2 = sheet.createRow(currentRowNumber);
						HSSFCell cellA = rowValue2.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						HSSFCell cellB = rowValue2.createCell(1);
						cellB.setCellStyle(styleValue);
						cellB.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Label")));
						HSSFCell cellC = rowValue2.createCell(2);
						cellC.setCellStyle(styleValue);
						HSSFCell cellD = rowValue2.createCell(3);
						cellD.setCellStyle(styleValue);
						cellC.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Name")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						HSSFCell cellE = rowValue2.createCell(4);
						cellE.setCellStyle(styleValue);
						HSSFCell cellF = rowValue2.createCell(5);
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
						cellE.setCellValue(new HSSFRichTextString(workString1));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
						HSSFCell cellG = rowValue2.createCell(6);
						cellG.setCellStyle(styleValue);
						HSSFCell cellH = rowValue2.createCell(7);
						cellH.setCellStyle(styleValue);
						HSSFCell cellI = rowValue2.createCell(8);
						cellI.setCellStyle(styleValue);
						cellG.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Descriptions")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					}
					//
					if (rowSequence == 0) {
						currentRowNumber++;
						HSSFRow rowBlank = sheet.createRow(currentRowNumber);
						HSSFCell cellA8 = rowBlank.createCell(0);
						cellA8.setCellStyle(styleValueNumber);
						HSSFCell cellB8 = rowBlank.createCell(1);
						cellB8.setCellStyle(styleValue);
						cellB8.setCellValue(new HSSFRichTextString("(None)"));
						HSSFCell cellC8 = rowBlank.createCell(2);
						cellC8.setCellStyle(styleValue);
						HSSFCell cellD8 = rowBlank.createCell(3);
						cellD8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						HSSFCell cellE8 = rowBlank.createCell(4);
						cellE8.setCellStyle(styleValue);
						HSSFCell cellF8 = rowBlank.createCell(5);
						cellF8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
						HSSFCell cellG8 = rowBlank.createCell(6);
						cellG8.setCellStyle(styleValue);
						HSSFCell cellH8 = rowBlank.createCell(7);
						cellH8.setCellStyle(styleValue);
						HSSFCell cellI8 = rowBlank.createCell(8);
						cellI8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					}
				}
				if (elementArray1[i].getType().equals("IOSpool")) {
					currentRowNumber++;
					HSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
					HSSFCell cellA7 = rowColumnHeading.createCell(0);
					cellA7.setCellStyle(styleHeader2Number);
					cellA7.setCellValue(new HSSFRichTextString("No."));
					HSSFCell cellB7 = rowColumnHeading.createCell(1);
					cellB7.setCellStyle(styleHeader2);
					cellB7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments41")));
					HSSFCell cellC7 = rowColumnHeading.createCell(2);
					cellC7.setCellStyle(styleHeader2);
					HSSFCell cellD7 = rowColumnHeading.createCell(3);
					cellD7.setCellStyle(styleHeader2);
					cellC7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments42")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
					HSSFCell cellE7 = rowColumnHeading.createCell(4);
					cellE7.setCellStyle(styleHeader2);
					HSSFCell cellF7 = rowColumnHeading.createCell(5);
					cellF7.setCellStyle(styleHeader2);
					HSSFCell cellG7 = rowColumnHeading.createCell(6);
					cellG7.setCellStyle(styleHeader2);
					HSSFCell cellH7 = rowColumnHeading.createCell(7);
					cellH7.setCellStyle(styleHeader2);
					HSSFCell cellI7 = rowColumnHeading.createCell(8);
					cellI7.setCellStyle(styleHeader2);
					cellE7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					//
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
						Arrays.sort(elementArray2, 0, countOfElementArray2 + 1, new NodeComparator());
					}
					for (int j = 0; j <= countOfElementArray2; j++) {
						workElement2 = (org.w3c.dom.Element)elementArray2[j].getElement();
						currentRowNumber++;
						HSSFRow rowValue2 = sheet.createRow(currentRowNumber);
						HSSFCell cellA = rowValue2.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						HSSFCell cellB = rowValue2.createCell(1);
						cellB.setCellStyle(styleValue);
						cellB.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Label")));
						HSSFCell cellC = rowValue2.createCell(2);
						cellC.setCellStyle(styleValue);
						HSSFCell cellD = rowValue2.createCell(3);
						cellD.setCellStyle(styleValue);
						cellC.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Name")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						HSSFCell cellE = rowValue2.createCell(4);
						cellE.setCellStyle(styleValue);
						HSSFCell cellF = rowValue2.createCell(5);
						cellF.setCellStyle(styleValue);
						HSSFCell cellG = rowValue2.createCell(6);
						cellG.setCellStyle(styleValue);
						HSSFCell cellH = rowValue2.createCell(7);
						cellH.setCellStyle(styleValue);
						HSSFCell cellI = rowValue2.createCell(8);
						cellI.setCellStyle(styleValue);
						cellE.setCellValue(new HSSFRichTextString(workElement2.getAttribute("Descriptions")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					}
					//
					if (rowSequence == 0) {
						currentRowNumber++;
						HSSFRow rowBlank = sheet.createRow(currentRowNumber);
						HSSFCell cellA8 = rowBlank.createCell(0);
						cellA8.setCellStyle(styleValueNumber);
						HSSFCell cellB8 = rowBlank.createCell(1);
						cellB8.setCellStyle(styleValue);
						cellB8.setCellValue(new HSSFRichTextString("(None)"));
						HSSFCell cellC8 = rowBlank.createCell(2);
						cellC8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						HSSFCell cellD8 = rowBlank.createCell(3);
						cellD8.setCellStyle(styleValue);
						HSSFCell cellE8 = rowBlank.createCell(4);
						cellE8.setCellStyle(styleValue);
						HSSFCell cellF8 = rowBlank.createCell(5);
						cellF8.setCellStyle(styleValue);
						HSSFCell cellG8 = rowBlank.createCell(6);
						cellG8.setCellStyle(styleValue);
						HSSFCell cellH8 = rowBlank.createCell(7);
						cellH8.setCellStyle(styleValue);
						HSSFCell cellI8 = rowBlank.createCell(8);
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
						//
						currentRowNumber++;
						HSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
						HSSFCell cellA7 = rowColumnHeading.createCell(0);
						cellA7.setCellStyle(styleHeader2Number);
						cellA7.setCellValue(new HSSFRichTextString("No."));
						HSSFCell cellB7 = rowColumnHeading.createCell(1);
						cellB7.setCellStyle(styleHeader2);
						HSSFCell cellC7 = rowColumnHeading.createCell(2);
						cellC7.setCellStyle(styleHeader2);
						cellB7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments48")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
						HSSFCell cellD7 = rowColumnHeading.createCell(3);
						cellD7.setCellStyle(styleHeader2);
						HSSFCell cellE7 = rowColumnHeading.createCell(4);
						cellE7.setCellStyle(styleHeader2);
						cellD7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments49")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
						HSSFCell cellF7 = rowColumnHeading.createCell(5);
						cellF7.setCellStyle(styleHeader2);
						HSSFCell cellG7 = rowColumnHeading.createCell(6);
						cellG7.setCellStyle(styleHeader2);
						HSSFCell cellH7 = rowColumnHeading.createCell(7);
						cellH7.setCellStyle(styleHeader2);
						HSSFCell cellI7 = rowColumnHeading.createCell(8);
						cellI7.setCellStyle(styleHeader2);
						cellF7.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments50")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
						//
						NodeList primaryKeyFieldList = null;
						workList3 = workElement2.getElementsByTagName("TableKey");
						for (int m = 0; m < workList3.getLength(); m++) {
							workElement3 = (org.w3c.dom.Element)workList3.item(m);
							if (workElement3.getAttribute("Type").equals("PK")) {
								primaryKeyFieldList = workElement3.getElementsByTagName("TableKeyField");
								break;
							}
						}
						//
						rowSequence = 0;
						countOfElementArray2 = -1;
						workList2 = workElement2.getElementsByTagName("TableField");
						for (int k = 0; k < workList2.getLength(); k++) {
							countOfElementArray2++;
							node = new XeadNode("TableField",(org.w3c.dom.Element)workList2.item(k));
							elementArray2[countOfElementArray2] = node;
						}
						if (countOfElementArray2 > 0) {
							Arrays.sort(elementArray2, 0, countOfElementArray2 + 1, new NodeComparator());
						}
						for (int k = 0; k <= countOfElementArray2; k++) {
							workElement3 = (org.w3c.dom.Element)elementArray2[k].getElement();
							currentRowNumber++;
							HSSFRow rowValue2 = sheet.createRow(currentRowNumber);
							HSSFCell cellA = rowValue2.createCell(0);
							cellA.setCellStyle(styleValueNumber);
							rowSequence++;
							cellA.setCellValue(rowSequence);
							HSSFCell cellB = rowValue2.createCell(1);
							cellB.setCellStyle(styleValue);
							HSSFCell cellC = rowValue2.createCell(2);
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
							cellB.setCellValue(new HSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
							HSSFCell cellD = rowValue2.createCell(3);
							cellD.setCellStyle(styleValue);
							HSSFCell cellE = rowValue2.createCell(4);
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
							cellD.setCellValue(new HSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
							HSSFCell cellF = rowValue2.createCell(5);
							cellF.setCellStyle(styleValue);
							HSSFCell cellG = rowValue2.createCell(6);
							cellG.setCellStyle(styleValue);
							HSSFCell cellH = rowValue2.createCell(7);
							cellH.setCellStyle(styleValue);
							HSSFCell cellI = rowValue2.createCell(8);
							cellI.setCellStyle(styleValue);
							workString1 = "test";
							for (int m = 0; m < ioFieldList.getLength(); m++) {
								workElement4 = (org.w3c.dom.Element)ioFieldList.item(m);
								if (workElement4.getAttribute("FieldID").equals(workElement3.getAttribute("ID"))) {
									workString1 = workElement4.getAttribute("Descriptions");
									break;
								}
							}
							cellF.setCellValue(new HSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
						}
						break;
					}
				}
			}
		}
	}

	void setupTableSummary(HSSFSheet sheet, org.w3c.dom.Element element) {
		//NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		String workString = "";
		//
		HSSFRow row0 = sheet.createRow(0);
		HSSFRow row1 = sheet.createRow(1);
		HSSFRow row2 = sheet.createRow(2);
		HSSFRow row3 = sheet.createRow(3);
		HSSFRow row4 = sheet.createRow(4);
		HSSFRow row5 = sheet.createRow(5);
		//
		//Title
		HSSFRichTextString title = new HSSFRichTextString(res.getString("DialogDocuments51"));
		HSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleTitle);
		HSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleTitle);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleTitle);
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleTitle);
		cellA0.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
		//
		//Label Subsystem
		HSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader2);
		HSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader2);
		HSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader2);
		HSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader2);
		HSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader2);
		HSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader2);
		HSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader2);
		cellC0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));
		//
		//Value Subsystem
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleValue);
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleValue);
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleValue);
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleValue);
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleValue);
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleValue);
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleValue);
		cellC1.setCellValue(new HSSFRichTextString(subsystemElement.getAttribute("SortKey") + " / " + subsystemElement.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));
		//
		//Label Table Name
		HSSFCell cellA2 = row2.createCell(0);
		cellA2.setCellStyle(styleHeader2);
		HSSFCell cellB2 = row2.createCell(1);
		cellB2.setCellStyle(styleHeader2);
		HSSFCell cellC2 = row2.createCell(2);
		cellC2.setCellStyle(styleHeader2);
		HSSFCell cellD2 = row2.createCell(3);
		cellD2.setCellStyle(styleHeader2);
		HSSFCell cellE2 = row2.createCell(4);
		cellE2.setCellStyle(styleHeader2);
		cellA2.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments52")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
		//
		//Value Table Name
		HSSFCell cellA3 = row3.createCell(0);
		cellA3.setCellStyle(styleValue);
		HSSFCell cellB3 = row3.createCell(1);
		cellB3.setCellStyle(styleValue);
		HSSFCell cellC3 = row3.createCell(2);
		cellC3.setCellStyle(styleValue);
		HSSFCell cellD3 = row3.createCell(3);
		cellD3.setCellStyle(styleValue);
		HSSFCell cellE3 = row3.createCell(4);
		cellE3.setCellStyle(styleValue);
		cellA3.setCellValue(new HSSFRichTextString(element.getAttribute("SortKey") + " / " + element.getAttribute("Name")));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
		//
		//Label TableType
		HSSFCell cellF2 = row2.createCell(5);
		cellF2.setCellStyle(styleHeader2);
		HSSFCell cellG2 = row2.createCell(6);
		cellG2.setCellStyle(styleHeader2);
		HSSFCell cellH2 = row2.createCell(7);
		cellH2.setCellStyle(styleHeader2);
		HSSFCell cellI2 = row2.createCell(8);
		cellI2.setCellStyle(styleHeader2);
		cellF2.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments53")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));
		//
		//Value TableType
		HSSFCell cellF3 = row3.createCell(5);
		cellF3.setCellStyle(styleValue);
		HSSFCell cellG3 = row3.createCell(6);
		cellG3.setCellStyle(styleValue);
		HSSFCell cellH3 = row3.createCell(7);
		cellH3.setCellStyle(styleValue);
		HSSFCell cellI3 = row3.createCell(8);
		cellI3.setCellStyle(styleValue);
		for (int i = 0; i < tableTypeList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)tableTypeList.item(i);
			if (workElement1.getAttribute("ID").equals(element.getAttribute("TableTypeID"))) {
				workString = workElement1.getAttribute("Name");
				break;
			}
		}
		cellF3.setCellValue(new HSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));
		//
		//Label Descriptions
		HSSFCell cellA4 = row4.createCell(0);
		cellA4.setCellStyle(styleHeader2);
		HSSFCell cellB4 = row4.createCell(1);
		cellB4.setCellStyle(styleHeader2);
		HSSFCell cellC4 = row4.createCell(2);
		cellC4.setCellStyle(styleHeader2);
		HSSFCell cellD4 = row4.createCell(3);
		cellD4.setCellStyle(styleHeader2);
		HSSFCell cellE4 = row4.createCell(4);
		cellE4.setCellStyle(styleHeader2);
		HSSFCell cellF4 = row4.createCell(5);
		cellF4.setCellStyle(styleHeader2);
		HSSFCell cellG4 = row4.createCell(6);
		cellG4.setCellStyle(styleHeader2);
		HSSFCell cellH4 = row4.createCell(7);
		cellH4.setCellStyle(styleHeader2);
		HSSFCell cellI4 = row4.createCell(8);
		cellI4.setCellStyle(styleHeader2);
		cellA4.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));
		//
		//Value Descriptions
		HSSFCell cellA5 = row5.createCell(0);
		cellA5.setCellStyle(styleValue);
		HSSFCell cellB5 = row5.createCell(1);
		cellB5.setCellStyle(styleValue);
		HSSFCell cellC5 = row5.createCell(2);
		cellC5.setCellStyle(styleValue);
		HSSFCell cellD5 = row5.createCell(3);
		cellD5.setCellStyle(styleValue);
		HSSFCell cellE5 = row5.createCell(4);
		cellE5.setCellStyle(styleValue);
		HSSFCell cellF5 = row5.createCell(5);
		cellF5.setCellStyle(styleValue);
		HSSFCell cellG5 = row5.createCell(6);
		cellG5.setCellStyle(styleValue);
		HSSFCell cellH5 = row5.createCell(7);
		cellH5.setCellStyle(styleValue);
		HSSFCell cellI5 = row5.createCell(8);
		cellI5.setCellStyle(styleValue);
		workString = substringLinesWithTokenOfEOL(element.getAttribute("Descriptions"), "\n");
		cellA5.setCellValue(new HSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));
		row5.setHeight((short)1000);
		//
		currentRowNumber = 5;
	}

	void setupTableFieldList(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		String workString1 = "";
		//String workString2 = "";
		int rowSequence = 0;
		XeadNode node = null;
		//
		//Label FieldList
		currentRowNumber++;
		HSSFRow row0 = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments54")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header FieldList
		currentRowNumber++;
		HSSFRow row1 = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments55")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments56")));
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new HSSFRichTextString("Null"));
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments57")));
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellG1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		//
		//Rows of TableField
		countOfElementArray1 = -1;
		workList = element.getElementsByTagName("TableField");
		for (int i = 0; i < workList.getLength(); i++) {
			countOfElementArray1++;
			node = new XeadNode("TableField",(org.w3c.dom.Element)workList.item(i));
			elementArray1[countOfElementArray1] = node;
		}
		if (countOfElementArray1 > 0) {
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1, new NodeComparator());
		}
		for (int i = 0; i <= countOfElementArray1; i++) {
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			HSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
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
			cellB.setCellValue(new HSSFRichTextString(workString1));
			HSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			HSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			workString1 = "";
			for (int m = 0; m < dataTypeList.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)dataTypeList.item(m);
				if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("DataTypeID"))) {
					if (workElement2.getAttribute("Decimal").equals("") || workElement2.getAttribute("Decimal").equals("0")) {
						workString1 = workElement2.getAttribute("Name") + "("  + workElement2.getAttribute("Length") + ")";
					} else {
						workString1 = workElement2.getAttribute("Name") + "(" + workElement2.getAttribute("Length") + "."
						+ workElement2.getAttribute("Decimal") + ")";
					}
					break;
				}
			}
			cellD.setCellValue(new HSSFRichTextString(workString1));
			HSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			if (workElement1.getAttribute("NotNull").equals("true")) {
				workString1 = res.getString("DialogDocuments58");
			} else {
				workString1 = res.getString("DialogDocuments59");
			}
			cellE.setCellValue(new HSSFRichTextString(workString1));
			HSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			cellF.setCellValue(new HSSFRichTextString(workElement1.getAttribute("Default")));
			HSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			HSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			HSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			workString1 = substringLinesWithTokenOfEOL(workElement1.getAttribute("Descriptions"), "\n");
			cellG.setCellValue(new HSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
		//
		if (rowSequence == 0) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			HSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			HSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new HSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			HSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			HSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			HSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			HSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			HSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupTableKeyList(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		NodeList workList2 = null;
		NodeList fieldList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		String workString1 = "";
		//String workString2 = "";
		int rowSequence = 0;
		XeadNode node = null;
		//
		//Label KeyList
		currentRowNumber++;
		HSSFRow row0 = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments60")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header KeyList
		currentRowNumber++;
		HSSFRow row1 = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments61")));
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellC1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments62")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));
		//
		//Rows of Key
		fieldList = element.getElementsByTagName("TableField");
		workList1 = element.getElementsByTagName("TableKey");
		for (int i = 0; i < workList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList1.item(i);
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			HSSFCell cellB = nextRow.createCell(1);
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
			cellB.setCellValue(new HSSFRichTextString(workString1));
			HSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			HSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			HSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			HSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			HSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			HSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			HSSFCell cellI = nextRow.createCell(8);
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
				Arrays.sort(elementArray1, 0, countOfElementArray1 + 1, new NodeComparator());
			}
			for (int j = 0; j <= countOfElementArray1; j++) {
				workElement2 = (org.w3c.dom.Element)elementArray1[j].getElement();
				for (int k = 0; k < fieldList.getLength(); k++) {
					workElement3 = (org.w3c.dom.Element)fieldList.item(k);
					if (workElement2.getAttribute("FieldID").equals(workElement3.getAttribute("ID"))) {
						if (workString1.equals("")) {
							workString1 = workElement3.getAttribute("Name");
						} else {
							workString1 = workString1 + " + " + workElement3.getAttribute("Name");
						}
						break;
					}
				}
			}
			if (workString1.equals("")) {
				workString1 = "*None";
			}
			cellC.setCellValue(new HSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));
		}
	}

	void setupTableUsageList(HSSFSheet sheet, org.w3c.dom.Element element) {
		NodeList workList1 = null;
		//NodeList workList2 = null;
		//NodeList fieldList = null;
		org.w3c.dom.Element workElement1 = null;
		org.w3c.dom.Element workElement2 = null;
		org.w3c.dom.Element workElement3 = null;
		String workString1 = "";
		//String workString2 = "";
		int rowSequence = 0;
		XeadNode node = null;
		//
		//Label FieldList
		currentRowNumber++;
		HSSFRow row0 = sheet.createRow(currentRowNumber);
		HSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		HSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		HSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		HSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		HSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		HSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		HSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		HSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		HSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments66")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
		//
		//Header FunctionList
		currentRowNumber++;
		HSSFRow row1 = sheet.createRow(currentRowNumber);
		HSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new HSSFRichTextString("No."));
		HSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		HSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		HSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		HSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		HSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		HSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		HSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
		HSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellI1.setCellValue(new HSSFRichTextString("CRUD"));
		//
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
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1, new NodeComparator());
		}
		for (int i = 0; i <= countOfElementArray1; i++) {
			workElement1 = (org.w3c.dom.Element)elementArray1[i].getElement();
			workList1 = workElement1.getElementsByTagName("IOTable");
			for (int j = 0; j < workList1.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)workList1.item(j);
				if (workElement2.getAttribute("TableID").equals(element.getAttribute("ID"))) {
					currentRowNumber++;
					HSSFRow nextRow = sheet.createRow(currentRowNumber);
					HSSFCell cellA = nextRow.createCell(0);
					cellA.setCellStyle(styleValueNumber);
					rowSequence++;
					cellA.setCellValue(rowSequence);
					HSSFCell cellB = nextRow.createCell(1);
					cellB.setCellStyle(styleValue);
					HSSFCell cellC = nextRow.createCell(2);
					cellC.setCellStyle(styleValue);
					workString1 = "";
					for (int k = 0; k < subsystemList.getLength(); k++) {
						workElement3 = (org.w3c.dom.Element)subsystemList.item(k);
						if (workElement1.getAttribute("SubsystemID").equals(workElement3.getAttribute("ID"))) {
							workString1 = workElement3.getAttribute("Name");
							break;
						}
					}
					cellB.setCellValue(new HSSFRichTextString(workString1));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
					HSSFCell cellD = nextRow.createCell(3);
					cellD.setCellStyle(styleValue);
					HSSFCell cellE = nextRow.createCell(4);
					cellE.setCellStyle(styleValue);
					HSSFCell cellF = nextRow.createCell(5);
					cellF.setCellStyle(styleValue);
					HSSFCell cellG = nextRow.createCell(6);
					cellG.setCellStyle(styleValue);
					HSSFCell cellH = nextRow.createCell(7);
					cellH.setCellStyle(styleValue);
					cellD.setCellValue(new HSSFRichTextString(workElement1.getAttribute("SortKey") + " / " + workElement1.getAttribute("Name")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
					HSSFCell cellI = nextRow.createCell(8);
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
					cellI.setCellValue(new HSSFRichTextString(workString1));
				}
			}
		}
		if (rowSequence == 0) {
			currentRowNumber++;
			HSSFRow nextRow = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			HSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			HSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new HSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			HSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			HSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			HSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			HSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
			HSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
		}
	}

	void setupTableKeyDetails(HSSFSheet sheet, org.w3c.dom.Element element) {
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
		for (int i = 0; i < workList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)workList1.item(i);
			rowSequence = 0;
			//
			//Label Key Definition
			currentRowNumber++;
			HSSFRow row0 = sheet.createRow(currentRowNumber);
			HSSFCell cellA0 = row0.createCell(0);
			cellA0.setCellStyle(styleHeader1);
			HSSFCell cellB0 = row0.createCell(1);
			cellB0.setCellStyle(styleHeader1);
			HSSFCell cellC0 = row0.createCell(2);
			cellC0.setCellStyle(styleHeader1);
			HSSFCell cellD0 = row0.createCell(3);
			cellD0.setCellStyle(styleHeader1);
			HSSFCell cellE0 = row0.createCell(4);
			cellE0.setCellStyle(styleHeader1);
			HSSFCell cellF0 = row0.createCell(5);
			cellF0.setCellStyle(styleHeader1);
			HSSFCell cellG0 = row0.createCell(6);
			cellG0.setCellStyle(styleHeader1);
			HSSFCell cellH0 = row0.createCell(7);
			cellH0.setCellStyle(styleHeader1);
			HSSFCell cellI0 = row0.createCell(8);
			cellI0.setCellStyle(styleHeader1);
			sectionNumber++;
			cellA0.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments67") + "(" + sectionNumber + "/" + workList1.getLength() + ")"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
			//
			//Header Key Definition
			currentRowNumber++;
			HSSFRow row1 = sheet.createRow(currentRowNumber);
			HSSFCell cellA1 = row1.createCell(0);
			cellA1.setCellStyle(styleHeader2);
			HSSFCell cellB1 = row1.createCell(1);
			cellB1.setCellStyle(styleHeader2);
			cellA1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments61")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 1));
			HSSFCell cellC1 = row1.createCell(2);
			cellC1.setCellStyle(styleHeader2);
			HSSFCell cellD1 = row1.createCell(3);
			cellD1.setCellStyle(styleHeader2);
			HSSFCell cellE1 = row1.createCell(4);
			cellE1.setCellStyle(styleHeader2);
			HSSFCell cellF1 = row1.createCell(5);
			cellF1.setCellStyle(styleHeader2);
			HSSFCell cellG1 = row1.createCell(6);
			cellG1.setCellStyle(styleHeader2);
			HSSFCell cellH1 = row1.createCell(7);
			cellH1.setCellStyle(styleHeader2);
			HSSFCell cellI1 = row1.createCell(8);
			cellI1.setCellStyle(styleHeader2);
			cellC1.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments62")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));
			//
			currentRowNumber++;
			HSSFRow next2 = sheet.createRow(currentRowNumber);
			HSSFCell cellA2 = next2.createCell(0);
			cellA2.setCellStyle(styleValue);
			HSSFCell cellB2 = next2.createCell(1);
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
			cellA2.setCellValue(new HSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 1));
			HSSFCell cellC2 = next2.createCell(2);
			cellC2.setCellStyle(styleValue);
			HSSFCell cellD2 = next2.createCell(3);
			cellD2.setCellStyle(styleValue);
			HSSFCell cellE2 = next2.createCell(4);
			cellE2.setCellStyle(styleValue);
			HSSFCell cellF2 = next2.createCell(5);
			cellF2.setCellStyle(styleValue);
			HSSFCell cellG2 = next2.createCell(6);
			cellG2.setCellStyle(styleValue);
			HSSFCell cellH2 = next2.createCell(7);
			cellH2.setCellStyle(styleValue);
			HSSFCell cellI2 = next2.createCell(8);
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
				Arrays.sort(elementArray1, 0, countOfElementArray1 + 1, new NodeComparator());
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
			cellC2.setCellValue(new HSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));
			//
			//Header RelationshipList
			currentRowNumber++;
			HSSFRow row3 = sheet.createRow(currentRowNumber);
			HSSFCell cellA3 = row3.createCell(0);
			cellA3.setCellStyle(styleHeader2Number);
			cellA3.setCellValue(new HSSFRichTextString("No."));
			HSSFCell cellB3 = row3.createCell(1);
			cellB3.setCellStyle(styleHeader2);
			cellB3.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments68")));
			HSSFCell cellC3 = row3.createCell(2);
			cellC3.setCellStyle(styleHeader2);
			HSSFCell cellD3 = row3.createCell(3);
			cellD3.setCellStyle(styleHeader2);
			HSSFCell cellE3 = row3.createCell(4);
			cellE3.setCellStyle(styleHeader2);
			cellC3.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments69")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 4));
			HSSFCell cellF3 = row3.createCell(5);
			cellF3.setCellStyle(styleHeader2);
			HSSFCell cellG3 = row3.createCell(6);
			cellG3.setCellStyle(styleHeader2);
			HSSFCell cellH3 = row3.createCell(7);
			cellH3.setCellStyle(styleHeader2);
			HSSFCell cellI3 = row3.createCell(8);
			cellI3.setCellStyle(styleHeader2);
			cellF3.setCellValue(new HSSFRichTextString(res.getString("DialogDocuments70")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
			//
			for (int j = 0; j < relationshipList.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)relationshipList.item(j);
				if ((workElement2.getAttribute("Table1ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey1ID").equals(workElement1.getAttribute("ID"))))
						|| (workElement2.getAttribute("Table2ID").equals(element.getAttribute("ID")) && (workElement2.getAttribute("TableKey2ID").equals(workElement1.getAttribute("ID")))) ) {
					currentRowNumber++;
					HSSFRow nextRow = sheet.createRow(currentRowNumber);
					HSSFCell cellA = nextRow.createCell(0);
					cellA.setCellStyle(styleValueNumber);
					rowSequence++;
					cellA.setCellValue(rowSequence);
					HSSFCell cellB = nextRow.createCell(1);
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
					cellB.setCellValue(new HSSFRichTextString(workString));
					HSSFCell cellC = nextRow.createCell(2);
					cellC.setCellStyle(styleValue);
					HSSFCell cellD = nextRow.createCell(3);
					cellD.setCellStyle(styleValue);
					HSSFCell cellE = nextRow.createCell(4);
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
					cellC.setCellValue(new HSSFRichTextString(workString));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 4));
					HSSFCell cellF = nextRow.createCell(5);
					cellF.setCellStyle(styleValue);
					HSSFCell cellG = nextRow.createCell(6);
					cellG.setCellStyle(styleValue);
					HSSFCell cellH = nextRow.createCell(7);
					cellH.setCellStyle(styleValue);
					HSSFCell cellI = nextRow.createCell(8);
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
								Arrays.sort(elementArray1, 0, countOfElementArray1 + 1, new NodeComparator());
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
					cellF.setCellValue(new HSSFRichTextString(workString));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
				}
			}
			if (rowSequence == 0) {
				currentRowNumber++;
				HSSFRow row4 = sheet.createRow(currentRowNumber);
				HSSFCell cellA4 = row4.createCell(0);
				cellA4.setCellStyle(styleValueNumber);
				HSSFCell cellB4 = row4.createCell(1);
				cellB4.setCellStyle(styleValue);
				HSSFCell cellC4 = row4.createCell(2);
				cellC4.setCellStyle(styleValue);
				cellC4.setCellValue(new HSSFRichTextString("(None)"));
				HSSFCell cellD4 = row4.createCell(3);
				cellD4.setCellStyle(styleValue);
				HSSFCell cellE4 = row4.createCell(4);
				cellE4.setCellStyle(styleValue);
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 4));
				HSSFCell cellF4 = row4.createCell(5);
				cellF4.setCellStyle(styleValue);
				HSSFCell cellG4 = row4.createCell(6);
				cellG4.setCellStyle(styleValue);
				HSSFCell cellH4 = row4.createCell(7);
				cellH4.setCellStyle(styleValue);
				HSSFCell cellI4 = row4.createCell(8);
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

	void jComboBoxSubsystems_actionPerformed(ActionEvent e) {
		org.w3c.dom.Element workElement = null;
		XeadNode workNode = null;
		String subsystemID = "";
		//
		comboBoxModelTables.removeAllElements();
		comboBoxModelFunctions.removeAllElements();
		//
		if (jComboBoxSubsystems.getSelectedIndex() > 0) {
			workNode = (XeadNode)comboBoxModelSubsystems.getSelectedItem();
			workElement = workNode.getElement();
			subsystemID = workElement.getAttribute("ID");
			//
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
			//
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
			//
			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}
		//
		comboBoxModelTables.insertElementAt("（All Tables）", 0);
		comboBoxModelTables.setSelectedItem(comboBoxModelTables.getElementAt(0));
		comboBoxModelFunctions.insertElementAt("（All Functions）", 0);
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
			TreeSet<XeadNode> treeSet = new TreeSet<XeadNode>(new NodeComparator());
			int elementCount = this.getSize();
			XeadNode node;
			for (int i = 0; i < elementCount; i++) {
				node = (XeadNode)this.getElementAt(i);
				treeSet.add(node);
			}
			this.removeAllElements();
			Iterator<XeadNode> it = treeSet.iterator();
			while( it.hasNext() ){
				node = (XeadNode)it.next();
				this.addElement(node);
			}
		}
	}

	class NodeComparator implements java.util.Comparator<XeadNode> {
		public int compare(XeadNode node1, XeadNode node2 ) {
			String value1, value2;
			value1 = node1.getElement().getAttribute("SortKey");
			value2 = node2.getElement().getAttribute("SortKey");
			int compareResult = value1.compareTo(value2);
			if (compareResult == 0) {
				value1 = node1.getElement().getAttribute("ID");
				value2 = node2.getElement().getAttribute("ID");
				compareResult = value1.compareTo(value2);
				if (compareResult == 0) {
					compareResult = 1;
				}
			}
			return(compareResult);
		}
	}

	class XeadNode {
		private String nodeType_;
		private org.w3c.dom.Element domNode_;
		//
		//Constructor//
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
