package cn.huanzi.qch.baseadmin.medicine.service;


import cn.huanzi.qch.baseadmin.common.service.CommonService;
import cn.huanzi.qch.baseadmin.medicine.pojo.UtilDrug;
import cn.huanzi.qch.baseadmin.medicine.vo.CalculateDaysVo;
import cn.huanzi.qch.baseadmin.medicine.vo.UtilDrugResultVo;
import cn.huanzi.qch.baseadmin.medicine.vo.UtilDrugVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UtilDrugService extends CommonService<UtilDrugVo, UtilDrug, Integer> {


    /**
     * 药品服用天数计算接口
     * @param calculateDaysVo
     * @return
     */
    UtilDrugResultVo calculateDays(CalculateDaysVo calculateDaysVo) throws Exception ;
    /**
     * 药品服用天数计算接口
     * @param calculateDaysVo
     * @return
     */
    UtilDrugResultVo calculateDaysV2(CalculateDaysVo calculateDaysVo) throws Exception ;


    /**
     * 药品服用天数计算接口-批量计算
     * @param
     * @return
     */
    void importExcel(MultipartFile excel ,HttpServletResponse response) throws IOException;
}
