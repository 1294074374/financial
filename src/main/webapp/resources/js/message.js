window.onload = function onload(){
	getUser();
	getBoothList();
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


function getBoothList(){
	$.ajax({
		url:"/booth/booth/getBoothList",
		type : "post",
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			showBoothData(data);
		},
	});
}

function showBoothData(data){
	var str = "";// 定义用于拼接的字符串
	for (var i = 0; i < data.boothList.length; i++) {
		// 拼接表格的行和列
		var temp = i;
		str = 
			'<div class="th w5 text-center"></div>'+
			'<div class="padding-left-15 th w55">'+data.boothList[i].area+data.boothList[i].boothName+'</div>';
			
		if(data.boothList[i].state == '1'){
			str = str +'<div class="th w25" style="color:#00BB00">'+'可申请'+'</div>';
		} else if(data.boothList[i].state == '2'){
			str = str +'<div class="th w25" style="color:#FF0000">'+'已禁用'+'</div>';
		}else if(data.boothList[i].state == '3'){
			str = str +'<div class="th w25" style="color:#D9B300">'+'已申请'+'</div>';
		}
		str = str + '<div class="th w15">'
		  //+		'<button onclick=updateBooth('+data.boothList[i].boothId+','+data.boothList[i].boothName+','+data.boothList[i].area+','+')>修改</booth>'
		+		'<button onclick=updateBoothHtml('+data.boothList[i].boothId+')>修改</booth>'  
		+ 	'<button onclick=deleteBooth('+data.boothList[i].boothId+')>删除</booth>'
		  + '</div>'
		/*str = str+ '<div class="th w15" style="color:#D9B300">'+'申请审核中'+'</div>'*/
		// 追加到table中
		$("#boothList").append(str);
		str = "";
	}
}


function updateBoothHtml(boothId){
	var booth
	$.ajax({
		url:"/booth/booth/getBooth",
		type : "post",
		data : {
			boothId : boothId
		
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			booth = data;
		},
	});
	var boothName = booth.boothName;
	var area = booth.area;
var str =	'<p class="booth-title">'
		+		'<span>修改摊位信息</span>'
		+	'</p>'
		+	'<span class="add-font">摊位名称&nbsp;:&nbsp;</span>'
		+	'<input type="text" id="update_booth_name" value="'+boothName+'"><br>'
		+	'<span class="add-font">摊位区域&nbsp;:&nbsp;</span>'
		+		'<input type="text" id="update_booth_area" value="'+area+'"><br> '
		+	'<div id="add-state">'
		+		'<span class="add-font">摊位状态&nbsp;:&nbsp;</span> '
		+		'<input type="radio" name="state" value="可用" class="update-state">可用&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; '
		+		'<input type="radio" name="state" value="禁用" class="update-state">禁用<br>'
		+	'</div>'
		+	'<button onclick="updateBooth('+boothId+')">修改</button>'

		$("#update-booth").append(str);
		$("#update-booth").show();
}
function updateBooth(boothId){
	var boothId = boothId;
	var boothName = $('#update_booth_name').val();
	var area = $('#update_booth_area').val();
	var state = $("input[type='radio']:checked").val();
	if(state == '可用'){
		state = '1';
	}else{
		state = '2';
	}
	$.ajax({
		url:"/booth/booth/updateBooth",
		type : "post",
		data : {
			boothId : boothId,
			boothName : boothName,
			area : area,
			state : state
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			if(data.success){
				alert("修改成功！");
				$("#update-booth").hide();
				location.reload();
			}else{
				alert("修改失败！"+data.Msg);
				$("#update-booth").hide();
			}
		},
	});
}

function deleteBooth(boothId){
	$.ajax({
		url:"/booth/booth/deleteBooth",
		type : "post",
		data : {
			boothId : boothId 
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			if(data.success){
				alert("删除成功！");
				location.reload();
			}else{
				alert("删除失败！"+data.Msg);
			}
		},
	});
}

