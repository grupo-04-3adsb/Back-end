package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

    public void gerarRelatorioPedidos(List<PedidoResponseDTO> pedidos, OutputStream outputStream){

        Workbook workbook = new HSSFWorkbook();

        for(PedidoResponseDTO pedido : pedidos){
            String sheetName = "Pedido " + pedido.getId() + " - " + pedido.getCliente().getNome();
            Sheet sheet = workbook.createSheet(sheetName);

            createHeaderRow(sheet);

            fillPedidoData(sheet, pedido);
        }

        try {
            workbook.write(outputStream);
            LOGGER.info("Relatório gerado com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "ID", "Valor Total", "Valor Desconto", "Valor Frete", "Parcelas", "Forma de Pagamento", "Status",
                "Data Pedido", "Data Entrega", "Cliente", "Endereço Entrega"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

            CellStyle style = sheet.getWorkbook().createCellStyle();
            Font font = sheet.getWorkbook().createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }
    }

    private void fillPedidoData(Sheet sheet, PedidoResponseDTO pedido) {
        Row dataRow = sheet.createRow(1);

        dataRow.createCell(0).setCellValue(pedido.getId());
        dataRow.createCell(1).setCellValue(pedido.getValorTotal());
        dataRow.createCell(2).setCellValue(pedido.getValorDesconto());
        dataRow.createCell(3).setCellValue(pedido.getValorFrete());
        dataRow.createCell(4).setCellValue(pedido.getParcelas());
        dataRow.createCell(5).setCellValue(pedido.getFormaPgto());
        dataRow.createCell(6).setCellValue(pedido.getStatus());
        dataRow.createCell(7).setCellValue(pedido.getDataPedido());
        dataRow.createCell(8).setCellValue(pedido.getDataEntrega());

        if (pedido.getCliente() != null) {
            dataRow.createCell(9).setCellValue(pedido.getCliente().getNome());
        }

        if (pedido.getEnderecoEntrega() != null) {
            dataRow.createCell(10).setCellValue(
                    pedido.getEnderecoEntrega().getRua() + ", " +
                            pedido.getEnderecoEntrega().getNumero()
            );
        }

        if (pedido.getItens() != null) {
            int rowIndex = 2;

            for (ItemPedidoResponseDTO item : pedido.getItens()) {
                Row itemRow = sheet.createRow(rowIndex++);
                itemRow.createCell(0).setCellValue("Item " + item.getId());
                itemRow.createCell(1).setCellValue(item.getQuantidade());
                itemRow.createCell(2).setCellValue(item.getValor());
                itemRow.createCell(3).setCellValue(item.getValorTotal());
                itemRow.createCell(4).setCellValue(item.getProduto() != null ? item.getProduto().getNome() : "");
            }
        }
    }
}
