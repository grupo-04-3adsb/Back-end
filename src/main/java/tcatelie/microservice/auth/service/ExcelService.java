package tcatelie.microservice.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);

    public void gerarArquivoPedidosExcel(HttpServletResponse response, List<PedidoResponseDTO> pedidos) {
        LOGGER.info("Gerando arquivo Excel de pedidos");
        Workbook workbook = new XSSFWorkbook();

        // Criar estilos personalizados
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        for (PedidoResponseDTO pedido : pedidos) {
            // Criar uma nova aba para cada pedido
            Sheet sheet = workbook.createSheet("Pedido " + pedido.getId());

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Cliente", "Status", "Valor Total", "Desconto", "Frete", "Data do Pedido", "Data Entrega", "Data Pagamento", "Data Cancelamento", "Código Rastreio", "Observação"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Dados do pedido
            Row dataRow = sheet.createRow(1);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = dataRow.createCell(i);
                switch (i) {
                    case 0 -> cell.setCellValue(pedido.getId());
                    case 1 -> cell.setCellValue(pedido.getCliente().getNome());
                    case 2 -> cell.setCellValue(pedido.getStatus());
                    case 3 -> cell.setCellValue(pedido.getValorTotal());
                    case 4 -> cell.setCellValue(pedido.getValorDesconto());
                    case 5 -> cell.setCellValue(pedido.getValorFrete());
                    case 6 -> cell.setCellValue(pedido.getDataPedido());
                    case 7 -> cell.setCellValue(pedido.getDataEntrega());
                    case 8 -> cell.setCellValue(pedido.getDataPagamento());
                    case 9 -> cell.setCellValue(pedido.getDataCancelamento());
                    case 10 -> cell.setCellValue(pedido.getCodigoRastreio());
                    case 11 -> cell.setCellValue(pedido.getObservacao());
                }
                cell.setCellStyle(dataStyle);
            }

            // Adicionar itens relacionados ao pedido
            Row itemsHeaderRow = sheet.createRow(3);
            String[] itemColumns = {"Item ID", "Produto", "Quantidade", "Preço Unitário", "Subtotal"};
            for (int i = 0; i < itemColumns.length; i++) {
                Cell cell = itemsHeaderRow.createCell(i);
                cell.setCellValue(itemColumns[i]);
                cell.setCellStyle(headerStyle);
            }

            int itemRowNum = 4;
            for (ItemPedidoResponseDTO item : pedido.getItens()) {
                Row itemRow = sheet.createRow(itemRowNum++);
                for (int i = 0; i < itemColumns.length; i++) {
                    Cell cell = itemRow.createCell(i);
                    switch (i) {
                        case 0 -> cell.setCellValue(item.getId());
                        case 1 -> cell.setCellValue(item.getProduto().getNome());
                        case 2 -> cell.setCellValue(item.getQuantidade());
                        case 3 -> cell.setCellValue(item.getValor());
                        case 4 -> cell.setCellValue(item.getValorTotal());
                    }
                    cell.setCellStyle(dataStyle);
                }
            }

            // Rodapé com total do pedido
            Row totalRow = sheet.createRow(itemRowNum);
            Cell totalLabelCell = totalRow.createCell(3);
            totalLabelCell.setCellValue("Total:");
            totalLabelCell.setCellStyle(headerStyle);

            Cell totalValueCell = totalRow.createCell(4);
            totalValueCell.setCellValue(pedido.getValorTotal());
            totalValueCell.setCellStyle(dataStyle);

            // Ajustar o tamanho das colunas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }

        // Configurar a resposta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=pedidos.xlsx");
        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            LOGGER.error("Erro ao gerar arquivo Excel.", e);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);

        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.LEFT);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        style.setFont(font);

        return style;
    }
}