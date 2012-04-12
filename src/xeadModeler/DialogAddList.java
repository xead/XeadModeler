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
import java.util.ResourceBundle;

public class DialogAddList extends JDialog {
	private static final long serialVersionUID = 1L;
	static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	JPanel panelMain = new JPanel();
	JButton jButtonOK = new JButton();
	JButton jButtonCancel = new JButton();
	JButton jButtonImport = new JButton();
	int reply;
	Modeler frame_;
	JLabel jLabelHeadID = new JLabel();
	JLabel jLabelHeadName = new JLabel();
	JLabel jLabelNo1 = new JLabel();
	JTextField jTextFieldID1 = new JTextField();
	KanjiTextField jTextFieldName1 = new KanjiTextField();
	KanjiTextField jTextFieldName2 = new KanjiTextField();
	JTextField jTextFieldID2 = new JTextField();
	JLabel jLabelNo2 = new JLabel();
	KanjiTextField jTextFieldName3 = new KanjiTextField();
	JTextField jTextFieldID3 = new JTextField();
	JLabel jLabelNo3 = new JLabel();
	KanjiTextField jTextFieldName4 = new KanjiTextField();
	JTextField jTextFieldID4 = new JTextField();
	JLabel jLabelNo4 = new JLabel();
	KanjiTextField jTextFieldName5 = new KanjiTextField();
	JTextField jTextFieldID5 = new JTextField();
	JLabel jLabelNo5 = new JLabel();
	KanjiTextField jTextFieldName6 = new KanjiTextField();
	JTextField jTextFieldID6 = new JTextField();
	JLabel jLabelNo6 = new JLabel();
	KanjiTextField jTextFieldName7 = new KanjiTextField();
	JTextField jTextFieldID7 = new JTextField();
	JLabel jLabelNo7 = new JLabel();
	KanjiTextField jTextFieldName8 = new KanjiTextField();
	JTextField jTextFieldID8 = new JTextField();
	JLabel jLabelNo8 = new JLabel();
	KanjiTextField jTextFieldName9 = new KanjiTextField();
	JTextField jTextFieldID9 = new JTextField();
	JLabel jLabelNo9 = new JLabel();
	KanjiTextField jTextFieldName10 = new KanjiTextField();
	JTextField jTextFieldID10 = new JTextField();
	JLabel jLabelNo10 = new JLabel();
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanelButtons = new JPanel();

