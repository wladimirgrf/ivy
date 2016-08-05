package br.com.ivy.view;

import javax.servlet.annotation.WebServlet;


@WebServlet("/about")
public class AboutAction extends DefaultAction{

	private static final long serialVersionUID = 1418603946544607379L;

	@Override
	protected String getJspPath() {
		return "/public/pages/about.jsp";
	}

	@Override
	protected String getModel() {
		return "about";
	}
}
