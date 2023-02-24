package com.inditex.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A catalog that contains the ServiceException types.
 *
 * They could be separated according to whatever is needed and may contain a default message if desired.
 */
@Getter
@AllArgsConstructor
public enum ServiceErrorCatalog {

	GENERIC_SERVICE_ERROR("An error has occurred processing your request"),
	START_DATE_IS_NOT_CORRRECT("Start date id is not correct"),
	END_DATE_IS_NOT_CORRRECT("End date id is not correct"),
	BRAND_ID_IS_NOT_CORRRECT("Brand id id is not correct"),
	PRODUCT_ID_IS_NOT_CORRRECT("Product id id is not correct"),
	ERROR_OBTAINING_PRICES("An error occurred while obtaining the prices")
	;

	private String message;
}
