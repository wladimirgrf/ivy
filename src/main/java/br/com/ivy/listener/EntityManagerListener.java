package br.com.ivy.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import br.com.ivy.util.ManagerFactory;

@WebListener("EntityManagerListener")
public class EntityManagerListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		ManagerFactory.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		ManagerFactory.start();		
	}
}