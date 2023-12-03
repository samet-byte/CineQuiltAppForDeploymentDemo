package com.sametb.cinequiltapp.favs;

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 4:52 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


@Data
@Entity
@Table(name = "favourites")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "metadata_id", nullable = false)
    private Metadata metadata;

//    @Column(name = "metadata_title", nullable = false)
//    private String metadataTitle;
}
