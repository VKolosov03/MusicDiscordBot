import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    public final AudioPlayer audioPlayer; // object for tracks control
    public final BlockingQueue<AudioTrack> queue; // tracks queue
    private TextChannel textChannel; // Server text channel

    public TrackScheduler(AudioPlayer audioPlayer){
        this.audioPlayer=audioPlayer;
        this.queue = new LinkedBlockingQueue<>();
    }
    /* Add new track to the list(play if this is the only song) */
    public void queueAdd(AudioTrack track, TextChannel textChannel){
        this.textChannel=textChannel;
        if(this.queue.isEmpty() && audioPlayer.getPlayingTrack()==null) this.textChannel.sendMessage("Now playing: "+track.getInfo().title).queue();
        if(!this.audioPlayer.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    /* Start next track in list(leave if list is empty) */
    public void queueNext() {
        final AudioTrack track = this.queue.poll();
        if(track!=null) {
            this.textChannel.sendMessage("Now playing: " + track.getInfo().title).queue();
            this.audioPlayer.startTrack(track, false);
        }
        else stopAction();
    }

    /* Leave voice channel */
    public void stopAction(){
        audioPlayer.destroy();
        textChannel.getGuild().getAudioManager().closeAudioConnection();
    }

    /* Set pause or continue */
    public boolean setStatus() {
        this.audioPlayer.setPaused(!this.audioPlayer.isPaused());
        return this.audioPlayer.isPaused();
    }

    /* Return tracks list */
    public ArrayList<String> queueDisplay() {
        ArrayList<String> list = new ArrayList<>();
        for(AudioTrack track: this.queue) {
            list.add(track.getInfo().title);
        }
        return list;
    }

    /* Process track ending(works automatically) */
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext) this.queueNext();
    }
}
