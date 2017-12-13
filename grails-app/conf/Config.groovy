// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
import grails.plugins.springsecurity.SecurityConfigType


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

grails.sitemesh.default.layout = 'oyslayout'

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true

    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

//log4j = {
   // Do not configure logging here.  Instead, use log4j.properties
//}

file.upload.directory="C:/uploads"

grails.doc.title="OYS Writers Tool Documentation"
grails.doc.copyright="&copy; 2012 Nationwide Mutual Insurance Company. All rights reserved."
grails.doc.footer=""
grails.doc.authors="Drew Repasky"

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.nationwide.edm.authoring.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.nationwide.edm.authoring.UserRole'
grails.plugins.springsecurity.authority.className = 'com.nationwide.edm.authoring.Role'
grails.plugins.springsecurity.roleHierarchy = '''
   ROLE_ADMIN > ROLE_REVIEWER
   ROLE_REVIEWER > ROLE_WRITER
   ROLE_WRITER > ROLE_USER

'''
grails.plugins.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
grails.plugins.springsecurity.interceptUrlMap = [
	'/js/**':		['IS_AUTHENTICATED_ANONYMOUSLY'],
	 '/css/**':		['IS_AUTHENTICATED_ANONYMOUSLY'],
	 '/images/**':	['IS_AUTHENTICATED_ANONYMOUSLY'],	
	 '/login/**': 	['IS_AUTHENTICATED_ANONYMOUSLY'],
	 '/logout/**':  ['IS_AUTHENTICATED_ANONYMOUSLY'],
	 '/admin/**':	['ROLE_REVIEWER','IS_AUTHENTICATED_FULLY'],
	 '/**': 		['ROLE_USER','IS_AUTHENTICATED_FULLY']
	
]
grails.plugins.springsecurity.providerNames = ['preAuthenticatedAuthenticationProvider', 'anonymousAuthenticationProvider']

//grails.war.dependencies = [
//	"ojdbc6.jar"
//]


