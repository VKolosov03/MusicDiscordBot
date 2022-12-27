import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class AudioHandlerTest {
    AudioPlayer player;
    AudioHandler model;

    @BeforeEach
    public void setUp() {
        player = mock(AudioPlayer.class);
        model = new AudioHandler(player);
    }

    @Test
    @DisplayName("new test for canProvide")
    public void testCanProvide() {
        boolean condition = model.canProvide();
        assertFalse(condition);
    }

    @Test
    @DisplayName("new test for provide20MsAudio")
    public void testProvide20MsAudio() {
        ByteBuffer expected = (ByteBuffer)((Buffer) ByteBuffer.allocate(1024)).flip();
        ByteBuffer actual = model.provide20MsAudio();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("new test for isOpus")
    public void testIsOpus() {
        boolean condition = model.isOpus();
        assertTrue(condition);
    }

}
