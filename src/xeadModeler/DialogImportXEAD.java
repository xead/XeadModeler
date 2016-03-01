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

public class DialogImportXEAD extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private GridLayout gridLayout1 = new GridLayout();
	private JRadioButton jRadioButtonTablesAndFunctions = new JRadioButton();
	private JRadioButton jRadioButtonTables = new JRadioButton();
	private JRadioButton jRadioButtonFunctions = new JRadioButton();
	private JRadioButton jRadioButtonTasks = new JRadioButton();
	private JRadioButton jRadioButtonDataflow = new JRadioButton();
	private ButtonGroup buttonGroup1 = new ButtonGroup();
	private JLabel jLabel3 = new JLabel();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonStart = new JButton();
	private JButton jButtonCancel = new JButton();
	private SortableXeadNodeComboBoxModel comboBoxModelBlockFrom = new SortableXeadNodeComboBoxModel();
	private SortableXeadNodeComboBoxModel comboBoxModelBlockInto = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxBlockFrom = new JComboBox(comboBoxModelBlockFrom);
	private JComboBox jComboBoxBlockInto = new JComboBox(comboBoxModelBlockInto);
	private JTextArea jTextArea1 = new JTextArea();
	private JLabel jLabel4 = new JLabel();
	private JTextField jTextFieldImportFileName = new JTextField();
	private JLabel jLabel5 = new JLabel();
	private JTextField jTextFieldImportSystemName = new JTextField();

	private String importResult = "";
	private Modeler frame_;
	private org.w3c.dom.Document domDocumentFrom;
	private org.w3c.dom.Element systemElement;
	private org.w3c.dom.Element blockElementFrom, blockElementInto;
	private String blockIDFrom, blockIDInto;
	private FileWriter fileWriter = null;
	private BufferedWriter bufferedWriter = null;

	private int updateTableCounter, createTableCounter, cancelTableCounter;
	private int updateFunctionCounter, createFunctionCounter, cancelFunctionCounter;
	private int updateTaskCounter, createTaskCounter, cancelTaskCounter, cancelRoleCounter;
	private int missingTableCounter, missingTableKeyCounter, missingFunctionCounter, missingFunctionIOCounter;

	public DialogImportXEAD(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DialogImportXEAD(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogImportXEAD01"));

		jLabel4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText(res.getString("DialogImportXEAD02"));
		jLabel4.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 350, 25));
		jTextFieldImportFileName.setEditable(false);

		jLabel5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText(res.getString("DialogImportXEAD03"));
		jLabel5.setBounds(new Rectangle(5, 43, 170, 20));
		jTextFieldImportSystemName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportSystemName.setBounds(new Rectangle(180, 40, 350, 25));
		jTextFieldImportSystemName.setEditable(false);

		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText(res.getString("DialogImportXEAD04"));
		jLabel3.setBounds(new Rectangle(5, 74, 170, 20));
		jLabel3.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setBounds(new Rectangle(180, 71, 350, 120));
		jPanel1.setLayout(gridLayout1);
		gridLayout1.setColumns(1);
		gridLayout1.setRows(5);
		jRadioButtonTablesAndFunctions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTablesAndFunctions.setText(res.getString("DialogImportXEAD05"));
		jRadioButtonTablesAndFunctions.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonTables.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTables.setText(res.getString("DialogImportXEAD46"));
		jRadioButtonTables.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonFunctions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonFunctions.setText(res.getString("DialogImportXEAD44"));
		jRadioButtonFunctions.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonTasks.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTasks.setText(res.getString("DialogImportXEAD06"));
		jRadioButtonTasks.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonDataflow.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonDataflow.setText(res.getString("S3385"));
		jRadioButtonDataflow.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jPanel1.add(jRadioButtonFunctions, null);
		jPanel1.add(jRadioButtonTables, null);
		jPanel1.add(jRadioButtonTablesAndFunctions, null);
		jPanel1.add(jRadioButtonTasks, null);
		jPanel1.add(jRadioButtonDataflow, null);
		buttonGroup1.add(jRadioButtonFunctions);
		buttonGroup1.add(jRadioButtonTables);
		buttonGroup1.add(jRadioButtonTablesAndFunctions);
		buttonGroup1.add(jRadioButtonTasks);
		buttonGroup1.add(jRadioButtonDataflow);

		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setBounds(new Rectangle(5, 194, 170, 20));
		jComboBoxBlockFrom.setBounds(new Rectangle(180, 191, 350, 25));
		jComboBoxBlockFrom.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxBlockFrom.addActionListener(new DialogImportXEAD_jComboBoxBlockFrom_actionAdapter(this));

		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setBounds(new Rectangle(5, 225, 170, 20));
		jComboBoxBlockInto.setBounds(new Rectangle(180, 222, 350, 25));
		jComboBoxBlockInto.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		//jComboBoxBlockInto.addActionListener(new DialogImportXEAD_jComboBoxBlockInto_actionAdapter(this));

		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(9, 253, 522, 173));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());

		jProgressBar.setBounds(new Rectangle(30, 440, 340, 27));
		jProgressBar.setStringPainted(true);
		jProgressBar.setVisible(false);

		jButtonStart.setBounds(new Rectangle(30, 440, 340, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogImportXEAD07"));
		jButtonStart.addActionListener(new DialogImportXEAD_jButtonStart_actionAdapter(this));
		jButtonStart.setEnabled(false);

		jButtonCancel.setBounds(new Rectangle(400, 440, 110, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogImportXEAD08"));
		jButtonCancel.addActionListener(new DialogImportXEAD_jButtonCancel_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(540, 480));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jLabel3, null);
		panelMain.add(jLabel4, null);
		panelMain.add(jLabel5, null);
		panelMain.add(jPanel1, null);
		panelMain.add(jTextFieldImportFileName, null);
		panelMain.add(jTextFieldImportSystemName, null);
		panelMain.add(jComboBoxBlockFrom, null);
		panelMain.add(jComboBoxBlockInto, null);
		panelMain.add(jTextArea1, null);
		panelMain.add(jProgressBar, null);
		panelMain.add(jButtonStart, null);
		panelMain.add(jButtonCancel, null);

		this.getContentPane().add(panelMain, BorderLayout.SOUTH);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String fileName) {
		NodeList elementList;
		org.w3c.dom.Element element;
		importResult = "";

		domDocumentFrom = null;
		jRadioButtonFunctions.setSelected(true);

		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new FileInputStream(fileName)));
			domDocumentFrom = parser.getDocument();
		} catch (IOException ex) {
		} catch (SAXException ex) {
		}

		elementList = domDocumentFrom.getElementsByTagName("System");
		element = (org.w3c.dom.Element)elementList.item(0);
		float importFileFormat = Float.parseFloat(element.getAttribute("FormatVersion"));
		float appliFormat = Float.parseFloat(DialogAbout.FORMAT_VERSION);
		if (importFileFormat > appliFormat) {
			JOptionPane.showMessageDialog(this, res.getString("S1") + element.getAttribute("FormatVersion") + res.getString("S2") + DialogAbout.FORMAT_VERSION + res.getString("S3"));
		} else {
			jTextFieldImportFileName.setText(fileName);
			jTextFieldImportSystemName.setText(element.getAttribute("Name"));
			jRadioButton_itemStateChanged();
			jProgressBar.setValue(0);
			super.setVisible(true);
		}

		return importResult;
	}

	void jRadioButton_itemStateChanged() {
		XeadNode node;
		NodeList elementList;

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			if (domDocumentFrom != null) {

				if (jRadioButtonTables.isSelected() || jRadioButtonFunctions.isSelected() || jRadioButtonTablesAndFunctions.isSelected()) {

					jLabel1.setText(res.getString("DialogImportXEAD09"));
					jLabel2.setText(res.getString("DialogImportXEAD10"));
					if (jRadioButtonTables.isSelected()) {
						jTextArea1.setText(res.getString("DialogImportXEAD47"));
					}
					if (jRadioButtonFunctions.isSelected()) {
						jTextArea1.setText(res.getString("DialogImportXEAD45"));
					}
					if (jRadioButtonTablesAndFunctions.isSelected()) {
						jTextArea1.setText(res.getString("DialogImportXEAD11"));
					}

					///////////////////
					//Setup ComboBox1//
					///////////////////
					comboBoxModelBlockFrom.removeAllElements();
					elementList = domDocumentFrom.getElementsByTagName("Subsystem");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("Subsystem",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockFrom.addElement((Object)node);
					}
					comboBoxModelBlockFrom.sortElements();
					comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));

					///////////////////
					//Setup ComboBox2//
					///////////////////
					comboBoxModelBlockInto.removeAllElements();
					elementList = frame_.domDocument.getElementsByTagName("Subsystem");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("Subsystem",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockInto.addElement((Object)node);
					}
					comboBoxModelBlockInto.sortElements();
					//comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockInto.insertElementAt("*New", 0);
					comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));
				}

				if (jRadioButtonTasks.isSelected()) {

					jLabel1.setText(res.getString("DialogImportXEAD13"));
					jLabel2.setText(res.getString("DialogImportXEAD14"));
					jTextArea1.setText(res.getString("DialogImportXEAD15"));

					///////////////////
					//Setup ComboBox1//
					///////////////////
					comboBoxModelBlockFrom.removeAllElements();
					elementList = domDocumentFrom.getElementsByTagName("Role");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("Role",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockFrom.addElement((Object)node);
					}
					comboBoxModelBlockFrom.sortElements();
					comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));

					///////////////////
					//Setup ComboBox2//
					///////////////////
					comboBoxModelBlockInto.removeAllElements();
					elementList = frame_.domDocument.getElementsByTagName("Role");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("Role",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockInto.addElement((Object)node);
					}
					comboBoxModelBlockInto.sortElements();
					//comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockInto.insertElementAt("*New", 0);
					comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));
				}

				if (jRadioButtonDataflow.isSelected()) {

					jLabel1.setText(res.getString("DialogImportXEAD52"));
					jLabel2.setText(res.getString("DialogImportXEAD53"));
					jTextArea1.setText(res.getString("DialogImportXEAD51"));

					///////////////////
					//Setup ComboBox1//
					///////////////////
					comboBoxModelBlockFrom.removeAllElements();
					elementList = domDocumentFrom.getElementsByTagName("SubjectArea");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("SubjectArea",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockFrom.addElement((Object)node);
					}
					comboBoxModelBlockFrom.sortElements();
					comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));

					///////////////////
					//Setup ComboBox2//
					///////////////////
					comboBoxModelBlockInto.removeAllElements();
					elementList = frame_.domDocument.getElementsByTagName("SubjectArea");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("SubjectArea",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockInto.addElement((Object)node);
					}
					comboBoxModelBlockInto.sortElements();
					//comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockInto.insertElementAt("*New", 0);
					comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));
				}
			}
		} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	void jComboBoxBlockFrom_actionPerformed(ActionEvent e) {
		if (jComboBoxBlockFrom.getSelectedIndex() > 0) {
			String value = jComboBoxBlockFrom.getItemAt(jComboBoxBlockFrom.getSelectedIndex()).toString();
			for (int i = 0; i < jComboBoxBlockInto.getItemCount(); i++) {
				if (jComboBoxBlockInto.getItemAt(i).toString().equals(value)) {
					jComboBoxBlockInto.setSelectedIndex(i);
					break;
				}
			}
			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}
	}
	
