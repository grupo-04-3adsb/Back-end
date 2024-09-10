package tcatelie.microservice.auth.strategy;

import tcatelie.microservice.auth.dto.request.PagamentoDto;

import java.util.List;

public class GerenciadorPagamento {

    public static void ordenarVetorPorOrdemAlfabetica(PagamentoDto[] pagamentos){

        for (int i = 0; i < pagamentos.length - 1; i++) {
            for (int j = 1; j < pagamentos.length - i; j++) {
                if (pagamentos[j - 1].getNome().compareTo(pagamentos[j].getNome()) > 0) {
                    //Necessário completar aqui
                    PagamentoDto aux = pagamentos[j-1];
                    pagamentos[j-1] = pagamentos[j];
                    pagamentos[j] = aux;
                }
            }
        }
    }
}
