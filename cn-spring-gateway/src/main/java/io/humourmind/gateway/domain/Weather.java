package io.humourmind.gateway.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
	private Long id;
	private String name;
	private String country;
	private String stateCode;
	private String postalCode;
	private String weather="YYY";
}
