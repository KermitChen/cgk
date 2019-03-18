$(function(){
	var index = 0;
	var pageNo = parseInt($("#pageNo").val());
	var length = parseInt($("#length").val());
	var totalPageCount = parseInt($("#totalPageCount").text());
	if(length == 0 || length == NaN){
		layer.confirm('sorry,未查询到数据!是否退出当前页面？', {
			  //skin:'layui-layer-lan',
			  title:'温馨提示',
			  btn: ['确定','取消'] //按钮
			}, function(){
			  window.close();
			}, function(){
			  
			});
	}
	$("#gene_lunbo>.simple_gene_dic").click(function(){
		$("#gene_lunbo").find("."+index).find("img").css("border","1px #fff solid");
		index = parseInt($(this).attr("data-value"));
		showPicDet(index);
	});
	$("#detail_lunbo .left").click(function () {
        index -= 1;
        if (index == -1 && $("#gene_lunbo").find(".pre").is(':hidden')) { 
        	layer.msg('已到第一条数据！');
        	index += 1;
        }else if(index == -1 && !$("#gene_lunbo").find(".pre").is(':hidden')){
        	pageNo -= 1;
        	doGoPage(pageNo); 
        }else{
	        showPicDet(index);
        }
    });
    $("#detail_lunbo .right").click(function () {
        index += 1;
        if (index == length && $("#gene_lunbo").find(".next").is(':hidden')) {
        	layer.msg('已到最后一条数据！');
        	index -= 1;
        }else if(index == length && !$("#gene_lunbo").find(".next").is(':hidden')){
        	pageNo += 1;
        	doGoPage(pageNo); 
        }else{
	        showPicDet(index);
        }
    });
    $("#gene_lunbo .left").click(function () {
        pageNo -= 1;
        doGoPage(pageNo);
    });
    $("#gene_lunbo .right").click(function () {
        pageNo += 1;
        doGoPage(pageNo);
    });
    //下一条记录
    function showPicDet(index){
    	var tpid = $("#gene_lunbo").find("."+index).find("input").val();
    	var location = (window.location+'').split('/');  
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
    	$.ajax({
				async:false,
				type: 'POST',
				data:{tpid:tpid},
				dataType : "json",
				url: basePath + "/clcx/getByTpidAjax.do",//请求的action路径
				error: function () {//请求失败处理函数
					alert("查询失败！");
				},
				success:function(data){ //请求成功后处理函数。
			    	$("#detail_lunbo").find("img").attr("src",data.tp1Url);
			    	
			    	$("#gene_lunbo").find("."+index).find("img").css("border","2px solid #E05A28");
			    	$("#gene_lunbo").find("."+(index+1)).find("img").css("border","1px #fff solid");
			    	$("#gene_lunbo").find("."+(index-1)).find("img").css("border","1px #fff solid");
				}
			});
    }
    //翻页
    function doGoPage(pageNo){
    	$("#pageNo").val(pageNo);
    	document.forms[0].submit();
    }
});