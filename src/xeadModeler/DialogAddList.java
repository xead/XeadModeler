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

import javax.swing.*;

import java.awt.event.*;
import java.util.ResourceBundle;

public class DialogAddList extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JButton jButtonOK = new JButton();
	private JButton jButtonCancel = new JButton();
	private JButton jButtonImport = new JButton();
	private int reply;
	private Modeler frame_;
	private JLabel jLabelHeadID = new JLabel();
	private JLabel jLabelHeadName = new JLabel();
	private JLabel jLabelNo1 = new JLabel();
	private JTextField jTextFieldID1 = new JTextField();
	private KanjiTextField jTextFieldName1 = new KanjiTextField();
	private KanjiTextField jTextFieldName2 = new KanjiTextField();
	private JTextField jTextFieldID2 = new JTextField();
	private JLabel jLabelNo2 = new JLabel();
	private KanjiTextField jTextFieldName3 = new KanjiTextField();
	private JTextField jTextFieldID3 = new JTextField();
	private JLabel jLabelNo3 = new JLabel();
	private KanjiTextField jTextFieldName4 = new KanjiTextField();
	private JTextField jTextFieldID4 = new JTextField();
	private JLabel jLabelNo4 = new JLabel();
	private KanjiTextField jTextFieldName5 = new KanjiTextField();
	private JTextField jTextFieldID5 = new JTextField();
	private JLabel jLabelNo5 = new JLabel();
	private KanjiTextField jTextFieldName6 = new KanjiTextField();
	private JTextField jTextFieldID6 = new JTextField();
	private JLabel jLabelNo6 = new JLabel();
	private KanjiTextField jTextFieldName7 = new KanjiTextField();
	private JTextField jTextFieldID7 = new JTextField();
	private JLabel jLabelNo7 = new JLabel();
	private KanjiTextField jTextFieldName8 = new KanjiTextField();
	private JTextField jTextFieldID8 = new JTextField();
	private JLabel jLabelNo8 = new JLabel();
	private KanjiTextField jTextFieldName9 = new KanjiTextField();
	private JTextField jTextFieldID9 = new JTextField();
	private JLabel jLabelNo9 = new JLabel();
	private KanjiTextField jTextFieldName10 = new KanjiTextField();
	private JTextField jTextFieldID10 = new JTextField();
	private JLabel jLabelNo10 = new JLabel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private JPanel jPanelButtons = new JPanel();

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
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().setLayout(borderLayout1);
		jPanelButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelButtons.setPreferredSize(new Dimension(400, 43));
		jPanelButtons.setLayout(null);

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

		this.getContentPane().add(jPanelButtons,  BorderLayout.SOUTH);
		jPanelButtons.add(jButtonOK, null);
		jPanelButtons.add(jButtonImport, null);
		jPanelButtons.add(jButtonCancel, null);

		panelMain.setLayout(null);
		this.setResizable(false);
		this.setSize(new Dimension(400, 347));
		panelMain.setPreferredSize(new Dimension(458, 302));

		jButtonOK.setBounds(new Rectangle(40, 7, 90, 28));
		jButtonOK.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogAddList_jButtonOK_actionAdapter(this));
		jButtonImport.setBounds(new Rectangle(165, 7, 130, 28));
		jButtonImport.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonImport.setText(res.getString("DialogAddList01"));
		jButtonImport.addActionListener(new DialogAddList_jButtonImport_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(330, 7, 90, 28));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogAddList02"));
		jButtonCancel.addActionListener(new DialogAddList_jButtonCancel_actionAdapter(this));

		jLabelHeadID.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelHeadID.setText("ID");
		jLabelHeadID.setBounds(new Rectangle(43, 5, 70, 22));
		jLabelHeadName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelHeadName.setBounds(new Rectangle(144, 5, 200, 22));

		jLabelNo1.setBounds(new Rectangle(12, 27, 25, 27));
		jLabelNo1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo1.setText("1");
		jLabelNo1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID1.setText("");
		jTextFieldID1.setBounds(new Rectangle(41, 27, 100, 25));
		jTextFieldName1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName1.setText("");
		jTextFieldName1.setBounds(new Rectangle(142, 27, 300, 25));

		jLabelNo2.setBounds(new Rectangle(12, 53, 25, 25));
		jLabelNo2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo2.setText("2");
		jLabelNo2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID2.setBounds(new Rectangle(41, 53, 100, 25));
		jTextFieldID2.setText("");
		jTextFieldID2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName2.setBounds(new Rectangle(142, 53, 300, 25));
		jTextFieldName2.setText("");
		jTextFieldName2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo3.setBounds(new Rectangle(12, 79, 25, 25));
		jLabelNo3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo3.setText("3");
		jLabelNo3.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID3.setBounds(new Rectangle(41, 79, 100, 25));
		jTextFieldID3.setText("");
		jTextFieldID3.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName3.setBounds(new Rectangle(142, 79, 300, 25));
		jTextFieldName3.setText("");
		jTextFieldName3.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo4.setBounds(new Rectangle(12, 105, 25, 25));
		jLabelNo4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo4.setText("4");
		jLabelNo4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID4.setBounds(new Rectangle(41, 105, 100, 25));
		jTextFieldID4.setText("");
		jTextFieldID4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName4.setBounds(new Rectangle(142, 105, 300, 25));
		jTextFieldName4.setText("");
		jTextFieldName4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo5.setBounds(new Rectangle(12, 131, 25, 25));
		jLabelNo5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo5.setText("5");
		jLabelNo5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID5.setBounds(new Rectangle(41, 131, 100, 25));
		jTextFieldID5.setText("");
		jTextFieldID5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName5.setBounds(new Rectangle(142, 131, 300, 25));
		jTextFieldName5.setText("");
		jTextFieldName5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo6.setBounds(new Rectangle(12, 157, 25, 15));
		jLabelNo6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo6.setText("6");
		jLabelNo6.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID6.setBounds(new Rectangle(41, 157, 100, 25));
		jTextFieldID6.setText("");
		jTextFieldID6.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName6.setBounds(new Rectangle(142, 157, 300, 25));
		jTextFieldName6.setText("");
		jTextFieldName6.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo7.setBounds(new Rectangle(12, 183, 25, 25));
		jLabelNo7.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo7.setText("7");
		jLabelNo7.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID7.setBounds(new Rectangle(41, 183, 100, 25));
		jTextFieldID7.setText("");
		jTextFieldID7.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName7.setBounds(new Rectangle(142, 183, 300, 25));
		jTextFieldName7.setText("");
		jTextFieldName7.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo8.setBounds(new Rectangle(12, 209, 25, 25));
		jLabelNo8.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo8.setText("8");
		jLabelNo8.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID8.setBounds(new Rectangle(41, 209, 100, 25));
		jTextFieldID8.setText("");
		jTextFieldID8.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName8.setBounds(new Rectangle(142, 209, 300, 25));
		jTextFieldName8.setText("");
		jTextFieldName8.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo9.setBounds(new Rectangle(12, 235, 25, 25));
		jLabelNo9.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo9.setText("9");
		jLabelNo9.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID9.setBounds(new Rectangle(41, 235, 100, 25));
		jTextFieldID9.setText("");
		jTextFieldID9.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName9.setBounds(new Rectangle(142, 235, 300, 25));
		jTextFieldName9.setText("");
		jTextFieldName9.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		jLabelNo10.setBounds(new Rectangle(12, 262, 25, 25));
		jLabelNo10.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelNo10.setText("10");
		jLabelNo10.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldID10.setBounds(new Rectangle(41, 262, 100, 25));
		jTextFieldID10.setText("");
		jTextFieldID10.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName10.setBounds(new Rectangle(142, 262, 300, 25));
		jTextFieldName10.setText("");
		jTextFieldName10.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));

		this.getContentPane().add(panelMain,  BorderLayout.CENTER);
	}

	public int request() {
		reply = 0;
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

		jPanelButtons.getRootPane().setDefaultButton(jButtonOK);
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		super.setVisible(true);
		return reply;
	}

	void jButtonOK_actionPerformed(ActionEvent e) {
		reply = 1;
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

	void jButtonImport_actionPerformed(ActionEvent e) {
		reply = 2;
		this.setVisible(false);
	}

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
