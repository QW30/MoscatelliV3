package fr.hellocorp.projetmoscatelli;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        try {
            System.setErr(new PrintStream(new FileOutputStream(System.getProperty("user.home")+"/error.log")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return "error";
    }
}