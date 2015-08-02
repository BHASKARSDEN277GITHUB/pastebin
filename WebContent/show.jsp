<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="snap.bhaskar.i18n.*" %>    
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>

<html>
<head>
    <script type="text/javascript" src="main.js"></script>
    <link rel="stylesheet" type="text/css" href="main.css"/>
    <title>Pastebin</title>
</head>
<%
	//get parameter from request
	String param=request.getParameter("id");
	//get path of file correspoding to current param
	
	Bundle bundle=new Bundle();
	Connection conn=null;
	String filePath="";
	String url="";
	try{
		Class.forName(bundle.getDriver());
		conn=DriverManager.getConnection(bundle.getDB(),bundle.getUserName(),bundle.getPassword());
		PreparedStatement ps=conn.prepareStatement("select * from pastebin_post where param=?");
		ps.setString(1,param);
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			filePath=rs.getString("path");
			url=rs.getString("url");
		}
		conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	
	//read file and display in div
	BufferedReader reader=null;
	String content="";
	try{
		reader=new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		String line=reader.readLine();
		while(line!=null){
			content+=line+"\n";
			line=reader.readLine();
		}
		reader.close();
	}catch(Exception e){
		e.printStackTrace();
	}
%>
    <div id="wrapper">
        <div id="outer">
            <div id="header">
                <div id="sitename">
                	<span>Pastebin</span>&nbsp;&nbsp;
                	<span><img id="logo" src="logo.jpg"></span>
                </div>
                 <ul id="menu">
                    <li class="menuitem"><a href="#">Archive</a>&nbsp;&nbsp;</li>
                    <li class="menuitem"><a href="contribute.html">Contribute</a>&nbsp;&nbsp;</li>
                    <li class="menuitem"><a href="index.html">Home</a>&nbsp;&nbsp;</li>
                </ul>
            </div>
            <div id="content" style="text-align:center">
               URL :<span style="font-size:20px;background-color: black;color:white"> <%=url%><br/></span>
               <br/>
               <div id="contentShow">
              <xmp> <%=content%> </xmp>
              </div>
            </div>
            <div id="footer">
                <small>&copy;&nbsp;Bhaskar Kalia</small>
            </div>
        </div>
    </div>
</html>