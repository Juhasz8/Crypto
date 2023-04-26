import java.util.Random;

public class Miner extends User
{
    private int miningPower;
    private Random rand;

    public Miner(int minPower, int maxPower)
    {
        super();
        rand = new Random();
        miningPower = minPower + rand.nextInt(maxPower-minPower); // -> generate a random number between minPower and maxPower
        //Update();
    }

    private void Update()
    {
        TryToMine();
        try
        {
            Thread.sleep(GetSleepingTime(miningPower));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        Update();
    }

    private void TryToMine()
    {
        //we get the data from the datamanager

        //we add a transaction to it where we get some amount of coin

        //we put that data + an offset into the hash function, and check the result

        //if it starts with X zeros, we validate the block, otherwise we increment the offsets
    }

    //private void ValidateBlock

    //here we broadcast the new block to everyone execute all transactions stated on the ledger

    private long GetSleepingTime(int x)
    {
        return (long)-0.018*x + (long)1.9; //sleeping time should be 1 sec if power is 10 and 0.1 sec if power 100
    }
}
