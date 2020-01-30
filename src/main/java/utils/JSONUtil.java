package utils;

import com.devskiller.jfairy.Fairy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.andreinc.mockneat.MockNeat;

import java.util.concurrent.ThreadLocalRandom;

public class JSONUtil {
    private int countOfMainKeys;
    private MockNeat mockNeat = MockNeat.threadLocal();
    private Fairy fairy;
    String json;

    public JSONUtil(int countOfMainKeys){
        this.countOfMainKeys = countOfMainKeys;
        this.fairy = Fairy.create();
    }

    private String createPair() {
        String keyValuePair = String.format(" %s : %s ", "_"+mockNeat.strings().size(5).get(), mockNeat.strings().get() );
        return keyValuePair;
    }

    private String createInsertedMass(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s : [", "_"+mockNeat.strings().size(5).get()));
        int rand = ThreadLocalRandom.current().nextInt(1,5);
        for (int i = 0; i <= rand; i++){
            stringBuilder.append(mockNeat.strings().get());
            if (i < rand)
                stringBuilder.append(",");
        }
        stringBuilder.append("]");

        return  stringBuilder.toString();
    }

    private String createInsertedObject(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%s : {", "_"+mockNeat.strings().size(5).get()));
        int rand = ThreadLocalRandom.current().nextInt(1,5);
        for (int i = 0; i <= rand; i++){
            if (mockNeat.bools().get()){
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
            if (mockNeat.bools().get()) {
                stringBuilder.append(createInsertedObject());
                stringBuilder.append(",");
            }
            else {
                stringBuilder.append(createPair());
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(String.format("ID : \"%s\",", fairy.person().getPassportNumber()));
        stringBuilder.append(String.format("name : \"%s\",", mockNeat.names().full().get()));
        stringBuilder.append(String.format("country : \"%s\",", mockNeat.countries().names().get()));
        stringBuilder.append(String.format("snn : \"%s\",", fairy.person().getNationalIdentityCardNumber()));
        stringBuilder.append(String.format("postal_code : \"%s\",", fairy.person().getAddress().getPostalCode()));
        stringBuilder.append(String.format("dateOfBirth : \"%s\"", fairy.person().getDateOfBirth()));
        stringBuilder.append("}");

        //System.out.println(stringBuilder.toString());

        JsonObject jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
        json = stringBuilder.toString();
        return jsonObject.toString();
    }
}
