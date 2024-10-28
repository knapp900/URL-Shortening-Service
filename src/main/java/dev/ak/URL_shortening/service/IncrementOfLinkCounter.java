package dev.ak.URL_shortening.service;

public interface IncrementOfLinkCounter {
	/**
	 * This method increment of Link counter
	 * 
	 * @param linkId
	 */
	void incrementCount(int linkId);

}
