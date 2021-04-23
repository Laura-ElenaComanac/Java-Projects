using System;
using System.Data;
using System.Data.SQLite;
using System.Configuration;
using System.Collections.Generic;

namespace ConnectionUtils
{
	public class SqliteConnectionFactory : ConnectionFactory
	{
		public override IDbConnection createConnection(IDictionary<String, string> properties)
		{
			//var props = ConfigurationManager.ConnectionStrings["db-ta"].ConnectionString;
			return new SQLiteConnection(properties["db-ta"]);
		}
	}
}
