package com.mcep.task;


public class QuartzReadExcel {

/*implements Job {
	
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	  String path = "E:/数据表.xlsx";//E://Microsoft Excel 工作表.xlsx
          InputStream is = null;
          String htmlExcel = null;
          try {
              File sourcefile = new File(path);
              System.out.println("sourcefile:"+sourcefile.getPath());
              is = new FileInputStream(sourcefile);
              Workbook wb = WorkbookFactory.create(is);//此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
              if (wb instanceof XSSFWorkbook) {
                  XSSFWorkbook xWb = (XSSFWorkbook) wb;
                  htmlExcel = POIReadExcelToHtml.getExcelInfo(xWb,true);
              }else if(wb instanceof HSSFWorkbook){
                  HSSFWorkbook hWb = (HSSFWorkbook) wb;
                  htmlExcel = POIReadExcelToHtml.getExcelInfo(hWb,true);
              }
              System.out.println(htmlExcel);
          } catch (Exception e) {
              e.printStackTrace();
          }finally{
              try {
                  is.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
    }*/
}
