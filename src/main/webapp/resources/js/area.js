window.onload = function onload(){
	getUser();
	getAreaList();
};

function showDIV(){ $("#add-area").show();
}

function hiddenDIV(){
	var areaName = $('#booth_name').val();
	var state = $("input[type='radio']:checked").val();
	if(state == '可用'){
		state = '1';
	}else{
		state = '2';
	}
	$.ajax({
		url:"/booth/area/createNewArea",
		type : "post",
		dataType : "json",
		data:{
			areaName :areaName,
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


function getAreaList(){
	$.ajax({
		url:"/booth/area/getAreaList",
		type : "post",
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			showAreaData(data);
		},
	});
}

function showAreaData(data){
	var str = "";// 定义用于拼接的字符串
	for (var i = 0; i < data.areaList.length; i++) {
		// 拼接表格的行和列
		var temp = i;
		str = 
			'<div class="th w5 text-center"></div>'+
			'<div class="padding-left-15 th w55">'+data.areaList[i].areaName+'</div>';
			
		if(data.areaList[i].state == '1'){
			str = str +'<div class="th w25" style="color:#00BB00">'+'可申请'+'</div>';
		} else if(data.areaList[i].state == '2'){
			str = str +'<div class="th w25" style="color:#FF0000">'+'已禁用'+'</div>';
		}else if(data.areaList[i].state == '3'){
			str = str +'<div class="th w25" style="color:#D9B300">'+'已申请'+'</div>';
		}
		str = str + '<div class="th w15">'
		  //+		'<button onclick=updateBooth('+data.boothList[i].boothId+','+data.boothList[i].boothName+','+data.boothList[i].area+','+')>修改</booth>'
		+		'<button onclick=updateAreaHtml('+data.areaList[i].areaId+')>修改</booth>'  
		+ 	'<button onclick=deleteArea('+data.areaList[i].areaId+')>删除</booth>'
		  + '</div>'
		/*str = str+ '<div class="th w15" style="color:#D9B300">'+'申请审核中'+'</div>'*/
		// 追加到table中
		$("#areaList").append(str);
		str = "";
	}
}


function updateAreaHtml(areaId){
	var area
	$.ajax({
		url:"/booth/area/getArea",
		type : "post",
		data : {
			areaId : areaId
		
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			area = data;
		},
	});
	var areaName = area.areaName;
	var areaId = area.areaId;
var str =	'<p class="booth-title">'
		+		'<span>修改摊位信息</span>'
		+	'</p>'
/*		+	'<span class="add-font">摊位名称&nbsp;:&nbsp;</span>'
		+	'<input type="text" id="update_booth_name" value="'+boothName+'"><br>'*/
		+	'<span class="add-font">区域名称&nbsp;:&nbsp;</span>'
		+		'<input type="text" id="update_area_name" value="'+areaName+'"><br> '
		+	'<div id="add-state">'
		+		'&nbsp;<span class="add-font">区域状态&nbsp;:&nbsp;</span> '
		+		'<input type="radio" name="state" value="可用" class="update-state">可用&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; '
		+		'<input type="radio" name="state" value="禁用" class="update-state">禁用<br>'
		+	'</div>'
		+	'<button onclick="updateArea('+areaId+')">修改</button>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;'
		+	'<button onclick="hiddenupdateDIV()">取消</button>'
		$("#update-area").append(str);
		$("#update-area").show();
}
function updateArea(areaId){
	var areaId = areaId;
	var areaName = $('#update_area_name').val();
	var state = $("input[type='radio']:checked").val();
	if(state == '可用'){
		state = '1';
	}else{
		state = '2';
	}
	$.ajax({
		url:"/booth/area/updateArea",
		type : "post",
		data : {
			areaId : areaId,
			areaName : areaName, 
			state : state
		},
		dataType : "json",
		async : false,
		cache : false,
		success : function(data) {
			if(data.success){
				alert("修改成功！");
				$("#update-area").hide();
				location.reload();
			}else{
				alert("修改失败！"+data.Msg);
				$("#update-area").hide();
			}
		},
	});
}

function deleteArea(areaId){
	$.ajax({
		url:"/booth/area/deleteArea",
		type : "post",
		data : {
			areaId : areaId 
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


function hiddenaddDIV(){
	$("#add-area").hide();
}

function hiddenupdateDIV(){
	$("#update-area").hide();
}

