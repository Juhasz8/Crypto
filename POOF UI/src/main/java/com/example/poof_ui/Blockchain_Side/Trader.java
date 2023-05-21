package com.example.poof_ui.Blockchain_Side;

import com.example.poof_ui.PoofController;

import java.security.PublicKey;
import java.security.Signature;

enum TraderType { RISK_APPETITE, TREND_FOLLOWER, CONTRARIAN_APPROACH, EVENT_FOLLOWER, PSYCHOPATH }

public class Trader extends User
{

    public Trader()
    {
        super();
        Network.getInstance().JoinTraderToTheNetwork(this);
        PoofController.getInstance().AddTraderGUI();
    }

    public void run()
    {
        synchronized (this)
        {
            while (true)
            {
                try
                {
                    if (isSuspended)
                        wait();

                    DecideWhetherToBuy();

                    Thread.sleep(2000);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void DecideWhetherToBuy()
    {

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
