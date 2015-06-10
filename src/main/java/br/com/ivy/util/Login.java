package br.com.ivy.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.ivy.dao.UserDAO;
import br.com.ivy.entity.User;


@WebServlet("/login")
public class Login extends HttpServlet {


	private static final long serialVersionUID = 7882229436396481562L;

	public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = null;
		String password = null;
		if (request.getParameter("user") != null) {
			email = request.getParameter("user");
		}
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");			
		}
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			User user = UserDAO.getInstance().get(email, parseHash(password));
			if (user != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				request.setAttribute("content", "/restrict/default/home.jsp");
				request.getRequestDispatcher("/restrict/default/layout.jsp").forward(request, response);
				return;
			}
		}
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	private String parseHash(String password) {
        String salt = password + "pentest";
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes(), 0, salt.length());
            hash = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }
}
