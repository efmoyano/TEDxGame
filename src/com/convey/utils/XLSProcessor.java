package com.convey.utils;

import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 * @projectName TEDxGame
 * @package com.convey.utils
 * @filename XLSProcessor.java
 * @encoding UTF-8
 * @author Ernesto Moyano
 * @date 28/08/2014 22:20:04
 */
public class XLSProcessor {

    public void test() throws IOException, BiffException {

        Workbook workbook = Workbook.getWorkbook(new File("C:\\test.xls"));

        Sheet sheet = workbook.getSheet(0);

        Cell a1 = sheet.getCell(0, 0);
        Cell b2 = sheet.getCell(0, 1);
        Cell c2 = sheet.getCell(0, 2);

        String stringa1 = a1.getContents();
        String stringb2 = b2.getContents();
        String stringc2 = c2.getContents();

        System.out.println(stringa1);
        System.out.println(stringb2);
        System.out.println(stringc2);

    }

}
