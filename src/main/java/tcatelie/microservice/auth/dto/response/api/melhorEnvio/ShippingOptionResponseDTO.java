package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "ShippingOptionDTO", description = "Opção de envio")
public class ShippingOptionResponseDTO {

    @Schema(name = "id", description = "Identificador")
    private int id;

    @Schema(name = "name", description = "Nome")
    private String name;

    @Schema(name = "price", description = "Preço")
    private BigDecimal price;

    @Schema(name = "customPrice", description = "Preço personalizado")
    private BigDecimal customPrice;

    @Schema(name = "discount", description = "Desconto")
    private BigDecimal discount;

    @Schema(name = "currency", description = "Moeda")
    private String currency;

    @Schema(name = "deliveryTime", description = "Tempo de entrega")
    private int deliveryTime;

    @Schema(name = "deliveryRange", description = "Faixa de entrega")
    private DeliveryRangeResponseDTO deliveryRange;

    @Schema(name = "customDeliveryTime", description = "Tempo de entrega personalizado")
    private int customDeliveryTime;

    @Schema(name = "customDeliveryRange", description = "Faixa de entrega personalizada")
    private DeliveryRangeResponseDTO customDeliveryRange;

    @Schema(name = "pacotes", description = "Pacotes")
    private PackageResponseDTO[] packages;

    @Schema(name = "additionalServices", description = "Serviços adicionais")
    private AdditionalServicesResponseDTO additionalServices;

    @Schema(name = "additional", description = "Adicional")
    private AdditionalResponseDTO additional;

    @Schema(name = "company", description = "Empresa de transporte")
    private ShippingCompanyResponseDTO company;

    @Schema(name = "error", description = "Erro")
    private String error;

}