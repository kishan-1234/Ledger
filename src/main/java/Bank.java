import java.util.HashMap;
import java.util.Map;

public class Bank {

    public String Name;
    public Map<String, Loan> loanMap = new HashMap<>();

    public Bank(String name) {

        Name = name;
    }

    public String getName() {
        return Name;
    }

    public Loan getLoan(String borrowName) {

        if(loanMap.containsKey(borrowName)) {
            return loanMap.get(borrowName);
        } else {
            return null;
        }
    }

    public void addLoan(Borrower borrower, Double principalAmount, int term, Double rateOfInterest) {

        Loan loan = new Loan(borrower, principalAmount, term, rateOfInterest);
        loanMap.put(borrower.getName(), loan);
        return;
    }
}
