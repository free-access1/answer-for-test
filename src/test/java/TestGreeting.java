import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class TestGreeting {

    @Test
    public void checkExistingFileTimesBoundaryproperties () throws IOException {
        File file = new File(Constants.TIME_BOUNDARY_PROPERTIES_FILEPATH);
        Assert.assertTrue(file.exists(), "File TimesBoundary.properties does not exist");
    }

    @Test
    public void checkExistingFileLog4jProperties () throws IOException {
        File file = new File(Constants.LOG4J_PROPERTIES_FILEPATH);
        Assert.assertTrue(file.exists(), "File TimesBoundary.properties does not exist");
    }

    @Test
    public void checkGettingCurrentTimeBySimplerWay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String curentTime1 = dateFormat.format(calendar.getTime());
        String currentTime2 = LocalTime.now().toString().substring(0,8);
        System.out.println(curentTime1 + " " + currentTime2);
        Assert.assertTrue(curentTime1.equals(currentTime2), "Localtime is not matching to current time");
    }
}
