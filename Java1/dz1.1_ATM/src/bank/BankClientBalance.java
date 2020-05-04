package bank;

public class BankClientBalance {

    private double availableSum = 0;
    private double creditSum = 0;
    private double creditMaximum = 199;     // Для тестов

    public double getAvailableSum() {
        return availableSum;
    }

    public void setAvailableSum(double availableSum) {
        this.availableSum = availableSum;
    }

    public double getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(double creditSum) {
        this.creditSum = creditSum;
    }

    public void withdrawalOperation(double sum) {
        if (availableSum >= sum) {
            availableSum -= sum;
        } else {
            double temp = sum - availableSum;
            availableSum = 0;
            creditSum += temp;
        }
    }

    public void addMoney(double sum) {
        if (creditSum > 0) {
            double temp = creditSum - sum;
            if (temp < 0) {
                availableSum = sum - creditSum;
                creditSum = 0;
            } else {
                creditSum -= sum;
            }
        } else {
            availableSum += sum;
        }
    }

    public double getCreditMaximum() {
        return creditMaximum;
    }

    public void setCreditMaximum(double creditMaximum) {
        this.creditMaximum = creditMaximum;
    }
}
