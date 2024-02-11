package quran.gnd.com;

public class DateTimes {
    private String date,start,end;
    public DateTimes(){}
    public DateTimes(String date, String start, String end){
        this.date = date;
        this.start = start;
        this.end = end;
    }
    public String getDate() {
        return date;
    }
    public String getStart() {
        return start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setStart(String start) {
        this.start = start;
    }
}
