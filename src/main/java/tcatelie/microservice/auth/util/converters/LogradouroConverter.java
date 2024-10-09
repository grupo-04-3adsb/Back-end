package tcatelie.microservice.auth.util.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tcatelie.microservice.auth.enums.Logradouro;

@Converter(autoApply = true)
public class LogradouroConverter implements AttributeConverter<Logradouro, String> {

    @Override
    public String convertToDatabaseColumn(Logradouro logradouro) {
        if (logradouro == null) {
            return null;
        }
        return logradouro.getDescricao();
    }

    @Override
    public Logradouro convertToEntityAttribute(String descricao) {
        if (descricao == null) {
            return null;
        }
        for (Logradouro logradouro : Logradouro.values()) {
            if (logradouro.getDescricao().equalsIgnoreCase(descricao)) {
                return logradouro;
            }
        }
        throw new IllegalArgumentException("Desconhecido: " + descricao);
    }
}
