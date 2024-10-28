package dev.ak.URL_shortening.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.ak.URL_shortening.entity.Link;

@Repository
public interface LinksRepository extends JpaRepository<Link, Integer> {

	boolean existsByLink(String link);

	Optional<Link> findByLink(String link);

	Page<Link> findAll(Pageable pageble);
	
	boolean deleteByLink(String link);
	
	@Query(value = "SELECT * FROM t_links WHERE c_last_updated > DATEADD('MINUTE', -?1, CURRENT_TIMESTAMP)", nativeQuery = true)
	List<Link> findLinksToUpdate(@Param("interval") int interval);



}
