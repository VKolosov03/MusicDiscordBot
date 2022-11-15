import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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

    /* Process user request */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("play")){
            if(event.getMember().getVoiceState().getChannel()!=null) {
                final AudioManager audioManager = event.getGuild().getAudioManager();
                String linkOption = event.getOption("link").getAsString();
                audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
                String link = String.join(" ", linkOption);
                PlayerManager.getINSTANCE().loadAndPlay(event, link);
            }
            else event.reply("User, please connect to voice channel").queue();
        }
        // Check if `play` was activated
        else if(event.getGuild().getAudioManager().isConnected()){
            if(event.getName().equals("skip")){
                event.reply(PlayerManager.getINSTANCE().skipAndPlay(event.getChannel().asTextChannel())).queue();
            }
            else if(event.getName().equals("list")){
                event.reply(PlayerManager.getINSTANCE().getList(event.getChannel().asTextChannel())).queue();
            }
            else if(event.getName().equals("leave")){
                event.reply(PlayerManager.getINSTANCE().leaveBot(event.getChannel().asTextChannel())).queue();
            }
            else if(event.getName().equals("poc")){
                event.reply(PlayerManager.getINSTANCE().pause(event.getChannel().asTextChannel())).queue();
            }
        }
        else event.reply("This command is not working now").queue();
    }

//    @Override
//    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
//    }

    /* Add commands to bot list of active commands */
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        OptionData option1 = new OptionData(OptionType.STRING, "link", "music link");
        event.getGuild().updateCommands().addCommands(
                Commands.slash("play", "play track").addOptions(option1),
                Commands.slash("skip", "skip track"),
                Commands.slash("list", "get list"),
                Commands.slash("leave", "delete bot"),
                Commands.slash("poc", "pause or continue")
        ).queue();
    }
}
