layui.config({
	base : "js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element(),
		$ = layui.jquery;
	var basePath = $("body",window.parent.parent.document).attr("basePath");
	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	})

	//动态获取文章总数和待审核文章数量,最新文章
	$.get(basePath+"course/findTopTen",
		function(data){
			var waitNews = [];
			$(".allNews span").text(data.length);  //文章总数
			$(".waitNews span").text("20");  //待审核文章
			//加载最新文章
			var hotNewsHtml = '';
			for(var i=0;i<data.length;i++){
				hotNewsHtml += '<tr>'
		    	+'<td align="left">'+data[i].course_name+'</td>'
		    	+'<td>'+data[i].date+'</td>'
		    	+'</tr>';
			}
			$(".hot_news").html(hotNewsHtml);
		}
	)

	//图片总数
	$.get(basePath+"Admin/list",
		function(data){
			$(".imgAll span").text(data.length);
		}
	)

	//用户数
	$.get(basePath+"studentInfo/list",
		function(data){
			$(".userAll span").text(data.length);
		}
	)

	//新消息
	$.get("../json/message.json",
		function(data){
			$(".newMessage span").text(data.length);
		}
	)


	//数字格式化
	$(".panel span").each(function(){
		$(this).html($(this).text()>9999 ? ($(this).text()/10000).toFixed(2) + "<em>万</em>" : $(this).text());	
	})

	//填充数据方法
 	function fillParameter(data){
 		//判断字段数据是否存在
 		function nullData(data){
 			if(data == '' || data == "undefined"){
 				return "未定义";
 			}else{
 				return data;
 			}
 		}
 		
 	}

})
