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
        System.out.println("#3 : JSON to file");
        System.out.println("#4 : Delete by indexes");

        switch (scanner.nextInt()){

            case 1: {
                CVSUtil cvsUtil = new CVSUtil();

                String cvsFileName = "ES_csv";
                int count = 105000;

                System.out.println("Wait a little while...");
                cvsUtil.createCvsData(count, cvsFileName);
                System.out.println(String.format("Mock-data are in %s%d.csv file", cvsFileName, count));
                break;
            }
            case 2:{
                String threadName = "big_json_";

                //for (int i = 1; i < 3; i++)
                    new JSONUtil(100, threadName + 2).start();

                break;
            }
            case 3:{
                BufferedWriter writer = null;
                writer = new BufferedWriter( new FileWriter("oneBigJSON.json"));

                String bigJson = new JSONUtil(100, "testName").oneBigJson();

                writer.write(bigJson);
                writer.close();
                break;
            }
            case 4:{

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
