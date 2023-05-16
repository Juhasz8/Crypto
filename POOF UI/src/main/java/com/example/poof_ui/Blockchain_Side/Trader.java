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

}
