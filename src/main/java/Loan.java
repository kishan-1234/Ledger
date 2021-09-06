import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Loan {

    private Borrower borrower;
    private Date sanctionDate;
    private Double principalAmount;
    private int term;
    private Double rateOfInterest;
    private Double totalAmount;
    private int totalEmisRemaing;
    private int firstUnpaidEmi;
    private Double emiCost;
    private List<Emi> emis = new ArrayList<>();
    private final Helper helper = Helper.getInstance();

    public Loan(Borrower borrower, Double principalAmount, int term, Double rateOfInterest) {

         this.borrower = borrower;
         this.sanctionDate = new Date();
         this.principalAmount = principalAmount;
         this.term = term;
         this.rateOfInterest = rateOfInterest;
         sanctionLoan();
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public int getTotalEmisRemaing() {
        return totalEmisRemaing;
    }

    private void sanctionLoan() {

        totalAmount = (principalAmount*rateOfInterest*term)/100 + principalAmount;
        totalEmisRemaing = term*12;
        emiCost = Math.ceil(totalAmount/totalEmisRemaing);
        firstUnpaidEmi = 1;
        Double emiSum = 0.0;

        for(int i=0;i<totalEmisRemaing;i++)
        {
            emiSum += emiCost;
            Emi emi = new Emi(i+1,Math.min(emiCost, totalAmount-emiSum), helper.DateAfterDays(sanctionDate, 30*(i+1)), totalEmisRemaing-i-1);
            emis.add(emi);
        }
    }

    public void makeLumpSumPayment(int emiNumber, Double lumpSum) {

        double emiSum = 0;
        for(int i=firstUnpaidEmi-1;i<emiNumber;i++)
        {
            Emi emi = emis.get(i);
            emiSum += emi.getEmiAmount();
            if(i==emiNumber-1) {
                emi.makePayment(new Date(), emi.getEmiAmount()+lumpSum);
            }
            else emi.makePayment(new Date(), emi.getEmiAmount());
            firstUnpaidEmi++;
        }
        double totalAmountRemaining = totalAmount - (emiSum+lumpSum);
        totalEmisRemaing = (int)Math.ceil(totalAmountRemaining/emiCost);
        emis.get(firstUnpaidEmi-2).updateEmisReaming(totalEmisRemaing);
        List<Integer> removeEmiIndexList = new ArrayList<>();
        int temp = totalEmisRemaing;

        for(int i=firstUnpaidEmi-1;i<emis.size();i++)
        {
            double emiAmount = Math.min(emiCost, totalAmountRemaining - emiCost);
            if(emiAmount==0.0) {
                removeEmiIndexList.add(i);
            } else {
                Emi emi = emis.get(i);
                if(emi.getEmiAmount()!=emiAmount) {
                    emi.updateEmiAmount(emiAmount);
                }
                emi.updateEmisReaming(temp-1);
                totalAmountRemaining -= emiAmount;
                temp--;
            }
        }
        for(Integer i: removeEmiIndexList) {
            emis.remove(i);
        }
    }

    public Double getTotalPaymentMade(int emiNumber) {

        double res = 0;
        for(int i=0;i<emiNumber;i++)
        {
            res += emis.get(i).getPaymentAmount();
        }
        return res;
    }

    public Emi getEmi(int emiNumber) {

        return emiNumber == 0 ? emis.get(0):emis.get(emiNumber-1);
    }
}
