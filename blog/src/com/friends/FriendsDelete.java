package com.friends;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datalink.DataLink;

public class FriendsDelete extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String friendsname = request.getParameter("friendsname");
		String DELETE_SQL = "delete from friends where friend_name = ?";
		try{
			DataLink datalink = new DataLink();
			Connection con = datalink.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = null;
			ps = con.prepareStatement(DELETE_SQL);
			//ps.setString(2,username);
			ps.setString(1,friendsname);
			ps.executeUpdate();
			con.commit();
			try{
				ps.close();
				con.close();
			}catch(SQLException e2){
				session.setAttribute("addfriendsmsg","删除好友失败!");
				System.out.print("数据库关闭失败!");
			}
		}catch(SQLException e){
			session.setAttribute("addfriendsmsg","删除好友失败!");
			System.out.print(e.getMessage());
		}
		session.setAttribute("addfriendsmsg","成功的将好友 "+friendsname+" 删除!");
		username = new String(username.getBytes("UTF-8"),"ISO-8859-1");
		response.sendRedirect("friends.jsp?username="+username+"&numberpage=4");
		return;
	}

}
