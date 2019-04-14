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

public class Historico {

    public static void main(String...args) throws Exception{
        new Historico().criarHistorico();
    }

    private DocumentBuilder builder;
    private Document novoHistorico;

    public Historico() throws Exception{
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        this.novoHistorico = builder.newDocument();
    }

    public void criarHistorico() throws Exception{
        Element root = novoHistorico.createElement("historico");
        novoHistorico.appendChild(root);

        Element nome = novoHistorico.createElement("nome");
        nome.appendChild(novoHistorico.createTextNode("Renan"));
        root.appendChild(nome);

        Element matricula = novoHistorico.createElement("matricula");
        matricula.appendChild(novoHistorico.createTextNode("2014780550"));
        root.appendChild(matricula);

        Element curso = novoHistorico.createElement("curso");
        curso.appendChild(novoHistorico.createTextNode("Ciência da Computação"));
        root.appendChild(curso);

        Element codigocurso = novoHistorico.createElement("codigocurso");
        codigocurso.appendChild(novoHistorico.createTextNode("DCC"));
        root.appendChild(codigocurso);

        Element anoingresso = novoHistorico.createElement("anoingresso");
        anoingresso.appendChild(novoHistorico.createTextNode("2014"));
        root.appendChild(anoingresso);

        Element disciplinas = novoHistorico.createElement("disciplinas");
        root.appendChild(disciplinas);

        Element disciplina = novoHistorico.createElement("disciplina");
        disciplinas.appendChild(disciplina);

        Element periodo = novoHistorico.createElement("periodo");
        periodo.appendChild(novoHistorico.createTextNode("1"));
        disciplina.appendChild(periodo);

        Element codigo = novoHistorico.createElement("codigo");
        codigo.appendChild(novoHistorico.createTextNode("DCC001"));
        disciplina.appendChild(codigo);

        Element credito = novoHistorico.createElement("credito");
        credito.appendChild(novoHistorico.createTextNode("4"));
        disciplina.appendChild(credito);

        Element nomeDisciplina = novoHistorico.createElement("nome");
        nomeDisciplina.appendChild(novoHistorico.createTextNode("Computação 1"));
        disciplina.appendChild(nomeDisciplina);

        Element nota = novoHistorico.createElement("nota");
        nota.appendChild(novoHistorico.createTextNode("8"));
        disciplina.appendChild(nota);

        Element situacao = novoHistorico.createElement("situacao");
        situacao.appendChild(novoHistorico.createTextNode("APR"));
        disciplina.appendChild(situacao);

        DOMSource dom = new DOMSource(novoHistorico);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        //transformer.transform(dom, new StreamResult(System.out));

        List <Element> algos = this.criarDisciplinas();

        algos.forEach(p -> disciplinas.appendChild(p));

        String filename = "historico.xml";

        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(dom, result);
    }


    public List<Element> criarDisciplinas() throws Exception{
        List <Element> disciplinas = new ArrayList<>();

        disciplinas.add(this.novoHistorico.createElement("disciplina"));
        disciplinas.add(this.novoHistorico.createElement("disciplina"));
        disciplinas.add(this.novoHistorico.createElement("disciplina"));
        disciplinas.add(this.novoHistorico.createElement("disciplina"));
        disciplinas.add(this.novoHistorico.createElement("disciplina"));

        return disciplinas;
    }

    /*
    public Element criarListaDisciplina() throws Exception{

    }
    */

}
