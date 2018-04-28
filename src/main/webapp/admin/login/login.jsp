<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="ZH-cn">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>用户登录</title>
  <link rel="stylesheet" href="<s:url value='/admin/layui/css/layui.css' />" media="all" />
  <link rel="stylesheet" href="<s:url value='/admin/login/css/login.css' />">
</head>

<body>
  <div class="kit-login">
    <div class="kit-login-bg"></div>
    <div class="kit-login-wapper">
      <h2 class="kit-login-slogan">欢迎使用 <br> 学生信息管理系统</h2>
      <div class="kit-login-form">
        <h4 class="kit-login-title">登录</h4>
        <form class="layui-form" action="<s:url value='/checkLogin/checkLogin' />" method="post">
          <div class="kit-login-row">
            <div class="kit-login-col">
              <i class="layui-icon">&#xe612;</i>
              <span class="kit-login-input">
                <input type="text" name="admin.username" lay-verify="required" placeholder="请输入用户名" />
              </span>
            </div>
            <div class="kit-login-col"></div>
          </div>
          <div class="kit-login-row">
            <div class="kit-login-col">
              <i class="layui-icon">&#xe64c;</i>
              <span class="kit-login-input">
                <input type="password" name="admin.password" lay-verify="required" placeholder="请输入密码" />
              </span>
            </div>
            <div class="kit-login-col"></div>
          </div>
          <div class="kit-login-row">
            <div class="kit-login-col">
              <input type="checkbox" name="rememberMe" title="记住帐号" lay-skin="primary">
            </div>
          </div>
          <div class="kit-login-row">
            <button class="layui-btn kit-login-btn" lay-submit="submit" lay-filter="login_hash">登录</button>
          </div>
          <div class="kit-login-row" style="margin-bottom:0;">
            <a href="javascript:;" style="color: rgb(153, 153, 153); text-decoration: none; font-size: 13px;" id="forgot">忘记密码</a>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="<s:url value='/admin/login/js/polyfill.min.js' />"></script>
  <script type="text/javascript" src="<s:url value='/admin/layui/layui.js' />"></script>
  <script>
  layui.config({
		base : "js/"
	}).use(['layer', 'form'], function() {
      var form = layui.form(),
        $ = layui.jquery;

      $('#forgot').on('click', function() {
        layer.msg('请联系管理员.');
      });
     
      //监听提交
      form.on('submit(login_hash)', function(data) {
    	  console.log(data.field);
        $.ajax({
        	"url":"<s:url value='/checkLogin/checkLogin' />",
        	"data":data.field,
        	"success":function(result){
        		if(!result.state){
        			layer.msg(result.msg, {icon: 5});
        		}else{
        			location.href="<s:url value='/admin/index.jsp' />";
        		}
        	}
        })
        return false;
      });
    });
  </script>
</body>

</html>