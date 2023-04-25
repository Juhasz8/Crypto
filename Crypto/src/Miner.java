import java.util.Random;

public class Miner
{
    private int miningPower;
    private Random rand;

    public Miner(int minPower, int maxPower)
    {
        rand = new Random();
        miningPower = minPower + rand.nextInt(maxPower-minPower);
        Update();
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

    }

    private long GetSleepingTime(int x)
    {
        return (long)-0.018*x + (long)1.9;
    }
}
