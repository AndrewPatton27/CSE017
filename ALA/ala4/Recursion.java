public class Recursion {

    public static int count(String str, char c) {
        if (str.length() == 0) {
            return 0;
        } 
        return (str.charAt(0) == c ? 1 : 0) + count(str.substring(1), c) ;
    }

    public static void permutations(String s) {
        permutations("", s);
    }

    public static void permutations(String s1, String s2) {
        if (s2.length() == 0) {
            System.out.println(s1);
            return;
        }
        for (int i=0; i<s2.length(); i++) {
            permutations(s1 + s2.charAt(i), 
                            s2.substring(0,i)+s2.substring(i+1));
        }
    }

}
