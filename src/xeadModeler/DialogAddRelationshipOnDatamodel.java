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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.w3c.dom.NodeList;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import xeadModeler.Modeler.*;

public class DialogAddRelationshipOnDatamodel extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel jPanelMain = new JPanel();
	private JPanel jPanelRadioButtons = new JPanel();
	private JLabel jLabelName = new JLabel();
	private KanjiTextField jTextFieldName = new KanjiTextField();
	private JLabel jLabelAlias = new JLabel();
	private JTextField jTextFieldAlias = new JTextField();
	private JRadioButton jRadioButtonPasteFields = new JRadioButton();
	private JRadioButton jRadioButtonReuseFields = new JRadioButton();
	private JButton jButtonOK = new JButton();
	private JButton jButtonCancel = new JButton();
	private JScrollPane jScrollPaneDescriptions = new JScrollPane();
	private JTextArea jTextAreaDescriptions = new JTextArea();
	private boolean buttonOKIsPressed;
	private Modeler frame_;
	private XeadTreeNode sourceTableNode_;
	private org.w3c.dom.Element sourceKeyElement_;
	private XeadTreeNode targetTableNode_;
	private ArrayList<String> keyFieldNameList = new ArrayList<String>();
	private ArrayList<String> keyFieldAliasList = new ArrayList<String>();
	private ArrayList<String> keyFieldDataTypeIDList = new ArrayList<String>();
	private ArrayList<String> keyFieldNameListChanged = new ArrayList<String>();
	private ArrayList<String> keyFieldAliasListChanged = new ArrayList<String>();

	public DialogAddRelationshipOnDatamodel(Modeler frame) {
		super(frame, res.getString("DialogAddRelationshipOnDatamodel1"), true);
		try {
			frame_ = frame;
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		jPanelMain.setLayout(null);
		jPanelMain.setPreferredSize(new Dimension(500, 290));
		jPanelMain.setBorder(BorderFactory.createEtchedBorder());
		jTextAreaDescriptions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextAreaDescriptions.setLineWrap(true);
		jTextAreaDescriptions.setText(res.getString("DialogAddRelationshipOnDatamodel2"));
		jTextAreaDescriptions.setEditable(false);
		jTextAreaDescriptions.setBackground(SystemColor.control);
		jScrollPaneDescriptions.getViewport().add(jTextAreaDescriptions, null);
		jScrollPaneDescriptions.setBounds(new Rectangle(10, 10, 480, 95));
		jPanelRadioButtons.setLayout(null);
		jPanelRadioButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelRadioButtons.setBounds(new Rectangle(10, 110, 481, 140));
		jRadioButtonPasteFields.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonPasteFields.setText(res.getString("DialogAddRelationshipOnDatamodel3"));
		jRadioButtonPasteFields.setBounds(new Rectangle(5, 9, 470, 25));
		jRadioButtonPasteFields.addChangeListener(new DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter(this));
		jRadioButtonReuseFields.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonReuseFields.setText(res.getString("DialogAddRelationshipOnDatamodel4"));
		jRadioButtonReuseFields.setBounds(new Rectangle(5, 101, 470, 25));
		jRadioButtonReuseFields.addChangeListener(new DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter(this));
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(jRadioButtonPasteFields);
		buttonGroup.add(jRadioButtonReuseFields);
		jLabelName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelName.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelName.setText(res.getString("DialogAddRelationshipOnDatamodel5"));
		jLabelName.setBounds(new Rectangle(5, 38, 130, 22));
		jTextFieldName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName.setBounds(new Rectangle(140, 37, 320, 25));
		jLabelAlias.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelAlias.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelAlias.setText(res.getString("DialogAddRelationshipOnDatamodel6"));
		jLabelAlias.setBounds(new Rectangle(5, 69, 130, 22));
		jTextFieldAlias.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldAlias.setBounds(new Rectangle(140, 68, 320, 25));
		jPanelRadioButtons.add(jRadioButtonPasteFields);
		jPanelRadioButtons.add(jRadioButtonReuseFields);
		jPanelRadioButtons.add(jLabelName);
		jPanelRadioButtons.add(jTextFieldName);
		jPanelRadioButtons.add(jLabelAlias);
		jPanelRadioButtons.add(jTextFieldAlias);
		jButtonOK.setBounds(new Rectangle(40, 255, 100, 27));
		jButtonOK.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogAddRelationshipOnDatamodel_jButtonOK_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(340, 255, 100, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogAddRelationshipOnDatamodel7"));
		jButtonCancel.addActionListener(new DialogAddRelationshipOnDatamodel_jButtonCancel_actionAdapter(this));
		jPanelMain.add(jScrollPaneDescriptions, null);
		jPanelMain.add(jPanelRadioButtons, null);
		jPanelMain.add(jButtonOK, null);
		jPanelMain.add(jButtonCancel, null);
		this.getContentPane().add(jPanelMain);
	}

	public boolean request(XeadTreeNode sourceTableNode, org.w3c.dom.Element sourceKeyElement, XeadTreeNode targetTableNode) {
		sourceTableNode_ = sourceTableNode;
		sourceKeyElement_ = sourceKeyElement;
		targetTableNode_ = targetTableNode;
		//
		buttonOKIsPressed = false;
		keyFieldDataTypeIDList.clear();
		keyFieldNameList.clear();
		keyFieldAliasList.clear();
		//
		// Retrieve key fields information of source key //
		org.w3c.dom.Element element;
		XeadTreeNode fieldNode;
		NodeList keyFieldList = sourceKeyElement_.getElementsByTagName("TableKeyField");
		SortableDomElementListModel sortableDomElementListModel = frame_.new SortableDomElementListModel();
		sortableDomElementListModel.removeAllElements();
		for (int i = 0; i < keyFieldList.getLength(); i++) {
			sortableDomElementListModel.addElement((Object)keyFieldList.item(i));
		}
		sortableDomElementListModel.sortElements();
		StringBuffer bfName = new StringBuffer();
		StringBuffer bfAlias = new StringBuffer();
		StringBuffer bfWork = new StringBuffer();
		for (int i = 0; i < sortableDomElementListModel.getSize(); i++) {
			if (i > 0) {
				bfName.append(";");
				bfAlias.append(";");
				bfWork.append(";");
			}
			element = (org.w3c.dom.Element)sortableDomElementListModel.getElementAt(i);
			fieldNode = frame_.getSpecificXeadTreeNode("TableField", sourceTableNode_.getElement().getAttribute("ID"), element.getAttribute("FieldID"));
			bfName.append(fieldNode.getElement().getAttribute("Name"));
			bfAlias.append(fieldNode.getElement().getAttribute("Alias"));
			bfWork.append("");
			keyFieldDataTypeIDList.add(fieldNode.getElement().getAttribute("DataTypeID"));
			keyFieldNameList.add(fieldNode.getElement().getAttribute("Name"));
			keyFieldAliasList.add(fieldNode.getElement().getAttribute("Alias"));
		}
		jTextFieldName.setText(bfName.toString());
		if (bfAlias.toString().equals(bfWork.toString())) {
			jTextFieldAlias.setText("");
		} else {
			jTextFieldAlias.setText(bfAlias.toString());
		}
		//
		////////////////////////////////////////////////////////////////////////////
		// Check if target table key status                                       //
		// status 0: PK of target table contains source key                       //
		// status 1: target table contains fields equivalent to source key fields //
		// status 2: other than 0 nor 1                                           //
		////////////////////////////////////////////////////////////////////////////
		int statusOfTarget = 0;
		if (targetTableNode_.getElement().getAttribute("ID").equals(sourceTableNode_.getElement().getAttribute("ID"))) {
			statusOfTarget = 2;
		} else {
			boolean hasNone = true;
			NodeList targetTableKeyList = targetTableNode_.getElement().getElementsByTagName("TableKey");
			element = (org.w3c.dom.Element)targetTableKeyList.item(0);
			NodeList targetTablePKFieldList = element.getElementsByTagName("TableKeyField");
			for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
				hasNone = true;
				for (int j = 0; j < targetTablePKFieldList.getLength(); j++) {
					element = (org.w3c.dom.Element)targetTablePKFieldList.item(j);
					fieldNode = frame_.getSpecificXeadTreeNode("TableField", targetTableNode_.getElement().getAttribute("ID"), element.getAttribute("FieldID"));
					if (fieldNode.getElement().getAttribute("DataTypeID").equals(keyFieldDataTypeIDList.get(i)) ) {
						hasNone = false;
						break;
					}
				}
				if (hasNone) {
					break;
				}
			}
			if (hasNone) {
				statusOfTarget = 1;
			}
			//
			if (statusOfTarget == 1) {
				NodeList targetTableFieldList = targetTableNode_.getElement().getElementsByTagName("TableField");
				for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
					hasNone = true;
					for (int j = 0; j < targetTableFieldList.getLength(); j++) {
						element = (org.w3c.dom.Element)targetTableFieldList.item(j);
						if (element.getAttribute("DataTypeID").equals(keyFieldDataTypeIDList.get(i)) ) {
							hasNone = false;
							break;
						}
					}
					if (hasNone) {
						break;
					}
				}
				if (hasNone) {
					statusOfTarget = 2;
				}
			}
		}
		//
		jRadioButtonPasteFields.setSelected(true);
		if (statusOfTarget == 0 || statusOfTarget == 1) {
			jRadioButtonReuseFields.setEnabled(true);
		}
		if (statusOfTarget == 2) {
			jRadioButtonReuseFields.setEnabled(false);
		}
		//
		jPanelMain.getRootPane().setDefaultButton(jButtonOK);
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		this.setVisible(true);
		//
		return buttonOKIsPressed;
	}
	
	void jRadioButton_stateChanged(ChangeEvent e) {
		if (jRadioButtonPasteFields.isSelected()) {
			jTextFieldName.setEnabled(true);
			jTextFieldAlias.setEnabled(true);
		} else {
			jTextFieldName.setEnabled(false);
			jTextFieldAlias.setEnabled(false);
		}
	}

	void jButtonOK_actionPerformed(ActionEvent e) {
		if (jRadioButtonPasteFields.isSelected()) {
			keyFieldNameListChanged.clear();
			keyFieldAliasListChanged.clear();
			//
			StringTokenizer tokenizerName = new StringTokenizer(jTextFieldName.getText(), ";");
			while (tokenizerName.hasMoreTokens()) {
				keyFieldNameListChanged.add(tokenizerName.nextToken());
				if (jTextFieldAlias.getText().equals("")) {
					keyFieldAliasListChanged.add("");
				}
			}
			if (!jTextFieldAlias.getText().equals("")) {
				StringTokenizer tokenizerAlias = new StringTokenizer(jTextFieldAlias.getText(), ";");
				while (tokenizerAlias.hasMoreTokens()) {
					keyFieldAliasListChanged.add(tokenizerAlias.nextToken());
				}
			}
			//
			if (keyFieldNameListChanged.size() == keyFieldAliasListChanged.size()
					&& keyFieldNameListChanged.size() == keyFieldDataTypeIDList.size()) {
				//
				org.w3c.dom.Element element;
				boolean noError = true;
				NodeList targetFieldList = targetTableNode_.getElement().getElementsByTagName("TableField");
				for (int i = 0; i < targetFieldList.getLength(); i++) {
					element = (org.w3c.dom.Element)targetFieldList.item(i);
					for (int j = 0; j < keyFieldNameListChanged.size(); j++) {
						if (keyFieldNameListChanged.get(j).equals(element.getAttribute("Name"))
								|| (keyFieldAliasListChanged.get(j).equals(element.getAttribute("Alias")))
									&& !element.getAttribute("Alias").equals("")) {
							noError = false;
							JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamode20"));
							break;
						}
					}
					if (!noError) {
						break;
					}
				}
				//
				if (noError) {
					pasteKeyFieldsIntoTargetTable();
					buttonOKIsPressed = true;
					this.setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamodel8"));
			}
		}
		//
		if (jRadioButtonReuseFields.isSelected()) {
			reuseCurrentFieldsAsForeignKey();
			buttonOKIsPressed = true;
			this.setVisible(false);
		}
	}
	
	public void pasteKeyFieldsIntoTargetTable() {
		//frame_.addNodeToTheArrayOfFieldListNodeToBeRenumbered((XeadTreeNode)targetTableNode_.getChildAt(0));
		XeadTreeNode newFieldNode, newTableKeyNode;
		NodeList nodeList1, nodeList2;
		org.w3c.dom.Element newElement, element, element1, newElementChild;
		org.w3c.dom.Element lastElement = null;
		int intFieldID = 0;
		int lastID = 0;
		ArrayList<String> keyFieldIDList = new ArrayList<String>();
		//
		frame_.getCurrentMainTreenode().updateFields();
		//
		//Get biggest values of field ID and SortKey in the Table//
		nodeList1 = targetTableNode_.getElement().getElementsByTagName("TableField");
		for (int i = 0; i < nodeList1.getLength(); i++) {
			element1 = (org.w3c.dom.Element)nodeList1.item(i);
			intFieldID = Integer.parseInt(element1.getAttribute("ID"));
			if (intFieldID > lastID) {
				lastID = intFieldID;
			}
		}
		//
		// Add new fields //
		for (int k = 0; k < keyFieldNameListChanged.size(); k++) {
			newElement = frame_.getDomDocument().createElement("TableField");
			//newElement.setAttribute("ID", Integer.toString(lastID + 1));
			lastID++;
			newElement.setAttribute("ID", Integer.toString(lastID));
			newElement.setAttribute("Name", keyFieldNameListChanged.get(k));
			newElement.setAttribute("DataTypeID", keyFieldDataTypeIDList.get(k));
			newElement.setAttribute("Alias", keyFieldAliasListChanged.get(k));
			newElement.setAttribute("AttributeType", "NATIVE");
			newElement.setAttribute("NotNull", "true");
			newElement.setAttribute("Default", "");
			newElement.setAttribute("Descriptions", "");
			newElement.setAttribute("ShowOnModel", "true");
			newElement.setAttribute("SortKey", ((XeadTreeNode)targetTableNode_.getChildAt(0)).getNextSortKeyOfTableField(targetTableNode_));
			newFieldNode = frame_.new XeadTreeNode("TableField", newElement);
			keyFieldIDList.add(newElement.getAttribute("ID"));
			//
			//Insert DOM Element before Key element//
			nodeList1 = targetTableNode_.getElement().getElementsByTagName("TableKey");
			if (nodeList1.getLength() > 0) {
				element = (org.w3c.dom.Element)nodeList1.item(0);
				targetTableNode_.getElement().insertBefore(newElement, element);
			} else {
				targetTableNode_.getElement().appendChild(newElement);
			}
			//
			//Add TreeNode of TableField//
			((XeadTreeNode)targetTableNode_.getChildAt(0)).add(newFieldNode);
			//
			//Add as a IOTable Field//
			String tableID = targetTableNode_.getElement().getAttribute("ID");
			nodeList2 = frame_.getDomDocument().getElementsByTagName("IOTable");
			for (int i = 0; i < nodeList2.getLength(); i++) {
				element1 = (org.w3c.dom.Element)nodeList2.item(i);
				if (element1.getAttribute("TableID").equals(tableID)) {
					newElementChild = frame_.getDomDocument().createElement("IOTableField");
					newElementChild.setAttribute("FieldID", newElement.getAttribute("ID"));
					newElementChild.setAttribute("Descriptions", "");
					element1.appendChild(newElementChild);
				}
			}
			//
			//Add Log of Add//
			frame_.getXeadUndoManager().addLogOfAdd(newFieldNode);
		}
		//
		// Add node of Foreign Key //
		lastID = 0;
		int elementID;
		int lastSortKey = 0;
		NodeList nodeListToGetLastNumber = targetTableNode_.getElement().getElementsByTagName("TableKey");
		for (int i = 0; i < nodeListToGetLastNumber.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeListToGetLastNumber.item(i);
			if (lastElement == null) {
				lastElement = element;
			} else {
				elementID = Integer.parseInt(element.getAttribute("ID"));
				lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				if (elementID > lastID) {
					lastElement = element;
				}
			}
			if (!element.getAttribute("SortKey").equals("")) {
				if (lastSortKey < Integer.parseInt(lastElement.getAttribute("SortKey"))) {
					lastSortKey = Integer.parseInt(lastElement.getAttribute("SortKey"));
				}
			}
		}
		lastID = Integer.parseInt(lastElement.getAttribute("ID"));
		newElement = frame_.getDomDocument().createElement("TableKey");
		newElement.setAttribute("ID", Integer.toString(lastID + 1));
		newElement.setAttribute("Type", "FK");
		newElement.setAttribute("SortKey", "010");
		lastSortKey = 0;
		for (int k = 0; k < keyFieldIDList.size(); k++) {
			newElementChild = frame_.getDomDocument().createElement("TableKeyField");
			newElementChild.setAttribute("FieldID", keyFieldIDList.get(k));
			newElementChild.setAttribute("SortKey", Modeler.getFormatted4ByteString(lastSortKey + 10));
			newElement.appendChild(newElementChild);
		}
		targetTableNode_.getElement().appendChild(newElement);
		newTableKeyNode = frame_.new XeadTreeNode("TableKey", newElement);
		((XeadTreeNode)targetTableNode_.getChildAt(1)).add(newTableKeyNode);
		frame_.getXeadUndoManager().addLogOfAdd(newTableKeyNode);
		//
		// Add a relationship //
		frame_.getXeadUndoManager().saveNodeBeforeModified(newTableKeyNode);
		newElement = frame_.getDomDocument().createElement("Relationship");
		newElement.setAttribute("ID", Integer.toString(frame_.incrementLastIDOfRelationship()));
		newElement.setAttribute("Table1ID", sourceTableNode_.getElement().getAttribute("ID"));
		newElement.setAttribute("TableKey1ID", sourceKeyElement_.getAttribute("ID"));
		newElement.setAttribute("Table2ID", targetTableNode_.getElement().getAttribute("ID"));
		newElement.setAttribute("TableKey2ID", Integer.toString(lastID + 1));
		newElement.setAttribute("Type", "REFFER");
		newElement.setAttribute("ExistWhen1", "");
		newElement.setAttribute("ExistWhen2", "");
		frame_.getSystemElement().appendChild(newElement);
		frame_.getXeadUndoManager().addLogAfterModified(newTableKeyNode);
		//
		// Add Relationship attributes to each Subsystem if necessary//
		NodeList subsystemNodeList = frame_.getDomDocument().getElementsByTagName("Subsystem");
		for (int i = 0; i < subsystemNodeList.getLength(); i++) {
			//sourceTableNode_.createSubsystemAttributesForRelationship(newElement, (org.w3c.dom.Element)subsystemNodeList.item(i));
			frame_.createSubsystemAttributesForRelationship(newElement, (org.w3c.dom.Element)subsystemNodeList.item(i));
		}
		//
		frame_.getXeadUndoManager().saveNodeBeforeModified(frame_.getCurrentMainTreenode());
	}

	public void reuseCurrentFieldsAsForeignKey() {
		org.w3c.dom.Element newElement, element, element1, element2, newElementChild;
		org.w3c.dom.Element lastElement = null;
		int lastID = 0;
		int wrkCnt;
		boolean eachFieldSpecified;
		ArrayList<String> keyFieldIDList = new ArrayList<String>();
		NodeList fieldListOnTargetTable = targetTableNode_.getElement().getElementsByTagName("TableField");
		NodeList keyListOnTargetTable = targetTableNode_.getElement().getElementsByTagName("TableKey");
		XeadTreeNode availableTableKeyNode = null;
		//
		frame_.getCurrentMainTreenode().updateFields();
		//
		// Search and specify corresponding field //
		keyFieldIDList.clear();
		eachFieldSpecified = true;
		for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
			wrkCnt = 0;
			for (int j = 0; j < fieldListOnTargetTable.getLength(); j++) {
				element1 = (org.w3c.dom.Element)fieldListOnTargetTable.item(j);
				if (element1.getAttribute("DataTypeID").equals(keyFieldDataTypeIDList.get(i))) {
					wrkCnt++;
					keyFieldIDList.add(element1.getAttribute("ID"));
				}
			}
			if (wrkCnt != 1) {
				eachFieldSpecified = false;
			}
		}
		//
		if (!eachFieldSpecified) {
			keyFieldIDList.clear();
			eachFieldSpecified = true;
			for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
				wrkCnt = 0;
				for (int j = 0; j < fieldListOnTargetTable.getLength(); j++) {
					element1 = (org.w3c.dom.Element)fieldListOnTargetTable.item(j);
					if (element1.getAttribute("DataTypeID").equals(keyFieldDataTypeIDList.get(i))
							&& (element1.getAttribute("Name").equals(keyFieldNameList.get(i))
								|| element1.getAttribute("Alias").equals(keyFieldAliasList.get(i)))) {
						wrkCnt++;
						keyFieldIDList.add(element1.getAttribute("ID"));
					}
				}
				if (wrkCnt != 1) {
					eachFieldSpecified = false;
				}
			}
		}
		//
		if (!eachFieldSpecified) {
			keyFieldIDList.clear();
			eachFieldSpecified = true;
			for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
				wrkCnt = 0;
				for (int j = 0; j < fieldListOnTargetTable.getLength(); j++) {
					element1 = (org.w3c.dom.Element)fieldListOnTargetTable.item(j);
					if (element1.getAttribute("DataTypeID").equals(keyFieldDataTypeIDList.get(i))
							&& element1.getAttribute("Name").equals(keyFieldNameList.get(i))
							&& element1.getAttribute("Alias").equals(keyFieldAliasList.get(i))) {
						wrkCnt++;
						keyFieldIDList.add(element1.getAttribute("ID"));
					}
				}
				if (wrkCnt != 1) {
					eachFieldSpecified = false;
				}
			}
		}
		//
		if (eachFieldSpecified) {
			//
			// Search if key definition is available //
			NodeList keyFieldElementList;
			availableTableKeyNode = null;
			String relationshipType = "";
			for (int i = 0; i < keyListOnTargetTable.getLength(); i++) {
				wrkCnt = 0;
				element1 = (org.w3c.dom.Element)keyListOnTargetTable.item(i);
				keyFieldElementList = element1.getElementsByTagName("TableKeyField");
				if (keyFieldElementList.getLength() >= keyFieldIDList.size()) {
					for (int j = 0; j < keyFieldElementList.getLength(); j++) {
						element2 = (org.w3c.dom.Element)keyFieldElementList.item(j);
						if (keyFieldIDList.contains(element2.getAttribute("FieldID"))) {
							wrkCnt++;
						}
					}
					if (wrkCnt == keyFieldIDList.size()) {
						if (keyFieldElementList.getLength() > keyFieldIDList.size()) {
							if (element1.getAttribute("Type").equals("PK")
									|| element1.getAttribute("Type").equals("SK")) {
								relationshipType = "FAMILY";
							}
						}
						if (keyFieldElementList.getLength() == keyFieldIDList.size()) {
							if (element1.getAttribute("Type").equals("FK")) {
								relationshipType = "REFFER";
							}
							if (element1.getAttribute("Type").equals("PK")
									|| element1.getAttribute("Type").equals("SK")) {
								relationshipType = "SUBTYPE";
							}
						}
						if (!relationshipType.equals("")) {
							availableTableKeyNode = frame_.getSpecificXeadTreeNode("TableKey", targetTableNode_.getElement().getAttribute("ID"), element1.getAttribute("ID"));
							break;
						}
					}
				}
			}
			//
			// Add node of Foreign Key if without available key //
			if (availableTableKeyNode == null) {
				lastID = 0;
				int elementID;
				int lastSortKey = 0;
				relationshipType = "REFFER";
				NodeList nodeListToGetLastNumber = targetTableNode_.getElement().getElementsByTagName("TableKey");
				for (int i = 0; i < nodeListToGetLastNumber.getLength(); i++) {
					element = (org.w3c.dom.Element)nodeListToGetLastNumber.item(i);
					if (lastElement == null) {
						lastElement = element;
					} else {
						elementID = Integer.parseInt(element.getAttribute("ID"));
						lastID = Integer.parseInt(lastElement.getAttribute("ID"));
						if (elementID > lastID) {
							lastElement = element;
						}
					}
					if (!element.getAttribute("SortKey").equals("")) {
						if (lastSortKey < Integer.parseInt(lastElement.getAttribute("SortKey"))) {
							lastSortKey = Integer.parseInt(lastElement.getAttribute("SortKey"));
						}
					}
				}
				lastID = Integer.parseInt(lastElement.getAttribute("ID"));
				newElement = frame_.getDomDocument().createElement("TableKey");
				newElement.setAttribute("ID", Integer.toString(lastID + 1));
				newElement.setAttribute("Type", "FK");
				newElement.setAttribute("SortKey", "010");
				lastSortKey = 0;
				for (int k = 0; k < keyFieldIDList.size(); k++) {
					newElementChild = frame_.getDomDocument().createElement("TableKeyField");
					newElementChild.setAttribute("FieldID", keyFieldIDList.get(k));
					newElementChild.setAttribute("SortKey", Modeler.getFormatted4ByteString(lastSortKey + 10));
					newElement.appendChild(newElementChild);
				}
				targetTableNode_.getElement().appendChild(newElement);
				availableTableKeyNode = frame_.new XeadTreeNode("TableKey", newElement);
				((XeadTreeNode)targetTableNode_.getChildAt(1)).add(availableTableKeyNode);
				frame_.getXeadUndoManager().addLogOfAdd(availableTableKeyNode);
			}
			//
			if (availableTableKeyNode != null && !relationshipType.equals("")) {
				//
				// Add a relationship //
				frame_.getXeadUndoManager().saveNodeBeforeModified(availableTableKeyNode);
				newElement = frame_.getDomDocument().createElement("Relationship");
				newElement.setAttribute("ID", Integer.toString(frame_.incrementLastIDOfRelationship()));
				newElement.setAttribute("Table1ID", sourceTableNode_.getElement().getAttribute("ID"));
				newElement.setAttribute("TableKey1ID", sourceKeyElement_.getAttribute("ID"));
				newElement.setAttribute("Table2ID", targetTableNode_.getElement().getAttribute("ID"));
				newElement.setAttribute("TableKey2ID", availableTableKeyNode.getElement().getAttribute("ID"));
				newElement.setAttribute("Type", relationshipType);
				newElement.setAttribute("ExistWhen1", "");
				newElement.setAttribute("ExistWhen2", "");
				frame_.getSystemElement().appendChild(newElement);
				frame_.getXeadUndoManager().addLogAfterModified(availableTableKeyNode);
				//
				// Add Relationship attributes to each Subsystem if necessary//
				NodeList subsystemNodeList = frame_.getDomDocument().getElementsByTagName("Subsystem");
				for (int i = 0; i < subsystemNodeList.getLength(); i++) {
					//sourceTableNode_.createSubsystemAttributesForRelationship(newElement, (org.w3c.dom.Element)subsystemNodeList.item(i));
					frame_.createSubsystemAttributesForRelationship(newElement, (org.w3c.dom.Element)subsystemNodeList.item(i));
				}
				//
				frame_.getXeadUndoManager().saveNodeBeforeModified(frame_.getCurrentMainTreenode());
			}
		} else {
			JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamodel9"));
		}
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		keyFieldNameList.clear();
		keyFieldAliasList.clear();
		//
		buttonOKIsPressed = false;
		this.setVisible(false);
	}
}

class DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter implements ChangeListener {
	DialogAddRelationshipOnDatamodel adaptee;
	DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter(DialogAddRelationshipOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jRadioButton_stateChanged(e);
	}
}

class DialogAddRelationshipOnDatamodel_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
	DialogAddRelationshipOnDatamodel adaptee;
	DialogAddRelationshipOnDatamodel_jButtonOK_actionAdapter(DialogAddRelationshipOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonOK_actionPerformed(e);
	}
}

class DialogAddRelationshipOnDatamodel_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogAddRelationshipOnDatamodel adaptee;
	DialogAddRelationshipOnDatamodel_jButtonCancel_actionAdapter(DialogAddRelationshipOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
