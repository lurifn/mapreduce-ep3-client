package br.com.ufabc.sistemasdistribuidos.ep3.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

class TCPClient {
	private Socket socket;
	private Scanner scanner;

	private TCPClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }

	private void start() throws IOException {
		String input;
		while (true) {
			input = scanner.nextLine();
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			out.println(input);
			out.flush();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Digite o host:");
		String host = new Scanner(System.in).nextLine();
		
		System.out.println("Digite a porta:");
		int porta = Integer.parseInt(new Scanner(System.in).nextLine());
		
		TCPClient client = new TCPClient(InetAddress.getByName(host), porta);

		System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
		client.start();
	}
}
