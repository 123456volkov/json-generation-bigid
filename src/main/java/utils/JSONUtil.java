package utils;

import com.devskiller.jfairy.Fairy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.andreinc.mockneat.MockNeat;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedWriter;
import java.io.FileWriter;

import static io.restassured.RestAssured.given;

public class JSONUtil extends Thread {
    private int countOfMainKeys;
    private MockNeat m = MockNeat.threadLocal();
    private Fairy fairy;
    String json;
    private String name;

    public JSONUtil(int countOfMainKeys, String name){
        super(name);
        this.countOfMainKeys = countOfMainKeys;
        this.fairy = Fairy.create();
    }

    @Override
    public void run() {
        Response response = null;
        RestAssured.baseURI = "http://10.2.200.33:9200";

        int docCount = 0;
        int prefix = 1;

        String doc = "_doc";
        String index = Thread.currentThread().getName();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new FileWriter(String.format("add_info %s.txt", Thread.currentThread().getName())));


        String json = "";

        for (int i = 0; i < 10000000; i++) {

            if (docCount == 31){
                docCount = 0;
                ++prefix;
            }


            System.out.println("Number :" + i);

            json = this.createNewJSON();

            // System.out.println(json);

            response = given()
                    .contentType("application/json; charset=UTF-8")
                    //.header("Authorization", "Basic ZWxhc3RpYzplbGFzdGlj")
                    .body(json)
                    .post(String.format("/%s/_doc/%d",index, i));

            System.out.println("End-point :" + RestAssured.baseURI + String.format("/%s/_doc/%d", index, i));
            System.out.println("Status Code :" + response.getStatusCode());
            System.out.println("Response as String :" + response.asString());
            System.out.println();

            ++docCount;

            //writer.write(json);
            writer.write("Number :" + i);
            writer.write("\n");
            writer.write("End-point :" + RestAssured.baseURI + String.format("/%s/_doc/%d", index, i));
            writer.write("\n");
            writer.write("Status Code :" + response.getStatusCode());
            writer.write("\n");
            writer.write("Response as String :" + response.asString());
            writer.write("\n");
            writer.write("\n");
        }
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createPair() {
        String keyValuePair = String.format(" %s : %s ", "_"+ m.strings().size(5).get(), m.strings().size(5).get() );
        return keyValuePair;
    }

    private String createInsertedMass(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s : [", "_"+ m.strings().size(5).get()));
        int rand = ThreadLocalRandom.current().nextInt(1,5);
        for (int i = 0; i <= rand; i++){
            stringBuilder.append(m.strings().size(5).get());
            if (i < rand)
                stringBuilder.append(",");
        }
        stringBuilder.append("]");

        return  stringBuilder.toString();
    }

    private String createInsertedObject(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s : {", "_"+ m.strings().size(5).get()));
        int rand = ThreadLocalRandom.current().nextInt(1,5);
        for (int i = 0; i <= rand; i++){
            if (m.bools().get()){
                stringBuilder.append(createInsertedMass());
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

    public String createNewJSON() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        // для того чтобы добавить рандомных данных
        /* раскоментить
        for (int i = 0; i <= countOfMainKeys; i++){

            if (m.bools().get()) {
                stringBuilder.append(createInsertedObject());
                stringBuilder.append(",");
            }
            else {
                stringBuilder.append(createPair());
                stringBuilder.append(",");
            }
        }

         */

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
        stringBuilder.append("}");


        //System.out.println(stringBuilder.toString());

        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        json = stringBuilder.toString();
        return jsonObject.toString();
    }


    public void deleteIndex(String index){
        Response response = null;
        return;
    }
}
