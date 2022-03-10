package com.sunmi.printerhelper.nextgen_sharique.voly_url;
import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by macdev on 15/02/16.
 */



    public class DecryptionLogic {

        private byte[] key;
        private byte[] encryptedRequest;
        private final String DEFAULT_ALGORITHM = "DESede";
        private final String DEFAULT_PROVIDER = "SunJCE";


        public static byte[] hexToBytes(String str) {
            if (str == null) {
                return null;
            } else if (str.length() < 2) {
                return null;
            } else {
                int len = str.length() / 2;
                byte[] buffer = new byte[len];
                for (int i = 0; i < len; i++) {
                    buffer[i] = (byte) Integer.parseInt(
                            str.substring(i*  2, i*  2 + 2), 16);
                }
                return buffer;
            }

        }

        public String getDecryptValue(String _key, String _encryptedRequest) {

            String decryptValue = "";
            key = hexToBytes(_key+_key+_key);
            encryptedRequest = hexToBytes(_encryptedRequest);
            byte[] decryptText = new byte[0];
            try {
                decryptText = decrypt(encryptedRequest);
                decryptValue = bytesToHex(decryptText);

            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            finally {
                return decryptValue;
            }


        }

        private SecretKey buildKey()
                throws Exception {
            SecretKeySpec keyfactory = new SecretKeySpec(key, DEFAULT_ALGORITHM);
            return keyfactory;
        }

        public byte[] decrypt(byte[] cyphertext)
                throws Exception {
            try {
                return decrypt(cyphertext, ((buildKey())), DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
            }
            catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }

        private byte[] decrypt(byte[] cyphertext, Key key, String algorithm, String provider)
                throws Exception {
            String result = "";
            byte utf8[] = null;
            if (cyphertext != null) {
                Cipher dcipher = Cipher.getInstance(algorithm);
                dcipher.init(2, key);
                utf8 = dcipher.doFinal(cyphertext);
                result = bytesToHex(utf8);
            }
            return utf8;
        }

        public static String bytesToHex(byte[] data) {
            if (data == null) {
                return null;
            } else {
                int len = data.length;
                String str = "";
                for (int i = 0; i < len; i++) {
                    if ((data[i] & 0xFF) < 16) str = str + "0"
                            + Integer.toHexString(data[i] & 0xFF);
                    else str = str
                            + Integer.toHexString(data[i] & 0xFF);
                }
                return str.toUpperCase();
            }
        }


//        public static void main(String[] args) {
//            try {
//                String key = "1234567890123456";
//                String theKey = bytesToHex(hexToBytes(key));
//
//                String serialNo = "150747927117";
//                String serialNoAscii = bytesToHex(serialNo.getBytes());
//               // String diversion = getDiversionKey(theKey, serialNoAscii);
//                String diversion = "5ACE925996954B8D5ACE925996954B8D5ACE925996954B8D";
//
//                byte[] masterKey = hexToBytes(diversion);
//                String requestMsg = "1357997531";
//                TD logic = new TD();
//                //byte[] encryptedText = Triple_DES_Test.encrypt(hexToBytes(requestMsg));
//System.out.println(logic.getDecryptValue(diversion, "6A78C91F71328E8336480CC67A3609CF"));
//                //System.out.println("ECrypt.encrypt()" + bytesToHex(encryptedText));
//
//                //System.out.println("MasterKey : " + bytesToHex(masterKey));
//                //System.out.println("encryptedText : " + bytesToHex(encryptedText));
//
//
//
//
//// byte[] decryptText = Triple_DES_Test.decrypt(encryptedText);
////
// //System.out.println("ECrypt.decrypt()" + bytesToHex(decryptText));
//
//                System.out.println(logic.getDecryptValue(bytesToHex(masterKey), bytesToHex(hexToBytes("6A78C91F71328E8336480CC67A3609CF"))));
//
//            } catch (Exception e) {
//                e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
//            }
//
//        }

        private static String getDiversionKey(String theKey, String theMsg) {
            String diversionKey = "";
            try {
                KeySpec ks = new DESKeySpec(hexToBytes(theKey));
                SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
                SecretKey ky = kf.generateSecret(ks);
                Cipher cf = Cipher.getInstance("DES/CBC/PKCS5Padding"); // DES-CBC with padding according to DES specs
                IvParameterSpec ivSpec = new IvParameterSpec(hexToBytes("0000000000000000")); // IV is all zeros
                cf.init(Cipher.ENCRYPT_MODE, ky, ivSpec);
                byte[] theCph = cf.doFinal(hexToBytes(theMsg));
                diversionKey = bytesToHex(theCph);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return diversionKey;

        }


    }



