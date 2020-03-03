package utils;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.StringType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Locale.GERMANY;

public class CVSUtil {

    private MockNeat m;
    private Path path;


    public CVSUtil(){
        m = MockNeat.threadLocal();
    }

    public void createCvsData(int count, String path){

        String newPath = String.format("%s_%d.csv", path, count);
        this.path = Paths.get(newPath);

        m.csvs().column(m.intSeq())
                .column(m.fmt("#{fname} #{key}. #{lname}")
                        .param("fname", m.names().first())
                        .param("key", m.strings().size(1).type(StringType.LETTERS))
                        .param("lname", m.names().last())
                )
                .column(m.fmt("#{d1}#{d2}#{d3}-#{d4}#{d5}-#{d6}#{d7}#{d8}#{d9}")
                        .param("d1", m.ints().range(0, 9))
                        .param("d2", m.ints().range(0, 9))
                        .param("d3", m.ints().range(0, 9))
                        .param("d4", m.ints().range(0, 9))
                        .param("d5", m.ints().range(0, 9))
                        .param("d6", m.ints().range(0, 9))
                        .param("d7", m.ints().range(0, 9))
                        .param("d8", m.ints().range(0, 9))
                        .param("d9", m.ints().range(0, 9)))
                .column(m.ints().range(10000, 99999))
                .column(m.countries().names())
                .column(m.localDates())
                .column(m.genders())
                .column(m.emails())
                .column(m.creditCards().masterCard())
                .column(m.money().locale(GERMANY).range(2000, 5000))
                .column(m.passwords().medium())
                .separator(",")
                .write(newPath, count);

    }

    public void cteateCvsData(int count){
        m.fmt("#{id},#{first},#{last},#{email},#{salary},#{capital},#{day},#{fmt}")
                .param("id", m.longSeq())
                .param("first", m.names().first())
                .param("last", m.names().last())
                .param("email", m.emails())
                .param("salary", m.money().locale(GERMANY).range(2000, 5000))
                .param("capital", m.cities().capitals())
                .param("day", m.days())
                .param("awd",m.fmt("[1-9]{9}"))
                .list(count)
                .consume(list -> {
                    try { Files.write(path, list, CREATE, WRITE); }
                    catch (IOException e) { e.printStackTrace(); }
                });
    }
}
