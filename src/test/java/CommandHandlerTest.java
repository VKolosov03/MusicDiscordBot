import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class CommandHandlerTest {
    CommandHandler model;
    SlashCommandInteractionEvent event1;
    GuildReadyEvent event2;

    @BeforeEach
    public void setUp() {
        model = new CommandHandler();
        event1 = mock(SlashCommandInteractionEvent.class);
        event2 = mock(GuildReadyEvent.class);
    }

    @Test
    @DisplayName("new test for onSlashCommandInteraction")
    public void testOnSlashCommandInteraction() {
        try {
            model.onSlashCommandInteraction(event1);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(event1).getName();
    }

    @Test
    @DisplayName("new test for onGuildReady")
    public void testOnGuildReady() {
        try {
            model.onGuildReady(event2);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(event2).getGuild();
    }

}
