package dev.ak.URL_shortening.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.ak.URL_shortening.entity.Link;

public interface ILinkService {
	/**
	 * Create link
	 * 
	 * @param originalURL
	 * @param generator   for shortening long URL
	 * @return Link object
	 */
	Link create(String originalURL, IShortLinkGenerator generator);

	/**
	 * Reading link from repository by shortURl
	 * 
	 * @param shortURL
	 * @return Link if exist
	 */
	Link read(String shortURL);

	/**
	 * Read all links from repository with pageable
	 * 
	 * @param pageble
	 * @return Page<Link> links
	 */
	Page<Link> read(Pageable pageable);

	/**
	 * Update original URL name of link
	 * 
	 * @param originalURL
	 * @param shortURL
	 * @return true if success
	 */
	boolean update(String originalURL, String shortURL);

	/**
	 * Delete link by shortURL
	 */
	boolean delete(String shortURL);

}