	public DialogAddList(Modeler frame) {
		super(frame, "", true);
		frame_ = frame;
		try {
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		jLabelNo1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo7.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo8.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo9.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo10.setHorizontalAlignment(SwingConstants.RIGHT);
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().setLayout(borderLayout1);
		jPanelButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelButtons.setPreferredSize(new Dimension(400, 43));
		jPanelButtons.setLayout(null);
		//
		panelMain.add(jLabelHeadID, null);
		panelMain.add(jLabelHeadName, null);
		panelMain.add(jTextFieldID1, null);
		panelMain.add(jTextFieldID2, null);
		panelMain.add(jTextFieldID3, null);
		panelMain.add(jTextFieldID4, null);
		panelMain.add(jTextFieldID5, null);
		panelMain.add(jTextFieldID6, null);
		panelMain.add(jTextFieldID7, null);
		panelMain.add(jTextFieldID8, null);
		panelMain.add(jTextFieldID9, null);
		panelMain.add(jTextFieldID10, null);
		panelMain.add(jTextFieldName1, null);
		panelMain.add(jTextFieldName2, null);
		panelMain.add(jTextFieldName3, null);
		panelMain.add(jTextFieldName4, null);
		panelMain.add(jTextFieldName5, null);
		panelMain.add(jTextFieldName6, null);
		panelMain.add(jTextFieldName7, null);
		panelMain.add(jTextFieldName8, null);
		panelMain.add(jTextFieldName9, null);
		panelMain.add(jTextFieldName10, null);
		panelMain.add(jLabelNo1, null);
		panelMain.add(jLabelNo2, null);
		panelMain.add(jLabelNo3, null);
		panelMain.add(jLabelNo4, null);
		panelMain.add(jLabelNo5, null);
		panelMain.add(jLabelNo6, null);
		panelMain.add(jLabelNo7, null);
		panelMain.add(jLabelNo8, null);
		panelMain.add(jLabelNo9, null);
		panelMain.add(jLabelNo10, null);
		//
		this.getContentPane().add(jPanelButtons,  BorderLayout.SOUTH);
		jPanelButtons.add(jButtonOK, null);
		jPanelButtons.add(jButtonImport, null);
		jPanelButtons.add(jButtonCancel, null);
		//
		panelMain.setLayout(null);
		this.setResizable(false);
		this.setSize(new Dimension(400, 347));
		panelMain.setPreferredSize(new Dimension(400, 310));
		jButtonOK.setBounds(new Rectangle(44, 10, 73, 25));
		jButtonOK.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogAddList_jButtonOK_actionAdapter(this));
		jButtonImport.setBounds(new Rectangle(154, 10, 103, 25));
		jButtonImport.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonImport.setText(res.getString("DialogAddList01"));
		jButtonImport.addActionListener(new DialogAddList_jButtonImport_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(284, 10, 73, 25));
		jButtonCancel.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonCancel.setText(res.getString("DialogAddList02"));
		jButtonCancel.addActionListener(new DialogAddList_jButtonCancel_actionAdapter(this));
		jLabelHeadID.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelHeadID.setText("ID");
		jLabelHeadID.setBounds(new Rectangle(46, 11, 70, 15));
		jLabelHeadName.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelHeadName.setBounds(new Rectangle(142, 10, 150, 15));
		jLabelNo1.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo1.setText("1");
		jLabelNo1.setBounds(new Rectangle(12, 29, 19, 15));
		jTextFieldID1.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID1.setText("");
		jTextFieldID1.setBounds(new Rectangle(41, 28, 86, 21));
		jTextFieldName1.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName1.setText("");
		jTextFieldName1.setBounds(new Rectangle(136, 28, 249, 22));
		jTextFieldName2.setBounds(new Rectangle(136, 55, 249, 22));
		jTextFieldName2.setText("");
		jTextFieldName2.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID2.setBounds(new Rectangle(41, 55, 86, 21));
		jTextFieldID2.setText("");
		jTextFieldID2.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo2.setBounds(new Rectangle(12, 56, 19, 15));
		jLabelNo2.setText("2");
		jLabelNo2.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName3.setBounds(new Rectangle(136, 82, 249, 22));
		jTextFieldName3.setText("");
		jTextFieldName3.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID3.setBounds(new Rectangle(41, 82, 86, 21));
		jTextFieldID3.setText("");
		jTextFieldID3.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo3.setBounds(new Rectangle(12, 83, 19, 15));
		jLabelNo3.setText("3");
		jLabelNo3.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName4.setBounds(new Rectangle(136, 109, 249, 22));
		jTextFieldName4.setText("");
		jTextFieldName4.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID4.setBounds(new Rectangle(41, 109, 86, 21));
		jTextFieldID4.setText("");
		jTextFieldID4.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo4.setBounds(new Rectangle(12, 110, 19, 15));
		jLabelNo4.setText("4");
		jLabelNo4.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName5.setBounds(new Rectangle(136, 136, 249, 22));
		jTextFieldName5.setText("");
		jTextFieldName5.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID5.setBounds(new Rectangle(41, 136, 86, 21));
		jTextFieldID5.setText("");
		jTextFieldID5.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo5.setBounds(new Rectangle(12, 137, 19, 15));
		jLabelNo5.setText("5");
		jLabelNo5.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName6.setBounds(new Rectangle(136, 164, 249, 22));
		jTextFieldName6.setText("");
		jTextFieldName6.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID6.setBounds(new Rectangle(41, 164, 86, 21));
		jTextFieldID6.setText("");
		jTextFieldID6.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo6.setBounds(new Rectangle(12, 165, 19, 15));
		jLabelNo6.setText("6");
		jLabelNo6.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName7.setBounds(new Rectangle(136, 191, 249, 22));
		jTextFieldName7.setText("");
		jTextFieldName7.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID7.setBounds(new Rectangle(41, 191, 86, 21));
		jTextFieldID7.setText("");
		jTextFieldID7.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo7.setBounds(new Rectangle(12, 192, 19, 15));
		jLabelNo7.setText("7");
		jLabelNo7.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName8.setBounds(new Rectangle(136, 218, 249, 22));
		jTextFieldName8.setText("");
		jTextFieldName8.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID8.setBounds(new Rectangle(41, 218, 86, 21));
		jTextFieldID8.setText("");
		jTextFieldID8.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo8.setBounds(new Rectangle(12, 219, 19, 15));
		jLabelNo8.setText("8");
		jLabelNo8.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName9.setBounds(new Rectangle(136, 245, 249, 22));
		jTextFieldName9.setText("");
		jTextFieldName9.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID9.setBounds(new Rectangle(41, 245, 86, 21));
		jTextFieldID9.setText("");
		jTextFieldID9.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo9.setBounds(new Rectangle(12, 246, 19, 15));
		jLabelNo9.setText("9");
		jLabelNo9.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldName10.setBounds(new Rectangle(136, 272, 249, 22));
		jTextFieldName10.setText("");
		jTextFieldName10.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldID10.setBounds(new Rectangle(41, 272, 86, 21));
		jTextFieldID10.setText("");
		jTextFieldID10.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelNo10.setBounds(new Rectangle(12, 273, 19, 15));
		jLabelNo10.setText("10");
		jLabelNo10.setFont(new java.awt.Font("Dialog", 0, 12));
		this.getContentPane().add(panelMain,  BorderLayout.CENTER);
	}
	//
	public int request() {
		reply = 0;
		//
		jTextFieldID1.setText("");
		jTextFieldID2.setText("");
		jTextFieldID3.setText("");
		jTextFieldID4.setText("");
		jTextFieldID5.setText("");
		jTextFieldID6.setText("");
		jTextFieldID7.setText("");
		jTextFieldID8.setText("");
		jTextFieldID9.setText("");
		jTextFieldID10.setText("");
		jTextFieldName1.setText("");
		jTextFieldName2.setText("");
		jTextFieldName3.setText("");
		jTextFieldName4.setText("");
		jTextFieldName5.setText("");
		jTextFieldName6.setText("");
		jTextFieldName7.setText("");
		jTextFieldName8.setText("");
		jTextFieldName9.setText("");
		jTextFieldName10.setText("");
		//
		String nodeType = frame_.currentMainTreeNode.getType();
		if (nodeType.equals("SubjectAreaList")) {
			this.setTitle(res.getString("DialogAddList03"));
			jLabelHeadName.setText(res.getString("DialogAddList04"));
		}
		if (nodeType.equals("RoleList")) {
			this.setTitle(res.getString("DialogAddList05"));
			jLabelHeadName.setText(res.getString("DialogAddList06"));
		}
		if (nodeType.equals("Role")) {
			this.setTitle(res.getString("DialogAddList07"));
			jLabelHeadName.setText(res.getString("DialogAddList08"));
		}
		if (nodeType.equals("SubsystemList")) {
			this.setTitle(res.getString("DialogAddList09"));
			jLabelHeadName.setText(res.getString("DialogAddList10"));
		}
		if (nodeType.equals("TableList")) {
			this.setTitle(res.getString("DialogAddList11"));
			jLabelHeadName.setText(res.getString("DialogAddList12"));
		}
		if (nodeType.equals("TableFieldList")) {
			this.setTitle(res.getString("DialogAddList13"));
			jLabelHeadName.setText(res.getString("DialogAddList14"));
		}
		if (nodeType.equals("FunctionList")) {
			this.setTitle(res.getString("DialogAddList15"));
			jLabelHeadName.setText(res.getString("DialogAddList16"));
		}
		//
		jPanelButtons.getRootPane().setDefaultButton(jButtonOK);
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		super.setVisible(true);
		//
		return reply;
	}
	//
	void jButtonOK_actionPerformed(ActionEvent e) {
		reply = 1;
		//
		if (!jTextFieldName1.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID1.getText(), jTextFieldName1.getText());
		}
		if (!jTextFieldName2.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID2.getText(), jTextFieldName2.getText());
		}
		if (!jTextFieldName3.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID3.getText(), jTextFieldName3.getText());
		}
		if (!jTextFieldName4.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID4.getText(), jTextFieldName4.getText());
		}
		if (!jTextFieldName5.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID5.getText(), jTextFieldName5.getText());
		}
		if (!jTextFieldName6.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID6.getText(), jTextFieldName6.getText());
		}
		if (!jTextFieldName7.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID7.getText(), jTextFieldName7.getText());
		}
		if (!jTextFieldName8.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID8.getText(), jTextFieldName8.getText());
		}
		if (!jTextFieldName9.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID9.getText(), jTextFieldName9.getText());
		}
		if (!jTextFieldName10.getText().equals("")) {
			frame_.currentMainTreeNode.addChildNode(null, "", jTextFieldID10.getText(), jTextFieldName10.getText());
		}
		this.setVisible(false);
	}
	//
	void jButtonImport_actionPerformed(ActionEvent e) {
		reply = 2;
		this.setVisible(false);
	}
	//
	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

class DialogAddList_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
	DialogAddList adaptee;
	DialogAddList_jButtonOK_actionAdapter(DialogAddList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonOK_actionPerformed(e);
	}
}

class DialogAddList_jButtonImport_actionAdapter implements java.awt.event.ActionListener {
	DialogAddList adaptee;
	DialogAddList_jButtonImport_actionAdapter(DialogAddList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonImport_actionPerformed(e);
	}
}

class DialogAddList_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogAddList adaptee;
	DialogAddList_jButtonCancel_actionAdapter(DialogAddList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
