grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.tomcat.jvmArgs= ["-Xms256m",  "-Xmx1024m", "-XX:PermSize=512m", "-XX:MaxPermSize=1024m"]
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
	pom true
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	checksums true // Whether to verify checksums on resolve

	repositories {
		inherits false // Whether to inherit repository definitions from plugins
		//grailsPlugins()
		//grailsHome()
		//grailsCentral()
		//mavenCentral()
		

		// uncomment these to enable remote dependency resolution from public Maven repositories
		//mavenCentral()
		mavenLocal()
		mavenRepo "http://maven.nwie.net/enterprise/"
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
	}
	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		runtime 'com.nationwide.security:spring-security-preauth-cleartrust:3.0.7'
		//runtime 'org.springframework.security:spring-security-taglibs:3.0.5.RELEASE'

		// runtime 'mysql:mysql-connector-java:5.1.16'
	}

	plugins {
		runtime ":hibernate:$grailsVersion"
		runtime ":jquery:1.7.1"
		runtime ":resources:1.1.6"
		test ":spock:0.6"
		runtime ":bootstrap-file-upload:2.1.1"
		compile (":twitter-bootstrap:2.0.2.25") { excludes 'svn' }
		runtime ":spring-security-core:1.2.7.3"
		
		//Uncomment when ready to use the database migrations plugin
		//runtime ":database-migration:1.1"
		

		// Uncomment these (or add new ones) to enable additional resources capabilities
		//runtime ":zipped-resources:1.0"
		//runtime ":cached-resources:1.0"
		//runtime ":yui-minify-resources:0.1.4"

		build ":tomcat:$grailsVersion"
	}
}
