import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.server.*;
import com.luxoft.bankapp.service.AccountType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.RunnableFuture;

/**
 * Created by LChlebda on 2016-01-04.
 */
public class BankClientMock implements Runnable{
    Socket requestSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    String message = "test";
    static final String SERVER = "localhost";
    private boolean loggedIn = false;
    private Client client = null;
    boolean activeAccountChoosen = false;
    Scanner sc = new Scanner(System.in);
    String userInput;
    private Request logoutRequest;
    private Request loginRequest;
    private Request getActiveAccountRequest;
    private Request changeActiveAccountRequest;
    private Request withdrawRequest;
    //    Request[] requests = {loginRequest};
    boolean flag = true;
    boolean globalFlag = true;
    String userName;

    private int withdrowCounter = 0;

    public BankClientMock(String userName) {
        this.userName = userName;
    }

    public synchronized void serviceRequest() {

        if (globalFlag) {
            if (!loggedIn) {
                System.out.println("Welcome in super Bank \nEnter your name to login into system \n$> ");
                //userName = sc.next();

                loginRequest = new LoginReguest();
                ((LoginReguest) loginRequest).setLogin(userName);
                sendRequest(loginRequest);

                try {
                    message = (String) objectInputStream.readObject();
                    System.out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (message.equals("OK")) {
                loggedIn = true;
            }

            if (loggedIn) {
                System.out.println("Hello " + userName);
                flag = true;
                while (flag) {
                    System.out.println("\nChoose action: \n1 - Display accounts information \n" +
                            "2 - Withdraw \n" +
                            "3 - Logout \n$> ");
                    if(withdrowCounter<1){
                        userName = "2";
                    }else
                        userName="3";


                    switch (userName) {
                        case "1":
                            getActiveAccountRequest = new GetAccountsInfoRequest();
                            sendRequest(getActiveAccountRequest);
                            try {
                                message = (String) objectInputStream.readObject();
                                System.out.println(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "2":
                            flag = true;
                            while (flag) {
                                System.out.println("Chose account: \n1 - Saving Account \n2 - Checking Account \n3 - Back\n$>");
                                userInput = "1";
                                switch (userInput) {
                                    case "1":
                                        changeActiveAccountRequest = new ChangeActiveAccountRequest(AccountType.SAVING_ACCOUNT);
                                        sendRequest(changeActiveAccountRequest);
                                        try {
                                            message = (String) objectInputStream.readObject();
                                            activeAccountChoosen = true;
                                            performWithdraw();
                                            withdrowCounter++;
                                            loggedIn = true;
                                            flag = false;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "2":
                                        changeActiveAccountRequest = new ChangeActiveAccountRequest(AccountType.CHECKING_ACCOUNT);
                                        sendRequest(changeActiveAccountRequest);
                                        try {
                                            message = (String) objectInputStream.readObject();
                                            activeAccountChoosen = true;
                                            performWithdraw();
                                            loggedIn = true;
                                            flag = false;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case "3":
                                        activeAccountChoosen = false;
                                        loggedIn = true;
                                        flag = false;
                                        break;
                                    default:
                                        System.out.println("No such option");
                                        break;
                                }
                            }
                            break;
                        case "3":
                            logoutRequest = new LogoutRequest();
                            sendRequest(logoutRequest);
                            ((LoginReguest) loginRequest).setLogin("");
                            loggedIn = false;
                            globalFlag = false;
                            flag = false;
                            break;
                        default:
                            System.out.println("No such option ");
                            break;
                    }
                }
            } else {
                System.out.println("No user " + userName + " found in database ");
            }
        }
    }

    public void run() {
        while (true) {
            try {
                requestSocket = new Socket(SERVER, 2004);
                System.out.println("Connected to the localhost ");
                objectOutputStream = new ObjectOutputStream(requestSocket.getOutputStream());
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(requestSocket.getInputStream());
                message = (String) objectInputStream.readObject();
                System.out.println(message);

                do {
                    synchronized (this) {
                        serviceRequest();
                        flag = false;
                        if (!flag) {
                            message = "bye";
                        }
                    }
                } while (!message.equals("bye"));


            } catch (UnknownHostException unknownHost) {
                System.err.println("You are trying to connect to an unknown host!");
            } catch (IOException ioException) {
                ioException.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    requestSocket.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public boolean loginRequest(String name) {
        sendMessage(name);
        try {
            message = (String) objectInputStream.readObject();
            if(message.equals("OK"));
            {
                return true;
            }
        }catch (ClassNotFoundException e) {

        }catch (IOException f) {

        }
        return false;
    }
    public void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            System.out.println("client>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            System.out.println("client>" + request.getRequestType());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String reciveRequest() {
        try {
            message = (String)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    private void performWithdraw() {

        if(activeAccountChoosen) {
            System.out.println("Enter amount ");
            userInput = "1";
            withdrawRequest = new WithdrawRequest();
            ((WithdrawRequest) withdrawRequest).setAmountToWithdraw(Float.valueOf(userInput));
            sendRequest(withdrawRequest);
            try {
                message = (String) objectInputStream.readObject();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
