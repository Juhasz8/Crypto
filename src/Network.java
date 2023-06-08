import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.PublicKey;
import java.util.*;


//should also have a type REWARD, which is used by the miners
enum TransactionType { BUY, SELL, REWARD }

//class used to listen for broadcasted transaction requests, and connect them with eachother
//after these transaction requests become connected, it broadcasts it towards every miner.
//then the miners will create the block from them, and try to mine it
public class Network
{

    private static Network instance;
    private ArrayList<SellingRequest> sellingRequests = new ArrayList<>();
    private ArrayList<BuyingRequest> buyingRequests = new ArrayList<>();

    private ArrayList<TransactionMatch> matches = new ArrayList<>();

    private float minerReward = 5;

    private ArrayList<Miner> miners = new ArrayList<>();
    private ArrayList<Trader> traders = new ArrayList<>();

    // an array of random names
    private String[] names = new String[] { "Alfred", "Bob", "Steve" };
    private int[] amountOfUsedNames = new int[3];

    private Random rand = new Random();

    private Network()
    {
        for(int i = 0; i < amountOfUsedNames.length; i++)
        {
            amountOfUsedNames[i] = 0;
        }
    }

    public String GetNewRandomUserName()
    {
        int index = rand.nextInt(names.length);
        String name = names[index];
        amountOfUsedNames[index]++;

        if(amountOfUsedNames[index] > 1)
            name += " " + amountOfUsedNames[index];

        return name;
    }

    private void TryToMatch()
    {

        if(sellingRequests.size() == 0 || buyingRequests.size() == 0)
            return;

        Collections.sort(sellingRequests);

        //for(int i = 0; i < sellingRequests.size(); i++)
        while (sellingRequests.size() > 0 && buyingRequests.size() > 0)
        //for(int i = sellingRequests.size()-1; i >= 0; i--)
        {
            //System.out.println("REQ: " + request.user + request.tradeAmount + " " + request.transactionFee );

            User seller = sellingRequests.get(0).user;
            Trader trader = buyingRequests.get(0).trader;

            TransactionMatch match = GetMatch(seller, trader);

            byte[] originalMessage = Convert(match);
            //convert the match into an array of bits, and pass these bytes and the publicKey of the buyer to the seller to sign
            byte[] signedMessage = seller.Sign(originalMessage);

            //ask for the verification of the trader for the signed transaction
            if(trader.VerifySignedMessage(originalMessage, signedMessage, seller.publicKey))
            {
                //pass the message to the miners

                System.out.println("VERIFIED MATCH: " + seller.name + " sends " + match.amount + " puffs to "+ trader.name);
                matches.add(match);
            }
            else
            {
                System.out.println("The Buyer didnt verify the Transaction! ");
            }

            AdjustRequests();
        }
    }

    private TransactionMatch GetMatch(User seller, Trader trader)
    {
        //the seller sells more
        if(sellingRequests.get(0).tradeAmount > buyingRequests.get(0).tradeAmount)
            return new TransactionMatch(seller.publicKeyString, trader.publicKeyString, buyingRequests.get(0).tradeAmount);
        else
            return new TransactionMatch(seller.publicKeyString, trader.publicKeyString, sellingRequests.get(0).tradeAmount);
    }

    private void AdjustRequests()
    {
        if(sellingRequests.get(0).tradeAmount > buyingRequests.get(0).tradeAmount)
        {
            sellingRequests.get(0).tradeAmount -= buyingRequests.get(0).tradeAmount;
            buyingRequests.remove(0);
        }
        else
        {
            buyingRequests.get(0).tradeAmount -= sellingRequests.get(0).tradeAmount;
            if(buyingRequests.get(0).tradeAmount == 0)
                buyingRequests.remove(0);

            sellingRequests.remove(0);
        }
    }

    private byte[] Convert(TransactionMatch match)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {
            dos.write(match.fromPublicKey.getBytes());
            dos.write(match.toPublicKey.getBytes());
            dos.writeFloat(match.amount);

            dos.flush();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return bos.toByteArray();
    }

    public static Network getInstance()
    {
        if(instance == null)
            instance = new Network();

        return instance;
    }

    public void ProcessBuyingRequest(BuyingRequest request)
    {
        buyingRequests.add(request);
        TryToMatch();
    }

    public void ProcessSellingRequest(SellingRequest request)
    {
        sellingRequests.add(request);
        TryToMatch();
    }

    public float GetMinerRewardAmount()
    {
        return minerReward;
    }

    public void JoinMinerToTheNetwork(Miner miner)
    {
        miners.add(miner);
    }

    public void JoinTraderToTheNetwork(Trader trader)
    {
        traders.add(trader);
    }
}