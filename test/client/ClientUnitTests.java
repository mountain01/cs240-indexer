package client;

import client.models.QualityCheckModel;
import org.junit.*;
import server.Server;
import shared.Communicator.ClientCommunicator;

import java.util.List;

import static org.junit.Assert.*;

public class ClientUnitTests {

    private static final String KNOWN_GENDER_DATA = "http://localhost:34590/knowndata/genders.txt";
    private static final String KNOWN_FIRSTNAME_DATA = "http://localhost:34590/knowndata/1890_first_names.txt";

    QualityCheckModel checker;

    @BeforeClass
    public static void init(){
        Server.startTest();
        ClientCommunicator.getInstance().setPort(34590);
    }

    @AfterClass
    public static void teardown(){
        Server.endTest();
    }

    @Before
    public void setup(){
        checker = new QualityCheckModel();
        checker.fieldChange(KNOWN_GENDER_DATA);
        checker.fieldChange(KNOWN_FIRSTNAME_DATA);
    }

    @Test
    public void validGenderDataTest(){
        List<String> suggestions = checker.getSuggestions("L",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

    @Test
    public void invalidGenderDataTest(){
        List<String> suggestions = checker.getSuggestions("Lazy",KNOWN_GENDER_DATA);
        Assert.assertEquals(0, suggestions.size());
    }

    @Test
    public void checkIfSortedTest(){
        List<String> suggestions = checker.getSuggestions("ez",KNOWN_FIRSTNAME_DATA);
        for(int i = 1; i < suggestions.size(); i++){
            //Checks to see if it's in alphabetical order and that there are no duplicates
            String compareTo = suggestions.get(i-1);
            String compareWith = suggestions.get(i);
            Assert.assertTrue(compareTo.compareTo(compareWith) < 0);
        }
    }

    @Test
    public void checkIfErrorOnPunctuationInput(){
        List<String> suggestions = checker.getSuggestions(".",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

    @Test
    public void checkIfErrorOnBlankInput(){
        List<String> suggestions = checker.getSuggestions("",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

    @Test
    public void checkSpaceInput(){
        List<String> suggestions = checker.getSuggestions(" ",KNOWN_GENDER_DATA);
        Assert.assertEquals(2, suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

