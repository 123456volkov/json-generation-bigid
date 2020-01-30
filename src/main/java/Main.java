import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.JSONUtil;

import static io.restassured.RestAssured.given;

public class Main {
    public static void main(String[] args) {

        int index = 0;
        int prefix = 1;
        String doc = "indx_29_1_";

        Response response = null;
        RestAssured.baseURI = "http://10.2.200.225:9200";
        //BufferedWriter writer = new BufferedWriter(new FileWriter("mockData.json"));
        String json = "";

        JSONUtil jsoNutil = new JSONUtil(5);

        for (int i = 0; i <= 1000000; i++) {
            if (index == 32){
                index = 0;
                ++prefix;
            }
            System.out.println("Number :" + i);

            json = jsoNutil.createNewJSON();

            response = given()
                    .contentType("application/json")
                    .header("Authorization", "Basic ZWxhc3RpYzplbGFzdGlj")
                    .body(json)
                    .post(String.format("/%s/primary",  doc + prefix));

            System.out.println("End-point :" + RestAssured.baseURI + String.format("/%s/primary",  doc + prefix));
            System.out.println("Status Code :" + response.getStatusCode());
            System.out.println("Response as String :" + response.asString());
            System.out.println();
            ++index;
            //writer.write(jsoNutil.createNewJSON());
            //writer.write("\n");
        }
        //writer.close();
    }
}
