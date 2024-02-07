import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.APItests;
import tests.HomePageTests;
import tests.SearchTests;
import tests.SiteCrawlerTest;

@Suite
@SelectClasses({
        HomePageTests.class,
        SearchTests.class,
        SiteCrawlerTest.class,
        APItests.class
})
public class TestSuiteRunner {

}

