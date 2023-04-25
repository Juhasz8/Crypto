import java.util.ArrayList;

enum TransactionType { BUY, SELL }

public class DataManager
{

    public static DataManager instance;
    private ArrayList<TransactionRequest> requests = new ArrayList<>();

    private DataManager()
    {
        
    }

    public static DataManager getInstance()
    {
        if(instance == null)
            instance = new DataManager();

        return instance;
    }

    public void ProcessRequest(TransactionRequest request)
    {
        requests.add(request);
    }

}
