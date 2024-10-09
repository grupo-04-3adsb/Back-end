package tcatelie.microservice.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Logradouro {
    AEROPORTO("Aeroporto", 1),
    ALAMEDA("Alameda", 2),
    AREA("Área", 3),
    AVENIDA("Avenida", 4),
    CAMPO("Campo", 5),
    CHACARA("Chácara", 6),
    COLONIA("Colônia", 7),
    CONDOMINIO("Condomínio", 8),
    CONJUNTO("Conjunto", 9),
    DISTRITO("Distrito", 10),
    ESPLANADA("Esplanada", 11),
    ESTACAO("Estação", 12),
    ESTRADA("Estrada", 13),
    FAVELA("Favela", 14),
    FAZENDA("Fazenda", 15),
    FEIRA("Feira", 16),
    JARDIM("Jardim", 17),
    LADEIRA("Ladeira", 18),
    LAGO("Lago", 19),
    LAGOA("Lagoa", 20),
    LARGO("Largo", 21),
    LOTEAMENTO("Loteamento", 22),
    MORRO("Morro", 23),
    NUCLEO("Núcleo", 24),
    PARQUE("Parque", 25),
    PASSARELA("Passarela", 26),
    PATIO("Pátio", 27),
    PRAÇA("Praça", 28),
    QUADRA("Quadra", 29),
    RECANTO("Recanto", 30),
    RESIDENCIAL("Residencial", 31),
    RODOVIA("Rodovia", 32),
    RUA("Rua", 33),
    SETOR("Setor", 34),
    SITIO("Sítio", 35),
    TRAVESSA("Travessa", 36),
    TRECHO("Trecho", 37),
    TREVO("Trevo", 38),
    VALE("Vale", 39),
    VEREDA("Vereda", 40),
    VIA("Via", 41),
    VIADUTO("Viaduto", 42),
    VIELA("Viela", 43),
    VILA("Vila", 44);

    private final String descricao;
    private final int valor;
}
