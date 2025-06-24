package tcatelie.microservice.auth.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageFilter {
  private int page;
  private int size;
  private String sortBy;
  private Sort.Direction direction = Sort.Direction.ASC;

  public Pageable toPageable() {
    return PageRequest.of(page, size, Sort.by(direction, sortBy));
  }
}
