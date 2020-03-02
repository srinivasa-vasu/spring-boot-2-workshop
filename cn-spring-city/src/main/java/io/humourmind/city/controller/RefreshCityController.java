package io.humourmind.city.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class RefreshCityController {

	@Value("${hello.there: Hi there!}")
	private String refreshValue;


}
