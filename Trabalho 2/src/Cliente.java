import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Cliente {
    private int porta;
    private String endereco;
    private final int bufferSize = 4096;
    private final byte[] buffer = new byte[bufferSize];
    private Socket cliente;

    private DocumentBuilder builder;
    private Document requisicao;

    public static void main(String...args) throws Exception{
        new Cliente("127.0.0.1", 789).executa();
    }

    public Cliente(String endereco, int porta) throws Exception{
        this.porta = porta;
        this.endereco = endereco;
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        this.requisicao = builder.newDocument();
        this.cliente = new Socket(this.endereco, this.porta);

        System.out.println("Conexão aberta " + this.cliente.getInetAddress().getHostAddress());
    }

    public void executa() throws UnknownHostException, IOException, Exception {
        //String getMatricula = new Scanner(System.in).nextLine();

        System.out.println("Cliente " + this.cliente.getInetAddress().getHostAddress() + " sendo enviada");

        //Sending request to server

        File requisition = new File("/Users/renan/IdeaProjects/T1 TEBD/src/XML/requisicao.xml");
        FileInputStream in = new FileInputStream(requisition);

        OutputStream out = this.cliente.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);

        int lidos = -1;

        while((lidos = in.read(this.buffer, 0, this.bufferSize)) != -1){
            out.write(this.buffer, 0, lidos);
        }

        out.flush();

        System.out.println("Mensagem enviada!");

        //Receive response from server

        InputStream is = this.cliente.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);

        DataInputStream dis = new DataInputStream(this.cliente.getInputStream());

        BufferedReader br = new BufferedReader(isr);

        File fileReceiver = new File("historico.xml");
        FileOutputStream fos = new FileOutputStream(fileReceiver);

        lidos = -1;

        fos.flush();

        if(dis.available() == 0){
            System.out.println("Não há dados a serem recebidos");
        } else{
            while((lidos = dis.read(this.buffer, 0, this.bufferSize)) != -1){
                System.out.println(lidos);
                fos.write(this.buffer, 0, lidos);
            }

            System.out.println("Histórico recebido!");
        }

        this.cliente.close();

        System.out.println("Conexão terminada");
    }

    private void getXmlRequest(String matricula) throws Exception{
        Element root = requisicao.createElement("requisicao");
        this.requisicao.appendChild(root);

        Element nomefuncao = this.requisicao.createElement("nomefuncao");
        nomefuncao.appendChild(this.requisicao.createTextNode("historicoAluno"));
        root.appendChild(nomefuncao);

        Element tagmatricula = this.requisicao.createElement("matricula");
        tagmatricula.appendChild(this.requisicao.createTextNode(matricula));
        root.appendChild(tagmatricula);

        DOMSource dom = new DOMSource(this.requisicao);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        StreamResult result = new StreamResult(new File("requisicao.xml"));
        transformer.transform(dom, result);
    }
}
