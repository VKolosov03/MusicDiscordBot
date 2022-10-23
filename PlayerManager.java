import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildManager> musicManager;
    private final AudioPlayerManager manager;

    public PlayerManager(){
        this.musicManager = new HashMap<>();
        this.manager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(this.manager);
        AudioSourceManagers.registerLocalSource(this.manager);
    }

    public GuildManager getMusicManager(Guild guild) {
        return this.musicManager.computeIfAbsent(guild.getIdLong(), (guildId) -> {
           final GuildManager guildManager = new GuildManager(this.manager);
           guild.getAudioManager().setSendingHandler(guildManager.getHandler());
           return guildManager;
        });
    }

    public void skipAndPlay(TextChannel textChannel) {
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        textChannel.sendMessage("Skipping track").queue();
        musicManager.scheduler.queueNext();
    }

    public void getList(TextChannel textChannel) {
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        ArrayList<String> list=musicManager.scheduler.queueDisplay();
        String text="Next songs:\n";
        for(int i=0; i<list.size(); i++) text=text+(i+1)+". "+list.get(i)+"\n";
        textChannel.sendMessage(text).queue();
    }

    public void loadAndPlay(TextChannel textChannel, String trackURl) {
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        this.manager.loadItemOrdered(musicManager, trackURl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queueAdd(audioTrack, textChannel);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                final List<AudioTrack> tracks = audioPlaylist.getTracks();
                if(!tracks.isEmpty()) musicManager.scheduler.queueAdd(tracks.get(0), textChannel);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }

    public static PlayerManager getINSTANCE() {
        if(INSTANCE == null)  INSTANCE = new PlayerManager();
        return INSTANCE;
    }
}
