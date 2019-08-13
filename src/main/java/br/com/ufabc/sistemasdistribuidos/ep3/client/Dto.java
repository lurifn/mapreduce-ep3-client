package br.com.ufabc.sistemasdistribuidos.ep3.client;

public class Dto {
	String host;
	int port;
	String urls;
	
	public Dto(String host, int port, String urls) {
		super();
		this.host = host;
		this.port = port;
		this.urls = urls;
	}
	
	public Dto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
}
