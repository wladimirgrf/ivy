package br.com.ivy.service.injection;


public class SqlInjection extends Injection{
	
	public SqlInjection(){
		this.code = "'";
		this.exceptions = new String[]{"erro","sql","select"};
	}
}
