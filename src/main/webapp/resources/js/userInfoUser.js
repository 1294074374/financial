window.onload = function onload(){
	getUser();
};

function getUser(){
	$.ajax({
		url:"/booth/util/getUser",
		type : "post",
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			if(data.success){
				var time = format(data.user.createTime,'yyyy-MM-dd HH:mm:ss');
				document.getElementById("userName").innerText = data.user.userName;
				document.getElementById("userName2").innerText = data.user.userName;
				document.getElementById("createTime").innerText = time;
			}else{
				layout();
			}
		},
	});
}

function layout(){
	window.location.href = '/booth/order/toLogin';
}

function format(time, format) {
    var t = new Date(time);
    var tf = function (i) {
      return (i < 10 ? '0' : '') + i
    };
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function (a) {
      switch (a) {
        case 'yyyy':
          return tf(t.getFullYear());
          break;
        case 'MM':
          return tf(t.getMonth() + 1);
          break;
        case 'mm':
          return tf(t.getMinutes());
          break;
        case 'dd':
          return tf(t.getDate());
          break;
        case 'HH':
          return tf(t.getHours());
          break;
        case 'ss':
          return tf(t.getSeconds());
          break;
      }
    })
  }
