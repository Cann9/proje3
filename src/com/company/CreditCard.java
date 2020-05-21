package com.company;

public class CreditCard
{
    private int limit;
    private String creditcardnumber;
    private int totaldebt;
    private int minimumdebt;

    public CreditCard(int limit, String creditcardnumber, int totaldebt) {
        this.limit = limit;
        this.creditcardnumber = creditcardnumber;
        this.totaldebt = totaldebt;
        minimumdebt=(totaldebt/100)*30;
        limit=5000;
    }
    public void payDebt(Account accountToPayDebt,int ödenecektutar)
    {
        int ödenecekdebt=accountToPayDebt.getBalance()-ödenecektutar;
        totaldebt-=ödenecektutar;
        accountToPayDebt.setBalance(ödenecekdebt);
    }


    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    public int getTotaldebt() {
        return totaldebt;
    }

    public void setTotaldebt(int totaldebt) {
        this.totaldebt = totaldebt;
    }

    public int getMinimumdebt() {
        return minimumdebt;
    }

    public void setMinimumdebt(int minimumdebt) {
        this.minimumdebt = minimumdebt;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "limit=" + limit +
                ", creditcardnumber='" + creditcardnumber + '\'' +
                ", totaldebt=" + totaldebt +
                ", minimumdebt=" + minimumdebt +
                '}';
    }
}
