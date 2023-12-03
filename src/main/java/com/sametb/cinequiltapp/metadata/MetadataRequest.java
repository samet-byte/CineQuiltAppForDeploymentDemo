package com.sametb.cinequiltapp.metadata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MetadataRequest {

    private Integer id;
    private String title;
    private String director;
    private int releaseYear;
    private int duration;
    private String posterUrl;
    private String videoUrl;
    private String trailerUrl;
    private String soundtrackUrl;


}
