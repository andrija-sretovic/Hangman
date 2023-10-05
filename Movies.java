import java.util.ArrayList;

public class Movies {




    public String getRandomMovie (ArrayList<String> arrayList) {
        int random = (int) (Math.random() * arrayList.size());
        return arrayList.get(random);
    }

    public String movieToUnderscore (String str) {
        StringBuilder underscore = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                underscore.append("-");
            }
            else {
                underscore.append(str.charAt(i));
            }
        }
        return underscore.toString();
    }
}
