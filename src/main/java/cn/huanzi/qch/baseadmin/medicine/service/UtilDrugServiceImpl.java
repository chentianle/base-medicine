package cn.huanzi.qch.baseadmin.medicine.service;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.common.service.CommonServiceImpl;
import cn.huanzi.qch.baseadmin.medicine.pojo.UtilDrug;
import cn.huanzi.qch.baseadmin.medicine.repository.UtilDrugRepository;
import cn.huanzi.qch.baseadmin.medicine.unit.DosageParser;
import cn.huanzi.qch.baseadmin.medicine.unit.DosageSpec;
import cn.huanzi.qch.baseadmin.medicine.unit.DrugSpec;
import cn.huanzi.qch.baseadmin.medicine.unit.DrugSpecParser;
import cn.huanzi.qch.baseadmin.medicine.vo.CalculateDaysVo;
import cn.huanzi.qch.baseadmin.medicine.vo.UtilDrugResultVo;
import cn.huanzi.qch.baseadmin.medicine.vo.UtilDrugVo;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.vo.WechatUserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.html.HTMLTableRowElement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UtilDrugServiceImpl extends CommonServiceImpl<UtilDrugVo, UtilDrug, Integer> implements UtilDrugService{


    @Override
    public UtilDrugResultVo calculateDays(CalculateDaysVo condition) throws Exception {

        UtilDrugResultVo resultVo = new UtilDrugResultVo();
        return resultVo;
    }

    /**
     * 数据清洗
     * @param condition
     */
    public void dataClean(CalculateDaysVo condition) throws IllegalArgumentException{

        //药品规格清洗
        String specification = condition.getSpecification();
        specification = specification.replaceAll("\\(","").replaceAll("\\)","");
        specification = specification.replaceAll("（","").replaceAll("）","");
        specification = specification.replaceAll("：","*").replaceAll(":","*").replaceAll("x","*").replaceAll("×","*").
                replaceAll(",","*").replaceAll("%","*").replaceAll("X","*").replaceAll("\\+","*");
        specification = specification.replaceAll("ML","ml").replaceAll("毫克","mg").replaceAll("克","g").
                replaceAll("1滴","0.4ml").replaceAll("S","s").replaceAll("μg","ug");

        //药品用品用量清洗
        String dosage = condition.getDosage();
            dosage = dosage.replaceAll("毫克", "mg").replaceAll("克", "g").replaceAll("1滴", "0.4ml");
        if(!specification.contains("毫升")) {
            dosage = dosage.replaceAll("毫升", "ml");
        }
        if(specification.contains("s")){
            dosage = dosage.replaceAll("粒","s").replaceAll("丸","s").replaceAll("片","s").replaceAll("枚","s").replaceAll("贴","s");
        }
        String hebing = dosage + specification;
        if(hebing.contains("贴")){
            dosage = dosage.replaceAll("贴","片");
            specification = specification.replaceAll("贴","片");
        }
        condition.setSpecification(specification);
        condition.setDosage(dosage);
    }


    @Override
    public UtilDrugResultVo calculateDaysV2(CalculateDaysVo condition) throws Exception {
        UtilDrugResultVo resultVo = new UtilDrugResultVo();
        try {
            //数据清洗
            this.dataClean(condition);

            //解决用法用量适量的问题
            if(condition.getDosage()!=null && condition.getDosage().contains("适量")){
                //解析药品规格
                List<DrugSpec> listDrugSpec = new DrugSpecParser().drugSpecList(condition.getSpecification());
                if (listDrugSpec != null && listDrugSpec.size() > 0) {
                    String dosage = condition.getDosage();
                    String specification = listDrugSpec.get(0).getQuantity()+listDrugSpec.get(0).getUnit();
                    dosage = dosage.replaceAll("0适量", specification).replaceAll("适量",specification);
                    condition.setDosage(dosage);
                }
            }

            //解析药品用法用量
            DosageSpec dosageSpec = new DosageParser().dosageChuli(condition.getDosage());
            //解析药品规格
            List<DrugSpec> listDrugSpec = new DrugSpecParser().drugSpecList(condition.getSpecification());

            String dosageUnit = dosageSpec.getUnit();

            //每日总用量
            double dosageAll = dosageSpec.getPillsPerTime() * dosageSpec.getTimesPerDay();

            //最小单位总数
            double quantityAll = 0;

            for(int i = 0 ; i<listDrugSpec.size() ;i++){
                double dosageQuantity = listDrugSpec.get(i).getQuantity();
                if(dosageUnit.equals(listDrugSpec.get(i).getUnit())){
                    for(int j = (i+1) ; j<listDrugSpec.size() ;j++){
                        dosageQuantity = dosageQuantity * listDrugSpec.get(j).getQuantity();
                    }
                    quantityAll = dosageQuantity * condition.getQuantity();
                    break;
                }
            }
            if(quantityAll<=0){
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("药品规格:");
                String guige = "";
                for(int i = 0 ; i<listDrugSpec.size() ;i++){
                    guige = guige + listDrugSpec.get(i).getUnit() +",";
                }
                if(!guige.equals("")){
                    guige = guige.substring(0,guige.length()-1);
                }
                stringBuffer.append("'"+guige);
                stringBuffer.append("'和药品用法用量'"+dosageUnit);
                stringBuffer.append("'没有对应关系");
                throw new Exception(stringBuffer.toString());
            }
            resultVo.setCalculateDaysVo(condition);
            resultVo.setDays( (int) Math.round(quantityAll/dosageAll));
            resultVo.setMessage("计算成功！");
            resultVo.setType("是");
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return resultVo;
    }
    /**
     * 批量计算
     * @param excel
     */
    @Override
    public void importExcel(MultipartFile excel, HttpServletResponse response) throws IOException {
        List<CalculateDaysVo> data = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(excel.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        try {
            Row fristRow = sheet.getRow(0);
            Map<String,Integer> headerMap = new HashMap<>();
            for(Cell cell : fristRow){
                headerMap.put(cell.getStringCellValue(),cell.getColumnIndex());
            }
            Cell cellFrist = fristRow.createCell(6);
            cellFrist.setCellValue("返回结果");
            cellFrist = fristRow.createCell(7);
            cellFrist.setCellValue("备注");
            cellFrist = fristRow.createCell(8);
            cellFrist.setCellValue("参与计算药品规格");
            cellFrist = fristRow.createCell(9);
            cellFrist.setCellValue("参与计算用药用量");
            for (int  rowNum = 1 ; rowNum<sheet.getLastRowNum();rowNum++) {
                Row currentRow = sheet.getRow(rowNum);
                if(currentRow == null){
                    continue;
                }
                CalculateDaysVo daysVo = new CalculateDaysVo();
                daysVo.setQuantity(1);
                if(currentRow.getCell(headerMap.get("spmch"))!=null) {
                    daysVo.setDrug_name(currentRow.getCell(headerMap.get("spmch")).getStringCellValue());
                }
                if(currentRow.getCell(headerMap.get("shpgg"))!=null) {
                    daysVo.setSpecification(currentRow.getCell(headerMap.get("shpgg")).getStringCellValue());
                }
                if(currentRow.getCell(headerMap.get("yfyl"))!=null) {
                    daysVo.setDosage(currentRow.getCell(headerMap.get("yfyl")).getStringCellValue());
                }
                if(StringUtils.isNotEmpty(daysVo.getSpecification()) && StringUtils.isNotEmpty(daysVo.getDosage())) {
                    if(daysVo.getSpecification().equals(" ") || daysVo.getDosage().equals(" 次 一次")){
                        Cell cell = currentRow.createCell(6);
                        cell.setCellValue("无效数据");
                        cell = currentRow.createCell(7);
                        cell.setCellValue("药品名称|规格|用法用量为空");
                    }else {
                        try {
                            UtilDrugResultVo resultVo = this.calculateDaysV2(daysVo);
                            Cell cell = currentRow.createCell(6);
                            cell.setCellValue("计算成功");
                            cell = currentRow.createCell(8);
                            cell.setCellValue(resultVo.getCalculateDaysVo().getSpecification());
                            cell = currentRow.createCell(9);
                            cell.setCellValue(resultVo.getCalculateDaysVo().getDosage());
                        } catch (Exception e) {
                            Cell cell = currentRow.createCell(6);
                            cell.setCellValue("计算失败");
                            cell = currentRow.createCell(7);
                            cell.setCellValue(e.getMessage());
                            this.dataClean(daysVo);
                            cell = currentRow.createCell(8);
                            cell.setCellValue(daysVo.getSpecification());
                            cell = currentRow.createCell(9);
                            cell.setCellValue(daysVo.getDosage());
                        }
                    }
                }else{
                    Cell cell = currentRow.createCell(6);
                    cell.setCellValue("无效数据");
                    cell = currentRow.createCell(7);
                    cell.setCellValue("药品名称|规格|用法用量为空");
                }
            }
        }catch (Exception io){
            System.out.println(io.toString());
        }finally {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            // 设置响应头
            String filename = "export_data.xlsx";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // 将Excel文件写入响应
            response.getOutputStream().write(outputStream.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}
