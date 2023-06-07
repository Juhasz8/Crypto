package com.example.poof_ui.Blockchain_Side;

import com.example.poof_ui.CurrentEventManager;
import com.example.poof_ui.PoofController;

import java.util.Random;

public class SimulationManager implements Runnable
{
    private static SimulationManager instance;
    //creates the User objects and the threads
    private Miner miner;

    public CurrentEventManager eventManager;

    public float marketPrice = 0;

    private Random random = new Random();

    private boolean isSuspended = false;


    //These fields are for storing the last 5 cycle's buying and selling requests
    // (which happened in the last 5*2 = 10 seconds)
    //if we would need the correct order in which the amounts happened we will need a linked list
    private int currentListLength = 1;
    private int maxListLength = 5;

    //the requests that happened in the last update cycle
    public RequestLink requestLinkHead = new RequestLink();

    //the requests that happened in the 5th last update cycle
    private RequestLink requestLinkTail;

    //getters for the total values
    public int GetSellingRequestAmount()
    {
        int sum = 0;
        RequestLink current = requestLinkTail;
        while(current.next != null)
        {
            sum += current.cycleSellingRequestAmount;
            current = current.next;
        }
        return sum;
    }
    public int GetBuyingRequestAmount()
    {
        int sum = 0;
        RequestLink current = requestLinkTail;
        while(current.next != null)
        {
            sum += current.cycleBuyingRequestAmount;
            current = current.next;
        }
        return sum;
    }

    //called in the update loop
    private void UpdateRequestListening()
    {
        //the linked list reached its maximum capacity
        if(currentListLength == maxListLength)
        {
            //we remove the last element by setting the tail pointer to the right by one
            requestLinkTail = requestLinkTail.next;

            //adding new element, and setting the head point there
            RequestLink newHead = new RequestLink();
            requestLinkHead.next = newHead;
            requestLinkHead = newHead;
        }
        else
        {
            //if we only have the head, the tail will point there too
            if(currentListLength == 1)
                requestLinkTail = requestLinkHead;

            //adding new element and setting the head point there
            RequestLink newHead = new RequestLink();
            requestLinkHead.next = newHead;
            requestLinkHead = newHead;

            //we increment the length
            currentListLength++;
        }
    }
    //----------------


    public static SimulationManager getInstance() {
        if (instance == null)
            instance = new SimulationManager();

        return instance;
    }

    private SimulationManager()
    {
        //make the very first miner join the network
        //Miner miner1 = new Miner(10, 11, MinerType.THAT_ONE_GUY, GetMinerSleepingTime(MinerType.THAT_ONE_GUY));
        //miner1.start();

        Miner miner2 = new Miner(MinerType.HUGE_CORP, GetMinerSleepingTime(MinerType.HUGE_CORP));
        miner2.start();


        Miner miner3 = new Miner(MinerType.HUGE_CORP, GetMinerSleepingTime(MinerType.HUGE_CORP));
        miner3.start();
    }

    public void run()
    {
        synchronized (this)
        {
            while (true)
            {
                try
                {
                    while (isSuspended)
                        wait();

                    DetermineMarketPrice();
                    JoiningPeople();
                    UpdateRequestListening();

                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void JoiningPeople() {

        //there shouldn't be any people joining until the first block is mined
        // (after that the price will go up so this won't return)
        if(marketPrice == 0)
            return;

        //create the user objects and their threads respectively

        //decide how many miner will join
        //depends on the amount of miners currently on the market, and the value of the currency and some random factor


        //decide how many trader will join
        //depends on the amount of traders currently on the market, and the value of the currency and some random factor
        //1 + 1/(n+1)
        if(Network.getInstance().GetTraderAmount() == 0)
        {
            Trader myGuy = new Trader(TraderType.RISK_APPETITE);
            myGuy.start();
        }


        //we will decide the miner and trader type here, and not in the miner and trader constructor.
        //and the constructor asks for a miner or trader type parameter

        //join the traders and miners to the network


    }

    private void DetermineMarketPrice()
    {
        float marketPriceChange = 0;

        //before the first block was mined, the market price stays at 0
        if(Network.getInstance().fullNode.GetLongestChainSize() == 0)
            return;

        //I need a reference to all the transaction requests that has happened since the last update cycle
        //based on their amounts, we change the value of market price

        //if(Network.getInstance().networkUsers.size() > 1)
        System.out.println("...................");
        System.out.println("all selling requests last 5 cycles: " + GetSellingRequestAmount());
        System.out.println("all buying requests last 5 cycles: " + GetBuyingRequestAmount());
        System.out.println("...................");


        if(marketPrice == 0)
        {
            marketPriceChange += random.nextFloat(10);
        }
        else
            marketPriceChange += random.nextFloat(20) - 10;
        marketPrice += marketPriceChange;

        if(marketPrice < 0)
            marketPrice = 0;

        //System.out.println("new market price: " + marketPrice);

        // Update Market Price Label
        PoofController.getInstance().updateMarketPriceLabel(String.valueOf(marketPrice));
        // Update Price Graph
        PoofController.getInstance().updatePriceGraph(String.valueOf(marketPrice));
        // Update Price Percentage
        PoofController.getInstance().updateMarketPercentageLabel(String.valueOf(marketPrice));
    }

    public void SuspendSimulation()
    {
        //suspend all the user threads
        for(User user : Network.getInstance().networkUsers.values())
        {
            user.SuspendThread();
        }
        //suspend this thread
        isSuspended = true;
    }

    public void ResumeSimulation()
    {
        //resume all the user threads
        for(User user : Network.getInstance().networkUsers.values())
        {
            user.ResumeThread();
        }
        //resume this thread
        isSuspended = false;
    }

    private long GetMinerSleepingTime(MinerType minerType)
    {
        if(minerType == MinerType.THAT_ONE_GUY)
            return random.nextLong(100)+50;
        else if(minerType == MinerType.THESE_GUYS)
            return random.nextLong(100)+50;
        else if(minerType == MinerType.GROUP)
            return random.nextLong(100)+50;
        else if(minerType == MinerType.SMALL_CORP)
            return random.nextLong(100)+50;
        else if(minerType == MinerType.HUGE_CORP)
            //return random.nextLong(10)+10;
            return 30;

        System.out.println("Something went wrong! Non-existing MinerType!");
        return random.nextLong(100)+50;
    }

}
