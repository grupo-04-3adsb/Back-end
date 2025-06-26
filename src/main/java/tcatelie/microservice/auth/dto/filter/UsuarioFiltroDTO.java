package tcatelie.microservice.auth.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import tcatelie.microservice.auth.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFiltroDTO extends PageFilter {
  private String nome;
  private String email;
  private String cpf;
  private String telefone;
  private LocalDateTime dataCadastroInicial;
  private LocalDateTime dataCadastroFinal;
  private List<UserRole> roles;
  private Boolean ativo;

}
