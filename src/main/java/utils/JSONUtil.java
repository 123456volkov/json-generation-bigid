package utils;

import com.devskiller.jfairy.Fairy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringType;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedWriter;
import java.io.FileWriter;

import static io.restassured.RestAssured.given;

public class JSONUtil extends Thread {
    private int countOfMainKeys;
    private MockNeat m;
    private Fairy fairy;
    String json;

    public JSONUtil(int countOfMainKeys, String name){
        super(name);
        this.countOfMainKeys = countOfMainKeys;
        this.fairy = Fairy.create();
        this.m = MockNeat.threadLocal();
    }

    @Override
    public void run() {
        Response response = null;
        RestAssured.baseURI = "http://10.2.200.33:9200";
        String index = Thread.currentThread().getName();

        BufferedWriter writer = null;
        try {

            writer = new BufferedWriter( new FileWriter(String.format("Test Data/add_info %s.txt", Thread.currentThread().getName())));

            String json = "";

            for (int i = 0; i < 9000; i++) {

                System.out.println("Number :" + i);

                json = this.oneBigJson();

                RequestSpecification connection = given()
                        .contentType("application/json; charset=UTF-8")
                        //.header("Content-Length", "203499")
                        //                    //.header("Connection", "keep-alive")
                        //                    //.header("Authorization", "Basic ZWxhc3RpYzplbGFzdGlj")
                        .body(json);

                while(true) {
                    //connection.body(json);
                    response = connection
                            .post(String.format("/%s/info",index, i));
                    if (response.getStatusCode() == 200 || response.getStatusCode() == 201){
                        System.out.println("End-point :" + RestAssured.baseURI + String.format("/%s/info",index));
                        System.out.println("Response as String :" + response.asString());
                        System.out.println("Status Code :" + response.getStatusCode());
                        System.out.println();
                        break;
                    }
                    if (response.getStatusCode() == 400){
                        System.out.println("retry");
                        System.out.println("Response as String :" + response.asString());
                        System.out.println("Status Code :" + response.getStatusCode());
                        response = connection
                                .body(this.oneBigJson())
                                .post(String.format("/%s/info",index));
                    }
                    if (response.getStatusCode() == 429){
                        System.out.println("Response as String :" + response.asString());
                        System.out.println("Status Code :" + response.getStatusCode());
                        System.out.println("retry");
                        Thread.sleep(20000);
                    }

                }

                /*
                System.out.println("End-point :" + RestAssured.baseURI + String.format("/%s/info",index, i));
                System.out.println("Status Code :" + response.getStatusCode());
                System.out.println("Response as String :" + response.asString());
                System.out.println();

                 */

                Thread.sleep(1000);
                //writer.write(json);

                /*
                writer.write("Number :" + i);
                writer.write("\n");
                writer.write("End-point :" + RestAssured.baseURI + String.format("/%s/_doc/%d", index, i));
                writer.write("\n");
                writer.write("Status Code :" + response.getStatusCode());
                writer.write("\n");
                writer.write("Response as String :" + response.asString());
                writer.write("\n");
                writer.write("\n");
                 */

        }
         writer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String createPair() {
        String keyValuePair = String.format("\"%s\" : \"%s\"", m.strings().size(5).get(), m.strings().size(50).get() );
        return keyValuePair;
    }

    private String createInsertedMass(int x){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("\"%s\" : [", m.strings().size(5).get()));
        int rand = x;
        for (int i = 0; i <= rand; i++){
            stringBuilder.append(String.format("\"%s\"", m.strings().size(50).get()));
            if (i < rand)
                stringBuilder.append(",");
        }
        stringBuilder.append("]");

        return  stringBuilder.toString();
    }

    private String createInsertedMassRandom(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("\"%s\" : [", "_"+ m.strings().size(5).get()));
        int rand = ThreadLocalRandom.current().nextInt(1,5);
        for (int i = 0; i <= rand; i++){
            stringBuilder.append(String.format("\"%s\"", m.strings().size(50).get()));
            if (i < rand)
                stringBuilder.append(",");
        }
        stringBuilder.append("]");

        return  stringBuilder.toString();
    }


    private String createInsertedObject(int x){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("\"%s\" : {", m.strings().size(5).get()));
        if (x%2 == 0){
            stringBuilder.append(createInsertedMass(x));
        }
        else{
            stringBuilder.append(createPair());
        }
        stringBuilder.append("}");

