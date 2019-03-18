Date.prototype.Format = function (fmt) {//格式化时间函数
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
//根据值查询在数组中的位置
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};
//移除数组中的某个元素
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};
//判断字符串为空
function isEmpty(str){
	if(str == '' || str == null || str == undefined){
		return true;
	}else{
		return false;
	}
}
//判断字符串不为空
function isNotEmpty(str){
	if(str == '' || str == null || str == undefined){
		return false;
	}else{
		return true;
	}
}
//比较时间大小,1大于2等于3小于
function compareTime(time1,time2){
	var arr1 = new Array();
	var arr2 = new Array();
	arr1 = time1.split("-");
	arr2 = time2.split("-");
	if(parseInt(arr1[0]) > parseInt(arr2[0])){
		return '1';
	}else if(parseInt(arr1[0]) == parseInt(arr2[0])){
		if(parseInt(arr1[1]) > parseInt(arr2[1])){
			return '1';
		}else if(parseInt(arr1[1]) == parseInt(arr2[1])){
			if(arr1.length > 2 && arr2.length > 2){
				if(parseInt(arr1[2]) > parseInt(arr2[2])){
					return '1';
				}else if(parseInt(arr1[2]) == parseInt(arr2[2])){
					return '2';
				}else{
					return '3';
				}
			}
			return '2';
		}else{
			return '3';
		}
	}else{
		return '3';
	}
}
//moment比较时间
function compareMoment(time1,time2,format){
	var t1 = moment(time1,format);
	var t2 = moment(time2,format);
	if(t1.diff(t2) > 0){
		return '1';
	}else if(t1.diff(t2) == 0){
		return '2';
	}else{
		return '3';
	}
}
//校验时间
function checkTime(startTime,endTime){
	var start=new Date(startTime.replace("-", "/").replace("-", "/"));
	var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	if(end < start){
		return false;
	}
	return true;
}
//查询单条过车记录
function showSbDetail(tpid){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	if(tpid.toString().indexOf("00000000000000000") != -1){
		layer.msg("红名单车辆无法查询!");
		return;
	}
	var url = basePath + "/clcx/getSbByTpid.do?tpid="+tpid;
	layer.open({
	  type: 2,
	  title:false,
	  area:['826px','610px'],
	  closeBtn:2,
	  shadeClose:true,
	  content: url
	});
}
//将监测点编号翻译成监测点名称
function getJcdName(jcdid){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var jcdName = "";
	$.ajax({
		async:false,
		type: 'POST',
		data:{jcdid:jcdid},
		dataType : "json",
		url: basePath + "/bdController/tranJcdId.do",//请求的action路径
		error: function () {//请求失败处理函数
			layer.msg("翻译监测点失败！");
		},
		success:function(data){ //请求成功后处理函数。
			jcdName = data;
		}
	});
	return jcdName;
}
//翻译车牌类型
function getCplx(cplx){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var cplxName = "";
	$.ajax({
		async:false,
		type: 'POST',
		data:{cplx:cplx},
		dataType : "json",
		url: basePath + "/bdController/tranCplx.do",//请求的action路径
		error: function () {//请求失败处理函数
			layer.msg("翻译车牌类型失败！");
		},
		success:function(data){ //请求成功后处理函数。
			console.log(data);
			cplxName = data;
		}
	});
	return cplxName;
}
//查询单条过车记录
function showSbDetailInParent(tpid){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];  
	var url = basePath + "/clcx/getSbByTpid.do?tpid="+tpid;
	parent.layer.open({
	  type: 2,
	  title:false,
	  area:['826px','610px'],
	  closeBtn:2,
	  shadeClose:true,
	  content: url
	});
}
//选择监测点
function doChoseJcd(){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var url = basePath + "/hmd/getJcd.do";
    layer.open({
           type: 2,
           title: '监测点筛选窗口',
           shadeClose: false,
           shade: 0.5,
           area: ['800px', '500px'],
           content: url
       }); 	
}
	
//使用地图和图片展示过车记录的页面
function showSbInGis(hphm,kssj,jssj){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var url = basePath + "/clcx/showSbInGis.do?hphm="+hphm+"&kssj="+kssj+"&jssj="+jssj;
	window.open(url);
}
//展示车辆轨迹
function showWheelPath(hphm,kssj,jssj){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var url = basePath + "/clcx/showWheelPath.do?hphm="+hphm+"&kssj="+kssj+"&jssj="+jssj;
	layer.open({
           type: 2,
           title: '车辆轨迹',
           shadeClose: true,
           shade: 0.1,
           maxmin:true,
           area: ['800px', '600px'],
           content: url
       });
}

