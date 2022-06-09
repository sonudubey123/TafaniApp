package com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.KeySpec;

public class Triple_DES

{

    public byte[] masterKey;
    private final String DEFAULT_ALGORITHM = "TripleDES";
    private final String DEFAULT_PROVIDER = "SunJCE";

  /*  static{
        try{

            Security.addProvider(newBouncyCastleProvider());
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }*/

    public byte[] encrypt(byte[] cleartext)
            throws Exception {
        try {
            return encrypt(cleartext, ((Key) (buildKey())), DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public byte[] encrypt(byte[] cleartext, String serialNo)
            throws Exception {
        try {
            String theKey = "1234567890123456";
            //theKey = bytesToHex(hexToBytes(theKey));
//            Integer serialLength = serialNo.length();
//            for (int i = 1; i <= 20 - serialLength; i++) {
//                serialNo = "0" + serialNo;
//
//            }
            //String serialNoAscii = bytesToHex(serialNo.getBytes());
            String serialNoAscii = serialNo;
            String diversionKey = getDiversionKey(theKey, serialNoAscii);
            //System.out.println("Diversion Key=" + diversionKey);
            StringBuffer sb = new StringBuffer(diversionKey);
            sb.append(diversionKey.substring(0, 16));
            sb.append(diversionKey.substring(0, 16));
            byte[] mKey = hexToBytes(sb.toString());
            return encrypt(cleartext, ((Key) (buildKey(mKey))), DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public byte[] encrypt(byte[] cleartext, Key key, String algorithm, String provider)
            throws Exception {
        byte enc[] = null;
        if (cleartext != null) {
            Cipher ecipher = Cipher.getInstance(algorithm, provider);
            ecipher.init(1, key);
            enc = ecipher.doFinal(cleartext);
        }
        return enc;
    }

//    public static String encrypt(String cleartext, Key key, String algorithm, String provider)
//            throws Exception {
//        String result = "";
//        if (cleartext != null && !"".equalsIgnoreCase(cleartext)) {
//            byte utf8[] = cleartext.getBytes("UTF8");
//            Cipher ecipher = Cipher.getInstance(algorithm, provider);
//            ecipher.init(1, key);
//            byte enc[] = ecipher.doFinal(utf8);
//            result = (new BASE64Encoder()).encode(enc);
//        }
//        return result;
//    }

    public byte[] decrypt(byte[] cyphertext)
            throws Exception {
        try {
            return decrypt(cyphertext, ((Key) (buildKey())), DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public byte[] decrypt(byte[] cyphertext, Key key, String algorithm, String provider)
            throws Exception {
        String result = "";
        byte utf8[] = null;
        if (cyphertext != null) {
            Cipher dcipher = Cipher.getInstance(algorithm, provider);
            dcipher.init(2, key);
            utf8 = dcipher.doFinal(cyphertext);
            result = new String(utf8, "UTF8");
        }
        return utf8;
    }

    private SecretKey buildKey()
            throws Exception {
        SecretKeySpec keyfactory = new SecretKeySpec(masterKey, DEFAULT_ALGORITHM);
        return keyfactory;
    }

    private SecretKey buildKey(byte[] mKey)
            throws Exception {
        SecretKeySpec keyfactory = new SecretKeySpec(mKey, DEFAULT_ALGORITHM);
        return keyfactory;
    }


    private String convertInASCII(String key) {
        StringBuffer sb = new StringBuffer();
        char keyArray[] = key.toCharArray();
        for (int i = 0; i < key.length(); i++) {
            int x = (int) keyArray[i];
            String x1 = Integer.toHexString(x);
            sb.append(x1);
        }
        return sb.toString();
    }


    private String pinPayKey(String key) {
        byte[] theKey = hexToBytes(key);
        return bytesToHex(theKey);
    }

    private String getDiversionKey(String theKey, String theMsg) {
        String diversionKey = "";
        try {
            //KeySpec ks = new DESKeySpec(hexToBytes(theKey));
            KeySpec ks = new DESKeySpec(theKey.getBytes());
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



    private String padRequestString(String requestMsg) {
        StringBuffer msg = new StringBuffer(requestMsg);

        int paddingVal = 7;//(8-(msg.length()%8));
        if (paddingVal > 0) {
            StringBuffer sb = new StringBuffer("0");
            sb.append("" + String.valueOf(paddingVal));
            for (int i = 0; i < paddingVal; i++) {
                msg.append(sb.toString());
            }
        }
        return msg.toString();

    }

    /*    public static void main(String[] args) {
        try {

            // Step First ****************  1.	Hashing the MPIN

            MD5 md5 = new MD5("96130123451234");
            String hashPin = md5.MD5Convertor();
            //System.out.println( "HashPin : " + hashPin );
           // Step two *****************  2.	Creating the Diversified Key
            String pinPay = pinPayKey("1122334455667788");
            //System.out.println( "pinpay Key : " + pinPay );
            String iccid = "12345678900987654321";
            String iccidASCII = bytesToHex( iccid.getBytes() );
            //System.out.println( "ICCID : " + iccidASCII);
            StringBuffer iccidPadding = new StringBuffer(iccidASCII );
            String paddingString ="04040404";
            iccidPadding.append( "ICCID (After Padding) : " + paddingString);
            String diversion = getDiversionKey(pinPay, iccidASCII );
            //System.out.println( "Diversion : " + diversion );

            // Step three  *****************  3.	Encrypting the entire SMS

            String requestText = convertInASCII("BILLPAY 9613012345 9613012345 1234567890 1000.00 ");

            StringBuffer requestMSG = new StringBuffer(requestText);
            requestMSG.append(hashPin);

            //System.out.println( "Hex encoded message : " + requestMSG) ;
            String padRequest = padRequestString( requestMSG.toString());

            //System.out.println( "Hex encoded message(After Padding) : " + padRequest ) ;

             masterKey = hexToBytes(diversion);
             byte[] encryptedText = Triple_DES.encrypt(hexToBytes(requestMSG.toString()));
             //System.out.println("ECrypt.encrypt()" + bytesToHex(encryptedText));
             byte[] decryptText = Triple_DES.decrypt(encryptedText);

            //System.out.println("ECrypt.decrypt()" + bytesToHex(decryptText));
            StringBuffer sb = new StringBuffer( iccidASCII );
            sb.append(bytesToHex( " ".getBytes()) );
            sb.append(bytesToHex(encryptedText) );

            //System.out.println( "Complete Message : " + sb.toString());





        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
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
                        str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }

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

    private String encryptedPin(String plainPin, String serialNo) {
        String result = "";
        byte[] encryptedText = null;
        Triple_DES tripleDes = new Triple_DES();

        int pinLength = plainPin.length();

        if (pinLength == 17) {
            plainPin = "B" + plainPin;
        } else {
            if (pinLength % 2 == 1) {
                plainPin = "A" + plainPin;
            }
        }

        try {
            //encryptedText = tripleDes.encrypt(tripleDes.hexToBytes(plainPin), serialNo);
            encryptedText = tripleDes.encrypt(tripleDes.hexToBytes(plainPin), serialNo);

        } catch (Exception e) {
            //error("BulkDwnProcessor...............................Pin encryption Error", e);
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (encryptedText != null) {
                result = tripleDes.bytesToHex(encryptedText);

            }
        }

        return (result);
    }

    private String encryptedPinTest(String plainPin, String serialNo) {
        String result = "";
        StringBuffer sb = new StringBuffer(plainPin);
        //info("[AMIT00] encryptedPin() plainPin......."+plainPin);
        byte[] encryptedText = null;
        Triple_DES tripleDes = new Triple_DES();
        int pinLength = sb.length();

        while (pinLength <= 30) {
            sb.append("A");
            pinLength++;
        }
        plainPin = sb.toString();

        try {
            //info("[AMIT] encryptedPin() plainPin......."+plainPin);
            encryptedText = tripleDes.encrypt(tripleDes.hexToBytes(plainPin), serialNo);
            //info("[AMIT] encryptedPin() encryptedText......."+encryptedText);

        } catch (Exception e) {
            e.printStackTrace();
            //info("[AMIT] encryptedPin() Exception......."+e);
           // error("BulkDwnProcessor...............................Pin encryption Error " + e.getMessage());
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            //info("[AMIT] encryptedPin() Finally encryptedText......."+encryptedText);
            if (encryptedText != null) {
                result = tripleDes.bytesToHex(encryptedText);
            }
        }

        return (result);
    }

    public String decryptedPinTest(String plainPin, String serialNo) {

        // 69DAE1518FFBA9982279E86462E2B7A2   // android
        // 50060726                          // 50060726
        // 69DAE1518FFBA9982279E86462E2B7A2 // web

        String result = "";
        StringBuffer sb = new StringBuffer(plainPin);
        //info("[AMIT00] encryptedPin() plainPin......."+plainPin);
        byte[] encryptedText = null;
        Triple_DES tripleDes = new Triple_DES();
        int pinLength = sb.length();
        while (pinLength <= 30) {
            sb.append("A");
            pinLength++;
        }
        plainPin = sb.toString();
        try {
            //info("[AMIT] encryptedPin() plainPin......."+plainPin);
            encryptedText = tripleDes.decryptTest(tripleDes.hexToBytes(plainPin), serialNo);

            System.out.println(encryptedText);
            System.out.println(encryptedText);

            //info("[AMIT] encryptedPin() encryptedText......."+encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
            //info("[AMIT] encryptedPin() Exception......."+e);
            // error("BulkDwnProcessor...............................Pin encryption Error " + e.getMessage());
            //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            //info("[AMIT] encryptedPin() Finally encryptedText......."+encryptedText);
            if (encryptedText != null) {
                result = tripleDes.bytesToHexTest(encryptedText);
            }
        }
        return (result.replace("AAAAAAAAAAAAAAAAA",""));
    }

    public static String bytesToHexTest(byte[] data) {
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

    public byte[] decryptTest(byte[] cleartext, String serialNo)
            throws Exception {
        try {
            String theKey = "1234567890123456";
            //theKey = bytesToHex(hexToBytes(theKey));
//            Integer serialLength = serialNo.length();
//            for (int i = 1; i <= 20 - serialLength; i++) {
//                serialNo = "0" + serialNo;
//
//            }
            //String serialNoAscii = bytesToHex(serialNo.getBytes());
            String serialNoAscii = serialNo;
            String diversionKey = getDiversionKey(theKey, serialNoAscii);
            //System.out.println("Diversion Key=" + diversionKey);
            StringBuffer sb = new StringBuffer(diversionKey);
            sb.append(diversionKey.substring(0, 16));
            sb.append(diversionKey.substring(0, 16));
            byte[] mKey = hexToBytes(sb.toString());
            return decrypt(cleartext, ((Key) (buildKey(mKey))), DEFAULT_ALGORITHM, DEFAULT_PROVIDER);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }


}
