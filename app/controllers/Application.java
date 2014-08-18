package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Application extends Controller {
    public static JedisPool pool;

    public static Result command(String a) {
        if (pool != null) {
	    String res = "";
            
            Jedis jedis = pool.getResource();
	    switch (a) { 
                case "set":
                    res = jedis.set("welcome_msg", "Hello from Redis!");
                    break;
                case "get":
                    res = jedis.get("welcome_msg");
                    break;					
                case "info":
                    res = jedis.info();
                    break;					
                case "flush":
                    res = jedis.flushDB();
                    break;		
	    }		
            pool.returnResource(jedis);

            return ok(res != null ? res : "N/A");
        } else {
            return ok("Error");
        }
    }
}
