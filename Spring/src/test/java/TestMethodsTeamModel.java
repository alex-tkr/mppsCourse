import org.example.models.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestMethodsTeamModel {


    @Test
    public void testEquals(){
        Team teamExpexted=new Team(4,"test12","rtjtyjk","2023-04-03 22:59:24.122993+03");
        Team teamActive=new Team(4,"test1","rtjtyjk","2023-04-03 22:59:24.122993+03");
        assertEquals(true, teamActive.equals(teamExpexted));

        teamExpexted=new Team(4,"test12","rtjtyjk","2023-04-03 22:59:24.122993+03");
        teamActive=new Team(4,"test1","rtjtyjk","2023-04-03 22:59:24.122993+03");
        assertEquals(false, teamActive.equals(teamExpexted));

        teamExpexted=new Team(4,"test1","rtjtyjk1","2023-04-03 22:59:24.122993+03");
        teamActive=new Team(4,"test1","rtjtyjk","2023-04-03 22:59:24.122993+03");
        assertEquals(false, teamActive.equals(teamExpexted));

        teamExpexted=new Team(4,"test1","rtjtyjk","2023-04-03 22:59:24.122993+03");
        teamActive=new Team(4,"test1","rtjtyjk","2023-04-03 22:59:24.122993+03");
        assertEquals(false, teamActive.equals(teamExpexted));




   }

}
