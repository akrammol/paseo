package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.BlockedPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlockedPerson} and its DTO {@link BlockedPersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface BlockedPersonMapper extends EntityMapper<BlockedPersonDTO, BlockedPerson> {

    @Mapping(source = "pesron.id", target = "pesronId")
    @Mapping(source = "blockedPerson.id", target = "blockedPersonId")
    BlockedPersonDTO toDto(BlockedPerson blockedPerson);

    @Mapping(source = "pesronId", target = "pesron")
    @Mapping(source = "blockedPersonId", target = "blockedPerson")
    BlockedPerson toEntity(BlockedPersonDTO blockedPersonDTO);

    default BlockedPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        BlockedPerson blockedPerson = new BlockedPerson();
        blockedPerson.setId(id);
        return blockedPerson;
    }
}
