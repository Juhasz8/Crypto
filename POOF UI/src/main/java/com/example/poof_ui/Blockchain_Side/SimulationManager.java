package com.example.poof_ui.Blockchain_Side;

import java.util.Random;

public class SimulationManager
{
    private static SimulationManager instance;
    //creates the User objects and the threads

    public float marketPrice = 0;

    private Random random = new Random();

    public static SimulationManager getInstance()
    {
        if(instance == null)
            instance = new SimulationManager();

        return instance;
    }

    private SimulationManager()
    {
        UpdateCycle();
    }

    private void UpdateCycle()
    {
        JoiningPeople();
        DetermineMarketPrice();

        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        UpdateCycle();
    }

    private void JoiningPeople()
    {
        //create the user objects and their threads respectively
    }

    private void DetermineMarketPrice()
    {
        float marketPriceChange = 0;

        //i need a reference to all the transaction requests that has happened since the last update cycle
        //based on their amounts, we change the value of marketprice

        marketPriceChange += random.nextFloat(20)-10;
        marketPrice += marketPriceChange;

    }

}
