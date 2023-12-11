package com.sametb.cinequiltapp.metadata;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Samet Bayat.
 * Date: 11.12.2023 2:27 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "film_series")
public class FilmSeries {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
