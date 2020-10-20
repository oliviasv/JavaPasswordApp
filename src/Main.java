import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Please, enter your password:");
        InputStreamReader passSet = new InputStreamReader(System.in);
        BufferedReader passKey = new BufferedReader(passSet);
        String password = passKey.readLine();

        String originalPassword = "andromeda";
        byte[] salt = getSalt();

        try {
            FileWriter myWriter = new FileWriter("passwordToHash.txt");
            myWriter.write(getSecurePassword(originalPassword, salt));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        FileReader fr = new FileReader("passwordToHash.txt");
        BufferedReader br = new BufferedReader(fr);
        String s="";
        s = br.readLine();

        if (validatePassword(password, salt).equals(s)) {
            System.out.println("Welcome!");
        } else {
            System.out.println("Wrong Password");
        }
    }


    private static String getSecurePassword(String originalPassword, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] bytes = md.digest(originalPassword.getBytes());
        return toHex(bytes);
    }

    private static String validatePassword (String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] bytes = md.digest(password.getBytes());
        return toHex(bytes);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

}
