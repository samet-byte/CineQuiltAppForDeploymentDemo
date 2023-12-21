package com.sametb.cinequiltapp.metadata.paging_sorting;

import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.RelationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Samet Bayat.
 * Date: 18.12.2023 8:15 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface MetadataPagingSortingRepository extends PagingAndSortingRepository<Metadata, Integer> {


    Page<Metadata> getAllByType(RelationType type, Pageable pageable);





}