        return  stringBuilder.toString();
    }
    private String createInsertedObjectRandom(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("\"%s\" : {", "_"+ m.strings().size(5).get()));
        int rand = ThreadLocalRandom.current().nextInt(1,50);
        for (int i = 0; i <= rand; i++){
            if (m.bools().get()){
                stringBuilder.append(createInsertedMassRandom());
            }
            else{
                stringBuilder.append(createPair());
            }
            if (i < rand)
                stringBuilder.append(",");
        }
        stringBuilder.append("}");

        return  stringBuilder.toString();
    }

    public String createNewJSONS(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        // для того чтобы добавить рандомных данных
        for (int i = 0; i <= countOfMainKeys; i++) {
            if (i%2 == 0) {
                stringBuilder.append(createInsertedObject(i));
                stringBuilder.append(",");
                continue;
            }
            if (i%10 == 0){
                stringBuilder.append(createPair());
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(String.format("id : \"%s\",", fairy.person().getPassportNumber()));
        /*
        stringBuilder.append(String.format("full_name : \"%s\",", m.fmt("#{fname} #{key}. #{lname}")
                                                                .param("fname", m.names().first())
                                                                .param("key", m.strings().size(1).type(StringType.LETTERS))
                                                                .param("lname", m.names().last()))
        );
         */
        stringBuilder.append(String.format("full_name : \"%s\",", fairy.person().getFirstName() + " " + fairy.person().getMiddleName() + " " + fairy.person().getLastName()));
        stringBuilder.append(String.format("snn : \"%s\",", fairy.person().getNationalIdentityCardNumber()));
        stringBuilder.append(String.format("zip_code : \"%s\",", fairy.person().getAddress().getPostalCode()));
        stringBuilder.append(String.format("country : \"%s\",", m.countries().names().get()));
        stringBuilder.append(String.format("date_of_birth : \"%s\"", fairy.person().getDateOfBirth()));
        //stringBuilder.append(String.format("address : \"%s\"", fairy.person().getAddress()));
        stringBuilder.append("}");


        //System.out.println(stringBuilder.toString());

        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        json = stringBuilder.toString();
        return jsonObject.toString();
    }

    public String createNewJSONRand() throws IOException {

        BufferedWriter writer = null;
        writer = new BufferedWriter( new FileWriter("Test Data/oneBigJSON.json"));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        // для того чтобы добавить рандомных данных
        for (int i = 0; i <= countOfMainKeys; i++){

            if (m.bools().get()) {
                stringBuilder.append(createInsertedObjectRandom());
                stringBuilder.append(",");
            }
            else {
                stringBuilder.append(createPair());
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(String.format("\"id\" : \"%s\",", fairy.person().getPassportNumber()));

        /*stringBuilder.append(String.format("\"full_name\" : \"%s\",", m.fmt("#{fname} #{key}. #{lname}")
                                                                .param("fname", m.names().first())
                                                                .param("key", m.strings().size(1).type(StringType.LETTERS))
                                                                .param("lname", m.names().last()))
        );
         */

        stringBuilder.append(String.format("\"full_name\" : \"%s\",", fairy.person().getFirstName() + " " + fairy.person().getMiddleName() + " " + fairy.person().getLastName()));
        stringBuilder.append(String.format("\"snn\" : \"%s\",", fairy.person().getNationalIdentityCardNumber()));
        stringBuilder.append(String.format("\"zip_code\" : \"%s\",", fairy.person().getAddress().getPostalCode()));
        stringBuilder.append(String.format("\"country\" : \"%s\",", m.countries().names().get()));
        stringBuilder.append(String.format("\"date_of_birth\" : \"%s\"", fairy.person().getDateOfBirth()));
        //stringBuilder.append(String.format("address : \"%s\"", fairy.person().getAddress()));
        stringBuilder.append("}");

        //System.out.println(stringBuilder.toString());
        //JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();

        writer.write(stringBuilder.toString());
        writer.close();

        return null;


    }


    public String oneBigJson(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        // для того чтобы добавить данных
        for (int i = 1; i <= countOfMainKeys; i++){

            if (i < countOfMainKeys/2) {
                stringBuilder.append(createInsertedObject(i));
                stringBuilder.append(",");
            }
            else {
                stringBuilder.append(createPair());
                stringBuilder.append(",");
            }
        }

        stringBuilder.append(String.format("id : \"%s\",", fairy.person().getPassportNumber()));
        /*
        stringBuilder.append(String.format("full_name : \"%s\",", m.fmt("#{fname} #{key}. #{lname}")
                                                                .param("fname", m.names().first())
                                                                .param("key", m.strings().size(1).type(StringType.LETTERS))
                                                                .param("lname", m.names().last()))
        );
         */
        stringBuilder.append(String.format("full_name : \"%s\",", fairy.person().getFirstName() + " " + fairy.person().getMiddleName() + " " + fairy.person().getLastName()));
        stringBuilder.append(String.format("snn : \"%s\",", fairy.person().getNationalIdentityCardNumber()));
        stringBuilder.append(String.format("zip_code : \"%s\",", fairy.person().getAddress().getPostalCode()));
        stringBuilder.append(String.format("country : \"%s\",", m.countries().names().get()));
        stringBuilder.append(String.format("date_of_birth : \"%s\"", fairy.person().getDateOfBirth()));
        //stringBuilder.append(String.format("address : \"%s\"", fairy.person().getAddress()));
        stringBuilder.append("}");


        //System.out.println(stringBuilder.toString());

        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        json = stringBuilder.toString();
        return jsonObject.toString();
    }

    public void deleteIndexes(ArrayList<String> indexes){

        Response response = null;
        RestAssured.baseURI = "http://10.2.200.33:9200";

        for (String index : indexes){
            while(true){
                response = given().
                            delete(index);
                if (response.getStatusCode() == 200){
                    System.out.println("deleted: " + index);
                    break;
                }
                else {
                    System.out.println(response.getStatusCode());
                    continue;
                }

            }
        }
    }
}
