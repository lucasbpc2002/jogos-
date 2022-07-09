package br.tads.eaj.gamesapp.controller;

import br.tads.eaj.gamesapp.domain.Jogos;
import br.tads.eaj.gamesapp.service.FileStorageService;
import br.tads.eaj.gamesapp.service.JogosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping("/cadastrarjogo")
    public String getJogosCadastro(Model model) {
        Jogos j = new Jogos();
        model.addAttribute("jogoscadastrar", j);
        return "cadastro";
    }

    @PostMapping("/salvar")
    public String doSalvaJogo(@ModelAttribute Jogos j, @RequestParam("file") MultipartFile file) {
        j.setImagemurl(file.getOriginalFilename());
        service.alterar(j);
        fileStorageService.save(file);
        return "admin";
    }

    @GetMapping("/addItemCarrinho")
    public void doAdicionarItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var idJogo = 1;
        var jogo = service.findById(idJogo);
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
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
    }
    @GetMapping("/visualizarCarrinho")
    public String visualizarCarrinho(HttpServletRequest request, Model model) throws ServletException, IOException {
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
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
        Cookie carrinhoCompras = new Cookie("carrinhoCompras", "");
        response.addCookie(carrinhoCompras);
        return "redirect:/index";
    }
}
