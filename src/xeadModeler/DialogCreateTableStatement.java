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

public class DialogCreateTableStatement extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JLabel jLabelTableNameOption = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private GridLayout gridLayout1 = new GridLayout();
	private JRadioButton jRadioButtonTableName = new JRadioButton();
	private JRadioButton jRadioButtonTableAlias = new JRadioButton();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel jLabelCommentMark = new JLabel();
	private JTextField jTextFieldCommentMark = new JTextField();
	private JLabel jLabelSectionMark = new JLabel();
	private JTextField jTextFieldSectionMark = new JTextField();
	private JCheckBox jCheckBoxWithComment = new JCheckBox();
	private JCheckBox jCheckBoxIgnoreFKConstraints = new JCheckBox();
	private JCheckBox jCheckBoxIgnoreWarnings = new JCheckBox();
	private JLabel jLabelAdditionalParms = new JLabel();
	private JTextArea jTextAreaAdditionalParms = new JTextArea();
	private JScrollPane jScrollPaneAdditionalParms = new JScrollPane();
	private JTextArea jTextAreaMessage = new JTextArea();
	private JButton jButtonClose = new JButton();
	private JButton jButtonSet = new JButton();
	private Modeler frame_;
	//private boolean startRequested = false;
	private int reply = -1;

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
		panelMain.setPreferredSize(new Dimension(410, 412));
		this.setResizable(false);
		this.setTitle(res.getString("DialogCreateTableStatement01"));

		jLabelTableNameOption.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelTableNameOption.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelTableNameOption.setText(res.getString("DialogCreateTableStatement03"));
		jLabelTableNameOption.setBounds(new Rectangle(5, 13, 130, 20));
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setBounds(new Rectangle(140, 12, 261, 60));
		jPanel1.setLayout(gridLayout1);
		gridLayout1.setColumns(1);
		gridLayout1.setRows(2);
		jRadioButtonTableName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTableName.setText(res.getString("DialogCreateTableStatement04"));
		jRadioButtonTableAlias.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTableAlias.setText(res.getString("DialogCreateTableStatement05"));

		jLabelCommentMark.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelCommentMark.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelCommentMark.setText(res.getString("DialogCreateTableStatement06"));
		jLabelCommentMark.setBounds(new Rectangle(5, 80, 130, 20));
		jTextFieldCommentMark.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldCommentMark.setBounds(new Rectangle(140, 77, 30, 25));

		jLabelSectionMark.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelSectionMark.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelSectionMark.setText(res.getString("DialogCreateTableStatement07"));
		jLabelSectionMark.setBounds(new Rectangle(180, 80, 140, 20));
		jTextFieldSectionMark.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldSectionMark.setBounds(new Rectangle(325, 77, 30, 25));

		jCheckBoxWithComment.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxWithComment.setBounds(new Rectangle(12, 108, 390, 25));
		jCheckBoxWithComment.setText(res.getString("DialogCreateTableStatement11"));

		jCheckBoxIgnoreFKConstraints.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxIgnoreFKConstraints.setBounds(new Rectangle(12, 136, 390, 25));
		jCheckBoxIgnoreFKConstraints.setText(res.getString("DialogCreateTableStatement13"));

		jCheckBoxIgnoreWarnings.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxIgnoreWarnings.setBounds(new Rectangle(12, 164, 390, 25));
		jCheckBoxIgnoreWarnings.setText(res.getString("DialogCreateTableStatement14"));

		jLabelAdditionalParms.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelAdditionalParms.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelAdditionalParms.setText(res.getString("DialogCreateTableStatement12"));
		jLabelAdditionalParms.setBounds(new Rectangle(12, 192, 200, 20));
		jTextAreaAdditionalParms.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jScrollPaneAdditionalParms.getViewport().add(jTextAreaAdditionalParms, null);
		jScrollPaneAdditionalParms.setBounds(new Rectangle(9, 214, 392, 70));

		jTextAreaMessage.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextAreaMessage.setForeground(Color.BLUE);
		jTextAreaMessage.setEditable(false);
		jTextAreaMessage.setBounds(new Rectangle(9, 294, 392, 74));
		jTextAreaMessage.setLineWrap(true);
		jTextAreaMessage.setBackground(SystemColor.control);
		jTextAreaMessage.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextAreaMessage.setText(res.getString("DialogCreateTableStatement08"));

		buttonGroup.add(jRadioButtonTableName);
		buttonGroup.add(jRadioButtonTableAlias);
		jButtonClose.setBounds(new Rectangle(50, 377, 100, 27));
		jButtonClose.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonClose.setText(res.getString("DialogCreateTableStatement09"));
		jButtonClose.addActionListener(new DialogCreateTableStatement_jButtonClose_actionAdapter(this));
		jButtonSet.setBounds(new Rectangle(250, 377, 100, 27));
		jButtonSet.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonSet.setText(res.getString("DialogCreateTableStatement10"));
		jButtonSet.addActionListener(new DialogCreateTableStatement_jButtonSet_actionAdapter(this));

		panelMain.setBorder(BorderFactory.createEtchedBorder());
		getContentPane().add(panelMain);
		jPanel1.add(jRadioButtonTableAlias, null);
		jPanel1.add(jRadioButtonTableName, null);
		panelMain.add(jLabelTableNameOption, null);
		panelMain.add(jPanel1, null);
		panelMain.add(jLabelCommentMark, null);
		panelMain.add(jTextFieldCommentMark, null);
		panelMain.add(jLabelSectionMark, null);
		panelMain.add(jTextFieldSectionMark, null);
		panelMain.add(jCheckBoxWithComment, null);
		panelMain.add(jCheckBoxIgnoreFKConstraints, null);
		panelMain.add(jCheckBoxIgnoreWarnings, null);
		panelMain.add(jLabelAdditionalParms, null);
		panelMain.add(jScrollPaneAdditionalParms, null);
		panelMain.add(jTextAreaMessage, null);
		panelMain.add(jButtonClose, null);
		panelMain.add(jButtonSet, null);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public int request() {
		reply = -1;
		if (frame_.useTableNameAsTableNameInCreateTableStatement) {
			jRadioButtonTableName.setSelected(true);
		} else {
			jRadioButtonTableAlias.setSelected(true);
		}
		jTextFieldCommentMark.setText(frame_.ddlCommentMark);
		jTextFieldSectionMark.setText(frame_.ddlSectionMark);
		if (frame_.setCommentToFieldsWithAlias) {
			jCheckBoxWithComment.setSelected(true);
		} else {
			jCheckBoxWithComment.setSelected(false);
		}
		if (frame_.ignoreForeignKeyConstraints) {
			jCheckBoxIgnoreFKConstraints.setSelected(true);
		} else {
			jCheckBoxIgnoreFKConstraints.setSelected(false);
		}
		if (frame_.ignoreKeyDefinitionWarnings) {
			jCheckBoxIgnoreWarnings.setSelected(true);
		} else {
			jCheckBoxIgnoreWarnings.setSelected(false);
		}
		jTextAreaAdditionalParms.setText(frame_.ddlAdditionalParms);

		panelMain.getRootPane().setDefaultButton(jButtonSet);
		super.setVisible(true);
		return reply;
	}

	void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void jButtonSet_actionPerformed(ActionEvent e) {
		reply = 0;
		setVariants();
		this.setVisible(false);
	}

	void setVariants() {
		if (jRadioButtonTableName.isSelected()) {
			frame_.useTableNameAsTableNameInCreateTableStatement = true;
		}
		if (jRadioButtonTableAlias.isSelected()) {
			frame_.useTableNameAsTableNameInCreateTableStatement = false;
		}
		frame_.ddlCommentMark =jTextFieldCommentMark.getText();
		frame_.ddlSectionMark = jTextFieldSectionMark.getText();
		if (jCheckBoxWithComment.isSelected()) {
			frame_.setCommentToFieldsWithAlias = true;
		} else {
			frame_.setCommentToFieldsWithAlias = false;
		}
		if (jCheckBoxIgnoreFKConstraints.isSelected()) {
			frame_.ignoreForeignKeyConstraints = true;
		} else {
			frame_.ignoreForeignKeyConstraints = false;
		}
		if (jCheckBoxIgnoreWarnings.isSelected()) {
			frame_.ignoreKeyDefinitionWarnings = true;
		} else {
			frame_.ignoreKeyDefinitionWarnings = false;
		}
		frame_.ddlAdditionalParms = jTextAreaAdditionalParms.getText();
	}
}

class DialogCreateTableStatement_jButtonClose_actionAdapter implements java.awt.event.ActionListener {
	DialogCreateTableStatement adaptee;

	DialogCreateTableStatement_jButtonClose_actionAdapter(DialogCreateTableStatement adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonClose_actionPerformed(e);
	}
}

class DialogCreateTableStatement_jButtonSet_actionAdapter implements java.awt.event.ActionListener {
	DialogCreateTableStatement adaptee;

	DialogCreateTableStatement_jButtonSet_actionAdapter(DialogCreateTableStatement adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonSet_actionPerformed(e);
	}
}
