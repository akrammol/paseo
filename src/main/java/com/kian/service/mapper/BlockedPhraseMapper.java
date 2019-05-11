package com.kian.service.mapper;

import com.kian.domain.*;
import com.kian.service.dto.BlockedPhraseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BlockedPhrase} and its DTO {@link BlockedPhraseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlockedPhraseMapper extends EntityMapper<BlockedPhraseDTO, BlockedPhrase> {



    default BlockedPhrase fromId(Long id) {
        if (id == null) {
            return null;
        }
        BlockedPhrase blockedPhrase = new BlockedPhrase();
        blockedPhrase.setId(id);
        return blockedPhrase;
    }
}
