import java.util.Date;

public class Helper {

    private Helper() {}
    private static Helper helper = null;

    public static Helper getInstance() {
        if(helper==null) {
            synchronized(Helper.class) {
                helper = new Helper();
            }
        }
        return helper;
    }

    public Date DateAfterDays(Date date, int addDays) {

        long newDate = date.getTime()+addDays*24*60*60*1000;
        return new Date(newDate);
    }

    public String[] Split(String str, String seperator) {

        return str.split(seperator);
    }
}
