package br.com.ufabc.sistemasdistribuidos.ep3.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

class TCPClient {
	private Socket socket;
	private Scanner scanner;

	private TCPClient(InetAddress serverAddress, int serverPort) throws Exception {
		this.socket = new Socket(serverAddress, serverPort);
		this.scanner = new Scanner(System.in);
	}

	private void stop() throws IOException {
		socket.close();
	}

	private void start() throws Exception {
		// lê o json com a lista de urls
		String urls = readFileAsString("lista/lista.txt");
		
		ObjectMapper mapper = new ObjectMapper();
		
		Dto dto = new Dto();
		dto.setHost(InetAddress.getLocalHost().getHostAddress());
		dto.setPort(8083);
		dto.setUrls(urls);
		
		System.out.println("Enviando urls: " + urls);

		// envia as urls pro coordenador
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto));
		out.flush();
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("CLIENT");
		String host;

		// pode receber o host do coordenador por argumento ou não
		if (args.length > 0)
			host = args[0];
		else
			host = "localhost";

		// abre conexao com o coordenador
		TCPClient client = new TCPClient(InetAddress.getLocalHost(), 8080);

		System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());

		// faz a solicitação
		client.start();

		// fecha o socket
		client.stop();

		// inicia TCP de resposta
		TCPServer.inicia();
	}
}
