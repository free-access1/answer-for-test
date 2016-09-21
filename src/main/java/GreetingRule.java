import org.apache.log4j.Logger;
import java.util.LinkedList;
import java.util.List;

/* Задачи этого класса следующие:
* 1. Сформировать список времён суток
* 2. Перебрав список, определить к какому времени суток относится время начала работы и вывести соответствующее сообщение*/

public class GreetingRule {

    Logger logger = Logger.getRootLogger();
    List<TimesBoundary> dayParts = new LinkedList<TimesBoundary>();

    public GreetingRule(String filepath) {
        setPreparedData(filepath);
        this.logger.info("datalist is ready");
    }

    // т.к. файл с начальными условиями заведомо небольшой по объему и находится внутри проекта
    // и есть необходимость подмены файла в юнит-тестах, я заведомо решил писать минимальный по объему код.
    // Но это приводит к тому, что файл настроек будет открываться/закрываться при построчном чтении,
    // но в заданных условиях задачи это не влияет на производительность
    public void setPreparedData(String filepath) {
        for (int i = 0; i < Constants.RULE_PARAMETERS.length; i++) {
            TimesBoundary timesBoundary = new TimesBoundary(
                    filepath,
                    Constants.RULE_PARAMETERS[i][0],
                    Constants.RULE_PARAMETERS[i][1],
                    Constants.RULE_PARAMETERS[i][2]);
            this.logger.trace(Constants.RULE_PARAMETERS[i][2] + ": data is collected - (long) "+Constants.RULE_PARAMETERS[i][0]+", (long) "+Constants.RULE_PARAMETERS[i][1]+" and localized message for " + Constants.RULE_PARAMETERS[i][2]);

            this.dayParts.add(timesBoundary);
            this.logger.trace(Constants.RULE_PARAMETERS[i][2] + ": data is added to list");
        }
    }

    public String sayHallo(long currentTime) {

        this.logger.trace("searching right answer for current time is started");

        if (dayParts.size() == 0) {
            return System.out.println("There is no data to write greeting");
        }
        else {

            for (TimesBoundary dayPart : dayParts) {

                if (dayPart.getStartTime() <= dayPart.getEndTime()) {

                    if ((dayPart.getStartTime() <= currentTime) && (currentTime < dayPart.getEndTime())) {

                        this.logger.info("searching right answer for current time is finished");
                        return System.out.println(dayPart.getMessage());
                    }
                } else {
                    if (((dayPart.getStartTime() <= currentTime) && (currentTime < 86340000)) ||
                            ((0 <= currentTime) && (currentTime < dayPart.getEndTime()))) {

                        this.logger.info("searching right answer for current time is finished");
                        return System.out.println(dayPart.getMessage());

                    }
                }
            }
        }
        return System.out.println("Your current time is incorrect, check please");
    }
}

// вспомогательный класс для прехвата текста - вывода в консоль
class System  {
    public final static OutputStream out = new OutputStream();

    static class OutputStream {
        public String println(String x) {
            new java.io.PrintStream(java.lang.System.out).println(x);
            return x;
        }
    }
}