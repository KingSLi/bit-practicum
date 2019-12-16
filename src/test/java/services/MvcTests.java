package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MvcTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void TestRBCPositiveMvc() throws Exception {
        this.mockMvc.perform(get("/rbc")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void TestWeatherMVC() throws Exception {
        this.mockMvc.perform(get("/weather")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void TestRatesMVC() throws Exception {
        this.mockMvc.perform(post("/predict").param("temperature", "34")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void TestPredictMVC() throws Exception {
        this.mockMvc.perform(post("/predict")
                .param("temperature", "35"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("with temperature 35, Dollar will be - 59.492283946210506"));

    }

}
