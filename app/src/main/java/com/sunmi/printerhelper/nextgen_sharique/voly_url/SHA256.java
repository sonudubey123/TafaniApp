package com.sunmi.printerhelper.nextgen_sharique.voly_url;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {


    public static String  key_sha256(String mobileNumber_transactiontype) {

        byte[] hash = null;
        String hashCode = null;// w  ww  .  j  a va 2 s.c  o m
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(mobileNumber_transactiontype.getBytes());
        } catch (NoSuchAlgorithmException e) {

            System.out.println(e);
        }

        if (hash != null) {
            StringBuilder hashBuilder = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(hash[i]);
                if (hex.length() == 1) {
                    hashBuilder.append("0");
                    hashBuilder.append(hex.charAt(hex.length() - 1));
                } else {
                    hashBuilder.append(hex.substring(hex.length() - 2));
                }
            }
            hashCode = hashBuilder.toString();


        }
        return hashCode;
    }
    }