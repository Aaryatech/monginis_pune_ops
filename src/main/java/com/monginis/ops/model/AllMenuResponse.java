package com.monginis.ops.model;

import java.util.List;


public class AllMenuResponse {

private List<Menus> menuConfigurationPage ;

private ErrorMessage errorMessage;




public List<Menus> getMenuConfigurationPage() {
	return menuConfigurationPage;
}

public void setMenuConfigurationPage(List<Menus> menuConfigurationPage) {
	this.menuConfigurationPage = menuConfigurationPage;
}

public ErrorMessage getErrorMessage() {
return errorMessage;
}

public void setErrorMessage(ErrorMessage errorMessage) {
this.errorMessage = errorMessage;
}

}