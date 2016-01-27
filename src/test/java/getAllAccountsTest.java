import com.luxoft.bankapp.dao.BaseDAO;
import com.luxoft.bankapp.dao.BaseDAOImpl;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by LChlebda on 2016-01-25.
 */
public class getAllAccountsTest {

    @Test
    public void getAllAccountsTest() {
        BaseDAO baseDAO = new BaseDAOImpl();
        Client client = new Client(67, "Luka");

        ClientDaoImpl clientDao = new ClientDaoImpl();
        try {
            client.addAccounts(clientDao.getClientAccounts(67));
        } catch (DAOException e) {
            e.printStackTrace();
        }



            System.out.print(client.getAccounts().get(0).getAccountInfo());

    }

}
