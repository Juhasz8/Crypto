package com.example.poof_ui.Blockchain_Side;

import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class User
{

    public String name;
    //these three could be in its own wallet class
    public String publicKeyString;
    private String privateKeyString;

    public PublicKey publicKey;
    private PrivateKey privateKey;

    //the amount of Puff you own
    private double wallet = 0;


    public User()
    {
        try
        {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

            //Initializing the KeyPairGenerator
            keyPairGen.initialize(512);

            //Generating the pair of keys
            KeyPair pair = keyPairGen.generateKeyPair();

            //Getting the private key from the key pair
            privateKey = pair.getPrivate();

            //Getting the public key from the key pair
            publicKey = pair.getPublic();

            Base64.Encoder encoder = Base64.getEncoder();

            //System.out.println("Keys generated -> public: " + encoder.encodeToString(publicKey.getEncoded()) + " size: "+ encoder.encodeToString(publicKey.getEncoded()).length() + " private: " + encoder.encodeToString(privateKey.getEncoded()));
            publicKeyString = encoder.encodeToString(publicKey.getEncoded());
            privateKeyString = encoder.encodeToString(privateKey.getEncoded());

        }
        catch (Exception e)
        {
            System.out.println("Keys couldn't be created: " + e);
        }


        name = Network.getInstance().GetNewRandomUserName();
    }

    //this is called whenever the transaction is verified

    public void IncreaseWallet(double amount)
    {
        if(amount > 0)
            System.out.println(name + ": My wallet got increased (+" + amount +")");
        else
            System.out.println(name + ": My wallet got decreased (" + amount +")");
        wallet += amount;
    }

    public double GetWallet()
    {
        return wallet;
    }

    public void RequestToSell(float amount, float fee)
    {
        if(wallet < amount)
            return;

        //TransactionRequest request = new TransactionRequest(this, publicKeyString, TransactionType.SELL, amount, fee);
        SellingRequest request = new SellingRequest(this, amount, fee);
        try
        {
            Network.getInstance().ProcessSellingRequest(request);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public byte[] Sign(byte[] message)
    {
        try
        {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            signature.update(message);

            return signature.sign();
        }
        catch (Exception e)
        {
            System.out.println("Signing failed: " + e);
        }

        System.out.println("Seller didn't sign the Transaction! ");
        return null;
    }



}
