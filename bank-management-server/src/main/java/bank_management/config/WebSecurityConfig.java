package bank_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bank_management.enumeration.Role;
import bank_management.filter.JwtAuthenticationFilter;
import bank_management.service.CustomUserDetailsService;

@EnableWebSecurity // Kích hoạt tính năng Spring Security trên ứng dụng Web
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }	
    
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService) // Cung cáp customUserDetailsService cho spring security
            .passwordEncoder(passwordEncoder()); // Cung cấp password encoder
    }

    // Override lại method configure để thực hiện việc phân quyền.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors() // Ngăn chặn request từ một domain khác
             .and()
            .csrf().disable() // Tăt bảo vệ CSRF
            .authorizeRequests()
            	
            // Cho phép mọi người truy cập vào các đường dẫn này
            .antMatchers(
                "/person/login",
                "/person/forgot_password"
            ).permitAll()

            // Cho phép những người đã authenticate và là Manager truy cập vào các đường dẫn này
            .antMatchers(
                "/bank_account",
                "/bank_account/search",
                "/bank_account/*",
                "/bank_account/delete/*",
                "/bank_account/creditAccountByIdCustomer/*"
            ).hasAuthority(Role.Manager.name())

            // Cho phép những người đã authenticated và là Employee truy cập vào các đường dẫn này
            .antMatchers(
                "/bank_account",
                "/bank_account/search",
                "/bank_account/*",
                "/bank_account/delete/*",
                "/bank_account/creditAccountByIdCustomer/*"
            ).hasAuthority(Role.Employee.name())

            // Cho phép những người đã authenticated và là Customer truy cập vào các đường dẫn này
            .antMatchers(
                "/bank_account/payment",
                    "/transaction/*",
                    "/transaction/filter",
                    "/transaction/newCode",
                    "/transaction/sendMoney",
                    "/transaction/deposit/*",
                    "/transaction/payCreditCard/*"
             ).hasAuthority(Role.Customer.name())

            // Tất cả các request khác đều cần phải xác thực mới được truy cập
            .anyRequest().authenticated();

        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

// https://loda.me/articles/ss-huong-dan-spring-security-jwt-json-web-token-hibernate
// https://techmaster.vn/posts/36312/spring-security-ban-sau-ve-authentication-va-authorization-p2