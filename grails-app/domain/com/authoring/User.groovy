package com.authoring

class User {

	def springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String firstName
	String lastName

	static constraints = {
		username blank: false, unique: true
		password nullable:true
		firstName nullable:true, blank:true
		lastName nullable:true, blank:true
	}

	static mapping = {
		password column: '`password`'
		// "User" is a reserved word in Oracle, so change the underlying table name
		table "WRITERS_USER"
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()		
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	String toString() {
		username
	}
	
		
	//All Users have ROLE_USER by default
	//This code assumes that you can only be ONE, writer, reviewer or admin.  Not multiple users.
	String getUserRole(){
		def roles = this.getAuthorities()
		def role = ""
			roles.each{
				if(it.authority != 'ROLE_USER'){
					role = it.authority
				}
			}
			return role
		}
	
}
