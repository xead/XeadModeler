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

public class DialogCreateTableStatement extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JLabel jLabelTableNameOption = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private GridLayout gridLayout1 = new GridLayout();
	private JRadioButton jRadioButtonTableName = new JRadioButton();
	private JRadioButton jRadioButtonTableID = new JRadioButton();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel jLabelCommentMark = new JLabel();
	private JTextField jTextFieldCommentMark = new JTextField();
	private JLabel jLabelSectionMark = new JLabel();
	private JTextField jTextFieldSectionMark = new JTextField();
	private JCheckBox jCheckBoxWithComment = new JCheckBox();
	private JButton jButtonStart = new JButton();
	private JButton jButtonCancel = new JButton();
	private JTextArea jTextArea1 = new JTextArea();
	private Modeler frame_;
	private boolean startRequested = false;

	public DialogCreateTableStatement(Modeler frame, String title, boolean modal) {
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

	public DialogCreateTableStatement(Modeler frame) {
		this(frame, "", true);
	}
	private void jbInit() throws Exception {
		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(333, 233));
		this.setResizable(false);
		this.setTitle(res.getString("DialogCreateTableStatement01"));
		//
		jLabelTableNameOption.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelTableNameOption.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelTableNameOption.setText(res.getString("DialogCreateTableStatement03"));
		jLabelTableNameOption.setBounds(new Rectangle(10, 13, 90, 15));
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setBounds(new Rectangle(106, 12, 216, 53));
		jPanel1.setLayout(gridLayout1);
		gridLayout1.setColumns(1);
		gridLayout1.setRows(2);
		jRadioButtonTableName.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonTableName.setText(res.getString("DialogCreateTableStatement04"));
		jRadioButtonTableID.setFont(new java.awt.Font("Dialog", 0, 12));
		jRadioButtonTableID.setText(res.getString("DialogCreateTableStatement05"));
		//
		jLabelCommentMark.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelCommentMark.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelCommentMark.setText(res.getString("DialogCreateTableStatement06"));
		jLabelCommentMark.setBounds(new Rectangle(12, 72, 88, 15));
		jTextFieldCommentMark.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldCommentMark.setBounds(new Rectangle(106, 71, 25, 17));
		//
		jLabelSectionMark.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelSectionMark.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelSectionMark.setText(res.getString("DialogCreateTableStatement07"));
		jLabelSectionMark.setBounds(new Rectangle(142, 72, 118, 15));
		jTextFieldSectionMark.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextFieldSectionMark.setBounds(new Rectangle(265, 71, 20, 17));
		//
		jCheckBoxWithComment.setFont(new java.awt.Font("Dialog", 0, 12));
		jCheckBoxWithComment.setBounds(new Rectangle(12, 93, 310, 17));
		jCheckBoxWithComment.setText(res.getString("DialogCreateTableStatement11"));
		//
		jTextArea1.setFont(new java.awt.Font("Dialog", 0, 12));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(9, 117, 314, 72));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea1.setText(res.getString("DialogCreateTableStatement08"));
		//
		jButtonStart.setMaximumSize(new Dimension(71, 25));
		jButtonStart.setMinimumSize(new Dimension(71, 25));
		jButtonStart.setPreferredSize(new Dimension(71, 25));
		buttonGroup.add(jRadioButtonTableName);
		buttonGroup.add(jRadioButtonTableID);
		jButtonStart.setBounds(new Rectangle(146, 198, 73, 25));
		jButtonStart.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonStart.setText(res.getString("DialogCreateTableStatement09"));
		jButtonStart.addActionListener(new DialogCreateTableStatement_jButtonStart_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(247, 198, 73, 25));
		jButtonCancel.setFont(new java.awt.Font("Dialog", 0, 12));
		jButtonCancel.setText(res.getString("DialogCreateTableStatement10"));
		jButtonCancel.addActionListener(new DialogCreateTableStatement_jButtonCancel_actionAdapter(this));
		//
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(panelMain);
		jPanel1.add(jRadioButtonTableID, null);
		jPanel1.add(jRadioButtonTableName, null);
		panelMain.add(jLabelTableNameOption, null);
		panelMain.add(jPanel1, null);
		panelMain.add(jLabelCommentMark, null);
		panelMain.add(jTextFieldCommentMark, null);
		panelMain.add(jLabelSectionMark, null);
		panelMain.add(jTextFieldSectionMark, null);
		panelMain.add(jCheckBoxWithComment, null);
		panelMain.add(jTextArea1, null);
		panelMain.add(jButtonStart, null);
		panelMain.add(jButtonCancel, null);
		//
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public boolean request() {
		startRequested = false;
		//
		if (frame_.useTableNameAsTableNameInCreateTableStatement) {
			jRadioButtonTableName.setSelected(true);
		} else {
			jRadioButtonTableID.setSelected(true);
		}
		jTextFieldCommentMark.setText(frame_.createTableStatementCommentMark);
		jTextFieldSectionMark.setText(frame_.createTableStatementSectionMark);
		if (frame_.setCommentToFieldsWithAlias) {
			jCheckBoxWithComment.setSelected(true);
		} else {
			jCheckBoxWithComment.setSelected(false);
		}
		//
		panelMain.getRootPane().setDefaultButton(jButtonStart);
		super.setVisible(true);
		//
		return startRequested;
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		startRequested = true;
		setVariants();
		this.setVisible(false);
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		setVariants();
		this.setVisible(false);
	}

	void setVariants() {
		if (jRadioButtonTableName.isSelected()) {
			frame_.useTableNameAsTableNameInCreateTableStatement = true;
		}
		if (jRadioButtonTableID.isSelected()) {
			frame_.useTableNameAsTableNameInCreateTableStatement = false;
		}
		frame_.createTableStatementCommentMark =jTextFieldCommentMark.getText();
		frame_.createTableStatementSectionMark = jTextFieldSectionMark.getText();
		if (jCheckBoxWithComment.isSelected()) {
			frame_.setCommentToFieldsWithAlias = true;
		} else {
			frame_.setCommentToFieldsWithAlias = false;
		}
	}
}

class DialogCreateTableStatement_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogCreateTableStatement adaptee;

	DialogCreateTableStatement_jButtonStart_actionAdapter(DialogCreateTableStatement adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogCreateTableStatement_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogCreateTableStatement adaptee;

	DialogCreateTableStatement_jButtonCancel_actionAdapter(DialogCreateTableStatement adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
