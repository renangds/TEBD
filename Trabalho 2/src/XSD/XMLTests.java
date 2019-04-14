package XSD;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import XMLTree.*;

public class XMLTests {

    public static void main(String...args) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File("/Users/renan/IdeaProjects/T1 TEBD/src/XML/requisicao.xml"));
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("requisicao");

        Node first = nodeList.item(0);

        System.out.println(first.getNodeName());

        Node first2 = doc.getElementsByTagName("requisicao").item(0);

        NodeList nodeList2 = first2.getChildNodes();

        System.out.println(doc.getElementsByTagName("nomefuncao").item(0).getTextContent());

        //Node current;



        /*
        Root arvore = new Root("historico", "Renan");

        System.out.println(arvore.getRootDocumentXML().getElementsByTagName("historico").item(0).getTextContent());
        */
    }
}
