package tcatelie.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.service.AzureBlobStorageService;
import tcatelie.microservice.auth.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/arquivos")
@RequiredArgsConstructor
public class ArquivoController {

  private final AzureBlobStorageService azureBlobStorageService;
  private final PedidoRepository pedidoRepository;
  private final EmailService emailService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoController.class);

  @PostMapping("/nota-fiscal/{idPedido}")
  public ResponseEntity salvarNotaFiscal(@PathVariable Integer idPedido,
                                         @RequestParam MultipartFile file) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Arquivo vazio");
    }

    String virtualPath = "";

    if (pedidoRepository.existsById(idPedido)) {
      Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);

      if (pedido != null) {
        if (StringUtils.isNotBlank(pedido.getNotaFiscalUrl())) {
          azureBlobStorageService.removeFile(pedido.getNotaFiscalUrl());
        }

        virtualPath = azureBlobStorageService.uploadPdf(file, idPedido + "_" + file.getOriginalFilename());

        pedido.setNotaFiscalUrl(virtualPath);
        pedidoRepository.save(pedido);

        try {
          emailService.sendNotaFiscalEmail(pedido.getUsuario().getEmail(),
                  pedido.getUsuario().getNome(),
                  pedido.getId().toString(),
                  virtualPath, "TCAteliê");
        } catch (Exception e) {
          LOGGER.warn("Erro ao enviar email de nota fiscal: {}", e.getMessage());
        }
      }

      return ResponseEntity.ok("Arquivo salvo com sucesso no pedido: " + idPedido + ", URL: " + virtualPath);
    } else {
      return ResponseEntity.badRequest().body("Pedido não encontrado");
    }
  }

  @GetMapping("/nota-fiscal/{idPedido}")
  public ResponseEntity getNotaFiscalPedido(@PathVariable Integer idPedido) {
    Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);

    if (pedido == null || StringUtils.isBlank(pedido.getNotaFiscalUrl())) {
      Map<String, String> errorResponse = new HashMap();
      errorResponse.put("error", "Nota fiscal não encontrada para o pedido informado.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    return azureBlobStorageService.downloadPdf(pedido.getNotaFiscalUrl());
  }

}
