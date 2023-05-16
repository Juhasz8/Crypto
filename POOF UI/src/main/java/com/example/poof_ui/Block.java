package com.example.poof_ui;

import java.util.Date;
import java.io.ByteArrayOutputStream;

public class Block {

    public String hash;
    public String previousHash;
    public long timeStamp;

    private byte[] blockData;
    private MerkleTree dataTree;

    public Block(byte[] blockData, String previousHash)
    {
        this.blockData = blockData;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        dataTree = new MerkleTree();
    }

    public void BlockMined(String hash)
    {
        this.hash = hash;
        //notify some other whatever
    }

    /*
    public void AddData(byte[] dataToAdd)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try
        {
            if(blockData != null)
                outputStream.write(blockData);
            outputStream.write(dataToAdd);
            byte[] result = outputStream.toByteArray();
            blockData = result;
        }
        catch (Exception e)
        {
            System.out.println("ADDING DATA TO LEDGER ERROR: " + e);
        }
    }
*/
    public void AddData(TransactionMatch transaction)
    {
        dataTree.AddTransaction(transaction);
    }


    public byte[] GetData()
    {
        return blockData;
    }

    public boolean IsEmpty()
    {
        return blockData == null;
    }

}