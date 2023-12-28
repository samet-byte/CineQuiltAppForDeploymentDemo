package com.sametb.cinequiltapp.metadata.paging_sorting;

import lombok.*;

/**
 * @author Samet Bayat.
 * Date: 19.12.2023 9:53 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Getter
@RequiredArgsConstructor
public enum SortMetadata {
    SORT_BY_TITLE("title"),
    SORT_BY_DIRECTOR("director"),
    SORT_BY_RELEASE_YEAR("releaseYear"),
    SORT_BY_ID("id"),
    SORT_BY_TYPE("type"),
    SORT_BY_DURATION("duration"),
    SORT_BY_GENRE("genre"),
    SORT_BY_CREATE_DATE("createDate");

    private final String value;
}

