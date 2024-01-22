package com.sametb.cinequiltapp.metadata.paging_sorting;

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.RelationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMetadataPagingSortingService {
    Page<Metadata> getAllMetadata(Pageable pageable);

    Page<Metadata> getAllMetadataByType(RelationType type, Pageable pageable);
}
