package br.net.digitalzone.algafood.core.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//Habilita a salvar tudo no REDIS
@EnableRedisHttpSession
public class SessionConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	/*
	 * Isso é suficiente para salvar tudo no REDIS.
	 */
}
