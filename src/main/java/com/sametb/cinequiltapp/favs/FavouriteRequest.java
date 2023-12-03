package com.sametb.cinequiltapp.favs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 5:37 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */
@Getter
@Setter
@Builder
public class FavouriteRequest {
    public Integer userId;
    public Integer metadataId;
}
