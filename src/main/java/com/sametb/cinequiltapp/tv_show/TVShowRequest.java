package com.sametb.cinequiltapp.tv_show;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:57 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TVShowRequest {

    private Integer metadataId;
    private Integer season;
    private Integer episode;
    private String title;
    private String videoUrl;
    private String description;

}
