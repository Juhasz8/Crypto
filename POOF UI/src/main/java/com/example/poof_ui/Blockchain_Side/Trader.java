package com.example.poof_ui.Blockchain_Side;

import com.example.poof_ui.PoofController;

import java.util.Random;

enum TraderType { RISK_APPETITE, TREND_FOLLOWER, CONTRARIAN_APPROACH, EVENT_FOLLOWER, PSYCHOPATH }

public class Trader extends User
{

    public TraderType type;
    public Random random = new Random();

    public Trader(TraderType type)
    {
        super();
        this.type = type;
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

                    DecideWhetherToBuyOrSell();

                    Thread.sleep(2000);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void DecideWhetherToBuyOrSell()
    {
        Exchange exchange = new Exchange();
        //difference represents how likely u will sell or buy
        //Double difference = 0.0;
        //percent represent how many percent of you current poof or euro you want to exchange
        //Double exchangePercent;

        if(type == TraderType.RISK_APPETITE) // high risk high reward guy
        {
            //whatever he decides to do, he will deal with a lot of his current money or poof
            exchange.percent = random.nextDouble(.4)+0.5; //will deal with 35-75% of his current currency

            //sells fewer times

            //buys if the price is very low, or the amount of people buying is very few

        }
        else if (type == TraderType.TREND_FOLLOWER)  //makes decisions based on what the majority is doing
        {
            //sells if a lot of people sell

            //buys if a lot of people buy
        }
        else if (type == TraderType.CONTRARIAN_APPROACH)  //makes decisions based on what the minority is doing
        {
            //sells if a lot of people buy

            //buys if a lot of people sell
        }
        else if (type == TraderType.EVENT_FOLLOWER)  //makes decisions based on the events
        {
            //sell and buys based on the positive or negative impact of an event



        }
        else if (type == TraderType.PSYCHOPATH)  //makes decisions completely randomly?
        {
            //completely random behaviour
        }


        //every type's decision is influenced by the current event and the trend
        CalculateNormalInfluences(exchange);


        //there is a 20% chance for the trader to make a bigger decision
        if(random.nextInt(5) == 0)
        {
            exchange.difference *= 1.5;

            if(type == TraderType.PSYCHOPATH)
            {
                //MakeTheTradeRequest(difference, exchangePercent);
                return;
            }
        }

        //everyone has a 5% chance of deciding based on complete randomness, like a psychopath
        if(random.nextInt(20) == 0)
        {

        }

        MakeTheTradeRequest(exchange.difference, exchange.percent);
    }

    private void MakeTheTradeRequest(double difference, double exchangePercent)
    {
        //double feePercent = random.nextDouble(5)+2;
        double feePercent = random.nextDouble(0.05)+0.02; //feePercent is a random value between 2 and 7

        //calculate the actual amount based on difference and exchangePercent


        if(difference > 0)
            RequestToBuy(difference);
        else
            RequestToSell(difference * (1-feePercent), difference * feePercent);
    }

    public void RequestToBuy(double amount)
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
