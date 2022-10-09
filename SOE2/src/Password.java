import javax.swing.*;
public class Password {

    public static boolean hasMinimumLength(String str){
        return str.length() >= 8;
    }
    //String contains all the special characters
    private static final String allValidSpecialChar = "!@#$%^&*()_+";
    /**
     * * Checks for valid special char
     * * @param str
     * * @return
     */
    //123456Aa*
    private static boolean isSpecialChar(char c) {
        return allValidSpecialChar.contains(String.valueOf(c));
    }

    //boolean method for a lowercase letter which will return true if the password contains a lower letter
    public static boolean hasLowerLetter(String str){
        for (char c: str.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                return true;
            }
        }
        return false;
    }

    //boolean method for an uppercase letter which will return true if the password contains an uppercase letter
    public static boolean hasUpperLetter(String str) {
        for (char c : str.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                return true;
            }
        }
        return false;
    }

    //boolean method for a number which will return true if the password contains a number
    public static boolean hasNumericChar(String str) {
        for (char c : str.toCharArray()) {
            if (c >= '0' && c <= '9') {
                return true;
            }
        }
        return false;
    }

    //boolean method for a special character which will return true if the password contains a special character
    public static boolean hasSpecialChar(String str) {
        for (char c : str.toCharArray()) {
            //isSpecialChar() contains a String which has all the special characters in it
            if(isSpecialChar(c))
                return true;
        }
        return false;
    }

    /* We create a boolean hasError and assigned it to false. Then we create a StringBuilder called errorStr
     to which we will add to what the password needs to have. When we use this method, it will start to check for
     the length. If the length is not enough, it will print an error message and change the boolean to true.
     Then it will go to the next if statement and check for the lowercase letter, uppercase letter, number and
     lastly it will check for the special character.
     */
    public static boolean checkPassword(JFrame frame, String password) {
        StringBuilder errorStr = new StringBuilder("Password must have at least one");
        boolean hasError = false;
        if(!hasMinimumLength(password)){
            errorStr = new StringBuilder("password '" + password + "'  is too short!\"");
            hasError = true;
        }
        if (!hasError && !hasLowerLetter(password)){
            errorStr.append(" lower case char");
            hasError = true;
        }
        if (!hasError && !hasUpperLetter(password)){
            errorStr.append(" upper case char");
            hasError = true;
        }
        if (!hasError && !hasNumericChar(password)){
            errorStr.append(" numeric char");
            hasError = true;
        }
        if (!hasError && !hasSpecialChar(password)){
            errorStr.append(" special char");
            hasError = true;
        }

        if(hasError){
            JOptionPane.showMessageDialog(frame, errorStr.toString(), "Password Error", JOptionPane.ERROR_MESSAGE);
        }
        return !hasError;
    }
}

