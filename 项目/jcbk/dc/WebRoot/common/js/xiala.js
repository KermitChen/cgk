//$(function (){
//  $('.multi_select').MSDL({
//    'value': ['1','2','3','4','5','6','7','8','9','10','11'],
//    'data': ['北京','上海','武汉','广州','武汉','重庆','成都','乌海','临河','石家庄','南京']
//  });
//  $('.multi_select1').MSDL({
//    'value': ['1','2','3','4','5','6','7','8','9','10','11'],
//    'data': ['北京1','上海1','武汉1','广州1','武汉1','重庆1','成都1','乌海1','临河1','石家庄1','南京1']
//  });
//});

(function ($){ 
   $.fn.extend({
        MSDL: function (options){/*MultiSelectDropList*/
		 //各种属性参数
		 
		 var defaults = {
			width: '170',//下拉列表宽 
			maxheight: '150',//下拉列表最大高度
            data: ['item1','item2','item3','item4','item5','item6'],//下拉列表中的数据	
			selectallTxt: '全选',//全选文本
			selectokTxt: '清空',//确认文本
		 };
		 var options = $.extend(defaults, options);
		 
		 return this.each(function (){
		 
		 //插件实现代码
			//创建 input输入框
			//readonly:锁住键盘，不能向文本框输入内容  
			var $xiala_duoxuan_a = $('<a class="xiala_duoxuan_a"></a>');
			var $ipt = $('<input type="text" readonly value="==请选择==" class="select_rel" />');
			
			$ipt.width(options.width - 0);//设定文本框宽度
			
			var $this = $(this);
			$this.width(options.width);
			$ipt.appendTo($this);
			
			var $ipt_value = $this.find('input').eq(0);
		    
			//创建 下拉选项 
			
			//1.下拉选项包裹
			var $container = $('<div class="container"></div>');
		
			
			//2.创建 全选和确认按钮  top层 
			var $tops = $('<div class="tops" style="text-indent: 20px;"></div>');//外层div包裹
			var $all = $('<input type="checkbox" class="select_all"/><label>'+options.selectallTxt+'</label>');//全选
            var $btn = $('<input type="button" class="ok blue" value="'+options.selectokTxt+'"/>');
            $all.appendTo($tops);
            $btn.appendTo($tops);
			
			//3.下拉中的内容 content层
			var $contents = $('<div class="contents"></div>');//外层div包裹
			var count = options.data.length;
			var h = ( (count * 30) > parseInt(options.maxheight) ) ? options.maxheight : "'" + count * 30 + "'";
			$contents.height(h);
			for(var i = count-1; i >= 0; i--){
				var $list = $('<div><input type="checkbox" value=' + options.value[i] + ' /><label>' + options.data[i] + '</label><br></div>');
				$list.appendTo($contents);
			}
           
			//4把top层和content层加到$container下
			$tops.appendTo($container);
            $contents.appendTo($container);	

            //把$container加到$(this)下
			$container.appendTo($this);	

			
            //js Effect
			var $dropList = $contents.children().children('input');
			
			$all.change(function (){//点击all
				
				  var opt_arr = [];
				  var opt_value = [];
				  $dropList.each(function (){
					  if($all.is(':checked')){
						  $(this)[0].checked = 'checked';
						  opt_arr.push($(this).next().text());
				   		opt_value.push($(this).val());
					  }else{
						  $(this)[0].checked = '';
						  opt_arr=[];
					  }
				  }); 
				  
				  $ipt.val(opt_arr.join(';')); 	
				  $ipt_value.val(opt_value.join(';'));  
			});
			
			$container.addClass('hidden');//开始隐藏
			
			$ipt.focus(function (e){//文本框处于编辑
				$container.removeClass('hidden');
				$(".mask").show();
				if($(this).siblings(".container").css("display") == "block"){
					$(this).parent().parent().parent(".dropdown_all").css("z-index","9");
					$(this).parent().parent().parent().parent().siblings().children(".dropdown_all").css("z-index","0");
				}
				$this.addClass('multi_select_focus');

			});
			
			$(".xiala_duoxuan_a").on("click", function() {
			 	$(this).siblings(".container").removeClass("hidden");
			 	$(".mask").show();
			 	if($(this).siblings(".container").css("display") == "block"){
					$(this).parent().parent().parent(".dropdown_all").css("z-index","9");
					$(this).parent().parent().parent().parent().siblings().children(".dropdown_all").css("z-index","0");
				}
				$this.addClass('multi_select_focus');
			});
			
			$btn.click(function (){//点击 ok按钮 
				$(".select_rel").val("");
				$(".container input").prop("checked", false);
			});
			
			
			$dropList.change(function (){//勾选选项
				 var opt_arr = [];
				 var opt_value = [];
				 $dropList.each(function (){
				   if ($(this).is(':checked')){
				   	opt_arr.push($(this).next().text());
				   	opt_value.push($(this).val());
				   }
				 });
				 var $dropList_selected = $contents.children().children('input:checked');
				 $ipt.val(opt_arr.join(';'));
				 $ipt_value.val(opt_value.join(';'));
				 var o = $all[0];
				 var n1 = $dropList_selected.length;
				 var n2 = $dropList.length;
				 o.checked = (n1 === n2) ? 'checked' : '';
			});
		 });
	 },
   });
})(jQuery);