import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {

    private static final Helper helper = Helper.getInstance();
    private static final String LOAN = "LOAN";
    private static final String PAYMENT = "PAYMENT";
    private static final String BALANCE = "BALANCE";
    private static Map<String, Bank> bankMap = new HashMap<>();
    private static Map<String, Borrower> borrowerMap = new HashMap<>();

    public static void main(String[] args) {

        String fileName = args[0];
        try {
            processFile(fileName);
        } catch (Exception e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    private static void processFile(String fileName) throws FileNotFoundException {

        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] args = helper.Split(data, " ");
                switch (args[0]) {
                    case LOAN:
                        handleLoan(args);
                        break;
                    case PAYMENT:
                        handlePayment(args);
                        break;
                    case BALANCE:
                        handleBalance(args);
                        break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void handleLoan(String[] args) {

        String bankName = args[1];
        String borrowerName = args[2];
        Double principalAmount = Double.parseDouble(args[3]);
        int term = Integer.parseInt(args[4]);
        Double rateOfInterest = Double.parseDouble(args[5]);

        Bank bank = getBank(bankName);
        Borrower borrower = getBorrower(borrowerName);
        bank.addLoan(borrower, principalAmount, term, rateOfInterest);
    }

    private static void handleBalance(String[] args) {

        String bankName = args[1];
        String borrowerName = args[2];
        int emiNumber = Integer.parseInt(args[3]);

        Bank bank = getBank(bankName);
        Loan loan = bank.getLoan(borrowerName);
        Double totalPaymentMade = loan.getTotalPaymentMade(emiNumber);
        int emisRemiaing = loan.getEmi(emiNumber).getEmisRemaining();
        if(emiNumber==0) emisRemiaing++;
        System.out.println(bank.getName()+" "+loan.getBorrower().Name+" "+totalPaymentMade.intValue()+" "+emisRemiaing);
    }

    private static void handlePayment(String[] args) {

        String bankName = args[1];
        String borrowerName = args[2];
        Double lumpsum = Double.parseDouble(args[3]);
        int emiNumber = Integer.parseInt(args[4]);

        Bank bank = getBank(bankName);
        Loan loan = bank.getLoan(borrowerName);
        loan.makeLumpSumPayment(emiNumber, lumpsum);
    }

    private static Bank getBank(String bankName) {

        Bank bank;
        if(bankMap.containsKey(bankName)) {
            bank =  bankMap.get(bankName);
        } else {
            synchronized (Application.class) {
                bank = new Bank(bankName);
                bankMap.put(bankName, bank);
            }
        }
        return bank;
    }

    private static Borrower getBorrower(String borrowerName) {

        Borrower borrower;
        if(borrowerMap.containsKey(borrowerMap)) {
            borrower = borrowerMap.get(borrowerName);
        } else {
            synchronized (Application.class) {
                borrower = new Borrower(borrowerName);
                borrowerMap.put(borrowerName, borrower);
            }
        }
        return borrower;
    }
}
