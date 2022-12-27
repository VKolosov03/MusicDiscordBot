import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class PlayerManagerTest {
    PlayerManager model;
    PlayerManager model2;
    Guild guild;
    TextChannel channel;
    SlashCommandInteractionEvent event;

    @BeforeEach
    public void setUp() {
        model = PlayerManager.getINSTANCE();
        model2 = PlayerManager.getINSTANCE();
        guild = mock(Guild.class);
        channel = mock(TextChannel.class);
        event = mock(SlashCommandInteractionEvent.class);
    }

    @Test
    @DisplayName("new test for getMusicManager")
    public void testGetMusicManager() {
        try {
            model.getMusicManager(guild);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(guild).getAudioManager();
    }

    @Test
    @DisplayName("new test for skipAndPLay")
    public void testSkipAndPlay() {
        try{
            model.skipAndPlay(channel);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(channel).getGuild();
    }

    @Test
    @DisplayName("new test for leaveBot")
    public void testLeaveBot() {
        try{
            model.leaveBot(channel);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(channel).getGuild();
    }

    @Test
    @DisplayName("new test for pause")
    public void testPause() {
        try{
            model.pause(channel);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(channel).getGuild();
    }

    @Test
    @DisplayName("new test for getList")
    public void testGetList() {
        try{
            model.getList(channel);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(channel).getGuild();
    }

    @Test
    @DisplayName("new test for loadAndPlay")
    public void testLoadAndPlay() {
        try{
            model.loadAndPlay(event, "");
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
        verify(event).getGuild();
    }

    @Test
    @DisplayName("new test for getINSTANCE")
    public void testGetINSTANCE() {
        try {
            assertEquals(model, model2);
        } catch (Exception e) {
            fail("Expected 0 exceptions");
        }
    }
}
