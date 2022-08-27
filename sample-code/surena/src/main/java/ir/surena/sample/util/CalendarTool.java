package ir.surena.sample.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarTool {
    private int irYear;
    private int irMonth;
    private int irDay;
    private int gYear;
    private int gMonth;
    private int gDay;
    private int juYear;
    private int juMonth;
    private int juDay;
    private int leap;
    private int JDN;
    private int march;
    private int myHour;
    private int myMinutes;
    private int mySeconds;

    public CalendarTool() {
        Calendar calendar = new GregorianCalendar();
        this.setGregorianDate(calendar.get(1), calendar.get(2) + 1, calendar.get(5));
        this.myHour = calendar.get(11);
        this.myMinutes = calendar.get(12);
        this.mySeconds = calendar.get(13);
    }

    public CalendarTool(Date dt) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dt);
        this.setGregorianDate(calendar.get(1), calendar.get(2) + 1, calendar.get(5));
        this.myHour = calendar.get(11);
        this.myMinutes = calendar.get(12);
        this.mySeconds = calendar.get(13);
    }

    public CalendarTool(Timestamp dt) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dt);
        this.setGregorianDate(calendar.get(1), calendar.get(2) + 1, calendar.get(5));
        this.myHour = calendar.get(11);
        this.myMinutes = calendar.get(12);
        this.mySeconds = calendar.get(13);
    }

    public CalendarTool(int year, int month, int day) {
        this.setGregorianDate(year, month, day);
    }

    public int getIranianYear() {
        return this.irYear;
    }

    public int getIranianMonth() {
        return this.irMonth;
    }

    public int getIranianDay() {
        return this.irDay;
    }

    public int getMyHour() {
        return this.myHour;
    }

    public int getMyMinutes() {
        return this.myMinutes;
    }

    public int getMySeconds() {
        return this.mySeconds;
    }

    public int getGregorianYear() {
        return this.gYear;
    }

    public int getGregorianMonth() {
        return this.gMonth;
    }

    public int getGregorianDay() {
        return this.gDay;
    }

    public int getJulianYear() {
        return this.juYear;
    }

    public int getJulianMonth() {
        return this.juMonth;
    }

    public int getJulianDay() {
        return this.juDay;
    }

    public String getIranianDate() {
        int var10000 = this.irYear;
        return var10000 + "/" + String.format("%02d", this.irMonth) + "/" + String.format("%02d", this.irDay);
    }

    public String getIranianDate2() {
        int var10000 = this.irYear;
        return var10000 + "-" + String.format("%02d", this.irMonth) + "-" + String.format("%02d", this.irDay);
    }

    public String getIranianDateTime() {
        int var10000 = this.irYear;
        return var10000 + "/" + String.format("%02d", this.irMonth) + "/" + String.format("%02d", this.irDay) + " " + String.format("%02d", this.myHour) + ":" + String.format("%02d", this.myMinutes) + ":" + String.format("%02d", this.mySeconds);
    }

    public String getIranianDateTime2() {
        int var10000 = this.irYear;
        return var10000 + "-" + String.format("%02d", this.irMonth) + "-" + String.format("%02d", this.irDay) + " " + String.format("%02d", this.myHour) + ":" + String.format("%02d", this.myMinutes) + ":" + String.format("%02d", this.mySeconds);
    }

    public String getGregorianDate() {
        return this.gYear + "/" + this.gMonth + "/" + this.gDay;
    }

    public String getGregorianDate2() {
        return this.gYear + "-" + this.gMonth + "-" + this.gDay;
    }

    public String getGregorianDate2WithTime() {
        int var10000 = this.gYear;
        return var10000 + "-" + String.format("%02d", this.gMonth) + "-" + String.format("%02d", this.gDay) + " " + String.format("%02d", this.myHour) + ":" + String.format("%02d", this.myMinutes) + ":" + String.format("%02d", this.mySeconds);
    }

    public String getJulianDate() {
        return this.juYear + "/" + this.juMonth + "/" + this.juDay;
    }

    public String getWeekDayStr() {
        String[] weekDayStr = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return weekDayStr[this.getDayOfWeek()];
    }

    public String toString() {
        String var10000 = this.getWeekDayStr();
        return var10000 + ", Gregorian:[" + this.getGregorianDate() + "], Julian:[" + this.getJulianDate() + "], Iranian:[" + this.getIranianDate() + "]";
    }

    public int getDayOfWeek() {
        return this.JDN % 7;
    }

    public void nextDay() {
        ++this.JDN;
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void nextDay(int days) {
        this.JDN += days;
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void previousDay() {
        --this.JDN;
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void previousDay(int days) {
        this.JDN -= days;
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void setIranianDate(int year, int month, int day) {
        this.irYear = year;
        this.irMonth = month;
        this.irDay = day;
        this.JDN = this.IranianDateToJDN();
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void setIranianDate(int year, int month, int day, int hour, int min, int sec) {
        this.irYear = year;
        this.irMonth = month;
        this.irDay = day;
        this.myHour = hour;
        this.myMinutes = min;
        this.mySeconds = sec;
        this.JDN = this.IranianDateToJDN();
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void setGregorianDate(int year, int month, int day) {
        this.gYear = year;
        this.gMonth = month;
        this.gDay = day;
        this.JDN = this.gregorianDateToJDN(year, month, day);
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    public void setJulianDate(int year, int month, int day) {
        this.juYear = year;
        this.juMonth = month;
        this.juDay = day;
        this.JDN = this.julianDateToJDN(year, month, day);
        this.JDNToIranian();
        this.JDNToJulian();
        this.JDNToGregorian();
    }

    private void IranianCalendar() {
        int[] Breaks = new int[]{-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        this.gYear = this.irYear + 621;
        int leapJ = -14;
        int jp = Breaks[0];
        int j = 1;

        int jm;
        int jump;
        do {
            jm = Breaks[j];
            jump = jm - jp;
            if (this.irYear >= jm) {
                leapJ += jump / 33 * 8 + jump % 33 / 4;
                jp = jm;
            }

            ++j;
        } while(j < 20 && this.irYear >= jm);

        int N = this.irYear - jp;
        leapJ += N / 33 * 8 + (N % 33 + 3) / 4;
        if (jump % 33 == 4 && jump - N == 4) {
            ++leapJ;
        }

        int leapG = this.gYear / 4 - (this.gYear / 100 + 1) * 3 / 4 - 150;
        this.march = 20 + leapJ - leapG;
        if (jump - N < 6) {
            N = N - jump + (jump + 4) / 33 * 33;
        }

        this.leap = ((N + 1) % 33 - 1) % 4;
        if (this.leap == -1) {
            this.leap = 4;
        }

    }

    public boolean IsLeap(int irYear1) {
        int[] Breaks = new int[]{-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        this.gYear = irYear1 + 621;
        int leapJ = -14;
        int jp = Breaks[0];
        int j = 1;

        int jm;
        int jump;
        do {
            jm = Breaks[j];
            jump = jm - jp;
            if (irYear1 >= jm) {
                leapJ += jump / 33 * 8 + jump % 33 / 4;
                jp = jm;
            }

            ++j;
        } while(j < 20 && irYear1 >= jm);

        int N = irYear1 - jp;
        leapJ += N / 33 * 8 + (N % 33 + 3) / 4;
        if (jump % 33 == 4 && jump - N == 4) {
            ++leapJ;
        }

        int leapG = this.gYear / 4 - (this.gYear / 100 + 1) * 3 / 4 - 150;
        this.march = 20 + leapJ - leapG;
        if (jump - N < 6) {
            N = N - jump + (jump + 4) / 33 * 33;
        }

        this.leap = ((N + 1) % 33 - 1) % 4;
        if (this.leap == -1) {
            this.leap = 4;
        }

        return this.leap == 4 || this.leap == 0;
    }

    private int IranianDateToJDN() {
        this.IranianCalendar();
        return this.gregorianDateToJDN(this.gYear, 3, this.march) + (this.irMonth - 1) * 31 - this.irMonth / 7 * (this.irMonth - 7) + this.irDay - 1;
    }

    private void JDNToIranian() {
        this.JDNToGregorian();
        this.irYear = this.gYear - 621;
        this.IranianCalendar();
        int JDN1F = this.gregorianDateToJDN(this.gYear, 3, this.march);
        int k = this.JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                this.irMonth = 1 + k / 31;
                this.irDay = k % 31 + 1;
                return;
            }

            k -= 186;
        } else {
            --this.irYear;
            k += 179;
            if (this.leap == 1) {
                ++k;
            }
        }

        this.irMonth = 7 + k / 30;
        this.irDay = k % 30 + 1;
    }

    private int julianDateToJDN(int year, int month, int day) {
        return (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408;
    }

    private void JDNToJulian() {
        int j = 4 * this.JDN + 139361631;
        int i = j % 1461 / 4 * 5 + 308;
        this.juDay = i % 153 / 5 + 1;
        this.juMonth = i / 153 % 12 + 1;
        this.juYear = j / 1461 - 100100 + (8 - this.juMonth) / 6;
    }

    private int gregorianDateToJDN(int year, int month, int day) {
        int jdn = (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408;
        jdn = jdn - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
        return jdn;
    }

    private void JDNToGregorian() {
        int j = 4 * this.JDN + 139361631;
        j += (4 * this.JDN + 183187720) / 146097 * 3 / 4 * 4 - 3908;
        int i = j % 1461 / 4 * 5 + 308;
        this.gDay = i % 153 / 5 + 1;
        this.gMonth = i / 153 % 12 + 1;
        this.gYear = j / 1461 - 100100 + (8 - this.gMonth) / 6;
    }
}
