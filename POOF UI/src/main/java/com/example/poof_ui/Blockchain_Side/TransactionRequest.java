package com.example.poof_ui.Blockchain_Side;

public class TransactionRequest
{
    public double tradeAmount;

    public TransactionRequest(double tradeAmount)
    {
        this.tradeAmount = tradeAmount;
    }

}

class BuyingRequest extends TransactionRequest
{
    public Trader trader;

    public BuyingRequest(Trader trader, float tradeAmount)
    {
        super(tradeAmount);
        this.trader = trader;
    }

}

class SellingRequest extends TransactionRequest implements Comparable<SellingRequest>
{
    public User user;
    public double transactionFeePercent;

    public SellingRequest(User user, double tradeAmount, double transactionFeePercent)
    {
        super(tradeAmount);
        this.user = user;
        this.transactionFeePercent = transactionFeePercent;
    }

    @Override
    public int compareTo(SellingRequest request)
    {

        if (request.transactionFeePercent > this.transactionFeePercent)
        {
            return 1;
        }
        else if (request.transactionFeePercent < this.transactionFeePercent)
        {
            return -1;
        }

        return 0;
    }
}
