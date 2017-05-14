package com.manriqueweb.evrjiraaccess.Utils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by omar on 9/05/17.
 */

public final class DateUtils {
    public static DateTime getJodaDate(Date theDate) {
        DateTime response = new DateTime(theDate);

        return response;
    }
}
