package com.example.poof_ui.Blockchain_Side;

public class TransactionMatch
{
    //public User fromUser;
    //public User toUser;
    public String fromPublicKey;
    public String toPublicKey;
    public float amount;

    //the byte message of this match
    public byte[] originalMessage;
    public byte[] signedMessage;
    public User seller;

    public TransactionType type;

    public TransactionMatch(TransactionType type, String fromPublicKey, String toPublicKey, float amount)
    {
        //this.fromUser = fromUser;
        //this.toUser = toUser;
        this.type = type;
        this.fromPublicKey = fromPublicKey;
        this.toPublicKey = toPublicKey;
        this.amount = amount;
    }

    public void SignTransaction(User seller)
    {
        this.seller = seller;
        originalMessage = Cryptography.ConvertFromTransactionToByte(this);
        signedMessage = seller.Sign(originalMessage);
    }

}
