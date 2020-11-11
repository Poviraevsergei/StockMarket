package by.itechart.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.model.domain.Company;
import by.itechart.service.CompanyService;
import org.mockito.junit.MockitoJUnitRunner;
import by.itechart.model.response.CompanyResponse;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import by.itechart.model.response.CompanyNewsResponse;
import by.itechart.model.response.AllCompaniesResponse;
import com.fasterxml.jackson.databind.SerializationFeature;
import by.itechart.service.serviceFeign.CompanyServiceFeign;
import by.itechart.model.response.CompanyFinancialReportResponse;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyLong;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.anyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static by.itechart.utils.ProjectProperties.TEST_TICKER;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(MockitoJUnitRunner.class)
class CompanyControllerTest {

    @Mock
    private CompanyServiceFeign companyServiceFeign;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    protected MockMvc mvc;

    private final Company company = new Company();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(companyController).build();
        company.setId(1L);
        company.setName("CompanyName");
        company.setIndustry("Technology");
        company.setTicker(TEST_TICKER);
        company.setPhone("+375 29 666 66 66");
        company.setUrl("www.stock-text.com");
        company.setLogo("There should be a logo here!");
    }

    @Test
    void getCompanyByTicker() throws Exception {
        when(companyService.findCompanyFromDbByTicker(anyString())).thenReturn(Optional.of(company));

        MvcResult result = mvc.perform(get("/company/getCompanyFromDb/{ticker}", TEST_TICKER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyService, times(1)).findCompanyFromDbByTicker(anyString());
    }

    @Test
    void getAllCompaniesFromDb() throws Exception {
        List<Company> companyList = new ArrayList<>();
        companyList.add(company);
        when(companyService.findAllCompanyFromDb()).thenReturn(Optional.of(companyList));

        MvcResult result = mvc.perform(get("/company/getAllCompaniesFromDb"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyService, times(1)).findAllCompanyFromDb();
    }

    @Test
    void getAllCompanies() throws Exception {
        List<AllCompaniesResponse> companyList = new ArrayList<>();
        companyList.add(new AllCompaniesResponse());
        when(companyServiceFeign.getAllCompanies()).thenReturn(Optional.of(companyList));

        MvcResult result = mvc.perform(get("/company/getAllCompanies"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyServiceFeign, times(1)).getAllCompanies();
    }

    @Test
    void getCompany() throws Exception {
        when(companyServiceFeign.getCompany(anyString())).thenReturn(Optional.of(new CompanyResponse()));

        MvcResult result = mvc.perform(get("/company/getCompany/{ticker}", TEST_TICKER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyServiceFeign, times(1)).getCompany(anyString());
    }

    @Test
    void getCompanyNews() throws Exception {
        List<CompanyNewsResponse> companyList = new ArrayList<>();
        companyList.add(new CompanyNewsResponse());
        when(companyServiceFeign.getCompanyNews(anyString(), anyString(), anyString())).thenReturn(Optional.of(companyList));

        MvcResult result = mvc.perform(get("/company/getCompanyNews")
                .param("ticker", TEST_TICKER)
                .param("from", "2020-09-10")
                .param("to", "2020-10-10"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyServiceFeign, times(1)).getCompanyNews(anyString(), anyString(), anyString());
    }

    @Test
    void getFinancialReport() throws Exception {
        when(companyServiceFeign.getFinancialReport(anyString())).thenReturn(Optional.of(new CompanyFinancialReportResponse()));

        MvcResult result = mvc.perform(get("/company/getFinancialReport/{ticker}",TEST_TICKER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyServiceFeign, times(1)).getFinancialReport(anyString());
    }

    @Test
    void findCompanyById() throws Exception {
        when(companyService.findCompanyById(anyLong())).thenReturn(Optional.of(company));

        MvcResult result = mvc.perform(get("/company/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyService, times(1)).findCompanyById(anyLong());
    }

    @Test
    void createCompany() throws Exception {
        when(companyService.createCompany(any())).thenReturn(company);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(company);

        MvcResult result = mvc.perform(post("/company/create").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyService, times(1)).createCompany(any());
    }

    @Test
    void updateCompany() throws Exception {
        when(companyService.updateCompany(any())).thenReturn(company);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(company);

        MvcResult result = mvc.perform(put("/company/update").contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), allOf(notNullValue()));
        verify(companyService, times(1)).updateCompany(any());
    }

    @Test
    void deleteCompany() throws Exception {
        MvcResult result = mvc.perform(delete("/company/{id}", anyLong()))
                .andExpect(status().isNoContent())
                .andReturn();
        verify(companyService, times(1)).deleteCompanyById(anyLong());
    }
}
