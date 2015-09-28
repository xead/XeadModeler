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
import java.util.*;

import javax.swing.*;

import org.w3c.dom.*;

import xeadModeler.Modeler.*;

public class DialogTableSynchronize extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JButton jButtonClose = new JButton();
	private JButton jButtonImport = new JButton();
	private JScrollPane jScrollPaneMessage = new JScrollPane();
	private JTextArea jTextAreaMessage = new JTextArea();
	private JPanel jPanelButtons = new JPanel();
	private boolean isSynchError_ = false;
	private boolean isImported_ = false;
	private String errorMessage_ = "";
	private org.w3c.dom.Document synchFileDomDocument_ = null;
	private org.w3c.dom.Element synchFileTableElement_ = null;
	private org.w3c.dom.Element targetTableElement_ = null;
	private int countOfErrors = 0;
	private Modeler frame_;

	public DialogTableSynchronize(Modeler frame, String title, boolean modal) {
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

	public DialogTableSynchronize(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		jScrollPaneMessage.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().setLayout(new BorderLayout());

		jTextAreaMessage.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextAreaMessage.setEditable(false);
		jTextAreaMessage.setOpaque(false);
		jTextAreaMessage.setLineWrap(true);
		jTextAreaMessage.setTabSize(4);
		jScrollPaneMessage.getViewport().add(jTextAreaMessage);

		jButtonClose.setText(res.getString("DialogTableSynchronize02"));
		jButtonClose.setBounds(new Rectangle(20, 7, 150, 29));
		jButtonClose.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonClose.addActionListener(new DialogTableSynchronize_jButtonClose_actionAdapter(this));
		jButtonImport.setText(res.getString("DialogTableSynchronize03"));
		jButtonImport.setBounds(new Rectangle(455, 7, 220, 29));
		jButtonImport.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonImport.addActionListener(new DialogTableSynchronize_jButtonImport_actionAdapter(this));
		jPanelButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelButtons.setPreferredSize(new Dimension(100, 43));
		jPanelButtons.setLayout(null);
		jPanelButtons.add(jButtonClose, null);
		jPanelButtons.add(jButtonImport, null);

		this.setTitle(res.getString("DialogTableSynchronize01"));
		this.getContentPane().add(jPanelButtons,  BorderLayout.SOUTH);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(700, 400));
		this.getContentPane().add(jScrollPaneMessage,  BorderLayout.CENTER);
	}

	public boolean request(XeadTreeNode tableNode) {
		isSynchError_ = tableNode.isError();
		isImported_ = false;

		if (tableNode.getType().equals("Table")) {
			try {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));

				isSynchError_ = isSynchError(tableNode.getElement(), null);
				if (isSynchError_) {
					jTextAreaMessage.setText(errorMessage_);
				} else {
					jTextAreaMessage.setText(res.getString("DialogTableSynchronize05"));
				}
				jTextAreaMessage.setCaretPosition(0);
				jPanelButtons.getRootPane().setDefaultButton(jButtonClose);
				Dimension dlgSize = this.getPreferredSize();
				Dimension frmSize = frame_.getSize();
				Point loc = frame_.getLocation();
				this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
			} finally {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			super.setVisible(true);
		}

		return isSynchError_;
	}

	public boolean isSynchError(org.w3c.dom.Element tableElement, org.w3c.dom.Document domDocumentComparedTo) {
		isSynchError_ = false;
		errorMessage_ = "";
		org.w3c.dom.Element workElement = null;

		jButtonImport.setEnabled(false);

		synchFileTableElement_ = null;
		targetTableElement_ = tableElement;
		if (!targetTableElement_.getAttribute("SynchFile").equals("")) {
			if (targetTableElement_.getAttribute("SortKey").equals("")) {
				isSynchError_ = true;
				errorMessage_ = res.getString("DialogTableSynchronize06");
			} else {
				if (domDocumentComparedTo == null) {
					synchFileDomDocument_ = frame_.getSynchFileDocument(targetTableElement_.getAttribute("SynchFile"));
				} else {
					synchFileDomDocument_ = domDocumentComparedTo;
				}
				if (synchFileDomDocument_ == null) {
					isSynchError_ = true;
					errorMessage_ = res.getString("DialogTableSynchronize07");
				} else {
					boolean isFound = false;
					NodeList tableList = synchFileDomDocument_.getElementsByTagName("Table");
					for (int i = 0; i < tableList.getLength(); i++) {
						workElement = (org.w3c.dom.Element)tableList.item(i);
						if (workElement.getAttribute("SortKey").equals(targetTableElement_.getAttribute("SortKey"))) {
							isFound = true;
							synchFileTableElement_ = workElement;
							break;
						}
					}
					if (isFound) {
						if (synchFileTableElement_.getAttribute("SynchFile").equals("")) {
							jButtonImport.setEnabled(true);
							isSynchError_ = compareDefinitions(targetTableElement_, synchFileTableElement_);
							errorMessage_ = errorMessage_ + "\n\n" + res.getString("DialogTableSynchronize04");
						} else {
							isSynchError_ = true;
							errorMessage_ = res.getString("DialogTableSynchronize25");
						}
					} else {
						isSynchError_ = true;
						errorMessage_ = res.getString("DialogTableSynchronize08");
					}
				}
			}
		}

		return isSynchError_;
	}

	boolean compareDefinitions(org.w3c.dom.Element targetElement, org.w3c.dom.Element synchFileElement) {
		org.w3c.dom.Element targetFieldElement, synchFileFieldElement = null;
		org.w3c.dom.Element targetKeyElement = null;
		org.w3c.dom.Element synchFileKeyElement = null;
		countOfErrors = 0;
		StringBuffer bf = new StringBuffer();

		if (!targetElement.getAttribute("Name").equals(synchFileElement.getAttribute("Name"))) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize09"));
		}
		if (!targetElement.getAttribute("Alias").equals(synchFileElement.getAttribute("Alias"))) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize30"));
		}
		if (isDifferentTableType(targetElement, synchFileElement)) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize31"));
		}
		if (!targetElement.getAttribute("Descriptions").equals(synchFileElement.getAttribute("Descriptions"))) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize10"));
		}

		NodeList targetFieldList = targetElement.getElementsByTagName("TableField");
		NodeList synchFileFieldList = synchFileElement.getElementsByTagName("TableField");

		for (int i = 0; i < targetFieldList.getLength(); i++) {
			boolean isFound = false;
			targetFieldElement = (org.w3c.dom.Element)targetFieldList.item(i);
			for (int j = 0; j < synchFileFieldList.getLength(); j++) {
				synchFileFieldElement = (org.w3c.dom.Element)synchFileFieldList.item(j);
				if (targetFieldElement.getAttribute("Alias").equals(synchFileFieldElement.getAttribute("Alias"))) {
					isFound = true;
					break;
				}
			}
			if (isFound) {
				if (!targetFieldElement.getAttribute("Name").equals(synchFileFieldElement.getAttribute("Name"))) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize12"));
				}
				if (isDifferentDataType(targetFieldElement, synchFileFieldElement)) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize13"));
				}
				if (!targetFieldElement.getAttribute("Descriptions").equals(synchFileFieldElement.getAttribute("Descriptions"))) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize14"));
				}
				if (!targetFieldElement.getAttribute("AttributeType").equals(synchFileFieldElement.getAttribute("AttributeType"))) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize26"));
				}
				if (!targetFieldElement.getAttribute("NotNull").equals(synchFileFieldElement.getAttribute("NotNull"))) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize27"));
				}
				if (!targetFieldElement.getAttribute("Default").equals(synchFileFieldElement.getAttribute("Default"))) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize28"));
				}
				if (!targetFieldElement.getAttribute("NoUpdate").equals("") && !synchFileFieldElement.getAttribute("NoUpdate").equals("")
						&& !targetFieldElement.getAttribute("NoUpdate").equals(synchFileFieldElement.getAttribute("NoUpdate"))) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize29"));
				}
			} else {
				countOfErrors++;
				bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize15"));
			}
		}

		for (int i = 0; i < synchFileFieldList.getLength(); i++) {
			boolean isFound = false;
			synchFileFieldElement = (org.w3c.dom.Element)synchFileFieldList.item(i);
			for (int j = 0; j < targetFieldList.getLength(); j++) {
				targetFieldElement = (org.w3c.dom.Element)targetFieldList.item(j);
				if (targetFieldElement.getAttribute("Alias").equals(synchFileFieldElement.getAttribute("Alias"))) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				countOfErrors++;
				bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize16") + synchFileFieldElement.getAttribute("Alias") + res.getString("DialogTableSynchronize17"));
			}
		}

		NodeList targetKeyList = targetElement.getElementsByTagName("TableKey");
		NodeList synchFileKeyList = synchFileElement.getElementsByTagName("TableKey");

		for (int i = 0; i < targetKeyList.getLength(); i++) {
			targetKeyElement = (org.w3c.dom.Element)targetKeyList.item(i);
			if (targetKeyElement.getAttribute("Type").equals("PK")) {
				for (int j = 0; j < synchFileKeyList.getLength(); j++) {
					synchFileKeyElement = (org.w3c.dom.Element)synchFileKeyList.item(j);
					if (synchFileKeyElement.getAttribute("Type").equals(targetKeyElement.getAttribute("Type"))) {
						if (!isEquivalentKey(targetKeyElement, synchFileKeyElement, targetFieldList, synchFileFieldList)) {
							countOfErrors++;
							bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize18"));
						}
						break;
					}
				}
			} else {
				boolean isFound = false;
				for (int j = 0; j < synchFileKeyList.getLength(); j++) {
					synchFileKeyElement = (org.w3c.dom.Element)synchFileKeyList.item(j);
					if (synchFileKeyElement.getAttribute("Type").equals(targetKeyElement.getAttribute("Type"))) {
						if (isEquivalentKey(targetKeyElement, synchFileKeyElement, targetFieldList, synchFileFieldList)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize20"));
				}
			}
		}

		for (int i = 0; i < synchFileKeyList.getLength(); i++) {
			boolean isFound = false;
			synchFileKeyElement = (org.w3c.dom.Element)synchFileKeyList.item(i);
			if (!synchFileKeyElement.getAttribute("Type").equals("PK")) {
				for (int j = 0; j < targetKeyList.getLength(); j++) {
					targetKeyElement = (org.w3c.dom.Element)targetKeyList.item(j);
					if (targetKeyElement.getAttribute("Type").equals(synchFileKeyElement.getAttribute("Type"))) {
						if (isEquivalentKey(targetKeyElement, synchFileKeyElement, targetFieldList, synchFileFieldList)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize22"));
				}
			}
		}
		
		if (countOfErrors == 0) {
			return false;
		} else {
			errorMessage_ = res.getString("DialogTableSynchronize23") + countOfErrors + res.getString("DialogTableSynchronize24") + bf.toString();
			return true;
		}
	}

	boolean isDifferentTableType(org.w3c.dom.Element targetTableElement, org.w3c.dom.Element synchFileTableElement) {
		boolean isDifferent = false;
		org.w3c.dom.Element element;

		org.w3c.dom.Element targetTableTypeElement = null;
		NodeList targetTableTypeList = targetTableElement.getOwnerDocument().getElementsByTagName("TableType");
		for (int i = 0; i < targetTableTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)targetTableTypeList.item(i);
			if (element.getAttribute("ID").equals(targetTableElement.getAttribute("TableTypeID"))) {
				targetTableTypeElement = element;
			}
		}

		org.w3c.dom.Element synchFileTableTypeElement = null;
		NodeList synchFileTableTypeList = synchFileDomDocument_.getElementsByTagName("TableType");
		for (int i = 0; i < synchFileTableTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)synchFileTableTypeList.item(i);
			if (element.getAttribute("ID").equals(synchFileTableElement.getAttribute("TableTypeID"))) {
				synchFileTableTypeElement = element;
			}
		}
		
		if (targetTableTypeElement != null && synchFileTableTypeElement != null) {
			if (!targetTableTypeElement.getAttribute("SortKey").equals(synchFileTableTypeElement.getAttribute("SortKey"))
					|| !targetTableTypeElement.getAttribute("Name").equals(synchFileTableTypeElement.getAttribute("Name"))) {
				isDifferent = true;
			}
		} else {
			isDifferent = true;
		}
		
		return isDifferent;
	}

	boolean isDifferentDataType(org.w3c.dom.Element targetFieldElement, org.w3c.dom.Element synchFileFieldElement) {
		boolean isDifferent = false;
		org.w3c.dom.Element element;

		org.w3c.dom.Element targetDataTypeElement = null;
		NodeList targetDataTypeList = targetFieldElement.getOwnerDocument().getElementsByTagName("DataType");
		for (int i = 0; i < targetDataTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)targetDataTypeList.item(i);
			if (element.getAttribute("ID").equals(targetFieldElement.getAttribute("DataTypeID"))) {
				targetDataTypeElement = element;
			}
		}

		org.w3c.dom.Element synchFileDataTypeElement = null;
		NodeList synchFileDataTypeList = synchFileDomDocument_.getElementsByTagName("DataType");
		for (int i = 0; i < synchFileDataTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)synchFileDataTypeList.item(i);
			if (element.getAttribute("ID").equals(synchFileFieldElement.getAttribute("DataTypeID"))) {
				synchFileDataTypeElement = element;
			}
		}
		
		if (targetDataTypeElement != null && synchFileDataTypeElement != null) {
			if (!targetDataTypeElement.getAttribute("BasicType").equals(synchFileDataTypeElement.getAttribute("BasicType"))
					|| !targetDataTypeElement.getAttribute("Name").equals(synchFileDataTypeElement.getAttribute("Name"))
					|| !targetDataTypeElement.getAttribute("SQLExpression").equals(synchFileDataTypeElement.getAttribute("SQLExpression"))
					|| !targetDataTypeElement.getAttribute("Length").equals(synchFileDataTypeElement.getAttribute("Length"))
					|| !targetDataTypeElement.getAttribute("Decimal").equals(synchFileDataTypeElement.getAttribute("Decimal"))) {
				isDifferent = true;
			}
		} else {
			isDifferent = true;
		}
		
		return isDifferent;
	}

	boolean isEquivalentKey(org.w3c.dom.Element targetKeyElement, org.w3c.dom.Element synchFileKeyElement, NodeList targetFieldList, NodeList synchFileFieldList) {
		boolean isEquivalent = true;
		org.w3c.dom.Element targetKeyFieldElement = null;
		org.w3c.dom.Element synchFileKeyFieldElement = null;
		String targetKeyFieldAlias, synchFileKeyFieldAlias;
		boolean isFound;

		NodeList targetKeyFieldList = targetKeyElement.getElementsByTagName("TableKeyField");
		NodeList synchFileKeyFieldList = synchFileKeyElement.getElementsByTagName("TableKeyField");

		if (targetKeyFieldList.getLength() == synchFileKeyFieldList.getLength()) {
			for (int i = 0; i < targetKeyFieldList.getLength(); i++) {
				isFound = false;

				targetKeyFieldAlias = "";
				targetKeyFieldElement = (org.w3c.dom.Element)targetKeyFieldList.item(i);
				for (int j = 0; j < targetFieldList.getLength(); j++) {
					org.w3c.dom.Element element = (org.w3c.dom.Element)targetFieldList.item(j);
					if (targetKeyFieldElement.getAttribute("FieldID").equals(element.getAttribute("ID"))) {
						targetKeyFieldAlias = element.getAttribute("Alias");
						break;
					}
				}

				synchFileKeyFieldAlias = "";
				for (int j = 0; j < synchFileKeyFieldList.getLength(); j++) {
					synchFileKeyFieldElement = (org.w3c.dom.Element)synchFileKeyFieldList.item(j);
					for (int k = 0; k < synchFileFieldList.getLength(); k++) {
						org.w3c.dom.Element element = (org.w3c.dom.Element)synchFileFieldList.item(k);
						if (synchFileKeyFieldElement.getAttribute("FieldID").equals(element.getAttribute("ID"))) {
							synchFileKeyFieldAlias = element.getAttribute("Alias");
							break;
						}
					}
					if (targetKeyFieldAlias.equals(synchFileKeyFieldAlias)) {
						if (targetKeyFieldElement.getAttribute("AscDesc").equals(synchFileKeyFieldElement.getAttribute("AscDesc"))) {
							isFound = true;
						}
						break;
					}
				}

				if (!isFound) {
					isEquivalent = false;
				}
			}
		} else {
			isEquivalent = false;
		}
		
		return isEquivalent;
	}

	void jButtonClose_actionPerformed(ActionEvent e) {
		super.setVisible(false);
	}

	void jButtonImport_actionPerformed(ActionEvent e) {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));

			isImported_ = true;
			importIntoTarget(targetTableElement_, synchFileTableElement_);

			isSynchError_ = compareDefinitions(targetTableElement_, synchFileTableElement_);
			if (isSynchError_) {
				jTextAreaMessage.setText(errorMessage_ + "\n\n" + res.getString("DialogTableSynchronize04"));
			} else {
				jTextAreaMessage.setText(res.getString("DialogTableSynchronize05"));
			}
			jTextAreaMessage.setCaretPosition(0);
			jButtonImport.setEnabled(false);

		} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	void importIntoTarget(org.w3c.dom.Element targetTableElement, org.w3c.dom.Element synchFileTableElement) {
		targetTableElement.setAttribute("Name", synchFileTableElement.getAttribute("Name"));
		targetTableElement.setAttribute("Alias", synchFileTableElement.getAttribute("Alias"));
		targetTableElement.setAttribute("Descriptions", synchFileTableElement.getAttribute("Descriptions"));
		if (isDifferentTableType(targetTableElement, synchFileTableElement)) {
			String tableTypeID = getEquivalentTableTypeIDFromTargetDocument(targetTableElement, synchFileTableElement.getAttribute("TableTypeID"));
			targetTableElement.setAttribute("TableTypeID", tableTypeID);
		}

		String dataTypeID;
		org.w3c.dom.Element targetFieldElement = null;
		org.w3c.dom.Element synchFileFieldElement = null;
		NodeList targetFieldList = targetTableElement.getElementsByTagName("TableField");
		NodeList synchFileFieldList = synchFileTableElement.getElementsByTagName("TableField");
		for (int i = 0; i < synchFileFieldList.getLength(); i++) {
			boolean isFound = false;
			synchFileFieldElement = (org.w3c.dom.Element)synchFileFieldList.item(i);
			for (int j = 0; j < targetFieldList.getLength(); j++) {
				targetFieldElement = (org.w3c.dom.Element)targetFieldList.item(j);
				if (targetFieldElement.getAttribute("Alias").equals(synchFileFieldElement.getAttribute("Alias"))) {
					isFound = true;
					break;
				}
			}
			if (isFound) {
				targetFieldElement.setAttribute("Name", synchFileFieldElement.getAttribute("Name"));
				if (isDifferentDataType(targetFieldElement, synchFileFieldElement)) {
					dataTypeID = getEquivalentDataTypeIDFromTargetDocument(synchFileFieldElement.getAttribute("DataTypeID"));
					targetFieldElement.setAttribute("DataTypeID", dataTypeID);
				}
				targetFieldElement.setAttribute("Descriptions", synchFileFieldElement.getAttribute("Descriptions"));
				targetFieldElement.setAttribute("AttributeType", synchFileFieldElement.getAttribute("AttributeType"));
				targetFieldElement.setAttribute("NotNull", synchFileFieldElement.getAttribute("NotNull"));
				targetFieldElement.setAttribute("Default", synchFileFieldElement.getAttribute("Default"));
				targetFieldElement.setAttribute("NoUpdate", synchFileFieldElement.getAttribute("NoUpdate"));
				targetFieldElement.setAttribute("SortKey", synchFileFieldElement.getAttribute("SortKey"));
			} else {
				targetFieldElement = targetTableElement.getOwnerDocument().createElement("TableField");
				targetFieldElement.setAttribute("ID", getNextIDOfNodeType("TableField"));
				targetFieldElement.setAttribute("Name", synchFileFieldElement.getAttribute("Name"));
				targetFieldElement.setAttribute("Alias", synchFileFieldElement.getAttribute("Alias"));
				dataTypeID = getEquivalentDataTypeIDFromTargetDocument(synchFileFieldElement.getAttribute("DataTypeID"));
				targetFieldElement.setAttribute("DataTypeID", dataTypeID);
				targetFieldElement.setAttribute("Descriptions", synchFileFieldElement.getAttribute("Descriptions"));
				targetFieldElement.setAttribute("AttributeType", synchFileFieldElement.getAttribute("AttributeType"));
				targetFieldElement.setAttribute("NotNull", synchFileFieldElement.getAttribute("NotNull"));
				targetFieldElement.setAttribute("Default", synchFileFieldElement.getAttribute("Default"));
				targetFieldElement.setAttribute("NoUpdate", synchFileFieldElement.getAttribute("NoUpdate"));
				targetFieldElement.setAttribute("ShowOnModel", synchFileFieldElement.getAttribute("ShowOnModel"));
				targetFieldElement.setAttribute("SortKey", synchFileFieldElement.getAttribute("SortKey"));
				targetTableElement.appendChild(targetFieldElement);
			}
		}

		org.w3c.dom.Element element = null;
		org.w3c.dom.Element targetKeyElement = null;
		org.w3c.dom.Element targetKeyFieldElement = null;
		org.w3c.dom.Element synchFileKeyElement = null;
		org.w3c.dom.Element synchFileKeyFieldElement = null;
		NodeList synchFileKeyFieldList;
		NodeList targetKeyFieldList;

		int biggestKeyID = 0;
		int wrkInt;
		NodeList targetKeyList = targetTableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < targetKeyList.getLength(); i++) {
			targetKeyElement = (org.w3c.dom.Element)targetKeyList.item(i);
			wrkInt = Integer.parseInt(targetKeyElement.getAttribute("ID"));
			if (wrkInt > biggestKeyID) {
				biggestKeyID = wrkInt;
			}
		}

		NodeList targetRelationshipList = targetTableElement.getElementsByTagName("Relationship");
		NodeList synchFileKeyList = synchFileTableElement.getElementsByTagName("TableKey");
		for (int i = 0; i < synchFileKeyList.getLength(); i++) {
			synchFileKeyElement = (org.w3c.dom.Element)synchFileKeyList.item(i);

			if (synchFileKeyElement.getAttribute("Type").equals("PK")) {
				for (int j = 0; j < targetKeyList.getLength(); j++) {
					targetKeyElement = (org.w3c.dom.Element)targetKeyList.item(j);
					if (targetKeyElement.getAttribute("Type").equals(synchFileKeyElement.getAttribute("Type"))) {
						if (!isEquivalentKey(targetKeyElement, synchFileKeyElement, targetFieldList, synchFileFieldList)) {
							boolean hasRelationships = false;
							for (int k = 0; k < targetRelationshipList.getLength(); k++) {
								element = (org.w3c.dom.Element)targetRelationshipList.item(k);
								if ((element.getAttribute("Table1ID").equals(targetTableElement.getAttribute("ID")) && element.getAttribute("TableKey1ID").equals(targetKeyElement.getAttribute("ID")))
									|| (element.getAttribute("Table2ID").equals(targetTableElement.getAttribute("ID")) && element.getAttribute("TableKey2ID").equals(targetKeyElement.getAttribute("ID")))) {
									hasRelationships = true;
									break;
								}
							}
							if (hasRelationships) {
								JOptionPane.showMessageDialog(this, res.getString("DialogTableSynchronize19"));
							} else {
								targetKeyFieldList = targetKeyElement.getElementsByTagName("TableKeyField");
								int workCount = targetKeyFieldList.getLength();
								for (int k = 0; k < workCount; k++) {
									targetKeyElement.removeChild(targetKeyElement.getFirstChild());
								}
								synchFileKeyFieldList = synchFileKeyElement.getElementsByTagName("TableKeyField");
								for (int k = 0; k < synchFileKeyFieldList.getLength(); k++) {
									synchFileKeyFieldElement = (org.w3c.dom.Element)synchFileKeyFieldList.item(k);
									targetKeyFieldElement = targetTableElement.getOwnerDocument().createElement("TableKeyField");
									targetKeyFieldElement.setAttribute("FieldID",
											getEquivalentFieldIDFromTargetTable(synchFileKeyFieldElement.getAttribute("FieldID"), synchFileTableElement, targetTableElement));
									targetKeyFieldElement.setAttribute("AscDesc", synchFileKeyFieldElement.getAttribute("AscDesc"));
									targetKeyFieldElement.setAttribute("SortKey", synchFileKeyFieldElement.getAttribute("SortKey"));
									targetKeyElement.appendChild(targetKeyFieldElement);
								}
							}
						}
						break;
					}
				}
			} else {
				boolean isFound = false;
				for (int j = 0; j < targetKeyList.getLength(); j++) {
					targetKeyElement = (org.w3c.dom.Element)targetKeyList.item(j);
					if (targetKeyElement.getAttribute("Type").equals(synchFileKeyElement.getAttribute("Type"))) {
						if (isEquivalentKey(targetKeyElement, synchFileKeyElement, targetFieldList, synchFileFieldList)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					biggestKeyID++;
					targetKeyElement = targetTableElement.getOwnerDocument().createElement("TableKey");
					targetKeyElement.setAttribute("ID", Integer.toString(biggestKeyID));
					targetKeyElement.setAttribute("Type", synchFileKeyElement.getAttribute("Type"));
					targetKeyElement.setAttribute("SortKey", synchFileKeyElement.getAttribute("SortKey"));
					targetTableElement.appendChild(targetKeyElement);
					synchFileKeyFieldList = synchFileKeyElement.getElementsByTagName("TableKeyField");
					for (int j = 0; j < synchFileKeyFieldList.getLength(); j++) {
						synchFileKeyFieldElement = (org.w3c.dom.Element)synchFileKeyFieldList.item(j);
						targetKeyFieldElement = targetTableElement.getOwnerDocument().createElement("TableKeyField");
						targetKeyFieldElement.setAttribute("FieldID", 
								getEquivalentFieldIDFromTargetTable(synchFileKeyFieldElement.getAttribute("FieldID"), synchFileTableElement, targetTableElement));
						targetKeyFieldElement.setAttribute("AscDesc", synchFileKeyFieldElement.getAttribute("AscDesc"));
						targetKeyFieldElement.setAttribute("SortKey", synchFileKeyFieldElement.getAttribute("SortKey"));
						targetKeyElement.appendChild(targetKeyFieldElement);
					}
				}
			}
		}
	}

	private String getEquivalentTableTypeIDFromTargetDocument(org.w3c.dom.Element targetTableElement, String synchFileTableTypeID) {
		org.w3c.dom.Element element;

		org.w3c.dom.Element synchFileTableTypeElement = null;
		NodeList synchFileTableTypeList = synchFileDomDocument_.getElementsByTagName("TableType");
		for (int i = 0; i < synchFileTableTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)synchFileTableTypeList.item(i);
			if (element.getAttribute("ID").equals(synchFileTableTypeID)) {
				synchFileTableTypeElement = element;
				break;
			}
		}

		String targetTableTypeID = "";
		org.w3c.dom.Element targetTableTypeElement = null;
		NodeList targetTableTypeList = targetTableElement.getOwnerDocument().getElementsByTagName("TableType");
		for (int i = 0; i < targetTableTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)targetTableTypeList.item(i);
			if (element.getAttribute("SortKey").equals(synchFileTableTypeElement.getAttribute("SortKey"))
					&& element.getAttribute("Name").equals(synchFileTableTypeElement.getAttribute("Name"))) {
				targetTableTypeID = element.getAttribute("ID");
				break;
			}
		}
		if (targetTableTypeID.equals("")) {
			NodeList workElementList = targetTableElement.getOwnerDocument().getElementsByTagName("System");
			org.w3c.dom.Element systemElement = (org.w3c.dom.Element)workElementList.item(0);
			targetTableTypeElement = frame_.domDocument.createElement("TableType");
			targetTableTypeID = getNextIDOfNodeType("TableType");
			targetTableTypeElement.setAttribute("ID", targetTableTypeID);
			targetTableTypeElement.setAttribute("Name", synchFileTableTypeElement.getAttribute("Name"));
			targetTableTypeElement.setAttribute("SortKey", synchFileTableTypeElement.getAttribute("SortKey"));
			targetTableTypeElement.setAttribute("Descriptions", synchFileTableTypeElement.getAttribute("Descriptions"));
			systemElement.appendChild(targetTableTypeElement);
		}

		return targetTableTypeID;
	}

	private String getEquivalentDataTypeIDFromTargetDocument(String synchFileDataTypeID) {
		org.w3c.dom.Element element;

		org.w3c.dom.Element synchFileDataTypeElement = null;
		NodeList synchFileDataTypeList = synchFileDomDocument_.getElementsByTagName("DataType");
		for (int i = 0; i < synchFileDataTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)synchFileDataTypeList.item(i);
			if (element.getAttribute("ID").equals(synchFileDataTypeID)) {
				synchFileDataTypeElement = element;
				break;
			}
		}

		String targetDataTypeID = "";
		org.w3c.dom.Element targetDataTypeElement = null;
		NodeList targetDataTypeList = frame_.domDocument.getElementsByTagName("DataType");
		for (int i = 0; i < targetDataTypeList.getLength(); i++) {
			element = (org.w3c.dom.Element)targetDataTypeList.item(i);
			if (element.getAttribute("BasicType").equals(synchFileDataTypeElement.getAttribute("BasicType"))
					&& element.getAttribute("Name").equals(synchFileDataTypeElement.getAttribute("Name"))
					&& element.getAttribute("SQLExpression").equals(synchFileDataTypeElement.getAttribute("SQLExpression"))
					&& element.getAttribute("Length").equals(synchFileDataTypeElement.getAttribute("Length"))
					&& element.getAttribute("Decimal").equals(synchFileDataTypeElement.getAttribute("Decimal"))) {
				targetDataTypeID = element.getAttribute("ID");
				break;
			}
		}
		if (targetDataTypeID.equals("")) {
			NodeList workElementList = frame_.domDocument.getElementsByTagName("System");
			org.w3c.dom.Element systemElement = (org.w3c.dom.Element)workElementList.item(0);
			targetDataTypeElement = frame_.domDocument.createElement("DataType");
			targetDataTypeID = getNextIDOfNodeType("DataType");
			targetDataTypeElement.setAttribute("ID", targetDataTypeID);
			targetDataTypeElement.setAttribute("Name", synchFileDataTypeElement.getAttribute("Name"));
			targetDataTypeElement.setAttribute("SortKey", synchFileDataTypeElement.getAttribute("SortKey"));
			targetDataTypeElement.setAttribute("BasicType", synchFileDataTypeElement.getAttribute("BasicType"));
			targetDataTypeElement.setAttribute("SQLExpression", synchFileDataTypeElement.getAttribute("SQLExpression"));
			targetDataTypeElement.setAttribute("Length", synchFileDataTypeElement.getAttribute("Length"));
			targetDataTypeElement.setAttribute("Decimal", synchFileDataTypeElement.getAttribute("Decimal"));
			systemElement.appendChild(targetDataTypeElement);
		}

		return targetDataTypeID;
	}

	private String getEquivalentFieldIDFromTargetTable(String synchFileFieldID, org.w3c.dom.Element synchFileTableElement, org.w3c.dom.Element targetTableElement) {
		org.w3c.dom.Element element;

		org.w3c.dom.Element synchFileFieldElement = null;
		NodeList synchFileFieldList = synchFileTableElement.getElementsByTagName("TableField");
		for (int i = 0; i < synchFileFieldList.getLength(); i++) {
			element = (org.w3c.dom.Element)synchFileFieldList.item(i);
			if (element.getAttribute("ID").equals(synchFileFieldID)) {
				synchFileFieldElement = element;
				break;
			}
		}

		String targetFieldID = "";
		NodeList targetFieldList = targetTableElement.getElementsByTagName("TableField");
		for (int i = 0; i < targetFieldList.getLength(); i++) {
			element = (org.w3c.dom.Element)targetFieldList.item(i);
			if (element.getAttribute("Alias").equals(synchFileFieldElement.getAttribute("Alias"))) {
				targetFieldID = element.getAttribute("ID");
				break;
			}
		}
		return targetFieldID;
	}

	private String getNextIDOfNodeType(String tagName) {
		String nextID = "1";
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
		if (lastDomElement != null) {
			int lastID = Integer.parseInt(lastDomElement.getAttribute("ID"));
			nextID = Integer.toString(lastID + 1);
		}
		return nextID;
	}

	public boolean isImported() {
		return isImported_;
	}
}

class DialogTableSynchronize_jButtonClose_actionAdapter implements java.awt.event.ActionListener {
	DialogTableSynchronize adaptee;

	DialogTableSynchronize_jButtonClose_actionAdapter(DialogTableSynchronize adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonClose_actionPerformed(e);
	}
}

class DialogTableSynchronize_jButtonImport_actionAdapter implements java.awt.event.ActionListener {
	DialogTableSynchronize adaptee;

	DialogTableSynchronize_jButtonImport_actionAdapter(DialogTableSynchronize adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonImport_actionPerformed(e);
	}
}