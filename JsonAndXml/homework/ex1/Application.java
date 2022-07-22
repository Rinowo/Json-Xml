package homework.ex1;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException, IOException,
            ParserConfigurationException, TransformerException, SAXException {
        Scanner sc = new Scanner(System.in);
        Controller controller = new Controller();

        while (true) {
            menu();
            int choose = sc.nextInt();

            switch (choose) {
                case 1 -> controller.getAll();
                case 2 -> controller.writeDataToJson();
                case 3 -> controller.writeDataToXml();
                case 4 -> {
                    System.out.print("Enter name to find: ");
                    String name = sc.next();
                    controller.findByNameJson(name);
                }
                case 5 -> {
                    System.out.print("Enter name to find: ");
                    String name = sc.next();
                    controller.findByNameXml(name);
                }
                case 6 -> controller.jsonToDB();
                case 7 -> System.exit(0);
            }
        }
    }

    public static void menu() {
        System.out.println("1. Load data -> Display product information");
        System.out.println("2. Write data to JSON");
        System.out.println("3. Write data to XML");
        System.out.println("4. Find by product name JSON");
        System.out.println("5. Find by product name XML");
        System.out.println("6. Json to DB");
        System.out.println("7. Exit");
    }
}
