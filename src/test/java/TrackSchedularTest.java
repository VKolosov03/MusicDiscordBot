import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class TrackSchedularTest {
    AudioTrack track;
    TextChannel channel;
    AudioPlayer player;
    AudioTrackEndReason reason;
    TrackScheduler model;
    @Before
    public void setUp() {
        track = mock(AudioTrack.class);
        channel = mock(TextChannel.class);
        player = mock(AudioPlayer.class);
        reason = mock(AudioTrackEndReason.class);
    }

    @Test
    @DisplayName("new test for queueAdd")
    public void testQueueAdd() {
        model = mock(TrackScheduler.class);
        try{
            model.queueAdd(track, channel);
        } catch (Exception e) {
            fail("Expected 0 exceptions");
        }
        verify(model).queueAdd(track, channel);
    }

    @Test
    @DisplayName("new test for queueNext")
    public void testQueueNext() {
        model = mock(TrackScheduler.class);
        try{
            model.queueNext();
        } catch (Exception e) {
            fail("Expected 0 exceptions");
        }
        verify(model).queueNext();
    }

    @Test
    @DisplayName("new test for stopAction")
    public void testStopAction() {
        model = mock(TrackScheduler.class);
        try{
            model.stopAction();
        } catch (Exception e) {
            fail("Expected 0 exceptions");
        }
        verify(model).stopAction();
    }

    @Test
    @DisplayName("new test for setStatus")
    public void testSetStatus() {
        model = new TrackScheduler(player);
        try {
            model.setStatus();
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("new test for queueDisplay")
    public void testQueueDisplay() {
        model = new TrackScheduler(player);
        ArrayList<String> actual = model.queueDisplay();
        ArrayList<String> expected = new ArrayList<String>(){};
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("new test for onTrackEnd")
    public void testOnTrackEnd() {
        model = mock(TrackScheduler.class);
        try{
            model.onTrackEnd(player, track, reason);
        } catch (Exception e) {
            fail("Expected 0 exceptions");
        }
        verify(model).onTrackEnd(player, track, reason);
    }
}
