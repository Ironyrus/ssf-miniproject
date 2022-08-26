package trafficweatherapp.project.Database;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.json.JsonObject;
import trafficweatherapp.project.Models.options;

//https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html

@Service
public class redisService implements servicesRepo {
    
    ArrayList<String> keys;

    //To save and retrieve normal HashMap, String etc form Redis
    // @Autowired
    // RedisTemplate<String, Object> redisTemplate;

    //To save and retrieve POJO from Redis
    @Autowired
    RedisTemplate<String, options> redisTemplate;

    @Override
    public void deleteAll() {
        this.keys = getKeys();
        for (String key : keys) {
            redisTemplate.delete(key); //Deleting key. Casting from integer to String (Since Redis keys are String)
        }
    }

    //Helper class to get all keys from database
    public ArrayList<String> getKeys() {
        Set<String> redisKeys = redisTemplate.keys("*"); //Pattern is * for ALL keys. Getting all keys.
        ArrayList<String> keys = new ArrayList<>();
        for (String item : redisKeys) {
            keys.add(item);
        }
        return keys;
    }

    @Override
    public void saveOptions(String username, options options) {
        redisTemplate.opsForValue().set(username, options);
    }

    @Override
    public options getOptions(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    // @Override
    // public void saveCurrVariable() {
    //     String id = "1";
    //     redisTemplate.opsForValue().set(id, "test");
        
    // }

    // @Override
    // public void getCurrVariable() {
    //     String id = "1";
    //     String test = (String)redisTemplate.opsForValue().get(id);
    // }
    
    // //GET
    // @Override
    // public Checkers findBoardGame(String boardGame) {
    //     Checkers game = redisTemplate.opsForValue().get(boardGame);
    //     //String game = "test";
    //     System.out.println(game);
    //     return game;
    // }

    // //PUT
    // @Override
    // public int update(final Checkers checkerObj, String Id) {

    //     Checkers  result = (Checkers) redisTemplate.opsForValue().get(Id);

    //     System.out.println("INUPDATEMETHOD: " + result.getId());
    //     if (result.isUpsert())
    //         redisTemplate.opsForValue().setIfAbsent(Id, checkerObj);
    //     else
    //         redisTemplate.opsForValue().setIfPresent(Id, checkerObj);
    //     if (result != null) {
    //         checkerObj.setUpdateCount(result.getUpdateCount() + 1); //Incrementing Update Count
    //         checkerObj.setId(result.getId());
    //         redisTemplate.opsForValue().setIfPresent(Id, checkerObj);
    //         return checkerObj.getUpdateCount();
    //     }
    //     return 0;
    // }

}