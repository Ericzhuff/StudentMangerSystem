layui.config({
	base : "js/"
}).use(['form','layer','jquery','laypage'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		$ = layui.jquery;
	var basePath = $("body",window.parent.document).attr("basePath");
	$.ajaxSetup({  
	    async : false  
	}); 
	LoadData();
	//加载页面数据
	function LoadData(){
		var newsData = '';
		$.get(basePath+"/Navs/list", function(data){
	        	newsData = data;
	        	newsList(newsData);
		})
	}
	

	//查询
	$(".search_btn").click(function(){
		var newArray = [];
		if($(".search_input").val() != ''){
			var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
			setTimeout(function(){
            	$.ajax({
					url : basePath+"/Navs/findByKeyWords",
					type : "post",
					dataType : "json",
					data:{"keywords":$(".search_input").val()},
					success : function(data){
			        	newsList(data);
					}
				})
            	
                layer.close(index);
            },2000);
		}else{
			layer.msg("请输入需要查询的内容");
		}
	})

	//添加模块
	$(".newsAdd_btn").click(function(){
		var index = layui.layer.open({
			title : "添加模块",
			type : 2,
			content : "navsAdd.jsp",
			success : function(layero, index){
				layui.layer.tips('点击此处返回模块列表', '.layui-layer-setwin .layui-layer-close', {
					tips: 3
				});
			}
		})
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		layui.layer.full(index);
	})

	//批量删除
	$(".batchDel").click(function(){
		var $checkbox = $('.news_list tbody input[type="checkbox"][name="checked"]');
		var $checked = $('.news_list tbody input[type="checkbox"][name="checked"]:checked');
		if($checkbox.is(":checked")){
			layer.confirm('确定删除选中的信息？',{icon:3, title:'提示信息'},function(index){
				var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
	            setTimeout(function(){
	            	//删除数据
	            	var ids="";
	            	for(var i = 0;i<$checked.length;i++){
	            		ids += $($checked[i]).attr("data-id")+",";
	            	}
	            	$.ajax({
	    				"url":basePath+"/Navs/delete",
	    				"data":{"ids":ids.substring(0,ids.length-1)},
	    				"success":function(data){
	    					layer.msg("删除成功");
	    					LoadData();
	    				}
	    			})
	            	$('.news_list thead input[type="checkbox"]').prop("checked",false);
	            	form.render();
	                layer.close(index);
					layer.msg("删除成功");
	            },2000);
	        })
		}else{
			layer.msg("请选择需要删除的文章");
		}
	})

	//全选
	form.on('checkbox(allChoose)', function(data){
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
		child.each(function(index, item){
			item.checked = data.elem.checked;
		});
		form.render('checkbox');
	});

	//通过判断文章是否全部选中来确定全选按钮是否选中
	form.on("checkbox(choose)",function(data){
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
		var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
		if(childChecked.length == child.length){
			$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
		}else{
			$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
		}
		form.render('checkbox');
	})

	
 
	//操作
	$("body").on("click",".navs_edit",function(){  //编辑
		var _this = $(this);
		var index = layui.layer.open({
			title : "修改模块",
			type : 2,
			content : basePath+"/Navs/edit?id="+_this.attr("data-id"),
			success : function(layero, index){
				layui.layer.tips('点击此处返回模块列表', '.layui-layer-setwin .layui-layer-close', {
					tips: 3
				});
			}
		})
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		layui.layer.full(index);
	})

	$("body").on("click",".nav_view",function(){  //修改
		var _this = $(this);
		var index = layui.layer.open({
			title : "修改模块",
			type : 2,
			content : basePath+"/Navs/view?id="+_this.attr("data-id"),
			success : function(layero, index){
				layui.layer.tips('点击此处返回模块列表', '.layui-layer-setwin .layui-layer-close', {
					tips: 3
				});
			}
		})
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		layui.layer.full(index);
	})

	$("body").on("click",".news_del",function(){  //删除
		var _this = $(this);
		layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
			$.ajax({
				"url":basePath+"/Navs/delete",
				"data":{"ids":_this.attr("data-id")},
				"success":function(data){
					layer.msg("删除成功");
					LoadData();
				}
			})
			layer.close(index);
		});
	})

	function newsList(that){
		//渲染数据
		function renderDate(data,curr){
			var dataHtml = '';
			if(!that){
				currData = newsData.concat().splice(curr*nums-nums, nums);
			}else{
				currData = that.concat().splice(curr*nums-nums, nums);
			}
			if(currData.length != 0){
				for(var i=0;i<currData.length;i++){
					dataHtml += '<tr>'
			    	+'<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose" data-id="'+data[i].id+'"></td>'
			    	+'<td>'+currData[i].title+'</td>'
			    	+'<td>'+currData[i].icon+'</td>';
			    	if(currData[i].spread == "false"){
			    		dataHtml += '<td>关闭</td>';
			    	}else{
			    		dataHtml += '<td>打开</td>';
			    	}
			    	dataHtml += '<td>'+currData[i].href+'</td>'
			    	+'<td>'
					+  '<a class="layui-btn layui-btn-mini navs_edit" data-id="'+data[i].id+'"><i class="iconfont icon-edit"></i> 编辑</a>'
			    	+  '<a class="layui-btn layui-btn-normal layui-btn-mini nav_view" data-id="'+data[i].id+'"><i class="layui-icon">&#xe60b;</i> 详细</a>'
					+  '<a class="layui-btn layui-btn-danger layui-btn-mini news_del" data-id="'+data[i].id+'"><i class="layui-icon">&#xe640;</i> 删除</a>'
			        +'</td>'
			    	+'</tr>';
				}
			}else{
				dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
			}
		    return dataHtml;
		}

		//分页
		var nums = 10; //每页出现的数据量
		if(that){
			newsData = that;
		}
		laypage({
			cont : "page",
			pages : Math.ceil(newsData.length/nums),
			jump : function(obj){
				$(".news_content").html(renderDate(newsData,obj.curr));
				$('.news_list thead input[type="checkbox"]').prop("checked",false);
		    	form.render();
			}
		})
	}
})