import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/* Задача этого класса выполнить предварительную работу с сырыми данными и подготовить их к вычислению.
*  В частности:
*  1. Выбрать нужный бандл сообщений на основе системной локали пользователя
*  2. Взять входные значения начала и окончания времени суток
*  3. Привести значения времени от строчного к целочисленному значению для последующих операций сравнения
* */

public class TimesBoundary {
    public Logger logger = Logger.getRootLogger();
    public long startTime = 0;
    public long endTime = 0;
    public String message = "";

    public TimesBoundary(String filepath, String startTimeKey, String endTimeKey, String messageKey) {

        ResourceBundle messages = ResourceBundle.getBundle("GreetingBundle", Locale.getDefault());
        this.message = messages.getString(messageKey);
        this.logger.trace(messageKey + ": localized message is defined");

        getInitialConditions(filepath, startTimeKey, endTimeKey, messageKey);
    }

    public void getInitialConditions(String filepath, String startTimeKey, String endTimeKey, String messageKey){
        Properties timesBoundariesValues = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(filepath);
            timesBoundariesValues.load(input);
            this.logger.trace(messageKey + ": property-file is opened");

            this.startTime = modifyTimeFromStringToLong(timesBoundariesValues, startTimeKey, messageKey);
            this.endTime = modifyTimeFromStringToLong(timesBoundariesValues, endTimeKey, messageKey);
            this.logger.trace(messageKey + ": (long) "+startTimeKey+" and (long) "+endTimeKey+" are defined");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            this.logger.error(messageKey + ": something is going wrong with file " + filepath);
        } finally {
            if (input != null) {
                try {
                    input.close();
                    this.logger.trace(messageKey + ": property-file is closed");
                } catch (IOException e) {
                    e.printStackTrace();
                    this.logger.error(messageKey + ": property-file is unreachable");
                }
            }
        }
    }

    public long modifyTimeFromStringToLong(Properties properties, String value, String messageKey) {
        String stringValue = properties.getProperty(value);
        this.logger.trace(messageKey + ": (String) "+value+" is got from file");
        long result = LocalTime.parse(stringValue).toNanoOfDay() / 1000000;
        this.logger.trace(messageKey + ": (long) "+value+" is defined");
        return result;
    }

    public String getMessage() {
        return message;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
