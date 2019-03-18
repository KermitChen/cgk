$(function(){
	var oldInputVal = $("#hphm").val();
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	 $(".grayTip").each(function(){
		  var $input=$(this);
		  $input.css("color","#888");
		  var oldText=$.trim($input.val());
		  $input.focus(function(){
			  var newText=$.trim($input.val());
			  if (newText==oldText){
			   $input.val("");   
			  }
			  $input.css("color","#000");
			  });
			  $input.blur(function(){
			  var newText=$.trim($input.val());
			  if(newText==""){
			   $input.val(oldText);
			   $input.css("color","#888");
			  }
		  });
	 }); 
	 
	 $("#left_data").on("dblclick","div",function(){
	 	var data = getRightVal();
	 	moveRight($(this),data);
	 });
	  $("#right_data").on("dblclick","div",function(){
	  	var data = getLeftVal();
	 	moveLeft($(this),data);
	 });
	 $("#right_arrows").click(function(){
	 	var data = getRightVal();
	 	$("#left_data").children("div").each(function(){
	 		var val = $(this).children("input:checkbox").is(":checked");
			if(val == true){
				moveRight($(this),data);
			}
	 	});
	 });
	 $("#left_arrows").click(function(){
	 	var data = getLeftVal();
	 	$("#right_data").children("div").each(function(){
	 		var val = $(this).children("input:checkbox").is(":checked");
			if(val == true){
				moveLeft($(this),data);
			}
	 	});
	 });
	 $("#double_right_arrows").click(function(){
	 	var data = getRightVal();
	 	var list = $("#left_data").children("div");
	 	for(var i=0;i<list.length;i++){
	 		moveRight($(list[i]),data);
	 	}
	 });
	 $("#double_left_arrows").click(function(){
	 	var data = getLeftVal();
	 	var list = $("#right_data").children("div");
	 	for(var i=0;i<list.length;i++){
	 		moveLeft($(list[i]),data);
	 	}
	 });
	 $("#find_button").click(function(){
	 	var hphm = $("#hphm").val();
	 	if(hphm == oldInputVal){
	 		hphm = "";
	 	}
	 	layer.load();
	 	var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];  
		$.ajax({
				async:true,
				type: 'POST',
				data:{hphm:hphm},
				dataType : "json",
				url: basePath + "/JjHmd/findHmdAjax.do",//请求的action路径
				error: function () {//请求失败处理函数
					layer.closeAll('loading');
					layer.msg("查询失败！");
					$("#left_data").empty();
				},
				success:function(data){ //请求成功后处理函数。
					layer.closeAll('loading');
					if(data.length > 0){
						$("#left_data").empty();
						for(var i=0;i<data.length;i++){
							addLeft(data[i].cphid);
						}
					}
				}
			});
	 });
	 $("#ensure").click(function(){
	 	var res = getRightVal();
	 	parent.$("#cphid").val(res.substring(0,res.length-1));
	 	parent.layer.close(index);
	 });
	 $("#cancel").click(function(){
	 	parent.layer.close(index);
	 });
	 backShow();
});
/*$(document).on('click', ':checkbox', function (e) {
    //停止事件冒泡,当点击的是checkbox时,就不执行父div的click
    e.stopPropagation();
    //checkbox选中,div变色,否则不变色
    $(this).prop('checked') ? 
    $(this).parent().css('background-color', 'blue') 
    : $(this).parent().css('background-color', '');
});*/
//回显
function backShow(){
	var hphm = parent.$("#cphid").val();
	var arr = new Array();
	if(isNotEmpty(hphm)){
		arr = hphm.split(",");
		for(var i=0;i<arr.length;i++){
			addRight(arr[i]);		
		}
	}
}
//单击选中
function checkData(dom){
	//dom.children(':checkbox').click();
	var box = dom.children("input:checkbox");
	var val = dom.children("input:checkbox").is(":checked");
	if(val == false){
		dom.children("input:checkbox").attr("checked",true);
		dom.css("background","#CCCCCC");
	}else{
		dom.children("input:checkbox").attr("checked",false);
		dom.css("background","#fff");
	}
}
//移到右边
function moveRight(val,data){
	if(data.indexOf(val.children("span").text()) == -1){
		val.children("input:checkbox").attr("checked",false);
		val.css("background","#fff");
		val.clone(true).appendTo($("#right_data"));
	}
	val.remove();
}
//移到左边
function moveLeft(val,data){
	if(data.indexOf(val.children("span").text()) == -1){
		val.children("input:checkbox").attr("checked",false);
		val.css("background","#fff");
		val.clone(true).appendTo($("#left_data"));
	}
	val.remove();
}
function addLeft(str){
	var val = "<div class=\"data_li\" onclick=\"checkData($(this));\">"+
						"<input type=\"checkbox\"><span>"+str+"</span>"+
					"</div>";
	$("#left_data").append(val);
}
function addRight(str){
	var val = "<div class=\"data_li\" onclick=\"checkData($(this));\">"+
						"<input type=\"checkbox\"><span>"+str+"</span>"+
					"</div>";
	$("#right_data").append(val);
}
function getRightVal(){
	var res = "";
	$("#right_data").children("div").each(function(){
		res += $(this).children("span").text() + ",";
	});
	return res;
}
function getLeftVal(){
	var res = "";
	$("#left_data").children("div").each(function(){
		res += $(this).children("span").text() + ",";
	});
	return res;
}
