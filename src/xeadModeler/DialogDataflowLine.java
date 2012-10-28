package xeadModeler;

/*
 * Copyright (c) 2012 WATANABE kozo <qyf05466@nifty.com>,
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
import java.awt.event.*;
import xeadModeler.Modeler.*;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class DialogDataflowLine extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JTextField jTextFieldNode1Name = new JTextField();
	private ButtonGroup buttonGroupArrowStyle = new ButtonGroup();
	private JLabel jLabel2 = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private JRadioButton jRadioButtonArrowStyle3 = new JRadioButton();
	private JRadioButton jRadioButtonArrowStyle1 = new JRadioButton();
	private JRadioButton jRadioButtonArrowStyle2 = new JRadioButton();
	private JPanel jPanel2 = new JPanel();
	private JRadioButton jRadioButtonIcon7 = new JRadioButton();
	private GridLayout gridLayout1 = new GridLayout();
	private ButtonGroup buttonGroupIcon = new ButtonGroup();
	private JRadioButton jRadioButtonIcon9 = new JRadioButton();
	private JRadioButton jRadioButtonIcon10 = new JRadioButton();
	private JRadioButton jRadioButtonIcon11 = new JRadioButton();
	private JRadioButton jRadioButtonIcon14 = new JRadioButton();
	private JRadioButton jRadioButtonIcon12 = new JRadioButton();
	private JRadioButton jRadioButtonIcon13 = new JRadioButton();
	private JRadioButton jRadioButtonIcon8 = new JRadioButton();
	private JRadioButton jRadioButtonIcon15 = new JRadioButton();
	private JRadioButton jRadioButtonIcon16 = new JRadioButton();
	private JRadioButton jRadioButtonIcon17 = new JRadioButton();
	private JRadioButton jRadioButtonIcon18 = new JRadioButton();
	private JRadioButton jRadioButtonIcon19 = new JRadioButton();
	private JRadioButton jRadioButtonIcon20 = new JRadioButton();
	private JRadioButton jRadioButtonIcon6 = new JRadioButton();
	private JRadioButton jRadioButtonIcon5 = new JRadioButton();
	private JRadioButton jRadioButtonIcon4 = new JRadioButton();
	private JRadioButton jRadioButtonIcon3 = new JRadioButton();
	private JRadioButton jRadioButtonIcon2 = new JRadioButton();
	private JRadioButton jRadioButtonIcon1 = new JRadioButton();
	private JButton jButtonOK = new JButton();
	private JButton jButtonCancel = new JButton();
	private GridLayout gridLayout2 = new GridLayout();
	private JLabel jLabel3 = new JLabel();
	private KanjiTextField jTextFieldName = new KanjiTextField();
	private KanjiTextField jTextFieldNameExt = new KanjiTextField();
	private JLabel jLabel4 = new JLabel();
	private JLabel jLabel5 = new JLabel();
	private JLabel jLabel7 = new JLabel();
	private JComboBox jComboBoxNode2 = new JComboBox();
	private boolean buttonOKIsPressed = false;
	private Modeler frame_;
	private JLabel jLabel6 = new JLabel();
	private DataflowNode[] storageArray = new DataflowNode[100];
	private ImageIcon iconFlow10, iconFlow11, iconFlow20, iconFlow21, iconFlow30, iconFlow31;

	public DialogDataflowLine(Modeler frame, String title, boolean modal) {
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

	public DialogDataflowLine(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(333, 440));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		this.setResizable(false);
		this.setTitle("");
		iconFlow10 = new ImageIcon(xeadModeler.DialogDataflowLine.class.getResource("flow10.png"));
		iconFlow11 = new ImageIcon(xeadModeler.DialogDataflowLine.class.getResource("flow11.png"));
		iconFlow20 = new ImageIcon(xeadModeler.DialogDataflowLine.class.getResource("flow20.png"));
		iconFlow21 = new ImageIcon(xeadModeler.DialogDataflowLine.class.getResource("flow21.png"));
		iconFlow30 = new ImageIcon(xeadModeler.DialogDataflowLine.class.getResource("flow30.png"));
		iconFlow31 = new ImageIcon(xeadModeler.DialogDataflowLine.class.getResource("flow31.png"));
		jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogDataflowLine01"));
		jLabel1.setBounds(new Rectangle(20, 92, 77, 15));
		jTextFieldNode1Name.setBackground(SystemColor.control);
		jTextFieldNode1Name.setEnabled(true);
		jTextFieldNode1Name.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldNode1Name.setDoubleBuffered(false);
		jTextFieldNode1Name.setEditable(false);
		jTextFieldNode1Name.setBounds(new Rectangle(103, 93, 149, 22));
		jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogDataflowLine02"));
		jLabel2.setBounds(new Rectangle(17, 140, 82, 15));
		jPanel1.setBounds(new Rectangle(99, 118, 177, 66));
		jPanel1.setLayout(gridLayout2);
		jRadioButtonArrowStyle1.setFont(new java.awt.Font("Monospaced", 0, 12));
		jRadioButtonArrowStyle1.setIcon(iconFlow10);
		jRadioButtonArrowStyle1.setSelectedIcon(iconFlow11);
		jRadioButtonArrowStyle2.setFont(new java.awt.Font("Monospaced", 0, 12));
		jRadioButtonArrowStyle2.setIcon(iconFlow20);
		jRadioButtonArrowStyle2.setSelectedIcon(iconFlow21);
		jRadioButtonArrowStyle3.setFont(new java.awt.Font("Monospaced", 0, 12));
		jRadioButtonArrowStyle3.setIcon(iconFlow30);
		jRadioButtonArrowStyle3.setSelectedIcon(iconFlow31);
		buttonGroupArrowStyle.add(jRadioButtonArrowStyle1);
		buttonGroupArrowStyle.add(jRadioButtonArrowStyle2);
		buttonGroupArrowStyle.add(jRadioButtonArrowStyle3);
		jPanel2.setBorder(BorderFactory.createEtchedBorder());
		jPanel2.setBounds(new Rectangle(6, 239, 321, 154));
		jPanel2.setLayout(gridLayout1);
		gridLayout1.setColumns(3);
		gridLayout1.setRows(7);
		jRadioButtonIcon1.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon1.setText(res.getString("DialogDataflowLine06"));
		jRadioButtonIcon2.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon2.setText(res.getString("DialogDataflowLine07"));
		jRadioButtonIcon3.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon3.setText(res.getString("DialogDataflowLine08"));
		jRadioButtonIcon4.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon4.setText(res.getString("DialogDataflowLine09"));
		jRadioButtonIcon5.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon5.setText(res.getString("DialogDataflowLine10"));
		jRadioButtonIcon6.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon6.setText(res.getString("DialogDataflowLine11"));
		jRadioButtonIcon7.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon7.setText(res.getString("DialogDataflowLine12"));
		jRadioButtonIcon8.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon8.setText(res.getString("DialogDataflowLine13"));
		jRadioButtonIcon9.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon9.setText(res.getString("DialogDataflowLine14"));
		jRadioButtonIcon10.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon10.setText(res.getString("DialogDataflowLine15"));
		jRadioButtonIcon11.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon11.setText(res.getString("DialogDataflowLine16"));
		jRadioButtonIcon12.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon12.setText(res.getString("DialogDataflowLine17"));
		jRadioButtonIcon13.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon13.setText(res.getString("DialogDataflowLine18"));
		jRadioButtonIcon14.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon14.setText(res.getString("DialogDataflowLine19"));
		jRadioButtonIcon15.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon15.setText(res.getString("DialogDataflowLine20"));
		jRadioButtonIcon16.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon16.setText(res.getString("DialogDataflowLine21"));
		jRadioButtonIcon17.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon17.setText(res.getString("DialogDataflowLine22"));
		jRadioButtonIcon18.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon18.setText(res.getString("DialogDataflowLine23"));
		jRadioButtonIcon19.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonIcon19.setText(res.getString("DialogDataflowLine24"));
		jRadioButtonIcon20.setFont(new java.awt.Font("Dialog", 0, 11));
		jRadioButtonIcon20.setText(res.getString("DialogDataflowLine25"));
		buttonGroupIcon.add(jRadioButtonIcon1);
		buttonGroupIcon.add(jRadioButtonIcon2);
		buttonGroupIcon.add(jRadioButtonIcon3);
		buttonGroupIcon.add(jRadioButtonIcon4);
		buttonGroupIcon.add(jRadioButtonIcon5);
		buttonGroupIcon.add(jRadioButtonIcon6);
		buttonGroupIcon.add(jRadioButtonIcon7);
		buttonGroupIcon.add(jRadioButtonIcon8);
		buttonGroupIcon.add(jRadioButtonIcon9);
		buttonGroupIcon.add(jRadioButtonIcon10);
		buttonGroupIcon.add(jRadioButtonIcon11);
		buttonGroupIcon.add(jRadioButtonIcon12);
		buttonGroupIcon.add(jRadioButtonIcon13);
		buttonGroupIcon.add(jRadioButtonIcon14);
		buttonGroupIcon.add(jRadioButtonIcon15);
		buttonGroupIcon.add(jRadioButtonIcon16);
		buttonGroupIcon.add(jRadioButtonIcon17);
		buttonGroupIcon.add(jRadioButtonIcon18);
		buttonGroupIcon.add(jRadioButtonIcon19);
		buttonGroupIcon.add(jRadioButtonIcon20);
		jButtonOK.setBounds(new Rectangle(74, 404, 73, 25));
		jButtonOK.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogDataflowLine_jButtonOK_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(194, 404, 73, 25));
		jButtonCancel.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonCancel.setText(res.getString("DialogDataflowLine26"));
		jButtonCancel.addActionListener(new DialogDataflowLine_jButtonCancel_actionAdapter(this));
		gridLayout2.setColumns(3);
		gridLayout2.setHgap(0);
		gridLayout2.setRows(1);
		jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText(res.getString("DialogDataflowLine27"));
		jLabel3.setBounds(new Rectangle(13, 12, 84, 15));
		jTextFieldName.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName.setText("jTextField1");
		jTextFieldName.setBounds(new Rectangle(103, 11, 130, 22));
		jTextFieldNameExt.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldNameExt.setText("jTextField2");
		jTextFieldNameExt.setBounds(new Rectangle(103, 36, 130, 22));
		jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel4.setText(res.getString("DialogDataflowLine28"));
		jLabel4.setBounds(new Rectangle(11, 222, 121, 15));
		jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText(res.getString("DialogDataflowLine29"));
		jLabel5.setBounds(new Rectangle(37, 190, 60, 15));
		jComboBoxNode2.setBounds(new Rectangle(103, 190, 149, 21));
		jComboBoxNode2.setFont(new java.awt.Font("Dialog", 0, 12));
		jComboBoxNode2.setMaximumRowCount(20);
		jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText(res.getString("DialogDataflowLine30"));
		jLabel6.setBounds(new Rectangle(12, 65, 85, 15));
		jLabel7.setText(res.getString("DialogDataflowLine31"));
		jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel7.setBounds(new Rectangle(13, 37, 84, 15));
		jPanel2.add(jRadioButtonIcon1, null);
		jPanel2.add(jRadioButtonIcon2, null);
		jPanel2.add(jRadioButtonIcon3, null);
		jPanel2.add(jRadioButtonIcon4, null);
		jPanel2.add(jRadioButtonIcon5, null);
		jPanel2.add(jRadioButtonIcon6, null);
		jPanel2.add(jRadioButtonIcon7, null);
		jPanel2.add(jRadioButtonIcon8, null);
		jPanel2.add(jRadioButtonIcon9, null);
		jPanel2.add(jRadioButtonIcon10, null);
		jPanel2.add(jRadioButtonIcon11, null);
		jPanel2.add(jRadioButtonIcon12, null);
		jPanel2.add(jRadioButtonIcon13, null);
		jPanel2.add(jRadioButtonIcon14, null);
		jPanel2.add(jRadioButtonIcon15, null);
		jPanel2.add(jRadioButtonIcon16, null);
		jPanel2.add(jRadioButtonIcon17, null);
		jPanel2.add(jRadioButtonIcon18, null);
		jPanel2.add(jRadioButtonIcon19, null);
		jPanel2.add(jRadioButtonIcon20, null);
		getContentPane().add(panelMain);
		jPanel1.add(jRadioButtonArrowStyle1, null);
		jPanel1.add(jRadioButtonArrowStyle2, null);
		jPanel1.add(jRadioButtonArrowStyle3, null);
	}

	public boolean request(String action, org.w3c.dom.Element element, ArrayList<DataflowNode> nodeArray) {
		buttonOKIsPressed = false;
		//
		panelMain.removeAll();
		panelMain.add(jPanel1, null);
		panelMain.add(jLabel4, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jLabel5, null);
		panelMain.add(jLabel1, null);
		panelMain.add(jTextFieldNode1Name, null);
		panelMain.add(jLabel6, null);
		panelMain.add(jLabel7, null);
		panelMain.add(jTextFieldName, null);
		panelMain.add(jLabel3, null);
		panelMain.add(jTextFieldNameExt, null);
		panelMain.add(jButtonOK, null);
		panelMain.add(jButtonCancel, null);
		panelMain.add(jPanel2, null);
		panelMain.add(jComboBoxNode2, null);
		//
		//Setup Title and RadioButtons of ImageType//
		if (action.equals("Add")) {
			this.setTitle(res.getString("DialogDataflowLine32"));
		} else {
			this.setTitle(res.getString("DialogDataflowLine33"));
		}
		jRadioButtonIcon1.setSelected(true);
		if (element.getAttribute("ImageType").equals("1")) {
			jRadioButtonIcon1.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("2")) {
			jRadioButtonIcon2.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("3")) {
			jRadioButtonIcon3.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("4")) {
			jRadioButtonIcon4.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("5")) {
			jRadioButtonIcon5.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("6")) {
			jRadioButtonIcon6.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("7")) {
			jRadioButtonIcon7.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("8")) {
			jRadioButtonIcon8.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("9")) {
			jRadioButtonIcon9.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("10")) {
			jRadioButtonIcon10.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("11")) {
			jRadioButtonIcon11.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("12")) {
			jRadioButtonIcon12.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("13")) {
			jRadioButtonIcon13.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("14")) {
			jRadioButtonIcon14.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("15")) {
			jRadioButtonIcon15.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("16")) {
			jRadioButtonIcon16.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("17")) {
			jRadioButtonIcon17.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("18")) {
			jRadioButtonIcon18.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("19")) {
			jRadioButtonIcon19.setSelected(true);
		}
		if (element.getAttribute("ImageType").equals("20")) {
			jRadioButtonIcon20.setSelected(true);
		}
		jTextFieldName.setText(element.getAttribute("Name"));
		jTextFieldNameExt.setText(element.getAttribute("NameExt"));
		jTextFieldNode1Name.setText("");
		NodeList nodeList = frame_.currentMainTreeNode.getElement().getElementsByTagName("DataflowNode");
		org.w3c.dom.Element nodeElement;
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeElement = (org.w3c.dom.Element)nodeList.item(i);
			if (nodeElement.getAttribute("ID").equals(element.getAttribute("NodeID1"))) {
				if (nodeElement.getAttribute("Type").equals("Process")) {
					Modeler.XeadTreeNode node = frame_.getSpecificXeadTreeNode("Task", nodeElement.getAttribute("TaskID"), null);
					jTextFieldNode1Name.setText(node.getElement().getAttribute("Name"));
				} else {
					jTextFieldNode1Name.setText(nodeElement.getAttribute("Name"));
				}
			}
		}
		//
		//Setup arrow style//
		if (element.getAttribute("ShowArrow1").equals("true")) {
			if (element.getAttribute("ShowArrow2").equals("true")) {
				jRadioButtonArrowStyle3.setSelected(true);
			} else {
				jRadioButtonArrowStyle2.setSelected(true);
			}
		} else{
			jRadioButtonArrowStyle1.setSelected(true);
		}
		//
		//Setup ComboBox of nodes to be linked//
		int numberOfStorageArray = -1;
		int selectedIndex = 0;
		String selectedItemName = "";
		jComboBoxNode2.removeAllItems();
		for (int i = 0; i < nodeArray.size(); i++) {
			if (!nodeArray.get(i).getElement().getAttribute("ID").equals(element.getAttribute("NodeID1"))) {
				if (nodeArray.get(i).getElement().getAttribute("Type").equals("Process")) {
					Modeler.XeadTreeNode node = frame_.getSpecificXeadTreeNode("Task", nodeArray.get(i).getElement().getAttribute("TaskID"), null);
					jComboBoxNode2.addItem(node.getElement().getAttribute("Name"));
				} else {
					jComboBoxNode2.addItem(nodeArray.get(i).getElement().getAttribute("Name"));
				}
				numberOfStorageArray = numberOfStorageArray + 1;
				storageArray[numberOfStorageArray] = nodeArray.get(i);
				if (nodeArray.get(i).getElement().getAttribute("ID").equals(element.getAttribute("NodeID2")) && !action.equals("Add")) {
					selectedIndex = numberOfStorageArray;
				}
			}
		}
		jComboBoxNode2.setSelectedIndex(selectedIndex);
		selectedItemName = jComboBoxNode2.getSelectedItem().toString();
		//
		//Create spinner with specified default//
		int defaultValue = Integer.parseInt(element.getAttribute("SlideNumber"));
		SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(defaultValue, 1, 30, 1);
		JSpinner jSpinnerSlideNumber = new JSpinner(spinnerNumberModel);
		JSpinner.NumberEditor spinnerEditor = new JSpinner.NumberEditor(jSpinnerSlideNumber, "00");
		jSpinnerSlideNumber.setEditor(spinnerEditor);
		jSpinnerSlideNumber.setBounds(new Rectangle(102, 63, 43, 24));
		panelMain.add(jSpinnerSlideNumber, null);
		panelMain.getRootPane().setDefaultButton(jButtonOK);
		//
		//Setup dialog and show
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		this.setVisible(true);
		//
		//Update and return parameter value//
		if (buttonOKIsPressed) {
			//
			//Update DOM element//
			element.setAttribute("Name", jTextFieldName.getText());
			element.setAttribute("NameExt", jTextFieldNameExt.getText());
			element.setAttribute("SlideNumber", jSpinnerSlideNumber.getValue().toString());
			//
			String workName = jComboBoxNode2.getSelectedItem().toString();
			if (!workName.equals(selectedItemName) || action.equals("Add")) {
				int work = jComboBoxNode2.getSelectedIndex();
				DataflowNode node2 = storageArray[work];
				DataflowNode node1 = null;
				Point[] pointArray1, pointArray2;
				for (int i = 0; i < nodeArray.size(); i++) {
					if (nodeArray.get(i).getID().equals(element.getAttribute("NodeID1"))) {
						node1 = nodeArray.get(i);
						break;
					}
				}
				pointArray1 = node1.getArrowCordinates();
				pointArray2 = node2.getArrowCordinates();
				int workDistance = 0;
				int shortestDistance = 0;
				int shortestDistanceIndex1 = 0;
				int shortestDistanceIndex2 = 0;
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						workDistance = (pointArray1[i].x - pointArray2[j].x) * (pointArray1[i].x - pointArray2[j].x);
						workDistance = workDistance + (pointArray1[i].y - pointArray2[j].y) * (pointArray1[i].y - pointArray2[j].y);
						if (workDistance < shortestDistance || shortestDistance == 0) {
							shortestDistance = workDistance;
							shortestDistanceIndex1 = i;
							shortestDistanceIndex2 = j;
						}
					}
				}
				element.setAttribute("NodeID2", node2.getElement().getAttribute("ID"));
				element.setAttribute("TerminalPosIndex1", Integer.toString(shortestDistanceIndex1));
				element.setAttribute("TerminalPosIndex2", Integer.toString(shortestDistanceIndex2));
			}
			if (jRadioButtonArrowStyle1.isSelected()) {
				element.setAttribute("ShowArrow1", "false");
				element.setAttribute("ShowArrow2", "true");
			}
			if (jRadioButtonArrowStyle2.isSelected()) {
				element.setAttribute("ShowArrow1", "true");
				element.setAttribute("ShowArrow2", "false");
			}
			if (jRadioButtonArrowStyle3.isSelected()) {
				element.setAttribute("ShowArrow1", "true");
				element.setAttribute("ShowArrow2", "true");
			}
			if (jRadioButtonIcon1.isSelected()) {
				element.setAttribute("ImageType", "1");
			}
			if (jRadioButtonIcon2.isSelected()) {
				element.setAttribute("ImageType", "2");
			}
			if (jRadioButtonIcon3.isSelected()) {
				element.setAttribute("ImageType", "3");
			}
			if (jRadioButtonIcon4.isSelected()) {
				element.setAttribute("ImageType", "4");
			}
			if (jRadioButtonIcon5.isSelected()) {
				element.setAttribute("ImageType", "5");
			}
			if (jRadioButtonIcon6.isSelected()) {
				element.setAttribute("ImageType", "6");
			}
			if (jRadioButtonIcon7.isSelected()) {
				element.setAttribute("ImageType", "7");
			}
			if (jRadioButtonIcon8.isSelected()) {
				element.setAttribute("ImageType", "8");
			}
			if (jRadioButtonIcon9.isSelected()) {
				element.setAttribute("ImageType", "9");
			}
			if (jRadioButtonIcon10.isSelected()) {
				element.setAttribute("ImageType", "10");
			}
			if (jRadioButtonIcon11.isSelected()) {
				element.setAttribute("ImageType", "11");
			}
			if (jRadioButtonIcon12.isSelected()) {
				element.setAttribute("ImageType", "12");
			}
			if (jRadioButtonIcon13.isSelected()) {
				element.setAttribute("ImageType", "13");
			}
			if (jRadioButtonIcon14.isSelected()) {
				element.setAttribute("ImageType", "14");
			}
			if (jRadioButtonIcon15.isSelected()) {
				element.setAttribute("ImageType", "15");
			}
			if (jRadioButtonIcon16.isSelected()) {
				element.setAttribute("ImageType", "16");
			}
			if (jRadioButtonIcon17.isSelected()) {
				element.setAttribute("ImageType", "17");
			}
			if (jRadioButtonIcon18.isSelected()) {
				element.setAttribute("ImageType", "18");
			}
			if (jRadioButtonIcon19.isSelected()) {
				element.setAttribute("ImageType", "19");
			}
			if (jRadioButtonIcon20.isSelected()) {
				element.setAttribute("ImageType", "20");
			}
			if (action.equals("Add")) {
				frame_.currentMainTreeNode.getElement().appendChild(element);
			}
		}
		return buttonOKIsPressed;
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		buttonOKIsPressed = false;
		this.setVisible(false);
	}

	void jButtonOK_actionPerformed(ActionEvent e) {
		buttonOKIsPressed = true;
		this.setVisible(false);
	}
}

class DialogDataflowLine_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogDataflowLine adaptee;

	DialogDataflowLine_jButtonCancel_actionAdapter(DialogDataflowLine adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}

class DialogDataflowLine_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
	DialogDataflowLine adaptee;

	DialogDataflowLine_jButtonOK_actionAdapter(DialogDataflowLine adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonOK_actionPerformed(e);
	}
}
