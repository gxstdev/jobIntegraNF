package org.jobIntegraNf.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA entre {@link StatusNF} e seu código numérico persistido.
 */
@Converter(autoApply = false)
public class StatusNFConverter implements AttributeConverter<StatusNF, Long> {

    @Override
    /** {@inheritDoc} */
    public Long convertToDatabaseColumn(StatusNF attribute) {
        return attribute == null ? null : attribute.getCodigoStatus();
    }

    @Override
    /** {@inheritDoc} */
    public StatusNF convertToEntityAttribute(Long dbData) {
        if (dbData == null) return null;
        for (StatusNF s : StatusNF.values()) {
            if (s.getCodigoStatus().equals(dbData)) return s;
        }
        throw new IllegalArgumentException("Código de StatusNF desconhecido: " + dbData);
    }
}
