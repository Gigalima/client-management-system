package br.com.legalmanager.devProject.controller;

import br.com.legalmanager.devProject.entity.Cliente;
import br.com.legalmanager.devProject.exception.CPFAlreadyExistsException;
import br.com.legalmanager.devProject.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;
    @InjectMocks
    private ClienteController clienteController;

    @Test
    public void testIndex() {
        Model model = mock(Model.class);

        List<Cliente> clientes = new ArrayList<>();

        when(clienteService.getClientes()).thenReturn(clientes);
        String viewName = clienteController.index(model, 'A');
        verify(model).addAttribute("clientes", clientes);
        assertEquals("index", viewName);
    }
    @Test
    public void testBuscarClientesPorFiltro() {
        String nome = "Douglas";
        String cpf = "147.258.369-10";
        Character status = 'A';
        Model model = mock(Model.class);

        List<Cliente> clientes = new ArrayList<>();

        when(clienteService.getClientesPorCriterios(nome, cpf, status)).thenReturn(clientes);
        String viewName = clienteController.buscarClientesPorFiltro(nome, cpf, status, model);
        verify(model).addAttribute("clientes", clientes);
        verify(model).addAttribute("statusSelecionado", status != null ? status.toString() : "");
        assertEquals("index", viewName);
    }
    @Test
    public void testAddClienteSucesso() {
        Cliente cliente = new Cliente("Saitama Dev", "999.999.999-99", "saitama@onepunch.com", "(99) 99999-9999", "Rua dos Monstros");

        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = clienteController.addCliente(cliente, bindingResult, redirectAttributes, model);
        assertEquals("redirect:/", viewName);
    }
    @Test
    public void testAddClienteError() {
        Cliente cliente = new Cliente();

        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = clienteController.addCliente(cliente, bindingResult, redirectAttributes, model);
        assertEquals("/cadastro", viewName);
    }
    @Test
    public void testAddClienteCPFAlreadyExistsException() throws CPFAlreadyExistsException {
        Cliente cliente = new Cliente("Saitama Dev", "999.999.999-99", "saitama@onepunch.com", "(99) 99999-9999", "Rua dos Monstros");
        CPFAlreadyExistsException exception = new CPFAlreadyExistsException("CPF já cadastrado", new Exception());

        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(exception).when(clienteService).addCliente(cliente);
        String viewName = clienteController.addCliente(cliente, bindingResult, redirectAttributes, model);
        assertEquals("/cadastro", viewName);
    }
    @Test
    public void testAtualizaClienteSuccesso() {
        Cliente cliente = new Cliente("Genos Cyborg", "777.777.666-66", "genos@onepunch.com", "(99) 77777-7777", "Rua dos Monstros");

        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = clienteController.atualizaCliente(cliente, 1L, redirectAttributes, bindingResult, model);
        assertEquals("redirect:/", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("sucesso", true);
    }
    @Test
    public void testAtualizaClienteValidationError() {
        Cliente cliente = new Cliente();

        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = clienteController.atualizaCliente(cliente, 1L, redirectAttributes, bindingResult, model);
        assertEquals("/atualiza", viewName);
    }
    @Test
    public void testAtualizaCliente_CPFAlreadyExistsException() throws CPFAlreadyExistsException {
        Cliente cliente = new Cliente("Rock Balboa", "456.654.753-10", "stallone@rambo.com", "(79) 98745-5252", "Rua 1, Filadélfia");
        CPFAlreadyExistsException exception = new CPFAlreadyExistsException("CPF já cadastrado", new Exception());

        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(exception).when(clienteService).atualizaCliente(cliente);
        String viewName = clienteController.atualizaCliente(cliente, 1L, redirectAttributes, bindingResult, model);
        assertEquals("/atualiza", viewName);
    }
    @Test
    public void testeDeleteCliente() {
        Long id = 1L;

        String viewName = clienteController.deletaCliente(id, redirectAttributes);
        assertEquals("redirect:/", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute("exclusaoSucesso", true);
    }
}
