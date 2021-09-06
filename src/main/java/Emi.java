import java.util.Date;

public class Emi {
    private int emiNumber;
    private Double emiAmount;
    private Double paymentAmount;
    private Date dueDate;
    private Date paymentDate;
    private int emisRemaining;

    public Emi(int emiNumber, Double emiAmount, Date dueDate, int emisRemaining) {

        this.emiNumber = emiNumber;
        this.emiAmount = emiAmount;
        this.dueDate = dueDate;
        this.paymentAmount = emiAmount;
        this.emisRemaining = emisRemaining;
    }

    public void makePayment(Date date, Double paymentAmount) {
        paymentDate = date;
        this.paymentAmount = paymentAmount;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public int getEmisRemaining() {
        return emisRemaining;
    }

    public void updateEmiAmount(Double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public void updateEmisReaming(int emisRemaining) {
        this.emisRemaining = emisRemaining;
    }
}
