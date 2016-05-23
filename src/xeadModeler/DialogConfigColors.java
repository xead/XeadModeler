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

public class DialogConfigColors extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JCheckBox jCheckBox = new JCheckBox();
	private JPanel jPanelRadioButtonsColor = new JPanel();
	private JRadioButton jRadioButtonColor1 = new JRadioButton();
	private JRadioButton jRadioButtonColor2 = new JRadioButton();
	private ButtonGroup buttonGroupColor = new ButtonGroup();
	private JPanel jPanelRadioButtonsSize = new JPanel();
	private JRadioButton jRadioButtonSizeS = new JRadioButton();
	private JRadioButton jRadioButtonSizeM = new JRadioButton();
	private JRadioButton jRadioButtonSizeL = new JRadioButton();
	private ButtonGroup buttonGroupSize = new ButtonGroup();
	private JButton jButtonOK = new JButton();
	private JButton jButtonCancel = new JButton();
	private boolean buttonOKIsPressed;
	private Modeler frame_;

	public DialogConfigColors(Modeler frame, String title, boolean modal) {
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

	public DialogConfigColors(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setModal(true);
		this.setResizable(false);
		this.setTitle(res.getString("DialogConfigColors01"));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(310, 170));
		panelMain.setBorder(BorderFactory.createEtchedBorder());

		jCheckBox.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBox.setText(res.getString("DialogConfigColors02"));
		jCheckBox.setBounds(new Rectangle(8, 12, 200, 20));

		jPanelRadioButtonsColor.setLayout(null);
		jPanelRadioButtonsColor.setBounds(new Rectangle(5, 40, 300, 38));
		jPanelRadioButtonsColor.setBorder(BorderFactory.createEtchedBorder());
		jRadioButtonColor1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonColor1.setText(res.getString("DialogConfigColors03"));
		jRadioButtonColor1.setBounds(new Rectangle(5, 9, 140, 20));
		jRadioButtonColor2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonColor2.setText(res.getString("DialogConfigColors04"));
		jRadioButtonColor2.setBounds(new Rectangle(150, 9, 140, 20));
		buttonGroupColor.add(jRadioButtonColor1);
		buttonGroupColor.add(jRadioButtonColor2);

		jPanelRadioButtonsSize.setLayout(null);
		jPanelRadioButtonsSize.setBounds(new Rectangle(5, 81, 300, 38));
		jPanelRadioButtonsSize.setBorder(BorderFactory.createEtchedBorder());
		jRadioButtonSizeS.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonSizeS.setText("S");
		jRadioButtonSizeS.setBounds(new Rectangle(5, 9, 90, 20));
		jRadioButtonSizeM.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonSizeM.setText("M");
		jRadioButtonSizeM.setBounds(new Rectangle(100, 9, 90, 20));
		jRadioButtonSizeL.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonSizeL.setText("L");
		jRadioButtonSizeL.setBounds(new Rectangle(195, 9, 90, 20));
		buttonGroupSize.add(jRadioButtonSizeS);
		buttonGroupSize.add(jRadioButtonSizeM);
		buttonGroupSize.add(jRadioButtonSizeL);

		jButtonOK.setBounds(new Rectangle(30, 130, 110, 27));
		jButtonOK.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogConfigColors_jButtonOK_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(170, 130, 110, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogConfigColors05"));
		jButtonCancel.addActionListener(new DialogConfigColors_jButtonCancel_actionAdapter(this));

		getContentPane().add(panelMain);
		panelMain.add(jCheckBox, null);
		panelMain.add(jPanelRadioButtonsColor, null);
		jPanelRadioButtonsColor.add(jRadioButtonColor1, null);
		jPanelRadioButtonsColor.add(jRadioButtonColor2, null);
		panelMain.add(jPanelRadioButtonsSize, null);
		jPanelRadioButtonsSize.add(jRadioButtonSizeS, null);
		jPanelRadioButtonsSize.add(jRadioButtonSizeM, null);
		jPanelRadioButtonsSize.add(jRadioButtonSizeL, null);
		panelMain.add(jButtonOK, null);
		panelMain.add(jButtonCancel, null);
		panelMain.getRootPane().setDefaultButton(jButtonOK);
	}

	public boolean requestForDataModel() {
		buttonOKIsPressed = false;

		// Set values of components //
		jCheckBox.setSelected(frame_.isToShowGridsOnDataModel);
		jRadioButtonColor1.setSelected(frame_.isNormalColorConfigOnDataModel);
		jRadioButtonSizeS.setEnabled(true);
		jRadioButtonSizeM.setEnabled(true);
		jRadioButtonSizeL.setEnabled(true);
		if (frame_.datamodelSize.equals("S")) {
			jRadioButtonSizeS.setSelected(true);
		}
		if (frame_.datamodelSize.equals("M")) {
			jRadioButtonSizeM.setSelected(true);
		}
		if (frame_.datamodelSize.equals("L")) {
			jRadioButtonSizeL.setSelected(true);
		}

		//Setup dialog and show//
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		this.setVisible(true);

		//Update and return parameter value//
		if (buttonOKIsPressed) {
			frame_.isToShowGridsOnDataModel = jCheckBox.isSelected();
			frame_.isNormalColorConfigOnDataModel = jRadioButtonColor1.isSelected();
		}
		if (jRadioButtonSizeS.isSelected()) {
			frame_.datamodelSize = "S";
		}
		if (jRadioButtonSizeM.isSelected()) {
			frame_.datamodelSize = "M";
		}
		if (jRadioButtonSizeL.isSelected()) {
			frame_.datamodelSize = "L";
		}
		return buttonOKIsPressed;
	}

	public boolean requestForSubjectArea() {
		buttonOKIsPressed = false;

		// Set values of components //
		jCheckBox.setSelected(frame_.isToShowGridsOnSubjectArea);
		jRadioButtonColor1.setSelected(frame_.isNormalColorConfigOnSubjectArea);
		jRadioButtonSizeS.setEnabled(false);
		jRadioButtonSizeM.setEnabled(false);
		jRadioButtonSizeL.setEnabled(false);
		jRadioButtonSizeM.setSelected(true);

		//Setup dialog and show//
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		this.setVisible(true);

		//Update and return parameter value//
		if (buttonOKIsPressed) {
			frame_.isToShowGridsOnSubjectArea = jCheckBox.isSelected();
			frame_.isNormalColorConfigOnSubjectArea = jRadioButtonColor1.isSelected();
		}
		return buttonOKIsPressed;
	}

	void jButtonOK_actionPerformed(ActionEvent e) {
		buttonOKIsPressed = true;
		this.setVisible(false);
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		buttonOKIsPressed = false;
		this.setVisible(false);
	}
}

class DialogConfigColors_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
	DialogConfigColors adaptee;

	DialogConfigColors_jButtonOK_actionAdapter(DialogConfigColors adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonOK_actionPerformed(e);
	}
}

class DialogConfigColors_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogConfigColors adaptee;

	DialogConfigColors_jButtonCancel_actionAdapter(DialogConfigColors adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
