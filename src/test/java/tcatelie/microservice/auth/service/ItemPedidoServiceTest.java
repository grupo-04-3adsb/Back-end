package tcatelie.microservice.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.request.ItemPedidoRequestDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemPedidoServiceTest {

    @InjectMocks
    private ItemPedidoService service;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PersonalizacaoRepository personalizacaoRepository;

    @Mock
    private OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;

    @Mock
    private PersonalizacaoItemPedidoRepository personalizacaoItemPedidoRepository;

    @Mock
    private CustosOutrosRepository custosOutrosRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    private Usuario usuarioMock;
    private Pedido pedidoMock;
    private Produto produtoMock;
    private ItemPedido itemPedidoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1);

        pedidoMock = new Pedido();
        pedidoMock.setId(1);
        pedidoMock.setStatus(StatusPedido.CARRINHO);
        pedidoMock.setUsuario(usuarioMock);

        produtoMock = new Produto();
        produtoMock.setId(1);
        produtoMock.setPreco(100.0);
        produtoMock.setDesconto(10.0);

        itemPedidoMock = new ItemPedido();
        itemPedidoMock.setId(1);
        itemPedidoMock.setProduto(produtoMock);
        itemPedidoMock.setPedido(pedidoMock);
        itemPedidoMock.setQuantidade(2);
    }

    @Test
    void testAdicionarAoCarrinhoComPersonalizacoes() {
        Integer idCliente = 1;
        ItemPedidoRequestDTO requestDTO = new ItemPedidoRequestDTO();
        requestDTO.setFkProduto(1);
        requestDTO.setQuantidade(2);

        Personalizacao personalizacao = new Personalizacao();
        personalizacao.setIdPersonalizacao(1);

        OpcaoPersonalizacao opcaoPersonalizacao = new OpcaoPersonalizacao();
        opcaoPersonalizacao.setIdOpcaoPersonalizacao(1);
        opcaoPersonalizacao.setAcrescimoOpcao(20.0);

        when(pedidoRepository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idCliente))
                .thenReturn(Optional.of(pedidoMock));
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoMock));
        when(userRepository.findById(idCliente)).thenReturn(Optional.of(usuarioMock));
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produtoMock));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedidoMock);
        when(personalizacaoRepository.findById(anyInt())).thenReturn(Optional.of(personalizacao));
        when(opcaoPersonalizacaoRepository.findById(anyInt())).thenReturn(Optional.of(opcaoPersonalizacao));

        service.adicionarAoCarrinho(idCliente, requestDTO);

        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
        verify(personalizacaoItemPedidoRepository, times(0)).save(any());
    }

    @Test
    void testConcluirItemPedidoNaoEncontrado() {
        Integer idItemPedido = 1;

        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                service.concluirItemPedido(idItemPedido));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item pedido não encontrado", exception.getReason());
    }

    @Test
    void testAtualizarItemPedidoPagamentoAprovado() {
        produtoMock.setPreco(100.0);
        produtoMock.setDesconto(10.0);

        pedidoMock.setStatus(StatusPedido.CARRINHO);

        itemPedidoMock.setProduto(produtoMock);
        itemPedidoMock.setQuantidade(2);
        itemPedidoMock.setValorFrete(10.0);
        itemPedidoMock.setPedido(pedidoMock);
        itemPedidoMock.setPersonalizacoes(List.of());

        when(itemPedidoRepository.findById(1)).thenReturn(Optional.of(itemPedidoMock));
        when(produtoRepository.findById(produtoMock.getId())).thenReturn(Optional.of(produtoMock));

        service.atualizarItemPedidoPagamentoAprovado(itemPedidoMock);

        assertEquals(20.0, itemPedidoMock.getValorDesconto());
        assertEquals(220.0, itemPedidoMock.getValor());
        assertEquals(200.0, itemPedidoMock.getValorTotal());
    }

    @Test
    void testAdicionarAoCarrinhoPedidoInexistente() {
        Integer idCliente = 1;
        ItemPedidoRequestDTO requestDTO = new ItemPedidoRequestDTO();
        requestDTO.setFkProduto(1);
        requestDTO.setQuantidade(1);

        Endereco enderecoMock = new Endereco();
        enderecoMock.setId(1);
        enderecoMock.setEnderecoPadrao(true);

        usuarioMock.setIdUsuario(idCliente);
        usuarioMock.setEnderecos(Collections.singletonList(enderecoMock));

        pedidoMock.setId(1);
        pedidoMock.setStatus(StatusPedido.CARRINHO);
        pedidoMock.setUsuario(usuarioMock);
        pedidoMock.setEnderecoEntrega(enderecoMock);

        produtoMock.setId(1);
        produtoMock.setPreco(100.0);

        when(pedidoRepository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idCliente))
                .thenReturn(Optional.empty());
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedidoMock));
        when(userRepository.findById(idCliente)).thenReturn(Optional.of(usuarioMock));
        when(produtoRepository.findById(1)).thenReturn(Optional.of(produtoMock));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoMock);
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedidoMock);

        service.adicionarAoCarrinho(idCliente, requestDTO);

        verify(pedidoRepository, times(2)).save(any(Pedido.class));
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
    }


    @Test
    void testFinalizarItemPedido() {
        itemPedidoMock.setProdutoFeito(false);

        when(itemPedidoRepository.findById(1)).thenReturn(Optional.of(itemPedidoMock));
        when(custosOutrosRepository.findAll()).thenReturn(List.of());

        service.finalizarItemPedido(1);

        assertTrue(itemPedidoMock.getProdutoFeito());
        verify(itemPedidoRepository, times(1)).save(itemPedidoMock);
    }

    @Test
    void testTransformarItemPedidoResponseDTO() {
        pedidoMock.setStatus(StatusPedido.CONCLUIDO);
        itemPedidoMock.setPedido(pedidoMock);

        when(produtoMapper.toResponseDTO(produtoMock)).thenReturn(new ProdutoResponseDTO());

        var responseDTO = service.transformarItemPedidoResponseDTO(itemPedidoMock);

        assertNotNull(responseDTO);
        assertEquals(itemPedidoMock.getId(), responseDTO.getId());
        assertTrue(responseDTO.getFeito());
        assertEquals(StatusPedido.CONCLUIDO, pedidoMock.getStatus());
    }
}
