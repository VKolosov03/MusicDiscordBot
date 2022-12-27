import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.LoginException;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    @DisplayName("new test for getShardManager")
    public void testGetShardManager() {
        try {
            App app = new App();
            ShardManager unexpected = DefaultShardManagerBuilder.createDefault(Constants.token).build();
            ShardManager actual = app.getShardManager();
            assertNotEquals(unexpected, actual);
        } catch (LoginException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("new test for main")
    public void testMain() {
        try {
            App.main(new String[]{""});
        } catch (LoginException e) {
            fail("Unexpected LoginException");
        }
    }

//    @Test
//    @DisplayName("new test for main without Internet connection")
//    public void testMainNoConnection() {
//        try {
//            App.main(new String[]{""});
//            fail("Expected UnknownHostException");
//        } catch (LoginException e) {
//            fail("Unexpected LoginException");
//        } catch (ErrorResponseException e) {
//            assertNotNull(e);
//        }
//    }

}
