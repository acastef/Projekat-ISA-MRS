//package bioskopi.rs.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private EntryPointUnauthorizedHandler unauthorizedHandler;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Autowired
//    private SuccessAuth successAuth;
//
//    @Autowired FailerAuth failerAuth;
//
//    /*@Autowired
//    private SecurityService securityService;*/
//
//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .userDetailsService(this.userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
//        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
//        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
//        return authenticationTokenFilter;
//    }
//
//    /*@Bean
//    public SecurityService securityService() {
//        return this.securityService;
//    }*/
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        /*httpSecurity
//                .csrf()
//                .disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(this.unauthorizedHandler)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers("/auth/**").permitAll()
//                .anyRequest().authenticated();*/
//
//        // Custom JWT based authentication
//        /*httpSecurity
//                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);*/
//
//        httpSecurity
//                .csrf()
//                .disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(this.unauthorizedHandler)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/auth/**").authenticated()
//                .and()
//                .formLogin()
//                .successHandler(successAuth)
//                .failureHandler(failerAuth);
//
//    }
///*http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
//        http.formLogin().successHandler(authenticationSuccessHandler);
//        http.formLogin().failureHandler(authenticationFailureHandler);*/
//}