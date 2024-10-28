package dev.ak.URL_shortening.service;

public interface IShortLinkGenerator {
	
	/**
	 * This method will generate short URl from long URL
	 * @param original (long URL)
	 * @return short URL.
	 */
	String generate(String original);

}
