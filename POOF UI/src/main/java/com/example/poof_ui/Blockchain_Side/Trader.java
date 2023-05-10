package com.example.poof_ui.Blockchain_Side;

import java.security.PublicKey;
import java.security.Signature;

public class Trader extends User
{

    public Trader()
    {
        super();
        Network.getInstance().JoinTraderToTheNetwork(this);
    }

    public void RequestToBuy(float amount)
    {
        //TransactionRequest request = new TransactionRequest(this, publicKeyString, TransactionType.BUY, amount, 0);
        BuyingRequest request = new BuyingRequest(this, amount);
        try
        {
            Network.getInstance().ProcessBuyingRequest(request);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public boolean VerifySignedMessage(byte[] originalMessage, byte[] signedMessage, PublicKey sellerPublicKey)
    {
        try
        {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(sellerPublicKey);
            signature.update(originalMessage);

            boolean isCorrect = signature.verify(signedMessage);

            return isCorrect;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return false;
    }

}
