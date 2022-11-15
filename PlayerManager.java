import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

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

    /* Send skip request */
    public String skipAndPlay(TextChannel textChannel) {
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        musicManager.scheduler.queueNext();
        return "Skipping track";
    }

    /* Send leave request */
    public String leaveBot(TextChannel textChannel){
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        musicManager.scheduler.stopAction();
        return "Bye-bye";
    }

    /* Set track status(pause, continue) */
    public String pause(TextChannel textChannel){
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        musicManager.scheduler.setStatus();
        if(musicManager.scheduler.setStatus()) return "Track paused";
        return "Track continued";
    }

    /* Form List into String */
    public String getList(TextChannel textChannel) {
        final GuildManager musicManager = this.getMusicManager(textChannel.getGuild());
        ArrayList<String> list=musicManager.scheduler.queueDisplay();
        String text = "Next songs:\n";
        if(list.size()==0) return "List is empty";
        for (int i = 0; i < list.size(); i++) text = text + (i + 1) + ". " + list.get(i) + "\n";
        textChannel.sendMessage(text).queue();
        return text;
    }

    /* Process the URL */
    public void loadAndPlay(SlashCommandInteractionEvent event, String trackURl){
        final GuildManager musicManager = this.getMusicManager(event.getGuild());
        this.manager.loadItemOrdered(musicManager, trackURl, new AudioLoadResultHandler() {
            /* Add 1 track */
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManager.scheduler.queueAdd(audioTrack, event.getChannel().asTextChannel());
                event.reply("Track added").queue();
            }

            /* Add list */
            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                final List<AudioTrack> tracks = audioPlaylist.getTracks();
                if(!tracks.isEmpty()) musicManager.scheduler.queueAdd(tracks.get(0), event.getChannel().asTextChannel());
                event.reply("List added").queue();
            }

            /* Process not URL error */
            @Override
            public void noMatches() {
                this.closeFailedConnection();
                event.reply("It's not even a URL, bebra").queue();
            }

            /* Process wrong URL error */
            @Override
            public void loadFailed(FriendlyException e) {
                this.closeFailedConnection();
                event.reply("Wrong URL, I can't open it").queue();
            }

            /* Check if track's playing now then close connection */
            private void closeFailedConnection() { if(musicManager.player.getPlayingTrack()==null) event.getGuild().getAudioManager().closeAudioConnection(); }
        });
    }

    /* Create object(Singletone) */
    public static PlayerManager getINSTANCE() {
        if(INSTANCE == null)  INSTANCE = new PlayerManager();
        return INSTANCE;
    }
}
