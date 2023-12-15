package com.sametb.cinequiltapp.fav;

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:35 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "Favourite")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    @JoinColumn(name = "metadata_title", referencedColumnName = "title")
    private Metadata metadata;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH.mm:ss A")
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;



}
