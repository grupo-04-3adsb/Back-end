package tcatelie.microservice.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.request.ItemPedidoRequestDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
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
    private CustosOutrosRepository custosOutrosRepository;

    private Usuario usuarioMock;
    private Pedido pedidoMock;
    private Produto produtoMock;
    private ItemPedido itemPedidoMock;
    private Endereco enderecoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1);

        enderecoMock = new Endereco();
        enderecoMock.setEnderecoPadrao(true);
        enderecoMock.setUsuario(usuarioMock);

        pedidoMock = new Pedido();
        pedidoMock.setId(1);
        pedidoMock.setStatus(StatusPedido.CARRINHO);
        pedidoMock.setUsuario(usuarioMock);
        pedidoMock.setEnderecoEntrega(
                enderecoMock
        );

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
    void testAdicionarAoCarrinhoQuandoCarrinhoNaoExisteComNovoPedido() {
        Integer idCliente = 1;
        ItemPedidoRequestDTO requestDTO = new ItemPedidoRequestDTO();

        requestDTO.setFkProduto(1);
        requestDTO.setQuantidade(2);

        Endereco enderecoPadrao = new Endereco();
        enderecoPadrao.setEnderecoPadrao(true);

        usuarioMock.setEnderecos(Collections.singletonList(enderecoPadrao));

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(usuarioMock));
        when(pedidoRepository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idCliente))
                .thenReturn(Optional.empty());
        when(produtoRepository.findById(1))
                .thenReturn(Optional.of(produtoMock));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setId(1);
            return pedido;
        });
        when(pedidoRepository.findById(1))
                .thenReturn(Optional.of(pedidoMock));
        when(itemPedidoRepository.save(any(ItemPedido.class)))
                .thenReturn(itemPedidoMock);

        service.adicionarAoCarrinho(idCliente, requestDTO);

        verify(pedidoRepository, times(2)).save(any(Pedido.class));
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));

        assertNotNull(pedidoMock.getId(), "O ID do pedido não deveria ser nulo.");
        assertEquals(StatusPedido.CARRINHO, pedidoMock.getStatus(), "O status do pedido deveria ser CARRINHO.");
        assertEquals(usuarioMock, pedidoMock.getUsuario(), "O usuário associado ao pedido deveria ser o mesmo.");

        assertNotNull(pedidoMock.getEnderecoEntrega(), "O pedido deveria ter um endereço de entrega.");
        assertTrue(pedidoMock.getEnderecoEntrega().isEnderecoPadrao(), "O endereço de entrega deveria ser o padrão.");

        ArgumentCaptor<ItemPedido> itemCaptor = ArgumentCaptor.forClass(ItemPedido.class);
        verify(itemPedidoRepository).save(itemCaptor.capture());
        ItemPedido savedItem = itemCaptor.getValue();
        assertNotNull(savedItem, "O item do pedido salvo não deveria ser nulo.");
        assertEquals(produtoMock, savedItem.getProduto(), "O produto do item deveria ser o mesmo mockado.");
        assertEquals(requestDTO.getQuantidade(), savedItem.getQuantidade(), "A quantidade do item deveria corresponder ao valor solicitado.");
    }



    @Test
    void testAdicionarAoCarrinhoUsuarioNaoEncontrado() {
        Integer idCliente = 1;
        ItemPedidoRequestDTO requestDTO = new ItemPedidoRequestDTO();

        when(userRepository.findById(idCliente)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> service.adicionarAoCarrinho(idCliente, requestDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário não encontrado", exception.getReason());
    }

    @Test
    void testAdicionarAoCarrinhoProdutoNaoEncontrado() {
        Integer idCliente = 1;
        ItemPedidoRequestDTO requestDTO = new ItemPedidoRequestDTO();
        requestDTO.setFkProduto(999);

        when(userRepository.findById(idCliente)).thenReturn(Optional.of(usuarioMock));
        when(pedidoRepository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idCliente))
                .thenReturn(Optional.of(pedidoMock));
        when(produtoRepository.findById(999)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> service.adicionarAoCarrinho(idCliente, requestDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Produto não encontrado", exception.getReason());
    }

    @Test
    void testFinalizarItemPedido() {
        Integer idItemPedido = 1;

        CustoOutros custoExtra = new CustoOutros();
        custoExtra.setValor(10.0);

        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.of(itemPedidoMock));
        when(custosOutrosRepository.findAll()).thenReturn(List.of(custoExtra));

        service.finalizarItemPedido(idItemPedido);

        assertTrue(itemPedidoMock.getProdutoFeito());
        assertNotNull(itemPedidoMock.getCustoProducao());
        verify(itemPedidoRepository, times(1)).save(itemPedidoMock);
    }

    @Test
    void testFinalizarItemPedidoNaoEncontrado() {
        Integer idItemPedido = 1;

        when(itemPedidoRepository.findById(idItemPedido)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> service.finalizarItemPedido(idItemPedido));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item pedido não encontrado", exception.getReason());
    }
}
