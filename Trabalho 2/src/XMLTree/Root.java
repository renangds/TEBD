package XMLTree;

/*
        Mini API de criação de documentos XML
        para o trabalho de TEBD
*/


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.List;

public class Root{
    protected List<Element> elements;
    private Document rootDocumentXML;

    public Root( String tag, String content) throws ParserConfigurationException{
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        this.rootDocumentXML = builder.newDocument();

        Element root = this.rootDocumentXML.createElement(tag);
        root.appendChild(this.rootDocumentXML.createTextNode(content));

        this.rootDocumentXML.appendChild(root);
    }

    public void addBranches(String tag, String content) throws ParserConfigurationException{
        //Cria um builder de XMLs
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        //Cria a nova tag filha da tag raiz do documento XML
        Element branch = this.rootDocumentXML.createElement(tag);
        branch.appendChild(this.rootDocumentXML.createTextNode(content));

        //Adiciona o novo elemento criado ao documento XML
        this.rootDocumentXML.appendChild(branch);

        //Adiciona o novo elemento a lista da classe
        this.elements.add(branch);
    }

    public List<Element> getBranches(){
        return this.elements;
    }

    public Document getRootDocumentXML(){
        return this.rootDocumentXML;
    }
}
