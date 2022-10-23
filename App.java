import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import javax.security.auth.login.LoginException;

public class App {
    private ShardManager shm;
    public App() throws LoginException {
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(Constants.token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.setActivity(Activity.listening("Вас"));
        builder.enableCache(CacheFlag.VOICE_STATE);
        shm=builder.build();
        shm.addEventListener(new CommandHandler());
    }
    public ShardManager getShardManager(){
        return shm;
    }
    public static void main(String[] args) throws LoginException{
        try {
            App bot= new App();
            double e= .1E-1;
        }catch (LoginException e){
            System.out.println("Error");
        }
    }
}
