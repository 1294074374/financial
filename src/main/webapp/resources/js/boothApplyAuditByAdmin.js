window.onload = function requestData(){
	getApplyList();
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
		url : "/booth/boothApplyAudit/queryAdminBoothApplyList",
		type : "post",
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			/* 这个方法里是ajax发送请求成功之后执行的代码 */
			showData(data);// 我们仅做数据展示
		},
		/*error : function(msg) {
			alert("ajax连接异常：" + msg);
		}*/
	});
}

function showData(data) {
	var str = "";// 定义用于拼接的字符串
	for (var i = 0; i < data.boothApplyList.length; i++) {
		// 拼接表格的行和列
		var temp = i;
		str = '<tr>'
			+	'<td>' + ++temp +'</td>'
			+	'<td>' + data.boothApplyList[i].boothName + '</td>'
			+	'<td>' + data.boothApplyList[i].userDate + '</td>'
			+	'<td>' + data.boothApplyList[i].userTime + '</td>'
			+	'<td>' + data.boothApplyList[i].department + '</td>'
			+	'<td>' + data.boothApplyList[i].purpose + '</td>'
			+	'<td>'
			+		'<div id="shenhe'+data.boothApplyList[i].boothApplyId+'" value="'+data.boothApplyList[i].boothApplyId+'">'
			+			'<input  type="radio" name="'+data.boothApplyList[i].boothApplyId+'"value="1">同意</input>&emsp;'
			+			'<input  type="radio" name="'+data.boothApplyList[i].boothApplyId+'"value="2">反对</input>&emsp;'
			+			'<button type="'+'button'+'" onclick="audit('+data.boothApplyList[i].boothApplyId+')">审核</button><br>' 
			+			'<textarea rows="10" cols="30" placeholder="审核意见" id="audit_operation'+data.boothApplyList[i].boothApplyId+'"></textarea>'
			+		'</div>'
			+	'</td>'
			+ '</tr>';
		// 追加到table中
		$("#tab").append(str);
	}
}

function audit(boothApplyId){
	var auditFinalState=$('input[name="'+boothApplyId+'"]:checked').val();
	var audit_operation = "audit_operation" + boothApplyId;
	var auditFinalOpinion = $('#'+audit_operation+'').val();
	$.ajax({
		url : '/booth/boothApplyAudit/auditByAdmin',
		async : false,
		cache : false,
		type : "post",
		dataType : 'json',
		data : {
			auditFinalState : auditFinalState,
			auditFinalOpinion : auditFinalOpinion,
			boothApplyId : boothApplyId
		},
		success : function(data){
			if(data.success){
				alert('审批完成!');
				window.location.reload();
			}else{
				alert(data.Messg);
				window.location.reload();
			}
		}
	})
}
	