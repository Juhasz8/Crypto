import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//should also have a type REWARD, which is used by the miners
enum TransactionType { BUY, SELL, REWARD }

//class used to listen for broadcasted transaction requests, and connect them with eachother
//after these transaction requests become connected, it broadcasts it towards every miner.
//then the miners will create the block from them, and try to mine it
public class Network
{

    public static Network instance;
    private ArrayList<TransactionRequest> sellingRequests = new ArrayList<>();
    private ArrayList<TransactionRequest> buyingRequests = new ArrayList<>();

    private ArrayList<TransactionMatch> matches = new ArrayList<>();

    private float minerReward = 5;

    private ArrayList<User> users = new ArrayList<>();

    private Network()
    {
        
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

            TransactionMatch match;
            if(sellingRequests.get(0).tradeAmount > buyingRequests.get(0).tradeAmount)
            {
                match = new TransactionMatch(sellingRequests.get(0).user, buyingRequests.get(0).user, buyingRequests.get(0).tradeAmount);
                sellingRequests.get(0).tradeAmount -= buyingRequests.get(0).tradeAmount;
                buyingRequests.remove(0);
            }
            else if(sellingRequests.get(0).tradeAmount < buyingRequests.get(0).tradeAmount)
            {
                match = new TransactionMatch(sellingRequests.get(0).user, buyingRequests.get(0).user, sellingRequests.get(0).tradeAmount);
                buyingRequests.get(0).tradeAmount -= sellingRequests.get(0).tradeAmount;
                sellingRequests.remove(0);
            }
            else
            {
                match = new TransactionMatch(sellingRequests.get(0).user, buyingRequests.get(0).user, sellingRequests.get(0).tradeAmount);
                buyingRequests.remove(0);
                sellingRequests.remove(0);
            }
            System.out.println("MATCH: from: " + match.fromUser.publicKeyString + " to: " + match.toUser.publicKeyString + " amount: " + match.amount);
            matches.add(match);
        }
    }

    public static Network getInstance()
    {
        if(instance == null)
            instance = new Network();

        return instance;
    }

    public void ProcessRequest(TransactionRequest request)
    {
        if(request.type == TransactionType.SELL)
            sellingRequests.add(request);
        else if (request.type == TransactionType.BUY)
            buyingRequests.add(request);

        TryToMatch();
    }

    public void ProcessSignedMessage(byte[] signedMessage, PublicKey sellerPublicKey)
    {

    }
    //we need a method for getting the final ledger

    public float GetMinerRewardAmount()
    {
        return minerReward;
    }

    public void JoinNetwork(User user)
    {
        users.add(user);
    }
}
