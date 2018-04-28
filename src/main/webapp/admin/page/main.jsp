<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>首页--layui后台管理模板</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="<s:url value='/admin/layui/css/layui.css' />" media="all" />
	<link rel="stylesheet" href="<s:url value='/admin/css/font_eolqem241z66flxr.css' />" media="all" />
	<link rel="stylesheet" href="<s:url value='/admin/css/main.css' />" media="all" />
</head>
<body class="childrenBody">
	<div class="panel_box row">
		<div class="panel col">
			<a href="javascript:;" data-url="page/message/message.html">
				<div class="panel_icon">
					<i class="layui-icon" data-icon="&#xe63a;">&#xe63a;</i>
				</div>
				<div class="panel_word newMessage">
					<span></span>
					<cite>新消息</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/user/allUsers.html">
				<div class="panel_icon" style="background-color:#FF5722;">
					<i class="iconfont icon-dongtaifensishu" data-icon="icon-dongtaifensishu"></i>
				</div>
				<div class="panel_word userAll">
					<span></span>
					<cite>新增人数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/studentInfo/studentInfoList.jsp">
				<div class="panel_icon" style="background-color:#009688;">
					<i class="layui-icon" data-icon="&#xe613;">&#xe613;</i>
				</div>
				<div class="panel_word userAll">
					<span></span>
					<cite>学生总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="<s:url value='/Admin/view' />?id=1">
				<div class="panel_icon" style="background-color:#5FB878;">
					<i class="layui-icon" data-icon="&#xe64a;">&#xe64a;</i>
				</div>
				<div class="panel_word imgAll">
					<span></span>
					<cite>管理员总数</cite>
				</div>
			</a>
		</div>
		<div class="panel col">
			<a href="javascript:;" data-url="page/news/newsList.html">
				<div class="panel_icon" style="background-color:#F7B824;">
					<i class="iconfont icon-wenben" data-icon="icon-wenben"></i>
				</div>
				<div class="panel_word waitNews">
					<span></span>
					<cite>待审核文章</cite>
				</div>
			</a>
		</div>
		<div class="panel col max_panel">
			<a href="javascript:;" data-url="page/news/newsList.html">
				<div class="panel_icon" style="background-color:#2F4056;">
					<i class="iconfont icon-text" data-icon="icon-text"></i>
				</div>
				<div class="panel_word allNews">
					<span></span>
					<em>文章总数</em>
					<cite>文章列表</cite>
				</div>
			</a>
		</div>
	</div>
	
	<div class="row">
		<div class="sysNotice col">
			<blockquote class="layui-elem-quote title">系统监控</blockquote>
			<div id="cpu" style="width: 380px;height:370px;display:block;float:left;margin-top:50px;"></div>
			<div id="memory" style="width: 380px;height:370px;display:block;float:left;margin-top:50px;" ></div>
		</div>
		<div class="sysNotice col"  style="width:40%;">
			<blockquote class="layui-elem-quote title">最新课程<i class="iconfont icon-new1"></i></blockquote>
			<table class="layui-table" lay-skin="line">
				<colgroup>
					<col>
					<col width="110">
				</colgroup>
				<tbody class="hot_news"></tbody>
			</table> 
		</div>
	</div>

	<script type="text/javascript" src="<s:url value='/admin/layui/layui.js' />"></script>
	<script type="text/javascript" src="<s:url value='/admin/js/main.js' />"></script>
	<script type="text/javascript" src="<s:url value='/admin/js/echarts.min.js' />"></script>
	<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('cpu'));
	var option = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    series: [
		        {
		            name: 'CPU使用率',
		            type: 'gauge',
		            detail: {formatter:'{value}%'},
		            data: [{value: 0, name: 'CPU使用率'}]
		        }
		    ]
		};

		
		 myChart.setOption(option);
		 //------------------------------内存----------------------------------------
		 var myChart2 = echarts.init(document.getElementById('memory'));
			var option2 = {
				    tooltip : {
				        formatter: "{a} <br/>{b} : {c}%"
				    },
				    series: [
				        {
				            name: '内存使用率',
				            type: 'gauge',
				            detail: {formatter:'{value}%'},
				            data: [{value: 0, name: '内存使用率'}]
				        }
				    ]
				};

				 myChart2.setOption(option2);
				 var webSocket;  
				    if ('WebSocket' in window) {  
				        webSocket = new WebSocket("ws://localhost:8080<s:url value='/' />ws/websocket");  
				    }else if ('MozWebSocket' in window) {  
				           ws = new MozWebSocket("ws://localhost:8080<s:url value='/' />ws/websocket");  
				      } else {  
				           alert('WebSocket is not supported by this browser.');  
				           
				      }   
				   
				    webSocket.onerror = function(event) {  
				      onError(event)  
				    };  
				   
				    webSocket.onopen = function(event) {  
				      onOpen(event)  
				    };  
				   
				    webSocket.onmessage = function(event) {  
				      onMessage(event)  
				    };  
				   
				    function onMessage(event) {  
						
						var str = event.data.split(",");
				    	 option.series[0].data[0].value = parseFloat(str[0]).toFixed(2);
						 myChart.setOption(option, true);
						 option2.series[0].data[0].value = parseFloat(str[1]).toFixed(2);
						 myChart2.setOption(option2, true);
				    }  
				   
				    function onOpen(event) {  
				     	init();
				    }  
				   
				    function onError(event) {  
				      
				    }  
				   
				    function init(){
				    	webSocket.send('hello');  
				    }
				   
	</script>
</body>
</html>