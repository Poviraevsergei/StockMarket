package by.itechart.repository;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import by.itechart.model.domain.Company;
import by.itechart.exception.CustomException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static by.itechart.utils.ProjectProperties.TEST_TICKER;
import static by.itechart.utils.ProjectProperties.COMPANY_NOT_FOUND;

@DataJpaTest
@RunWith(SpringRunner.class)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private final Company company = new Company();

    @BeforeEach
    void setUp() {
        company.setName("CompanyName");
        company.setIndustry("Technology");
        company.setTicker(TEST_TICKER);
        company.setPhone("+375 29 666 66 66");
        company.setUrl("www.stock-text.com");
        company.setLogo("There should be a logo here!");
    }

    @Test
    void findCompaniesByTicker() {
        Company savedCompany = companyRepository.save(company);
        Company findCompany = companyRepository.findCompaniesByTicker(savedCompany.getTicker()).orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));

        assertThat(findCompany, is(notNullValue()));
        assertThat(savedCompany.getTicker(), is(findCompany.getTicker()));
    }

    @Test
    void findAll() {
        final int COMPANIES_COUNT_IN_DATABASE = 3;
        List<Company> resultList = (List<Company>) companyRepository.findAll();

        assertThat(resultList, is(notNullValue()));
        assertThat(resultList.size(), is(COMPANIES_COUNT_IN_DATABASE));
    }

    @Test
    void findById() {
        Company savedCompany = companyRepository.save(company);
        Company findCompany = companyRepository.findById(savedCompany.getId()).orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));

        assertThat(savedCompany.getId(), is(equalTo(findCompany.getId())));
    }

    @Test
    void save() {
        List<Company> listCompanies = (List<Company>) companyRepository.findAll();
        companyRepository.save(company);
        List<Company> newListCompanies = (List<Company>) companyRepository.findAll();

        assertThat(newListCompanies.size(), allOf(notNullValue(), equalTo(listCompanies.size() + 1)));
    }

    @Test
    void deleteById() {
        Company resultCompany = companyRepository.save(company);
        companyRepository.deleteById(resultCompany.getId());

        assertThat(companyRepository.findById(resultCompany.getId()).orElse(null), is(nullValue()));
    }
}