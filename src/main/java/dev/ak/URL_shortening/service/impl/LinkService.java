package dev.ak.URL_shortening.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ak.URL_shortening.dao.LinksRepository;
import dev.ak.URL_shortening.entity.Link;
import dev.ak.URL_shortening.exceptions.LinkServiceException;
import dev.ak.URL_shortening.service.ILinkService;
import dev.ak.URL_shortening.service.IShortLinkGenerator;
import dev.ak.URL_shortening.service.IncrementOfLinkCounter;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LinkService implements ILinkService,IncrementOfLinkCounter {

	private final LinksRepository linksRepository;

	public LinkService(LinksRepository linksRepository) {
		this.linksRepository = linksRepository;
	}

    @Override
    public Link create(String originalURL, IShortLinkGenerator shorteningLink) {
        try {
            String shortURL;
            do {
                shortURL = shorteningLink.generate(originalURL);
            } while (linksRepository.existsByLink(shortURL));

            Link link = new Link();
            link.setOriginal(originalURL);
            link.setLink(shortURL);
            link.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
            
            log.info("Link for creation : {}",link.toString());

            return linksRepository.save(link);
        } catch (Exception e) {
            log.error("Error creating link for URL: {}", originalURL, e);
            throw new LinkServiceException("Error creating link for URL: " + originalURL, e);
        }
    }

	@Override
	public Link read(String shortURL) {
		return linksRepository.findByLink(shortURL).orElseThrow(() -> {
			log.error("Error getting link by shortURL: {}", shortURL);
			throw new LinkServiceException("Error getting link by shortURL: " + shortURL);
		});
	}

	@Override
	public Page<Link> read(Pageable pageble) {
		try {

			return linksRepository.findAll(pageble);

		} catch (Exception e) {

			log.error("Error getting all linka");
			throw new LinkServiceException("Error getting all linka", e);
		}
	}

	@Transactional
	@Override
	public boolean update(String originalURL, String shortURL) {
		try {
			Link link = linksRepository.findByLink(shortURL)
					.orElseThrow(() -> new EntityNotFoundException("Link not found for shortURL: " + shortURL));

			link.setOriginal(originalURL);
			linksRepository.save(link);
			return true;
		} catch (EntityNotFoundException e) {
			log.error("Link not found for update, shortURL: {}", shortURL);
			return false;
		} catch (Exception e) {
			log.error("Error updating link for shortURL: {}", shortURL, e);
			throw new LinkServiceException("Error updating link for shortURL: " + shortURL, e);
		}
	}

	@Override
	public boolean delete(String shortURL) {
		try {
			if (linksRepository.existsByLink(shortURL)) {
				linksRepository.deleteByLink(shortURL);
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("Error deleting link for shortURL: {}", shortURL, e);
			throw new LinkServiceException("Error deleting link for shortURL: " + shortURL, e);
		}
	}
	
	@Async
	@Transactional
	@Override
	public void incrementCount(int linkId) {
		
		linksRepository.findById(linkId).ifPresent(link -> {
			link.setCount(link.getCount() + 1);
			link.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
			linksRepository.save(link);
		});
		
	}

}
