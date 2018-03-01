/**
 * Amaze表单验证
 * 
 * @param $
 *            jQuery
 */
!function($) {
	"use strict";

	var AmForm = {};

	/**
	 * Amaze验证的方法
	 * 
	 * @param $form
	 * @param submitCallback
	 *            例如： function mySubmit($form){
	 *            $form.ajaxSubmit({url:"abc.do",type:"post",success:function(){}}); }
	 */
	function validate($form, submitCallback) {
		var $tooltip = $('<div id="vld-tooltip"></div>');
		$tooltip.appendTo(document.body);

		$form.validator({
			submit : function() {
				var formValidity = this.isFormValid();
				if (formValidity) {
					$tooltip.hide();
					if (submitCallback) {
						submitCallback($form);
					}
				}
				return false;
			}
		});
		var validator = $form.data('amui.validator');
		$form.on('focusin focusout', '.am-form-error input', function(e) {
			if (e.type === 'focusin') {
				var $this = $(this);
				var offset = $this.offset();
				var msg = $this.data('foolishMsg')
						|| validator.getValidationMessage($this
								.data('validity'));

				$tooltip.text(msg).show().css({
					left : offset.left + 10,
					top : offset.top + $(this).outerHeight() + 10
				});
			} else {
				$tooltip.hide();
			}
		});
	}

	AmForm.validate = validate;

	window.AmForm = AmForm;

}($);