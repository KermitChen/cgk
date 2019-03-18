//日期比较
function compareDate(dateStart,dateEnd){
	if(dateStart=="" || dateEnd == ""){
		return true;
	}
	var d1=new Date(dateStart.replace(/\-/g,'/'));
	var d2=new Date(dateEnd.replace(/\-/g,'/'));
	return d1<d2;
}
//日期转换
function formatDate(date){
	var now = new Date(date);
	var year=now.getFullYear();     
    var month=(now.getMonth()+1 < 10 ? '0'+(now.getMonth()+1) : now.getMonth()+1);     
    var date=(now.getDate() < 10 ? '0'+ now.getDate() : now.getDate());     
    var hour=(now.getHours() < 10 ? '0'+ now.getHours() : now.getHours());     
    var minute=(now.getMinutes() < 10 ? '0'+ now.getMinutes() : now.getMinutes());     
    var second=(now.getSeconds() < 10 ? '0'+ now.getSeconds() : now.getSeconds());     
    return  year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
}
//下拉
function slider(dom){
	if($(dom).children().eq(2).css("display") == "none"){
		$(dom).children().eq(2).slideDown("fast");
		$(".mask").show();
	}else{
		$(dom).children().eq(2).slideUp("fast");
		$(".mask").hide();
	}
}
function sliders(dom){
	var txt = $(dom).text(); 
    $(dom).parent().siblings().eq(0).val(txt); 
    $(dom).parent().siblings().eq(1).val($(dom).attr("data-value"));
}
function doCplrUI(){
	var location = (window.location+'').split('/');  
	var basePath = location[0]+'//'+location[2]+'/'+location[3]; 
	var url = basePath + "/views/HmdMsg/cplr.jsp";
	layer.open({
       type: 2,
       title: '车牌号码录入窗口',
       shadeClose: false,
       shade: 0.5,
       area: ['700px', '505px'],
       content: url //iframe的url
   });	
}
$(function(){
	//导航切换
	$(".head_nav a").on("click", function () {
		$(this).addClass('head_nav_select').siblings().removeClass('head_nav_select');
	});
    //ie下表格单双行变色
    $("tbody tr:even").css("background", "#f4f4f4");  
    //ie下圆角
    $(".button_blue").css("border-radius", 6 + "px");
    //全选下拉
    //关闭
    $("#close").on("click", function () {
    	$("#dropdown_quanxuan .ul").css("display","none"); 
    });
    //单选点击空白关闭
	$("body").on("click", ".mask", function (e) {
		$(".dropdown").each(function(index, el) {
			if($(this).hasClass('dropdown') && $(this).css("display") == 'block'){
				if($(this).hasClass('dropdowns')){
					$(this).children('.ul').hide();
				}
				if($(this).hasClass('dropdown_all')){
					$(this).children().children().children('.container').addClass('hidden');
				}
			}
		});
	    $(".mask").hide();
	});
});
//日期插件
//laydate({
//    elem: '#hello', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
//    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
//});
//var start = {
//    elem: '#start',
//    format: 'YYYY/MM/DD hh:mm:ss',
//    // min: laydate.now(), //设定最小日期为当前日期
//    max: '2099-06-16 23:59:59', //最大日期
//    istime: true,
//    istoday: false,
//    // choose: function(datas){
//    //      end.min = datas; //开始日选好后，重置结束日的最小日期
//    //      end.start = datas //将结束日的初始值设定为开始日
//    // }
//};
//var end = {
//    elem: '#end',
//    format: 'YYYY/MM/DD hh:mm:ss',
//    // min: laydate.now(),
//    max: '2099-06-16 23:59:59',
//    istime: true,
//    istoday: false,
//    // choose: function(datas){
//    //     start.max = datas; //结束日选好后，重置开始日的最大日期
//    // }
//};
//laydate(start);
//laydate(end);
	
	//高度
	$(function (){
//		$(".content").height($(window).height());
		$(".mask").height($(document).height());
		$(".mask").width($(window).width());
	});
	//日期光标
	$(document).ready(function () {
	$("#laydate_table tbody td").on("click", "#laydate_table td", function () {
		alert(1);
	});
	//清空
	$(".empty").each(function(){
	    $(this).click(function(){
	    	if($(this).attr("id") != null){
		        var textid = "#"+$(this).attr("id");
		        $(textid).val("");
		        $(textid)[0].focus();
	    	}
	    });
    });
});
