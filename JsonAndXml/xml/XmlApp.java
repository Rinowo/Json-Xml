package xml;

import Entity.Student;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlApp {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        writeXmlvsDom();
        readXmlvsDom();
    }
    public static void writeXmlvsDom() throws ParserConfigurationException, TransformerException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document document = builder.newDocument();//Tạo ra đối tượng Document

        //Tạo root element
        Element root = document.createElement("user");
        document.appendChild(root);

        Element roles = document.createElement("roles");
        root.appendChild(roles);

        //Tạo ra attribute thêm vào root
        Attr attr = document.createAttribute("id");
        attr.setValue("1");
        root.setAttributeNode(attr);

        //Tạo ra các elements con của root aka user
        Element name = document.createElement("name");
        name.setTextContent("Rinowo");
        Element email = document.createElement("email");
        email.setTextContent("Rinowogmail.com");
        Element phone = document.createElement("phone");
        phone.setTextContent("03312312312");
        Element role1 = document.createElement("role");
        role1.setTextContent("Leader");
        Element role2 = document.createElement("role");
        role2.setTextContent("Emloyee");
        Element role3 = document.createElement("role");
        role3.setTextContent("Student");

        root.appendChild(name);
        root.appendChild(email);
        root.appendChild(phone);
        roles.appendChild(role1);
        roles.appendChild(role2);
        roles.appendChild(role3);

        //Dom to xml file
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(document), new StreamResult((new File("student.xml"))));
    }
    public static void readXmlvsDom() throws ParserConfigurationException, IOException, SAXException {
        //1. Document
        DocumentBuilder builder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        Document dom = builder.parse(new File("student.xml"));

        //2. Định dạng format chuẩn xml
        dom.normalizeDocument();

        //3. Read xml
        Element root = dom.getDocumentElement();
        //4. Print attribute
        System.out.println("ID: " + root.getAttribute("id"));
        System.out.println("Name: " + root.getElementsByTagName("name").item(0).getTextContent());
        System.out.println("Email: " + root.getElementsByTagName("email").item(0).getTextContent());
        System.out.println("Phone: " + root.getElementsByTagName("phone").item(0).getTextContent());
        System.out.println("Roles: " + root.getElementsByTagName("roles").item(0).getTextContent());
    }
}
