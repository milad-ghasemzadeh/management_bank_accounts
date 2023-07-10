package org.management.mapper;

import org.management.domain.Sender;
import org.management.dto.SenderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SenderMapper  extends EntityMapper<SenderDTO, Sender> {

    // convert Entity to Dto
    SenderDTO toDto(Sender sender);

    // convert Dto to Entity
    Sender toEntity(SenderDTO senderDTO);

}
