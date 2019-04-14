import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class Validador {
    private File xsdFile;
    private File xmlFile;

    public static void main(String...args) throws SAXException, IOException{
        boolean x = new Validador("/Users/renan/IdeaProjects/T1 TEBD/src/XML/requisicao.xml",
                "/Users/renan/IdeaProjects/T1 TEBD/src/XSD/validacaoRequisicao.xsd").validade();

        System.out.println(x);
    }

    public Validador(String xmlPath, String xsdPath){
        this.xsdFile = new File(xsdPath);
        this.xmlFile = new File(xmlPath);
    }


    public boolean validade() throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        File schemaLocation = this.xsdFile;
        Schema schema = factory.newSchema(schemaLocation);

        Validator validator = schema.newValidator();

        Source source = new StreamSource(xmlFile);

        try {
            validator.validate(source);
            System.out.println(xmlFile.getName() + " é válido");

            return true;
        }
        catch (SAXException ex) {
            System.out.println(xmlFile.getName() + " não é válido pois");
            System.out.println(ex.getMessage());

            return false;
        }
    }
}
