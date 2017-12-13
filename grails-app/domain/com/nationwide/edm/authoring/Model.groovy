package com.nationwide.edm.authoring

import java.util.Date;

class Model {
	
	String templateStyle
	
	Date lastUpdated
	Date dateCreated

    static constraints = {
		templateStyle blank:false
	}
	
	String toString() {
		templateStyle
   }
}
