package br.com.legalmanager.devProject.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cliente SET deletado = true WHERE id=?")
@Where(clause = "deletado = false")
public class Cliente extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Campo nome obrigatório")
    private String nome;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Campo CPF obrigatório")
    @Size(min = 14, max = 14)
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF Deve corresponder ao formato 000.000.000-00")
    @Valid
    private String cpf;
    @Email
    private String email;
    private String telefone;
    private String endereco;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro = LocalDate.now();
    private char status = 'A';
    private boolean deletado = Boolean.FALSE;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataUltimaAlteracao;

    public Cliente(String nome, String cpf, String email, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco= endereco;
    }
}