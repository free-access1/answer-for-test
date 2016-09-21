import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.time.LocalTime;

public class Greeting {

    public static Logger startLogger() {
        Logger logger = Logger.getRootLogger();
        PropertyConfigurator.configure(Constants.LOG4J_PROPERTIES_FILEPATH);
        return logger;
    }

    static public void main(String[] args) {
        Logger logger = startLogger();
        logger.debug("application is started");

        long currentTime = LocalTime.now().toNanoOfDay() / 1000000;
        logger.debug("current time is recordered");

        // вывел путь к файлу в конструктор, чтобы в в юнит-тесте сделать подмену файла
        GreetingRule answer = new GreetingRule(Constants.TIME_BOUNDARY_PROPERTIES_FILEPATH);
        logger.debug("greeting rule is created");

        answer.sayHallo(currentTime);
        logger.debug("greeting is shown");

        logger.debug("application is finishing...");
        logger.debug("================================================");
    }
}
