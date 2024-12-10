package tcatelie.microservice.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.MaterialProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExcelService.class);
    private final ProdutoService produtoService;

    public void gerarArquivoPedidosExcel(HttpServletResponse response, List<PedidoResponseDTO> pedidos) {
        LOGGER.info("Gerando arquivo Excel de pedidos formatados");
        Workbook workbook = new XSSFWorkbook();

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        for (PedidoResponseDTO pedido : pedidos) {
            Sheet sheet = workbook.createSheet("Pedido " + pedido.getId());

            createPedidoHeader(sheet, headerStyle);
            fillPedidoData(sheet, pedido, dataStyle);

            int currentRow = 3;
            createItensHeader(sheet, currentRow++, headerStyle);

            for (ItemPedidoResponseDTO item : pedido.getItens()) {
                currentRow = fillItemData(sheet, item, currentRow, dataStyle, headerStyle);
            }

            List<ProdutoResponseDTO> produtos = pedido.getItens().stream().map(ItemPedidoResponseDTO::getProduto).map(p -> produtoService.buscarProdutoPorId(p.getId())).toList();

            currentRow++;

            //createMateriaisHeader(sheet, currentRow++, headerStyle, produtos);

            autoSizeColumns(sheet, 12);
        }

        writeWorkbookToResponse(response, workbook);
    }

    private void fillMaterialData(Sheet sheet, List<ProdutoResponseDTO> produtos, CellStyle dataStyle, int rowIndex) {
        Row materialRow = sheet.createRow(rowIndex);
        Set<MaterialProdutoResponseDTO> materiais = produtos.stream().map(ProdutoResponseDTO::getMateriais).collect(HashSet::new, Set::addAll, Set::addAll);

        for (MaterialProdutoResponseDTO material : materiais) {
            materialRow.createCell(0).setCellValue(material.getIdMaterial());
            materialRow.createCell(1).setCellValue(material.getNomeMaterial());
            materialRow.createCell(2).setCellValue(material.getQtdParaProducao());
            materialRow.createCell(3).setCellValue(material.getCustoProducao());
            materialRow.createCell(4).setCellValue(material.getPrecoUnitario());
        }
    }

    private void createPedidoHeader(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Cliente", "Status", "Valor Total", "Desconto", "Frete", "Data do Pedido", "Data Entrega", "Data Pagamento", "Data Cancelamento", "Código Rastreio", "Observação"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void fillPedidoData(Sheet sheet, PedidoResponseDTO pedido, CellStyle dataStyle) {
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(pedido != null ? pedido.getId() : 0);
        dataRow.createCell(1).setCellValue(pedido != null && pedido.getCliente() != null ? pedido.getCliente().getNome() : "");
        dataRow.createCell(2).setCellValue(pedido != null ? pedido.getStatus() : "");
        dataRow.createCell(3).setCellValue(pedido != null && pedido.getValorTotal() != null ? pedido.getValorTotal() : 0.0);
        dataRow.createCell(4).setCellValue(pedido != null && pedido.getValorDesconto() != null ? pedido.getValorDesconto() : 0.0);
        dataRow.createCell(5).setCellValue(pedido != null && pedido.getValorFrete() != null ? pedido.getValorFrete() : 0.0);
        dataRow.createCell(6).setCellValue(pedido != null ? pedido.getDataPedido() : "");
        dataRow.createCell(7).setCellValue(pedido != null ? pedido.getDataEntrega() : "");
        dataRow.createCell(8).setCellValue(pedido != null ? pedido.getDataPagamento() : "");
        dataRow.createCell(9).setCellValue(pedido != null ? pedido.getDataCancelamento() : "");
        dataRow.createCell(10).setCellValue(pedido != null ? pedido.getCodigoRastreio() : "");
        dataRow.createCell(11).setCellValue(pedido != null ? pedido.getObservacao() : "");

        for (Cell cell : dataRow) {
            cell.setCellStyle(dataStyle);
        }
    }

    private void createItensHeader(Sheet sheet, int rowIndex, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(rowIndex);
        String[] columns = {"Item ID", "Produto", "Quantidade", "Preço Unitário", "Subtotal"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private int fillItemData(Sheet sheet, ItemPedidoResponseDTO item, int rowIndex, CellStyle dataStyle, CellStyle headerStyle) {
        Row itemRow = sheet.createRow(rowIndex++);
        itemRow.createCell(0).setCellValue(item.getId());
        itemRow.createCell(1).setCellValue(item.getProduto().getNome());
        itemRow.createCell(2).setCellValue(item.getQuantidade());
        itemRow.createCell(3).setCellValue(item.getValor());
        itemRow.createCell(4).setCellValue(item.getValorTotal());

        for (Cell cell : itemRow) {
            cell.setCellStyle(dataStyle);
        }

        return rowIndex;
    }

    private void createMateriaisHeader(Sheet sheet, int rowIndex, CellStyle headerStyle, List<ProdutoResponseDTO> produtos) {
        Row productRow = sheet.createRow(rowIndex);
        Row subColumnRow = sheet.createRow(rowIndex + 1);
        String[] materialColumns = {"Material ID", "Nome", "Quantidade", "Custo Produção", "Preço Unitário"};
        String[] subColumns = {"Unid.", "R$ Total"};

        int index = 0;
        for (ProdutoResponseDTO produto : produtos) {
            Cell productCell = productRow.createCell(index);
            productCell.setCellValue(produto.getNome());
            productCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, index, index + 1));

            for (String sub : subColumns) {
                Cell subCell = subColumnRow.createCell(index++);
                subCell.setCellValue(sub);
                subCell.setCellStyle(headerStyle);
            }
        }

        for (String column : materialColumns) {
            Cell materialCell = subColumnRow.createCell(index);
            materialCell.setCellValue(column);
            materialCell.setCellStyle(headerStyle);
            index++;
        }
    }


    private void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void writeWorkbookToResponse(HttpServletResponse response, Workbook workbook) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=pedidos_formatados.xlsx");
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
