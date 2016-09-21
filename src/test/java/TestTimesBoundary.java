import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class TestTimesBoundary extends DataProviderContainer{
    public final String TEST_PROPERTY_FILEPATH = "src/test/java/TestTimesBoundary.properties";

    public void writeFile(String morningStartTime, String dayStartTime, String eveningStartTime, String nightStartTime) throws IOException {
        FileWriter writer = new FileWriter(TEST_PROPERTY_FILEPATH, false);
        try {
            writer.write("morningTime.start = " + morningStartTime); writer.append('\n');
            writer.write("morningTime.end = "   + dayStartTime);     writer.append('\n');
            writer.write("dayTime.start = "     + dayStartTime);     writer.append('\n');
            writer.write("dayTime.end = "       + eveningStartTime); writer.append('\n');
            writer.write("eveningTime.start = " + eveningStartTime); writer.append('\n');
            writer.write("eveningTime.end = "   + nightStartTime);   writer.append('\n');
            writer.write("nightTime.start = "   + nightStartTime);   writer.append('\n');
            writer.write("nightTime.end = "     + morningStartTime); writer.append('\n');

            writer.flush();
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public List<Set> modifyTime(String morningStartTime, String dayStartTime, String eveningStartTime, String nightStartTime) {
        List<Set> result = new LinkedList<Set>();
        result.add(new Set("morning", "утра", LocalTime.parse(morningStartTime).toNanoOfDay() / 1000000, LocalTime.parse(dayStartTime).toNanoOfDay() / 1000000));
        result.add(new Set("day", "дня", LocalTime.parse(dayStartTime).toNanoOfDay() / 1000000, LocalTime.parse(eveningStartTime).toNanoOfDay() / 1000000));
        result.add(new Set("evening", "вечера", LocalTime.parse(eveningStartTime).toNanoOfDay() / 1000000, LocalTime.parse(nightStartTime).toNanoOfDay() / 1000000));
        result.add(new Set("night", "ночи", LocalTime.parse(nightStartTime).toNanoOfDay() / 1000000, LocalTime.parse(morningStartTime).toNanoOfDay() / 1000000));

        return result;
    }

    @Test (dataProvider = "initDataForTimesBoundary")
    public void checkRightAnswer(String morningStartTime, String dayStartTime, String eveningStartTime, String nightStartTime) throws IOException {
        writeFile(morningStartTime, dayStartTime, eveningStartTime, nightStartTime);

        List<Set> set = modifyTime(morningStartTime, dayStartTime, eveningStartTime, nightStartTime);

        LocalTime currentTime = LocalTime.now();
        long currentTimeInMilliseconds = currentTime.toNanoOfDay() / 1000000;
        GreetingRule answer = new GreetingRule(TEST_PROPERTY_FILEPATH);
        String str = answer.sayHallo(currentTimeInMilliseconds);
        String resultStr = str.substring(str.indexOf(' ') + 1, str.indexOf(','));

        boolean result = false;

        for (int i = 0; i < set.size(); i++) {
            if ((resultStr.equals(set.get(i).getDayTimeEN())) || (resultStr.equals(set.get(i).getDayTimeRU()))) {
                if ((currentTimeInMilliseconds >= set.get(i).getStart()) && (currentTimeInMilliseconds < set.get(i).getEnd())) {
                    result = true;
                    break;
                }
                else if (((currentTimeInMilliseconds >= set.get(i).getStart()) &&
                        (currentTimeInMilliseconds < LocalTime.parse("23:59").toNanoOfDay() / 1000000)) ||
                        ((currentTimeInMilliseconds >= LocalTime.parse("00:00").toNanoOfDay() / 1000000) &&
                        (currentTimeInMilliseconds < set.get(i).getEnd()))) {
                    result = true;
                    break;
                }
                else {
                    result = false;
                    break;
                }
            }
        }
        Assert.assertTrue(result, "Incorrect day time definition");

    }
}

// вспомогательный класс для сбора данных
class Set {
    String dayTimeEN;
    String dayTimeRU;
    long start;
    long end;

    public Set (String dayTimeEN, String dayTimeRU, long start, long end) {
        this.dayTimeEN = dayTimeEN;
        this.dayTimeRU = dayTimeRU;
        this.start = start;
        this.end = end;
    }

    public String getDayTimeEN() {
        return dayTimeEN;
    }

    public String getDayTimeRU() {
        return dayTimeRU;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}