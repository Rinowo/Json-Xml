import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class JSON {
    BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("Customer.json"));

    public JSON() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        jsonWriter();
        jsonReader();
        //jsonEditData();
    }

    public static void jsonWriter() throws IOException {
        //Create writer


        //Create object customer
        JSONObject customer = new JSONObject();
        customer.put("id",1);
        customer.put("name", "Rinuwu");
        customer.put("email", "Rinuwu@gmail.com");
        customer.put("age", 23);

        //Create customer's address
        JSONObject address = new JSONObject();
        address.put("street", "Hang Buom");
        address.put("city", "Ha Noi");
        address.put("state", "Hoan Kiem");

        customer.put("address", address);

        //Payment method
        JSONArray pm = new JSONArray();
        pm.addAll(Arrays.asList("Momo", "Visa"));

        customer.put("paymentMethods", pm);

        //Create projects
        JSONArray projects = new JSONArray();

        //Create 1st project
        JSONObject p1 = new JSONObject();
        p1.put("title", "Java with JSON application");
        p1.put("budget", 5000);

        //Create 2st project
        JSONObject p2 = new JSONObject();
        p2.put("title", "Java with XML application");
        p2.put("budget", 3000);

        //Add project
        projects.addAll(Arrays.asList(p1, p2));

        //Add projects to customer
        customer.put("project", projects);


        //Write JSON to File
        FileWriter fileWriter = new FileWriter("Customer.json");
        fileWriter.write(customer.toJSONString());
        fileWriter.flush();
    }
    public static void jsonReader() throws IOException {
        //GSON
        Reader reader = Files.newBufferedReader(Paths.get("Customer.json"));
        JsonObject parser = JsonParser.parseReader(reader).getAsJsonObject();

        //Read Customer
        System.out.println(parser.get("id").getAsLong());
        System.out.println(parser.get("name").getAsString());
        System.out.println(parser.get("email").getAsString());
        System.out.println(parser.get("age").getAsInt());

        //Read Project
        for (JsonElement project :
                parser.get("project").getAsJsonArray()) {
            JsonObject object = project.getAsJsonObject();
            System.out.println(object.get("title").getAsString());
            System.out.println(object.get("budget").getAsDouble());
        }

        //Read Payment
//        for (JsonElement payment :
//                parser.get("paymentMethods").getAsJsonArray()) {
//            JsonObject object = payment.getAsJsonObject();
//            System.out.println(payment.getAsString());
//        }

    }
    public static void jsonEditData() throws IOException {
        FileWriter writer = new FileWriter("Customer.json");
        Reader reader = Files.newBufferedReader(Paths.get("Customer.json"));
        JSONObject customer = new JSONObject();
        JsonObject parser = JsonParser.parseReader(reader).getAsJsonObject();

        if (parser.get("id").getAsLong() == 1) {

            customer.replace("name", "Rinuwu", "Rinowowowowo");
            customer.replace("email", "Rinuwu@gmail.com", "Rinowoowooo@gmail.com");
            writer.write(customer.toJSONString());
        }
    }
}
