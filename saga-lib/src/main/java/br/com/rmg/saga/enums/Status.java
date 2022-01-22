package br.com.rmg.saga.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
	PENDENTE('P'),
	EXECUTADO('E'),
	FALHA('F'),
	EM_COMPENSACAO('C'),
	COMPENSACAO_FINALIZADA('D'),
	FINALIZADO('Z');
	
	@Getter
	private Character value;
	
	@Override
    public String toString() {
        switch (this){
            case PENDENTE:
                return PENDENTE.value.toString();
            case EXECUTADO:
                return EXECUTADO.value.toString();
            case FALHA:
                return FALHA.value.toString();
            case EM_COMPENSACAO:
                return EM_COMPENSACAO.value.toString();
            case COMPENSACAO_FINALIZADA:
            	return COMPENSACAO_FINALIZADA.value.toString();
            case FINALIZADO:
            	return FINALIZADO.value.toString();
            default:
                return "";
        }
    }
}
