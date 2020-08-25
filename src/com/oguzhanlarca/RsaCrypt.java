package com.oguzhanlarca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class RsaCrypt {
    public static final String ALGORITHM = "RSA";
    public static final String PRIVATE_KEY_FILE = "CryptoFiles/private.key";
    public static final String PUBLIC_KEY_FILE = "CryptoFiles/public.key";
    public static final int Key = 512;

    public File createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    // Aldigi adresteki dosyayi okur ve string tipinde dondurur
    public String readData(String fileAddress) {
        StringBuilder out = new StringBuilder();
        InputStream in;
        try {
            in = new FileInputStream(fileAddress);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    // Anahtar degerlerini uretme methodu
    public void generateKey() {
        File file;
        KeyPairGenerator keyGen = null;
        try {
            // belirtilen bit boyutuna gore anahtar ciftleri uretilir
            keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(Key);
            final KeyPair key = keyGen.generateKeyPair();

            file = createFile(PRIVATE_KEY_FILE);
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream(file));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();

            file = createFile(PUBLIC_KEY_FILE);
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(file));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Anahtarlar mevcut mu kontrolu
    public boolean areKeysPresent() {
        File privateKey = new File(PRIVATE_KEY_FILE);
        File publicKey = new File(PUBLIC_KEY_FILE);
        if (privateKey.exists() && publicKey.exists())
            return true;
        return false;
    }

    // Sifreleme methodu
    public String encrypt(String plainTextAddress, String cipherTextAddress) {
        // sifreli mesajin adres degerleri
        try {
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream(PUBLIC_KEY_FILE));
            final PublicKey pubKey = (PublicKey) inputStream.readObject();
            inputStream.close();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            FileInputStream fis = new FileInputStream(plainTextAddress);
            FileOutputStream fos = new FileOutputStream(cipherTextAddress);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            byte[] block = new byte[32];
            int i;

            // dongu ile byte byte okunur ve sifrelenir
            // icerik sonuna gelindiginde while dongusu sona erer.

            while ((i = fis.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();
            fis.close();
            fos.close();

            String cipherText = readData(cipherTextAddress);

            return "\nSifreleme islemi tamamlandi. Sifreli metin:\n" + cipherText;
        } catch (Exception e) {
            e.printStackTrace();
            return "Sifreleme islemi sirasinda hata olustu.";
        }
    }

    // Sifre cozme methodu
    public String decrypt( String ciphertextAddress, String plainTextAgainAddress) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream( new FileInputStream(PRIVATE_KEY_FILE));
            final PrivateKey privKey = (PrivateKey)inputStream.readObject();
            inputStream.close();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privKey);

            FileInputStream fis = new FileInputStream(ciphertextAddress);
            FileOutputStream fos = new FileOutputStream(plainTextAgainAddress);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            byte[] block = new byte[32];
            int i;
            while ((i = cis.read(block)) != -1 ) {
                fos.write(block, 0, i);
            }
            fos.close();
            cis.close();
            fis.close();

            String plainTestAgain = readData(plainTextAgainAddress);
            return "\nSifre cozme islemi tamamlandi. Cozulen metin:\n" + plainTestAgain;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "\nSifre cozme islemi sirasinda hata olustu.";
        }
    }
}
