package tcatelie.microservice.auth.util.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tcatelie.microservice.auth.enums.Genero;

@Converter(autoApply = true)
public class GeneroConverter implements AttributeConverter<Genero, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Genero gender) {
        if (gender == null) {
            return null;
        }
        return gender.getValor();
    }

    @Override
    public Genero convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return Genero.fromValue(value);
    }
}
