import ru.clevertec.beans.Check;
import ru.clevertec.exceptions.InputDataException;
import ru.clevertec.utils.CheckFactory;
import ru.clevertec.utils.Util;

public class CheckRunner {

    public static void main(String[] args) {

        try {
            Check check = CheckFactory.getInstance(args);
            String output = Util.getCheck(check);
            System.out.println(output);
            Util.symbolWrite(output, Util.getOutputFileName());
        } catch (InputDataException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}