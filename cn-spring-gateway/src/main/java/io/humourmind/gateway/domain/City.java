package io.humourmind.gateway.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class City {
	private Long id;
	private String name="XXX";
	private String country;
	private String stateCode;
	private String postalCode;
	private String latitude;
	private String longitude;

}
