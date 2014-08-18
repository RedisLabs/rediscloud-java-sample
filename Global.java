import play.*;
import controllers.Application;

import java.net.URI;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class Global  extends GlobalSettings {
    
    @Override
    public void onStart(play.Application app) {
        try {
	    URI redisUri = new URI(System.getenv("REDISCLOUD_URL"));
            controllers.Application.pool = new JedisPool(new JedisPoolConfig(),
                redisUri.getHost(),
                redisUri.getPort(),
                Protocol.DEFAULT_TIMEOUT,
                redisUri.getUserInfo().split(":",2)[1]);

            Logger.info("Connection pool successfully initialized.");				
	} catch (Exception e) {
	    Logger.error("Connection pool could  not be initialized.", e);
	    controllers.Application.pool = null;
	}
    }
}

