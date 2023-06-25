package com.numble.reservation.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
	@Value("${redis.address}")
	private String address;

	@Bean
	public RedissonClient redissonClient() {
		final Config config = new Config();

		config.useSingleServer()
			.setAddress(address);

		return Redisson.create(config);
	}
}
