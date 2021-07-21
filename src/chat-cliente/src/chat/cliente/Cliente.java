package chat.cliente;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import chat.cliente.gui.JanelaGui;
import chat.cliente.thread.RecebeMensagemServidor;

public class Cliente extends JanelaGui {

	private Socket socket;

	public static void main(String args[]) {
		new Cliente();
	}

	@Override
	protected boolean conectar() {
		System.out.println("Conectando no servidor...");
		try {
			this.socket = new Socket("127.0.0.1", 3333);
			RecebeMensagemServidor recebeMensagemServidor = new RecebeMensagemServidor(this.socket, this);
			new Thread(recebeMensagemServidor).start();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	protected void sendMessage(String mensagem) {
		System.out.println(String.format("Envia a mensagem via socket para o servidor - %s", mensagem));

		try {
			OutputStream os = this.socket.getOutputStream();
			DataOutput dos = new DataOutputStream(os);
			dos.writeUTF(mensagem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
