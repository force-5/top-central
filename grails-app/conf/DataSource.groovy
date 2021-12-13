dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect // must be set for transactions to work!
	username = "root"
	password = "igdefault"
    // loggingSql = true
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost:3306/care"
        }
    }
	demo {
		dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/care"
		}
	}
	test {
		dataSource {
//			dbCreate = "update" // one of 'create', 'create-drop','update'
			dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/careIntTest"
		}
	}
	production {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/care"
		}
	}
	mssql {
		dataSource {
			driverClassName = "net.sourceforge.jtds.jdbc.Driver"
			dialect = org.hibernate.dialect.SQLServerDialect
			dbCreate = "update"
			username = "sa"
			password = "igdefault"
			url = "jdbc:jtds:sqlserver://192.168.1.95:1433/care"
		}
	}
}
