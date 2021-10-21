import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class CalculatorTest
{
    Calculator calculator = new Calculator();

    @Test
    public void testExpressionToRPN() throws Exception
    {
        String expression = "2+2*2";
        String pn = "2 2 2*+";

        System.out.println("Test Programm: test 1 - forming reverse polish notation");
        System.out.println(expression + " = " + calculator.expressionToRPN(expression));
        assertEquals("Неверная польская запись!", pn, calculator.expressionToRPN(expression));
    }

    @Test
    public void testExpressionToRPNNegative() throws Exception
    {
        String expression = "-(-2-2)";
        String pn = "0 0 2 -2 --";

        System.out.println("Test Programm: test 2 - forming negative reverse polish notation");
        System.out.println(expression + " = " + calculator.expressionToRPN(expression));
        assertEquals("Неверная польская запись!", pn, calculator.expressionToRPN(expression));
    }

    @Test
    public void testRPNToAnswer() throws Exception
    {
        String expression = "2+2*2";
        double result = 6;

        System.out.println("Test Programm: test 3 - calculation");
        System.out.println("Ответом будет: " + calculator.rpnToAnswer(calculator.expressionToRPN(expression)));
        assertEquals("Неверный ответ!", String.valueOf(result), String.valueOf(calculator.rpnToAnswer(calculator.expressionToRPN(expression))));
    }

    @Test
    public void testRPNToAnswerNegative() throws Exception
    {
        String expression = "-(-2-2)";
        double result = 4;

        System.out.println("Test Programm: test 4 - calculation negative");
        System.out.println("Ответом будет: " + calculator.rpnToAnswer(calculator.expressionToRPN(expression)));
        assertEquals("Неверный ответ!", String.valueOf(result), String.valueOf(calculator.rpnToAnswer(calculator.expressionToRPN(expression))));
    }

    @Test
    public void testExpressionToRNPFromTable() throws Exception
    {
        System.out.println("Test Programm: test 5 - forming reverse polish notation from table");
        InputStream in = new FileInputStream("D:\\Study\\PL\\Java\\RPNCalculator\\src\\test\\TestData\\FRPNData.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(in);

        String expression = "";
        String result = "";

        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                int cellType = cell.getCellType();
                if (cell == row.getCell(0) && cellType == Cell.CELL_TYPE_STRING)
                {
                    System.out.print((expression = cell.getStringCellValue()) + " = ");
                }else if (cell == row.getCell(1) && cellType == Cell.CELL_TYPE_STRING)
                {
                    System.out.print((result = cell.getStringCellValue()));
                }else break;

            }
            System.out.println();
            assertEquals("Ошибка в числе: " + expression, result, calculator.expressionToRPN(expression));

        }
    }


    @Test
    public void testRPNToAnswerFromTable() throws Exception
    {
        System.out.println("Test Programm: test 6 - calculation from table");
        InputStream in = new FileInputStream("D:\\Study\\PL\\Java\\RPNCalculator\\src\\test\\TestData\\СRPNData.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(in);

        String expression = "";
        double result = 0;

        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                int cellType = cell.getCellType();

                if (cellType == Cell.CELL_TYPE_STRING)
                {
                    System.out.print((expression = cell.getStringCellValue())+ " = ");
                }else if (cellType == Cell.CELL_TYPE_NUMERIC)
                {
                    System.out.print((result = cell.getNumericCellValue()) );
                }else break;


            }
            System.out.println();
            assertEquals("Ошибка в числе: " + expression, result, calculator.rpnToAnswer(calculator.expressionToRPN(expression)), 0.1);

        }
    }

}
