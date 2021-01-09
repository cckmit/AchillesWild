package com.taobao.cfp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.taobao.cfp.business.manager.IDreamBenefitManager;
import com.taobao.cfp.domain.DreamBenefitDTO;
import com.taobao.cfp.repo.IItemRepo;
import com.taobao.cfp.service.impl.DreamAdminBenefitServiceImpl;
import com.taobao.item.domain.ItemDO;
import com.taobao.item.exception.IcException;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.doThrow;

public class DreamAdminBenefitServiceMockTest {

    @Spy
    @InjectMocks
    private DreamAdminBenefitServiceImpl dreamAdminBenefitService;

    @Mock
    private IItemRepo iItemRepo;

    @Mock
    private IDreamBenefitManager dreamBenefitManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addItemsBenefitMock(){

        List<ItemDO> list = new ArrayList<>();
        ItemDO itemDO = new ItemDO();
        itemDO.setItemId(2300951170799L);
        itemDO.setTitle("232");
        itemDO.setUserId(3535435L);
        itemDO.setAuctionStatus(3);

        itemDO.setPictUrl("http");
        itemDO.setPictUrl("img");
        itemDO.setReservePrice(23L);
        list.add(itemDO);
        when(iItemRepo.getItemsByIds(anyObject())).thenReturn(list);

        //when(iItemRepo.getItemsByIds(anyObject())).thenThrow(new RuntimeException());


        Map<Date,Integer> dateToSortWeightMap = new HashMap<>();

        when(dreamBenefitManager.getMaxSortWeight(anyObject())).thenReturn(dateToSortWeightMap);

        List<DreamBenefitDTO> dreamBenefitDTOList = new ArrayList<>();
        DreamBenefitDTO dreamBenefitDTO = new DreamBenefitDTO();
        dreamBenefitDTO.setItemId(2300951170799L);
        dreamBenefitDTO.setPublishDate(DateUtils.truncate(new Date(), Calendar.DATE));
        dreamBenefitDTO.setPublishDateEnd(new Date());
        dreamBenefitDTO.setItemCount(12);
        dreamBenefitDTO.setMaxUserCount(23);
        dreamBenefitDTOList.add(dreamBenefitDTO);


        DreamBenefitDTO dreamBenefitDTO2 = new DreamBenefitDTO();
        dreamBenefitDTO2.setItemId(2300951170788L);
        dreamBenefitDTO2.setPublishDate(DateUtils.truncate(new Date(), Calendar.DATE));
        dreamBenefitDTO2.setPublishDateEnd(new Date());
        dreamBenefitDTO2.setItemCount(55);
        dreamBenefitDTO2.setMaxUserCount(77);
        dreamBenefitDTOList.add(dreamBenefitDTO2);
        dreamAdminBenefitService.addItemsBenefits(dreamBenefitDTOList, Arrays.asList(1L));



    }
}
