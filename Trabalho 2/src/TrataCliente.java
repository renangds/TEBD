import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;

public class TrataCliente implements Runnable {
    private Socket client;
    private final int bufferSize = 4096;
    private final byte[] buffer = new byte[bufferSize];

    public TrataCliente(Socket client){
        this.client = client;
    }

    public void run(){
        System.out.println("Thread está sendo executada com o cliente " + this.client.getInetAddress().getHostName());

        try{
            this.receiveRequest();
        } catch(IOException e){
            System.err.println(e);
        }
    }

    private void receiveRequest() throws IOException{
        try{
            //Receive the request from client

            System.out.println("Recebendo dados da requisição...");

            InputStream in = this.client.getInputStream();
            //InputStreamReader isr = new InputStreamReader(in);

            //BufferedReader reader = new BufferedReader(isr);

            File requisitionFile = new File("requisicao.xml");
            FileOutputStream out = new FileOutputStream(requisitionFile);

            int lidos = -1;

            while((lidos = in.read(buffer, 0, bufferSize)) != -1){
                System.out.println(lidos);
                out.write(buffer, 0, lidos);
            }

            Validador validator = new Validador("requisicao.xml", "validacaoRequisicao.xsd");
            boolean valido;

            try{
                valido = validator.validade();
            } catch(SAXException e){
                System.err.println(e);
            }

            out.flush();

            System.out.println("Dados recebidos!");


        } catch(IOException e){
            System.err.println(e);
        }
    }

    private void sendResponse() throws IOException{
        try{
            //Sending the response to client

            System.out.println("enviar dados ao cliente...");

            OutputStream os = this.client.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);

            DataOutputStream dos = new DataOutputStream(this.client.getOutputStream());

            File fileSender = new File("/Users/renan/IdeaProjects/T1 TEBD/src/XML/erro.xml");
            FileInputStream inFile = new FileInputStream(fileSender);

            os.flush();

            int lidos = -1;

            while((lidos = inFile.read(buffer, 0, bufferSize)) != -1){
                System.out.println("Colocados " + lidos + " no buffer");
                dos.write(buffer, 0, lidos);
            }

            /*
            while((lidos = inFile.read(buffer, 0, bufferSize)) != -1){
                System.out.println("Colocados " + lidos + " no buffer");
                os.write(buffer, 0, lidos);
            }
            */

            System.out.println("Histórico enviado ao cliente!");
        } catch(IOException e){
            System.err.println(e);
        }
    }

    private boolean operationIsValid(){
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new File("requisicao.xml"));

            doc.getDocumentElement().normalize();

            if(doc.getElementsByTagName("nomefuncao").item(0).getTextContent().equals("HistoricoAluno")){
                return true;
            }
        } catch(IOException e){
            System.err.println(e);
        } catch(SAXException e){
            System.err.println(e);
        } catch(ParserConfigurationException e){
            System.err.println(e);
        }

        return false;
    }
}
