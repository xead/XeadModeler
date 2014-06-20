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

public class Res_ja extends java.util.ListResourceBundle {
	private static final Object[][] contents = new String[][]{
		{ "DialogAddList01", "CSV読込" },
		{ "DialogAddList02", "取消" },
		{ "DialogAddList03", "「サブジェクトエリア」を一覧形式で追加する" },
		{ "DialogAddList04", "サブジェクトエリア名" },
		{ "DialogAddList05", "「職務」を一覧形式で追加する" },
		{ "DialogAddList06", "職務名" },
		{ "DialogAddList07", "「業務」を一覧形式で追加する" },
		{ "DialogAddList08", "業務名" },
		{ "DialogAddList09", "「サブシステム」を一覧形式で追加する" },
		{ "DialogAddList10", "サブシステム名" },
		{ "DialogAddList11", "「テーブル」を一覧形式で追加する" },
		{ "DialogAddList12", "テーブル名" },
		{ "DialogAddList13", "「フィールド」を一覧形式で追加する" },
		{ "DialogAddList14", "フィールド名" },
		{ "DialogAddList15", "「機能」を一覧形式で追加する" },
		{ "DialogAddList16", "機能名" },
		{ "DialogAddRelationshipOnDatamodel1", "テーブル関連の追加確認" },
		{ "DialogAddRelationshipOnDatamodel2", "新たなテーブル関連を追加します。関連を構成するために必要なフィールドをターゲットテーブルに追加するかどうかを指定してください。複数フィールドが関わる場合は、名称やIDをセミコロン(;)で区切ってください。" },
		{ "DialogAddRelationshipOnDatamodel3", "新たなフィールドとしてコピペして外部キー(FK)を構成する" },
		{ "DialogAddRelationshipOnDatamodel4", "ターゲット上の既存フィールドでテーブル関連を構成する" },
		{ "DialogAddRelationshipOnDatamodel5", "新フィールド名" },
		{ "DialogAddRelationshipOnDatamodel6", "新ID(alias)" },
		{ "DialogAddRelationshipOnDatamodel7", "取消" },
		{ "DialogAddRelationshipOnDatamodel8", "新名称、新IDの数が選択されたキーのフィールド数と一致しません。" },
		{ "DialogAddRelationshipOnDatamodel9", "ターゲット上の既存フィールドの中から該当を絞り込めませんでした。個別にテーブル定義を開いてキー定義／関連定義を追加してください。" },
		{ "DialogAddRelationshipOnDatamode20", "追加されるフィールドの名前かＩＤが、ターゲットテーブルの既存フィールドと重複します。" },
		{ "DialogCreateTableStatement01", "CREATE TABLE文の出力と設定" },
		{ "DialogCreateTableStatement02", "出力ファイル名" },
		{ "DialogCreateTableStatement03", "テーブル名設定" },
		{ "DialogCreateTableStatement04", "テーブル名をそのまま用いる" },
		{ "DialogCreateTableStatement05", "テーブルＩＤをテーブル名とする" },
		{ "DialogCreateTableStatement06", "コメント行文字" },
		{ "DialogCreateTableStatement07", "セクション区切文字" },
		{ "DialogCreateTableStatement08", "無条件のテーブル関連定義のみがキー制約として解釈される点に注意してください。条件付きのテーブル関連については、データ処理上の制約として組み込まれる必要があります。" },
		{ "DialogCreateTableStatement09", "出力" },
		{ "DialogCreateTableStatement10", "設定" },
		{ "DialogCreateTableStatement11", "エイリアス指定フィールドについてCOMMENT出力" },
		{ "DialogDataflowLine01", "ノード１" },
		{ "DialogDataflowLine02", "矢印の向き" },
		{ "DialogDataflowLine06", "情報,データ" },
		{ "DialogDataflowLine07", "単伝票" },
		{ "DialogDataflowLine08", "複伝票" },
		{ "DialogDataflowLine09", "箱" },
		{ "DialogDataflowLine10", "箱,単伝票" },
		{ "DialogDataflowLine11", "箱,複伝票" },
		{ "DialogDataflowLine12", "平箱" },
		{ "DialogDataflowLine13", "平箱,単伝票" },
		{ "DialogDataflowLine14", "平箱,複伝票" },
		{ "DialogDataflowLine15", "角材" },
		{ "DialogDataflowLine16", "角材,単伝票" },
		{ "DialogDataflowLine17", "角材,複伝票" },
		{ "DialogDataflowLine18", "円柱" },
		{ "DialogDataflowLine19", "円柱,単伝票" },
		{ "DialogDataflowLine20", "円柱,複伝票" },
		{ "DialogDataflowLine21", "人" },
		{ "DialogDataflowLine22", "人,単伝票" },
		{ "DialogDataflowLine23", "人,複伝票" },
		{ "DialogDataflowLine24", "人,平箱" },
		{ "DialogDataflowLine25", "人,平箱,単伝票" },
		{ "DialogDataflowLine26", "取消" },
		{ "DialogDataflowLine27", "フロー名" },
		{ "DialogDataflowLine28", "フローのアイコン" },
		{ "DialogDataflowLine29", "ノード２" },
		{ "DialogDataflowLine30", "スライド順序" },
		{ "DialogDataflowLine31", "（２行目）" },
		{ "DialogDataflowLine32", "フローの追加" },
		{ "DialogDataflowLine33", "フローの変更" },
		{ "DialogDataflowNode01", "ラベル" },
		{ "DialogDataflowNode02", "タイプ" },
		{ "DialogDataflowNode03", "台帳" },
		{ "DialogDataflowNode04", "置き場（棚）" },
		{ "DialogDataflowNode05", "置き場（床）" },
		{ "DialogDataflowNode06", "トレー" },
		{ "DialogDataflowNode07", "書類入れ" },
		{ "DialogDataflowNode08", "主体・システム" },
		{ "DialogDataflowNode09", "キャッシャー" },
		{ "DialogDataflowNode10", "金庫" },
		{ "DialogDataflowNode11", "待合室" },
		{ "DialogDataflowNode12", "スライド順序" },
		{ "DialogDataflowNode13", "取消" },
		{ "DialogDataflowNode14", "プロセス" },
		{ "DialogDataflowNode15", "ノードの追加" },
		{ "DialogDataflowNode16", "ノードの変更" },
		{ "DialogDataflowNode17", "プロセスの変更" },
		{ "DialogDataflowNode18", "バスケット" },
		{ "DialogDataflowNode19", "ドラム" },
		{ "DialogDataflowNode20", "説明" },
		{ "DialogDataflowNode21", "イベント位置" },
		{ "DialogDataflowNode22", "左" },
		{ "DialogDataflowNode23", "右" },
		{ "DialogDataflowNode24", "（２行目）" },
		{ "DialogDocuments01", "サブシステム" },
		{ "DialogDocuments02", "設計書タイプ" },
		{ "DialogDocuments03", "テーブル設計書" },
		{ "DialogDocuments04", "機能設計書" },
		{ "DialogDocuments05", "IDと名称の組み合わせでシート名が設定されます。シート名が重複する定義要素については出力されません。また、ある種の記号(*,:,?,[,],/ 半角も全角も)をシート名に含めると、ファイルを開いたときに警告メッセージが示される点にも注意してください。" },
		{ "DialogDocuments06", "なお、機能設計書において、ＸＥＡＤ上で登録されたパネルやスプールの背景色指定は無視されます（規定名の画像ファイルが存在すれば、自動的にシートに貼り付けられます）。" },
		{ "DialogDocuments07", "出力" },
		{ "DialogDocuments08", "閉じる" },
		{ "DialogDocuments09", "設計書出力" },
		{ "DialogDocuments10", "＊選択してください" },
		{ "DialogDocuments11", "個の定義要素について、次のファイル名で設計書が出力されました。" },
		{ "DialogDocuments12", "個の定義要素についてはシート出力されませんでした。同名の定義要素によってシート名が重複した可能性があります。" },
		{ "DialogDocuments13", "ＭＳ Ｐゴシック" },
		{ "DialogDocuments14", "ＭＳ Ｐ明朝" },
		{ "DialogDocuments15", "ＭＳ ゴシック" },
		{ "DialogDocuments16", "機能設計書" },
		{ "DialogDocuments17", "サブシステム名" },
		{ "DialogDocuments18", "機能名" },
		{ "DialogDocuments19", "機能タイプ" },
		{ "DialogDocuments20", "概要" },
		{ "DialogDocuments21", "パラメータ" },
		{ "DialogDocuments22", "返り値" },
		{ "DialogDocuments23", "説明" },
		{ "DialogDocuments24", "入出力一覧" },
		{ "DialogDocuments25", "入出力名" },
		{ "DialogDocuments26", "タイプ" },
		{ "DialogDocuments27", "パネル" },
		{ "DialogDocuments28", "印刷" },
		{ "DialogDocuments29", "ページ" },
		{ "DialogDocuments30", "この機能が利用する機能" },
		{ "DialogDocuments31", "起動イベント" },
		{ "DialogDocuments32", "この機能を利用する機能" },
		{ "DialogDocuments33", "この機能を直接利用する業務" },
		{ "DialogDocuments34", "職務名" },
		{ "DialogDocuments35", "業務名" },
		{ "DialogDocuments36", "アクション" },
		{ "DialogDocuments37", "入出力定義" },
		{ "DialogDocuments38", "入出力タイプ" },
		{ "DialogDocuments39", "入出力イメージ" },
		{ "DialogDocuments40a", "" },
		{ "DialogDocuments40b", " を参照してください。" },
		{ "DialogDocuments41", "ラベル" },
		{ "DialogDocuments42", "項目名" },
		{ "DialogDocuments43", "項目タイプ" },
		{ "DialogDocuments44", "任意入力項目" },
		{ "DialogDocuments45", "必須入力項目" },
		{ "DialogDocuments46", "表示項目" },
		{ "DialogDocuments47", "ＧＵＩ部品" },
		{ "DialogDocuments48", "フィールド" },
		{ "DialogDocuments49", "データタイプ" },
		{ "DialogDocuments50", "フィールドに対する操作" },
		{ "DialogDocuments51", "テーブル設計書" },
		{ "DialogDocuments52", "テーブル名" },
		{ "DialogDocuments53", "テーブルタイプ" },
		{ "DialogDocuments54", "フィールド一覧" },
		{ "DialogDocuments55", "フィールド名" },
		{ "DialogDocuments56", "タイプ" },
		{ "DialogDocuments57", "省略値" },
		{ "DialogDocuments58", "不可" },
		{ "DialogDocuments59", "可" },
		{ "DialogDocuments60", "キー定義一覧" },
		{ "DialogDocuments61", "キータイプ" },
		{ "DialogDocuments62", "フィールド構成" },
		{ "DialogDocuments63", "一次識別子" },
		{ "DialogDocuments64", "外部キー" },
		{ "DialogDocuments65", "二次識別子" },
		{ "DialogDocuments66", "利用機能一覧" },
		{ "DialogDocuments67", "キー定義" },
		{ "DialogDocuments68", "関連名" },
		{ "DialogDocuments69", "関連テーブル名" },
		{ "DialogDocuments70", "関連キー定義" },
		{ "DialogDocuments71", "親子関係" },
		{ "DialogDocuments72", "参照関係" },
		{ "DialogDocuments73", "派生関係" },
		{ "DialogScan01", "文字列の走査・置換" },
		{ "DialogScan02", "走査文字列" },
		{ "DialogScan03", "置換文字列" },
		{ "DialogScan04", "大文字小文字を区別する" },
		{ "DialogScan05", "走査範囲" },
		{ "DialogScan06", "システム記述" },
		{ "DialogScan07", "部門定義" },
		{ "DialogScan08", "テーブルタイプ" },
		{ "DialogScan09", "データタイプ" },
		{ "DialogScan10", "機能タイプ" },
		{ "DialogScan11", "保守履歴" },
		{ "DialogScan12", "業務フロー" },
		{ "DialogScan13", "職務定義" },
		{ "DialogScan14", "業務定義" },
		{ "DialogScan15", "サブシステム記述" },
		{ "DialogScan16", "テーブル定義" },
		{ "DialogScan17", "機能定義" },
		{ "DialogScan18", "走査開始" },
		{ "DialogScan19", "選" },
		{ "DialogScan20", "定義要素区分" },
		{ "DialogScan21", "定義要素名" },
		{ "DialogScan22", "属性名" },
		{ "DialogScan23", "属性値" },
		{ "DialogScan24", "Hit" },
		{ "DialogScan25", "閉じる" },
		{ "DialogScan26", "業務タイプ" },
		{ "DialogScan28", "選択行について一括置換" },
		{ "DialogScan29", "一覧出力" },
		{ "DialogScan30", "（すべての職務）" },
		{ "DialogScan31", "（すべてのサブシステム）" },
		{ "DialogScan32", "個の定義要素の、" },
		{ "DialogScan33", "箇所で指定文字列が見つかりました。行を選択すれば属性値が示されます。" },
		{ "DialogScan34", "指定文字列を含む定義要素は見つかりませんでした。" },
		{ "DialogScan35", "システム記述" },
		{ "DialogScan36", "部門定義" },
		{ "DialogScan37", "テーブルタイプ" },
		{ "DialogScan38", "データタイプ" },
		{ "DialogScan39", "機能タイプ" },
		{ "DialogScan40", "保守履歴" },
		{ "DialogScan41", "業務フロー" },
		{ "DialogScan42", "業務フロー／プロセス" },
		{ "DialogScan43", "業務フロー／フロー" },
		{ "DialogScan44", "職務定義" },
		{ "DialogScan45", "業務定義" },
		{ "DialogScan46", "業務定義／手順" },
		{ "DialogScan47", "業務定義／手順／ＵＩ" },
		{ "DialogScan48", "サブシステム記述" },
		{ "DialogScan49", "サブシステム記述／モデル" },
		{ "DialogScan50", "テーブル定義" },
		{ "DialogScan51", "テーブル定義／フィールド" },
		{ "DialogScan52", "機能定義" },
		{ "DialogScan53", "機能定義／起動機能" },
		{ "DialogScan54", "機能定義／パネル" },
		{ "DialogScan55", "機能定義／パネル項目" },
		{ "DialogScan56", "機能定義／ＷＥＢページ" },
		{ "DialogScan57", "機能定義／ＷＥＢページ項目" },
		{ "DialogScan58", "機能定義／スプール" },
		{ "DialogScan59", "機能定義／スプール項目" },
		{ "DialogScan60", "機能定義／テーブル" },
		{ "DialogScan61", "機能定義／テーブルフィールド" },
		{ "DialogScan62", "ＩＤ(一覧順)" },
		{ "DialogScan63", "名称" },
		{ "DialogScan64", "拡張名" },
		{ "DialogScan65", "実行条件" },
		{ "DialogScan66", "ラベル" },
		{ "DialogScan67", "インスタンス" },
		{ "DialogScan68", "履歴名" },
		{ "DialogScan69", "説明" },
		{ "DialogScan70", "イベント" },
		{ "DialogScan71", "別名" },
		{ "DialogScan72", "省略値" },
		{ "DialogScan73", "参照制約" },
		{ "DialogScan74", "起動イベント" },
		{ "DialogScan75", "操作方法" },
		{ "DialogScan76", "概要" },
		{ "DialogScan77", "引数" },
		{ "DialogScan78", "返り値" },
		{ "DialogScan79", "入出力イメージ" },
		{ "DialogScan80", "テーブル関連" },
		{ "DialogScan81", "個の定義要素の、" },
		{ "DialogScan82", "箇所で指定文字列が置換されました。" },
		{ "DialogScan83", "モデルの説明" },
		{ "DialogImportXEAD01", "ＸＥＡＤ定義要素のインポート" },
		{ "DialogImportXEAD02", "インポート元ファイル" },
		{ "DialogImportXEAD03", "インポート元システム" },
		{ "DialogImportXEAD04", "対象定義要素" },
		{ "DialogImportXEAD05", "テーブル定義と機能定義" },
		{ "DialogImportXEAD06", "業務定義" },
		{ "DialogImportXEAD07", "インポート" },
		{ "DialogImportXEAD08", "閉じる" },
		{ "DialogImportXEAD09", "インポート元サブシステム" },
		{ "DialogImportXEAD10", "インポート先サブシステム" },
		{ "DialogImportXEAD11", "インポート元サブシステムに含まれるテーブル定義と機能定義とがインポート先サブシステムにコピーされます。ＩＤ（一覧順）で対象が識別され、インポート先に対象が存在すれば更新、存在しなければ新規追加されます。したがって、インポート元サブシステムでもインポート先サブシステムでも同タイプ定義のＩＤが重複して存在する場合、予期しない更新が起こる可能性があります（処理後の確認ダイアログで「名前を変えて保存」を選択することをお勧めします）。他にもいくつかの制約があるので、事前にヘルプで確認しておいてください。" },
		{ "DialogImportXEAD12", "（選択してください）" },
		{ "DialogImportXEAD13", "インポート元職務" },
		{ "DialogImportXEAD14", "インポート先職務" },
		{ "DialogImportXEAD15", "インポート元職務に含まれる業務定義がインポート先職務にコピーされます。ＩＤ（一覧順）で対象が識別され、インポート先に対象が存在すれば更新、存在しなければ新規追加されます。したがって、インポート元職務でもインポート先職務でも業務定義のＩＤが重複して存在する場合、予期しない更新が起こる可能性があります（処理後の確認ダイアログで「名前を変えて保存」を選択することをお勧めします）。他にもいくつかの制約があるので、事前にヘルプで確認しておいてください。" },
		{ "DialogImportXEAD16", "テーブル定義として、" },
		{ "DialogImportXEAD17", "個が更新され、" },
		{ "DialogImportXEAD18", "個が追加され、" },
		{ "DialogImportXEAD19", "機能定義として、" },
		{ "DialogImportXEAD20", "機能別のテーブル入出力定義として特定できなかったテーブル定義が" },
		{ "DialogImportXEAD21", "個、" },
		{ "DialogImportXEAD22", "起動される機能として特定できなかった機能定義が" },
		{ "DialogImportXEAD23", "個見つかりました。" },
		{ "DialogImportXEAD24", "業務定義として" },
		{ "DialogImportXEAD25", "業務手順内で用いられるパネル／スプールとして特定できなかった入出力定義が" },
		{ "DialogImportXEAD26", "個見つかりました。" },
		{ "DialogImportXEAD27", "インポート先に存在しないキー定義にもとづくテーブル関連定義が" },
		{ "DialogImportXEAD28", "個見つかり、これらはインポートされませんでした。" },
		{ "DialogImportXEAD29", "詳細については、" },
		{ "DialogImportXEAD30", "の名前で出力されたログファイルで確認してください。" },
		{ "DialogImportXEAD31", "インポート先ファイル" },
		{ "DialogImportXEAD32", "インポート先システム" },
		{ "DialogImportXEAD33", "インポート先の既存定義が更新されました。" },
		{ "DialogImportXEAD34", "インポート先に新規定義として追加されました。" },
		{ "DialogImportXEAD35", "が関係するテーブル関連定義に必要なキー定義の一方が、インポート先システムに見つからなかったために、インポートされませんでした。" },
		{ "DialogImportXEAD36", "が関係するテーブル関連定義がインポート先に新規追加されました。" },
		{ "DialogImportXEAD37", "のテーブル定義がインポート先に存在しないため、上記機能のテーブル入出力情報としてインポートされませんでした。" },
		{ "DialogImportXEAD38", "の機能定義がインポート先に存在しないため、" },
		{ "DialogImportXEAD39", "からの呼出情報としてインポートされませんでした。" },
		{ "DialogImportXEAD40", "のパネル／スプール定義がインポート先に存在しないため、上記業務のパネル／スプール操作情報としてインポートされませんでした。" },
		{ "DialogImportXEAD41", "（異なる名前でインポート結果が保存された可能性があります）" },
		{ "DialogImportXEAD42", "が外部テーブルとしてサブシステムに利用されていますがその定義がインポート先のシステムに存在しないため、外部テーブルの利用に関する情報はインポートされませんでした。" },
		{ "DialogImportXEAD43", "テーブル関連を伴っているため、一次識別子のフィールド構成の変更は受け入れられませんでした。" },
		{ "DialogImportXEAD44", "機能定義" },
		{ "DialogImportXEAD45", "インポート元サブシステムに含まれる機能定義がインポート先サブシステムにコピーされます。ＩＤ（一覧順）で対象が識別され、インポート先に対象が存在すれば更新、存在しなければ新規追加されます。したがって、インポート元サブシステムでもインポート先サブシステムでも同タイプ定義のＩＤが重複して存在する場合、予期しない更新が起こる可能性があります（処理後の確認ダイアログで「名前を変えて保存」を選択することをお勧めします）。他にもいくつかの制約があるので、事前にヘルプで確認しておいてください。" },
		{ "DialogImportXEAD46", "テーブル定義" },
		{ "DialogImportXEAD47", "インポート元サブシステムに含まれるテーブル定義がインポート先サブシステムにコピーされます。ＩＤ（一覧順）で対象が識別され、インポート先に対象が存在すれば更新、存在しなければ新規追加されます。したがって、インポート元サブシステムでもインポート先サブシステムでも同タイプ定義のＩＤが重複して存在する場合、予期しない更新が起こる可能性があります（処理後の確認ダイアログで「名前を変えて保存」を選択することをお勧めします）。他にもいくつかの制約があるので、事前にヘルプで確認しておいてください。" },
		{ "DialogImportXEAD48", "インポートされませんでした。インポート先において対応する定義が、指定のサブシステム以外に所属しているためです。" },
		{ "DialogImportXEAD49", "インポートされませんでした。インポート先において対応する定義が、指定の職務以外に所属しているためです。" },
		{ "DialogImportXEAD50", "個がインポートの対象から除外されました。" },
		{ "DialogImportXEAD51", "インポート元の業務フロー上の定義要素がインポート先の業務フロー定義にコピーされます。業務フロー上の業務定義（プロセス）がインポート先に存在しない場合、処理はキャンセルされます。インポート元やインポート先で業務定義のＩＤ（一覧順）が重複して存在する場合、予期しない更新が起こる可能性があります（処理後の確認ダイアログで「名前を変えて保存」を選択することをお勧めします）。他にもいくつかの制約があるので、事前にヘルプで確認しておいてください。" },
		{ "DialogImportXEAD52", "インポート元業務フロー" },
		{ "DialogImportXEAD53", "インポート先業務フロー" },
		{ "DialogImportXEAD54", "対応する業務定義（プロセス）がインポート先に存在しないため、業務フローのインポート処理は中止されました。" },
		{ "DialogImportXEAD55", "業務フローが正常にインポートされました。" },
		{ "DialogImportXEAD56", "業務フローのインポート処理はキャンセルされました。" },
		{ "DialogImportSQL01", "CreateTable文のインポート" },
		{ "DialogImportSQL02", "インポート元ファイル" },
		{ "DialogImportSQL03", "インポート先サブシステム" },
		{ "DialogImportSQL04", "R登録日時,R更新日時,R登録ユーザ,R更新ユーザ,削除フラグ" },
		{ "DialogImportSQL05", "インポート" },
		{ "DialogImportSQL06", "閉じる" },
		{ "DialogImportSQL07", "（選択してください）" },
		{ "DialogImportSQL08", "個のテーブル定義と" },
		{ "DialogImportSQL09", "個のテーブル関連定義とがインポートされました。" },
		{ "DialogImportSQL10", "データ型にもとづいてデータタイプを設定する" },
		{ "DialogImportSQL11", "以下の名称のフィールドをデータモデル上非表示とする" },
		{ "DialogImportSQL12", "テーブルＩＤをインポート順にもとづいて自動設定する" },
		{ "DialogImportSQL13", "文中のフィールド名をフィールド定義ID(Alias)とみなす" },
		{ "DialogImportSQL14", "文中のフィールドコメントをフィールド定義名とみなす" },
		{ "S1", "指定ファイルのフォーマットバージョンは " },
		{ "S2", " ですが、このバージョンのXEAD Modeler\nが扱えるのは " },
		{ "S3", " までです。最新のXEAD Modelerをご利用ください。" },
		{ "S4", "テーブル入出力定義に内部的な不整合が検出されました。これは旧版のインポート機能の不具合によるもので、いったん保存することで不整合は修復されます。" },
		{ "S10", "を追加" },
		{ "S11", "を変更" },
		{ "S12", "を削除" },
		{ "S49", "システム名" },
		{ "S50", "ファイル" },
		{ "S51", "新規" },
		{ "S52", "開く" },
		{ "S53", "終了" },
		{ "S54", "用紙設定" },
		{ "S55", "印刷" },
		{ "S56", "保存" },
		{ "S57", "別名で保存" },
		{ "S58", "編集" },
		{ "S59", "ノードに対する変更を取り消す" },
		{ "S60", "ノードに対する変更をやり直す" },
		{ "S61", "検索" },
		{ "S62", "文字列の走査・置換" },
		{ "S63", "しおりの設定・解除" },
		{ "S64", "インポート" },
		{ "S65", "モデル情報ファイル(*.xead)より" },
		{ "S66", "CreateTable文(*.sql;txt)より" },
		{ "S73", "しおりへジャンプ" },
		{ "S74", "マトリックスリスト出力" },
		{ "S75", "業務フロー＆業務" },
		{ "S76", "業務フロー＆機能" },
		{ "S77", "業務＆機能" },
		{ "S78", "業務＆テーブル" },
		{ "S79", "テーブル＆機能" },
		{ "S80", "○" },
		{ "S81", "次のファイル名でマトリックスリストが出力されました。" },
		{ "S82", "設計書出力" },
		{ "S83", "ツール" },
		{ "S84", "業務一覧出力" },
		{ "S85", "テーブル一覧出力" },
		{ "S86", "機能一覧出力" },
		{ "S87", "CreateTable文出力" },
		//{ "S88", "業務プロトコル出力" },
		{ "S89", "指定ＵＲＬへジャンプ" },
		{ "S90", "ヘルプ" },
		{ "S91", "概要と目次" },
		{ "S92", "バージョン情報" },
		{ "S94", "ファイル(F)" },
		{ "S95", "編集(E)" },
		{ "S96", "検索(S)" },
		{ "S97", "ツール(T)" },
		{ "S98", "ヘルプ(H)" },
		{ "S99", "フィールド一覧出力" },
		{ "S100", "下位要素を追加" },
		{ "S101", "一覧形式で追加" },
		{ "S102", "下位要素を貼り付け" },
		{ "S103", "コピー" },
		{ "S104", "切り取り" },
		{ "S105", "削除" },
		{ "S106", "下位要素を追加" },
		{ "S107", "パネル" },
		{ "S108", "スプール" },
		{ "S109", "WEBページ" },
		{ "S110", "外部キー" },
		{ "S111", "二次識別子" },
		{ "S132", "フォームの文字サイズを変える" },
		{ "S133", "フォームのサイズを変える" },
//		{ "S134", "小" },
//		{ "S135", "中" },
//		{ "S136", "大" },
		{ "S137", "フォームの背景色を変える" },
		{ "S150", "選択領域の背景色を変える" },
		{ "S163", "選択領域の文字色を変える" },
		{ "S176", "元に戻す" },
		{ "S177", "やり直し" },
		{ "S178", "切り取り" },
		{ "S179", "コピー" },
		{ "S180", "貼り付け" },
		{ "S181", "削除" },
		{ "S182", "すべて選択" },
		{ "S183", "選択領域の文字に下線をひく" },
		{ "S184", "選択領域の書式をリセットする" },
		{ "S185", "イメージ出力" },
		{ "S186", "選択領域の下線をはずす" },
		{ "S187", "矩形選択" },
		{ "S189", "システム名" },
		{ "S190", "イメージファイル探索" },
		{ "S192", "バージョン" },
		{ "S195", "システムの説明" },
		{ "S196", "部門一覧" },
		{ "S198", "部門名" },
		{ "S201", "ID(一覧順)" },
		{ "S204", "説明" },
		{ "S206", "テーブルタイプ一覧" },
		{ "S208", "テーブルタイプ名" },
		{ "S209", "業務タイプ一覧" },
		{ "S210", "業務タイプ名" },
		{ "S216", "データタイプ一覧" },
		{ "S218", "データタイプ名" },
		{ "S223", "ＳＱＬ表現" },
		{ "S227", "基本データ型" },
		{ "S237", "桁数" },
		{ "S240", "小数桁" },
		{ "S242", "機能タイプ一覧" },
		{ "S244", "機能タイプ名" },
		{ "S252", "保守履歴" },
		{ "S254", "バージョン等" },
		{ "S257", "改版日時(一覧順)" },
		{ "S260", "変更内容" },
		{ "S265", "部門名" },
		{ "S271", "関連担当名" },
		{ "S277", "テーブルタイプ名" },
		{ "S283", "関連テーブル名" },
		{ "S284", "所属サブシステム名" },
		{ "S289", "データタイプ名" },
		{ "S290", "基本データ型" },
		{ "S291", "桁数" },
		{ "S292", "小数桁" },
		{ "S293", "ＳＱＬ表現" },
		{ "S297", "ID(alias)" },
		{ "S298", "関連フィールド名" },
		{ "S299", "所属テーブル名" },
		{ "S300", "所属サブシステム名" },
		{ "S305", "機能タイプ名" },
		{ "S311", "関連機能名" },
		{ "S312", "所属サブシステム名" },
		{ "S313", "引数" },
		{ "S317", "改版日時" },
		{ "S318", "バージョン等" },
		{ "S319", "変更内容" },
		{ "S324", "業務フロー名" },
		{ "S339", "職務定義" },
		{ "S340", "職務名" },
		{ "S341", "部門" },
		{ "S348", "職務名" },
		{ "S356", "部門" },
		{ "S359", "業務定義" },
		{ "S360", "担当業務名" },
		{ "S361", "イベント" },
		{ "S362", "業務タイプ" },
		{ "S363", "関連業務名" },
		{ "S368", "この業務が開始されるべき「事態」を表します" },
		{ "S372", "担当業務名" },
		{ "S375", "プロトコル" },
		{ "S380", "この業務を含む業務フロー" },
		{ "S390", "注釈" },
		{ "S391", "実行条件" },
		{ "S392", "必要ならば、このアクションが"+"\n"+"実行されるべき条件を指定してください" },
		{ "S394", "アクション" },
		{ "S400", "操作方法" },
		{ "S403", "入出力イメージの説明" },
		{ "S407", "サブシステム一覧" },
		{ "S408", "機能階層ビュー" },
		{ "S411", "起動イベント" },
		{ "S413", "サブシステム" },
		{ "S415", "機能名" },
		{ "S417", "機能タイプ" },
		{ "S419", "概要" },
		{ "S421", "引数／返り値" },
		{ "S436", "サブシステム名" },
		{ "S437", "表" },
		{ "S438", "機" },
		{ "S439", "外C" },
		{ "S440", "外R" },
		{ "S441", "外U" },
		{ "S442", "外D" },
		{ "S443", "外機" },
		{ "S448", "テーブルID" },
		{ "S449", "テーブル入出力名" },
		{ "S464", "テーブル一覧" },
		{ "S465", "外部テーブル一覧" },
		{ "S466", "データモデル" },
		{ "S467", "モデルの説明" },
		{ "S471", "テーブル名" },
		{ "S472", "モデル" },
		{ "S477", "テーブルタイプ" },
		{ "S478", "一次識別子" },
		{ "S479", "外C" },
		{ "S480", "外R" },
		{ "S481", "外U" },
		{ "S482", "外D" },
		{ "S483", "表示" },
		{ "S490", "モデル" },
		{ "S495", "テーブルタイプ" },
		{ "S496", "一次識別子" },
		{ "S502", "タイプ" },
		{ "S508", "一次識別子" },
		{ "S518", "CREATE文" },
		{ "S519", "内部利用機能一覧" },
		{ "S520", "外部利用機能一覧" },
		{ "S523", "機能名" },
		{ "S532", "機能名" },
		{ "S540", "一覧順" },
		{ "S542", "フィールド名" },
		{ "S543", "属性区分" },
		{ "S544", "データタイプ" },
		{ "S546", "省略値" },
		{ "S547", "モデル" },
		{ "S550", "新規" },
		{ "S554", "属性区分" },
		{ "S556", "タイプ" },
		{ "S558", "固有属性" },
		{ "S559", "通常の属性項目であることを示します" },
		{ "S561", "継承属性" },
		{ "S562", "親テーブル、または参照先テーブル上の項目と連動していることを示します" },
		{ "S564", "導出属性" },
		{ "S565", "他の項目を組み合わせて算出される項目であることを示します" },
		{ "S569", "省略値" },
		{ "S577", "フィールド名" },
		{ "S583", "モデル表示" },
		{ "S585", "これをチェックすると、データモデルのエンティティボックス内でこのフィールドが表示されます" },
		{ "S586", "（説明なし）" },
		{ "S592", "機能名" },
		{ "S597", "項目に対する操作" },
		{ "S602", "機能名" },
		{ "S607", "項目に対する操作" },
		{ "S612", "タイプ" },
		{ "S613", "フィールド構成" },
		{ "S616", "ＫＥＹタイプ" },
		{ "S624", "テーブル関連一覧" },
		{ "S632", "機能一覧" },
		{ "S633", "外部機能一覧" },
		{ "S638", "機能名" },
		{ "S639", "機能タイプ" },
		{ "S640", "概要" },
		{ "S641", "引数" },
		{ "S642", "返り値" },
		{ "S647", "機能ID" },
		{ "S648", "機能名" },
		{ "S649", "機能タイプ" },
		{ "S650", "概要" },
		{ "S662", "タイプ" },
		{ "S664", "機能名" },
		{ "S666", "引数／返り値" },
		{ "S668", "概要" },
		{ "S673", "入出力一覧" },
		{ "S674", "この機能が利用する機能" },
		{ "S675", "この機能を利用する機能" },
		{ "S676", "この機能を直接利用する業務" },
		{ "S680", "入出力名" },
		{ "S688", "機能名" },
		{ "S689", "起動イベント" },
		{ "S693", "入出力名" },
		{ "S694", "担当名" },
		{ "S696", "業務名" },
		{ "S697", "アクション" },
		{ "S701", " パネルイメージ" },
		{ "S703", "パネル名" },
		{ "S717", "スプール名" },
		{ "S727", " スプールイメージ" },
		{ "S739", "一次識別子" },
		{ "S742", "入出力接尾名" },
		{ "S745", "操作区分" },
		{ "S761", "テーブル定義へジャンプ" },
		{ "S766", " ページイメージ" },
		{ "S768", "ページ名" },
		{ "S774", "ファイル名" },
		{ "S783", "終了" },
		{ "S782", "ファイルを新規作成して開く" },
		{ "S781", "ファイルを選んで開く" },
		{ "S784", "閲覧・編集されるファイルの指定に関して、以下のいずれかを選んでください。" },
		{ "S785", "XEAD Modeler - 編集ファイルの選択" },
		{ "S786", "既存のコンテンツファイルを開く" },
		{ "S789", "新規作成して開く" },
		{ "S788", "XEAD Modeler - フォルダとファイル名（漢字不可）を指定してください。" },
		{ "S794", "指定されたxeadファイルの解析に失敗しました。様式を確認してください" },
		{ "S798", "有効な拡張子を持つ指定名のファイルが見つかりません。" },
		{ "S799", "指定ファイルの拡張子が無効です。" },
		{ "S800", "このアクションは下位アクションを伴います。\nそれらを含めてコピーしますか？" },
		{ "S801", "コピー操作の確認" },
		{ "S806", "取消" },
		{ "S805", "ファイル名の再指定" },
		{ "S804", "上書き" },
		{ "S807", "指定されたファイルは既に存在します。上書きしますか？" },
		{ "S808", "XEAD Modeler - 編集ファイルの選択" },
		{ "S811", "指定されたファイルは読み取り専用です。Modelerで編集" },
		{ "S813", "しても、その内容を反映できない点にご注意ください。" },
		{ "S860", "追加" },
		{ "S861", "追加(パネル)" },
		{ "S862", "追加(スプール)" },
		{ "S863", "追加(WEBページ)" },
		{ "S864", "追加(外部キー)" },
		{ "S865", "追加(二次識別子)" },
		{ "S866", "コピー" },
		{ "S867", "貼り付け" },
		{ "S868", "削除" },
		{ "S869", "はずす" },
		{ "S870", "移動↑" },
		{ "S871", "移動↓" },
		{ "S872", "移動←" },
		{ "S873", "移動→" },
		{ "S874", "ジャンプ" },
		{ "S875", "スライドショー" },
		{ "S876", "スライド順序" },
		{ "S877", "テーブルの整列" },
		{ "S878", "イメージ出力" },
		{ "S879", "編集のヒント" },
		{ "S880", "一覧出力" },
		{ "S881", "再読込" },
		{ "S882", "テーブル幅の調整" },
		{ "S915", "ノードの追加" },
		{ "S916", "ノードの貼り付け" },
		{ "S919", "操作方法" },
		{ "S993", "キーとは何か" },
		{ "S1000", "XEAD Modeler - 置換結果の保存確認" },
		{ "S1001", "置換処理の結果を保存しますか？" },
		{ "S1002", "現在のファイル内容が変更されています。検索・置換の前に保存する必要があります。" },
		{ "S1098", "保存して終了" },
		{ "S1099", "保存せずに終了" },
		{ "S1100", "編集に戻る" },
		{ "S1101", "編集結果を保存して終了しますか？" },
		{ "S1102", "XEAD Modeler - 変更の保存" },
		{ "S1104", "保存せずに新規作成" },
		{ "S1103", "保存してから新規作成" },
		{ "S1106", "編集結果を保存してから新規作成しますか？" },
		{ "S1107", "XEAD Modeler - 新規作成にともなう変更の保存" },
		{ "S1108", "XEAD Modeler - インポート結果の保存" },
		{ "S1109", "新規作成して開く" },
		{ "S1110", "インポート結果を保存しますか？" },
		{ "S1111", "保存する" },
		{ "S1112", "名前を変えて保存" },
		{ "S1113", "保存しない" },
		{ "S1114", "保存してから開く" },
		{ "S1115", "保存せずに開く" },
		{ "S1116", "現在のファイル内容が変更されています。インポートの前に保存する必要があります。" },
		{ "S1117", "現在のファイル内容が変更されています。保存してから開きますか？" },
		{ "S1118", "変更の保存" },
		{ "S1119", "既存のＸＥＡＤファイルを開く" },
		{ "S1120", "インポート元となるＸＥＡＤファイルを開く" },
		{ "S1121", "CreateTable文を含むテキストファイル(txt;sql)を開く" },
		{ "S1125", "この名前で保存" },
		{ "S1149", "保存できませんでした。" },
		{ "S1225", "№,職務ＩＤ,職務名,所属部門,業務ＩＤ,業務名,イベント,業務タイプＩＤ,業務タイプ名" },
		{ "S1242", "XEAD Modeler - 業務一覧のフォルダとCSVファイル名を指定してください。" },
		{ "S1247", "出力されるべき業務定義が存在しません" },
		{ "S1250", "№,サブシステムＩＤ,サブシステム名,テーブルＩＤ,テーブル名,C,R,U,D,テーブルタイプ,一次識別子,外C,外R,外U,外D" },
		{ "S1251", "№,サブシステムＩＤ,サブシステム名,テーブルＩＤ,テーブル名,Key,フィールドＩＤ,フィールド名,属性,データタイプ,Null,省略値,モデル,説明" },
		{ "S1310", "XEAD Modeler - テーブル一覧のフォルダとCSVファイル名を指定してください。" },
		{ "S1315", "出力されるべきテーブル定義が存在しません" },
		{ "S1319", "№,サブシステムＩＤ,サブシステム名,機能ＩＤ,機能名,タイプ,概要,この機能を直接利用する業務" },
		{ "S1347", ") 」" },
		{ "S1343", "「" },
		{ "S1348", "等" },
		{ "S1350", "なし" },
		{ "S1353", "XEAD Modeler - 機能一覧のフォルダとCSVファイル名を指定してください。" },
		{ "S1358", "出力されるべき機能定義が存在しません" },
		{ "S1360", "XEAD Modeler - CREATE文を保存するフォルダとtxtファイル名を指定してください。" },
		{ "S1366", "警告：キー項目のデータタイプ設定に不整合が見つかりました。FOREIGN KEYステートメント上の" },
		{ "S1368", "項目名に(?)と示されている部分に関して、関連するキー定義同士のデータタイプ構成を調べてください。" },
		{ "S1370", "詳しくはヘルプのキー定義に関する説明を参照してください。" },
		{ "S1371", "出力されるべきテーブル定義が存在しません" },
		{ "S1372", "次のファイル名でステートメントが出力されました。" },
		{ "S1582", "XEAD Modeler - 業務プロトコルデータを保存するフォルダとcsvファイル名を指定してください。" },
		{ "S1587", "担当者," },
		{ "S1593", "業務," },
		{ "S1599", "イベント," },
		{ "S1600", "、" },
		{ "S1613", "処理されるべき業務定義が存在しません" },
		{ "S1617", " が無効です" },
		{ "S1618", "カーソル指定されている文字列" },
		{ "S1621", "はURLとして無効です。”http:”か”file:”で始まり、半角のコンマ”,”、セミコロン”;”、閉じカッコ”)”" },
		{ "S1623", "または行末で終わる文字列でなければなりません。" },
		{ "S1629", "が見つかりません。" },
		{ "S1628", "ヘルプファイル" },
		{ "S1735", "既存のＣＳＶファイルを開く" },
		{ "S1747", "様式に従った有効な行が見つかりません。ヘルプで様式を再確認してください。" },
		{ "S1748", "一部の行が取り込まれませんでした。ヘルプで様式を再確認してください。" },
		{ "S1770", "パネルの横幅と縦幅とをその順で、半角桁数" },
		{ "S1772", "にて指定してください。フォームの右辺や下辺" },
		{ "S1774", "をマウスでドラッグしてもサイズ変更できます。" },
		{ "S1779", "スプールの横幅と縦幅を文字数で指定してください。(横幅,縦幅)" },
		{ "S1797", "（機能階層のルート）" },
		{ "S1829", "入出力定義の「ファイル名」が正しくありません。" },
		{ "S1845", "テーブル入出力" },
		{ "S1847", "」(Page " },
		{ "S1846", "業務フロー「" },
		{ "S1920", "警告：このフィールドを含むキー定義がすでにテーブル関連をともなっています。関連定義における、" },
		{ "S1922", "もういっぽうのキー定義のデータタイプ構成との矛盾が生じ得ることに注意してください。矛盾がある" },
		{ "S1924", "場合には、テーブル定義の「CREATE TABLE文」上で警告メッセージが示されます。" },
		{ "S1959", "XEAD Modeler - 一覧データを保存するフォルダとCSVファイル名を指定してください。" },
		{ "S1976", "「ファイル名」を正しく設定したうえで、右クリックで表示される「再読込」を実行してください。" },
		{ "S1977", "この一覧はここで編集されるものではなく、「業務定義」の編集内容に応じて" },
		{ "S1978", "表示専用として照会されるものです。必要ならば「業務定義」の「業務タイプ」" },
		{ "S1979", "を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S1981", "この一覧はここで編集されるものではなく、「職務定義」の編集内容に応じて" },
		{ "S1983", "表示専用として照会されるものです。必要ならば「職務定義」の「所属部門」" },
		{ "S1985", "を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S1987", "この一覧はここで編集されるものではなく、「テーブル定義」の編集内容に応じて" },
		{ "S1989", "表示専用として照会されるものです。必要ならば「テーブル定義」の「テーブルタイプ」" },
		{ "S1991", "を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S1993", "この一覧はここで編集されるものではなく、「フィールド定義」の編集内容に応じて" },
		{ "S1995", "表示専用として照会されるものです。必要ならば「フィールド定義」の「データタイプ」" },
		{ "S1997", "を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S1999", "この一覧はここで編集されるものではなく、「機能定義」の編集内容に応じて" },
		{ "S2001", "表示専用として照会されるものです。必要ならば「機能定義」の「機能タイプ」" },
		{ "S2003", "を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S2005", "業務フローにはさまざまな要素が載りますが、大きく「システム境界」、「プロセスノード」、「フロー」、" },
		{ "S2007", "「ストレージノード」の４種類に分かれます。「システム境界（背景にある白い枠）」の枠の部分を" },
		{ "S2009", "ドラッグすれば大きさや位置を変更できます。「プロセスノード（青い楕円）」 を追加するには、ツリ" },
		{ "S2011", "ービュー上の「職務別担当業務」→「職務定義」→「業務定義」のノードをここにドラッグ＆ドロップ" },
		{ "S2013", "してください。「プロセスノード」の名称あたりで右クリックすれば、「フロー（矢印）」を追加するための" },
		{ "S2015", "項目が現れます。「ストレージノード（その他のいろいろなアイコン）」を追加したい場合には、何も" },
		{ "S2017", "ないところで右クリックして現れるメニュー項目「ノードの追加」を用いてください。" },
		{ "S2019", "矢印キー（Tabキーではありません）の[→]でページが進み、[←]で戻ります。" },
		{ "S2021", "Shiftキーを押しながら矢印キーを押せば、先頭や最後尾のページへ移ります。" },
		{ "S2023", "途中で終了させたい場合には、Escキーを押してください。" },
		{ "S2025", "ツリービュー上の、他の職務下の「業務定義」のノードをここにドラッグ＆ドロップ" },
		{ "S2027", "すれば、こちら側に移動します。" },
		{ "S2029", "入出力イメージをここでは編集できません。それらのレイアウトを編集するためには、メインツリービュー" },
		{ "S2031", "上の「アプリケーション」→「サブシステム定義」→「機能構成」→「機能定義」→「入出力定義" },
		{ "S2033", "(パネル／スプール)」のノードを開いてください（「ジャンプ」で簡単にアクセスできます）。そのタイプの" },
		{ "S2035", "ノードをここにドラッグ＆ドロップすれば、このアクションで利用される入出力定義として追加されます。" },
		{ "S2037", "なお、Modeler上では「WEBページ」のレイアウトを編集できない点に注意してください。" },
		{ "S2039", "メインツリービュー上の「アプリケーション」→「サブシステム定義」→「機能構成」→" },
		{ "S2041", "「機能定義」→「入出力定義(パネル／WEBページ／スプール)」のノードをここにドラ" },
		{ "S2043", "ッグ＆ドロップすれば、このアクションで利用される入出力定義として追加されます。" },
		{ "S2045", "このツリー構造は、機能定義毎の「この機能が利用する機能」の内容に" },
		{ "S2047", "もとづいて表示されているもので、ここでは編集できません。" },
		{ "S2049", "入出力イメージをここでは編集できません。それらのレイアウトを編集するためには、メイン" },
		{ "S2051", "ツリービュー上の「アプリケーション」→「サブシステム定義」→「機能構成」→「機能定義」" },
		{ "S2053", "→「入出力定義(パネル／スプール)」のノードを開いてください（「ジャンプ」で簡単にアクセス" },
		{ "S2055", "できます）。なお、「WEBページ」のレイアウトについてはModeler上で編集することはできません。" },
		{ "S2057", "ツリービュー上の、他のサブシステム下の「テーブル定義」のノードをここにドラッグ＆ドロ" },
		{ "S2059", "ップすれば、こちら側に移動します。その際、移動元のサブシステムではそのテーブルが" },
		{ "S2061", "「外部テーブル一覧」上に残る点に注意してください。" },
		{ "S2063", "ツリービュー上の、他のサブシステム下の「テーブル定義」のノードをここにドラッグ＆ドロップ" },
		{ "S2065", "すれば、そのテーブルがこのサブシステムの「外部テーブル」として登録されます。外部テー" },
		{ "S2067", "ブルに対する更新操作(CRUDのうちのC,U,D)をなるべく避けることが、サブシステムの独" },
		{ "S2069", "立性とシステム全体の保守性を高めるコツです。" },
		{ "S2071", "ツリービュー上の「テーブル定義」をここにドラッグ＆ドロップすれば、データ" },
		{ "S2073", "モデル上にエンティティボックスが追加されます。ボックス上のキー項目をテー" },
		{ "S2075", "ブル名の上にドラッグ＆ドロップすれば、新たなテーブル関連を追加するため" },
		{ "S2077", "のダイアログが表示されます。なお、モデル上の関連線の起点を右クリック" },
		{ "S2079", "することで、表示不要な関連線を隠せます。" },
		{ "S2081", "この一覧はここで編集されるものではなく、このサブシステムに含まれる「機能定義」の" },
		{ "S2083", "編集内容に応じて表示専用として照会されるものです。必要ならば「機能定義」の" },
		{ "S2085", "「テーブルＩＯ」を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S2093", "ツリービュー上の「フィールド定義」のノードをここにドラッグ＆ドロップすれば、" },
		{ "S2095", "定義内容がコピーされて追加されます。この操作はそれらのノードを「コピー」" },
		{ "S2097", "して「貼り付け」するよりもお手軽です。フィールドの並び順を変えたい場合に" },
		{ "S2098", "は、この一覧上で行を上下にドラッグしてください。" },
		{ "S2111", "テーブルには必ず「一次識別子」が１個だけ含まれます。テーブルを新規追加すれば" },
		{ "S2113", "一次識別子が自動的に追加されるので、あらたに追加できるのは「二次識別子」か" },
		{ "S2115", "「外部キー」のいずれかです。「一次識別子」や「二次識別子」の実際値はテーブル内" },
		{ "S2117", "で重複することがないし、「外部キー」の実際値は関連するテーブル上に存在する値" },
		{ "S2119", "のみとなります。詳細についてはマニュアルで確認してください。" },
		{ "S2121", "ツリービュー上の、このテーブルに含まれる「フィールド定義」のノードをここ" },
		{ "S2123", "にドラッグ＆ドロップすれば、キー項目として追加されます。他のテーブルに" },
		{ "S2125", "含まれるフィールドをドラッグ＆ドロップしても無効です。キー項目の並び順" },
		{ "S2126", "を変更したい場合には、この一覧上で行を上下にドラッグしてください。" },
		{ "S2127", "ツリービュー上の、他の「キー定義」のノードをここにドラッグ＆ドロップすれば、" },
		{ "S2129", "あらたな「テーブル関連」が生成されます。ただし、どんなキー定義でもよい" },
		{ "S2131", "というわけではなく、キーに含まれるフィールドの構成に関するいくつかの" },
		{ "S2133", "制約があります。詳細についてはマニュアルで確認してください。" },
		{ "S2135", "ツリービュー上の、他のサブシステム下の「機能定義」のノードをここに" },
		{ "S2137", "ドラッグ＆ドロップすれば、こちら側に移動します。" },
		{ "S2139", "この一覧はここで編集されるものではなく、このサブシステムに含まれる「機能定義」が利用して" },
		{ "S2141", "いる機能のうち、他のサブシステムに含まれるものを表示専用として照会したものです。必要な" },
		{ "S2143", "らば「機能定義」の内容を編集してください。この一覧の内容も自動的に変わります。" },
		{ "S2145", "ツリービュー上の、「テーブル定義」のノードをここにドラッグ＆ドロップ" },
		{ "S2147", "すれば、そのテーブルにもとづくあらたな入出力定義が追加されます。" },
		{ "S2149", "また、「パネル定義」、「スプール定義」、「テーブル入出力」のノードをここ" },
		{ "S2151", "にドラッグ＆ドロップすれば、定義内容がコピーされて追加されます。この" },
		{ "S2153", "操作はそれらのノードを「コピー」して「貼り付け」するよりもお手軽です。" },
		{ "S2155", "ページの内容はＸＥＡＤでは編集できません。外部のエディタを利用してください。" },
		{ "S2157", "ツリービュー上の、「機能定義」のノードをここにドラッグ＆ドロップすれば、" },
		{ "S2159", "この機能が利用する機能として追加されます。機能の並び順を変更したい" },
		{ "S2160", "場合には、この一覧上で行を上下にドラッグしてください。" },
		{ "S2161", "この一覧はここで編集されるものではなく、他の「機能定義」の編集内容に応じて" },
		{ "S2163", "表示専用として照会されるものです。必要ならば、関連する「機能定義」の内容を" },
		{ "S2165", "編集してください。この一覧も自動的に変わります。" },
		{ "S2167", "この一覧はここで編集されるものではなく、「業務定義」の編集内容に応じて" },
		{ "S2169", "表示専用として照会されるものです。必要ならば、関連する「業務定義」の" },
		{ "S2171", "内容を編集してください。この一覧も自動的に変わります。" },
		{ "S2173", "一覧上の行はここで追加・削除できません。それらは「テーブル定義」の編集内容" },
		{ "S2175", "に応じて自動設定されているからです。必要ならば、関連する「テーブル定義」の" },
		{ "S2177", "内容を編集してください。この一覧も自動的に変わります。" },
		{ "S2184", "新規部門" },
		{ "S2185", "新規業務タイプ" },
		{ "S2198", "新規テーブルタイプ" },
		{ "S2212", "新規データタイプ" },
		{ "S2235", "新規機能タイプ" },
		{ "S2274", "新規ノード" },
		{ "S2286", "．．．ならば" },
		{ "S2288", "．．．を．．．する" },
		{ "S2300", "CDKokyaku,7,顧客コード" },
		{ "S2301", "のように、新規データタイプのＩＤ(一覧順)と桁数と名称とを" },
		{ "S2302", "カンマでつないで指定してください。指定長の文字型項目" },
		{ "S2303", "としてデータタイプ定義が新規作成されます。定義上の他の" },
		{ "S2304", "属性についても「システム定義／データタイプ一覧」のページ" },
		{ "S2305", "で別途設定してください。" },
		{ "S2527", "このテーブルはいずれかの機能に利用されているため、除去できません。" },
		{ "S2536", "関連定義ともなうキーであるため、データ項目を除去できません。" },
		{ "S2539", "この部門に所属する担当者が存在するため、削除できません。" },
		{ "S2540", "最小限１件の部門定義が必要であるため、削除できません。" },
		{ "S2542", "このタイプが指定されているテーブルが存在するため、削除できません。" },
		{ "S2543", "最小限１件のタイプ定義が必要であるため、削除できません。" },
		{ "S2545", "このタイプが指定されているデータ項目が存在するため、削除できません。" },
		{ "S2548", "このタイプが指定されている機能が存在するため、削除できません。" },
		{ "S2551", "最小限１件の保守履歴が必要であるため、削除できません。" },
		{ "S2593", "起点となるアクションは削除できません。" },
		{ "S2612", "画像ファイルの形式を選んでください。" },
		{ "S2613", "XEAD Modeler - 画像ファイル形式の選択" },
		{ "S2617", "形式)を指定してください。" },
		{ "S2616", "XEAD - イメージファイル名(" },
		{ "S2623", "印刷用配色" },
		{ "S2622", "通常配色" },
		{ "S2624", "業務フローの配色設定を選んでください。" },
		{ "S2625", "XEAD Modeler - 配色設定の選択" },
		{ "S2629", "データモデルの配色設定を選んでください。" },
		{ "S2865", "」へジャンプ" },
		{ "S2864", "「" },
		{ "S2870", "ジャンプ" },
		{ "S2871", "フロー追加" },
		{ "S2872", "変更" },
		{ "S2873", "コピー" },
		{ "S2874", "削除" },
		{ "S2875", "隠す" },
		{ "S2876", "インスタンス" },
		{ "S2877", "全関連線を表示" },
		{ "S2954", "新規フロー" },
		{ "S3062", " ; 常に対応する" },
		{ "S3063", " ; 参照キー項目がすべてNullでなければ対応する" },
		{ "S3221", "（値例を書き込んでください）" },
		{ "S3248", "システムの説明" },
		{ "S3253", "保守履歴一覧" },
		{ "S3268", "サブシステムの説明" },
		{ "S3305", "イメージ" },
		{ "S3307", "項目一覧" },
		{ "S3319", "・" },
		{ "S3320", "【 " },
		{ "S3321", " 】" },
		{ "S3326", "「" },
		{ "S3327", "」" },
		{ "S3328", "」の" },
		{ "S3385", "業務フロー" },
		{ "S3391", "職務別担当業務" },
		{ "S3401", "アプリケーション" },
		{ "S3407", "ＤＢ構成" },
		{ "S3427", "ＫＥＹ構成" },
		{ "S3433", "機能構成" },
		{ "S3442", "（再帰）" },
		{ "S3485", "[注]" },
		//{ "S3488", "キーとは何か" },
		{ "S3528", "新規業務フロー" },
		{ "S3545", "新規職務" },
		{ "S3562", "新規業務" },
		{ "S3568", "随時" },
		{ "S3590", "新規サブシステム" },
		{ "S3606", "新規テーブル" },
		{ "S3651", "新規フィールド" },
		{ "S3701", "新規機能" },
		{ "S3707", "なし" },
		{ "S3709", "戻りコード" },
		{ "S3721", "新規パネル" },
		{ "S3722", "リンク時に業務定義上で表示される説明文" },
		{ "S3723", "リンク時に業務定義上で表示されない説明文" },
		{ "S3742", "項目１" },
		{ "S3750", "新規スプール" },
		{ "S3771", "項目１" },
		{ "S3777", "新規ページ" },
		{ "S3792", "項目１" },
		{ "S3802", "この担当者は何らかの業務を担当しているため、削除できません。" },
		{ "S3811", "」他、合計" },
		{ "S3810", "この業務は「" },
		{ "S3812", "個の業務フローにおいて" },
		{ "S3814", "プロセスとして置かれているため、削除できません。" },
		{ "S3820", "このサブシステムには（内部）テーブルが含まれているため、削除できません。" },
		{ "S3824", "このサブシステムには何らかの機能が含まれているため、削除できません。" },
		{ "S3830", "このテーブルはいずれかの機能に利用されているため、削除できません。" },
		{ "S3835", "このテーブルはテーブル関連定義をともなうため、削除できません。" },
		{ "S3844", "」において" },
		{ "S3843", "このテーブルは「" },
		{ "S3846", "外部テーブルとして登録されているため、削除できません。" },
		{ "S3848", "」他、合計" },
		{ "S3847", "このテーブルは「" },
		{ "S3849", "個のサブシステムにおいて" },
		{ "S3851", "外部テーブルとして登録されているため、削除できません。" },
		{ "S3858", "この項目はこのテーブルのいずれかのキーに含まれているため、削除できません。" },
		{ "S3866", "この項目に対する処理内容がブランクでない機能が存在するため、削除できません。" },
		{ "S3878", "一次識別子は必須であるため、削除できません。" },
		{ "S3884", "このキーはテーブル関連定義をともなうため、削除できません。" },
		{ "S3897", "この機能はいずれかの業務で利用されているため、削除できません。" },
		{ "S3903", "この機能は他の機能によって利用されているため、削除できません。" },
		{ "S3911", "このパネルはいずれかの業務で利用されているため、削除できません。" },
		{ "S3922", "このスプールはいずれかの業務で利用されているため、削除できません。" },
		{ "S3937", "このページはいずれかの業務で利用されているため、削除できません。" },
		{ "S3951", "(コピー)" },
		{ "S4556", "システム定義" },
		{ "S4606", "一覧" },
		{ "S4623", "職務一覧" },
		{ "S4632", " - 業務一覧" },
		//{ "S4659", "なし" },
		//{ "S4661", "」" },
		//{ "S4660", "「" },
		//{ "S4664", "個" },
		//{ "S4663", "」他、合計" },
		//{ "S4662", "「" },
		{ "S4695", "このアクションはサーバーとのやりとりを伴いません" },
		{ "S4911", "固有" },
		{ "S4914", "継承" },
		{ "S4917", "導出" },
		{ "S4936", "不可" },
		{ "S4937", "可" },
		{ "S5007", "一次識別子(PK)" },
		{ "S5011", "外部キー(FK)" },
		{ "S5014", "二次識別子(SK)" },
		{ "S5032", "関連タイプ" },
		{ "S5033", "多重度" },
		{ "S5036", "ＫＥＹ" },
		{ "S5037", "存在条件" },
		{ "S5050", "親子関係" },
		{ "S5051", "1:複数" },
		{ "S5052", "（指定不能）" },
		{ "S5055", "参照関係" },
		{ "S5060", "派生関係" },
		{ "S5064", "1:1または0" },
		{ "S5161", "機能名" },
		{ "S5162", "引数" },
		{ "S5163", "起動イベント" },
		{ "S5219", "ラベル" },
		{ "S5220", "項目名" },
		{ "S5221", "項目タイプ" },
		{ "S5226", "ＧＵＩ部品" },
		{ "S5225", "表示専用項目" },
		{ "S5224", "必須入力項目" },
		{ "S5223", "任意入力項目" },
		{ "S5269", "設定される値" },
		{ "S5301", "項目に対する操作" },
		{ "S5425", "この機能はすでに関連づけられているため、追加できません。" },
		{ "S5586", "テーブル関連をともなうため、キー定義は変更できません。" },
		{ "S5595", "この項目はすでに組み込まれているため、追加できません。" },
		{ "S5600", "警告：キー定義に同一のデータタイプのフィールドが同居しているため、ツールメニューの「CreateTable文出力」の" },
		{ "S5602", "実行時に、このキーにもとづく親子関係における子から親への参照制約が正しく設定されない可能性があります。" },
		{ "S5604", "詳しくはヘルプのキー定義に関する説明を参照してください。" },
		{ "S5628", "指定されたキーの組み合わせにもとづく関連定義がすでに存在します。" },
		{ "S5631", "外部キー（ＦＫ）の組み合わせに対してテーブル関連は成立しません。" },
		{ "S5703", "指定されたキーの組み合わせに対してテーブル関連は成立しません。キーに含まれるデータ項目の数やデータタイプを調整してください。" },
		//{ "S5713", "この入出力定義はすでに関連づけられているため、追加できません。" },
		{ "S5728", "この業務はすでに配置されているため、追加できません。" },
		{ "S5748", "このテーブルはすでにデータモデル上に存在するので追加できません。" },
		{ "S5750", "このテーブルはすでにこのサブシステムに関連付けられているので追加できません。" },
		{ "S5760", "警告：サブシステム間の結合度を抑えるために、他のサブシステムが主管" },
		{ "S5761", "するテーブルに対するC,U,D操作はなるべく控えてください。サブシステム" },
		{ "S5762", "構成を見直すか、更新用関数を用いる等の工夫をおすすめします。" },
		{ "S6450", "変更あり" },
		{ "SplashMessage0", "XEAD Modelerを起動中" },
		{ "SplashMessage1", "システム定義を分析中" },
		{ "XLSFontDTL", "ＭＳ Ｐ明朝" },
		{ "XLSFontHDR", "ＭＳ Ｐゴシック" }};
	public Object[][] getContents() {
		return contents;
	}
}
