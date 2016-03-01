package com.zerol.crm.common.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.util.Locale;
import java.util.Random;

/**
 * Created by gaoqiang on 23/1/2015.
 */
public class JxlUtil {
    public static void main(String[] args) throws Exception {
        WritableWorkbook wworkbook = null;
        File file = new File("D:\\myapp\\output1.xls");
        if(file.exists()){
            //file.delete();
        }
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("cn", "CN"));
        wworkbook = Workbook.createWorkbook(file,wbSettings);
        WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
        Label label = new Label(5, 2, "2013-12-12");
        wsheet.addCell(label);
        for(int i=0; i<5; i++){
            int intt = new Random().nextInt(10);
            Label ls1 = new Label(0, i, "AA_"+intt);
            Number num = new Number(1, i, intt);
            wsheet.addCell(ls1);
            wsheet.addCell(num);
        }

        wsheet.addCell(new Label(0, 6, "总和"));
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(B1:B5)");
        Formula f = new Formula(1, 6, buf.toString());
        wsheet.addCell(f);

        Number number = new Number(3,4,3.1459);
        wsheet.addCell(number);


        wworkbook.write();
        wworkbook.close();

        Workbook workbook = Workbook.getWorkbook(new File("D:\\myapp\\output1.xls"));
        Sheet sheet = workbook.getSheet(0);
        Cell cell1 = sheet.getCell(0, 2);
        System.out.println(cell1.getContents());
        Cell cell2 = sheet.getCell(3, 4);
        System.out.println(cell2.getContents());
        workbook.close();
    }
}
