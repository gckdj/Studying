package hello.servlet.Web.frontcontroller.v2;

import hello.servlet.Web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {
    MyView process(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException;
}
