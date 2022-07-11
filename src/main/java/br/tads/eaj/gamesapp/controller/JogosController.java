package br.tads.eaj.gamesapp.controller;

import br.tads.eaj.gamesapp.domain.Jogos;
import br.tads.eaj.gamesapp.service.FileStorageService;
import br.tads.eaj.gamesapp.service.JogosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Controller
public class JogosController {
    private final JogosService service;
    private final FileStorageService fileStorageService;

    public JogosController(JogosService service, FileStorageService fileStorageService) {
        this.service = service;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/index")
    public String jogos(Model model) {
        List<Jogos> jogos = service.Listall();
        model.addAttribute("jogos", jogos);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<Jogos> jogos = service.Listall();
        model.addAttribute("jogos", jogos);
        return "admin";
    }

    @GetMapping("/cadastrarJogo")
    public String getJogosCadastro(Model model) {
        Jogos j = new Jogos();
        model.addAttribute("jogoscadastrar", j);
        return "cadastro";
    }

    @PostMapping("/salvar")
    public String doSalvaJogo(@ModelAttribute Jogos j, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        j.setImagemurl(file.getOriginalFilename());
        service.alterar(j);
        fileStorageService.save(file);
        redirectAttributes.addAttribute("msgSalvar", "Jogo salvo com sucesso");
        return "redirect:/admin";
    }
    @GetMapping("/addItemCarrinho/{id}")
    public String doAdicionarItem( @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var jogo = service.findById(id);
        Cookie carrinhoCompras = new Cookie("carrinhoCompras1", "");
        carrinhoCompras.setMaxAge(60 * 60 * 24);
        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;
        if (requestCookies != null) {
            for (var c : requestCookies) {
                achouCarrinho = true;
                carrinhoCompras = c;
                break;
            }
        }
        Jogos jogos = null;
        if (jogo != null){
            jogos = jogo;
            if (achouCarrinho == true){
                String value = carrinhoCompras.getValue();
                carrinhoCompras.setValue(value + jogos.getId() + "|");
            }else{
                carrinhoCompras.setValue(jogos.getId() + "|");
            }
        }else {
            response.addCookie(carrinhoCompras);
        }
        response.addCookie(carrinhoCompras);
        return "redirect:/index";
    }
    @GetMapping("/visualizarCarrinho")
    public String visualizarCarrinho(HttpServletRequest request, Model model) throws ServletException, IOException {
        Cookie carrinhoCompras = new Cookie("carrinhoCompras1", "");
        Cookie[] requestCookies = request.getCookies();
        boolean achouCarrinho = false;
        if (requestCookies != null) {
            for (var c : requestCookies) {
                achouCarrinho = true;
                carrinhoCompras = c;
                break;
            }
        }
        Jogos jogo = null;
        var i = 0;
        ArrayList<Jogos> lista_jogos = new ArrayList();
        if(achouCarrinho == true) {
            StringTokenizer tokenizer = new StringTokenizer(carrinhoCompras.getValue(), "|");
            while (tokenizer.hasMoreTokens()) {
                jogo = service.findById(Integer.parseInt(tokenizer.nextToken()));
                lista_jogos.add(jogo);
            }
            model.addAttribute("jogos", lista_jogos);
            return "vercarrinho";

        } else {
            return "redirect:/index";
        }
    }
    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpServletRequest request, HttpServletResponse response){
        Cookie carrinhoCompras = new Cookie("carrinhoCompras1", "");
        response.addCookie(carrinhoCompras);
        return "redirect:/index";
    }
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editarJogos(Model model, @PathVariable Integer id, RedirectAttributes redirectAttributes){
        Jogos jogos = service.findById(id);
        model.addAttribute("jogoseditar", jogos);
        redirectAttributes.addAttribute("msgEditar", "Jogo editado com sucesso");
        return "editar";
    }
    @RequestMapping(value = "/deletar/{id}", method = RequestMethod.GET)
    public String deletarJogos(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Jogos jogos = service.findById(id);
        jogos.setDeleted(new Date(System.currentTimeMillis()));
        service.alterar(jogos);
        redirectAttributes.addAttribute("msgDeletar", "Jogo deletado com sucesso");
        return "redirect:/admin";
    }
}
