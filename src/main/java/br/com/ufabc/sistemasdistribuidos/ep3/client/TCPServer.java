package br.com.ufabc.sistemasdistribuidos.ep3.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	private ServerSocket server;

	public TCPServer(String ipAddress) throws Exception {
		if (ipAddress != null && !ipAddress.isEmpty())
			this.server = new ServerSocket(8083, 1, InetAddress.getByName(ipAddress));
		else
			this.server = new ServerSocket(8083, 1, InetAddress.getLocalHost());
	}

	private void listen() throws Exception {
		String data = null;
		Socket client = this.server.accept();
		String clientAddress = client.getInetAddress().getHostAddress();
		System.out.println("\r\nNew connection from " + clientAddress);

		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		StringBuilder builder = new StringBuilder();
		while ((data = in.readLine()) != null) {
			System.out.println("\r\nMessage from " + clientAddress + ": " + data);

			builder.append(data);
		}
	}

	public InetAddress getSocketAddress() {
		return this.server.getInetAddress();
	}

	public int getPort() {
		return this.server.getLocalPort();
	}

	public static void inicia() throws Exception {
		TCPServer app = new TCPServer("ec2-3-91-11-161.compute-1.amazonaws.com");

		System.out.println(
				"\r\nRunning Server: " + "Host=" + app.getSocketAddress().getHostAddress() + " Port=" + app.getPort());

		while (true)
			app.listen();
	}
}
