// Place your Spring DSL code here
beans = {
	log4jConfigurer(org.springframework.beans.factory.config.MethodInvokingFactoryBean) {
		targetClass = "org.springframework.util.Log4jConfigurer"
		targetMethod = "initLogging"
		arguments = ["log4j.properties"]
	}

	preAuthenticatedUserDetailsService(org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper) { userDetailsService = ref('userService') }

	preAuthenticatedAuthenticationProvider(org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider) {
		preAuthenticatedUserDetailsService = ref('preAuthenticatedUserDetailsService') 
	}

	requestHeaderAuthenticationFilter(org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter) {
		principalRequestHeader = 'CT-REMOTE-USER'
		authenticationManager = ref('authenticationManager')		
	}
	
	cleartrustStubFilter(com.nationwide.security.web.authentication.preauth.stub.ClearTrustRequestHeaderStubFilter,
		['unknown':[:],
			'writer':[
				'fullName':'Joe User',
				'lastname':'User',
				'email':'userj99@nationwide.com',
				'firstname':'Joe',
				'employeeNumber':'123456',
				'groupMembership':'cn=ROLE_USER,ou=LDAP,ou=groups,dc=nationwidedir,dc=pilot',
				'telephoneNumber':'5555555555',
				'ct-auth-type':'2048',
				'ct-web-svr-id':'B2E_J2EE_DEV'],
			'reviewer':[
				'fullName':'Mary Manager',
				'lastname':'Manager',
				'email':'mgr9999@nationwide.com',
				'firstname':'Mary',
				'employeeNumber':'654321',
				'groupMembership':'cn=ROLE_USER,ou=LDAP,ou=groups,dc=nationwidedir,dc=pilot,cn=ROLE_SUPERVISOR,ou=LDAP,ou=groups,dc=nationwidedir,dc=pilot',
				'telephoneNumber':'5555555555',
				'ct-auth-type':'2048',
				'ct-web-svr-id':'B2E_J2EE_DEV']
		]) {
		}

	/*userService(org.springframework.security.core.userdetails.memory.InMemoryDaoImpl) {  userMap = """writer=12b141f35d58b8b3a46eea65e6ac179e,ROLE_USER
                      reviewer=d1a5e26d0558c455d386085fad77d427,ROLE_REVIEWER,ROLE_USER""" }*/
		
	userService(org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService){
		grailsApplication=ref('grailsApplication')
	}
	
	
}