<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %> <!-- jsp页面应用Java类库需要使用 page指令的 import -->
<%@ page import="com.chinasofti.hrsys.entity.User" %>
<%@ page import="com.chinasofti.hrsys.utils.PageHelper" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="css/mystyle.css" />
		<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
	</head>
	<body id="body1">
		<div class="view">
			<!-- left -->
			<%@ include file="left.jsp" %>
			<!-- left end -->
			<div class="right" >
				<!-- top bar -->
				<%@ include file="top.jsp" %>
				<!-- top bar end -->
				<div class="main">
					<% 
						User searchUser = (User)request.getAttribute("searchUser");
						String searchName = searchUser != null ? searchUser.getUserName() : "";
					%>
					<form name="mainForm" action="<%=path%>/user" method="post">
						<input type="hidden" name="currentPage" />
						<input type="hidden" name="pageSize" />
						<input type="hidden" name="opr" />
						<div class="form-group">
						<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addModal">添加</button>
						用户名：<input type="text" value="<%=searchName %>" name="userName" class="form-control" style="display: inline;width: 200px;" />
						<button type="button" class="btn btn-primary btn-sm" onclick="search()">查询</button>
						<% 
							int result = 3;
							Object reResult = request.getAttribute("result");
							if(reResult != null){
								result = (Integer) reResult;
							}else{
								result = 3;
							}
						%>
						<% 
							if(result == 1){
						%>
								<font color="green">操作成功</font>
						<% 
							}else if(result == 0){ 
						%>
								<font color="red">操作失败</font>
						<% 
							}
						%>
						</div>
					<div>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>用户ID</th>
									<th>用户名</th>
									<th>手机号</th>
									<th>email</th>
									<th>年龄</th>
									<th>QQ号</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<%
								PageHelper pageHelper = (PageHelper)request.getAttribute("pageHelper");
								List<User> userList = (List<User>)pageHelper.getDataList();
								for(User user : userList){ // 增强的for循环
								%>
								<tr>
									<td><%=user.getUserId() %></td>
									<td><%=user.getUserName() %></td>
									<td><%=user.getPhoneNo() %></td>
									<td><%=user.getEmail() %></td>
									<td><%=user.getAge() %></td>
									<td><%=user.getQq() %></td>
									<td>
										<button type="button" onclick="updateUser('<%=user.getUserId() %>','<%=user.getUserName() %>','<%=user.getPhoneNo() %>','<%=user.getEmail() %>','<%=user.getAge() %>','<%=user.getQq() %>','<%=user.getGender() %>' )" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#updateModal">修改</button>
										<button class="btn btn-danger btn-sm">禁用</button>
									</td>
								</tr>
								<% 
									}
								%>
								<tr>
									<td colspan="7" style="text-align: right;">
										总共有<%=pageHelper.getTotalCount()%>条数据
										总共有<%=pageHelper.getTotalPage()%>页   
										当前第<%=pageHelper.getCurrentPage()%>页
										<a href="javascript:changePage('first',<%=pageHelper.getCurrentPage()%>, <%=pageHelper.getPageSize()%>, <%=pageHelper.getTotalPage()%>);"> 首页 </a>
										<a href="javascript:changePage('pre',<%=pageHelper.getCurrentPage()%>, <%=pageHelper.getPageSize()%>,<%=pageHelper.getTotalPage()%>);"> 上一页</a>
										<a href="javascript:changePage('next',<%=pageHelper.getCurrentPage()%>, <%=pageHelper.getPageSize()%>,<%=pageHelper.getTotalPage()%>);"> 下一页</a>
										<a href="javascript:changePage('last',<%=pageHelper.getCurrentPage()%>, <%=pageHelper.getPageSize()%>,<%=pageHelper.getTotalPage()%>);"> 尾页</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					</form>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			function addUser(){
				document.addForm.submit();
			}
			function updateUser(userid, username, phoneno, email, age, qq, gender){
				document.updateForm.userId.value = userid;
				document.updateForm.userName.value = username;
				document.updateForm.phoneno.value = phoneno;
				document.updateForm.email.value = email;
				document.updateForm.age.value = age;
				document.updateForm.qq.value = qq;
				if(gender == '1'){
					document.updateForm.gender[0].checked="checked";
				}else if(gender == "2"){
					document.updateForm.gender[1].checked="checked";
				}
				
			}
			function submitUpdate(){
				document.updateForm.submit();
			}
			function changePage(opr, currentPage, pageSize, totalPage){
				if(opr == "first"){
					document.mainForm.currentPage.value=1;
				}else if(opr == "pre"){
					if(currentPage == 1){
						return
					}
					document.mainForm.currentPage.value=currentPage - 1;
				}else if(opr == "next"){
					if(currentPage == totalPage){
						return
					}
					document.mainForm.currentPage.value=currentPage + 1;
				}else if(opr == "last"){
					document.mainForm.currentPage.value=totalPage;
				}
				document.mainForm.pageSize.value = pageSize;
				document.mainForm.opr.value="search";
				document.mainForm.submit();
			}
			function search(){
				document.mainForm.currentPage.value = 1;
				document.mainForm.pageSize.value = 10;
				document.mainForm.opr.value="search";
				document.mainForm.submit();
			}
		</script>
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">添加用户</h5>
                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                  <form action="<%=path%>/user" method="post" name="addForm">
                  	 <input type="hidden" name="opr" value="add" />
                     <div class="form-group">        
                         <input type="text" name="userName" id="simpleinput" class="form-control" placeholder="用户名">
                     </div>
                     <div class="form-group">
                     	 性别:
                         <input type="radio" checked="checked" name="gender" id="gender" value="1">男
                         <input type="radio" name="gender" id="gender" value="2">女
                     </div>      
                     <div class="form-group">                
                         <input type="text" name="qq" class="form-control" id="qq" placeholder="qq号">
                     </div>
                       <div class="form-group">                
                         <input type="text" name="phoneno" class="form-control" id="phoneno" placeholder="手机号">
                     </div>  
                     <div class="form-group">                
                         <input type="text"  name="age" class="form-control" id="age" placeholder="年龄">
                     </div>
                      <div class="form-group">                
                         <input type="text"  name="email" class="form-control" id="email" placeholder="邮箱">
                     </div>   
                 </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary waves-effect waves-light" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary waves-effect waves-light" onclick="addUser()" >保存</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">修改用户</h5>
                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                  <form name="updateForm" action="<%=path%>/user" method="post">
                     <input type="hidden" name="opr" value="update" />
                     <input type="hidden" name="userId" />
                     <div class="form-group">        
                                                             用户名:<input type="text" name="userName" id="simpleinput" class="form-control" placeholder="用户名">
                     </div>
                     <div class="form-group">
                     	性别:
                         <input type="radio" name="gender" id="gender" value="1">男
                         <input type="radio" name="gender" id="gender" value="2">女
                     </div>      
                     <div class="form-group">                
                         QQ:<input type="text" name="qq" class="form-control" id="qq" placeholder="qq号">
                     </div>
                       <div class="form-group">                
                                                                手机:<input type="text" name="phoneno" class="form-control" id="phoneno" placeholder="手机号">
                     </div>  
                     <div class="form-group">                
                                                              年龄:<input type="text" name="age" class="form-control" id="age" placeholder="年龄">
                     </div> 
                     <div class="form-group">                
                                                             邮箱:<input type="text"  name="email" class="form-control" id="email" placeholder="邮箱">
                     </div> 
                 </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary waves-effect waves-light" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary waves-effect waves-light" onclick="submitUpdate()">保存</button>
            </div>
        </div>
    </div>
</div>
	<!-- bottom -->
	<%@ include file="bottom.jsp" %>
	</body>
</html>