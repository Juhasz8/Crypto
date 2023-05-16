package com.example.poof_ui;

import java.util.Random;
import java.security.Signature;
import java.util.ArrayList;

public class Miner extends User
{
    private int miningPower;
    private Random rand;
    int numberOfTransactions = 0;
    private ArrayList<TransactionMatch> waitingTrans = new ArrayList<>();

    //mining
    private Block myblock;
    private int nonce;

    //this is the previous trusted hash, which means, if the last block was mined by me, its the hash of that block.
    //if it was mined by someone else, and that block matches up with my block, it becomes that blocks hash.
    //if it was miney by someone else, but that block doesnt match up, the previoustrustedhash doesnt change
    private String previousTrustedHash;

    public Miner(int minPower, int maxPower)
    {
        super();
        rand = new Random();
        miningPower = minPower + rand.nextInt(maxPower-minPower); // -> generate a random number between minPower and maxPower
        //Update();
        Network.getInstance().JoinMinerToTheNetwork(this);

        myblock = new Block(null, lastTrustedBlock.hash);
        previousTrustedHash = lastTrustedBlock.hash;
        //myblock.AddData(Cryptography.ConvertFromTransactionToByte(new TransactionMatch(TransactionType.REWARD, null, publicKeyString, Network.getInstance().GetMinerReward())));
        myblock.AddData(new TransactionMatch(TransactionType.REWARD, null, publicKeyString, Network.getInstance().GetMinerReward()));

        Update();
    }

    private void Update()
    {
        TryToMine();
        try
        {
            Thread.sleep(GetSleepingTime(miningPower)*10);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        Update();
    }

    private void TryToMine()
    {
        String target = new String(new char[Network.getInstance().GetDifficulty()]).replace("\0", "0");

        String hash = CalculateHash();

        //System.out.println("Tried to mine, result: " + hash);

        if(hash.substring(0, Network.getInstance().GetDifficulty()).equals(target))
        {
            //New Block mined succesfully
            IMinedABlockSuccessfully(hash);
        }
        else
        {
            nonce++;
        }
    }

    private String CalculateHash()
    {
        return Cryptography.sha256(Long.toString(myblock.timeStamp) + Integer.toString(nonce) + myblock.GetData());
    }

    //private void ValidateBlock

    //here we broadcast the new block to everyone execute all transactions stated on the ledger
    public boolean VerifySignedMessage(TransactionMatch signedTransaction)
    {
        //byte[] originalMessage, byte[] signedMessage, PublicKey sellerPublicKey
        try
        {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(signedTransaction.seller.publicKey);
            signature.update(signedTransaction.originalMessage);

            boolean isCorrect = signature.verify(signedTransaction.signedMessage);

            return isCorrect;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return false;
    }

    private long GetSleepingTime(int x)
    {
        return (long)-0.018*x + (long)1.9; //sleeping time should be 1 sec if power is 10 and 0.1 sec if power 100
    }

    private void IMinedABlockSuccessfully(String correctHash)
    {
        System.out.println("I mined a block successfully!! " + name);
        myblock.BlockMined(correctHash);
        previousTrustedHash = correctHash;

        //resetting the nonce
        nonce = 0;

        //we have to notify everyone on the network that we are the lottery winners
        Network.getInstance().NewBlockWasMined(myblock);

        //flushing the data
        myblock = new Block(null, previousTrustedHash);

        //adding the waiting transactions to the ledger
        for(int i = waitingTrans.size()-1; i >= 0; i--)
        {
            if(numberOfTransactions < Network.getInstance().GetMaxLedgerCount())
            {
                ProcessLedger(waitingTrans.get(i));
                waitingTrans.remove(i);
            }
            else
                break;
        }

        //everyone else will add this block on the blockchain, while keeping in mind that it might cause conflicts


        //A -> B -> C -> D -> E -> F

        //A -> B -> C  -> D*

        //the way we will deal with conflicts, is the following:
        //we will only compute the transactions, whenever it becomes a trusted block.
        //otherwise we will just wait until new blocks are mined


        //if a miner who is unlucky and didnt mine the block, gets notified of a block that was mined
        //he will check if that block is the same that he has. If they match up, he will discard his current block
        //because from his point of view, in his belief, that block which was just mined is going to become a trusted block later.
        //otherwise, he will keep working on his current block, because the block which was just mined was either a sneaky block, or this miner is working on a sneaky block himself
    }


    public void SomebodyElseMinedABlock(Block newBlock)
    {
        //the previoushash will be updated according to the relation between my current block i am working on and this new block that was mined by someone else
    }

    public void ProcessLedger(TransactionMatch signedTransaction)
    {
        //byte[] originalMessage, byte[] signedMessage, PublicKey sellerPublicKey
        if (numberOfTransactions < Network.getInstance().GetMaxLedgerCount())
        {
            if (VerifySignedMessage(signedTransaction))
            {
                myblock.AddData(signedTransaction);
                numberOfTransactions++;
            }
            else
            {
                System.out.println("Fucking unverified transaction");
            }
        }
        else
        {
            waitingTrans.add(signedTransaction);
        }
    }

}
