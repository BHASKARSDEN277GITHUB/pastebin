package snap.bhaskar.paste;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import snap.bhaskar.i18n.Bundle;

public class SubmitPaste extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text");
		// get parameters from request
		String title = request.getParameter("title");
		String content = request.getParameter("pastedContent");
		
		//format content
		//String content1=content.replaceAll("<", "&lt;");
		//String content2=content1.replaceAll(">", "&gt;");
		
		//content=content2;
		
		// get reference to printwriter object
		PrintWriter writer = response.getWriter();
		//writer.write("Title : " + title + "	PastedContent : " + content);
		
		//generate SecureRandom number for each file
		SecureRandom random=new SecureRandom(new Date().toString().getBytes());
		
		// generate url and make entry to database
		String url="";
		String param=title+"."+random.toString();
		url += "http://localhost:8080/Pastebin/show.jsp?id="+param;
		

		// define path where to save the files on the disk
		String path = "D:\\training_snapdeal\\filesystemdatabase\\pastebinfiles\\"
				+ title+random.toString()+".txt";

		writer.write("	url : " + url + " path : " + path);

		// create file
		File file = new File(path);

		if (!file.exists()) {
			file.createNewFile();
			//write content to file
			BufferedWriter bWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bWriter.write(content);
			bWriter.close();
			//now read from same file and insert
			
			//make entry to database here
			Bundle bundle=new Bundle();
			Connection conn=null;
			
			try{
				Class.forName(bundle.getDriver());
				conn=DriverManager.getConnection(bundle.getDB(),bundle.getUserName(),bundle.getPassword());
				PreparedStatement ps=conn.prepareStatement("insert into pastebin_post values(?,?,?,?)");
				ps.setString(1,title);
				ps.setString(2,path);
				ps.setString(3,param);
				ps.setString(4,url);
				
				int i=ps.executeUpdate();
				conn.close();
				
				if(i!=0){
					//database entry made successful
					//redirect to show.jsp
					writer.write("Data written to database successfully");
					//writer.close();
					//redirect to generated url
					response.sendRedirect(url);
					
				}else{
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}else{
			//do something here later
		}

	}

}
