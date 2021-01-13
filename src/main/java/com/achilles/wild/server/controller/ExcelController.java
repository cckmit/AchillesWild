package com.achilles.wild.server.controller;

import com.achilles.wild.server.listener.UploadExcelListener;
import com.achilles.wild.server.model.response.account.vo.DreamBenefitExcelUploadVO;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {
    private final static Logger LOG = LoggerFactory.getLogger(ExcelController.class);

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link }
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link}
     * <p>
     * 3. 直接读即可
     */
    @RequestMapping("/upload")
    public String upload(MultipartFile file){

        UploadExcelListener listener = null;
        InputStream inputStream = null;
        try {
            //String key = DigestUtils.md5Hex(file.getInputStream());
            LOG.info("========file.getName()="+ file.getName()+",file.getSize()="+file.getSize()+",file.getContentType()="+file.getContentType());
            LOG.info("========key=====111111===="+ DigestUtils.md5Hex(file.getName()+file.getSize()+file.getContentType()));
            LOG.info("========key=====222222===="+ DigestUtils.md5Hex(file.getInputStream()));

            inputStream = file.getInputStream();

            listener = new UploadExcelListener();
            EasyExcel.read(inputStream,  DreamBenefitExcelUploadVO.class,listener).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<DreamBenefitExcelUploadVO> list = listener.getList();
        LOG.info("list==========="+ JSON.toJSONString(list));
        return "success";
    }


    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("测试A", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
        EasyExcel.write(response.getOutputStream(), DreamBenefitExcelUploadVO.class).sheet("第一个sheet").doWrite(data());;
    }


    protected List<?> data() {
        List<DreamBenefitExcelUploadVO> rowList = new ArrayList<>();
        DreamBenefitExcelUploadVO vo = new DreamBenefitExcelUploadVO();
        vo.setItemId(12L);
        vo.setPublishDate(new Date());
        vo.setName("2422342");
        rowList.add(vo);
        return rowList;

    }
}
