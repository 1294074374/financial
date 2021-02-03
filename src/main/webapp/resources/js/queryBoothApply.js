window.onload = function onload(){
	getUser();
	getApplyList();
};

function showDIV(){ $("#add-booth").show();
}

function hiddenDIV(){
	var area = $('#area_select option:selected').text();
	var boothName = $('#booth_name').val();
	var state = $("input[type='radio']:checked").val();
	if(state == '可用'){
		state = '1';
	}else{
		state = '2';
	}
	$.ajax({
		url:"/booth/booth/addBooth",
		type : "post",
		dataType : "json",
		data:{
			area :area,
			boothName:boothName,
			state:state
		},
		async : false,
		cache : false,
		success : function(data) {
			if(data.success){
				alert("添加成功");
				location.reload();
			}else{
				alert("添加失败！"+data.Msg);
				$("#add-booth").hide();
			}
		},
	})
	$("#add-booth").hide();
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

function getApplyList(){
	$.ajax({
		url:"/booth/boothApplyAudit/getApplyState",
		type : "post",
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			showData(data);
		},
	});
}

function showData(data){
	var str = "";// 定义用于拼接的字符串
	for (var i = 0; i < data.boothApplyList.length; i++) {
		// 拼接表格的行和列
		var temp = i;
		var time = format(data.boothApplyList[i].createTime,'yyyy-MM-dd HH:mm:ss');
		str = 
			'<div class="th w5 text-center"></div>'+
			'<div class="padding-left-15 th w55">'+data.boothApplyList[i].boothName+' '+data.boothApplyList[i].userDate+' '+data.boothApplyList[i].userTime+'</div>'+
			'<div class="th w25">'+time+'</div>';
			
		if(data.boothApplyList[i].state == '1'){
			str = str +'<div class="th w15" style="color:#D9B300">'+'指导老师审核中'+'</div>';
		} else if(data.boothApplyList[i].state == '2'){
			str = str +'<div class="th w15" style="color:#FF0000">'+'指导老师审拒绝申请'+'</div>';
		}else if(data.boothApplyList[i].state == '3'){
			str = str +'<div class="th w15" style="color:#D9B300">'+'校管理员审核中'+'</div>';
		}else if(data.boothApplyList[i].state == '4'){
			str = str +'<div class="th w15" style="color:#FF0000">'+'校管理员拒绝申请'+'</div>';
		}else if(data.boothApplyList[i].state == '5'){
			str = str +'<div class="th w15" style="color:#00EC00">'+'申请通过'+'</div>';
		}else{
			str = str +'<div class="th w15" style="color:#D9B300">'+'申请审核中'+'</div>';
		}
		// 追加到table中
		$("#applyList").append(str);
		str = "";
	}
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


