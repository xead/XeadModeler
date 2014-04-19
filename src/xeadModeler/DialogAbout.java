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
import java.awt.event.*;
import java.net.URI;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

public class DialogAbout extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * Application Information
	 */
	public static final String APPLICATION_NAME  = "XEAD Modeler 1.4";
	public static final String PRODUCT_NAME = "XEAD[zi:d] Modeler";
	public static final String FULL_VERSION  = "V1.R4.M1";
	//
	// 1.4.1での変更点
	//・業務フローとデータモデル上での矩形選択ガイドの色を水色から白に変更した
	//・xeadmdl.propertiesの検索順序を、(1)定義ファイル(*.xead)があるフォルダ、(2)xeadmdl.jarがあるフォルダ、にした
	//
	// 1.4.0での変更点
	//・xeadmdl.propertiesでフォント名を指定できるようにした
	//・一部の要素を除き、フォントサイズを12pから16pに変更した
	//・IOイメージのフォントサイズの指定範囲を10～14から10～20に拡張した
	//・業務フローとデータモデルについて、一部のテキストのフォントサイズを自動調整するようにした
	//・業務フローのイベントについて表示設定様式を改善した
	//・業務フロー上で複数のプロセスやストレージを矩形選択して同時に移動できるようにした
	//・データモデル上のテーブルについて、フィールド群を常に１段で示すようにした（複数段で表示するとインスタンスが書きにくくなるため）
	//・データモデル上の全テーブルの幅を自動設定するためのコンテキストメニューを設けた
	//・データモデル上で複数のテーブルをを矩形選択して同時に移動できるようにした
	//
	// 57での変更点
	//・業務フロー上のストレージとデータフローについて、ダブルクリックすれば記述変更ダイアログを示すようにした
	//
	// 56での変更点
	//・ファイル選択ダイアログの拡張子設定を改善した
	//・データタイプ別のフィールド一覧表示のパフォーマンスを改善した
	//
	// 55での変更点
	//・保守履歴の初期値に同一内容の行が含まれないようにした
	//・保守履歴の日時の区切り文字をドットからアンダーバーに変更した
	//・SQLインポートで『文中のフィールドコメントをフィールド定義名とみなす』をチェックするとＰＫが生成されないバグを修正
	//・フィールド毎のデータタイプの桁数表示形式を改善した
	//・データモデルや業務フロー上のヒント表示が横長にならないようにした
	//
	// 54での変更点
	//・業務フロー上のプロセスをダブルクリックすれば業務定義にジャンプするようにした
	//・データモデル上のテーブルをダブルクリックすればテーブル定義にジャンプするようにした
	//・業務定義上の入出力イメージをダブルクリックすれば機能定義にジャンプするようにした
	//・走査ダイアログでの検索結果の名称にＩＤを含めるようにした
	//・CRUD図等を出力するために、ツールメニューに「マトリックスリスト出力」を追加した
	//・業務定義の入出力イメージ下部のラベルの長さをテキスト長に合わせるようにした
	//・Undoの回数を無制限にするとともに、Undo情報を使って保守履歴の初期値を設定するようにした
	//・業務タイプの組み込みの影響で一部のUndo操作がうまくいかなかったバグを修正した
	//
	// 53での変更点
	//・業務タイプの影響で業務定義の更新ルーチンが異状終了することのあるバグを修正した
	//
	// 52での変更点
	//・業務定義のイベントをブランクにすれば、業務フロー上の「爆発マーク」が表示されないようにした
	//（Github上では1.3.51で反映済）
	//・業務定義中の機能UI表示の左下のキャプションを機能ID＋入出力IDから機能ID＋入出力名に変更した
	//・データモデルや業務フローの印刷出力において、日本語環境ではIDを全角文字に変換して出力するようにした
	//（Java7のGraphics2Dの文字列描画処理のバグにもとづく暫定仕様）
	//・データモデルを印刷した場合、テーブルのCRUD表示の右側が切れる問題を修正
	//・業務定義毎に「この業務を含む業務フロー」をリストしてジャンプできるようにした
	//・業務フロー上でノードをコピーするとラベルの２行目がコピーされない問題を修正
	//・システム変数として「業務タイプ」を追加して、業務定義で選択できるようにした
	//・業務フロー上でデータフローを追加するダイアログで、ノードの２行目をノード名に含めるようにした
	//・業務定義のアクションをコピーする際に「下位アクション」を含めてコピーするかどうかを選択するためのダイアログを表示するようにした
	//・データフローのアイコンの摘要「電気信号」を「情報,データ」に変更した
	//・業務定義のアクションでのＩＯのラベルに機能タイプ名を含めるようにした
	//・フィールド一覧の一覧系処理のレスポンスを大幅に改善した
	//
	// 51での変更点
	//・業務フローをインポートできるようにした
	//・業務フロー上のプロセスの変更ダイアログで、イベント位置が現在値で示されない問題を修正
	//・業務フローの「プロセス」以外のノードで、フローと同様に「ラベル」を２行置けるようにした
	//・業務フローを複写した場合、ノードの２行目のラベルとプロセス・ノードのイベント位置がコピーされない問題を修正
	//・データモデル上でテーブルを非表示にしてもインスタンスが表示されることがある問題を修正
	//・データモデル上でのテーブル関連追加用ダイアログ中の、新規フィールド追加操作における内部ＩＤの設定ロジックのバグを修正した
	//（複数フィールドを追加する際に同じ内部ＩＤを与えるようになっていた）
	//・データモデルのテーブル関連線描画処理において、無効な関連線データが存在する場合に自動的にそれを削除するようにした
	//・データモデルや業務フローの印刷出力において、キャプションの配置に関する仕様を修正した
	//（Java7のGraphics2Dの文字列描画処理のバグにもとづく暫定仕様）
	//
	// 50での変更点
	//・部門、テーブルタイプ、データタイプ、機能タイプ別の定義要素の一覧がID順になるように修正
	//・フィールドIDの入力域の幅を拡張
	//・フィールド一覧上のキーフィールドを太字表示にするとともに、CSV出力で太字を*付に変換するステップを追加
	//・一部のドラッグドロップ対象のコンポーネントで、マウスオーバーするだけでカーソルが変化していた問題を修正
	//・業務フローにノードを追加した場合に前回にノードに対して入力した摘要がクリアされない問題を修正
	//・ツリービュー上で Ctrl+C,Ctrl+V,Ctrl+X を使えるようにするとともに、複数ノードを同時選択できないようにした
	//・右ペインで値を変更直後にCtrl+Sを押しても上書きされなかった問題を修正
	public static final String FORMAT_VERSION  = "1.2";
	public static final String COPYRIGHT = "Copyright 2004-2014 DBC,Ltd.";
	public static final String URL_DBC = "http://homepage2.nifty.com/dbc/";
	/**
	 * Components on the panel
	 */
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel insetsPanel1 = new JPanel();
	private JPanel insetsPanel2 = new JPanel();
	private JPanel insetsPanel3 = new JPanel();
	private JButton button1 = new JButton();
	private JLabel imageLabel = new JLabel();
	private JLabel labelName = new JLabel();
	private JLabel labelVersion = new JLabel();
	private JLabel labelCopyright = new JLabel();
	private JLabel labelURL = new JLabel();
	private ImageIcon imageXead = new ImageIcon();
	private HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
	private Desktop desktop = Desktop.getDesktop();

	public DialogAbout(Modeler parent) {
		super(parent);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit(parent);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Modeler parent) throws Exception  {
		imageXead = new ImageIcon(xeadModeler.Modeler.class.getResource("title.png"));
		imageLabel.setIcon(imageXead);
		panel1.setLayout(new BorderLayout());
		panel1.setBorder(BorderFactory.createEtchedBorder());
		panel2.setLayout(new BorderLayout());
		insetsPanel2.setLayout(new BorderLayout());
		insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		insetsPanel2.setPreferredSize(new Dimension(75, 52));
		insetsPanel2.add(imageLabel, BorderLayout.EAST);

		labelName.setFont(new java.awt.Font("Serif", 1, 20));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setText(PRODUCT_NAME);
		labelName.setBounds(new Rectangle(0, 8, 240, 22));
		labelVersion.setFont(new java.awt.Font("Dialog", 0, 16));
		labelVersion.setHorizontalAlignment(SwingConstants.CENTER);
		labelVersion.setText(FULL_VERSION);
		labelVersion.setBounds(new Rectangle(0, 32, 240, 20));
		labelCopyright.setFont(new java.awt.Font("Dialog", 0, 16));
		labelCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		labelCopyright.setText(COPYRIGHT);
		labelCopyright.setBounds(new Rectangle(0, 53, 240, 20));
		labelURL.setFont(new java.awt.Font("Dialog", 0, 16));
		labelURL.setHorizontalAlignment(SwingConstants.CENTER);
		labelURL.setText("<html><u><font color='blue'>" + URL_DBC);
		labelURL.setBounds(new Rectangle(0, 75, 240, 20));
		labelURL.addMouseListener(new DialogAbout_labelURL_mouseAdapter(this));
		insetsPanel3.setLayout(null);
		insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
		insetsPanel3.setPreferredSize(new Dimension(250, 80));
		insetsPanel3.add(labelName, null);
		insetsPanel3.add(labelVersion, null);
		insetsPanel3.add(labelCopyright, null);
		insetsPanel3.add(labelURL, null);

		button1.setText("OK");
		button1.addActionListener(this);
		insetsPanel1.add(button1, null);

		panel1.add(insetsPanel1, BorderLayout.SOUTH);
		panel1.add(panel2, BorderLayout.NORTH);
		panel2.setPreferredSize(new Dimension(350, 100));
		panel2.add(insetsPanel2, BorderLayout.CENTER);
		panel2.add(insetsPanel3, BorderLayout.EAST);

		this.setTitle("About XEAD Modeler");
		this.getContentPane().add(panel1, null);
		this.setResizable(false);
	}

	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			cancel();
		}
		super.processWindowEvent(e);
	}

	void cancel() {
		dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			cancel();
		}
	}

	void labelURL_mouseClicked(MouseEvent e) {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			desktop.browse(new URI(URL_DBC));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "The Site is inaccessible.");
		} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	void labelURL_mouseEntered(MouseEvent e) {
		setCursor(htmlEditorKit.getLinkCursor());
	}

	void labelURL_mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}

class DialogAbout_labelURL_mouseAdapter extends java.awt.event.MouseAdapter {
	DialogAbout adaptee;
	DialogAbout_labelURL_mouseAdapter(DialogAbout adaptee) {
		this.adaptee = adaptee;
	}
	public void mouseClicked(MouseEvent e) {
		adaptee.labelURL_mouseClicked(e);
	}
	public void mouseEntered(MouseEvent e) {
		adaptee.labelURL_mouseEntered(e);
	}
	public void mouseExited(MouseEvent e) {
		adaptee.labelURL_mouseExited(e);
	}
}