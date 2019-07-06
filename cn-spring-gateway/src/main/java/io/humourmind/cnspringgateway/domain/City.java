package io.humourmind.cnspringgateway.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class City {
	private Long id;
	private String name="NaN";
	private String country;
	private String stateCode;
	private String postalCode;
	private String latitude;
	private String longitude;

}
