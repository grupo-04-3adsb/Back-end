package tcatelie.microservice.auth.util.converters;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tcatelie.microservice.auth.enums.StatusPedido;

@Converter(autoApply = true)
public class StatusPedidoConverter implements AttributeConverter<StatusPedido, String> {

    @Override
    public String convertToDatabaseColumn(StatusPedido status) {
        if (status == null) {
            return null;
        }
        return status.getDescricao();
    }

    @Override
    public StatusPedido convertToEntityAttribute(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            return null;
        }
        return StatusPedido.fromDescricao(descricao); // Converte a descrição do banco para o enum
    }
}
