dataSource {
    pooled = true
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
			
//			dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
//			url = "jdbc:oracle:thin:@shplordb0006.nwie.net:1522:edmd"
//			driverClassName = "oracle.jdbc.driver.OracleDriver"
//			username = "WritXmen"
//			password = "R3dSun1"
//			// note: org.hibernate.dialect.OracleDialect is deprecated
//			dialect = "org.hibernate.dialect.Oracle10gDialect"
			
//			logSql = true
//			formatSql = true
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            jndiName = "jdbc/writersTool"
        }
    }
}
