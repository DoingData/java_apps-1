package ca.jrvs.apps.twitter.example.dto;

import java.util.Objects;

public class Dividend {

    private String exDate;
    private String paymentDate;
    private String recordDate;
    private String declaredDate;
    private double amount;

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getDeclaredDate() {
        return declaredDate;
    }

    public void setDeclaredDate(String declaredDate) {
        this.declaredDate = declaredDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dividend dividend = (Dividend) o;
        return Double.compare(dividend.amount, amount) == 0 && Objects.equals(exDate, dividend.exDate) && Objects.equals(paymentDate, dividend.paymentDate) && Objects.equals(recordDate, dividend.recordDate) && Objects.equals(declaredDate, dividend.declaredDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exDate, paymentDate, recordDate, declaredDate, amount);
    }
}