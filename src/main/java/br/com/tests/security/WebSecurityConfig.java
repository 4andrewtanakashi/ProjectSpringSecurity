package br.com.tests.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = {"http://localhost:4200", "localhost:4200"}  )
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    
    @Autowired
    private ImplementsUserDetailsService userDetailsService;
    
   /*Configuração de páginas estáticas que precisam ser autenticadas.*/
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       
        http
        .cors().configurationSource(corsConfigurationSource()).and()
        .csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        .antMatchers(HttpMethod.POST, "/register").permitAll()
        .antMatchers(HttpMethod.POST, "/{uhuhu}/test").permitAll()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        //.antMatchers(HttpMethod.GET, "/test2").permitAll()
        .antMatchers(HttpMethod.GET, "/").permitAll()
        .anyRequest().authenticated()
        .and()
        
        .addFilterBefore(new LoginFilter("/login", authenticationManager()),  
                UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new AuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class)
        
        //.formLogin().permitAll().and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler(
                new HttpStatusReturningLogoutSuccessHandler
                (HttpStatus.ACCEPTED)); 
        //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        //.and().httpBasic();
        
        
    }
   
   @Bean
   CorsConfigurationSource corsConfigurationSource() {

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
       CorsConfiguration config = new CorsConfiguration();  
       config.setAllowedOrigins(Arrays.asList("*"));  
       config.setAllowedMethods(Arrays.asList("*"));  
       config.setAllowedHeaders(Arrays.asList("*"));  
       config.setAllowCredentials(true);  
       config.applyPermitDefaultValues();  
 
       source.registerCorsConfiguration("/**", config);  
       return source; 
   }
   
   /*Como vai ser feita a autenticação */
   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }
   
//   /*Permite nos diretórios do PC, após autenticado*/
//   @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/login?logout**");
//    }
   
//   @Override
//   @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//   @Primary
//   public AuthenticationManager authenticationManagerBean() throws Exception {
//       System.out.println("PASSOU AQUi2 !!!");
//       return super.authenticationManager();
//   }
    

   @Bean  
   public PasswordEncoder passwordEncoder() {  
         return new BCryptPasswordEncoder();  
     }
   
   @Autowired  
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  
         auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());  
     } 
}
