import org.testng.annotations.DataProvider;

public class DataProviderContainer {

    public int loopTime (int time) {
        if (time > 23) return time - 24;
        else return time;
    }

    public String changeDateFormat (int time) {
        int loopedTime = loopTime(time);
        if (loopedTime < 10) return "0" + Integer.toString(loopedTime);
        else return Integer.toString(loopedTime);
    }

    @DataProvider
    public Object[][] initDataForTimesBoundary() {
        Object [][] result = new Object[24][4];

        for(int i = 0; i < 24; i++) {
            result[i][0] = changeDateFormat(i) + ":00";
            result[i][1] = changeDateFormat(i+3) + ":00";
            result[i][2] = changeDateFormat(i+13) + ":00";
            result[i][3] = changeDateFormat(i+17) + ":00";
        }

        return result;
    }
}