$(function () {
	var pageNo = $.trim($("#pageNo").val());
	var totalPageCount = $.trim($("#totalPageCount").val());
    var sWidth = $("#focus").width();
    var len = $("#focus ul li").length;
    var index = 0;
    var picTimer;
    var btn = "<div class='btnBg'></div><div class='btn'>";
    for (var i = 0; i < len; i++) {
        btn += "<span></span>";
    }
    btn += "</div><div class='preNext pre'></div><div class='preNext next'></div>";
    $("#focus").append(btn);
    $("#focus .btnBg").css("opacity", 0);
    $("#focus .btn span").css("opacity", 0.4).mouseenter(function () {
        index = $("#focus .btn span").index(this);
        showPics(index);
    }).eq(0).trigger("mouseenter");
    $("#focus .preNext").css("opacity", 0.0).hover(function () {
        $(this).stop(true, false).animate({ "opacity": "0.5" }, 300);
    }, function () {
        $(this).stop(true, false).animate({ "opacity": "0" }, 300);
    });
    $("#focus .pre").click(function () {
        index -= 1;
        if (index == -1) { 
        	doGoPage(parseInt(pageNo)-1);
        }
        showPics(index);
        showMess(index);
    });
    $("#focus .next").click(function () {
        index += 1;
        console.log("totalPageCount:"+totalPageCount);
        if (index == len && pageNo != totalPageCount) { 
        	doGoPage(parseInt(pageNo)+1);
        }else if(index == len && pageNo == totalPageCount){
        	layer.msg('已到最后一页！');
        	index -= 1;
        	return;
        }
        showPics(index);
        showMess(index);
    });
    $("#focus ul").css("width", sWidth * (len));
    $("#focus").hover(function () {
        clearInterval(picTimer);
    }, 
    function () {
        picTimer = setInterval(function () {
            showPics(index);
            index++;
            if (index == len) { index = 0; }
        }, 2800000);
    }).trigger("mouseleave");
    function showPics(index) {
        var nowLeft = -index * sWidth;
        $("#focus ul").stop(true, false).animate({ "left": nowLeft }, 300);
        $("#focus .btn span").stop(true, false).animate({ "opacity": "0.4" }, 300).eq(index).stop(true, false).animate({ "opacity": "1" }, 300);
    }
    function showMess(index){
    	findVeh(index);
    	if(index == 0){
    		$(".0").show();
        	$(".1").hide();
        	$(".9").hide();
    	}else if(index == 9){
    		$(".9").show();
        	$(".8").hide();
        	$(".0").hide();
    	}else{
    		$("." + index).show();
        	$("." + (index-1)).hide();
        	$("." + (index+1)).hide();
    	}
    }
});