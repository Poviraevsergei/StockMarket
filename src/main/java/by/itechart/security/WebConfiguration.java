package by.itechart.security;

import by.itechart.security.filter.Oauth2AuthorizationFilter;
import by.itechart.security.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userDetailsService;
    private final Oauth2AuthorizationFilter oauth2AuthorizationFilter;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/h2-console/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login", "/logout", "/registration").permitAll()
                .antMatchers("/comparison", "/comparisonForTheYear").hasAnyRole("ADMIN", "CLIENT")
                .antMatchers("/company/getCompanyfromDb/**", "/company/getAllComaniesFromDb", "/company/getAllCompanies"
                        , "/company/getCompany/**", "/company/getCompanyNews", "/company/getFinancialReport").hasAnyRole("USER", "ADMIN", "CLIENT")
                .antMatchers("/stock/getStockCandles", "/stock/getCompanyStockForTheDay", "/stock/getCompanyStockForTheYear").hasAnyRole("USER", "ADMIN", "CLIENT")
                .antMatchers("/user/addCompany").hasAnyRole("USER", "ADMIN", "CLIENT")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .oauth2Login();

        http.addFilterBefore(oauth2AuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}