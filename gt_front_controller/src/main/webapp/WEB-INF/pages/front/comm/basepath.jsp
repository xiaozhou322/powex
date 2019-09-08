<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

if (request.getServerName().contains("www.powex.pro"))
{basePath="https://"+request.getServerName();}
%>
<base href="<%=basePath%>" />