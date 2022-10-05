package com.getir.job.bstm.stats.mapper;

import com.getir.job.bstm.stats.model.Stats;
import com.getir.job.bstm.stats.rest.response.StatsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StatsMapper {

    public abstract StatsResponse entityToResponse (Stats stats);
}
