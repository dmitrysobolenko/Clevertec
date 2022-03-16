/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ru.clevertec;

import ru.clevertec.model.entities.Check;
import ru.clevertec.model.entities.CheckFactory;
import ru.clevertec.model.exceptions.InputDataException;
import ru.clevertec.service.utils.Init;
import ru.clevertec.service.utils.Util;
import ru.clevertec.service.CheckService;

import static ru.clevertec.service.utils.Init.getErrorLogFileName;
import static ru.clevertec.service.utils.Init.getOutputFileName;
import static ru.clevertec.service.utils.Util.getPath;

public class CheckRunner {

    public static void main(String[] args) {
        try {
            String path = getPath();
            Init.initialize(path);
            Check check = CheckFactory.getInstance(args);
            String output = CheckService.getCheck(check);
            System.out.println(output);
            Util.symbolWrite(output, path + getOutputFileName());
            Util.symbolWrite(Util.getWrongData(), path + getErrorLogFileName());
        } catch (InputDataException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
