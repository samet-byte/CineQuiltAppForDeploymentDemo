package com.sametb.cinequiltapp.metadata.paging_sorting;

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.RelationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Samet Bayat.
 * Date: 18.12.2023 8:19 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Service
@RequiredArgsConstructor
public class MetadataPagingSortingService implements IMetadataPagingSortingService {

    private final MetadataPagingSortingRepository metadataPSRepository;

    @Override
    public Page<Metadata> getAllMetadata(Pageable pageable) {
        return metadataPSRepository.findAll(pageable);
    }

    @Override
    public Page<Metadata> getAllMetadataByType(RelationType type, Pageable pageable) {
        return metadataPSRepository.getAllByType(type, pageable);
    }

}
