package dev.ak.URL_shortening.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.ak.URL_shortening.dao.LinksRepository;
import dev.ak.URL_shortening.entity.Link;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleUpdateRankOfLink {

	@Value("${task.sql.interval}")
	private int sqlInterval;

	private final LinksRepository repository;

	public ScheduleUpdateRankOfLink(LinksRepository repository) {
		this.repository = repository;
	}

	@Transactional
	@Scheduled(fixedRateString = "${task.scheduling.interval}")
	public void updateRankOfLinks() {
		List<Link> linksToUpdate = repository.findLinksToUpdate(sqlInterval);
		
		log.info("ScheduleUpdateRankOfLink: {}" ,linksToUpdate.toString());
		
        for (Link link : linksToUpdate) {
            double newRank = calculateRank(link.getCount());
            link.setRank(newRank);
//            link.setLastUpdated(Timestamp.valueOf(LocalDateTime.now())); //Нужно время бд
            repository.save(link);
        }
    }

    private double calculateRank(long count) {
        
        return  Math.log(count + 1);
    }
}
