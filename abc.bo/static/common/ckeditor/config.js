/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';
	
	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
	// 엔터시 <br> 태그
	config.enterMode = CKEDITOR.ENTER_BR;
	// 쉬프트+엔터시 <p> 태그
    config.shiftEnterMode = CKEDITOR.ENTER_P;
    
    config.filebrowserImageUploadUrl= "/cmm/editor/image/upload";
    config.image_previewText = " ";
    
    config.height = 400;

	config.extraPlugins = 'youtube';
	config.youtube_responsive = true;
	config.youtube_related = false;
	
	// 태그안의 값이  없더라도  지워지지 않게 추가
	CKEDITOR.dtd.$removeEmpty['i'] = false;
	CKEDITOR.dtd.$removeEmpty['span'] = false;
	CKEDITOR.dtd.$removeEmpty['label'] = false;
	
	// html tag filter disabled   
    config.allowedContent = true;
};
