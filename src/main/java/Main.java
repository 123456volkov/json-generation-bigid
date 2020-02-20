import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.CVSUtil;
import utils.JSONUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static io.restassured.RestAssured.given;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Switch mode: ");
        System.out.println("#1 : CVS");
        System.out.println("#2 : JSON to url");
        System.out.println("#3 : Delete by indexes");

        switch (scanner.nextInt()){

            case 1:{
                CVSUtil cvsUtil = new CVSUtil();

                String cvsFileName = "mockDataBugInv_";
                int count = 10;

                System.out.println("Wait a little while...");
                cvsUtil.createCvsData(count, cvsFileName);
                System.out.println(String.format("Mock-data are in %s%d.csv file", cvsFileName, count));
                break;
            }
            case 2:{

                Response response = null;
                RestAssured.baseURI = "http://10.2.200.33:9200";

                //int index = 0;
                //int prefix = 1;
                String doc = "index_test_";

                //BufferedWriter writer = new BufferedWriter(new FileWriter("mockData.json"));
                String json = "";

                JSONUtil jsoNutil = new JSONUtil(5);

                for (int i = 0; i <= 10; i++) {
                    /*
                    if (index == 32){
                        index = 0;
                        ++prefix;
                    }
                     */
                    System.out.println("Number :" + i);

                    json = jsoNutil.createNewJSON();

                    response = given()
                            .contentType("application/json")
                            //.header("Authorization", "Basic ZWxhc3RpYzplbGFzdGlj")
                            .body(json)
                            .post(String.format("/%s/primary",  doc + i));

                    System.out.println("End-point :" + RestAssured.baseURI + String.format("/%s/primary",  doc + i));
                    System.out.println("Status Code :" + response.getStatusCode());
                    System.out.println("Response as String :" + response.asString());
                    System.out.println();
                    //++index;

                    //writer.write(json);
                   // writer.write("\n");
                }
               // writer.close();
                break;
            }
            case 3:{

                Response response = null;

                String index = "index_29_1_";

                for (int i = 0; i<300; i++) {

                    response = given()
                            .contentType("application/json")
                            .header("Authorization", "Basic ZWxhc3RpYzplbGFzdGlj")
                            .delete(String.format("/%s", index + i));

                    System.out.println("End-point :" + RestAssured.baseURI + String.format("/%s", index + i));
                    System.out.println("Status Code :" + response.getStatusCode());
                    System.out.println("Response as String :" + response.asString());
                    System.out.println();
                }
                break;
            }

            default:
                System.out.println("Something went wrong");
        }





    }
}