//	void jComboBoxBlockInto_actionPerformed(ActionEvent e) {
//		if (jComboBoxBlockFrom.getSelectedIndex() > 0 && jComboBoxBlockInto.getSelectedIndex() > 0) {
//			jButtonStart.setEnabled(true);
//		} else {
//			jButtonStart.setEnabled(false);
//		}
//	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		XeadNode workNode;
		NodeList workElementList;
		String logFileName = "";
		org.w3c.dom.Element lastElement;
		int lastID = 0;

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);

			String dateTime = getStringValueOfDateAndTime();
			File file = new File(frame_.currentFileName);
			logFileName = file.getParent() + File.separator + "XeadImportLog" + dateTime + ".txt";
			fileWriter = new FileWriter(logFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(res.getString("DialogImportXEAD01") + "(" + dateTime + ")" + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(res.getString("DialogImportXEAD02") + ":" + jTextFieldImportFileName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAD31") + ":" + frame_.currentFileName + res.getString("DialogImportXEAD41") + "\n");
			bufferedWriter.write("\n");
			if (jRadioButtonTables.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD46") + "\n");
			}
			if (jRadioButtonFunctions.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD44") + "\n");
			}
			if (jRadioButtonTablesAndFunctions.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD05") + "\n");
			}
			if (jRadioButtonTasks.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD06") + "\n");
			}
			if (jRadioButtonDataflow.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("S3385") + "\n");
			}
			bufferedWriter.write(res.getString("DialogImportXEAD03") + ":" + jTextFieldImportSystemName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAD32") + ":" + frame_.systemName + "\n");
			bufferedWriter.write("\n");

			updateTableCounter = 0;
			createTableCounter = 0;
			cancelTableCounter = 0;
			updateFunctionCounter = 0;
			createFunctionCounter = 0;
			cancelFunctionCounter = 0;
			updateTaskCounter = 0;
			createTaskCounter = 0;
			cancelTaskCounter = 0;
			cancelRoleCounter = 0;
			missingTableCounter = 0;
			missingFunctionCounter = 0;
			missingFunctionIOCounter = 0;

			workNode = (XeadNode)comboBoxModelBlockFrom.getSelectedItem();
			blockElementFrom = workNode.getElement();
			blockIDFrom = blockElementFrom.getAttribute("ID");
			bufferedWriter.write(jLabel1.getText() + ":" + blockElementFrom.getAttribute("Name") + "(" + blockElementFrom.getAttribute("SortKey") + ")" + "\n");

			workElementList = frame_.domDocument.getElementsByTagName("System");
			systemElement = (org.w3c.dom.Element)workElementList.item(0);

			//////////////////////////////
			// Setup the target element //
			//////////////////////////////
			if (jComboBoxBlockInto.getSelectedIndex() == 0) {
				if (jRadioButtonTables.isSelected()
						|| jRadioButtonFunctions.isSelected()
						|| jRadioButtonTablesAndFunctions.isSelected()) {
					blockElementInto = frame_.domDocument.createElement("Subsystem");
					lastElement = getLastDomElementOfTheType("Subsystem");
					if (lastElement == null) {
						lastID = 0;
					} else {
						lastID = Integer.parseInt(lastElement.getAttribute("ID"));
					}
				}
				if (jRadioButtonTasks.isSelected()) {
					blockElementInto = frame_.domDocument.createElement("Role");
					lastElement = getLastDomElementOfTheType("Role");
					if (lastElement == null) {
						lastID = 0;
					} else {
						lastID = Integer.parseInt(lastElement.getAttribute("ID"));
					}
				}
				if (jRadioButtonDataflow.isSelected()) {
					blockElementInto = frame_.domDocument.createElement("SubjectArea");
					lastElement = getLastDomElementOfTheType("SubjectArea");
					if (lastElement == null) {
						lastID = 0;
					} else {
						lastID = Integer.parseInt(lastElement.getAttribute("ID"));
					}
				}
				blockElementInto.setAttribute("ID", Integer.toString(lastID + 1));
				systemElement.appendChild(blockElementInto);
				bufferedWriter.write(jLabel2.getText() + ":*New\n");
			} else {
				workNode = (XeadNode)comboBoxModelBlockInto.getSelectedItem();
				blockElementInto = workNode.getElement();
				bufferedWriter.write(jLabel2.getText() + ":" + blockElementInto.getAttribute("Name") + "(" + blockElementInto.getAttribute("SortKey") + ")" + "\n");
			}
			blockIDInto = blockElementInto.getAttribute("ID");

			//////////////////////
			// Importing tables //
			//////////////////////
			if (jRadioButtonTables.isSelected()) {
				importSubsystem();
				importTables();
				importSubsystemAttributesOfTable();
				importResult = res.getString("DialogImportXEAD16") +
				updateTableCounter + res.getString("DialogImportXEAD17") +
				createTableCounter + res.getString("DialogImportXEAD18") +
				cancelTableCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD27") + missingTableKeyCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			/////////////////////////
			// Importing functions //
			/////////////////////////
			if (jRadioButtonFunctions.isSelected()) {
				importSubsystem();
				importFunctions();
				importResult = res.getString("DialogImportXEAD19") +
				updateFunctionCounter + res.getString("DialogImportXEAD17") +
				createFunctionCounter + res.getString("DialogImportXEAD18") +
				cancelFunctionCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD20") + missingTableCounter + res.getString("DialogImportXEAD21") + "\n" + res.getString("DialogImportXEAD22") + missingFunctionCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			////////////////////////////////////
			// Importing tables and functions //
			////////////////////////////////////
			if (jRadioButtonTablesAndFunctions.isSelected()) {
				importSubsystem();
				importTables();
				importSubsystemAttributesOfTable();
				importFunctions();
				importResult = res.getString("DialogImportXEAD16") +
				updateTableCounter + res.getString("DialogImportXEAD17") +
				createTableCounter + res.getString("DialogImportXEAD18") +
				cancelTableCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD27") +
				missingTableKeyCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD19") +
				updateFunctionCounter + res.getString("DialogImportXEAD17") +
				createFunctionCounter + res.getString("DialogImportXEAD18") +
				cancelFunctionCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD20") +
				missingTableCounter + res.getString("DialogImportXEAD21") + "\n" +
				res.getString("DialogImportXEAD22") +
				missingFunctionCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			/////////////////////
			// Importing tasks //
			/////////////////////
			if (jRadioButtonTasks.isSelected()) {
				importRole();
				importTasks();
				importResult = res.getString("DialogImportXEAD24") +
				updateTaskCounter + res.getString("DialogImportXEAD17") +
				createTaskCounter + res.getString("DialogImportXEAD18") +
				cancelTaskCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD25") +
				missingFunctionIOCounter + res.getString("DialogImportXEAD26") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			///////////////////////////
			// Importing a data-flow //
			///////////////////////////
			if (jRadioButtonDataflow.isSelected()) {
				importDataflow();
				if (cancelTaskCounter == 0 && cancelRoleCounter == 0) {
					importResult = res.getString("DialogImportXEAD55") + "\n" +
					res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
				} else {
					importResult = res.getString("DialogImportXEAD56") + "\n" +
					res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
				}
			}
		}
		catch (Exception ex1) {
			try {
				bufferedWriter.close();
			} catch (Exception ex2) {}
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.setVisible(false);
		}
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception ex3) {
			try {
				bufferedWriter.close();
			} catch (Exception ex4) {}
		}
	}

	void importTables() {
		org.w3c.dom.Element workElement;
		NodeList workElementList, targetElementList;
		org.w3c.dom.Element[] elementListFrom = new org.w3c.dom.Element[500];
		int itemsOfElementListFrom = 0;
		int checkCounter = 0;
		String sortKeyOfDefinition;

		//////////////////////////
		//Import Table/Data Type//
		//////////////////////////
		importTypeDefinitionWithTagName("TableType");
		importTypeDefinitionWithTagName("DataType");

		///////////////////////////////////////
		//Setup List of Tables to be imported//
		///////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (!workElement.getAttribute("SortKey").equals("") && workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				elementListFrom[itemsOfElementListFrom] = workElement;
				itemsOfElementListFrom++;
			}
		}

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		/////////////////////////////////////////////////
		//Import Table definitions into target document//
		/////////////////////////////////////////////////
		targetElementList = frame_.domDocument.getElementsByTagName("Table");
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			checkCounter = 0;
			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");
			for (int n = 0; n < targetElementList.getLength(); n++) {
				workElement = (org.w3c.dom.Element)targetElementList.item(n);
				if (workElement.getAttribute("SortKey").equals(sortKeyOfDefinition)) {
					checkCounter++;
					if (workElement.getAttribute("SubsystemID").equals(blockIDInto)) {
						try {
							bufferedWriter.write(elementListFrom[i].getAttribute("Name") +
									"(" + elementListFrom[i].getAttribute("SortKey") +
									"):" + res.getString("DialogImportXEAD33") + "\n");
						} catch (IOException ex1) {}
						updateTableDefinition(elementListFrom[i], workElement);
						updateTableCounter++;
					} else {
						try {
							bufferedWriter.write(elementListFrom[i].getAttribute("Name") +
									"(" + elementListFrom[i].getAttribute("SortKey") +
									"):" + res.getString("DialogImportXEAD48") + "\n");
						} catch (IOException ex1) {}
						cancelTableCounter++;
					}
					break;
				}
			}

			if (checkCounter == 0) {
				createTableDefinition(elementListFrom[i], blockIDInto);
				createTableCounter++;
				try {
					bufferedWriter.write(elementListFrom[i].getAttribute("Name") + "(" +
							elementListFrom[i].getAttribute("SortKey") +
							"):" + res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex2) {}
			}
		}

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}

		//////////////////////////////////////////////////
		//Import Table Relationship into target document//
		//////////////////////////////////////////////////
		String table1ID, table2ID, tableID;

		workElementList = domDocumentFrom.getElementsByTagName("Relationship");
		jProgressBar.setValue(0);
		jProgressBar.setString("Importing Table Relationships");
		jProgressBar.setMaximum(workElementList.getLength());

		for (int i = 0; i < workElementList.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement = (org.w3c.dom.Element)workElementList.item(i);
			table1ID = workElement.getAttribute("Table1ID");
			table2ID = workElement.getAttribute("Table2ID");
			checkCounter = 0;
			for (int m = 0; m < itemsOfElementListFrom; m++) {
				tableID = elementListFrom[m].getAttribute("ID");
				if (tableID.equals(table1ID)) {
					checkCounter++;
				}
				if (tableID.equals(table2ID)) {
					checkCounter++;
				}
			}
			if (checkCounter >= 1) {
				adjustTableRelationship(workElement);
			}
		}
	}

	void importFunctions() {
		org.w3c.dom.Element workElement;
		NodeList workElementList;
		org.w3c.dom.Element[] elementListFrom = new org.w3c.dom.Element[500];
		int itemsOfElementListFrom = 0;
		int checkCounter = 0;
		String sortKeyOfDefinition;

		////////////////////////
		//Import Function Type//
		////////////////////////
		importTypeDefinitionWithTagName("FunctionType");

		//////////////////////////////////////////
		//Setup List of Functions to be imported//
		/////////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Function");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (!workElement.getAttribute("SortKey").equals("") && workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				elementListFrom[itemsOfElementListFrom] = workElement;
				itemsOfElementListFrom++;
			}
		}

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		////////////////////////////////////////////////////
		//Import Function definitions into target document//
		////////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		ArrayList<org.w3c.dom.Element> targetFunctionArray = new ArrayList<org.w3c.dom.Element>(); 
		workElementList = frame_.domDocument.getElementsByTagName("Function");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (workElement.getAttribute("SubsystemID").equals(blockIDInto)) {
				targetFunctionArray.add(workElement);
			}
		}
		
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			checkCounter = 0;
			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");

			for (int n = 0; n < targetFunctionArray.size(); n++) {
				workElement = targetFunctionArray.get(n);
				if (workElement.getAttribute("SortKey").equals(sortKeyOfDefinition)) {
					checkCounter++;
					try {
						bufferedWriter.write(elementListFrom[i].getAttribute("Name") +
								"(" + sortKeyOfDefinition +
								"):" + res.getString("DialogImportXEAD33") + "\n");
					} catch (IOException ex1) {}

					updateFunctionDefinition(elementListFrom[i], workElement);
					updateFunctionCounter++;
					break;
				}
			}

			if (checkCounter == 0) {
				try {
					bufferedWriter.write(elementListFrom[i].getAttribute("Name") + "(" +
							elementListFrom[i].getAttribute("SortKey") + "):" +
							res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex2) {}

				createFunctionDefinition(elementListFrom[i], blockIDInto);
				createFunctionCounter++;
			}
		}
		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}

		////////////////////////////////////////////////
		//Import Functions-called into target document//
		////////////////////////////////////////////////
		jProgressBar.setString("Checking functions called");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		for (int i = 0; i < itemsOfElementListFrom; i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");
			for (int n = 0; n < targetFunctionArray.size(); n++) {
				workElement = (org.w3c.dom.Element)targetFunctionArray.get(n);
				if (workElement.getAttribute("SortKey").equals(sortKeyOfDefinition)) {
					adjustFunctionsCalled(elementListFrom[i], workElement);
					break;
				}
			}
		}
	}

	void importRole() {
		//////////////////////
		//Import Departments//
		//////////////////////
		importTypeDefinitionWithTagName("Department");

		////////////////////////////////////
		//Update attributes of target Role//
		////////////////////////////////////
		blockElementInto.setAttribute("Name", blockElementFrom.getAttribute("Name"));
		blockElementInto.setAttribute("SortKey", blockElementFrom.getAttribute("SortKey"));
		blockElementInto.setAttribute("Descriptions", blockElementFrom.getAttribute("Descriptions"));
		blockElementInto.setAttribute("DepartmentID", convertInternalIDOfTheTypeTag(blockElementFrom.getAttribute("DepartmentID"), "Department"));
	}

	void importTasks() {
		org.w3c.dom.Element workElement;
		NodeList workElementList, targetElementList;
		org.w3c.dom.Element[] elementListFrom = new org.w3c.dom.Element[500];
		int itemsOfElementListFrom = 0;
		int checkCounter = 0;
		String sortKeyOfDefinition;

		///////////////////
		//Import TaskType//
		///////////////////
		importTypeDefinitionWithTagName("TaskType");

		//////////////////////////////////////
		//Setup List of Tasks to be imported//
		//////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Task");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (!workElement.getAttribute("SortKey").equals("") && workElement.getAttribute("RoleID").equals(blockIDFrom)) {
				elementListFrom[itemsOfElementListFrom] = workElement;
				itemsOfElementListFrom++;
			}
		}

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		////////////////////////////////////////////////
		//Import Task definitions into target document//
		////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		targetElementList = frame_.domDocument.getElementsByTagName("Task");
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + workElementList.getLength());
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			try {
				bufferedWriter.write(elementListFrom[i].getAttribute("Name") + "(" +
						elementListFrom[i].getAttribute("SortKey") + "):");
			} catch (IOException ex1) {}

			checkCounter = 0;
			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");
			for (int n = 0; n < targetElementList.getLength(); n++) {
				workElement = (org.w3c.dom.Element)targetElementList.item(n);
				if (workElement.getAttribute("SortKey").equals(sortKeyOfDefinition)) {
					checkCounter++;
					if (workElement.getAttribute("RoleID").equals(blockIDInto)) {
						try {
							bufferedWriter.write(res.getString("DialogImportXEAD33") + "\n");
						} catch (IOException ex2) {}

						updateTaskDefinition(elementListFrom[i], workElement);
						updateTaskCounter++;
					} else {
						try {
							bufferedWriter.write(res.getString("DialogImportXEAD49") + "\n");
						} catch (IOException ex2) {}

						cancelTaskCounter++;
					}
					break;
				}
			}

			if (checkCounter == 0) {
				try {
					bufferedWriter.write(res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex3) {}

				createTaskDefinition(elementListFrom[i], blockIDInto);
				createTaskCounter++;
			}
		}
	}
	
	String checkTaskDefinition(org.w3c.dom.Element nodeElementFrom) {
		String targetTaskID = "";
		String taskSortKey = "";
		org.w3c.dom.Element workElement;
		NodeList taskListFrom = domDocumentFrom.getElementsByTagName("Task");
		for (int i = 0; i < taskListFrom.getLength(); i++) {
			workElement = (org.w3c.dom.Element)taskListFrom.item(i);
			if (workElement.getAttribute("ID").equals(nodeElementFrom.getAttribute("TaskID"))) {
				taskSortKey = workElement.getAttribute("SortKey");
			}
		}
		NodeList targetElementList = frame_.domDocument.getElementsByTagName("Task");
		for (int i = 0; i < targetElementList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)targetElementList.item(i);
				if (workElement.getAttribute("SortKey").equals(taskSortKey)) {
					targetTaskID = workElement.getAttribute("ID");
					break;
				}
		}
		return targetTaskID;
	}
	
	String checkRoleDefinition(org.w3c.dom.Element nodeElementFrom) {
		String targetRoleID = "";
		String roleSortKey = "";
		org.w3c.dom.Element workElement;
		NodeList roleListFrom = domDocumentFrom.getElementsByTagName("Role");
		for (int i = 0; i < roleListFrom.getLength(); i++) {
			workElement = (org.w3c.dom.Element)roleListFrom.item(i);
			if (workElement.getAttribute("ID").equals(nodeElementFrom.getAttribute("RoleID"))) {
				roleSortKey = workElement.getAttribute("SortKey");
			}
		}
		NodeList targetElementList = frame_.domDocument.getElementsByTagName("Role");
		for (int i = 0; i < targetElementList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)targetElementList.item(i);
				if (workElement.getAttribute("SortKey").equals(roleSortKey)) {
					targetRoleID = workElement.getAttribute("ID");
					break;
				}
		}
		return targetRoleID;
	}

	void importDataflow() {
		org.w3c.dom.Element workElement1, workElement2, newElement, lastElement;
		NodeList workElementList;
		int lastID;
		String taskIDInto, roleIDInto;
		ArrayList<String> nodeIDListFrom = new ArrayList<String>();
		ArrayList<String> nodeIDListInto = new ArrayList<String>();
		
		workElementList = blockElementFrom.getElementsByTagName("DataflowNode");
		int countOfElements = workElementList.getLength() * 2;
		workElementList = blockElementFrom.getElementsByTagName("DataflowLine");
		countOfElements = countOfElements + workElementList.getLength();
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(countOfElements);
		
		///////////////////////////////////////////////////////////////
		// Check if task(process) definitions exist in target system //
		///////////////////////////////////////////////////////////////
		workElementList = blockElementFrom.getElementsByTagName("DataflowNode");
		for (int i = 0; i < workElementList.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement1 = (org.w3c.dom.Element)workElementList.item(i);
			if (workElement1.getAttribute("Type").equals("Process")) {
				taskIDInto = checkTaskDefinition(workElement1);
				if (taskIDInto.equals("")) {
					cancelTaskCounter++;
					try {
						NodeList taskListFrom = domDocumentFrom.getElementsByTagName("Task");
						for (int j = 0; j < taskListFrom.getLength(); j++) {
							workElement2 = (org.w3c.dom.Element)taskListFrom.item(j);
							if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TaskID"))) {
								bufferedWriter.write(workElement2.getAttribute("Name") + "(" +
										workElement2.getAttribute("SortKey") + 
										"):" + res.getString("DialogImportXEAD54") + "\n");
							}
						}
					}
					catch (IOException ex1) {}
					break;
				}
			}
			if (workElement1.getAttribute("Type").equals("Subject")
					&& !workElement1.getAttribute("RoleID").equals("")) {
				roleIDInto = checkRoleDefinition(workElement1);
				if (roleIDInto.equals("")) {
					cancelRoleCounter++;
					try {
						NodeList roleListFrom = domDocumentFrom.getElementsByTagName("Role");
						for (int j = 0; j < roleListFrom.getLength(); j++) {
							workElement2 = (org.w3c.dom.Element)roleListFrom.item(j);
							if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("RoleID"))) {
								bufferedWriter.write(workElement2.getAttribute("Name") + "(" +
										workElement2.getAttribute("SortKey") + 
										"):" + res.getString("DialogImportXEAD57") + "\n");
							}
						}
					}
					catch (IOException ex1) {}
					break;
				}
			}
		}
		
		if (cancelTaskCounter == 0 && cancelRoleCounter == 0) {
			
			////////////////////////////////////////
			//Update attributes of target DataFlow//
			////////////////////////////////////////
			blockElementInto.setAttribute("Name", blockElementFrom.getAttribute("Name"));
			blockElementInto.setAttribute("Descriptions", blockElementFrom.getAttribute("Descriptions"));
			blockElementInto.setAttribute("BoundaryPosition", blockElementFrom.getAttribute("BoundaryPosition"));
			blockElementInto.setAttribute("BoundarySize", blockElementFrom.getAttribute("BoundarySize"));
			blockElementInto.setAttribute("SortKey", blockElementFrom.getAttribute("SortKey"));

			workElementList = blockElementInto.getChildNodes();
			int countOfChild = workElementList.getLength();
			for (int i = 0; i < countOfChild; i++) {
				blockElementInto.removeChild(blockElementInto.getFirstChild());
			}

			workElementList = blockElementFrom.getElementsByTagName("DataflowNode");
			for (int i = 0; i < workElementList.getLength(); i++) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				workElement1 = (org.w3c.dom.Element)workElementList.item(i);

				newElement = frame_.domDocument.createElement("DataflowNode");
				lastElement = getLastDomElementOfTheType("DataflowNode");
				if (lastElement == null) {
					lastID = 0;
				} else {
					lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				}
				newElement.setAttribute("ID", Integer.toString(lastID + 1));
				newElement.setAttribute("Name", workElement1.getAttribute("Name"));
				newElement.setAttribute("Position", workElement1.getAttribute("Position"));
				newElement.setAttribute("SlideNumber", workElement1.getAttribute("SlideNumber"));
				if (workElement1.getAttribute("Type").equals("Process")) {
					taskIDInto = checkTaskDefinition(workElement1);
					newElement.setAttribute("TaskID", taskIDInto);
					newElement.setAttribute("EventPos", workElement1.getAttribute("EventPos"));
				} else {
					if (workElement1.getAttribute("Type").equals("Subject")
							&& !workElement1.getAttribute("RoleID").equals("")) {
						roleIDInto = checkRoleDefinition(workElement1);
						newElement.setAttribute("RoleID", roleIDInto);
					} else {
						newElement.setAttribute("NameExt", workElement1.getAttribute("NameExt"));
					}
				}
				newElement.setAttribute("Type", workElement1.getAttribute("Type"));
				newElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
				blockElementInto.appendChild(newElement);

				nodeIDListFrom.add(workElement1.getAttribute("ID"));
				nodeIDListInto.add(newElement.getAttribute("ID"));
			}

			workElementList = blockElementFrom.getElementsByTagName("DataflowLine");
			for (int i = 0; i < workElementList.getLength(); i++) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				workElement1 = (org.w3c.dom.Element)workElementList.item(i);

				newElement = frame_.domDocument.createElement("DataflowLine");
				lastElement = getLastDomElementOfTheType("DataflowLine");
				if (lastElement == null) {
					lastID = 0;
				} else {
					lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				}
				newElement.setAttribute("ID", Integer.toString(lastID + 1));
				newElement.setAttribute("Name", workElement1.getAttribute("Name"));
				newElement.setAttribute("NameExt", workElement1.getAttribute("NameExt"));
				newElement.setAttribute("ImageType", workElement1.getAttribute("ImageType"));
				newElement.setAttribute("NodeID1", nodeIDListInto.get(nodeIDListFrom.indexOf(workElement1.getAttribute("NodeID1"))));
				newElement.setAttribute("NodeID2", nodeIDListInto.get(nodeIDListFrom.indexOf(workElement1.getAttribute("NodeID2"))));
				newElement.setAttribute("ShowArrow1", workElement1.getAttribute("ShowArrow1"));
				newElement.setAttribute("ShowArrow2", workElement1.getAttribute("ShowArrow2"));
				newElement.setAttribute("TerminalPosIndex1", workElement1.getAttribute("TerminalPosIndex1"));
				newElement.setAttribute("TerminalPosIndex2", workElement1.getAttribute("TerminalPosIndex2"));
				newElement.setAttribute("SlideNumber", workElement1.getAttribute("SlideNumber"));
				blockElementInto.appendChild(newElement);
			}
			try {
				bufferedWriter.write(res.getString("DialogImportXEAD33") + "\n");
			} catch (IOException ex3) {
			}
		}
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

	void updateFunctionDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		String internalID;

		elementInto.setAttribute("Name", elementFrom.getAttribute("Name"));
		elementInto.setAttribute("Summary", elementFrom.getAttribute("Summary"));
		elementInto.setAttribute("Parameters", elementFrom.getAttribute("Parameters"));
		elementInto.setAttribute("Return", elementFrom.getAttribute("Return"));
		elementInto.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));
		internalID = convertInternalIDOfTheTypeTag(elementFrom.getAttribute("FunctionTypeID"), "FunctionType");
		elementInto.setAttribute("FunctionTypeID", internalID);

		adjustIOPanelDefinition(elementFrom, elementInto);
		adjustIOWebPageDefinition(elementFrom, elementInto);
		adjustIOSpoolDefinition(elementFrom, elementInto);
		adjustIOTableDefinition(elementFrom, elementInto);
	}

	void adjustIOPanelDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2, elementList3;

		elementList1 = elementFrom.getElementsByTagName("IOPanel");
		elementList2 = elementInto.getElementsByTagName("IOPanel");
		int workCount = elementList2.getLength();
		for (int i = 0; i < workCount; i++) {
			workElement1 = (org.w3c.dom.Element)elementList2.item(0);
			elementInto.removeChild(workElement1);
		}

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);

			//////////////////
			//Create IOPanel//
			//////////////////
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOPanel");

			childElement.setAttribute("ID", Integer.toString(getNextIDOfFunctionIOs()));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("ImageText", workElement1.getAttribute("ImageText"));
			childElement.setAttribute("Background", workElement1.getAttribute("Background"));
			childElement.setAttribute("Size", workElement1.getAttribute("Size"));
			childElement.setAttribute("FontSize", workElement1.getAttribute("FontSize"));

			elementList3 = workElement1.getElementsByTagName("IOPanelStyle");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOPanelStyle");
				grandChildElement.setAttribute("Value", workElement2.getAttribute("Value"));
				childElement.appendChild(grandChildElement);
			}

			elementList3 = workElement1.getElementsByTagName("IOPanelField");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOPanelField");
				grandChildElement.setAttribute("Name", workElement2.getAttribute("Name"));
				grandChildElement.setAttribute("Label", workElement2.getAttribute("Label"));
				grandChildElement.setAttribute("IOType", workElement2.getAttribute("IOType"));
				grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}

			elementInto.appendChild(childElement);
		}
	}

	void adjustIOWebPageDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2, elementList3;

		elementList1 = elementFrom.getElementsByTagName("IOWebPage");
		elementList2 = elementInto.getElementsByTagName("IOWebPage");
		int workCount = elementList2.getLength();
		for (int i = 0; i < workCount; i++) {
			workElement1 = (org.w3c.dom.Element)elementList2.item(0);
			elementInto.removeChild(workElement1);
		}

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);

			////////////////////
			//Create IOWebPage//
			////////////////////
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOWebPage");

			childElement.setAttribute("ID", Integer.toString(getNextIDOfFunctionIOs()));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("FileName", workElement1.getAttribute("FileName"));

			elementList3 = workElement1.getElementsByTagName("IOWebPageField");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOWebPageField");
				grandChildElement.setAttribute("Name", workElement2.getAttribute("Name"));
				grandChildElement.setAttribute("Label", workElement2.getAttribute("Label"));
				grandChildElement.setAttribute("IOType", workElement2.getAttribute("IOType"));
				grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}

			elementInto.appendChild(childElement);
		}
	}

	void adjustIOSpoolDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2, elementList3;

		elementList1 = elementFrom.getElementsByTagName("IOSpool");
		elementList2 = elementInto.getElementsByTagName("IOSpool");
		int workCount = elementList2.getLength();
		for (int i = 0; i < workCount; i++) {
			workElement1 = (org.w3c.dom.Element)elementList2.item(0);
			elementInto.removeChild(workElement1);
		}

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);

			//////////////////
			//Create IOSpool//
			//////////////////
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOSpool");

			childElement.setAttribute("ID", Integer.toString(getNextIDOfFunctionIOs()));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("ImageText", workElement1.getAttribute("ImageText"));
			childElement.setAttribute("Background", workElement1.getAttribute("Background"));
			childElement.setAttribute("Size", workElement1.getAttribute("Size"));
			childElement.setAttribute("FontSize", workElement1.getAttribute("FontSize"));

			elementList3 = workElement1.getElementsByTagName("IOSpoolStyle");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOSpoolStyle");
				grandChildElement.setAttribute("Value", workElement2.getAttribute("Value"));
				childElement.appendChild(grandChildElement);
			}

			elementList3 = workElement1.getElementsByTagName("IOSpoolField");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOSpoolField");
				grandChildElement.setAttribute("Name", workElement2.getAttribute("Name"));
				grandChildElement.setAttribute("Label", workElement2.getAttribute("Label"));
				grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}

			elementInto.appendChild(childElement);
		}
	}

	void adjustIOTableDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element ioTableElementFrom, workElement;
		NodeList elementListFrom, elementListInto, workElementList;
		org.w3c.dom.Element tableElementFrom, tableElementInto;
		String internalTableIDFrom = "";
		String internalTableIDInto = "";
		String internalFieldIDInto = "";
		String tableName = "";
		String tableSortKey = "";

		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		NodeList tableListInto = frame_.domDocument.getElementsByTagName("Table");

		elementListFrom = elementFrom.getElementsByTagName("IOTable");

		elementListInto = elementInto.getElementsByTagName("IOTable");
		int workCount = elementListInto.getLength();
		for (int i = 0; i < workCount; i++) {
			workElement = (org.w3c.dom.Element)elementListInto.item(0);
			elementInto.removeChild(workElement);
		}

		for (int i = 0; i < elementListFrom.getLength(); i++) {
			
			ioTableElementFrom = (org.w3c.dom.Element)elementListFrom.item(i);
			internalTableIDFrom = ioTableElementFrom.getAttribute("TableID");
			internalTableIDInto = convertInternalIDOfTheTag(internalTableIDFrom, "Table");

			tableElementInto = null;
			if (!internalTableIDInto.equals("")) {
				for (int j = 0; j < tableListInto.getLength(); j++) {
					workElement = (org.w3c.dom.Element)tableListInto.item(j);
					if (internalTableIDInto.equals(workElement.getAttribute("ID"))) {
						tableElementInto = workElement;
						break;
					}
				}
			}

			//////////////////
			//Create IOTable//
			//////////////////
			if (tableElementInto == null) {
				missingTableCounter++;
				for (int m = 0; m < tableListFrom.getLength(); m++) {
					tableElementFrom = (org.w3c.dom.Element)tableListFrom.item(m);
					if (internalTableIDFrom.equals(tableElementFrom.getAttribute("ID"))) {
						tableName = tableElementFrom.getAttribute("Name");
						tableSortKey = tableElementFrom.getAttribute("SortKey");
						break;
					}
				}
				try {
					bufferedWriter.write("  " + tableName + "(" + tableSortKey + ")" + res.getString("DialogImportXEAD37") + "\n");
				} catch (IOException ex) {}
			} else {
				org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");

				childElement.setAttribute("ID", Integer.toString(getNextIDOfFunctionIOs()));
				childElement.setAttribute("Position", ioTableElementFrom.getAttribute("Position"));
				childElement.setAttribute("NameExtension", ioTableElementFrom.getAttribute("NameExtension"));
				childElement.setAttribute("SortKey", ioTableElementFrom.getAttribute("SortKey"));
				childElement.setAttribute("Descriptions", ioTableElementFrom.getAttribute("Descriptions"));
				childElement.setAttribute("TableID", internalTableIDInto);
				childElement.setAttribute("OpC", ioTableElementFrom.getAttribute("OpC"));
				childElement.setAttribute("OpR", ioTableElementFrom.getAttribute("OpR"));
				childElement.setAttribute("OpU", ioTableElementFrom.getAttribute("OpU"));
				childElement.setAttribute("OpD", ioTableElementFrom.getAttribute("OpD"));

				///////////////////////
				//Create IOTableField//
				///////////////////////
				workElementList = ioTableElementFrom.getElementsByTagName("IOTableField");
				for (int m = 0; m < workElementList.getLength(); m++) {
					workElement = (org.w3c.dom.Element)workElementList.item(m);
					internalFieldIDInto = convertInternalIDOfTableField(internalTableIDFrom, workElement.getAttribute("FieldID"), domDocumentFrom, frame_.domDocument);
					if (!workElement.getAttribute("Descriptions").equals("") && !internalFieldIDInto.equals("")) {
						org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOTableField");
						grandChildElement.setAttribute("FieldID", internalFieldIDInto);
						grandChildElement.setAttribute("Descriptions", workElement.getAttribute("Descriptions"));
						childElement.appendChild(grandChildElement);
					}
				}
				elementInto.appendChild(childElement);
			}

			setupSubsystemAttributeOfTable(internalTableIDInto, blockElementInto);
		}
	}

	void setupSubsystemAttributeOfTable(String internalTableIDInto, org.w3c.dom.Element subsystemElementInto) {
		org.w3c.dom.Element workElement1, workElement2, workElement3;
		NodeList relationshipListInto, subsystemRelationshipListInto;

		int checkCounter = 0;
		NodeList subsystemTableListInto = subsystemElementInto.getElementsByTagName("SubsystemTable");
		for (int i = 0; i < subsystemTableListInto.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)subsystemTableListInto.item(i);
			if (workElement1.getAttribute("TableID").equals(internalTableIDInto)) {
				checkCounter++;
			}
		}
		if (checkCounter == 0) {

			/////////////////////////////////////////
			//add subsystem attributes of the table//
			/////////////////////////////////////////
			org.w3c.dom.Element newElement = frame_.domDocument.createElement("SubsystemTable");
			newElement.setAttribute("TableID", internalTableIDInto);
			newElement.setAttribute("BoxPosition", "50,50");
			newElement.setAttribute("ExtDivLoc", "600");
			newElement.setAttribute("IntDivLoc", "300");
			newElement.setAttribute("ShowOnModel", "false");
			newElement.setAttribute("ShowInstance", "false");
			newElement.setAttribute("Instance", "");
			subsystemElementInto.appendChild(newElement);

			subsystemTableListInto = subsystemElementInto.getElementsByTagName("SubsystemTable");
			relationshipListInto = frame_.domDocument.getElementsByTagName("Relationship");
			subsystemRelationshipListInto = subsystemElementInto.getElementsByTagName("SubsystemRelationship");

			//////////////////////////////
			//add subsystem relationship//
			//////////////////////////////
			for (int k = 0; k < relationshipListInto.getLength(); k++) {
				workElement2 = (org.w3c.dom.Element)relationshipListInto.item(k);
				checkCounter = 0;
				if (workElement2.getAttribute("Table1ID").equals(internalTableIDInto)) {
					for (int j = 0; j < subsystemTableListInto.getLength(); j++) {
						workElement3 = (org.w3c.dom.Element)subsystemTableListInto.item(j);
						if (workElement3.getAttribute("TableID").equals(workElement2.getAttribute("Table2ID"))) {
							checkCounter++;
						}
					}
				}
				if (workElement2.getAttribute("Table2ID").equals(internalTableIDInto)) {
					for (int j = 0; j < subsystemTableListInto.getLength(); j++) {
						workElement3 = (org.w3c.dom.Element)subsystemTableListInto.item(j);
						if (workElement3.getAttribute("TableID").equals(workElement2.getAttribute("Table1ID"))) {
							checkCounter++;
						}
					}
				}
				if (checkCounter > 0) {
					checkCounter = 0;
					for (int j = 0; j < subsystemRelationshipListInto.getLength(); j++) {
						workElement3 = (org.w3c.dom.Element)subsystemRelationshipListInto.item(j);
						if (workElement3.getAttribute("RelationshipID").equals(workElement2.getAttribute("ID"))) {
							checkCounter++;
						}
					}
					if (checkCounter == 0) {
						org.w3c.dom.Element newRelElement = frame_.domDocument.createElement("SubsystemRelationship");
						newRelElement.setAttribute("RelationshipID", workElement2.getAttribute("ID"));
						newRelElement.setAttribute("TerminalIndex1", "-1");
						newRelElement.setAttribute("TerminalIndex2", "-1");
						subsystemElementInto.appendChild(newRelElement);
					}
				}
			}
		}
	}

	void updateTaskDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		//////////////////////////
		//Update Task Definition//
		//////////////////////////
		elementInto.setAttribute("Name", elementFrom.getAttribute("Name"));
		elementInto.setAttribute("Event", elementFrom.getAttribute("Event"));
		elementInto.setAttribute("TaskTypeID", convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TaskTypeID"), "TaskType"));
		elementInto.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));

		/////////////////////////////////
		//Purge and create Task Actions//
		/////////////////////////////////
		purgeChildElements(elementInto, "TaskAction");
		createTaskActions(elementFrom, elementInto);
	}

	void purgeChildElements(org.w3c.dom.Element parentElement, String childTagNameToBePurged) {
		org.w3c.dom.Element workElement;
		NodeList workElementList;
		org.w3c.dom.Element[] elementArray1 = new org.w3c.dom.Element[1000];
		int arrayIndex1 = -1;
		org.w3c.dom.Node parentDomNode;

		workElementList = parentElement.getElementsByTagName(childTagNameToBePurged);
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			arrayIndex1++;
			elementArray1[arrayIndex1] = workElement;
		}

		for (int i = 0; i <= arrayIndex1; i++) {
			parentDomNode = elementArray1[i].getParentNode();
			parentDomNode.removeChild(elementArray1[i]);
		}
	}

	void updateTableDefinition(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element workElement1, workElement2, workElement3, lastElement;
		NodeList elementList;
		int lastID = 0;
		int checkCounter = 0;
		String workStr = "";
		ArrayList<org.w3c.dom.Element> ioTableList = new ArrayList<org.w3c.dom.Element>();
		String fieldID = "";

		String tableIDFrom = elementFrom.getAttribute("ID");
		String tableIDInto = elementInto.getAttribute("ID");
		NodeList fieldListFrom = elementFrom.getElementsByTagName("TableField");
		NodeList fieldListInto = elementInto.getElementsByTagName("TableField");
		NodeList keyListFrom = elementFrom.getElementsByTagName("TableKey");
		NodeList keyListInto = elementInto.getElementsByTagName("TableKey");
		NodeList relationshipListInto = frame_.domDocument.getElementsByTagName("Relationship");

		elementList = frame_.domDocument.getElementsByTagName("IOTable");
		for (int i = 0; i < elementList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList.item(i);
			if (tableIDInto.equals(workElement1.getAttribute("TableID"))) {
				ioTableList.add(workElement1);
			}
		}

		///////////////////////////
		//Update Table Attributes//
		///////////////////////////
		elementInto.setAttribute("Name", elementFrom.getAttribute("Name"));
		elementInto.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));
		elementInto.setAttribute("TableTypeID", convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TableTypeID"), "TableType"));

		/////////////////////
		//Adjust TableField//
		/////////////////////
		for (int i = 0; i < fieldListFrom.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)fieldListFrom.item(i);
			workStr = convertInternalIDOfTableField(tableIDFrom, workElement1.getAttribute("ID"), domDocumentFrom, frame_.domDocument);

			checkCounter = 0;
			for (int j = 0; j < fieldListInto.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)fieldListInto.item(j);
				if (workStr.equals(workElement2.getAttribute("ID"))) {
					workElement2.setAttribute("Name", workElement1.getAttribute("Name"));
					workElement2.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
					workElement2.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
					workElement2.setAttribute("Alias", workElement1.getAttribute("Alias"));
					workElement2.setAttribute("AttributeType", workElement1.getAttribute("AttributeType"));
					workElement2.setAttribute("DataTypeID", convertInternalIDOfTheTypeTag(workElement1.getAttribute("DataTypeID"), "DataType"));
					workElement2.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
					workElement2.setAttribute("NotNull", workElement1.getAttribute("NotNull"));
					workElement2.setAttribute("NoUpdate", workElement1.getAttribute("NoUpdate"));
					workElement2.setAttribute("Default", workElement1.getAttribute("Default"));
					checkCounter++;
					break;
				}
			}
			if (checkCounter == 0) {
				org.w3c.dom.Element newElement = frame_.domDocument.createElement("TableField");
				lastElement = getLastDomElementOfTheType("TableField");
				if (lastElement == null) {
					lastID = 0;
				} else {
					lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				}
				fieldID = Integer.toString(lastID + 1);
				newElement.setAttribute("ID", fieldID);
				newElement.setAttribute("Name", workElement1.getAttribute("Name"));
				newElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
				newElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
				newElement.setAttribute("Alias", workElement1.getAttribute("Alias"));
				newElement.setAttribute("AttributeType", workElement1.getAttribute("AttributeType"));
				newElement.setAttribute("DataTypeID", convertInternalIDOfTheTypeTag(workElement1.getAttribute("DataTypeID"), "DataType"));
				newElement.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
				newElement.setAttribute("NotNull", workElement1.getAttribute("NotNull"));
				newElement.setAttribute("NoUpdate", workElement1.getAttribute("NoUpdate"));
				newElement.setAttribute("Default", workElement1.getAttribute("Default"));
				elementInto.appendChild(newElement);

//				for (int j = 0; j < countOfIOTableList; j++) {
//					org.w3c.dom.Element ioTableFieldElement = frame_.domDocument.createElement("IOTableField");
//					ioTableFieldElement.setAttribute("FieldID", fieldID);
//					ioTableFieldElement.setAttribute("Descriptions", "");
//					ioTableList[j].appendChild(ioTableFieldElement);
//				}
				for (int j = 0; j < ioTableList.size(); j++) {
					org.w3c.dom.Element ioTableFieldElement = frame_.domDocument.createElement("IOTableField");
					ioTableFieldElement.setAttribute("FieldID", fieldID);
					ioTableFieldElement.setAttribute("Descriptions", "");
					ioTableList.get(j).appendChild(ioTableFieldElement);
				}
			}
		}

		///////////////////
		//Adjust TableKey//
		///////////////////
		for (int i = 0; i < keyListFrom.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)keyListFrom.item(i);
			checkCounter = 0;
			lastElement = null;
			for (int j = 0; j < keyListInto.getLength(); j++) {
				workElement2 = (org.w3c.dom.Element)keyListInto.item(j);
				if (theseAreIdenticalKeyDefinitions(workElement1, workElement2)) {
					workElement2.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
					checkCounter++;
				} else {
					if (workElement1.getAttribute("Type").equals("PK") && workElement2.getAttribute("Type").equals("PK")) {
						checkCounter++;
						boolean validRelationshipExisting = false;
						for (int p = 0; p < relationshipListInto.getLength(); p++) {
							workElement3 = (org.w3c.dom.Element)relationshipListInto.item(p);
							if (workElement3.getAttribute("Table1ID").equals(tableIDInto) && workElement3.getAttribute("TableKey1ID").equals(workElement2.getAttribute("ID"))) {
								validRelationshipExisting = true;
								break;
							}
							if (workElement3.getAttribute("Table2ID").equals(tableIDInto) && workElement3.getAttribute("TableKey2ID").equals(workElement2.getAttribute("ID"))) {
								validRelationshipExisting = true;
								break;
							}
						}
						if (validRelationshipExisting) {
							try {
								bufferedWriter.write("  " + res.getString("DialogImportXEAD43") + "\n");
							}
							catch (IOException ex) {
							}
						} else {
							purgeChildElements(workElement2, "TableKeyField");
							elementList = workElement1.getElementsByTagName("TableKeyField");
							for (int m = 0; m < elementList.getLength(); m++) {
								workElement3 = (org.w3c.dom.Element)elementList.item(m);
								org.w3c.dom.Element childElement = frame_.domDocument.createElement("TableKeyField");
								childElement.setAttribute("FieldID", convertInternalIDOfTableField(elementFrom.getAttribute("ID"), workElement3.getAttribute("FieldID"), domDocumentFrom, frame_.domDocument));
								childElement.setAttribute("SortKey", workElement3.getAttribute("SortKey"));
								workElement2.appendChild(childElement);
							}
						}
						break;
					}
				}
				if (lastElement == null) {
					lastElement = workElement2;
				} else {
					if (Integer.parseInt(workElement2.getAttribute("ID")) > Integer.parseInt(lastElement.getAttribute("ID"))) {
						lastElement = workElement2;
					}
				}
			}
			if (checkCounter == 0 && lastElement != null) {
				org.w3c.dom.Element childElement = frame_.domDocument.createElement("TableKey");
				lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				childElement.setAttribute("ID", Integer.toString(lastID + 1));
				childElement.setAttribute("Type", workElement1.getAttribute("Type"));
				childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
				elementList = workElement1.getElementsByTagName("TableKeyField");
				for (int m = 0; m < elementList.getLength(); m++) {
					workElement2 = (org.w3c.dom.Element)elementList.item(m);
					org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("TableKeyField");
					grandChildElement.setAttribute("FieldID", convertInternalIDOfTableField(elementFrom.getAttribute("ID"), workElement2.getAttribute("FieldID"), domDocumentFrom, frame_.domDocument));
					grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
					childElement.appendChild(grandChildElement);
				}
				elementInto.appendChild(childElement);
			}
		}
	}

	void createTableDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2;
		int lastID = 0;

		///////////////////////////
		//Create Table Definition//
		///////////////////////////
		org.w3c.dom.Element newElement = frame_.domDocument.createElement("Table");
		org.w3c.dom.Element lastElement = getLastDomElementOfTheType("Table");
		if (lastElement == null) {
			lastID = 0;
		} else {
			lastID = Integer.parseInt(lastElement.getAttribute("ID"));
		}
		newElement.setAttribute("ID", Integer.toString(lastID + 1));
		newElement.setAttribute("Name", elementFrom.getAttribute("Name"));
		newElement.setAttribute("SortKey", elementFrom.getAttribute("SortKey"));
		newElement.setAttribute("SubsystemID", subsystemID);
		newElement.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));
		newElement.setAttribute("TableTypeID", convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TableTypeID"), "TableType"));
		systemElement.appendChild(newElement);

		/////////////////////
		//Create TableField//
		/////////////////////
		elementList1 = elementFrom.getElementsByTagName("TableField");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("TableField");
			lastElement = getLastDomElementOfTheType("TableField");
			if (lastElement == null) {
				lastID = 0;
			} else {
				lastID = Integer.parseInt(lastElement.getAttribute("ID"));
			}
			childElement.setAttribute("ID", Integer.toString(lastID + 1));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("Alias", workElement1.getAttribute("Alias"));
			childElement.setAttribute("AttributeType", workElement1.getAttribute("AttributeType"));
			childElement.setAttribute("DataTypeID", convertInternalIDOfTheTypeTag(workElement1.getAttribute("DataTypeID"), "DataType"));
			childElement.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
			childElement.setAttribute("NotNull", workElement1.getAttribute("NotNull"));
			childElement.setAttribute("NoUpdate", workElement1.getAttribute("NoUpdate"));
			childElement.setAttribute("Default", workElement1.getAttribute("Default"));
			newElement.appendChild(childElement);
		}

		///////////////////
		//Create TableKey//
		///////////////////
		elementList1 = elementFrom.getElementsByTagName("TableKey");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("TableKey");
			childElement.setAttribute("ID", workElement1.getAttribute("ID"));
			childElement.setAttribute("Type", workElement1.getAttribute("Type"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			elementList2 = workElement1.getElementsByTagName("TableKeyField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("TableKeyField");
				grandChildElement.setAttribute("FieldID", convertInternalIDOfTableField(elementFrom.getAttribute("ID"), workElement2.getAttribute("FieldID"), domDocumentFrom, frame_.domDocument));
				grandChildElement.setAttribute("AscDesc", workElement2.getAttribute("AscDesc"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}
			newElement.appendChild(childElement);
		}
	}

	void adjustTableRelationship(org.w3c.dom.Element elementFrom) {
		org.w3c.dom.Element workElement;
		int lastID = 0;

		String tableID1From = elementFrom.getAttribute("Table1ID");
		String tableID2From = elementFrom.getAttribute("Table2ID");
		String tableKey1From = elementFrom.getAttribute("TableKey1ID");
		String tableKey2From = elementFrom.getAttribute("TableKey2ID");
		String relationshipType = elementFrom.getAttribute("Type");
		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		NodeList relationshipListInto = frame_.domDocument.getElementsByTagName("Relationship");

		String tableName1From = "";
		String tableName2From = "";
		String tableSortKey1From = "";
		String tableSortKey2From = "";
		for (int i = 0; i < tableListFrom.getLength(); i++) {
			workElement = (org.w3c.dom.Element)tableListFrom.item(i);
			if (workElement.getAttribute("ID").equals(tableID1From)) {
				tableName1From = workElement.getAttribute("Name");
				tableSortKey1From = workElement.getAttribute("SortKey");
			}
			if (workElement.getAttribute("ID").equals(tableID2From)) {
				tableName2From = workElement.getAttribute("Name");
				tableSortKey2From = workElement.getAttribute("SortKey");
			}
		}

		String relationshipIDInto = convertInternalIDOfTableRelationship(elementFrom.getAttribute("ID"));
		if (relationshipIDInto.equals("")) {
			String tableID1Into = convertInternalIDOfTheTag(tableID1From, "Table");
			String tableID2Into = convertInternalIDOfTheTag(tableID2From, "Table");
			String convertedKey1ID = convertTableKeyID(tableID1From, tableKey1From, tableID1Into);
			String convertedKey2ID = convertTableKeyID(tableID2From, tableKey2From, tableID2Into);
			if (convertedKey1ID.equals("") || convertedKey2ID.equals("")) {
				missingTableKeyCounter++;
				try {
					bufferedWriter.write(tableName1From + "(" + tableSortKey1From + ")," + tableName2From + "(" + tableSortKey2From + ")" + res.getString("DialogImportXEAD35") + "\n");
				} catch (IOException ex) {}
			} else {
				org.w3c.dom.Element newElement = frame_.domDocument.createElement("Relationship");
				org.w3c.dom.Element lastElement = getLastDomElementOfTheType("Relationship");
				if (lastElement == null) {
					lastID = 0;
				} else {
					lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				}
				newElement.setAttribute("ID", Integer.toString(lastID + 1));
				newElement.setAttribute("Table1ID", tableID1Into);
				newElement.setAttribute("Table2ID", tableID2Into);
				newElement.setAttribute("TableKey1ID", convertedKey1ID);
				newElement.setAttribute("TableKey2ID", convertedKey2ID);
				newElement.setAttribute("Type", relationshipType);
				newElement.setAttribute("ExistWhen1", elementFrom.getAttribute("ExistWhen1"));
				newElement.setAttribute("ExistWhen2", elementFrom.getAttribute("ExistWhen2"));
				systemElement.appendChild(newElement);

				//Add Subsystem Relationship Attributes//
				NodeList subsystemNodeList = frame_.domDocument.getElementsByTagName("Subsystem");
				for (int i = 0; i < subsystemNodeList.getLength(); i++) {
					frame_.createSubsystemAttributesForRelationship(newElement, (org.w3c.dom.Element)subsystemNodeList.item(i));
				}

				try {
					bufferedWriter.write(tableName1From + "(" + tableSortKey1From + ")," + tableName2From + "(" + tableSortKey2From + ")" + res.getString("DialogImportXEAD36") + "\n");
				} catch (IOException ex) {}
			}
		} else {
			for (int i = 0; i < relationshipListInto.getLength(); i++) {
				workElement = (org.w3c.dom.Element)relationshipListInto.item(i);
				if (workElement.getAttribute("ID").equals(relationshipIDInto)) {
					workElement.setAttribute("ExistWhen1", elementFrom.getAttribute("ExistWhen1"));
					workElement.setAttribute("ExistWhen2", elementFrom.getAttribute("ExistWhen2"));
					break;
				}
			}
		}
	}
	
	void importSubsystemAttributesOfTable() {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementListFrom, elementListInto, tableListFrom;
		String tableID1 = "";
		String tableID2 = "";
		String relationshipIDFrom = "";
		String relationshipIDInto = "";
		String relationshipIDWork = "";
		int checkCounter = 0;

		/////////////////////////
		//Adjust SubsystemTable//
		/////////////////////////
		tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		elementListFrom = blockElementFrom.getElementsByTagName("SubsystemTable");
		elementListInto = blockElementInto.getElementsByTagName("SubsystemTable");

		jProgressBar.setString("Importing Subsystem Attribute...");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(elementListFrom.getLength());

		for (int i = 0; i < elementListFrom.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement1 = (org.w3c.dom.Element)elementListFrom.item(i);
			tableID1 = convertInternalIDOfTheTag(workElement1.getAttribute("TableID"), "Table");
			if (tableID1.equals("")) {
				try {
					for (int j = 0; j < tableListFrom.getLength(); j++) {
						workElement2 = (org.w3c.dom.Element)tableListFrom.item(j);
						if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TableID"))) {
							bufferedWriter.write(workElement2.getAttribute("Name") + "(" + workElement2.getAttribute("SortKey") + ")" + res.getString("DialogImportXEAD42") + "\n");
						}
					}
				} catch (IOException ex) {}
			} else {
				checkCounter = 0;
				for (int j = 0; j < elementListInto.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)elementListInto.item(j);
					tableID2 = workElement2.getAttribute("TableID");
					if (tableID1.equals(tableID2)) {
						workElement2.setAttribute("BoxPosition", workElement1.getAttribute("BoxPosition"));
						workElement2.setAttribute("ExtDivLoc", workElement1.getAttribute("ExtDivLoc"));
						workElement2.setAttribute("IntDivLoc", workElement1.getAttribute("IntDivLoc"));
						workElement2.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
						workElement2.setAttribute("ShowInstance", workElement1.getAttribute("ShowInstance"));
						workElement2.setAttribute("Instance", workElement1.getAttribute("Instance"));
						checkCounter++;
						break;
					}
				}
				if (checkCounter == 0) {
					org.w3c.dom.Element newElement = frame_.domDocument.createElement("SubsystemTable");
					newElement.setAttribute("TableID", tableID1);
					newElement.setAttribute("BoxPosition", workElement1.getAttribute("BoxPosition"));
					newElement.setAttribute("ExtDivLoc", workElement1.getAttribute("ExtDivLoc"));
					newElement.setAttribute("IntDivLoc", workElement1.getAttribute("IntDivLoc"));
					newElement.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
					newElement.setAttribute("ShowInstance", workElement1.getAttribute("ShowInstance"));
					newElement.setAttribute("Instance", workElement1.getAttribute("Instance"));
					blockElementInto.appendChild(newElement);
				}
			}
		}

		////////////////////////////////
		//Adjust SubsystemRelationship//
		////////////////////////////////
		elementListFrom = blockElementFrom.getElementsByTagName("SubsystemRelationship");
		elementListInto = blockElementInto.getElementsByTagName("SubsystemRelationship");

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(elementListFrom.getLength());

		for (int i = 0; i < elementListFrom.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement1 = (org.w3c.dom.Element)elementListFrom.item(i);
			relationshipIDFrom = workElement1.getAttribute("RelationshipID");
			relationshipIDInto = convertInternalIDOfTableRelationship(relationshipIDFrom);
			if (!relationshipIDInto.equals("")) {
				checkCounter = 0;
				for (int j = 0; j < elementListInto.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)elementListInto.item(j);
					relationshipIDWork = workElement2.getAttribute("RelationshipID");
					if (relationshipIDWork.equals(relationshipIDInto)) {
						workElement2.setAttribute("TerminalIndex1", workElement1.getAttribute("TerminalIndex1"));
						workElement2.setAttribute("TerminalIndex2", workElement1.getAttribute("TerminalIndex2"));
						workElement2.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
						checkCounter++;
						break;
					}
				}
				if (checkCounter == 0) {
					org.w3c.dom.Element newElement = frame_.domDocument.createElement("SubsystemRelationship");
					newElement.setAttribute("RelationshipID", relationshipIDInto);
					newElement.setAttribute("TerminalIndex1", workElement1.getAttribute("TerminalIndex1"));
					newElement.setAttribute("TerminalIndex2", workElement1.getAttribute("TerminalIndex2"));
					newElement.setAttribute("ShowOnModel", workElement1.getAttribute("ShowOnModel"));
					blockElementInto.appendChild(newElement);
				}
			}
		}
	}

	void importSubsystem() {
		/////////////////////////////////////////
		//Update attributes of target subsystem//
		/////////////////////////////////////////
		blockElementInto.setAttribute("Name", blockElementFrom.getAttribute("Name"));
		blockElementInto.setAttribute("SortKey", blockElementFrom.getAttribute("SortKey"));
		blockElementInto.setAttribute("Descriptions", blockElementFrom.getAttribute("Descriptions"));
	}

	String convertInternalIDOfTableRelationship(String relationshipIDFrom) {
		org.w3c.dom.Element workElement1, workElement2, workElement3;
		NodeList tableList, tableKeyList, relationshipList;
		org.w3c.dom.Element tableKey1ElementFrom = null;
		org.w3c.dom.Element tableKey2ElementFrom = null;
		org.w3c.dom.Element tableKey1ElementInto = null;
		org.w3c.dom.Element tableKey2ElementInto = null;
		String table1IDFrom = "";
		String tableKey1IDFrom = "";
		String table2IDFrom = "";
		String tableKey2IDFrom = "";
		String relationshipTypeFrom = "";
		String table1IDInto = "";
		String tableKey1IDInto = "";
		String table2IDInto = "";
		String tableKey2IDInto = "";
		String relationshipTypeInto = "";
		String relationshipIDInto = "";
		String relationshipIDWork = "";

		relationshipList = domDocumentFrom.getElementsByTagName("Relationship");
		for (int i = 0; i < relationshipList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)relationshipList.item(i);
			if (workElement1.getAttribute("ID").equals(relationshipIDFrom)) {
				table1IDFrom = workElement1.getAttribute("Table1ID");
				tableKey1IDFrom = workElement1.getAttribute("TableKey1ID");
				table2IDFrom = workElement1.getAttribute("Table2ID");
				tableKey2IDFrom = workElement1.getAttribute("TableKey2ID");
				relationshipTypeFrom = workElement1.getAttribute("Type");
			}
		}

		tableList = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < tableList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)tableList.item(i);
			if (workElement1.getAttribute("ID").equals(table1IDFrom)) {
				tableKeyList = workElement1.getElementsByTagName("TableKey");
				for (int j = 0; j < tableKeyList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableKeyList.item(j);
					if (workElement2.getAttribute("ID").equals(tableKey1IDFrom)) {
						tableKey1ElementFrom = workElement2;
					}
				}
			}
			if (workElement1.getAttribute("ID").equals(table2IDFrom)) {
				tableKeyList = workElement1.getElementsByTagName("TableKey");
				for (int j = 0; j < tableKeyList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableKeyList.item(j);
					if (workElement2.getAttribute("ID").equals(tableKey2IDFrom)) {
						tableKey2ElementFrom = workElement2;
					}
				}
			}
		}

		relationshipList = frame_.domDocument.getElementsByTagName("Relationship");
		tableList = frame_.domDocument.getElementsByTagName("Table");

		for (int i = 0; i < relationshipList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)relationshipList.item(i);
			relationshipIDWork = workElement1.getAttribute("ID");
			table1IDInto = workElement1.getAttribute("Table1ID");
			tableKey1IDInto = workElement1.getAttribute("TableKey1ID");
			table2IDInto = workElement1.getAttribute("Table2ID");
			tableKey2IDInto = workElement1.getAttribute("TableKey2ID");
			relationshipTypeInto = workElement1.getAttribute("Type");

			if (relationshipTypeFrom.equals(relationshipTypeInto)) {
				for (int k = 0; k < tableList.getLength(); k++) {
					workElement2 = (org.w3c.dom.Element)tableList.item(k);
					if (workElement2.getAttribute("ID").equals(table1IDInto)) {
						tableKeyList = workElement2.getElementsByTagName("TableKey");
						for (int j = 0; j < tableKeyList.getLength(); j++) {
							workElement3 = (org.w3c.dom.Element)tableKeyList.item(j);
							if (workElement3.getAttribute("ID").equals(tableKey1IDInto)) {
								tableKey1ElementInto = workElement3;
							}
						}
					}
					if (workElement2.getAttribute("ID").equals(table2IDInto)) {
						tableKeyList = workElement2.getElementsByTagName("TableKey");
						for (int j = 0; j < tableKeyList.getLength(); j++) {
							workElement3 = (org.w3c.dom.Element)tableKeyList.item(j);
							if (workElement3.getAttribute("ID").equals(tableKey2IDInto)) {
								tableKey2ElementInto = workElement3;
							}
						}
					}
				}

				if (theseAreIdenticalKeyDefinitions(tableKey1ElementFrom, tableKey1ElementInto) &&
						theseAreIdenticalKeyDefinitions(tableKey2ElementFrom, tableKey2ElementInto)) {
					relationshipIDInto = relationshipIDWork;
					break;
				}
			}
		}
		return relationshipIDInto;
	}

	void createFunctionDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2, elementList3;
		String internalTableIDInto = "";
		String internalFieldIDInto = "";
		int lastID = 0;
		org.w3c.dom.Element tableElement;
		String tableName = "";
		String tableSortKey = "";
		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");

		//////////////////////////////
		//Create Function Definition//
		//////////////////////////////
		org.w3c.dom.Element newElement = frame_.domDocument.createElement("Function");
		org.w3c.dom.Element lastElement = getLastDomElementOfTheType("Function");
		if (lastElement == null) {
			lastID = 0;
		} else {
			lastID = Integer.parseInt(lastElement.getAttribute("ID"));
		}
		newElement.setAttribute("ID", Integer.toString(lastID + 1));
		newElement.setAttribute("Name", elementFrom.getAttribute("Name"));
		newElement.setAttribute("SortKey", elementFrom.getAttribute("SortKey"));
		newElement.setAttribute("SubsystemID", subsystemID);
		newElement.setAttribute("Summary", elementFrom.getAttribute("Summary"));
		newElement.setAttribute("Parameters", elementFrom.getAttribute("Parameters"));
		newElement.setAttribute("Return", elementFrom.getAttribute("Return"));
		newElement.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));
		newElement.setAttribute("FunctionTypeID", convertInternalIDOfTheTypeTag(elementFrom.getAttribute("FunctionTypeID"), "FunctionType"));

		//////////////////
		//Create IOPanel//
		//////////////////
		elementList1 = elementFrom.getElementsByTagName("IOPanel");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOPanel");

			childElement.setAttribute("ID", workElement1.getAttribute("ID"));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("ImageText", workElement1.getAttribute("ImageText"));
			childElement.setAttribute("Background", workElement1.getAttribute("Background"));
			childElement.setAttribute("Size", workElement1.getAttribute("Size"));
			childElement.setAttribute("FontSize", workElement1.getAttribute("FontSize"));

			elementList2 = workElement1.getElementsByTagName("IOPanelStyle");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOPanelStyle");
				grandChildElement.setAttribute("Value", workElement2.getAttribute("Value"));
				childElement.appendChild(grandChildElement);
			}

			elementList2 = workElement1.getElementsByTagName("IOPanelField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOPanelField");
				grandChildElement.setAttribute("Name", workElement2.getAttribute("Name"));
				grandChildElement.setAttribute("Label", workElement2.getAttribute("Label"));
				grandChildElement.setAttribute("IOType", workElement2.getAttribute("IOType"));
				grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}

			newElement.appendChild(childElement);
		}

		////////////////////
		//Create IOWebPage//
		////////////////////
		elementList1 = elementFrom.getElementsByTagName("IOWebPage");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOWebPage");

			childElement.setAttribute("ID", workElement1.getAttribute("ID"));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("FileName", workElement1.getAttribute("FileName"));

			elementList2 = workElement1.getElementsByTagName("IOWebPageField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOWebPageField");
				grandChildElement.setAttribute("Name", workElement2.getAttribute("Name"));
				grandChildElement.setAttribute("Label", workElement2.getAttribute("Label"));
				grandChildElement.setAttribute("IOType", workElement2.getAttribute("IOType"));
				grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}

			newElement.appendChild(childElement);
		}

		//////////////////
		//Create IOSpool//
		//////////////////
		elementList1 = elementFrom.getElementsByTagName("IOSpool");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOSpool");

			childElement.setAttribute("ID", workElement1.getAttribute("ID"));
			childElement.setAttribute("Name", workElement1.getAttribute("Name"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("ImageText", workElement1.getAttribute("ImageText"));
			childElement.setAttribute("Background", workElement1.getAttribute("Background"));
			childElement.setAttribute("Size", workElement1.getAttribute("Size"));
			childElement.setAttribute("FontSize", workElement1.getAttribute("FontSize"));

			elementList2 = workElement1.getElementsByTagName("IOSpoolStyle");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOSpoolStyle");
				grandChildElement.setAttribute("Value", workElement2.getAttribute("Value"));
				childElement.appendChild(grandChildElement);
			}

			elementList2 = workElement1.getElementsByTagName("IOSpoolField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOSpoolField");
				grandChildElement.setAttribute("Name", workElement2.getAttribute("Name"));
				grandChildElement.setAttribute("Label", workElement2.getAttribute("Label"));
				grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
				grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
				childElement.appendChild(grandChildElement);
			}

			newElement.appendChild(childElement);
		}

		//////////////////
		//Create IOTable//
		//////////////////
		elementList1 = elementFrom.getElementsByTagName("IOTable");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			internalTableIDInto = convertInternalIDOfTheTag(workElement1.getAttribute("TableID"), "Table");
			if (internalTableIDInto.equals("")) {
				missingTableCounter++;
				for (int m = 0; m < tableListFrom.getLength(); m++) {
					tableElement = (org.w3c.dom.Element)tableListFrom.item(m);
					if (workElement1.getAttribute("TableID").equals(tableElement.getAttribute("ID"))) {
						tableName = tableElement.getAttribute("Name");
						tableSortKey = tableElement.getAttribute("SortKey");
					}
				}
				try {
					bufferedWriter.write("  " + tableName + "(" + tableSortKey + ")" + res.getString("DialogImportXEAD37") + "\n");
				} catch (IOException ex) {}
			} else {
				org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
				childElement.setAttribute("ID", workElement1.getAttribute("ID"));
				childElement.setAttribute("NameExtension", workElement1.getAttribute("NameExtension"));
				childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
				childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
				childElement.setAttribute("TableID", internalTableIDInto);
				childElement.setAttribute("OpC", workElement1.getAttribute("OpC"));
				childElement.setAttribute("OpR", workElement1.getAttribute("OpR"));
				childElement.setAttribute("OpU", workElement1.getAttribute("OpU"));
				childElement.setAttribute("OpD", workElement1.getAttribute("OpD"));

				elementList3 = workElement1.getElementsByTagName("IOTableField");
				for (int m = 0; m < elementList3.getLength(); m++) {
					workElement2 = (org.w3c.dom.Element)elementList3.item(m);
					internalFieldIDInto = convertInternalIDOfTableField(workElement1.getAttribute("TableID"), workElement2.getAttribute("FieldID"), domDocumentFrom, frame_.domDocument);
					if (!workElement2.getAttribute("Descriptions").equals("") && !internalFieldIDInto.equals("")) {
						org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("IOTableField");
						grandChildElement.setAttribute("FieldID", internalFieldIDInto);
						grandChildElement.setAttribute("Descriptions", workElement2.getAttribute("Descriptions"));
						childElement.appendChild(grandChildElement);
					}
				}

				newElement.appendChild(childElement);

				setupSubsystemAttributeOfTable(internalTableIDInto, blockElementInto);
			}
		}

		systemElement.appendChild(newElement);
	}

	String createTaskDefinition(org.w3c.dom.Element elementFrom, String roleID) {
		int lastID = 0;
		String newID = "";

		//////////////////////////
		//Create Task Definition//
		//////////////////////////
		org.w3c.dom.Element newElement = frame_.domDocument.createElement("Task");
		org.w3c.dom.Element lastElement = getLastDomElementOfTheType("Task");
		if (lastElement == null) {
			lastID = 0;
		} else {
			lastID = Integer.parseInt(lastElement.getAttribute("ID"));
		}
		newID = Integer.toString(lastID + 1);
		newElement.setAttribute("ID", newID);
		newElement.setAttribute("Name", elementFrom.getAttribute("Name"));
		newElement.setAttribute("Event", elementFrom.getAttribute("Event"));
		newElement.setAttribute("SortKey", elementFrom.getAttribute("SortKey"));
		newElement.setAttribute("RoleID", roleID);
		newElement.setAttribute("TaskTypeID", convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TaskTypeID"), "TaskType"));
		newElement.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));

		//////////////////////
		//Create TaskActions//
		//////////////////////
		createTaskActions(elementFrom, newElement);

		systemElement.appendChild(newElement);

		return newID;
	}

	void createTaskActions(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element workElement1, workElement2, workElement3,workElement4, workElement5;
		NodeList elementList1, elementList2, elementList3;
		String functionSortKey = "";
		String functionName = "";
		String ioSortKey = "";
		String ioName = "";
		String targetFunctionID = "";
		String targetFunctionIOID = "";

		NodeList functionListFrom = domDocumentFrom.getElementsByTagName("Function");
		NodeList functionListInto = frame_.domDocument.getElementsByTagName("Function");
		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");

		//////////////////////
		//Create TaskActions//
		//////////////////////
		elementList1 = elementFrom.getElementsByTagName("TaskAction");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("TaskAction");
			childElement.setAttribute("ExecuteIf", workElement1.getAttribute("ExecuteIf"));
			childElement.setAttribute("Label", workElement1.getAttribute("Label"));
			childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Descriptions"));
			childElement.setAttribute("IndentLevel", workElement1.getAttribute("IndentLevel"));

			elementList2 = workElement1.getElementsByTagName("TaskFunctionIO");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);

				ioSortKey = "";
				ioName = "";
				targetFunctionID = "";
				targetFunctionIOID = "";

				for (int j = 0; j < functionListFrom.getLength(); j++) {
					workElement3 = (org.w3c.dom.Element)functionListFrom.item(j);
					if (workElement3.getAttribute("ID").equals(workElement2.getAttribute("FunctionID"))) {
						functionName = workElement3.getAttribute("Name");
						functionSortKey = workElement3.getAttribute("SortKey");
						ioSortKey = "";
						ioName = "";

						if (!workElement2.getAttribute("IOID").equals("0")) {
							elementList3 = workElement3.getElementsByTagName("IOPanel");
							for (int n = 0; n < elementList3.getLength(); n++) {
								workElement4 = (org.w3c.dom.Element)elementList3.item(n);
								if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
									ioSortKey = workElement4.getAttribute("SortKey");
									ioName = workElement4.getAttribute("Name");
									break;
								}
							}
							if (ioSortKey.equals("") && ioName.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOSpool");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
										ioSortKey = workElement4.getAttribute("SortKey");
										ioName = workElement4.getAttribute("Name");
										break;
									}
								}
							}
							if (ioSortKey.equals("") && ioName.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOTable");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
										for (int p = 0; p < tableListFrom.getLength(); p++) {
											workElement5 = (org.w3c.dom.Element)tableListFrom.item(p);
											if (workElement5.getAttribute("ID").equals(workElement4.getAttribute("TableID"))) {
												ioSortKey = workElement5.getAttribute("SortKey");
												ioName = workElement5.getAttribute("Name");
												break;
											}
										}
										break;
									}
								}
							}
							if (ioSortKey.equals("") && ioName.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOWebPage");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
										ioSortKey = workElement4.getAttribute("SortKey");
										ioName = workElement4.getAttribute("Name");
										break;
									}
								}
							}
						}
						break;
					}
				}

				for (int j = 0; j < functionListInto.getLength(); j++) {
					workElement3 = (org.w3c.dom.Element)functionListInto.item(j);
					if (workElement3.getAttribute("SortKey").equals(functionSortKey)) {
						targetFunctionID = workElement3.getAttribute("ID");

						if (workElement2.getAttribute("IOID").equals("0")) {
							targetFunctionIOID = "0";
						} else {
							elementList3 = workElement3.getElementsByTagName("IOPanel");
							for (int n = 0; n < elementList3.getLength(); n++) {
								workElement4 = (org.w3c.dom.Element)elementList3.item(n);
								if (workElement4.getAttribute("SortKey").equals(ioSortKey)) {
									targetFunctionIOID = workElement4.getAttribute("ID");
									break;
								}
							}
							if (targetFunctionIOID.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOSpool");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("SortKey").equals(ioSortKey)) {
										targetFunctionIOID = workElement4.getAttribute("ID");
										break;
									}
								}
							}
							if (targetFunctionIOID.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOTable");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("SortKey").equals(ioSortKey)) {
										targetFunctionIOID = workElement4.getAttribute("ID");
										break;
									}
								}
							}
							if (targetFunctionIOID.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOWebPage");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("SortKey").equals(ioSortKey)) {
										targetFunctionIOID = workElement4.getAttribute("ID");
										break;
									}
								}
							}
						}
						break;
					}
				}

				if (targetFunctionID.equals("") || targetFunctionIOID.equals("")) {
					missingFunctionIOCounter++;
					try {
						bufferedWriter.write("  " + functionName + "(" + functionSortKey + ")/" + ioName + "(" + ioSortKey + ")" + res.getString("DialogImportXEAD40") + "\n");
					} catch (IOException ex) {}
				} else {
					org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("TaskFunctionIO");
					grandChildElement.setAttribute("FunctionID", targetFunctionID);
					grandChildElement.setAttribute("IOID", targetFunctionIOID);
					grandChildElement.setAttribute("IOIDSeq", workElement2.getAttribute("IOIDSeq"));
					grandChildElement.setAttribute("Operations", workElement2.getAttribute("Operations"));
					grandChildElement.setAttribute("SortKey", workElement2.getAttribute("SortKey"));
					childElement.appendChild(grandChildElement);
				}
			}

			elementInto.appendChild(childElement);
		}
	}

	void adjustFunctionsCalled(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementInto) {
		org.w3c.dom.Element workElement1, workElement2;
		org.w3c.dom.Element elementToBeUpdated = null;
		NodeList elementList1, elementList2;
		String internalID1, internalID2;
		int countEquals = 0;
		org.w3c.dom.Element functionElement;
		String functionName = "";
		String functionSortKey = "";
		String functionNameCalling = "";
		String functionSortKeyCalling = "";

		NodeList functionList = domDocumentFrom.getElementsByTagName("Function");
		elementList1 = elementFrom.getElementsByTagName("FunctionUsedByThis");
		elementList2 = elementInto.getElementsByTagName("FunctionUsedByThis");

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			internalID1 = workElement1.getAttribute("FunctionID");
			internalID2 = convertInternalIDOfTheTag(internalID1, "Function");
			if (internalID2.equals("")) {
				missingFunctionCounter++;
				for (int m = 0; m < functionList.getLength(); m++) {
					functionElement = (org.w3c.dom.Element)functionList.item(m);
					if (internalID1.equals(functionElement.getAttribute("ID"))) {
						functionName = functionElement.getAttribute("Name");
						functionSortKey = functionElement.getAttribute("SortKey");
					}
					if (elementFrom.getAttribute("ID").equals(functionElement.getAttribute("ID"))) {
						functionNameCalling = functionElement.getAttribute("Name");
						functionSortKeyCalling = functionElement.getAttribute("SortKey");
					}
				}
				try {
					bufferedWriter.write(functionName + "(" + functionSortKey + ")" + res.getString("DialogImportXEAD38") + functionNameCalling + "(" + functionSortKeyCalling + ")" + res.getString("DialogImportXEAD39") +"\n");
				} catch (IOException ex) {}
			} else {
				countEquals = 0;
				for (int j = 0; j < elementList2.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)elementList2.item(j);
					if (internalID2.equals(workElement2.getAttribute("FunctionID"))) {
						elementToBeUpdated = workElement2;
						countEquals++;
						break;
					}
				}

				/////////////////////////
				//Create FunctionCalled//
				/////////////////////////
				if (countEquals == 0) {
					org.w3c.dom.Element childElement = frame_.domDocument.createElement("FunctionUsedByThis");
					childElement.setAttribute("FunctionID", internalID2);
					childElement.setAttribute("LaunchEvent", workElement1.getAttribute("LaunchEvent"));
					childElement.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
					elementInto.appendChild(childElement);
				}

				/////////////////////////
				//Update FunctionCalled//
				/////////////////////////
				if (countEquals == 1) {
					elementToBeUpdated.setAttribute("LaunchEvent", workElement1.getAttribute("LaunchEvent"));
					elementToBeUpdated.setAttribute("SortKey", workElement1.getAttribute("SortKey"));
				}
			}
		}
	}

	void importTypeDefinitionWithTagName(String tagName) {
		int checkCount = 0;
		int lastID = 0;
		org.w3c.dom.Element elementFrom, elementInto;

		NodeList typeListFrom = domDocumentFrom.getElementsByTagName(tagName);
		NodeList typeListInto = frame_.domDocument.getElementsByTagName(tagName);

		jProgressBar.setString("Importing Type List...");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(typeListFrom.getLength());

		for (int i = 0; i < typeListFrom.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			elementFrom = (org.w3c.dom.Element)typeListFrom.item(i);
			checkCount = 0;
			for (int j = 0; j < typeListInto.getLength(); j++) {
				elementInto = (org.w3c.dom.Element)typeListInto.item(j);
				if (elementInto.getAttribute("SortKey").equals(elementFrom.getAttribute("SortKey"))) {
					checkCount++;
					if (tagName.equals("Department") || tagName.equals("TaskType") || tagName.equals("TableType") || tagName.equals("FunctionType")) {
						elementInto.setAttribute("Name", elementFrom.getAttribute("Name"));
						elementInto.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));
					}
					if (tagName.equals("DataType")) {
						elementInto.setAttribute("Name", elementFrom.getAttribute("Name"));
						elementInto.setAttribute("BasicType", elementFrom.getAttribute("BasicType"));
						elementInto.setAttribute("Length", elementFrom.getAttribute("Length"));
						elementInto.setAttribute("Decimal", elementFrom.getAttribute("Decimal"));
						elementInto.setAttribute("SQLExpression", elementFrom.getAttribute("SQLExpression"));
					}
					break;
				}
			}
			if (checkCount == 0) {
				org.w3c.dom.Element newElement = frame_.domDocument.createElement(tagName);

				org.w3c.dom.Element lastElement = getLastDomElementOfTheType(tagName);
				if (lastElement == null) {
					lastID = 0;
				} else {
					lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				}
				newElement.setAttribute("ID", Integer.toString(lastID + 1));
				newElement.setAttribute("SortKey", elementFrom.getAttribute("SortKey"));

				if (tagName.equals("Department") || tagName.equals("TaskType") || tagName.equals("TableType") || tagName.equals("FunctionType")) {
					newElement.setAttribute("Name", elementFrom.getAttribute("Name"));
					newElement.setAttribute("Descriptions", elementFrom.getAttribute("Descriptions"));
				}
				if (tagName.equals("DataType")) {
					newElement.setAttribute("Name", elementFrom.getAttribute("Name"));
					newElement.setAttribute("BasicType", elementFrom.getAttribute("BasicType"));
					newElement.setAttribute("Length", elementFrom.getAttribute("Length"));
					newElement.setAttribute("Decimal", elementFrom.getAttribute("Decimal"));
					newElement.setAttribute("SQLExpression", elementFrom.getAttribute("SQLExpression"));
				}

				systemElement.appendChild(newElement);
			}
		}
	}

	String convertInternalIDOfTheTypeTag(String originalInternalID, String tagName) {
		String convertedInternalID = "";
		String defaultID = "";
		String defaultSortKey = "";
		String sortKey = "";
		org.w3c.dom.Element element = null;

		NodeList nodeList = domDocumentFrom.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (element.getAttribute("ID").equals(originalInternalID)) {
				sortKey = element.getAttribute("SortKey");
				break;
			}
		}

		nodeList = frame_.domDocument.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);

			if (defaultID.equals("")) {
				defaultID = element.getAttribute("ID");
				defaultSortKey = element.getAttribute("SortKey");
			} else {
				if (defaultSortKey.compareTo(element.getAttribute("SortKey")) > 0) {
					defaultID = element.getAttribute("ID");
					defaultSortKey = element.getAttribute("SortKey");
				}
			}

			if (element.getAttribute("SortKey").equals(sortKey)) {
				convertedInternalID = element.getAttribute("ID");
			}
		}

		if (convertedInternalID.equals("")) {
			convertedInternalID = defaultID;
		}

		return convertedInternalID;
	}

	String convertInternalIDOfTheTag(String originalInternalID, String tagName) {
		String convertedInternalID = "";
		String sortKey = "";
		String name = "";
		org.w3c.dom.Element element = null;

		NodeList nodeList = domDocumentFrom.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (element.getAttribute("ID").equals(originalInternalID)) {
				sortKey = element.getAttribute("SortKey");
				name = element.getAttribute("Name");
				break;
			}
		}

		if (!sortKey.equals("")) {
			nodeList = frame_.domDocument.getElementsByTagName(tagName);
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (org.w3c.dom.Element)nodeList.item(i);
				if (element.getAttribute("SortKey").equals(sortKey) && element.getAttribute("Name").equals(name)) {
					convertedInternalID = element.getAttribute("ID");
					break;
				}
			}
		}

		return convertedInternalID;
	}

	String convertInternalIDOfTableField(String originalInternalTableID, String originalInternalFieldID, org.w3c.dom.Document documentFrom, org.w3c.dom.Document documentInto) {
		String convertedInternalFieldID = "";
		String externalIDOfTable = "";
		String externalIDOfField = "";
		String nameOfField = "";
		org.w3c.dom.Element tableElement = null;
		org.w3c.dom.Element element = null;

		NodeList nodeList = documentFrom.getElementsByTagName("Table");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (element.getAttribute("ID").equals(originalInternalTableID)) {
				externalIDOfTable = element.getAttribute("SortKey");
				tableElement = element;
				break;
			}
		}

		if (tableElement != null) {
			nodeList = tableElement.getElementsByTagName("TableField");
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (org.w3c.dom.Element)nodeList.item(i);
				if (element.getAttribute("ID").equals(originalInternalFieldID)) {
					externalIDOfField = element.getAttribute("Alias");
					nameOfField = element.getAttribute("Name");
					break;
				}
			}

			if (!externalIDOfTable.equals("")  && !nameOfField.equals("")) {
				tableElement = null;
				nodeList = documentInto.getElementsByTagName("Table");
				for (int i = 0; i < nodeList.getLength(); i++) {
					element = (org.w3c.dom.Element)nodeList.item(i);
					if (element.getAttribute("SortKey").equals(externalIDOfTable)) {
						tableElement = element;
						break;
					}
				}

				if (tableElement != null) {
					nodeList = tableElement.getElementsByTagName("TableField");
					for (int i = 0; i < nodeList.getLength(); i++) {
						element = (org.w3c.dom.Element)nodeList.item(i);
						if (element.getAttribute("Alias").equals(externalIDOfField) && element.getAttribute("Name").equals(nameOfField)) {
							convertedInternalFieldID = element.getAttribute("ID");
							break;
						}
						if (element.getAttribute("Alias").equals(externalIDOfField) && !element.getAttribute("Alias").equals("")) {
							convertedInternalFieldID = element.getAttribute("ID");
							break;
						}
					}
				}
			}
		}

		return convertedInternalFieldID;
	}

	String convertTableKeyID(String originalInternalTableID, String originalInternalKeyID, String targetTableID) {
		String convertedInternalKeyID = "";
		String keyType = "";
		org.w3c.dom.Element element1, element2, element3, element4;
		NodeList nodeList1, nodeList2, nodeList3, nodeList4;

		nodeList1 = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < nodeList1.getLength(); i++) {
			element1 = (org.w3c.dom.Element)nodeList1.item(i);
			if (element1.getAttribute("ID").equals(originalInternalTableID)) {
				nodeList2 = element1.getElementsByTagName("TableKey");
				for (int j = 0; j < nodeList2.getLength(); j++) {
					element2 = (org.w3c.dom.Element)nodeList2.item(j);
					if (element2.getAttribute("ID").equals(originalInternalKeyID)) {
						keyType = element2.getAttribute("Type");

						nodeList3 = frame_.domDocument.getElementsByTagName("Table");
						for (int m = 0; m < nodeList3.getLength(); m++) {
							element3 = (org.w3c.dom.Element)nodeList3.item(m);
							if (element3.getAttribute("ID").equals(targetTableID)) {
								nodeList4 = element3.getElementsByTagName("TableKey");
								for (int n = 0; n < nodeList4.getLength(); n++) {
									element4 = (org.w3c.dom.Element)nodeList4.item(n);
									if (element4.getAttribute("Type").equals(keyType)) {
										if (theseAreIdenticalKeyDefinitions(element2, element4)) {
											convertedInternalKeyID = element4.getAttribute("ID");
										}
									}
								}
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
		return convertedInternalKeyID;
	}

	boolean theseAreIdenticalKeyDefinitions(org.w3c.dom.Element keyElementFrom, org.w3c.dom.Element keyElementInto) {
		String workStr;
		org.w3c.dom.Element element1;
		NodeList nodeList1;
		String[] keyFieldIDFrom = new String[30];
		int countOfKeyFieldFrom = 0;
		String[] keyFieldIDInto = new String[30];
		int countOfKeyFieldInto = 0;
		int checkCount = 0;
		boolean result = false;

		String keyTypeFrom = keyElementFrom.getAttribute("Type");
		org.w3c.dom.Element tableElementFrom = (org.w3c.dom.Element)keyElementFrom.getParentNode();
		String tableIDFrom = tableElementFrom.getAttribute("ID");
		String tableSortKeyFrom = tableElementFrom.getAttribute("SortKey");

		String keyTypeInto = keyElementInto.getAttribute("Type");
		org.w3c.dom.Element tableElementInto = (org.w3c.dom.Element)keyElementInto.getParentNode();
		String tableSortKeyInto = tableElementInto.getAttribute("SortKey");

		if (tableSortKeyFrom.equals(tableSortKeyInto) && keyTypeFrom.equals(keyTypeInto)) {

			nodeList1 = keyElementFrom.getElementsByTagName("TableKeyField");
			for (int i = 0; i < nodeList1.getLength(); i++) {
				element1 = (org.w3c.dom.Element)nodeList1.item(i);
				keyFieldIDFrom[countOfKeyFieldFrom] = element1.getAttribute("FieldID");
				countOfKeyFieldFrom++;
			}

			nodeList1 = keyElementInto.getElementsByTagName("TableKeyField");
			for (int i = 0; i < nodeList1.getLength(); i++) {
				element1 = (org.w3c.dom.Element)nodeList1.item(i);
				keyFieldIDInto[countOfKeyFieldInto] = element1.getAttribute("FieldID");
				countOfKeyFieldInto++;
			}

			if (countOfKeyFieldInto == countOfKeyFieldFrom) {
				checkCount = 0;
				for (int i = 0; i < countOfKeyFieldFrom; i++) {
					workStr = convertInternalIDOfTableField(tableIDFrom, keyFieldIDFrom[i], domDocumentFrom, frame_.domDocument);
					for (int j = 0; j < countOfKeyFieldInto; j++) {
						if (workStr.equals(keyFieldIDInto[j])) {
							keyFieldIDInto[j] = "";
							checkCount++;
						}
					}
				}
				if (checkCount == countOfKeyFieldInto) {
					result = true;
				}
			}
		}

		return result;
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

	int getNextIDOfFunctionIOs() {
		org.w3c.dom.Element element = null;
		int elementID = 0;
		int lastID = 0;

		NodeList nodeList = frame_.domDocument.getElementsByTagName("IOPanel");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			elementID = Integer.parseInt(element.getAttribute("ID"));
			if (lastID == 0) {
				lastID = elementID;
			} else {
				if (elementID > lastID) {
					lastID = elementID;
				}
			}
		}

		nodeList = frame_.domDocument.getElementsByTagName("IOWebPage");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			elementID = Integer.parseInt(element.getAttribute("ID"));
			if (lastID == 0) {
				lastID = elementID;
			} else {
				if (elementID > lastID) {
					lastID = elementID;
				}
			}
		}

		nodeList = frame_.domDocument.getElementsByTagName("IOSpool");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			elementID = Integer.parseInt(element.getAttribute("ID"));
			if (lastID == 0) {
				lastID = elementID;
			} else {
				if (elementID > lastID) {
					lastID = elementID;
				}
			}
		}

		nodeList = frame_.domDocument.getElementsByTagName("IOTable");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			elementID = Integer.parseInt(element.getAttribute("ID"));
			if (lastID == 0) {
				lastID = elementID;
			} else {
				if (elementID > lastID) {
					lastID = elementID;
				}
			}
		}

		lastID++;
		return lastID;
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
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
			String str = "";
			if (nodeType_.equals("Role")) {
				str = domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
			if (nodeType_.equals("Subsystem")) {
				str = domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
			if (nodeType_.equals("SubjectArea")) {
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
}

class DialogImportXEAD_jComboBoxBlockFrom_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jComboBoxBlockFrom_actionAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxBlockFrom_actionPerformed(e);
	}
}

//class DialogImportXEAD_jComboBoxBlockInto_actionAdapter implements java.awt.event.ActionListener {
//	DialogImportXEAD adaptee;
//
//	DialogImportXEAD_jComboBoxBlockInto_actionAdapter(DialogImportXEAD adaptee) {
//		this.adaptee = adaptee;
//	}
//	public void actionPerformed(ActionEvent e) {
//		adaptee.jComboBoxBlockInto_actionPerformed(e);
//	}
//}

class DialogImportXEAD_jRadioButton_itemAdapter implements ItemListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jRadioButton_itemAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void itemStateChanged(ItemEvent e) {
		adaptee.jRadioButton_itemStateChanged();
	}
}

class DialogImportXEAD_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jButtonStart_actionAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogImportXEAD_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jButtonCancel_actionAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
