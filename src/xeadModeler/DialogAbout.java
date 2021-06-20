package xeadModeler;

/*
 * Copyright (c) 2019 WATANABE kozo <qyf05466@nifty.com>,
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
	public static final String COPYRIGHT = "Copyright 2021 DBC Ltd.";
	public static final String URL_DBC = "http://dbc.in.coocan.jp/";
	public static final String FULL_VERSION = "V1.R4.M49";
	// 1.4.49�ł̕ύX�_
	//�EJava11��������JComboBox��JList�ɂ��đ��̌^�ɐݒ肵��
	//�EJava11��������OutputFormat��XMLSerializer��LS�n�ɒu��������
	//
	// 1.4.48�ł̕ύX�_
	//�IOPanel��IOSpool�̃m�[�h���R�s�[����ۂ�FormID���R�s�[�����悤�ɂ���
	//����ٕ��̓��|�[�g��ID�̏d���`�F�b�N��g�ݍ��ނƂƂ��ɁAIOTable��Position�̈��������P����
	//�DDL�̃C���|�[�g�@�\�����P����
	//
	// 1.4.47�ł̕ύX�_
	//�DFD��̃v���Z�X�ȊO�̃m�[�h�^�C�v��ύX�ł���悤�ɂ���
	//��f�[�^���f���̃p�l�����C�A�E�g�����P����
	//�Linux�nOS�ł��g����悤�ɁA�ꕔ�̃R���e�L�X�g���j���[�̋N���X�e�b�v���C������
	//
	// 1.4.46�ł̕ύX�_
	//�DDL�̊O���L�[�����Ɨ��������iDDL�ꗗ��p�����e�[�u���ꊇ�쐬�G���[��������邽�߁j
	//
	// 1.4.45�ł̕ύX�_
	//��@�\�ʓ��o�͒�`�Ƀt�H�[��ID��݂��A�摜�t�@�C�����̒T�����W�b�N���t�H�[��ID�ɘA��������
	//���L�ύX�ɂƂ��Ȃ��ăw���v��`���[�g���A���̊Y�����������肵��
	//
	// 1.4.44�ł̕ύX�_
	//��t�@�C�������~�}�[�N�Ńp�X�w��ł���悤�ɂ���
	//��Ɩ��t���[�̃T�C�Y����Ɋւ���ׂ����o�O���C������
	//��Ɩ��t���[�̃X���C�h������ҏW���₷������
	//
	// 1.4.43�ł̕ύX�_
	//��@�\��`�Ɓu�p��ƃ��[���v�Ɋւ���O���t�@�C���̈����d�l�����P����
	//��L�͈͂ɂ킽���ă��C�A�E�g�����P����
	//
	// 1.4.42�ł̕ύX�_
	//��@�\�ʓ��o�͒�`�̕��я����h���b�O���ĕύX�ł���悤�ɂ���ƂƂ��ɁA�����̈����d�l�����P����
	//
	// 1.4.41�ł̕ύX�_
	//��uDocFileFormat�v���V�X�e����`�Ƃ��Ďw��ł���悤�ɂ����ixteamdl.properties�ł͊������g���ɂ������߁j
	//
	// 1.4.40�ł̕ύX�_
	//�xteamdl.properties�ŁuDocFileFormat�v���w��ł���悤�ɂ���
	//
	// 1.4.39�ł̕ύX�_
	//�EDFD��̃V�X�e�����̈ʒu�ݒ�Ɋւ�������C������
	//�EDFD��̃C�x���g�̃e�L�X�g��<EOL>���g����悤�ɂ���
	//�E�e�[�u�����o�͏�̃e�[�u�����̕\���d�l�����P����
	//�E�u�f�[�^�^�C�v�̐����v�Ńt�B�[���h�ꗗ�̌����������w��ł���悤�ɂ���
	//�E�������̌x�����b�Z�[�W�ɂ��āu����͕\�����Ȃ��v���w��ł���悤�ɂ���
	//
	// 1.4.38�ł̕ύX�_
	//�E�e�[�u������Camel�\�L�̕ϊ����W�b�N�����P����
	//�ECreateTable���ł̃t�B�[���h�R�����g�̐ݒ�d�l�����P����
	//
	// 1.4.37�ł̕ύX�_
	//�E���𑜓x���ɍ��킹�ă��C���t�H���g�̕����T�C�Y�̏ȗ��l��16����18�ɕύX����
	//�Eproperties�t�@�C���Ń��C���t�H���g�̕����T�C�Y(MainFontSize)��ݒ�ł���悤�ɂ���
	//�Eproperties�t�@�C���Ńf�[�^���f���T�C�Y�̏����l(InitialModelSize)��ݒ�ł���悤�ɂ���
	//�E�t�@�C�����ٌ����̑Ώ۔͈͂ɁA�Ɩ��t���[��̃V�X�e�����E�̃L���v�V�����ʒu���܂߂�
	//�E�݌v���o�͂őΏے�`���P�����܂܂�Ȃ��ꍇ�A�G�N�Z���\���ŃG���[�ɂȂ�����C������
	//
	// 1.4.36�ł̕ύX�_
	//�E�Ɩ��t���[��̃V�X�e�����E�̃L���v�V�����ʒu��ݒ�ł���悤�ɂ���
	//�EIO�C���[�W�̃J�[�\���ʒu�\���̃o�O���C������
	//
	// 1.4.35�ł̕ύX�_
	//�E�e�[�u�����̃c���[�r���[��A����уe�[�u���ꗗ��ł̕\���l�������P����
	//�E���l�n�̃f�[�^�^�C�v�̌����\���l�������P����
	//�E�u�c�[���bDDL�̐ݒ�v���@�\��������
	//
	// 1.4.34�ł̕ύX�_
	//�E��`�t�@�C���Ԃ̍��ك��|�[�g�o�͂̍ۂɁu����DLL�v���o�͂ł���悤�ɂ���
	//
	// 1.4.33�ł̕ύX�_
	//�EIO�C���[�W��ł̃y�[�X�g����ł̃o�O���C������
	//�E�t�B�[���h�Ƀf�[�^�^�C�v��ݒ肵���ꍇ�A�t�B�[���hID�i�G�C���A�X�j�������ݒ肳���悤�ɂ���
	//
	// 1.4.32�ł̕ύX�_
	//�EMac�Ńt�@�C�����w��p�_�C�A���O�̓��삪�s����ł�����ɑΉ�����
	//�E�d�l���o�͂Ńe�[�u�����o�͍��ڂ��u�����N���ƕs�v�ȕ������o�͂����o�O���C������
	//
	// 1.4.31�ł̕ύX�_
	//�E�Q�ƃt�@�C���̈����Ɋւ���ׂ����o�O���C������
	//�E�e��`�̐�����@NOTE@�̗\����u���΁A���ߕt���ڂƂ��ăc���[�r���[���*�t���ŕ\�������悤�ɂ���
	//�E�c�[�����j���[�ɒ��ߕt���ڂ̈ꗗ�o�͋@�\��݂���
	//�ETXT�t�@�C������̃t�B�[���h��`�̃C���|�[�g�����̃o�O���C������
	//
	// 1.4.30�ł̕ύX�_
	//�EMac�Ŏ��s�����ۂ̃o�b�N�A�b�v�����̖����C������
	//�E�f�[�^���f���̊g��k���Ɋւ���o�O���C������
	//
	// 1.4.29�ł̕ύX�_
	//�E�f�[�^���f�����g��k���ł���悤�ɂ���
	//�ESVG�o�͈͂̔͐ݒ�̃��W�b�N�����P����
	//�EUndo/Redo�p���j���[�A�C�e���̕\�L�����P����
	//�E�o�b�N�A�b�v���Ɏw��t�H���_���쐬����悤�ɂ���
	//
	// 1.4.28�ł̕ύX�_
	//�E�e�[�u���ꗗ�̗���FLD(�t�B�[���h��)��������
	//�E�e�[�u�����o�͒�`�Ɂu�|�W�V�����v�̑�����ǉ�����
	//�E�p�l���A�X�v�[���̕\�������ꂼ��t�H�[���A���[�ɕύX����
	//
	// 1.4.27�ł̕ύX�_
	//�E�Ɩ���`�̃A�N�V�����ɋ@�\��`�������N�ł���悤�ɂ���
	//
	// 1.4.26�ł̕ύX�_
	//�E�t�@�C���Ԃ̍��ٕ\���̂��߂̃I�v�V�������t�@�C�����j���[�ɐ݂���
	//�Eproperties�̐ݒ�ɂ��X�V�O�̎����o�b�N�A�b�v�@�\��݂���
	//
	// 1.4.25�ł̕ύX�_
	//�E�t�B�[���h���X�g�̃e�[�u���ɑS�I��p�`�F�b�N�{�b�N�X��݂���
	//�E�e�[�u����`�Ƌ@�\��`�̃y�C���ɃX�N���[���y�C����g�ݍ���
	//�E�f�[�^���f���̕ҏW���ł��C���X�^���X��\���ł���悤�ɂ���
	//�E�ꗗ���͂ł̃e�L�X�g�t�@�C������̃C���|�[�g�̃��b�Z�[�W�����P����
	//
	// 1.4.24�ł̕ύX�_
	//�E�e�[�u����`�ɃC���f�b�N�X��o�^�ł���悤�ɂ���
	//�E�C���f�b�N�X��`�ɂ��ƂÂ���DDL���������悤�ɂ���
	//�E�C���|�[�g������e�[�u����`�o�͂��C���f�b�N�X��`�ɑΉ�������
	//�E�@�\��`�́u�O�������v�̎g����������P����
	//
	// 1.4.23�ł̕ύX�_
	//�E�u�ꗗ�`���Œǉ��v�ł́uTXT�ǂݍ��݁v�̃��W�b�N�����P����
	//�E�X�v���b�V���̌`�������P����
	//�E�@�\��`�Ɂu�O�������v�̍��ڂ�݂���
	//
	// 1.4.22�ł̕ύX�_
	//�EMacOS�ő��쉇����\���ł��Ȃ������C������
	//�E�ꕔ�̃A�C�R���̔w�i�������łȂ����������C������
	//�E�Ɩ��t���[��̃V�X�e�����̕\���`�������P����
	//�E�e�[�u�����o�͒�`���Ɩ��A�N�V�����Ɋ֘A�t������΂��̊T�v�����o�̓C���[�W�Ƃ��Ď������悤�ɂ���
	//�E�e�[�u���C���X�^���X�̍X�V���W�b�N�����P����
	//
	// 1.4.21�ł̕ύX�_
	//�E�c�[������X-TEA�@Modeler�ɕύX����
	//�E�e�[�u�������Ƃ��āu�G�C���A�X�v��ǉ�����
	//�E�����E�u���Ώۍ��ڂƂ��āA�e�[�u���́u�G�C���A�X�v�Ɓu�Q�Ɛ�t�@�C���v��g�ݍ���
	//�E�e�[�u���̊֘A�����������d�Ȃ����ꍇ�ɂɂ��񂾈�ۂɂȂ�����C������
	//�E�f�[�^���f���̌������ׂ̍������������P����
	//�E�e�[�u����`���Ɂu�Q�ƌ��t�@�C���v���ꗗ����悤�ɂ���
	//�E�Ɩ��t���[��ŃV�X�e�����E��������\������������C������
	//�E�u�p��ƃ��[���v�Ɓu�ێ痚���v��UNDO����̃o�O���C������
	//�E�������̖����ɉ��s�f�[�^���������ꍇ�̍X�V����̃o�O���C������
	//
	// 1.4.20�ł̕ύX�_
	//�E�X���C�h�V���[�̍Ō�ɍŏI�y�[�W�ł��邱�Ƃ������p�l���������悤�ɂ���
	//�EDDL�̈ꗗ������DDL�̐ݒ�_�C�A���O����Ɨ�������
	//�E�e�[�u����`�̓��������𐷂荞��
	//�E�e�[�u���ꗗ�o�͂Ɂu�Q�Ɛ�t�@�C���v�̗���g�ݍ���
	//�E�����p�_�C�A���O�Ɂu�S�́v�̃`�F�b�N�{�b�N�X��u����
	//
	// 1.4.19�ł̕ύX�_
	//�EIO�C���[�W�̃J�[�\���ʒu�v�Z���[�`���ׂ̍����o�O���C������
	//�E�����E�u���_�C�A���O�ł̃e�L�X�g�t�B�[���h�Ƀt�H�[�J�X�����ꍇ�A�������S�I��������悤�ɂ���
	//�EJava1.8�Ńf�[�^���f���̊֘A���̈ʒu��ύX�ł��Ȃ��Ȃ��Ă��������C������
	//�ECREATE TABLE���̐������W�b�N�����P����
	//
	// 1.4.18�ł̕ύX�_
	//�EJava1.8�ɑΉ����邽�߂ɁAsort������comparator����comparable�x�[�X�ɏC������
	//�ECtrl+R�ŁAIO�p�l���̏����ݒ�������ł���悤�ɂ���
	//�EKEY��`�̍폜�ɂƂ��Ȃ�Undo�֘A�����ƕύX�����e�L�X�g�̏o�̓��W�b�N�̃o�O���C������
	//�E�����E�u�������Ńe�[�u����ID�������Ώۂ���͂���Ă����o�O���C������
	//
	// 1.4.17�ł̕ύX�_
	//�EID���u�����N�ł���悤�ȃf�[�^�^�C�v��e�[�u���^�C�v���܂ޏꍇ�ɋN����xead�t�@�C���̃C���|�[�g�����̃o�O���C������
	//�E�ꗗ�`���ł̒ǉ��ł̃t�@�C���Ǎ����ACSV����TXT(�^�u��؂�)�ɕύX����ƂƂ��ɁA�E�v����荞�߂�悤�ɂ���
	//�E�ꗗ�`���ł̒ǉ��ł̃t�@�C���Ǎ��ŁA���O�œ��肳�������v�f��ID��E�v���X�V����悤�ɂ���
	//
	// 1.4.16�ł̕ύX�_
	//�EDDL�ݒ胍�W�b�N�̃o�O���C������
	//�E�ʖ��ŕۑ������ꍇ�Ɂu�ʂ̃��[�U�ɂ���čX�V���ꂽ�v�̃��b�Z�[�W��������Ă��܂��o�O���C������
	//�E�Ɩ��t���[�̃t���[�ǉ��_�C�A���O�ł́A�v���Z�X���X�g�̑I���Ɋւ���o�O���C������
	//�ESQL�̃C���|�[�g�����ŁA�������̕������F������悤�ɂ���
	//�E�f�[�^���f���ɃO���b�h�������ƂƂ��ɁA���񏈗��ł���ɉ����悤�ɂ���
	//�E�t�B�[���h�����Ƃ��āu�X�V�s�v�̃`�F�b�N�{�b�N�X��ǉ�����
	//�E�f�[�^���f���ƋƖ��t���[�ɂ��āA�Q��ނ̔z�F�p�^�[������I�ׂ�悤�ɂ���
	//
	// 1.4.15�ł̕ύX�_
	//�E�c�[�����j���[�uDDL�̐ݒ�Əo�́v�̃_�C�A���O�Ɂu�ǉ��p�����[�^�v�Ɓu�Q�Ɛ���̖����v��g�ݍ��ނƂƂ��ɁA��`�t�@�C���ɐݒ��ۑ�����悤�ɂ���
	//�E�r���I�T�u�N���X�֌W�̊֘A���̕`�惍�W�b�N�����P����
	//�E�f�[�^���f���̃C���X�^���X�ҏW�@�\�����V����ƂƂ��ɁA�X���C�h�V���[�@�\��g�ݍ���
	//
	// 1.4.14�ł̕ύX�_
	//�EXEAD�t�@�C���̃C���|�[�g�@�\�ŁA�^�[�Q�b�g�v�f��V�K�쐬�ł���悤�ɂ���
	//�E�Ɩ��t���[�̕ύX�_�C�A���O�ŁA�o���̃v���Z�X��ύX�ł���悤�ɂ���
	//�E�Ɩ��t���[�̕ύX�_�C�A���O�ŁA�X���C�h�����̕ύX�Ɍ㑱�v�f���ǐ�����悤�ɂ���
	//�E�p��ƃ��[����HTML�t�@�C�����w��ł���悤�ɂ���
	//�E�t�@�C���X�V���ɑ����[�U�ɂ��X�V���`�F�b�N����悤�ɂ���
	//
	// 1.4.13�ł̕ύX�_
	//�E�f�[�^���f����SVG�o�͂ɂ��āA�z�F�ݒ�����P
	//�E�Ɩ��t���[�́u�v���Z�X�v�Ɓu��́E�V�X�e���v�ɂ��ĕ\���ݒ�����P
	//�E�Ɩ��t���[�̃t���[�ǉ��_�C�A���O�ŁA�E���ƃ����N���ꂽ�u��́E�V�X�e���v�̗v�f�����\������Ȃ������C��
	//
	// 1.4.12�ł̕ύX�_
	//�E�t�B�[���h��`�ꗗ�ゾ���łȂ��A�X�̃t�B�[���h��`��ł��u���f���\���v��ݒ�ł���悤�ɂ���
	//�E�Ɩ��t���[�ƃf�[�^���f���̉摜�o�͂ɂ��āA�x�N�^�[�`��(SVG)�ɑΉ�����ƂƂ��ɁA���ꂼ��̕`�惍�W�b�N�����������
	//�Exeaf�̃L�[�����e�[�u���ɂ��āA�C���|�[�g�����̃o�O���C������
	//
	// 1.4.11�ł̕ύX�_
	//�E�Ɩ��t���[�̃C���|�[�g�����ɂ����ăt���[�^�C�v����荞�܂�Ȃ����������C������
	//�Exeaf�̃C���|�[�g�����ł̃t�B�[���h�̕��я��̐ݒ�~�X���C������
	//
	// 1.4.10�ł̕ύX�_
	//�EXEAD Driver�p�̃V�X�e����`�t�@�C��(*.xeaf)����C���|�[�g�ł���悤�ɂ���
	//�E�X���C�h�V���[�Ń}�E�X�N���b�N����ƉE���L�[�Ɠ��l�Ɏ��y�[�W�ɐi�ނ悤�ɂ���
	//�E�X���C�h�V���[�̕\���ʒu�ƃT�C�Y��e�̃t���[���ɍ��킹��悤�ɂ���(�v���W�F�N�^�ɂ��܂��\�����邽�߂̑[�u�j
	//�EUI�C���[�W�̋�`�I�����W�b�N���t�H���g�ϑΉ��ɂȂ��Ă��Ȃ����������C������
	//
	// 1.4.9�ł̕ύX�_
	//�E�����񑖍��p�_�C�A���O�Łu�Ɩ��t���[�v�̃`�F�b�N�{�b�N�X���B��Ă��������C������
	//�E�E���ꗗ�Ɂu�S���Ɩ����v�̗��ǉ�����
	//�E�u�p��ƃ��[���v�̃��C�A�E�g�����������
	//�E�u�f�[�^�^�C�v�̐����v�̃_�C�A���O������ꍇ�̃J�[�\���ݒ�̖����C������
	//
	// 1.4.8�ł̕ύX�_
	//�E�f�[�^���f���̔ĉ��֌W�̕`�����W�b�N�����������
	//�E�ҏW�r���ŕۊǂ�����ł��A�ύX�����̒ǉ����ɂ͕ۊǈȑO�̃A�N�V�������܂߂ď����ݒ肳���悤�ɂ���
	//�E�ύX�����̒ǉ����̏����ݒ�Łu�p��ƃ��[���v�������Ă��������C������
	//
	// 1.4.7�ł̕ύX�_
	//�E�f�[�^���f���̔ĉ��֌W�ɂ����Ĕr������\�����邽�߂̕`�����W�b�N��g�ݍ���
	//�E�u�p��ƃ��[���v�́u�^�C�g���v�������Ή��t�B�[���h�ɂ���
	//�E�u�݌v���o�́v�Ɓu�}�g���b�N�X���X�g�o�́v��Excel�o�͂�xlsx�̌`���ɍ����ւ���
	//
	// 1.4.6�ł̕ύX�_
	//�E�V�X�e���m�[�h�Ɂu�p��ƃ��[���v�̃^�u��݂���
	//�E�C���|�[�g�����̃o�O�ɂ��s�������C�����邽�߂̃��j���[�I�v�V������g�ݍ���
	//
	// 1.4.5�ł̕ύX�_
	//�E�C���X�^���X�\�����L�����Z�������Ƃ��e�[�u�������̈ʒu�ɖ߂�Ȃ������C������
	//�E�f�[�^���f���̃R���e�L�X�g���j���[�́u�e�[�u�����̐ݒ�v���u�e�[�u���̐���v�ɓ�������
	//�E�f�[�^�t���[�}�ɐE�����h���b�O���h���b�v���āu�V�X�e���E��́v�̃m�[�h���쐬�ł���悤�ɂ���
	//�E�C���|�[�g�����̃p�t�H�[�}���X�����P����ƂƂ��ɍׂ����o�O���C������
	//�E�u�f�[�^�^�C�v�̐����v�̃c�[�����j���[�A�C�e����ǉ�����
	//
	// 1.4.4�ł̕ύX�_
	//�E�t�B�[���h�ꗗ��Ń��f���\����ݒ�ł���悤�ɂ���ƂƂ��ɁA�t�B�[���h��`�p�l���ォ��\���ݒ荀�ڂ��͂�����
	//�E�p�l���C���[�W��̃J�[�\���̐F��̈�̕����F�ɍ��킹��
	//�E�p�l���C���[�W��ŉ������������߂̃V���[�g�J�b�g(Ctrl+U)��݂���
	//�E�p�l���C���[�W�̃t�H���g�T�C�Y��properties�w��ł̌Œ�ɂ���
	//�E�C���X�^���X�\���̍s���̖����C������
	//�E�N������Java�̃o�[�W�������`�F�b�N����悤�ɂ���
	//
	// 1.4.3�ł̕ύX�_
	//�E�p�l���^���[�C���[�W��̋�`�I��p�K�C�h������Ă��������C��
	//�E�p�l���^���[�C���[�W�̉摜�t�@�C���T���t�H���_��properties�Ŏw��ł���悤�ɂ���
	//�E�Ɩ���`�Ńp�l���^���[�C���[�W�̉摜�t�@�C����\�������ꍇ�A�ꕔ���B��邱�Ƃ̂�������C������
	//properties�̓ǂݎ�胍�W�b�N�����P����
	//
	// 1.4.2�ł̕ύX�_
	//�E�Ɩ���`�y�C���A�t�B�[���h��`�y�C���A�����񌟍��_�C�A���O�ɂ��Ăw�f�`�T�C�Y�ł��{���\�ɂ���
	//�Eproperties�ςɂȂ��Ă��Ȃ������e�[�u���ꗗ�y�C����̈ꕔ�A����у^�X�N��`�y�C����̈ꕔ�̃t�H���g�ɑΉ�����
	//�E���o�̓C���[�W�̃t�H���g�T�C�Y�̏����l��properties�̉ςɂ����i���w��̏ꍇ��16�j
	//�E�Ɩ��t���[��Łu�V�X�e�����E�v���ړ������Ƃ��ɃV�X�e�����̈ꕔ�������邱�Ƃ̂�������C������
	//�E�p�l���^���[�C���[�W�̃T�C�Y�ύX�p�K�C�h�̈ʒu������Ă��������C��
	//�E�p�l���^���[�C���[�W��̃L�����b�g�F��w�i�F�ɍ��킹�ĕω�������悤�ɂ���
	//�E�p�l���^���[�C���[�W��UNDO/REDO�������Ȃ��Ȃ��Ă��������C��
	//
	// 1.4.1�ł̕ύX�_
	//�E�Ɩ��t���[�ƃf�[�^���f����ł̋�`�I���K�C�h�̐F�𐅐F���甒�ɕύX����
	//�Exeadmdl.properties�̌����������A(1)��`�t�@�C��(*.xead)������t�H���_�A(2)xeadmdl.jar������t�H���_�A�ɂ���
	//
	// 1.4.0�ł̕ύX�_
	//�Exeadmdl.properties�Ńt�H���g�����w��ł���悤�ɂ���
	//�E�ꕔ�̗v�f�������A�t�H���g�T�C�Y��12p����16p�ɕύX����
	//�EIO�C���[�W�̃t�H���g�T�C�Y�̎w��͈͂�10�`14����10�`20�Ɋg������
	//�E�Ɩ��t���[�ƃf�[�^���f���ɂ��āA�ꕔ�̃e�L�X�g�̃t�H���g�T�C�Y��������������悤�ɂ���
	//�E�Ɩ��t���[�̃C�x���g�ɂ��ĕ\���ݒ�l�������P����
	//�E�Ɩ��t���[��ŕ����̃v���Z�X��X�g���[�W����`�I�����ē����Ɉړ��ł���悤�ɂ���
	//�E�f�[�^���f����̃e�[�u���ɂ��āA�t�B�[���h�Q����ɂP�i�Ŏ����悤�ɂ����i�����i�ŕ\������ƃC���X�^���X�������ɂ����Ȃ邽�߁j
	//�E�f�[�^���f����̑S�e�[�u���̕��������ݒ肷�邽�߂̃R���e�L�X�g���j���[��݂���
	//�E�f�[�^���f����ŕ����̃e�[�u��������`�I�����ē����Ɉړ��ł���悤�ɂ���
	//
	// 57�ł̕ύX�_
	//�E�Ɩ��t���[��̃X�g���[�W�ƃf�[�^�t���[�ɂ��āA�_�u���N���b�N����΋L�q�ύX�_�C�A���O�������悤�ɂ���
	//
	// 56�ł̕ύX�_
	//�E�t�@�C���I���_�C�A���O�̊g���q�ݒ�����P����
	//�E�f�[�^�^�C�v�ʂ̃t�B�[���h�ꗗ�\���̃p�t�H�[�}���X�����P����
	//
	// 55�ł̕ύX�_
	//�E�ێ痚���̏����l�ɓ�����e�̍s���܂܂�Ȃ��悤�ɂ���
	//�E�ێ痚���̓����̋�؂蕶�����h�b�g����A���_�[�o�[�ɕύX����
	//�ESQL�C���|�[�g�Łw�����̃t�B�[���h�R�����g���t�B�[���h��`���Ƃ݂Ȃ��x���`�F�b�N����Ƃo�j����������Ȃ��o�O���C��
	//�E�t�B�[���h���̃f�[�^�^�C�v�̌����\���`�������P����
	//�E�f�[�^���f����Ɩ��t���[��̃q���g�\���������ɂȂ�Ȃ��悤�ɂ���
	//
	// 54�ł̕ύX�_
	//�E�Ɩ��t���[��̃v���Z�X���_�u���N���b�N����΋Ɩ���`�ɃW�����v����悤�ɂ���
	//�E�f�[�^���f����̃e�[�u�����_�u���N���b�N����΃e�[�u����`�ɃW�����v����悤�ɂ���
	//�E�Ɩ���`��̓��o�̓C���[�W���_�u���N���b�N����΋@�\��`�ɃW�����v����悤�ɂ���
	//�E�����_�C�A���O�ł̌������ʂ̖��̂ɂh�c���܂߂�悤�ɂ���
	//�ECRUD�}�����o�͂��邽�߂ɁA�c�[�����j���[�Ɂu�}�g���b�N�X���X�g�o�́v��ǉ�����
	//�E�Ɩ���`�̓��o�̓C���[�W�����̃��x���̒������e�L�X�g���ɍ��킹��悤�ɂ���
	//�EUndo�̉񐔂𖳐����ɂ���ƂƂ��ɁAUndo�����g���ĕێ痚���̏����l��ݒ肷��悤�ɂ���
	//�E�Ɩ��^�C�v�̑g�ݍ��݂̉e���ňꕔ��Undo���삪���܂������Ȃ������o�O���C������
	//
	// 53�ł̕ύX�_
	//�E�Ɩ��^�C�v�̉e���ŋƖ���`�̍X�V���[�`�����ُ�I�����邱�Ƃ̂���o�O���C������
	//
	// 52�ł̕ύX�_
	//�E�Ɩ���`�̃C�x���g���u�����N�ɂ���΁A�Ɩ��t���[��́u�����}�[�N�v���\������Ȃ��悤�ɂ���
	//�iGithub��ł�1.3.51�Ŕ��f�ρj
	//�E�Ɩ���`���̋@�\UI�\���̍����̃L���v�V�������@�\ID�{���o��ID����@�\ID�{���o�͖��ɕύX����
	//�E�f�[�^���f����Ɩ��t���[�̈���o�͂ɂ����āA���{����ł�ID��S�p�����ɕϊ����ďo�͂���悤�ɂ���
	//�iJava7��Graphics2D�̕�����`�揈���̃o�O�ɂ��ƂÂ��b��d�l�j
	//�E�f�[�^���f������������ꍇ�A�e�[�u����CRUD�\���̉E�����؂������C��
	//�E�Ɩ���`���Ɂu���̋Ɩ����܂ދƖ��t���[�v�����X�g���ăW�����v�ł���悤�ɂ���
	//�E�Ɩ��t���[��Ńm�[�h���R�s�[����ƃ��x���̂Q�s�ڂ��R�s�[����Ȃ������C��
	//�E�V�X�e���ϐ��Ƃ��āu�Ɩ��^�C�v�v��ǉ����āA�Ɩ���`�őI���ł���悤�ɂ���
	//�E�Ɩ��t���[��Ńf�[�^�t���[��ǉ�����_�C�A���O�ŁA�m�[�h�̂Q�s�ڂ��m�[�h���Ɋ܂߂�悤�ɂ���
	//�E�Ɩ���`�̃A�N�V�������R�s�[����ۂɁu���ʃA�N�V�����v���܂߂ăR�s�[���邩�ǂ�����I�����邽�߂̃_�C�A���O��\������悤�ɂ���
	//�E�f�[�^�t���[�̃A�C�R���̓E�v�u�d�C�M���v���u���,�f�[�^�v�ɕύX����
	//�E�Ɩ���`�̃A�N�V�����ł̂h�n�̃��x���ɋ@�\�^�C�v�����܂߂�悤�ɂ���
	//�E�t�B�[���h�ꗗ�̈ꗗ�n�����̃��X�|���X��啝�ɉ��P����
	//
	// 51�ł̕ύX�_
	//�E�Ɩ��t���[���C���|�[�g�ł���悤�ɂ���
	//�E�Ɩ��t���[��̃v���Z�X�̕ύX�_�C�A���O�ŁA�C�x���g�ʒu�����ݒl�Ŏ�����Ȃ������C��
	//�E�Ɩ��t���[�́u�v���Z�X�v�ȊO�̃m�[�h�ŁA�t���[�Ɠ��l�Ɂu���x���v���Q�s�u����悤�ɂ���
	//�E�Ɩ��t���[�𕡎ʂ����ꍇ�A�m�[�h�̂Q�s�ڂ̃��x���ƃv���Z�X�E�m�[�h�̃C�x���g�ʒu���R�s�[����Ȃ������C��
	//�E�f�[�^���f����Ńe�[�u�����\���ɂ��Ă��C���X�^���X���\������邱�Ƃ���������C��
	//�E�f�[�^���f����ł̃e�[�u���֘A�ǉ��p�_�C�A���O���́A�V�K�t�B�[���h�ǉ�����ɂ���������h�c�̐ݒ胍�W�b�N�̃o�O���C������
	//�i�����t�B�[���h��ǉ�����ۂɓ��������h�c��^����悤�ɂȂ��Ă����j
	//�E�f�[�^���f���̃e�[�u���֘A���`�揈���ɂ����āA�����Ȋ֘A���f�[�^�����݂���ꍇ�Ɏ����I�ɂ�����폜����悤�ɂ���
	//�E�f�[�^���f����Ɩ��t���[�̈���o�͂ɂ����āA�L���v�V�����̔z�u�Ɋւ���d�l���C������
	//�iJava7��Graphics2D�̕�����`�揈���̃o�O�ɂ��ƂÂ��b��d�l�j
	//
	// 50�ł̕ύX�_
	//�E����A�e�[�u���^�C�v�A�f�[�^�^�C�v�A�@�\�^�C�v�ʂ̒�`�v�f�̈ꗗ��ID���ɂȂ�悤�ɏC��
	//�E�t�B�[���hID�̓��͈�̕����g��
	//�E�t�B�[���h�ꗗ��̃L�[�t�B�[���h�𑾎��\���ɂ���ƂƂ��ɁACSV�o�͂ő�����*�t�ɕϊ�����X�e�b�v��ǉ�
	//�E�ꕔ�̃h���b�O�h���b�v�Ώۂ̃R���|�[�l���g�ŁA�}�E�X�I�[�o�[���邾���ŃJ�[�\�����ω����Ă��������C��
	//�E�Ɩ��t���[�Ƀm�[�h��ǉ������ꍇ�ɑO��Ƀm�[�h�ɑ΂��ē��͂����E�v���N���A����Ȃ������C��
	//�E�c���[�r���[��� Ctrl+C,Ctrl+V,Ctrl+X ���g����悤�ɂ���ƂƂ��ɁA�����m�[�h�𓯎��I���ł��Ȃ��悤�ɂ���
	//�E�E�y�C���Œl��ύX�����Ctrl+S�������Ă��㏑������Ȃ����������C��

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