/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saltencryptie;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author mikerooijackers
 */
public class FXMLDocumentController implements Initializable {
    ArrayList<String> encryptedPassword = new ArrayList<String>();
    
    @FXML
    private TextField textfield;
    
    @FXML
    private PasswordField passwordfield;
    
    @FXML
    private Button button;
    
    @FXML
    private Button decrypte;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        System.out.println("You clicked me!");
        String text = textfield.getText();
        String password = passwordfield.getText();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        String publicKeyFilename = "public";

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();

        FileOutputStream fos = new FileOutputStream(publicKeyFilename);
        fos.write(publicKeyBytes);
        fos.close();

        String privateKeyFilename = "privateKeyFilename";

        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

       passwordEncrypt(password.toCharArray(), text.getBytes());

       
        
//        passwordfield.setText("");
//        textfield.setText("");
                
    }
    
    @FXML
    private void handleButtonActiondecrypte(ActionEvent event) throws Exception {
        Path filePath = Paths.get("./","privateKeyFilename");
        byte[] readFile = Files.readAllBytes(filePath);
       
        passwordDecrypt(passwordfield.getText().toCharArray(), readFile);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private static void passwordEncrypt(char[] password, byte[] plaintext) throws Exception {
        int MD5_ITERATIONS = 1000;
        byte[] salt = new byte[8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        PBEKeySpec keySpec = new PBEKeySpec(password);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, MD5_ITERATIONS);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

        byte[] ciphertext = cipher.doFinal(plaintext);
FileOutputStream  fos = new FileOutputStream("privateKeyFilename");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(salt);
        baos.write(ciphertext);
        baos.writeTo(fos);
     
        fos.close();
        
    }
    
    private static void passwordDecrypt(char[] password, byte[] file) throws Exception {
        int MD5_ITERATIONS = 1000;
        byte[] salt = new byte[8];
        FileInputStream inputStream = new FileInputStream("privateKeyFilename");
        inputStream.read(salt, 0 , 8);
        System.out.println(salt);

        PBEKeySpec keySpec = new PBEKeySpec(password);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, MD5_ITERATIONS);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        
        byte[] filteredByteArray = Arrays.copyOfRange(file, 8,file.length);
        byte[] filteredFixedSize = new byte[16];
        System.arraycopy(filteredByteArray, 0,filteredFixedSize, 0, filteredByteArray.length);
        
        byte[] ciphertext = cipher.doFinal(filteredByteArray);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.out.println("salt:" + new String(salt));
        System.out.println("text:" + new String(ciphertext));
    }
}
