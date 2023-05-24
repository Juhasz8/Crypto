package com.example.poof_ui.Blockchain_Side;

public class FullNodeBlock
{
    public Block block;

    public String luckyMinerPublicKey;

    public FullNodeBlock (Block block, String luckyMinerPublicKey)
    {
        this.block = block;
        this.luckyMinerPublicKey = luckyMinerPublicKey;
    }

}
