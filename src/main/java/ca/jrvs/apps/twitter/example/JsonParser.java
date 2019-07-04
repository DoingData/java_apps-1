package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.example.dto.Company;
import ca.jrvs.apps.twitter.example.dto.Dividend;
import ca.jrvs.apps.twitter.example.dto.Financial;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonParser {

    public static void main(String[] args) {
        Company company = new Company();
        company.setSymbol("AAPL");
        company.setCompanyName("Apple Inc.");
        company.setExchange("Nasdaq Global Select");
        company.setDescription("Apple Inc is designs, manufactures and markets mobile communication and media devices " + "and personal computers, and sells a variety of related software, services, accessories,networking " + "solutions and third-party digital content and applications.");
        company.setCEO("Timothy D. Cook");
        company.setSector("Technology");
        Financial financial1 = new Financial();
        financial1.setReportDate("2018-12-31");
        financial1.setGrossProfit(new BigInteger("32131000000"));
        financial1.setCostOfRevenue(new BigInteger("52279000000"));
        financial1.setOperatingRevenue(new BigInteger("84310000000"));
        financial1.setTotalRevenue(new BigInteger("84310000000"));
        financial1.setOperatingIncome(new BigInteger("23346000000"));
        financial1.setNetIncome(new BigInteger("19965000000"));
        Financial financial2 = new Financial();
        financial2.setReportDate("2018-09-30");
        financial2.setGrossProfit(new BigInteger("24084000000"));
        financial2.setCostOfRevenue(new BigInteger("38816000000"));
        financial2.setOperatingRevenue(new BigInteger("62900000000"));
        financial2.setTotalRevenue(new BigInteger("62900000000"));
        financial2.setOperatingIncome(new BigInteger("16118000000"));
        financial2.setNetIncome(new BigInteger("14125000000"));
        ArrayList<Financial> financials = new ArrayList<>();
        financials.addAll(Arrays.asList(financial1, financial2));
        company.setFinancials(financials);
        Dividend dividend1 = new Dividend();
        dividend1.setExDate("2018-02-09");
        dividend1.setPaymentDate("2018-02-15");
        dividend1.setRecordDate("2018-02-12");
        dividend1.setDeclaredDate("2018-02-01");
        dividend1.setAmount(0.63);
        Dividend dividend2 = new Dividend();
        dividend2.setExDate("2017-11-10");
        dividend2.setPaymentDate("2017-11-16");
        dividend2.setRecordDate("2017-11-13");
        dividend2.setDeclaredDate("2017-11-02");
        dividend2.setAmount(0.63);
        ArrayList<Dividend> dividends = new ArrayList<>();
        dividends.addAll(Arrays.asList(dividend1, dividend2));
        company.setDividends(dividends);

        try {
            String json = toJson(company, true, false);
            System.out.println(json);
            Company company2 = toObjectFromJson(json, Company.class);
            System.out.println(company2.equals(company));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert a java object to JSON string
     *
     * @param object            input object
     * @param prettyJson        if prettyJson is enabled it will write standard indentation
     * @param includeNullValues decides whether null values are written as an empty string or throw exception
     * @return JSON String
     * @throws JsonProcessingException
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (prettyJson) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        if (includeNullValues) {
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }
        return mapper.writeValueAsString(object);
    }

    /**
     * Parse JSON string to an object
     *
     * @param json  JSON string
     * @param clazz object class
     * @param <T>   Type
     * @return Object
     * @throws IOException
     */
    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, clazz);
    }
}
