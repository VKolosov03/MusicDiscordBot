import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildManager {
    public final AudioPlayer player;
    public final TrackScheduler scheduler;
    private final AudioHandler handler;

    public GuildManager(AudioPlayerManager manager){
        this.player = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.player);
        this.player.addListener(this.scheduler);
        this.handler = new AudioHandler(this.player);
    }

    public AudioHandler getHandler() { return this.handler; }
}
