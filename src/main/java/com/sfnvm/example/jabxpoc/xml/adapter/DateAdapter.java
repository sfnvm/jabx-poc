package com.sfnvm.example.jabxpoc.xml.adapter;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateAdapter extends XmlAdapter<String, Date> {
    /**
     * Overrides the unmarshal method of the class XmlAdapter in order
     * to parse a date of the type LocalDate
     *
     * @param date String
     * @return Date
     * @throws ParseException - throws unmarshal exception
     */
    public Date unmarshal(String date) throws ParseException {
        // dd/MM/yyyy HH:mm:ss
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    /**
     * Overrides the marshal method of the class XmlAdapter in order
     * to convert the passed date to an String
     *
     * @param date Date
     * @return String
     */
    public String marshal(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(date);
    }
}
