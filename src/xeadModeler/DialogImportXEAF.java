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

public class DialogImportXEAF extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
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
	//private SortableDomElementFieldListModel sortableDomElementFieldListModel = new SortableDomElementFieldListModel();

	private String importResult = "";
	private Modeler frame_;
	private org.w3c.dom.Document domDocumentFrom;
	private org.w3c.dom.Element systemElement;
	private org.w3c.dom.Element blockElementFrom, blockElementInto;
	private String blockIDFrom, blockIDInto;
	private FileWriter fileWriter = null;
	private BufferedWriter bufferedWriter = null;

	private int createTableCounter, cancelTableCounter;
	private int createFunctionCounter, cancelFunctionCounter;
	private int missingTableCounter, missingFunctionCounter;
	private String defaultTableTypeID = "";
	private HashMap<String, String> functionTypeMap = new HashMap<String, String>();
	
	private static String[] typeIDArray = {"XF000","XF100","XF110","XF200","XF290","XF300","XF310","XF390"};
	private static String[] typeNameArray = {res.getString("XF000Name"),res.getString("XF100Name"),res.getString("XF110Name"),res.getString("XF200Name"),
			res.getString("XF290Name"),res.getString("XF300Name"),res.getString("XF310Name"),res.getString("XF390Name")};
	private static String[] typeDescArray = {res.getString("XF000Desc"),res.getString("XF100Desc"),res.getString("XF110Desc"),res.getString("XF200Desc"),
			res.getString("XF290Desc"),res.getString("XF300Desc"),res.getString("XF310Desc"),res.getString("XF390Desc")};


	public DialogImportXEAF(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DialogImportXEAF(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogImportXEAF01"));

		jLabel4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText(res.getString("DialogImportXEAF02"));
		jLabel4.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 350, 25));
		jTextFieldImportFileName.setEditable(false);

		jLabel5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText(res.getString("DialogImportXEAF03"));
		jLabel5.setBounds(new Rectangle(5, 43, 170, 20));
		jTextFieldImportSystemName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportSystemName.setBounds(new Rectangle(180, 40, 350, 25));
		jTextFieldImportSystemName.setEditable(false);

		jLabel1.setText(res.getString("DialogImportXEAF09"));
		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setBounds(new Rectangle(5, 74, 170, 20));
		jComboBoxBlockFrom.setBounds(new Rectangle(180, 71, 350, 25));
		jComboBoxBlockFrom.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxBlockFrom.addActionListener(new DialogImportXEAF_jComboBoxBlockFrom_actionAdapter(this));

		jLabel2.setText(res.getString("DialogImportXEAF10"));
		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setBounds(new Rectangle(5, 105, 170, 20));
		jComboBoxBlockInto.setBounds(new Rectangle(180, 102, 350, 25));
		jComboBoxBlockInto.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxBlockInto.addActionListener(new DialogImportXEAF_jComboBoxBlockInto_actionAdapter(this));

		jTextArea1.setText(res.getString("DialogImportXEAF11"));
		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(9, 133, 522, 120));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());

		jProgressBar.setBounds(new Rectangle(30, 264, 340, 27));
		jProgressBar.setStringPainted(true);
		jProgressBar.setVisible(false);

		jButtonStart.setBounds(new Rectangle(30, 264, 340, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogImportXEAF07"));
		jButtonStart.addActionListener(new DialogImportXEAF_jButtonStart_actionAdapter(this));
		jButtonStart.setEnabled(false);

		jButtonCancel.setBounds(new Rectangle(400, 264, 110, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogImportXEAF08"));
		jButtonCancel.addActionListener(new DialogImportXEAF_jButtonCancel_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(540, 303));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jLabel4, null);
		panelMain.add(jLabel5, null);
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

			///////////////////////
			//Setup ComboBox1 & 2//
			///////////////////////
			XeadNode node;
			comboBoxModelBlockFrom.removeAllElements();
			elementList = domDocumentFrom.getElementsByTagName("Subsystem");
			for (int i = 0; i < elementList.getLength(); i++) {
				node = new XeadNode((org.w3c.dom.Element)elementList.item(i));
				comboBoxModelBlockFrom.addElement((Object)node);
			}
			comboBoxModelBlockFrom.sortElements();
			comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAF12"), 0);
			comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));
			comboBoxModelBlockInto.removeAllElements();
			elementList = frame_.domDocument.getElementsByTagName("Subsystem");
			for (int i = 0; i < elementList.getLength(); i++) {
				node = new XeadNode((org.w3c.dom.Element)elementList.item(i));
				comboBoxModelBlockInto.addElement((Object)node);
			}
			comboBoxModelBlockInto.sortElements();
			comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAF12"), 0);
			comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));

			jProgressBar.setValue(0);
			super.setVisible(true);
		}

		return importResult;
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
		}
	}
	
	void jComboBoxBlockInto_actionPerformed(ActionEvent e) {
		if (jComboBoxBlockFrom.getSelectedIndex() > 0 && jComboBoxBlockInto.getSelectedIndex() > 0) {
			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		XeadNode workNode;
		NodeList workElementList;
		String logFileName = "";

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);

			String dateTime = getStringValueOfDateAndTime();
			File file = new File(frame_.currentFileName);
			logFileName = file.getParent() + File.separator + "XeadImportLog" + dateTime + ".txt";
			fileWriter = new FileWriter(logFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(res.getString("DialogImportXEAF01") + "(" + dateTime + ")" + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(res.getString("DialogImportXEAF02") + ":" + jTextFieldImportFileName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAF31") + ":" + frame_.currentFileName + res.getString("DialogImportXEAF41") + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(res.getString("DialogImportXEAF03") + ":" + jTextFieldImportSystemName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAF32") + ":" + frame_.systemName + "\n");
			bufferedWriter.write("\n");

			createTableCounter = 0;
			cancelTableCounter = 0;
			createFunctionCounter = 0;
			cancelFunctionCounter = 0;
			missingTableCounter = 0;
			missingFunctionCounter = 0;

			workNode = (XeadNode)comboBoxModelBlockFrom.getSelectedItem();
			blockElementFrom = workNode.getElement();
			blockIDFrom = blockElementFrom.getAttribute("ID");
			bufferedWriter.write(jLabel1.getText() + ":" + blockElementFrom.getAttribute("Name") + "(" + blockElementFrom.getAttribute("ID") + ")" + "\n");

			workNode = (XeadNode)comboBoxModelBlockInto.getSelectedItem();
			blockElementInto = workNode.getElement();
			blockIDInto = blockElementInto.getAttribute("ID");
			bufferedWriter.write(jLabel2.getText() + ":" + blockElementInto.getAttribute("Name") + "(" + blockElementInto.getAttribute("SortKey") + ")" + "\n");

			workElementList = frame_.domDocument.getElementsByTagName("System");
			systemElement = (org.w3c.dom.Element)workElementList.item(0);
			
			importSubsystem();
			importTables();
			importFunctions();

			importResult = res.getString("DialogImportXEAF16") +
					createTableCounter + res.getString("DialogImportXEAF18") +
					cancelTableCounter + res.getString("DialogImportXEAF50") + "\n" +
					res.getString("DialogImportXEAF19") +
					createFunctionCounter + res.getString("DialogImportXEAF18") +
					cancelFunctionCounter + res.getString("DialogImportXEAF50") + "\n" +
					res.getString("DialogImportXEAF20") +
					missingTableCounter + res.getString("DialogImportXEAF21") + "\n" +
					res.getString("DialogImportXEAF22") +
					missingFunctionCounter + res.getString("DialogImportXEAF23") + "\n" +
					res.getString("DialogImportXEAF29") + logFileName + res.getString("DialogImportXEAF30") + "\n" + "\n";
		} catch (Exception ex1) {
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
		ArrayList<org.w3c.dom.Element> tableElementListFrom = new ArrayList<org.w3c.dom.Element>(); 
		boolean isAlreadyExisting;
		String idOfDefinition;

		////////////////////////////
		//Setup Default Table Type//
		////////////////////////////
		targetElementList = frame_.domDocument.getElementsByTagName("TableType");
		workElement = (org.w3c.dom.Element)targetElementList.item(0);
		defaultTableTypeID = workElement.getAttribute("ID");

		///////////////////////////////////////
		//Setup List of Tables to be imported//
		///////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				tableElementListFrom.add(workElement);
			}
		}

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(tableElementListFrom.size());

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		/////////////////////////////////////////////////
		//Import Table definitions into target document//
		/////////////////////////////////////////////////
		targetElementList = frame_.domDocument.getElementsByTagName("Table");
		for (int i = 0; i < tableElementListFrom.size(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString("Importing tables(" + (i+1) + "/" + tableElementListFrom.size() + ")");
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			isAlreadyExisting = false;
			idOfDefinition = tableElementListFrom.get(i).getAttribute("ID");

			for (int j = 0; j < targetElementList.getLength(); j++) {
				workElement = (org.w3c.dom.Element)targetElementList.item(j);
				if (workElement.getAttribute("SortKey").equals(idOfDefinition)) {
					isAlreadyExisting = true;
					try {
						bufferedWriter.write(tableElementListFrom.get(i).getAttribute("Name") +
								"(" + tableElementListFrom.get(i).getAttribute("ID") +
								"):" + res.getString("DialogImportXEAF48") + "\n");
					} catch (IOException ex1) {}
					cancelTableCounter++;
					break;
				}
			}

			if (!isAlreadyExisting) {
				String tableID = createTableDefinition(tableElementListFrom.get(i), blockIDInto);
				createTableCounter++;

				/////////////////////////////////////////
				//Create Subsystem attributes for Table//
				/////////////////////////////////////////
				org.w3c.dom.Element newElement = frame_.domDocument.createElement("SubsystemTable");
				newElement.setAttribute("TableID", tableID);
				int pos = 50 + 10 * i;
				newElement.setAttribute("BoxPosition", Integer.toString(pos) + "," + Integer.toString(pos));
				newElement.setAttribute("ExtDivLoc", "500");
				newElement.setAttribute("IntDivLoc", "0");
				newElement.setAttribute("ShowOnModel", "true");
				newElement.setAttribute("ShowInstance", "false");
				newElement.setAttribute("Instance", "");
				blockElementInto.appendChild(newElement);

				try {
					bufferedWriter.write(tableElementListFrom.get(i).getAttribute("Name") + "(" +
							tableElementListFrom.get(i).getAttribute("ID") +
							"):" + res.getString("DialogImportXEAF34") + "\n");
				} catch (IOException ex2) {}
			}
		}

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}
	}

	void importFunctions() {
		org.w3c.dom.Element workElement, fromFunctionElement, newFunctionElement;
		NodeList functionElementListInto;
		ArrayList<org.w3c.dom.Element> functionElementListFrom = new ArrayList<org.w3c.dom.Element>(); 
		boolean isAlreadyExisting;
		String idOfDefinition;

		///////////////////////////
		//Setup Function Type Map//
		///////////////////////////
		setupFunctionTypeMap();

		//////////////////////////////////////////
		//Setup List of Functions to be imported//
		/////////////////////////////////////////
		functionElementListInto = domDocumentFrom.getElementsByTagName("Function");
		for (int i = 0; i < functionElementListInto.getLength(); i++) {
			workElement = (org.w3c.dom.Element)functionElementListInto.item(i);
			if (workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				functionElementListFrom.add(workElement);
			}
		}

//		try {
//			bufferedWriter.write("\n");
//		} catch (IOException ex) {}

		////////////////////////////////////////////////////
		//Import Function definitions into target document//
		////////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(functionElementListFrom.size());
		
		ArrayList<org.w3c.dom.Element> fromFunctionArray = new ArrayList<org.w3c.dom.Element>();
		ArrayList<org.w3c.dom.Element> newFunctionArray = new ArrayList<org.w3c.dom.Element>();

		ArrayList<org.w3c.dom.Element> targetFunctionArray = new ArrayList<org.w3c.dom.Element>(); 
		functionElementListInto = frame_.domDocument.getElementsByTagName("Function");
		for (int i = 0; i < functionElementListInto.getLength(); i++) {
			workElement = (org.w3c.dom.Element)functionElementListInto.item(i);
			if (workElement.getAttribute("SubsystemID").equals(blockIDInto)) {
				targetFunctionArray.add(workElement);
			}
		}
		
		for (int i = 0; i < functionElementListFrom.size(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString("Importing functions(" + (i+1) + "/" + functionElementListFrom.size() + ")");
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			isAlreadyExisting = false;
			idOfDefinition = functionElementListFrom.get(i).getAttribute("ID");

			for (int j = 0; j < targetFunctionArray.size(); j++) {
				workElement = targetFunctionArray.get(j);
				if (workElement.getAttribute("SortKey").equals(idOfDefinition)) {
					isAlreadyExisting = true;
					try {
						bufferedWriter.write(functionElementListFrom.get(i).getAttribute("Name") +
								"(" + functionElementListFrom.get(i).getAttribute("ID") +
								"):" + res.getString("DialogImportXEAF48") + "\n");
					} catch (IOException ex1) {}
					cancelFunctionCounter++;
					break;
				}
			}

			if (!isAlreadyExisting) {
				try {
					bufferedWriter.write(functionElementListFrom.get(i).getAttribute("Name") + "(" +
							functionElementListFrom.get(i).getAttribute("ID") + "):" +
							res.getString("DialogImportXEAF34") + "\n");
				} catch (IOException ex2) {}

				org.w3c.dom.Element newElement = createFunctionDefinition(functionElementListFrom.get(i), blockIDInto);
				fromFunctionArray.add(functionElementListFrom.get(i));
				newFunctionArray.add(newElement);
				
				createFunctionCounter++;
			}
		}
		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}
		

		////////////////////////////////////////////////////////////
		//Update Function-called-information by Functions imported//
		////////////////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(fromFunctionArray.size());

		for (int i = 0; i < fromFunctionArray.size(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString("Checking functions called...");
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			fromFunctionElement = (org.w3c.dom.Element)fromFunctionArray.get(i);
			newFunctionElement = (org.w3c.dom.Element)newFunctionArray.get(i);
			updateFunctionsCalledOfNewFunction(fromFunctionElement, newFunctionElement, functionElementListInto);
		}
	}

	void updateFunctionsCalledOfNewFunction(org.w3c.dom.Element elementFrom, org.w3c.dom.Element elementNew, NodeList functionListInto) {
		org.w3c.dom.Element workElement;
		String action;
		int pos1, pos2;
		String convertedID;

		if (elementFrom.getAttribute("Type").equals("XF100")) {
			if (!elementFrom.getAttribute("DetailFunction").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("DetailFunction"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("DetailFunction")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					org.w3c.dom.Element childElement = frame_.domDocument.createElement("FunctionUsedByThis");
					childElement.setAttribute("FunctionID", convertedID);
					childElement.setAttribute("LaunchEvent", "");
					childElement.setAttribute("SortKey", "0");
					elementNew.appendChild(childElement);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF110")) {
			if (!elementFrom.getAttribute("BatchRecordFunction").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("BatchRecordFunction"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("BatchRecordFunction")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					org.w3c.dom.Element childElement = frame_.domDocument.createElement("FunctionUsedByThis");
					childElement.setAttribute("FunctionID", convertedID);
					childElement.setAttribute("LaunchEvent", "");
					childElement.setAttribute("SortKey", "0");
					elementNew.appendChild(childElement);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF200")) {
			if (!elementFrom.getAttribute("FunctionAfterInsert").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("FunctionAfterInsert"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("FunctionAfterInsert")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					org.w3c.dom.Element childElement = frame_.domDocument.createElement("FunctionUsedByThis");
					childElement.setAttribute("FunctionID", convertedID);
					childElement.setAttribute("LaunchEvent", "");
					childElement.setAttribute("SortKey", "0");
					elementNew.appendChild(childElement);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF300")) {
			if (!elementFrom.getAttribute("HeaderFunction").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("HeaderFunction"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("HeaderFunction")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					org.w3c.dom.Element childElement = frame_.domDocument.createElement("FunctionUsedByThis");
					childElement.setAttribute("FunctionID", convertedID);
					childElement.setAttribute("LaunchEvent", "");
					childElement.setAttribute("SortKey", "0");
					elementNew.appendChild(childElement);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF100")
				|| elementFrom.getAttribute("Type").equals("XF110")
				|| elementFrom.getAttribute("Type").equals("XF200")
				|| elementFrom.getAttribute("Type").equals("XF300")
				|| elementFrom.getAttribute("Type").equals("XF310")) {
			NodeList buttonList = elementFrom.getElementsByTagName("Button");
			for (int i = 0; i < buttonList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)buttonList.item(i);
				action = workElement.getAttribute("Action");
				pos1 = action.indexOf("CALL(");
				pos2 = action.indexOf(")");
				if (pos1 >= 0 && pos2 > pos1) {
					convertedID = convertFunctionID(action.substring(pos1+5, pos2), functionListInto);
					if (convertedID.equals("")) {
						missingFunctionCounter++;
						try {
							bufferedWriter.write(action.substring(pos1+5, pos2)
									+ res.getString("DialogImportXEAF38") + "\n");
						} catch (IOException ex1) {}
					} else {
						org.w3c.dom.Element childElement = frame_.domDocument.createElement("FunctionUsedByThis");
						childElement.setAttribute("FunctionID", convertedID);
						childElement.setAttribute("LaunchEvent", "");
						childElement.setAttribute("SortKey", "0");
						elementNew.appendChild(childElement);
					}
				}
			}
		}
	}
	
	String convertFunctionID(String originalID, NodeList functionListInto) {
		org.w3c.dom.Element workElement;
		String convertedID = "";
		if (!originalID.equals("")) {
			for (int i = 0; i < functionListInto.getLength(); i++) {
				workElement = (org.w3c.dom.Element)functionListInto.item(i);
				if (workElement.getAttribute("SortKey").equals(originalID)) {
					convertedID = workElement.getAttribute("ID");
					break;
				}
			}
		}
		return convertedID;
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

	String createTableDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		org.w3c.dom.Element workElement1;
		NodeList elementList1;
		int lastID = 0;
		int length = 0;
		int decimal = 0;
		String tableID = "";

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
		tableID = Integer.toString(lastID + 1);
		newElement.setAttribute("ID", tableID);
		newElement.setAttribute("Name", elementFrom.getAttribute("Name"));
		newElement.setAttribute("SortKey", elementFrom.getAttribute("ID"));
		newElement.setAttribute("SubsystemID", subsystemID);
		newElement.setAttribute("Descriptions", elementFrom.getAttribute("Remarks"));
		newElement.setAttribute("TableTypeID", defaultTableTypeID);
		systemElement.appendChild(newElement);

		/////////////////////
		//Create TableField//
		/////////////////////
		HashMap<String, String> fieldIDMap = new HashMap<String, String>();
		elementList1 = elementFrom.getElementsByTagName("Field");
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
			childElement.setAttribute("SortKey", workElement1.getAttribute("Order"));
			childElement.setAttribute("Descriptions", workElement1.getAttribute("Remarks"));
			childElement.setAttribute("Alias", workElement1.getAttribute("ID"));
			if (workElement1.getAttribute("TypeOptions").contains("VIRTUAL")) {
				childElement.setAttribute("AttributeType", "DERIVABLE");
			} else {
				childElement.setAttribute("AttributeType", "NATIVE");
			}
			if (workElement1.getAttribute("Size").equals("")) {
				length = 0;
			} else {
				length = Integer.parseInt(workElement1.getAttribute("Size"));				
			}
			if (workElement1.getAttribute("Decimal").equals("")) {
				decimal = 0;
			} else {
				decimal = Integer.parseInt(workElement1.getAttribute("Decimal"));				
			}
			childElement.setAttribute("DataTypeID", getIDOfDataType(workElement1.getAttribute("Type"), length, decimal));
			if (i < 20) {
				childElement.setAttribute("ShowOnModel", "true");
			} else {
				childElement.setAttribute("ShowOnModel", "false");
			}
			if (workElement1.getAttribute("Nullable").equals("T")) {
				childElement.setAttribute("NotNull", "false");
			} else {
				childElement.setAttribute("NotNull", "true");
			}
			if (workElement1.getAttribute("NoUpdate").equals("T")) {
				childElement.setAttribute("NoUpdate", "true");
			} else {
				childElement.setAttribute("NoUpdate", "false");
			}
			childElement.setAttribute("Default", "");

			newElement.appendChild(childElement);
			fieldIDMap.put(childElement.getAttribute("Alias"), childElement.getAttribute("ID"));
		}

		///////////////////
		//Create TableKey//
		///////////////////
		boolean hasPK = false;
		StringTokenizer workTokenizer;
		String fieldID;
		elementList1 = elementFrom.getElementsByTagName("Key");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			if (workElement1.getAttribute("Type").equals("PK")
					|| workElement1.getAttribute("Type").equals("SK")
					|| workElement1.getAttribute("Type").equals("XK")) {
				org.w3c.dom.Element childElement = frame_.domDocument.createElement("TableKey");
				childElement.setAttribute("ID", Integer.toString(i));
				childElement.setAttribute("Type", workElement1.getAttribute("Type"));
				if (workElement1.getAttribute("Type").equals("PK")) {
					childElement.setAttribute("SortKey", "000");
				} else {
					childElement.setAttribute("SortKey", "010");
				}
				workTokenizer = new StringTokenizer(workElement1.getAttribute("Fields"), ";" );
				while (workTokenizer.hasMoreTokens()) {
					fieldID = workTokenizer.nextToken();
					org.w3c.dom.Element grandChildElement = frame_.domDocument.createElement("TableKeyField");
					if (workElement1.getAttribute("Type").equals("XK")) {
						if (fieldID.contains("(D)")) {
							fieldID = fieldID.replace("(D)", "");
							grandChildElement.setAttribute("AscDesc", "D");
						} else {
							grandChildElement.setAttribute("AscDesc", "A");
						}
					}
					grandChildElement.setAttribute("FieldID", fieldIDMap.get(fieldID));
					grandChildElement.setAttribute("SortKey", Modeler.getFormatted4ByteString(i * 10));
					childElement.appendChild(grandChildElement);
				}
				newElement.appendChild(childElement);
			}
			if (workElement1.getAttribute("Type").equals("PK")) {
				hasPK = true;
			}
		}
		if (!hasPK) {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("TableKey");
			childElement.setAttribute("ID", Integer.toString(0));
			childElement.setAttribute("Type", "PK");
			childElement.setAttribute("SortKey", "000");
			newElement.appendChild(childElement);
		}
		
		return tableID;
	}

	void importSubsystem() {
		/////////////////////////////////////////
		//Update attributes of target subsystem//
		/////////////////////////////////////////
		blockElementInto.setAttribute("Name", blockElementFrom.getAttribute("Name"));
		blockElementInto.setAttribute("Descriptions", blockElementFrom.getAttribute("Descriptions"));
	}

	void setupFunctionTypeMap() {
		org.w3c.dom.Element workElement;
		int lastID;
		String idOfFunctionType;
		boolean isAlreadyExisting;

		functionTypeMap.clear();
		NodeList functionTypeListInto = frame_.domDocument.getElementsByTagName("FunctionType");
		
		for (int i = 0; i < typeIDArray.length; i++) {
			isAlreadyExisting = false;
			for (int j = 0; j < functionTypeListInto.getLength(); j++) {
				workElement = (org.w3c.dom.Element)functionTypeListInto.item(j);
				if (workElement.getAttribute("SortKey").equals(typeIDArray[i])) {
					isAlreadyExisting = true;
					functionTypeMap.put(typeIDArray[i], workElement.getAttribute("ID"));
					break;
				}
			}
			if (!isAlreadyExisting) {
				org.w3c.dom.Element newElement = frame_.domDocument.createElement("FunctionType");
				org.w3c.dom.Element lastElement = getLastDomElementOfTheType("FunctionType");
				if (lastElement == null) {
					lastID = 0;
				} else {
					lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				}
				idOfFunctionType = Integer.toString(lastID + 1);
				newElement.setAttribute("ID", idOfFunctionType);
				newElement.setAttribute("SortKey", typeIDArray[i]);
				newElement.setAttribute("Name", typeNameArray[i]);
				newElement.setAttribute("Descriptions", typeDescArray[i]);
				systemElement.appendChild(newElement);
				functionTypeMap.put(typeIDArray[i], idOfFunctionType);
			}
		}
		
	}

	org.w3c.dom.Element createFunctionDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		int lastID = 0;

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
		newElement.setAttribute("SortKey", elementFrom.getAttribute("ID"));
		newElement.setAttribute("SubsystemID", subsystemID);
		newElement.setAttribute("Return", res.getString("DialogImportXEAF04"));
		newElement.setAttribute("Descriptions", "");
		newElement.setAttribute("FunctionTypeID", functionTypeMap.get(elementFrom.getAttribute("Type")));

		//////////////////////////////////
		//Make up for attributes by type//
		//////////////////////////////////
		if (elementFrom.getAttribute("Type").equals("XF000")) {
			newElement.setAttribute("Summary", typeDescArray[0]);
			newElement.setAttribute("Parameters", "*None");
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF100")) {
			newElement.setAttribute("Summary", typeDescArray[1]);
			newElement.setAttribute("Parameters", "*None");
			setupElementForFunctionXF100(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF110")) {
			newElement.setAttribute("Summary", typeDescArray[2]);
			newElement.setAttribute("Parameters", "*None");
			setupElementForFunctionXF110(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF200")) {
			newElement.setAttribute("Summary", typeDescArray[3]);
			setupElementForFunctionXF200(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF290")) {
			newElement.setAttribute("Summary", typeDescArray[4]);
			setupElementForFunctionXF290(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF300")) {
			newElement.setAttribute("Summary", typeDescArray[5]);
			setupElementForFunctionXF300(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF310")) {
			newElement.setAttribute("Summary", typeDescArray[6]);
			setupElementForFunctionXF310(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		if (elementFrom.getAttribute("Type").equals("XF390")) {
			newElement.setAttribute("Summary", typeDescArray[7]);
			setupElementForFunctionXF390(elementFrom, newElement);
			systemElement.appendChild(newElement);
		}
		
		return newElement;
	}

	void setupElementForFunctionXF100(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF13"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "PRIMARY");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
		}
	}

	void setupElementForFunctionXF110(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF13"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "PRIMARY");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "+");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
		}
		if (!elementFrom.getAttribute("BatchTable").equals("")) {
			tableElementInto = convertTableID(elementFrom.getAttribute("BatchTable"));
			if (tableElementInto == null) {
				missingTableCounter++;
				try {
					bufferedWriter.write(elementFrom.getAttribute("BatchTable")
							+ res.getString("DialogImportXEAF37") + "\n");
				} catch (IOException ex1) {}
			} else {
				org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
				childElement.setAttribute("ID", "2");
				childElement.setAttribute("NameExtension", "");
				childElement.setAttribute("SortKey", "020");
				childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF24"));
				childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
				childElement.setAttribute("Position", "JOIN");
				childElement.setAttribute("OpC", "+");
				childElement.setAttribute("OpR", "");
				childElement.setAttribute("OpU", "");
				childElement.setAttribute("OpD", "");
				newElement.appendChild(childElement);
			}
		}
	}

	void setupElementForFunctionXF200(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF13"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "PRIMARY");
			childElement.setAttribute("OpC", "+");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "+");
			childElement.setAttribute("OpD", "+");
			newElement.appendChild(childElement);
			newElement.setAttribute("Parameters", getKeyFieldNameOfTable(tableElementInto));
		}
	}

	void setupElementForFunctionXF290(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF13"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "PRIMARY");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
			newElement.setAttribute("Parameters", getKeyFieldNameOfTable(tableElementInto));
		}
	}

	void setupElementForFunctionXF300(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("HeaderTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("HeaderTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF13"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "HEADER");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
			newElement.setAttribute("Parameters", getKeyFieldNameOfTable(tableElementInto));
		}
		org.w3c.dom.Element workElement;
		NodeList detailList = elementFrom.getElementsByTagName("Detail");
		for (int i = 0; i < detailList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)detailList.item(i);
			tableElementInto = convertTableID(workElement.getAttribute("Table"));
			if (tableElementInto == null) {
				missingTableCounter++;
				try {
					bufferedWriter.write(workElement.getAttribute("Table")
							+ res.getString("DialogImportXEAF37") + "\n");
				} catch (IOException ex1) {}
			} else {
				org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
				childElement.setAttribute("ID", "2");
				childElement.setAttribute("NameExtension", "");
				childElement.setAttribute("SortKey", "020");
				childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF15"));
				childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
				childElement.setAttribute("Position", "DETAIL");
				childElement.setAttribute("OpC", "");
				childElement.setAttribute("OpR", "+");
				childElement.setAttribute("OpU", "");
				childElement.setAttribute("OpD", "");
				newElement.appendChild(childElement);
			}
		}
	}

	void setupElementForFunctionXF310(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("HeaderTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("HeaderTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF14"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "HEADER");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "+");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
			newElement.setAttribute("Parameters", getKeyFieldNameOfTable(tableElementInto));
		}
		tableElementInto = convertTableID(elementFrom.getAttribute("DetailTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("DetailTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "2");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF15"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "DETAIL");
			childElement.setAttribute("OpC", "+");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "+");
			childElement.setAttribute("OpD", "+");
			newElement.appendChild(childElement);
		}
	}

	void setupElementForFunctionXF390(org.w3c.dom.Element elementFrom, org.w3c.dom.Element newElement) {
		org.w3c.dom.Element tableElementInto = convertTableID(elementFrom.getAttribute("HeaderTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("HeaderTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "1");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF14"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "HEADER");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
			newElement.setAttribute("Parameters", getKeyFieldNameOfTable(tableElementInto));
		}
		tableElementInto = convertTableID(elementFrom.getAttribute("DetailTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("DetailTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			org.w3c.dom.Element childElement = frame_.domDocument.createElement("IOTable");
			childElement.setAttribute("ID", "2");
			childElement.setAttribute("NameExtension", "");
			childElement.setAttribute("SortKey", "020");
			childElement.setAttribute("Descriptions", res.getString("DialogImportXEAF15"));
			childElement.setAttribute("TableID", tableElementInto.getAttribute("ID"));
			childElement.setAttribute("Position", "DETAIL");
			childElement.setAttribute("OpC", "");
			childElement.setAttribute("OpR", "+");
			childElement.setAttribute("OpU", "");
			childElement.setAttribute("OpD", "");
			newElement.appendChild(childElement);
		}
	}
	
	String getKeyFieldNameOfTable(org.w3c.dom.Element tableElement) {
		org.w3c.dom.Element element;
		String keyFieldName = "";

		NodeList keyList = tableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < keyList.getLength(); i++) {
			element = (org.w3c.dom.Element)keyList.item(i);
			if (element.getAttribute("Type").equals("PK")) {
				ArrayList<String> keyFieldIDList = new ArrayList<String>();
				NodeList tableKeyFieldList = element.getElementsByTagName("TableKeyField");
				for (int j = 0; j < tableKeyFieldList.getLength(); j++) {
					element = (org.w3c.dom.Element)tableKeyFieldList.item(j);
					keyFieldIDList.add(element.getAttribute("FieldID"));
				}
				StringBuffer bf = new StringBuffer();
				NodeList fieldList = tableElement.getElementsByTagName("TableField");
				for (int j = 0; j < keyFieldIDList.size(); j++) {
					for (int k = 0; k < fieldList.getLength(); k++) {
						element = (org.w3c.dom.Element)fieldList.item(k);
						if (element.getAttribute("ID").equals(keyFieldIDList.get(j))) {
							if (!bf.toString().equals("")) {
								bf.append(", ");
							}
							bf.append(element.getAttribute("Name"));
							break;
						}
					}
				}
				keyFieldName = bf.toString();
				break;
			}
		}
		return keyFieldName;
	}
	
	String getIDOfDataType(String dataType, int length, int decimal) {
		String idOfDataType = "";
		boolean isAlreadyExisting = false;
		int lastID = 0;
		org.w3c.dom.Element elementInto;

		NodeList typeListInto = frame_.domDocument.getElementsByTagName("DataType");

		for (int j = 0; j < typeListInto.getLength(); j++) {
			elementInto = (org.w3c.dom.Element)typeListInto.item(j);
			if (elementInto.getAttribute("SortKey").equals(dataType + "_" + length)) {
				idOfDataType = elementInto.getAttribute("ID");
				isAlreadyExisting = true;
				break;
			}
		}
		if (!isAlreadyExisting) {
			org.w3c.dom.Element newElement = frame_.domDocument.createElement("DataType");
			org.w3c.dom.Element lastElement = getLastDomElementOfTheType("DataType");
			if (lastElement == null) {
				lastID = 0;
			} else {
				lastID = Integer.parseInt(lastElement.getAttribute("ID"));
			}
			idOfDataType = Integer.toString(lastID + 1);
			newElement.setAttribute("ID", idOfDataType);
			newElement.setAttribute("SortKey", dataType + "_" + length);
			newElement.setAttribute("Name", dataType + "_" + length);
			newElement.setAttribute("BasicType", "String");
			if (dataType.contains("INT")
					|| dataType.contains("DOUBLE")
					|| dataType.equals("DECIMAL")
					|| dataType.equals("NUMERIC")
					|| dataType.equals("REAL")) {
				newElement.setAttribute("BasicType", "SignedNumber");
			}
			if (dataType.contains("DATE")) {
				newElement.setAttribute("BasicType", "Date");
			}
			if (dataType.contains("BINARY")
					|| dataType.contains("LOB")) {
				newElement.setAttribute("BasicType", "Object");
			}
			newElement.setAttribute("Length", Integer.toString(length));
			newElement.setAttribute("Decimal", Integer.toString(decimal));
			if (dataType.contains("CHAR")) {
				newElement.setAttribute("SQLExpression", dataType + "(" + length + ")");
			} else {
				newElement.setAttribute("SQLExpression", dataType);
			}
			systemElement.appendChild(newElement);
		}
		
		return idOfDataType;
	}

	org.w3c.dom.Element convertTableID(String tableIDFrom) {
		org.w3c.dom.Element convertedElement = null;
		org.w3c.dom.Element element;

		if (!tableIDFrom.equals("")) {
			NodeList nodeList = frame_.domDocument.getElementsByTagName("Table");
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (org.w3c.dom.Element)nodeList.item(i);
				if (element.getAttribute("SortKey").equals(tableIDFrom)) {
					convertedElement = element;
					break;
				}
			}
		}

		return convertedElement;
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

	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	class XeadNode implements Comparable {
		private org.w3c.dom.Element domNode_;
		public XeadNode(org.w3c.dom.Element node) {
			super();
			domNode_ = node;
		}
		public String toString() {
			String str = "";
			str = this.getName();
			return str;
		}
		public String getName() {
			if (domNode_.getAttribute("SortKey").equals("")) {
				return domNode_.getAttribute("ID") + " " + domNode_.getAttribute("Name");
			} else {
				return domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
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

//	class ElementFieldComparator implements java.util.Comparator<org.w3c.dom.Element> {
//		public int compare(org.w3c.dom.Element element1, org.w3c.dom.Element element2) {
//			String value1, value2;
//			value1 = element1.getAttribute("Order");
//			value2 = element2.getAttribute("Order");
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

//	class SortableDomElementFieldListModel extends DefaultListModel {
//		private static final long serialVersionUID = 1L;
//		public void sortElements() {
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
//		}
//	}
}

class DialogImportXEAF_jComboBoxBlockFrom_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jComboBoxBlockFrom_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxBlockFrom_actionPerformed(e);
	}
}

class DialogImportXEAF_jComboBoxBlockInto_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jComboBoxBlockInto_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxBlockInto_actionPerformed(e);
	}
}

class DialogImportXEAF_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jButtonStart_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogImportXEAF_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jButtonCancel_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