//列表展示过车数据
function showSbInTable(hphm,kssj,jssj){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var url = basePath + "/clcx/showSbInTable.do?hphm="+hphm+"&kssj="+kssj+"&jssj="+jssj;
	layer.open({
           type: 2,
           title: '过车详情',
           shadeClose: true,
           shade: 0.1,
           area: ['850px', '650px'],
           content: url
       });
}
//通用的一些检查
function commonCheck(hphmIsEmpty,timeIsRight){
	//判断车牌号是否为空
	if(hphmIsEmpty){
		var cphid = $.trim($("#cphid").val());
		if(isEmpty(cphid)){
			$("#errSpan").text("错误提示：请输入号牌号码！");
			return false;
		}
	}
	//判断时间是否正确
	if(timeIsRight){
		var kssj = $.trim($("#kssj").val());//开始时间
		var jssj = $.trim($("#jssj").val());//结束时间
		if(isEmpty(kssj) || isEmpty(jssj)){
			$("#errSpan").text("错误提示：时间不可为空！");
			return false;
		}
//		if(moment(kssj).isAfter(moment()) || moment(jssj).isAfter(moment())){
//			$("#errSpan").text("错误提示：开始时间和结束时间均不可大于当前时间！");
//			return false;
//		}
		if(moment(kssj).isAfter(moment(jssj))){
			$("#errSpan").text("错误提示：结束时间不能小于开始时间！");
			return false;
		}
	}
	return true;
}
//查询计算任务数量
function askIsCalculate(){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var flag = false;
	$.ajax({
		async:false,
		type: 'POST',
		dataType : "json",
		url: basePath + "/gjfx/getCalCount.do",//请求的action路径
		error: function () {//请求失败处理函数
			layer.msg("获取计算任务个数失败！");
		},
		success:function(data){ //请求成功后处理函数。
			console.log(data);
			layer.confirm('当前有'+data+'个计算任务,是否继续计算？',{icon: 7, title:'提示',btn: ['继续','取消']}
				  , function(index){
				    flag = true;
					layer.close(index);
				}, function(index){
					layer.close(index);
				});
		}
	});
	return flag;
}
function isHmd(hphm){
	var location = (window.location+'').split('/');
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var flag = false;
	$.ajax({
		async:false,
		type:'post',
		data: {hphm:hphm},
		dataType:'text',
		url:basePath+'/JjHmd/isHmd.do',
		error: function(){
					
		},
		success: function(data){
			console.log(data);
			if('1' == data){
				flag = true;
			}
		}
	});
	return flag;
}
var si;
//显示进度条
function showBarLine(times){
	processerbar(parseInt(times*calTime*60*1000));
	$("#bar_content").show();
	barlineIndex = layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 0,
		  shade:0.5,
		  shadeClose: false,
		  scrollbar: false,
		  area: ['230px', '90px'],
		  skin: 'layui-layer-nobg',
		  content: $('#bar_content'),
		  end: function(){
		  	$("#bar_content").hide();
		  	clearInterval(si);
		  	$("#line").each(function(i,item){
				$(item).stop(false,true).animate();
				$(item).animate({width: 0+"%"});
			});
		  }
		});
}
//进度条初始化
function processerbar(time){
     document.getElementById('probar').style.display="block";
	$("#line").each(function(i,item){
		var a=parseInt($(item).attr("w"));
		$(item).animate({
			width: a+"%"
		},time);
	});
   	si = window.setInterval(
	function(){
		a=$("#line").width();
		b=(a/200*100).toFixed(0);
		document.getElementById('percent').innerHTML=b+"%";
		document.getElementById('percent').style.left=a-12+"px";
		document.getElementById('msg').innerHTML="计算中..";
		if(document.getElementById('percent').innerHTML=="100%") {
			layer.close(barlineIndex);
		}
	},70);
};
$(function(){
	/*$("#errSpan").change(function(){
		var mess = $("#errSpan").text();
		if(mess != "" || mess != null){
			$("#query_button").attr("disabled","true");
		}else{
			$("#query_button").removeAttr("disabled");
		}
	});*/
	$("#laydate_box").hide(function(){
		var startTime = $("#kssj").val();
		var start=new Date(startTime.replace("-", "/").replace("-", "/"));
		var endTime = $("#jssj").val();
		var end=new Date(endTime.replace("-", "/").replace("-", "/"));
		if(end < start){
			$("#errSpan").text("选择时间错误！");
			$("#kssj").val("");
		}else{
			$("#errSpan").text("");
		}
	});
	
});
		