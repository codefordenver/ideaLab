package idealab.api.security;

import idealab.api.repositories.EmployeeRepo;
import idealab.api.service.UserDetailsServiceImpl;
import idealab.api.security.SecurityConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter { // TODO: also i think it's better name it as WebSecurityConfigurer since it's duty configure security related operations.
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmployeeRepo employeeRepo;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, EmployeeRepo employeeRepo) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.employeeRepo = employeeRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("**/**").permitAll() //TODO W.E. : Remove when authentication starts
                //.antMatchers(LOGIN_URL).permitAll()
                //.antMatchers(CHANGE_PASSWORD_URL, SIGN_UP_URL).hasRole(EmployeeRole.ADMIN.getText())
                //.antMatchers(HttpMethod.DELETE).hasRole(EmployeeRole.ADMIN.getText())
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), employeeRepo))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), employeeRepo))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();

        List<String> origins = new ArrayList<String>();
        origins.add("*");
        configuration.setAllowedOrigins(origins);

        List<String> methods = new ArrayList<String>();
        methods.add("HEAD");
        methods.add("GET");
        methods.add("POST");
        methods.add("OPTIONS");
        methods.add("PUT");
        methods.add("DELETE");
        methods.add("PATCH");
        configuration.setAllowedMethods(methods);
        configuration.applyPermitDefaultValues();

        configuration.setExposedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
