package com.achilles.wild.server.tool.data;

import com.achilles.wild.server.business.service.info.impl.CitizenServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CitizenExcelInfoTest{

    @Spy
    @InjectMocks
    private CitizenServiceImpl citizenServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void simpleRead() {
        // ���� ��Ҫָ�������ĸ�classȥ����Ȼ���ȡ��һ��sheet �ļ������Զ��ر�
      //  EasyExcel.read("/Users/achilleswild/Desktop/test.xls", DreamBenefitExcelUploadVO.class, new UploadExcelListener(citizenServiceImpl)).sheet().doRead();
    }
}
