package com.example.poof_ui.Blockchain_Side;

public class Transaction
{
    //public User fromUser;
    //public User toUser;
    public String fromPublicKey;
    public String toPublicKey;
    public double expectedAmount;
    public double actualAmount;

    //the byte message of this match
    public byte[] originalMessage;
    public byte[] signedMessage;
    public User seller;

    public TransactionType type;

    public Transaction(TransactionType type, String fromPublicKey, String toPublicKey, double expectedAmount, double tipPercent)
    {
        //this.fromUser = fromUser;
        //this.toUser = toUser;
        this.type = type;
        this.fromPublicKey = fromPublicKey;
        this.toPublicKey = toPublicKey;
        this.expectedAmount = expectedAmount;
        this.actualAmount = expectedAmount * Double.parseDouble("1."+Double.toString(tipPercent));
    }

    public void SignTransaction(User seller)
    {
        this.seller = seller;
        originalMessage = Cryptography.ConvertFromTransactionToByte(this);
        signedMessage = seller.Sign(originalMessage);
    }

}
