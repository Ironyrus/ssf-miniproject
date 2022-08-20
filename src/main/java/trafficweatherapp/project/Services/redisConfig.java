package trafficweatherapp.project.Services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class redisConfig {

    private static final Logger logger = LoggerFactory.getLogger(redisConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;
    
    // @Value("${spring.redis.password}")
    private String redisPassword = System.getenv("REDIS_API_KEY");

    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setPassword(redisPassword);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config, jedisClient);
        jedisFactory.afterPropertiesSet();

        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    // @Value("${spring.redis.host}")
    // private String redisHost;

    // @Value("${spring.redis.port}")
    // private Optional<Integer> redisPort;

    // @Value("${spring.redis.password}")
    // private String redisPassword;

    // @Value("${spring.redis.database}")
    // private String redisDatabase;

    // @Bean()
    // @Scope("singleton")
    // public RedisTemplate<String, currconverter> redisTemplate() {
    //     final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    //     config.setHostName(redisHost);
    //     config.setPort(redisPort.get());
    //     config.setPassword(redisPassword);
    //     Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(currconverter.class);

    //     final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
    //     final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
    //     jedisFac.afterPropertiesSet();
    //     RedisTemplate<String, currconverter> template = new RedisTemplate<String, currconverter>();
    //     template.setConnectionFactory(jedisFac);
    //     template.setKeySerializer(new StringRedisSerializer());
    //     template.setValueSerializer(jackson2JsonJsonSerializer);
    //     template.setHashKeySerializer(template.getKeySerializer());
    //     template.setHashValueSerializer(template.getValueSerializer());
    //     return template;
    // }
}