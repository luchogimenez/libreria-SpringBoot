package egg.libreria.libreria.controladores;

import egg.libreria.libreria.entidades.Usuario;
import egg.libreria.libreria.servicios.RolService;
import egg.libreria.libreria.servicios.UsuarioService;
import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
@Autowired
    private RolService rolService;
    @GetMapping
    public ModelAndView mostrar(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuarios");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("usuarios", usuarioService.buscarTodos());
        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crear(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("usuario", flashMap.get("usuario"));
            mav.addObject("roles", rolService.buscarTodos());
        } else {
            mav.addObject("usuario", new Usuario());
            mav.addObject("roles", rolService.buscarTodos());
        }

        mav.addObject("title", "Crear Usuario");
        mav.addObject("action", "guardar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@ModelAttribute Usuario usuario, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/usuario");
        System.out.println(usuario);
        try {
            usuarioService.crear(usuario);
            attributes.addFlashAttribute("exito", "La creación ha sido realizada satisfactoriamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/usuario/crear");
        }

        return redirectView;
    }
    
    @GetMapping("/usuario")
    public Usuario findByCorreo(@RequestParam(value = "correo") String correo){
        
        return usuarioService.findByCorreo(correo);
    }
}
