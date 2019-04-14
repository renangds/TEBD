import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class Servidor {
    private int porta;
    private final int bufferSize = 4096;
    private final byte[] buffer = new byte[bufferSize];

    public static void main(String...args) throws IOException{
        new Servidor(789).executa();
    }

    public Servidor(int porta){
        this.porta = porta;
    }

    public void executa() throws IOException{

        ServerSocket server = new ServerSocket(this.porta);

        System.out.println("Porta do servidor " + server.getInetAddress().getHostName() + " aberta");

        while(true){
            System.out.println("Aguardando cliente...");

            Socket cliente = server.accept();

            System.out.println("Cliente " + cliente.getInetAddress().getHostName() + " conectado!");

            TrataCliente novoCliente = new TrataCliente(cliente);

            System.out.println("Executa a thread do novo cliente...");

            new Thread(novoCliente).run();

            System.out.println("Thread terminada");
        }
    }
}