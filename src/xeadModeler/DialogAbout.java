package xeadModeler;

/*
 * Copyright (c) 2016 WATANABE kozo <qyf05466@nifty.com>,
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
	public static final String APPLICATION_NAME = "X-TEA Modeler 1.4";
	public static final String PRODUCT_NAME = "X-TEA Modeler";
	public static final String FORMAT_VERSION  = "1.2";
	public static final String COPYRIGHT = "Copyright 2016 DBC Ltd.";
	public static final String URL_DBC = "http://dbc.in.coocan.jp/";
	public static final String FULL_VERSION = "V1.R4.M36";
	// 1.4.36での変更点
	//・業務フロー上のシステム境界のキャプション位置を設定できるようにした
	//
	// 1.4.35での変更点
	//・テーブル名のツリービュー上、およびテーブル一覧上での表示様式を改善した
	//・数値系のデータタイプの桁数表示様式を改善した
	//・「ツール｜DDLの設定」を機能強化した
	//
	// 1.4.34での変更点
	//・定義ファイル間の差異レポート出力の際に「差分DLL」を出力できるようにした
	//
	// 1.4.33での変更点
	//・IOイメージ上でのペースト操作でのバグを修正した
	//・フィールドにデータタイプを設定した場合、フィールドID（エイリアス）が自動設定されるようにした
	//
	// 1.4.32での変更点
	//・Macでファイル名指定用ダイアログの動作が不安定である問題に対応した
	//・仕様書出力でテーブル入出力項目がブランクだと不要な文字が出力されるバグを修正した
	//
	// 1.4.31での変更点
	//・参照ファイルの扱いに関する細かいバグを修正した
	//・各定義の説明に@NOTE@の予約語を置けば、注釈付項目としてツリービュー上で*付きで表示されるようにした
	//・ツールメニューに注釈付項目の一覧出力機能を設けた
	//・TXTファイルからのフィールド定義のインポート処理のバグを修正した
	//
	// 1.4.30での変更点
	//・Macで実行した際のバックアップ処理の問題を修正した
	//・データモデルの拡大縮小に関するバグを修正した
	//
	// 1.4.29での変更点
	//・データモデルを拡大縮小できるようにした
	//・SVG出力の範囲設定のロジックを改善した
	//・Undo/Redo用メニューアイテムの表記を改善した
	//・バックアップ時に指定フォルダを作成するようにした
	//
	// 1.4.28での変更点
	//・テーブル一覧の欄にFLD(フィールド数)を加えた
	//・テーブル入出力定義に「ポジション」の属性を追加した
	//・パネル、スプールの表現をそれぞれフォーム、帳票に変更した
	//
	// 1.4.27での変更点
	//・業務定義のアクションに機能定義をリンクできるようにした
	//
	// 1.4.26での変更点
	//・ファイル間の差異表示のためのオプションをファイルメニューに設けた
	//・propertiesの設定による更新前の自動バックアップ機能を設けた
	//
	// 1.4.25での変更点
	//・フィールドリストのテーブルに全選択用チェックボックスを設けた
	//・テーブル定義と機能定義のペインにスクロールペインを組み込んだ
	//・データモデルの編集中でもインスタンスを表示できるようにした
	//・一覧入力でのテキストファイルからのインポートのメッセージを改善した
	//
	// 1.4.24での変更点
	//・テーブル定義にインデックスを登録できるようにした
	//・インデックス定義にもとづいてDDLが示されるようにした
	//・インポート処理やテーブル定義出力をインデックス定義に対応させた
	//・機能定義の「外部資料」の使い勝手を改善した
	//
	// 1.4.23での変更点
	//・「一覧形式で追加」での「TXT読み込み」のロジックを改善した
	//・スプラッシュの形式を改善した
	//・機能定義に「外部資料」の項目を設けた
	//
	// 1.4.22での変更点
	//・MacOSで操作援助を表示できない問題を修正した
	//・一部のアイコンの背景が透明でなかった問題を修正した
	//・業務フロー上のシステム名の表示形式を改善した
	//・テーブル入出力定義を業務アクションに関連付けすればその概要が入出力イメージとして示されるようにした
	//・テーブルインスタンスの更新ロジックを改善した
	//
	// 1.4.21での変更点
	//・ツール名をX-TEA　Modelerに変更した
	//・テーブル属性として「エイリアス」を追加した
	//・検索・置換対象項目として、テーブルの「エイリアス」と「参照先ファイル」を組み込んだ
	//・テーブルの関連線がいくつも重なった場合ににじんだ印象になる問題を修正した
	//・データモデルの見かけの細かい部分を改善した
	//・テーブル定義毎に「参照元ファイル」を一覧するようにした
	//・業務フロー上でシステム境界が消える可能性がある問題を修正した
	//・「用語とルール」と「保守履歴」のUNDO操作のバグを修正した
	//・説明文の末尾に改行データがあった場合の更新操作のバグを修正した
	//
	// 1.4.20での変更点
	//・スライドショーの最後に最終ページであることを示すパネルを示すようにした
	//・DDLの一覧処理をDDLの設定ダイアログから独立させた
	//・テーブル定義の同期処理を盛り込んだ
	//・テーブル一覧出力に「参照先ファイル」の欄を組み込んだ
	//・走査用ダイアログに「全体」のチェックボックスを置いた
	//
	// 1.4.19での変更点
	//・IOイメージのカーソル位置計算ルーチンの細かいバグを修正した
	//・検索・置換ダイアログでのテキストフィールドにフォーカスした場合、文字列を全選択させるようにした
	//・Java1.8でデータモデルの関連線の位置を変更できなくなっていた問題を修正した
	//・CREATE TABLE文の生成ロジックを改善した
	//
	// 1.4.18での変更点
	//・Java1.8に対応するために、sort処理をcomparatorからcomparableベースに修正した
	//・Ctrl+Rで、IOパネルの書式設定を解除できるようにした
	//・KEY定義の削除にともなうUndo関連処理と変更履歴テキストの出力ロジックのバグを修正した
	//・検索・置換処理でテーブルのIDが処理対象からはずれていたバグを修正した
	//
	// 1.4.17での変更点
	//・IDがブランクであるようなデータタイプやテーブルタイプを含む場合に起こるxeadファイルのインポート処理のバグを修正した
	//・一覧形式での追加でのファイル読込を、CSVからTXT(タブ区切り)に変更するとともに、摘要を取り込めるようにした
	//・一覧形式での追加でのファイル読込で、名前で特定される既存要素のIDや摘要を更新するようにした
	//
	// 1.4.16での変更点
	//・DDL設定ロジックのバグを修正した
	//・別名で保存した場合に「別のユーザによって更新された」のメッセージが示されてしまうバグを修正した
	//・業務フローのフロー追加ダイアログでの、プロセスリストの選択に関するバグを修正した
	//・SQLのインポート処理で、小文字の文字列を認識するようにした
	//・データモデルにグリッドを示すとともに、整列処理でこれに沿うようにした
	//・フィールド属性として「更新不可」のチェックボックスを追加した
	//・データモデルと業務フローについて、２種類の配色パターンから選べるようにした
	//
	// 1.4.15での変更点
	//・ツールメニュー「DDLの設定と出力」のダイアログに「追加パラメータ」と「参照制約の無視」を組み込むとともに、定義ファイルに設定を保存するようにした
	//・排他的サブクラス関係の関連線の描画ロジックを改善した
	//・データモデルのインスタンス編集機能を刷新するとともに、スライドショー機能を組み込んだ
	//
	// 1.4.14での変更点
	//・XEADファイルのインポート機能で、ターゲット要素を新規作成できるようにした
	//・業務フローの変更ダイアログで、双方のプロセスを変更できるようにした
	//・業務フローの変更ダイアログで、スライド順序の変更に後続要素が追随するようにした
	//・用語とルールにHTMLファイルを指定できるようにした
	//・ファイル更新時に他ユーザによる更新をチェックするようにした
	//
	// 1.4.13での変更点
	//・データモデルのSVG出力について、配色設定を改善
	//・業務フローの「プロセス」と「主体・システム」について表示設定を改善
	//・業務フローのフロー追加ダイアログで、職務とリンクされた「主体・システム」の要素名が表示されない問題を修正
	//
	// 1.4.12での変更点
	//・フィールド定義一覧上だけでなく、個々のフィールド定義上でも「モデル表示」を設定できるようにした
	//・業務フローとデータモデルの画像出力について、ベクター形式(SVG)に対応するとともに、それぞれの描画ロジックを微調整した
	//・xeafのキー無しテーブルについて、インポート処理のバグを修正した
	//
	// 1.4.11での変更点
	//・業務フローのインポート処理においてフロータイプが取り込まれなかった問題を修正した
	//・xeafのインポート処理でのフィールドの並び順の設定ミスを修正した
	//
	// 1.4.10での変更点
	//・XEAD Driver用のシステム定義ファイル(*.xeaf)からインポートできるようにした
	//・スライドショーでマウスクリックすると右矢印キーと同様に次ページに進むようにした
	//・スライドショーの表示位置とサイズを親のフレームに合わせるようにした(プロジェクタにうまく表示するための措置）
	//・UIイメージの矩形選択ロジックがフォント可変対応になっていなかった問題を修正した
	//
	// 1.4.9での変更点
	//・文字列走査用ダイアログで「業務フロー」のチェックボックスが隠れていた問題を修正した
	//・職務一覧に「担当業務数」の列を追加した
	//・「用語とルール」のレイアウトを微調整した
	//・「データタイプの整理」のダイアログを閉じた場合のカーソル設定の問題を修正した
	//
	// 1.4.8での変更点
	//・データモデルの汎化関係の描線ロジックを微調整した
	//・編集途中で保管した後でも、変更履歴の追加時には保管以前のアクションを含めて初期設定されるようにした
	//・変更履歴の追加時の初期設定で「用語とルール」が抜けていた問題を修正した
	//
	// 1.4.7での変更点
	//・データモデルの汎化関係において排他性を表現するための描線ロジックを組み込んだ
	//・「用語とルール」の「タイトル」を漢字対応フィールドにした
	//・「設計書出力」と「マトリックスリスト出力」のExcel出力をxlsxの形式に差し替えた
	//
	// 1.4.6での変更点
	//・システムノードに「用語とルール」のタブを設けた
	//・インポート処理のバグによる不整合を修復するためのメニューオプションを組み込んだ
	//
	// 1.4.5での変更点
	//・インスタンス表示をキャンセルしたときテーブルが元の位置に戻らない問題を修正した
	//・データモデルのコンテキストメニューの「テーブル幅の設定」を「テーブルの整列」に統合した
	//・データフロー図に職務をドラッグ＆ドロップして「システム・主体」のノードを作成できるようにした
	//・インポート処理のパフォーマンスを改善するとともに細かいバグを修正した
	//・「データタイプの整理」のツールメニューアイテムを追加した
	//
	// 1.4.4での変更点
	//・フィールド一覧上でモデル表示を設定できるようにするとともに、フィールド定義パネル上から表示設定項目をはずした
	//・パネルイメージ上のカーソルの色を領域の文字色に合わせた
	//・パネルイメージ上で下線を引くためのショートカット(Ctrl+U)を設けた
	//・パネルイメージのフォントサイズをproperties指定での固定にした
	//・インスタンス表示の行高の問題を修正した
	//・起動時にJavaのバージョンをチェックするようにした
	//
	// 1.4.3での変更点
	//・パネル／帳票イメージ上の矩形選択用ガイドがずれていた問題を修正
	//・パネル／帳票イメージの画像ファイル探索フォルダをpropertiesで指定できるようにした
	//・業務定義でパネル／帳票イメージの画像ファイルを表示した場合、一部が隠れることのある問題を修正した
	//propertiesの読み取りロジックを改善した
	//
	// 1.4.2での変更点
	//・業務定義ペイン、フィールド定義ペイン、文字列検索ダイアログについてＸＧＡサイズでも閲覧可能にした
	//・properties可変になっていなかったテーブル一覧ペイン上の一部、およびタスク定義ペイン上の一部のフォントに対応した
	//・入出力イメージのフォントサイズの初期値をpropertiesの可変にした（無指定の場合は16）
	//・業務フロー上で「システム境界」を移動したときにシステム名の一部が消えることのある問題を修正した
	//・パネル／帳票イメージのサイズ変更用ガイドの位置がずれていた問題を修正
	//・パネル／帳票イメージ上のキャレット色を背景色に合わせて変化させるようにした
	//・パネル／帳票イメージのUNDO/REDOが効かなくなっていた問題を修正
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
	private Modeler modeler;

	public DialogAbout(Modeler parent) {
		super(parent);
		modeler = parent;
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

		labelName.setFont(new java.awt.Font(modeler.mainFontName, 1, 20));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setText(PRODUCT_NAME);
		labelName.setBounds(new Rectangle(0, 8, 240, 22));
		labelVersion.setFont(new java.awt.Font(modeler.mainFontName, 0, 16));
		labelVersion.setHorizontalAlignment(SwingConstants.CENTER);
		labelVersion.setText(FULL_VERSION);
		labelVersion.setBounds(new Rectangle(0, 32, 240, 20));
		labelCopyright.setFont(new java.awt.Font(modeler.mainFontName, 0, 16));
		labelCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		labelCopyright.setText(COPYRIGHT);
		labelCopyright.setBounds(new Rectangle(0, 53, 240, 20));
		labelURL.setFont(new java.awt.Font(modeler.mainFontName, 0, 14));
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

		button1.setFont(new java.awt.Font(modeler.mainFontName, 0, 16));
		button1.setText("OK");
		button1.addActionListener(this);
		insetsPanel1.add(button1, null);

		panel1.add(insetsPanel1, BorderLayout.SOUTH);
		panel1.add(panel2, BorderLayout.NORTH);
		panel2.setPreferredSize(new Dimension(350, 100));
		panel2.add(insetsPanel2, BorderLayout.CENTER);
		panel2.add(insetsPanel3, BorderLayout.EAST);

		this.setTitle("About X-TEA Modeler");
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