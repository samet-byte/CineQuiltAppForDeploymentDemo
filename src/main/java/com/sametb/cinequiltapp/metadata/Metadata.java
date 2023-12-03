package com.sametb.cinequiltapp.metadata;


import com.sametb.cinequiltapp.favs.Favourite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "metadatas")
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // UUID?

    @Column(name = "title", nullable = false) // length = 255 default
    private String title;

    @Column(name = "director")//, nullable = true, columnDefinition = "VARCHAR(255) DEFAULT 'N/A'")
    private String director;

    @Column(name = "release_year")//, nullable = true, columnDefinition = "INT DEFAULT -1")
    private int releaseYear;

    @Column(name = "duration")//, nullable = true, columnDefinition = "VARCHAR(255) DEFAULT 'N/A'")
    private int duration;

    @Column(name = "poster_url")//)//, nullable = true, columnDefinition = "VARCHAR(255) DEFAULT 'https://sametb.com/no-content.jpg'")
    private String posterUrl;

    @Column(name = "video_url")//, nullable = true, columnDefinition = "VARCHAR(255) DEFAULT 'https://sametb.com/no-content.mp4'")
    private String videoUrl;

    @Column(name = "trailer_url", nullable = true) //, columnDefinition = "VARCHAR(255) DEFAULT 'https://sametb.com/no-content.mp4'")
    private String trailerUrl;

    @Column(name = "soundtrack_url", nullable = true)
    private String soundtrackUrl;

//    @Column(name = "price", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
//    private double price;

//    @Column(name = "create_time", nullable = false)
//    private String createTime;

    // for logging, sorting and possible lawsuits :|
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;


    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;



//    @OneToMany(mappedBy = "metadata", cascade = CascadeType.ALL)
//    private List<UserFavourites> favorites;


    @OneToMany(mappedBy = "metadata", cascade = CascadeType.ALL) //, cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<Favourite> favourites;
}
