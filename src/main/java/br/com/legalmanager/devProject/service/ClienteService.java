package br.com.legalmanager.devProject.service;

import br.com.legalmanager.devProject.exception.CPFAlreadyExistsException;
import br.com.legalmanager.devProject.exception.CustomDataIntegrityViolationException;
import br.com.legalmanager.devProject.entity.Cliente;
import br.com.legalmanager.devProject.repository.ClienteRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public List<Cliente> getClientes() {
        Sort nomeAsc = Sort.by("nome").ascending();
        return clienteRepository.findAll(nomeAsc);
    }
    public List<Cliente> getClientesPorCriterios(String nome, String cpf, Character status) {
        Sort nomeAsc = Sort.by("nome").ascending();
        if (status == null) {
            return clienteRepository.findByNomeContainingAndCpfContaining(nome, cpf);
        } else {
            if(nome != null && !nome.isEmpty() && cpf != null && !cpf.isEmpty()) {
                return clienteRepository.findByNomeContainingAndCpfContainingAndStatus(nome, cpf, status);
            } else if(nome != null && !nome.isEmpty() && cpf != null && !cpf.isEmpty() && status != null) {
            return clienteRepository.findByNomeContainingAndCpfContaining(nome, cpf);
            } else if (nome != null && !nome.isEmpty() && status != null) {
                return clienteRepository.findByNomeContainingAndStatus(nome, status);
            } else if (cpf != null && !cpf.isEmpty() && status != null) {
                return clienteRepository.findByCpfContainingAndStatus(cpf, status);
            } else if (status != null) {
                return clienteRepository.findByStatus(status, nomeAsc);
            } else {
                return clienteRepository.findAll(nomeAsc);
            }
        }
    }
    public Cliente getClienteById(Long id) {
        return clienteRepository.getReferenceById(id);
    }
    public Page<Cliente> findPagina(int numeroPagina) {
        Pageable pageable = PageRequest.of(numeroPagina - 1, 8, Sort.by("nome").ascending());
        return clienteRepository.findAll(pageable);
    }
    public void addCliente(Cliente cliente) {
        try {
            clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolationException(e);
        }
    }
    public void atualizaCliente(Cliente cliente) {
        try {
            cliente.setDataUltimaAlteracao(LocalDate.now());
            clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolationException(e);
        }
    }
    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }
    private void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
            if (constraintViolationException.getConstraintName().contains("CPF")) {
                throw new CPFAlreadyExistsException("CPF já existe", ex);
            }
        }
        throw new CustomDataIntegrityViolationException("Erro de violação de integridade de dados", ex);
    }
}
