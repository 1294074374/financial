$(function() {
	$('#xiao-submit-button').click(function() {
		var username = $('#username').val();
		var password = $('#userPassword').val();
		var repassword = $('#userRePassword').val();
		var role = $('input:radio:checked').val();
		var phone = $('#userPhone').val();
		var email = $('#userEmail').val();
		var department = $('#department').val();
		$.ajax({
			url : '/booth/user/register',
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				username : username,
				password : password,
				repassword : repassword,
				role : role,
				phone : phone,
				email : email,
				department : department
			},
			success : function(data) {
				if (data.success) {
					alert('注册成功!');
					window.location.href = '/booth/user/toLogin';

				} else {
					alert('注册失败!' + data.Messg)
				}
			}
		});
	});
});