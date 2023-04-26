public class TransactionMatch
{
    public User fromUser;
    public User toUser;
    public float amount;

    public TransactionMatch(User fromUser, User toUser, float amount)
    {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
    }
}
