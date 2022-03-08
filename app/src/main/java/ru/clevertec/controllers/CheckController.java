package ru.clevertec.controllers;

import ru.clevertec.model.beans.Check;
import ru.clevertec.model.exceptions.InputDataException;
import ru.clevertec.model.utils.CheckFactory;
import ru.clevertec.model.utils.Init;
import ru.clevertec.model.utils.Util;
import ru.clevertec.model.utils.XMLInit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "CheckController", value = "/controller")
public class CheckController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] args = request.getParameter("args").split(" ");
        File rootDir = new File(getServletContext().getRealPath("/WEB-INF/classes/"));

        Init.initialize(rootDir + "/config.properties");
        XMLInit.initialize(rootDir + "/");

        Check check = null;

        try {
            check = CheckFactory.getInstance(args);
        } catch (InputDataException e) {
            e.printStackTrace();
        }
        String output = null;
        if (check != null) {
            output = Util.getCheck(check);
        }
        Util.symbolWrite(output, rootDir + "/" + Init.getOutputFileName());

        RequestDispatcher rd = request.getRequestDispatcher("\\WEB-INF\\classes\\check.txt");
        rd.forward(request, response);
    }
}
