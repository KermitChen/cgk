$(function () {
	var pageNo = $.trim($("#pageNo").val());
	var totalPage = $.trim($("#totalPage").val());
    var sWidth = $("#focus").width();
    var len = $("#focus ul li").length;
    //如果是前一页prePage，则从最后一条开始，如果是下一页，则从第一条开始
    var index = $("#preOrNext").val() == "prePage"?len-1:0;
    //恢复为下一页，解决直接点击某一页出现的缺陷，非上一页，都从0开始
    $("#preOrNext").val("nextPage");
    var picTimer;
    var btn = "<div class='btnBg'></div><div class='btn'>";
    for (var i = 0;i < len;i++) {
        btn += "<span></span>";
    }
    btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
    $("#focus").append(btn);
    $("#focus .btnBg").css("opacity", 0);
    
    $("#focus .btn span").css("opacity", 0.4).mouseenter(
    	function () {
    		//index = $("#focus .btn span").index(this);
    		showPics(index);
    	}
    ).eq(0).trigger("mouseenter");
    
    $("#focus .preNext").css("opacity", 0.0).hover(
    	function () {
    		$(this).stop(true, false).animate({ "opacity": "0.5" }, 300);
    	}, 
    	function () {
    		$(this).stop(true, false).animate({ "opacity": "0" }, 300);
    	}
    );
    
    $("#focus .pre").click(function () {
    	if(index >= 0){
    		//上一条
    		index -= 1;
    		
    		//控制翻页，翻上一页时，当前页必须大于第一页
    		if (index == -1 && parseInt(pageNo) > 1) {
    			$("#preOrNext").val("prePage");//上一页
            	doGoPage(parseInt(pageNo)-1);
            }
    		
    		if(index == -1){
    		    //下标从0开始
    		    index = 0;
    		    return;
    		}
            
    		//控制显示信息，当index不等于-1即不翻页时且当index等于-1且当前页不为第一页时
            if(0 <= index && index < len){
            	showPics(index);
                showMess(index);
            }
    	}
    });
    
    $("#focus .next").click(function () {
    	if(index < len){
    		//下一条
    		index += 1;
    		
    		//控制翻页，翻下一页时，当前页必须小于最大页数
    		if (index == len && parseInt(pageNo) < totalPage) {
    			$("#preOrNext").val("nextPage");//下一页
    	        doGoPage(parseInt(pageNo)+1);
    	    }
    		
    		if(index == len){
    		    //不能大于等于数组长度
    		    index = index - 1;
    		    return;
    		}

    	    //控制显示信息，当index不等于-1即不翻页时且当index等于-1且当前页不为第一页时
            if(0 <= index && index < len){
            	showPics(index);
                showMess(index);
            }
    	}
    });
    
    $("#focus ul").css("width", sWidth * (len));
    
    $("#focus").hover(
    	function () {
        	clearInterval(picTimer);
    	}, 
	    function () {
	        picTimer = setInterval(function () {
	            showPics(index);
	            index++;
	            if (index == len) { index = 0; }
	        }, 2800000);
	    }
    ).trigger("mouseleave");
    
    function showPics(index) {
        var nowLeft = -index * sWidth;
        $("#focus ul").stop(true, false).animate({ "left": nowLeft }, 300);
        $("#focus .btn span").stop(true, false).animate({ "opacity": "0.4" }, 300).eq(index).stop(true, false).animate({ "opacity": "1" }, 300);
    }
    
    function showMess(index){
    	if(index == 0){
    		$(".0").show();
        	$(".1").hide();
        	$(".9").hide();
    	} else if(index == 9){
    		$(".9").show();
        	$(".8").hide();
        	$(".0").hide();
    	} else{
    		$("." + index).show();
        	$("." + (index-1)).hide();
        	$("." + (index+1)).hide();
    	}
    }    
});