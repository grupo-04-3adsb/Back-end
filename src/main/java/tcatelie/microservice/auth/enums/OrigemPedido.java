package tcatelie.microservice.auth.enums;

import lombok.Getter;

@Getter
public enum OrigemPedido {
  FACEBOOK(1, "Facebook"),
  INSTAGRAM(2, "Instagram"),
  WHATSAPP(3, "WhatsApp"),
  SITE(4, "Site"),
  TELEGRAM(5, "Telegram"),
  TELEFONE(6, "Telefone"),
  OUTRO(7, "Outro");

  private Integer id;
  private String descricao;

  OrigemPedido(Integer id, String descricao) {
    this.id = id;
    this.descricao = descricao;
  }

  public static OrigemPedido fromId(Integer id) {
    for (OrigemPedido origem : values()) {
      if (origem.getId().equals(id)) {
        return origem;
      }
    }
    throw new IllegalArgumentException("Origem de pedido inválida: " + id);
  }

  public static OrigemPedido fromDescricao(String descricao) {
    for (OrigemPedido origem : values()) {
      if (origem.getDescricao().equalsIgnoreCase(descricao)) {
        return origem;
      }
    }
    throw new IllegalArgumentException("Origem de pedido inválida: " + descricao);
  }

}
