package dev.ak.URL_shortening.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "t_links")
@Entity
public class Link {
	@Id
	@Column(name = "c_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "c_link")
	private String link;
	@Column(name = "c_original")
	private String original;
	@Column(name = "c_rank")
	private double rank;
	@Column(name = "c_count")
	private long count;
	@Column(name = "c_last_updated")
    private Timestamp lastUpdated;
}
