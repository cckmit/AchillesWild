package com.achilles.wild.server.tool.file.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;

import com.google.common.collect.Maps;
import com.achilles.wild.server.listener.UploadExcelListener;
import com.achilles.wild.server.model.response.account.vo.DreamBenefitExcelUploadVO;
import com.achilles.wild.server.tool.date.DateUtil;
import org.junit.Test;

public class EasyExcelUtilTest{

    String path = "/Users/achilleswild/Desktop/Achilles.xls";
    //String path = "/Users/achilleswild/Desktop/Achilles.xlsx";

    @Test
    public void simpleRead() {
        UploadExcelListener listener =  new UploadExcelListener();
        EasyExcel.read(path, DreamBenefitExcelUploadVO.class, listener).sheet().doRead();
        System.out.println(JSON.toJSONString(listener.getList()));
    }


    @Test
    public void simpleWrite(){
        List<DreamBenefitExcelUploadVO> list = new ArrayList<DreamBenefitExcelUploadVO>();
        for (int i = 100001; i <= 100002; i++) {
            DreamBenefitExcelUploadVO vo = new DreamBenefitExcelUploadVO();
            vo.setItemId(i*3L);
            vo.setPublishDate(DateUtil.getTheDayFirstTime(7));
            vo.setPublishDateEnd(DateUtil.getTheDayFirstTime(9));
            vo.setItemCount(i+100);
            vo.setMaxUserCount(i+998);
            list.add(vo);
        }
        EasyExcel.write(path, DreamBenefitExcelUploadVO.class).sheet("test").doWrite(list);
    }

    @Test
    public void simpleWriteInputStram() throws Exception{
        List<DreamBenefitExcelUploadVO> list = new ArrayList<DreamBenefitExcelUploadVO>();
        for (int i = 1; i <= 10; i++) {
            DreamBenefitExcelUploadVO dreamBenefitExcelUploadVO = new DreamBenefitExcelUploadVO();
            dreamBenefitExcelUploadVO.setItemId(i*3L);
            dreamBenefitExcelUploadVO.setName("������"+i);
            dreamBenefitExcelUploadVO.setPublishDate(DateUtil.getTheDayFirstTime(7));
            dreamBenefitExcelUploadVO.setPublishDateEnd(DateUtil.getTheDayFirstTime(9));
            dreamBenefitExcelUploadVO.setItemCount(i+100);
            dreamBenefitExcelUploadVO.setMaxUserCount(i+998);
            dreamBenefitExcelUploadVO.setFire(i);
            list.add(dreamBenefitExcelUploadVO);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, DreamBenefitExcelUploadVO.class).excelType(ExcelTypeEnum.XLSX).sheet("��Ŀ").doWrite(list);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        //String str = new String(outputStream.toByteArray());
        //BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray()),"GBK"));
        //String str = br.readLine();
        byte[] data = new byte[4096];
        int count = -1;
        while((count = inputStream.read(data,0,4096)) != -1)
            outputStream.write(data, 0, count);

            System.out.println();
    }


    @Test
    public void simpleWriteTest()  throws Exception {
        List<DreamBenefitExcelUploadVO> list = new ArrayList<DreamBenefitExcelUploadVO>();
        for (int i = 100001; i <= 100003; i++) {
            DreamBenefitExcelUploadVO vo = new DreamBenefitExcelUploadVO();
            vo.setItemId(i * 4L);
            vo.setPublishDate(DateUtil.getTheDayFirstTime(7));
            vo.setPublishDateEnd(DateUtil.getTheDayFirstTime(9));
            vo.setItemCount(i + 100);
            vo.setMaxUserCount(i + 998);
            list.add(vo);
        }

        byte[] array =  exportByteArray("ACHI",list,DreamBenefitExcelUploadVO.class);
        //String fileContent = new String(array, "GBK");
        ByteArrayInputStream excelFile = new ByteArrayInputStream(array);
        String result = new String(array, "UTF-8");
        System.out.println(array);
    }

    private  byte[] exportByteArray(String sheetName, List<DreamBenefitExcelUploadVO> dataList,
        Class clazz) throws Exception{
        // ����������Ϣ
        Map<String, List<DreamBenefitExcelUploadVO>> dataListMap = Maps.newLinkedHashMap();
        // ����������뵽Excel��
        dataListMap.put(sheetName, dataList);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        writeIntoOutputStream(dataListMap, clazz, out);

       // String str = out.toString("utf-8");
       // byte[] lens = out.toByteArray();
       //
       // String result = new String(lens);

       // DataOutputStream dis = new DataOutputStream(out);
        //String newName = dis.readUTF();
        //System.out.println(dis.toString());

        return out.toByteArray();
    }


    private void writeIntoOutputStream(Map<String, List<DreamBenefitExcelUploadVO>> dataListMap, Class clazz, OutputStream outputStream) {

            // ��������
            int sheetNo = 1;
            // �����ļ���
            ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
            // ѭ��д��ÿ��������
            for (Entry<String, List<DreamBenefitExcelUploadVO>> entry : dataListMap.entrySet()) {
                // �õ�����������
                String sheetName = entry.getKey();
                // �õ�����������
                List<DreamBenefitExcelUploadVO> dataList = entry.getValue();
                // ���ù�������Ϣ
                Sheet sheet1 = new Sheet(sheetNo++, 1, clazz, sheetName, null);
                // ��������Ӧ���
               // sheet1.setAutoWidth(Boolean.TRUE);
                // ��ʼд����
                writer.write(dataList, sheet1);
            }
            // ��ջ���
            writer.finish();

    }


}
