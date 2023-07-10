package org.management.mapper;


import org.management.domain.Card;
import org.management.dto.CardDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, SenderMapper.class})
public interface CardMapper extends EntityMapper<CardDTO, Card> {


    // convert Entity to Dto
    CardDTO toDto(Card card);

    // convert Dto to Entity
    Card toEntity(CardDTO cardDTO);

}
