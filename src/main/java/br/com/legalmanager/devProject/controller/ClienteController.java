package br.com.legalmanager.devProject.controller;

import br.com.legalmanager.devProject.exception.CPFAlreadyExistsException;
import br.com.legalmanager.devProject.exception.CustomDataIntegrityViolationException;
import br.com.legalmanager.devProject.entity.Cliente;
import br.com.legalmanager.devProject.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/")
    public String index(Model model, Character status) {
        model.addAttribute("clientes", clienteService.getClientes());
        return "index";
    }
    @GetMapping("/buscarClientes")
    public String buscarClientesPorFiltro(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Character status,
            Model model) {
        List<Cliente> clientes = clienteService.getClientesPorCriterios(nome, cpf, status);
        model.addAttribute("clientes", clientes);
        model.addAttribute("statusSelecionado", status != null ? status.toString() : "");
        return "index";
    }
    @GetMapping("/cadastro")
    public String cadastro(@ModelAttribute("cliente") Cliente cliente) {
        return "cadastro";
    }
    @GetMapping("/atualiza/{id}")
    public String getAtualiza(Model model,@PathVariable Long id, Character status) {
        model.addAttribute("cliente", clienteService.getClienteById(id));
        model.addAttribute("statusSelecionado", status != null ? status.toString() : "");
        return "atualiza";
    }
    @PostMapping("/cadastro")
    public String addCliente(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if(bindingResult.hasErrors()) {
            return "/cadastro";
        }
        try {
            clienteService.addCliente(cliente);
            redirectAttributes.addFlashAttribute("cadastroSucesso", true);
        } catch (CPFAlreadyExistsException e) {
            model.addAttribute("cpfError", e.getMessage());
            return "/cadastro";
        } catch (CustomDataIntegrityViolationException e) {
            return "redirect:/error";
        }
        return "redirect:/";
    }
    @PostMapping("/atualiza/{id}")
    public String atualizaCliente(@Valid @ModelAttribute ("cliente") Cliente cliente, @PathVariable Long id,
                                  RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/atualiza";
        }
        try {
            clienteService.atualizaCliente(cliente);
            redirectAttributes.addFlashAttribute("sucesso", true);
        } catch (CPFAlreadyExistsException e) {
            model.addAttribute("cpfError", e.getMessage());
            return "/atualiza";
        } catch (CustomDataIntegrityViolationException e) {
            return "redirect:/error";
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletaCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clienteService.deleteCliente(id);
        redirectAttributes.addFlashAttribute("exclusaoSucesso", true);
        return "redirect:/";
    }
}
