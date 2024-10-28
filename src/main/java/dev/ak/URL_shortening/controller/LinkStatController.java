package dev.ak.URL_shortening.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ak.URL_shortening.entity.Link;
import dev.ak.URL_shortening.service.impl.LinkService;

@RestController
@RequestMapping("/stat")
public class LinkStatController {

	private final LinkService linkService;

	public LinkStatController(LinkService linkService) {
		this.linkService = linkService;
	}

	@GetMapping
	public ResponseEntity<Page<Map<String, String>>> getAllStats(Pageable pageable) {
		Page<Link> responseOfService = linkService.read(pageable);

		Page<Map<String, String>> mappedPage = responseOfService.map(link -> {
			Map<String, String> map = new HashMap<>();
			map.put("link", link.getLink());
			map.put("original", link.getOriginal());
			map.put("rank", String.format("%3f",link.getRank()));
			map.put("count", Long.toString(link.getCount()));
			return map;
		});

		return ResponseEntity.ok(mappedPage);

	}

	@GetMapping("{shortURL}")
	public ResponseEntity<Map<String, String>> getStatByName(@PathVariable String shortURL) {
		Map<String, String> responseForUser = new HashMap<>();
		Link link = linkService.read(shortURL);
		responseForUser.put("link", link.getLink());
		responseForUser.put("original", link.getOriginal());
		responseForUser.put("rank", Double.toString(link.getRank()));
		responseForUser.put("count", Long.toString(link.getCount()));
		return ResponseEntity.ok(responseForUser);

	}

}
