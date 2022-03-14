package ru.clevertec.controllers;

import ru.clevertec.model.entities.Check;
import ru.clevertec.model.exceptions.InputDataException;
import ru.clevertec.model.entities.CheckFactory;
import ru.clevertec.model.utils.Init;
import ru.clevertec.model.utils.Util;
import ru.clevertec.service.CheckService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckController", value = "/controller")
public class CheckController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] args = request.getParameter("args").split(" ");
        String path = getServletContext().getRealPath("/WEB-INF/classes/");

        Init.initialize(path);

        Check check = null;

        try {
            check = CheckFactory.getInstance(args);
        } catch (InputDataException e) {
            e.printStackTrace();
        }
        String output = null;
        if (check != null) {
            output = CheckService.getCheck(check);
        }
        Util.symbolWrite(output, path + "/" + Init.getOutputFileName());

        RequestDispatcher rd = request.getRequestDispatcher("\\WEB-INF\\classes\\check.txt");
        rd.forward(request, response);
    }
}
