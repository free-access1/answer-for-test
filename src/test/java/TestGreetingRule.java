import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.LocalTime;

public class TestGreetingRule {

    @Test
    public void checkListSizeAfterConstuctorCall() {
        GreetingRule greetingRule = new GreetingRule(Constants.TIME_BOUNDARY_PROPERTIES_FILEPATH);
        Assert.assertTrue(greetingRule.dayParts.size() == 4, "List size is not equal 4");
    }

    @Test
    public void checkAnswerAfterDataCrash() {
        GreetingRule greetingRule = new GreetingRule(Constants.TIME_BOUNDARY_PROPERTIES_FILEPATH);
        greetingRule.dayParts.clear();
        Assert.assertTrue(greetingRule.sayHallo(LocalTime.now().toNanoOfDay() / 1000000).equals("There is no data to write greeting"), "Data crash of 'dayParts' is undefined");
    }

    @Test
    public void checkAnswerOnIncorrectData() {
        GreetingRule greetingRule = new GreetingRule(Constants.TIME_BOUNDARY_PROPERTIES_FILEPATH);
        Assert.assertTrue(greetingRule.sayHallo(LocalTime.parse("23:59").toNanoOfDay()).equals("Your current time is incorrect, check please"), "Incorrect input data 'current time' is undefined");
    }
}
