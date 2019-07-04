package ca.jrvs.apps.twitter.example.dto;

import java.util.ArrayList;
import java.util.Objects;

public class Company {

    private String symbol;
    private String companyName;
    private String exchange;
    private String description;
    private String CEO;
    private String sector;
    private ArrayList<Financial> financials;
    private ArrayList<Dividend> dividends;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public ArrayList<Financial> getFinancials() {
        return financials;
    }

    public void setFinancials(ArrayList<Financial> financials) {
        this.financials = financials;
    }

    public ArrayList<Dividend> getDividends() {
        return dividends;
    }

    public void setDividends(ArrayList<Dividend> dividends) {
        this.dividends = dividends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(symbol, company.symbol) && Objects.equals(companyName, company.companyName) && Objects.equals(exchange, company.exchange) && Objects.equals(description, company.description) && Objects.equals(CEO, company.CEO) && Objects.equals(sector, company.sector) && Objects.equals(financials, company.financials) && Objects.equals(dividends, company.dividends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, companyName, exchange, description, CEO, sector, financials, dividends);
    }
}