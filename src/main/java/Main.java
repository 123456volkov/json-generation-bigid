import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.CVSUtil;
import utils.FILEUtil;
import utils.JSONUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
        System.out.println("#5 : Delete indexes");
        System.out.println("*******************************");
        System.out.println("#6 : Create particular count of files with PII");

        switch (scanner.nextInt()){

            case 1: {
                CVSUtil cvsUtil = new CVSUtil();

                String cvsFileName = "ES_csv";
                int count = 45000;

                System.out.println("Wait a little while...");
                cvsUtil.createCvsData(count, cvsFileName);
                System.out.println(String.format("Mock-data are in %s%d.csv file", cvsFileName, count));
                break;
            }
            case 2:{
                String threadName = "index_";

                //for (int i = 7; i <= 8; i++)
                    new JSONUtil(100, threadName + 2).start();
                    new JSONUtil(100, threadName + 2).start();
                    new JSONUtil(100, threadName + 2).start();
                    new JSONUtil(100, threadName + 2).start();
                    //new JSONUtil(100, threadName + 1).start();

                break;
            }
            case 3:{

                String bigJson = new JSONUtil(100000, "testName").createNewJSONRand();

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
            case 5:{
                ArrayList<String> indexes = new ArrayList<>();

                indexes.add("index_five");
                indexes.add("index_six");
                indexes.add("index_seven");
                indexes.add("index_nine");
                indexes.add("index_ten");
                indexes.add("index_eleven");
                indexes.add("index_twelf");;
                indexes.add("index_thirteen");
                indexes.add("index_fourteen");
                indexes.add("index_fifteen");

                new JSONUtil(1, "test").deleteIndexes(indexes);

                break;
            }
            case 6:{
                System.out.println();
                System.out.println("Enter count of files : ");
                int count = scanner.nextInt();

                new FILEUtil().createParticularCountOfFilesWithPII(count);

                break;
            }
            default:
                System.out.println("Something went wrong");
        }
    }
}
