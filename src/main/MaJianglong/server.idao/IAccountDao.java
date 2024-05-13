package server.idao;

import server.model.Account;

public interface IAccountDao {
    boolean deleteAccount(String accNo);
    public Account updateText(String accId);
    public void updateText_Name(String accId,String name);

    public boolean transfer1(String fromAccount);
    public boolean add_Balance(String accNo);
    public boolean dec_Balance(String accNo);
}
