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
	public static final String FULL_VERSION  = "V1.R4.M2";
	// 1.4.2での変更点
	//・業務定義ペイン、フィールド定義ペイン、文字列検索ダイアログについてＸＧＡサイズでも閲覧可能にした
	//・properties可変になっていなかったテーブル一覧ペイン上の一部、およびタスク定義ペイン上の一部のフォントに対応した
	//・入出力イメージのフォントサイズの初期値をpropertiesの可変にした（無指定の場合は16）
	//・業務フロー上で「システム境界」を移動したときにシステム名の一部が消えることのある問題を修正した
	//・パネル／帳票イメージのサイズ変更用ガイドの位置がずれていた問題を修正
	//・パネル／帳票イメージ上のキャレット色を背景色に合わせて変化させるようにした
	//・パネル／帳票イメージのUNDO/REDOが効かなくなっていた問題を修正
	//
	// 1.4.1縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｨ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫〒縺ｮ遏ｩ蠖｢驕ｸ謚槭ぎ繧､繝峨�ｮ濶ｲ繧呈ｰｴ濶ｲ縺九ｉ逋ｽ縺ｫ螟画峩縺励◆
	//繝ｻxeadmdl.properties縺ｮ讀懃ｴ｢鬆�蠎上ｒ縲�(1)螳夂ｾｩ繝輔ぃ繧､繝ｫ(*.xead)縺後≠繧九ヵ繧ｩ繝ｫ繝�縲�(2)xeadmdl.jar縺後≠繧九ヵ繧ｩ繝ｫ繝�縲√↓縺励◆
	//
	// 1.4.0縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻxeadmdl.properties縺ｧ繝輔か繝ｳ繝亥錐繧呈欠螳壹〒縺阪ｋ繧医≧縺ｫ縺励◆
	//繝ｻ荳�驛ｨ縺ｮ隕∫ｴ�繧帝勁縺阪�√ヵ繧ｩ繝ｳ繝医し繧､繧ｺ繧�12p縺九ｉ16p縺ｫ螟画峩縺励◆
	//繝ｻIO繧､繝｡繝ｼ繧ｸ縺ｮ繝輔か繝ｳ繝医し繧､繧ｺ縺ｮ謖�螳夂ｯ�蝗ｲ繧�10縲�14縺九ｉ10縲�20縺ｫ諡｡蠑ｵ縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｨ繝�繝ｼ繧ｿ繝｢繝�繝ｫ縺ｫ縺､縺�縺ｦ縲∽ｸ�驛ｨ縺ｮ繝�繧ｭ繧ｹ繝医�ｮ繝輔か繝ｳ繝医し繧､繧ｺ繧定�ｪ蜍戊ｪｿ謨ｴ縺吶ｋ繧医≧縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｮ繧､繝吶Φ繝医↓縺､縺�縺ｦ陦ｨ遉ｺ險ｭ螳壽ｧ伜ｼ上ｒ謾ｹ蝟�縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫〒隍�謨ｰ縺ｮ繝励Ο繧ｻ繧ｹ繧�繧ｹ繝医Ξ繝ｼ繧ｸ繧堤洸蠖｢驕ｸ謚槭＠縺ｦ蜷梧凾縺ｫ遘ｻ蜍輔〒縺阪ｋ繧医≧縺ｫ縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫�ｮ繝�繝ｼ繝悶Ν縺ｫ縺､縺�縺ｦ縲√ヵ繧｣繝ｼ繝ｫ繝臥ｾ､繧貞ｸｸ縺ｫ�ｼ第ｮｵ縺ｧ遉ｺ縺吶ｈ縺�縺ｫ縺励◆�ｼ郁､�謨ｰ谿ｵ縺ｧ陦ｨ遉ｺ縺吶ｋ縺ｨ繧､繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ縺梧嶌縺阪↓縺上￥縺ｪ繧九◆繧��ｼ�
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫�ｮ蜈ｨ繝�繝ｼ繝悶Ν縺ｮ蟷�繧定�ｪ蜍戊ｨｭ螳壹☆繧九◆繧√�ｮ繧ｳ繝ｳ繝�繧ｭ繧ｹ繝医Γ繝九Η繝ｼ繧定ｨｭ縺代◆
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫〒隍�謨ｰ縺ｮ繝�繝ｼ繝悶Ν繧偵ｒ遏ｩ蠖｢驕ｸ謚槭＠縺ｦ蜷梧凾縺ｫ遘ｻ蜍輔〒縺阪ｋ繧医≧縺ｫ縺励◆
	//
	// 57縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫�ｮ繧ｹ繝医Ξ繝ｼ繧ｸ縺ｨ繝�繝ｼ繧ｿ繝輔Ο繝ｼ縺ｫ縺､縺�縺ｦ縲√ム繝悶Ν繧ｯ繝ｪ繝�繧ｯ縺吶ｌ縺ｰ險倩ｿｰ螟画峩繝�繧､繧｢繝ｭ繧ｰ繧堤､ｺ縺吶ｈ縺�縺ｫ縺励◆
	//
	// 56縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ繝輔ぃ繧､繝ｫ驕ｸ謚槭ム繧､繧｢繝ｭ繧ｰ縺ｮ諡｡蠑ｵ蟄占ｨｭ螳壹ｒ謾ｹ蝟�縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繧ｿ繧､繝怜挨縺ｮ繝輔ぅ繝ｼ繝ｫ繝我ｸ�隕ｧ陦ｨ遉ｺ縺ｮ繝代ヵ繧ｩ繝ｼ繝槭Φ繧ｹ繧呈隼蝟�縺励◆
	//
	// 55縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ菫晏ｮ亥ｱ･豁ｴ縺ｮ蛻晄悄蛟､縺ｫ蜷御ｸ�蜀�螳ｹ縺ｮ陦後′蜷ｫ縺ｾ繧後↑縺�繧医≧縺ｫ縺励◆
	//繝ｻ菫晏ｮ亥ｱ･豁ｴ縺ｮ譌･譎ゅ�ｮ蛹ｺ蛻�繧頑枚蟄励ｒ繝峨ャ繝医°繧峨い繝ｳ繝�繝ｼ繝舌�ｼ縺ｫ螟画峩縺励◆
	//繝ｻSQL繧､繝ｳ繝昴�ｼ繝医〒縲取枚荳ｭ縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨さ繝｡繝ｳ繝医ｒ繝輔ぅ繝ｼ繝ｫ繝牙ｮ夂ｾｩ蜷阪→縺ｿ縺ｪ縺吶�上ｒ繝√ぉ繝�繧ｯ縺吶ｋ縺ｨ�ｼｰ�ｼｫ縺檎函謌舌＆繧後↑縺�繝舌げ繧剃ｿｮ豁｣
	//繝ｻ繝輔ぅ繝ｼ繝ｫ繝画ｯ弱�ｮ繝�繝ｼ繧ｿ繧ｿ繧､繝励�ｮ譯∵焚陦ｨ遉ｺ蠖｢蠑上ｒ謾ｹ蝟�縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ繧�讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫�ｮ繝偵Φ繝郁｡ｨ遉ｺ縺梧ｨｪ髟ｷ縺ｫ縺ｪ繧峨↑縺�繧医≧縺ｫ縺励◆
	//
	// 54縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫�ｮ繝励Ο繧ｻ繧ｹ繧偵ム繝悶Ν繧ｯ繝ｪ繝�繧ｯ縺吶ｌ縺ｰ讌ｭ蜍吝ｮ夂ｾｩ縺ｫ繧ｸ繝｣繝ｳ繝励☆繧九ｈ縺�縺ｫ縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫�ｮ繝�繝ｼ繝悶Ν繧偵ム繝悶Ν繧ｯ繝ｪ繝�繧ｯ縺吶ｌ縺ｰ繝�繝ｼ繝悶Ν螳夂ｾｩ縺ｫ繧ｸ繝｣繝ｳ繝励☆繧九ｈ縺�縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ荳翫�ｮ蜈･蜃ｺ蜉帙う繝｡繝ｼ繧ｸ繧偵ム繝悶Ν繧ｯ繝ｪ繝�繧ｯ縺吶ｌ縺ｰ讖溯�ｽ螳夂ｾｩ縺ｫ繧ｸ繝｣繝ｳ繝励☆繧九ｈ縺�縺ｫ縺励◆
	//繝ｻ襍ｰ譟ｻ繝�繧､繧｢繝ｭ繧ｰ縺ｧ縺ｮ讀懃ｴ｢邨先棡縺ｮ蜷咲ｧｰ縺ｫ�ｼｩ�ｼ､繧貞性繧√ｋ繧医≧縺ｫ縺励◆
	//繝ｻCRUD蝗ｳ遲峨ｒ蜃ｺ蜉帙☆繧九◆繧√↓縲√ヤ繝ｼ繝ｫ繝｡繝九Η繝ｼ縺ｫ縲後�槭ヨ繝ｪ繝�繧ｯ繧ｹ繝ｪ繧ｹ繝亥�ｺ蜉帙�阪ｒ霑ｽ蜉�縺励◆
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ縺ｮ蜈･蜃ｺ蜉帙う繝｡繝ｼ繧ｸ荳矩Κ縺ｮ繝ｩ繝吶Ν縺ｮ髟ｷ縺輔ｒ繝�繧ｭ繧ｹ繝磯聞縺ｫ蜷医ｏ縺帙ｋ繧医≧縺ｫ縺励◆
	//繝ｻUndo縺ｮ蝗樊焚繧堤┌蛻ｶ髯舌↓縺吶ｋ縺ｨ縺ｨ繧ゅ↓縲ゞndo諠�蝣ｱ繧剃ｽｿ縺｣縺ｦ菫晏ｮ亥ｱ･豁ｴ縺ｮ蛻晄悄蛟､繧定ｨｭ螳壹☆繧九ｈ縺�縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吶ち繧､繝励�ｮ邨�縺ｿ霎ｼ縺ｿ縺ｮ蠖ｱ髻ｿ縺ｧ荳�驛ｨ縺ｮUndo謫堺ｽ懊′縺�縺ｾ縺上＞縺九↑縺九▲縺溘ヰ繧ｰ繧剃ｿｮ豁｣縺励◆
	//
	// 53縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ讌ｭ蜍吶ち繧､繝励�ｮ蠖ｱ髻ｿ縺ｧ讌ｭ蜍吝ｮ夂ｾｩ縺ｮ譖ｴ譁ｰ繝ｫ繝ｼ繝√Φ縺檎焚迥ｶ邨ゆｺ�縺吶ｋ縺薙→縺ｮ縺ゅｋ繝舌げ繧剃ｿｮ豁｣縺励◆
	//
	// 52縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ縺ｮ繧､繝吶Φ繝医ｒ繝悶Λ繝ｳ繧ｯ縺ｫ縺吶ｌ縺ｰ縲∵･ｭ蜍吶ヵ繝ｭ繝ｼ荳翫�ｮ縲檎��逋ｺ繝槭�ｼ繧ｯ縲阪′陦ｨ遉ｺ縺輔ｌ縺ｪ縺�繧医≧縺ｫ縺励◆
	//�ｼ�Github荳翫〒縺ｯ1.3.51縺ｧ蜿肴丐貂茨ｼ�
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ荳ｭ縺ｮ讖溯�ｽUI陦ｨ遉ｺ縺ｮ蟾ｦ荳九�ｮ繧ｭ繝｣繝励す繝ｧ繝ｳ繧呈ｩ溯�ｽID�ｼ句�･蜃ｺ蜉姜D縺九ｉ讖溯�ｽID�ｼ句�･蜃ｺ蜉帛錐縺ｫ螟画峩縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ繧�讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｮ蜊ｰ蛻ｷ蜃ｺ蜉帙↓縺翫＞縺ｦ縲∵律譛ｬ隱樒腸蠅�縺ｧ縺ｯID繧貞�ｨ隗呈枚蟄励↓螟画鋤縺励※蜃ｺ蜉帙☆繧九ｈ縺�縺ｫ縺励◆
	//�ｼ�Java7縺ｮGraphics2D縺ｮ譁�蟄怜�玲緒逕ｻ蜃ｦ逅�縺ｮ繝舌げ縺ｫ繧ゅ→縺･縺乗圻螳壻ｻ墓ｧ假ｼ�
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ繧貞魂蛻ｷ縺励◆蝣ｴ蜷医�√ユ繝ｼ繝悶Ν縺ｮCRUD陦ｨ遉ｺ縺ｮ蜿ｳ蛛ｴ縺悟��繧後ｋ蝠城｡後ｒ菫ｮ豁｣
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ豈弱↓縲後％縺ｮ讌ｭ蜍吶ｒ蜷ｫ繧�讌ｭ蜍吶ヵ繝ｭ繝ｼ縲阪ｒ繝ｪ繧ｹ繝医＠縺ｦ繧ｸ繝｣繝ｳ繝励〒縺阪ｋ繧医≧縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫〒繝弱�ｼ繝峨ｒ繧ｳ繝斐�ｼ縺吶ｋ縺ｨ繝ｩ繝吶Ν縺ｮ�ｼ定｡檎岼縺後さ繝斐�ｼ縺輔ｌ縺ｪ縺�蝠城｡後ｒ菫ｮ豁｣
	//繝ｻ繧ｷ繧ｹ繝�繝�螟画焚縺ｨ縺励※縲梧･ｭ蜍吶ち繧､繝励�阪ｒ霑ｽ蜉�縺励※縲∵･ｭ蜍吝ｮ夂ｾｩ縺ｧ驕ｸ謚槭〒縺阪ｋ繧医≧縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫〒繝�繝ｼ繧ｿ繝輔Ο繝ｼ繧定ｿｽ蜉�縺吶ｋ繝�繧､繧｢繝ｭ繧ｰ縺ｧ縲√ヮ繝ｼ繝峨�ｮ�ｼ定｡檎岼繧偵ヮ繝ｼ繝牙錐縺ｫ蜷ｫ繧√ｋ繧医≧縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ縺ｮ繧｢繧ｯ繧ｷ繝ｧ繝ｳ繧偵さ繝斐�ｼ縺吶ｋ髫帙↓縲御ｸ倶ｽ阪い繧ｯ繧ｷ繝ｧ繝ｳ縲阪ｒ蜷ｫ繧√※繧ｳ繝斐�ｼ縺吶ｋ縺九←縺�縺九ｒ驕ｸ謚槭☆繧九◆繧√�ｮ繝�繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ縺吶ｋ繧医≧縺ｫ縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繝輔Ο繝ｼ縺ｮ繧｢繧､繧ｳ繝ｳ縺ｮ鞫倩ｦ√�碁崕豌嶺ｿ｡蜿ｷ縲阪ｒ縲梧ュ蝣ｱ,繝�繝ｼ繧ｿ縲阪↓螟画峩縺励◆
	//繝ｻ讌ｭ蜍吝ｮ夂ｾｩ縺ｮ繧｢繧ｯ繧ｷ繝ｧ繝ｳ縺ｧ縺ｮ�ｼｩ�ｼｯ縺ｮ繝ｩ繝吶Ν縺ｫ讖溯�ｽ繧ｿ繧､繝怜錐繧貞性繧√ｋ繧医≧縺ｫ縺励◆
	//繝ｻ繝輔ぅ繝ｼ繝ｫ繝我ｸ�隕ｧ縺ｮ荳�隕ｧ邉ｻ蜃ｦ逅�縺ｮ繝ｬ繧ｹ繝昴Φ繧ｹ繧貞､ｧ蟷�縺ｫ謾ｹ蝟�縺励◆
	//
	// 51縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ繧偵う繝ｳ繝昴�ｼ繝医〒縺阪ｋ繧医≧縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ荳翫�ｮ繝励Ο繧ｻ繧ｹ縺ｮ螟画峩繝�繧､繧｢繝ｭ繧ｰ縺ｧ縲√う繝吶Φ繝井ｽ咲ｽｮ縺檎樟蝨ｨ蛟､縺ｧ遉ｺ縺輔ｌ縺ｪ縺�蝠城｡後ｒ菫ｮ豁｣
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｮ縲後�励Ο繧ｻ繧ｹ縲堺ｻ･螟悶�ｮ繝弱�ｼ繝峨〒縲√ヵ繝ｭ繝ｼ縺ｨ蜷梧ｧ倥↓縲後Λ繝吶Ν縲阪ｒ�ｼ定｡檎ｽｮ縺代ｋ繧医≧縺ｫ縺励◆
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ繧定､�蜀吶＠縺溷�ｴ蜷医�√ヮ繝ｼ繝峨�ｮ�ｼ定｡檎岼縺ｮ繝ｩ繝吶Ν縺ｨ繝励Ο繧ｻ繧ｹ繝ｻ繝弱�ｼ繝峨�ｮ繧､繝吶Φ繝井ｽ咲ｽｮ縺後さ繝斐�ｼ縺輔ｌ縺ｪ縺�蝠城｡後ｒ菫ｮ豁｣
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫〒繝�繝ｼ繝悶Ν繧帝撼陦ｨ遉ｺ縺ｫ縺励※繧ゅう繝ｳ繧ｹ繧ｿ繝ｳ繧ｹ縺瑚｡ｨ遉ｺ縺輔ｌ繧九％縺ｨ縺後≠繧句撫鬘後ｒ菫ｮ豁｣
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ荳翫〒縺ｮ繝�繝ｼ繝悶Ν髢｢騾｣霑ｽ蜉�逕ｨ繝�繧､繧｢繝ｭ繧ｰ荳ｭ縺ｮ縲∵眠隕上ヵ繧｣繝ｼ繝ｫ繝芽ｿｽ蜉�謫堺ｽ懊↓縺翫¢繧句��驛ｨ�ｼｩ�ｼ､縺ｮ險ｭ螳壹Ο繧ｸ繝�繧ｯ縺ｮ繝舌げ繧剃ｿｮ豁｣縺励◆
	//�ｼ郁､�謨ｰ繝輔ぅ繝ｼ繝ｫ繝峨ｒ霑ｽ蜉�縺吶ｋ髫帙↓蜷後§蜀�驛ｨ�ｼｩ�ｼ､繧剃ｸ弱∴繧九ｈ縺�縺ｫ縺ｪ縺｣縺ｦ縺�縺滂ｼ�
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ縺ｮ繝�繝ｼ繝悶Ν髢｢騾｣邱壽緒逕ｻ蜃ｦ逅�縺ｫ縺翫＞縺ｦ縲∫┌蜉ｹ縺ｪ髢｢騾｣邱壹ョ繝ｼ繧ｿ縺悟ｭ伜惠縺吶ｋ蝣ｴ蜷医↓閾ｪ蜍慕噪縺ｫ縺昴ｌ繧貞炎髯､縺吶ｋ繧医≧縺ｫ縺励◆
	//繝ｻ繝�繝ｼ繧ｿ繝｢繝�繝ｫ繧�讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｮ蜊ｰ蛻ｷ蜃ｺ蜉帙↓縺翫＞縺ｦ縲√く繝｣繝励す繝ｧ繝ｳ縺ｮ驟咲ｽｮ縺ｫ髢｢縺吶ｋ莉墓ｧ倥ｒ菫ｮ豁｣縺励◆
	//�ｼ�Java7縺ｮGraphics2D縺ｮ譁�蟄怜�玲緒逕ｻ蜃ｦ逅�縺ｮ繝舌げ縺ｫ繧ゅ→縺･縺乗圻螳壻ｻ墓ｧ假ｼ�
	//
	// 50縺ｧ縺ｮ螟画峩轤ｹ
	//繝ｻ驛ｨ髢�縲√ユ繝ｼ繝悶Ν繧ｿ繧､繝励�√ョ繝ｼ繧ｿ繧ｿ繧､繝励�∵ｩ溯�ｽ繧ｿ繧､繝怜挨縺ｮ螳夂ｾｩ隕∫ｴ�縺ｮ荳�隕ｧ縺栗D鬆�縺ｫ縺ｪ繧九ｈ縺�縺ｫ菫ｮ豁｣
	//繝ｻ繝輔ぅ繝ｼ繝ｫ繝迂D縺ｮ蜈･蜉帛沺縺ｮ蟷�繧呈僑蠑ｵ
	//繝ｻ繝輔ぅ繝ｼ繝ｫ繝我ｸ�隕ｧ荳翫�ｮ繧ｭ繝ｼ繝輔ぅ繝ｼ繝ｫ繝峨ｒ螟ｪ蟄苓｡ｨ遉ｺ縺ｫ縺吶ｋ縺ｨ縺ｨ繧ゅ↓縲，SV蜃ｺ蜉帙〒螟ｪ蟄励ｒ*莉倥↓螟画鋤縺吶ｋ繧ｹ繝�繝�繝励ｒ霑ｽ蜉�
	//繝ｻ荳�驛ｨ縺ｮ繝峨Λ繝�繧ｰ繝峨Ο繝�繝怜ｯｾ雎｡縺ｮ繧ｳ繝ｳ繝昴�ｼ繝阪Φ繝医〒縲√�槭え繧ｹ繧ｪ繝ｼ繝舌�ｼ縺吶ｋ縺�縺代〒繧ｫ繝ｼ繧ｽ繝ｫ縺悟､牙喧縺励※縺�縺溷撫鬘後ｒ菫ｮ豁｣
	//繝ｻ讌ｭ蜍吶ヵ繝ｭ繝ｼ縺ｫ繝弱�ｼ繝峨ｒ霑ｽ蜉�縺励◆蝣ｴ蜷医↓蜑榊屓縺ｫ繝弱�ｼ繝峨↓蟇ｾ縺励※蜈･蜉帙＠縺滓遭隕√′繧ｯ繝ｪ繧｢縺輔ｌ縺ｪ縺�蝠城｡後ｒ菫ｮ豁｣
	//繝ｻ繝�繝ｪ繝ｼ繝薙Η繝ｼ荳翫〒 Ctrl+C,Ctrl+V,Ctrl+X 繧剃ｽｿ縺医ｋ繧医≧縺ｫ縺吶ｋ縺ｨ縺ｨ繧ゅ↓縲∬､�謨ｰ繝弱�ｼ繝峨ｒ蜷梧凾驕ｸ謚槭〒縺阪↑縺�繧医≧縺ｫ縺励◆
	//繝ｻ蜿ｳ繝壹う繝ｳ縺ｧ蛟､繧貞､画峩逶ｴ蠕後↓Ctrl+S繧呈款縺励※繧ゆｸ頑嶌縺阪＆繧後↑縺九▲縺溷撫鬘後ｒ菫ｮ豁｣
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