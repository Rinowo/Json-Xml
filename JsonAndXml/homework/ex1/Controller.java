package homework.ex1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    List<Product> list = new ArrayList<>();
    Product product = new Product();

    public Connection getConnection() throws SQLException {
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=Product Manament";
        String username = "Rinowo";
        String password = "Rinochan205.";
        return DriverManager.getConnection(dbURL, username, password);
    }

    public void addProduct(Product product) {
        list.add(product);
    }

    public List<Product> getAll() throws SQLException {
        String query = "SELECT * FROM product";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");
            String description = resultSet.getString("description");
            Product product = new Product(id, name, price, quantity, description);
            addProduct(product);
            System.out.println("Id: " + id + "| Name: " + name + "| Price: " + price + "| Quantity: " + quantity + "| Description: " + description);
            System.out.println();
        }
        return list;
    }

    public void writeDataToJson() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = Files.newBufferedWriter(Paths.get("Product.json"));
        gson.toJson(list, writer);
        writer.close();
        System.out.println("Write Success");
    }

    public void findByNameJson(String name) throws IOException {

        FileReader reader = new FileReader("Product.json");
        list = new Gson().fromJson(reader, new TypeToken<List<Product>>(){}.getType());

        for (Product product :
                list) {
            if (product.getName().equals(name)) {
                System.out.println(product);
                break;
            } else {
                System.err.println("Not Found");
            }
        }
        reader.close();
    }

    public void writeDataToXml() throws ParserConfigurationException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = builder.newDocument();

        Element root = dom.createElement("Store");
        dom.appendChild(root);

        for (Product product :
                list) {
            Element products = dom.createElement("Product");
            root.appendChild(products);

            Attr attr = dom.createAttribute("id");
            attr.setValue(String.valueOf(product.getId()));
            products.setAttributeNode(attr);

            Element name = dom.createElement("name");
            name.setTextContent(product.getName());

            Element price = dom.createElement("price");
            price.setTextContent(String.valueOf(product.getPrice()));

            Element quantity = dom.createElement("quantity");
            quantity.setTextContent(String.valueOf(product.getQuantity()));

            Element description = dom.createElement("description");
            description.setTextContent(product.getDescription());

            products.appendChild(name);
            products.appendChild(price);
            products.appendChild(quantity);
            products.appendChild(description);

        }
        Transformer transformer = TransformerFactory.newDefaultInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(dom), new StreamResult((new File("product.xml"))));
        System.out.println("Write Success");
    }

    public void findByNameXml(String name) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        Document dom = builder.parse(new File("product.xml"));

        dom.normalizeDocument();

        Element root = dom.getDocumentElement();
        Element products = dom.getDocumentElement();
        String name1 = products.getElementsByTagName("name").item(0).getTextContent();

        for (Product product :
                list) {
            if (products.getElementsByTagName("name").item(0).getTextContent().equals(name)) {
                System.out.println(product);
            } else {
                System.err.println("Not Found");
            }
        }
    }
}
