package com.oguzhanlarca;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("\nRSA Algoritmasi ile Sifreleme Ornegi");
        System.out.println("------------------------------------\n");
        RsaCrypt rsaObject = new RsaCrypt();

        // Anahtar degerler daha onceden olusturulmadiysa uretir
        if (!rsaObject.areKeysPresent())
            rsaObject.generateKey();

        // txt bulundugu klasore gider ve dosyayi okur
        String plainText = rsaObject.readData("CryptoFiles/plainText.txt");
        System.out.println("Sifreleme islemi basladi. Sifrelenecek Metin:\n" + plainText);

        // Dosyadan okunan mesaji sifreler ve ekrana yazdirir
        String result = rsaObject.encrypt("CryptoFiles/plainText.txt",
                "CryptoFiles/cipherText.txt");
        System.out.println(result);

        // Sifreli mesaji cozer ve ekrana yazar
        result = rsaObject.decrypt("CryptoFiles/cipherText.txt",
                "CryptoFiles/plainTextAgain.txt");
        System.out.println(result);
    }
}
