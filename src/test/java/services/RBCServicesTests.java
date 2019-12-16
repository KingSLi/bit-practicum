package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RBCServicesTests {

    @Test
    public void testParsingData() {
        RBCService rbcService = new RBCService();
        RBCService mockRbcService = Mockito.spy(rbcService);
        Double[] arr = {64.14, 63.123, 65.154};
        List<Double> dollars = Arrays.asList(arr);
        String responce = "0,123456,64.14\n" +
                "0,123456,63.123\n" +
                "0,123456,65.154\n";

        Mockito.when(mockRbcService.CollectData("20")).thenReturn(responce);
        assertEquals(dollars, mockRbcService.GetRatesForLastDays("20"));
    }

    @Test
    public void testMocMaxDollar() {
        RBCService rbcService = new RBCService();
        RBCService mockRbcService = Mockito.spy(rbcService);
        Double[] arr = {64.14, 63.123, 65.154};
        List<Double> dollars = Arrays.asList(arr);

        Mockito.when(mockRbcService.GetRatesForLastDays("30")).thenReturn(dollars);
        assertEquals(65.154, mockRbcService.GetMaxRate(), 1e-4);
    }

}
