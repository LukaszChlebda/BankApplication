
import com.luxoft.bankapp.command_request.ClientCommander;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.server.*;
import com.luxoft.bankapp.service.AccountType;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Łukasz on 17.01.2016.
 */
public class BankClientMock implements Runnable{
    private Socket requestSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String message;
    static final String SERVER = "localhost";

    private ClientCommander clientCommander;

    Request loginRequest, changeActiveAccountRequest,  withdrawRequest, logOutRequest;

    public BankClientMock() {

    }

    public void run() {

        synchronized (this) {
            try {
                    requestSocket = new Socket(SERVER, 2004);
                    System.out.println("Connected to the localhost ");
                    objectOutputStream = new ObjectOutputStream(requestSocket.getOutputStream());
                    objectOutputStream.flush();
                    objectInputStream = new ObjectInputStream(requestSocket.getInputStream());

                    loginRequest = new LoginReguest("Lukasz");
                    sendRequest(loginRequest);
                    message = (String) objectInputStream.readObject();
                    System.out.println(message);

                    changeActiveAccountRequest = new ChangeActiveAccountRequest(AccountType.SAVING_ACCOUNT);
                    sendRequest(changeActiveAccountRequest);
                    message = (String) objectInputStream.readObject();
                    System.out.println(message);

                    withdrawRequest = new WithdrawRequest();
                    ((WithdrawRequest) withdrawRequest).setAmountToWithdraw(1);
                    sendRequest(withdrawRequest);
                    message = (String) objectInputStream.readObject();

                    logOutRequest = new LogoutRequest();
                    sendRequest(logOutRequest);
                    message = (String) objectInputStream.readObject();
                    System.out.println(message);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {

                    objectInputStream.close();
                    objectOutputStream.close();
                    requestSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            }
        }



    public synchronized void serviceRequest() {
        try {
            clientCommander.mainMenu();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (NotEnoughtFundsException e) {
            e.printStackTrace();
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            System.out.println("client>" + request.getRequestType());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public synchronized void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            System.out.println("client>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}