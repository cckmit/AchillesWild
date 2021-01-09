package com.taobao.cfp.test.screen;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.taobao.cfp.biz.repo.IItemRepo;
import com.taobao.cfp.domain.DreamProjectDTO;
import com.taobao.cfp.domain.DreamProjectItemDTO;
import com.taobao.cfp.domain.Item;
import com.taobao.cfp.domain.TaobaoItemDTO;
import com.taobao.cfp.web.ao.EnterTaoAO;
import com.taobao.cfp.web.ao.ProjectAO;
import com.taobao.cfp.web.module.screen.dream.ajax.GetProjectItemList;
import com.taobao.item.domain.ItemDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class GetProjectItemListMockTest {

    @InjectMocks
    private GetProjectItemList getProjectItemList;

    @Mock
    private ProjectAO projectAO;

    @Mock
    private EnterTaoAO enterTaoAO;

    @Mock
    private IItemRepo itemRepo;

    @Test
    public void execute(){
        DreamProjectDTO projectDTO = new DreamProjectDTO();
        projectDTO.setId(123L);

        List<DreamProjectItemDTO> projectItemDTOS = new ArrayList<>();
        DreamProjectItemDTO dreamProjectItemDTO = new DreamProjectItemDTO();
        dreamProjectItemDTO.setItemId(2100505343978L);
        projectItemDTOS.add(dreamProjectItemDTO);
        projectDTO.setItems(projectItemDTOS);

        Mockito.when(projectAO.getProjectById(Mockito.any())).thenReturn(projectDTO);

        Map<Long, TaobaoItemDTO> taobaoItemDTOMap = new HashMap<>();
        TaobaoItemDTO taobaoItemDTO = new TaobaoItemDTO();
        taobaoItemDTO.setStatus(3);
        taobaoItemDTO.setItemId(2100505343978L);
        taobaoItemDTOMap.put(2100505343978L,taobaoItemDTO);

        Mockito.when(enterTaoAO.getEnterTaoItemInfo(Mockito.any(),Mockito.any())).thenReturn(taobaoItemDTOMap);

        ItemDO itemDO = new ItemDO();
        itemDO.setItemId(2100505343978L);
        itemDO.setTitle("title");
        itemDO.setReservePrice(12L);
        itemDO.setPictUrl("http");
        Item item = new Item(itemDO,null,null);

        Mockito.when(itemRepo.getItemByIdFormAll(Mockito.anyLong())).thenReturn(item);

        Long projectId = 316L;

        TurbineRunData rundata = Mockito.mock(TurbineRunData.class);
        ParameterParser parameterParser = Mockito.mock(ParameterParser.class);
        Mockito.when(rundata.getParameters()).thenReturn(parameterParser);
        Mockito.when(parameterParser.getString("callback")).thenReturn("callback");
        Mockito.when(parameterParser.getLong("projectId")).thenReturn(projectId);

        Context context = Mockito.mock(Context.class);

        getProjectItemList.execute(rundata,context);
    }

}
