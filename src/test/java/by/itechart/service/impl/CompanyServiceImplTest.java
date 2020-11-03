package by.itechart.service.impl;

import by.itechart.model.domain.Company;
import by.itechart.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.itechart.utils.ProjectProperties.TEST_TICKER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;

@RunWith(MockitoJUnitRunner.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private final Company company = new Company();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        company.setId(1L);
        company.setName("CompanyName");
        company.setIndustry("Technology");
        company.setTicker(TEST_TICKER);
        company.setPhone("+375 29 666 66 66");
        company.setUrl("www.stock-text.com");
        company.setLogo("There should be a logo here!");
    }

    @Test
    void findAllCompanyFromDb() {
        List<Company> companyList = new ArrayList<>();
        companyList.add(company);
        when(companyRepository.findAll()).thenReturn(companyList);
        List<Company> result = companyService.findAllCompanyFromDb().orElseThrow();

        assertThat(result, allOf(notNullValue(), equalTo(companyList)));
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void findCompanyFromDbByTicker() {
        when(companyRepository.findCompaniesByTicker(anyString())).thenReturn(Optional.of(company));
        Company newCompany = companyService.findCompanyFromDbByTicker(anyString()).orElseThrow();

        assertThat(newCompany, equalTo(company));
        verify(companyRepository, times(1)).findCompaniesByTicker(anyString());
    }

    @Test
    void findCompanyById() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        Company newCompany = companyService.findCompanyById(anyLong()).orElseThrow();

        assertThat(newCompany, equalTo(company));
        verify(companyRepository, times(1)).findById(anyLong());
    }

    @Test
    void createCompany() {
        when(companyRepository.save(any())).thenReturn(company);
        Company newCompany = companyService.createCompany(any());

        assertThat(newCompany, equalTo(company));
        verify(companyRepository, times(1)).save(any());
    }

    @Test
    void updateCompany() {
        when(companyRepository.save(any())).thenReturn(company);
        Company newCompany = companyService.updateCompany(any());

        assertThat(newCompany, equalTo(company));
        verify(companyRepository, times(1)).save(any());
    }

    @Test
    void deleteCompanyById() {
        companyService.deleteCompanyById(anyLong());

        verify(companyRepository, times(1)).deleteById(anyLong());
    }
}