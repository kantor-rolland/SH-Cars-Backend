package edu.bbte.idde.krim2244.mapper;

import edu.bbte.idde.krim2244.dataaccess.model.Extra;
import edu.bbte.idde.krim2244.dto.extra.ExtraDetailsDTO;
import edu.bbte.idde.krim2244.dto.extra.ExtraInDTO;
import edu.bbte.idde.krim2244.dto.extra.ExtraOverviewDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
// import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ExtraMapper {

    // ExtraMapper INSTANCE = Mappers.getMapper(ExtraMapper.class);

    @IterableMapping(elementTargetType = ExtraOverviewDTO.class)
    public abstract List<ExtraOverviewDTO> toExtraOverviewDTOList(List<Extra> extras);

    public abstract ExtraOverviewDTO toExtraOverviewDTO(Extra extra);

    public abstract List<ExtraDetailsDTO> toExtraDetailsDTOList(List<Extra> extras);

    public abstract ExtraDetailsDTO toExtraDetailsDTO(Extra extra);

    public abstract Extra toExtra(ExtraInDTO extraInDTO);

    public abstract Extra detailsToExtra(ExtraDetailsDTO extraDetailsDTO);
}
