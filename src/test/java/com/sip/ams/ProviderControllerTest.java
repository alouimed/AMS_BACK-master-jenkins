package com.sip.ams;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sip.ams.controllers.ProviderController;
import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;
import com.sip.ams.services.ProviderService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProviderController.class)
public class ProviderControllerTest {

    @InjectMocks
    private ProviderController providerController;

    @MockBean
    private ProviderService providerService;

    @Autowired
    private MockMvc mockMvc;

  /*  @Test
    public void getAllProviders_ReturnsAllProviders_Success() throws Exception {
        Provider provider1 = new Provider("Provider1", "Address1", "email1@example.com");
        Provider provider2 = new Provider("Provider2", "Address2", "email2@example.com");
        List<Provider> providers = Arrays.asList(provider1, provider2);

        when(providerService.getAllProviders()).thenReturn(providers);

        mockMvc.perform(MockMvcRequestBuilders.get("/providers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(providers.size()));
    }*/
    
    @Test
    void shouldReturnListOfTutorials() throws Exception {
      List<Provider> providers = new ArrayList<>(
          Arrays.asList(new Provider("Provider1", "Address1", "email1@example.com"),
              new Provider("Provider2", "Address2", "email2@example.com")));

     // when(providerRepository.findAll()).thenReturn(providers);
      when(providerService.getAllProviders()).thenReturn(providers);
      mockMvc.perform(get("/providers/"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()").value(providers.size()))
          .andDo(print());
      
     /* mockMvc.perform(MockMvcRequestBuilders.get("/providers/")
    		    .contentType(MediaType.APPLICATION_JSON))
    		    .andExpect(status().isOk())
    		    .andExpect(jsonPath("$").isArray())
    		    .andExpect(jsonPath("$.length()").value(providers.size()));*/
    }
    
}
