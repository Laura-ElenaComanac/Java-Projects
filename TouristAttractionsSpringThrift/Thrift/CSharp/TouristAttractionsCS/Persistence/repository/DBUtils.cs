using System;
using System.Data;
using System.Reflection;
using System.Data.SQLite;
using System.Configuration;
using ConnectionUtils;
using System.Collections.Generic;

namespace Persistence.repository
{
	public static class DBUtils
	{


		private static IDbConnection instance = null;

		public static IDbConnection getConnection(IDictionary<String, string> properties)
		{
			if (instance == null || instance.State == System.Data.ConnectionState.Closed)
			{
				instance = getNewConnection(properties);
				instance.Open();
			}
			return instance;
		}

		private static IDbConnection getNewConnection(IDictionary<String, string> properties)
		{
			return ConnectionFactory.getInstance().createConnection(properties);
		}
	}
}
