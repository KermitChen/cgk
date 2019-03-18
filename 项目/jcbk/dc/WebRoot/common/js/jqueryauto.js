var highlightindex = -1;
$(document).ready(function() {
	var wordInput = $("#word");
	var fkPath = $("#fkPath").val();
	var wordInputOffset = wordInput.offset();
	//自动补全框最开始应该隐藏起来
	$("#auto").hide().css("border", "1px black solid").css("position", "absolute").css("top", "20px").css("left", "-100px").width(wordInput.width() + 200);
	//给文本框添加键盘按下并弹起的事件
	wordInput.keyup(function(event) {
	//处理文本框中的键盘事件
		var myEvent = event || window.event;
		var keyCode = myEvent.keyCode;
		//如果输入的是字母，应该将文本框中最新的信息发送给服务器
		//如果输入的是退格键或删除键，也应该将文本框中的最新信息发送给服务器
		//  if (keyCode >= 65 && keyCode <= 90 || keyCode == 8 || keyCode == 46) {

		if (!(keyCode == 13 || keyCode == 40 || keyCode == 38)) {
			//1.首先获取文本框中的内容
			var wordText = $("#word").val();
			var autoNode = $("#auto");
			if (wordText != "") {
				//2.将文本框中的内容发送给服务器段
				$.ajax({
					url : fkPath + "?" + new Date().getTime(),
					data : {word : wordText},
					dataType : "xml",
					type : "POST", //请求方式
					success : function(data) {
						//将dom对象data转换成JQuery的对象
						var jqueryObj = $(data);
						//找到所有的word节点
						var wordNodes = jqueryObj.find("word");
						//遍历所有的word节点，取出单词内容，然后将单词内容添加到弹出框中
						//需要清空原来的内容
						autoNode.html("");
						wordNodes.each(function(i) {
							//获取单词内容
							var wordNode = $(this);
							//新建div节点，将单词内容加入到新建的节点中
							//将新建的节点加入到弹出框的节点中
							var newDivNode = $("<div></div>").attr("id", i);
							newDivNode.html(wordNode.text());
							newDivNode.appendTo(autoNode);
							
							//增加鼠标进入事件，高亮节点;
							newDivNode.mouseover(
								function() {
									if (highlightindex != -1) {
										$("#auto").children("div").eq(highlightindex).css("background-color", "white");
									}
									highlightindex = $(this).attr("id");
									$(this).css("background-color", "#B0C4DE");
								}
							);
							//增加鼠标移出事件，取消当前高亮节点
							newDivNode.mouseout(function() {
								$(this).css("background-color", "white");
							});
							//增加鼠标点击事件，可以进行补全
							newDivNode.click(function() {
								var comText = $(this).text();
								$("#auto").hide();
								highlightindex = -1;
								var d = comText.split(",");
								//var c = new Array("first", "second", "third"); 
								if (d[0] == null) {
									$("#jcdid").val("");
								} else {
									$("#word").val(d[0]);
									$("#jcdid").val(d[1]);
								}
							});
						});
						
						//如果服务器段有数据返回，则显示弹出框
						if (wordNodes.length > 0) {
							autoNode.show();
						} else {
							autoNode.hide();
							//弹出框隐藏的同时，高亮节点索引值也制成-1
							highlightindex = -1;
						}
					}
				});
			} else {
				autoNode.hide();
				highlightindex = -1;
			}
		} else if (keyCode == 38 || keyCode == 40) {
			//如果输入的是向上38向下40按键
			if (keyCode == 38) {
				//向上
				var autoNodes = $("#auto").children("div")
				if (highlightindex != -1) {
					//如果原来存在高亮节点，则将背景色改称白色
					autoNodes.eq(highlightindex).css("background-color", "white");
					highlightindex = -1;
				} else {
					highlightindex = autoNodes.length - 1;
				}
				if (highlightindex == -1) {
					//如果修改索引值以后index变成-1，则将索引值指向最后一个元素
					highlightindex = autoNodes.length - 1;
				}
				//让现在高亮的内容变成红色
				autoNodes.eq(highlightindex).css("background-color", "#B0C4DE");
			}
			if (keyCode == 40) {
				//向下
				var autoNodes = $("#auto").children("div")
				if (highlightindex != -1) {
					//如果原来存在高亮节点，则将背景色改称白色
					autoNodes.eq(highlightindex).css("background-color", "white");
				}
				highlightindex = +1;
				if (highlightindex == autoNodes.length) {
					//如果修改索引值以后index变成-1，则将索引值指向最后一个元素
					highlightindex = 0;
				}
				//让现在高亮的内容变成红色
				autoNodes.eq(highlightindex).css("background-color", "#B0C4DE");
			}
		} else if (event.keyCode == 13) {
			//如果输入的是回车
			//下拉框有高亮内容
			//	alert("enter键");
			if (highlightindex != -1) {
				//取出高亮节点的文本内容
				var comText = $("#auto").hide().children("div").eq(highlightindex).text();
				highlightindex = -1;
				//文本框中的内容变成高亮节点的内容         
				var d = comText.split(",");
				//var c = new Array("first", "second", "third");
				// alert("dd");
				$("#word").val(d[0]);
				$("#jcdid").val(d[1]);
			} else {
				//下拉框没有高亮内容
				alert("文本框中的[" + $("#word").val() + "]被提交了");
			}
		}
	});
})