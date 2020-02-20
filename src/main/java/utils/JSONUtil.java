package utils;

import com.devskiller.jfairy.Fairy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringType;

import java.util.concurrent.ThreadLocalRandom;

public class JSONUtil {
    private int countOfMainKeys;
    private MockNeat m = MockNeat.threadLocal();
    private Fairy fairy;
    String json;

    public JSONUtil(int countOfMainKeys){
        this.countOfMainKeys = countOfMainKeys;
        this.fairy = Fairy.create();
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

        stringBuilder.append(String.format("id : \"%s\",", fairy.person().getPassportNumber()));
        stringBuilder.append(String.format("full_name : \"%s\",", m.fmt("#{fname} #{key}. #{lname}")
                                                                .param("fname", m.names().first())
                                                                .param("key", m.strings().size(1).type(StringType.LETTERS))
                                                                .param("lname", m.names().last())));
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
