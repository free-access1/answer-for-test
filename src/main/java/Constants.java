public interface Constants {

    // Resource files
    String TIME_BOUNDARY_PROPERTIES_FILEPATH = "src/main/resources/TimesBoundary.properties";
    String LOG4J_PROPERTIES_FILEPATH = "src/main/resources/Log4j.properties";
    String[][] RULE_PARAMETERS =
    {
        {"morningTime.start", "morningTime.end", "morning"},
        {"dayTime.start",     "dayTime.end",     "day"},
        {"eveningTime.start", "eveningTime.end", "evening"},
        {"nightTime.start",   "nightTime.end",   "night"}
    };
}
