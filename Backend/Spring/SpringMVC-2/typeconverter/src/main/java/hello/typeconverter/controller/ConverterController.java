package hello.typeconverter.controller;

import hello.typeconverter.type.ipPort;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConverterController {

    @GetMapping("/converter-view")
    public String converterView(Model model) {
        model.addAttribute("number", 10000);
        model.addAttribute("ipPort", new ipPort("127.0.0.1", 8080));
        return "converter-view";
    }

    @GetMapping("/converter/edit")
    public String converterForm(Model model) {
        ipPort ipPort = new ipPort("127.0.0.1", 8080);

        Form form = new Form(ipPort);
        model.addAttribute("form", form);
        return "converter-form";
    }

    @PostMapping("/converter/edit")
    public String converterForm(@ModelAttribute Form form, Model model) {
        ipPort ipPort = form.getIpPort();
        model.addAttribute("ipPort", ipPort);

        return "converter-view";
    }

    @Data
    static class Form {
        private ipPort ipPort;

        public Form(ipPort ipPort) {
            this.ipPort = ipPort;
        }
    }
}
