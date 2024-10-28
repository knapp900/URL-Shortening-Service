package dev.ak.URL_shortening.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.ak.URL_shortening.entity.Link;
import dev.ak.URL_shortening.service.ILinkService;
import dev.ak.URL_shortening.service.IShortLinkGenerator;
import dev.ak.URL_shortening.service.IncrementOfLinkCounter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class LinkController {

	private final ILinkService linkService;
	private final IShortLinkGenerator generator;
	private final IncrementOfLinkCounter incrementOfLinkCounter;

	public LinkController(ILinkService linkService, IShortLinkGenerator generator,
			IncrementOfLinkCounter incrementOfLinkCounter) {

		this.linkService = linkService;
		this.generator = generator;
		this.incrementOfLinkCounter = incrementOfLinkCounter;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> generate(@RequestBody Map<String, String> originalURL) {
		Map<String, String> responseForUser = new HashMap<String, String>();
		
		Link responseFromService = linkService.create(originalURL.get("original"), generator);
		responseForUser.put("original", responseFromService.getOriginal());
		responseForUser.put("link",'/' + responseFromService.getLink());
		return ResponseEntity.ok(responseForUser);
	}

	@GetMapping("{shortURL}")
	public ResponseEntity<Void> redirect(@PathVariable String shortURL, RedirectAttributes redirectAttributes) {
		HttpHeaders headers = new HttpHeaders();
		Link response = linkService.read(shortURL);
		incrementOfLinkCounter.incrementCount(response.getId());

		log.info("REDIRECT TO: {}", response.getOriginal());

		headers.setLocation(URI.create(response.getOriginal()));
		return new ResponseEntity<>(headers, HttpStatus.FOUND);

	}

}
