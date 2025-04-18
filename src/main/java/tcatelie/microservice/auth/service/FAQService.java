package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.response.FAQResponseDTO;
import tcatelie.microservice.auth.mapper.FAQMapper;
import tcatelie.microservice.auth.repository.FAQRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQService {

  private final FAQRepository faqRepository;

  public List<FAQResponseDTO> findAll() {
    return faqRepository.findAll().stream().map(faq ->
            FAQMapper.INSTANCE.toResponseDTO(faq)).toList();
  }
}
