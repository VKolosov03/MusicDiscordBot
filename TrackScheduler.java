import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    public final AudioPlayer audioPlayer;
    public final BlockingQueue<AudioTrack> queue;
    private TextChannel textChannel;
    public TrackScheduler(AudioPlayer audioPlayer){
        this.audioPlayer=audioPlayer;
        this.queue = new LinkedBlockingQueue<>();
    }
    public void queueAdd(AudioTrack track, TextChannel textChannel){
        this.textChannel=textChannel;
        if(this.queue.isEmpty() && audioPlayer.getPlayingTrack()==null) this.textChannel.sendMessage("Now playing: "+track.getInfo().title).queue();
        if(!this.audioPlayer.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void queueNext() {
        final AudioTrack track = this.queue.poll();
        if(track!=null) {
            this.textChannel.sendMessage("Now playing: " + track.getInfo().title).queue();
            this.audioPlayer.startTrack(track, false);
        }
        else stopAction();
    }

    public void stopAction(){
        audioPlayer.destroy();
        textChannel.getGuild().getAudioManager().closeAudioConnection();
    }

    public void setStatus() {
        if(!this.audioPlayer.isPaused()) this.audioPlayer.setPaused(true);
        else this.audioPlayer.setPaused(false);
    }

    public ArrayList<String> queueDisplay() {
        ArrayList<String> list = new ArrayList<>();
        for(AudioTrack track: this.queue) {
            list.add(track.getInfo().title);
        }
        return list;
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext) this.queueNext();
    }
}
