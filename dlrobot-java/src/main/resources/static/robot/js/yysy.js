/*
* @Author: Administrator
* @Date:   2017-12-29 10:46:51
* @Last Modified by:   hp
* @Last Modified time: 2018-01-03 09:56:51
*/
$(function(){
	$(function(){
		$('.welcome .first').animate({
			'margin-left':'500px',
		},1000);
		$('.welcome .second').animate({
			'margin-right':'387px',
		},1000);

		$('.yysy-bottom-con').click(function(){
			this.src='img/next2.png';
			$(this).addClass('active');
		})
	})
})