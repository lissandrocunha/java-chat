package chat.servidor;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chat.servidor.thread.RecebeMensagemCliente;

public class Servidor {

	private List<RecebeMensagemCliente> clientes = new ArrayList<>();

	public static void main(String[] args) {
		Servidor servidor = new Servidor();
		servidor.aguardarConexoes(servidor);
	}

	private void aguardarConexoes(Servidor servidor) {
		try (ServerSocket server = new ServerSocket(3333)) {

			while (true) {
				System.out.println("Aguardando conexões...");
				Socket socket = server.accept();

				RecebeMensagemCliente recebeMensagemCliente = new RecebeMensagemCliente(socket, this);
				new Thread(recebeMensagemCliente).start();

				servidor.clientes.add(recebeMensagemCliente);
				System.out.println("Novo cliente conectado.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarMensagensClientes(String mensagem) {
		for (RecebeMensagemCliente cliente : this.clientes) {
			cliente.enviarMensagem(mensagem);
		}
	}

}
