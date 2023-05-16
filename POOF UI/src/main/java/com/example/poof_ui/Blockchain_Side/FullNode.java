package com.example.poof_ui.Blockchain_Side;

import java.util.ArrayList;


//acts as a trusted source of the blockchain
public class FullNode
{

    public Block block1 = new Block(null, null);

    public Block lastTrustedBlock;
    //this will be the blockchains and the trusted blockchain
    private ArrayList<ArrayList<Block>> blockChains = new ArrayList<>();

    private int lastTrustedBlockIndex = 0;

    private int blockTrustingLimit = 2;   //this means in this blockchain:  A(first block is already trusted) -> B -> C -> D   ----> B gets trusted after D is mined

    public FullNode()
    {

    }

    public String GetLastTrustedBlockHash()
    {
        if(lastTrustedBlock == null)
            return "-";
        else
            return lastTrustedBlock.hash;
    }

    public void NotifyNodeThatNewBlockWasMined(Block newBlock)
    {
        //in the case of the very first blockchain that was mined and added, we just add it and trust it immediately (cause its data is empty anyways)
        if(blockChains.size() == 0)
        {
            System.out.println("VERY FIRST BLOCK ADDED TO THE CHAIN");
            blockChains.add(new ArrayList<>(){});
            blockChains.get(0).add(newBlock);
            TrustABlock(newBlock);
            DebugPrintingOfBlockchains();
            return;
        }

        boolean conflictHappened = false;
        //check whether there is a conflict
        ArrayList<Block> longestChain = GetLongestChain();
        for(int i = lastTrustedBlockIndex; i < longestChain.size(); i++)
        {
            if(newBlock.previousHash == longestChain.get(i).previousHash)
            {
                //Conflict at block number I!!

                //branch apart from the current longestChain
                ArrayList<Block> branch = new ArrayList<>();
                for (int j=0; j < i; j++)
                {
                    branch.add(longestChain.get(j));
                }
                branch.add(newBlock);
                blockChains.add(branch);

                conflictHappened = true;
            }
        }

        //if there were no conflicts, we just trust an old block in the past and add the new block to the chain
        if(!conflictHappened)
        {
            //and add the new block to the currect blockchain
            for (int i = 0; i < blockChains.size(); i++)
            {
                if(blockChains.get(i).get(blockChains.get(i).size()-1).hash == newBlock.previousHash)
                {
                    //System.out.println("added a new block on an existing chain! ");
                    blockChains.get(i).add(newBlock);
                    break;
                }
                else if(i == blockChains.size()-1)
                    System.out.println("SOMETHING WRONG WITH ADDING NEW BLOCK! ");
            }

            longestChain = GetLongestChain();
            //trust a block in the past
            if(longestChain.size() >= 4)
            {
                lastTrustedBlockIndex++;
                Block blockToBeTrusted = longestChain.get(lastTrustedBlockIndex);
                TrustABlock(longestChain.get(lastTrustedBlockIndex));

                //remove all the branch chains, which has a size less or equal to the longest one - difficulty
                for (int i = blockChains.size()-1; i >= 0; i--)
                {
                    //the branch chain is too short, terminate this branch!
                    if(blockChains.get(i).size() <= longestChain.size() - blockTrustingLimit)
                    {
                        blockChains.remove(i);
                    }
                }
            }
        }

        DebugPrintingOfBlockchains();
    }

    private void DebugPrintingOfBlockchains()
    {
        System.out.println("Blockchains: ");
        for (int i = 0; i < blockChains.size(); i++)
        {
            if(i != 0)
                System.out.println();
            for (int j = 0; j < blockChains.get(i).size(); j++)
            {
                if(blockChains.get(i).get(j).isTrusted)
                    System.out.print(" -> " + j + "(T)");
                else
                    System.out.print(" -> " + j + "(?)");
            }
        }
        System.out.println();
        System.out.println("--------------------");
    }

    private void TrustABlock(Block block)
    {
        lastTrustedBlock = block;
        block.isTrusted = true;

        //we basically do all the transactions on this block ledger
        //so everyone gets his money and poffcoin, and the miner gets the rewards and fee aswell
        //so here, you just check for the transaction YOU are involved in, the dont give a shit about the others.!!!

        //we have to think about it tho, how we prevent someone from trying to spend more poffcoin than they have

        //ByteArrayInputStream bos = new ByteArrayInputStream(block.GetData());
        //DataInputStream dos = new DataInputStream(bos);
        for(int i = 0; i < block.dataTree.transactions.size(); i++)
        {
            Transaction transaction = block.dataTree.transactions.get(i);

            if(transaction.type == TransactionType.NORMAL)
            {
                User fromUser = Network.getInstance().networkUsers.get(transaction.fromPublicKey);
                fromUser.IncreaseWallet(-transaction.amount);
            }

            User toUser = Network.getInstance().networkUsers.get(transaction.toPublicKey);
            toUser.IncreaseWallet(transaction.amount);
        }
    }

    public ArrayList<Block> GetLongestChain()
    {
        int longestSoFar = 1;
        ArrayList<Block> longestChainSoFar = blockChains.get(0);
        for (int i = 0; i < blockChains.size(); i++)
        {
            if(blockChains.get(i).size() > longestSoFar)
            {
                longestSoFar = blockChains.get(i).size();
                longestChainSoFar = blockChains.get(i);
            }
        }
        return longestChainSoFar;
    }



}
