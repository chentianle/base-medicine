package cn.huanzi.qch.baseadmin.medicine.controller;

import cn.huanzi.qch.baseadmin.common.controller.CommonController;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.medicine.pojo.UtilDrug;
import cn.huanzi.qch.baseadmin.medicine.service.UtilDrugService;
import cn.huanzi.qch.baseadmin.medicine.vo.CalculateDaysVo;
import cn.huanzi.qch.baseadmin.medicine.vo.UtilDrugResultVo;
import cn.huanzi.qch.baseadmin.medicine.vo.UtilDrugVo;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/openApi/")
public class UtilDrugController extends CommonController<UtilDrugVo, UtilDrug, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(UtilDrugController.class);

    @Autowired
    private UtilDrugService utilDrugService;

    /**
     * 药品服用天数计算接口
     * @param calculateDaysVo
     * @return
     * @throws Exception
     */
    @PostMapping("calculate-days")
    public Result<UtilDrugResultVo> calculateDays(@RequestBody CalculateDaysVo calculateDaysVo) throws Exception{
        logger.info("药品服用天数计算接口："+ JSONObject.toJSONString(calculateDaysVo));
        try {
            if (StringUtils.isEmpty(calculateDaysVo.getDrug_name())) {
                return Result.of(null, true, "药品名称不能为空!");
            }
            if (StringUtils.isEmpty(calculateDaysVo.getUnit())) {
                return Result.of(null, true, "药品单位不能为空!");
            }
            if (StringUtils.isEmpty(calculateDaysVo.getSpecification())) {
                return Result.of(null, true, "药品规格不能为空!");
            }
            if (StringUtils.isEmpty(calculateDaysVo.getDosage())) {
                return Result.of(null, true, "药品用法用量不能为空!");
            }
            if (calculateDaysVo.getQuantity() == null || calculateDaysVo.getQuantity() == 0) {
                return Result.of(null, true, "药品购买数量不能为空!");
            }
            UtilDrugResultVo resultVo = utilDrugService.calculateDaysV2(calculateDaysVo);
            return Result.of(resultVo,true,"接口成功");
        }catch (Exception e){
            return Result.of(null, false, e.getMessage());
        }
    }



    /**
     * 药品服用天数批量计算
     * @return
     * @throws Exception
     */
    @PostMapping("calculate-days-file")
    public Result<UtilDrugResultVo> calculateDaysFile(@RequestParam("file") MultipartFile excel, HttpServletResponse response) throws Exception{
        try {
            utilDrugService.importExcel(excel,response);
        }catch (Exception e){
            e.printStackTrace();
            return Result.of(null, false, e.getMessage());
        }
        return null;
    }
}
