$(".sidebar-title").live('click', function() {
	if ($(this).parent(".sidebar-nav").hasClass("sidebar-nav-fold")) {
		$(this).next().slideDown(200);
		$(this).parent(".sidebar-nav").removeClass("sidebar-nav-fold");
	} else {
		$(this).next().slideUp(200);
		$(this).parent(".sidebar-nav").addClass("sidebar-nav-fold");
	}
});

window.onload = function requestData() {
	setDate();
	getUser();
}

function setDate(){
	var moon = '<option value =""></option>';
	var day = '<option value =""></option>';
	for (var i = 1; i <= 12; i++) {
		if (i < 10) {
			moon = moon + ' <option value ="0' + i + '">' + i + '月</option>';
		} else {
			moon = moon + ' <option value ="' + i + '">' + i + '月</option>';
		}
	}
	for (var i = 1; i <= 31; i++) {
		if (i < 10) {
			day = day + ' <option value ="0' + i + '">' + i + '日</option>';
		} else {
			day = day + ' <option value ="' + i + '">' + i + '日</option>';
		}
	}
	$("#user_date_moon").append(moon);
	$("#user_date_day").append(day);
}

function submitBoothApply() {
	var boothArea = $('#booth_area').val();
	var boothNumber = $('#booth_number').val();
	var userDateYear = $('#user_date_year').val();
	var userDateMoon = $('#user_date_moon').val();
	var userDateDay = $('#user_date_day').val();
	var userTime = $('#user_time').val();
	var userName = $('#user_name').val();
	var purpose = $('#purpose').val();
	var phone = $('#phone').val();
	var department = $('#department').val();
	var userDate = userDateYear + '-' + userDateMoon + '-' + userDateDay
	$.ajax({
		url : '/booth/boothApply/insertBoothApply',
		async : false,
		cache : false,
		type : "post",
		dataType : 'json',
		data : {
			boothArea : boothArea,
			boothNumber : boothNumber,
			userDate : userDate,
			userTime : userTime,
			userName : userName,
			purpose : purpose,
			phone : phone,
			department : department
		},
		success : function(data) {
			if (data.success) {
				alert('申请成功!');
				$('#jsForm')[0].reset();
			} else {
				alert('申请失败!' + data.Messg)
			}
		}
	});
}

function getUser(){
	$.ajax({
		url:"/booth/util/getUser",
		type : "post",
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			if(data.success){
				document.getElementById("userName").innerText = data.user.userName;
			}else{
				layout();
			}
		},
	});
}

function layout(){
	window.location.href = '/booth/order/toLogin';
}
