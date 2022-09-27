import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class Soal1 {
    public static void main(String[] args){

        String[] stringArray = new String[] {"X", "C", "B", "D", "L", "A", "A", "C", "D", "C", "A"};
        HashMap<String, Integer> mapCount = new HashMap<>();
        for (int i = 0; i<stringArray.length ;i++) {
            String thisChar = stringArray[i];
            if (mapCount.containsKey(thisChar)){
                int cnt = mapCount.get(thisChar) + 1;
                if (cnt >= 3)
                {
                    System.out.println("result is "+thisChar);
                    break;
                }
                mapCount.put(thisChar, cnt );
            }
            else
                mapCount.put(thisChar, 1);
        }

        System.out.println("=======");
    }
}