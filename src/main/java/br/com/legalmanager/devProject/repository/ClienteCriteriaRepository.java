package br.com.legalmanager.devProject.repository;

import br.com.legalmanager.devProject.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteCriteriaRepository {
    private final EntityManager entityManager;

    public List<Cliente> findByNomeOrCpfOrStatus(String nome, String cpf, Character status) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Cliente.class);

        Root<Cliente> root = criteriaQuery.from(Cliente.class);
        Predicate nomePredicate = criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
        Predicate cpfPredicate = criteriaBuilder.like(root.get("cpf"), "%" + cpf + "%");
        Predicate statusPredicate = criteriaBuilder.equal(root.get("status"),  status);


        Predicate orPredicate = criteriaBuilder.or(nomePredicate, cpfPredicate, statusPredicate);

        criteriaQuery.where(orPredicate).orderBy();
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Cliente> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
