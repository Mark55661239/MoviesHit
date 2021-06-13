<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Message: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='lightgray'>

<table id="table-1">
   <tr><td><h3>IBM Message: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Message: Home</p>

<h3>��Ƭd��:</h3>

<%-- ���~���C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/front-end/message/listAllMessage.jsp'>List</a> all Messages    <h4>(byDAO).         </h4></li>

  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/message/message.do" >
        <b>��J�T���s�� (�p1):</b>
        <input type="text" name="message_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="�e�X"> 
    </FORM>
  </li>

  <jsp:useBean id="messageSvc" scope="page" class="com.message.model.MessageService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/message/message.do" >
       <b>��ܰT���s��:</b>
       <select size="1" name="message_no">
         <c:forEach var="messageVO" items="${messageSvc.all}" > 
          <option value="${messageVO.message_no}">${messageVO.message_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
    </FORM>
  </li>
  

  
  
  
  
  
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/message/message.do" >
       <b>��ܰT�����e:</b>
       <select size="1" name="message_no">
         <c:forEach var="messageVO" items="${messageSvc.all}" > 
          <option value="${messageVO.message_no}">${messageVO.message_content}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
     </FORM>
  </li>
</ul>

<h3>�T���޲z</h3>

<ul>
  <li><a href='addMessage.jsp'>Add</a> a new Message.</li>
</ul>

</body>
</html>