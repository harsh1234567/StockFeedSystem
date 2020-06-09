package com.usermanagementservice.authsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.usermanagementservice.constants.Constants;

/**
 * THe CORSFilter.
 * 
 * @author harsh.jain
 *
 */
@Configuration
@EnableWebFlux
public class CORSFilter implements WebFluxConfigurer {

	/**
	 * The addCorsMappings.
	 * 
	 * @param CorsRegistry registry.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(Constants.ASTRIK_ASTRIK).allowedOrigins(Constants.ASTRIK).allowedMethods(Constants.ASTRIK)
				.allowedHeaders(Constants.ASTRIK);
	}
}