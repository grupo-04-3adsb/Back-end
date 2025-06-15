package tcatelie.microservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.service.AzureBlobStorageService;

@RestController
@RequestMapping("/arquivos")
@RequiredArgsConstructor
public class ArquivoController {

  private final AzureBlobStorageService azureBlobStorageService;
  private final PedidoRepository pedidoRepository;

  @PostMapping("/nota-fiscal/{idPedido}")
  public ResponseEntity salvarNotaFiscal(@PathVariable Integer idPedido,
                                         @RequestParam MultipartFile file){
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Arquivo vazio");
    }
    if(pedidoRepository.existsById(idPedido)){
      String virtualPath = azureBlobStorageService.uploadPdf(file, file.getOriginalFilename());
      Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);

      if (pedido != null) {
        pedido.setNotaFiscalUrl(virtualPath);
        pedidoRepository.save(pedido);
      }

      return ResponseEntity.ok("Arquivo salvo com sucesso no pedido: " + idPedido + ", URL: " + virtualPath);
    } else{
      return ResponseEntity.badRequest().body("Pedido não encontrado");
    }
  }
}
