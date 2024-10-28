package dev.ak.URL_shortening.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import dev.ak.URL_shortening.service.IShortLinkGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShortLinkGenerator implements IShortLinkGenerator {

	@Override
	public String generate(String original) {

		if (Objects.nonNull(original)) {

			try {
				String originalURLPlusRandomvalue = original + ThreadLocalRandom.current().nextDouble();
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] hash = md.digest(originalURLPlusRandomvalue.getBytes());

				StringBuilder hexString = new StringBuilder();
				for (int i = 0; i < 4; i++) {
					hexString.append(String.format("%02x", hash[i]));
				}

				return hexString.toString();

			} catch (NoSuchAlgorithmException e) {

				log.error("Generation shortURL error : {}", e);
				throw new RuntimeException("SHA-256 algorithm not found", e);
			}

		} else {

			log.error("URL for shortening should be not null.");
			throw new IllegalArgumentException("URL for shortening should be not null.");
		}

	}

}
