package br.com.legalmanager.devProject.repository;

import br.com.legalmanager.devProject.entity.Cliente;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND c.status = :status ORDER BY c.nome")
    List<Cliente> findByNomeContainingAndStatus(String nome, Character status);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.cpf) LIKE LOWER(CONCAT('%', :cpf, '%')) AND c.status = :status ORDER BY c.nome")
    List<Cliente> findByCpfContainingAndStatus(String cpf, Character status);
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND LOWER(c.cpf) LIKE LOWER(CONCAT('%', :cpf, '%')) ORDER BY c.nome")
    List<Cliente> findByNomeContainingAndCpfContaining(String nome, String cpf);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND " +
            "LOWER(c.cpf) LIKE LOWER(CONCAT('%', :cpf, '%')) AND c.status = :status ORDER BY c.nome ASC")
    List<Cliente> findByNomeContainingAndCpfContainingAndStatus(String nome, String cpf, Character status);
    List<Cliente> findByStatus(Character status, Sort sort);


}
