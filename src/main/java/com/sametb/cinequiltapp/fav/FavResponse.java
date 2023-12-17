package com.sametb.cinequiltapp.fav;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:59 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavResponse {
    private Integer userId;
    private Integer metadataId;
    private String title;
    private String posterUrl;
}
