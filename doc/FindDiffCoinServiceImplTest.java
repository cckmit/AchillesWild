package com.alipic.cfp.portal.service;

import com.alipic.cfp.portal.api.domain.finddiff.FindDiffReduceCoinVO;
import com.alipic.cfp.portal.api.request.finddiff.CoinGetRequest;
import com.alipic.cfp.portal.api.request.finddiff.FindDiffReduceCoinRequest;
import com.alipic.cfp.portal.common.enums.FindDiffChanceAppEnum;
import com.alipic.cfp.portal.repository.module.finddiff.FindDiffUserDO;
import com.alipic.cfp.portal.service.impl.FindDiffCoinServiceImpl;
import com.alipic.common.exception.BizException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * description
 *
 * @author duanweichao.dwc
 * @date 2020/04/27
 */
@RunWith(MockitoJUnitRunner.class)
public class FindDiffCoinServiceImplTest {

    @InjectMocks
    private FindDiffCoinServiceImpl findDiffCoinService;

    @Mock
    private FindDiffGameLevelFacadeService findDiffGameLevelFacadeService;

    @Mock
    private FindDiffGameUserFacadeService findDiffGameUserFacadeService;

    @Test
    public void getBizType() {
        FindDiffChanceAppEnum bizType = findDiffCoinService.getBizType();
        Assert.assertEquals(FindDiffChanceAppEnum.AZAO, bizType);
    }

    @Test
    public void getCoin() {
        Integer channelId = 1;
        Long userId = 20L;
        CoinGetRequest request = new CoinGetRequest(channelId, userId);
        Integer coin = findDiffCoinService.getCoin(request);
        Assert.assertNull(coin);
    }

    @Test
    public void reduceCoin() {
        Integer channelId = 10;
        Long userId = 20L;
        FindDiffReduceCoinRequest findDiffReduceCoinRequest = new FindDiffReduceCoinRequest();
        findDiffReduceCoinRequest.setUserId(userId);
        findDiffReduceCoinRequest.setGameNo("gameNo");
        findDiffReduceCoinRequest.setLevelNo("levelNo");
        findDiffReduceCoinRequest.setIdempotentKey("idk");
        findDiffReduceCoinRequest.setCount(1);
        findDiffReduceCoinRequest.setType(channelId);

        FindDiffUserDO findDiffUserDO = new FindDiffUserDO();
        findDiffUserDO.setChance(10);
        Mockito.when(findDiffGameUserFacadeService.getFindDiffUserDO(Mockito.any(), Mockito.any())).thenReturn(findDiffUserDO);

        FindDiffReduceCoinVO reduceCoinVO = findDiffCoinService.reduceCoin(userId, findDiffReduceCoinRequest);


        Assert.assertNotNull(reduceCoinVO);
    }

    @Test(expected = BizException.class)
    public void reduceCoinException() {
        Integer channelId = 10;
        Long userId = 20L;
        FindDiffReduceCoinRequest findDiffReduceCoinRequest = new FindDiffReduceCoinRequest();
        findDiffReduceCoinRequest.setUserId(userId);
        findDiffReduceCoinRequest.setGameNo("gameNo");
        findDiffReduceCoinRequest.setLevelNo("levelNo");
        findDiffReduceCoinRequest.setIdempotentKey("idk");
        findDiffReduceCoinRequest.setCount(1);
        findDiffReduceCoinRequest.setType(channelId);

        findDiffCoinService.reduceCoin(userId, findDiffReduceCoinRequest);

        Mockito.when(findDiffGameUserFacadeService.getFindDiffUserDO(Mockito.any(), Mockito.any())).thenReturn(null);

    }
}