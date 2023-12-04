package TemaTest.CommonUse;

public class Identifier {
    public static boolean checkIdentifier(String[] args) {
        if(args.length == 3 || args.length <= 2) {
            return true;
        }
        return false;
    }
    public static boolean checkCredentials(String[] args) {
        if(args.length <= 2) {
            return true;
        }
        return false;
    }
}
