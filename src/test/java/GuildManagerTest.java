import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GuildManagerTest {

    @Test
    @DisplayName("new test for getHandler")
    public void testGetHandler() {
        GuildManager guildManager = mock(GuildManager.class);
        try{
            AudioHandler obj = guildManager.getHandler();
            assertNull(obj);
        } catch (Exception e) {
            fail("Expected 0 exceptions");
        }
        verify(guildManager).getHandler();
    }

}
