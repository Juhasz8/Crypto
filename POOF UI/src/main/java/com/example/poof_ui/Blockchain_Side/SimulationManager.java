package com.example.poof_ui.Blockchain_Side;

import com.example.poof_ui.CurrentEventManager;
import com.example.poof_ui.PoofController;
import com.example.poof_ui.PoofInterface;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.Random;

public class SimulationManager implements Runnable
{
    private static SimulationManager instance;
    //creates the User objects and the threads

    public CurrentEventManager eventManager;

    public float marketPrice = 0;

    private Random random = new Random();

    private boolean isSuspended = false;

    public static SimulationManager getInstance() {
        if (instance == null)
            instance = new SimulationManager();

        return instance;
    }

    private SimulationManager()
    {
        //make the very first miner join the network
        Miner miner1 = new Miner(20, 21);
        miner1.start();
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

                    JoiningPeople();
                    DetermineMarketPrice();

                    Thread.sleep(5000);

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

        //decide how many trader will join

        //join the traders and networks


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

        if(marketPrice == 0)
        {
            marketPriceChange += random.nextFloat(10);
        }
        else
            marketPriceChange += random.nextFloat(20) - 10;
        marketPrice += marketPriceChange;

        if(marketPrice < 0)
            marketPrice = 0;

        System.out.println("new market price: " + marketPrice);

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


}
