package com.license.ProjectSocialNetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication(scanBasePackages = "com.license.ProjectSocialNetwork")//, exclude = { XADataSourceAutoConfiguration.class, DataSourceAutoConfiguration.class })
// @EnableJpaRepositories(value = "com.license.ProjectSocialNetwork.repository", basePackages="com.license.ProjectSocialNetwork")
//@ConditionalOnMissingBean({ LocalContainerEntityManagerFactoryBean.class, EntityManagerFactory.class })

@SpringBootApplication
public class ProjectSocialNetworkApplication extends SpringBootServletInitializer{// implements WebApplicationInitializer {

	public static void main(String[] args){

		SpringApplication.run(ProjectSocialNetworkApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(ProjectSocialNetworkApplication.class);
//	}

	//private static Class<ProjectSocialNetworkApplication> applicationClass = ProjectSocialNetworkApplication.class;
}
