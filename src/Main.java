import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        List<String> listOfQuote = readAllLines("input.txt");

        boolean prvniCisliceNalezena = true;
        int prvniCislice=0;
        int prvniCisliceIndex=-1;
        int posledniCislice=0;
        int posledniCisliceIndex=-1;
        int soucet = 0;
        System.out.println(listOfQuote.size());
        System.out.println(listOfQuote.get(0));

        String[] polozky = {"one","two","three","four","five","six","seven","eight","nine"};
        int polozka = -1;
        int temp = -1;
        int tempMin=-1;
        int cisloZtextuMax=-1;
        int cisloZtextuMin=-1;
        boolean prvniIterace = true;

        for (int i = 0; i < listOfQuote.size() ; i++) {
          
            for (int j = 0; j < listOfQuote.get(i).length(); j++) {

                char charakter  = listOfQuote.get(i).charAt(j);
                String charStr = String.valueOf(charakter);
                if (prvniIterace){
                    tempMin = listOfQuote.get(i).length();
                }

                //hledání posledního textového čísla
                for (int k = 0; k < polozky.length; k++) {
                    polozka = listOfQuote.get(i).indexOf(polozky[k],j);
                    if ((polozka != -1) && (polozka>temp)){
                        temp = polozka;
                        cisloZtextuMax = k+1;
                    }
                    //hledání nejmenšího
                    if ((polozka!=-1) && (polozka<tempMin) && prvniIterace){
                        tempMin = polozka;
                        cisloZtextuMin = k+1;
                    }
                }
                prvniIterace = false;
                if (isNumeric(charStr)){
                    if (prvniCisliceNalezena){
                        prvniCislice = Integer.parseInt(charStr);
                        prvniCisliceIndex = j;
                    }
                    prvniCisliceNalezena = false;
                    posledniCislice = Integer.parseInt(charStr);
                    posledniCisliceIndex = j;
                }
            }
            if (posledniCisliceIndex<temp){
                posledniCislice = cisloZtextuMax;
            }
            if (prvniCisliceIndex>tempMin){
                prvniCislice = cisloZtextuMin;
            }

            System.out.printf("\nPrvní číslice je: %d , poslední číslice je: %d",prvniCislice,posledniCislice);
            int spojeniRetezce = Integer.parseInt(Integer.toString(prvniCislice) + Integer.toString(posledniCislice));
            soucet += spojeniRetezce;
            prvniCisliceNalezena = true;
            prvniIterace = true;
            temp = -1;
            tempMin = -1;
            cisloZtextuMin=-1;
            cisloZtextuMax=-1;
        }
        System.out.printf("\nSoučet všech čísel je: %d",soucet);
    }
    public static boolean isNumeric(String num) {
        if (num == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static List<String> readAllLines(String resource)throws IOException {
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            return reader.lines().collect(Collectors.toList());
        }
    }
}