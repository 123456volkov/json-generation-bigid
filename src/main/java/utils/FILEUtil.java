package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FILEUtil {

    private BufferedWriter writer = null;

    private String loremWithPII = "Minim cillum adipisicing irure occaecat eu esse quis nostrud aute. Minim labore mollit adipisicing fugiat. Dolor duis irure pariatur non pariatur. Minim quis ut Lorem tempor excepteur consequat ut et incididunt. Nostrud laboris qui adipisicing reprehenderit ex aliquip enim sint minim est Lorem nostrud nisi. Aliquip consequat laboris proident minim amet ad culpa eu quis non sint amet dolor. Aute id minim sit nulla nostrud.\n" +
            "Sunt enim elit occaecat nulla voluptate quis duis deserunt quis exercitation. Exercitation magna aute amet nisi dolore qui duis ad cillum id. Adipisicing irure ex sint ex ullamco magna.\n" +
            "Proident nisi consectetur proident non est Lorem ex dolor ea laborum commodo consectetur. Aliquip anim ea fugiat consequat ut sunt nulla Lorem sint proident velit pariatur. Fugiat irure eiusmod incididunt qui excepteur irure consectetur eiusmod excepteur ullamco est labore deserunt. Esse amet duis qui dolor excepteur dolor elit enim voluptate cillum mollit ipsum tempor.\n" +
            "Commodo minim laborum nulla qui dolore voluptate non veniam nostrud et est. Aliquip cillum dolor ex ex tempor nulla culpa consectetur fugiat amet. Quis veniam minim cillum adipisicing sint culpa occaecat in id culpa dolor id.\n" +
            "   Thomas U. Banks - 25/5/1966 - RU    \n" +
            "Dolore ut sint excepteur eu aliquip laboris consequat culpa culpa et esse. Ea aute duis aliqua proident sunt laborum. Dolore minim minim mollit quis duis.\n" +
            "Do exercitation dolor ut minim in laborum anim nisi est exercitation. Aliquip non adipisicing aliquip occaecat qui anim reprehenderit in velit sunt aute. Nisi ad ex mollit labore adipisicing pariatur minim officia exercitation aliqua proident excepteur nisi. In nisi eiusmod veniam excepteur fugiat esse nulla esse. Ad cupidatat mollit id sit exercitation quis voluptate ullamco pariatur sit. Irure et id sit veniam fugiat commodo. Excepteur ullamco qui laboris ea laborum laborum reprehenderit id excepteur do laboris ut magna deserunt.\n" +
            "Occaecat mollit sunt ea qui magna eiusmod labore qui. Minim commodo ut cupidatat elit duis fugiat dolor ea ex sint. Eu proident nisi occaecat reprehenderit esse excepteur commodo. Mollit sit aute exercitation reprehenderit magna minim. Eiusmod ullamco consequat labore cillum.\n" +
            "Ipsum Lorem et aliqua esse deserunt pariatur commodo. Cupidatat velit sunt mollit irure cillum dolore exercitation proident officia. Aliquip anim anim enim consectetur Lorem nisi. Dolore fugiat aute officia enim cupidatat ea reprehenderit magna incididunt reprehenderit. Excepteur nisi incididunt ut veniam sunt nostrud velit cillum duis duis ullamco sint incididunt.\n" +
            "Cillum veniam ad cillum aliqua. Dolore aliqua officia deserunt in elit irure consequat eiusmod magna. Exercitation Lorem occaecat officia aliqua aute. Est est elit occaecat anim nostrud laborum eiusmod irure in esse incididunt esse cillum.\n" +
            "Tempor reprehenderit ex et qui mollit anim dolor pariatur sint est amet. Ullamco duis enim irure aute do officia. Irure exercitation irure ullamco ullamco mollit est irure ipsum cillum aliqua. In labore deserunt deserunt elit elit culpa nostrud. Pariatur occaecat ullamco do non aliquip tempor do proident excepteur. Laborum quis est duis velit eiusmod amet enim proident in non consequat.\n";

    private String loremWithOutPII = "Minim cillum adipisicing irure occaecat eu esse quis nostrud aute. Minim labore mollit adipisicing fugiat. Dolor duis irure pariatur non pariatur. Minim quis ut Lorem tempor excepteur consequat ut et incididunt. Nostrud laboris qui adipisicing reprehenderit ex aliquip enim sint minim est Lorem nostrud nisi. Aliquip consequat laboris proident minim amet ad culpa eu quis non sint amet dolor. Aute id minim sit nulla nostrud.\n" +
            "Sunt enim elit occaecat nulla voluptate quis duis deserunt quis exercitation. Exercitation magna aute amet nisi dolore qui duis ad cillum id. Adipisicing irure ex sint ex ullamco magna.\n" +
            "Proident nisi consectetur proident non est Lorem ex dolor ea laborum commodo consectetur. Aliquip anim ea fugiat consequat ut sunt nulla Lorem sint proident velit pariatur. Fugiat irure eiusmod incididunt qui excepteur irure consectetur eiusmod excepteur ullamco est labore deserunt. Esse amet duis qui dolor excepteur dolor elit enim voluptate cillum mollit ipsum tempor.\n" +
            "Commodo minim laborum nulla qui dolore voluptate non veniam nostrud et est. Aliquip cillum dolor ex ex tempor nulla culpa consectetur fugiat amet. Quis veniam minim cillum adipisicing sint culpa occaecat in id culpa dolor id.\n" +
            "Dolore ut sint excepteur eu aliquip laboris consequat culpa culpa et esse. Ea aute duis aliqua proident sunt laborum. Dolore minim minim mollit quis duis.\n" +
            "Do exercitation dolor ut minim in laborum anim nisi est exercitation. Aliquip non adipisicing aliquip occaecat qui anim reprehenderit in velit sunt aute. Nisi ad ex mollit labore adipisicing pariatur minim officia exercitation aliqua proident excepteur nisi. In nisi eiusmod veniam excepteur fugiat esse nulla esse. Ad cupidatat mollit id sit exercitation quis voluptate ullamco pariatur sit. Irure et id sit veniam fugiat commodo. Excepteur ullamco qui laboris ea laborum laborum reprehenderit id excepteur do laboris ut magna deserunt.\n" +
            "Occaecat mollit sunt ea qui magna eiusmod labore qui. Minim commodo ut cupidatat elit duis fugiat dolor ea ex sint. Eu proident nisi occaecat reprehenderit esse excepteur commodo. Mollit sit aute exercitation reprehenderit magna minim. Eiusmod ullamco consequat labore cillum.\n" +
            "Ipsum Lorem et aliqua esse deserunt pariatur commodo. Cupidatat velit sunt mollit irure cillum dolore exercitation proident officia. Aliquip anim anim enim consectetur Lorem nisi. Dolore fugiat aute officia enim cupidatat ea reprehenderit magna incididunt reprehenderit. Excepteur nisi incididunt ut veniam sunt nostrud velit cillum duis duis ullamco sint incididunt.\n" +
            "Cillum veniam ad cillum aliqua. Dolore aliqua officia deserunt in elit irure consequat eiusmod magna. Exercitation Lorem occaecat officia aliqua aute. Est est elit occaecat anim nostrud laborum eiusmod irure in esse incididunt esse cillum.\n" +
            "Tempor reprehenderit ex et qui mollit anim dolor pariatur sint est amet. Ullamco duis enim irure aute do officia. Irure exercitation irure ullamco ullamco mollit est irure ipsum cillum aliqua. In labore deserunt deserunt elit elit culpa nostrud. Pariatur occaecat ullamco do non aliquip tempor do proident excepteur. Laborum quis est duis velit eiusmod amet enim proident in non consequat.\n";


    private void readeToFile(String text){

    }

    public void createParticularCountOfFilesWithOUTPII(int count) throws IOException {


        for (int i = 1; i <= count; i++){
            writer = new BufferedWriter( new FileWriter(String.format("Test Data/particular count of files/%d.txt", i)));
            writer.write(loremWithOutPII);
        }
        writer.close();

        System.out.println("DONE!");
    }

    public void createParticularCountOfFilesWithPII(int count) throws IOException {

        for (int i = 1; i <= count; i++){
            writer = new BufferedWriter( new FileWriter(String.format("Test Data/particular count of files/%d.txt", i)));
            writer.write(loremWithPII);
            writer.close();
        }

        System.out.println("DONE!");
    }
}
