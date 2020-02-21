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
                int count = 60000000;

                System.out.println("Wait a little while...");
                cvsUtil.createCvsData(count, cvsFileName);
                System.out.println(String.format("Mock-data are in %s%d.csv file", cvsFileName, count));
                break;
            }
            case 2:{
                String threadName = "index_ide_";

                for (int i = 0; i < 3; i++)
                    new JSONUtil(5, threadName + i).start();

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
