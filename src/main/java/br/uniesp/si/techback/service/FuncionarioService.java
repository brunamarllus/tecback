package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.ViaCepClient;
import br.uniesp.si.techback.dto.ViaCepResponseDTO;
import br.uniesp.si.techback.dto.request.FuncionarioRequestDTO;
import br.uniesp.si.techback.exception.CustomBeanException;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final ViaCepClient viaCepClient;

    public List<Funcionario> listar() {
        log.info("Listando funcionários");
        return funcionarioRepository.findAll();
    }

    public Funcionario incluir(FuncionarioRequestDTO dto) {
        log.info("Incluindo funcionário: nome={}", dto.nome());

        Funcionario funcionario = Funcionario.builder()
                .nome(dto.nome())
                .cargo(dto.cargo())
                .cep(dto.cep())
                .build();

        String cepLimpo = dto.cep().replaceAll("\\D", "");
        log.info("Consultando ViaCEP para o cep={}", cepLimpo);
        ViaCepResponseDTO endereco = viaCepClient.buscarPorCep(cepLimpo);

        if (Boolean.TRUE.equals(endereco.getErro())) {
            log.warn("CEP inválido retornado pelo ViaCEP: {}", cepLimpo);
            throw new CustomBeanException("CEP invalido para consulta no ViaCEP");
        }

        funcionario.setCep(endereco.getCep());
        funcionario.setLogradouro(endereco.getLogradouro());
        funcionario.setBairro(endereco.getBairro());
        funcionario.setLocalidade(endereco.getLocalidade());
        funcionario.setUf(endereco.getUf());

        Funcionario salvo = funcionarioRepository.save(funcionario);
        log.info("Funcionário incluído com sucesso: id={}", salvo.getId());
        return salvo;
    }
}
