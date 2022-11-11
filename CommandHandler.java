import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("play")){
            final AudioManager audioManager = event.getGuild().getAudioManager();
            String linkOption = event.getOption("link").getAsString();
            audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
            String link = String.join(" ", linkOption);
            PlayerManager.getINSTANCE().loadAndPlay(event.getChannel().asTextChannel(), link);

        }
        if(event.getName().equals("skip")){
            PlayerManager.getINSTANCE().skipAndPlay(event.getChannel().asTextChannel());
        }
        if(event.getName().equals("list")){
            PlayerManager.getINSTANCE().getList(event.getChannel().asTextChannel());
        }
        if(event.getName().equals("leave")){
            PlayerManager.getINSTANCE().leaveBot(event.getChannel().asTextChannel());
        }
        if(event.getName().equals("poc")){
            PlayerManager.getINSTANCE().pause(event.getChannel().asTextChannel());
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        OptionData option1 = new OptionData(OptionType.STRING, "link", "music link");
        commandData.add(Commands.slash("play", "play track").addOptions(option1));
        commandData.add(Commands.slash("skip", "skip track"));
        commandData.add(Commands.slash("list", "get list"));
        commandData.add(Commands.slash("leave", "delete bot"));
        commandData.add(Commands.slash("poc", "pause or continue"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
